package org.adligo.tests4j.run.remote.io;

import java.io.UnsupportedEncodingException;

/**
 * http://en.wikipedia.org/wiki/UTF-8
 * Read in one UTF8 character at a time,
 * useful for socket programming (could be reused in aws_client).
 * Something like this should be added to the JSE standard.
 * 
 * Not thread safe, please use one per thread.
 * 
 * @author scott
 *
 */
public class UTF8_CharacterBuilder {

	private Byte [] bytes = new Byte[6];
	
	public void addByte(int index, byte b) {
		bytes[index] = b;
	}
	
	public void clear() {
		bytes = new Byte[6];
	}
	
	public boolean needsBytes() {
		Byte b = bytes[0];
		if (b == null) {
			return true;
		}
		ByteMutant br = new ByteMutant(b);
		if (br.getSlotZero()) {
			return false;
		}
		int counter = br.getUTF8_BytesInSequence();
		for (int i = 1; i < counter; i++) {
			Byte bt = bytes[i];
			if (bt == null) {
				return true;
			}
		}
		return false;
	}
	
	public Character toCharacter() throws UnsupportedEncodingException {
		if (needsBytes()) {
			return null;
		}
		Byte b = bytes[0];
		ByteMutant br = new ByteMutant(b);
		int counter = br.getUTF8_BytesInSequence();
		byte [] charBytes = new byte[counter];
		for (int i = 0; i < charBytes.length; i++) {
			charBytes[i] = bytes[i];
		}
		return new String(charBytes, "UTF-8").charAt(0);
	}
}
