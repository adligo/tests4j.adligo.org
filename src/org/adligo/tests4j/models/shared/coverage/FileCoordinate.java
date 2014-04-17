package org.adligo.tests4j.models.shared.coverage;

public class FileCoordinate {
	private int line;
	private int charCoord;
	
	public FileCoordinate(int l, int c) {
		line = l;
		charCoord = c;
	}
	
	public int getLine() {
		return line;
	}
	public int getChar() {
		return charCoord;
	}
}
