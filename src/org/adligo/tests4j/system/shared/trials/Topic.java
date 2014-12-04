package org.adligo.tests4j.system.shared.trials;

/**
 * This annotation determines what is getting tested by Trials.
 * When it is used in a MetaTrial it is cascaded to
 * all Trials which don't override the setting
 * with their own Topic.
 * This was added to provide some trace ability between analysis effort
 * and testing effort, which has often been achieved by spreadsheets.
 * Using this annotation to map between analysis and testing
 * effort should;
 * 1) Greatly reduce effort/human error in tracking 
 *     if a particular analysis unit (use case) actually has tests.
 * 2) Allow for a tree like hierarchy for the parts of a system
 *     which is independent of the project dependency hierarchy
 *     which can eventually provide a system like intelligence4j
 *     with enough data for two basic visual ad hoc drill downs
 *     for systems and a independent project drill down which 
 *     can be linked to the systems.
 *     
 *     system
 *        |-> what analysis is tested
 *        |-> a system partition
 *        |           |-> the analysis units (use cases) tested
 *        |           |-> project
 *        
 *     project
 *        |->  the analysis units  (use cases) tested
 *        |->  the source files/apis tested
 *        
 * @diagram_sync with System_To_Project_Scopes on 12/2/2014
 * Terms;
 * Project: A group of java classes/packages which may be used
 *    in one or more software system.
 * System Partition: A section of a software system which 
 *      may correspond to a JVM tier, or any other 
 *      partitioning methodology.
 * System:  A software system as it is viewed by the end user,
 *     this includes simple systems like command line systems (ls, rm, cd),
 *     as well as large N-tier systems like seti@home, which may use 
 *     other systems.
 *     
 * @author scott
 *
 */
public @interface Topic {
  /**
   * This is the name of a software system
   * and may be null if this is targeting a project.
   */
  public String system();
  /**
   * This is the name of a system partition 
   * and may be null.  When it is null either 
   * this annotation is targeting a project or
   * this is targeting a top level system
   * (so it is the default system partition).
   * The partition name returned by this 
   * should use no slashes(/) in the name 
   * to signify a parent child partition structure;
   * (i.e. 
   *      browser
   *      browser/authenticate_user 
   *      
   *      cli
   *      cli/authenticate_user 
   * )
   */
  public String partition();
  /**
   * This is the name of a 
   * managed project (i.e. tests4j.adligo.org in git).
   */
  public String project();
}
