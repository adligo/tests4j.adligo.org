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
	
	public Bits(boolean [] array) {
    bits = new boolean[8];
    for (int i = 0; i < bits.length; i++) {
      bits[i] = array[i];
    }
  }
	
	public Bits(byte b) {
    bits = new boolean[8];
    toBooleans(b);
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
	
 public static int unsign(byte p) {
    return p & 0xff;
  }
	
	private void toBooleans(byte bigB) {
    int result = unsign(bigB);
    if (result >= 128) {
      result = result - 128;
      bits[0] = true;
    }
    if (result >= 64) {
      result = result - 64;
      bits[1] = true;
    }
    if (result >= 32) {
      result = result - 32;
      bits[2] = true;
    }
    if (result >= 16) {
      result = result - 16;
      bits[3] = true;
    }
    if (result >= 8) {
      result = result - 8;
      bits[4] = true;
    }
    if (result >= 4) {
      result = result - 4;
      bits[5] = true;
    }
    if (result >= 2) {
      result = result - 2;
      bits[6] = true;
    }
    if (result >= 1) {
      result = result - 1;
      bits[7] = true;
    }
  }
	
	public String toOnesAndZeros() {
    char [] chars = new char[8];
    for (int i = 0; i < chars.length; i++) {
      boolean bit = bits[i];
      if (bit) {
        chars[i] = '1';
      } else {
        chars[i] = '0';
      }
    }
    return new String(chars);
  }
	
	public byte toByte() {
	  String ozs = toOnesAndZeros();
    byte b = (byte) Integer.parseInt(ozs, 2);
    return b;
	}
}
