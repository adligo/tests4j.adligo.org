package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.common.I_TrialType;
import org.adligo.tests4j.models.shared.common.StackTraceBuilder;
import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.metadata.I_UseCaseMetadata;
import org.adligo.tests4j.models.shared.metadata.UseCaseMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.IgnoreTrial;
import org.adligo.tests4j.models.shared.trials.PackageScope;
import org.adligo.tests4j.models.shared.trials.SourceFileScope;
import org.adligo.tests4j.models.shared.trials.TrialTimeout;
import org.adligo.tests4j.models.shared.trials.UseCaseScope;

/**
 * a generally immutable class that represents/wrapps
 * a Trial class adding the reflection Methods exc
 * so that it can be run.
 * 
 * @author scott
 *
 */
public class TrialDescription implements I_TrialDescription {

	/**
	 * this may be either the non instrumented class instance
	 * or the instrumented class instance, as instances of 
	 * TrialDescription are created twice for each trial 
	 * when there is a CodeCoveragePlugin
	 */
	private I_AbstractTrial trial;
	private Class<? extends I_AbstractTrial> trialClass;
	
	private Method beforeTrialMethod;
	private Method afterTrialTestsMethod;
	private Method afterTrialMethod;
	private final List<TestDescription> testMethods = new CopyOnWriteArrayList<TestDescription>();
	private I_TrialType type;
	private boolean runnable = false;
	private boolean ignored;
	private long timeout;
	private I_Tests4J_Log reporter;
	private SourceFileScope sourceFileScope;
	private UseCaseScope useCaseScope;
	private PackageScope packageScope;
	private double minCoverage = 100.0;
	private List<I_TrialFailure> failures = new ArrayList<I_TrialFailure>();
	
	public TrialDescription(Class<? extends I_AbstractTrial> pTrialClass,
			I_Tests4J_Log pLog) {
		reporter = pLog;
		trialClass = pTrialClass;
		if (reporter.isLogEnabled(TrialDescription.class)) {
			reporter.log("Creating TrialDescription for " + trialClass);
		}
		
		runnable = checkTestClass();
	}

	private boolean checkTestClass() {
		type = TrialTypeFinder.getTypeInternal(trialClass, failures);
		if (failures.size() >= 1) {
			return false;
		}
		if (!checkTypeAnnotations()) {
			return false;
		}
		IgnoreTrial ignoredTrial = trialClass.getAnnotation(IgnoreTrial.class);
		if (ignoredTrial != null) {
			ignored = true;
			return false;
		}
		TrialTimeout trialTimeout = trialClass.getAnnotation(TrialTimeout.class);
		if (trialTimeout != null) {
			timeout = trialTimeout.timeout();
		} else {
			timeout = 0;
		}
		findTestMethods();
		if (failures.size() >= 1) {
			return false;
		}
		if (!createInstance()) {
			return false;
		}
		return true;
	}

	private boolean checkTypeAnnotations() {
		
		String trialName = trialClass.getName();
		TrialType tt = TrialType.get(type);
		switch(tt) {
			case SourceFileTrial:
					sourceFileScope = trialClass.getAnnotation(SourceFileScope.class);
					if (sourceFileScope == null) {
						I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
						I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
						
						failures.add(new TrialFailure(
								annonErrors.getNoSourceFileScope(),
								trialName + annonErrors.getWasAnnotatedIncorrectly()));
						return false;
					} else {
						Class<?> clazz = sourceFileScope.sourceClass();
						if (clazz == null) {
							I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
							I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
							
							failures.add(new TrialFailure(
									annonErrors.getSourceFileScopeEmptyClass(),
									trialName + annonErrors.getWasAnnotatedIncorrectly()));
							return false;
						}
						minCoverage = sourceFileScope.minCoverage();
						if (minCoverage > 100.0 || minCoverage < 0.0) {
							I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
							I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
							
							failures.add(new TrialFailure( 
									annonErrors.getMinCoverageMustBeBetweenZeroAndOneHundred(),
									trialName + annonErrors.getWasAnnotatedIncorrectly()));
							return false;
						}
					}
				break;
			case ApiTrial:
				packageScope = trialClass.getAnnotation(PackageScope.class);
				if (packageScope == null) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getNoPackageScope(),
							trialName + annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				}
				String testedPackageName = packageScope.packageName();
				if (StringMethods.isEmpty(testedPackageName)) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getPackageScopeEmptyName(),
							trialName + annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				} 
				
