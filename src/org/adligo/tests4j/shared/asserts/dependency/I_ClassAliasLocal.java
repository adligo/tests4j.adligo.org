package org.adligo.tests4j.shared.asserts.dependency;

/**
 * This is a wrapper around a class, which isn't comparable.
 * This also allows me to pass around the Class instance
 * in classes which need to to and from xml,
 * to jvms where the class may not be available.
 * 
 * @author scott
 *
 */
public interface I_ClassAliasLocal extends I_ClassAlias {
	/**
	 * the class that this points to
	 * @return
	 */
	public Class<?> getTarget();
}
