package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadProjectCodeAllWebAction extends WebActionHelper implements WebAction {

	private Logger log = Logger.getLogger(this.getClass());
	private String nextAction = null;
	
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
		
		UserDetailM userM = (UserDetailM)getRequest().getSession().getAttribute("ORIGUser");
		String project_code = getRequest().getParameter("project_code");
		String textboxCode = "project_code";
        String textboxDesc = getRequest().getParameter("textboxDesc");
        
        Calendar calendar = Calendar.getInstance();
		Date currentDate = new Date();
		calendar.setTime(currentDate);

        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT  distinct PC.PROJECT_CODE, PC.PROJECT_DESC, PC.PROMOTION, PC.START_DATE, PC.END_DATE, ");
			sql.append("PC.APPROVE_DATE, PC.APPLICANT_PROPERTY ");
			sql.append("FROM MS_PROJECT_CODE PC, MS_PROJECT_CODE_BUS_CLASS PCB, TABLE( bus_class.getBusClassByUser(?) ) BUS ");
			valueListM.setString(++index,userM.getUserName());
			
			sql.append("WHERE PC.PROJECT_CODE = PCB.PROJECT_CODE AND PCB.BUS_CLASS_ID = BUS.BUS_CLASS_ID ");
			
			if(!ORIGUtility.isEmptyString(project_code)){
			    sql.append("AND PC.PROJECT_CODE LIKE ? ");
			    valueListM.setString(++index, project_code.replace("%", "chr(37)")+"%");
			}
			
			sql.append("ORDER BY PC.PROJECT_CODE ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadProjectCodeAll");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=PROJECT_CODE_ALL_POPUP");
            valueListM.setCurrentScreen("PROJECT_CODE_ALL_POPUP");
                        
        	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
        		(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
            valueListM.setScreenFlow(screenFlowManager.getCurrentScreen());            
            
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListPopupWebAction";
            
            if(!ORIGUtility.isEmptyString(project_code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, project_code);
            }
            
       } catch (Exception e) {
           log.fatal("exception ",e);
       }
        return true;
    }

    public int getNextActivityType() {
        return FrontController.ACTION;
    }
    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
