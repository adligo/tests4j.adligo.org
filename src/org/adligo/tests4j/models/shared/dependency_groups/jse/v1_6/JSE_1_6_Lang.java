package org.adligo.tests4j.models.shared.dependency_groups.jse.v1_6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.adligo.tests4j.models.shared.dependency.I_PackageConstantLookupModel;
import org.adligo.tests4j.shared.asserts.dependency.DependencyGroupBaseDelegate;
import org.adligo.tests4j.shared.asserts.dependency.NameOnlyDependencyGroup;


/**
 * these are constants for the versions
 * of java, the plan is to support at least the 
 * previous 3 minor versions (today on 8/26/2014 that would be 1.8, 1.7, 1.6)
 * 
 * use, use the constant imports.
 * Classes included but removed before the 
 * first version should be removed ASAP, if that ever happens.
 * 
 * Note this doesn't implements JSE_1_7_Lang or JSE_1_8_Lang
 * since I am trying to support all the way back to 1_6
 * (although running 1.8 now).
 * 
 * partially generated by org.adligo.tests4j_gen.PackageClassNameWriter
 * copy/pasting...
 * Also this class should eventually include the entire api 
 * (public methods and fields), for assertion dependency.
 * 
 * @author scott
 *
 */
public class JSE_1_6_Lang extends DependencyGroupBaseDelegate implements I_JSE_1_6_Lang, I_PackageConstantLookupModel {
	private static final Map<String,String> CONSTANT_LOOKUP = getConstantLookup();
	public static final JSE_1_6_Lang INSTANCE = new JSE_1_6_Lang();
	
