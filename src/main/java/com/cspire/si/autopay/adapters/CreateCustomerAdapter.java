package com.cspire.si.autopay.adapters;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapMessage;

import com.cellularsouth.autopay.util.Utility;
import com.cellularsouth.businessentities.client.v2.customer.createcustomer.CreateCustomerABO;
import com.cellularsouth.businessentities.client.v2.customer.createcustomer.CreateCustomerProcessStepType;
import com.cellularsouth.businessservices.client.v2.customer.createcustomer.CreateCustomerABM;
import com.cellularsouth.businessservices.client.v2.customer.createcustomer.ObjectFactory;
import com.cellularsouth.commonentities.v2.AddressListType;
import com.cellularsouth.commonentities.v2.AddressType;
import com.cellularsouth.commonentities.v2.BasicContactInfoType;
import com.cellularsouth.commonentities.v2.BasicCustomerInfoType;
import com.cellularsouth.commonentities.v2.BasicCustomerInfoType.CustomerAddressList;
import com.cellularsouth.commonentities.v2.BasicCustomerInfoType.CustomerContactList;
import com.cellularsouth.commonentities.v2.ContactInfoListType;
import com.cellularsouth.commonentities.v2.ContactNumberTypeType;
import com.cellularsouth.commonentities.v2.ContactPrimacyType;
import com.cellularsouth.commonentities.v2.CountryCodeType;
import com.cellularsouth.commonentities.v2.CreditApplicationType;
import com.cellularsouth.commonentities.v2.CreditApplicationTypeType;
import com.cellularsouth.commonentities.v2.CustomerLevelCreditInfoType;
import com.cellularsouth.commonentities.v2.CustomerStatusType;
import com.cellularsouth.commonentities.v2.CustomerType;
import com.cellularsouth.commonentities.v2.CustomerTypeType;
import com.cellularsouth.commonentities.v2.EmploymentInfoType;
import com.cellularsouth.commonentities.v2.ExtendedCustomerInfoType;
import com.cellularsouth.commonentities.v2.ExtendedCustomerInfoType.CreditApplicationList;
import com.cellularsouth.commonentities.v2.GeoCodesType;
import com.cellularsouth.commonentities.v2.HousingStatusType;
import com.cellularsouth.commonentities.v2.LengthOfTimeType;
import com.cellularsouth.commonentities.v2.PhotoIdentificationType;
import com.cellularsouth.commonentities.v2.PhotoIdentificationTypeType;
import com.cellularsouth.commonentities.v2.StateCodeType;
import com.cspire.si.autopay.crm.dao.CrmCustomerSqlDao;
import com.cspire.si.autopay.utils.Environment;

@Component
public class CreateCustomerAdapter {

	private WebServiceTemplate createCustomerTemplate;

	@Autowired
	private CrmCustomerSqlDao dao;
	
	@Resource(name="createCustomerTemplateTEST")
	private WebServiceTemplate createCustomerTemplateTEST;

	@Resource(name="createCustomerTemplateINT")
	private WebServiceTemplate createCustomerTemplateINT;

	@Resource(name="createCustomerTemplateDEV")
	private WebServiceTemplate createCustomerTemplateDEV;

