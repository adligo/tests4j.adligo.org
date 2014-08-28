package org.adligo.tests4j.gen.dependency_groups;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public class ConstantTrialGen {
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
		Class<?> groupFactoryClass = ctx.getGroupFactoryClass();
		
		Class<?> clazz = caa.getClazz();
		
		out.println("\t@Test");
		out.println("\tpublic void test" + clazz.getSimpleName() +"() {");
		out.println("\t\tI_ClassAttributes result = " + groupFactoryClass.getSimpleName() +
				".get" + clazz.getSimpleName() + "();");
		out.println("\t\tassertEquals(\"" + clazz.getName() + "\", result.getName());");
		//do constructors
		I_ClassAttributes ca = caa.getAttributes();
		Set<I_MethodSignature> ms = ca.getMethods();
		Set<I_FieldSignature> fs = ca.getFields();
		
		out.println("\t\tSet<I_FieldSignature> fs = result.getFields();");
		out.println("\t\tSet<I_MethodSignature> ms = result.getMethods();");
		for (I_MethodSignature method: ms) {
			if ("<init>".equals(method.getMethodName())) {
				String nextLine = "\t\tassertContains(ms, new MethodSignature(\"" + method.getMethodName() + "\"";
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
		}
		out.println("\t\tdelegates.delegate" + clazz.getSimpleName() + "MemberAsserts(result);");
		out.println("\t\tassertEquals(" + fs.size() + ", fs.size());");
		out.println("\t\tassertEquals(" + caa.getMethodsCount() + ", ms.size());");
		out.println("\t}");
		
		out.println("\tpublic void delegate" + clazz.getSimpleName() + "MemberAsserts(I_ClassAttributes result) {");
		Class<?> parent = clazz.getSuperclass();
		if (parent != null) {
			out.println("\t\tdelegate" + parent.getSimpleName() + "MemberAsserts(result);");
		}
		
		List<String> lines = new ArrayList<String>();
		lines.add("\t\tSet<I_FieldSignature> fs = result.getFields();");
		for (I_FieldSignature sig: fs) {
			lines.add("\t\tassertContains(fs, new FieldSignature(\"" + sig.getName() + "\", " +
					constantLookup.get(sig.getClassName()) + "));");
			
		}
		if (lines.size() >= 2) {
			for (String line: lines) {
				out.println(line);
			}
		}
		
		lines.clear();
		lines.add("\t\tSet<I_MethodSignature> ms = result.getMethods();");
		for (I_MethodSignature method: ms) {
			if ( !"<init>".equals(method.getMethodName())) {
				String nextLine = "\t\tassertContains(ms, new MethodSignature(\"" + method.getMethodName() + "\"";
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
				lines.add(nextLine);
			}
		}
		if (lines.size() >= 2) {
			for (String line: lines) {
				out.println(line);
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
