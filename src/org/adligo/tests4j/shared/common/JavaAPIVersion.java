package org.adligo.tests4j.shared.common;

/**
 * this class parses the jdk version from
 * System.getProperties so that you can have 
 * detailed info about the version
 * 
 * Written for jse, but hopefully can reuse for 
 * other apis.
 * @author scott
 *
 */
public class JavaAPIVersion {
	private static final String NUMBERS = "0123456789";
	private int major = 0;
	private int minor = 0;
	/** 
	 * TODO what is the last .X_ number in the version,
	 * has it ever been used?
	 */
	private int minorSpecification = 0;
	private int build = 0;
	
	/**
	 * Should be fairly safe and return a 0,0,0,0 instance
	 * for junk input
	 * @param systemGetPropertyVersionDotProperties
	 */
	public JavaAPIVersion(String systemGetPropertyVersionDotProperties) {
		Integer maj = null;
		Integer min = null;
		Integer spec = null;
		Integer bld = null;
		StringBuilder sb = new StringBuilder();
		
		char [] chars = systemGetPropertyVersionDotProperties.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (NUMBERS.indexOf(c) == -1) {
				if (maj == null) {
					maj = new Integer(sb.toString());
					major = maj.intValue();
				} else if (min == null) {
					min = new Integer(sb.toString());
					minor = min.intValue();
				} else if (spec == null) {
					spec = new Integer(sb.toString());
					minorSpecification = spec.intValue();
				} else if (bld == null) {
					bld = new Integer(sb.toString());
					build = bld.intValue();
				}
				sb = new StringBuilder();
			} else {
				sb.append(c);
			}
			
		}
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getMinorSpecification() {
		return minorSpecification;
	}

	public int getBuild() {
		return build;
	}
	
	/**
	 * if the paramter is greater than this,
	 * written so that it may ignore minor version numbers after 
	 * the second integer X.X!
	 * @param v
	 * @return
	 */
	public boolean isAbove(JavaAPIVersion v) {
		if (major > v.getMajor()) {
			return false;
		} else if (major == v.getMajor()) {
			if (minor > v.getMinor()) {
				return false;
			} else if (minor == v.getMinor()) {
				if (minorSpecification > v.getMinorSpecification()) {
					return false;
				} else if (minorSpecification == v.getMinorSpecification()) {
					if (v.getBuild() != 0) {
						if (build > v.getBuild()) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
