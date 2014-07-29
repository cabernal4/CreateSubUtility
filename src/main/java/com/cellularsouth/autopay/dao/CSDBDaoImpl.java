package com.cellularsouth.autopay.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CSDBDaoImpl implements CSDBDao {
	@PersistenceContext
	private EntityManager em;
	
//	public AutopayTerms getTerms(String autopayTypeIdentifier) {
//		TypedQuery<AutopayTerms> q = em.createNamedQuery("GET_TERMS_FROM_IDENTIFIER", AutopayTerms.class);
//		q.setParameter(1, autopayTypeIdentifier);
//		if(q.getResultList().isEmpty()) {
//			throw new AutopayException("The identifier [" + autopayTypeIdentifier + "] is not associated with any known autopay configuration.", 
//					"Make sure the autopay configuration identifier being passed in is correct.");
//		} else {
//			return q.getResultList().get(0);
//		}
//	}
//	
//	public ClientTermsJoin getClientTermsJoinByClientId(Long clientId) {
//		TypedQuery<ClientTermsJoin> q = em.createNamedQuery("GET_JOIN_FROM_CLIENT_ID", ClientTermsJoin.class);
//		q.setParameter(1, clientId);
//		if(q.getResultList().isEmpty()) {
//			return null;
//		} else {
//			return q.getResultList().get(0);
//		}
//	}
//	
//	public void createClientTermsJoin(ClientTermsJoin clientTermsJoin) {
//		em.persist(clientTermsJoin);
//	}
//	
//	public void updateClientTermsJoin(ClientTermsJoin clientTermsJoin) {
//		em.merge(clientTermsJoin);
//	}
//	
//	public void removeClientTermsJoin(ClientTermsJoin clientTermsJoin) {
//		em.remove(clientTermsJoin);
//	}
}
