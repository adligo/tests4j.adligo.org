package org.adligo.tests4j.models.shared.metadata;

public interface I_MachineMetadata {

	public abstract int getThreadCount();

	public abstract int getAvailableProcessors();

	public abstract long getStartFreeMemory();

	public abstract long getStartTotalMemory();

	public abstract long getEndFreeMemory();

	public abstract long getEndTotalMemory();

	public abstract String getCpuBrandName();

}