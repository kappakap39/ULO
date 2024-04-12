package com.eaf.orig.ulo.app.validation;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;

public class KPLAccountValidation extends AccountValidationHelper {

	private static transient Logger logger = Logger.getLogger(KPLAccountValidation.class);
	
	public KPLAccountValidation(){
		super();
	}

	@Override
	public String validateField(HttpServletRequest request,	Object objectForm) {
		String ERR_CODE_ACCT_APPLICANT_ONLY_KPL = SystemConstant.getConstant("ERR_CODE_ACCT_APPLICANT_ONLY_KPL");
		String ERR_CODE_ACCT_SINGLE_ACCT_KPL = SystemConstant.getConstant("ERR_CODE_ACCT_SINGLE_ACCT_KPL");
		String ERR_CODE_ACCT_SAVING_OR_CURRENT_KPL = SystemConstant.getConstant("ERR_CODE_ACCT_SAVING_OR_CURRENT_KPL");
		String DIH_SINGLE_OWNER_CODE = SystemConstant.getConstant("DIH_SINGLE_OWNER_CODE");
		DIHProxy dihService = new DIHProxy();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM) objectForm;
		String CIS_INFO = super.validateField(request, objectForm);
		if(Util.empty(CIS_INFO)) {
			setErrorCode(ERR_CODE_ACCT_APPLICANT_ONLY_KPL);
		} else {
			String CIS_NO = CIS_INFO;
			if(CIS_NO != null && CIS_NO.equals(personalInfo.getCisNo())) {
				logger.info("Acct valid");
			} else {
				setErrorCode(ERR_CODE_ACCT_APPLICANT_ONLY_KPL);
			}
					
			DIHQueryResult<String> dihCIS_SRC_STM_CD = dihService.getCisInfoByAccountNo(getAccountNo(), "CIS_SRC_STM_CD");
			if(ResponseData.SUCCESS.equals(dihCIS_SRC_STM_CD.getStatusCode())){
				String CIS_SRC_STM_CD = dihCIS_SRC_STM_CD.getResult();
				if(CIS_SRC_STM_CD != null &&
						(
							/*CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.CURRENT.getCode()) || */
							CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.SAVING.getCode())
						)
				  ) 
				{
					logger.info("Acct valid");
				} else {
					setErrorCode(ERR_CODE_ACCT_SAVING_OR_CURRENT_KPL);
				}
									
				DIHQueryResult<String> dihAR_OWN_TP_CD = dihService.getCisInfoByAccountNo(getAccountNo(), "AR_OWN_TP_CD");
				if(ResponseData.SUCCESS.equals(dihAR_OWN_TP_CD.getStatusCode())){
					String AR_OWN_TP_CD = dihAR_OWN_TP_CD.getResult();
					if(AR_OWN_TP_CD != null && AR_OWN_TP_CD.equals(DIH_SINGLE_OWNER_CODE)) {
						logger.info("Single Acct owner");
					} else {
						setErrorCode(ERR_CODE_ACCT_SINGLE_ACCT_KPL);
					}
				}else{
					setErrorCode(ErrorController.errorMessage(dihAR_OWN_TP_CD.getErrorData().getErrorInformation(), ErrorController.ERROR_MESSAGE_NAME.ERROR_REFER_TO_WORK_SHEET_DIH));
				}
			}else{
				setErrorCode(ErrorController.errorMessage(dihCIS_SRC_STM_CD.getErrorData().getErrorInformation(), ErrorController.ERROR_MESSAGE_NAME.ERROR_REFER_TO_WORK_SHEET_DIH));
			}
		}
		return CIS_INFO;
	}

}
