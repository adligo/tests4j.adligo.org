package org.adligo.tests4j.models.shared.system;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.trials.I_MetaTrial;
import org.adligo.tests4j.models.shared.trials.I_Trial;
import org.adligo.tests4j.models.shared.xml.I_XML_Builder;


public class Tests4J_Params implements I_Tests4J_Params {
	public static final String META_TRIAL_XML_END = "</meta_trial>";
	public static final String META_TRIAL_XML_START = "<meta_trial>";
	public static final String TRIAL_XML_END = "</trial>";
	public static final String TRIAL_XML_START = "<trial>";
	public static final String TRIALS_XML_END = "</trials>";
	public static final String TRIALS_XML_START = "<trials>";
	public static final String COVERAGE_PLUGIN_XML_KEY = " coveragePluginFactory=\"";
	public static final String THREAD_COUNT_XML_KEY = "threadCount=\"";
	public static final String TEST_XML_END = "</test>";
	public static final String TEST_XML_START = "<test>";
	public static final String TESTS_XML_START = "<tests>";
	public static final String TESTS_XML_END = "</tests>";
	public static final String LOGS_XML_START = "<logs>";
	public static final String LOG_XML_START = "<log>";
	public static final String LOG_XML_END = "</log>";
	public static final String LOGS_XML_END = "</logs>";
	public static final String XML_END = "</Tests4J_Params>";
	public static final String XML_START = "<Tests4J_Params ";
	
	/**
	 * @see I_Tests4J_Params#getTrials()
	 */
	private List<Class<? extends I_Trial>> trials = 
				new ArrayList<Class<? extends I_Trial>>();
	/**
	 * @see I_Tests4J_Params#getMetaTrialClass()
	 */
	private Class<? extends I_MetaTrial>  metaTrialClass;
	
	private Set<String> tests = new HashSet<String>();
	
	private Integer recommendedTrialThreadCount;
	
	/**
	 * All coverage is always recorded if there is a plugin,
	 * more fine grained coverage may be recorded if the 
	 * plugin supports it and recordTrialCoverage
	 * is null or true, and if recordTestCoverage is 
	 * null or true.
	 */
	private Class<? extends I_CoveragePluginFactory> coveragePluginFactoryClass;
	/**
	 * these classes get reporting turned on 
	 */
	private List<Class<?>> loggingClasses = new ArrayList<Class<?>>();
	private Map<I_Tests4J_RemoteInfo, I_Tests4J_Params> remoteParams = 
			new HashMap<I_Tests4J_RemoteInfo, I_Tests4J_Params>();
			
	private I_EvaluatorLookup evaluatorLookup = EvaluatorLookup.DEFAULT_LOOKUP;
	
	public Tests4J_Params() {}
	
	public Tests4J_Params(I_Tests4J_Params p) {
		trials.addAll(p.getTrials());
		metaTrialClass = p.getMetaTrialClass();
		tests.addAll(p.getTests());
		coveragePluginFactoryClass = p.getCoveragePluginFactoryClass();
		recommendedTrialThreadCount = p.getRecommendedTrialThreadCount();
		loggingClasses.addAll(p.getLoggingClasses());
		Collection<I_Tests4J_RemoteInfo> remotes = p.getRemoteInfo();
		for (I_Tests4J_RemoteInfo remote: remotes){
			remoteParams.put(remote, p.getRemoteParams(remote));
		}
	}
	
	public Tests4J_Params(String p) {
		fromXml(p);
	}

