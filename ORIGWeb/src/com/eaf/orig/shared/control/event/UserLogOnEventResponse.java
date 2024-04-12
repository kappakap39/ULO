package com.eaf.orig.shared.control.event;

import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;

/**
 * @author burin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UserLogOnEventResponse
	extends EventResponseHelper
	implements EventResponse {
	public UserLogOnEventResponse(int result, String message) {
		this.result = result;
		this.message = message;
	}
}
