package org.adligo.tests4j.run.remote.socket_api;

import org.adligo.tests4j.models.shared.system.I_Tests4J_XML_IO;

import com.sun.xml.internal.ws.util.StringUtils;

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
public class Tests4J_SocketMessage implements I_Tests4J_XML_IO {
	
	public static final String REQUIRES_AUTH_CODE_ATTRIBUTE_ERROR = "Requires AuthCode Attribute.";
	public static final String REQUIRES_COMMAND_KEY_ATTRIBUTE_ERROR = "Requires Command Key Attribute.";
	public static final String TAG_NAME ="tests4j_socket_message";
	public static final String VERSION_KEY = "version" + EQUALS_QUOTE;
	public static final String MESSAGE_START = START + TAG_NAME + " " + VERSION_KEY;
	public static final String VERSION_1_0 = "1.0";
	
	public static final String COMMAND_KEY = "command" + EQUALS_QUOTE;
	public static final String AUTH_CODE_KEY = "authCode" + EQUALS_QUOTE;
	public static final String TAG_PAIR_END = END_START + TAG_NAME + END;
	public static int MIN_LENGTH = MESSAGE_START.length() +
			VERSION_KEY.length() + VERSION_1_0.length() + ATTRIBUTE_END.length() +
			COMMAND_KEY.length() + Tests4J_Commands.getMinLength() + ATTRIBUTE_END.length() + 
			AUTH_CODE_KEY.length() + ATTRIBUTE_END.length() +
			START_END.length();
	public static final String REQUIRES_ERROR = "Requires a " + TAG_NAME + " tag.";
	public static final String UNKNOWN_VERSION_ERROR_START = "Unknown Version ";
	public static final String UNKNOWN_VERSION_ERROR_END = " expecting "+ VERSION_1_0 +".";
	
	private Tests4J_Commands command;
	private Tests4J_Commands ack;
	private String version = VERSION_1_0;
	private String authCode;
	private String payload;
	
	public Tests4J_SocketMessage(Tests4J_Commands pCommand, Tests4J_Commands ackCommand) {
		command = pCommand;
		ack = ackCommand;
	}
	
	public Tests4J_SocketMessage(Tests4J_Commands pCommand, String pAuthCode) {
		command = pCommand;
		authCode = pAuthCode;
	}

	public Tests4J_SocketMessage(Tests4J_Commands pCommand, String pAuthCode, String pPayload) {
		command = pCommand;
		authCode = pAuthCode;
		payload = pPayload;
	}
	
	public Tests4J_SocketMessage(String xml) {
		if (xml == null) {
			throw new IllegalArgumentException(REQUIRES_ERROR);
		}
		int startIndex = xml.indexOf(MESSAGE_START);
		if (startIndex == -1) {
			throw new IllegalArgumentException(REQUIRES_ERROR);
		}
		int startLastIndex = startIndex + MESSAGE_START.length();
		int versionEnd = xml.indexOf(ATTRIBUTE_END, startLastIndex);
		if (versionEnd == -1) {
			throw new IllegalArgumentException(UNKNOWN_VERSION_ERROR_START + "null" + 
					UNKNOWN_VERSION_ERROR_END);
		}
		version = xml.substring(startLastIndex, versionEnd);
		if (!VERSION_1_0.equals(version)) {
			throw new IllegalArgumentException(UNKNOWN_VERSION_ERROR_START + version + 
					UNKNOWN_VERSION_ERROR_END);
		}
		
		int startCommandIndex = xml.indexOf(COMMAND_KEY, versionEnd);
		if (startCommandIndex == -1) {
			throw new IllegalArgumentException(REQUIRES_COMMAND_KEY_ATTRIBUTE_ERROR);
		}
		int startCommandEndIndex = startCommandIndex + COMMAND_KEY.length();
		int endCommandIndex = xml.indexOf(ATTRIBUTE_END, startCommandEndIndex);
		if (endCommandIndex == -1) {
			throw new IllegalArgumentException(REQUIRES_COMMAND_KEY_ATTRIBUTE_ERROR);
		}
		String commandString = xml.substring(startCommandEndIndex, endCommandIndex);
		command = Tests4J_Commands.valueOf(commandString);
		
		int startAuthIndex = xml.indexOf(AUTH_CODE_KEY, endCommandIndex);
		if (startAuthIndex == -1) {
			throw new IllegalArgumentException(REQUIRES_AUTH_CODE_ATTRIBUTE_ERROR);
		}
		int startAuthEndIndex = startAuthIndex + AUTH_CODE_KEY.length();
		int endAuthIndex = xml.indexOf(ATTRIBUTE_END, startAuthEndIndex);
		if (endAuthIndex == -1) {
			throw new IllegalArgumentException(REQUIRES_AUTH_CODE_ATTRIBUTE_ERROR);
		}
		authCode = xml.substring(startAuthEndIndex, endAuthIndex);
	}
	
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append(MESSAGE_START);
		sb.append(VERSION_1_0);
		sb.append(ATTRIBUTE_END);
		
		sb.append(COMMAND_KEY);
		sb.append(command);
		sb.append(ATTRIBUTE_END);
		
		sb.append(AUTH_CODE_KEY);
		sb.append(authCode);
		sb.append(ATTRIBUTE_END);
		
		if (payload == null) {
			sb.append(START_END);
		} else {
			sb.append(END);
			sb.append(NEW_LINE);
			sb.append(TAB);
			sb.append(payload);
			sb.append(NEW_LINE);
			sb.append(TAG_PAIR_END);
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

	public String getAuthCode() {
		return authCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result
				+ ((authCode == null) ? 0 : authCode.hashCode());
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
		if (authCode == null) {
			if (other.authCode != null)
				return false;
		} else if (!authCode.equals(other.authCode))
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
