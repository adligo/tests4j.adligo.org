package org.adligo.tests4j.system.shared.trials;

/**
 * This a simple marker interface for a trial, which
 * flags the trial as a trial which is aware of the 
 * trial run.  If it is, tests4j will attempt to call
 * beforeAllTests(YourContextInterface p)
 * afterAllTests(YourContextInterface p)
 * 
 * Where YourContextInterface and YourContextInterfaceImplementation
 * are provided by parameters passed to tests4j.
 * 
 * The initial reason for this was to add 
 * a LocalBrowserWorld context which had the method;
 * obtainBrowserWindowSession(); 
 *    so that use cases trials could share open browser windows
 *    to simulate traffic in a way that correlates to the 
 *    way real users would.
 *    So authenticated users could be shared and only manipulate
 *    the applications menu/sub menu/panels/buttons/text exc. 
 *    
 *    TODO this is the initial file for this feature
 *       implement in I_Tests4J_Params and across tests4j
 * @author scott
 *
 */
public @interface TrialRunContext {

}
