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
import com.eaf.orig.ulo.pl.model.app.UserPointDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class InboxCAIcdcWebAction extends WebActionHelper implements WebAction {

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
		 logger.debug("InboxCAIcdcWebAction");
		 UserDetailM userM =	(UserDetailM) getRequest().getSession().getAttribute("ORIGUser");	 
		 //load Inbox Data
		 String userName = userM.getUserName();
		 
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
	     
		 UserPointDataM userPoint = null;
		 try{
//			 userPoint = PLORIGDAOFactory.getPLOrigUserDAO().getUserPointDetails(userName);
			 userPoint = PLORIGEJBService.getORIGDAOUtilLocal().getUserPointDetails(userName, menuM);
		 }catch (Exception e){
			 e.printStackTrace();
			 logger.fatal("##### Cannot get user point details :" + e.getMessage());
			 userPoint = new UserPointDataM();
		 }
		 getRequest().getSession().setAttribute("PL_USER_POINT",userPoint) ;
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
