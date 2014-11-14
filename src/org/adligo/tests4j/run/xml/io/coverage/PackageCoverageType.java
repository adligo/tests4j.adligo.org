//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.14 at 05:16:19 PM CST 
//


package org.adligo.tests4j.run.xml.io.coverage;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for package_coverage_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="package_coverage_type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="file" type="{http://www.adligo.org/tests4j/run/xml/io/coverage}source_file_coverage_type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="package" type="{http://www.adligo.org/tests4j/run/xml/io/coverage}package_coverage_type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="coverageUnits" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *       &lt;attribute name="coveredCoverageUnits" use="required" type="{http://www.w3.org/2001/XMLSchema}int" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "package_coverage_type", propOrder = {
    "file",
    "_package"
})
public class PackageCoverageType {

    protected List<SourceFileCoverageType> file;
    @XmlElement(name = "package")
    protected List<PackageCoverageType> _package;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "coverageUnits", required = true)
    protected int coverageUnits;
    @XmlAttribute(name = "coveredCoverageUnits", required = true)
    protected int coveredCoverageUnits;

    /**
     * Gets the value of the file property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the file property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SourceFileCoverageType }
     * 
     * 
     */
    public List<SourceFileCoverageType> getFile() {
        if (file == null) {
            file = new ArrayList<SourceFileCoverageType>();
        }
        return this.file;
    }

    /**
     * Gets the value of the package property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the package property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPackage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PackageCoverageType }
     * 
     * 
     */
    public List<PackageCoverageType> getPackage() {
        if (_package == null) {
            _package = new ArrayList<PackageCoverageType>();
        }
        return this._package;
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
     * Gets the value of the coverageUnits property.
     * 
     */
    public int getCoverageUnits() {
        return coverageUnits;
    }

    /**
     * Sets the value of the coverageUnits property.
     * 
     */
    public void setCoverageUnits(int value) {
        this.coverageUnits = value;
    }

    /**
     * Gets the value of the coveredCoverageUnits property.
     * 
     */
    public int getCoveredCoverageUnits() {
        return coveredCoverageUnits;
    }

    /**
     * Sets the value of the coveredCoverageUnits property.
     * 
     */
    public void setCoveredCoverageUnits(int value) {
        this.coveredCoverageUnits = value;
    }

}