	public void fromXml(String p) {
		/** TODO
		int start =  p.indexOf(XML_START);
		int tagEnd = p.indexOf(">", start);
		String tag = p.substring(start, tagEnd);
		
		int threadCountIndex = tag.indexOf(THREAD_COUNT_XML_KEY);
		if (threadCountIndex != -1) {
			threadCountIndex = threadCountIndex + THREAD_COUNT_XML_KEY.length();
			int end = tag.indexOf("\"", threadCountIndex);
			String clazz = tag.substring(threadCountIndex, end);
			
		}
		
		int coveragePluginIndex = tag.indexOf(COVERAGE_PLUGIN_XML_KEY);
		if (coveragePluginIndex != -1) {
			coveragePluginIndex = coveragePluginIndex + COVERAGE_PLUGIN_XML_KEY.length();
			int end = tag.indexOf("\"", coveragePluginIndex);
			String clazz = tag.substring(coveragePluginIndex,end);
			
		}
		int metaTrialStart = p.indexOf(META_TRIAL_XML_START);
		int metaTrialEnd = p.indexOf(META_TRIAL_XML_END);
		if (metaTrialStart != -1 && metaTrialEnd != -1) {
			String metaTrial = p.substring(metaTrialStart + META_TRIAL_XML_START.length(),
					metaTrialEnd);
			try {
				Class<? extends I_MetaTrial> trialClazz = (Class<? extends I_MetaTrial>) Class.forName(metaTrial);
				metaTrialClass = trialClazz;
			} catch (ClassNotFoundException x) {
				throw new IllegalArgumentException(x);
			}
		}
		
		int trialsStart = p.indexOf(TRIALS_XML_START);
		int trialsEnd = p.indexOf(TRIALS_XML_END);
		if (trialsStart != -1 && trialsEnd != -1) {
			String trialsPart = p.substring(trialsStart, trialsEnd);
			int trialIndex = trialsPart.indexOf(TRIAL_XML_START);
			while (trialIndex != -1) {
				int trialEnd = trialsPart.indexOf(TRIAL_XML_END, trialIndex);
				String trial = trialsPart.substring(trialIndex + TRIAL_XML_START.length(),
						trialEnd);
				try {
					Class<? extends I_Trial> trialClazz = (Class<? extends I_Trial>) Class.forName(trial);
					trials.add(trialClazz);
				} catch (ClassNotFoundException x) {
					throw new IllegalArgumentException(x);
				}
				trialsPart = trialsPart.substring(trialEnd, trialsPart.length());
				trialIndex = trialsPart.indexOf(TRIAL_XML_START);
			}
		}
		
		int testsStart = p.indexOf(TESTS_XML_START);
		int testsEnd = p.indexOf(TESTS_XML_END);
		if (testsStart != -1 && testsEnd != -1) {
			String testsPart = p.substring(testsStart, testsEnd);
			int testIndex = testsPart.indexOf(TEST_XML_START);
			while (testIndex != -1) {
				int testEnd = testsPart.indexOf(TEST_XML_END, testIndex);
				String test = testsPart.substring(testIndex + TRIAL_XML_START.length(),
						testEnd);
				tests.add(test);
				testsPart = testsPart.substring(testEnd, testsPart.length());
				testIndex = testsPart.indexOf(TEST_XML_START);
			}
		}
		*/
	}
	
	public List<Class<? extends I_Trial>> getTrials() {
		return trials;
	}
	public void setTrials(List<Class<? extends I_Trial>> p) {
		trials.clear();
		trials.addAll(p);
	}
	public void addTrial(Class<? extends I_Trial> p) {
		trials.add(p);
	}
	
	public Class<? extends I_CoveragePluginFactory> getCoveragePluginFactory() {
		return coveragePluginFactoryClass;
	}
	

	
	public void addTrials(I_TrialList p) {
		trials.addAll(p.getTrials());
	}

	public Set<String> getTests() {
		return tests;
	}

	public void setTests(Set<String> p) {
		tests.clear();
		tests.addAll(p);
	}
	
	public void addTest(String p) {
		tests.add(p);
	}
	
	public void removeTest(String p) {
		tests.remove(p);
	}

	public List<Class<?>> getLoggingClasses() {
		return new ArrayList<Class<?>>(loggingClasses);
	}

	public void setLoggingClasses(List<Class<?>> p) {
		loggingClasses.clear();
		loggingClasses.addAll(p);
	}