	@Resource(name="createCustomerTemplateSAND")
	private WebServiceTemplate createCustomerTemplateSAND;
	
	
	public String createCustomer() {
		String ssn = getNextAvailableSSN();
		
		ObjectFactory abmFactory = new ObjectFactory();
		com.cellularsouth.businessentities.client.v2.customer.createcustomer.ObjectFactory aboFactory = 
				new com.cellularsouth.businessentities.client.v2.customer.createcustomer.ObjectFactory();
		com.cellularsouth.commonentities.v2.ObjectFactory commonFactory = 
				new com.cellularsouth.commonentities.v2.ObjectFactory();
		
		CreateCustomerABM abm = abmFactory.createCreateCustomerABM();
		CreateCustomerABO abo = aboFactory.createCreateCustomerABO();
		
		abm.setMessageHeader(commonFactory.createGenericHeaderType());
		abm.getMessageHeader().setSalesRepID("102223");
		abm.getMessageHeader().setSourceApplication("RETAIL");
		abm.getMessageHeader().setDateTime(Utility.getXMLGregorianCalendar());
		
		abm.setMessagePayload(abmFactory.createCreateCustomerPayloadType());
		abm.getMessagePayload().setCreateCustomer(abo);
		
		abo.setCreateCustomerHeader(aboFactory.createCreateCustomerHeaderType());
		abo.getCreateCustomerHeader().setChannelId("383");
		abo.getCreateCustomerHeader().setCreateCustomerProcessStep(CreateCustomerProcessStepType.CREATE_CUSTOMER);
		
		abo.setCreateCustomerDataArea(aboFactory.createCreateCustomerDataAreaType());
		CustomerType customer = new CustomerType();
		abo.getCreateCustomerDataArea().setCustomer(customer);
		
		customer.setTestCustomer(false);
		BasicCustomerInfoType basicCustomer = new BasicCustomerInfoType();
		customer.setCustomerInfo(basicCustomer);
		ExtendedCustomerInfoType extendedCustomer = new ExtendedCustomerInfoType();
		customer.setExtendedCustomerInfo(extendedCustomer);
		
		basicCustomer.setCustomerFirstName("Carlos");
		basicCustomer.setCustomerLastName("Bernal");
		basicCustomer.setCustomerSalutation("DR");
		basicCustomer.setCustomerSuffix("JR");
		basicCustomer.setCustomerDOB(Utility.getXMLGregorianCalendar());
		basicCustomer.getCustomerDOB().setYear(1987);
		basicCustomer.setCustomerEmailAddress("test@test.com");
		basicCustomer.setUniqueIdentifier(ssn);
		basicCustomer.setCustomerType(CustomerTypeType.CONSUMER);
		basicCustomer.setCustomerStatus(CustomerStatusType.ACTIVE);

		CustomerAddressList addressList = new CustomerAddressList();
		basicCustomer.setCustomerAddressList(addressList);
		AddressType address1 = new AddressType();
		address1.setValidated(true);
		address1.setCurrentAddress(true);
		address1.setAddressLine1("500 AVALON WAY APT 908");
		address1.setAttn("");
		address1.setCity("BRANDON");
		address1.setState(StateCodeType.MS);
		address1.setCounty("RANKIN");
		address1.setZip("39047");
		address1.setPostPlusCode("7488");
		address1.setCountry(CountryCodeType.US);
		GeoCodesType geoCodes = new GeoCodesType();
		geoCodes.setGeoCityCode("0000");
		geoCodes.setGeoCountyCode("121");
		geoCodes.setGeoStateCode("25");
		address1.setGeoCodes(geoCodes);
		
		addressList.getCustomerAddress().add(address1);
		
		CustomerContactList contactList = new CustomerContactList();
		basicCustomer.setCustomerContactList(contactList);
		BasicContactInfoType contact = new BasicContactInfoType();
		contact.setContactNumber("6108508383");
		contact.setContactNumberType(ContactNumberTypeType.MOBILE_PHONE);
		contact.setContactPrimacy(ContactPrimacyType.PC);
		contact.setDoNotCallFlag(true);
		contactList.getCustomerContact().add(contact);
		
		BasicContactInfoType contact2 = new BasicContactInfoType();
		contact2.setContactNumber("6019747246");
		contact2.setContactNumberType(ContactNumberTypeType.WORK_PHONE);
		contact2.setContactPrimacy(ContactPrimacyType.PC);
		contact2.setDoNotCallFlag(true);
		contactList.getCustomerContact().add(contact2);
		
		CustomerLevelCreditInfoType creditInfo = new CustomerLevelCreditInfoType();
		creditInfo.setOverrideAllowedAccounts("0");
		basicCustomer.setCreditInfo(creditInfo);
		
		AddressType address2 = new AddressType();
		address2.setValidated(true);
		address2.setCurrentAddress(true);
		address2.setAddressLine1("500 AVALON WAY APT 908");
		address2.setAttn("");
		address2.setCity("BRANDON");
		address2.setState(StateCodeType.MS);
		address2.setCounty("RANKIN");
		address2.setZip("39047");
		address2.setPostPlusCode("7488");
		address2.setCountry(CountryCodeType.US);
		address2.setLengthOfResidence(new LengthOfTimeType());
		address2.getLengthOfResidence().setMonths("0");
		address2.getLengthOfResidence().setYears("4");
		address2.setHousingStatus(HousingStatusType.RENT);
		address2.setGeoCodes(geoCodes);

		CreditApplicationList appList = new CreditApplicationList();
		CreditApplicationType creditApp = new CreditApplicationType();
		creditApp.setAddressList(new AddressListType());
		creditApp.getAddressList().getAddress().add(address2);
		creditApp.setCreditApplicationType(CreditApplicationTypeType.EMPLOYEE);
		creditApp.setCustomerType(CustomerTypeType.CONSUMER);
		creditApp.setFirstName("Carlos");
		creditApp.setLastName("Bernal");
		creditApp.setSalutation("DR");
		creditApp.setSuffix("JR");
		creditApp.setEmail("test@test.com");
		creditApp.setDOB(basicCustomer.getCustomerDOB());
		creditApp.setUniqueIdentifier(ssn);
		creditApp.setComments("Initial Customer Application");
		
		PhotoIdentificationType photoId = new PhotoIdentificationType();
		photoId.setValidated(true);
		photoId.setIDNumber("0094567890");
		photoId.setIDState(StateCodeType.LA);
		photoId.setIDType(PhotoIdentificationTypeType.DRIVERS_LICENSE);
		creditApp.setPhotoIdentification(photoId);

		creditApp.setContactInfoList(new ContactInfoListType());
		creditApp.getContactInfoList().getContactInfo().add(contact);
		creditApp.getContactInfoList().getContactInfo().add(contact2);
		
		EmploymentInfoType employment = new EmploymentInfoType();
		employment.setEmployer("Patients");
		employment.setPosition("Dr.");
		employment.setLengthOfEmployment(new LengthOfTimeType());
		employment.getLengthOfEmployment().setMonths("0");
		employment.getLengthOfEmployment().setYears("10");
		creditApp.setEmploymentInfo(employment);

		appList.getCreditApplication().add(creditApp);
		extendedCustomer.setCreditApplicationList(appList);

//		JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(CreateCustomerABM.class);
//
//
//			Marshaller m = context.createMarshaller();
//			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			m.marshal(abm, System.out);
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		abm = (CreateCustomerABM) createCustomerTemplate.marshalSendAndReceive(abm, new WebServiceMessageCallback() {
			public void doWithMessage(WebServiceMessage message) {
				((SoapMessage) message).setSoapAction("CreateCustomer");
            }
        });
		
		return abm.getMessagePayload().getCreateCustomer()
				.getCreateCustomerDataArea().getCustomer().getCustomerInfo().getCustomerID();
	}
	
	public String getNextAvailableSSN() {
		List<String> used = dao.getUsedSSNs("foo");
		long start = 900002000;
		
		for (int i = 0; i < 100; i++){
			if (used.size() <= i || start + i < Long.parseLong(used.get(i))) {
				return String.valueOf(start + i);
			}
		}
		return null;
	}
	
	public void setEnvironment(Environment env) {
		dao.setEnvironment(env);
		switch(env) {
		case SAND:
		case LOCALSAND:
			createCustomerTemplate = createCustomerTemplateSAND;
			break;
			
		case DEV:
		case LOCALDEV:
			createCustomerTemplate = createCustomerTemplateDEV;
			break;
			
		case INT:
		case LOCALINT:
			createCustomerTemplate = createCustomerTemplateINT;
			break;
			
		case TEST:
		case LOCALTEST:
			createCustomerTemplate = createCustomerTemplateTEST;
			break;
		}
	}
}