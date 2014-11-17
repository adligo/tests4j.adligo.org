package org.adligo.tests4j.run.xml_bindings.io;

import org.adligo.tests4j.run.xml.io.trial_result.v1_0.TrialResultType;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

public class TrialResultIO {
  
  public static void write(String file, TrialResultType result) throws IOException {
    try {
      Marshaller marshaller = Tests4J_JaxbContexts.TRIAL_RESULTS_CONTEXT_1_0.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.marshal(new JAXBElement<TrialResultType>(
          new QName(Tests4J_JaxbContexts.TRIAL_RESULTS_NS_1_0,
          "result"), TrialResultType.class, result), new File(file));
    } catch (Exception e) {
      throw new IOException(e);
    } 
  }
  @SuppressWarnings("unchecked")
  public static TrialResultType parse(String file) throws IOException {
    try {
      Unmarshaller jaxbUnmarshaller = Tests4J_JaxbContexts.TRIAL_RESULTS_CONTEXT_1_0.createUnmarshaller();
      
      jaxbUnmarshaller.setSchema(Tests4J_SchemaLoader.INSTANCE.get());
      JAXBElement<TrialResultType> devType = (JAXBElement<TrialResultType>) 
          jaxbUnmarshaller.unmarshal(new File(file));
      return devType.getValue();
    } catch (JAXBException e) {
      throw new IOException(e);
    } 
  }
}
