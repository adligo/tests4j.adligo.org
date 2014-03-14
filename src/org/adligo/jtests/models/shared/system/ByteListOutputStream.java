package org.adligo.jtests.models.shared.system;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is to bridget GWT and JSE,
 * otherwise I would have used ByteArrayOutputStream
 * @author scott
 *
 */
public class ByteListOutputStream  extends OutputStream {
	private List<byte[]> bytes = new ArrayList<byte[]>();
	private byte[] currentArray;
	private int chunckSize;
	private int currentChunk;
	
	public ByteListOutputStream(int pChunkSize) {
		chunckSize = pChunkSize;
		currentArray = new byte[chunckSize];
	}
	
	@Override
	public synchronized void write(int b) throws IOException {
		if (currentChunk > chunckSize) {
			bytes.add(currentArray);
			currentArray = new byte[chunckSize];
			currentChunk = 0;
			currentArray[currentChunk] = (byte) b;
		} else {
			currentArray[currentChunk] = (byte) b;
			currentChunk++;
		}
		
	}

	/**
	 * returns a string representing all the bytes in this
	 * output stream
	 */
	@Override
	public synchronized String toString() {
		int size = (bytes.size() * chunckSize) + currentChunk;
		byte [] all = new byte[size];
		int counter = 0;
		for (byte[] bs: bytes) {
			for (int i = 0; i < bs.length; i++) {
				all[counter] = bs[i];
				counter++;
			}
		}
		for (int i = 0; i < currentChunk; i++) {
			all[counter] = currentArray[i];
			counter++;
		}
		String toRet = new String(all);
		bytes.clear();
		currentChunk = 0;
		currentArray = new byte[chunckSize];
		return toRet;
	}

}
