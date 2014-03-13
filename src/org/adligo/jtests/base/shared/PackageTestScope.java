package org.adligo.jtests.base.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface PackageTestScope {
	String testedPackageName();
}
