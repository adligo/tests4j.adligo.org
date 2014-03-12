package org.adligo.jtests.base.shared;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum AssertCommandType {
	AssertTrue, AssertFalse, AssertNull, AssertNotNull,
	AssertEquals, AssertNotEquals, AssertSame, AssertNotSame,
	AssertThrown, AssertNotThrown;
	
	public static Set<AssertCommandType> BOOLEAN_TYPES = getBooleanTypes();
	
	private static Set<AssertCommandType> getBooleanTypes() {
		Set<AssertCommandType> toRet = new HashSet<AssertCommandType>();
		toRet.add(AssertTrue);
		toRet.add(AssertFalse);
		toRet.add(AssertNull);
		toRet.add(AssertNotNull);
		return Collections.unmodifiableSet(toRet);
	}
}
