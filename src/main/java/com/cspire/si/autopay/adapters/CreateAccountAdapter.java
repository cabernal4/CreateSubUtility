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
import com.cellularsouth.customer.schema.AccountExecutivesType;
import com.cellularsouth.customer.schema.AccountType;
import com.cellularsouth.customer.schema.CreateAccountForm;
import com.cellularsouth.customer.schema.PaymentTypeType;
import com.cspire.si.autopay.utils.Environment;

@Component
public class CreateAccountAdapter {
	
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
	
	public String createAccount(String customerId, String paymentType, String typeOfAccount) {
		CreateAccountForm caForm = new CreateAccountForm();
		AccountType accountType = new AccountType();
		accountType.setCustomerId(customerId);
		accountType.setFirstName("Carlos");
		accountType.setLastName("Bernal");
		accountType.setBusinessName("Carlos Computers");
		accountType.setIndustryTypeId(57l);
		
		accountType.setDateOfBirth(Utility.getXMLGregorianCalendar());

        accountType.setType(typeOfAccount);
        
        accountType.setBillingAddress(Utility.getAddressType());
		
        accountType.setEmail("test@test.com");
        
        accountType.setContacts(Utility.getContactsType());

        accountType.setEBill(false);
        accountType.setEBillEmail("ebill@test.com");

        AccountExecutivesType accountExecs = new AccountExecutivesType();
        accountExecs.getAccountExecutive().add("1000000097");
        accountType.setAccountExecutives(accountExecs);

        accountType.setUpdateSalesRepId("993278");
        accountType.setHoldBill(false);
        if(paymentType.equals("AP")) {
        	accountType.setPaymentType(PaymentTypeType.AP);
        } else {
        	accountType.setPaymentType(PaymentTypeType.PP);
        }
        
        caForm.setAccount(accountType);

        try {
        	caForm = (CreateAccountForm) accountServiceTemplate.marshalSendAndReceive(caForm, new WebServiceMessageCallback() {
        		public void doWithMessage(WebServiceMessage message) {
        			((SoapMessage) message).setSoapAction("CreateAccount");					
        		}
        	});
        } catch (SoapFaultClientException soapFault) {
        	throw new AutopayException("failed creating account" + soapFault.getMessage());
        }
        
        return caForm.getAccount().getAccountId();
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
