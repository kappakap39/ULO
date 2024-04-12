package com.eaf.orig.shared.view.webaction;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class SaveNotePadWebAction extends WebActionHelper implements WebAction {

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public Event toEvent() {
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest() {
		Vector<NotePadDataM> noteVect = (Vector<NotePadDataM>) getRequest().getSession().getAttribute("NOTE_PAD_DATAM");
		String appRecId = getRequest().getParameter("appRecId");
		if(!OrigUtil.isEmptyVector(noteVect)){
			PLORIGEJBService.getORIGDAOUtilLocal().saveNotePad(noteVect, appRecId);
		}
		return false;
	}

	@Override
	public int getNextActivityType() {
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
