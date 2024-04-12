package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class LoadProjectCodeWebAction extends WebActionHelper implements WebAction {
	 Logger logger = Logger.getLogger(LoadProjectCodeWebAction.class);	 
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
		 
    	PLOrigFormHandler origForm = (PLOrigFormHandler) getRequest().getSession().getAttribute("PLORIGForm");
    	PLApplicationDataM appM = origForm.getAppForm();
    	
    	getRequest().getSession(true).removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
    	
    	ORIGCacheUtil origc = new ORIGCacheUtil();
    	
        String project_code = getRequest().getParameter("project_code");
        String code = getRequest().getParameter("code");
        String textboxCode = "project_code";        
        
        String productFeature = origc.getSystemIDFromListbox(getRequest().getParameter("product_feature"), "35", "2");
        String exceptionProject = getRequest().getParameter("exception_project");
        
        logger.debug("project_code >> "+project_code);
        logger.debug("code >> "+code);
        logger.debug("exceptionProject >> "+exceptionProject);
        
        try{
	        	StringBuilder sql = new StringBuilder();
				ValueListM valueListM = new ValueListM();
				int index = 0;
				
				sql.append(" SELECT MSP.PROJECT_CODE,PROJECT_DESC,PROMOTION, START_DATE, END_DATE, APPROVE_DATE, APPLICANT_PROPERTY, MSPB.BUS_CLASS_ID");
				sql.append(" FROM MS_PROJECT_CODE MSP, MS_PROJECT_CODE_BUS_CLASS MSPB ");
				sql.append(" WHERE MSP.PROJECT_CODE = MSPB.PROJECT_CODE  AND MSP.ACTIVE_STATUS = 'A' " );
				sql.append(" AND MSP.APPLICANT_PROPERTY = ? ");
				
				if(OrigUtil.isEmptyString(productFeature)) productFeature="9999";
				valueListM.setString(++index,productFeature);
				
				sql.append("AND MSPB.BUS_CLASS_ID = ? ");
				valueListM.setString(++index,appM.getBusinessClassId());
				
				if(!OrigUtil.isEmptyString(code)){
				    sql.append("AND  MSP.PROJECT_CODE LIKE ? ");
				    valueListM.setString(++index,code.replace("%", "chr(37)")+"%");
				}
				
				if(!OrigUtil.isEmptyString(project_code)){
				    sql.append("AND  MSP.PROJECT_CODE LIKE ? ");
				    valueListM.setString(++index,project_code.replace("%", "chr(37)")+"%");
				}
	
				if(!"Y".equals(exceptionProject)){
					 sql.append("AND TRUNC(SYSDATE) BETWEEN MSP.START_DATE AND MSP.APPROVE_DATE ");
				}	
				
				sql.append("ORDER BY  MSP.PROJECT_CODE ");
				
				valueListM.setTextboxCode(textboxCode);
				valueListM.setSearchAction("LoadProjectCode");
	            valueListM.setSQL(String.valueOf(sql));
	            valueListM.setNextPage(false);
	            valueListM.setItemsPerPage(20);
	            valueListM.setReturnToAction("page=PROJECTCODE_MASTER_POPUP");
	            valueListM.setCurrentScreen("PROJECTCODE_MASTER_POPUP");
	            	            
	        	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
	        		(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
	            valueListM.setScreenFlow(screenFlowManager.getCurrentScreen());	            
	            
	            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
	            nextAction = "action=ValueListPopupWebAction";
	    
            if(!OrigUtil.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, project_code);
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