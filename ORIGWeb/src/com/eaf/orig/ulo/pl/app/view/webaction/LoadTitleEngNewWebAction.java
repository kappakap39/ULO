package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadTitleEngNewWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(LoadTitleEngNewWebAction.class);
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
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest() {
		
	    String code = getRequest().getParameter("code");
	    
		String textbox_code = getRequest().getParameter("textbox_code");		
		String textbox_desc = getRequest().getParameter("textbox_desc");
		String textbox_value = getRequest().getParameter("textbox_value");
		String hidden_value = getRequest().getParameter("hidden_value");
		
        try{
        	StringBuilder sql = new StringBuilder();
        	
			ValueListM valueListM = new ValueListM();
			int index = 0;
	        
			sql.append(" SELECT CHOICE_NO, DISPLAY_NAME, SYSTEM_ID1, ENG_DISPLAY_NAME, ACTIVE_STATUS, FIELD_ID ");
			sql.append(" FROM LIST_BOX_MASTER WHERE ACTIVE_STATUS= ? AND FIELD_ID= ? ");
			
			valueListM.setString(++index,OrigConstant.ACTIVE_FLAG);
			
			valueListM.setInt(++index,4);
			
			if(!ORIGUtility.isEmptyString(hidden_value)){
				sql.append("AND CHOICE_NO = ? ");
				valueListM.setString(++index,hidden_value);
			}else{			
				if(!ORIGUtility.isEmptyString(textbox_value)){
				    sql.append("AND DISPLAY_NAME LIKE ? ");
				    valueListM.setString(++index,textbox_value+"%");
				}
			}
			
			if(!ORIGUtility.isEmptyString(code)){
			    sql.append("AND DISPLAY_NAME LIKE ? ");
			    valueListM.setString(++index,code+"%");
			}			
			
			
			sql.append("ORDER BY CHOICE_NO ");
			
			valueListM.setTextboxCode(textbox_code);
			valueListM.setTextboxDesc(textbox_desc);
			valueListM.setSearchAction("LoadTitleEngNew");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=MASTER_POPUP_FIELDID_DESC");
            valueListM.setCurrentScreen("MASTER_POPUP_FIELDID_DESC");                    
            
        	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
        		(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
            valueListM.setScreenFlow(screenFlowManager.getCurrentScreen());
            
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);            
            
            nextAction = "action=ValueListPopupWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, textbox_value);
            }

       }catch(Exception e){
           logger.error("exception ",e);
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
