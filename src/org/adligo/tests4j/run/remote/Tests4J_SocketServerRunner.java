package org.adligo.tests4j.run.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;
import org.adligo.tests4j.models.shared.system.DefaultSystemExitor;
import org.adligo.tests4j.models.shared.system.I_SystemExit;
import org.adligo.tests4j.models.shared.system.I_TrialRunListener;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;
import org.adligo.tests4j.run.helpers.Tests4J_ThreadFactory;
import org.adligo.tests4j.run.remote.socket_api.AfterShutdownHandler;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_Commands;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;

public class Tests4J_SocketServerRunner extends Tests4J_SocketApiHandler implements I_TrialRunListener {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private AfterShutdownHandler afterShutdownHandler;
	private I_SystemExit exitor = new DefaultSystemExitor();
	private AtomicBoolean connected = new AtomicBoolean(false);
	
	public Tests4J_SocketServerRunner() {
		super.setThreadName(Tests4J_ThreadFactory.REMOTE_RUNNER_THREAD_NAME);
	}
	public static void main(String [] args) {
		//note these lines will never be testable as it runs System.exit(0);
		Tests4J_SocketServerRunner runner = new Tests4J_SocketServerRunner();
		
		ConsoleReporter reporter = new ConsoleReporter();
		reporter.setLogOn(Tests4J_SocketServerRunner.class);
		reporter.setLogOn(Tests4J_SocketApiHandler.class);
		reporter.setPrefix("Tests4J_Runner:");
		runner.setReporter(reporter);
		
		runner.main(args, new DefaultSystemExitor());
	}
	
	public void main(String[] args, I_SystemExit pExitor) {
		Integer port = null;
		for (int i = 0; i < args.length; i++) {
			if("-port".equalsIgnoreCase(args[i])) { //$NON-NLS-1$
				if (args.length > i +1) {
					port= Integer.parseInt(args[i+1]);
					i++;
				}
			}
		}
		
		I_Tests4J_Reporter reporter = super.getReporter();
		if (port == null) {
			if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
				reporter.log("Port is null exiting.");
			}
			return;
		}
		startup(port, pExitor);
	}
	
	
	public void startup(int pPort, I_SystemExit pExitor) {
		super.setPort(pPort);
		afterShutdownHandler = new AfterShutdownHandler(pExitor);
		
		I_Tests4J_Reporter reporter = super.getReporter();
		reporter.setLogOn(Tests4J_SocketServerRunner.class);
		reporter.setLogOn(Tests4J_SocketApiHandler.class);
		super.start();
			
	}

	@Override
	public void onMetadataCalculated(I_TrialRunMetadata metadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartingTrail(String trialName) {
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

	@Override
	void processIncomingMessage(Tests4J_SocketMessage message) {
		Tests4J_Commands cmd = message.getCommand();
		if (super.getReporter().isLogEnabled(Tests4J_SocketServerRunner.class)) {
			super.getReporter().log("received command " + cmd);
		}
		switch (cmd) {
			case CONNECT:
				if (connected.get()) {
					throw new SecurityException("Already Connected");
				}
				String uid = message.getPayload();
				long connectTime = System.currentTimeMillis();
				String connectionId = uid + connectTime;
				super.setConnectionId(connectionId);
				super.send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						connectionId));
				break;
			case RUN:
				checkConnectionId(message);
				
				break;
			case SHUTDOWN:
				checkConnectionId(message);
				super.send(new Tests4J_SocketMessage(
						Tests4J_Commands.ACK, 
						super.getConnectionId()), afterShutdownHandler);
			default:
		}
	}

	private void checkConnectionId(Tests4J_SocketMessage message) {
		String connectionId = super.getConnectionId();
		if (connectionId == null) {
			throw new SecurityException("Not Connected!");
		}
		if ( !connectionId.equals(message.getConnectionId())) {
			throw new SecurityException("ConnectionId Mismatch!");
		}
	}

	@Override
	void shutdownChild() {
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

	public I_SystemExit getExitor() {
		return exitor;
	}

	public void setExitor(I_SystemExit exitor) {
		this.exitor = exitor;
	}

	@Override
	void startChild() throws IOException {
		int port = super.getPort();
		I_Tests4J_Reporter reporter = super.getReporter();
		InetAddress localhost = InetAddress.getLocalHost();
		if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
			reporter.log("Starting SocketServer on " + localhost + " " + port);
		}
		
		serverSocket = new ServerSocket(port, 3, localhost);
	    clientSocket = serverSocket.accept();
	    super.setOut(clientSocket.getOutputStream());
	    super.setIn(clientSocket.getInputStream());
	    if (reporter.isLogEnabled(Tests4J_SocketServerRunner.class)) {
			reporter.log("SocketServer on " + localhost + " " + port + " started");
		}
	}


}