				break;
			case UseCaseTrial:
				useCaseScope = trialClass.getAnnotation(UseCaseScope.class);
				if (useCaseScope == null) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getNoUseCaseScope(),
							trialName + annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				}
				String system = useCaseScope.system();
				if (StringMethods.isEmpty(system)) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getUseCaseScopeEmptySystem()
							, trialName + annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				} 
				
				String nown = useCaseScope.nown();
				if (StringMethods.isEmpty(nown)) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getUseCaseScopeEmptyNown(), 
							trialName + 
									annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				} 
				
				String verb = useCaseScope.verb();
				if (StringMethods.isEmpty(verb)) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getUseCaseScopeEmptyVerb(),
							trialName + annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				} 
			default:
				//MetaTrial
		}
		return true;
	}

	private void findTestMethods() {
		/**
		 * since all tests must take 
		 * no params, we can rely on it's hashCode
		 */
		Set<Method> methods = new HashSet<Method>();
		Method [] dm = trialClass.getDeclaredMethods();
		for (int i = 0; i < dm.length; i++) {
			methods.add(dm[i]);
		}
		Method [] ms = trialClass.getMethods();
		for (int i = 0; i < ms.length; i++) {
			methods.add(ms[i]);
		}
		
		
		
		for (Method method: methods) {
			if (BeforeTrialAuditor.audit(this, failures, method)) {
				if (beforeTrialMethod == null) {
					beforeTrialMethod = method;
				} else {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getMultipleBeforeTrial(),
							trialClass.getName() + annonErrors.getWasAnnotatedIncorrectly()));
				}
			}
			TestDescription testDesc = TestAuditor.audit(this, failures, method);
			if (testDesc != null) {
				testMethods.add(testDesc);
			}
			if (AfterTrialAuditor.audit(this, failures, method)) {
				if (afterTrialMethod == null) {
					afterTrialMethod = method;
				} else {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
					
					failures.add(new TrialFailure(
							annonErrors.getMultipleAfterTrial(),
							trialClass.getName() + annonErrors.getWasAnnotatedIncorrectly()));
				}
			}
			
		}

	}



	/**
	 * Note this needs to be called twice
	 * once to see if the trial can run 
	 * and another time to 
	 * @return
	 */
	private boolean createInstance() {
		try {
			Constructor<? extends I_AbstractTrial> constructor =
					trialClass.getConstructor(new Class[] {});
			constructor.setAccessible(true);
			Object o = constructor.newInstance(new Object[] {});
			trial =  (I_AbstractTrial) o;
			return true;
		} catch (Exception x) {
			failures.add(new TrialFailure(
					Tests4J_Constants.CONSTANTS.getBadConstuctor(),
					StackTraceBuilder.toString(x, true)));
		}
		return false;
	}

	public I_TrialType getType() {
		if (type == null) {
			return TrialType.UnknownTrialType;
		}
		return type;
	}
	
	/**
	 * if this trial can run.
	 * @return
	 */
	public boolean isRunnable() {
		return runnable;
	}

	public void setRunnable(boolean trialCanRun) {
		this.runnable = trialCanRun;
	}

	public boolean isIgnored() {
		return ignored;
	}

	/**
	 * @return 
	 *    trialClass.getName(); or in other words the 
	 *    full name of the trial class with dots.
	 */
	public String getTrialName() {
		return trialClass.getName();
	}
	
	public Method getBeforeTrialMethod() {
		return beforeTrialMethod;
	}

	public Method getAfterTrialMethod() {
		return afterTrialMethod;
	}

	public int getTestMethodsSize() {
		return testMethods.size();
	}
	
	public Iterator<TestDescription> getTestMethods() {
		return testMethods.iterator();
	}

	public Class<? extends I_AbstractTrial> getTrialClass() {
		return trialClass;
	}

	public long getTimeout() {
		return timeout;
	}

	public Method getAfterTrialTestsMethod() {
		return afterTrialTestsMethod;
	}
	
	public Class<?> getSourceFileClass() {
		if (sourceFileScope == null) {
			return null;
		}
		return sourceFileScope.sourceClass();
	}
	
	/**
	 * return the packageName from the package scope if it
	 * is present, or the package from the sourceFileScope's
	 * class package if present
	 * @return
	 */
	public String getPackageName() {
		if (packageScope == null) {
			if (sourceFileScope != null) {
				Class<?> claz = sourceFileScope.sourceClass();
				if (claz != null) {
					Package pkg = claz.getPackage();
					if (pkg != null) {
						return pkg.getName();
					}
				}
			}
			return null;
		}
		return packageScope.packageName();
	}
	
	public String getSystemName() {
		if (useCaseScope == null) {
			return null;
		}
		return useCaseScope.system();
	}
	
	public I_UseCaseMetadata getUseCase() {
		if (useCaseScope == null) {
			return null;
		}
		String nown = useCaseScope.nown();
		String verb = useCaseScope.verb();
		if (StringMethods.isEmpty(nown)) {
			return null;
		}
		if (StringMethods.isEmpty(verb)) {
			return null;
		}
		return new UseCaseMetadata(nown, verb);
	}
	
	public I_SourceFileCoverage findSourceFileCoverage(List<I_PackageCoverage> coverages) {
		I_PackageCoverage cover = findPackageCoverage(coverages);
		if (cover != null) {
			Class<?> sourceFileClass = getSourceFileClass();
			if (sourceFileClass != null) {
				return cover.getCoverage(sourceFileClass.getSimpleName());
			}
		}
		return null;
	}
	
	public I_PackageCoverage findPackageCoverage(List<I_PackageCoverage> coverages) {
		String packageName = getPackageName();
		if (packageName == null) {
			Class<?> sourceFileClass = getSourceFileClass();
			if (sourceFileClass != null) {
				packageName = sourceFileClass.getPackage().getName();
			}
		}
		if (packageName == null) {
			return null;
		}
		for (I_PackageCoverage cover: coverages) {
			String coverName = cover.getPackageName();
			if (coverName.equals(packageName)) {
				return cover;
			} else if (packageName.contains(coverName)) {
				return findPackageCoverage(cover.getChildPackageCoverage());
			}
		}
		return null;
	}




	@Override
	public String toString() {
		return "TrialDescription [trialClass=" + trialClass + "]";
	}

	public double getMinCoverage() {
		return minCoverage;
	}

	public I_AbstractTrial getTrial() {
		return trial;
	}

	public List<I_TrialFailure> getFailures() {
		return Collections.unmodifiableList(failures);
	}
}
