package com.cellularsouth.autopay.models.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name = "AUTOPAY_TERMS_SETUP")
public class AutopayTerms implements Serializable {
	private static final long serialVersionUID = 8546379531691749125L;

	@Id
	@Column(name = "AUTOPAY_TERMS_SETUP_ID")
	private Long id;
	
	@Column(name = "TERMS_SETUP_TEXT")
	private String text;
	
	@Column(name = "TEXT_TYPE")
	private String identifier;
	
	@Override
	public boolean equals(Object o) {
		if(null == o) { return false; }
		if(!(o instanceof AutopayTerms)) { return false; }
		if(this == o) { return true; }
		
		AutopayTerms terms = (AutopayTerms)o;
		return new EqualsBuilder()
			.append(this.text, terms.text)
			.append(this.identifier, terms.identifier)
			.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(79, 31)
			.append(this.text)
			.append(this.identifier)
			.toHashCode();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}
}
