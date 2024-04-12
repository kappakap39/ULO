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
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;

public class LoadSellerBranchCode extends WebActionHelper implements WebAction {
	 Logger logger = Logger.getLogger(LoadSellerBranchCode.class);
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
		String seller_branch_code = getRequest().getParameter("seller_branch_code");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
//        String dataValue = getRequest().getParameter("dataValue");
//        String branch = getRequest().getParameter("channel");
        String atPage = getRequest().getParameter("atPage");
        
        logger.debug("[SellerBranch]..atPage = "+atPage);
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT KBANK_BR_NO, ADD_THAI_SHORT_ADDR ");
			sql.append("FROM VW_MS_BRANCH_CODE WHERE 1=1 ");
			
			if(!ORIGUtility.isEmptyString(seller_branch_code)){
			    sql.append("AND KBANK_BR_NO like ? ");
			    valueListM.setString(++index,seller_branch_code+"%");
			}
			
			if(!ORIGUtility.isEmptyString(code)){
				sql.append("AND ADD_THAI_SHORT_ADDR like ? ");
			    valueListM.setString(++index,"%"+code+"%");
			}
						
			sql.append("ORDER BY KBANK_BR_NO ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadSellerBranch");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(true);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=PL_MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            if(null!=atPage){
            	int atPaging = DataFormatUtility.StringToInt(atPage);
                valueListM.setAtPage(atPaging);
            }
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, seller_branch_code);
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
   
    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}
}
