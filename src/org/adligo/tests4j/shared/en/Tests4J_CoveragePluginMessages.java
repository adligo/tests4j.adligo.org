package org.adligo.tests4j.shared.en;

import org.adligo.tests4j.shared.i18n.I_Tests4J_CoveragePluginMessages;

/**
 * This class provides English to the coverage plug-in (i.e. tests4j_4jacoco.adligo.org)
 * for messages to the console.
 * @author scott
 *
 */
public class Tests4J_CoveragePluginMessages implements I_Tests4J_CoveragePluginMessages {
  public static final Tests4J_CoveragePluginMessages INSTANCE = new Tests4J_CoveragePluginMessages();
  
  private static final String THE_DEPENDENCIES_ARE_AS_FOLLOWS = "The dependencies are as follows;";
  private static final String THE_FOLLOWING_DEPENDENCIES_COULD_NOT_BE_ORDERED = 
      "The following dependencies could NOT be ordered;";
  private static final String THE_FOLLOWING_DEPENDENCIES_WERE_ORDERED_SUCCESSFULLY = 
      "The following dependencies were ordered successfully;";
  private static final String UNABLE_TO_FIND_DEPENDENCY_ORDER_FOR_THE_FOLLOWING_CLASS = 
      "Unable to find dependency order for the following class;";
  private static final String UNABLE_TO_INSTRUMENT_THE_FOLLOWING_CLASS = 
      "Unable to instrument the following class;";
  
  private Tests4J_CoveragePluginMessages() {
  }
  
  
  /* (non-Javadoc)
   * @see org.adligo.tests4j.shared.en.I_Tests4J_CoveragePluginMessages#getTheDependenciesAreAsFollows()
   */
  @Override
  public String getTheDependenciesAreAsFollows() {
    return THE_DEPENDENCIES_ARE_AS_FOLLOWS;
  } 
  /* (non-Javadoc)
   * @see org.adligo.tests4j.shared.en.I_Tests4J_CoveragePluginMessages#getTheFollowingDependenciesCouldNotBeOrdered()
   */
  @Override
  public String getTheFollowingDependenciesCouldNotBeOrdered() {
    return THE_FOLLOWING_DEPENDENCIES_COULD_NOT_BE_ORDERED;
  } 
  /* (non-Javadoc)
   * @see org.adligo.tests4j.shared.en.I_Tests4J_CoveragePluginMessages#getTheFollowingDependenciesWereOrderedSuccessfully()
   */
  @Override
  public String getTheFollowingDependenciesWereOrderedSuccessfully() {
    return THE_FOLLOWING_DEPENDENCIES_WERE_ORDERED_SUCCESSFULLY;
  } 
  /* (non-Javadoc)
   * @see org.adligo.tests4j.shared.en.I_Tests4J_CoveragePluginMessages#getUnableToFineDependencyOrderForTheFollowingClass()
   */
  @Override
  public String getUnableToFineDependencyOrderForTheFollowingClass() {
    return UNABLE_TO_FIND_DEPENDENCY_ORDER_FOR_THE_FOLLOWING_CLASS;
  }
  /* (non-Javadoc)
   * @see org.adligo.tests4j.shared.en.I_Tests4J_CoveragePluginMessages#getUnableToInstrumentTheFollowingClass()
   */
  @Override
  public String getUnableToInstrumentTheFollowingClass() {
    return UNABLE_TO_INSTRUMENT_THE_FOLLOWING_CLASS;
  }
}
