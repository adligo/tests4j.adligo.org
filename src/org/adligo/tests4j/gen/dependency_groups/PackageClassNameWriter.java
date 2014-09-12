package org.adligo.tests4j.gen.dependency_groups;

import java.util.ArrayList;
import java.util.List;

public class PackageClassNameWriter {

	public static void main(String [] args) {
		List<Class<?>> utils = getLangClasses();
		for (Class<?> u: utils) {
			/*
			System.out.println(
					"toRet.put(\"" + u.getName() + "\",\"" + 
			toConstantJavaText(u) + "\");");
			*/
			System.out.println(
					"assertEquals(\"" + toConstantJavaText(u)  + "\","
							+ "JSE_Util.INSTANCE.getConstantName(\"" +u.getName()
							+ "\"));");
		}
	}
	
	public static String toConstantJavaText(Class<?> c) {
		String orig = c.getSimpleName();
		
		StringBuilder sb = new StringBuilder();
		char [] chars = orig.toCharArray();
		boolean lastUpper = true;
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			if (Character.isUpperCase(ch)) {
				if (!lastUpper) {
					sb.append("_");
				}
				sb.append(ch);
				lastUpper = true;
			} else {
				lastUpper = false;
				sb.append(Character.toUpperCase(ch));
			}
		}
		return sb.toString();
	}
	
	public static List<Class<?>> getUtilClasses() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		/* keep commented out and 
		 * remove import so older versions of java can use the project
		toRet.add(Collection.class);
		toRet.add(Comparator.class);
		toRet.add(Deque.class);
		toRet.add(Enumeration.class);
		toRet.add(EventListener.class);
		toRet.add(Formattable.class);
		toRet.add(Iterator.class);
		toRet.add(List.class);
		toRet.add(ListIterator.class);
		toRet.add(Map.class);
		
		toRet.add(Map.Entry.class);
		toRet.add(NavigableMap.class);
		toRet.add(NavigableSet.class);
		toRet.add(Observer.class);
		toRet.add(Queue.class);
		toRet.add(RandomAccess.class);
		toRet.add(Set.class);
		toRet.add(SortedMap.class);
		toRet.add(SortedSet.class);
		
		toRet.add(AbstractCollection.class);
		toRet.add(AbstractList.class);
		toRet.add(AbstractMap.class);
		toRet.add(AbstractMap.SimpleEntry.class);
		toRet.add(AbstractMap.SimpleImmutableEntry.class);
		toRet.add(AbstractQueue.class);
		toRet.add(AbstractSequentialList.class);
		toRet.add(AbstractSet.class);
		toRet.add(ArrayDeque.class);
		toRet.add(ArrayList.class);
		toRet.add(Arrays.class);
		toRet.add(BitSet.class);
		
		toRet.add(Calendar.class);
		toRet.add(Collections.class);
		toRet.add(Currency.class);
		toRet.add(Date.class);
		toRet.add(Dictionary.class);
		toRet.add(EnumMap.class);
		toRet.add(EnumSet.class);
		toRet.add(EventListenerProxy.class);
		toRet.add(EventObject.class);
		toRet.add(FormattableFlags.class);
		toRet.add(Formatter.class);
		
		toRet.add(GregorianCalendar.class);
		toRet.add(HashMap.class);
		toRet.add(HashSet.class);
		toRet.add(Hashtable.class);
		toRet.add(IdentityHashMap.class);
		toRet.add(LinkedHashMap.class);
		toRet.add(LinkedHashSet.class);
		toRet.add(ListResourceBundle.class);
		toRet.add(Locale.class);
		toRet.add(Locale.Builder.class);
		
		toRet.add(Objects.class);
		toRet.add(Observable.class);
		toRet.add(PriorityQueue.class);
		toRet.add(Properties.class);
		toRet.add(PropertyPermission.class);
		toRet.add(PropertyResourceBundle.class);
		toRet.add(Random.class);
		toRet.add(ResourceBundle.class);
		toRet.add(ResourceBundle.Control.class);
		toRet.add(Scanner.class);
		
		toRet.add(ServiceLoader.class);
		toRet.add(SimpleTimeZone.class);
		toRet.add(Stack.class);
		toRet.add(StringTokenizer.class);
		toRet.add(Timer.class);
		toRet.add(TimerTask.class);
		toRet.add(TimeZone.class);
		toRet.add(TreeMap.class);
		toRet.add(TreeSet.class);
		toRet.add(UUID.class);
		toRet.add(Vector.class);
		toRet.add(WeakHashMap.class);
		
		toRet.add(Formatter.BigDecimalLayoutForm.class);
		toRet.add(Locale.Category.class);
		
		toRet.add(ConcurrentModificationException.class);
		toRet.add(DuplicateFormatFlagsException.class);
		toRet.add(EmptyStackException.class);
		toRet.add(FormatFlagsConversionMismatchException.class);
		toRet.add(FormatterClosedException.class);
		toRet.add(IllegalFormatCodePointException.class);
		toRet.add(IllegalFormatConversionException.class);
		toRet.add(IllegalFormatException.class);
		toRet.add(IllegalFormatFlagsException.class);
		toRet.add(IllegalFormatPrecisionException.class);
		
		toRet.add(IllegalFormatWidthException.class);
		toRet.add(IllformedLocaleException.class);
		toRet.add(InputMismatchException.class);
		toRet.add(InvalidPropertiesFormatException.class);
		toRet.add(MissingFormatArgumentException.class);
		toRet.add(MissingFormatWidthException.class);
		toRet.add(MissingResourceException.class);
		toRet.add(NoSuchElementException.class);
		toRet.add(TooManyListenersException.class);
		toRet.add(UnknownFormatFlagsException.class);
		
		toRet.add(ServiceConfigurationError.class);
		*/
		return toRet;
	}
	
	public static List<Class<?>> getLangClasses() {
		List<Class<?>> toRet = new ArrayList<Class<?>>();
		
		/* keep commented out and 
		 * remove import so older versions of java can use the project
		 */
		toRet.add(Appendable.class);
		toRet.add(AutoCloseable.class);
		toRet.add(CharSequence.class);
		toRet.add(Cloneable.class);
		toRet.add(Iterable.class);
		toRet.add(Readable.class);
		toRet.add(Runnable.class);
		toRet.add(Thread.UncaughtExceptionHandler.class);
		toRet.add(Boolean.class);
		
		toRet.add(Byte.class);
		toRet.add(Character.class);
		toRet.add(Character.Subset.class);
		toRet.add(Character.UnicodeBlock.class);
		toRet.add(Class.class);
		toRet.add(ClassLoader.class);
		toRet.add(ClassValue.class);
		toRet.add(Compiler.class);
		toRet.add(Double.class);
		toRet.add(Enum.class);
		
		toRet.add(Float.class);
		toRet.add(InheritableThreadLocal.class);
		toRet.add(Integer.class);
		toRet.add(Long.class);
		toRet.add(Math.class);
		toRet.add(Number.class);
		toRet.add(Object.class);
		toRet.add(Package.class);
		toRet.add(Process.class);
		toRet.add(ProcessBuilder.class);
		
		toRet.add(ProcessBuilder.Redirect.class);
		toRet.add(Runtime.class);
		toRet.add(RuntimePermission.class);
		toRet.add(SecurityManager.class);
		toRet.add(Short.class);
		toRet.add(StackTraceElement.class);
		toRet.add(StrictMath.class);
		toRet.add(String.class);
		toRet.add(StringBuilder.class);
		toRet.add(StringBuffer.class);
		
		toRet.add(System.class);
		toRet.add(Thread.class);
		toRet.add(ThreadGroup.class);
		toRet.add(ThreadLocal.class);
		toRet.add(Throwable.class);
		toRet.add(Void.class);
		
		toRet.add(Character.UnicodeScript.class);
		toRet.add(ProcessBuilder.Redirect.Type.class);
		toRet.add(Thread.State.class);
		
		toRet.add(ArithmeticException.class);
		toRet.add(ArrayIndexOutOfBoundsException.class);
		toRet.add(ArrayStoreException.class);
		toRet.add(ClassCastException.class);
		toRet.add(CloneNotSupportedException.class);
		toRet.add(EnumConstantNotPresentException.class);
		toRet.add(Exception.class);
		toRet.add(IllegalAccessException.class);
		toRet.add(IllegalArgumentException.class);
		toRet.add(IllegalMonitorStateException.class);
		toRet.add(IllegalStateException.class);
		toRet.add(IllegalThreadStateException.class);
		toRet.add(InstantiationException.class);
		
		toRet.add(InterruptedException.class);
		toRet.add(NegativeArraySizeException.class);
		toRet.add(NoSuchFieldException.class);
		toRet.add(NoSuchMethodException.class);
		toRet.add(NullPointerException.class);
		toRet.add(NumberFormatException.class);
		toRet.add(ReflectiveOperationException.class);
		toRet.add(RuntimeException.class);
		toRet.add(SecurityException.class);
		
		toRet.add(StringIndexOutOfBoundsException.class);
		toRet.add(TypeNotPresentException.class);
		toRet.add(UnsupportedOperationException.class);
		
		toRet.add(AbstractMethodError.class);
		toRet.add(AssertionError.class);
		toRet.add(BootstrapMethodError.class);
		toRet.add(ClassCircularityError.class);
		toRet.add(ClassFormatError.class);
		toRet.add(Error.class);
		toRet.add(ExceptionInInitializerError.class);
		
		toRet.add(IllegalAccessError.class);
		toRet.add(IncompatibleClassChangeError.class);
		toRet.add(InstantiationError.class);
		toRet.add(InternalError.class);
		toRet.add(LinkageError.class);
		toRet.add(NoClassDefFoundError.class);
		toRet.add(NoSuchFieldError.class);
		toRet.add(OutOfMemoryError.class);
		toRet.add(StackOverflowError.class);
		toRet.add(ThreadDeath.class);
		
		toRet.add(UnknownError.class);
		toRet.add(UnsatisfiedLinkError.class);
		toRet.add(UnsupportedClassVersionError.class);
		toRet.add(VerifyError.class);
		toRet.add(VirtualMachineError.class);
		
		toRet.add(Deprecated.class);
		toRet.add(Override.class);
		toRet.add(SafeVarargs.class);
		toRet.add(SuppressWarnings.class);
		
		return toRet;
	}
}
