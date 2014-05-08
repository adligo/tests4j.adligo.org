package org.adligo.tests4j.models.shared.coverage;

/**
 * 255 seems a nice line length limiter
 * for java source code.
 * @author scott
 *
 */
public class Tiny {
	private byte b;
	
	private Tiny(byte p) {
		b = p;
	}
	
	private Tiny(int p) {
		b = (byte) p;
	}
	
	public short get() {
		return (short) getInt();
	}
	
	public int getInt() {
		return b & 0xFF;
	}
}
