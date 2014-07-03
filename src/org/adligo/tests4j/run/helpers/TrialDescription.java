package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.i18n.trials.I_Tests4J_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.metadata.I_UseCase;
import org.adligo.tests4j.models.shared.metadata.UseCase;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Reporter;
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


	private Class<? extends I_AbstractTrial> trialClass;
	
	private I_AbstractTrial trial;
	
	private Method beforeTrialMethod;
	private Method afterTrialTestsMethod;
	private Method afterTrialMethod;
	private final List<TestDescription> testMethods = new CopyOnWriteArrayList<TestDescription>();
	private TrialType type;
	private boolean trialCanRun = false;
	private String resultFailureMessage;
	private Exception resultException;
	private boolean ignored;
	private long duration;
	private long timeout;
	private I_Tests4J_Reporter reporter;
	private SourceFileScope sourceFileScope;
	private UseCaseScope useCaseScope;
	private PackageScope packageScope;
	
	public TrialDescription(Class<? extends I_AbstractTrial> pTrialClass,
			I_Tests4J_Reporter pLog) {
		long start = System.currentTimeMillis();
		reporter = pLog;
		trialClass = pTrialClass;
		if (reporter.isLogEnabled(TrialDescription.class)) {
			reporter.log("Creating TrialDescription for " + trialClass);
		}
		
		trialCanRun = checkTestClass();
		long end = System.currentTimeMillis();
		duration = end - start;
	}

	private boolean checkTestClass() {
		try {
			type = TrialTypeFinder.getTypeInternal(trialClass);
		} catch (IllegalArgumentException x) {
			resultException = x;
			resultFailureMessage = x.getMessage();
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
		
		List<TrialVerificationFailure> failures = locateTestMethods();
		if (failures.size() >= 1) {
			TrialVerificationFailure topFailure = failures.get(0);
			resultFailureMessage = topFailure.getFailureMessage();
			resultException = topFailure.getException();
			return false;
		}
		if (!createInstance()) {
			return false;
		}
		return true;
	}

	private boolean checkTypeAnnotations() {
		I_Tests4J_TrialDescriptionMessages messages = Tests4J_Constants.CONSTANTS.getTrialDescriptionMessages();
		
		String trialName = trialClass.getName();
		switch(type) {
			case SourceFileTrial:
					sourceFileScope = trialClass.getAnnotation(SourceFileScope.class);
					if (sourceFileScope == null) {
						resultFailureMessage = 
								messages.getNoSourceFileScope();
						resultException	 =
								new IllegalArgumentException(trialName + 
										messages.getWasAnnotatedIncorrectly());
						return false;
					} else {
						Class<?> clazz = sourceFileScope.sourceClass();
						if (clazz == null) {
							resultFailureMessage = 
									messages.getSourceFileScopeEmptyClass();
							resultException	 =
									new IllegalArgumentException(trialName + 
											messages.getWasAnnotatedIncorrectly());
							return false;
						}
					}
				break;
			case ApiTrial:
				packageScope = trialClass.getAnnotation(PackageScope.class);
				if (packageScope == null) {
					resultFailureMessage = 
							messages.getNoPackageScope();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				}
				String testedPackageName = packageScope.packageName();
				if (StringMethods.isEmpty(testedPackageName)) {
					resultFailureMessage = 
							messages.getPackageScopeEmptyName();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
				
				break;
			case UseCaseTrial:
				useCaseScope = trialClass.getAnnotation(UseCaseScope.class);
				if (useCaseScope == null) {
					resultFailureMessage = 
							messages.getNoUseCaseScope();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				}
				String system = useCaseScope.system();
				if (StringMethods.isEmpty(system)) {
					resultFailureMessage = 
							messages.getUseCaseScopeEmptySystem();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
				
				String nown = useCaseScope.nown();
				if (StringMethods.isEmpty(nown)) {
					resultFailureMessage = 
							messages.getUseCaseScopeEmptyNown();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
				
				String verb = useCaseScope.verb();
				if (StringMethods.isEmpty(verb)) {
					resultFailureMessage = 
							messages.getUseCaseScopeEmptyVerb();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
			default:
				//MetaTrial
		}
		return true;
	}

	private List<TrialVerificationFailure> locateTestMethods() {
		List<TrialVerificationFailure> failures = new ArrayList<TrialVerificationFailure>();
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
		
		I_Tests4J_TrialDescriptionMessages messages = 
				Tests4J_Constants.CONSTANTS.getTrialDescriptionMessages();
		
		for (Method method: methods) {
			if (BeforeTrialAuditor.audit(this, failures, method)) {
				if (beforeTrialMethod == null) {
					beforeTrialMethod = method;
				} else {
					failures.add(new TrialVerificationFailure(
							messages.getMultipleBeforeTrial(),
							new IllegalArgumentException(trialClass.getName() + 
									messages.getWasAnnotatedIncorrectly())));
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
					failures.add(new TrialVerificationFailure(
							messages.getMultipleAfterTrial(),
							new IllegalArgumentException(trialClass.getName() + 
									messages.getWasAnnotatedIncorrectly())));
				}
			}
			
		}

		
		if (testMethods.size() == 0) {
			failures.add(new TrialVerificationFailure(
					messages.getNoTests(),
					new IllegalArgumentException(trialClass.getName() + 
							messages.getWasAnnotatedIncorrectly())));
		}
		return failures;
	}



	private boolean createInstance() {
		try {
			Constructor<? extends I_AbstractTrial> constructor =
					trialClass.getConstructor(new Class[] {});
			constructor.setAccessible(true);
			Object o = constructor.newInstance(new Object[] {});
			trial = (I_AbstractTrial) o;
		} catch (Exception x) {
			I_Tests4J_TrialDescriptionMessages messages = 
					Tests4J_Constants.CONSTANTS.getTrialDescriptionMessages();
			
			resultFailureMessage = messages.getBadConstuctor(); 
			resultException	= x;
			return false;
		}
		return true;
	}

	public TrialType getType() {
		if (type == null) {
			return TrialType.UnknownTrialType;
		}
		return type;
	}
	
	public boolean isTrialCanRun() {
		return trialCanRun;
	}

	public String getResultFailureMessage() {
		return resultFailureMessage;
	}

	public Exception getResultException() {
		return resultException;
	}

	public void setTrialCanRun(boolean trialCanRun) {
		this.trialCanRun = trialCanRun;
	}

	public void setResultFailureMessage(String resultFailureMessage) {
		this.resultFailureMessage = resultFailureMessage;
	}

	public void setResultException(IllegalArgumentException resultException) {
		this.resultException = resultException;
	}

	public boolean isIgnored() {
		return ignored;
	}

	public long getDuration() {
		return duration;
	}

	/**
	 * @return 
	 *    trialClass.getName(); or in other words the 
	 *    full name of the trial class with dots.
	 */
	public String getTrialName() {
		return trialClass.getName();
	}
	public I_AbstractTrial getTrial() {
		return trial;
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
	
	public I_UseCase getUseCase() {
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
		return new UseCase(nown, verb);
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
}
