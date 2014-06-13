package org.adligo.tests4j.models.shared.results;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;

public class TestResultMutant implements I_TestResult {
	public static final String TEST_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_NAME = 
			"TestResultMutant requires a non empty testName";
	private String name = "";
	private int assertionCount = 0;
	private Set<Integer> uniqueAsserts = new HashSet<Integer>();
	private boolean passed = false;
	private boolean ignored = false;
	private TestFailureMutant failure;
	private String beforeOutput;
	private String output;
	private String afterOutput;
	
	public TestResultMutant() {}
	
	public TestResultMutant(I_TestResult p) {
		name = p.getName();
		StringMethods.isEmpty(name,
				TEST_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_NAME);
		assertionCount = p.getAssertionCount();
		uniqueAsserts.addAll(p.getUniqueAsserts());
		
		passed = p.isPassed();
		ignored = p.isIgnored();
		I_TestFailure pFailure = p.getFailure();
		if (pFailure != null) {
			failure = new TestFailureMutant(pFailure);
		}
		beforeOutput = p.getBeforeOutput();
		output = p.getOutput();
		afterOutput = p.getAfterOutput();
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getExhibitName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getAssertionCount()
	 */
	@Override
	public int getAssertionCount() {
		return assertionCount;
	}
	
	public void incrementAssertionCount(int assertionHash) {
		assertionCount++;
		uniqueAsserts.add(assertionHash);
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getUniqueAsserts()
	 */
	@Override
	public Set<Integer> getUniqueAsserts() {
		return uniqueAsserts;
	}
	
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getUniqueAsserts()
	 */
	@Override
	public int getUniqueAssertionCount() {
		return uniqueAsserts.size();
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#isPassed()
	 */
	@Override
	public boolean isPassed() {
		return passed;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#isIgnored()
	 */
	@Override
	public boolean isIgnored() {
		return ignored;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getFailure()
	 */
	@Override
	public TestFailureMutant getFailure() {
		return failure;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getBeforeOutput()
	 */
	@Override
	public String getBeforeOutput() {
		return beforeOutput;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getOutput()
	 */
	@Override
	public String getOutput() {
		return output;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_TestResult#getAfterOutput()
	 */
	@Override
	public String getAfterOutput() {
		return afterOutput;
	}
	public void setName(String p) {
		this.name = p;
	}
	public void setAssertionCount(int assertionCount) {
		this.assertionCount = assertionCount;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	public void setIgnored(boolean ignored) {
		this.ignored = ignored;
	}
	public void setFailure(I_TestFailure p) {
		this.failure = new TestFailureMutant(p);
	}
	public void setBeforeOutput(String beforeOutput) {
		this.beforeOutput = beforeOutput;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public void setAfterOutput(String afterOutput) {
		this.afterOutput = afterOutput;
	}
	
	@Override
	public String toString() {
		return toString(this);
	}
	
	public static String toString(I_TestResult result) {
		return result.getClass().getSimpleName() + " [name=" +
				result.getName() + ", passed=" + 
				result.isPassed() + ", ignored=" + 
				result.isIgnored() + "]";
	}
}
