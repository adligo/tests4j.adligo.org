package org.adligo.tests4j.run.discovery;

import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.run.common.I_Memory;
import org.adligo.tests4j.shared.asserts.reference.AllowedReferences;
import org.adligo.tests4j.shared.asserts.reference.I_ReferenceGroup;
import org.adligo.tests4j.shared.asserts.reference.ReferenceGroupAggregate;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.shared.output.I_Tests4J_Log;
import org.adligo.tests4j.system.shared.trials.I_AbstractTrial;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AllowedDependenciesAuditor {

	public static final String INSTANCE = "INSTANCE";

	/**
	 *  may return null
	 * @param trialDesc
	 * @param failures
	 * @return
	 */
	public static ReferenceGroupAggregate audit(I_TrialDescription trialDesc,
			List<I_TrialFailure> failures, I_Memory memory) {
		
		Class<? extends I_AbstractTrial> trial = trialDesc.getTrialClass();
		AllowedReferences ad = trial.getAnnotation(AllowedReferences.class);
		if (ad != null) {
			Class<? extends I_ReferenceGroup>[] groupClasses = ad.groups();
			if (groupClasses != null && groupClasses.length >= 1) {
				List<I_ReferenceGroup> dgs = new ArrayList<I_ReferenceGroup>();
				I_Tests4J_Log log =  memory.getLog();
				
				for (Class<? extends I_ReferenceGroup> cls: groupClasses) {
					if (cls != null) {
						I_ReferenceGroup dep = memory.getDependencyGroup(cls);
						if (dep != null) {
							dgs.add(dep);
						} else {
							try {
								Field field = cls.getField(INSTANCE);
								if (field != null) {
									dep = (I_ReferenceGroup) field.get(null);
								}
							} catch (NoSuchFieldException e) {
								I_Tests4J_Constants constants = memory.getConstants();
								I_Tests4J_AnnotationMessages messages = constants.getAnnotationMessages();
								log.onThrowable(new MemoryWarning(
										messages.getAllowedDependenciesIsNotASingletonWarning() +
										log.lineSeparator() + 
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
										I_ReferenceGroup dg = cls.newInstance();
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
				return new ReferenceGroupAggregate(dgs);
			}
		}
		return null;
	}
}
