package org.adligo.tests4j.models.shared.reference_groups.jse.v1_6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adligo.tests4j.models.shared.association.I_PackageConstantLookupModel;
import org.adligo.tests4j.shared.asserts.reference.NameOnlyReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupBaseDelegate;


/**
 * these are constants for the versions
 * of java, the plan is to support at least the 
 * previous 3 minor versions (today on 8/26/2014 that would be 1.8, 1.7, 1.6)
 * 
 * partially generated by org.adligo.tests4j_gen.console.JSEGroupGen
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_1_6_LangAnnot extends ReferenceGroupBaseDelegate implements I_JSE_1_6_LangAnnot, I_PackageConstantLookupModel {
	public static final String JAVA_LANG_ANNOTATION = "java.lang.annotation";
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final JSE_1_6_LangAnnot INSTANCE = new JSE_1_6_LangAnnot();
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
		toRet.put("java.lang.annotation.Annotation","ANNOTATION");
		toRet.put("java.lang.annotation.ElementType","ELEMENT_TYPE");
		toRet.put("java.lang.annotation.RetentionPolicy","RETENTION_POLICY");
		toRet.put("java.lang.annotation.AnnotationTypeMismatchException","ANNOTATION_TYPE_MISMATCH_EXCEPTION");
		toRet.put("java.lang.annotation.IncompleteAnnotationException","INCOMPLETE_ANNOTATION_EXCEPTION");
		toRet.put("java.lang.annotation.AnnotationFormatError","ANNOTATION_FORMAT_ERROR");
		toRet.put("java.lang.annotation.Documented","DOCUMENTED");
		toRet.put("java.lang.annotation.Inherited","INHERITED");
		toRet.put("java.lang.annotation.Native","NATIVE");
		toRet.put("java.lang.annotation.Repeatable","REPEATABLE");
		toRet.put("java.lang.annotation.Retention","RETENTION");
		toRet.put("java.lang.annotation.Target","TARGET");
		return Collections.unmodifiableMap(toRet);
	}
	private JSE_1_6_LangAnnot() {
		super.setDelegate(new NameOnlyReferenceGroup(CONSTANT_LOOKUP.keySet()));
	}
	@Override
	public String getPackageName() {
		return JAVA_LANG_ANNOTATION;
	}
	@Override
	public String getConstantName(String javaName) {
		return CONSTANT_LOOKUP.get(javaName);
	}
	@Override
	public Map<String, String> getModelMap() {
		return CONSTANT_LOOKUP;
	};
}
