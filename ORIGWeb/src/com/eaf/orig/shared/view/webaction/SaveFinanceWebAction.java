/*
 * Created on Sep 27, 2007
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
package com.eaf.orig.shared.view.webaction;

import java.math.BigDecimal;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.BankDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.model.PopulatePopupM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;

/**
 * @author weeraya
 *
 * Type: SaveFinanceWebAction
 */
public class SaveFinanceWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SaveFinanceWebAction.class);
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
        ORIGFormHandler formHandler = (ORIGFormHandler) getRequest().getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        ORIGUtility utility = new ORIGUtility();
        ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
        
        try{
	        String type = getRequest().getParameter("type");
	        String account_no = getRequest().getParameter("account_no");
	        String open_date = getRequest().getParameter("open_date");
	        String bank = getRequest().getParameter("bank");
	        String amount = getRequest().getParameter("amount");
	        String expiry_date = getRequest().getParameter("expiry_date");
	        String branch = getRequest().getParameter("branch");
	        String seq = getRequest().getParameter("finance_seq");
	        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
	        
	        logger.debug("Bank seq = "+seq);
	        logger.debug("Personal Type = "+personalType);
	        
	        ApplicationDataM applicationDataM = formHandler.getAppForm();
	        PersonalInfoDataM personalInfoDataM = new PersonalInfoDataM();
	    	if(OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)){
	    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
	    	}else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
	    		personalInfoDataM = utility.getPersonalInfoByType(formHandler.getAppForm(),OrigConstant.PERSONAL_TYPE_APPLICANT);
	    	}else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
	            personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
	        }
	    	
	        Vector financeVect = personalInfoDataM.getFinanceVect();
	        
	        //Get current bank data
	        BankDataM bankDataM;
	        if(Integer.parseInt(seq) == 0){// Add new finance data
	            bankDataM = new BankDataM();
	            bankDataM.setSeq(personalInfoDataM.getFinanceIndex());
	            personalInfoDataM.setFinanceIndex(personalInfoDataM.getFinanceIndex()+1);
	            financeVect.add(bankDataM);
	        }else{//Edit finance data
	            bankDataM = utility.getFinanceBySeq(financeVect, Integer.parseInt(seq));
	        }
	        
	        bankDataM.setFinancialType(type);
	        bankDataM.setAccountNo(account_no);
	        bankDataM.setOpenDate(ORIGUtility.parseThaiToEngDate(open_date));
	        
	        bankDataM.setBankCode(bank);
	        if(!ORIGUtility.isEmptyString(amount)){
	            bankDataM.setAmount(ORIGDisplayFormatUtil.replaceCommaForBigDecimal(amount));
	        }else{
	            bankDataM.setAmount(new BigDecimal(0));
	        }
	        bankDataM.setExpiryDate(ORIGUtility.parseThaiToEngDate(expiry_date));
	        bankDataM.setBranchCode(branch);
	        bankDataM.setOrigOwner("Y");
	        
	        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
	        if(ORIGUtility.isEmptyString(bankDataM.getCreateBy())){
	        	bankDataM.setCreateBy(userM.getUserName());
	        }else{
	        	bankDataM.setUpdateBy(userM.getUserName());
	        }
	        
	        //Rewrite
	        String tableData = utility.getFinanceTable(financeVect, getRequest());
	        
	        PopulatePopupM populatePopupM = new PopulatePopupM("Finance",ORIGDisplayFormatUtil.replaceDoubleQuot(tableData.toString()));
			Vector popMs = new Vector();
			popMs.add(populatePopupM);
			getRequest().getSession(true).setAttribute("POPULATE_POPUP",popMs);
			popMs = (Vector)getRequest().getSession(true).getAttribute("POPULATE_POPUP");
        }catch(Exception e){
            logger.error("Error in SaveFinanceWebAction.preModelRequest() >> ", e);
        }
        
        return true;
    }

    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.FORWARD;
    }
    /* (non-Javadoc)
	 * @see com.bara.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
	 */
	public String getNextActionParameter() {
		return "orig/appform/filterMainScreen.jsp";
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
