package org.adligo.tests4j.models.shared.dependency_groups.jse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adligo.tests4j.models.shared.dependency.I_PackageConstantLookup;
import org.adligo.tests4j.models.shared.dependency_groups.jse.v1_6.I_JSE_1_6_Math;
import org.adligo.tests4j.shared.asserts.dependency.DependencyGroupBaseDelegate;
import org.adligo.tests4j.shared.asserts.dependency.NameOnlyDependencyGroup;


/**
 * these are constants for the versions
 * of java, the plan is to support at least the 
 * previous 3 minor versions (today on 8/26/2014 that would be 1.8, 1.7, 1.6)
 * 
 * partially generated by org.adligo.tests4j_gen.PackageClassNameWriter
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_Math extends DependencyGroupBaseDelegate  implements I_JSE_1_6_Math, I_PackageConstantLookup {
	public static final String JAVA_MATH = "java.math";
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final JSE_Math INSTANCE = new JSE_Math();
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
		toRet.put("java.math.BigDecimal","BIG_DECIMAL");
		toRet.put("java.math.BigInteger","BIG_INTEGER");
		toRet.put("java.math.RoundingMode","ROUNDING_MODE");
		toRet.put("java.math.MathContext","MATH_CONTEXT");
		return Collections.unmodifiableMap(toRet);
	}
	
	
	private JSE_Math() {
		super.setDelegate(new NameOnlyDependencyGroup(CONSTANT_LOOKUP.keySet()));
	}


	@Override
	public String getPackageName() {
		return JAVA_MATH;
	}


	@Override
	public String getConstantName(String javaName) {
		return CONSTANT_LOOKUP.get(javaName);
	};
}
