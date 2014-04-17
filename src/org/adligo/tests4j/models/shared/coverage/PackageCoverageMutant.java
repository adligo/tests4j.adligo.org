package org.adligo.tests4j.models.shared.coverage;

import java.util.HashMap;
import java.util.Map;

public class PackageCoverageMutant implements I_PackageCoverage {
	private String packageName;
	private Map<String, I_ClassCoverage> coverage = new HashMap<String, I_ClassCoverage>();
	public String getPackageName() {
		return packageName;
	}
	public Map<String, I_ClassCoverage> getCoverage() {
		return coverage;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public void setCoverage(Map<String, I_ClassCoverage> coverage) {
		this.coverage = coverage;
	}
}
class Foo {

	public Foo() {
		new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
			}
		};
	}
}
