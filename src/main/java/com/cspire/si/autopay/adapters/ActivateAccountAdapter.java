package com.cspire.si.autopay.adapters;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.cellularsouth.autopay.exceptions.AutopayException;
import com.cellularsouth.customer.schema.ActivateAccountForm;
import com.cspire.si.autopay.utils.Environment;

@Component
public class ActivateAccountAdapter {

	private WebServiceTemplate accountServiceTemplate;

	@Resource(name="accountServiceTemplateTEST")
	private WebServiceTemplate accountServiceTemplateTEST;

	@Resource(name="accountServiceTemplateINT")
	private WebServiceTemplate accountServiceTemplateINT;
	
	@Resource(name="accountServiceTemplateDEV")
	private WebServiceTemplate accountServiceTemplateDEV;
	
	@Resource(name="accountServiceTemplateSAND")
	private WebServiceTemplate accountServiceTemplateSAND;

	@Resource(name="accountServiceTemplateLOCAL")
	private WebServiceTemplate accountServiceTemplateLOCAL;
	
	public void activateAccount(String accountId) {
		ActivateAccountForm aaForm = new ActivateAccountForm();
        aaForm.setAccountId(accountId);
        aaForm.setChannelId("1");
        aaForm.setOrderId("2");
        aaForm.setCSA("PICAY");
        aaForm.setUpdateSalesRepId("21");

        try {
        	aaForm = (ActivateAccountForm) accountServiceTemplate.marshalSendAndReceive(aaForm, new WebServiceMessageCallback() {
        		public void doWithMessage(WebServiceMessage message) {
        			((SoapMessage) message).setSoapAction("ActivateAccount");					
        		}
        	});
        } catch (SoapFaultClientException soapFault) {
        	throw new AutopayException("failed creating activateAccount" + soapFault.getMessage());
        }
        
        if(!aaForm.isWasSuccessful()) {
        	throw new AutopayException("activateAccount was not successful");
        }
	}
	
	public void setEnvironment(Environment env) {
		switch(env) {
		case SAND:
			accountServiceTemplate = accountServiceTemplateSAND;
			break;
		case DEV:
			accountServiceTemplate = accountServiceTemplateDEV;
			break;
		case INT:
			accountServiceTemplate = accountServiceTemplateINT;
			break;
		case TEST:
			accountServiceTemplate = accountServiceTemplateTEST;
			break;
		case LOCALSAND: case LOCALDEV: case LOCALINT: case LOCALTEST:
			accountServiceTemplate = accountServiceTemplateLOCAL;
			break;
		}
	}
}
