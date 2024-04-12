/*
 * Created on Nov 8, 2007
 * Created by Sankom Sanpunya
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
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.ncb.model.output.IDRespM;
import com.eaf.ncb.model.output.NCBOutputDataM;
import com.eaf.ncb.model.output.PNRespM;
import com.eaf.ncb.service.ejb.NCBServiceManager;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.OrigXRulesUtil;
import com.eaf.xrules.shared.model.XRulesNCBDataM;
import com.eaf.xrules.shared.model.XRulesVerificationResultDataM;

/**
 * @author Sankom
 * 
 * Type: LoadXrulesNCBSummaryPopupWebAction
 */
public class LoadXrulesNCBSummaryPopupWebAction extends WebActionHelper implements WebAction {
    private static Logger log = (Logger) Logger.getLogger(LoadXrulesNCBSummaryPopupWebAction.class);

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {

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
        //loan NCB DataM
        log.debug("LoadXrulesNCBDetailPopupWebAction-->preModelRequest");
        log.debug("LoadXrulesNCBDetailPopupWebAction Webation");
        ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");
        UserDetailM ORIGUser = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        ApplicationDataM applicationDataM = ORIGForm.getAppForm();
        if (applicationDataM == null) {
            applicationDataM = new ApplicationDataM();
        }
        ORIGUtility utility = new ORIGUtility();
        String personalType = getRequest().getParameter("appPersonalType");//(String)
                                                                           // getRequest().getSession().getAttribute("PersonalType");
        PersonalInfoDataM personalInfoDataM;
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("MAIN_POPUP_DATA");
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
    		personalInfoDataM = (PersonalInfoDataM) getRequest().getSession(true).getAttribute("SUPCARD_POPUP_DATA");
		} else {
            personalInfoDataM = utility.getPersonalInfoByType(ORIGForm.getAppForm(), OrigConstant.PERSONAL_TYPE_APPLICANT);
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
            //personalInfoDataM.setPersonalType(personalType);
            //applicationDataM.getPersonalInfoVect().add(personalInfoDataM);
        }
        //======================================
        if (getRequest().getParameter("viewFromReport") != null) {
            if (getRequest().getSession().getAttribute("applicationVerification") != null) {
                applicationDataM = (ApplicationDataM) getRequest().getSession().getAttribute("applicationVerification");
                String reportPersonalSeq = getRequest().getParameter("reportPersonalSeq");
                String reportPersonalType = getRequest().getParameter("reportPersonalType");
                int personalSeq = utility.stringToInt(reportPersonalSeq);
                personalInfoDataM = utility.getPersonalInfoByTypeAndSeq(applicationDataM, reportPersonalType, personalSeq);
            }
        }
        if (personalInfoDataM == null) {
            personalInfoDataM = new PersonalInfoDataM();
        }
        //	======================================
        XRulesVerificationResultDataM xRulesVerification = personalInfoDataM.getXrulesVerification();
        if (xRulesVerification == null) {
            xRulesVerification = new XRulesVerificationResultDataM();
        }
        String tracingCode = xRulesVerification.getNCBTrackingCode();
        log.debug("tracingCode " + tracingCode);
        //Vector vNCBEnquiry=new Vector();
        NCBOutputDataM ncbOutput = null;
        try {
            if ((tracingCode != null && !"".equals(tracingCode))) {
                NCBServiceManager ncbService = ORIGEJBService.getNCBServiceManager();
                //Change Loan NCB Data
                //vNCBEnquiry=ncbService.getNCBEnquiry(tracingCode);
                ncbOutput = ncbService.getNCBOutput(tracingCode);
                ncbService.getNCBReqRespConsent(tracingCode);
            }
            //Set Data to Session
        } catch (Exception e) {
            log.fatal("getNCBOutput error",e);
        }
        
        
        String totalInstallment = "";
        HashMap hNCBSummayAdjustDetail=new HashMap();
        BigDecimal debdbParm=null;
        if(OrigConstant.ORIG_FLAG_Y.equals(xRulesVerification.getDebtAmountODInterestFlag())){
            if( xRulesVerification.getDEBT_BD_PARAM() == null){
                debdbParm=utility.stringToBigDecimal(utility.getGeneralParamByCode("DEPT_AMT_OD_INTEREST"));
            } else{
                debdbParm=xRulesVerification.getDEBT_BD_PARAM();
            }
        
        }
        OrigXRulesUtil origXRulesUtil=OrigXRulesUtil.getInstance();
        Vector NCBAdjust=xRulesVerification.getVNCBAdjust();
        BigDecimal ncbInstllment =new BigDecimal(0);
            
            
        Vector  vXrulesNCB=xRulesVerification.getVXRulesNCBDataM();
        if(vXrulesNCB==null){
        vXrulesNCB=new Vector();
        }
        
