package org.adligo.tests4j.system.shared.api;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adligo.tests4j.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.shared.common.ClassMethods;
import org.adligo.tests4j.shared.common.Tests4J_System;
import org.adligo.tests4j.shared.xml.I_XML_Builder;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrialParams;
import org.adligo.tests4j.system.shared.trials.I_Trial;
import org.adligo.tests4j.system.shared.trials.I_TrialParams;
import org.adligo.tests4j.system.shared.trials.I_TrialParamsFactory;


public class Tests4J_Params implements I_Tests4J_Params {

	
	/**
	 * @see I_Tests4J_Params#getTrials()
	 */
	private List<Class<? extends I_Trial>> trials = 
				new ArrayList<Class<? extends I_Trial>>();
	/**
	 * @see I_Tests4J_Params#getMetaTrialClass()
	 */
	private Class<? extends I_MetaTrial>  metaTrialClass;
	private I_MetaTrialParams<?> metaTrialParams_;
	private I_TrialParamsFactory trialParamsQueue_;
	
	private Set<I_Tests4J_Selection> tests = new HashSet<I_Tests4J_Selection>();
	
	private Integer recommendedTrialThreadCount;
	private Integer recommendedRemoteThreadCount;
	//there is a deadlock problem in setup, so run it single threaded, unless your improving
	private Integer recommendedSetupThreadCount = 1;
	private I_Tests4J_ProgressParams progressParams_ = new Tests4J_DefaultProgressParams();
	private I_Tests4J_SourceInfoParams sourceInfoParams = new Tests4J_SourceInfoParams();
	
	/**
	 * OutputStreams are not passed between jvms of course.
	 * 
	 */
	private transient List<OutputStream> additionalReportOutputStreams = new ArrayList<OutputStream>();
	
	/**
	 * All coverage is always recorded if there is a plugin,
	 * more fine grained coverage may be recorded if the 
	 * plugin supports it and recordTrialCoverage
	 * is null or true, and if recordTestCoverage is 
	 * null or true.
	 */
	private Class<? extends I_Tests4J_CoveragePluginFactory> coveragePluginFactoryClass;
	private I_Tests4J_CoveragePluginParams coverageParams = new Tests4J_CoveragePluginParams();
	/**
	 * these classes get reporting turned on or off,
	 * the defaults are in the Tests4J_ParamsReader
	 */
	private Map<Class<?>, Boolean> logStates = new HashMap<Class<?>, Boolean>();
	private Map<I_Tests4J_RemoteInfo, I_Tests4J_Params> remoteParams = 
			new HashMap<I_Tests4J_RemoteInfo, I_Tests4J_Params>();
			
	private Class<? extends I_EvaluatorLookup> evaluatorLookup = EvaluatorLookup.DEFAULT_LOOKUP.getClass();
	
	public Tests4J_Params() {}
	
