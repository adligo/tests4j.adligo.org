package org.adligo.tests4j.models.shared.asserts.uniform;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.AnnotationFormatError;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.annotation.IncompleteAnnotationException;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.EmptyStackException;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.TooManyListenersException;

/**
 * This class is a threadsafe (non mutable) implementation
 * of I_UniformEvaluatorLookup.
 * @author scott
 *
 */
public class EvaluatorLookup implements I_EvaluatorLookup {
	/**
	 * @see getDefault()
	 */
	public static final I_EvaluatorLookup DEFAULT_LOOKUP = getDefault();
	
	/**
	 * Note the default evaluators must know about each implementation class
	 * for fast lookup using class names to evaluators.  Also since we are planning
	 * to use this framework in GWT client, only GWT emulation classes are used as 
	 * defaults.
	 * 
	 * @return
	 */
	private static final I_EvaluatorLookup getDefault() {
		EvaluatorLookupMutant mut = new EvaluatorLookupMutant();
		
		ThrowableUniformEvaluator tue = new ThrowableUniformEvaluator();
		mut.setEvaluator(Throwable.class, tue);
		
		mut.setEvaluator(Error.class, tue);
		mut.setEvaluator(AssertionError.class, tue);
		mut.setEvaluator(AnnotationFormatError.class, tue);
		
		mut.setEvaluator(Exception.class, tue);
		mut.setEvaluator(ArithmeticException.class, tue);
		mut.setEvaluator(ArrayIndexOutOfBoundsException.class, tue);
		mut.setEvaluator(ArrayStoreException.class, tue);
		mut.setEvaluator(ClassCastException.class, tue);
		mut.setEvaluator(IllegalArgumentException.class, tue);
		mut.setEvaluator(IllegalStateException.class, tue);
		mut.setEvaluator(IndexOutOfBoundsException.class, tue);
		mut.setEvaluator(NegativeArraySizeException.class, tue);
		mut.setEvaluator(NoSuchMethodException.class, tue);
		mut.setEvaluator(NullPointerException.class, tue);
		mut.setEvaluator(NumberFormatException.class, tue);
		mut.setEvaluator(RuntimeException.class, tue);
		mut.setEvaluator(StringIndexOutOfBoundsException.class, tue);
		mut.setEvaluator(UnsupportedOperationException.class, tue);
		mut.setEvaluator(AnnotationTypeMismatchException.class, tue);
		mut.setEvaluator(IncompleteAnnotationException.class, tue);
		mut.setEvaluator(IOException.class, tue);
		mut.setEvaluator(UnsupportedEncodingException.class, tue);
		mut.setEvaluator(ConcurrentModificationException.class, tue);
		mut.setEvaluator(EmptyStackException.class, tue);
		mut.setEvaluator(MissingResourceException.class, tue);
		mut.setEvaluator(NoSuchElementException.class, tue);
		mut.setEvaluator(TooManyListenersException.class, tue);
		
		mut.setEvaluator(String.class, new StringUniformEvaluator());
		return new EvaluatorLookup(mut);
	}
	
	/**
	 * The map of class names
	 * to evaluators
	 */
	private Map<String, I_UniformAssertionEvaluator<?>> lookup;
	
	@SuppressWarnings("unchecked")
	public EvaluatorLookup() {
		lookup = Collections.EMPTY_MAP;
	}
	
	public EvaluatorLookup(I_EvaluatorLookup other) {
		lookup = Collections.unmodifiableMap(other.getLookupData());
	}

	@Override
	public I_UniformAssertionEvaluator<?> findEvaluator(Class<?> clazz) {
		String name = clazz.getName();
		return lookup.get(name);
	}

	@Override
	public Map<String, I_UniformAssertionEvaluator<?>> getLookupData() {
		return lookup;
	}
}
