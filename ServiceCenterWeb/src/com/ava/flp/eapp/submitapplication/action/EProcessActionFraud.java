package com.ava.flp.eapp.submitapplication.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ProcessActionResponse;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.iib.mapper.util.DecisionServiceUtil;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

public class EProcessActionFraud extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(EProcessActionFraud.class);
	private String DECISION_ACTION_FULL_FRAUD = SystemConstant.getConstant("DECISION_ACTION_FULL_FRAUD");
	private String FINAL_APP_DECISION_REJECT = SystemConstant.getConstant("FINAL_APP_DECISION_REJECT");
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	private String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	private String PRODUCT_K_PERSONAL_LOAN =SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	
	private ArrayList<String> WIP_JOBSTATE_DE2 = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_DE2");
	private ArrayList<String> JOB_STATE_DE2_APPROVE_SUBMIT = SystemConfig.getArrayListGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
	private ArrayList<String> JOB_STATE_EAPP_SYSTEM = SystemConfig.getArrayListGeneralParam("JOB_STATE_EAPP_SYSTEM");
	private ArrayList<String> JOB_STATE_FULL_FRAUD = SystemConfig.getArrayListGeneralParam("JOB_STATE_FULL_FRAUD");
	private ArrayList<String> WIP_JOBSTATE_CA = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_CA");
	private ArrayList<String> WIP_JOBSTATE_VT = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_VT");
	private ArrayList<String> JOB_STATE_DV_SAVING_PLUS = SystemConfig.getArrayListGeneralParam("JOB_STATE_DV_SAVING_PLUS");
	private ArrayList<String> WIP_JOBSTATE_FRAUD = SystemConfig.getArrayListGeneralParam("WIP_JOBSTATE_FRAUD");
	private ArrayList<String> JOB_STATE_KEC_LOWINCOME = SystemConfig.getArrayListGeneralParam("JOB_STATE_KEC_LOWINCOME");
	
	private String ERROR_SUBMIT_APP_WAIT_FOR_APPROVE = MessageErrorUtil.getText("ERROR_SUBMIT_APP_WAIT_FOR_APPROVE");
	
	@Override
	public Object processAction() {
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		
		try {
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
			String userId = applicationGroup.getUserId();
			
			String appGroupNo = applicationGroup.getApplicationGroupNo();
			String idNo = "";
			ArrayList<String> products = applicationGroup.getProducts();
			if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT) != null) {
				idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
			} else if(applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY) != null) {
				idNo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY).getIdno();
			}
			logger.debug("appGroupNo = " + appGroupNo);
			logger.debug("idNo = " + idNo);
			logger.debug("products = " + products.toString());
			ArrayList<String> jobStates = new ArrayList<>();
			jobStates.addAll(WIP_JOBSTATE_DE2);
			jobStates.addAll(JOB_STATE_DE2_APPROVE_SUBMIT);
			jobStates.addAll(JOB_STATE_EAPP_SYSTEM);
			jobStates.addAll(JOB_STATE_FULL_FRAUD);
			jobStates.addAll(WIP_JOBSTATE_CA);
			jobStates.addAll(WIP_JOBSTATE_VT);
			jobStates.addAll(JOB_STATE_DV_SAVING_PLUS);
			jobStates.addAll(WIP_JOBSTATE_FRAUD);
			jobStates.addAll(JOB_STATE_KEC_LOWINCOME);
			if(isAppWaitForApprove(idNo, appGroupNo, jobStates, products)) {
				processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, "10", ERROR_SUBMIT_APP_WAIT_FOR_APPROVE));
				deleteErrorSubmitIATimestamp(idNo);
				return processResponse;
			}
			
			String transactionId = applicationGroup.getTransactionId();
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			logger.debug("process fraud for " + applicationGroup.getApplicationGroupNo());
			
			trace.create("EProcessActionFraud");
			trace.create("callDecision");
			applicationGroup.setUserId(applicationGroup.getSourceUserId());
			ProcessActionResponse decisionResponse = EAppAction.requestDecision(applicationGroup, DecisionServiceUtil.DECISION_POINT.DECISION_POINT_EDV2, userId);
			String decisionResultCode = decisionResponse.getResultCode();						
			logger.debug("decisionResultCode : "+decisionResultCode);	
			processResponse.setStatusCode(decisionResponse.getResultCode());
			trace.end("callDecision");			
			if(ServiceResponse.Status.SUCCESS.equals(decisionResultCode)){

				EAppAction.saveApplication(applicationGroup, userId, "FRAUD");
				
				logger.debug("DecisionAction >> "+applicationGroup.getDecisionAction());
				logger.debug("getLastDecision >> "+applicationGroup.getLastDecision());
				if(!FINAL_APP_DECISION_REJECT.equals(applicationGroup.getLastDecision())){
					applicationGroup.setDecisionAction(DECISION_ACTION_FULL_FRAUD);
				}
				
				WorkflowResponseDataM workflowResponse = EAppAction.workflowAction(applicationGroup);
				if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
					processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
				}else{
					processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
					processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, workflowResponse.getResultCode(), workflowResponse.getErrorMsg()));
				}
			}else{
				processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, decisionResponse.getResultCode(), decisionResponse.getErrorMsg()));
			}
			trace.end("EProcessActionFraud");
			trace.trace();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(EAppAction.error(e));
		}
		return processResponse;
	}
	
	private boolean isAppWaitForApprove(String idNo, String appGroupNo, ArrayList<String> jobStates, ArrayList<String> products) throws Exception {
		boolean result = false;
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT AG.APPLICATION_GROUP_ID ");
			sql.append(" FROM ORIG_PERSONAL_INFO PI ");
			sql.append(" INNER JOIN ORIG_PERSONAL_RELATION PR ON PR.PERSONAL_ID = PI.PERSONAL_ID ");
			sql.append(" INNER JOIN ORIG_APPLICATION A ON PR.REF_ID = A.APPLICATION_RECORD_ID ");
			if(null != products && products.contains("CC") && !products.contains("KEC") && !products.contains("KPL")) {
				sql.append(" AND SUBSTR(A.BUSINESS_CLASS_ID, 1, 2) = 'CC' ");
			} else if(null != products && !products.contains("CC") && (products.contains("KEC") || products.contains("KPL"))) {
				sql.append(" AND SUBSTR(A.BUSINESS_CLASS_ID, 1, 3) IN ('KEC', 'KPL') ");
			}
			sql.append(" INNER JOIN ORIG_APPLICATION_GROUP AG ON AG.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID ");
			sql.append("     AND AG.JOB_STATE IN ( ");
			int jobStateSize = jobStates.size();
			for(int i = 0; i < jobStateSize; i++) {
				sql.append(" ?, ");
			}
			if(jobStateSize > 0) {
				sql.replace(sql.length() - 2, sql.length(), " ");
			}
			sql.append("     ) ");
			sql.append("     AND AG.APPLICATION_GROUP_NO <> ? ");
			sql.append(" WHERE PI.IDNO = ? AND PI.PERSONAL_TYPE IN (?, ?) ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			for(String jobState : jobStates) {
				ps.setString(index++, jobState);
			}
			ps.setString(index++, appGroupNo);
			ps.setString(index++, idNo);
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(index++, OrigConstant.PERSONAL_TYPE_SUP_CARD);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				result = true;
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		return result;
	}
	
	public void deleteErrorSubmitIATimestamp(String idNo) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			sql.append(" WHERE IDNO = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, idNo);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	
}
