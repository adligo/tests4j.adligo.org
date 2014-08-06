package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.common.I_Immutable;

public class TrialFailure implements I_TrialFailure, I_Immutable {
	private String message;
	/**
	 * may be a stack trace
	 */
	private String detail;
	
	public TrialFailure(String pMessage, String pDetail) {
		message = pMessage;
		detail = pDetail;
	}

	public TrialFailure(I_TrialFailure p) {
		message = p.getMessage();
		detail = p.getFailureDetail();
	}
	
	public String getMessage() {
		return message;
	}


	public String getFailureDetail() {
		return detail;
	}

	@Override
	public String toString() {
		return "TrialFailure [message=" + message + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrialFailure other = (TrialFailure) obj;
		if (detail == null) {
			if (other.detail != null)
				return false;
		} else if (!detail.equals(other.detail))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		return true;
	}
	
}
