package com.spring.boot.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "popular_location")
public class FinancialModel {



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	// sequence for oracle
	/*
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FM_SEQ")
    @SequenceGenerator(sequenceName = "financial_model_seq", allocationSize = 1, name = "FM_SEQ")
    */
	private Long id;
	
	@Column(name="STORE_CODE")
	private String storeCode;
	
	@Column(name="FACEBOOK_WEB_ADDRESS")
	private String facebookWebAddress;
	
	@Column(name="GOOGLE_ID")
	private String googleId;

	public FinancialModel(String storeCode, String faceBookWebAddress, String googleId) {
		this.storeCode=storeCode;
		this.facebookWebAddress=faceBookWebAddress;
		this.googleId=googleId;
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getFacebookWebAddress() {
		return facebookWebAddress;
	}

	public void setFacebookWebAddress(String facebookWebAddress) {
		this.facebookWebAddress = facebookWebAddress;
	}

	public String getGoogleId() {
		return googleId;
	}

	public void setGoogleId(String googleId) {
		this.googleId = googleId;
	}

	@Override
	public String toString() {
		return "FinancialModel [id=" + id + ", storeCode=" + storeCode + ", facebookWebAddress=" + facebookWebAddress
				+ ", googleId=" + googleId + "]";
	}

	public FinancialModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
