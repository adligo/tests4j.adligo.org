//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.17 at 03:26:33 PM CST 
//


package org.adligo.tests4j.run.xml.io.requirements.v1_0;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.adligo.tests4j.run.xml.io.requirements.v1_0 package. 
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

    private final static QName _Requirements_QNAME = new QName("http://www.adligo.org/tests4j/run/xml/io/requirements/v1_0", "requirements");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.adligo.tests4j.run.xml.io.requirements.v1_0
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RequirementsType }
     * 
     */
    public RequirementsType createRequirementsType() {
        return new RequirementsType();
    }

    /**
     * Create an instance of {@link SystemType }
     * 
     */
    public SystemType createSystemType() {
        return new SystemType();
    }

    /**
     * Create an instance of {@link ProjectType }
     * 
     */
    public ProjectType createProjectType() {
        return new ProjectType();
    }

    /**
     * Create an instance of {@link UseCasesPathType }
     * 
     */
    public UseCasesPathType createUseCasesPathType() {
        return new UseCasesPathType();
    }

    /**
     * Create an instance of {@link UseCasesPathNameType }
     * 
     */
    public UseCasesPathNameType createUseCasesPathNameType() {
        return new UseCasesPathNameType();
    }

    /**
     * Create an instance of {@link ImportedRequirementsType }
     * 
     */
    public ImportedRequirementsType createImportedRequirementsType() {
        return new ImportedRequirementsType();
    }

    /**
     * Create an instance of {@link PartitionType }
     * 
     */
    public PartitionType createPartitionType() {
        return new PartitionType();
    }

    /**
     * Create an instance of {@link ActorType }
     * 
     */
    public ActorType createActorType() {
        return new ActorType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequirementsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.adligo.org/tests4j/run/xml/io/requirements/v1_0", name = "requirements")
    public JAXBElement<RequirementsType> createRequirements(RequirementsType value) {
        return new JAXBElement<RequirementsType>(_Requirements_QNAME, RequirementsType.class, null, value);
    }

}
