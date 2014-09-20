package org.adligo.tests4j.models.shared.asserts.common;

/**
 * for test failures from souce file trials,
 * provide access to the sourceClass from the annotation.
 * @author scott
 *
 */
public interface I_SourceTestFailure  extends I_TestFailure {

	/**
	 * the sourceClass from the SourceFileScope annotation.
	 * @return
	 */
	public abstract Class<?> getSourceClass();
}
