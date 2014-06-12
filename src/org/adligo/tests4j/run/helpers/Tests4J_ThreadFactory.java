package org.adligo.tests4j.run.helpers;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public final class Tests4J_ThreadFactory implements ThreadFactory {
	public static final String BASE_THREAD_NAME = "tests4j-";
	/**
	 * for the runService which runs the TrialInstanceProcessor runnables
	 */
	public static final String TRIAL_THREAD_NAME = BASE_THREAD_NAME + "trial";
	/**
	 * for the testRunner which runs the TestRunable runnables
	 */
	public static final String TEST_THREAD_NAME = BASE_THREAD_NAME + "test";
	
	/**
	 * for the testRunner which runs the TestRunable runnables
	 */
	public static final String SERVER_THREAD_NAME = BASE_THREAD_NAME + "runner";
	
	
	private List<Thread> threads = new CopyOnWriteArrayList<Thread>();
	private AtomicInteger id = new AtomicInteger();
	private String threadGroupName = "";
	private ThreadGroup group;
	
	public Tests4J_ThreadFactory(String pThreadGroupName, ThreadGroup parent) {
		if (pThreadGroupName.contains(TRIAL_THREAD_NAME) || 
			pThreadGroupName.contains(TEST_THREAD_NAME) ||
			pThreadGroupName.contains(SERVER_THREAD_NAME)) {
			threadGroupName = pThreadGroupName;
		} else {
			throw new IllegalArgumentException("Tests4J threads must start"
					+ "with one of the pre defined name prefixes in " + 
					Tests4J_ThreadFactory.class.getName());
		}
		createNewThreadGroup(parent);
	}

	
	
	public Tests4J_ThreadFactory(String pName) {
		this(pName,Thread.currentThread().getThreadGroup());
	}
	
	@Override
	public Thread newThread(Runnable r) {
		int newId = id.addAndGet(1);
		Thread t = createNewThread(r, newId);
		threads.add(t);
		return t;
	}

	/**
	 * do not change this method name,
	 * it is checked for in the Tests4J_SecurityManager
	 * @param r
	 * @param newId
	 * @return
	 */
	private void createNewThreadGroup(ThreadGroup parent) {
		group = new ThreadGroup(parent, threadGroupName);
	}
	
	/**
	 * do not change this method name,
	 * it is checked for in the Tests4J_SecurityManager
	 * @param r
	 * @param newId
	 * @return
	 */
	private Thread createNewThread(Runnable r, int newId) {
		Thread t = new Thread(group, r, threadGroupName + "-" + newId);
		return t;
	}

	public List<Thread> getThreads() {
		return Collections.unmodifiableList(threads);
	}

	public ThreadGroup getGroup() {
		return group;
	}
}
