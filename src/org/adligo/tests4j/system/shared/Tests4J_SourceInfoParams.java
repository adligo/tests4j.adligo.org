package org.adligo.tests4j.system.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A reuseable implementation of I_Tests4J_SourceInfoParams
 * 
 * @author scott
 *
 */
public class Tests4J_SourceInfoParams implements I_Tests4J_SourceInfoParams {
	private List<String> packagesTested_;
	private List<String> nonTestablePackageNameParts_;
	private Set<String> nonTestableClassNames_;
	
	public Tests4J_SourceInfoParams() {
		/**
		 * the default instance
		 */
		packagesTested_ = Collections.emptyList();
		nonTestablePackageNameParts_ = Collections.singletonList("_tests");
		nonTestableClassNames_ = Collections.emptySet();
	}
	
	public Tests4J_SourceInfoParams(List<String> packagesTested, 
			List<String> nonTestablePackageNameParts, 
			Set<String> nonTestableClassNames) {
		
		if (packagesTested == null) {
			packagesTested_ = Collections.emptyList();
		} else {
			packagesTested_ = Collections.unmodifiableList(packagesTested);
		}
		
		if (nonTestablePackageNameParts == null) {
			nonTestablePackageNameParts_ = Collections.emptyList();
		} else {
			nonTestablePackageNameParts_ = Collections.unmodifiableList(nonTestablePackageNameParts);
		}
		if (nonTestableClassNames == null) {
			nonTestableClassNames_ = Collections.emptySet();
		} else {
			nonTestableClassNames_ = Collections.unmodifiableSet(nonTestableClassNames);
		}
	}

	@Override
	public List<String> getPackagesTested() {
		return packagesTested_;
	}

	@Override
	public boolean isTestable(String className) {
		if (nonTestableClassNames_.contains(className)) {
			return false;
		}
		for (String pkgPartName: nonTestablePackageNameParts_) {
			if (className.contains(pkgPartName)) {
				return false;
			}
		}
		return true;
	}
}
