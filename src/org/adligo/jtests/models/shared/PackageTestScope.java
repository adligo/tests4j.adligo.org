package org.adligo.jtests.models.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface PackageTestScope {
	String testedPackageName();
}
