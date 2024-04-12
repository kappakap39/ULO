package com.eaf.orig.ulo.app.validation;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;

public class ATMSavingAccountValidation extends AccountValidationHelper {
	private static transient Logger logger = Logger.getLogger(ATMSavingAccountValidation.class);		
	public ATMSavingAccountValidation(){
		super();
	}
	@Override
	public String validateField(HttpServletRequest request,
			Object objectForm) {
		String ERR_CODE_ACCT_APPLICANT_ONLY_ATM_SAVING = SystemConstant.getConstant("ERR_CODE_ACCT_APPLICANT_ONLY_ATM_SAVING");
		String ERR_CODE_ACCT_SINGLE_ACCT_ATM_SAVING = SystemConstant.getConstant("ERR_CODE_ACCT_SINGLE_ACCT_ATM_SAVING");
		String ERR_CODE_ACCT_SAME_BRANCH = SystemConstant.getConstant("ERR_CODE_ACCT_SAME_BRANCH");
		String DIH_SINGLE_OWNER_CODE = SystemConstant.getConstant("DIH_SINGLE_OWNER_CODE");
		DIHProxy dihService = new DIHProxy();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM) objectForm;
		String cisNo = super.validateField(request, objectForm);
		if(Util.empty(cisNo)) {
			if(Util.empty(getErrorCode())){
				setErrorCode(ERR_CODE_ACCT_APPLICANT_ONLY_ATM_SAVING);
			}
		}else{
			DIHQueryResult<String> querySrcResult = dihService.getCisInfoByAccountNo(getAccountNo(),"CIS_SRC_STM_CD"); 
			String CIS_SRC_STM_CD = querySrcResult.getResult();		
			if(cisNo != null && cisNo.equals(personalInfo.getCisNo()) &&
					CIS_SRC_STM_CD != null && CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.SAVING.getCode())) {
				logger.info("Acct valid");
			} else {
				setErrorCode(ERR_CODE_ACCT_APPLICANT_ONLY_ATM_SAVING);
			}			
			DIHQueryResult<String> queryOwnResult = dihService.getCisInfoByAccountNo(getAccountNo(),"AR_OWN_TP_CD"); 
			String AR_OWN_TP_CD = queryOwnResult.getResult();			
			if(AR_OWN_TP_CD != null && AR_OWN_TP_CD.equals(DIH_SINGLE_OWNER_CODE)) {
				logger.info("Single Acct owner");
			} else {
				setErrorCode(ERR_CODE_ACCT_SINGLE_ACCT_ATM_SAVING);
			}			
			if(!Util.empty(getAdditionalAccountNo())) {
				querySrcResult = dihService.getCisInfoByAccountNo(getAdditionalAccountNo(),"CIS_SRC_STM_CD"); 
				String CIS_SRC_STM_CD2 = querySrcResult.getResult();				
				if(cisNo != null && cisNo.equals(personalInfo.getCisNo()) &&
						CIS_SRC_STM_CD2 != null && CIS_SRC_STM_CD2.equals(AccountInformationDataM.AcctTableName.CURRENT.getCode())) {
					logger.info("Acct valid");
				} else {
					setErrorCode(ERR_CODE_ACCT_APPLICANT_ONLY_ATM_SAVING);
				}				
				DIHQueryResult<String> queryAccountNoBrNoResult = dihService.getAccountInfo(getAccountNo(),"DOMC_BR_NO");
				String crnBranchCode = queryAccountNoBrNoResult.getResult();
				DIHQueryResult<String> queryAdditaionalAccountNoBrNoResult =  dihService.getAccountInfo(getAdditionalAccountNo(),"DOMC_BR_NO");
				String svgBranchCode = queryAdditaionalAccountNoBrNoResult.getResult();
				if(svgBranchCode == null || crnBranchCode == null || !crnBranchCode.equals(svgBranchCode)) {
					setErrorCode(ERR_CODE_ACCT_SAME_BRANCH);
				}
			}
		}
		return cisNo;
	}
}
