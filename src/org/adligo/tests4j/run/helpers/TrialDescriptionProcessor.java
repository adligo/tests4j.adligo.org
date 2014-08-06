package org.adligo.tests4j.run.helpers;

import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.common.I_TrialType;
import org.adligo.tests4j.models.shared.common.StackTraceBuilder;
import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.I_TrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResult;
import org.adligo.tests4j.models.shared.results.SourceFileTrialResultMutant;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResult;
import org.adligo.tests4j.models.shared.results.UseCaseTrialResultMutant;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;
import org.adligo.tests4j.run.discovery.TrialDescription;
import org.adligo.tests4j.run.discovery.TrialTypeFinder;

public class TrialDescriptionProcessor {
	private Tests4J_Memory memory;
	
	public TrialDescriptionProcessor(Tests4J_Memory pMemory) {
		memory = pMemory;
	}
	/**
	 * This instruments the trialClass
	 * and creates a TrialDescription from the class which has NOT
	 * been instrumented.  
	 * 
	 *  The coverage plug-in recorder must be started before 
	 *  a instrumented class instance is created, otherwise it causes
	 *  errors.
	 *  
	 * @param trialClazz
	 */
	public TrialDescription instrumentAndAddTrialDescription(Class<? extends I_AbstractTrial> trialClazz) {
		String className = trialClazz.getName();
		// synchronized on the trialClass instance to make sure
		// that only one thread is doing this for a specific trial at a time
		// This allows reuse of TrialDescription instances
		synchronized (trialClazz) {
			
			//try to reuse the description if another thread already described it
			TrialDescription desc = memory.getTrialDescription(className);
			if (desc == null) {
				desc = new TrialDescription(trialClazz, memory.getLogger());
			}
			memory.addTrialDescription(className, desc);
			
			if (!desc.isIgnored()) {
				if (!desc.isRunnable()) {
					BaseTrialResultMutant trm = new BaseTrialResultMutant();
					trm.setTrialName(className);
					List<I_TrialFailure> failures = desc.getFailures();
					if (failures.size() >= 1) {
						trm.setFailures(failures);
					}
					trm.setPassed(false);
					I_TrialType type = desc.getType();
					if (type == null) {
						memory.addResultBeforeMetadata(new BaseTrialResult(trm));
					} else {
					
						trm.setType(type);
						
						TrialType tt = TrialType.get(type);
						switch (tt) {
							case UseCaseTrial:
									UseCaseTrialResultMutant mut = new UseCaseTrialResultMutant(trm);
									mut.setSystem(desc.getSystemName());
									mut.setUseCase(desc.getUseCase());
									memory.addResultBeforeMetadata(new UseCaseTrialResult(mut));
								break;
							case ApiTrial:
									ApiTrialResultMutant api = new ApiTrialResultMutant(trm);
									api.setPackageName(desc.getPackageName());
									memory.addResultBeforeMetadata(new ApiTrialResult(api));
								break;
							case SourceFileTrial:
									SourceFileTrialResultMutant src = new SourceFileTrialResultMutant(trm);
									Class<?> clazz = desc.getSourceFileClass();
									if (clazz != null) {
										src.setSourceFileName(clazz.getName());
									}
									memory.addResultBeforeMetadata(new SourceFileTrialResult(src));
									break;
							default:
								memory.addResultBeforeMetadata(new BaseTrialResult(trm));
						}
					}
				}
			}
			return desc;
		}
	}
	
	/**
	 * Crate a trial description from the trial
	 * class which is going to run, which may have been instrumented.
	 * This is called after the coverage plug-in has started recording.
	 * 
	 * This may NOT be runnable, even though the non instrumented 
	 * version of the TrialDescription for the class is.
	 * 
	 * @param trialClazz
	 * @param notifier
	 */
	public TrialDescription newTrailDescriptionToRun(Class<? extends I_AbstractTrial> trialClazz, I_Tests4J_NotificationManager notifer) {
		String className = trialClazz.getName();
		TrialDescription desc = new TrialDescription(trialClazz, memory.getLogger());
		
		if (!desc.isIgnored()) {
			if (!desc.isRunnable()) {
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
				notifer.onTrialCompleted(toSend);
			}
		}
		return desc;
	}
}
