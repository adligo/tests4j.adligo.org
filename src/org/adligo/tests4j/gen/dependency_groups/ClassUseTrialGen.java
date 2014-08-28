package org.adligo.tests4j.gen.dependency_groups;

import java.io.PrintStream;
import java.util.List;
import java.util.Set;

import org.adligo.tests4j.models.shared.common.StringMethods;
import org.adligo.tests4j.models.shared.dependency.I_ClassAttributes;
import org.adligo.tests4j.models.shared.dependency.I_ClassDependencies;
import org.adligo.tests4j.models.shared.dependency.I_FieldSignature;
import org.adligo.tests4j.models.shared.dependency.I_MethodSignature;
import org.adligo.tests4j.models.shared.results.I_SourceFileTrialResult;
import org.adligo.tests4j.models.shared.trials.SourceFileScope;
import org.adligo.tests4j.models.shared.trials.SourceFileTrial;
import org.adligo.tests4j.models.shared.trials.Test;
import org.adligo.tests4j.models.shared.trials.TrialDelegate;

public class ClassUseTrialGen {

	public void gen(ClassAndAttributes caa, PrintStream out, GenDepGroupContext ctx) {
		String packageName = ctx.getTrialPackageName();
		String api = ctx.getApiVersion();
		if (!StringMethods.isEmpty(api)) {
			api = "_" + api;
		}
		out.println("package " + packageName + ";");
		out.println("");
		
		Class<?> clazz = caa.getClazz();
		String pkgName = clazz.getPackage().getName();
		if (!packageName.equals(pkgName)) {
			out.println("import " + clazz.getName() + ";");
		}
		Class<?> groupFactoryClass = ctx.getGroupFactoryClass();
		out.println("import " + groupFactoryClass.getName() + ";");
		out.println("import " + TrialDelegate.class.getName() + ";");
		out.println("import " + Test.class.getName() + ";");
		out.println("import " + SourceFileTrial.class.getName() + ";");
		out.println("import " + SourceFileScope.class.getName() + ";");
		out.println("import " + I_SourceFileTrialResult.class.getName() + ";");
		out.println("import " + I_FieldSignature.class.getName() + ";");
		out.println("import " + I_MethodSignature.class.getName() + ";");
		out.println("import " + I_SourceFileTrialResult.class.getName() + ";");
		out.println("import " + I_ClassDependencies.class.getName() + ";");
		out.println("import " + I_ClassAttributes.class.getName() + ";");
		
		out.println("");
		out.println("@SourceFileTrial (sourceClass=" + clazz.getSimpleName() + api +"_MockUse.class)");
		if (clazz.isInterface()) {
			out.println("public class " + clazz.getSimpleName() + api +"_UseTrial extends SourceFileTrial ");
			
			out.println("  implements " + clazz.getSimpleName() + " {");
			out.println("");
			
			out.println("\tprivate int methodsCalled = 0;");
			out.println("");
			I_ClassAttributes ca = caa.getAttributes();
			Set<I_MethodSignature> ms = ca.getMethods();
			for (I_MethodSignature method: ms) {
				String nextLine = "\tpublic " + method.getReturnClassName() + " " + method.getMethodName() + "(" +
						getMethodParams(method) + ") {";
				out.println(nextLine);
				out.println("\t\tmethodsCalled++;");
				out.println("\t}");
				
			}
			out.println("");
			out.println("\t@Test");
			out.println("\tpublic void testMethods() throws Exception {");
			out.println("\t\tnew " + clazz.getSimpleName() + "_MockUse(this);");
			out.println("\t\tassertEquals(" + ms.size() + ", methodsCalled);");
			out.println("\t}");
			
			out.println("");
			out.println("\tpublic void afterTrialTests(I_SourceFileTrialResult p) {");
			out.println("\t\tI_ClassDependencies cRefs = p.getDependencies();");
			out.println("\t\tif (cRefs == null) {");
			out.println("\t\t\treturn;");
			out.println("\t\t}");
			out.println("\t\tList<I_ClassAttributes> refs = cRefs.getReferences();");
			out.println("\t\tI_ClassAttributes example = " +groupFactoryClass.getSimpleName() +
					".get" + clazz.getSimpleName() + "();");
			out.println("\t\tI_ClassAttributes ca = refs.get(0);");
			out.println("\t\tassertNotNull(ca);");
			out.println("\t\tassertEquals(example.getClassName(), ca.getClassName());");
			out.println("\t\tSet<I_FieldSignature> exampleFields = example.getFields();");
			out.println("\t\tSet<I_FieldSignature> fields = ca.getFields();");
			out.println("\t\tfor (I_FieldSignature sig: exampleFields) {");
			out.println("\t\t\tassertContains(fields, sig)");
			out.println("\t\t}");
			out.println("\t\tassertEquals(exampleFields.size(), fields.size());");
			
			out.println("\t\tSet<I_MethodSignature> exampleMethods = example.getMethods();");
			out.println("\t\tSet<I_MethodSignature> methods = ca.getMethods();");
			out.println("\t\tfor (I_MethodSignature method: exampleMethods) {");
			out.println("\t\t\tassertContains(methods, method)");
			out.println("\t\t}");
			out.println("\t\tassertEquals(exampleMethods.size(), methods.size());");
			
			
			out.println("\t}");
		} else {
			out.println("public class " + clazz.getSimpleName() + api +"_UseTrial extends SourceFileTrial {");
			out.println("");
			out.println("\t@Test");
			out.println("\tpublic void testMethods() throws Exception {");
			out.println("\t\tnew " + clazz.getSimpleName() + "_MockUse(this);");
			out.println("\t\tassertTrue(\"The trial should be able to create a" + 
					clazz.getSimpleName() + "_MockUse" + ".\",true);");
			out.println("\t}");
			
			
			out.println("");
			out.println("\tpublic void afterTrialTests(I_SourceFileTrialResult p) {");
			out.println("\t\tI_ClassAttributes refs = p.getSourceClassAttributes();");
			out.println("\t\tif (refs == null) {");
			out.println("\t\t\treturn;");
			out.println("\t\t}");
			out.println("\t\tassertEquals(" +
					clazz.getSimpleName() + "_MockUse.getClass().getName(), refs.getName());");
			
			out.println("\t\tI_ClassAttributes result = p.getAttributes(\"" + clazz.getName() + "\");");
			out.println("\t\tassertNotNull(result);");
			out.println("\t\tI_ClassAttributes example = " +groupFactoryClass.getSimpleName() +
					".get" + clazz.getSimpleName()  + "();");
			out.println("\t\tassertNotNull(ca);");
			out.println("\t\tassertEquals(example.getClassName(), result.getClassName());");
			out.println("\t\tSet<I_FieldSignature> exampleFields = example.getFields();");
			out.println("\t\tSet<I_FieldSignature> fields = result.getFields();");
			out.println("\t\tfor (I_FieldSignature sig: exampleFields) {");
			out.println("\t\t\tassertContains(fields, sig)");
			out.println("\t\t}");
			out.println("\t\tassertEquals(exampleFields.size(), fields.size());");
			out.println("");
			
			out.println("\t\tSet<I_MethodSignature> exampleMethods = example.getMethods();");
			out.println("\t\tSet<I_MethodSignature> methods = result.getMethods();");
			out.println("\t\tfor (I_MethodSignature method: exampleMethods) {");
			out.println("\t\t\tassertContains(methods, method)");
			out.println("\t\t}");
			out.println("\t\tassertEquals(exampleMethods.size(), methods.size());");
			
			
			out.println("\t}");
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
