package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TrialMetadataMutant implements I_TrialMetadata {
	private String trialName;
	private Long timeout;
	private boolean skipped = false;
	private String beforeTrialMethodName;
	private String afterTrialMethodName;
	private List<TestMetadataMutant> tests = new ArrayList<TestMetadataMutant>();
	
	public TrialMetadataMutant() {}
	
	public TrialMetadataMutant(I_TrialMetadata p) {
		trialName = p.getTrialName();
		timeout = p.getTimeout();
		skipped = p.isSkipped();
		beforeTrialMethodName = p.getBeforeTrialMethodName();
		afterTrialMethodName = p.getAfterTrialMethodName();
		setTests(p.getTests());
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
	public boolean isSkipped() {
		return skipped;
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
	public List<? extends I_TestMetadata> getTests() {
		return tests;
	}
	public void setTrialName(String trialName) {
		this.trialName = trialName;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	public void setSkipped(boolean skipped) {
		this.skipped = skipped;
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
			tests.add(new TestMetadataMutant(test));
		}
	}
	
	public int getTestCount() {
		return tests.size();
	}
	
	public int getSkippedTestCount() {
		int toRet = 0;
		for (TestMetadataMutant test: tests) {
			if (test.isSkipped()) {
				toRet++;
			}
		}
		return toRet;
	}
}