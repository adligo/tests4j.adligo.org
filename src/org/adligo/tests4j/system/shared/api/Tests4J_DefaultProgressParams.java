package org.adligo.tests4j.system.shared.api;


public class Tests4J_DefaultProgressParams implements I_Tests4J_ProgressParams {
	private long notificationInterval_ = 250;
	
  @Override
  public long getNotificationInterval() {
    return notificationInterval_;
  }

  public void setNotificationInterval(long notificationInterval) {
    this.notificationInterval_ = notificationInterval;
  }

}
