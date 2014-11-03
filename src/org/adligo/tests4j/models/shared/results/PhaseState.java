package org.adligo.tests4j.models.shared.results;

import org.adligo.tests4j.system.shared.api.I_Tests4J_TrialProgress;

import java.math.BigDecimal;
import java.util.List;

public class PhaseState implements I_PhaseState {
  private PhaseStateMutant mutant_;
  
  public PhaseState(I_PhaseState state) {
    mutant_ = new PhaseStateMutant(state);
  }

  public String getProcessName() {
    return mutant_.getProcessName();
  }

  public int getThreadCount() {
    return mutant_.getThreadCount();
  }

  public int getCount() {
    return mutant_.getCount();
  }

  public int getDoneCount() {
    return mutant_.getDoneCount();
  }

  public int getRunnablesStarted() {
    return mutant_.getRunnablesStarted();
  }

  public int getRunnablesFinished() {
    return mutant_.getRunnablesFinished();
  }

  public boolean hasStartedAll() {
    return mutant_.hasStartedAll();
  }

  public boolean hasFinishedAll() {
    return mutant_.hasFinishedAll();
  }

  public List<I_Tests4J_TrialProgress> getTrials() {
    return mutant_.getTrials();
  }

  public double getPercentDone() {
    return mutant_.getPercentDone();
  }
}
