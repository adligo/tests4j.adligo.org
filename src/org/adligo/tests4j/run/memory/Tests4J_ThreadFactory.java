package org.adligo.tests4j.run.memory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * In order to correctly record code coverage the trial threads each have
 * their own numbered thread group and all child threads must 
 * use the numbered trial thread group.
 * Ie
 * thread tests4j-trial-1 with thread group tests4j-trial-1
 *         thread tests4j-test-1 with thread group tests4j-trial-1
 *         		thread custom-thread-1 with thread group tests4j-trial-1
 *                     
 * @author scott
 *
 */
public final class Tests4J_ThreadFactory implements ThreadFactory {
	public static final String THREAD_NAME = "tests4j-main";
	
	public static final String BASE_THREAD_NAME = "tests4j-";
	/**
	 * for the Tests4J_TrialsRunnables
	 */
	public static final String TRIAL_THREAD_NAME = BASE_THREAD_NAME + "trial";
	/**
	 * for the Tests4J_TestRunnables
	 */
	public static final String TEST_THREAD_NAME = BASE_THREAD_NAME + "test";
	/**
	 * for the Tests4J_SetupRunnables
	 */
	public static final String SETUP_THREAD_NAME = BASE_THREAD_NAME + "setup";
	
	/**
	 * for the Tests4J_RemoteRunnables
	 */
	public static final String REMOTE_THREAD_NAME = BASE_THREAD_NAME + "remote";
	
	/**
	 * for the Tests4J_ServerRunnables
	 */
	public static final String SERVER_THREAD_NAME = BASE_THREAD_NAME + "server";
	
	
	private List<Thread> threads = new CopyOnWriteArrayList<Thread>();
	private AtomicInteger id = new AtomicInteger();
	private String name;
	private I_Tests4J_Log reporter;
	private final ThreadGroup instanceCreationThreadGroup = Thread.currentThread().getThreadGroup();
	
	public Tests4J_ThreadFactory(String pName, I_Tests4J_Log pReporter) {
		reporter = pReporter;
		if (pName.contains(THREAD_NAME) || 
				pName.contains(TRIAL_THREAD_NAME) || 
				pName.contains(TEST_THREAD_NAME) ||
				pName.contains(SETUP_THREAD_NAME) ||
				pName.contains(REMOTE_THREAD_NAME) ||
				pName.contains(SERVER_THREAD_NAME)) {
			name = pName;
		} else {
			throw new IllegalArgumentException("Tests4J threads must start"
					+ "with one of the pre defined name prefixes in " + 
					Tests4J_ThreadFactory.class.getName());
		}
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
	private Thread createNewThread(Runnable r, int newId) {
		ThreadGroup parentThreadGroup = Thread.currentThread().getThreadGroup();
		String threadAndGroupName = name + "-" + newId;
		Thread t = null;
		if (name.indexOf(TRIAL_THREAD_NAME) != -1) {
			ThreadGroup group = new ThreadGroup(parentThreadGroup, threadAndGroupName);
			t = new Thread(group, r, threadAndGroupName);
		} else {
			t = new Thread(parentThreadGroup, r, threadAndGroupName);
		}
		if (reporter.isLogEnabled(Tests4J_ThreadFactory.class)) {
			StringBuilder sb = new StringBuilder();
			ThreadGroup childGroup = t.getThreadGroup();
			while (!instanceCreationThreadGroup.equals(childGroup)) {
				sb.append("~group=");
				sb.append(childGroup);
				childGroup = childGroup.getParent();
			}
			reporter.log("Tests4J_ThreadFactory creating new thread " + threadAndGroupName + sb.toString());
		}
		return t;
	}

	public List<Thread> getThreads() {
		return Collections.unmodifiableList(threads);
	}

}
