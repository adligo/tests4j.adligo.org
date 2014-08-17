package org.adligo.tests4j.models.shared.system;

import org.adligo.tests4j.models.shared.xml.I_XML_Builder;

public class Tests4J_CoveragePluginParams implements I_Tests4J_CoveragePluginParams{
	private boolean canThreadLocalRecord = true;
	private boolean writeOutInstrumentedClasses = false;
	private String instrumentedClassOutputFolder = "instrumentedClasses";
	
	@Override
	public void toXml(I_XML_Builder builder) {
		throw new RuntimeException("todo");
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

}
