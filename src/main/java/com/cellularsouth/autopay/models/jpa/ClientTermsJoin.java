package com.cellularsouth.autopay.models.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "CLIENT_AUTOPAY_TERMS")
public class ClientTermsJoin implements Serializable {
	private static final long serialVersionUID = -1059660984606983649L;

	@Id
	@Column(name = "CLIENT_AUTOPAY_TERMS_ID")
	@GeneratedValue(generator = "ClientTermsJoinSequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ClientTermsJoinSequence", sequenceName = "CLIENT_AUTOPAY_TERMS_SEQ", allocationSize = 1)
	private Long id;
	
	@Column(name = "CLIENT_ID")
	private Long clientId;
	
	@ManyToOne
	@JoinColumn(name = "AUTOPAY_TERMS_SETUP_ID")
	private AutopayTerms terms;
	
	@Column(name = "ALTER_USER_ID")
	private Long userId;

	@Override
	public boolean equals(Object o) {
		if(null == o) { return false; }
		if(!(o instanceof ClientTermsJoin)) { return false; }
		if(this == o) { return true; }
		
		ClientTermsJoin join = (ClientTermsJoin)o;
		return new EqualsBuilder()
			.append(this.clientId, join.clientId)
			.append(this.terms, join.terms)
			.append(this.userId, join.userId)
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(111, 31)
			.append(this.clientId)
			.append(this.terms)
			.append(this.userId)
			.toHashCode();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setTerms(AutopayTerms terms) {
		this.terms = terms;
	}

	public AutopayTerms getTerms() {
		return terms;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return userId;
	}

}
