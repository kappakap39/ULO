package com.eaf.orig.ulo.control.event;

import com.eaf.core.ulo.common.engine.NotifyDataM;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;

public class ApplicationEventResponse extends EventResponseHelper implements EventResponse {
	private static final long serialVersionUID = 1L;
	public ApplicationEventResponse(int result, String message) {
		this.result = result;
		this.message = message;
	}
	public ApplicationEventResponse(int eventType, int result, String message, Object obj){
		this.type = eventType;
		this.result = result;
		this.message = message;
		this.encapData = obj;
	}	
	public ApplicationEventResponse(int eventType, int result, String message, Object obj,String refId){
		this.type = eventType;
		this.result = result;
		this.message = message;
		this.encapData = obj;
		this.refId = refId;
	}
	public ApplicationEventResponse(int eventType, int result){
		this.type = eventType;
		this.result = result;
	}
	public ApplicationEventResponse(int eventType, int result,NotifyDataM notify){
		this.type = eventType;
		this.result = result;
		this.notify = notify;
	}
}
