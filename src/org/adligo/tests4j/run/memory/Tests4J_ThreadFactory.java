package org.adligo.tests4j.run.memory;

import org.adligo.tests4j.run.common.I_Threads;
import org.adligo.tests4j.run.common.ThreadsDelegate;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * In order to correctly record code coverage the trial threads each have
 * their own numbered thread group and all child threads must 
 * use the numbered trial thread group.
 * Ie
 * thread tests4j-trial-1 with thread group tests4j-trial-1
 *         thread tests4j-trial-1-test-1 with thread group        
 *              tests4j-trial-1-group
 *         		thread tests4j-trial-1-custom-1 with thread group 
 *              tests4j-trial-1-group
 *                     
 * @author scott
 *
 */
public final class Tests4J_ThreadFactory implements ThreadFactory {
	public static final String NAME_NOT_ALLOWED = "Name not allowed!";
  public static final String MAIN_THREAD_NAME = "tests4j-main";
	public static final String MAIN_THREAD_GROUP = "tests4j-main-group";
	
	public static final String BASE_THREAD_NAME = "tests4j-";
	/**
	 * for the Tests4J_TrialsRunnables
	 */
	public static final String TRIAL_THREAD_NAME = "trial";
	public static final String TRIAL_THREAD_GROUP = BASE_THREAD_NAME + "trial-group";
	/**
	 * for the Tests4J_TestRunnables
	 */
	public static final String TEST_THREAD_NAME = "test";
	/**
	 * for the Tests4J_SetupRunnables
	 */
	public static final String SETUP_THREAD_NAME = "setup";
	
	/**
	 * for the Tests4J_RemoteRunnables
	 */
	public static final String REMOTE_THREAD_NAME = "remote";
	
	/**
	 * for the Tests4J_ServerRunnables
	 */
	public static final String SERVER_THREAD_NAME = "server";
	
	/**
   * for the Tests4J_ServerRunnables
   */
  public static final String CUSTOM_THREAD_NAME = "custom";
  
  private static final Set<String> ALLOWED_NAMES = getAllowedNames();
  private static final Set<String> getAllowedNames() {
    Set<String> toRet = new HashSet<String>();
    toRet.add(MAIN_THREAD_NAME);
    toRet.add(TRIAL_THREAD_NAME);
    toRet.add(TEST_THREAD_NAME);
    toRet.add(SETUP_THREAD_NAME);
    toRet.add(REMOTE_THREAD_NAME);
    toRet.add(SERVER_THREAD_NAME);
    toRet.add(CUSTOM_THREAD_NAME);
    return Collections.unmodifiableSet(toRet);
  }
  
  private I_Threads threads_;
  private ThreadGroup instanceCreationThreadGroup_;
  private ThreadGroup group_;
  private Tests4J_ThreadFactory parent_;
	
  private List<Thread> threads = new CopyOnWriteArrayList<Thread>();
	private AtomicInteger id_ = new AtomicInteger();
	private String name_;
	private I_Tests4J_Log log_;
	
	public Tests4J_ThreadFactory(I_Tests4J_Log log) {
    this(MAIN_THREAD_NAME, log);
  }
	public Tests4J_ThreadFactory(I_Tests4J_Log log, I_Threads threads) {
    this(MAIN_THREAD_NAME, log, threads);
  }
	public Tests4J_ThreadFactory(Tests4J_ThreadFactory parent, String name) {
	  setParent(parent);
	  setName(name);
  }

	public Tests4J_ThreadFactory(String name, I_Tests4J_Log log, I_Threads threads) {
    setLog(log);
    setThreads(threads);
    instanceCreationThreadGroup_ = threads_.currentThread().getThreadGroup();
    setName(name); 
  }
	
  private void setParent(Tests4J_ThreadFactory parent) {
    if (parent == null) {
      throw new NullPointerException();
    } else {
      parent_ = parent;
    }
    log_ = parent.log_;
  }
	public Tests4J_ThreadFactory(String name, I_Tests4J_Log reporter) {
	  this(name, reporter, null);
  }
	
	
  private void setLog(I_Tests4J_Log log) {
    if (log == null) {
	    throw new NullPointerException();
	  } else {
	    log_ = log;
	  }
  }

  private void setThreads(I_Threads threads) {
    if (threads == null) {
		  threads_ = new ThreadsDelegate();
		} else {
		  threads_ = threads;
		}
  }

  private void setName(String name) {
    if (name == null) {
		  throw new NullPointerException();
		} else {
		  if (!ALLOWED_NAMES.contains(name)) {
		    throw new IllegalArgumentException(NAME_NOT_ALLOWED);
		  }
		  if (MAIN_THREAD_NAME.equals(name)) {
		    group_ = new ThreadGroup(instanceCreationThreadGroup_, MAIN_THREAD_GROUP);
        name_ = name;
        return;
      } else if (parent_ == null) {
        throw new IllegalArgumentException(NAME_NOT_ALLOWED);
      }
		  
		  String parentName = parent_.name_;
		  if (parentName.indexOf(BASE_THREAD_NAME) != 0) {
        throw new IllegalArgumentException(NAME_NOT_ALLOWED);
      }
		  
		  if (TEST_THREAD_NAME.equals(name) || CUSTOM_THREAD_NAME.equals(name)) {
		     
	      if (parentName.indexOf(TRIAL_THREAD_NAME) != BASE_THREAD_NAME.length()) {
          throw new IllegalArgumentException(NAME_NOT_ALLOWED);
        }
	      group_ = parent_.group_;
	      name_ = parentName + "-" + name;
		  } else {
		    group_ = new ThreadGroup(parent_.group_, BASE_THREAD_NAME + name + "-group");
		    name_ = BASE_THREAD_NAME + name;
		  }
		  
		}
  }
	
	@Override
	public Thread newThread(Runnable r) {
	  Thread t = null;
	  if (name_.equals(MAIN_THREAD_NAME)) {
	    t =  new Thread(group_, r, MAIN_THREAD_NAME);
	  } else  {
	    t = new Thread(group_, r, name_ + "-" + id_.incrementAndGet());
	  }
	  threads.add(t);
	  return t;
	}

	public List<Thread> getThreads() {
		return Collections.unmodifiableList(threads);
	}
  public ThreadGroup getInstanceCreationThreadGroup() {
    return instanceCreationThreadGroup_;
  }

}
