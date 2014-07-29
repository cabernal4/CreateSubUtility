package com.cspire.si.autopay.crm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

import com.cspire.si.autopay.utils.Environment;

@Component
public class CrmCustomerSqlDaoImpl implements CrmCustomerSqlDao{

	private JdbcTemplate template;
	
	@Resource(name="csdbJdbcTemplateTEST")
	private JdbcTemplate templateTEST;
	
	@Resource(name="csdbJdbcTemplateINT")
	private JdbcTemplate templateINT;

	@Resource(name="csdbJdbcTemplateDEV")
	private JdbcTemplate templateDEV;

	@Resource(name="csdbJdbcTemplateSAND")
	private JdbcTemplate templateSAND;


	public void updateClientEmailAddress(String crmAccountId, String emailAddress) {
		String sql = null;
		Object[] args = null;

		sql = "update CLIENT set EMAIL_ADDRESS = ? where CLIENT_ID = ?";
		args = new Object[]{emailAddress, crmAccountId};

		template.update(sql, args);
	}

	public String getEmailAddressFromAccountId(String crmAccountId) {

		String sql = "select client.email_address from account_lvl_attr account, client client where account.client_id = client.client_id and account.client_id = ?";
		Object[] args = new Object[]{crmAccountId};
		try {
			return template.query(sql, args, new SingleColumnRowMapper<String>()).get(0);
		} catch (EmptyResultDataAccessException e) {
			throw new IllegalArgumentException("The CRM account ID [" + crmAccountId + "] is not a valid ID for this environment.", e);
		}
	}

	public String getAccountPaymentTypeForAccountNumber(String accountNumber) {

		String sql = "SELECT stage.payment_type FROM account_lvl_attr_stage stage, account_lvl_attr attr WHERE attr.client_id = stage.client_id and attr.account_nbr = ?";
		Object[] args = new Object[]{accountNumber};
		try {
			return template.query(sql, args, new SingleColumnRowMapper<String>()).get(0);
		} catch (EmptyResultDataAccessException e) {
			throw new IllegalArgumentException("The CRM account number [" + accountNumber + "] is not a valid ID for this environment.", e);
		}
	}

	public List<String> getUsedSSNs(String env) {
		String sql = "SELECT idntfc_nbr from client_credit where idntfc_nbr like '9000020%'";

		List<String> usedNumbers = new ArrayList<String>();
		try{
			usedNumbers = template.query(sql, new SingleColumnRowMapper<String>());
		} catch (EmptyResultDataAccessException e) {
			// it's all good, all numbers are available
			System.out.println("empty results");
		}
		return usedNumbers;
	}

	public void setEnvironment(Environment env) {
		switch(env) {
		case SAND:
		case LOCALSAND:
			template = templateSAND;
			break;

		case DEV:
		case LOCALDEV:
			template = templateDEV;
			break;

		case INT:
		case LOCALINT:
			template = templateINT;
			break;

		case TEST:
		case LOCALTEST:
			template = templateTEST;
			break;
		}
	}
}
