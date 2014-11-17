package org.adligo.tests4j.run.xml_bindings.io;

import java.util.StringTokenizer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

/**
 * from this suggestion
 * https://jaxb.java.net/guide/Performance_and_thread_safety.html
 * @author scott
 *
 */
public class Tests4J_JaxbContexts {
    private static final String HTTP_WWW_ADLIGO_ORG = "http://www.adligo.org";
    private static final String USE_CASES_PKG_1_0 = "org.adligo.tests4j.run.xml.io.use_cases.v1_0";
    private static final String RUN_RESULT_PKG_1_0 = "org.adligo.tests4j.run.xml.io.trial_run_result.v1_0";
    private static final String TRIAL_RESULTS_PKG_1_0 = "org.adligo.tests4j.run.xml.io.trial_result.v1_0";
    private static final String TEST_RESULTS_PKG_1_0 = "org.adligo.tests4j.run.xml.io.test_result.v1_0";
    private static final String COVERAGE_PKG_1_0 = "org.adligo.tests4j.run.xml.io.coverage.v1_0";
    private static final String PARAMS_PKG_1_0 = "org.adligo.tests4j.run.xml.io.params.v1_0";
    
    public static final String COVERAGE_NS_1_0 = getURI(COVERAGE_PKG_1_0);
    public static final String TEST_RESULTS_NS_1_0 = getURI(TEST_RESULTS_PKG_1_0);
    public static final String TRIAL_RESULTS_NS_1_0 = getURI(TRIAL_RESULTS_PKG_1_0);
    public static final String RUN_RESULT_NS_1_0 = getURI(RUN_RESULT_PKG_1_0);
    public static final String USE_CASES_NS_1_0 = getURI(USE_CASES_PKG_1_0);
    public static final String PARAMS_NS_1_0 = getURI(PARAMS_PKG_1_0);
    
    public static final JAXBContext COVERAGE_CONTEXT_1_0 = getContext(COVERAGE_PKG_1_0);
    public static final JAXBContext TEST_RESULTS_CONTEXT_1_0 = getContext(TEST_RESULTS_PKG_1_0);
    public static final JAXBContext TRIAL_RESULTS_CONTEXT_1_0 = getContext(TRIAL_RESULTS_PKG_1_0);
    public static final JAXBContext RUN_RESULT_CONTEXT_1_0 = getContext(RUN_RESULT_PKG_1_0);
    public static final JAXBContext USE_CASES_CONTEXT_1_0 = getContext(USE_CASES_PKG_1_0);
    public static final JAXBContext PARAMS_CONTEXT_1_0 = getContext(PARAMS_PKG_1_0);

    
    private static final JAXBContext getContext(String namespace) {
      try {
        return JAXBContext.newInstance(namespace);
      } catch (JAXBException e) {
        throw new RuntimeException(namespace, e);
      }
    }
    
    private static final String getURI(String namespace) {
      StringTokenizer st = new StringTokenizer(namespace, ".");
      StringBuilder sb = new StringBuilder();
      sb.append(HTTP_WWW_ADLIGO_ORG);
      while (st.hasMoreTokens()) {
        String token = st.nextToken();
        sb.append("/");
        sb.append(token);
      }
      return  sb.toString();
    }
}
