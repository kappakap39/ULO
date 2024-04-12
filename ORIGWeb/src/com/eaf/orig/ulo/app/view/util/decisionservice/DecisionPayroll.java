package com.eaf.orig.ulo.app.view.util.decisionservice;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayrollDataM;
import com.eaf.orig.ulo.model.decison.DecisionApplication;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.common.model.decisions.DecisionServiceRequestDataM;

public class DecisionPayroll extends ProcessActionHelper {
	private static transient Logger logger = Logger.getLogger(DecisionPayroll.class);
	String DECISION_IMPLEMENT_ACTION_PAYROLL =SystemConstant.getConstant("DECISION_IMPLEMENT_ACTION_PAYROLL");
	String DECISION_SERVICE_POINT_DOC_COMPLETE =SystemConstant.getConstant("DECISION_SERVICE_POINT_DOC_COMPLETE");
	String PAYROLL_NO_OF_EMPLOYEE_CONDITION = SystemConstant.getConstant("PAYROLL_NO_OF_EMPLOYEE_CONDITION");
	public Object processAction() {
		logger.debug(" Decision DOCUMENT .processAction()..");
		DecisionApplication decisionApplication = new DecisionApplication();
		try{
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			Object object = FormControl.getMasterObjectForm(request);
			if(object instanceof ApplicationGroupDataM){
				 applicationGroup = (ApplicationGroupDataM)FormControl.getMasterObjectForm(request);
			}
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			ModuleFormHandler moduleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = (IncomeInfoDataM)moduleForm.getObjectForm();
			ArrayList<IncomeCategoryDataM> payrollList = incomeM.getAllIncomes();
			PayrollDataM payrollM = new PayrollDataM();
			if(payrollList.size() > 0){
				payrollM = (PayrollDataM)payrollList.get(0);
				logger.debug("payrollM.getNoOfEmployee()>>"+payrollM.getNoOfEmployee());
				if(payrollM.getNoOfEmployee()<FormatUtil.getInt(PAYROLL_NO_OF_EMPLOYEE_CONDITION)){
					DecisionServiceRequestDataM     decisionRequest = new DecisionServiceRequestDataM();
					decisionRequest.setApplicationGroup(applicationGroup);
					decisionRequest.setDecisionPoint(DecisionServiceUtil.DECISION_POINT.DECISION_POINT_DOC_COMPLETE);
					decisionRequest.setUserId(userM.getUserNo());
					decisionRequest.setRoleId(FormControl.getFormRoleId(request));
					String transactionId = (String)request.getSession().getAttribute("transactionId");
					decisionRequest.setTransactionId(transactionId);
					DecisionServiceResponse decisionResponse = DecisionApplicationUtil.requestDecisionService(decisionRequest);
							
					String responseResult = decisionResponse.getResultCode();
					logger.debug("responseResult >> "+responseResult);
					decisionApplication.setResultCode(responseResult);	
					decisionApplication.setResultDesc(decisionResponse.getResultDesc());
					decisionApplication.setErrorType(decisionResponse.getErrorType());
					if(DecisionApplicationUtil.ResultCode.SUCCESS.equals(responseResult)){			
						decisionApplication.setDecisionId(DECISION_SERVICE_POINT_DOC_COMPLETE);
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SUCCESS);
						logger.debug("DECISION_SERVICE_POINT_DOC_COMPLETE >> "+DECISION_SERVICE_POINT_DOC_COMPLETE);
						logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());		
					}else{
						decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.BUSINESS_EXCEPTION);
					}
				}
				
			}  	
		}catch(Exception e){
			logger.fatal("ERROR",e);
			decisionApplication.setResultCode(DecisionApplicationUtil.ResultCode.SYSTEM_EXCEPTION);
			decisionApplication.setResultDesc(e.getLocalizedMessage());
		}
		return decisionApplication;
	}
}
