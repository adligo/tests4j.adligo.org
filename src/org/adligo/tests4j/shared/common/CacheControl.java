package org.adligo.tests4j.shared.common;

import java.util.ArrayList;
import java.util.List;

public class CacheControl implements I_CacheControl {
	private List<Runnable> runnables = new ArrayList<Runnable>();
	
	@Override
	public synchronized void addClearRunnable(Runnable r) {
		runnables.add(r);
	}

	@Override
	public void clear() {
		for (Runnable r: runnables) {
			r.run();
		}
	}

}
