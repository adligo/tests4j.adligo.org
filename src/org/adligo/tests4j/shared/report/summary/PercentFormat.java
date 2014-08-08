package org.adligo.tests4j.shared.report.summary;

/**
 * bridge for GWT, JSE
 * @author scott
 *
 */
public class PercentFormat {
	
	public static String format(double p, int places) {
		return format((float) p, places) ;
	}
	
	public static String format(float p, int places) {
		String value = "" + p;
		int dot = value.indexOf(".");
		if (dot + places + 1 < value.length()) {
			return value.substring(0, dot + places + 1);
		} else if (dot + places > value.length()){
			int diff = dot + places - value.length();
			for (int i = 0; i < diff; i++) {
				value = value + "0";
			}
		}
		return value;
	}
}
