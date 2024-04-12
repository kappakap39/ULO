package com.eaf.orig.shared.view.webaction;

import java.util.Vector;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class LoadNotepadWebAction extends WebActionHelper implements WebAction {
	
	public Event toEvent(){		 
		return null;
	}
	
	public boolean requiredModelRequest(){		 
		return false;
	}
	
	public boolean processEventResponse(EventResponse response){		 
		return false;
	}
	
	public boolean preModelRequest(){
		String appRecId = getRequest().getParameter("appRecId");
		Vector<NotePadDataM> noteVect = PLORIGEJBService.getORIGDAOUtilLocal().loadNotePad(appRecId);
			getRequest().getSession().setAttribute("NOTE_PAD_DATAM", noteVect);
		return true;
	}
	
	public int getNextActivityType(){		 
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
