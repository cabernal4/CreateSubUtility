package com.cspire.si.autopay.adapters;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.cspire.si.autopay.crm.dao.CrmCustomerSqlDao;

@RunWith(MockitoJUnitRunner.class)
public class CreateCustomerAdapterTest {

	@InjectMocks
	private CreateCustomerAdapter adapter = new CreateCustomerAdapter();
	
	@Mock
	private CrmCustomerSqlDao dao;
	
	@Test
	public void testSSNEmptyList() {
		List<String> usedSSNs = new ArrayList<String>();
		
		Mockito.when(dao.getUsedSSNs(Matchers.anyString())).thenReturn(usedSSNs);
		
		String ssn = adapter.getNextAvailableSSN();
		
		Assert.assertEquals("900002000", ssn);
	}
	
	@Test
	public void testSSNCompactList() {
		List<String> usedSSNs = new ArrayList<String>();
		usedSSNs.add("900002000");
		usedSSNs.add("900002001");
		usedSSNs.add("900002002");
		
		Mockito.when(dao.getUsedSSNs(Matchers.anyString())).thenReturn(usedSSNs);
		
		String ssn = adapter.getNextAvailableSSN();
		
		Assert.assertEquals("900002003", ssn);
	}
	
	@Test
	public void testSSNHoledList() {
		List<String> usedSSNs = new ArrayList<String>();
		usedSSNs.add("900002000");
		usedSSNs.add("900002001");
		usedSSNs.add("900002060");
		
		Mockito.when(dao.getUsedSSNs(Matchers.anyString())).thenReturn(usedSSNs);
		
		String ssn = adapter.getNextAvailableSSN();
		
		Assert.assertEquals("900002002", ssn);
	}
}
