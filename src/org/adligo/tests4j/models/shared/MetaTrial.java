package org.adligo.tests4j.models.shared;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.adligo.tests4j.models.shared.common.TrialTypeEnum;
import org.adligo.tests4j.models.shared.metadata.I_SourceInfo;
import org.adligo.tests4j.models.shared.metadata.I_TrialMetadata;
import org.adligo.tests4j.models.shared.metadata.I_TrialRunMetadata;
import org.adligo.tests4j.models.shared.metadata.RelevantClassesWithTrialsCalculator;
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

	
	@Override
	public void afterMetadataCalculated(I_TrialRunMetadata pMetadata) {
		calledTestMetadata = true;
		RelevantClassesWithTrialsCalculator calc = new RelevantClassesWithTrialsCalculator(pMetadata);
		assertGreaterThanOrEquals(minPercentSourceFileClassesWithTrials, calc.getPct());
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
	public void testAftersCalled() {
		assertTrue(calledTestMetadata);
		assertTrue(calledTestResults);
	}

}
