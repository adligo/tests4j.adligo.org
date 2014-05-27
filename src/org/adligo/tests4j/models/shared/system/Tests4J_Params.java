package org.adligo.tests4j.models.shared.system;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.I_MetaTrial;
import org.adligo.tests4j.models.shared.I_Trial;
import org.adligo.tests4j.models.shared.system.report.ConsoleReporter;
import org.adligo.tests4j.models.shared.system.report.I_Tests4J_Reporter;


public class Tests4J_Params implements I_Tests4J_Params {
	public static final String META_TRIAL_XML_END = "</meta_trial>";
	public static final String META_TRIAL_XML_START = "<meta_trial>";
	public static final String TRIAL_XML_END = "</trial>";
	public static final String TRIAL_XML_START = "<trial>";
	public static final String TRIALS_XML_END = "</trials>";
	public static final String TRIALS_XML_START = "<trials>";
	public static final String MIN_TRIALS_XML_KEY = "\" minTrials=\"";
	public static final String MIN_TESTS_XML_KEY = "\" minTests=\"";
	public static final String MIN_ASSERTS_XML_KEY = "\" minAsserts=\"";
	public static final String MIN_UNIQUE_ASSERTS_XML_KEY = "\" minUniqueAsserts=\"";
	public static final String COVERAGE_PLUGIN_CONFIGURATOR_XML_KEY = "\" coveragePluginConfigurator=\"";
	public static final String COVERAGE_PLUGIN_XML_KEY = "\" coveragePlugin=\"";
	public static final String TEST_XML_END = "</test>";
	public static final String TEST_XML_START = "<test>";
	public static final String TESTS_XML_START = "<tests>";
	public static final String TESTS_XML_END = "</tests>";
	public static final String LOGS_XML_START = "<logs>";
	public static final String LOG_XML_START = "<log>";
	public static final String LOG_XML_END = "</log>";
	public static final String LOGS_XML_END = "</logs>";
	public static final String XML_END = "</Tests4J_Params>";
	public static final String XML_START = "<Tests4J_Params trialThreadCount=\"";

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
	private I_Tests4J_Reporter reporter = new ConsoleReporter();
	
	/**
	 * this flaggs a jvm exit
	 */
	private boolean exitAfterLastNotification = true;
	
	/**
	 * @see I_Tests4J_Params#getMinTrials()
	 */
	private Integer minTrials = null;
	/**
	 * @see I_Tests4J_Params#getMinTests()
	 */
	private Integer minTests = null;
	/**
	 *  a null value means don\"t check it
	 */
	private Integer minAsserts = null;
	/**
	 *  a null value means don\"t check it
	 */
	private Integer minUniqueAsserts = null;
	private int trialThreads = 32;
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
	private List<Class<?>> loggingClasses = new ArrayList<Class<?>>();
	private I_SystemExit exitor = new DefaultSystemExitor();
	
	public Tests4J_Params() {}
	
	public Tests4J_Params(I_Tests4J_Params p) {
		trials.addAll(p.getTrials());
		metaTrialClass = p.getMetaTrialClass();
		tests.addAll(p.getTests());
		reporter = p.getReporter();
		minTrials = p.getMinTrials();
		minTests = p.getMinTests();
		minAsserts = p.getMinAsserts();
		minUniqueAsserts = p.getMinUniqueAssertions();
		coveragePluginClass = p.getCoveragePluginClass();
		trialThreads = p.getTrialThreadCount();
		exitAfterLastNotification = p.isExitAfterLastNotification();
		loggingClasses.addAll(p.getLoggingClasses());
		exitor = p.getExitor();
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
			Constructor<? extends I_CoveragePlugin> con =
					coveragePluginClass.getConstructor(new Class[] {});
			I_CoveragePlugin plugin = con.newInstance(new Object[] {});
			
			if (coveragePluginConfiguratorClass != null) {
				Constructor<? extends I_CoveragePluginConfigurator> con2
						= coveragePluginConfiguratorClass.getConstructor(new Class[] {});
				I_CoveragePluginConfigurator configurator = con2.newInstance(new Object[] {});
				configurator.configure(plugin);
			}
			return plugin;
		} catch (NoSuchMethodException x) {
			x.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void setCoveragePluginClass(Class<? extends I_CoveragePlugin> coverageRecorder) {
		this.coveragePluginClass = coverageRecorder;
	}
	public Integer getMinTests() {
		return minTests;
	}
	public Integer getMinAsserts() {
		return minAsserts;
	}
	public Integer getMinUniqueAssertions() {
		return minUniqueAsserts;
	}
	public void setMinTests(int minTests) {
		this.minTests = minTests;
	}
	public void setMinAsserts(int minAsserts) {
		this.minAsserts = minAsserts;
	}
	public void setMinUniqueAssertions(int minUniqueAssertions) {
		this.minUniqueAsserts = minUniqueAssertions;
	}
	
	public void addTrials(I_TrialList p) {
		trials.addAll(p.getTrials());
		/*
		minTests = minTests + p.getMinTests();
		minAsserts = minAsserts + p.getMinAsserts();
		minUniqueAssertions = minUniqueAssertions + p.getMinUniqueAssertions();
		*/
	}
	public int getTrialThreadCount() {
		if (trials.size() < trialThreads) {
			return trials.size();
		}
		return trialThreads;
	}
	public void setTrialThreads(int p) {
		this.trialThreads = p;
	}
	public I_Tests4J_Reporter getReporter() {
		return reporter;
	}
	public void setReporter(I_Tests4J_Reporter p) {
		this.reporter = p;
	}

	public Integer getMinTrials() {
		return minTrials;
	}

	public void setMinTrials(Integer minTrials) {
		this.minTrials = minTrials;
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

	public String toXmlString() {
		StringBuilder sb = new StringBuilder();
		sb.append(XML_START);
		sb.append(trialThreads);
		if (coveragePluginClass != null) {
			sb.append(COVERAGE_PLUGIN_XML_KEY);
			sb.append(coveragePluginClass.getName());
		}
		if (coveragePluginConfiguratorClass != null) {
			sb.append(COVERAGE_PLUGIN_CONFIGURATOR_XML_KEY);
			sb.append(coveragePluginConfiguratorClass.getName());
		}
		//don\"t include exitAfterLastNotification
		if (minUniqueAsserts != null) {
			sb.append(MIN_UNIQUE_ASSERTS_XML_KEY);
			sb.append(minUniqueAsserts);
		}
		if (minAsserts != null) {
			sb.append(MIN_ASSERTS_XML_KEY);
			sb.append(minAsserts);
		}
		if (minTests != null) {
			sb.append(MIN_TESTS_XML_KEY);
			sb.append(minTests);
		}
		if (minTrials != null) {
			sb.append(MIN_TRIALS_XML_KEY);
			sb.append(minTrials);
		}
		sb.append("\" >\n");
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
}
