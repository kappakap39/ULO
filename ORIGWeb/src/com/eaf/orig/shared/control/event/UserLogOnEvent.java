package com.eaf.orig.shared.control.event;

import java.util.Vector;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventHelper;

/**
 * @author burin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UserLogOnEvent extends EventHelper implements Event {

	/**
	 * @see com.eaf.j2ee.pattern.control.event.Event#getEventName()
	 */
	// type on = 1 off= 0
	public static final int TYPE_ON = 1;
	public static final int TYPE_OFF = 0;
	private Vector roles ;
	public UserLogOnEvent(String user, int type,Vector roles) {
		this.setUserName(user);
		this.setEventType(type);
		this.setRoles(roles);

	}
	public String getEventName() {
		return "UserLogOnEvent";
	}


	/**
	 * Returns the role.
	 * @return String
	 */
	public Vector getRoles() {
		return roles;
	}

	/**
	 * Sets the role.
	 * @param role The role to set
	 */
	public void setRoles(Vector roles) {
		this.roles = roles;
	}

}
