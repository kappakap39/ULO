package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

public class LoadRecommendNameWebaction extends WebActionHelper implements	WebAction {
	static Logger logger = Logger.getLogger(LoadRecommendNameWebaction.class);
	private String nextAction = null;
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
        String textboxCode = getRequest().getParameter("textbox_code");
        String textboxDesc = getRequest().getParameter("textbox_desc");
       
        String saleID = getRequest().getParameter("sale_id");
        String ref_value01 = getRequest().getParameter("ref_value01");
        String ref_value02 = getRequest().getParameter("ref_value02");
        		        
		ORIGCacheUtil origc = new ORIGCacheUtil();
		String channelGroup = origc.getSystemIDFromListbox(ref_value02,"50","2");

        try{
        	StringBuilder sql = new StringBuilder();
        	
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT SALE_ID, SALE_NAME, SALE_TYPE ");
			sql.append("FROM VW_MS_SALE_PERSON WHERE 1=1 ");
			
			if(OrigUtil.isEmptyString(channelGroup)){
				channelGroup="9999";
			}
			
			if("seller_title".equalsIgnoreCase(textboxCode)){		
				if("02".equals(channelGroup)){
					sql.append("AND SALE_TYPE = ? ");
					valueListM.setString(++index,ref_value02);
				}else{
					sql.append("AND CHANNEL_GROUP = ? ");
					valueListM.setString(++index,channelGroup);
				}				
			}else{
				if("0400".equals(ref_value01)){
					sql.append("AND CHANNEL_GROUP = ? ");
					valueListM.setString(++index,"03");
				}
			}
			
			if(!OrigUtil.isEmptyString(saleID)){
				sql.append("AND SALE_ID like ? ");
			    valueListM.setString(++index,"%"+saleID+"%");
			}
			
			if(!OrigUtil.isEmptyString(code)){
				sql.append("AND SALE_NAME like ? ");
			    valueListM.setString(++index,"%"+code+"%");
			}
			
			if(OrigUtil.isEmptyString(ref_value01)){
				ref_value01 = "9999";
			}
			
			if(!"0400".equals(ref_value01)){
				sql.append("AND BRANCH_CODE = ? ");
				valueListM.setString(++index,ref_value01);
			}
			
			sql.append("AND STATUS = ? ");
			valueListM.setString(++index,OrigConstant.Status.STATUS_ACTIVE);
			
			sql.append("ORDER BY SALE_ID ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setRef_value01(ref_value01);
			valueListM.setRef_value02(ref_value02);
			valueListM.setSearchAction("LoadRecommendInformation");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(20);
            valueListM.setReturnToAction("page=MASTER_POPUP_CODE");
            valueListM.setCurrentScreen("MASTER_POPUP_CODE");
                        
        	com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
        		(com.eaf.j2ee.pattern.control.ScreenFlowManager) getRequest().getSession(true).getAttribute("screenFlowManager");
            valueListM.setScreenFlow(screenFlowManager.getCurrentScreen());            
            
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);

            nextAction = "action=ValueListPopupWebAction";
            
            getRequest().getSession().removeAttribute( ValueListWebAction.VALUE_LIST_SEARCH_CODE);
            
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
