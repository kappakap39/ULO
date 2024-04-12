package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.MessageResourceUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadRecomendBranchCode extends WebActionHelper implements	WebAction {
	static Logger logger = Logger.getLogger(LoadRecomendBranchCode.class);
	private String nextAction = null;
	
	@Override
	public Event toEvent() {
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
	public boolean preModelRequest(){
		
		String branch_code = getRequest().getParameter("branch_code");
        String code = getRequest().getParameter("code");
        
        String textboxCode = getRequest().getParameter("textbox_code");
        String textboxDesc = getRequest().getParameter("textbox_desc");
        
        String ref_code01 = getRequest().getParameter("ref_code01");
        String ref_desc01 = getRequest().getParameter("ref_desc01");
        
        
//        #septemwi comment
//        String atPage = getRequest().getParameter("atPage");
        
        try {
        	StringBuilder sql = new StringBuilder();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			sql.append("SELECT KBANK_BR_NO, ADD_THAI_SHORT_ADDR ");
			sql.append("FROM VW_MS_BRANCH_CODE WHERE 1=1 ");
			
			if(!OrigUtil.isEmptyString(branch_code)){
			    sql.append("AND KBANK_BR_NO like ? ");
			    valueListM.setString(++index,branch_code+"%");
			}
			if(!OrigUtil.isEmptyString(code)){
			    sql.append("AND ADD_THAI_SHORT_ADDR like ? ");
			    valueListM.setString(++index,"%"+code+"%");
			}
			
			
			sql.append("ORDER BY KBANK_BR_NO ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadRecomendBranch");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=MASTER_POPUP_CODE");
            valueListM.setCurrentScreen("MASTER_POPUP_CODE");
            valueListM.setRef_code01(ref_code01);
            valueListM.setRef_desc01(ref_desc01);
            valueListM.setTitle(MessageResourceUtil.getTextDescription(getRequest(), "BRANCH_CODE"));            
            
        	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
        		(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
            valueListM.setScreenFlow(screenFlowManager.getCurrentScreen());
            
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
                    
//            #septemwi comment not used at page this webaction
//            if(null!=atPage){
//            	int atPaging = DataFormatUtility.StringToInt(atPage);
//                valueListM.setAtPage(atPaging);
//            }

            nextAction = "action=ValueListPopupWebAction";
            
            getRequest().getSession().removeAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE);
            
            if(!OrigUtil.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }
       }catch(Exception e){
           logger.error("exception ",e);
       }
       return true;
	}

	@Override
	public int getNextActivityType() {
        return FrontController.ACTION;
    }
   
    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken(){
		return false;
	}
}
