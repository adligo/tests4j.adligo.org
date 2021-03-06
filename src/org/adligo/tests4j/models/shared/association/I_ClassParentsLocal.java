package org.adligo.tests4j.models.shared.association;

import java.util.List;

import org.adligo.tests4j.shared.asserts.reference.I_ClassAliasLocal;

/**
 * The local version of I_ClassParents,
 * which includes the actual class instances.
 * 
 * @author scott
 *
 */
public interface I_ClassParentsLocal extends I_ClassParents, I_ClassAliasLocal {
	
	public List<I_ClassParentsLocal> getParentsLocal();
}
