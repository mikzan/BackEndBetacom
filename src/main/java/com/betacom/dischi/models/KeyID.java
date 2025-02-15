package com.betacom.dischi.models;

import jakarta.persistence.Embeddable;

@Embeddable
public class KeyID {

	private String language;
	private String code;
	
	public KeyID() {
		super();
	}
	public KeyID(String language, String code) {
		super();
		this.language = language;
		this.code = code;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
