package org.adligo.jtests.models.shared.results;

import java.util.HashSet;
import java.util.Set;

import org.adligo.jtests.models.shared.common.IsEmpty;

public class ExhibitResultMutant implements I_ExhibitResult {
	public static final String EXHIBIT_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_EXHIBIT_NAME = "ExhibitResultMutant requires a non empty exhibitName";
	private String exhibitName = "";
	private int assertionCount = 0;
	private Set<Integer> uniqueAsserts = new HashSet<Integer>();
	private boolean passed = false;
	private boolean ignored = false;
	private TestFailureMutant failure;
	private String beforeOutput;
	private String output;
	private String afterOutput;
	
	public ExhibitResultMutant() {}
	
	public ExhibitResultMutant(I_ExhibitResult p) {
		exhibitName = p.getExhibitName();
		IsEmpty.isEmpty(exhibitName,
				EXHIBIT_RESULT_MUTANT_REQUIRES_A_NON_EMPTY_EXHIBIT_NAME);
		assertionCount = p.getAssertionCount();
		try {
			uniqueAsserts.clear();
			uniqueAsserts.addAll(((ExhibitResultMutant) p).uniqueAsserts);
		} catch (ClassCastException x) {
			//do nothing this is a instance of for GWT
		}
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
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#getExhibitName()
	 */
	@Override
	public String getExhibitName() {
		return exhibitName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#getAssertionCount()
	 */
	@Override
	public int getAssertionCount() {
		return assertionCount;
	}
	
	public void incrementAssertionCount() {
		assertionCount++;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#getUniqueAsserts()
	 */
	@Override
	public int getUniqueAsserts() {
		return uniqueAsserts.size();
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#isPassed()
	 */
	@Override
	public boolean isPassed() {
		return passed;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#isIgnored()
	 */
	@Override
	public boolean isIgnored() {
		return ignored;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#getFailure()
	 */
	@Override
	public TestFailureMutant getFailure() {
		return failure;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#getBeforeOutput()
	 */
	@Override
	public String getBeforeOutput() {
		return beforeOutput;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#getOutput()
	 */
	@Override
	public String getOutput() {
		return output;
	}
	/* (non-Javadoc)
	 * @see org.adligo.jtests.base.shared.results.I_ExhibitResult#getAfterOutput()
	 */
	@Override
	public String getAfterOutput() {
		return afterOutput;
	}
	public void setExhibitName(String exhibitName) {
		this.exhibitName = exhibitName;
	}
	public void setAssertionCount(int assertionCount) {
		this.assertionCount = assertionCount;
	}
	public void addUniqueAsserts(int p) {
		uniqueAsserts.add(p);
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
	
}
