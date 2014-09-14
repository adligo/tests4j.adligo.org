package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.HashSet;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.Tests4J_Constants;
import org.adligo.tests4j.models.shared.dependency.DependencyGroupBaseDelegate;
import org.adligo.tests4j.models.shared.dependency.NameOnlyDependencyGroup;
import org.adligo.tests4j.models.shared.en.Tests4J_AnnotationErrors;
import org.adligo.tests4j.models.shared.en.Tests4J_AssertionInputMessages;
import org.adligo.tests4j.models.shared.en.Tests4J_EclipseErrors;
import org.adligo.tests4j.models.shared.en.Tests4J_LineDiffTextDisplayMessages;
import org.adligo.tests4j.models.shared.en.Tests4J_ParamsReaderMessages;
import org.adligo.tests4j.models.shared.en.Tests4J_ReportMessages;
import org.adligo.tests4j.models.shared.en.Tests4J_ResultMessages;

public class Tests4J_EN_DependencyGroup extends DependencyGroupBaseDelegate {

	public Tests4J_EN_DependencyGroup() {
		Set<String> names = new HashSet<String>();
		
		add(names, Tests4J_AnnotationErrors.class);
		add(names, Tests4J_AssertionInputMessages.class);
		add(names, Tests4J_Constants.class);
		add(names, Tests4J_EclipseErrors.class);
		add(names, Tests4J_LineDiffTextDisplayMessages.class);
		add(names, Tests4J_ParamsReaderMessages.class);
		add(names, Tests4J_ReportMessages.class);
		add(names, Tests4J_ResultMessages.class);
		
		Tests4J_I18N_DependencyGroup dg = new Tests4J_I18N_DependencyGroup();
		names.addAll(dg.getClassNames());
		
		super.setDelegate(new NameOnlyDependencyGroup(names));
	}
	
	private void add(Set<String> names, Class<?> c) {
		names.add(c.getName());
	}
}
