package com.cspire.si.autopay.adapters;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.cellularsouth.autopay.exceptions.AutopayException;
import com.cellularsouth.customer.schema.AddDeviceInfoToSubscriptionForm;
import com.cellularsouth.customer.schema.AddDeviceInfoType;
import com.cellularsouth.customer.schema.AssignMdnForm;
import com.cellularsouth.primenumbermanagement.schema.PrimeAssignNumberForm;
import com.cellularsouth.primenumbermanagement.schema.PrimeGetAvailableNumbersForm;
import com.cellularsouth.primenumbermanagement.schema.PrimeNumberManagementType;
import com.cellularsouth.primenumbermanagement.schema.PrimeReservePhoneNumberForm;
import com.cellularsouth.primenumbermanagement.schema.SubscriptionTypeType;
import com.cspire.si.autopay.utils.Environment;

@Component
public class NumberManagementAdapter {
	
	private WebServiceTemplate numberManagementServiceTemplate;
	private WebServiceTemplate subscriptionServiceTemplate;

	@Resource(name="numberManagementServiceTemplateTEST")
	private WebServiceTemplate numberManagementServiceTemplateTEST;

	@Resource(name="numberManagementServiceTemplateINT")
	private WebServiceTemplate numberManagementServiceTemplateINT;

	@Resource(name="numberManagementServiceTemplateDEV")
	private WebServiceTemplate numberManagementServiceTemplateDEV;

	@Resource(name="numberManagementServiceTemplateSAND")
	private WebServiceTemplate numberManagementServiceTemplateSAND;
	
	
	@Resource(name="subscriptionServiceTemplateTEST")
	private WebServiceTemplate subscriptionServiceTemplateTEST;

	@Resource(name="subscriptionServiceTemplateINT")
	private WebServiceTemplate subscriptionServiceTemplateINT;
	
	@Resource(name="subscriptionServiceTemplateDEV")
	private WebServiceTemplate subscriptionServiceTemplateDEV;
	
	@Resource(name="subscriptionServiceTemplateSAND")
	private WebServiceTemplate subscriptionServiceTemplateSAND;

	@Resource(name="subscriptionServiceTemplateLOCAL")
	private WebServiceTemplate subscriptionServiceTemplateLOCAL;


	public String getMdn() {
        boolean gotMdn = false;
        int tries = 0;

        PrimeGetAvailableNumbersForm ganForm = new PrimeGetAvailableNumbersForm();
        ganForm.setBlockId("2");
        ganForm.setItemsToBeReturned("10");
        ganForm.setLineMax("500");
        ganForm.setLineMin("000");
        ganForm.setNPA("228");
        ganForm.setNXX("216");

        while (!gotMdn) {
        	try {
        		ganForm = (PrimeGetAvailableNumbersForm) numberManagementServiceTemplate.marshalSendAndReceive(ganForm, new WebServiceMessageCallback() {
        			public void doWithMessage(WebServiceMessage message) {
        				((SoapMessage) message).setSoapAction("GetAvailableNumbers");					
        			}
        		});
        	} catch (SoapFaultClientException soapFault) {
            	throw new AutopayException("failed calling getMdn" + soapFault.getMessage());
        	}

        	if(!ganForm.getAvailableMDN().isEmpty() && !ganForm.getAvailableMDN().get(0).endsWith("000")) {
        		gotMdn = true;
        	}
        	
        	tries++;
        	if (tries >= 5){
        		throw new AutopayException("failed getting mdn called 5 times");
        	}
        }

        return ganForm.getAvailableMDN().get(0);
	}
	
	public String reserveNumberAdapter(String subscriptionId,String mdn) {
		PrimeReservePhoneNumberForm rnForm = new PrimeReservePhoneNumberForm();
		PrimeNumberManagementType pnmt = new PrimeNumberManagementType();
		pnmt.setMDN(mdn);
		pnmt.setSubClientId(subscriptionId);

		rnForm.setPrimeNumberManagement(pnmt);
		try {
			rnForm = (PrimeReservePhoneNumberForm) numberManagementServiceTemplate.marshalSendAndReceive(rnForm, new WebServiceMessageCallback() {
				public void doWithMessage(WebServiceMessage message) {
					((SoapMessage) message).setSoapAction("PrimeReservePhoneNumber");					
				}
			});
		} catch (SoapFaultClientException soapFault) {
			throw new AutopayException("failed calling reservePhoneNumber" + soapFault.getMessage());
		}

		return rnForm.getPrimeNumberManagement().getMIN();
	}
	
