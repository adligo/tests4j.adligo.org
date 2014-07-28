package org.adligo.tests4j.run.discovery;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import org.adligo.tests4j.models.shared.system.I_Tests4J_Listener;
import org.adligo.tests4j.models.shared.system.I_Tests4J_Log;

/**
 * a immutable class is a fast way to discover/queue dependencies of a class
 * using the java reflection api (which does NOT have getImports();)
 * and therefore could give incorrect results
 * for imported classes that are only used inside of a method
 * This issue could be fixed if Method had a method getVariables
 * which returned a Variable which had a type class.
 * 
 * @author scott
 *
 */
public class ClassDiscovery {
	/**
	 * it all depends on java. which we
	 * wouldn't want to instrument
	 */
	private Set<String> ignoredPackages = new HashSet<String>();
	private Map<String, DependencyMutant> dependencies = new HashMap<String, DependencyMutant>();
	private I_Tests4J_Log log;
	
	public ClassDiscovery(Collection<String> pIgnoredPackages, Class<?> pClass, I_Tests4J_Log pLog) {
		if (pIgnoredPackages != null) {
			ignoredPackages.addAll(pIgnoredPackages);
		}
		log = pLog;
		Stack<String> refTree = new Stack<String>(); 
		addDependencies(pClass, refTree);
	}
	
	/**
	 * recursive method to find all classes referenced 
	 * by this class.
	 * @param pClass
	 */
	public void addDependencies(Class<?> pClass, Stack<String> refTree) {
		String className = pClass.getName();
		if (refTree.contains(className)) {
			addReference(className, refTree);
			return;
		}
		if ("void".equals(className)) {
			return;
		}
		Package pkg = pClass.getPackage();
		if (pkg == null) {
			return;
		}
		String packageName = pkg.getName();
		if (isIgnored(packageName)) {
			return;
		}
		if (log.isLogEnabled(ClassDiscovery.class)) {
			log.log("adding dependencies for " + pClass.getName());
		}
		refTree.add(className);
		Field [] fields = pClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			Class<?> type = field.getType();
			addDependencies(type, refTree);
		}
		Class<?> [] interfaces = pClass.getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			addDependencies(interfaces[i], refTree);
		}
		Method [] methods = pClass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			Class<?> returnType =  method.getReturnType();
			addDependencies(returnType, refTree);
			Class<?> [] params = method.getParameterTypes();
			for (int j = 0; j < params.length; j++) {
				addDependencies(params[j], refTree);
			}
			Class<?> [] throwns = method.getExceptionTypes();
			for (int j = 0; j < throwns.length; j++) {
				addDependencies(throwns[j], refTree);
			}
			//method.get
		}
		String clazzName = pClass.getName();
		addReference(clazzName, refTree);
		refTree.pop();
	}

	private void addReference(String clazzName, Stack<String> refTree) {
		DependencyMutant dm = dependencies.get(clazzName);
		if (dm == null) {
			dm = new DependencyMutant();
			dm.setClazzName(clazzName);
			dependencies.put(clazzName, dm);
			if (refTree.size() != 1) {
				dm.addReference();
			}
		} else {
			dm.addReference();
		}
		
	}
	
	private boolean isIgnored(String p) {
		for (String pkg: ignoredPackages) {
			if (p.indexOf(pkg) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public PriorityQueue<I_Dependency> getDependencyQueue() {
		PriorityQueue<I_Dependency> queue = new PriorityQueue<I_Dependency>();
		Collection<DependencyMutant> dms =  dependencies.values();
		for (DependencyMutant dm: dms) {
			queue.add(new Dependency(dm));
		}
		
		return queue;
	}
}
