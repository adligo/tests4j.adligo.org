package org.adligo.tests4j.gen.dependency_groups;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.CharConversionException;
import java.io.Closeable;
import java.io.Console;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.Externalizable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.FilterReader;
import java.io.FilterWriter;
import java.io.Flushable;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.LineNumberInputStream;
import java.io.LineNumberReader;
import java.io.NotActiveException;
import java.io.NotSerializableException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.ObjectStreamConstants;
import java.io.ObjectStreamException;
import java.io.ObjectStreamField;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.SequenceInputStream;
import java.io.Serializable;
import java.io.SerializablePermission;
import java.io.StreamCorruptedException;
import java.io.StreamTokenizer;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.SyncFailedException;
import java.io.UTFDataFormatException;
import java.io.UnsupportedEncodingException;
import java.io.WriteAbortedException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.IncompleteAnnotationException;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Array;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.ClientInfoStatus;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DataTruncation;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.PseudoColumnUsage;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.RowIdLifetime;
import java.sql.SQLClientInfoException;
import java.sql.SQLData;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLInput;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.sql.SQLNonTransientConnectionException;
import java.sql.SQLOutput;
import java.sql.SQLPermission;
import java.sql.SQLRecoverableException;
import java.sql.SQLSyntaxErrorException;
import java.sql.SQLTimeoutException;
import java.sql.SQLTransactionRollbackException;
import java.sql.SQLTransientConnectionException;
import java.sql.SQLTransientException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.sql.Wrapper;
import java.util.AbstractCollection;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.AbstractQueue;
import java.util.AbstractSequentialList;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Currency;
import java.util.Deque;
import java.util.Dictionary;
import java.util.DuplicateFormatFlagsException;
import java.util.EmptyStackException;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.EventListenerProxy;
import java.util.EventObject;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Formattable;
import java.util.FormattableFlags;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.IllegalFormatCodePointException;
import java.util.IllegalFormatConversionException;
import java.util.IllegalFormatException;
import java.util.IllegalFormatFlagsException;
import java.util.IllegalFormatPrecisionException;
import java.util.IllegalFormatWidthException;
import java.util.IllformedLocaleException;
import java.util.InputMismatchException;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.MissingFormatWidthException;
import java.util.MissingResourceException;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.PropertyPermission;
import java.util.PropertyResourceBundle;
import java.util.Queue;
import java.util.Random;
import java.util.RandomAccess;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.UnknownFormatConversionException;
import java.util.UnknownFormatFlagsException;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.ErrorManager;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.LoggingPermission;
import java.util.logging.MemoryHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.SocketHandler;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;




public class PackageClassNameWriter {

