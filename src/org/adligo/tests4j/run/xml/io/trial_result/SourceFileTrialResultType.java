//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.16 at 04:04:16 PM CST 
//


package org.adligo.tests4j.run.xml.io.trial_result;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for source_file_trial_result_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="source_file_trial_result_type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="common" type="{http://www.adligo.org/tests4j/run/xml/io/trial_result}common_result_type"/&gt;
 *         &lt;element name="coverage" type="{http://www.adligo.org/tests4j/run/xml/io/trial_result}inner_source_file_coverage_type" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "source_file_trial_result_type", propOrder = {
    "common",
    "coverage"
})
public class SourceFileTrialResultType {

    @XmlElement(required = true)
    protected CommonResultType common;
    protected InnerSourceFileCoverageType coverage;

    /**
     * Gets the value of the common property.
     * 
     * @return
     *     possible object is
     *     {@link CommonResultType }
     *     
     */
    public CommonResultType getCommon() {
        return common;
    }

    /**
     * Sets the value of the common property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonResultType }
     *     
     */
    public void setCommon(CommonResultType value) {
        this.common = value;
    }

    /**
     * Gets the value of the coverage property.
     * 
     * @return
     *     possible object is
     *     {@link InnerSourceFileCoverageType }
     *     
     */
    public InnerSourceFileCoverageType getCoverage() {
        return coverage;
    }

    /**
     * Sets the value of the coverage property.
     * 
     * @param value
     *     allowed object is
     *     {@link InnerSourceFileCoverageType }
     *     
     */
    public void setCoverage(InnerSourceFileCoverageType value) {
        this.coverage = value;
    }

}
