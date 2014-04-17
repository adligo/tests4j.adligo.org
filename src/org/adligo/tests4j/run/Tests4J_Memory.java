package org.adligo.tests4j.run;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.adligo.tests4j.models.shared.I_AbstractTrial;

public class Tests4J_Memory {
	private ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>> trialClasses = new ConcurrentLinkedQueue<Class<? extends I_AbstractTrial>>();
	private ConcurrentLinkedQueue<TrialDescription> descriptions = new ConcurrentLinkedQueue<TrialDescription>();
	
	public Tests4J_Memory(Collection<Class<? extends I_AbstractTrial>> p) {
		trialClasses.addAll(p);
		
	}
	
	public Class<? extends I_AbstractTrial> pollTrial() {
		return trialClasses.poll();
	}
	
	public void add(TrialDescription p) {
		descriptions.add(p);
	}
	
	public TrialDescription pollDescriptions() {
		return descriptions.poll();
	}
}
