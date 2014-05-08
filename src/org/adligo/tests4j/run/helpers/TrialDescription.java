package org.adligo.tests4j.run.helpers;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.I_AbstractTrial;
import org.adligo.tests4j.models.shared.IgnoreTrial;
import org.adligo.tests4j.models.shared.PackageScope;
import org.adligo.tests4j.models.shared.SourceFileScope;
import org.adligo.tests4j.models.shared.TrialTimeout;
import org.adligo.tests4j.models.shared.TrialType;
import org.adligo.tests4j.models.shared.UseCaseScope;
import org.adligo.tests4j.models.shared.common.IsEmpty;
import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.results.I_UseCase;
import org.adligo.tests4j.models.shared.results.UseCase;
import org.adligo.tests4j.models.shared.system.Tests4J_Constants;
import org.adligo.tests4j.models.shared.system.i18n.trials.I_Tests4J_TrialDescriptionMessages;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;

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
	private TrialTypeEnum type;
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
		
		type = getTypeInternal();
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
				if (IsEmpty.isEmpty(testedPackageName)) {
					resultFailureMessage = 
							messages.getPackageScopeEmptyName();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
				
				break;
			default:
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
				if (IsEmpty.isEmpty(system)) {
					resultFailureMessage = 
							messages.getUseCaseScopeEmptySystem();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
				
				String nown = useCaseScope.nown();
				if (IsEmpty.isEmpty(nown)) {
					resultFailureMessage = 
							messages.getUseCaseScopeEmptyNown();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
				
				String verb = useCaseScope.verb();
				if (IsEmpty.isEmpty(verb)) {
					resultFailureMessage = 
							messages.getUseCaseScopeEmptyVerb();
					resultException	 =
							new IllegalArgumentException(trialName + 
									messages.getWasAnnotatedIncorrectly());
					return false;
				} 
		}
		return true;
	}

	private List<TrialVerificationFailure> locateTestMethods() {
		List<TrialVerificationFailure> failures = new ArrayList<TrialVerificationFailure>();
		Method [] methods = trialClass.getDeclaredMethods();
		
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
			if (AfterTrialTestsAuditor.audit(this, failures, method)) {
				if (afterTrialTestsMethod == null) {
					afterTrialTestsMethod = method;
				} else {
					failures.add(new TrialVerificationFailure(
							messages.getMultipleAfterTrialTests(),
							new IllegalArgumentException(trialClass.getName() + 
									messages.getWasAnnotatedIncorrectly())));
				}
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
			Object o = constructor.newInstance();
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

	public TrialTypeEnum getType() {
		if (type == null) {
			return TrialTypeEnum.UnknownTrialType;
		}
		return type;
	}
	
	private TrialTypeEnum getTypeInternal() {
		TrialType type = trialClass.getAnnotation(TrialType.class);
		if (type == null)  {
			Class<?> checking = trialClass.getSuperclass();
			while (!Object.class.equals(checking)) {
				type = checking.getAnnotation(TrialType.class);
				if (type != null) {
					break;
				}
				checking = checking.getSuperclass();
			}
		}
		I_Tests4J_TrialDescriptionMessages messages = 
				Tests4J_Constants.CONSTANTS.getTrialDescriptionMessages();
		
		if (type == null) {
			resultFailureMessage = messages.getMissingTypeAnnotationPre() + 
						trialClass.getName() + 
					messages.getMissingTypeAnnotationPost();
			resultException	= new IllegalArgumentException(resultFailureMessage);
			return null;
		}
		TrialTypeEnum typeEnum = type.type();
		return typeEnum;
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
	
	public String getPackageName() {
		if (packageScope == null) {
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
		if (IsEmpty.isEmpty(nown)) {
			return null;
		}
		if (IsEmpty.isEmpty(verb)) {
			return null;
		}
		return new UseCase(nown, verb);
	}
}
