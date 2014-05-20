package org.adligo.tests4j.run;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadShutdownExample {
	public static ThreadFactory FACTORY = new ThreadFactory() {
		private AtomicInteger id = new AtomicInteger();
		
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "example-" + id.addAndGet(1));
			return t;
		}
	};
	public static void main(String[] args) {
		
		ExecutorService rs = Executors.newSingleThreadExecutor();
		
		ExecutorService runService = Executors.newFixedThreadPool(32, FACTORY);
		if (runService instanceof ThreadPoolExecutor) {
			ThreadPoolExecutor tpe = (ThreadPoolExecutor) runService;
			tpe.setKeepAliveTime(5, TimeUnit.MILLISECONDS);
			tpe.allowCoreThreadTimeOut(true);
		}
		for (int i = 0; i < 32; i++) {
			if (Math.IEEEremainder(i, 2) == 0) {
				runService.submit(new Runner());
			} else {
				runService.submit(new Runner2());
			}
		}
		runService.shutdownNow();
		Map<Thread,StackTraceElement[]> threadStacks = Thread.getAllStackTraces();
		Set<Entry<Thread, StackTraceElement[]>> entries = threadStacks.entrySet();
		for (Entry<Thread, StackTraceElement[]> e: entries) {
			Thread t = e.getKey();
			if (t.getName().indexOf("ext-example-") != -1) {
				t.interrupt();
			}
			if (t.getName().indexOf("ext2-example-") != -1) {
				t.interrupt();
			}
			if (t.getName().indexOf("example-") != -1) {
				t.interrupt();
			}
		}
		
	}
}

class Runner implements Runnable {
	public static ThreadFactory FACTORY = new ThreadFactory() {
		private AtomicInteger id = new AtomicInteger();
		
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r, "ext-example-" + id.addAndGet(1));
			return t;
		}
	};
	
	public void run() {
		if (Thread.currentThread().getName().indexOf("ext-") == -1) {
			//Thread t = new Thread(this, Thread.currentThread().getName() + "-ext");
			//t.start();
			ExecutorService es = Executors.newFixedThreadPool(1, FACTORY);
			es.submit(this);
		}
		while (true) {
			System.out.println("hey " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("interrupted " + Thread.currentThread().getName());
				return;
			}
		}
	}
}


class Runner2 implements Runnable {
	private static final AtomicInteger id = new AtomicInteger();
	
	public void run() {
		if (Thread.currentThread().getName().indexOf("ext2-") == -1) {
			Thread t = new Thread(this, "ext2-example-" + id.getAndAdd(1));
			t.start();
		}
		while (true) {
			System.out.println("hey " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("interrupted " + Thread.currentThread().getName());
				return;
			}
		}
	}
}
