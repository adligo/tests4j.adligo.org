package org.adligo.tests4j.run.io;

import java.util.Arrays;

/**
 * again JSE could probably use something like this
 * 
 * @author scott
 *
 */
public class Bits {
	private boolean [] bits;
	
	public Bits(String binString) {
		bits = new boolean[binString.length()];
		char [] chars = binString.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == '0') {
				bits[i] = false;
			} else {
				bits[i] = true;
			}
		}
	}
	
	public boolean [] toBits() {
		return Arrays.copyOf(bits, bits.length);
	}
	
	public boolean [] toBits(int pad) {
		boolean [] toRet = new boolean[bits.length + pad];
		for (int i = 0; i < pad; i++) {
			toRet[i] = false;
		}
		for (int i = 0; i < bits.length; i++) {
			toRet[i + pad] = bits[i];
		}
		return toRet;
	}
	
}
