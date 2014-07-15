package org.adligo.tests4j.models.shared.asserts.line_text;

/**
 * a interface for passing data between class loaders
 * @author scott
 *
 */
public interface I_DiffIndexes {
	/**
	 * the start of where the lines were different
	 * @return
	 */
	public abstract int getStart();

	/**
	 * the end of where the lines were different
	 * @return
	 */
	public abstract int getEnd();

}