	public void addLoggingClass(Class<?> p) {
		loggingClasses.add(p);
	}

	public void toXml(I_XML_Builder builder) {
		/** TODO
		StringBuilder sb = new StringBuilder();
		sb.append(XML_START);
		if (coveragePluginFactoryClass != null) {
			sb.append(COVERAGE_PLUGIN_XML_KEY);
			sb.append(coveragePluginFactoryClass.getName());
			sb.append("\"");
		}

		sb.append(" >\n");
		if (trials.size() >= 1 || metaTrialClass != null) {
			
			sb.append("\t");
			sb.append(TRIALS_XML_START);
			sb.append("\n");
			if (metaTrialClass != null) {
				sb.append("\t");
				sb.append(META_TRIAL_XML_START);
				sb.append("\n");
				sb.append("\t");
				sb.append("\t");
				sb.append(metaTrialClass.getName());
				sb.append(META_TRIAL_XML_END);
				sb.append("\n");
			}
			for (Class<? extends I_Trial> c: trials) {
				if (c != null) {
					sb.append("\t");
					sb.append("\t");
					sb.append(TRIAL_XML_START);
					sb.append(c.getName());
					sb.append(TRIAL_XML_END);
					sb.append("\n");
				}
			}
			sb.append("\t");
			sb.append(TRIALS_XML_END);
			sb.append("\n");
		}
		if (tests.size() >= 1) {
			sb.append("\t");
			sb.append(TESTS_XML_START);
			sb.append("\n");
			for (String test: tests) {
				if (test != null) {
					sb.append("\t");
					sb.append("\t");
					sb.append(TEST_XML_START);
					sb.append(test);
					sb.append(TEST_XML_END);
					sb.append("\n");
				}
			}
			
			sb.append("\t");
			sb.append(TESTS_XML_END);
			sb.append("\n");
		}
		if (loggingClasses.size() >= 1) {
			sb.append("\t");
			sb.append(LOGS_XML_START);
			sb.append("\n");
			for (Class<?> c: loggingClasses) {
				if (c != null) {
					sb.append("\t");
					sb.append("\t");
					sb.append(LOG_XML_START);
					sb.append(c.getName());
					sb.append(LOG_XML_END);
					sb.append("\n");
				}
			}
			sb.append("\t");
			sb.append(LOGS_XML_END);
			sb.append("\n");
		}
		sb.append(XML_END);
		*/
	}


	public void setCoveragePluginFactoryClass(
			Class<? extends I_CoveragePluginFactory> coveragePluginConfiguratorClass) {
		coveragePluginFactoryClass = coveragePluginConfiguratorClass;
	}

	public Class<? extends I_MetaTrial> getMetaTrialClass() {
		return metaTrialClass;
	}

	public void setMetaTrialClass(Class<? extends I_MetaTrial> metaTrialClass) {
		this.metaTrialClass = metaTrialClass;
	}

	

	@Override
	public Collection<I_Tests4J_RemoteInfo> getRemoteInfo() {
		return remoteParams.keySet();
	}

	@Override
	public I_Tests4J_Params getRemoteParams(I_Tests4J_RemoteInfo p) {
		return remoteParams.get(p);
	}
	
	public void putRemoteParams(I_Tests4J_RemoteInfo info,I_Tests4J_Params p) {
		remoteParams.put(info, p);
	}

	public I_EvaluatorLookup getEvaluatorLookup() {
		return evaluatorLookup;
	}

	public void setEvaluatorLookup(I_EvaluatorLookup evaluatorLookup) {
		this.evaluatorLookup = evaluatorLookup;
	}

	@Override
	public Class<? extends I_CoveragePluginFactory> getCoveragePluginFactoryClass() {
		return coveragePluginFactoryClass;
	}

	@Override
	public Integer getRecommendedTrialThreadCount() {
		return recommendedTrialThreadCount;
	}
	
	public void setRecommendedTrialThreadCount(Integer p) {
		recommendedTrialThreadCount = p;
	}
}
