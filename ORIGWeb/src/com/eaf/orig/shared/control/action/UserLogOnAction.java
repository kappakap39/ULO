package com.eaf.orig.shared.control.action;

import com.eaf.j2ee.pattern.control.action.Action;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.control.event.EventResponseHelper;
import com.eaf.orig.shared.control.event.UserLogOnEventResponse;

/**
 * @author burin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UserLogOnAction implements Action {

	/**
	 * Constructor for UserLogOnAction.
	 */
	public UserLogOnAction() {
		super();
	}

	/**
	 * @see com.eaf.j2ee.pattern.control.action.Action#perform(Event)
	 */
	public EventResponse perform(Event ev) {
		int result = 0;
		String message = "";
		try {
			// type on = 1 off= 0
//			UserLogOnEvent lev = (UserLogOnEvent) ev;
//			int eventType = lev.getEventType();
		//	SignOnUtilDAO dao = GEDAOFactory.getSignOnUtilDAO();

			/*switch (eventType) {
				case UserLogOnEvent.TYPE_ON :
					{
						dao.CALogOn(lev.getUserName(),lev.getRoles().toString());
						result = EventResponseHelper.SUCCESS;

					}

					break;
				case UserLogOnEvent.TYPE_OFF :
					{
						dao.CALogOff(lev.getUserName()); 
						result = EventResponseHelper.SUCCESS;
					}

					break;

				default :
					break;
			}  */
		} catch (Exception e) {
			result = EventResponseHelper.FAILED;
			e.printStackTrace();
			message = e.getMessage();
		}
		return new UserLogOnEventResponse(result,message);

	}

}
