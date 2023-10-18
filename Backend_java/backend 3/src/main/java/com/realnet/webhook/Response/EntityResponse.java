package com.realnet.webhook.Response;

import lombok.Data;

@Data
public class EntityResponse {
	
	private String msg;

	public EntityResponse(String msg) {
		super();
		this.msg = msg;
	}

}
