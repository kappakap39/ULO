
package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;
import com.ibm.websphere.ce.cm.DuplicateKeyException;

public class DecisionPB1 extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionPB1.class);
	String DECISION_SERVICE_POINT_PB1 =SystemConstant.getConstant("DECISION_SERVICE_POINT_PB1");
	String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	
	private String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	private String ERROR_SUBMIT_CLOSE_INTERVAL_1 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_1");
	private String ERROR_SUBMIT_CLOSE_INTERVAL_2 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_2");
	
	public Object processAction() {
		logger.debug("Decision PB1.processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");

		DecisionServiceRequestDataM reqApplication = new DecisionServiceRequestDataM();
		reqApplication.setApplicationGroup(applicationGroup);
		reqApplication.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_PB1);
		reqApplication.setRoleId(FormControl.getFormRoleId(request));
		reqApplication.setUserId(userM.getUserName());
		String buttonAction = request.getParameter("buttonAction");
		try {
			logger.debug("buttonAction :: " + buttonAction);
			if(BUTTON_ACTION_SUBMIT.equals(buttonAction)) {
				String appGroupNo = applicationGroup.getApplicationGroupNo();
				String idNo = "";
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				
				logger.debug("appGroupNo = " + appGroupNo);
				logger.debug("idNo = " + idNo);
				
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteOldSubmitIATimestamp(appGroupNo, idNo, SUBMIT_IA_BLOCK_TIME);
				try {	
					utilDao.insertSubmitIATimestamp(appGroupNo, idNo);
				} catch(Exception e) {
					if(e instanceof DuplicateKeyException) {
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
						decisionApplication.setResultDesc(ERROR_SUBMIT_CLOSE_INTERVAL_1 + SUBMIT_IA_BLOCK_TIME + ERROR_SUBMIT_CLOSE_INTERVAL_2);
						return decisionApplication;
					} else {
						throw e;
					}
				}
			}
			
			String transactionId = (String)request.getSession().getAttribute("transactionId");
			reqApplication.setTransactionId(transactionId);
			DecisionServiceResponse responseData = DecisionApplicationUtil.requestDecisionService(reqApplication);	
			String responseResult = responseData.getResultCode();
			logger.debug("responseResult >> "+responseResult);
			decisionApplication.setResultCode(responseResult);	
			decisionApplication.setResultDesc(responseData.getResultDesc());
			decisionApplication.setErrorType(responseData.getErrorType());
			if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){
				decisionApplication.setDecisionId(DECISION_SERVICE_POINT_PB1);
				logger.debug("DECISION_SERVICE_POINT_PB1 >> "+DECISION_SERVICE_POINT_PB1);
				logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());	
			} else {
				/* change to use method from DecisionApplicationUtil						
					String idNo = "";
				if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
				} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
					idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
				}
				UtilityDAO utilDao = new UtilityDAOImpl();
				utilDao.deleteErrorSubmitIATimestamp(idNo);
				*/
				DecisionApplicationUtil.deleteLockSubmitIATimestamp(applicationGroup);
			}
			if(DecisionApplicationUtil.isApplyTypeError(applicationGroup.getApplyTypeStatus())){
				decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
				decisionApplication.setResultDesc(MessageErrorUtil.getText("ERROR_APPLY_TYPE"));
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);	
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}

}
