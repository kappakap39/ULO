/*
 * Created on Aug 22, 2008
 * Created by Sankom
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

import java.math.BigDecimal;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.model.output.TLRespM;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.xrules.shared.model.XRulesNCBAdjustDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: SaveXrulesNCBPaymentDetailPopupWebAction
 */
public class SaveXrulesNCBPaymentDetailPopupWebAction extends WebActionHelper implements WebAction {
    private static Logger log = (Logger) Logger.getLogger(SaveXrulesNCBPaymentDetailPopupWebAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        log.debug("Save XRules NCB Adjust Webaction");
        ORIGUtility utility = new ORIGUtility();
		OrigXRulesUtil origXruleUtil=OrigXRulesUtil.getInstance();
        TLRespM currentAccountM = (TLRespM) getRequest().getSession().getAttribute("NCBAccount");
        PersonalInfoDataM personalInfoDataM;
        String personalType = (String) getRequest().getSession().getAttribute("PersonalType");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");		
		UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM)  getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        XRulesVerificationResultDataM prmXRulesVer=personalInfoDataM.getXrulesVerification();
        Vector vNCBAdjust=null;
        if(prmXRulesVer!=null){
            vNCBAdjust=prmXRulesVer.getVNCBAdjust();    
        }
        String segmentCode=getRequest().getParameter("ncbAccountSegmentCode");
        String groupSeq=getRequest().getParameter("ncbAccountGroupSeq");
        log.debug("Segment Code-->"+segmentCode);
        String strInstallmentAdjust=getRequest().getParameter("ncbAdjust");
        log.debug("ncbAdjust-->"+strInstallmentAdjust);
        BigDecimal installmnetAdjust=null;
        if("-".equals(strInstallmentAdjust)){
            
        }else{
          installmnetAdjust=utility.stringToBigDecimal(strInstallmentAdjust);
        }
        int iGroupSeq=utility.stringToInt(groupSeq);
        if(segmentCode==null){segmentCode="";}
        if(vNCBAdjust==null){
            vNCBAdjust=new Vector();  
            prmXRulesVer.setVNCBAdjust(vNCBAdjust);
        }
        boolean found=false;
           for(int i=0;i<vNCBAdjust.size();i++){
               XRulesNCBAdjustDataM  prmNCBAdjust=(XRulesNCBAdjustDataM)vNCBAdjust.get(i);
               if(segmentCode.equals(prmNCBAdjust.getNCBSegmentValue()) && iGroupSeq==prmNCBAdjust.getGroupSeq()){                  
                   prmNCBAdjust.setNcbInstallmentAdjustAmount(installmnetAdjust); 
                   prmNCBAdjust.setUpdateBy(ORIGUser.getUserName());
                   if(prmNCBAdjust.getNcbInstallmentAdjustAmount()==null){
                     vNCBAdjust.remove(i); 
                   }                   
                   found=true;
                   break;
               }
           }            
        if(!found){
            log.debug("Not Found Add new NCB Adjust");
            XRulesNCBAdjustDataM  prmNCBAdjust=new XRulesNCBAdjustDataM();
            prmNCBAdjust.setNCBSegmentValue(segmentCode);
            prmNCBAdjust.setNCBTracingCode(prmXRulesVer.getNCBTrackingCode());
            prmNCBAdjust.setNcbInstallmentAdjustAmount(installmnetAdjust);
            prmNCBAdjust.setGroupSeq(iGroupSeq);
            prmNCBAdjust.setCreateBy(ORIGUser.getUserName());
            prmNCBAdjust.setUpdateBy(ORIGUser.getUserName());
            vNCBAdjust.add(prmNCBAdjust);
        }
        //OrigXRulesUtil xrulesUtil = OrigXRulesUtil.getInstance();
        //xrulesUtil.getXRulesVerificationRusult(
           //     xRulesVerification, XRulesConstant.ServiceID.DEBT_AMOUNT);
         XRulesVerificationResultDataM  xrulesVerification =personalInfoDataM.getXrulesVerification();
         xrulesVerification.setDebtAmountAdjustResult(null);
         xrulesVerification.setDebtAmountResult(null);
        log.debug("vNCBAdjust Size="+vNCBAdjust.size());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {

        return FrontController.PAGE;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
