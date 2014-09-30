package org.adligo.tests4j.run.remote;

import java.io.IOException;

import org.adligo.tests4j.run.io.I_CharacterInputStream;
import org.adligo.tests4j.run.remote.socket_api.Tests4J_SocketMessage;
import org.adligo.tests4j.shared.xml.I_XML_Producer;

public class RemoteMessageReader {
	private I_CharacterInputStream utf8In;
	
	public RemoteMessageReader(I_CharacterInputStream p) {
		utf8In = p;
	}
	
	public String read() throws IOException {
		StringBuilder sb = new StringBuilder();
		
		Character c = utf8In.read();
		boolean slash = false;
		boolean end = false;
		boolean startAnotherTag = false;
		StringBuilder sbAfterSlash = new StringBuilder();
		
		while (c != null) {
			sb.append(c);
			
			if ('>' == c) {
				if (slash) {
					//encountered <tests4j_socket_message version="1.0" command="X" authCode="X" />
					break;
				}
				end = true;
			} 
			if (end) {
				if ('<' == c) {
					startAnotherTag = true;
				}
			} else {
				if ('/' == c) {
					slash = true;
				} 
			}
			
			if (startAnotherTag) {
				if (Tests4J_SocketMessage.MIN_LENGTH <= sb.length()) {
					sbAfterSlash.append(c);
					if (sbAfterSlash.length() == Tests4J_SocketMessage.TAG_PAIR_END.length()) {
						String afterSlash = sbAfterSlash.toString();
						if (Tests4J_SocketMessage.TAG_PAIR_END.equals(afterSlash)) {
							//encountered </tests4j_socket_message> end tag
							break;
						}
					}
				}
			}
			c = utf8In.read();
		}
		return sb.toString();
	}
}
