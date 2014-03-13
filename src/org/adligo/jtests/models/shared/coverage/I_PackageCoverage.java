package org.adligo.jtests.models.shared.coverage;

import java.util.Map;

/**
 * @author scott
 *
 */
public interface I_PackageCoverage {
	public String getPackageName();
	public Map<String, I_ClassCoverage> getCoverage();
}
