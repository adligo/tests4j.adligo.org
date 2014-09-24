package org.adligo.tests4j.models.shared.system;

import java.util.Collections;
import java.util.List;

import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class Tests4J_SourceInfoParamsDelegate implements I_Tests4J_SourceInfoParams {
	public static final String TESTS4J_SOURCE_INFO_PARAMS_DELEGATE_REQUIRES_A_I_TESTS4J_LOG = 
			"Tests4J_SourceInfoParamsDelegate requires a I_Tests4J_Log";
	public static final String TESTS4J_SOURCE_INFO_PARAMS_DELEGATE_REQUIRES_A_I_TESTS4J_SOURCE_INFO_PARAMS = 
			"Tests4J_SourceInfoParamsDelegate requires a I_Tests4J_SourceInfoParams";
	private I_Tests4J_SourceInfoParams delegate_;
	private I_Tests4J_Log log_;
	
	public Tests4J_SourceInfoParamsDelegate(I_Tests4J_SourceInfoParams p,
			I_Tests4J_Log log) {
		if (p == null) {
			throw new NullPointerException(
					TESTS4J_SOURCE_INFO_PARAMS_DELEGATE_REQUIRES_A_I_TESTS4J_SOURCE_INFO_PARAMS);
		}
		delegate_ = p;
		if (log == null) {
			throw new NullPointerException(TESTS4J_SOURCE_INFO_PARAMS_DELEGATE_REQUIRES_A_I_TESTS4J_LOG);
		}
		log_ = log;
	}
	
	@Override
	public List<String> getPackagesTested() {
		try {
			return delegate_.getPackagesTested();
		} catch (Exception x) {
			log_.onThrowable(x);
			return Collections.emptyList();
		}
	}

	@Override
	public boolean isTestable(String className) {
		try {
			return delegate_.isTestable(className);
		} catch (Exception x) {
			log_.onThrowable(x);
			return false;
		}
	}

}
