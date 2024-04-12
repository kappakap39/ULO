package com.eaf.orig.ulo.control.event;

import java.io.Serializable;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventHelper;
import com.eaf.orig.profile.model.UserDetailM;

public class DMEvent extends EventHelper implements Event, Serializable{
	private static final long serialVersionUID = 1L;
	public static final int DM_SAVE_STORE = 207;
	public static final int DM_SAVE_BORROW = 208;
		
	private UserDetailM userM;
	@Override
	public String getEventName() {
		 return "DMEvent";
	}
	public DMEvent(){
	}	
	public DMEvent(int eventType, Object object){
		this.setEventType(eventType);
		this.setObject(object);
	}	
	public DMEvent(int eventType, Object object, UserDetailM userM){
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
