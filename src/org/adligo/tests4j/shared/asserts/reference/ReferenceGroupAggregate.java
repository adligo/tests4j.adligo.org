package org.adligo.tests4j.shared.asserts.reference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * this is the runtime class used to figure out
 * if a particular dependency (class,field, or method call)
 * is in the allowed dependencies (others_).
 * @author scott
 *
 */
public class ReferenceGroupAggregate implements I_ReferenceGroup {
	private List<I_ReferenceGroup> others_;
	
	/**
	 * although this takes any kind of collection
	 * it makes sense to order the dependencies
	 * @param othersIn
	 */
	public ReferenceGroupAggregate(Collection<I_ReferenceGroup> othersIn) {
		others_ = new ArrayList<I_ReferenceGroup>();
		for (I_ReferenceGroup o: othersIn) {
			if (o != null) {
				others_.add(o);
			}
		}
		others_ = Collections.unmodifiableList(others_);
	}
	@Override
	public Set<String> getClassNames() {
		Set<String> toRet = new HashSet<String>();
		for (I_ReferenceGroup dg: others_) {
			toRet.addAll(dg.getClassNames());
		}
		return toRet;
	}

	@Override
	public List<I_ClassAttributes> getClassAttributes() {
		List<I_ClassAttributes> toRet = new ArrayList<I_ClassAttributes>();
		for (I_ReferenceGroup dg: others_) {
			toRet.addAll(dg.getClassAttributes());
		}
		return toRet;
	}

	@Override
	public Set<I_MethodSignature> getMethods(String className) {
		for (I_ReferenceGroup dg: others_) {
			Set<I_MethodSignature> toRet = dg.getMethods(className);
			if (toRet != null) {
				return toRet;
			}
		}
		return null;
	}

	@Override
	public Set<I_FieldSignature> getFields(String className) {
		for (I_ReferenceGroup dg: others_) {
			Set<I_FieldSignature> toRet = dg.getFields(className);
			if (toRet != null) {
				return toRet;
			}
		}
		return null;
	}

	@Override
	public boolean isInGroup(String className, I_MethodSignature method) {
		for (I_ReferenceGroup dg: others_) {
			boolean result = dg.isInGroup(className, method);
			if (result) {
				return result;
			}
		}
		return false;
	}

	@Override
	public boolean isInGroup(String className, I_FieldSignature field) {
		for (I_ReferenceGroup dg: others_) {
			boolean result = dg.isInGroup(className, field);
			if (result) {
				return result;
			}
		}
		return false;
	}

	@Override
	public boolean isInGroup(String className) {
		for (I_ReferenceGroup dg: others_) {
			boolean result = dg.isInGroup(className);
			if (result) {
				return result;
			}
		}
		return false;
	}
	@Override
	public Set<String> getSubGroupNames() {
		Set<String> toRet = new TreeSet<String>();
		for (I_ReferenceGroup grp: others_) {
			toRet.add(grp.getClass().getSimpleName());
		}
		return toRet;
	}

}
