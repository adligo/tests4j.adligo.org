package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency.DependencyGroupAggregate;
import org.adligo.tests4j.models.shared.dependency.DependencyGroupBaseDelegate;
import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;
import org.adligo.tests4j.models.shared.dependency.NameOnlyDependencyGroup;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_Constants;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_EclipseErrors;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ParamsReaderMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.i18n.I_Tests4J_ResultMessages;

public class Tests4J_I18N_DependencyGroup extends DependencyGroupBaseDelegate {

	public Tests4J_I18N_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, I_Tests4J_AnnotationErrors.class);
		add(names, I_Tests4J_AssertionInputMessages.class);
		add(names, I_Tests4J_Constants.class);
		add(names, I_Tests4J_EclipseErrors.class);
		add(names, I_Tests4J_LineDiffTextDisplayMessages.class);
		add(names, I_Tests4J_ParamsReaderMessages.class);
		add(names, I_Tests4J_ReportMessages.class);
		add(names, I_Tests4J_ResultMessages.class);
		
		List<I_DependencyGroup> groups = new ArrayList<I_DependencyGroup>();
		groups.add(new NameOnlyDependencyGroup(names));
		groups.add(new AdligoGWT_DependencyGroup());
		DependencyGroupAggregate dga = new DependencyGroupAggregate(groups);
		super.setDelegate(dga);
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
