package com.cspire.si.autopay.adapters;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.cellularsouth.autopay.exceptions.AutopayException;
import com.cellularsouth.autopay.util.Utility;
import com.cellularsouth.customer.schema.AddressesType;
import com.cellularsouth.customer.schema.CreateSubscriptionForm;
import com.cellularsouth.customer.schema.SubscriptionType;
import com.cellularsouth.customer.schema.SubscriptionsType;
import com.cspire.si.autopay.utils.Environment;

@Component
public class CreateSubscriptionAdapter {
	
	private WebServiceTemplate subscriptionServiceTemplate;
	
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
	
	public String createSubscription(String accountId, String mdn, String typeOfSub) {
		CreateSubscriptionForm csForm = new CreateSubscriptionForm();
        SubscriptionsType subsType = new SubscriptionsType();
        SubscriptionType subType = new SubscriptionType();
        
        subType.setAccountID(accountId);
        subType.setFirstName("Carlos");
        subType.setLastName("Bernal");
        
		subType.setDateOfBirth(Utility.getXMLGregorianCalendar());
        
        AddressesType addressesType = new AddressesType();
        addressesType.getAddress().add(Utility.getAddressType());
        subType.setSubscriptionAddresses(addressesType);
        
        subType.setContacts(Utility.getContactsType());
        subType.setType(typeOfSub);
        subType.setStatus("ACTIVE");
//        subType.setCSA();
        subType.setCSN("MXC570");
        subType.setMDN(mdn);
        subType.setESNMEID("A1000009AFD8C0");
        subType.setModelName("MXC570 / SAM/PCD/UNI");
        subType.setModelNumber("MXC570");
        subType.setGender("M");
        subType.setDoNotCall(false);
        subType.setEmail("test@test.com");
        subType.setDelayedActivation(false);
        subType.setPendingSubscription(false);
        subType.setUpdateSalesRepId("10");
        
        subsType.getSubscription().add(subType);
        csForm.setSubscriptions(subsType);
        
        try {
        	csForm = (CreateSubscriptionForm) subscriptionServiceTemplate.marshalSendAndReceive(csForm, new WebServiceMessageCallback() {
        		public void doWithMessage(WebServiceMessage message) {
        			((SoapMessage) message).setSoapAction("CreateSubscription");					
        		}
        	});
        } catch (SoapFaultClientException soapFault) {
        	throw new AutopayException("failed creating subscription" + soapFault.getMessage());
        }
        
        return csForm.getSubscriptions().getSubscription().get(0).getSubscriptionId();
	}
	
	public void setEnvironment(Environment env) {
		switch(env) {
		case SAND:
			subscriptionServiceTemplate = subscriptionServiceTemplateSAND;
			break;
		case DEV:
			subscriptionServiceTemplate = subscriptionServiceTemplateDEV;
			break;
		case INT:
			subscriptionServiceTemplate = subscriptionServiceTemplateINT;
			break;
		case TEST:
			subscriptionServiceTemplate = subscriptionServiceTemplateTEST;
			break;
		case LOCALSAND: case LOCALDEV: case LOCALINT: case LOCALTEST:
			subscriptionServiceTemplate = subscriptionServiceTemplateLOCAL;
			break;
		}
	}
}
