package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class Tests4J_DelegateProgressMonitor implements I_Tests4J_ProgressParams {
	private I_Tests4J_ProgressParams delegate;
	private I_Tests4J_Log log;
	private long cachedNotificationInterval_;
	
	public Tests4J_DelegateProgressMonitor(I_Tests4J_Log logIn, I_Tests4J_ProgressParams delegateIn) {
		log = logIn;
		try {
      cachedNotificationInterval_ = delegate.getNotificationInterval();
    } catch (Exception e) {
      cachedNotificationInterval_ = 250;
      log.onThrowable(e);
    }
	}
	
  @Override
  public long getNotificationInterval() {
    
    return cachedNotificationInterval_;
  }

}
