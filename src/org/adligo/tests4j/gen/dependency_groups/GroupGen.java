package org.adligo.tests4j.gen.dependency_groups;

import java.util.List;

/**
 * this class will eventually generage
 * dependency code (dependency_group, 
 *    List<Class<with dependency_usage>>, dependecy_group_trial)
 *  So that a two point verification system can be 
 *  used for dependency groups, and so they can be
 *  auto generated (otherwise no one would probably ever use them).
 *  
 * @author scott
 *
 */
public class GroupGen {
	private GenDepGroupContext ctx;
	private ConstantGen constantGen = new ConstantGen();
	
	private ClassUseGen classUseGen = new ClassUseGen();
	private ClassUseTrialGen classUseTrialGen = new ClassUseTrialGen();
	
	/**
	 * note this doesn't take a PackageDiscovery
	 * @param classes
	 */
	public void gen(List<Class<?>> classes) {
		for (Class<?> c: classes) {
			ClassAndAttributes caa = new ClassAndAttributes(c);
			constantGen.gen(caa, System.out, ctx);
			//classUseGen.gen(caa, System.out, ctx);
			//classUseTrialGen.gen(caa, System.out, ctx);
		}
	}

	public GenDepGroupContext getCtx() {
		return ctx;
	}

	public void setCtx(GenDepGroupContext ctx) {
		this.ctx = ctx;
	}
	

}
