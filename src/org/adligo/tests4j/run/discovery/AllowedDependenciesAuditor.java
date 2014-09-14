package org.adligo.tests4j.run.discovery;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.adligo.tests4j.models.shared.dependency.DependencyGroupAggregate;
import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;
import org.adligo.tests4j.models.shared.results.I_TrialFailure;
import org.adligo.tests4j.models.shared.results.TrialFailure;
import org.adligo.tests4j.models.shared.trials.AllowedDependencies;
import org.adligo.tests4j.models.shared.trials.I_AbstractTrial;

public class AllowedDependenciesAuditor {

	/**
	 *  may return null
	 * @param trialDesc
	 * @param failures
	 * @return
	 */
	public static DependencyGroupAggregate audit(I_TrialDescription trialDesc,
			List<I_TrialFailure> failures) {
		
		Class<? extends I_AbstractTrial> trial = trialDesc.getTrialClass();
		AllowedDependencies ad = trial.getAnnotation(AllowedDependencies.class);
		if (ad != null) {
			Class<? extends I_DependencyGroup>[] groupClasses = ad.groups();
			if (groupClasses != null && groupClasses.length >= 1) {
				List<I_DependencyGroup> dgs = new ArrayList<I_DependencyGroup>();
				for (Class<? extends I_DependencyGroup> cls: groupClasses) {
					if (cls != null) {
						try {
							dgs.add(cls.newInstance());
						} catch (Exception x) {
							failures.add(new TrialFailure("Problem creating new " +
									cls.getName(), x.getMessage()));
						}
					}
				}
				return new DependencyGroupAggregate(dgs);
			}
		}
		return null;
	}
}
