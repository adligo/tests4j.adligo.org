package org.adligo.tests4j.models.shared.common;

import java.util.concurrent.CopyOnWriteArrayList;

public class CacheControl implements I_CacheControl {
	private CopyOnWriteArrayList<Runnable> runnables = new CopyOnWriteArrayList<Runnable>();
	
	@Override
	public void addClearRunnable(Runnable r) {
		runnables.add(r);
	}

	@Override
	public void clear() {
		for (Runnable r: runnables) {
			r.run();
		}
	}

}