        for(int i=0;i<vXrulesNCB.size();i++){
            HashMap ncbAdjsutDetail=new HashMap();
            XRulesNCBDataM  xrulesNCBDataM=(XRulesNCBDataM)vXrulesNCB.get(i);
            Vector vAccountDetail=origXRulesUtil.getNCBAccountDetail(ncbOutput.getTlRespMs(),xrulesNCBDataM.getLoanType());
            BigDecimal debt=origXRulesUtil.getTotalNCBBurdentAdjust(vAccountDetail, debdbParm, ncbOutput
                    .getHsRespMs(),NCBAdjust,ncbAdjsutDetail);
            ncbInstllment=ncbInstllment.add(debt);
            hNCBSummayAdjustDetail.put(xrulesNCBDataM.getLoanType(),debt);
        }
        log.debug("NCB Installment"+ ncbInstllment);
        /*
        
        if (OrigConstant.ORIG_FLAG_Y.equals(xRulesVerification.getDebtAmountODInterestFlag()) && xRulesVerification.getDEBT_BD_PARAM() != null) {
            totalInstallment = ErrorUtil.getShortErrorMessage(getRequest(), "ncb_mor");
        } else {
            BigDecimal ncbInstllment = OrigXRulesUtil.getTotalNCBBurdent(ncbOutput.getTlRespMs(), xRulesVerification.getDEBT_BD_PARAM(), ncbOutput
                    .getHsRespMs());
            
                        
        }*/
        //Group
        //HashMap xrulesNCBSummayInstallmentAdjsut=new 
        HashMap    hNCBNameGroups=new HashMap();
        HashMap    hNCBIdGroups=new HashMap();
        if(ncbOutput!=null){
        Vector vNCBName=ncbOutput.getPnRespMs();
        if(vNCBName!=null){
        for(int i=0;i<vNCBName.size();i++){
            PNRespM  pnNameResp=(PNRespM)vNCBName.get(i);
            if(hNCBNameGroups.containsKey( String.valueOf(pnNameResp.getGroupSeq()) )){
                Vector vNCBGroupName=(Vector)hNCBNameGroups.get(String.valueOf(pnNameResp.getGroupSeq()));
                vNCBGroupName.add(pnNameResp);
            }else{
                Vector vNCBGroupName=new Vector();
                vNCBGroupName.add(pnNameResp);
                hNCBNameGroups.put(String.valueOf(pnNameResp.getGroupSeq()),vNCBGroupName);            
            }
        }            
        }     
        Vector vNCBId=ncbOutput.getIdRespMs();
        if(vNCBId!=null){
            for(int i=0;i<vNCBId.size();i++){
                IDRespM idResp=(IDRespM)vNCBId.get(i);
                if(hNCBIdGroups.containsKey( String.valueOf(idResp.getGroupSeq()) )){
                    Vector vNCBGroupID=(Vector)hNCBIdGroups.get(String.valueOf(idResp.getGroupSeq()));
                    vNCBGroupID.add(idResp);
                }else{
                    Vector vNCBGroupID=new Vector();
                    vNCBGroupID.add(idResp);
                    hNCBIdGroups.put(String.valueOf(idResp.getGroupSeq()),vNCBGroupID);            
                }
            }
                
            }
        }
        //======================================================================
                
        try {
            totalInstallment=ORIGDisplayFormatUtil.displayCommaNumber(ncbInstllment);
        } catch (Exception e1) {                
            log.error("Error ",e1);
        }
        getRequest().getSession().setAttribute("NCBOutput", ncbOutput);
        getRequest().getSession().setAttribute("NCBSummaryInstallment", totalInstallment);
        getRequest().getSession().setAttribute("NCBSummaryInstallmentAdjust", hNCBSummayAdjustDetail);
        getRequest().getSession().setAttribute("NCBNameGroup",hNCBNameGroups);
        getRequest().getSession().setAttribute("NCBIdGroup",hNCBIdGroups);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {

        return 0;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
