package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.shared.common.I_TrialType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrialMetadataMutant implements I_TrialMetadata {
	private static final String TRIAL_METADATA_MUTANT_REQUIRES_A_NON_NULL_TYPE = "TrialMetadataMutant requires a non null type.";
	private String trialName;
	private Long timeout;
	private boolean ignored = false;
	private String beforeTrialMethodName;
	private String afterTrialMethodName;
	private List<TestMetadataMutant> tests = new ArrayList<TestMetadataMutant>();
	private I_TrialType type;
	private String testedSourceFile;
	private String testedPackage;
	private Double minCodeCoverage;
	
	public TrialMetadataMutant() {}
	
	public TrialMetadataMutant(I_TrialMetadata p) {
		trialName = p.getTrialName();
		timeout = p.getTimeout();
		ignored = p.isIgnored();
		beforeTrialMethodName = p.getBeforeTrialMethodName();
		afterTrialMethodName = p.getAfterTrialMethodName();
		setTests(p.getTests());
		
		type = p.getType();
		if (type == null) {
			throw new IllegalArgumentException(TRIAL_METADATA_MUTANT_REQUIRES_A_NON_NULL_TYPE);
		}
		testedSourceFile = p.getTestedSourceFile();
		testedPackage = p.getTestedPackage();
		minCodeCoverage = p.getMinimumCodeCoverage();
	}
	
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getTrialName()
	 */
	@Override
	public String getTrialName() {
		return trialName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getTimeout()
	 */
	@Override
	public Long getTimeout() {
		return timeout;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#isSkipped()
	 */
	@Override
	public boolean isIgnored() {
		return ignored;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getBeforeTrialMethodName()
	 */
	@Override
	public String getBeforeTrialMethodName() {
		return beforeTrialMethodName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getAfterTrialMethodName()
	 */
	@Override
	public String getAfterTrialMethodName() {
		return afterTrialMethodName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getTests()
	 */
	@Override
	public List<I_TestMetadata> getTests() {
		return new ArrayList<I_TestMetadata>(tests);
	}
	public void setTrialName(String trialName) {
		this.trialName = trialName;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	public void setIgnored(boolean p) {
		this.ignored = p;
	}
	public void setBeforeTrialMethodName(String beforeTrialMethodName) {
		this.beforeTrialMethodName = beforeTrialMethodName;
	}
	public void setAfterTrialMethodName(String afterTrialMethodName) {
		this.afterTrialMethodName = afterTrialMethodName;
	}
	public void setTests(Collection<? extends I_TestMetadata> p) {
		tests.clear();
		for (I_TestMetadata test: p) {
			addTest(test);
		}
	}
	
	public void addTest(I_TestMetadata p) {
		tests.add(new TestMetadataMutant(p));
	}
	
	public int getTestCount() {
		return tests.size();
	}
	
	public int getIgnoredTestCount() {
		int toRet = 0;
		for (TestMetadataMutant test: tests) {
			if (test.isIgnored()) {
				toRet++;
			}
		}
		return toRet;
	}

	public I_TrialType getType() {
		return type;
	}

	public String getTestedSourceFile() {
		return testedSourceFile;
	}

	public String getTestedPackage() {
		return testedPackage;
	}

	public void setTests(List<TestMetadataMutant> tests) {
		this.tests = tests;
	}

	public void setType(I_TrialType type) {
		this.type = type;
	}

	public void setTestedSourceFile(String sourceFile) {
		this.testedSourceFile = sourceFile;
	}

	public void setTestedPackage(String testedPackage) {
		this.testedPackage = testedPackage;
	}
	
	@Override
	public Double getMinimumCodeCoverage() {
		return minCodeCoverage;
	}
	
	public void setMinimumCodeCoverage(Double p) {
		minCodeCoverage = p;
	}
}
