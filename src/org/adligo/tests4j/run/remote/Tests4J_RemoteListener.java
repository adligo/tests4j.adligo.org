package org.adligo.tests4j.run.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;
import org.adligo.tests4j.run.remote.socket_api.I_SocketMessageHandler;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_Commands;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;

/**
 * This class listens to a remove JVM running a Tests4J_RemoteRunner
 * @author scott
 *
 */
public class Tests4J_RemoteListener extends Tests4J_SocketApiHandler {
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private AtomicBoolean connected = new AtomicBoolean(false);
	private Tests4J_Commands lastCommnadSent;
	private String uuid;
	private I_SocketMessageHandler onConnectHandler;
	
	public Tests4J_RemoteListener(int port, I_SocketMessageHandler pOnConnectHandler) {
		UUID random = UUID.randomUUID();
		Tests4J_SocketMessage message = new Tests4J_SocketMessage(
				Tests4J_Commands.CONNECT, random.toString());
		super.send(message);
		onConnectHandler = pOnConnectHandler;
		
		try {
			socket = new Socket("localhost",port);
			out  = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(socket.getInputStream()));
			super.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	void transportMessage(Tests4J_SocketMessage p) throws IOException {
		if (super.getReporter().isLogEnabled(Tests4J_RemoteListener.class)) {
			super.getReporter().log("sending command " + p.getCommand());
		}
		String message = p.toSocketMessage();
		lastCommnadSent = p.getCommand();
		out.write(message);
		out.flush();
	}



	void processIncomingMessage(Tests4J_SocketMessage message) {
		Tests4J_Commands cmd = message.getCommand();
		if (super.getReporter().isLogEnabled(Tests4J_RemoteListener.class)) {
			super.getReporter().log("received command " + message.getCommand());
		}
		switch (cmd) {
			case ACK:
				switch (lastCommnadSent) {
					case CONNECT:
						String connectionId = message.getPayload();
						super.setConnectionId(connectionId);
						onConnectHandler.onAck();
					break;
				}
			break;
		}
	}


}
