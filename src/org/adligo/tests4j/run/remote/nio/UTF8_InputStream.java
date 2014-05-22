package org.adligo.tests4j.run.remote.nio;

import java.io.IOException;
import java.io.InputStream;

/**
 * a input stream of UTF8 characters 
 * could also be used in (aws_client).
 * 
 * Some class like this should be added to the JSE standard.
 * 
 * @author scott
 *
 */
public class UTF8_InputStream {
	private InputStream in;
	
	public UTF8_InputStream(InputStream p) {
		in = p;
	}
	
	/**
	 * 
	 * @return null at the end of the InputStream
	 */
	public Character read() throws IOException {
		byte [] bytes = new byte[1];
		UTF8_CharacterReader cr = new UTF8_CharacterReader();
		int index = 0;
		while (in.read(bytes) != -1) {
			cr.addByte(index, bytes[0]);
			if (!cr.needsBytes()) {
				return cr.toCharacter();
			}
			index++;
		}
		return null;
	}
	
	public void close() {
		try {
			in.close();
		} catch (IOException x) {
			//ignore, what could be done here close it again?
		}
	}
}
