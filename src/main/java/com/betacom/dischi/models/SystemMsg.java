package com.betacom.dischi.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table
public class SystemMsg {

	@EmbeddedId
	private KeyID msgID;
	
	private String msg;

	public KeyID getMsgID() {
		return msgID;
	}
	public void setMsgID(KeyID msgID) {
		this.msgID = msgID;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
