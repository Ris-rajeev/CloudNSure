package com.realnet.Dbconfig.Entity;

import lombok.Data;

@Data
public class ResponseMessage {
	
	private String message;
	
	public ResponseMessage(String msg) {
		super();
		this.message=msg;
	}
	
}
