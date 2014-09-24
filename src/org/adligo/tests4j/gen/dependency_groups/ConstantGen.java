package org.adligo.tests4j.gen.dependency_groups;

import java.io.PrintStream;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;
import org.adligo.tests4j.models.shared.dependency.MethodSignature;
import org.adligo.tests4j.shared.common.Tests4J_System;

/**
 * this class generates a sibling
 * class for each class or interface
 * with _MockUse in the name.
 * 
 * @author scott
 *
 */
public class ConstantGen {
	private ConstantLookup constantLookup;
	/**
	 * this currently only prints
	 * out the method 
	 * @param caa
	 * @param out
	 * @param ctx
	 */
	public void gen(ClassAndAttributes caa, PrintStream out, GenDepGroupContext ctx) {
		constantLookup = ctx.getConstantLookup();
		String packageName = ctx.getTrialPackageName();
		Class<?> clazz = caa.getClazz();
		
			out.println("\tpublic static ClassAttributes get" + clazz.getSimpleName() +"() {");
			out.println("\t\tClassAttributesMutant toRet = new ClassAttributesMutant();");
			out.println("\t\ttoRet.setName(" + constantLookup.get(clazz.getName()) + ");");
			out.println("");
			
			I_ClassAttributes ca = caa.getAttributes();
			
			Set<I_MethodSignature> ms = ca.getMethods();
			if (!clazz.isInterface()) {
				out.println("\t\t//constructors");
				if (!ms.contains(new MethodSignature("<init>"))) {
					//everything has a hidden constructor with no arguments
					//which shows up in ASM but not reflection, 
					//even if there isn't a public one
					out.println("\t\ttoRet.addMethod(new MethodSignature(\"<init>\"));");
				}
			}
			for (I_MethodSignature method: ms) {
				if ("<init>".equals(method.getMethodName())) {
					String nextLine = "\t\ttoRet.addMethod(new MethodSignature(\"" + method.getMethodName() + "\"";
					if (method.getParameters() >= 1) {
						nextLine = nextLine + ", "  + Tests4J_System.lineSeperator() + "\t\t\t" + getMethodParamsDefaults(method);
					}
					if (method.getReturnClassName() != null) {
						String type = constantLookup.get(method.getReturnClassName());
						if (type != null) {
							nextLine = nextLine + ", "  + Tests4J_System.lineSeperator() + "\t\t\t" + type + "";
						}
					}
					nextLine = nextLine + "));";
					out.println(nextLine);
				}
			}
			out.println("");
			out.println("\t\tadd" + clazz.getSimpleName() + "Members(toRet);");
			out.println("\t\treturn new ClassAttributes(toRet);");
			out.println("\t}");
			out.println("");
			out.println("\tpublic static void add" + clazz.getSimpleName() + "Members(ClassAttributesMutant toRet) {");
			Class<?> parentClass = clazz.getSuperclass();
			if (parentClass != null) {
				out.println("\t\tadd" + parentClass.getSimpleName() + "Members(toRet);");
			}
			
			Set<I_FieldSignature> fs = ca.getFields();
			for (I_FieldSignature sig: fs) {
				String nextLine = "\t\ttoRet.addField(new FieldSignature(\"" + sig.getName() + "\", " +
						constantLookup.get(sig.getClassName()) + "));";
				out.println(nextLine);
			}
			
			for (I_MethodSignature method: ms) {
				if ( !"<init>".equals(method.getMethodName())) {
					String nextLine = "\t\ttoRet.addMethod(new MethodSignature(\"" + method.getMethodName() + "\"";
					if (method.getParameters() >= 1) {
						nextLine = nextLine + ", "  + Tests4J_System.lineSeperator() + "\t\t\t" + getMethodParamsDefaults(method);
					}
					if (method.getReturnClassName() != null) {
						String type = constantLookup.get(method.getReturnClassName());
						if (type != null) {
							nextLine = nextLine + ", "  + Tests4J_System.lineSeperator() + "\t\t\t" + type + "";
						}
					}
					nextLine = nextLine + "));";
					out.println(nextLine);
				}	
			}
			out.println("\t}");
		
	}
	
	private String getMethodParamsDefaults(I_MethodSignature ms) {
		StringBuilder sb = new StringBuilder();
		sb.append("new String[] {");
		for (int i = 0; i < ms.getParameters(); i++) {
			String param = ms.getParameterClassName(i);
			if (i >= 1) {
				sb.append(", ");
			}
			sb.append(constantLookup.get(param));
		}
		sb.append("}");
		return sb.toString();
	}
}
