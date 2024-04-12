package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ProcessMenuM;
import com.eaf.orig.ulo.pl.app.utility.WorkflowTool;
import com.eaf.orig.ulo.pl.model.app.ORIGInboxDataM;

public class InboxDCIcdcWebAction extends WebActionHelper implements WebAction {

	Logger logger = Logger.getLogger(this.getClass().getName());
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	@Override
	public boolean preModelRequest() {
		 logger.debug("InboxDCIcdcWebAction");
		/* UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		 BPMUtility bpmUtil=new BPMUtility();		 
		 //load Inbox Data
		 String userName=userM.getUserName();
		 String role = userM.getCurrentRole();
		 
		 String tdId = getRequest().getParameter("MenuID");
		 //if not found Menu ID from screen then get Menu ID from session
		 if(tdId == null || "".equals(tdId)){
			 ProcessMenuM currentMenuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
			 if(currentMenuM != null){
				 tdId = currentMenuM.getMenuID();
			 }
		 }
		 String queueType = getRequest().getParameter("queueType");
		 if(queueType == null || "".equals(queueType)){
				queueType = WorkflowConstant.ActivityType.INDIVIDUAL_QUEUE;
		 }
		 
		 String atPage="0";
		 String itemPerPage= getRequest().getParameter("itemsPerPage");
		 if(itemPerPage == null || "".equals(itemPerPage)){
			 itemPerPage = "20";
		 }
		 
		 String totalItem="";
		 boolean isNextPage=false;
		 PLInboxModelDataM inboxModel=bpmUtil.searchBPMJobList(userName, role, tdId, queueType, atPage, itemPerPage, totalItem, isNextPage);
		 logger.debug("inboxVect="+inboxModel);
		 getRequest().getSession().setAttribute("PL_INBOX_DATA",inboxModel) ;		*/
		 UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		 ProcessMenuM menuM = (ProcessMenuM) getRequest().getSession().getAttribute("CurrentMenuM");
			if(menuM == null) menuM = new ProcessMenuM();			
	     WorkflowTool wfXMLUtil = new WorkflowTool();		
	     ORIGInboxDataM oInboxM = null;
	     try{
	    	 oInboxM = wfXMLUtil.SearchWorkQueue(getRequest(), userM, menuM);	 	    	 
	     }catch(Exception e){
	    	 logger.fatal("Error "+e.getMessage());
		 }			
	     if(null == oInboxM) oInboxM = new ORIGInboxDataM();	     
	     getRequest().getSession().setAttribute(ORIGInboxDataM.Constant.ORIG_INBOX,oInboxM);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	@Override
	public int getNextActivityType() {
		 
		return FrontController.PAGE;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
