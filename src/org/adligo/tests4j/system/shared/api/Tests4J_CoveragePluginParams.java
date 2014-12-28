package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.shared.xml.I_XML_Builder;

public class Tests4J_CoveragePluginParams implements I_Tests4J_CoveragePluginParams{
	private boolean canThreadLocalRecord = true;
	private boolean writeOutInstrumentedClasses = false;
	private String instrumentedClassOutputFolder = "instrumentedClasses";
	private boolean concurrentRecording = true;
	
	public Tests4J_CoveragePluginParams() {}
	
	public Tests4J_CoveragePluginParams(I_Tests4J_CoveragePluginParams pParams) {
		canThreadLocalRecord = pParams.isCanThreadLocalGroupRecord();
		writeOutInstrumentedClasses = pParams.isWriteOutInstrumentedClasses();
		instrumentedClassOutputFolder = pParams.getInstrumentedClassOutputFolder();
		concurrentRecording = pParams.isConcurrentRecording();
	}


	@Override
	public boolean isCanThreadLocalGroupRecord() {
		return canThreadLocalRecord;
	}

	@Override
	public boolean isWriteOutInstrumentedClasses() {
		return writeOutInstrumentedClasses;
	}

	@Override
	public String getInstrumentedClassOutputFolder() {
		return instrumentedClassOutputFolder;
	}

	@Override
	public boolean isConcurrentRecording() {
		return concurrentRecording;
	}

	public void setCanThreadLocalRecord(boolean canThreadLocalRecord) {
		this.canThreadLocalRecord = canThreadLocalRecord;
	}

	public void setWriteOutInstrumentedClasses(boolean writeOutInstrumentedClasses) {
		this.writeOutInstrumentedClasses = writeOutInstrumentedClasses;
	}

	public void setInstrumentedClassOutputFolder(
			String instrumentedClassOutputFolder) {
		this.instrumentedClassOutputFolder = instrumentedClassOutputFolder;
	}

	public void setConcurrentRecording(boolean concurrentRecording) {
		this.concurrentRecording = concurrentRecording;
	}

}
