package com.eaf.orig.ulo.app.view.util.decisionservice;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
public class DecisionPrivilegeProject extends ProcessActionHelper  {
	private static transient Logger logger = Logger.getLogger(DecisionPrivilegeProject.class);
	String DECISION_SERVICE_POINT_DOC_COMPLETE =SystemConstant.getConstant("DECISION_SERVICE_POINT_DOC_COMPLETE");
	String DECISION_SERVICE_POINT_INCOME =SystemConstant.getConstant("DECISION_SERVICE_POINT_INCOME");
	String KASSET_TYPE_MORE_6_MONTH = SystemConstant.getConstant("KASSET_TYPE_MORE_6_MONTH");

	public Object processAction() {
		int PROJECT_CODE_INDEX = 0;
		PrivilegeProjectCodeKassetDocDataM kAssetDoc = null;
//		ProcessResponse processResponse = new ProcessResponse();
		logger.debug("decision PrivilegeProject.processAction()..");		
		DecisionApplication decisionApplication = new DecisionApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(!Util.empty(verificationResult)){
			PrivilegeProjectCodeDataM privilegeProjectCode = verificationResult.getPrivilegeProjectCode(PROJECT_CODE_INDEX);
			if(!Util.empty(privilegeProjectCode)){
				PrivilegeProjectCodeDocDataM privilegeprojectcodedocDataM = privilegeProjectCode.getPrivilegeProjectCodeDoc(PROJECT_CODE_INDEX);
				if(!Util.empty(privilegeprojectcodedocDataM)){
					kAssetDoc = privilegeprojectcodedocDataM.getPrivilegeProjectCodeKassetDoc(PROJECT_CODE_INDEX);
				}
			}
		}
		try{
			//	comment by condition of defect 1530	: [FLP] UAT 17-02-2017 	 
			//if(!Util.empty(kAssetDoc) && KASSET_TYPE_MORE_6_MONTH.equals(kAssetDoc.getKassetType())){
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				DecisionServiceRequestDataM requestDecision = new DecisionServiceRequestDataM();
				requestDecision.setApplicationGroup(applicationGroup);
				requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_INCOME);
				requestDecision.setRoleId(FormControl.getFormRoleId(request));
				requestDecision.setUserId(userM.getUserName());	
				String transactionId = (String)request.getSession().getAttribute("transactionId");
				requestDecision.setTransactionId(transactionId);
				DecisionServiceResponse responseData= DecisionApplicationUtil.requestDecisionService(requestDecision);	
				String responseResult = responseData.getResultCode();
				logger.debug("responseResult Income >> "+responseResult);
				decisionApplication.setResultCode(responseResult);
				decisionApplication.setResultDesc(responseData.getResultDesc());
				decisionApplication.setErrorType(responseData.getErrorType());
				decisionApplication.setDecisionId(DECISION_SERVICE_POINT_INCOME);
				if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){ 
					 requestDecision = new DecisionServiceRequestDataM();
					 requestDecision.setApplicationGroup(applicationGroup);
					 requestDecision.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DOC_COMPLETE);
					 requestDecision.setRoleId(FormControl.getFormRoleId(request));
					 requestDecision.setUserId(userM.getUserName());
					 responseData= DecisionApplicationUtil.requestDecisionService(requestDecision);	
					 responseResult = responseData.getResultCode();
					 logger.debug("responseResult Doc >> "+responseResult);
					 decisionApplication.setResultCode(responseResult);
					 decisionApplication.setResultDesc(responseData.getResultDesc());
					 decisionApplication.setErrorType(responseData.getErrorType());
					 decisionApplication.setDecisionId(DECISION_SERVICE_POINT_DOC_COMPLETE);
					 if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
						String REQUIRE_INCOME_FLAG = verificationResult.getRequiredVerIncomeFlag();
						logger.debug("REQUIRE_INCOME_FLAG >> "+REQUIRE_INCOME_FLAG);
						if("N".equals(REQUIRE_INCOME_FLAG)){
							applicationGroup.setDecisionLog(ApplicationGroupDataM.DecisionLog.NOT_REQUIRE_VERIFY_INCOME);
						}
					 }
				}
//			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}
}
