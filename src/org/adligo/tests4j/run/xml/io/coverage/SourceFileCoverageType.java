//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.04 at 04:26:22 PM CST 
//


package org.adligo.tests4j.run.xml.io.coverage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for source_file_coverage_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="source_file_coverage_type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="classProbes" type="{http://www.adligo.org/tests4j/run/xml/io/coverage}class_coverage_type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="probes" use="required" type="{http://www.w3.org/2001/XMLSchema}base64Binary" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "source_file_coverage_type", propOrder = {
    "classProbes"
})
public class SourceFileCoverageType {

    protected List<ClassCoverageType> classProbes;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "probes", required = true)
    protected byte[] probes;

    /**
     * Gets the value of the classProbes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classProbes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassProbes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClassCoverageType }
     * 
     * 
     */
    public List<ClassCoverageType> getClassProbes() {
        if (classProbes == null) {
            classProbes = new ArrayList<ClassCoverageType>();
        }
        return this.classProbes;
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
     * Gets the value of the probes property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getProbes() {
        return probes;
    }

    /**
     * Sets the value of the probes property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setProbes(byte[] value) {
        this.probes = value;
    }

}
