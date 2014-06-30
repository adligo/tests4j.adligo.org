package org.adligo.tests4j.models.shared.system;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.models.shared.I_MetaTrial;
import org.adligo.tests4j.models.shared.I_Trial;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;


public class Tests4J_Params implements I_Tests4J_Params {
	public static final String META_TRIAL_XML_END = "</meta_trial>";
	public static final String META_TRIAL_XML_START = "<meta_trial>";
	public static final String TRIAL_XML_END = "</trial>";
	public static final String TRIAL_XML_START = "<trial>";
	public static final String TRIALS_XML_END = "</trials>";
	public static final String TRIALS_XML_START = "<trials>";
	public static final String COVERAGE_PLUGIN_CONFIGURATOR_XML_KEY = "\" coveragePluginConfigurator=\"";
	public static final String COVERAGE_PLUGIN_XML_KEY = " coveragePlugin=\"";
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
	/**
	 * @see I_Tests4J_Params#getReporter()
	 */
	private transient I_Tests4J_Reporter reporter = new ConsoleReporter();
	
	/**
	 * this flaggs a jvm exit
	 */
	private transient boolean exitAfterLastNotification = true;
	
	private I_ThreadCount trialThreads = new SimpleThreadCount();
	/**
	 * All coverage is always recorded if there is a plugin,
	 * more fine grained coverage may be recorded if the 
	 * plugin supports it and recordTrialCoverage
	 * is null or true, and if recordTestCoverage is 
	 * null or true.
	 */
	private Class<? extends I_CoveragePlugin> coveragePluginClass;
	private Class<? extends I_CoveragePluginConfigurator> coveragePluginConfiguratorClass;
	/**
	 * these classes get reporting turned on 
	 */
	private transient List<Class<?>> loggingClasses = new ArrayList<Class<?>>();
	private transient I_SystemExit exitor = new DefaultSystemExitor();
	private Map<I_Tests4J_RemoteInfo, I_Tests4J_Params> remoteParams = 
			new HashMap<I_Tests4J_RemoteInfo, I_Tests4J_Params>();
			
	private I_EvaluatorLookup evaluatorLookup = EvaluatorLookup.DEFAULT_LOOKUP;
	
	public Tests4J_Params() {}
	
	public Tests4J_Params(I_Tests4J_Params p) {
		trials.addAll(p.getTrials());
		metaTrialClass = p.getMetaTrialClass();
		tests.addAll(p.getTests());
		reporter = p.getReporter();
		coveragePluginClass = p.getCoveragePluginClass();
		trialThreads = p.getThreadCount();
		exitAfterLastNotification = p.isExitAfterLastNotification();
		loggingClasses.addAll(p.getLoggingClasses());
		exitor = p.getExitor();
		Collection<I_Tests4J_RemoteInfo> remotes = p.getRemoteInfo();
		for (I_Tests4J_RemoteInfo remote: remotes){
			remoteParams.put(remote, p.getRemoteParams(remote));
		}
	}
	
	public Tests4J_Params(String p) {
		fromXml(p);
	}

