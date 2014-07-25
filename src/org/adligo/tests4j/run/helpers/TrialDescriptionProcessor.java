package org.adligo.tests4j.run.helpers;

import org.adligo.tests4j.models.shared.common.TrialType;
import org.adligo.tests4j.models.shared.results.ApiTrialResult;
import org.adligo.tests4j.models.shared.results.ApiTrialResultMutant;
import org.adligo.tests4j.models.shared.results.BaseTrialResult;
import org.adligo.tests4j.models.shared.results.BaseTrialResultMutant;
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
	 * @diagram sync on 5/8/2014
	 *    for Overview.seq
	 *    
	 * @param trialClazz
	 */
	public TrialDescription addTrialDescription(Class<? extends I_AbstractTrial> trialClazz) {
		String className = trialClazz.getName();
		// synchronized on the trialClass instance to make sure
		// that only one thread is doing this for a specific trial at a time
		// This allows reuse of TrialDescription instances
		synchronized (trialClazz) {
			TrialType type = TrialTypeFinder.getTypeInternal(trialClazz);
			
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
					String failureMessage = desc.getResultFailureMessage();
					if (failureMessage != null) {
						TrialFailure tf = new TrialFailure(failureMessage, desc.getResultException());
						trm.setFailure(tf);
					}
					
					trm.setPassed(false);
					trm.setType(type);
					
					switch (type) {
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
									memory.addResultBeforeMetadata(new SourceFileTrialResult(src));
									break;
								}
						default:
							memory.addResultBeforeMetadata(new BaseTrialResult(trm));
					}
				}
			}
			return desc;
		}
	}
}
