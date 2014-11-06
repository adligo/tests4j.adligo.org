package org.adligo.tests4j.run.common;

public class Holder<T> {
	private T held;
	
	public synchronized T getHeld() {
		return held;
	}

	public synchronized void setHeld(T held) {
		this.held = held;
	}
}
