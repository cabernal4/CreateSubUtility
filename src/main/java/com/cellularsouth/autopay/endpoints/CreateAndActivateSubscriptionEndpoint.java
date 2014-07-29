package com.cellularsouth.autopay.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.cellularsouth.autopay.exceptions.AutopayException;
import com.cellularsouth.createsub.schema.CreateSubForm;
import com.cspire.si.autopay.adapters.ActivateAccountAdapter;
import com.cspire.si.autopay.adapters.ActivateSubscriptionAdapter;
import com.cspire.si.autopay.adapters.CreateAccountAdapter;
import com.cspire.si.autopay.adapters.CreateCustomerAdapter;
import com.cspire.si.autopay.adapters.CreateSubscriptionAdapter;
import com.cspire.si.autopay.adapters.NumberManagementAdapter;
import com.cspire.si.autopay.utils.Environment;

@Endpoint
public class CreateAndActivateSubscriptionEndpoint {
	
	@Autowired
	private CreateCustomerAdapter createCustomerAdapter;
	
	@Autowired
	private NumberManagementAdapter numberManagementAdapter;
	
	@Autowired
	private CreateAccountAdapter createAccountAdapter;
	
	@Autowired
	private ActivateAccountAdapter activateAccountAdapter;
	
	@Autowired
	private CreateSubscriptionAdapter createSubscriptionAdapter;
	
	@Autowired
	private ActivateSubscriptionAdapter activateSubscripitionAdapter;
	
	@PayloadRoot(localPart = "CreateSubForm", namespace = "http://www.cellularsouth.com/createsub/schema")
	@ResponsePayload
	public CreateSubForm createSub(@RequestPayload CreateSubForm form) {
		
		Environment env = Environment.getFromString(form.getEnvironment());
		
		if(env == null) {
			form.setError("environment value is incorrect use one of the following: " +
					"SAND, DEV, INT, TEST, LOCALSAND, LOCALDEV,	LOCALINT, LOCALTEST");
			return form;
		}
		
		
		numberManagementAdapter.setEnvironment(env);
		createCustomerAdapter.setEnvironment(env);
		createAccountAdapter.setEnvironment(env);
		activateAccountAdapter.setEnvironment(env);
		createSubscriptionAdapter.setEnvironment(env);
		activateSubscripitionAdapter.setEnvironment(env);
		
		if(form.getAccountType() == null) {
			form.setAccountType("CONSUMER");
		}
		
		if(form.getSubscriptionType() == null) {
			form.setSubscriptionType("CONSUMER");
		}
		
		String customerId = form.getCustomerId();
		String accountId = form.getAccountId();
		String subscriptionId = null;
		String mdn = null;
		String min = null;
		
		
		boolean createAccount = accountId == null || accountId.isEmpty();
		boolean createCustomer = createAccount && (customerId == null || customerId.isEmpty());

		try {
			mdn = numberManagementAdapter.getMdn();
			form.setMdn(mdn);
			
			// CreateCustomer
			if(createCustomer) {
				customerId = createCustomerAdapter.createCustomer();
				form.setCustomerId(customerId);
			}

			// CreateAccount
			if(createAccount) {
				accountId = createAccountAdapter.createAccount(form.getCustomerId(), form.getPaymentType(), form.getAccountType());
				form.setAccountId(accountId);
				activateAccountAdapter.activateAccount(accountId);
			}
			
			subscriptionId = createSubscriptionAdapter.createSubscription(accountId, mdn, form.getSubscriptionType());
			form.setSubscriptionId(subscriptionId);
			
			min = numberManagementAdapter.reserveNumberAdapter(subscriptionId, mdn);
			
			numberManagementAdapter.assignNumber(subscriptionId, mdn);
//			numberManagementAdapter.assignMdn(mdn, subscriptionId, form.getSubscriptionType());
			numberManagementAdapter.addDeviceInfo(subscriptionId, mdn, min, form.getSubscriptionType());
			
			activateSubscripitionAdapter.activateSubscription(subscriptionId, form.getPaymentType(), form.getSubscriptionType());

			form.setError("NONE! it worked!");
			
			
		} catch (AutopayException e) {
			form.setError(e.getMessage());
		}
		
		return form;
	}
}