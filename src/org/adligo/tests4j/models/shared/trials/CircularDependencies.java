package org.adligo.tests4j.models.shared.trials;

public enum CircularDependencies implements I_CircularDependencies {
	NONE_(0), INNER_CLASSES_ONLY_(1);
	public static final int NONE = 0;
	public static final int INNER_CLASSES_ONLY = 1;
	
	private int id;
	
	private CircularDependencies(int p) {
		id = p;
	}
	
	public int getId() {
		return id;
	}
	
	public static I_CircularDependencies get(int p) {
		switch(p) {
			case INNER_CLASSES_ONLY:
				return INNER_CLASSES_ONLY_;
			default:
				return NONE_;
		}
	}
	
	public static CircularDependencies get(I_CircularDependencies p) {
		switch(p.getId()) {
			case INNER_CLASSES_ONLY:
				return INNER_CLASSES_ONLY_;
			default:
				return NONE_;
		}
	}
}
