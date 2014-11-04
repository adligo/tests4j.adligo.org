package org.adligo.tests4j.run.discovery;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.adligo.tests4j.models.shared.association.I_ClassAssociationsLocal;
import org.adligo.tests4j.shared.common.TrialType;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;

public class TrialQueueDecisionTree {
	private final int trialCount_;
	private final AtomicInteger trialsAdded_ = new AtomicInteger(0);
	private I_Tests4J_Log log_;
	private boolean coveragePlugin_;
	/**
	 * note this may not match with the trial count,
	 * since trials can be added more than once.
	 */
	private ConcurrentHashMap<TrialStateNameIdKey,TrialState> allStates = 
			new ConcurrentHashMap<TrialStateNameIdKey,TrialState>();
	private ConcurrentHashMap<String,TrialState> sourceFileTrialStateBySourceClass = 
			new ConcurrentHashMap<String,TrialState>();
	private ConcurrentHashMap<String,AtomicInteger> trialCounts = 
			new ConcurrentHashMap<String,AtomicInteger>();
	private ConcurrentLinkedQueue<TrialState> nonApprovedTrials = 
				new ConcurrentLinkedQueue<TrialState>();
	private ConcurrentHashMap<TrialStateNameIdKey, TrialState> unorderedApprovedSourceFileTrials = 
			new ConcurrentHashMap<TrialStateNameIdKey, TrialState>();
	private ConcurrentHashMap<TrialStateNameIdKey, Set<TrialState>> sourceFilePrerequisiteRuns = 
			new ConcurrentHashMap<TrialStateNameIdKey, Set<TrialState>>();
	private ConcurrentLinkedQueue<TrialState> sourceFileTrials = 
			new ConcurrentLinkedQueue<TrialState>();	
	private ConcurrentLinkedQueue<TrialState> apiTrials = 
			new ConcurrentLinkedQueue<TrialState>();	
	private ConcurrentLinkedQueue<TrialState> useCaseTrials = 
			new ConcurrentLinkedQueue<TrialState>();
	private AtomicBoolean returnedMetaTrialState = new AtomicBoolean(false);
	private AtomicInteger statesPolled = new AtomicInteger();
	private TrialState metaTrialState;
	
	public TrialQueueDecisionTree(int count, I_Tests4J_Log pLog, boolean coveragePluginIn) {
		trialCount_ = count;
		log_ = pLog;
		coveragePlugin_ = coveragePluginIn;
	}
	
	public synchronized boolean addTrial(TrialState state) {
		trialsAdded_.addAndGet(1);
		String trialName = state.getTrialName();
		AtomicInteger ai = trialCounts.get(trialName);
		if (ai == null) {
			trialCounts.put(trialName, new AtomicInteger(1));
			state.setId(1);
		} else {
			state.setId(ai.addAndGet(1));
		}
		allStates.putIfAbsent(new TrialStateNameIdKey(trialName, state.getId()), state);
		if (state.getApprovedForRun()) {
			TrialDescription td = state.getTrialDescription();
			TrialType tt = TrialType.get(td.getType());
			switch (tt) {
				case SourceFileTrial:
						unorderedApprovedSourceFileTrials.put(
								new TrialStateNameIdKey(trialName, state.getId()), state);
						Class<?> sourceFileClass =  td.getSourceFileClass();
						sourceFileTrialStateBySourceClass.put(sourceFileClass.getName(), state);
					break;
				case ApiTrial:
						apiTrials.add(state);
					break;
				case UseCaseTrial:
						useCaseTrials.add(state);
					break;
				default:
					metaTrialState = state;
			}
		} else {
			nonApprovedTrials.add(state);
		}
		
		if (trialCount_ == trialsAdded_.get()) {
			determineOrdering();
			return true;
		}
		return false;
	}
	
