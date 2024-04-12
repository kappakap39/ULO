package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.engine.NotifyDataM;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.NotifyForm;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.google.gson.Gson;

public class DecisionApplicationAction implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(DecisionApplicationAction.class);
	public static final String IIB_IMPLEMENT_TYPE = "DECISION_SERVICE";
	String DECISION_SERVICE_ACTION = SystemConstant.getConstant("DECISION_SERVICE_ACTION");	
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DECISION_APPLICATION_ACTION);
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		String functionId = request.getParameter("functionId");
		String buttonAction = request.getParameter("buttonAction");
		String ImplementActionId = getDecisionImplementActionId(functionId,request);
		if("RECALCULATE_DEBT_BURDEN_BTN".equals(buttonAction)){
			ImplementActionId = ImplementActionId+"_RECAL";
		}else if("INCOME".equals(buttonAction)){
			ImplementActionId = ImplementActionId+"_INCOME";
		}
		logger.debug("functionId >> "+functionId);
		logger.debug("ImplementActionId >> "+ImplementActionId);
		logger.debug("buttonAction >> "+buttonAction);
		DecisionApplication decisionApplication = new DecisionApplication();
		decisionApplication.setFunctionId(functionId);
		decisionApplication.setDecisionImplementActionId(ImplementActionId);
		decisionApplication.setButtonAction(buttonAction);
		if(!Util.empty(ImplementActionId)){
			try{
				ProcessActionInf processAction = ImplementControl.getProcessAction(IIB_IMPLEMENT_TYPE,ImplementActionId);
				if(null != processAction){
					processAction.init(request);
					Object objectProcess = processAction.processAction();
					if(objectProcess instanceof DecisionApplication){
						DecisionApplication objectApplication = (DecisionApplication)objectProcess;
						mapDecisionApplication(decisionApplication, objectApplication);
						if(Util.empty(decisionApplication.getResultCode())){
							decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.NO_ACTION);
						}
					}
				}else{
					decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.NO_ACTION);
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
				decisionApplication.setResultDesc(e.getLocalizedMessage());
				decisionApplication.setErrorType(ErrorData.ErrorType.SYSTEM_ERROR);
			}
		}else{
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.NO_ACTION);
		}
		logger.debug("decisionApplication >> "+decisionApplication);
		ArrayList<String> successIIB = new ArrayList<String>();
		successIIB.add(DecisionApplicationUtil.ResultCode.NO_ACTION);
		successIIB.add(DecisionApplicationUtil.ResultCode.SUCCESS);
		String decisionResult = decisionApplication.getResultCode();
		logger.debug("decisionResult >> "+decisionResult);
		if(!successIIB.contains(decisionResult)){
			logger.error("errorMsg >> "+decisionApplication.getResultDesc());
			if(Util.empty(decisionApplication.getResultDesc()) || !DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION.equals(decisionResult)){
				NotifyDataM notify = NotifyForm.errorMsg(decisionApplication.getResultDesc());
				String errorMsg = notify.getNotifyMsg();
				logger.error("errorMsg >> "+errorMsg);
				decisionApplication.setResultDesc(errorMsg);
			}
			
			return responseData.error(decisionApplication);
		}
		return responseData.success(new Gson().toJson(decisionApplication));
	}
	
	
	public void mapDecisionApplication(DecisionApplication decisionApplication,DecisionApplication objectDecisionApplication){
		decisionApplication.setDecisionId(objectDecisionApplication.getDecisionId());
		decisionApplication.setDecision(objectDecisionApplication.getDecision());
		decisionApplication.setIncomeScreenFlag(objectDecisionApplication.getIncomeScreenFlag());
		decisionApplication.setDocCompleteFlag(objectDecisionApplication.getDocCompleteFlag());
		decisionApplication.setResultCode(objectDecisionApplication.getResultCode());
		decisionApplication.setResultDesc(objectDecisionApplication.getResultDesc());
		decisionApplication.setDiffRequestFlag(objectDecisionApplication.getDiffRequestFlag());
		decisionApplication.setCallEscalateFlag(objectDecisionApplication.getCallEscalateFlag());
		decisionApplication.setResultCode(objectDecisionApplication.getResultCode());
		decisionApplication.setResultDesc(objectDecisionApplication.getResultDesc());
		decisionApplication.setErrorType(objectDecisionApplication.getErrorType());
		decisionApplication.setFraudFlag(objectDecisionApplication.getFraudFlag());
		decisionApplication.setBlockedFlag(objectDecisionApplication.getBlockedFlag());
		decisionApplication.setRejectFlag(objectDecisionApplication.getRejectFlag());
		decisionApplication.setLowIncomeFlag(objectDecisionApplication.getLowIncomeFlag());
		decisionApplication.setDebtAmount(objectDecisionApplication.getDebtAmount());
		decisionApplication.setDebtBurden(objectDecisionApplication.getDebtBurden());
		decisionApplication.setDebtBurdenCreditLimit(objectDecisionApplication.getDebtBurdenCreditLimit());
		decisionApplication.setDebtRecommend(objectDecisionApplication.getDebtRecommend());
		logger.debug("implementId>>"+objectDecisionApplication.getDebtAmount());
		logger.debug("implementId>>"+objectDecisionApplication.getDebtBurden());
		//KPL Additional
		decisionApplication.setSavingPlusFlag(objectDecisionApplication.getSavingPlusFlag());
	}
	public String getDecisionImplementActionId(String functionId,HttpServletRequest request){
		String roleId = FormControl.getFormRoleId(request);
		String implementId = functionId;
		if(DECISION_SERVICE_ACTION.equals(functionId)){
			implementId = SystemConstant.getConstant("DECISION_IMPLEMENT_ACTION_"+roleId);
			logger.debug("implementId>>"+implementId);
		}
		return implementId;
	}
}
