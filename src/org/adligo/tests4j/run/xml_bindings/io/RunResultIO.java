package org.adligo.tests4j.run.xml_bindings.io;

import org.adligo.tests4j.run.xml.io.trial_run_result.TrialRunResultType;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;

public class RunResultIO {
  
  public static void write(String file, TrialRunResultType result) throws IOException {
    try {
      Marshaller marshaller = Tests4J_JaxbContexts.RUN_RESULT_CONTEXT.createMarshaller();
      marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      marshaller.marshal(new JAXBElement<TrialRunResultType>(
          new QName(Tests4J_JaxbContexts.RUN_RESULT_NS,
          "trial_run_result"), TrialRunResultType.class, result), new File(file));
    } catch (Exception e) {
      throw new IOException(e);
    } 
  }
  @SuppressWarnings("unchecked")
  public static TrialRunResultType parse(String file) throws IOException {
    try {
      Unmarshaller jaxbUnmarshaller = Tests4J_JaxbContexts.RUN_RESULT_CONTEXT.createUnmarshaller();
      
      jaxbUnmarshaller.setSchema(Tests4J_SchemaLoader.INSTANCE.get());
      JAXBElement<TrialRunResultType> devType = (JAXBElement<TrialRunResultType>) 
          jaxbUnmarshaller.unmarshal(new File(file));
      return devType.getValue();
    } catch (JAXBException e) {
      throw new IOException(e);
    } 
  }
}
