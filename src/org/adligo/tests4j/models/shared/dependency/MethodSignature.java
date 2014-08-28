package org.adligo.tests4j.models.shared.dependency;

import java.util.Arrays;

import org.adligo.tests4j.models.shared.common.StringMethods;

public class MethodSignature implements I_MethodSignature {
	public static final String REQUIRES_A_NON_EMPTY_METHOD_NAME_IN = MethodSignature.class.getSimpleName() + 
			" requires a non empty methodNameIn.";
	public static final String PARAMETERS_MAY_NOT_BE_EMPTY_STRINGS = MethodSignature.class.getSimpleName() + 
			" parameter class names may not be empty.";
	
	private String methodName;
	private String[] params;
	private String returnClass;
	
	public MethodSignature(I_MethodSignature other) {
		String methodNameIn = other.getMethodName();
		if (StringMethods.isEmpty(methodNameIn)) {
			throw new IllegalArgumentException(REQUIRES_A_NON_EMPTY_METHOD_NAME_IN);
		}
		methodName = methodNameIn;
		int paramsCount = other.getParameters();
		if (paramsCount >= 1) {
			params = new String[paramsCount];
			for (int i = 0; i < paramsCount; i++) {
				String p = other.getParameterClassName(i);
				if (StringMethods.isEmpty(p)) {
					throw new IllegalArgumentException(PARAMETERS_MAY_NOT_BE_EMPTY_STRINGS);
				}
				params[i] = p;
			}
		}
		returnClass = other.getReturnClassName();
		
	}
	
	public MethodSignature(String methodNameIn) {
		this(methodNameIn, null, null);
	}
	
	public MethodSignature(String methodNameIn, String [] paramsIn) {
		this(methodNameIn, paramsIn, null);
	}
	
	public MethodSignature(String methodNameIn, String returnClassNameIn) {
		this(methodNameIn, null, returnClassNameIn);
	}
	
	public MethodSignature(String methodNameIn, String [] paramsIn, String returnClassNameIn) {
		if (StringMethods.isEmpty(methodNameIn)) {
			throw new IllegalArgumentException(REQUIRES_A_NON_EMPTY_METHOD_NAME_IN);
		}
		methodName = methodNameIn;
		if (paramsIn != null) {
			if (paramsIn.length >= 1) {
				params = new String[paramsIn.length];
				for (int i = 0; i < paramsIn.length; i++) {
					String p = paramsIn[i];
					if (StringMethods.isEmpty(p)) {
						throw new IllegalArgumentException(PARAMETERS_MAY_NOT_BE_EMPTY_STRINGS);
					}
					params[i] = p;
				}
			}
		}
		returnClass = returnClassNameIn;
	}
	

	@Override
	public String getMethodName() {
		return methodName;
	}

	@Override
	public int getParameters() {
		if (params == null) {
			return 0;
		}
		return params.length;
	}
	
	@Override
	public String getParameterClassName(int whichOne) {
		if (whichOne < 0 || params == null) {
			return null;
		}
		if (whichOne > params.length) {
			return null;
		}
		return params[whichOne];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		if (params != null) {
			result = prime * result + Arrays.hashCode(params);
		}
		result = prime * result
				+ ((returnClass == null) ? 0 : returnClass.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try {
			I_MethodSignature other = (I_MethodSignature) obj;
			if (methodName == null) {
				if (other.getMethodName() != null)
					return false;
			} else if (!methodName.equals(other.getMethodName()))
				return false;
			if (returnClass == null) {
				if (other.getReturnClassName() != null)
					return false;
			} else if (!returnClass.equals(other.getReturnClassName()))
				return false;
			if (params == null) {
				if (other.getParameters() == 0) {
					return true;
				} else {
					return false;
				}
			}
			if (params.length != other.getParameters()) {
				//diff parameter number
				return false;
			} 
			for (int i = 0; i < params.length; i++) {
				String otherClassName = other.getParameterClassName(i);
				String thisPName = params[i];
				if (!thisPName.equals(otherClassName)) {
					return false;
				}
			}
		} catch (ClassCastException x) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (returnClass == null) {
			sb.append("void");
		} else {
			sb.append(returnClass);
		}
		sb.append(" " + methodName + "(");
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (i >= 1) {
					sb.append(", ");
				}
				sb.append(params[i]);
			}
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String getReturnClassName() {
		return returnClass;
	}

	@Override
	public int compareTo(I_MethodSignature o) {
		int toRet = toString().compareTo(o.toString());
		if (toRet != 0) {
			return toRet;
		}
		
		return toRet;
	}

}
