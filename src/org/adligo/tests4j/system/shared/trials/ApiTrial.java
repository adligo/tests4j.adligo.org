package org.adligo.tests4j.system.shared.trials;

import java.util.Set;

import org.adligo.tests4j.models.shared.coverage.I_PackageCoverage;
import org.adligo.tests4j.models.shared.coverage.I_SourceFileCoverage;
import org.adligo.tests4j.models.shared.results.I_ApiTrialResult;
import org.adligo.tests4j.shared.common.TrialType;

@TrialTypeAnnotation(type = TrialType.API_TRIAL_TYPE)
public class ApiTrial extends AbstractTrial implements I_ApiTrial  {
	/**
	 * 
	 * Override this method if you want to make
	 * assertions about code coverage;
	 * ie
	 * assertNotNull(p);
	 * assertGreaterThanOrEquals(11.0, p.getAssertionCount());
	 * if (p.
	 * assertGreaterThanOrEquals(50.00, p.getPercentageCoveredDouble());
	 * 
	 * Although keep in mide there is never any basis 
	 * for ordering api trials, and so they can have
	 * 'innacurate sourceClass code coverage from thread overlap',
	 * so assertions about a specific class could very a little bit;
	 * ie 
	 * public class Foo() {
	 *    private static final String NAME = "name";
	 *    
	 *    Foo() {}
	 *    
	 *    public String getName() {
	 *      return NAME;
	 *    }
	 * }
	 * public class Bar() {
	 * 	public static final Foo foo = new Foo();
	 * }
	 * 
	 * if a trial that tests bar runs, it may set the code coverage to true for 
	 * Foo's init method, then later on a trial that tests Foo runs,
	 * it will may not to set the code coverage to true for Foo's constructor,
	 * since it never actually calls it, sinc Bar's Foo is already linked to the variable.
	 * 
	 * Also the code coverage in here can be used to assert the stack
	 * of some of the execution of the trial, for instance;
	 * public class TriangleDrawer() implements I_Drawer {
	 *    private drawA() {
	 *     //some graphics calls
	 *    }
	 *    private drawB() {
	 *     //some graphics calls
	 *    }
	 *     private drawC() {
	 *     //some graphics calls
	 *    }
	 *    
	 *    public draw() {
	 *    	drawA();
	 *      drawB();
	 *      drawC();
	 *    }
	 * }
	 * 
	 * public class ShapeDrawer
	 *   public void draw(I_Drawer p) {
	 *      ((TriangleDrawer) p).draw();
	 *    }
	 * }
	 * The ShapeDrawerApiTrial calls the draw method,
	 * and it could assert that it called into the 
	 * TriangleDrawer's execution to assert inner
	 * class calls (constructors, exc).
	 * @param p
	 */
	public void afterTrialTests(I_ApiTrialResult p) {}
}
