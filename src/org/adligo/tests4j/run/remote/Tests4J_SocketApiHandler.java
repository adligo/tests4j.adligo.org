package org.adligo.tests4j.run.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;

public abstract class Tests4J_SocketApiHandler {
	private BlockingQueue<Tests4J_SocketMessage> messages = new ArrayBlockingQueue<>(100);
	private ExecutorService listenerService;
	private I_Tests4J_Reporter reporter = new ConsoleReporter();
	private PrintWriter out;
	private BufferedReader in;
	private String connectionId;
	
	public Tests4J_SocketApiHandler() {}
	
	public Tests4J_SocketApiHandler(Tests4J_SocketMessage initalMessage) {
		messages.add(initalMessage);
	}
	
	void start() {
		listenerService = Executors.newFixedThreadPool(1);
		listenerService.submit(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					try {
						if (messages.size() == 0) {
							acceptMessage();
						} else {
							Tests4J_SocketMessage message = messages.take();
							transportMessage(message);
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
	
	void transportMessage(Tests4J_SocketMessage p) throws IOException {
		if (reporter.isLogEnabled(Tests4J_RunnerWithSocket.class)) {
			reporter.log("sending command " + p.getCommand());
		}
		String message = p.toSocketMessage();
		out.write(message);
		out.flush();
	}
	
	void acceptMessage() throws IOException {
		if (reporter.isLogEnabled(Tests4J_SocketApiHandler.class)) {
			reporter.log("in acceptMessage ");
		}
		try {
			StringBuilder sb = new StringBuilder();
			String line = in.r
			while(line != null) {
				sb.append(line);
				if (reporter.isLogEnabled(Tests4J_RunnerWithSocket.class)) {
					reporter.log("in acceptMessage read line;\n" + line);
				}
				line = in.readLine();
			}
			String content = sb.toString();
			if (!IsEmpty.isEmpty(content)) {
				Tests4J_SocketMessage message = new Tests4J_SocketMessage(sb.toString());
				processIncomingMessage(message);
			}
		} catch (IOException x) {
			x.printStackTrace();
		}
	}
	
	abstract void processIncomingMessage(Tests4J_SocketMessage p) throws IOException;
	
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

	public PrintWriter getOut() {
		return out;
	}

	public BufferedReader getIn() {
		return in;
	}

	public void setOut(PrintWriter out) {
		this.out = out;
	}

	public void setIn(BufferedReader in) {
		this.in = in;
	}

	public String getConnectionId() {
		return connectionId;
	}

	void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
}
