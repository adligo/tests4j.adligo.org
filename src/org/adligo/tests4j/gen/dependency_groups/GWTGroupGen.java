package org.adligo.tests4j.gen.dependency_groups;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.dependency_groups.gwt.GWT_2_6_Log;
import org.adligo.tests4j.models.dependency_groups.jse.JSE_Lang;
import org.adligo.tests4j.models.dependency_groups.jse.JSE_Util;
import org.adligo.tests4j.models.shared.common.Tests4J_System;


public class GWTGroupGen {

	public static void main(String[] args) {
		GroupGen gg = new GroupGen();
		GenDepGroupContext ctx = new GenDepGroupContext();
		ctx.setTrialPackageName("org.adligo.tests4j_v1_tests.models.dependency_groups.gwt.v2_6.logging");
		ctx.setApiVersion("2_6");
		ctx.setGroupFactoryClass(GWT_2_6_Log.class);
		//ctx.setExtraTrialAnnotations("@SuppressOutput");
		ctx.setTrialClass("org.adligo.tests4j_tests.base_trials.SourceFileCountingTrial");
		ctx.setTrialClassSimpleName("SourceFileCountingTrial");
		ctx.setExtraTrialContent("" + Tests4J_System.lineSeperator() +
			"\t@Override" + Tests4J_System.lineSeperator() +
			"\tpublic int getTests(I_CountType type) {"+ Tests4J_System.lineSeperator() +
			"\t\treturn super.getTests(type, 1);"+ Tests4J_System.lineSeperator() +
			"\t}"+ Tests4J_System.lineSeperator() +
			"" + Tests4J_System.lineSeperator() +
			"\t@Override "+ Tests4J_System.lineSeperator() +
			"\tpublic int getAsserts(I_CountType type) { "+ Tests4J_System.lineSeperator() +
			"\t\tint thisAsserts = 1;"+ Tests4J_System.lineSeperator() +
			"\t\t//code coverage and circular dependencies +"+ Tests4J_System.lineSeperator() +
			"\t\t//custom afterTrialTests"+ Tests4J_System.lineSeperator() +
			"\t\t//+ see above"+ Tests4J_System.lineSeperator() +
			"\t\tint thisAfterAsserts = 24;"+ Tests4J_System.lineSeperator() +
			"\t\tif (type.isFromMetaWithCoverage()) {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, thisAsserts + thisAfterAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t} else {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, thisAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t}"+ Tests4J_System.lineSeperator() +
			"\t}"+ Tests4J_System.lineSeperator() +
			"" + Tests4J_System.lineSeperator() +
			"\t@Override"+ Tests4J_System.lineSeperator() +
			"\tpublic int getUniqueAsserts(I_CountType type) {"+ Tests4J_System.lineSeperator() +
			"\t\tint uAsserts = 1;"+ Tests4J_System.lineSeperator() +
			"\t\t//code coverage and circular dependencies +"+ Tests4J_System.lineSeperator() +
			"\t\t//custom afterTrialTests"+ Tests4J_System.lineSeperator() +
			"\t\t//+ see above"+ Tests4J_System.lineSeperator() +
			"\t\tint uAfterAsserts = 24;"+ Tests4J_System.lineSeperator() +
			"\t\tif (type.isFromMetaWithCoverage()) {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, uAsserts + uAfterAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t} else {"+ Tests4J_System.lineSeperator() +
			"\t\t\treturn super.getAsserts(type, uAsserts);"+ Tests4J_System.lineSeperator() +
			"\t\t}"+ Tests4J_System.lineSeperator() +
			"\t}"); 
		
		ConstantLookup cl = ctx.getConstantLookup();
		cl.addLookups(JSE_Lang.INSTANCE);
		cl.addLookups(JSE_Util.INSTANCE);
		gg.setCtx(ctx);
		//ctx.setRunConstantGen(false);
		//ctx.setRunConstantTrialGen(false);
		//gg.gen(getLang());
		//gg.gen(getAnnot());
		//gg.gen(getMath());
		//gg.gen(getSQL());
		//gg.gen(getUtil());
		
		gg.gen(getLog());
	}
	
