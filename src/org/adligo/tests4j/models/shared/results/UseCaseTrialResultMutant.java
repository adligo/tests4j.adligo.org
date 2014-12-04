package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverageBrief;
import org.adligo.tests4j.models.shared.coverage.PackageCoverageBriefMutant;
import org.adligo.tests4j.models.shared.metadata.I_UseCaseBrief;

import java.util.HashMap;
import java.util.Map;

public class UseCaseTrialResultMutant extends BaseTrialResultMutant implements I_UseCaseTrialResult {
	private I_UseCaseBrief useCase_;
	private String system_;
	private String project_;
	private Map<String,PackageCoverageBriefMutant> coverage_ = 
	    new HashMap<String, PackageCoverageBriefMutant>();
	
	public UseCaseTrialResultMutant() {}
	
	public UseCaseTrialResultMutant(I_UseCaseTrialResult p) {
		this(p, true);
	}
	
	public UseCaseTrialResultMutant(I_UseCaseTrialResult p, boolean cloneRelaions) {
		super(p, cloneRelaions);
		useCase_ = p.getUseCase();
		system_ = p.getSystem();
	}

	public UseCaseTrialResultMutant(I_TrialResult p) {
		this(p, true);
	}
	
	public UseCaseTrialResultMutant(I_TrialResult p, boolean cloneRelaions) {
		super(p, cloneRelaions);
	}
	
	public I_UseCaseBrief getUseCase() {
		return useCase_;
	}

	public void setUseCase(I_UseCaseBrief useCase) {
		this.useCase_ = useCase;
	}

  public String getSystem() {
    return system_;
  }

  public void setSystem(String system) {
    system_ = system;
  }

  public String getProject() {
    return project_;
  }

  public void setProject(String project) {
    project_ = project;
  }

  @Override
  public Map<String, I_PackageCoverageBrief> getCoverageMap() {
    return new HashMap<String, I_PackageCoverageBrief>(coverage_);
  }

  @Override
  public I_PackageCoverageBrief getPackageCoverage(String packageName) {
    // TODO Auto-generated method stub
    return null;
  }
  /*
  public static I_PackageCoverageBrief getPackage(
      String packageName, Map<String, I_PackageCoverageBrief> topPackages) {
    
  }
  */
}
