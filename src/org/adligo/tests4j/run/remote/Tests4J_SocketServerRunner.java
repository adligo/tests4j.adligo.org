package org.adligo.tests4j.run.remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.system.DefaultSystemExitor;
import org.adligo.tests4j.models.shared.system.I_SystemExit;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Controls;
import org.adligo.tests4j.models.shared.system.I_Tests4J_RemoteInfo;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.Tests4J_Params;
import org.adligo.tests4j.models.shared.system.Tests4J_RemoteInfo;
import org.adligo.tests4j.models.shared.xml.XML_Builder;
import org.adligo.tests4j.run.Tests4J;
import org.adligo.tests4j.run.helpers.Tests4J_ThreadFactory;
import org.adligo.tests4j.run.remote.io.I_CharacterInputStream;
import org.adligo.tests4j.run.remote.io.UTF8_InputStream;
import org.adligo.tests4j.run.remote.socket_api.AfterShutdownHandler;
import org.adligo.tests4j.run.remote.socket_api.I_AfterMessageHandler;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_Commands;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;
import org.adligo.tests4j.shared.report.summary.SummaryReporter;

public class Tests4J_SocketServerRunner implements I_TrialRunListener {
	private BlockingQueue<Tests4J_SocketMessage> messages = new ArrayBlockingQueue<>(100);
	private Map<Tests4J_SocketMessage, I_AfterMessageHandler> afterMessageTransportHandlers =
			new ConcurrentHashMap<Tests4J_SocketMessage, I_AfterMessageHandler>();
	private I_Tests4J_Reporter reporter = new SummaryReporter();
	private OutputStream out;
	private InputStream in;
	private Tests4J_Commands lastCommnadSent;
	protected AtomicBoolean connected = new AtomicBoolean(false);
	private I_Tests4J_RemoteInfo info;
	private I_CharacterInputStream utf8In;
	private RemoteMessageReader reader;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private AfterShutdownHandler afterShutdownHandler;
	private I_SystemExit exitor = new DefaultSystemExitor();
	private ExecutorService listenerService;
	private I_Tests4J_Controls controlls;
	
	
	public static void main(String [] args) {
		//note these lines will never be testable as it runs System.exit(0);
		Tests4J_SocketServerRunner runner = new Tests4J_SocketServerRunner();
		
		SummaryReporter reporter = new SummaryReporter();
		reporter.setLogOn(Tests4J_SocketServerRunner.class);
		runner.setReporter(reporter);
		
		runner.main(args, new DefaultSystemExitor());
	}
	
