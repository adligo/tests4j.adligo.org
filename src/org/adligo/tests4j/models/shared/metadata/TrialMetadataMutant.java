package org.adligo.tests4j.models.shared.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adligo.tests4j.shared.common.I_TrialType;
import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.xml.I_XML_Builder;
import org.adligo.tests4j.shared.xml.XML_Parser;

public class TrialMetadataMutant implements I_TrialMetadata {
	public static final String READ_XML_ERROR = "Tag " + I_TrialMetadata.TAG_NAME + " not found!";
	private static final String TRIAL_METADATA_MUTANT_REQUIRES_A_NON_NULL_TYPE = "TrialMetadataMutant requires a non null type.";
	private String trialName;
	private Long timeout;
	private boolean ignored = false;
	private String beforeTrialMethodName;
	private String afterTrialMethodName;
	private List<TestMetadataMutant> tests = new ArrayList<TestMetadataMutant>();
	private I_TrialType type;
	private String testedSourceFile;
	private String testedPackage;
	private I_UseCaseBrief useCase;
	private Double minCodeCoverage;
	
	public TrialMetadataMutant() {}
	
	public TrialMetadataMutant(I_TrialMetadata p) {
		trialName = p.getTrialName();
		timeout = p.getTimeout();
		ignored = p.isIgnored();
		beforeTrialMethodName = p.getBeforeTrialMethodName();
		afterTrialMethodName = p.getAfterTrialMethodName();
		setTests(p.getTests());
		
		type = p.getType();
		if (type == null) {
			throw new IllegalArgumentException(TRIAL_METADATA_MUTANT_REQUIRES_A_NON_NULL_TYPE);
		}
		testedSourceFile = p.getTestedSourceFile();
		testedPackage = p.getTestedPackage();
		useCase = p.getUseCase();
		minCodeCoverage = p.getMinimumCodeCoverage();
	}
	
	
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getTrialName()
	 */
	@Override
	public String getTrialName() {
		return trialName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getTimeout()
	 */
	@Override
	public Long getTimeout() {
		return timeout;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#isSkipped()
	 */
	@Override
	public boolean isIgnored() {
		return ignored;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getBeforeTrialMethodName()
	 */
	@Override
	public String getBeforeTrialMethodName() {
		return beforeTrialMethodName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getAfterTrialMethodName()
	 */
	@Override
	public String getAfterTrialMethodName() {
		return afterTrialMethodName;
	}
	/* (non-Javadoc)
	 * @see org.adligo.tests4j.models.shared.metadata.I_TrialMetadata#getTests()
	 */
	@Override
	public List<I_TestMetadata> getTests() {
		return new ArrayList<I_TestMetadata>(tests);
	}
	public void setTrialName(String trialName) {
		this.trialName = trialName;
	}
	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
	public void setIgnored(boolean p) {
		this.ignored = p;
	}
	public void setBeforeTrialMethodName(String beforeTrialMethodName) {
		this.beforeTrialMethodName = beforeTrialMethodName;
	}
	public void setAfterTrialMethodName(String afterTrialMethodName) {
		this.afterTrialMethodName = afterTrialMethodName;
	}
	public void setTests(Collection<? extends I_TestMetadata> p) {
		tests.clear();
		for (I_TestMetadata test: p) {
			addTest(test);
		}
	}
	
	public void addTest(I_TestMetadata p) {
		tests.add(new TestMetadataMutant(p));
	}
	
	public int getTestCount() {
		return tests.size();
	}
	
	public int getIgnoredTestCount() {
		int toRet = 0;
		for (TestMetadataMutant test: tests) {
			if (test.isIgnored()) {
				toRet++;
			}
		}
		return toRet;
	}

	public I_TrialType getType() {
		return type;
	}

	public String getTestedSourceFile() {
		return testedSourceFile;
	}

	public String getTestedPackage() {
		return testedPackage;
	}


	public I_UseCaseBrief getUseCase() {
		return useCase;
	}

	public void setTests(List<TestMetadataMutant> tests) {
		this.tests = tests;
	}

	public void setType(I_TrialType type) {
		this.type = type;
	}

	public void setTestedSourceFile(String sourceFile) {
		this.testedSourceFile = sourceFile;
	}

	public void setTestedPackage(String testedPackage) {
		this.testedPackage = testedPackage;
	}

	public void setUseCase(I_UseCaseBrief useCase) {
		this.useCase = useCase;
	}
	
	@Override
	public void toXml(I_XML_Builder builder) {
		toXml(builder, this);
	}
	
	protected static void toXml(I_XML_Builder builder, I_TrialMetadata tm) {
		builder.indent();
		builder.addStartTag(I_TrialMetadata.TAG_NAME);
		
		if (tm.getTrialName() != null) {
			builder.addAttribute(I_TrialMetadata.TRIAL_NAME_ATTRIBUTE, tm.getTrialName());
		}
			
		I_TrialType type = tm.getType();
		if (type != null) {
			builder.addAttribute(I_TrialMetadata.TYPE_ATTRIBUTE, type.toString());
		}
		if (tm.getTimeout() != null) {
			builder.addAttribute(I_TrialMetadata.TIMEOUT_ATTRIBUTE, 
					"" + tm.getTimeout());
		}
		
		if (tm.getBeforeTrialMethodName() != null) {
			builder.addAttribute(I_TrialMetadata.BEFORE_TRIAL_METHOD_NAME_ATTRIBUTE, 
					tm.getBeforeTrialMethodName());
		}
		
		if (tm.isIgnored()) {
			builder.addAttribute(I_TrialMetadata.IGNORED_ATTRIBUTE,
					"" + tm.isIgnored());
		}
		
		if (tm.getMinimumCodeCoverage() != null) {
			builder.addAttribute(I_TrialMetadata.MIN_CODE_COVERAGE_ATTRIBUTE, 
					"" + tm.getMinimumCodeCoverage());
		}
		
		if (tm.getAfterTrialMethodName() != null) {
			builder.addAttribute(I_TrialMetadata.AFTER_TRIAL_METHOD_NAME_ATTRIBUTE, 
					tm.getAfterTrialMethodName());
		}
	
		if (tm.getTestedSourceFile() != null) {
			builder.addAttribute(I_TrialMetadata.TESTED_SOURCE_FILE_ATTRIBUTE, 
					tm.getTestedSourceFile());
		}
		if (tm.getTestedPackage() != null) {
			builder.addAttribute(I_TrialMetadata.TESTED_PACKAGE_ATTRIBUTE, 
					tm.getTestedPackage());
		}
		I_UseCaseBrief useCase = tm.getUseCase();
		List<? extends I_TestMetadata> tests = tm.getTests();
		if (useCase == null && tests.size() == 0) {
			builder.append("/>");
			builder.endLine();
		} else {
			builder.append(" >");
			builder.endLine();
			
			if (useCase != null) {
				useCase.toXml(builder);
			}
			
			
			if (tests.size() >= 1) {
				builder.indent();
				builder.addStartTag(I_TrialMetadata.TESTS_NESTED_TAG_NAME);
				builder.append(">");
				builder.endLine();
				builder.addIndent();
				for (I_TestMetadata tst: tests) {
					tst.toXml(builder);
				}
				builder.removeIndent();
				builder.indent();
				builder.addEndTag(I_TrialMetadata.TESTS_NESTED_TAG_NAME);
				builder.endLine();
			}
			builder.removeIndent();
			builder.indent();
			builder.addEndTag(I_TrialMetadata.TAG_NAME);
			builder.endLine();
		}
	}


	@Override
	public Double getMinimumCodeCoverage() {
		return minCodeCoverage;
	}
	
	public void setMinimumCodeCoverage(Double p) {
		minCodeCoverage = p;
	}
}
