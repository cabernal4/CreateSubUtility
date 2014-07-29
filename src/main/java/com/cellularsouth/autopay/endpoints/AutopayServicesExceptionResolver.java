package com.cellularsouth.autopay.endpoints;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

import com.cellularsouth.autopay.exceptions.AutopayException;
import com.cellularsouth.createsub.schema.Fault;

public class AutopayServicesExceptionResolver extends SoapFaultMappingExceptionResolver {
	private Logger log = Logger.getLogger(AutopayServicesExceptionResolver.class);
	private Jaxb2Marshaller marshaller;

	protected void customizeFault(Object endpoint, Exception e, SoapFault soapFault) {
		log.error("Exception thrown.", e);
		
		SoapFaultDetail soapFaultDetail = soapFault.addFaultDetail();
		Fault fault = new Fault();
		
		GregorianCalendar gc = new GregorianCalendar();
		DatatypeFactory dtf = null;
		XMLGregorianCalendar xgc = null;
		try {
			dtf = DatatypeFactory.newInstance();
			xgc = dtf.newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException ex) {
			log.error("ERROR! Could not get an instance of DatatypeFactory!", ex);
		}
		
		fault.setFaultDateTime(xgc);
		fault.setFaultStack(getStackTrace(e));
		
		if(e instanceof AutopayException) {
			AutopayException communicationsException = (AutopayException)e;
			fault.setFaultMessage(communicationsException.getMessage());
			fault.setRecommendedAction(communicationsException.getRecommendedAction());
		} else {
			fault.setFaultMessage(e.getMessage());
			fault.setRecommendedAction("This is probably a bug.  Contact Systems Integration.");
		}

		this.getMarshaller().marshal(fault, soapFaultDetail.getResult());
	}
	
	private String getStackTrace(Exception e) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		return writer.toString();
	}
	
	public void setMarshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public Jaxb2Marshaller getMarshaller() {
		return marshaller;
	}
}
