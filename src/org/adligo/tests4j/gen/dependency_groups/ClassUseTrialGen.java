package org.adligo.tests4j.gen.dependency_groups;

import java.io.PrintStream;
import java.util.Set;

import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;
import org.adligo.tests4j.models.shared.trials.SourceFileScope;
import org.adligo.tests4j.models.shared.trials.SourceFileTrial;
import org.adligo.tests4j.models.shared.trials.Test;
import org.adligo.tests4j.models.shared.trials.TrialDelegate;

public class ClassUseTrialGen {

	public void gen(ClassAndAttributes caa, PrintStream out, GenDepGroupContext ctx) {
		String packageName = ctx.getTrialPackageName();
		out.println("package " + packageName + ";");
		out.println("");
		
		Class<?> clazz = caa.getClazz();
		String pkgName = clazz.getPackage().getName();
		if (!packageName.equals(pkgName)) {
			out.println("import " + clazz.getName() + ";");
		}
		out.println("import " + TrialDelegate.class.getName() + ";");
		out.println("import " + Test.class.getName() + ";");
		out.println("import " + SourceFileTrial.class.getName() + ";");
		out.println("import " + SourceFileScope.class.getName() + ";");
		
		out.println("");
		out.println("@SourceFileTrial (sourceClass=" + clazz.getSimpleName() + ".class)");
		out.println("public class " + clazz.getSimpleName() + "_MockUseTrial extends SourceFileTrial ");
		if (clazz.isInterface()) {
			out.println( System.lineSeparator() + "\timplements " + clazz.getSimpleName() + " {");
		}
		out.println("");
		if (clazz.isInterface()) {
			out.println("\tpublic " + clazz.getSimpleName() + "_MockUse(" +clazz.getSimpleName() + " p) {");
			out.println("\t\tprivate int methodsCalled = 0;");
			out.println("");
			I_ClassAttributes ca = caa.getAttributes();
			Set<I_MethodSignature> ms = ca.getMethods();
			for (I_MethodSignature method: ms) {
				String nextLine = "\t\tp." + method.getMethodName() + "(" +
						getMethodParams(method) + ") {";
				out.println("\t\tmethodsCalled++;");
				out.println("\t}");
				out.println(nextLine);
				
			}
			out.println("");
			out.println("\t@Test");
			out.println("\tpublic void testMethods() throws Exception {");
			out.println("\t\tnew " + clazz.getSimpleName() + "_MockUse(this);");
			out.println("\t\tassertEquals(" + ms.size() + ", methodsCalled);");
			out.println("\t}");
			
			out.println("");
			out.println("\tpublic void testMethods() throws Exception {");
			out.println("\t\tnew " + clazz.getSimpleName() + "_MockUse(this);");
			out.println("\t\tassertEquals(" + ms.size() + ", methodsCalled);");
			out.println("\t}");
		} else {
			
		}
		out.println("}");
		
	}
	
	private String getMethodParams(I_MethodSignature ms) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < ms.getParameters(); i++) {
			String param = ms.getParameterClassName(i);
			if (i >= 1) {
				sb.append(", ");
			}
			sb.append(param + " p"+i);
		}
		return sb.toString();
	}
}