	public void main(String[] args, I_SystemExit pExitor) {
		Integer port = null;
		String authCode = "";
		for (int i = 0; i < args.length; i++) {
			if("-port".equalsIgnoreCase(args[i])) { //$NON-NLS-1$
				if (args.length > i +1) {
					port= Integer.parseInt(args[i+1]);
					i++;
				}
			}
			if("-authCode".equalsIgnoreCase(args[i])) { //$NON-NLS-1$
				if (args.length > i +1) {
					authCode = args[i+1];
					i++;
				}
			}
		}
		
		if (port == null) {
			if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
				reporter.log("Tests4J_SocketServerRunner: Port is null exiting.");
			}
			return;
		}
		if (StringMethods.isEmpty(authCode)) {
			if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
				reporter.log("Tests4J_SocketServerRunner: AuthCode is empty exiting.");
			}
			return;
		}
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			info = new Tests4J_RemoteInfo(localhost.getHostName(), port, authCode);
			startup(pExitor);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void startup() {
		startup(new DefaultSystemExitor());
	}
	
	public void startup(I_SystemExit pExitor) {
		
		afterShutdownHandler = new AfterShutdownHandler(pExitor);
		
		reporter.setLogOn(Tests4J_SocketServerRunner.class);
		start();
			
	}

	/**
	 * shuts down the listener service
	 */
	void stop() {
		listenerService.shutdownNow();
	}
	/**
	 * starts listening to the connection
	 */
	void start() {
		Tests4J_ThreadFactory factory = new Tests4J_ThreadFactory(Tests4J_ThreadFactory.SERVER_THREAD_NAME, reporter);
		listenerService = Executors.newFixedThreadPool(1, factory);
		listenerService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					int port = info.getPort();
					InetAddress localhost = InetAddress.getLocalHost();
					if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
						reporter.log("Tests4J_SocketServerRunner: Starting SocketServer on " + info.getHost() + " " + port);
					}
					
					serverSocket = new ServerSocket(port, 3, localhost);
				    clientSocket = serverSocket.accept();
				    out = clientSocket.getOutputStream();
				    in = clientSocket.getInputStream();
				    utf8In = new UTF8_InputStream(in);
				    reader = new RemoteMessageReader(utf8In);
				    
				    if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
						reporter.log("Tests4J_SocketServerRunner: SocketServer on " + localhost + " " + port + " started");
					}
				} catch (IOException x) {
					reporter.onError(x);
					shutdown();
					return;
				}
				while (true) {
					try {
						if (messages.size() == 0) {
							acceptMessage();
						} else {
							Tests4J_SocketMessage message = messages.take();
							transportMessage(message);
							acceptMessage();
						}
					} catch (IOException x) {
						reporter.onError(x);
						return;
					} catch (InterruptedException e) {
						reporter.onError(e);
						return;
					}
				}
			}
		});
	}
	
	void acceptMessage() throws IOException {
		String content = reader.read();
		if (!StringMethods.isEmpty(content)) {
			if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
				reporter.log("Tests4J_SocketServerRunner: in acceptMessage read line with message;\n" + content);
			}
			Tests4J_SocketMessage message = new Tests4J_SocketMessage(content);
			processIncomingMessage(message);
		} 
	}
	
	void shutdown() {
		stop();
		if (out != null) {
			try {
				out.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (in != null) {
			try {
				in.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		this.connected.set(false);
		
		if (clientSocket != null) {
			try {
				clientSocket.close();
			} catch (IOException x) {
				x.printStackTrace();
			}
		}
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException x) {
				x.printStackTrace();
			}
		}
		exitor.doSystemExit(0);
	}
	@Override
	public void onMetadataCalculated(I_TrialRunMetadata metadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartingTrial(String trialName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartingTest(String trialName, String testName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestCompleted(String trialName, String testName,
			boolean passed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTrialCompleted(I_TrialResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRunCompleted(I_TrialRunResult result) {
		// TODO Auto-generated method stub
		
	}

	void processIncomingMessage(Tests4J_SocketMessage message) {
		Tests4J_Commands cmd = message.getCommand();
		if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
			reporter.log("Tests4J_SocketServerRunner: received command " + cmd);
		}
		String authCode = info.getAuthCode();
		
		if (cmd == Tests4J_Commands.CONNECT) {
			if (connected.get()) {
				send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						"Already Connected to another client!"));
				return;
			}
			String messagePayload = message.getPayload();
			String version = message.getVersion();
			if (!"1.0".equals(version)) {
				send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						version  + " version not known!"));
				return;
			}
			if (!authCode.equals(messagePayload)) {
				send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						"AuthCode mismatch!"));
				return;
			} else {
				send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						Tests4J_Commands.CONNECT));
				return;
			}
		} else { 
			String messageAuthCode = message.getAuthCode();
			
			if (!authCode.equals(messageAuthCode)) {
				send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						"authCode mistmatch!"));
			} else {
				switch (cmd) {
					case RUN:
						String xml = message.getPayload();
						Tests4J_Params params = new Tests4J_Params();
						params.fromXml(xml);
						params.setReporter(reporter);
						controlls = Tests4J.run(params);
						send(new Tests4J_SocketMessage(
								Tests4J_Commands.ACK, 
								Tests4J_Commands.RUN));
						break;
					case SHUTDOWN:
						if (controlls != null) {
							controlls.shutdown();
						}
						send(new Tests4J_SocketMessage(
								Tests4J_Commands.ACK, 
								Tests4J_Commands.SHUTDOWN), afterShutdownHandler);
					default:
				}
			}
		}
	}



	public I_SystemExit getExitor() {
		return exitor;
	}

	public void setExitor(I_SystemExit exitor) {
		this.exitor = exitor;
	}


	public void send(Tests4J_SocketMessage message) {
		messages.add(message);
	}
	
	public void send(Tests4J_SocketMessage message, I_AfterMessageHandler handler) {
		afterMessageTransportHandlers.put(message, handler);
		messages.add(message);
	}
	
	void transportMessage(Tests4J_SocketMessage p) throws IOException {
		if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
			reporter.log("sending command " + p.getCommand());
		}
		lastCommnadSent = p.getCommand();
		XML_Builder builder = new XML_Builder();
		p.toXml(builder);
		String message = builder.toXmlString();
		out.write(message.getBytes());
		out.flush();
		I_AfterMessageHandler handler = afterMessageTransportHandlers.get(message);
		if (handler != null) {
			handler.afterMessageTransported();
		}
	}
	

	
	public boolean isConnected() {
		return connected.get();
	}
	
	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}

	@SuppressWarnings("null")
	public void setReporter(I_Tests4J_Reporter p) {
		if (p == null) {
			//throw a NPE
			p.hashCode();
		} else {
			reporter = p;
		}
	}



	public Tests4J_Commands getLastCommnadSent() {
		return lastCommnadSent;
	}
	
}
