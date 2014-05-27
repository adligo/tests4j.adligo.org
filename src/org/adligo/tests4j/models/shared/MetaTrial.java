package org.adligo.tests4j.models.shared;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.results.I_TrialRunResult;

@TrialType(type = TrialTypeEnum.MetaTrial)
public class MetaTrial extends AbstractTrial implements I_MetaTrial {
	private double minPercentCodeCoverage;
	private double minPercentSourceFileClassesWithTrials;
	
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
	public void testMetadata(I_TrialRunMetadata metadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testResults(I_TrialRunResult results) {
		//allow to run with out coverage plugin,
		//you may want to require this for your project.
		if (results.hasCoverage()) {
			assertGreaterThanOrEquals(minPercentCodeCoverage, results.getCoveragePercentage());
		}
	}

}
