package org.adligo.jtests.models.shared.coverage;

import java.util.List;

/**
 * Note I am basing the original implementaion on EclEmma's
 * ICounter and ILine in it's 
 * org.jacoco.core package
 * which seem to be what gives us the coverage
 * 
 * @author scott
 *
 */
public interface I_LineCoverage {
	public List<I_InstructionCoverage> getCoverageDetail();
	public LineStatus getStatus();
}
