package org.adligo.tests4j.models.shared.results;

/**
 * @see I_UseCase
 * @author scott
 *
 */
public class UseCase implements I_UseCase {
	private String nown;
	private String verb;
	
	public UseCase(String pNown, String pVerb) {
		nown = pNown;
		verb = pVerb;
		if (pNown == null || pVerb == null) {
			throw new IllegalArgumentException();
		}
	}

	public UseCase(I_UseCase p) {
		this(p.getNown(), p.getVerb());
	}
	
	public String getNown() {
		return nown;
	}

	public String getVerb() {
		return verb;
	}
}
