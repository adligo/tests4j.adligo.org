package org.adligo.tests4j.system.shared.trials;

/**
 * This is a simple marker annotation
 * which tells tests4j to remove the code coverage
 * from I_ApiTrialResults or I_UseCaseTrialResults
 * for a trial run with a I_CoveragePlugin.
 * 
 * This can be useful for;
 * 1) Saving disk space of build results
 *     A particular use case tests project A which calls
 *     project B which calls project C which calls a mock instance
 *     set up by the trial, the coverage information is
 *     quite verbose and no useful assertions are made about it
 *     so it is just using a lot of disk space 
 *     on the intelligence4j server in the test results file.
 * 2) Keeping the intention of a particular trial clear.
 *    A UseCaseTrial which tests a system,
 *       through browser/jdbc manipulation so the JVM
 *       of the tested code is not instrumented and
 *       therefore code coverage is not applicable.
 *       
 * @author scott
 *
 */
public @interface OmitCodeCoverage {

}