	public static void main(String [] args) {
		try {
			System.out.println("//autogenerated by " + PackageClassNameWriter.class.getName());
			List<String> classNames =  new ArrayList<String>();
			addJavaLang(classNames);
			String packageConstName = "JSE_Lang";
			
			for (String className: classNames) {
				System.out.println("\t\ttoRet.put(\"" + className + "\",\"" +
						toUpperWithUnderscore(Class.forName(className)) +
						"\");");
			}
			
			for (String className: classNames) {
				System.out.println("\tpublic static final String " + 
						toUpperWithUnderscore(Class.forName(className)) +
						" = \"" + className + "\";");
			}
			for (String className: classNames) {
				System.out.println("\tassertEquals(\"" + 
						className +
						"\"," + packageConstName + "." + toUpperWithUnderscore(Class.forName(className)) + ");");
			}
			for (String className: classNames) {
				System.out.println("\tassertEquals(\"" + 
						toUpperWithUnderscore(Class.forName(className)) +
						"\"," + packageConstName + ".INSTANCE.getConstantName(\"" + className + "\"));");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static String toUpperWithUnderscore(Class<?> clazz) {
		String name = clazz.getName();
		if (name.indexOf("$") == -1) {
			name = clazz.getSimpleName();
		} else {
			name = name.substring(name.lastIndexOf("."), name.length());
		}
		char [] chars = name.toCharArray();
		StringBuilder sb = new StringBuilder();
		
		boolean lastUpper = false;
		boolean lastLower = false;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (Character.isUpperCase(c)) {
				if (lastLower) {
					sb.append("_");
				}
				sb.append(c);
				lastUpper = true;
				lastLower = false;
			} else if (Character.isLowerCase(c)) {
				sb.append(Character.toUpperCase(c));
				lastLower = true;
			} else if (c == '$' || c == '.'){
				
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private static void addJavaLang(List<String> names) {
		add(names, Appendable.class);
		add(names, AutoCloseable.class);
		
		add(names, CharSequence.class);
		add(names, Cloneable.class);
		add(names, Comparable.class);
		
		add(names, Iterable.class);
		
		add(names, Readable.class);
		add(names, Runnable.class);
		
		add(names, Thread.UncaughtExceptionHandler.class);
		//classes
		add(names, Boolean.class);
		add(names, Byte.class);
		
		add(names, Character.class);
		add(names, Character.Subset.class);
		add(names, Character.UnicodeBlock.class);
		add(names, Class.class);
		add(names, ClassLoader.class);
		add(names, ClassValue.class);
		add(names, Compiler.class);
		
		add(names, Double.class);
		
		add(names, Enum.class);
		
		add(names, Float.class);
		
		add(names, InheritableThreadLocal.class);
		add(names, Integer.class);
		
		add(names, Long.class);
		
		add(names, Math.class);
		
		add(names, Number.class);
		
		add(names, Object.class);
		
		add(names, Package.class);
		add(names, Process.class);
		add(names, ProcessBuilder.class);
		add(names, ProcessBuilder.Redirect.class);
		
		add(names, Runtime.class);
		add(names, RuntimePermission.class);
		
		add(names, SecurityManager.class);
		add(names, Short.class);
		add(names, StackTraceElement.class);
		add(names, StrictMath.class);
		add(names, String.class);
		add(names, StringBuffer.class);
		add(names, StringBuilder.class);
		add(names, System.class);
		
		add(names, Thread.class);
		add(names, ThreadGroup.class);
		add(names, ThreadLocal.class);
		add(names, Throwable.class);
		
		add(names, Void.class);
		
		//enums
		add(names, Character.UnicodeScript.class);
		add(names, ProcessBuilder.Redirect.Type.class);
		add(names, Thread.State.class);
		
		//exceptions
		add(names, ArithmeticException.class);
		add(names, ArrayIndexOutOfBoundsException.class);
		add(names, ArrayStoreException.class);
		
		add(names, ClassCastException.class);
		add(names, ClassNotFoundException.class);
		add(names, CloneNotSupportedException.class);
		
		add(names, EnumConstantNotPresentException.class);
		add(names, Exception.class);
		
		add(names, IllegalAccessException.class);
		add(names, IllegalArgumentException.class);
		add(names, IllegalMonitorStateException.class);
		add(names, IllegalStateException.class);
		add(names, IllegalThreadStateException.class);
		add(names, IndexOutOfBoundsException.class);
		add(names, InstantiationException.class);
		add(names, InterruptedException.class);
		
		add(names, NegativeArraySizeException.class);
		add(names, NoSuchFieldException.class);
		add(names, NoSuchMethodException.class);
		add(names, NullPointerException.class);
		add(names, NumberFormatException.class);
		
		add(names, ReflectiveOperationException.class);
		add(names, RuntimeException.class);
		
		add(names, SecurityException.class);
		add(names, StringIndexOutOfBoundsException.class);
		
		add(names, TypeNotPresentException.class);
		
		add(names, UnsupportedOperationException.class);
		
		//errors
		add(names, AbstractMethodError.class);
		add(names, AssertionError.class);
		
		add(names, BootstrapMethodError.class);
		
		add(names, ClassCircularityError.class);
		add(names, ClassFormatError.class);
		
		add(names, Error.class);
		add(names, ExceptionInInitializerError.class);
		
		add(names, IllegalAccessError.class);
		add(names, IncompatibleClassChangeError.class);
		add(names, InstantiationError.class);
		add(names, InternalError.class);
		
		add(names, LinkageError.class);
		
		add(names, NoClassDefFoundError.class);
		add(names, NoSuchFieldError.class);
		add(names, NoSuchMethodError.class);
		
		add(names, OutOfMemoryError.class);
		
		add(names, StackOverflowError.class);
		
		add(names, ThreadDeath.class);
		
		add(names, UnknownError.class);
		add(names, UnsatisfiedLinkError.class);
		add(names, UnsupportedClassVersionError.class);
		
		add(names, VerifyError.class);
		add(names, VirtualMachineError.class);
		
		add(names, Deprecated.class);
		add(names, FunctionalInterface.class);
		add(names, Override.class);
		add(names, SafeVarargs.class);
		add(names, SuppressWarnings.class);
		
	}
	
	private static void addJavaLangAnnotation(List<String> names) {
		add(names, Annotation.class);
		
		add(names, ElementType.class);
		add(names, RetentionPolicy.class);
		
		add(names, AnnotationTypeMismatchException.class);
		add(names, IncompleteAnnotationException.class);
		
		add(names, AnnotationFormatError.class);
		
		add(names, Documented.class);
		add(names, Inherited.class);
		add(names, Retention.class);
		add(names, Target.class);
		
	}
	
	
	private static void addJavaMath(List<String> names) {
		add(names, BigDecimal.class);
		add(names, BigInteger.class);
		add(names, MathContext.class);
		add(names, RoundingMode.class);
		
	}
	
	private static void addJavaIO(List<String> names) {
		add(names, Closeable.class);
		
		add(names, DataOutput.class);
		add(names, DataInput.class);
		
		add(names, Externalizable.class);
		
		add(names, FileFilter.class);
		add(names, FilenameFilter.class);
		add(names, Flushable.class);
		
		add(names, ObjectInput.class);
		add(names, ObjectInputValidation.class);
		add(names, ObjectOutput.class);
		add(names, ObjectStreamConstants.class);
		
		add(names, Serializable.class);
		
		
		//classes
		add(names, BufferedInputStream.class);
		add(names, BufferedOutputStream.class);
		add(names, BufferedReader.class);
		add(names, BufferedWriter.class);
		add(names, ByteArrayInputStream.class);
		add(names, ByteArrayOutputStream.class);
		
		add(names, CharArrayReader.class);
		add(names, CharArrayWriter.class);
		add(names, Console.class);
		
		add(names, DataInputStream.class);
		add(names, DataOutputStream.class);
		
		add(names, File.class);
		add(names, FileDescriptor.class);
		add(names, FileInputStream.class);
		add(names, FileOutputStream.class);
		add(names, FilePermission.class);
		add(names, FileReader.class);
		add(names, FileWriter.class);
		add(names, FilterInputStream.class);
		add(names, FilterOutputStream.class);
		add(names, FilterReader.class);
		add(names, FilterWriter.class);
		
		add(names, InputStream.class);
		add(names, InputStreamReader.class);
		
		add(names, LineNumberInputStream.class);
		add(names, LineNumberReader.class);
		
		add(names, ObjectInputStream.class);
		add(names, ObjectInputStream.GetField.class);
		add(names, ObjectOutputStream.class);
		add(names, ObjectOutputStream.PutField.class);
		add(names, ObjectStreamClass.class);
		add(names, ObjectStreamField.class);
		add(names, OutputStream.class);
		add(names, OutputStreamWriter.class);
		
		add(names, PipedInputStream.class);
		add(names, PipedOutputStream.class);
		add(names, PipedReader.class);
		add(names, PipedWriter.class);
		add(names, PrintStream.class);
		add(names, PrintWriter.class);
		add(names, PushbackInputStream.class);
		add(names, PushbackReader.class);
		
		add(names, RandomAccessFile.class);
		add(names, Reader.class);
		
		add(names, SequenceInputStream.class);
		add(names, SerializablePermission.class);
		add(names, StreamTokenizer.class);
		add(names, StringBufferInputStream.class);
		add(names, StringReader.class);
		add(names, StringWriter.class);
		
		add(names, Writer.class);
		
		//exceptions
		add(names, CharConversionException.class);
		
		add(names, EOFException.class);
		
		add(names, FileNotFoundException.class);
		
		add(names, InterruptedException.class);
		add(names, InvalidClassException.class);
		add(names, InvalidObjectException.class);
		add(names, IOException.class);
		
		add(names, NotActiveException.class);
		add(names, NotSerializableException.class);
		
		add(names, ObjectStreamException.class);
		add(names, OptionalDataException.class);
		
		add(names, StreamCorruptedException.class);
		add(names, SyncFailedException.class);
		
		add(names, UnsupportedEncodingException.class);
		add(names, UTFDataFormatException.class);
		
		add(names, WriteAbortedException.class);
		
		//error
		add(names, IOError.class);
	}
	
	private static void addJavaSql(List<String> names) {
		add(names, Array.class);
		add(names, Blob.class);
		add(names, CallableStatement.class);
		
		add(names, Clob.class);
		add(names, Connection.class);
		
		add(names, DatabaseMetaData.class);
		add(names, Driver.class);
		
		add(names, NClob.class);
		
		add(names, ParameterMetaData.class);
		add(names, PreparedStatement.class);
		
		add(names, Ref.class);
		add(names, ResultSet.class);
		add(names, ResultSetMetaData.class);
		add(names, RowId.class);
		
		add(names, Savepoint.class);
		add(names, SQLData.class);
		add(names, SQLInput.class);
		add(names, SQLOutput.class);
		add(names, SQLXML.class);
		add(names, Statement.class);
		add(names, Struct.class);
		
		add(names, Wrapper.class);
		//classes
		add(names, Date.class);
		add(names, DriverManager.class);
		add(names, DriverPropertyInfo.class);
		
		add(names, SQLPermission.class);
		
		add(names, Time.class);
		add(names, Timestamp.class);
		add(names, Types.class);
		
		//enums
		add(names, ClientInfoStatus.class);
		add(names, PseudoColumnUsage.class);
		add(names, RowIdLifetime.class);
		
		//exception
		add(names, BatchUpdateException.class);
		add(names, DataTruncation.class);
		
		add(names, SQLClientInfoException.class);
		add(names, SQLDataException.class);
		add(names, SQLException.class);
		add(names, SQLFeatureNotSupportedException.class);
		add(names, SQLIntegrityConstraintViolationException.class);
		add(names, SQLInvalidAuthorizationSpecException.class);
		add(names, SQLNonTransientConnectionException.class);
		add(names, SQLRecoverableException.class);
		add(names, SQLSyntaxErrorException.class);
		add(names, SQLTimeoutException.class);
		add(names, SQLTransactionRollbackException.class);
		add(names, SQLTransientConnectionException.class);
		add(names, SQLTransientException.class);
		add(names, SQLWarning.class);
	}
	
	private static void addJavaUtil(List<String> names) {
		add(names, Collection.class);
		add(names, Comparator.class);
		
		add(names, Deque.class);
		
		add(names, Enumeration.class);
		add(names, EventListener.class);
		
		add(names, Formattable.class);
		
		add(names, Iterator.class);
		
		add(names, List.class);
		add(names, ListIterator.class);
		
		add(names, Map.class);
		add(names, Map.Entry.class);
		
		add(names, NavigableMap.class);
		add(names, NavigableSet.class);
		
		add(names, Observer.class);
		
		add(names, Queue.class);
		
		add(names, RandomAccess.class);
		
		add(names, Set.class);
		add(names, SortedMap.class);
		add(names, SortedSet.class);
		
		//classes
		add(names, AbstractCollection.class);
		add(names, AbstractList.class);
		add(names, AbstractMap.class);
		add(names, AbstractMap.SimpleEntry.class);
		add(names, AbstractMap.SimpleImmutableEntry.class);
		add(names, AbstractQueue.class);
		add(names, AbstractSequentialList.class);
		add(names, AbstractSet.class);
		add(names, ArrayList.class);
		add(names, Arrays.class);
		
		add(names, BitSet.class);
		
		add(names, Calendar.class);
		add(names, Collections.class);
		add(names, Currency.class);
		
		add(names, Date.class);
		add(names, Dictionary.class);
		
		add(names, EnumMap.class);
		add(names, EnumSet.class);
		add(names, EventListenerProxy.class);
		add(names, EventObject.class);
		
		add(names, FormattableFlags.class);
		add(names, Formatter.class);
		
		add(names, GregorianCalendar.class);
		
		add(names, HashMap.class);
		add(names, HashSet.class);
		add(names, Hashtable.class);
		
		add(names, IdentityHashMap.class);
		
		add(names, LinkedHashMap.class);
		add(names, LinkedHashSet.class);
		add(names, LinkedList.class);
		add(names, ListResourceBundle.class);
		add(names, Locale.class);
		add(names, Locale.Builder.class);
		
		add(names, Objects.class);
		add(names, Observable.class);
		
		add(names, PriorityQueue.class);
		add(names, Properties.class);
		add(names, PropertyPermission.class);
		add(names, PropertyResourceBundle.class);
		
		add(names, Random.class);
		add(names, ResourceBundle.class);
		add(names, ResourceBundle.Control.class);
		
		add(names, Scanner.class);
		add(names, ServiceLoader.class);
		add(names, SimpleTimeZone.class);
		add(names, Stack.class);
		add(names, StringTokenizer.class);
		
		add(names, Timer.class);
		add(names, TimerTask.class);
		add(names, TimeZone.class);
		add(names, TreeMap.class);
		add(names, TreeSet.class);
		
		add(names, UUID.class);
		
		add(names, Vector.class);
		
		add(names, WeakHashMap.class);
		
		//enums
		add(names, Formatter.BigDecimalLayoutForm.class);
		add(names, Locale.Category.class);
		
		//exceptions
		add(names, ConcurrentModificationException.class);
		
		add(names, DuplicateFormatFlagsException.class);
		
		add(names, EmptyStackException.class);
		
		add(names, FormatFlagsConversionMismatchException.class);
		add(names, FormatterClosedException.class);
		
		add(names, IllegalFormatCodePointException.class);
		add(names, IllegalFormatConversionException.class);
		add(names, IllegalFormatException.class);
		add(names, IllegalFormatFlagsException.class);
		add(names, IllegalFormatPrecisionException.class);
		add(names, IllegalFormatWidthException.class);
		add(names, IllformedLocaleException.class);
		add(names, InputMismatchException.class);
		add(names, InvalidPropertiesFormatException.class);
		
		add(names, MissingFormatArgumentException.class);
		add(names, MissingFormatWidthException.class);
		add(names, MissingResourceException.class);
		
		add(names, NoSuchElementException.class);
		
		add(names, TooManyListenersException.class);
		
		add(names, UnknownFormatConversionException.class);
		add(names, UnknownFormatFlagsException.class);
		//errors
		add(names, ServiceConfigurationError.class);
	}
	
	private static void addJavaUtilLogging(List<String> names) {
		add(names, Filter.class);
		
		add(names, ConsoleHandler.class);
		
		
		add(names, ErrorManager.class);
		
		add(names, FileHandler.class);
		add(names, Formatter.class);
		
		add(names, Handler.class);
	
		add(names, Level.class);
		add(names, Logger.class);
		add(names, LoggingPermission.class);
		add(names, LogManager.class);
		add(names, LogRecord.class);
		
		add(names, MemoryHandler.class);
		
		add(names, SimpleFormatter.class);
		add(names, SocketHandler.class);
		add(names, StreamHandler.class);
		
		add(names, XMLFormatter.class);
	}
	
	private static void add(List<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
