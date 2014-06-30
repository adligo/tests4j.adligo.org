package org.adligo.tests4j.models.shared.trials;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TrialType {
	TrialTypeEnum type();
}
