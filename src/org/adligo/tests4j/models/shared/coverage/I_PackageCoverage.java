package org.adligo.tests4j.models.shared.coverage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


/**
 * A query style interface to represent
 * coverage of java source files at the package level.
 * Note this is not implemented in this project,
 * but is intended to be implemented by the 
 * coverage plugin projects (see tests4j_4jacoco).
 * 
 * Note the {@link I_CoverageUnits} implementation
 * does not include information from child package coverage.
 * 
 * @author scott
 *
 */
public interface I_PackageCoverage extends I_CoverageUnitsContainer {
	/**
	 * the java package name, of the package
	 * this instance represents coverage for.
	 * @return
	 */
	public String getPackageName();
	/**
	 * @see I_SourceFileCoverage
	 * 
	 * @param sourceFileName
	 * @return
	 */
	public I_SourceFileCoverage getCoverage(String sourceFileName);
	/**
	 * return the classes directly in this package
	 * which pertain to a .java file,
	 * regardless of if they were covered at all.
	 * @return
	 */
	public Set<String> getSourceFileNames();
	
	/**
	 * return the child packages if any
	 * or a empty list 
	 * regardless of if they were covered at all.
	 * @return
	 */
	public List<I_PackageCoverage> getChildPackageCoverage();
	
	/**
	 * a convenience method for determining if there
	 * are child packages.
	 * @return
	 */
	public boolean hasChildPackageCoverage();
}
