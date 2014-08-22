package org.adligo.tests4j.run.discovery;

public class TrialStateNameIdKey implements I_TrialStateNameIdKey {
	private final String trialName;
	private final int id;
	
	public TrialStateNameIdKey(String trialNameIn, int idIn) {
		trialName = trialNameIn;
		id = idIn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((trialName == null) ? 0 : trialName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		I_TrialStateNameIdKey other = (I_TrialStateNameIdKey) obj;
		if (id != other.getId())
			return false;
		if (trialName == null) {
			if (other.getTrialName() != null)
				return false;
		} else if (!trialName.equals(other.getTrialName()))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.discovery.I_TrialStateNameIdKey#getTrialName()
	 */
	@Override
	public String getTrialName() {
		return trialName;
	}

	/* (non-Javadoc)
	 * @see org.adligo.tests4j.run.discovery.I_TrialStateNameIdKey#getId()
	 */
	@Override
	public int getId() {
		return id;
	}
}
