package org.adligo.tests4j.gen.dependency_groups;

import java.io.PrintStream;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;

/**
 * this class generates a sibling
 * class for each class or interface
 * with _MockUse in the name.
 * 
 * @author scott
 *
 */
public class ClassUseGen {

	public void gen(ClassAndAttributes caa, PrintStream out, GenDepGroupContext ctx) {
		String packageName = ctx.getTrialPackageName();
		out.println("package " + packageName + ";");
		out.println("");
		String api = ctx.getApiVersion();
		if (!StringMethods.isEmpty(api)) {
			api = "_" + api;
		}
		Class<?> clazz = caa.getClazz();
		String pkgName = clazz.getPackage().getName();
		if (!packageName.equals(pkgName)) {
			out.println("import " + clazz.getName() + ";");
		}
		out.println("");
		out.println("public class " + clazz.getSimpleName() + api + "_MockUse {");
		out.println("");
		if (clazz.isInterface()) {
			out.println("\tpublic " + clazz.getSimpleName() + api +"_MockUse(" +clazz.getSimpleName() + " p) {");
			
			I_ClassAttributes ca = caa.getAttributes();
			Set<I_MethodSignature> ms = ca.getMethods();
			for (I_MethodSignature method: ms) {
				String nextLine = "\t\tp." + method.getMethodName() + "(" +
						getMethodParamsDefaults(method) + ");";
				out.println(nextLine);
				
			}
			
			out.println("\t}");
		} else {
			
		}
		out.println("}");
		
	}
	
	private String getMethodParamsDefaults(I_MethodSignature ms) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < ms.getParameters(); i++) {
			String param = ms.getParameterClassName(i);
			if (i >= 1) {
				sb.append(", ");
			}
			sb.append(DefaultUseGenTypes.TYPES.get(param));
		}
		return sb.toString();
	}
}
