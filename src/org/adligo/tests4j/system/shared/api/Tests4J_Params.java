package org.adligo.tests4j.system.shared.api;

import org.adligo.tests4j.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.system.shared.trials.I_MetaTrial;
import org.adligo.tests4j.system.shared.trials.I_MetaTrialParams;
import org.adligo.tests4j.system.shared.trials.I_Trial;
import org.adligo.tests4j.system.shared.trials.I_TrialParamsFactory;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @see I_Tests4J_Params
 * @author scott
 *
 */
public class Tests4J_Params implements I_Tests4J_Params {

  private List<String> additionalNonInstrumentedClasses_ = new ArrayList<String>();
  private List<String> additionalNonInstrumentedPackages_ = new ArrayList<String>();
  
  private List<String> additionalNonResultPackages_ = new ArrayList<String>();
	/**
	 * @see I_Tests4J_Params#getTrials()
	 */
	private List<Class<? extends I_Trial>> trials_ = 
				new ArrayList<Class<? extends I_Trial>>();
	/**
	 * @see I_Tests4J_Params#getMetaTrialClass()
	 */
	private Class<? extends I_MetaTrial>  metaTrialClass_;
	private I_MetaTrialParams<?> metaTrialParams_;
	private I_TrialParamsFactory trialParamsQueue_;
	
	private Set<I_Tests4J_Selection> tests = new HashSet<I_Tests4J_Selection>();
	
	private Integer recommendedTrialThreadCount_;
  private Integer recommendedSetupThreadCount_;
	private I_Tests4J_ProgressParams progressParams_ = new Tests4J_DefaultProgressParams();
	private I_Tests4J_SourceInfoParams sourceInfoParams_ = new Tests4J_SourceInfoParams();

	
	
	/**
	 * OutputStreams are not passed between jvms of course.
	 * 
	 */
	private transient List<OutputStream> additionalReportOutputStreams_ = new ArrayList<OutputStream>();
	
	/**
	 * All coverage is always recorded if there is a plugin,
	 * more fine grained coverage may be recorded if the 
	 * plugin supports it and recordTrialCoverage
	 * is null or true, and if recordTestCoverage is 
	 * null or true.
	 */
	private Class<? extends I_Tests4J_CoveragePluginFactory> coveragePluginFactoryClass_;
	private I_Tests4J_CoveragePluginParams coverageParams_ = new Tests4J_CoveragePluginParams();
	/**
	 * these classes get reporting turned on or off,
	 * the defaults are in the Tests4J_ParamsReader
	 */
	private Map<Class<?>, Boolean> logStates_ = new HashMap<Class<?>, Boolean>();
	private Map<I_Tests4J_RemoteInfo, I_Tests4J_Params> remoteParams_ = 
			new HashMap<I_Tests4J_RemoteInfo, I_Tests4J_Params>();
			
	private Class<? extends I_EvaluatorLookup> evaluatorLookup_ = EvaluatorLookup.DEFAULT_LOOKUP.getClass();
	
	public Tests4J_Params() {}
	
	public Tests4J_Params(I_Tests4J_Params p) {
		trials_.addAll(p.getTrials());
		metaTrialClass_ = p.getMetaTrialClass();
		tests.addAll(p.getTests());
		coveragePluginFactoryClass_ = p.getCoveragePluginFactoryClass();
		recommendedTrialThreadCount_ = p.getRecommendedTrialThreadCount();
		recommendedSetupThreadCount_ = p.getRecommendedSetupThreadCount();
		sourceInfoParams_ = p.getSourceInfoParams();
		
		Map<Class<?>, Boolean> otherSettings = p.getLogStates();
		if (otherSettings != null) {
			logStates_.putAll(otherSettings);
		}
		Collection<I_Tests4J_RemoteInfo> remotes = p.getRemoteInfo();
		for (I_Tests4J_RemoteInfo remote: remotes){
			remoteParams_.put(remote, p.getRemoteParams(remote));
		}
	}
	
	public void addAdditionalNonInstrumentedClass(String className) {
    if (className != null) {
      additionalNonInstrumentedClasses_.add(className);
    }
  }
	
	public void addAdditionalNonInstrumentedPackage(String packageName) {
    if (packageName != null) {
      additionalNonInstrumentedPackages_.add(packageName);
    }
  }
	
	public void addAdditionalReportOutputStreams(OutputStream out) {
	  if (out != null) {
	     additionalReportOutputStreams_.add(out);
	  }
	}
	
	public void addTest(I_Tests4J_Selection p) {
	  if (p != null) {
	    tests.add(p);
	  }
	}
	 
  public void addTrial(Class<? extends I_Trial> p) {
    if (p != null) {
      trials_.add(p);
    }
  }
  
  public void addTrials(I_Tests4J_TrialList p) {
    if (p != null) {
      trials_.addAll(p.getTrials());
    }
  }
  
	public List<Class<? extends I_Trial>> getTrials() {
		return trials_;
	}
	public void setTrials(List<Class<? extends I_Trial>> p) {
		trials_.clear();
		trials_.addAll(p);
	}

	
	public Class<? extends I_Tests4J_CoveragePluginFactory> getCoveragePluginFactory() {
		return coveragePluginFactoryClass_;
	}

