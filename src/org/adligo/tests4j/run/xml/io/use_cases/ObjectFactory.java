//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.14 at 03:43:23 PM CST 
//


package org.adligo.tests4j.run.xml.io.use_cases;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.adligo.tests4j.run.xml.io.use_cases package. 
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

    private final static QName _System_QNAME = new QName("http://www.adligo.org/tests4j/run/xml/io/use_cases", "system");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.adligo.tests4j.run.xml.io.use_cases
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SystemUseCasesType }
     * 
     */
    public SystemUseCasesType createSystemUseCasesType() {
        return new SystemUseCasesType();
    }

    /**
     * Create an instance of {@link UseCaseType }
     * 
     */
    public UseCaseType createUseCaseType() {
        return new UseCaseType();
    }

    /**
     * Create an instance of {@link UseCaseBriefType }
     * 
     */
    public UseCaseBriefType createUseCaseBriefType() {
        return new UseCaseBriefType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SystemUseCasesType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.adligo.org/tests4j/run/xml/io/use_cases", name = "system")
    public JAXBElement<SystemUseCasesType> createSystem(SystemUseCasesType value) {
        return new JAXBElement<SystemUseCasesType>(_System_QNAME, SystemUseCasesType.class, null, value);
    }

}
