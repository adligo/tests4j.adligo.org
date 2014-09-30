package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.run.common.I_Tests4J_Memory;
import org.adligo.tests4j.shared.asserts.dependency.AllowedDependencies;
import org.adligo.tests4j.shared.asserts.dependency.DependencyGroupAggregate;
import org.adligo.tests4j.shared.asserts.dependency.I_DependencyGroup;
import org.adligo.tests4j.shared.common.Tests4J_Constants;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;

public class AllowedDependenciesAuditor {

	public static final String INSTANCE = "INSTANCE";

	/**
	 *  may return null
	 * @param trialDesc
	 * @param failures
	 * @return
	 */
	public static DependencyGroupAggregate audit(I_TrialDescription trialDesc,
			List<I_TrialFailure> failures, I_Tests4J_Memory memory) {
		
		Class<? extends I_AbstractTrial> trial = trialDesc.getTrialClass();
		AllowedDependencies ad = trial.getAnnotation(AllowedDependencies.class);
		if (ad != null) {
			Class<? extends I_DependencyGroup>[] groupClasses = ad.groups();
			if (groupClasses != null && groupClasses.length >= 1) {
				List<I_DependencyGroup> dgs = new ArrayList<I_DependencyGroup>();
				I_Tests4J_Log log =  memory.getLog();
				
				for (Class<? extends I_DependencyGroup> cls: groupClasses) {
					if (cls != null) {
						I_DependencyGroup dep = memory.getDependencyGroup(cls);
						if (dep != null) {
							dgs.add(dep);
						} else {
							try {
								Field field = cls.getField(INSTANCE);
								if (field != null) {
									dep = (I_DependencyGroup) field.get(null);
								}
							} catch (NoSuchFieldException e) {
								
								I_Tests4J_AnnotationMessages messages = Tests4J_Constants.CONSTANTS.getAnnotationMessages();
								log.onThrowable(new MemoryWarning(
										messages.getAllowedDependenciesIsNotASingletonWarning() +
										log.getLineSeperator() + 
										cls.getName()));
							} catch (SecurityException | IllegalArgumentException | 
									IllegalAccessException e) {
								log.onThrowable(e);
							}
							
							if (dep == null) {
								try {
									//try to cache it
									dep = memory.getDependencyGroup(cls);
									if (dep == null) {
										I_DependencyGroup dg = cls.newInstance();
										memory.putIfAbsent(cls, dg);
									}
								} catch (Exception x) {
									failures.add(new TrialFailure("Problem creating new " +
											cls.getName(), x.getMessage()));
								}
							}
							if (dep != null) {
								dgs.add(dep);
							}
						}
					}
				}
				return new DependencyGroupAggregate(dgs);
			}
		}
		return null;
	}
}