	public Set<I_Tests4J_Selection> getTests() {
		return tests;
	}

	public void setTests(Collection<? extends I_Tests4J_Selection> p) {
		tests.clear();
		tests.addAll(p);
	}
	

	
	public void removeTest(String p) {
		tests.remove(p);
	}

	public Map<Class<?>, Boolean> getLogStates() {
		return Collections.unmodifiableMap(logStates_);
	}

	@SuppressWarnings("boxing")
  public void setLogState(Class<?> p, boolean on) {
		logStates_.put(p, on);
	}



	public void setCoveragePluginFactoryClass(
			Class<? extends I_Tests4J_CoveragePluginFactory> coveragePluginConfiguratorClass) {
		coveragePluginFactoryClass_ = coveragePluginConfiguratorClass;
	}

	public Class<? extends I_MetaTrial> getMetaTrialClass() {
		return metaTrialClass_;
	}

	public void setMetaTrialClass(Class<? extends I_MetaTrial> metaTrialClass) {
		this.metaTrialClass_ = metaTrialClass;
	}

	

	@Override
	public Collection<I_Tests4J_RemoteInfo> getRemoteInfo() {
		return remoteParams_.keySet();
	}

	@Override
	public I_Tests4J_Params getRemoteParams(I_Tests4J_RemoteInfo p) {
		return remoteParams_.get(p);
	}
	
	public void putRemoteParams(I_Tests4J_RemoteInfo info,I_Tests4J_Params p) {
		remoteParams_.put(info, p);
	}

	public Class<? extends I_EvaluatorLookup> getEvaluatorLookup() {
		return evaluatorLookup_;
	}

	public void setEvaluatorLookup( Class<? extends I_EvaluatorLookup> evaluatorLookup) {
		this.evaluatorLookup_ = evaluatorLookup;
	}

	@Override
	public Class<? extends I_Tests4J_CoveragePluginFactory> getCoveragePluginFactoryClass() {
		return coveragePluginFactoryClass_;
	}

	@Override
	public Integer getRecommendedTrialThreadCount() {
		return recommendedTrialThreadCount_;
	}
	
	public void setRecommendedTrialThreadCount(Integer p) {
		recommendedTrialThreadCount_ = p;
	}


	@Override
	public Integer getRecommendedSetupThreadCount() {
		return recommendedSetupThreadCount_;
	}

	@Override
	public I_Tests4J_CoveragePluginParams getCoverageParams() {
		return coverageParams_;
	}

	public void setCoverageParams(I_Tests4J_CoveragePluginParams coverageParams) {
		this.coverageParams_ = coverageParams;
	}


	public void setRecommendedSetupThreadCount(Integer recommendedSetupThreadCount) {
		this.recommendedSetupThreadCount_ = recommendedSetupThreadCount;
	}

	@Override
	public List<OutputStream> getAdditionalReportOutputStreams() {
		return additionalReportOutputStreams_;
	}

	public void setAdditionalReportOutputStreams(Collection<OutputStream> out) {
		additionalReportOutputStreams_.clear();
		if (out != null) {
		  additionalReportOutputStreams_.addAll(out);
		  additionalReportOutputStreams_.remove(null);
		}
	}

	@Override
	public I_Tests4J_ProgressParams getProgressParams() {
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
		if (coveragePluginFactoryClass_ != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasMetaTrial() {
		if (metaTrialClass_ != null) {
			return true;
		}
		return false;
	}

	public I_Tests4J_SourceInfoParams getSourceInfoParams() {
		return sourceInfoParams_;
	}

	public void setSourceInfoParams(I_Tests4J_SourceInfoParams sourceInfoParams) {
		this.sourceInfoParams_ = sourceInfoParams;
	}

  public List<String> getAdditionalNonInstrumentedPackages() {
    return additionalNonInstrumentedPackages_;
  }

  public void setAdditionalNonInstrumentedPackages(Collection<String> additionalNonInstrumentedPackages) {
    additionalNonInstrumentedPackages_.clear();
    if (additionalNonInstrumentedPackages != null) {
      additionalNonInstrumentedPackages.remove(null);
      additionalNonInstrumentedPackages_.addAll(additionalNonInstrumentedPackages);
    }
  }
  
  public List<String> getAdditionalNonResultPackages() {
    return additionalNonResultPackages_;
  }

  public void setAdditionalNonResultPackages(Collection<String> additionalNonResultPackages) {
    additionalNonResultPackages_.clear();
    if (additionalNonResultPackages != null) {
      additionalNonResultPackages.remove(null);
      additionalNonResultPackages_.addAll(additionalNonResultPackages);
    }
  }

  public List<String> getAdditionalNonInstrumentedClasses() {
    return additionalNonInstrumentedClasses_;
  }

  public void setAdditionalNonInstrumentedClasses(Collection<String> additionalWhiteListClasses) {
    additionalNonInstrumentedClasses_.clear();
    if (additionalWhiteListClasses != null) {
      additionalNonInstrumentedClasses_.addAll(additionalWhiteListClasses);
      additionalNonInstrumentedClasses_.remove(null);
    }
  }
  
  
}
