package org.adligo.tests4j.models.shared.trials;


/**
 * a common way to notify the 
 * console monitor of progress of a 
 * test
 * @author scott
 *
 */
public class SubProgress implements I_SubProgress {
	private String name_;
	private double pctDone_;
	private I_SubProgress sub_;
	
	/**
	 * recursive copy,
	 * for test of input to AbstractTrial
	 * @param sub
	 */
	public SubProgress(I_SubProgress sub) {
		this(sub.getName(), sub.getPctDone(), new SubProgress(sub.getSub()));
	}
	
	public SubProgress(String name, double pctDone, I_SubProgress sub) {
		name_ = name;
		pctDone_ = pctDone;
		sub_ = sub;
	}
	
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.system.I_Tests4J_SubProgress#getName()
	 */
	@Override
	public String getName() {
		return name_;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.system.I_Tests4J_SubProgress#getPctDone()
	 */
	@Override
	public double getPctDone() {
		return pctDone_;
	}


	@Override
	public I_SubProgress getSub() {
		return sub_;
	}
}