	public static List<Class<?>> getLang() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		//the actual order
		/*
		toRet.add(Appendable.class);
		toRet.add(Object.class);
		toRet.add(StackTraceElement.class);
		toRet.add(Throwable.class);
		toRet.add(Exception.class);
		toRet.add(RuntimeException.class);
		toRet.add(ArithmeticException.class);
		toRet.add(IndexOutOfBoundsException.class);
		toRet.add(ArrayIndexOutOfBoundsException.class);
		toRet.add(ArrayStoreException.class);
		toRet.add(Error.class);
		toRet.add(AssertionError.class);
		toRet.add(ClassCastException.class);
		toRet.add(IllegalArgumentException.class);
		toRet.add(IllegalStateException.class);
		toRet.add(NegativeArraySizeException.class);
		//hmm gwt is still on 1.6?
		toRet.add(ReflectiveOperationException.class);
		
		
		toRet.add(NoSuchMethodException.class);
		toRet.add(NullPointerException.class);
		toRet.add(NumberFormatException.class);
		toRet.add(StringIndexOutOfBoundsException.class);
		toRet.add(UnsupportedOperationException.class);
		//all exceptions done
		toRet.add(Appendable.class);
		toRet.add(AutoCloseable.class);
		
		toRet.add(CharSequence.class);
		
		toRet.add(Cloneable.class);
		toRet.add(Comparable.class);
		toRet.add(Deprecated.class);
		toRet.add(Override.class);
		toRet.add(SuppressWarnings.class);
		
		toRet.add(Boolean.class);
		toRet.add(Enum.class);
		toRet.add(Number.class);
		toRet.add(Byte.class);
		toRet.add(Character.class);
		toRet.add(Character.class);
		toRet.add(Class.class);
		toRet.add(Double.class);
		toRet.add(Float.class);
		toRet.add(Integer.class);
		toRet.add(Iterable.class);
		toRet.add(Long.class);
		toRet.add(Math.class);
		toRet.add(Short.class);
		toRet.add(String.class);
		toRet.add(StringBuffer.class);
		toRet.add(StringBuilder.class);
		toRet.add(System.class);
		toRet.add(Void.class);
		*/
		return toRet;
	}
	
	public static List<Class<?>> getAnnot() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		/*
		toRet.add(Annotation.class);
		toRet.add(Documented.class); 
		toRet.add(AnnotationFormatError.class);
		toRet.add(AnnotationTypeMismatchException.class);
		toRet.add(ElementType.class); 
		
		toRet.add(Inherited.class); 
		toRet.add(Retention.class); 
		toRet.add(RetentionPolicy.class); 
		toRet.add(Target.class); 
		*/
		return toRet;
	}
	
	public static List<Class<?>> getMath() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		/*
		toRet.add(BigDecimal.class);
		toRet.add(BigInteger.class);
		toRet.add(MathContext.class);
		toRet.add(RoundingMode.class);
		*/
		return toRet;
	}
	
	public static List<Class<?>> getIO() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		/*
		toRet.add(IOException.class);
		toRet.add(UnsupportedEncodingException.class);
		toRet.add(FilterOutputStream.class);
		toRet.add(OutputStream.class);
		toRet.add(PrintStream.class);
		toRet.add(Serializable.class);
		*/
		return toRet;
	}
	
	public static List<Class<?>> getSQL() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		/*
		toRet.add(Date.class);
		toRet.add(Time.class);
		toRet.add(Timestamp.class);
		*/
		return toRet;
	}
	
	public static List<Class<?>> getUtil() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		/*
		toRet.add(ConcurrentModificationException.class);
		toRet.add(EmptyStackException.class);
		toRet.add(MissingResourceException.class);
		toRet.add(NoSuchElementException.class);
		toRet.add(TooManyListenersException.class);
		toRet.add(java.util.Date.class);
		
		toRet.add(AbstractCollection.class);
		toRet.add(AbstractList.class);
		toRet.add(AbstractMap.class);
		toRet.add(AbstractQueue.class);
		
		toRet.add(AbstractSequentialList.class);
		toRet.add(AbstractSet.class);
		toRet.add(ArrayList.class);
		
		toRet.add(Arrays.class);
		toRet.add(Collections.class);
		toRet.add(Collection.class);
		toRet.add(Comparator.class);
		toRet.add(EnumMap.class);
		toRet.add(EnumSet.class);
		
		toRet.add(Enumeration.class);
		toRet.add(EventListener.class);
		toRet.add(EventObject.class);
		toRet.add(HashMap.class);
		
		toRet.add(HashSet.class);
		toRet.add(IdentityHashMap.class);
		toRet.add(Iterator.class);
		toRet.add(LinkedHashMap.class);

		toRet.add(LinkedHashSet.class);
		toRet.add(LinkedList.class);
		toRet.add(List.class);
		toRet.add(ListIterator.class);
		
		toRet.add(Map.class);
		toRet.add(Map.Entry.class);
		toRet.add(Objects.class);
		
		toRet.add(PriorityQueue.class);
		toRet.add(Queue.class);
		toRet.add(Random.class);
		
		toRet.add(RandomAccess.class);
		toRet.add(Set.class);
		toRet.add(SortedMap.class);
		*/

		/*
		toRet.add(SortedSet.class);
		toRet.add(Stack.class);
		toRet.add(TreeMap.class);
		toRet.add(TreeSet.class);
		
		toRet.add(Vector.class);
		 */
		return toRet;
	}
	
	public static List<Class<?>> getLog() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		/*
		toRet.add(Formatter.class);
		toRet.add(Handler.class);
		toRet.add(Level.class);
		
		toRet.add(LogManager.class);
		toRet.add(LogRecord.class);
		toRet.add(Logger.class);
		*/
		return toRet;
	}
}
