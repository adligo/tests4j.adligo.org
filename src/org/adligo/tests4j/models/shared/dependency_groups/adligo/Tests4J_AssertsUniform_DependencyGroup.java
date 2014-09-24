package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.asserts.uniform.Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluationMutant;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.EvaluatorLookupMutant;
import org.adligo.tests4j.models.shared.asserts.uniform.I_Evaluation;
import org.adligo.tests4j.models.shared.asserts.uniform.I_EvaluatorLookup;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformAssertionEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionCommand;
import org.adligo.tests4j.models.shared.asserts.uniform.I_UniformThrownAssertionEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.StringUniformEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.ThrowableUniformEvaluator;
import org.adligo.tests4j.models.shared.asserts.uniform.UniformThrownAssertionEvaluator;

public class Tests4J_AssertsUniform_DependencyGroup extends Tests4J_DependencyGroup {

	public Tests4J_AssertsUniform_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, Evaluation.class);
		add(names, EvaluationMutant.class);
		add(names, EvaluatorLookup.class);
		add(names, EvaluatorLookupMutant.class);
		
		add(names, I_Evaluation.class);
		add(names, I_EvaluatorLookup.class);
		
		add(names, I_UniformAssertionCommand.class);
		add(names, I_UniformAssertionEvaluator.class);
		add(names, I_UniformThrownAssertionCommand.class);
		add(names, I_UniformThrownAssertionEvaluator.class);
		
		add(names, StringUniformEvaluator.class);
		add(names, ThrowableUniformEvaluator.class);
		add(names, UniformThrownAssertionEvaluator.class);
		
		Tests4J_AssertsLineText_DependencyGroup dg = new Tests4J_AssertsLineText_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		setupDelegates(names);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
