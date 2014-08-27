package org.adligo.tests4j.gen.dependency_groups;

import java.io.File;
import java.io.PrintStream;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;

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
			out.println("\t\ttoRet.setClassName(" + constantLookup.get(clazz.getName()) + ");");
			I_ClassAttributes ca = caa.getAttributes();
			Set<I_FieldSignature> fs = ca.getFields();
			for (I_FieldSignature sig: fs) {
				String nextLine = "\t\ttoRet.addField(new FieldSignature(\"" + sig.getName() + "\", " +
						constantLookup.get(sig.getClassName()) + "));";
				out.println(nextLine);
			}
			
			Set<I_MethodSignature> ms = ca.getMethods();
			for (I_MethodSignature method: ms) {
				String nextLine = "\t\ttoRet.addMethod(new MethodSignature(\"" + method.getMethodName() + "\"";
				if (method.getParameters() >= 1) {
					nextLine = nextLine + ", "  + System.lineSeparator() + "\t\t\t" + getMethodParamsDefaults(method);
				}
				if (method.getReturnClassName() != null) {
					String type = constantLookup.get(method.getReturnClassName());
					if (type != null) {
						nextLine = nextLine + ", "  + System.lineSeparator() + "\t\t\t" + type + "";
					}
				}
				nextLine = nextLine + "));";
				out.println(nextLine);
				
			}
			out.println("\t\treturn new ClassAttributes(toRet);");
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
