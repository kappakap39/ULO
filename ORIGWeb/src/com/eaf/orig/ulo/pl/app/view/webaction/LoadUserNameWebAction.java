package com.eaf.orig.ulo.pl.app.view.webaction;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;

public class LoadUserNameWebAction extends WebActionHelper implements WebAction {
	
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
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		String role = getRole(getRequest(),userM);	
		String search_code = getRequest().getParameter("search_code");
		String userName = getRequest().getParameter("userName");	
		String code = getRequest().getParameter("code");

		
		String textboxCode = "userName";
        String textboxDesc = "reassignTo";
		
        log.debug("role reassign >> "+role);
        
		try{
			
			ValueListM valueListM = new ValueListM();
			
			int index = 1;
			
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT US.THAI_FIRSTNAME, US.THAI_LASTNAME, UR.USER_NAME ");
			sql.append(" FROM USER_ROLE_JOIN_ROLE UR, US_USER_DETAIL US ");
			sql.append(" WHERE UR.USER_NAME = US.USER_NAME ");
			sql.append(" AND UR.ROLE_NAME = ? ");
			
			valueListM.setString(index++, role);
			
			if(!OrigUtil.isEmptyString(code)){
				sql.append("AND TRIM(UPPER(REPLACE(US.THAI_FIRSTNAME|| US.THAI_LASTNAME,' ',''))) LIKE (TRIM(UPPER( REPLACE(?,' ','')))) ");
				valueListM.setString(index++, code+"%");
			}else{
				if(!OrigUtil.isEmptyString(userName) && !"SEARCH_CODE".equals(search_code)){
					sql.append("AND TRIM(UPPER(REPLACE(US.THAI_FIRSTNAME|| US.THAI_LASTNAME,' ',''))) LIKE (TRIM(UPPER( REPLACE(?,' ','')))) ");
					valueListM.setString(index++, userName+"%");
				}
			}			
			sql.append("ORDER BY US.THAI_FIRSTNAME");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSQL(String.valueOf(sql));
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setSearchAction("LoadUserNameWebAction");
			valueListM.setReturnToAction("page=USER_POPUP_SEREEN");
			            
        	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
        		(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
            valueListM.setScreenFlow(screenFlowManager.getCurrentScreen());
            			
			valueListM.setCurrentScreen("USER_POPUP_SEREEN");
			
			getRequest().getSession().setAttribute("DIALOG_LIST", valueListM);
			nextAction = "action=ValueListDialog";
			
			getRequest().getSession().removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);	
			
			if(!OrigUtil.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else if(!"SEARCH_CODE".equals(search_code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, userName);
            }
		
		}catch(Exception e){
			log.error("exception ", e);
		}
		return true;
	}
	
	public String getRole(HttpServletRequest request,UserDetailM userM){
		return ORIGLogic.getReassignRole(request,userM);
	}
	
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter() {
		return nextAction;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
