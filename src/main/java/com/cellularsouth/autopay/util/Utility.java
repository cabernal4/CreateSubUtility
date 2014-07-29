package com.cellularsouth.autopay.util;

import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.cellularsouth.autopay.exceptions.AutopayException;
import com.cellularsouth.customer.schema.AddressType;
import com.cellularsouth.customer.schema.ContactType;
import com.cellularsouth.customer.schema.ContactTypeType;
import com.cellularsouth.customer.schema.ContactsType;

public class Utility {
	
	public static XMLGregorianCalendar getXMLGregorianCalendar() {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
        try{
        	DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
        	return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        
        } catch (Exception e) {
        	throw new AutopayException("failed creating xmlGregorianCalendar" + e.getMessage());
        }
	}
	
	public static AddressType getAddressType() {
        AddressType addressType = new AddressType();
        addressType.setAddressLine1("1018 Highland Colony Pkwy");
        addressType.setAddressLine2("St#300");
        addressType.setCity("RIDGELAND");
        addressType.setState("MS");
        addressType.setZip("39157");
        addressType.setPostPlusCode("9752");
        addressType.setCountry("US");
        addressType.setGeoCityCode("0000");
        addressType.setGeoCountyCode("121");
        addressType.setGeoStateCode("25");
        
        return addressType;
	}
	
	public static ContactsType getContactsType() {
		ContactsType contactsType = new ContactsType();
        ContactType contactType = new ContactType();
        contactType.setContactNumber("6019566666");
        contactType.setContactNumberType(ContactTypeType.BUSINESS_CONTACT);
        contactType.setContactPrimacy("PC");
        contactType.setDoNotPhone(false);
        
        ContactType secondaryContactType = new ContactType();
        secondaryContactType.setContactNumber("6019566667");
        secondaryContactType.setContactNumberType(ContactTypeType.HOME_PHONE);
        secondaryContactType.setContactPrimacy("SC");
        secondaryContactType.setDoNotPhone(false);
        
        contactsType.getContact().add(contactType);
//        contactsType.getContact().add(secondaryContactType);
        
        return contactsType;
	}
}
