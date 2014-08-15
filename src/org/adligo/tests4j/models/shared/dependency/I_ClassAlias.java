package org.adligo.tests4j.models.shared.dependency;

import org.adligo.tests4j.models.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;


/**
 * A wrapper around a class
 * which implements Comparable and to from xml I_XML_Consumer, I_XML_Producer,
 * it would be nice if Class was comparable 
 * on the name.
 * 
 * Note the comparable implementation
 * should handle I_ClassAlias, String (class Name), Class (class Name)
 * and Object.getClass (className)
 * 
 * Also note this 
 *  the sub interface I_ClassAliasLocal will not
 * this is so that string only representations
 * of the class can be passed between jvms,
 * while Class instances can be passed around in the
 * jvm.
 * 
 * @author scott
 *
 */
public interface I_ClassAlias extends Comparable<Object>, I_XML_Consumer, I_XML_Producer {

	/**
	 * the name of the class this represents
	 * 
	 * @return
	 */
	public String getName();
}
