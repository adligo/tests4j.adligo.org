//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.02.23 at 04:34:57 PM CST 
//


package org.adligo.tests4j.run.xml.io.test_result.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * 				Note passed and ignored are indicated by the position
 * 				of a test_result_type node with in the trial_result_type.
 * 			
 * 
 * <p>Java class for test_result_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="test_result_type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="failure" type="{http://www.adligo.org/tests4j/run/xml/io/test_result/v1_0}test_failure_type" minOccurs="0"/&gt;
 *         &lt;element name="beforeTestOutput" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="output" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="afterTestOutput" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="asserts" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="uniqueAsserts" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "test_result_type", propOrder = {
    "failure",
    "beforeTestOutput",
    "output",
    "afterTestOutput"
})
public class TestResultType {

    protected TestFailureType failure;
    protected String beforeTestOutput;
    protected String output;
    protected String afterTestOutput;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "asserts", required = true)
    protected int asserts;
    @XmlAttribute(name = "uniqueAsserts")
    protected Integer uniqueAsserts;

    /**
     * Gets the value of the failure property.
     * 
     * @return
     *     possible object is
     *     {@link TestFailureType }
     *     
     */
    public TestFailureType getFailure() {
        return failure;
    }

    /**
     * Sets the value of the failure property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestFailureType }
     *     
     */
    public void setFailure(TestFailureType value) {
        this.failure = value;
    }

    /**
     * Gets the value of the beforeTestOutput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeforeTestOutput() {
        return beforeTestOutput;
    }

    /**
     * Sets the value of the beforeTestOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeforeTestOutput(String value) {
        this.beforeTestOutput = value;
    }

    /**
     * Gets the value of the output property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutput() {
        return output;
    }

    /**
     * Sets the value of the output property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutput(String value) {
        this.output = value;
    }

    /**
     * Gets the value of the afterTestOutput property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAfterTestOutput() {
        return afterTestOutput;
    }

    /**
     * Sets the value of the afterTestOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAfterTestOutput(String value) {
        this.afterTestOutput = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the asserts property.
     * 
     */
    public int getAsserts() {
        return asserts;
    }

    /**
     * Sets the value of the asserts property.
     * 
     */
    public void setAsserts(int value) {
        this.asserts = value;
    }

    /**
     * Gets the value of the uniqueAsserts property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUniqueAsserts() {
        return uniqueAsserts;
    }

    /**
     * Sets the value of the uniqueAsserts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUniqueAsserts(Integer value) {
        this.uniqueAsserts = value;
    }

}
