/*
 * Created on Oct 12, 2007
 *
 * 
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.webaction;

import java.sql.Timestamp;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.NotePadDataM;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;


/**
 * @author Prawit
 *
 * Type: LoadNotepadPopupWebaction
 */
public class AddNotepadWebAction extends WebActionHelper implements
		WebAction {

	static Logger log = Logger.getLogger(AddNotepadWebAction.class);
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		 
		return null;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		 
		try{
			log.debug(">>>>>>>>>>>>>>>>add notepad");
			//ORIGFormHandler Orig_Form = (ORIGFormHandler)getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
			PLOrigFormHandler plOrig_Form = (PLOrigFormHandler)getRequest().getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
			PLApplicationDataM appForm = plOrig_Form.getAppForm();
			Vector notepadVt = appForm.getNotepadDataM();
			if(notepadVt==null){
				notepadVt = new Vector();
				appForm.setNotepadDataM(notepadVt);
			}
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			NotePadDataM notePadM = new NotePadDataM();
			notePadM.setNotePadDesc(getRequest().getParameter("notepad"));
			log.debug("notePadM.getDesc()" + notePadM.getNotePadDesc());
			notePadM.setUpdateBy(userM.getUserName());
			notePadM.setCreateBy(userM.getUserName());
			notePadM.setCreateDate(new Timestamp(System.currentTimeMillis()));
			notePadM.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			notePadM.setApplicationRecordId(appForm.getAppRecordID());
			notepadVt.add(notePadM);
			log.debug(">>>> notepadVt.size()" + notepadVt.size());
			appForm.setNotepadDataM(notepadVt);
		}catch(Exception e){
			log.debug("exception",e);
			return false;
		}		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		 
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
