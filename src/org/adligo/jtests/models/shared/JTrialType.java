package org.adligo.jtests.models.shared;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adligo.jtests.models.shared.common.TrialType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JTrialType {
	TrialType getType();
}