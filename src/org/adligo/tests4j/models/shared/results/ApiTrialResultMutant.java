package org.adligo.tests4j.models.shared.results;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;

public class ApiTrialResultMutant extends AbstractTrialResultMutant {
	private List<I_PackageCoverage> packageCoverage = new ArrayList<>();
	private String testedPackageName;
	
}
