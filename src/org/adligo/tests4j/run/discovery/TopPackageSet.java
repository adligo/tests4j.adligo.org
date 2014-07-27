package org.adligo.tests4j.run.discovery;

import java.util.Collection;
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

/**
 * this is a non thread safe model TopPackageSet,
 * as well as thread safe factory methods  
 * to discover packages for a group of classes.
 * 
 * The instances should contain the top packages
 * with the number of minimum dots in the package name.
 * 
 * @author scott
 * @deprecated this was used for the original way of
 * loading and then instrumenting classes, this is getting replaced by
 * PackageDiscovery
 * 
 */
public class TopPackageSet {
	private Set<String> packages = new HashSet<String>();
	private int minDots = 5;
	
	public void add(String p) {
		if (p != null) {
			if (!packages.contains(p)) {
				if (countDots(p) < minDots) {
					//it doesn't have the dots
					//add it only if there isn't already a child package
					Iterator<String> pkgIt = packages.iterator();
					while (pkgIt.hasNext()) {
						String pkg = pkgIt.next();
						if (pkg.equals(p)) {
							//it has the package
							return;
						} else if (pkg.contains(p)) {
							//don't add it, its a super package, with fewer dots
							return;
						} else if (p.contains(pkg)) {
							String nextChar = p.substring(pkg.length(), pkg.length() + 1);
							if (nextChar.equals(".")) {
								//replace with child package
								pkgIt.remove();
								packages.add(p);
								return;
							}
						}
					}
					//it didn't have a child so add it
					packages.add(p);
					return;
				} else {
					//it has the dots
					Iterator<String> pkgIt = packages.iterator();
					boolean replaced = false;
					while (pkgIt.hasNext()) {
						String pkg = pkgIt.next();
						if (pkg.equals(p)) {
							//has it already
							return;
						} else if (pkg.contains(p)) {
							//we want the top most packages only
							pkgIt.remove();
							packages.add(p);
							return;
						} else if (p.contains(pkg)) {
							String toAdd = p.substring(0, pkg.length());
							pkgIt.remove();
							packages.add(toAdd);
							return;
						} else if (!pkg.equals(p)) {
							StringBuilder sb = new StringBuilder();
							
							int shortest = pkg.length();
							if (p.length() < shortest) {
								shortest = p.length();
							}
							char [] pkgChars = pkg.toCharArray();
							char [] pChars = p.toCharArray();
							
							int lastDot = 0;
							int dots = 0;
							for (int i = 0; i < shortest; i++) {
								char e = pkgChars[i];
								char a = pChars[i];

								if (e == a) {
									sb.append(e);
									if (e == '.') {
										lastDot = i;
										dots++;
									}
								} else {
									break;
								}
							}
							String toAdd = sb.toString();
							if (dots > minDots) {
								//the dot is at the end of the line
								if (lastDot == toAdd.length() -1) {
									toAdd = toAdd.substring(0, lastDot);
									pkgIt.remove();
									packages.add(toAdd);
									return;
								} else {
									//other wise it is a different package name
									packages.add(toAdd);
									return;
								}
							} else {
								//the packages are different before the min dots
								packages.add(p);
								return;
							}
						}
					}
					if (!replaced) {
						packages.add(p);
					}
				}
				
			}
		}
	}
	
	
	private int countDots(String pkg) {
		char [] pChars = pkg.toCharArray();
		int dots = 0;
		for (int i = 0; i < pChars.length; i++) {
			char a = pChars[i];

			if (a == '.') {
				dots++;
			}
		}
		return dots;
	}

	public Set<String> get() {
		return Collections.unmodifiableSet(packages);
	}
	
	/**
	 * factory method to obtain each 
	 * clazz.getPackage().getName()
	 * @param classes
	 * @return
	 */
	public static TopPackageSet getPackages(Collection<Class<?>> classes) {
		TopPackageSet packages = new TopPackageSet();
		for (Class<?> clazz: classes) {
			Package pkg = clazz.getPackage();
			packages.add(pkg.getName());
		}
		return packages;
	}
	
	/**
	 * @threadsafe
	 * 
	 * @param packages
	 * @param clazz
	 */
	private static void addTrialTypePackage(TopPackageSet packages,
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
	
	/**
	 * @threadsafe
	 * 
	 * This is for the original way of 
	 * @param classes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static TopPackageSet getPackagesForInstrumentation(Collection<Class<?>> classes) {
		TopPackageSet packages = new TopPackageSet();
		for (Class<?> clazz: classes) {
			if (I_Trial.class.isAssignableFrom(clazz)) {
				addTrialTypePackage(packages,(Class<? extends I_Trial>) clazz);
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
