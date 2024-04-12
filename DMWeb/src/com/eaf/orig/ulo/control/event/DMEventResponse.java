package com.eaf.orig.ulo.control.event;

import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;

public class DMEventResponse extends EventResponseHelper implements EventResponse {
	private static final long serialVersionUID = 1L;
	public DMEventResponse(int result, String message) {
		this.result = result;
		this.message = message;
	}
	public DMEventResponse(int eventType, int result, String message, Object obj){
		this.type = eventType;
		this.result = result;
		this.message = message;
		this.encapData = obj;
	}
	
	public DMEventResponse(int eventType, int result, String message, Object obj,String uniqueId){
		this.type = eventType;
		this.result = result;
		this.message = message;
		this.encapData = obj;
		this.refId = uniqueId;
	}
}
