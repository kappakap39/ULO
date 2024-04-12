package com.eaf.service.common.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.eaf.orig.ulo.model.app.CVRSValidationResultDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.master.ObjectDAO;
import com.eaf.service.common.master.ServiceLocator;
import com.eaf.service.common.model.CISRefDataObject;
import com.eaf.service.common.model.FollowUpPegaRefDataObject;
import com.eaf.service.common.model.ServiceLogData;
import com.eaf.service.common.model.ServiceReqRespDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.manual.PegaFollowUpServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class ServiceDAOImpl extends ObjectDAO implements ServiceDAO{
	private static transient org.apache.log4j.Logger logger = Logger.getLogger(ServiceDAOImpl.class);
	String DEFAULT_VLD_TO_DT = ServiceCache.getConstant("DEFAULT_VLD_TO_DT"); 
	String DEFAULT_LAST_VRSN_F = ServiceCache.getConstant("DEFAULT_LAST_VRSN_F"); 
	String DEFAULT_IP_ST_CD = ServiceCache.getConstant("DEFAULT_IP_ST_CD"); 
	String FIELD_ID_CID_TYPE = ServiceCache.getConstant("FIELD_ID_CID_TYPE"); 
	String PRODUCT_CRADIT_CARD = ServiceCache.getConstant("PRODUCT_CRADIT_CARD");
	public ServiceDAOImpl(){
		super();
	}
	public ServiceDAOImpl(int dbType){
		this.dbType = dbType;
	}
	private int dbType = ServiceLocator.ORIG_DB;
	@Override
	public void createLog(ServiceReqRespDataM servReqRespM) throws Exception {
		PreparedStatement ps = null;	
		Connection conn = null;	
		try{			
			conn = getConnection(dbType);					
			StringBuilder sql = new StringBuilder("");			
			sql.append("INSERT INTO SERVICE_REQ_RESP ");
			sql.append("( REQ_RESP_ID, SERVICE_ID, ACTIVITY_TYPE, REF_CODE, RESP_CODE, ");
			sql.append("RESP_DESC, ERROR_MESSAGE, CREATE_DATE, CREATE_BY, CONTENT_MSG, ");
			sql.append("APP_ID, TRANSACTION_ID, SERVICE_DATA ) ");
			sql.append("VALUES (?,?,?,?,?,?,?,SYSTIMESTAMP(6),?,?,?,?,?)");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, servReqRespM.getReqRespId());
			ps.setString(index++, servReqRespM.getServiceId());
			ps.setString(index++, servReqRespM.getActivityType());
			ps.setString(index++, servReqRespM.getRefCode());
			ps.setString(index++, servReqRespM.getRespCode());
			
			ps.setString(index++, servReqRespM.getRespDesc());
			ps.setString(index++, servReqRespM.getErrorMessage());
			ps.setString(index++, servReqRespM.getCreateBy());
			ps.setString(index++, servReqRespM.getContentMsg());
			
			ps.setString(index++, servReqRespM.getAppId());
			ps.setString(index++, servReqRespM.getTransId());
			ps.setString(index++, servReqRespM.getServiceData());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
	}
	
	@Override
	public ServiceRequestDataM getRequestResponseData(String serviceReqResId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ServiceRequestDataM rawDataM = new ServiceRequestDataM();
		try{
			conn = getConnection(dbType);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ACTIVITY_TYPE,CONTENT_MSG,RESP_CODE,RESP_DESC,ERROR_MESSAGE FROM SERVICE_REQ_RESP WHERE REQ_RESP_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, serviceReqResId);
			rs = ps.executeQuery();
			while(rs.next()){
				if(rs.getString("ACTIVITY_TYPE").equals("O")){
					rawDataM.putRawData("requestJSON", rs.getString("CONTENT_MSG"));
				}else if(rs.getString("ACTIVITY_TYPE").equals("I")){
					String contentMsg = rs.getString("CONTENT_MSG");
					if(ServiceUtil.empty(contentMsg)){
						contentMsg = "{}";
					}
					String resultCode = rs.getString("RESP_CODE");
					if(ServiceUtil.empty(resultCode)){
						resultCode = "";
					}
					String resultDesc = rs.getString("RESP_DESC");
					if(ServiceUtil.empty(resultDesc)){
						resultDesc = "";
					}
					String errorMsg = rs.getString("ERROR_MESSAGE");
					if(ServiceUtil.empty(errorMsg)){
						errorMsg = "";
					}
					JSONObject JsonObject = new JSONObject();
					JsonObject.put("ResponseCode", resultCode);
					JsonObject.put("ResponseDesc", resultDesc);
					JsonObject.put("errorMsg", errorMsg);
					JSONObject responseObject = new JSONObject(contentMsg);
					JsonObject.put("ResponseData", responseObject);
					rawDataM.putRawData("responseJSON", JsonObject.toString());
				}
			}
			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		}finally{
			try{
				closeConnection(conn,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new Exception(e.getMessage());
			}
		}
		return rawDataM;
	}
	
	@Override
	public CISRefDataObject getServiceData(String serviceId, String appId,CISRefDataObject requestRefData) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CISRefDataObject refServiceData = null;
		logger.debug("serviceId : "+serviceId);
		logger.debug("appId : "+appId);
		logger.debug("requestRefData : "+requestRefData);
		String refType = requestRefData.getRefType();
		logger.debug("refType : "+refType);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT SERVICE_DATA,RESP_CODE ");
			sql.append(" FROM SERVICE_REQ_RESP ");
			sql.append(" WHERE SERVICE_ID = ? AND APP_ID = ? AND ACTIVITY_TYPE = ? ");
			sql.append(" ORDER BY CREATE_DATE DESC ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, serviceId);
			ps.setString(2, appId);
			ps.setString(3, ServiceConstant.IN);
			rs = ps.executeQuery();
			while(rs.next()){
				refServiceData = new CISRefDataObject();
				String serviceData = rs.getString("SERVICE_DATA");
				String respCode = rs.getString("RESP_CODE");				
				if(!ServiceUtil.empty(serviceData)){
					Gson gson = new Gson();
					refServiceData = gson.fromJson(serviceData, CISRefDataObject.class);
				}
				refServiceData.setRespCode(respCode);				
				if(ServiceResponse.Status.SUCCESS.equals(respCode)){
					String requestPersonalId = requestRefData.getPersonalId();
					String personalId = refServiceData.getPersonalId();
					if(null != requestPersonalId && requestPersonalId.equals(personalId)){
						refServiceData.setSuccess(true);
						break;
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		if(null == refServiceData){
			refServiceData = new CISRefDataObject();
		}
		refServiceData.setServiceId(serviceId);
		return refServiceData;
	}
	@Override
	public boolean sendDummyToPegaFlag(String applicationGroupId,int lifeCycle) throws Exception {
		logger.debug("applicationGroupId : "+applicationGroupId);
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean sendDummyToPegaFlag = true;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT RESP_CODE,SERVICE_DATA FROM SERVICE_REQ_RESP WHERE SERVICE_ID = ? AND APP_ID = ? AND ACTIVITY_TYPE = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, PegaFollowUpServiceProxy.serviceId);
			ps.setString(2, applicationGroupId);
			ps.setString(3, ServiceConstant.IN);
			rs = ps.executeQuery();
			while(rs.next()){
				sendDummyToPegaFlag = true;
				String responseCode = rs.getString("RESP_CODE");
				logger.debug("responseCode : "+responseCode);
				if(ServiceConstant.Status.SUCCESS.equals(responseCode)){
					String serviceData = rs.getString("SERVICE_DATA");
					logger.debug("serviceData : "+serviceData);
					if(!ServiceUtil.empty(serviceData)){
						Gson gson = new Gson();
						FollowUpPegaRefDataObject refDataObject = null;
						try{
							refDataObject = gson.fromJson(serviceData,FollowUpPegaRefDataObject.class);
						}catch(Exception e){
						}
						logger.debug("refDataObject : "+refDataObject);
						if(null != refDataObject && !ServiceUtil.empty(refDataObject.getAction()) 
								&& FollowUpPegaRefDataObject.FollowUpGroup.DUMMY.equals(refDataObject.getFollowUpGroup())
									&& lifeCycle == refDataObject.getLifeCycle()){
							sendDummyToPegaFlag = false;
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return sendDummyToPegaFlag;
	}
	
	@Override
	public  Date getAppCurrentDate() throws Exception {
		Date applicationDate = null;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;	
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT APP_DATE FROM APPLICATION_DATE");
			logger.debug("SQL >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				applicationDate = rs.getDate("APP_DATE");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return applicationDate;
	}
	@Override
	public void createCVRSValidationResultTable(CVRSValidationResultDataM cvrsValidationResult) throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO CVRS_VALIDATION_RESULT ");
			sql.append(" ( RESULT_ID, PERSONAL_ID, FIELD_GROUP, FIELD_ID, FIELD_NAME,");
			sql.append(" ERROR_CODE, ERROR_DESC, CREATE_DATE, CREATE_BY)");
			sql.append(" VALUES(CVRS_VALIDATION_RESULT_PK.NEXTVAL,?,?,?,?, ?,?,?,?) ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, cvrsValidationResult.getPersonalId());
			ps.setString(cnt++, cvrsValidationResult.getFieldGroup());
			ps.setString(cnt++, cvrsValidationResult.getFieldId());
			ps.setString(cnt++, cvrsValidationResult.getFieldName());
			
			ps.setString(cnt++, cvrsValidationResult.getErrorCode());		
			ps.setString(cnt++, cvrsValidationResult.getErrorDesc());			
			ps.setDate(cnt++, 	cvrsValidationResult.getCreateDate());
			ps.setString(cnt++, cvrsValidationResult.getCreateBy());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}	
	}
	@Override
	public String deleteCVRSValidationResultTable(String personalId)throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		String isDeleteComplete = ServiceConstant.Status.SUCCESS;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE CVRS_VALIDATION_RESULT ");
			sql.append(" WHERE PERSONAL_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			isDeleteComplete = ServiceConstant.Status.SYSTEM_EXCEPTION;
			throw new Exception(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				isDeleteComplete = ServiceConstant.Status.SYSTEM_EXCEPTION;
				throw new Exception(e.getMessage());
			}
		}
		return isDeleteComplete;
	}
	@Override
	public ServiceLogData getServiceData(String refCode,String serviceId, String taskId) throws Exception {
		ServiceLogData serviceLogData = new ServiceLogData();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;	
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT RESP_CODE,RESP_DESC,ERROR_MESSAGE " +
					"FROM SERVICE_REQ_RESP " +
					"WHERE REF_CODE = ? AND SERVICE_ID = ? AND SERVICE_DATA = ? AND ACTIVITY_TYPE = ?");
			logger.debug("SQL >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, refCode);
			ps.setString(2, serviceId);
			ps.setString(3, taskId);
			ps.setString(4, ServiceConstant.IN);
			rs = ps.executeQuery();
			if(rs.next()){
				serviceLogData.setResponseCode(rs.getString("RESP_CODE"));
				serviceLogData.setResponseDesc(rs.getString("RESP_DESC"));
				serviceLogData.setErrorMsg(rs.getString("ERROR_MESSAGE"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return serviceLogData;
	}
	@Override
	public String getCardType(String cardCode, String cardLevel, String product)
			throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String cardType = "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT CARD_TYPE_ID ");
			sql.append(" FROM CARD_TYPE ");
			sql.append(" WHERE ACTIVE_STATUS = 'A' AND CARD_CODE = ? ");
			if(PRODUCT_CRADIT_CARD.equals(product)){
				sql.append(" AND CARD_LEVEL = ? ");
			}
			logger.debug("SQL >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, cardCode);
			
			if(PRODUCT_CRADIT_CARD.equals(product)){
				ps.setString(2, cardLevel);
			}
			
			rs = ps.executeQuery();
			if(rs.next()){
				cardType = rs.getString("CARD_TYPE_ID");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return cardType;
	}
	
}