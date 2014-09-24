package org.adligo.tests4j.shared.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum TrialType implements I_TrialType {
	
	
	UnknownTrialType(0), SourceFileTrial(1), ApiTrial(2), UseCaseTrial(3),
	MetaTrial(4);
	public static final int UNKNOWN_TRIAL_TYPE = 0;
	public static final int SOURCE_FILE_TRIAL_TYPE = 1;
	public static final int API_TRIAL_TYPE = 2;
	public static final int USE_CASE_TRIAL_TYPE = 3;
	public static final int META_TRIAL_TYPE = 4;
	
	private static final Map<Integer, TrialType> typeMap = getTypeMap();
	
	private static Map<Integer, TrialType> getTypeMap() {
		Map<Integer, TrialType> toRet = new HashMap<Integer, TrialType>();
		toRet.put(0, UnknownTrialType);
		toRet.put(1, SourceFileTrial);
		toRet.put(2, ApiTrial);
		toRet.put(3, UseCaseTrial);
		toRet.put(4, MetaTrial);
		return Collections.unmodifiableMap(toRet);
	}
	
	private int id;
	
	TrialType(int p) {
		id = p;
	}

	public int getId() {
		return id;
	}
	
	public static TrialType get(I_TrialType p) {
		return get(p.getId());
	}
	
	public static TrialType get(int p) {
		return typeMap.get(p);
	}
}
