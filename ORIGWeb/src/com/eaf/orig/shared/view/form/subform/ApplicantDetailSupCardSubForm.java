/*
 * Created on Dec 15, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.PersonalInfoDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ApplicantDetailSupCardSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(ApplicantDetailSupCardSubForm.class);

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	public void setProperties(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ORIGUtility utility = new ORIGUtility();
        //appno for test
        String applicationNo = request.getParameter("application_no");
        String identificationNo = request.getParameter("identification_no");
        String officeCode = request.getParameter("office_code");
        String cmrCode = request.getParameter("cmr_code");
        String personalType = request.getParameter("appPersonalType");//(String)
                                                                      // request.getSession().getAttribute("PersonalType");
        String personalSeq = request.getParameter("personalSeq");
        String coBorrowerActiveFlag = request.getParameter("coborower_acive_flag");
        String debtIncludeFlag = request.getParameter("debtIncludeFlag");
        int seq = 0;
        if (!ORIGUtility.isEmptyString(personalSeq)) {
            seq = Integer.parseInt(personalSeq);
        }
        logger.debug("Coborrower Flag" + coBorrowerActiveFlag);
        logger.debug("debtIncludeFlag" + debtIncludeFlag);
        //      Get Personal Info
        ApplicationDataM applicationDataM = formHandler.getAppForm();
        PersonalInfoDataM personalInfoDataM = null;
        logger.debug("personalType=" + personalType);
        if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("MAIN_POPUP_DATA");
        } else if (OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personalType)) {
            personalInfoDataM = utility.getPersonalInfoByType(applicationDataM, OrigConstant.PERSONAL_TYPE_APPLICANT);
        }else if (OrigConstant.PERSONAL_TYPE_SUP_CARD.equals(personalType)) {
            personalInfoDataM = (PersonalInfoDataM) request.getSession(true).getAttribute("SUPCARD_POPUP_DATA");
        }
        if (personalInfoDataM != null) {
            personalInfoDataM.setIdNo(identificationNo);
            if ((!(OrigConstant.ROLE_UW.equals(userM.getRoles().elementAt(0)) || (OrigConstant.ROLE_DE.equals(userM.getRoles().elementAt(0)) && !ORIGUtility
                    .isEmptyString(applicationDataM.getCmrFirstId()))))
                    || ORIGUtility.isEmptyString(applicationDataM.getOfficeCode())) {
                applicationDataM.setOfficeCode(officeCode);
            }
            applicationDataM.setCmrCode(cmrCode);
            if (debtIncludeFlag == null) {
                debtIncludeFlag = "";
            }           
            if (coBorrowerActiveFlag == null) {
                coBorrowerActiveFlag = "";
            }
            //set Flag Coborower flag
            if (OrigConstant.PERSONAL_TYPE_GUARANTOR.equals(personalType)) {

                if (!coBorrowerActiveFlag.equals(personalInfoDataM.getCoborrowerFlag()) || !debtIncludeFlag.equals(personalInfoDataM.getDebtIncludeFlag())) {
                    logger.debug("CoBorroer /debtInclude   Flag Chage");
                    //PersonalInfoDataM mainAppPersonalInfoDataM =
                    // utility.getPersonalInfoByType(applicationDataM,OrigConstant.PERSONAL_TYPE_APPLICANT);
                    //XRulesVerificationResultDataM
                    // mainXrulesVerificationDataM=mainAppPersonalInfoDataM.getXrulesVerification();
                    //if(mainXrulesVerificationDataM !=null){
                    //    mainXrulesVerificationDataM.setDebtAmountResult(null);
                    //    mainXrulesVerificationDataM.setDebtAmountAdjustResult(null);
                    //}
                    applicationDataM.setIsReExcuteDebtAmtFlag(true);
                    applicationDataM.setIsReExcuteAppScoreFlag(true);
                    applicationDataM.setScorringResult(null);
                }
            }
            if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(coBorrowerActiveFlag)) {
                personalInfoDataM.setCoborrowerFlag(coBorrowerActiveFlag);
                personalInfoDataM.setDebtIncludeFlag(OrigConstant.ORIG_FLAG_Y);
            } else if (OrigConstant.COBORROWER_FLAG_ACTIVE.equals(personalInfoDataM.getCoborrowerFlag())) {
                personalInfoDataM.setCoborrowerFlag(OrigConstant.COBORROWER_FLAG_UN_ACTIVE);
                personalInfoDataM.setDebtIncludeFlag(OrigConstant.ORIG_FLAG_N);
            } else {
                personalInfoDataM.setCoborrowerFlag(OrigConstant.COBORROWER_FLAG_NO);
                personalInfoDataM.setDebtIncludeFlag(debtIncludeFlag);
            }
            //
            // applicationDataM.setApplicationNo(applicationNo);
            if (ORIGUtility.isEmptyString(applicationDataM.getCreateBy())) {
                applicationDataM.setCreateBy(userM.getUserName());
            } else {
                applicationDataM.setUpdateBy(userM.getUserName());
            }
        } else {
            logger.error("Personal info is null");
        }
        request.getSession().setAttribute("MAIN_POPUP_DATA", personalInfoDataM);
        request.getSession().setAttribute("SUPCARD_POPUP_DATA", personalInfoDataM);
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		// TODO Auto-generated method stub
		return null;
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateSubForm(javax.servlet.http.HttpServletRequest)
	 */
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
}
