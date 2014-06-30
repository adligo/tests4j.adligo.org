package org.adligo.tests4j.run.discovery;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import org.adligo.tests4j.models.shared.trials.AdditionalInstrumentation;
import org.adligo.tests4j.models.shared.trials.I_Trial;
import org.adligo.tests4j.models.shared.trials.PackageScope;
import org.adligo.tests4j.models.shared.trials.SourceFileScope;

public class TopPackageSet {
	private Set<String> packages = new HashSet<String>();
	
	public void add(String p) {
		if (p != null) {
			if (!packages.contains(p)) {
				Iterator<String> pkgIt = packages.iterator();
				boolean replaced = false;
				while (pkgIt.hasNext()) {
					String pkg = pkgIt.next();
					if (pkg.contains(p)) {
						//we want the top most packages only
						pkgIt.remove();
						packages.add(p);
						replaced = true;
						break;
					} 
				}
				if (!replaced) {
					packages.add(p);
				}
			}
		}
	}
	
	public Set<String> get() {
		return Collections.unmodifiableSet(packages);
	}
	
	public static TopPackageSet getPackages(List<Class<?>> classes) {
		TopPackageSet packages = new TopPackageSet();
		for (Class<?> clazz: classes) {
			packages.add(clazz.getPackage().getName());
		}
		return packages;
	}
	
	@SuppressWarnings("unchecked")
	public static TopPackageSet getPackagesForMetadata(List<Class<?>> classes) {
		TopPackageSet packages = new TopPackageSet();
		for (Class<?> clazz: classes) {
			if (I_Trial.class.isAssignableFrom(clazz)) {
				addTrialClassScopes(packages,(Class<? extends I_Trial>) clazz);
			} 
		}
		return packages;
	}

	private static void addTrialClassScopes(TopPackageSet packages,
			Class<? extends I_Trial> clazz) {
		PackageScope ps = clazz.getAnnotation(PackageScope.class);
		if (ps != null) {
			String pkg = ps.packageName();
			packages.add(pkg);
		} else {
			SourceFileScope cs = clazz.getAnnotation(SourceFileScope.class);
			if (cs != null) {
				String pkg = cs.sourceClass().getPackage().getName();
				packages.add(pkg);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static TopPackageSet getPackagesForInstrumentation(List<Class<?>> classes) {
		TopPackageSet packages = new TopPackageSet();
		for (Class<?> clazz: classes) {
			if (I_Trial.class.isAssignableFrom(clazz)) {
				addTrialClassScopes(packages,(Class<? extends I_Trial>) clazz);
				AdditionalInstrumentation ai = clazz.getAnnotation(AdditionalInstrumentation.class);
				if (ai != null) {
					String pkgs = ai.javaPackages();
					StringTokenizer tokens = new StringTokenizer(pkgs, ",");
					while (tokens.hasMoreElements()) {
						packages.add(tokens.nextToken().trim());
					}
				}
			} else {
				packages.add(clazz.getPackage().getName());
			}
		}
		return packages;
	}
}
