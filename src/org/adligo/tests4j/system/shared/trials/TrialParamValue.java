package org.adligo.tests4j.system.shared.trials;

import org.adligo.tests4j.shared.common.ClassMethods;
import org.adligo.tests4j.shared.xml.I_XML_Builder;

public class TrialParamValue implements I_TrialParamValue {
	public static final String TAG_NAME = "value";
	public static final String CLASS_NAME = "class";
	
	public static final String PARAMETER_VALUE_MUST_BE_A_NON_VOID_PRIMITIVE_OR_STRING = 
			"Parameter value must be a non Void primitive or String.";
	
	private Object value_;
	
	public TrialParamValue(Object value) {
		if (value == null) {
			throw new NullPointerException();
		}
		Class<?> c = value.getClass();
		if ( (ClassMethods.isPrimitiveClass(c) && !ClassMethods.isClass(Void.class, c))
				 || ClassMethods.isClass(String.class, c)) {
			value_ = value;
		} else {
			throw new IllegalArgumentException(
					PARAMETER_VALUE_MUST_BE_A_NON_VOID_PRIMITIVE_OR_STRING);
		}
		
	}
	
	@Override
	public String getClassName() {
		return value_.getClass().getName();
	}

	@Override
	public Object getValue() {
		return value_;
	}
	@Override
	public void toXml(I_XML_Builder builder) {
		builder.addIndent();
		builder.addStartTag(TAG_NAME);
		String name = ClassMethods.getSimpleName(value_.getClass());
		builder.addAttribute(CLASS_NAME, name);
		builder.endHeader();
		builder.addText(value_.toString());
		builder.addEndTag(TAG_NAME);
		builder.endLine();
	}
	
}
