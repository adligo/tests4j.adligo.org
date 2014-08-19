package org.adligo.tests4j.models.shared.dependency;

import org.adligo.tests4j.models.shared.xml.I_XML_Consumer;
import org.adligo.tests4j.models.shared.xml.I_XML_Producer;

/**
 * a method signature that can get passed between jvms,
 * and passed between classloaders in the same jvm.
 * @author scott
 *
 */
public interface I_MethodSignature extends I_XML_Consumer, I_XML_Producer {
	public String getClassName();
	public String getMethodName();
	public String[] getParameterClassNames();
}
