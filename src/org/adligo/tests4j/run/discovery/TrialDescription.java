package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.association.I_ClassAssociationsLocal;
import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageBriefMutant;
import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;
import org.adligo.tests4j.models.shared.metadata.UseCaseBrief;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.run.common.I_Memory;
import org.adligo.tests4j.shared.asserts.reference.CircularDependencies;
import org.adligo.tests4j.shared.asserts.reference.I_CircularDependencies;
import org.adligo.tests4j.shared.asserts.reference.I_ReferenceGroup;
import org.adligo.tests4j.shared.common.DefaultSystem;
import org.adligo.tests4j.shared.common.I_System;
import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.StackTraceBuilder;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.api.I_Tests4J_CoverageTrialInstrumentation;
import org.adligo.tests4j.system.shared.trials.AbstractTrial;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.system.shared.trials.IgnoreTrial;
import org.adligo.tests4j.system.shared.trials.PackageScope;
import org.adligo.tests4j.system.shared.trials.SourceFileScope;
import org.adligo.tests4j.system.shared.trials.SuppressOutput;
import org.adligo.tests4j.system.shared.trials.TrialTimeout;
import org.adligo.tests4j.system.shared.trials.UseCaseScope;

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

/**
 * a generally immutable class that represents/wrapps
 * a Trial class adding the reflection Methods exc
 * so that it can be run.
 * 
 * @author scott
 *
 */
public class TrialDescription implements I_TrialDescription {
  private static final I_System SYS = new DefaultSystem();
	/**
	 * this may be either the non instrumented class instance
	 * or the instrumented class instance, as instances of 
	 * TrialDescription are created twice for each trial 
	 * when there is a CodeCoveragePlugin
	 */
	private I_AbstractTrial trial_;
	private Class<? extends I_AbstractTrial> trialClass_;
	
	private Method beforeTrialMethod_;
	private Method afterTrialTestsMethod_;
	private Method afterTrialMethod_;
	private final List<TestDescription> testMethods_ = new CopyOnWriteArrayList<TestDescription>();
	private I_TrialType type_;
	private boolean runnable_ = false;
	private I_CircularDependencies allowedCircularDependencies_;
	private boolean ignored_;
	private long timeout_;
	private I_Tests4J_Log log_;
	private SourceFileScope sourceFileScope_;
	private PackageScope packageScope_;
	private double minCoverage_ = 100.0;
	private List<I_TrialFailure> failures_ = new ArrayList<I_TrialFailure>();
	private I_ClassAssociationsLocal sourceClassDependencies_;
	boolean printToStdOut_ = true;
	private I_ReferenceGroup refs_;
	private I_Memory memory_;
	
	public TrialDescription(Class<? extends I_AbstractTrial> pTrialClass,
			I_Memory memory) {
		log_ = memory.getLog();
		trialClass_ = pTrialClass;
		memory_ = memory;
		if (log_.isLogEnabled(TrialDescription.class)) {
			log_.log("Creating TrialDescription for " + trialClass_);
		}
		
		runnable_ = checkTestClass();
	}

	public TrialDescription(I_Tests4J_CoverageTrialInstrumentation instrIn,
			I_Memory memory) {
		log_ = memory.getLog();
		trialClass_ = instrIn.getInstrumentedClass();
		memory_ = memory;
		if (log_.isLogEnabled(TrialDescription.class)) {
			log_.log("Creating TrialDescription for " + trialClass_);
		}
		
		runnable_ = checkTestClass();
		if (runnable_) {
			if (TrialType.SourceFileTrial.equals(type_)) {
				Class<?> clazz =  sourceFileScope_.sourceClass();
				String className = clazz.getName();
				sourceClassDependencies_ = instrIn.getSourceClassDependencies();
				if (sourceClassDependencies_ == null) {
					throw new IllegalStateException("No know class dependencies for " + className +
							" when creating trial description for " + trialClass_.getName());
				}
			}
		}
	}
	
	private boolean checkTestClass() {
		type_ = TrialTypeFinder.getTypeInternal(trialClass_, failures_);
		if (failures_.size() >= 1) {
			return false;
		}
		if (!checkTypeAnnotations()) {
			return false;
		}
		IgnoreTrial ignoredTrial = trialClass_.getAnnotation(IgnoreTrial.class);
		if (ignoredTrial != null) {
			ignored_ = true;
			return false;
		}
		TrialTimeout trialTimeout = trialClass_.getAnnotation(TrialTimeout.class);
		if (trialTimeout != null) {
			timeout_ = trialTimeout.timeout();
		} else {
			timeout_ = 0;
		}
		SuppressOutput suppressOut = trialClass_.getAnnotation(SuppressOutput.class);
		if (suppressOut != null) {
			printToStdOut_ = false;
		}
		
		findTestMethods();
		findBeforeAfterTrials(trialClass_);
		if (failures_.size() >= 1) {
			return false;
		}
		if (!createInstance()) {
			return false;
		}
		return true;
	}

