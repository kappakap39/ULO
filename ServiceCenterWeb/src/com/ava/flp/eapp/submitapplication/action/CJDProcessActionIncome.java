package com.ava.flp.eapp.submitapplication.action;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.submitapplication.model.ESubmitApplicationObject;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.ActiveMQSendQueueProxy;
import com.eaf.service.module.model.ActiveMQSendQueueRequestDataM;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.ibm.websphere.ce.cm.DuplicateKeyException;
import com.orig.bpm.proxy.BpmProxyConstant;
import com.orig.bpm.ulo.workflow.model.WorkflowResponseDataM;

public class CJDProcessActionIncome extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(CJDProcessActionIncome.class);
	int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	@Override
	public Object processAction() {
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			ESubmitApplicationObject submitEAppObject = (ESubmitApplicationObject)objectForm;
//			ApplicationGroup eApplicationGroup = (ApplicationGroup) submitEAppObject.getApplicationGroup();
//			ApplicationGroupDataM uloApplicationGroup = new EAppModelMapper().mapUloModel(null, eApplicationGroup);
			ApplicationGroupDataM uloApplicationGroup = (ApplicationGroupDataM) submitEAppObject.getApplicationGroup();
			String userId = submitEAppObject.getUserId();
			String callAction = submitEAppObject.getCallAction();
			
			String transactionId = uloApplicationGroup.getTransactionId();
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			logger.debug("process action for " + uloApplicationGroup.getApplicationGroupNo());
			
			trace.create("CJDProcessActionIncome");
			
			EAppAction.mapBranchData(uloApplicationGroup);
			EAppAction.mapSaleData(uloApplicationGroup);
			EAppAction.mapCashTransferAccountType(uloApplicationGroup);
			
			EAppAction.saveApplication(uloApplicationGroup, userId, "INITIAL");
			
			EAppAction.mapCisData(uloApplicationGroup, userId);
			
			//Handle multiple submit in close interval
			String appGroupNo = uloApplicationGroup.getApplicationGroupNo();
			String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
			String idNo = uloApplicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT).getIdno();
			logger.debug("appGroupNo = " + appGroupNo);
			logger.debug("idNo = " + idNo);
				
			deleteOldSubmitIATimestamp(idNo, SUBMIT_IA_BLOCK_TIME);
			try
			{	
				insertSubmitIATimestamp(appGroupNo, idNo);
			}
			catch(Exception e)
			{
				if(e instanceof DuplicateKeyException)
				{
					logger.debug("DuplicateKeyException = " + ServiceResponse.Status.BUSINESS_EXCEPTION);
					processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, "10","We detect another submit request with same IDNO in close interval, please wait " + SUBMIT_IA_BLOCK_TIME + " seconds before submit again."));
					return processResponse;
				}
				else
				{
					throw e;
				}
			}
			
			WorkflowResponseDataM workflowResponse = EAppAction.createWorkflowTask(uloApplicationGroup, callAction);
			if(BpmProxyConstant.RestAPIResult.SUCCESS.equals(workflowResponse.getResultCode())){
				processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			}else{
				processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				processResponse.setErrorData(EAppAction.error(ErrorData.ErrorType.SYSTEM_ERROR, workflowResponse.getErrorCode(), workflowResponse.getErrorMsg()));
			}
			trace.end("CJDProcessActionIncome");
			trace.trace();
		}
		catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(EAppAction.error(e));
		}
		return processResponse;
	}
	
	private void deleteOldSubmitIATimestamp(String idNo, int seconds) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM ORIG_SUBMIT_IA_TIMESTAMP ");
			sql.append(" WHERE IDNO = ? ");
			sql.append(" AND ((SYSDATE - SUBMIT_DATE) * 86400) > ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, idNo);
			ps.setInt(2, seconds);
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
	
	private void insertSubmitIATimestamp(String appGroupNo, String idNo) throws Exception
	{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			OrigObjectDAO origObjectDAO = new OrigObjectDAO();
			conn = origObjectDAO.getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_SUBMIT_IA_TIMESTAMP(APPLICATION_GROUP_NO, IDNO, SUBMIT_DATE) ");
			sql.append(" VALUES(?, ?, TRUNC(SYSDATE,'MI')) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, appGroupNo);
			ps.setString(cnt++, idNo);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw e;
		} finally {
			try {
				if(ps != null){ps.close();}
				if(conn != null){conn.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