	public Tests4J_Params(I_Tests4J_Params p) {
		trials.addAll(p.getTrials());
		metaTrialClass = p.getMetaTrialClass();
		tests.addAll(p.getTests());
		coveragePluginFactoryClass = p.getCoveragePluginFactoryClass();
		recommendedTrialThreadCount = p.getRecommendedTrialThreadCount();
		recommendedSetupThreadCount = p.getRecommendedSetupThreadCount();
		recommendedRemoteThreadCount = p.getRecommendedRemoteThreadCount();
		sourceInfoParams = p.getSourceInfoParams();
		
		Map<Class<?>, Boolean> otherSettings = p.getLogStates();
		if (otherSettings != null) {
			logStates.putAll(otherSettings);
		}
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
	
	public Class<? extends I_Tests4J_CoveragePluginFactory> getCoveragePluginFactory() {
		return coveragePluginFactoryClass;
	}
	

	
	public void addTrials(I_Tests4J_TrialList p) {
		trials.addAll(p.getTrials());
	}

	public Set<I_Tests4J_Selection> getTests() {
		return tests;
	}

	public void setTests(Collection<? extends I_Tests4J_Selection> p) {
		tests.clear();
		tests.addAll(p);
	}
	
	public void addTest(I_Tests4J_Selection p) {
		tests.add(p);
	}
	
	public void removeTest(String p) {
		tests.remove(p);
	}

	public Map<Class<?>, Boolean> getLogStates() {
		return Collections.unmodifiableMap(logStates);
	}

	public void setLogState(Class<?> p, boolean on) {
		logStates.put(p, on);
	}


	public void toXml(I_XML_Builder builder) {
		builder.indent();
		builder.addStartTag(I_Tests4J_Params.TAG_NAME);
		
		if (coveragePluginFactoryClass != null) {
			builder.addAttribute(I_Tests4J_Params.COVERAGE_PLUGIN_FACTORY_ATTRIBUTE, 
					coveragePluginFactoryClass.getName());
		}
		if (evaluatorLookup != null) {
			builder.addAttribute(I_Tests4J_Params.EVALUATOR_LOOKUP_ATTRIBUTE, 
					evaluatorLookup.getName());
		}
		if (metaTrialClass != null) {
			builder.addAttribute(I_Tests4J_Params.META_TRIAL_ATTRIBUTE, 
					metaTrialClass.getName());
		}
		
		if (recommendedRemoteThreadCount != null) {
			builder.addAttribute(I_Tests4J_Params.RECOMENDED_REMOTE_THREADS_ATTRIBUTE, 
					"" + recommendedRemoteThreadCount);
		}
		
		if (recommendedTrialThreadCount != null) {
			builder.addAttribute(I_Tests4J_Params.RECOMENDED_TRIAL_THREADS_ATTRIBUTE, 
					"" + recommendedTrialThreadCount);
		}
		
		if (recommendedSetupThreadCount != null) {
			builder.addAttribute(I_Tests4J_Params.RECOMENDED_SETUP_THREADS_ATTRIBUTE, 
					"" + recommendedSetupThreadCount);
		}
		
		builder.append(">");
		builder.endLine();
		List<String> trialNames = ClassMethods.toNames(trials);
		builder.addCollection(trialNames, I_Tests4J_Params.TRIALS_TAG_NAME, I_Tests4J_Params.TRIAL_TAG_NAME);
		
		//builder.addCollection(tests, I_Tests4J_Params.TESTS_TAG_NAME, I_Tests4J_Params.TEST_TAG_NAME);
		
		/*
		List<String> logClassesNames = ClassMethods.toNames(loggingStates);
		builder.addCollection(logClassesNames, I_Tests4J_Params.LOG_CLASSESS_TAG_NAME, I_Tests4J_Params.CLASS_NAME_TAG_NAME);
		*/
	}

	

	

	public void setCoveragePluginFactoryClass(
			Class<? extends I_Tests4J_CoveragePluginFactory> coveragePluginConfiguratorClass) {
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

	public Class<? extends I_EvaluatorLookup> getEvaluatorLookup() {
		return evaluatorLookup;
	}

	public void setEvaluatorLookup( Class<? extends I_EvaluatorLookup> evaluatorLookup) {
		this.evaluatorLookup = evaluatorLookup;
	}

	@Override
	public Class<? extends I_Tests4J_CoveragePluginFactory> getCoveragePluginFactoryClass() {
		return coveragePluginFactoryClass;
	}

	@Override
	public Integer getRecommendedTrialThreadCount() {
		return recommendedTrialThreadCount;
	}
	
	public void setRecommendedTrialThreadCount(Integer p) {
		recommendedTrialThreadCount = p;
	}

	@Override
	public Integer getRecommendedRemoteThreadCount() {
		return recommendedRemoteThreadCount;
	}

	@Override
	public Integer getRecommendedSetupThreadCount() {
		return recommendedSetupThreadCount;
	}

	@Override
	public I_Tests4J_CoveragePluginParams getCoverageParams() {
		return coverageParams;
	}

	public void setCoverageParams(I_Tests4J_CoveragePluginParams coverageParams) {
		this.coverageParams = coverageParams;
	}

	public void setRecommendedRemoteThreadCount(Integer recommendedRemoteThreadCount) {
		this.recommendedRemoteThreadCount = recommendedRemoteThreadCount;
	}

	public void setRecommendedSetupThreadCount(Integer recommendedSetupThreadCount) {
		this.recommendedSetupThreadCount = recommendedSetupThreadCount;
	}

	@Override
	public List<OutputStream> getAdditionalReportOutputStreams() {
		return additionalReportOutputStreams;
	}

	@Override
	public void setAdditionalReportOutputStreams(Collection<OutputStream> out) {
		additionalReportOutputStreams.clear();
		additionalReportOutputStreams.addAll(out);
	}

	@Override
	public void addAdditionalReportOutputStreams(OutputStream out) {
		additionalReportOutputStreams.add(out);
	}
	

	@Override
	public I_Tests4J_ProgressParams getProgressMonitor() {
		return progressParams_;
	}

	public void setProgressMonitor(I_Tests4J_ProgressParams progressMonitor) {
		this.progressParams_ = progressMonitor;
	}

	@Override
	public I_MetaTrialParams<?> getMetaTrialParams() {
		return metaTrialParams_;
	}

	public void setMetaTrialParams(I_MetaTrialParams<?> p) {
		metaTrialParams_ = p;
	}
	
	@Override
	public I_TrialParamsFactory getTrialParamsQueue() {
		return trialParamsQueue_;
	}
	
	public void getTrialParamsQueue(I_TrialParamsFactory p) {
		trialParamsQueue_ = p;
	}
	
	public boolean hasCodeCoverageFactory() {
		if (coveragePluginFactoryClass != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasMetaTrial() {
		if (metaTrialClass != null) {
			return true;
		}
		return false;
	}

	public I_Tests4J_SourceInfoParams getSourceInfoParams() {
		return sourceInfoParams;
	}

	public void setSourceInfoParams(I_Tests4J_SourceInfoParams sourceInfoParams) {
		this.sourceInfoParams = sourceInfoParams;
	}
}
