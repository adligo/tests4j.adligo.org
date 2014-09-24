package org.adligo.tests4j.models.shared.dependency_groups.adligo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency.DependencyGroupAggregate;
import org.adligo.tests4j.models.shared.dependency.DependencyGroupBaseDelegate;
import org.adligo.tests4j.models.shared.dependency.I_DependencyGroup;
import org.adligo.tests4j.models.shared.dependency.NameOnlyDependencyGroup;

public class Tests4J_DependencyGroup extends DependencyGroupBaseDelegate {

	public void setupDelegates(Set<String> names) {
		List<I_DependencyGroup> groups = new ArrayList<I_DependencyGroup>();
		groups.add(new NameOnlyDependencyGroup(names));
		groups.add(new AdligoGWT_DependencyGroup());
		DependencyGroupAggregate dga = new DependencyGroupAggregate(groups);
		super.setDelegate(dga);
	}
}
