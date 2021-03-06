package org.adligo.tests4j.shared.i18n;


/**
 * ok the main constants class,
 * for i18n internationalization
 * only two levels deep right now
 * so either return a interface or a string.
 * 
 * If a interface is returned, it should only return strings
 * and not other interfaces.
 * Also no multiple interface inheritance 
 * in the interfaces returned.   Try to keep this nice and simple.
 * 
 * Also all interfaces must have at least 3 methods, to keep the .java files down.
 * 
 * This package will eventually contain the property files for 
 * other languages, also this package will contain
 * extension classes for Gwt (i.e. I_Tests4J_ConstantsGwt) in the 
 * tests4j_4gwt project which extend the Gwt Constants class.
 * This should allow JSE and GWT to share the property files for 
 * all languages.  
 * @author scott
 *
 */
public interface I_Tests4J_Constants {

	
	/**
	 * The order in which the language is formatted,
	 * left to right or right to left (Hebrew and Arabic)
	 * @return
	 */
	public boolean isLeftToRight();
	public String getPrefix();
	public String getHeader();
	
	public I_Tests4J_AnnotationMessages getAnnotationMessages();
	
	
	public I_Tests4J_AssertionInputMessages getAssertionInputMessages();
	public I_Tests4J_ResultMessages getResultMessages();
	public I_Tests4J_EclipseErrors getEclipseErrors();
	public I_Tests4J_LineDiffTextDisplayMessages getLineDiffTextDisplayMessages();
	public I_Tests4J_LogMessages getLogMessages();
	public I_Tests4J_ParamsReaderMessages getParamReaderMessages();
	public I_Tests4J_ReportMessages getReportMessages();
	public I_Tests4J_CoveragePluginMessages getCoveragePluginMessages();
	
	/**
	 * The at part of the stack traces.
	 * @return
	 */
	public String getAt();
	public String getBadConstuctor();
	public String getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames();
	public String getMethodBlockerRequiresABlockingClass();
	public String getMethodBlockerRequiresABlockingMethod();
	
	public String getNullParamsExceptionMessage();
	public String getNullListenerExceptionMessage();
	
	public String getTheMethodCanOnlyBeCalledBy_PartOne();
	public String getTheMethodCanOnlyBeCalledBy_PartTwo();
	public String getAnotherTests4J_InstanceIsRunningHere();
}
