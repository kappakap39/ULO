/*
 * Created on Oct 3, 2007
 * Created by weeraya
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.popup.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

/**
 * @author weeraya
 *
 * Type: LoadMarkettingCodeWebAction
 */
public class LoadMarkettingCodeWebAction extends WebActionHelper implements WebAction {
    private static Logger logger = Logger.getLogger(LoadMarkettingCodeWebAction.class);
	private String nextAction = "";
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
         
        return null;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {
         
        return false;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        StringBuffer sql = new StringBuffer();
        
        String mkt_code = getRequest().getParameter("mkt_code");      
        String code = getRequest().getParameter("code");
        String pre_score_mkt_code = getRequest().getParameter("pre_score_mkt_code");
        
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
        
        String dataValue = getRequest().getParameter("dataValue");
        
        if( dataValue != null && !dataValue.equals("") ) {        	
        	getRequest().getSession().setAttribute("dataValue", dataValue);
        } else {
        	getRequest().getSession().setAttribute("checkMaster", "Y");
        } 
        
        logger.debug("dataValue = "+dataValue);   
        
        boolean isFrist = true;
        
        logger.debug("pre_score_mkt_code = "+pre_score_mkt_code);
        logger.debug("mkt_code = "+mkt_code);
        logger.debug("code = "+code);   
    
        try {
			ValueListM valueListM = new ValueListM();
			int index = 0;
//			Comment By Rawi Songchaisin 
//			sql.append("SELECT LOANOFF, THNAME, ENNAME ");
//			sql.append("FROM HPTBHP31 ");
            
			sql.append(" SELECT LOAN_OFFICER, TH_NAME, EN_NAME , ACTIVE_STATUS FROM MS_LOAN_OFFICER ");
			
			if(!ORIGUtility.isEmptyString(mkt_code)){
				//Comment By Rawi Songchaisin
			    //sql.append("WHERE LOANOFF = ? ");
				sql.append("WHERE LOAN_OFFICER = ? ");
			    valueListM.setString(++index,mkt_code);
			    isFrist = false;
			}else if(!ORIGUtility.isEmptyString(code)){
			    sql.append("WHERE UPPER(TH_NAME) like ? ");
			    valueListM.setString(++index,"%"+code.toUpperCase()+"%");
			    isFrist = false;
			}else if(!ORIGUtility.isEmptyString(pre_score_mkt_code)){
				sql.append("WHERE LOAN_OFFICER = ? ");
			    valueListM.setString(++index,pre_score_mkt_code);
			    isFrist = false;
			}
			sql.append(" ORDER BY LOAN_OFFICER ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadMarkettingCode");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
            	//getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, mkt_code);
            }

       } catch (Exception e) {
           logger.error("exception ",e);
       }
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
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
