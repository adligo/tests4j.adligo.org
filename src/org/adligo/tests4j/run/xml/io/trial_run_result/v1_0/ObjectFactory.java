//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.17 at 03:26:33 PM CST 
//


package org.adligo.tests4j.run.xml.io.trial_run_result.v1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.adligo.tests4j.run.xml.io.trial_run_result.v1_0 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _TrialRunResult_QNAME = new QName("http://www.adligo.org/tests4j/run/xml/io/trial_run_result/v1_0", "trial_run_result");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.adligo.tests4j.run.xml.io.trial_run_result.v1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TrialRunResultType }
     * 
     */
    public TrialRunResultType createTrialRunResultType() {
        return new TrialRunResultType();
    }

    /**
     * Create an instance of {@link TrialTreeType }
     * 
     */
    public TrialTreeType createTrialTreeType() {
        return new TrialTreeType();
    }

    /**
     * Create an instance of {@link TrialGroupType }
     * 
     */
    public TrialGroupType createTrialGroupType() {
        return new TrialGroupType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TrialRunResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.adligo.org/tests4j/run/xml/io/trial_run_result/v1_0", name = "trial_run_result")
    public JAXBElement<TrialRunResultType> createTrialRunResult(TrialRunResultType value) {
        return new JAXBElement<TrialRunResultType>(_TrialRunResult_QNAME, TrialRunResultType.class, null, value);
    }

}
