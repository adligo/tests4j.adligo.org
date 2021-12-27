package org.adligo.tests4j.models.shared.reference_groups.jse.v1_6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adligo.tests4j.models.shared.association.I_PackageConstantLookupModel;
import org.adligo.tests4j.shared.asserts.reference.NameOnlyReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupBaseDelegate;

/**
 * This class represents the classes in java.io
 * for the latest version JSE version 1_8.
 * 
 * partially generated by org.adligo.tests4j_gen.console.JSEGroupGen
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_1_6_NIO extends ReferenceGroupBaseDelegate implements I_JSE_1_6_IO, I_PackageConstantLookupModel {
	public static final String JAVA_IO = "java.io";
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final JSE_1_6_NIO INSTANCE = new JSE_1_6_NIO();
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
    toRet.put("java.nio.Buffer","BUFFER");
    toRet.put("java.nio.ByteBuffer","BYTE_BUFFER");
    toRet.put("java.nio.ByteOrder","BYTE_ORDER");
    toRet.put("java.nio.CharBuffer","CHAR_BUFFER");
    toRet.put("java.nio.DoubleBuffer","DOUBLE_BUFFER");
    toRet.put("java.nio.FloatBuffer","FLOAT_BUFFER");
    toRet.put("java.nio.IntBuffer","INT_BUFFER");
    toRet.put("java.nio.LongBuffer","LONG_BUFFER");
    toRet.put("java.nio.MappedByteBuffer","MAPPED_BYTE_BUFFER");
    toRet.put("java.nio.ShortBuffer","SHORT_BUFFER");
    toRet.put("java.nio.BufferOverflowException","BUFFER_OVERFLOW_EXCEPTION");
    toRet.put("java.nio.BufferUnderflowException","BUFFER_UNDERFLOW_EXCEPTION");
    toRet.put("java.nio.InvalidMarkException","INVALID_MARK_EXCEPTION");
    toRet.put("java.nio.ReadOnlyBufferException","READ_ONLY_BUFFER_EXCEPTION");
		return Collections.unmodifiableMap(toRet);
	}
	
	private JSE_1_6_NIO() {
		super.setDelegate(new NameOnlyReferenceGroup(CONSTANT_LOOKUP.keySet()));
	}

	@Override
	public String getPackageName() {
		return JAVA_IO;
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