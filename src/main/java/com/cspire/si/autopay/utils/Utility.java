package com.cspire.si.autopay.utils;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.cellularsouth.autopay.exceptions.AutopayException;

public class Utility {
	
	public static long convertXmlGregorianCalendarToMilliseconds(XMLGregorianCalendar xmlGregorianCalendar) {
		return xmlGregorianCalendar.toGregorianCalendar().getTimeInMillis();
	}
	
	public static XMLGregorianCalendar mapDateToXMLGregorianCalendar(Date date){
		if (null == date){
			return null;
		}
		XMLGregorianCalendar xgc = null;
		try {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(date);
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			throw new AutopayException("Couldn't parse date value [" + date.toString() + "] in the database.",
					"Check the account/subscription being pulled for bad data.", e);
		}
		return xgc;
	}
	
	//This method is used to return the first digit and last four digits of a credit card number
	public static String getMaskedCreditCardNumber(String creditCardNumber) {
		String maskedCreditCardNumber = null;
		
		int length = creditCardNumber.length();
		
		maskedCreditCardNumber = creditCardNumber.substring(0,1);
		for(int i = 1; i < length-4; i++) {
			maskedCreditCardNumber += "*";
		}
		maskedCreditCardNumber += creditCardNumber.substring(length - 4, length);
		
		return maskedCreditCardNumber;		
	}
}
