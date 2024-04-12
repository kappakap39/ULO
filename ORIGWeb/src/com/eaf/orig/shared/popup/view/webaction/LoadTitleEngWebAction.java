/*
 * Created on Oct 5, 2007
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
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.valuelist.view.webaction.ValueListWebAction;

/**
 * @author weeraya
 *
 * Type: LoadTitleEngWebAction
 */
public class LoadTitleEngWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(LoadTitleEngWebAction.class);
    private String nextAction = null;
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
        ORIGUtility utility = new ORIGUtility();
        String title_eng = getRequest().getParameter("title_eng_desc");
        String m_title_eng = getRequest().getParameter("m_title_eng_desc");
        String code = getRequest().getParameter("code");
        String textboxCode = getRequest().getParameter("textboxCode");
        String textboxDesc = getRequest().getParameter("textboxDesc");
        String dataValue = getRequest().getParameter("dataValue");
        //    	Change desc to code
        ORIGCacheUtil origCache = new ORIGCacheUtil();
        dataValue = origCache.getORIGMasterCodeDataM("TitleEng", dataValue);
        if( dataValue != null && !dataValue.equals("") ) {
        	//getRequest().getSession().setAttribute("checkMaster", "1");
        	getRequest().getSession().setAttribute("dataValue", dataValue);
        } else {
        	getRequest().getSession().setAttribute("checkMaster", "Y");
        }
        
        logger.debug("title_eng = "+title_eng);
        logger.debug("m_title_eng = "+m_title_eng);
        logger.debug("code = "+code);
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			//sql.append("SELECT TITCDE, ENDESC, THDESC, SCORE, RECSTS ");
			//sql.append("FROM HPTBHP03 ");
			sql.append("SELECT TITLE_CODE, EN_DESC, ACTIVE_STATUS ");
			sql.append("FROM MS_TITLE_NAME ");
			sql.append("WHERE EN_DESC IS NOT NULL ");
            
			if(!ORIGUtility.isEmptyString(title_eng)){
			    sql.append("AND UPPER(EN_DESC) like ? ");
			    valueListM.setString(++index,title_eng.toUpperCase()+"%");
			}else if(!ORIGUtility.isEmptyString(m_title_eng)){
			    sql.append("AND UPPER(EN_DESC) like ? ");
			    valueListM.setString(++index,m_title_eng.toUpperCase()+"%");
			}else if(!ORIGUtility.isEmptyString(code)){
			    sql.append("AND UPPER(EN_DESC) like ? ");
			    valueListM.setString(++index,"%"+code.toUpperCase()+"%");
			}
			sql.append("ORDER BY TITLE_CODE ");
			
			valueListM.setTextboxCode(textboxCode);
			valueListM.setTextboxDesc(textboxDesc);
			valueListM.setSearchAction("LoadTitleEng");
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=MASTER_POPUP");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            if(!ORIGUtility.isEmptyString(code)){
            	getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, code);
            }else{
//            	if(title_eng!=null && !"".equals(title_eng)){
//            		getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, title_eng);
//            	}else if(m_title_eng!=null && !"".equals(m_title_eng)){
//            		getRequest().getSession().setAttribute(ValueListWebAction.VALUE_LIST_SEARCH_CODE, m_title_eng);
//            	}
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
