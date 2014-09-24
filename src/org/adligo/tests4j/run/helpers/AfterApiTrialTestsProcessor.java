package org.adligo.tests4j.run.helpers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;
import org.adligo.tests4j.models.shared.results.TestResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageRecorder;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.models.shared.trials.I_ApiTrial;
import org.adligo.tests4j.run.common.I_Tests4J_Memory;
import org.adligo.tests4j.run.discovery.PackageDiscovery;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

/**
 * This class should behave like a model (not thread safe)
 * 
 * @author scott
 *
 */
public class AfterApiTrialTestsProcessor extends AbstractAfterTrialTestsProcessor {
	private static final String AFTER_API_TRIAL_TESTS_METHOD =
			"afterTrialTests(I_ApiTrialResult p)";
	
	public AfterApiTrialTestsProcessor(I_Tests4J_Memory memory) {
		super(memory);
	}
	
	public TestResultMutant afterApiTrialTests(ApiTrialResultMutant trialResultMutant,
			I_Tests4J_Log log) {
		
		Method clazzMethod = null;
		List<I_PackageCoverage> coverage;
		I_AbstractTrial trial = super.getTrial();
		Class<? extends I_AbstractTrial> trialClass = trial.getClass();
		TrialDescription trialDesc = super.getTrialDescription();
		String packageName = trialDesc.getPackageName();
		
		try {
			clazzMethod = trialClass.getMethod(AFTER_TRIAL_TESTS, I_ApiTrialResult.class);
			//clazzMethod = trialClass.getDeclaredMethod(AFTER_TRIAL_TESTS, I_ApiTrialResult.class);
		} catch (NoSuchMethodException e) {
			//do nothing
		} catch (SecurityException e) {
			//do nothing
		}
		
		trialResultMutant.setRanAfterTrialTests(false);
		if (clazzMethod != null) {
			trialResultMutant.setHadAfterTrialTests(true);
		} else {
			trialResultMutant.setHadAfterTrialTests(false);
			return null;
		}
		
		I_Tests4J_CoverageRecorder rec = super.getTrialThreadLocalCoverageRecorder();
		if (rec != null) {
			try {
				PackageDiscovery pg = new PackageDiscovery(packageName);
				Set<String> classNames = new HashSet<String>();
				classNames.addAll(pg.getClassNames());
				List<PackageDiscovery> subs =  pg.getSubPackages();
				addClasses(subs, classNames);
				
				coverage = rec.endRecording(Collections.singleton(packageName));
				I_PackageCoverage cover = trialDesc.findPackageCoverage(coverage);
				if (cover != null) {
					trialResultMutant.setPackageCoverage(cover);
				}
			} catch (IOException x) {
				log.onThrowable(x);
			}
		}
		
		
		boolean passed = false;
		try {
			super.startDelegatedTest();
			trialResultMutant.setRanAfterTrialTests(true);
			ApiTrialResult result = new ApiTrialResult(trialResultMutant);
			((I_ApiTrial) trial).afterTrialTests(result);
			passed = true;
		} catch (DelegateTestAssertionFailure x) {
			//the test failed, in one of it's asserts
		} catch (Throwable x) {
			super.onDelegatedTestMethodException(x, AFTER_API_TRIAL_TESTS_METHOD);
		}
		TestResultMutant afterTrialTestsResultMutant = super.getAfterTrialTestsResultMutant();

		flushAssertionHashes(afterTrialTestsResultMutant);
		afterTrialTestsResultMutant.setAssertionCount(getAssertions());
		afterTrialTestsResultMutant.setPassed(passed);
		afterTrialTestsResultMutant.setName(AFTER_API_TRIAL_TESTS_METHOD);
		return afterTrialTestsResultMutant;
	}
	
	public void addClasses(List<PackageDiscovery> pgs, Set<String> classNames) {
		for (PackageDiscovery pg: pgs) {
			classNames.addAll(pg.getClassNames());
			List<PackageDiscovery> subs = pg.getSubPackages();
			addClasses(subs, classNames);
		}
	}
}
