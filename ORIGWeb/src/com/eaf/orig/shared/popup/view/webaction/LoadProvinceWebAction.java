package com.eaf.orig.shared.popup.view.webaction;


import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadProvinceWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadProvinceWebAction.class);
    private String nextAction = null;
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
    	
        String province = getRequest().getParameter("province");        
        String code = getRequest().getParameter("code");
        
        String textboxCode = "province";
        String textboxDesc = "province_desc";
        
        logger.debug("province = "+province);
        logger.debug("code = "+code);
        
        try {
        	StringBuilder sql = new StringBuilder();
			ValueListM valueListM = new ValueListM();
			int index = 0;

			sql.append(" SELECT PROVINCE_ID, PROVINCE_DESC ");
			sql.append(" FROM MS_PROVINCE WHERE ACTIVE_STATUS = 'A' ");
			
			if(!OrigUtil.isEmptyString(code)){
			    sql.append("AND PROVINCE_DESC like ? ");
			    valueListM.setString(++index,"%"+code+"%");
			}
			
			if(!OrigUtil.isEmptyString(province)){
			    sql.append("AND PROVINCE_ID = ? ");
			    valueListM.setString(++index,province);
			}
			
			sql.append("ORDER BY PROVINCE_ID ");			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadProvince");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=MASTER_DIALOG_POPUP");
            valueListM.setCurrentScreen("MASTER_DIALOG_POPUP");
            
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            
            nextAction = "action=ValueListDialogPopup";
            
            getRequest().getSession().removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);  
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }
            
       } catch (Exception e) {
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
