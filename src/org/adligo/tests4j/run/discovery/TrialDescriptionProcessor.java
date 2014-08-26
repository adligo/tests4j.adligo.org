package org.adligo.tests4j.run.discovery;

import java.util.List;

import org.adligo.tests4j.models.shared.common.I_TrialType;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResultMutant;
import org.adligo.tests4j.models.shared.system.I_Tests4J_CoverageTrialInstrumentation;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.run.helpers.I_Tests4J_NotificationManager;
import org.adligo.tests4j.run.helpers.Tests4J_Memory;

public class TrialDescriptionProcessor {
	private Tests4J_Memory memory;

	public TrialDescriptionProcessor(Tests4J_Memory pMemory) {
		memory = pMemory;
	}

	/**
	 * this method creates a TrialDescription
	 * and passes it to
	 * I_Tests4J_Memory#addResultBeforeMetadata(I_TrialResult)
	 * when it is NOT ignored and NOT runnable.
	 * @param pInstrumented if the first class parameter has been instrumented by the coverage plug-in.
	 * @return
	 */
	public TrialDescription createAndRemberNotRunnableTrials(Class<? extends I_AbstractTrial> trialClazz) {
		String className = trialClazz.getName();
		TrialDescription desc = new TrialDescription(trialClazz, memory);
		
		if (!desc.isIgnored()) {
			if (!desc.isRunnable()) {
				I_TrialResult toSend = getNonRunnableTrialResult(className,
						desc);
				memory.addResultBeforeMetadata(toSend);
			}
		}
		return desc;
	}
	
	public TrialDescription createAndRemberNotRunnableTrials(I_Tests4J_CoverageTrialInstrumentation instrIn) {
		Class<?> trialClass = instrIn.getInstrumentedClass();
		String className = trialClass.getName();
		TrialDescription desc = new TrialDescription(instrIn, memory);
		
		if (!desc.isIgnored()) {
			if (!desc.isRunnable()) {
				I_TrialResult toSend = getNonRunnableTrialResult(className,
						desc);
				memory.addResultBeforeMetadata(toSend);
			}
		}
		return desc;
	}
	
	/**
	 * Crate a trial description from the trial
	 * class which is going to run, which may be an instrumented class.
	 * 
	 * This method calls 
	 * @see I_Tests4J_NotificationManager#onTrialCompleted(I_TrialResult)
	 * when the trial description is NOT ignored and NOT runnable.
	 * 
	 * @param desc
	 * @param notifier
	 */
	public void sendNonRunnableTrialCompletions(TrialDescription desc, I_Tests4J_NotificationManager notifer) {
	
		if (!desc.isIgnored()) {
			if (!desc.isRunnable()) {
				I_TrialResult toSend = getNonRunnableTrialResult(desc.getTrialName(),
						desc);
				notifer.onTrialCompleted(toSend);
			}
		}
	}
	
	protected I_TrialResult getNonRunnableTrialResult(String className,
			TrialDescription desc) {
		BaseTrialResultMutant trm = new BaseTrialResultMutant();
		trm.setTrialName(className);
		List<I_TrialFailure> failures = desc.getFailures();
		if (failures.size() >= 1) {
			trm.setFailures(failures);
		}
		I_TrialType type = desc.getType();
		
		trm.setPassed(false);
		trm.setType(type);
		
		TrialType tt = TrialType.get(type);
		I_TrialResult toSend = null;
		switch (tt) {
			case UseCaseTrial:
					UseCaseTrialResultMutant mut = new UseCaseTrialResultMutant(trm);
					mut.setSystem(desc.getSystemName());
					mut.setUseCase(desc.getUseCase());
					toSend = new UseCaseTrialResult(mut);
				break;
			case ApiTrial:
					ApiTrialResultMutant api = new ApiTrialResultMutant(trm);
					api.setPackageName(desc.getPackageName());
					toSend = new ApiTrialResult(api);
				break;
			case SourceFileTrial:
					SourceFileTrialResultMutant src = new SourceFileTrialResultMutant(trm);
					Class<?> clazz = desc.getSourceFileClass();
					if (clazz != null) {
						src.setSourceFileName(clazz.getName());
					}
					toSend = new SourceFileTrialResult(src);
				break;
			default:
				toSend = new BaseTrialResult(trm);
		}
		return toSend;
	}

}