	private static Map<String,String> getConstantLookup() {
		Map<String,String> toRet = new HashMap<>();
		//autogenerated by org.adligo.tests4j.gen.dependency_groups.PackageClassNameWriter
		toRet.put("java.lang.Appendable","APPENDABLE");
		toRet.put("java.lang.AutoCloseable","AUTO_CLOSEABLE");
		toRet.put("java.lang.CharSequence","CHAR_SEQUENCE");
		toRet.put("java.lang.Cloneable","CLONEABLE");
		toRet.put("java.lang.Comparable","COMPARABLE");
		toRet.put("java.lang.Iterable","ITERABLE");
		toRet.put("java.lang.Readable","READABLE");
		toRet.put("java.lang.Runnable","RUNNABLE");
		toRet.put("java.lang.Thread$UncaughtExceptionHandler","THREAD_UNCAUGHT_EXCEPTION_HANDLER");
		toRet.put("java.lang.Boolean","BOOLEAN");
		toRet.put("java.lang.Byte","BYTE");
		toRet.put("java.lang.Character","CHARACTER");
		toRet.put("java.lang.Character$Subset","CHARACTER_SUBSET");
		toRet.put("java.lang.Character$UnicodeBlock","CHARACTER_UNICODE_BLOCK");
		toRet.put("java.lang.Class","CLASS");
		toRet.put("java.lang.ClassLoader","CLASS_LOADER");
		toRet.put("java.lang.ClassValue","CLASS_VALUE");
		toRet.put("java.lang.Compiler","COMPILER");
		toRet.put("java.lang.Double","DOUBLE");
		toRet.put("java.lang.Enum","ENUM");
		toRet.put("java.lang.Float","FLOAT");
		toRet.put("java.lang.InheritableThreadLocal","INHERITABLE_THREAD_LOCAL");
		toRet.put("java.lang.Integer","INTEGER");
		toRet.put("java.lang.Long","LONG");
		toRet.put("java.lang.Math","MATH");
		toRet.put("java.lang.Number","NUMBER");
		toRet.put("java.lang.Object","OBJECT");
		toRet.put("java.lang.Objects","OBJECTS");
		toRet.put("java.lang.Package","PACKAGE");
		toRet.put("java.lang.Process","PROCESS");
		toRet.put("java.lang.ProcessBuilder","PROCESS_BUILDER");
		toRet.put("java.lang.ProcessBuilder$Redirect","PROCESS_BUILDER_REDIRECT");
		toRet.put("java.lang.Runtime","RUNTIME");
		toRet.put("java.lang.RuntimePermission","RUNTIME_PERMISSION");
		toRet.put("java.lang.SecurityManager","SECURITY_MANAGER");
		toRet.put("java.lang.Short","SHORT");
		toRet.put("java.lang.StackTraceElement","STACK_TRACE_ELEMENT");
		toRet.put("java.lang.StrictMath","STRICT_MATH");
		toRet.put("java.lang.String","STRING");
		toRet.put("java.lang.StringBuffer","STRING_BUFFER");
		toRet.put("java.lang.StringBuilder","STRING_BUILDER");
		toRet.put("java.lang.System","SYSTEM");
		toRet.put("java.lang.Thread","THREAD");
		toRet.put("java.lang.ThreadGroup","THREAD_GROUP");
		toRet.put("java.lang.ThreadLocal","THREAD_LOCAL");
		toRet.put("java.lang.Throwable","THROWABLE");
		toRet.put("java.lang.Void","VOID");
		toRet.put("java.lang.Character$UnicodeScript","CHARACTER_UNICODE_SCRIPT");
		toRet.put("java.lang.ProcessBuilder$Redirect$Type","PROCESS_BUILDER_REDIRECT_TYPE");
		toRet.put("java.lang.Thread$State","THREAD_STATE");
		toRet.put("java.lang.ArithmeticException","ARITHMETIC_EXCEPTION");
		toRet.put("java.lang.ArrayIndexOutOfBoundsException","ARRAY_INDEX_OUT_OF_BOUNDS_EXCEPTION");
		toRet.put("java.lang.ArrayStoreException","ARRAY_STORE_EXCEPTION");
		toRet.put("java.lang.ClassCastException","CLASS_CAST_EXCEPTION");
		toRet.put("java.lang.ClassNotFoundException","CLASS_NOT_FOUND_EXCEPTION");
		toRet.put("java.lang.CloneNotSupportedException","CLONE_NOT_SUPPORTED_EXCEPTION");
		toRet.put("java.lang.EnumConstantNotPresentException","ENUM_CONSTANT_NOT_PRESENT_EXCEPTION");
		toRet.put("java.lang.Exception","EXCEPTION");
		toRet.put("java.lang.IllegalAccessException","ILLEGAL_ACCESS_EXCEPTION");
		toRet.put("java.lang.IllegalArgumentException","ILLEGAL_ARGUMENT_EXCEPTION");
		toRet.put("java.lang.IllegalMonitorStateException","ILLEGAL_MONITOR_STATE_EXCEPTION");
		toRet.put("java.lang.IllegalStateException","ILLEGAL_STATE_EXCEPTION");
		toRet.put("java.lang.IllegalThreadStateException","ILLEGAL_THREAD_STATE_EXCEPTION");
		toRet.put("java.lang.IndexOutOfBoundsException","INDEX_OUT_OF_BOUNDS_EXCEPTION");
		toRet.put("java.lang.InstantiationException","INSTANTIATION_EXCEPTION");
		toRet.put("java.lang.InterruptedException","INTERRUPTED_EXCEPTION");
		toRet.put("java.lang.NegativeArraySizeException","NEGATIVE_ARRAY_SIZE_EXCEPTION");
		toRet.put("java.lang.NoSuchFieldException","NO_SUCH_FIELD_EXCEPTION");
		toRet.put("java.lang.NoSuchMethodException","NO_SUCH_METHOD_EXCEPTION");
		toRet.put("java.lang.NullPointerException","NULL_POINTER_EXCEPTION");
		toRet.put("java.lang.NumberFormatException","NUMBER_FORMAT_EXCEPTION");
		toRet.put("java.lang.ReflectiveOperationException","REFLECTIVE_OPERATION_EXCEPTION");
		toRet.put("java.lang.RuntimeException","RUNTIME_EXCEPTION");
		toRet.put("java.lang.SecurityException","SECURITY_EXCEPTION");
		toRet.put("java.lang.StringIndexOutOfBoundsException","STRING_INDEX_OUT_OF_BOUNDS_EXCEPTION");
		toRet.put("java.lang.TypeNotPresentException","TYPE_NOT_PRESENT_EXCEPTION");
		toRet.put("java.lang.UnsupportedOperationException","UNSUPPORTED_OPERATION_EXCEPTION");
		toRet.put("java.lang.AbstractMethodError","ABSTRACT_METHOD_ERROR");
		toRet.put("java.lang.AssertionError","ASSERTION_ERROR");
		toRet.put("java.lang.BootstrapMethodError","BOOTSTRAP_METHOD_ERROR");
		toRet.put("java.lang.ClassCircularityError","CLASS_CIRCULARITY_ERROR");
		toRet.put("java.lang.ClassFormatError","CLASS_FORMAT_ERROR");
		toRet.put("java.lang.Error","ERROR");
		toRet.put("java.lang.ExceptionInInitializerError","EXCEPTION_IN_INITIALIZER_ERROR");
		toRet.put("java.lang.IllegalAccessError","ILLEGAL_ACCESS_ERROR");
		toRet.put("java.lang.IncompatibleClassChangeError","INCOMPATIBLE_CLASS_CHANGE_ERROR");
		toRet.put("java.lang.InstantiationError","INSTANTIATION_ERROR");
		toRet.put("java.lang.InternalError","INTERNAL_ERROR");
		toRet.put("java.lang.LinkageError","LINKAGE_ERROR");
		toRet.put("java.lang.NoClassDefFoundError","NO_CLASS_DEF_FOUND_ERROR");
		toRet.put("java.lang.NoSuchFieldError","NO_SUCH_FIELD_ERROR");
		toRet.put("java.lang.NoSuchMethodError","NO_SUCH_METHOD_ERROR");
		toRet.put("java.lang.OutOfMemoryError","OUT_OF_MEMORY_ERROR");
		toRet.put("java.lang.StackOverflowError","STACK_OVERFLOW_ERROR");
		toRet.put("java.lang.ThreadDeath","THREAD_DEATH");
		toRet.put("java.lang.UnknownError","UNKNOWN_ERROR");
		toRet.put("java.lang.UnsatisfiedLinkError","UNSATISFIED_LINK_ERROR");
		toRet.put("java.lang.UnsupportedClassVersionError","UNSUPPORTED_CLASS_VERSION_ERROR");
		toRet.put("java.lang.VerifyError","VERIFY_ERROR");
		toRet.put("java.lang.VirtualMachineError","VIRTUAL_MACHINE_ERROR");
		
		toRet.put("java.lang.Deprecated","DEPRECATED");
		toRet.put("java.lang.FunctionalInterface","FUNCTIONAL_INTERFACE");
		toRet.put("java.lang.Override","OVERRIDE");
		toRet.put("java.lang.SafeVarargs","SAFE_VARARGS");
		toRet.put("java.lang.SuppressWarnings","SUPPRESS_WARNINGS");
		return Collections.unmodifiableMap(toRet);
	}
	private JSE_1_6_Lang() {
		super.setDelegate(new NameOnlyDependencyGroup(CONSTANT_LOOKUP.keySet()));
	}
	@Override
	public String getPackageName() {
		return "java.lang";
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
