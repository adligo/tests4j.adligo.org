package org.adligo.tests4j.run.remote.socket_api;

import org.adligo.tests4j.models.shared.common.IsEmpty;

/**
 * the socket message is a builder class
 * which send/parses a string
 * 
 * Note the Tests4J_Socket_Api is 
 * synchronous both ways.
 * 
 * @author scott
 *
 */
public class Tests4J_SocketMessage {
	private static final String END_SECTION = "';";
	private static final String TESTS4J_SOCKET_MESSAGE_COMMAND = "tests4j_socketMessage;command='";
	private static final String VERSION = "1.0";
	public static final String VERSION_KEY = "version='";
	public static final String CONNECTION_ID = "connection_id='";
	private static final String PAYLOAD = "payload=;";
	private Tests4J_Commands command;
	private String version = VERSION;
	private String connectionId;
	private String payload;
	
	public Tests4J_SocketMessage(Tests4J_Commands pCommand, String pPayload) {
		command = pCommand;
		payload = pPayload;
	}

	public Tests4J_SocketMessage(Tests4J_Commands pCommand, String pConnectionId, String pPayload) {
		command = pCommand;
		connectionId = pConnectionId;
		payload = pPayload;
	}
	
	public Tests4J_SocketMessage(String socketMessage) {
		if (socketMessage.length() >= TESTS4J_SOCKET_MESSAGE_COMMAND.length()) {
			throw new IllegalArgumentException("Requires " + TESTS4J_SOCKET_MESSAGE_COMMAND);
		}
		String afterSocketMessage = socketMessage.substring(TESTS4J_SOCKET_MESSAGE_COMMAND.length() - 1, socketMessage.length());
		int index = afterSocketMessage.indexOf(END_SECTION);
		
		if (index == -1) {
			throw new IllegalArgumentException("Requires " + TESTS4J_SOCKET_MESSAGE_COMMAND + "XXX" + END_SECTION);
		}
		String commandString = afterSocketMessage.substring(0, index);
		command = Tests4J_Commands.valueOf(commandString);
		afterSocketMessage = afterSocketMessage.substring(index + END_SECTION.length(), afterSocketMessage.length());
		
		index = afterSocketMessage.indexOf(VERSION_KEY);
		if (index != -1) {
			int endIndex = afterSocketMessage.indexOf(END_SECTION);
			version = afterSocketMessage.substring(VERSION_KEY.length() - 1, endIndex);
		}
		index = afterSocketMessage.indexOf(CONNECTION_ID);
		if (index != -1) {
			int endIndex = afterSocketMessage.indexOf(END_SECTION, index);
			connectionId = afterSocketMessage.substring(CONNECTION_ID.length() - 1, endIndex);
		}
		
		
		index = afterSocketMessage.indexOf(PAYLOAD);
		if (index == -1) {
			throw new IllegalArgumentException("Requires " + PAYLOAD);
		}
		payload = afterSocketMessage.substring(PAYLOAD.length() - 1, afterSocketMessage.length());
	}
	
	public String toSocketMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(TESTS4J_SOCKET_MESSAGE_COMMAND);
		sb.append(command);
		sb.append(END_SECTION);
		if (command == Tests4J_Commands.CONNECT) {
			sb.append(VERSION_KEY);
			sb.append(version);
			sb.append(END_SECTION);
		} else if (command == Tests4J_Commands.RUN ||
				command == Tests4J_Commands.SHUTDOWN) {
			if (IsEmpty.isEmpty(connectionId)) {
				throw new IllegalArgumentException("Run and Shutdown require a connectionId");
			}
			sb.append(CONNECTION_ID);
			sb.append(connectionId);
			sb.append(END_SECTION);
		}
		if (payload != null) {
			sb.append(PAYLOAD);
			sb.append(payload);
		}
		return sb.toString();
	}

	public Tests4J_Commands getCommand() {
		return command;
	}

	public String getVersion() {
		return version;
	}

	public String getPayload() {
		return payload;
	}

	public String getConnectionId() {
		return connectionId;
	}
}
