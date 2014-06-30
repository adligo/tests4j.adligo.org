package org.adligo.tests4j.models.shared.trials;

import java.io.IOException;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.RelevantClassesWithTrialsCalculator;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

@TrialType(type = TrialTypeEnum.MetaTrial)
public class MetaTrial extends AbstractTrial implements I_MetaTrial {
	private RelevantClassesWithTrialsCalculator calculator;
	private double minPercentCodeCoverage;
	private double minPercentSourceFileClassesWithTrials;
	private boolean calledTestMetadata = false;
	private boolean calledTestResults = false;
	
	public MetaTrial() {}
	
	public MetaTrial(double pMinPercentCodeCoverage, double pMinPercentSourceFileClassesWithTrials) {
		minPercentCodeCoverage = pMinPercentCodeCoverage;
		minPercentSourceFileClassesWithTrials = pMinPercentSourceFileClassesWithTrials;
	}

	
	@Override
	public void afterMetadataCalculated(I_TrialRunMetadata pMetadata) throws Exception {
		calculator = new RelevantClassesWithTrialsCalculator(pMetadata);
		calledTestMetadata = true;
		RelevantClassesWithTrialsCalculator calc = new RelevantClassesWithTrialsCalculator(pMetadata);
		assertGreaterThanOrEquals(minPercentSourceFileClassesWithTrials, calc.getPct());
	}

	public void assertPackageTrialsPassed(I_TrialRunResult results, String packageName, int trials) throws IOException {
		Set<String> passingTrials = results.getPassingTrials();
		Set<String> trialNames = calculator.getSourceFileTrialNames(packageName);
		assertGreaterThanOrEquals(trials, trialNames.size());
		for (String trialName: trialNames) {
			assertTrue("The passing trials should include " + trialName, 
					passingTrials.contains(trialName));
		}
	}
	
	@Override
	public void afterNonMetaTrialsRun(I_TrialRunResult results) throws Exception {
		calledTestResults = true;
		//allow to run with out coverage plugin,
		//you may want to require this for your project.
		if (results.hasCoverage()) {
			double actual = results.getCoveragePercentage();
			assertGreaterThanOrEquals(minPercentCodeCoverage, actual);
			
			
		}
	}
	
	/**
	 * this method was mostly added
	 * so that this class had a @Test method
	 */
	@Test
	public void testAftersCalled() {
		assertTrue(calledTestMetadata);
		assertTrue(calledTestResults);
	}

	public RelevantClassesWithTrialsCalculator getCalculator() {
		return calculator;
	}
}