	/**
	 * adds source files to sourceFileTrials,
	 * if there is a coveragePlugin, it orders them 
	 * by the sourceClass dependencies
	 */
	private void determineOrdering() {
		Collection<TrialState> tstates =  unorderedApprovedSourceFileTrials.values();
		if (!coveragePlugin_) {
			sourceFileTrials.addAll(tstates);
			return;
		}
		Iterator<TrialState> it = tstates.iterator();
		/**
		 * just the source classe themselves, not all of there dependents
		 */
		Set<String> allSourceClassDependents = new HashSet<String>();
		Set<String> nonSourceClassDependents = new HashSet<String>();
		while (it.hasNext()) {
			TrialState ts =it.next();
			TrialDescription td =  ts.getTrialDescription();
			String sourceClassName = td.getSourceFileClass().getName();
			allSourceClassDependents.add(sourceClassName);
			I_ClassAssociationsLocal dependencies = td.getSourceClassDependencies();
			nonSourceClassDependents.addAll(dependencies.getDependencyNames());
			nonSourceClassDependents.remove(sourceClassName);
		}
		
		Set<String> sourceClassesWithAddedTrials = new HashSet<String>();
		while (tstates.size() != 0) {
			it = tstates.iterator();
			while (it.hasNext()) {
				TrialState ts =it.next();
				TrialDescription td =  ts.getTrialDescription();
				I_ClassAssociationsLocal dependencies = td.getSourceClassDependencies();
				Set<String> dependencyNames = dependencies.getDependencyNames();
				Set<String> copy = new HashSet<String>(dependencyNames);
				copy.removeAll(nonSourceClassDependents);
				
				String sourceFileClassName = td.getSourceFileClass().getName();
				copy.remove(sourceFileClassName);
				copy.removeAll(allSourceClassDependents);
				if (copy.size() == 0) {
					//it doesn't depend on any of the other sourceFileTrials, so add it
					sourceFileTrials.add(ts);
					sourceClassesWithAddedTrials.add(sourceFileClassName);
					it.remove();
				} else if (sourceClassesWithAddedTrials.containsAll(copy)) {
					Set<TrialState> prerequisites = new HashSet<TrialState>();
					
					for (String className: copy) {
						TrialState state = sourceFileTrialStateBySourceClass.get(className);
						int counter = 1;
						TrialState preqState = allStates.get(new TrialStateNameIdKey(state.getTrialName(), counter++));
						while (preqState != null) {
							prerequisites.add(preqState);
							preqState = allStates.get(new TrialStateNameIdKey(state.getTrialName(), counter++));
						}
					}
					sourceFilePrerequisiteRuns.put(new TrialStateNameIdKey(ts.getTrialName(), ts.getId()), prerequisites);
					//everything it depends on has already been added 
					sourceFileTrials.add(ts);
					sourceClassesWithAddedTrials.add(sourceFileClassName);
					it.remove();
				}
			}
		}
	}
	
	public TrialState poll() {
		TrialState toRet = nonApprovedTrials.poll();
		if (toRet == null) {
			toRet = sourceFileTrials.poll();
			if (toRet != null && coveragePlugin_) {
				//make sure all prerequisites are finished running
				Set<TrialState> preqStates =  sourceFilePrerequisiteRuns.get(toRet.getKey());
				if (preqStates != null) {
					for (TrialState ps: preqStates) {
						if (!ps.getFinishedRun()) {
							if (log_.isLogEnabled(TrialQueueDecisionTree.class)) {
								String message = this.toString() + toRet.getTrialName() + 
										" is waiting on " + ps.getTrialName();
								log_.log(message);
							}
							try {
								ps.wait(100);
							} catch (InterruptedException x) {
								Thread.currentThread().interrupt();
							}
						}
					}
				}
			}
			
		}
		
		if (toRet == null) {
			toRet = apiTrials.poll();
		}
		
		if (toRet == null) {
			toRet = useCaseTrials.poll();
		}
		if (toRet == null) {
			if (returnedMetaTrialState.get()) {
				toRet = null;
			} else {
				synchronized (returnedMetaTrialState) {
					if (!returnedMetaTrialState.get()) {
						returnedMetaTrialState.set(true);
						return metaTrialState;
					}
					toRet = null;
				}
			}
		}
		if (log_.isLogEnabled(TrialQueueDecisionTree.class)) {
			String trialName = "null";
			String trialId = "null";
			if (toRet != null) {
				trialName = toRet.getTrialName();
				trialId = "" + toRet.getId();
			}
			String message = "" + this.getClass().getSimpleName() + " returning " + trialName + "[" + 
					trialId + "]";
			log_.log(message);
		}
		statesPolled.addAndGet(1);
		return toRet;
	}
	
	public int getRunnableTrialDescriptions() {
		int toRet = 0;
		Iterator<TrialState> it = allStates.values().iterator();
		while (it.hasNext()) {
			TrialState ts = it.next();
			TrialDescription desc = ts.getTrialDescription();
			if (desc.isRunnable()) {
				if (!desc.isIgnored()) {
					toRet++;
				}
			}
		}
		
		return toRet;
	}
	
	public int getIgnoredTrialDescriptions() {
		int toRet = 0;
		Iterator<TrialState> it = allStates.values().iterator();
		while (it.hasNext()) {
			TrialState ts = it.next();
			TrialDescription desc = ts.getTrialDescription();
			if (desc.isIgnored()) {
				toRet++;
			}
		}
		return toRet;
	}

	public int getTrialCount() {
		return trialCount_;
	}
	
	public Collection<TrialState> getAllStates() {
		return allStates.values();
	}
	
	public synchronized boolean isFull() {
		if (log_.isLogEnabled(TrialQueueDecisionTree.class)) {
			String message = this.getClass().getName() + " trialCount = " + 
							trialCount_ + " trialsAdded " + trialsAdded_.get();
			log_.log(message);
		}
		if (trialCount_ == trialsAdded_.get()) {
			return true;
		}
		return false;
	}
	
	/**
	 * if the last state has been polled, 
	 * @return
	 */
	public synchronized boolean areAllTrialsFinished() {
		Iterator<TrialState> all = allStates.values().iterator();
		while (all.hasNext()) {
			TrialState ts = all.next();
			if (!ts.getFinishedRun()) {
				return false;
			}
		}
		return true;
	}

	public synchronized TrialState getMetaTrialState() {
		return metaTrialState;
	}
}
