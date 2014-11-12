package org.adligo.tests4j.run.xml_bindings.io;

import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class Tests4J_SchemaLoader implements LSResourceResolver {
  public static final Tests4J_SchemaLoader INSTANCE = new Tests4J_SchemaLoader();
  public static final String XML_PACKAGE = "/org/adligo/tests4j/run/xml/";
  public static final String COVERAGE_SCHEMA = XML_PACKAGE + "coverage.xsd";
  public static final String RUN_RESULTS_SCHEMA = XML_PACKAGE + "trial_run_results.xsd";
  public static final String TEST_RESULTS_SCHEMA = XML_PACKAGE + "test_result.xsd";
  public static final String TRIAL_RESULTS_SCHEMA = XML_PACKAGE + "trial_result.xsd";
  public static final String USE_CASES_SCHEMA = XML_PACKAGE + "use_cases.xsd";
  
  private DOMImplementationRegistry registry;
  private DOMImplementationLS domImplementationLS;
  private SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
  private Map<String,String> namespaceToSchema = new HashMap<String,String>();
  private Map<String,LSInput> namespaceToLSInput = new HashMap<String,LSInput>();
  private Schema schema;
  
  private Tests4J_SchemaLoader() {
    try {

      registry = DOMImplementationRegistry.newInstance();
      domImplementationLS = (DOMImplementationLS) registry
              .getDOMImplementation("LS 3.0");

    } catch (ClassCastException | ClassNotFoundException | InstantiationException | 
        IllegalAccessException e) {
        e.printStackTrace();
    }
    
    namespaceToSchema.put(Tests4J_JaxbContexts.COVERAGE_NS, COVERAGE_SCHEMA);
    namespaceToSchema.put(Tests4J_JaxbContexts.RUN_RESULTS_NS, RUN_RESULTS_SCHEMA);
    namespaceToSchema.put(Tests4J_JaxbContexts.TEST_RESULTS_NS, TEST_RESULTS_SCHEMA);
    namespaceToSchema.put(Tests4J_JaxbContexts.USE_CASES_NS, USE_CASES_SCHEMA);
    namespaceToSchema.put(Tests4J_JaxbContexts.TRIAL_RESULTS_NS, TRIAL_RESULTS_SCHEMA);
    
    factory.setResourceResolver(this);  
    
    try {
      load(new Source[] {
          new StreamSource(Tests4J_SchemaLoader.class.getResourceAsStream(COVERAGE_SCHEMA)),
          new StreamSource(Tests4J_SchemaLoader.class.getResourceAsStream(RUN_RESULTS_SCHEMA)),
          new StreamSource(Tests4J_SchemaLoader.class.getResourceAsStream(TEST_RESULTS_SCHEMA)),
          new StreamSource(Tests4J_SchemaLoader.class.getResourceAsStream(USE_CASES_SCHEMA)),
          new StreamSource(Tests4J_SchemaLoader.class.getResourceAsStream(TRIAL_RESULTS_SCHEMA))
          
      });
    } catch (IOException x) {
      x.printStackTrace();
    }
    
  }
  private void load(Source [] sources) throws IOException {
   InputStream in  = null;
    try {
       factory.newSchema(sources);
    } catch (SAXException x) {
      throw new IOException(x);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException e) {
          //do nothing
        }
      }
    }
  }
  
  public Schema get() {
    return schema;
  }
  
  @Override
  public LSInput resolveResource(String type, String namespaceURI,
          String publicId, String systemId, String baseURI) {
    
      LSInput toRet = namespaceToLSInput.get(namespaceURI);
      if (toRet != null) {
        return toRet;
      }
      
      LSInput ret = domImplementationLS.createLSInput();
      String schemaResource = namespaceToSchema.get(namespaceURI);
      if (schemaResource != null) {
        InputStream in = Tests4J_SchemaLoader.class.getResourceAsStream(schemaResource);
        ret.setByteStream(in);
        //ret.setSystemId(systemId);
        namespaceToLSInput.put(namespaceURI, ret);
        return ret;
      }

      return null;
  }
}
