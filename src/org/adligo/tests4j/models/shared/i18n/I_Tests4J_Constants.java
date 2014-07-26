package org.adligo.tests4j.models.shared.i18n;


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
 * @author scott
 *
 */
public interface I_Tests4J_Constants {
	public static final String PREFIX = "Tests4J";
	public static final String HEADER = ": ";
	public static final String PREFIX_HEADER = PREFIX + HEADER;
	
	public I_Tests4J_AnnotationErrors getAnnotationErrors();
	
	
	public I_Tests4J_AssertionInputMessages getAssertionInputMessages();
	public I_Tests4J_AssertionResultMessages getAssertionResultMessages();
	public I_Tests4J_EclipseErrors getEclipseErrors();
	public I_Tests4J_LineDiffTextDisplayMessages getLineDiffTextDisplayMessages();
	public I_Tests4J_ParamReaderMessages getParamReaderMessages();
	public I_Tests4J_ReportMessages getReportMessages();
	
	public String getTheMethodCanOnlyBeCalledBy_PartOne();
	public String getTheMethodCanOnlyBeCalledBy_PartTwo();
	public String getMethodBlockerRequiresAtLeastOneAllowedCallerClassNames();
	public String getNullParamsExceptionMessage();
	public String getNullListenerExceptionMessage();
	public String getBadConstuctor();
}
