package org.adligo.tests4j.run.common;



/**
 * This class allows filtering of thread groups
 * based on a filter which is part of a thread group name.
 * @author scott
 *
 */
public class ThreadGroupFilter implements I_ThreadGroupFilter {
	private final String filter_;
	private final I_Threads threads_;
	
	public ThreadGroupFilter(String filter) {
		this(filter, null);
	}
	
	/**
	 * @param filter the first part of the thread group name
	 *   which must match.
	 *   
	 * @param threads
	 */
	public ThreadGroupFilter(String filter, I_Threads threads) {
	  if (filter == null) {
	    throw new NullPointerException();
	  } 
    filter_ = filter;
    if (threads == null) {
      threads_ = new ThreadsDelegate();
    } else {
      threads_ = threads;
    }
  }
	
	/* (non-Javadoc)
   * @see org.adligo.tests4j.run.common.I_ThreadGroupFilter#getThreadGroupNameMatchingFilter()
   */
	@Override
  public String getThreadGroupNameMatchingFilter() {
		Thread currentThread = threads_.currentThread();
		ThreadGroup group = currentThread.getThreadGroup();
		String groupName = group.getName();
		if (groupName.indexOf(filter_) != 0) {
			while (group != null) {
				group = group.getParent();
				if (group != null) {
					groupName = group.getName();
					if (groupName.indexOf(filter_) == 0) {
						break;
					}
				}
			}
		}
		if (groupName.indexOf(filter_) == 0) {
		  return groupName;
    } else {
      return null;
    }
		
	}

  /* (non-Javadoc)
   * @see org.adligo.tests4j.run.common.I_ThreadGroupFilter#getFilter()
   */
  @Override
  public String getFilter() {
    return filter_;
  }

  /* (non-Javadoc)
   * @see org.adligo.tests4j.run.common.I_ThreadGroupFilter#getThreads()
   */
  @Override
  public I_Threads getThreads() {
    return threads_;
  }
	
	
}
