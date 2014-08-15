package org.adligo.tests4j.shared.report.summary;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DefaultReporterStates {
	public static Map<Class<?>, Boolean> getDefalutLogStates() {
		Map<Class<?>, Boolean> logStates = new HashMap<Class<?>, Boolean>();
		//set defaults;
		logStates.put(RemoteProgressDisplay.class, true);
		logStates.put(SetupProgressDisplay.class, true);
		logStates.put(SummaryReporter.class, true);
		
		logStates.put(TestFailedDisplay.class, true);
		logStates.put(TestsProgressDisplay.class, true);
		logStates.put(TestDisplay.class, true);
		logStates.put(ThreadDisplay.class, true);
		
		logStates.put(TrialFailedDisplay.class, true);
		logStates.put(TrialsProgressDisplay.class, true);
		logStates.put(TrialDisplay.class, true);
		logStates.put(TrialFailedDetailDisplay.class, true);
		return Collections.unmodifiableMap(logStates);
	}
}
