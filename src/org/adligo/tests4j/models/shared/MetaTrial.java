package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

@TrialType(type = TrialTypeEnum.MetaTrial)
public class MetaTrial extends AbstractTrial implements I_MetaTrial {
	private double minPercentCodeCoverage;
	private double minPercentSourceFileClassesWithTrials;
	private boolean calledTestMetadata = false;
	private boolean calledTestResults = false;
	
	public MetaTrial() {}
	
	public MetaTrial(double pMinPercentCodeCoverage, double pMinPercentSourceFileClassesWithTrials) {
		minPercentCodeCoverage = pMinPercentCodeCoverage;
		minPercentSourceFileClassesWithTrials = pMinPercentSourceFileClassesWithTrials;
	}
	/**
	 * asserts the percent passed in 
	 * is less than or equal to 
	 * the actual percentage of
	 * source file classes with trials
	 * to source file classes.
	 * Lists all source file classes
	 * with out a corresponding source file trial.
	 * 
	 * @param pct
	 */
	public void assertPercentOfSourceFileClassesWithSouceFileTrials(I_TrialRunMetadata metadata,double pct) {
		
	}

	
	@Override
	public void afterMetadataCalculated(I_TrialRunMetadata metadata) {
		calledTestMetadata = true;
		
	}

	@Override
	public void afterNonMetaTrialsRun(I_TrialRunResult results) {
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
	public void testTests() {
		assertTrue(calledTestMetadata);
		assertTrue(calledTestResults);
	}

}
