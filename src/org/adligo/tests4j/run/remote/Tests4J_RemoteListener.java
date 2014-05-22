package org.adligo.tests4j.run.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;
import org.adligo.tests4j.run.helpers.Tests4J_ThreadFactory;
import org.adligo.tests4j.run.remote.socket_api.I_AckHandler;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_Commands;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;

/**
 * This class listens to a remove JVM running a Tests4J_RemoteRunner
 * @author scott
 *
 */
public class Tests4J_RemoteListener extends Tests4J_SocketApiHandler {
	private Socket socket;
	private String uuid;
	private I_AckHandler onConnectHandler;
	
	public static void main(String [] args) {
		Integer port = null;
		for (int i = 0; i < args.length; i++) {
			if("-port".equalsIgnoreCase(args[i])) { //$NON-NLS-1$
				if (args.length > i +1) {
					port= Integer.parseInt(args[i+1]);
					i++;
				}
			}
		}
		final Tests4J_RemoteListener listener = new Tests4J_RemoteListener();
		ConsoleReporter reporter = new ConsoleReporter();
		reporter.setLogOn(Tests4J_RemoteListener.class);
		reporter.setLogOn(Tests4J_SocketApiHandler.class);
		reporter.setPrefix("Tests4J_Listener:");
		listener.setReporter(reporter);

		listener.connect(port, new I_AckHandler() {
			
			@Override
			public void onAck() {
				System.out.println("on ack " + listener.getLastCommnadSent());
			}
		});
	
	}
	public Tests4J_RemoteListener() {
		super.setThreadName(Tests4J_ThreadFactory.REMOTE_LISTENER_THREAD_NAME);
	}
	
	public void connect(int port, I_AckHandler pOnConnectHandler) {
		super.setPort(port);
		UUID random = UUID.randomUUID();
		Tests4J_SocketMessage message = new Tests4J_SocketMessage(
				Tests4J_Commands.CONNECT, random.toString());
		super.send(message);
		onConnectHandler = pOnConnectHandler;
		super.start();
		
	}
	

	void processIncomingMessage(Tests4J_SocketMessage message) {
		Tests4J_Commands cmd = message.getCommand();
		if (super.getReporter().isLogEnabled(Tests4J_RemoteListener.class)) {
			super.getReporter().log("received command " + message.getCommand());
		}
		switch (cmd) {
			case ACK:
				Tests4J_Commands lastCommandSent = getLastCommnadSent();
				switch (lastCommandSent) {
					case CONNECT:
						String connectionId = message.getPayload();
						super.setConnectionId(connectionId);
						onConnectHandler.onAck();
						super.connected.set(true);
					break;
					case SHUTDOWN:
					shutdown();
					break;
					default:
						//do nothing for run
				}
			break;
		}
	}


	
	public void shutdownAndDisconnect() {
		super.send(new Tests4J_SocketMessage(Tests4J_Commands.SHUTDOWN, super.getConnectionId()));
	}
	
	@Override
	void shutdownChild() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException x) {
				x.printStackTrace();
			}
		}
	}


	@Override
	void startChild() throws IOException {
		try {
			I_Tests4J_Reporter reporter = super.getReporter();
			int port = super.getPort();
			InetAddress addr = InetAddress.getLocalHost();
			if (reporter.isLogEnabled(Tests4J_RemoteListener.class)) {
				reporter.log("Opening connection to " + addr + " " + port);
			}
			socket = new Socket(addr,port);
			super.setOut(socket.getOutputStream());
			super.setIn(socket.getInputStream());
			if (reporter.isLogEnabled(Tests4J_RemoteListener.class)) {
				reporter.log("Connection to " + addr + " " + port + " is open.");
			}
		} catch (UnknownHostException e) {
			throw new IOException(e);
		} 
	}
}
