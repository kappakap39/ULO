package com.eaf.orig.ulo.control.event;

import java.io.Serializable;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventHelper;
import com.eaf.orig.profile.model.UserDetailM;

public class ApplicationEvent extends EventHelper implements Event, Serializable{
	private static final long serialVersionUID = 1L;
	private UserDetailM userM;
	public static final int SAVE_APPLICATION = 800;
	public static final int SUBMIT_APPLICATION = 808;
	public static final int CANCEL_APPLICATION = 809;
	@Override
	public String getEventName() {
		 return "ApplicationEvent";
	}
	public ApplicationEvent(){
	}	
	public ApplicationEvent(int eventType, Object object){
		this.setEventType(eventType);
		this.setObject(object);
	}	
	public ApplicationEvent(int eventType, Object object, UserDetailM userM){
		this.setEventType(eventType);
		this.setObject(object);
		this.setUserName((userM!=null)?userM.getUserName():"");
		this.setUserM(userM);
	}
	public UserDetailM getUserM() {
		return userM;
	}
	public void setUserM(UserDetailM userM) {
		this.userM = userM;
	}	
}
