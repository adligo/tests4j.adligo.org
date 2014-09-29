package org.adligo.tests4j.models.shared.metadata;

public class MachineMetadataMutant implements I_MachineMetadata {
	private int threadCount = 0;
	private int availableProcessors = 0;
	private long startFreeMemory = 0L;
	private long startTotalMemory = 0L;
	private long endFreeMemory = 0L;
	private long endTotalMemory = 0L;
	private String cpuBrandName;
	/**
	 * for asserting how great a cpu is
	 * http://www.cpubenchmark.net/high_end_cpus.html
	 * intellegence4j will have a manual entry
	 * for cpus, as trying to parse the cpu
	 * speed and navigating all the proprietary cpu 
	 * technology like hyper-threading is much to 
	 * complex for this project.
	 */
	
	public static void main(String [] args) {
		new MachineMetadataMutant();
	}
	public MachineMetadataMutant() {
		//running with Execute bash 
		//sysctl -a 
		//machdep.cpu.brand_string: Intel(R) Core(TM) i7 CPU         860  @ 2.80GHz
		//machdep.cpu.family: 6
		//machdep.cpu.model: 30
		/*
		System.out.println(System.getenv("PROCESSOR_IDENTIFIER"));
		System.out.println(System.getenv("PROCESSOR_ARCHITECTURE"));
		System.out.println(System.getenv("PROCESSOR_ARCHITEW6432"));
		System.out.println(System.getenv("NUMBER_OF_PROCESSORS"));
		
		System.out.println(Runtime.getRuntime().availableProcessors());
		*/
		//Runtime.getRuntime().totalMemory();
		//Runtime.getRuntime().freeMemory()
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_ProcessorMetadata#getThreadCount()
	 */
	@Override
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_ProcessorMetadata#getAvailableProcessors()
	 */
	@Override
	public int getAvailableProcessors() {
		return availableProcessors;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_ProcessorMetadata#getStartFreeMemory()
	 */
	@Override
	public long getStartFreeMemory() {
		return startFreeMemory;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_ProcessorMetadata#getStartTotalMemory()
	 */
	@Override
	public long getStartTotalMemory() {
		return startTotalMemory;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_ProcessorMetadata#getEndFreeMemory()
	 */
	@Override
	public long getEndFreeMemory() {
		return endFreeMemory;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_ProcessorMetadata#getEndTotalMemory()
	 */
	@Override
	public long getEndTotalMemory() {
		return endTotalMemory;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_ProcessorMetadata#getCpuBrandName()
	 */
	@Override
	public String getCpuBrandName() {
		return cpuBrandName;
	}
	public void setAvailableProcessors(int availableProcessors) {
		this.availableProcessors = availableProcessors;
	}
	public void setStartFreeMemory(long startFreeMemory) {
		this.startFreeMemory = startFreeMemory;
	}
	public void setStartTotalMemory(long startTotalMemory) {
		this.startTotalMemory = startTotalMemory;
	}
	public void setEndFreeMemory(long endFreeMemory) {
		this.endFreeMemory = endFreeMemory;
	}
	public void setEndTotalMemory(long endTotalMemory) {
		this.endTotalMemory = endTotalMemory;
	}
	public void setCpuBrandName(String cpuBrandName) {
		this.cpuBrandName = cpuBrandName;
	}
	
}
