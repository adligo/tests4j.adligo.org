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
	public static final String END_SECTION = "';";
	public static final String MESSAGE_START = "tests4j_socketMessage;command='";
	public static final String VERSION = "1.0";
	public static final String VERSION_KEY = "version='";
	public static final String CONNECTION_ID = "connection_id='";
	public static final String PAYLOAD = "payload=;";
	public static final String MESSAGE_END = "/tests4j_socketMessage;";
	public static int MIN_LENGTH = MESSAGE_START.length() +
			PAYLOAD.length() + MESSAGE_END.length();
	
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
		if (socketMessage.length() < MESSAGE_START.length()) {
			throw new IllegalArgumentException("Requires " + MESSAGE_START);
		}
		String afterSocketMessage = socketMessage.substring(MESSAGE_START.length() - 1, socketMessage.length());
		int index = afterSocketMessage.indexOf(END_SECTION);
		
		if (index == -1) {
			throw new IllegalArgumentException("Requires " + MESSAGE_START + "XXX" + END_SECTION);
		}
		String commandString = afterSocketMessage.substring(1, index);
		command = Tests4J_Commands.valueOf(commandString);
		afterSocketMessage = afterSocketMessage.substring(index + END_SECTION.length(), afterSocketMessage.length());
		
		index = afterSocketMessage.indexOf(VERSION_KEY);
		if (index != -1) {
			int endIndex = afterSocketMessage.indexOf(END_SECTION);
			version = afterSocketMessage.substring(VERSION_KEY.length(), endIndex);
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
		payload = afterSocketMessage.substring(index + PAYLOAD.length(), afterSocketMessage.length() - MESSAGE_END.length());
	}
	
	public String toSocketMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(MESSAGE_START);
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
		sb.append("\n");
		sb.append(MESSAGE_END);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result
				+ ((connectionId == null) ? 0 : connectionId.hashCode());
		result = prime * result + ((payload == null) ? 0 : payload.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tests4J_SocketMessage other = (Tests4J_SocketMessage) obj;
		if (command != other.command)
			return false;
		if (connectionId == null) {
			if (other.connectionId != null)
				return false;
		} else if (!connectionId.equals(other.connectionId))
			return false;
		if (payload == null) {
			if (other.payload != null)
				return false;
		} else if (!payload.equals(other.payload))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}
}
