package org.adligo.tests4j.models.shared.system;

public class SimpleThreadCount implements I_ThreadCount {
	private static final String XML_END = "\"/>";
	public static final String SIMPLE_THREAD_COUNT_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE = "SimpleThreadCount must be greater than or equal to one.";
	public static final String XML_START = "<SimpleThreadCount count=\"";
	private int threadCount = 32;
	
	public SimpleThreadCount() {}
	
	
	@Override
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int p) {
		if (p <= 0) {
			throw new IllegalArgumentException(
					SIMPLE_THREAD_COUNT_MUST_BE_GREATER_THAN_OR_EQUAL_TO_ONE);
		}
		threadCount = p;
	}
	@Override
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("\t" +XML_START + threadCount + XML_END);
		return sb.toString();
	}

	@Override
	public void fromXml(String xml) {
		int start = xml.indexOf(XML_START);
		int end = xml.indexOf(XML_END);
		if (start != -1 && end != -1) {
			String part = xml.substring(start + XML_START.length(), end);
			threadCount = Integer.parseInt(part);
		}
	}

}
