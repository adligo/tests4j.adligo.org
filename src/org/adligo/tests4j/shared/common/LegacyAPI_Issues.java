package org.adligo.tests4j.shared.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * this is a collection of exceptions
 * for tracking legacy issues when tests4j is running 
 * on a older jdk.
 * This should generally occur on the main thread
 * as most of these will be static.
 * 
 * @author scott
 *
 */
public class LegacyAPI_Issues {
	private volatile Map<JavaAPIVersion, List<Throwable>> issues = new HashMap<JavaAPIVersion,List<Throwable>>();
	
	public synchronized boolean hasIssues() {
		if (issues.size() == 0) {
			return true;
		}
		return false;
	}
	
	public synchronized void addIssues(JavaAPIVersion version, Throwable t ) {
		if (version.getBuild() != 0) {
			throw new IllegalArgumentException("Please use only specification versions.");
		}
		List<Throwable> thrown =  issues.get(version);
		if (thrown == null) {
			thrown = new ArrayList<Throwable>();
			issues.put(version, thrown);
		}
		thrown.add(t);
	}
	
	public synchronized void addIssues(LegacyAPI_Issues others) {
		//no need to optimize this, it probably only happens 2 or less times
		Set<JavaAPIVersion> versions = others.issues.keySet();
		for (JavaAPIVersion ver: versions) {
			List<Throwable> throwables =  others.issues.get(ver);
			if (throwables != null) {
				for (Throwable t: throwables) {
					addIssues(ver, t);
				}
			}
		}
	}
	public synchronized List<Throwable> getIssues(JavaAPIVersion v) {
		List<Throwable> toRet = new ArrayList<Throwable>();
		
		//no need to optimize this, it probably only happens 2 or less times
		Set<JavaAPIVersion> versions = issues.keySet();
		for (JavaAPIVersion ver: versions) {
			List<Throwable> throwables =  issues.get(ver);
			if (throwables != null) {
				toRet.addAll(throwables);
			}
		}
		
		return toRet;
	}
}
