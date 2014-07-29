package com.cspire.si.autopay.adapters;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.cellularsouth.autopay.exceptions.AutopayException;
import com.cellularsouth.customer.schema.ActivateSubscriptionForm;
import com.cellularsouth.customer.schema.ServiceType;
import com.cellularsouth.customer.schema.ServicesType;
import com.cspire.si.autopay.utils.Environment;

@Component
public class ActivateSubscriptionAdapter {
	
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
	
	public void activateSubscription(String subscriptionId, String paymentType, String subType) {
		ActivateSubscriptionForm asForm = new ActivateSubscriptionForm();
        asForm.setChannelId("RETAIL");
        asForm.setOrderId("2003487939");
        asForm.setSubscriptionId(subscriptionId);
        asForm.setSalesChannel("RETAIL");
        
        if(subType != null && subType.equals("CLOUD_SERVICES_BUS")) {
            ServicesType st = new ServicesType();
            ServiceType service = new ServiceType();
            service.setServiceId("CS_HOSTED_EXCHANGE");
            service.setServiceName("CS_HOSTED_EXCHANGE");
            service.setPlan(true);
            st.getService().add(service);
        	asForm.setServices(st);
        }
        else if(paymentType.equals("AP")){
        	ServicesType st = new ServicesType();
        	ServiceType apService = new ServiceType();
        	apService.setServiceId("APNW_TALK_MSG_250");
        	apService.setServiceName("APNW_TALK_MSG_250");
        	apService.setPlan(true);
        	st.getService().add(apService);
        	asForm.setServices(st);
        } 
        else {
        	ServicesType st = new ServicesType();
        	ServiceType ppService = new ServiceType();
        	ppService.setServiceId("24MTH_ADVANCED_POC");
        	ppService.setServiceName("24MTH_ADVANCED_POC");
        	ppService.setPlan(false);
        	st.getService().add(ppService);
        	
        	ServiceType vChat = new ServiceType();
        	vChat.setServiceId("VIDEO_CHAT_FEATURE");
        	vChat.setServiceName("VIDEO_CHAT_FEATURE");
        	vChat.setPlan(false);
        	st.getService().add(vChat);
        	asForm.setServices(st);
        	
        	ServiceType ppPlan = new ServiceType();
        	ppPlan.setServiceId("NW_UNLTD_ALL");
        	ppPlan.setServiceName("NW_UNLTD_ALL");
        	ppPlan.setPlan(true);
        	st.getService().add(ppPlan);
        	asForm.setServices(st);
        }
        
        asForm.setUpdateSalesRepId("10");
        asForm.setActivationSalesRepId("10");
        asForm.setOrderNumber("2003487939");
        asForm.setOriginationLocId(128472l);
        asForm.setPmtLocId(128472l);
        asForm.setActivationLocId(128472l);
        asForm.setESNMEID("99000258000580");
        asForm.setMake("MXC570");
        
        try {
        	asForm = (ActivateSubscriptionForm) subscriptionServiceTemplate.marshalSendAndReceive(asForm, new WebServiceMessageCallback() {
        		public void doWithMessage(WebServiceMessage message) {
        			((SoapMessage) message).setSoapAction("ActivateSubscription");					
        		}
        	});
        } catch (SoapFaultClientException soapFault) {
        	soapFault.printStackTrace();
        	throw new AutopayException("failed calling ActivateSubscription" + soapFault.getMessage() + "\n Stack trace: " + soapFault.getStackTrace());
        }
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
