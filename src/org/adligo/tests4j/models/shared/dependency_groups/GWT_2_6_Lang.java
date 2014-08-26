package org.adligo.tests4j.models.shared.dependency_groups;

import org.adligo.tests4j.models.shared.dependency.ClassAttributesMutant;
import org.adligo.tests4j.models.shared.dependency.DependencyGroup;
import org.adligo.tests4j.models.shared.dependency.DependencyGroupMutant;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;

public class GWT_2_6_Lang extends DependencyGroup {
	
	public static DependencyGroupMutant create() {
		DependencyGroupMutant dgm = new DependencyGroupMutant();
		
		ClassAttributesMutant cam = new ClassAttributesMutant();
		cam.setClassName(JSE_1_7_Lang.APPENDABLE);
		cam.addMethod(new MethodSignature("append", new String[] {}, JSE_1_7_Lang.APPENDABLE));
		
		return dgm;
	}
	
	public GWT_2_6_Lang() {
		super(create());
	}
}