	public void assignNumber(String subscriptionId, String mdn) {
        PrimeAssignNumberForm anForm = new PrimeAssignNumberForm();
        anForm.setPrimeNumberManagement(new PrimeNumberManagementType());
        anForm.getPrimeNumberManagement().setMDN(mdn);
        anForm.getPrimeNumberManagement().setSubClientId(subscriptionId);
        
        //TODO: we may want to fix this later, don't need to right now.
        anForm.getPrimeNumberManagement().setSubscriptionType(SubscriptionTypeType.CONSUMER);
        
        try {
        	anForm = (PrimeAssignNumberForm) numberManagementServiceTemplate.marshalSendAndReceive(anForm, new WebServiceMessageCallback() {
        		public void doWithMessage(WebServiceMessage message) {
        			((SoapMessage) message).setSoapAction("PrimeAssignNumber");					
        		}
        	});
        } catch (SoapFaultClientException soapFault) {
        	throw new AutopayException("failed calling AssignPhoneNumber" + soapFault.getMessage());
        }
        
        if(!anForm.getPrimeNumberManagement().isSuccessful()) {
        	throw new AutopayException("call to assignNumber failed");
        }
	}
	
	public void addDeviceInfo(String subscriptionId, String mdn, String min, String subscriptionType) {
        AddDeviceInfoToSubscriptionForm adiForm = new AddDeviceInfoToSubscriptionForm();
        AddDeviceInfoType adiType = new AddDeviceInfoType();
        
        if(!subscriptionType.contains("CLOUD_SERVICES")) {
        	adiType.setCOAM(false);
        	adiType.setCSN("MXC570");
        	adiType.setPortedMDN(false);
        	adiType.setMDN(mdn);
        	adiType.setMIN(min);
        	adiType.setESNMEID("99000258000580");
        	adiForm.setDeviceInfo(adiType);
        	adiForm.setUpdateSalesRepId("101");
        	adiForm.setCSA("PICAY");
        	adiForm.setSubscriptionId(subscriptionId);     
        } else {
        	adiType.setCOAM(false);
        	adiType.setCSN("HOSTED");
        	adiType.setPortedMDN(false);
        	adiType.setMDN(mdn);
        	adiType.setMIN(min);
        	adiType.setESNMEID("8000271B");
        	adiForm.setDeviceInfo(adiType);
        	adiForm.setUpdateSalesRepId("101");
        	adiForm.setCSA("PICAY");
        	adiForm.setSubscriptionId(subscriptionId);
        }

        try {
        	adiForm = (AddDeviceInfoToSubscriptionForm) subscriptionServiceTemplate.marshalSendAndReceive(adiForm, new WebServiceMessageCallback() {
        		public void doWithMessage(WebServiceMessage message) {
        			((SoapMessage) message).setSoapAction("AddDeviceInfoToSubscription");					
        		}
        	});
        } catch (SoapFaultClientException soapFault) {
        	throw new AutopayException("failed calling AddDeviceInfoToSubscription" + soapFault.getMessage());
        }
	}
	
	public void assignMdn(String mdn, String subId, String subType) {
		AssignMdnForm amf = new AssignMdnForm();
		amf.setChannelId("RETAIL");
		amf.setMDN(mdn);
		amf.setSubscriptionId(subId);
		amf.setSubscriptionType(subType);

        try {
        	amf = (AssignMdnForm) subscriptionServiceTemplate.marshalSendAndReceive(amf, new WebServiceMessageCallback() {
        		public void doWithMessage(WebServiceMessage message) {
        			((SoapMessage) message).setSoapAction("AssignMdn");					
        		}
        	});
        } catch (SoapFaultClientException soapFault) {
        	throw new AutopayException("failed calling AssignMdn" + soapFault.getMessage());
        }
	}

	public void setEnvironment(Environment env) {
		switch(env) {
		case SAND:
			numberManagementServiceTemplate = numberManagementServiceTemplateSAND;
			subscriptionServiceTemplate = subscriptionServiceTemplateSAND;
			break;
		case DEV:
			numberManagementServiceTemplate = numberManagementServiceTemplateDEV;
			subscriptionServiceTemplate = subscriptionServiceTemplateDEV;
			break;
		case INT:
			numberManagementServiceTemplate = numberManagementServiceTemplateINT;
			subscriptionServiceTemplate = subscriptionServiceTemplateINT;
			break;
		case TEST:
			numberManagementServiceTemplate = numberManagementServiceTemplateTEST;
			subscriptionServiceTemplate = subscriptionServiceTemplateTEST;
			break;
		case LOCALSAND:
			numberManagementServiceTemplate = numberManagementServiceTemplateSAND;
			subscriptionServiceTemplate = subscriptionServiceTemplateLOCAL;
			break;
		case LOCALDEV:
			numberManagementServiceTemplate = numberManagementServiceTemplateDEV;
			subscriptionServiceTemplate = subscriptionServiceTemplateLOCAL;
			break;
		case LOCALINT:
			numberManagementServiceTemplate = numberManagementServiceTemplateINT;
			subscriptionServiceTemplate = subscriptionServiceTemplateLOCAL;
			break;
		case LOCALTEST:
			numberManagementServiceTemplate = numberManagementServiceTemplateTEST;
			subscriptionServiceTemplate = subscriptionServiceTemplateLOCAL;
			break;
		}
	}
}