	private boolean checkTypeAnnotations() {
		
		String trialName = trialClass_.getName();
		TrialType tt = TrialType.get(type_);
		
		switch(tt) {
			case SourceFileTrial:
					refs_ = AllowedDependenciesAuditor.audit(this, failures_, memory_);
					sourceFileScope_ = trialClass_.getAnnotation(SourceFileScope.class);
					afterTrialTestsMethod_ =
							NonTests4jMethodDiscovery.findNonTests4jMethod(trialClass_, 
									"afterTrialTests", new Class[] {I_SourceFileTrialResult.class});
					if (sourceFileScope_ == null) {
						I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
						I_Tests4J_AnnotationMessages annonErrors = consts.getAnnotationMessages();
						
						failures_.add(new TrialFailure(
								annonErrors.getNoSourceFileScope(),
								trialName + annonErrors.getWasAnnotatedIncorrectly()));
						return false;
					} else {
						Class<?> clazz = sourceFileScope_.sourceClass();
						if (clazz == null) {
							I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
							I_Tests4J_AnnotationMessages annonErrors = consts.getAnnotationMessages();
							
							failures_.add(new TrialFailure(
									annonErrors.getSourceFileScopeEmptyClass(),
									trialName + annonErrors.getWasAnnotatedIncorrectly()));
							return false;
						}
						minCoverage_ = sourceFileScope_.minCoverage();
						if (minCoverage_ > 100.0 || minCoverage_ < 0.0) {
							I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
							I_Tests4J_AnnotationMessages annonErrors = consts.getAnnotationMessages();
							
							failures_.add(new TrialFailure( 
									annonErrors.getMinCoverageMustBeBetweenZeroAndOneHundred(),
									trialName + annonErrors.getWasAnnotatedIncorrectly()));
							return false;
						}
						allowedCircularDependencies_ = 
								CircularDependencies.get(sourceFileScope_.allowedCircularDependencies());
					}
				break;
			case ApiTrial:
				packageScope_ = trialClass_.getAnnotation(PackageScope.class);
				afterTrialTestsMethod_ =
						NonTests4jMethodDiscovery.findNonTests4jMethod(trialClass_, 
								"afterTrialTests", new Class[] {I_ApiTrialResult.class});
				if (packageScope_ == null) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationMessages annonErrors = consts.getAnnotationMessages();
					
					failures_.add(new TrialFailure(
							annonErrors.getNoPackageScope(),
							trialName + annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				}
				String testedPackageName = packageScope_.packageName();
				if (StringMethods.isEmpty(testedPackageName)) {
					I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
					I_Tests4J_AnnotationMessages annonErrors = consts.getAnnotationMessages();
					
					failures_.add(new TrialFailure(
							annonErrors.getPackageScopeEmptyName(),
							trialName + annonErrors.getWasAnnotatedIncorrectly()));
					return false;
				} 
				
				break;
			default:
			  //UseCaseTrial
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
			
			TestDescription testDesc = TestAuditor.audit(this, failures_, method);
			if (testDesc != null) {
				testMethods_.add(testDesc);
			}
			
		}

	}

	
	public void findBeforeAfterTrials(Class<?> p) {
		while (p != null && !AbstractTrial.TESTS4J_TRIAL_CLASSES.contains(p.getName())) {
			Method []  methods = p.getDeclaredMethods();
	
			for (Method method: methods) {
				if (BeforeTrialAuditor.audit(this, failures_, method)) {
					if (beforeTrialMethod_ == null) {
						beforeTrialMethod_ = method;
					} else {
						I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
						I_Tests4J_AnnotationMessages annonErrors = consts.getAnnotationMessages();
						
						failures_.add(new TrialFailure(
								annonErrors.getMultipleBeforeTrial(),
								trialClass_.getName() + annonErrors.getWasAnnotatedIncorrectly()));
					}
				}
				
				if (AfterTrialAuditor.audit(this, failures_, method)) {
					if (afterTrialMethod_ == null) {
						afterTrialMethod_ = method;
					} else {
						I_Tests4J_Constants consts = Tests4J_Constants.CONSTANTS;
						I_Tests4J_AnnotationMessages annonErrors = consts.getAnnotationMessages();
						
						failures_.add(new TrialFailure(
								annonErrors.getMultipleAfterTrial(),
								trialClass_.getName() + annonErrors.getWasAnnotatedIncorrectly()));
					}
				}
				
			}
			if (beforeTrialMethod_ != null && afterTrialMethod_ != null) {
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
			trial_ =  (I_AbstractTrial) o;
			return true;
		} catch (Exception x) {
			failures_.add(new TrialFailure(
					Tests4J_Constants.CONSTANTS.getBadConstuctor(),
					StackTraceBuilder.toString(x, true)));
		}
		return false;
	}

	public I_TrialType getType() {
		if (type_ == null) {
			return TrialType.UnknownTrialType;
		}
		return type_;
	}
	
	/**
	 * if this trial can run.
	 * @return
	 */
	public boolean isRunnable() {
		return runnable_;
	}

	public void setRunnable(boolean trialCanRun) {
		this.runnable_ = trialCanRun;
	}

	public boolean isIgnored() {
		return ignored_;
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
		return beforeTrialMethod_;
	}

	public Method getAfterTrialMethod() {
		return afterTrialMethod_;
	}

	public int getTestMethodsSize() {
		return testMethods_.size();
	}
	
	public Iterator<TestDescription> getTestMethods() {
		return testMethods_.iterator();
	}

	public Class<? extends I_AbstractTrial> getTrialClass() {
		return trialClass_;
	}

	public long getTimeout() {
		return timeout_;
	}

	public Method getAfterTrialTestsMethod() {
		return afterTrialTestsMethod_;
	}
	
	public Class<?> getSourceFileClass() {
		if (sourceFileScope_ == null) {
			return null;
		}
		return sourceFileScope_.sourceClass();
	}
	
	/**
	 * return the packageName from the package scope if it
	 * is present, or the package from the sourceFileScope's
	 * class package if present
	 * @return
	 */
	public String getPackageName() {
		if (packageScope_ == null) {
			if (sourceFileScope_ != null) {
				Class<?> claz = sourceFileScope_.sourceClass();
				if (claz != null) {
					Package pkg = claz.getPackage();
					if (pkg != null) {
						return pkg.getName();
					}
				}
			}
			return null;
		}
		return packageScope_.packageName();
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
	public I_PackageCoverageBrief findPackageCoverage(List<I_PackageCoverageBrief> coverages) {
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
		
		for (I_PackageCoverageBrief cover: coverages) {
			String coverName = cover.getPackageName();
			if (coverName.equals(packageName)) {
				return cover;
			} else if (packageName.indexOf(coverName) == 0) {
				// ie "org.adligo.tests4j.foo".indexOf("org.adligo.tests4j") == 0
				return findPackageCoverage(cover.getChildPackageCoverage());
			} 
		}
		
		PackageCoverageBriefMutant pcm = new PackageCoverageBriefMutant();
		pcm.setPackageName(packageName);
		
		StringBuilder pkgs = new StringBuilder();
		for (I_PackageCoverageBrief cover: coverages) {
			String coverName = cover.getPackageName();
			if (coverName.indexOf(packageName) == 0) {
				pcm.addCoverageUnits(cover.getCoverageUnits());
				pcm.addCoveredCoverageUnits(cover.getCoveredCoverageUnits());
				//ie org.adligo.tests4j packageName in cover name "org.adligo.tests4j.foo.bar"
				String tokenSpace = coverName.substring(packageName.length() + 1, coverName.length());
				StringTokenizer st = new StringTokenizer(tokenSpace, ".");
				String pkgName = packageName;
				PackageCoverageBriefMutant child = null;
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
							child = new PackageCoverageBriefMutant();
							pcm.addChild(child);
							
						} else {
							child = new PackageCoverageBriefMutant();
							child.addChild(child);
						}
						child.setPackageName(pkgName);
					}
				}
				
			} 
			pkgs.append(coverName);
			pkgs.append(SYS.lineSeperator());
		}
		if (pcm.getChildren().size() >= 1) {
			return new PackageCoverageBriefMutant(pcm);
		}
		if (sourceFileScope_ != null) {
			Class<?> srcClass = sourceFileScope_.sourceClass();
			if (srcClass.isInterface()) {
				return new PackageCoverageBriefMutant();
			}
		}
		throw new IllegalArgumentException("no package coverage for trial " + trialClass_.getName() +
				" with package " + packageName + SYS.lineSeperator() + 
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
		return trial_;
	}

	public List<I_TrialFailure> getFailures() {
		return Collections.unmodifiableList(failures_);
	}

	public I_ClassAssociationsLocal getSourceClassDependencies() {
		return sourceClassDependencies_;
	}

	public I_CircularDependencies getAllowedCircularDependencies() {
		return allowedCircularDependencies_;
	}

	public boolean isPrintToStdOut() {
		return printToStdOut_;
	}

	public I_ReferenceGroup getReferenceGroupAggregate() {
		return refs_;
	}

}
