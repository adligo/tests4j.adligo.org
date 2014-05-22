package org.adligo.tests4j.run.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;
import org.adligo.tests4j.run.helpers.Tests4J_ThreadFactory;
import org.adligo.tests4j.run.remote.socket_api.I_AfterMessageHandler;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_Commands;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;

public abstract class Tests4J_SocketApiHandler {
	private BlockingQueue<Tests4J_SocketMessage> messages = new ArrayBlockingQueue<>(100);
	private Map<Tests4J_SocketMessage, I_AfterMessageHandler> afterMessageTransportHandlers =
			new ConcurrentHashMap<Tests4J_SocketMessage, I_AfterMessageHandler>();
	private ExecutorService listenerService;
	private I_Tests4J_Reporter reporter = new ConsoleReporter();
	private OutputStream out;
	private InputStream in;
	private String connectionId;
	private Tests4J_Commands lastCommnadSent;
	protected AtomicBoolean connected = new AtomicBoolean(false);
	private int port;
	private String threadName;

	public Tests4J_SocketApiHandler() {}
	
	public Tests4J_SocketApiHandler(Tests4J_SocketMessage initalMessage) {
		messages.add(initalMessage);
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
		Tests4J_ThreadFactory factory = new Tests4J_ThreadFactory(threadName);
		listenerService = Executors.newFixedThreadPool(1, factory);
		listenerService.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					startChild();
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
						x.printStackTrace();
						return;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
				}
			}
		});
	}
	public void send(Tests4J_SocketMessage message) {
		messages.add(message);
	}
	
	public void send(Tests4J_SocketMessage message, I_AfterMessageHandler handler) {
		afterMessageTransportHandlers.put(message, handler);
		messages.add(message);
	}
	
	void transportMessage(Tests4J_SocketMessage p) throws IOException {
		if (reporter.isLogEnabled(Tests4J_SocketApiHandler.class)) {
			reporter.log("sending command " + p.getCommand());
		}
		lastCommnadSent = p.getCommand();
		String message = p.toSocketMessage();
		out.write(message.getBytes());
		out.flush();
		I_AfterMessageHandler handler = afterMessageTransportHandlers.get(message);
		if (handler != null) {
			handler.afterMessageTransported();
		}
	}
	
	void acceptMessage() throws IOException {
		try {
			StringBuilder sb = new StringBuilder();
			String line = null;
			//ASCII
			byte [] bytes = new byte[1];
			while (in.read(bytes) != -1) {
				sb.append(bytes[0]);
			}
			String content = sb.toString();
			if (!IsEmpty.isEmpty(content)) {
				String messageString = sb.toString();
				if (reporter.isLogEnabled(Tests4J_SocketApiHandler.class)) {
					reporter.log("in acceptMessage read line with message;\n" + messageString);
				}
				Tests4J_SocketMessage message = new Tests4J_SocketMessage(sb.toString());
				processIncomingMessage(message);
			} 
		} catch (IOException x) {
			x.printStackTrace();
			shutdown();
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
		connectionId = "";
		shutdownChild();
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

	public OutputStream getOut() {
		return out;
	}

	public InputStream getIn() {
		return in;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public String getConnectionId() {
		return connectionId;
	}

	void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public Tests4J_Commands getLastCommnadSent() {
		return lastCommnadSent;
	}
	
	abstract void startChild() throws IOException;
	
	abstract void processIncomingMessage(Tests4J_SocketMessage p) throws IOException;
	
	abstract void shutdownChild();

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	String getThreadName() {
		return threadName;
	}

	void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	
}
