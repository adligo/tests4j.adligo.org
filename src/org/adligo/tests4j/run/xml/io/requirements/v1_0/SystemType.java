//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.1 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.05.19 at 12:05:26 PM CDT 
//


package org.adligo.tests4j.run.xml.io.requirements.v1_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *     			The term system either corresponds to a project that can
 *     			be used directly on it's own, or a group of projects
 *     			which can be used in conjunction.
 *     		
 * 
 * <p>Java class for system_type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="system_type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="association" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="use_case_path" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/&gt;
 *         &lt;element name="use_case_priority" type="{http://www.adligo.org/tests4j/run/xml/io/requirements/v1_0}prioritized_use_cases" minOccurs="0"/&gt;
 *         &lt;element name="partition" type="{http://www.adligo.org/tests4j/run/xml/io/requirements/v1_0}partition_type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="parent" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "system_type", propOrder = {
    "association",
    "useCasePath",
    "useCasePriority",
    "partition"
})
public class SystemType {

    protected List<String> association;
    @XmlElement(name = "use_case_path", required = true)
    protected List<String> useCasePath;
    @XmlElement(name = "use_case_priority")
    protected PrioritizedUseCases useCasePriority;
    protected List<PartitionType> partition;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "parent")
    protected String parent;

    /**
     * Gets the value of the association property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the association property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssociation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getAssociation() {
        if (association == null) {
            association = new ArrayList<String>();
        }
        return this.association;
    }

    /**
     * Gets the value of the useCasePath property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the useCasePath property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUseCasePath().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getUseCasePath() {
        if (useCasePath == null) {
            useCasePath = new ArrayList<String>();
        }
        return this.useCasePath;
    }

    /**
     * Gets the value of the useCasePriority property.
     * 
     * @return
     *     possible object is
     *     {@link PrioritizedUseCases }
     *     
     */
    public PrioritizedUseCases getUseCasePriority() {
        return useCasePriority;
    }

    /**
     * Sets the value of the useCasePriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrioritizedUseCases }
     *     
     */
    public void setUseCasePriority(PrioritizedUseCases value) {
        this.useCasePriority = value;
    }

    /**
     * Gets the value of the partition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartitionType }
     * 
     * 
     */
    public List<PartitionType> getPartition() {
        if (partition == null) {
            partition = new ArrayList<PartitionType>();
        }
        return this.partition;
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
     * Gets the value of the parent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParent() {
        return parent;
    }

    /**
     * Sets the value of the parent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParent(String value) {
        this.parent = value;
    }

}
