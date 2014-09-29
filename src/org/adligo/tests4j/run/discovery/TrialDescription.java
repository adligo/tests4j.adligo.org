package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageMutant;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependenciesLocal;
import org.adligo.tests4j.models.shared.metadata.I_UseCaseMetadata;
import org.adligo.tests4j.models.shared.metadata.UseCaseMetadata;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.run.common.I_Tests4J_Memory;
import org.adligo.tests4j.shared.asserts.dependency.CircularDependencies;
import org.adligo.tests4j.shared.asserts.dependency.I_CircularDependencies;
import org.adligo.tests4j.shared.asserts.dependency.I_DependencyGroup;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.StackTraceBuilder;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.common.Tests4J_System;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.I_Tests4J_CoverageTrialInstrumentation;
import org.adligo.tests4j.system.shared.trials.AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.IgnoreTrial;
import org.adligo.tests4j.system.shared.trials.PackageScope;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.SuppressOutput;
import org.adligo.tests4j.system.shared.trials.TrialTimeout;
import org.adligo.tests4j.system.shared.trials.UseCaseScope;

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
	private Class<? extends I_AbstractTrial> trialClass_;
	
	private Method beforeTrialMethod;
	private Method afterTrialTestsMethod_;
	private Method afterTrialMethod;
	private final List<TestDescription> testMethods = new CopyOnWriteArrayList<TestDescription>();
	private I_TrialType type;
	private boolean runnable = false;
	private I_CircularDependencies allowedCircularDependencies;
	private boolean ignored;
	private long timeout;
	private I_Tests4J_Log reporter;
	private SourceFileScope sourceFileScope;
	private UseCaseScope useCaseScope;
	private PackageScope packageScope;
	private double minCoverage_ = 100.0;
	private List<I_TrialFailure> failures = new ArrayList<I_TrialFailure>();
	private I_ClassDependenciesLocal sourceClassDependencies;
	boolean printToStdOut_ = true;
	private I_DependencyGroup deps_;
	private I_Tests4J_Memory memory_;
	
	public TrialDescription(Class<? extends I_AbstractTrial> pTrialClass,
			I_Tests4J_Memory memory) {
		reporter = memory.getLog();
		trialClass_ = pTrialClass;
		memory_ = memory;
		if (reporter.isLogEnabled(TrialDescription.class)) {
			reporter.log("Creating TrialDescription for " + trialClass_);
		}
		
		runnable = checkTestClass();
	}

	public TrialDescription(I_Tests4J_CoverageTrialInstrumentation instrIn,
			I_Tests4J_Memory memory) {
		reporter = memory.getLog();
		trialClass_ = instrIn.getInstrumentedClass();
		memory_ = memory;
		if (reporter.isLogEnabled(TrialDescription.class)) {
			reporter.log("Creating TrialDescription for " + trialClass_);
		}
		
		runnable = checkTestClass();
		if (runnable) {
			if (TrialType.SourceFileTrial.equals(type)) {
				Class<?> clazz =  sourceFileScope.sourceClass();
				String className = clazz.getName();
				sourceClassDependencies = instrIn.getSourceClassDependencies();
				if (sourceClassDependencies == null) {
					throw new IllegalStateException("No know class dependencies for " + className +
							" when creating trial description for " + trialClass_.getName());
				}
			}
		}
	}
	
	private boolean checkTestClass() {
		type = TrialTypeFinder.getTypeInternal(trialClass_, failures);
		if (failures.size() >= 1) {
			return false;
		}
		if (!checkTypeAnnotations()) {
			return false;
		}
		IgnoreTrial ignoredTrial = trialClass_.getAnnotation(IgnoreTrial.class);
		if (ignoredTrial != null) {
			ignored = true;
			return false;
		}
		TrialTimeout trialTimeout = trialClass_.getAnnotation(TrialTimeout.class);
		if (trialTimeout != null) {
			timeout = trialTimeout.timeout();
		} else {
			timeout = 0;
		}
		SuppressOutput suppressOut = trialClass_.getAnnotation(SuppressOutput.class);
		if (suppressOut != null) {
			printToStdOut_ = false;
		}
		
		findTestMethods();
		findBeforeAfterTrials(trialClass_);
		if (failures.size() >= 1) {
			return false;
		}
		if (!createInstance()) {
			return false;
		}
		return true;
	}

	private boolean checkTypeAnnotations() {
		
		String trialName = trialClass_.getName();
		TrialType tt = TrialType.get(type);
		
		switch(tt) {
			case SourceFileTrial:
					deps_ = AllowedDependenciesAuditor.audit(this, failures, memory_);
					sourceFileScope = trialClass_.getAnnotation(SourceFileScope.class);
					afterTrialTestsMethod_ =
							NonTests4jMethodDiscovery.findNonTests4jMethod(trialClass_, 
									"afterTrialTests", new Class[] {I_SourceFileTrialResult.class});
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
						minCoverage_ = sourceFileScope.minCoverage();
						if (minCoverage_ > 100.0 || minCoverage_ < 0.0) {
							I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
							I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
							
							failures.add(new TrialFailure( 
									annonErrors.getMinCoverageMustBeBetweenZeroAndOneHundred(),
									trialName + annonErrors.getWasAnnotatedIncorrectly()));
							return false;
						}
						allowedCircularDependencies = 
								CircularDependencies.get(sourceFileScope.allowedCircularDependencies());
					}
				break;
			case ApiTrial:
				packageScope = trialClass_.getAnnotation(PackageScope.class);
				afterTrialTestsMethod_ =
						NonTests4jMethodDiscovery.findNonTests4jMethod(trialClass_, 
								"afterTrialTests", new Class[] {I_ApiTrialResult.class});
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
				useCaseScope = trialClass_.getAnnotation(UseCaseScope.class);
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
		Method [] dm = trialClass_.getDeclaredMethods();
		for (int i = 0; i < dm.length; i++) {
			methods.add(dm[i]);
		}
		Method [] ms = trialClass_.getMethods();
		for (int i = 0; i < ms.length; i++) {
			methods.add(ms[i]);
		}
		
		
		
		for (Method method: methods) {
			
			TestDescription testDesc = TestAuditor.audit(this, failures, method);
			if (testDesc != null) {
				testMethods.add(testDesc);
			}
			
		}

	}

	
	public void findBeforeAfterTrials(Class<?> p) {
		while (p != null && !AbstractTrial.TESTS4J_TRIAL_CLASSES.contains(p.getName())) {
			Method []  methods = p.getDeclaredMethods();
	
			for (Method method: methods) {
				if (BeforeTrialAuditor.audit(this, failures, method)) {
					if (beforeTrialMethod == null) {
						beforeTrialMethod = method;
					} else {
						I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
						I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
						
						failures.add(new TrialFailure(
								annonErrors.getMultipleBeforeTrial(),
								trialClass_.getName() + annonErrors.getWasAnnotatedIncorrectly()));
					}
				}
				
				if (AfterTrialAuditor.audit(this, failures, method)) {
					if (afterTrialMethod == null) {
						afterTrialMethod = method;
					} else {
						I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
						I_Tests4J_AnnotationErrors annonErrors = consts.getAnnotationErrors();
						
						failures.add(new TrialFailure(
								annonErrors.getMultipleAfterTrial(),
								trialClass_.getName() + annonErrors.getWasAnnotatedIncorrectly()));
					}
				}
				
			}
			if (beforeTrialMethod != null && afterTrialMethod != null) {
				break;
			} else {
				p = p.getSuperclass();
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
					trialClass_.getConstructor(new Class[] {});
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
		return trialClass_.getName();
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
		return trialClass_;
	}

	public long getTimeout() {
		return timeout;
	}

	public Method getAfterTrialTestsMethod() {
		return afterTrialTestsMethod_;
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
	
	/**
	 * this majic method will
	 * find the package of the source class
	 * or package scope setting of the trial.
	 * If there is a exact match from the coverages
	 * or a sub package of one of the coverages
	 * return it.
	 * If there is are child packages under the source class package
	 * or package scope package then add them to a aggregate result.
	 * 
	 * @param coverages
	 * @return
	 */
	public I_PackageCoverage findPackageCoverage(List<I_PackageCoverage> coverages) {
		String packageName = getPackageName();
		if (packageName == null) {
			Class<?> sourceFileClass = getSourceFileClass();
			if (sourceFileClass != null) {
				packageName = sourceFileClass.getPackage().getName();
			}
		}
		if (packageName == null) {
			throw new IllegalArgumentException("no package coverage for trial " + trialClass_.getName());
		}
		
		for (I_PackageCoverage cover: coverages) {
			String coverName = cover.getPackageName();
			if (coverName.equals(packageName)) {
				return cover;
			} else if (packageName.indexOf(coverName) == 0) {
				// ie "org.adligo.tests4j.foo".indexOf("org.adligo.tests4j") == 0
				return findPackageCoverage(cover.getChildPackageCoverage());
			} 
		}
		
		PackageCoverageMutant pcm = new PackageCoverageMutant();
		pcm.setPackageName(packageName);
		
		StringBuilder pkgs = new StringBuilder();
		for (I_PackageCoverage cover: coverages) {
			String coverName = cover.getPackageName();
			if (coverName.indexOf(packageName) == 0) {
				pcm.addCoverageUnits(cover.getCoverageUnits());
				pcm.addCoveredCoverageUnits(cover.getCoveredCoverageUnits());
				//ie org.adligo.tests4j packageName in cover name "org.adligo.tests4j.foo.bar"
				String tokenSpace = coverName.substring(packageName.length() + 1, coverName.length());
				StringTokenizer st = new StringTokenizer(tokenSpace, ".");
				String pkgName = packageName;
				PackageCoverageMutant child = null;
				while (st.hasMoreElements()) {
					
					String next = st.nextToken();
					pkgName = pkgName + "." + next;
					if (pkgName.equals(coverName)) {
						if (child == null) {
							pcm.addChild(cover);
						} else {
							child.addChild(cover);
							
						}
					} else {
						if (child == null)  {
							child = new PackageCoverageMutant();
							pcm.addChild(child);
							
						} else {
							child = new PackageCoverageMutant();
							child.addChild(child);
						}
						child.setPackageName(pkgName);
					}
				}
				
			} 
			pkgs.append(coverName);
			pkgs.append(Tests4J_System.lineSeperator());
		}
		if (pcm.getChildren().size() >= 1) {
			return new PackageCoverage(pcm);
		}
		if (sourceFileScope != null) {
			Class<?> srcClass = sourceFileScope.sourceClass();
			if (srcClass.isInterface()) {
				return new PackageCoverage();
			}
		}
		throw new IllegalArgumentException("no package coverage for trial " + trialClass_.getName() +
				" with package " + packageName + Tests4J_System.lineSeperator() + 
				" on thread "+ Thread.currentThread().getName() + " " +
				pkgs.toString());
	}




	@Override
	public String toString() {
		return "TrialDescription [trialClass=" + trialClass_ + "]";
	}

	public double getMinCoverage() {
		return minCoverage_;
	}

	public I_AbstractTrial getTrial() {
		return trial;
	}

	public List<I_TrialFailure> getFailures() {
		return Collections.unmodifiableList(failures);
	}

	public I_ClassDependenciesLocal getSourceClassDependencies() {
		return sourceClassDependencies;
	}

	public I_CircularDependencies getAllowedCircularDependencies() {
		return allowedCircularDependencies;
	}

	public boolean isPrintToStdOut() {
		return printToStdOut_;
	}

	public I_DependencyGroup getDependencyGroup() {
		return deps_;
	}

}
