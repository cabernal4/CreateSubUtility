package com.cspire.si.autopay.crm.dao;

import java.util.List;

import com.cspire.si.autopay.utils.Environment;

public interface CrmCustomerSqlDao {

	public void updateClientEmailAddress(String crmAccountId, String emailAddress);
	
	public String getEmailAddressFromAccountId(String crmAccountId);
	
	public String getAccountPaymentTypeForAccountNumber(String accountNumber);
	
	public List<String> getUsedSSNs(String env);
	
	public void setEnvironment(Environment env);
}
