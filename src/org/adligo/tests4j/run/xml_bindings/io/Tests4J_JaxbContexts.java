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
    private static final String USE_CASES_PKG = "org.adligo.tests4j.run.xml.io.use_cases";
    private static final String RUN_RESULT_PKG = "org.adligo.tests4j.run.xml.io.trial_run_result";
    private static final String TRIAL_RESULTS_PKG = "org.adligo.tests4j.run.xml.io.trial_result";
    private static final String TEST_RESULTS_PKG = "org.adligo.tests4j.run.xml.io.test_result";
    private static final String COVERAGE_PKG = "org.adligo.tests4j.run.xml.io.coverage";
    public static final String COVERAGE_NS = getURI(COVERAGE_PKG);
    public static final String TEST_RESULTS_NS = getURI(TEST_RESULTS_PKG);
    public static final String TRIAL_RESULTS_NS = getURI(TRIAL_RESULTS_PKG);
    public static final String RUN_RESULT_NS = getURI(RUN_RESULT_PKG);
    public static final String USE_CASES_NS = getURI(USE_CASES_PKG);
    public static final JAXBContext COVERAGE_CONTEXT = getContext(COVERAGE_PKG);
    public static final JAXBContext TEST_RESULTS_CONTEXT = getContext(TEST_RESULTS_PKG);
    public static final JAXBContext TRIAL_RESULTS_CONTEXT = getContext(TRIAL_RESULTS_PKG);
    public static final JAXBContext RUN_RESULT_CONTEXT = getContext(RUN_RESULT_PKG);
    public static final JAXBContext USE_CASES_CONTEXT = getContext(USE_CASES_PKG);

    
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
