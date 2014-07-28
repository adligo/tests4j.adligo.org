package org.adligo.tests4j.run.discovery;

import java.util.List;

public interface I_ClassContainer {
	public  List<String> getClassesInPackage(String pkgName);
	public  List<String> getAllClasses();
}
