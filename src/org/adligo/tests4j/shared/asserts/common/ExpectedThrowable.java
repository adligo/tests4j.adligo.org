package org.adligo.tests4j.shared.asserts.common;


/**
 * a immutable class to represent
 * when the author of a test expects 
 * a Throwable to be thrown.
 * Note this class should never throw a Exception from the 
 * constructor, validation is handled by ExpectedThrownDataValidator.
 * 
 * @author scott
 *
 */
public class ExpectedThrowable implements I_ExpectedThrowable {
	private String message_;
	private I_MatchType matchType_;
	private Class<? extends Throwable> throwableClass_;
	private Throwable instance_;
	private ExpectedThrowable expectedCause_;
	
	public ExpectedThrowable(I_ExpectedThrowable p) {
		Throwable inst = p.getInstance();
		matchType_ = p.getMatchType();
		if (inst == null) {
			setupThrowableClass(p.getThrowableClass());
		} else {
			setupInstance(inst);
		}
	}
	
	/**
	 * This constructor should be used for matching with 
	 * Throwable's that have any message. 
	 * @param clazz 
	 */
	public ExpectedThrowable(Class<? extends Throwable> clazz) {
	  this(clazz, MatchType.ANY);
	}

	/**
	 * 
	 * @param clazz the throwable class.
	 * @param matchType the message matching type.
	 */
	public ExpectedThrowable(Class<? extends Throwable> clazz, MatchType matchType) {
	  if (matchType == null) {
	    matchType_ = MatchType.ANY;
	  } else {
	    matchType_ = matchType;
	  }
    setupThrowableClass(clazz);
  }
	
	/**
   * This constructor should be used for matching with 
   * Throwable's that have any message. 
   * @param clazz 
   * @param the 
   */
	public ExpectedThrowable(Class<? extends Throwable> clazz, I_ExpectedThrowable expectedCause) {
	  this(clazz, MatchType.ANY, expectedCause);
	}
	
	public ExpectedThrowable(Class<? extends Throwable> clazz, MatchType matchType, I_ExpectedThrowable expectedCause) {
	  if (matchType == null) {
      matchType_ = MatchType.ANY;
    } else {
      matchType_ = matchType;
    }
    setupThrowableClass(clazz);
    setupCausationChain(expectedCause);
  }
	
	
	
	/**
   * This constructor should be used for matching
   * the Throwable class and message.
   * @param clazz
   */
	public ExpectedThrowable(Throwable t) {
	  this(t, MatchType.EQUALS);
	}
	
	 /**
   * This constructor should be used for matching
   * the Throwable class and message.
   * @param clazz
   */
  public ExpectedThrowable(Throwable t, MatchType matchType) {
    if (matchType == null) {
      matchType_ = MatchType.EQUALS;
    } else {
      matchType_ = matchType;
    }
    setupInstance(t);
  }
  
	/**
   * This constructor should be used for matching
   * the Throwable class and message.
   * @param clazz
   */
	public ExpectedThrowable(Throwable t, I_ExpectedThrowable p) {
	  this(t, MatchType.EQUALS, p);
	}

	 /**
   * This constructor should be used for matching
   * the Throwable class and message.
   * @param clazz
   */
  public ExpectedThrowable(Throwable t, MatchType matchType, I_ExpectedThrowable p) {
    if (matchType == null) {
      matchType_ = MatchType.EQUALS;
    } else {
      matchType_ = matchType;
    }
    setupInstance(t);
    setupCausationChain(p);
  }

	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ExpectedThrownData#getMessage()
	 */
	@Override
	public String getMessage() {
		return message_;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.asserts.I_ExpectedThrownData#getThrowableClass()
	 */
	@Override
	public Class<? extends Throwable> getThrowableClass() {
		return throwableClass_;
	}

	@Override
	public Throwable getInstance() {
		return instance_;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((message_ == null) ? 0 : message_.hashCode());
		result = prime * result + matchType_.hashCode();
		result = prime * result
				+ ((throwableClass_ == null) ? 0 : throwableClass_.getName().hashCode());
		result = prime * result
				+ ((expectedCause_ == null) ? 0 : expectedCause_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_ExpectedThrowable other = (I_ExpectedThrowable) obj;
			if (message_ == null) {
				if (other.getMessage() != null)
					return false;
			} else if (!message_.equals(other.getMessage()))
				return false;
			if (!matchType_.equals(other.getMatchType())) {
			  return false;
			}
			if (throwableClass_ == null) {
				if (other.getThrowableClass() != null)
					return false;
			} else if (!throwableClass_.equals(other.getThrowableClass()))
				return false;
			if (expectedCause_ == null) {
				if (other.getExpectedCause() != null)
					return false;
			} else if (!expectedCause_.equals(other.getExpectedCause()))
				return false;
		} catch (ClassCastException x) {
			//do nothing 
		}
		return true;
	}

	@Override
	public I_ExpectedThrowable getExpectedCause() {
		return expectedCause_;
	}

	@Override
  public I_MatchType getMatchType() {
    return matchType_;
  }
	
	protected void setupCausationChain(I_ExpectedThrowable p) {
    //expect non null input
    expectedCause_ = new ExpectedThrowable(p);
    I_ExpectedThrowable dec = p.getExpectedCause();
    if (dec != null) {
      expectedCause_.expectedCause_ = new ExpectedThrowable(dec);
    }
	}
	  
	private void setupInstance(Throwable t) {
    instance_ = t;
    throwableClass_ = t.getClass();
    message_ = t.getMessage();
  }
	  
	private void setupThrowableClass(Class<? extends Throwable> clazz) {
   throwableClass_ = clazz;
  }
}
