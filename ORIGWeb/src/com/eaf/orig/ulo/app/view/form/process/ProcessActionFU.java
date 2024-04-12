package com.eaf.orig.ulo.app.view.form.process;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.im.rest.common.model.ServiceResponse;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.ulo.app.view.util.decisionservice.DecisionApplicationUtil;
import com.eaf.orig.ulo.app.view.util.pega.PegaApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.pega.ErrorDataM;
import com.eaf.orig.ulo.model.pega.FollowUpResponse;
import com.ibm.websphere.ce.cm.DuplicateKeyException;

public class ProcessActionFU extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(ProcessActionFU.class);
	String ERROR_CODE_PEGA_INVALID_STATE = SystemConstant.getConstant("ERROR_CODE_PEGA_INVALID_STATE");
	
	private String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	private String ERROR_SUBMIT_CLOSE_INTERVAL_1 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_1");
	private String ERROR_SUBMIT_CLOSE_INTERVAL_2 = MessageErrorUtil.getText("ERROR_SUBMIT_CLOSE_INTERVAL_2");
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");
	
	@Override
	public Object processAction() {
		logger.debug("FUSaveApplication.processAction..");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ProcessActionResponse processActionResponse = new ProcessActionResponse();
		try{			
			String buttonAction = getButtonAction();
			logger.debug("buttonAction : "+buttonAction);
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
						processActionResponse.setResultCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
						processActionResponse.setResultDesc(ERROR_SUBMIT_CLOSE_INTERVAL_1 + SUBMIT_IA_BLOCK_TIME + ERROR_SUBMIT_CLOSE_INTERVAL_2);
						return processActionResponse;
					} else {
						throw e;
					}
				}
			}
			
			boolean isDummyToPegaFlag = PegaApplicationUtil.isDummyToPegaFlag(applicationGroup);
			logger.debug("isDummyToPegaFlag : "+isDummyToPegaFlag);
			FollowUpResponse followUpResponse = PegaApplicationUtil.sendToPega(applicationGroup,userM,isDummyToPegaFlag);
			String followUpResponseResult = followUpResponse.getStatusCode();
			logger.debug("followUpResponseResult : "+followUpResponseResult);
			processActionResponse.setResultCode(followUpResponseResult);
			if(!Util.empty(followUpResponse.getError())){
				ErrorDataM error = followUpResponse.getError().get(0);
				processActionResponse.setResultDesc(error.getErrorDesc());
				if(ERROR_CODE_PEGA_INVALID_STATE.equals(error.getErrorCode())){
					processActionResponse.setResultCode(ServiceResponse.Status.SUCCESS);
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processActionResponse.setResultCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processActionResponse.setResultDesc(e.getLocalizedMessage());
		}
		return processActionResponse;
	}
}
