//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-833 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.05.19 at 12:19:09 PM CDT 
//


package com.cellularsouth.createsub.schema;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FaultDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="FaultMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FaultCode" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="FaultStack" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RecommendedAction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "faultDateTime",
    "faultMessage",
    "faultCode",
    "faultStack",
    "recommendedAction"
})
@XmlRootElement(name = "Fault")
public class Fault {

    @XmlElement(name = "FaultDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar faultDateTime;
    @XmlElement(name = "FaultMessage")
    protected String faultMessage;
    @XmlElement(name = "FaultCode")
    protected BigInteger faultCode;
    @XmlElement(name = "FaultStack")
    protected String faultStack;
    @XmlElement(name = "RecommendedAction")
    protected String recommendedAction;

    /**
     * Gets the value of the faultDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFaultDateTime() {
        return faultDateTime;
    }

    /**
     * Sets the value of the faultDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFaultDateTime(XMLGregorianCalendar value) {
        this.faultDateTime = value;
    }

    /**
     * Gets the value of the faultMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultMessage() {
        return faultMessage;
    }

    /**
     * Sets the value of the faultMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultMessage(String value) {
        this.faultMessage = value;
    }

    /**
     * Gets the value of the faultCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFaultCode() {
        return faultCode;
    }

    /**
     * Sets the value of the faultCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFaultCode(BigInteger value) {
        this.faultCode = value;
    }

    /**
     * Gets the value of the faultStack property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultStack() {
        return faultStack;
    }

    /**
     * Sets the value of the faultStack property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultStack(String value) {
        this.faultStack = value;
    }

    /**
     * Gets the value of the recommendedAction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecommendedAction() {
        return recommendedAction;
    }

    /**
     * Sets the value of the recommendedAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecommendedAction(String value) {
        this.recommendedAction = value;
    }

}
