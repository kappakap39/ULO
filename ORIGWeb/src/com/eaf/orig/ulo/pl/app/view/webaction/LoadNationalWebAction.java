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

public class LoadNationalWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(LoadNationalWebAction.class);
	private String nextAction = "";
	@Override
	public Event toEvent(){
		return null;
	}
	@Override
	public boolean requiredModelRequest(){
		return false;
	}
	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}
	@Override
	public boolean preModelRequest() {
				
		String code = getRequest().getParameter("code");
        String country = getRequest().getParameter("country_desc");
       
        String textboxCode = "country_no";
        String textboxDesc = "country_desc";
        try{
        	
    		StringBuilder sql = new StringBuilder();
        	
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT CHOICE_NO, DISPLAY_NAME ");
			sql.append("FROM LIST_BOX_MASTER WHERE FIELD_ID='104' AND ACTIVE_STATUS = 'A' ");
            
			if(!ORIGUtility.isEmptyString(country)){
			    sql.append("AND DISPLAY_NAME like ? ");
				valueListM.setString(++index, "%" + country.replace("%", "chr(37)") + "%");
			}
			
			if(!ORIGUtility.isEmptyString(code)){
			    sql.append("AND DISPLAY_NAME like ? ");
				valueListM.setString(++index, "%" + code.replace("%", "chr(37)") + "%");
			}
			sql.append("ORDER BY CHOICE_NO ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadNational");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=MASTER_DIALOG_POPUP");
            valueListM.setCurrentScreen("MASTER_DIALOG_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListDialogPopup";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, country);
            }

       } catch (Exception e) {
           logger.error("exception ",e);
       }
        return true;
	}

	@Override
	public int getNextActivityType(){
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
