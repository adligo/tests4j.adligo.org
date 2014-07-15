package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.models.shared.xml.I_XML_Builder;
import org.adligo.tests4j.models.shared.xml.XML_Parser;

public class TestMetadataMutant implements I_TestMetadata {
	public static final String READ_XML_ERROR = "Tag " + I_TestMetadata.TAG_NAME + " not found!";
	private String testName;
	private Long timeout;
	private boolean ignored = false;
	
	public TestMetadataMutant() {}
	
	public TestMetadataMutant(I_TestMetadata p) {
		testName = p.getTestName();
		timeout = p.getTimeout();
		ignored = p.isIgnored();
	}
	public TestMetadataMutant(String xml) {
		int [] indexes = XML_Parser.getIndexes(xml, I_TestMetadata.TAG_NAME);
		if (indexes == null) {
			throw new IllegalArgumentException(READ_XML_ERROR);
		}
		String thisTag = xml.substring(indexes[0], indexes[1]);
		testName = XML_Parser.getAttributeValue(thisTag, I_TestMetadata.NAME_ATTRIBUTE);
		
		String timeOutString = XML_Parser.getAttributeValue(thisTag, I_TestMetadata.TIMEOUT_ATTRIBUTE);;
		if (timeOutString != null) {
			timeout = Long.parseLong(timeOutString);
		}
		String ignoredString = XML_Parser.getAttributeValue(thisTag, I_TestMetadata.IGNORED_ATTRIBUTE);
		if (ignoredString != null) {
			ignored = Boolean.parseBoolean(ignoredString);
		}
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#getTestName()
	 */
	@Override
	public String getTestName() {
		return testName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#getTimeout()
	 */
	@Override
	public Long getTimeout() {
		return timeout;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TestMetadata#isIgnored()
	 */
	@Override
	public boolean isIgnored() {
		return ignored;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	public void setIgnored(boolean skipped) {
		this.ignored = skipped;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((testName == null) ? 0 : testName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		I_TestMetadata other = (I_TestMetadata) obj;
		if (testName == null) {
			if (other.getTestName() != null)
				return false;
		} else if (!testName.equals(other.getTestName()))
			return false;
		
		return true;
	}

	@Override
	public void toXml(I_XML_Builder builder) {
		builder.indent();
		builder.addStartTag(I_TestMetadata.TAG_NAME);
		builder.addAttribute(I_TestMetadata.NAME_ATTRIBUTE, testName);
		if (ignored) {
			builder.addAttribute(I_TestMetadata.IGNORED_ATTRIBUTE,"" + ignored);
		}
		if (timeout != null) {
			builder.addAttribute(I_TestMetadata.TIMEOUT_ATTRIBUTE,"" + timeout);
		}
		builder.append(" />");
		builder.endLine();
	}
	
}
