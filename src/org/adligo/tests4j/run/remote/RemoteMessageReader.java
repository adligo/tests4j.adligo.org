package org.adligo.tests4j.run.remote;

import java.io.IOException;

import org.adligo.tests4j.run.remote.nio.I_CharacterInputStream;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;

public class RemoteMessageReader {
	private I_CharacterInputStream utf8In;
	
	public RemoteMessageReader(I_CharacterInputStream p) {
		utf8In = p;
	}
	
	public String read() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		Character c = utf8In.read();
		while (c != null) {
			sb.append(c);
			if (Tests4J_SocketMessage.MIN_LENGTH <= sb.length()) {
				if (sb.indexOf(Tests4J_SocketMessage.MESSAGE_END) != -1) {
					break;
				}
			}
			c = utf8In.read();
		}
		return sb.toString();
	}
}
