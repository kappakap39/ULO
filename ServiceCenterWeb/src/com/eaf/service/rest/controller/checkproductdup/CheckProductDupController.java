package com.eaf.service.rest.controller.checkproductdup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.CheckProductDupDataM;
import com.eaf.service.module.model.CheckProductDupRequestDataM;
import com.eaf.service.module.model.CheckProductDupResponseDataM;
import com.eaf.service.module.model.DuplicateProduct;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@RestController
@RequestMapping("/service/CheckProductDup")
public class CheckProductDupController {
	private static transient Logger logger = Logger.getLogger(CheckProductDupController.class);	
	
	@RequestMapping(value="/CheckProductDup", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> checkProductDup(@RequestBody CheckProductDupRequestDataM checkProductDupRequest
			, @RequestHeader HttpHeaders requestHeaders){
		
		if(null == checkProductDupRequest){
			checkProductDupRequest = new CheckProductDupRequestDataM();
		}
		checkProductDupRequest.setFuncNm(requestHeaders.getFirst("KBank-FuncNm"));
		checkProductDupRequest.setRqUID(requestHeaders.getFirst("KBank-RqUID"));
		checkProductDupRequest.setRqDt(requestHeaders.getFirst("KBank-RqDt"));
		checkProductDupRequest.setRqAppId(requestHeaders.getFirst("KBank-RqAppId"));
		checkProductDupRequest.setUserId(requestHeaders.getFirst("KBank-UserId"));
		checkProductDupRequest.setTerminalId(requestHeaders.getFirst("KBank-TerminalId"));
		checkProductDupRequest.setUserLangPref(requestHeaders.getFirst("KBank-UserLangPref"));
		checkProductDupRequest.setCorrID(requestHeaders.getFirst("KBank-CorrID"));			
		
		logger.debug("checkProductDupRequest : "+checkProductDupRequest);
		
		String idNo  = checkProductDupRequest.getIdNo();
		String userId = requestHeaders.getFirst("KBank-UserId");
		HttpStatus httpStatus = HttpStatus.OK;
		if(Util.empty(userId))
		{
			userId = SystemConstant.getConstant("SYSTEM_USER");
		}
		logger.debug("idNo >> "+idNo);
				
		// Create outbound entry in SERVICE_REQ_RESP
		String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		logger.debug("serviceReqRespId >> "+serviceReqRespId);
		
		// Prepare response
		String RsAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		CheckProductDupResponseDataM checkProductDupResponse = new CheckProductDupResponseDataM();
			checkProductDupResponse.setFuncNm(ServiceConstant.ServiceId.CheckProductDup);		
			checkProductDupResponse.setRqUID(requestHeaders.getFirst("KBank-RqUID"));		
			checkProductDupResponse.setRsAppId(RsAppId);
			checkProductDupResponse.setRsUID(serviceReqRespId);
			checkProductDupResponse.setRsDt(ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
			
		String errorMsg = "";
		try{
			if(!Util.empty(idNo)){
				// Create object for passing parameters to action class
				CheckProductDupDataM checkProductDupInfo = new CheckProductDupDataM();
				checkProductDupInfo.setIdNo(idNo);
				checkProductDupInfo.setUserId(userId);
				checkProductDupInfo.setRequestId(serviceReqRespId);
				
				//Find info about applications of input "idNo"
				logger.debug("Check products duplication of idNo : "+idNo);
				checkDup(checkProductDupInfo, checkProductDupResponse);
				
				logger.debug("duplicateProducts" + checkProductDupResponse.getDuplicateProducts());
				
			}else{
				errorMsg = "Invalid IDNO : " + idNo;
				logger.error(errorMsg);
				checkProductDupResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				KbankError kbankError = new KbankError();
					kbankError.setErrorAppId(RsAppId);
					kbankError.setErrorCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
					kbankError.setErrorDesc(errorMsg);
					kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
				checkProductDupResponse.error(kbankError);
			}
			
			checkProductDupResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			errorMsg = e.getLocalizedMessage();
			checkProductDupResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			KbankError kbankError = new KbankError();
				kbankError.setErrorAppId(RsAppId);
				kbankError.setErrorCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				kbankError.setErrorDesc(errorMsg);
				kbankError.setErrorSeverity(ServiceConstant.ErrorSeverityCode.ERROR);			
			checkProductDupResponse.error(kbankError);
		}
		
		HttpHeaders httpHeaderResp = new HttpHeaders();
			httpHeaderResp.set("KBank-FuncNm", ServiceConstant.ServiceId.CheckProductDup);
			httpHeaderResp.set("KBank-RqUID", ServiceUtil.displayText(requestHeaders.getFirst("KBank-RqUID")));
			httpHeaderResp.set("KBank-RsAppId", ServiceUtil.displayText(RsAppId));
			httpHeaderResp.set("KBank-RsUID", ServiceUtil.displayText(serviceReqRespId));
			httpHeaderResp.set("KBank-RsDt", ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
		
		KbankError kbankError = getFirstError(checkProductDupResponse.getError());			
		httpHeaderResp.set("KBank-StatusCode", ServiceUtil.displayText(checkProductDupResponse.getStatusCode()));
		if(null != kbankError){
			httpHeaderResp.set("KBank-ErrorAppId", ServiceUtil.displayText(kbankError.getErrorAppId()));
			httpHeaderResp.set("KBank-ErrorAppAbbrv", ServiceUtil.displayText(kbankError.getErrorAppAbbrv()));
			httpHeaderResp.set("KBank-ErrorCode", ServiceUtil.displayText(kbankError.getErrorCode()));
			httpHeaderResp.set("KBank-ErrorDesc", ServiceUtil.displayText(kbankError.getErrorDesc()));
			httpHeaderResp.set("KBank-ErrorSeverity", ServiceUtil.displayText(kbankError.getErrorSeverity()));			
		}
		Gson gson = new Gson();
		return new ResponseEntity<String>(gson.toJson(checkProductDupResponse),httpHeaderResp,httpStatus);
	}
	
	public KbankError getFirstError(ArrayList<KbankError> errorList){
		KbankError kbankError = null;
		if(errorList != null && errorList.size() > 0){
			errorList.get(0);
		}
		return kbankError;
	}
	
	public String checkDup(CheckProductDupDataM checkProductDupInfo,CheckProductDupResponseDataM checkProductDupResponse)
	{
		String JOBSTATE_APPROVE = SystemConstant.getConstant("JOBSTATE_APPROVE");
		String JOBSTATE_APPROVED = SystemConstant.getConstant("JOBSTATE_APPROVED");
		String JOBSTATE_PENDING_FULLFRAUD = SystemConstant.getConstant("JOBSTATE_PENDING_FULLFRAUD");
		String JOBSTATE_POST_APPROVED = SystemConstant.getConstant("JOBSTATE_POST_APPROVED");
		String CHECKDUP_POST_APPROVED_DAY = SystemConstant.getConstant("CHECKDUP_POST_APPROVED_DAY");
		String JOBSTATE_POSTAPPROVAL_APPROVE = SystemConstant.getConstant("JOBSTATE_POSTAPPROVAL_APPROVE");
		ArrayList<String> JOBSTATE_CHECK_DUP_MLS = SystemConstant.getArrayListConstant("JOBSTATE_CHECK_DUP_MLS");
		ArrayList<String> CJD_SOURCE = SystemConfig.getArrayListGeneralParam("CJD_SOURCE");
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		
		String idNo = checkProductDupInfo.getIdNo();
		
		OrigObjectDAO orig = new OrigObjectDAO();
		try
		{
			conn = orig.getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTINCT AG.APPLICATION_GROUP_NO, AG.JOB_STATE, BUS.ORG_ID,AG.APPLICATION_STATUS ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION APP ON APP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" JOIN BUSINESS_CLASS BUS ON APP.BUSINESS_CLASS_ID = BUS.BUS_CLASS_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION REL ON REL.REF_ID = APP.APPLICATION_RECORD_ID AND REL.RELATION_LEVEL = 'A' ");
			sql.append(" JOIN ORIG_PERSONAL_INFO PER ON PER.PERSONAL_ID = REL.PERSONAL_ID ");
			sql.append(" WHERE PER.IDNO = ? AND NVL(AG.SOURCE,'Paper') NOT IN ( ");
			
			//ignore CJD when check dup product
			String COMMA="";
			for(String source :CJD_SOURCE){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			
			sql.append(" ) AND (AG.JOB_STATE IN ( ");
			
			COMMA="";
			for(String jobStates :JOBSTATE_CHECK_DUP_MLS){
				sql.append(COMMA+"?");
				COMMA=",";
			}
			sql.append(" ) ");
			sql.append(" OR (AG.JOB_STATE = ? AND (TRUNC(SYSDATE) - TRUNC(AG.UPDATE_DATE) <= ? )))");
			
			logger.debug("sql : "+sql);
			int cnt=1;
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(cnt++, idNo); //IDNO
			//ignore CJD when check dup product
			for(String source :CJD_SOURCE){
				ps.setString(cnt++,source); 
			}
			for(String jobStates :JOBSTATE_CHECK_DUP_MLS){
				ps.setString(cnt++,jobStates); 
			}
			ps.setString(cnt++, JOBSTATE_POST_APPROVED);
			ps.setInt(cnt++, Integer.valueOf(CHECKDUP_POST_APPROVED_DAY));
			
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				String appGroupNo = rs.getString("APPLICATION_GROUP_NO");
				String jobstate = rs.getString("JOB_STATE");
				String product = rs.getString("ORG_ID");
				String appStatus = rs.getString("APPLICATION_STATUS");
				String remark = "";
				remark += "[Found " + appGroupNo + " - Status = " + appStatus + "]";
				
				//case Pre-Approve
				/*if(JOBSTATE_APPROVE.equals(jobstate))
				{remark += "[Found " + appGroupNo + " - Status = Pre-Approve]";}
				//case Approved
				else if(JOBSTATE_APPROVED.equals(jobstate))
				{remark += "[Found " + appGroupNo + " - Status = Approved]";}
				//case Pending Full Fraud
				else if(JOBSTATE_PENDING_FULLFRAUD.equals(jobstate))
				{remark += "[Found " + appGroupNo + " - Status = Pending Final Check]";}
				//case Post Approval status + xx day
				else if(JOBSTATE_POST_APPROVED.equals(jobstate))
				{remark += "[Found " + appGroupNo + " - Status = Post Approve]";}
				//case Pending Post Approval
				else if(JOBSTATE_POSTAPPROVAL_APPROVE.equals(jobstate)){
				remark += "[Found " + appGroupNo + " - Status = Pending Pre-Approve]";
				}*/
				
				if(!Util.empty(remark))
				{
					checkProductDupResponse.getDuplicateProducts().add(new DuplicateProduct(product, remark));
				};
			}
			
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			return e.getMessage();
		}
		finally 
		{
			try 
			{				
				orig.closeConnection(conn, rs, ps);
			}
			catch(Exception e) 
			{
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
		return null;
	}
}