	public void fromXml(String p) {
		int start =  p.indexOf(XML_START);
		int tagEnd = p.indexOf(">", start);
		String tag = p.substring(start, tagEnd);
		
		int threadCountIndex = tag.indexOf(THREAD_COUNT_XML_KEY);
		trialThreads = null;
		if (threadCountIndex != -1) {
			threadCountIndex = threadCountIndex + THREAD_COUNT_XML_KEY.length();
			int end = tag.indexOf("\"", threadCountIndex);
			String clazz = tag.substring(threadCountIndex, end);
			try {
				Class<?> clazzInst = Class.forName(clazz);
				trialThreads = (I_ThreadCount) clazzInst.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException x) {
				throw new IllegalArgumentException(x);
			}
		}
		if (trialThreads != null) {
			trialThreads.fromXml(p);
		}
		
		int coveragePluginIndex = tag.indexOf(COVERAGE_PLUGIN_XML_KEY);
		if (coveragePluginIndex != -1) {
			coveragePluginIndex = coveragePluginIndex + COVERAGE_PLUGIN_XML_KEY.length();
			int end = tag.indexOf("\"", coveragePluginIndex);
			String clazz = tag.substring(coveragePluginIndex,end);
			try {
				coveragePluginClass = (Class<? extends I_CoveragePlugin>) Class.forName(clazz);
			} catch (ClassNotFoundException x) {
				throw new IllegalArgumentException(x);
			}
		}
		
		int coveragePluginConfigIndex = tag.indexOf(COVERAGE_PLUGIN_CONFIGURATOR_XML_KEY);
		if (coveragePluginConfigIndex != -1) {
			coveragePluginConfigIndex = coveragePluginConfigIndex  + COVERAGE_PLUGIN_CONFIGURATOR_XML_KEY.length(); 
			int end = tag.indexOf("\"", coveragePluginConfigIndex);
			String clazz = tag.substring(coveragePluginIndex,end);
			try {
				coveragePluginConfiguratorClass = (Class<? extends I_CoveragePluginConfigurator>) Class.forName(clazz);
			} catch (ClassNotFoundException x) {
				throw new IllegalArgumentException(x);
			}
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
	
	public Class<? extends I_CoveragePlugin> getCoveragePluginClass() {
		return coveragePluginClass;
	}
	
	public I_CoveragePlugin getCoveragePlugin() {
		if (coveragePluginClass == null) {
			return null;
		}
		try {
			I_CoveragePlugin plugin = coveragePluginClass.newInstance();
			
			if (coveragePluginConfiguratorClass != null) {
				I_CoveragePluginConfigurator configurator = coveragePluginConfiguratorClass.newInstance();
				configurator.configure(plugin);
			}
			return plugin;
		} catch (InstantiationException |
				IllegalAccessException |
				IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
	}
	public void setCoveragePluginClass(Class<? extends I_CoveragePlugin> coverageRecorder) {
		this.coveragePluginClass = coverageRecorder;
	}
	
	public void addTrials(I_TrialList p) {
		trials.addAll(p.getTrials());
		/*
		minTests = minTests + p.getMinTests();
		minAsserts = minAsserts + p.getMinAsserts();
		minUniqueAssertions = minUniqueAssertions + p.getMinUniqueAssertions();
		*/
	}
	public I_ThreadCount getThreadCount() {
		return trialThreads;
	}
	public void setThreadCount(I_ThreadCount p) {
		this.trialThreads = p;
	}
	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}
	public void setReporter(I_Tests4J_Reporter p) {
		this.reporter = p;
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

	public boolean isExitAfterLastNotification() {
		return exitAfterLastNotification;
	}

	public void setExitAfterLastNotification(boolean exitAfterLastNotification) {
		this.exitAfterLastNotification = exitAfterLastNotification;
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

	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append(XML_START);
		if (trialThreads != null) {
			sb.append(THREAD_COUNT_XML_KEY);
			sb.append(trialThreads.getClass().getName());
			sb.append("\"");
		}
		if (coveragePluginClass != null) {
			sb.append(COVERAGE_PLUGIN_XML_KEY);
			sb.append(coveragePluginClass.getName());
			sb.append("\"");
		}
		if (coveragePluginConfiguratorClass != null) {
			sb.append(COVERAGE_PLUGIN_CONFIGURATOR_XML_KEY);
			sb.append(coveragePluginConfiguratorClass.getName());
			sb.append("\"");
		}

		sb.append(" >\n");
		if (trialThreads != null) {
			sb.append(trialThreads.toXml());
			sb.append("\n");
		}
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
		return sb.toString();
	}

	public Class<? extends I_CoveragePluginConfigurator> getCoveragePluginConfiguratorClass() {
		return coveragePluginConfiguratorClass;
	}

	public void setCoveragePluginConfiguratorClass(
			Class<? extends I_CoveragePluginConfigurator> coveragePluginConfiguratorClass) {
		this.coveragePluginConfiguratorClass = coveragePluginConfiguratorClass;
	}

	public I_SystemExit getExitor() {
		return exitor;
	}

	public void setExitor(I_SystemExit exitor) {
		this.exitor = exitor;
	}

	public Class<? extends I_MetaTrial> getMetaTrialClass() {
		return metaTrialClass;
	}

	public void setMetaTrialClass(Class<? extends I_MetaTrial> metaTrialClass) {
		this.metaTrialClass = metaTrialClass;
	}

	@Override
	public int getTrialThreadCount() {
		int threadCount = trialThreads.getThreadCount();
		if (trials.size() < threadCount) {
			threadCount = trials.size();
		}
		return threadCount;
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
}
