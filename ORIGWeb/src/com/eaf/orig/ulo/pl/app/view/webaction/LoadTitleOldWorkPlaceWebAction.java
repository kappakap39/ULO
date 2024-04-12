package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadTitleOldWorkPlaceWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(LoadTitleOldWorkPlaceWebAction.class);
	private String nextAction = null;
	@Override
	public Event toEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requiredModelRequest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean preModelRequest() {
		String oldofficenameTitle = getRequest().getParameter("occ_old_workplace_Title");
		String code = getRequest().getParameter("code");
		String textboxCode = "occ_old_workplace_Title";
        String textboxDesc = "occ_old_workplaceTitle";    
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
//			String textboxCode = getRequest().getParameter("textboxCode");
//	        String textboxDesc = getRequest().getParameter("textboxDesc");
			sql.append("SELECT CHOICE_NO, DISPLAY_NAME, ACTIVE_STATUS, FIELD_ID ");
			sql.append("FROM LIST_BOX_MASTER WHERE ACTIVE_STATUS='A' AND FIELD_ID='17' ");
			
			if(!ORIGUtility.isEmptyString(oldofficenameTitle)){
			    sql.append("AND DISPLAY_NAME like ? ");
			    valueListM.setString(++index,oldofficenameTitle+"%");
			}
			
			sql.append("ORDER BY CHOICE_NO ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadOldTitleWorkPlaceThai");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=PL_MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else if(oldofficenameTitle!=null && !"".equals(oldofficenameTitle)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, oldofficenameTitle);
            }

       } catch (Exception e) {
           logger.error("exception ",e);
       }
        return true;
    }

	@Override
	public int getNextActivityType() {
        return FrontController.ACTION;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
