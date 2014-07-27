package org.adligo.tests4j.run.discovery;

public interface I_Dependency extends Comparable<I_Dependency> {

	public abstract String getClazzName();

	public abstract int getReferences();

}