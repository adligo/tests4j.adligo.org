//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.04 at 03:31:14 AM CST 
//


package org.adligo.tests4j.run.xml.io.trial_result.v1_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.adligo.tests4j.run.xml.io.coverage.v1_0.PackageCoverageType;


/**
 * <p>Java class for api_trial_result_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="api_trial_result_type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="common" type="{http://www.adligo.org/tests4j/run/xml/io/trial_result/v1_0}common_result_type"/&gt;
 *         &lt;element name="coverage" type="{http://www.adligo.org/tests4j/run/xml/io/coverage/v1_0}package_coverage_type" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "api_trial_result_type", propOrder = {
    "common",
    "coverage"
})
public class ApiTrialResultType {

    @XmlElement(required = true)
    protected CommonResultType common;
    protected PackageCoverageType coverage;

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
     *     {@link PackageCoverageType }
     *     
     */
    public PackageCoverageType getCoverage() {
        return coverage;
    }

    /**
     * Sets the value of the coverage property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageCoverageType }
     *     
     */
    public void setCoverage(PackageCoverageType value) {
        this.coverage = value;
    }

}
