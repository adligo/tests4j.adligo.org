package org.adligo.tests4j.models.shared.metadata;

import org.adligo.tests4j.shared.common.StringMethods;
import org.adligo.tests4j.shared.i18n.I_Tests4J_AnnotationMessages;
import org.adligo.tests4j.shared.i18n.I_Tests4J_Constants;


/**
 * @see I_UseCaseBrief
 * @author scott
 *
 */
public class UseCaseBrief implements I_UseCaseBrief {
	private String name_;
	
	public UseCaseBrief(I_Tests4J_Constants constants, String name) {
		setup(constants, name);
	}

	protected void setup(I_Tests4J_Constants constants,String name) {
	  name_ = name;
		if (StringMethods.isEmpty(name)) {
			I_Tests4J_AnnotationMessages messages = constants.getAnnotationMessages();
			throw new IllegalArgumentException(messages.getUseCaseScopeEmptyName());
		}
	}

	public UseCaseBrief(I_Tests4J_Constants constants, I_UseCaseBrief p) {
		this(constants, p.getName());
	}
	
	
	public String getName() {
		return name_;
	}
	
	@Override
	public String toString() {
		return "UseCase [" + name_ + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name_ == null) ? 0 : name_.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UseCaseBrief other = (UseCaseBrief) obj;
		if (name_ == null) {
			if (other.name_ != null)
				return false;
		} else if (!name_.equals(other.name_))
			return false;
		return true;
	}

}
