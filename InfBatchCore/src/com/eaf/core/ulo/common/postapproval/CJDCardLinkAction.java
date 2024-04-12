package com.eaf.core.ulo.common.postapproval;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.DeliveryMode;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.inf.batch.ulo.applicationcardlink.CloseApplicationCardLinkDAOImpl;
import com.eaf.inf.batch.ulo.cardlink.result.dao.CardLinkResultDAO;
import com.eaf.inf.batch.ulo.cardlink.result.dao.CardLinkResultDAOFactory;
import com.eaf.inf.batch.ulo.cardlink.result.model.InfCardLinkResultDataM;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.inf.text.DecryptionTextFileCore;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceReqRespDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.module.model.MLSCardSetupResultRequest;
import com.eaf.service.module.model.MLSCardSetupResultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;

public class CJDCardLinkAction
{
	private static transient Logger logger = Logger.getLogger(CJDCardLinkAction.class);
	private static String USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	private static ArrayList<String> CJD_SOURCE = SystemConstant.getArrayListConstant("CJD_SOURCE");
	private static String REQUEST_CJD_QUEUE_CONNECTION_FACTORY_JNDI = SystemConstant.getConstant("REQUEST_CJD_QUEUE_CONNECTION_FACTORY_JNDI"); //jms/CJDCF
	private static String REQUEST_CJD_QUEUE_JNDI = SystemConstant.getConstant("REQUEST_CJD_QUEUE_JNDI"); //jms/CJDQueue
	private static String JMS_IBM_CHARACTER_SET = SystemConstant.getConstant("JMS_IBM_CHARACTER_SET");
	private static String REASON_TYPE_CANCEL = SystemConstant.getConstant("REASON_TYPE_CANCEL");
	private static String KBANK_APP_ID = SystemConfig.getGeneralParam("KBANK_APP_ID");
	
	//OLD KBMF
	public static void processCardLinkAction(String applicationGroupId, String applicationGroupNo) throws Exception
	{
		String CardLinkAccountSetUpText = InfFactory.getInfDAO().getCardLinkAccountSetUpCJD(USER_ID, applicationGroupId);
		logger.debug("CardLinkAccountSetUpText = " + CardLinkAccountSetUpText);
		
		if(!Util.empty(CardLinkAccountSetUpText))
		{
			//Initialize Stuff
			Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
			conn.setAutoCommit(false);
			QueueConnectionFactory qcf = (QueueConnectionFactory)InitialContext.doLookup(REQUEST_CJD_QUEUE_CONNECTION_FACTORY_JNDI);
			QueueConnection queueConnection = qcf.createQueueConnection();
			QueueSession queueSession = queueConnection.createQueueSession(true, Session.SESSION_TRANSACTED);
			Queue cjdQueue = (Queue)InitialContext.doLookup(REQUEST_CJD_QUEUE_JNDI);
			QueueSender queueSender = queueSession.createSender(cjdQueue);
			
			try
			{
				DecryptionTextFileCore dtfc = new DecryptionTextFileCore();
				CardLinkAccountSetUpText = dtfc.replaceCardNoWithDecryptedValue(CardLinkAccountSetUpText);
				logger.debug("CardLinkAccountSetUpText : " + CardLinkAccountSetUpText);
				
				//Sent cjdMsg to MQ Queue
				String lines[] = CardLinkAccountSetUpText.split("\\r?\\n");
				for(String line : lines)
				{
					//1 line = 1 cardLinkRefNo
					String cardLinkRefno = line.substring(8,17);
					
					//Log Request to SERVICE_REQ_RESP
					String serviceReqId = ServiceUtil.generateServiceReqResId();
					ServiceReqRespDataM serviceLogRequest = new ServiceReqRespDataM();
						serviceLogRequest.setReqRespId(serviceReqId);
						serviceLogRequest.setRefCode(applicationGroupNo);
						serviceLogRequest.setActivityType(ServiceConstant.OUT);
						serviceLogRequest.setContentMsg(line);
						serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDCardlinkAction);
						serviceLogRequest.setAppId(applicationGroupId);
						serviceLogRequest.setCreateBy("SYSTEM");
					CJDCardLinkAction.createServiceReqRespLog(conn, serviceLogRequest);
					
					//Sent line text to CJD Queue
					TextMessage textMessage = queueSession.createTextMessage();
					textMessage.setIntProperty("JMS_IBM_Character_Set", Integer.valueOf(JMS_IBM_CHARACTER_SET));
					textMessage.setText(line);
					textMessage.setJMSType("text/plain"); //message type
					textMessage.setJMSExpiration(2*1000); //message expiration
					textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
					queueSender.send(textMessage);
					
					//Update INF_BATCH_LOG INTERFACE_STATUS to C
					updateInfBatchLogComplete(conn, cardLinkRefno);
					
				}
				
				//Commit work
				queueSession.commit();
				
				//Log Response to SERVICE_REQ_RESP
				String serviceRespId = ServiceUtil.generateServiceReqResId();
				ServiceReqRespDataM serviceLogResponse = new ServiceReqRespDataM();
					serviceLogResponse.setReqRespId(serviceRespId);
					serviceLogResponse.setRefCode(applicationGroupNo);
					serviceLogResponse.setActivityType(ServiceConstant.IN);
					serviceLogResponse.setRespCode("00");
					serviceLogResponse.setServiceId(ServiceConstant.ServiceId.CJDCardlinkAction);
					serviceLogResponse.setAppId(applicationGroupId);
					serviceLogResponse.setCreateBy("SYSTEM");
				CJDCardLinkAction.createServiceReqRespLog(conn, serviceLogResponse);
				conn.commit();
				
				logger.debug("Send CJD Request for " + applicationGroupNo + " Done.");
			}
			catch(Exception e)
			{
				//Rollback on Exception
				conn.rollback();
				queueSession.rollback();
				
				//Update INF_BATCH_LOG INTERFACE_STATUS to E
				updateInfBatchLogError(applicationGroupId, StringUtils.abbreviate(e.getLocalizedMessage(), 500));
				
				//Throw Exception outside method
				throw e;
			}
			finally
			{
				//Close stuff
				try
				{
					if(conn != null){conn.close();}
					queueSession.close();
					queueConnection.close();
				}
				catch(Exception e)
				{
					logger.error("Fail to close : ", e);
				}
			}
		}
	}
	
	public static void responseCJD(List<String> applicationGroupNoList) throws Exception
	{
		InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
		Connection conn = infBatchService.getConnection(InfBatchServiceLocator.MLP_DB);
		boolean SEND_CJD_RESULT_TO_MLS = MConstant.FLAG.YES.equals(SystemConstant.getConstant("SEND_CJD_RESULT_TO_MLS"));
		conn.setAutoCommit(false);
		
		//Loop through distinct QR
		try
		{
			for(String applicationGroupNo : applicationGroupNoList)
			{
				String appGroupId = null;
				Pair<String,String> pair = getCJDResponse(applicationGroupNo); //CONTENT_MSG(L)/SEND_TO_MLS(R)
				String cjdRespTxt = pair.getLeft();
				if(SEND_CJD_RESULT_TO_MLS && !Util.empty(pair.getRight()))
				{SEND_CJD_RESULT_TO_MLS = MConstant.FLAG.YES.equals(pair.getRight());}
				
				if(!Util.empty(cjdRespTxt))
				{
					//Build InfCardLinkResultDataM from cjdResp
					ArrayList<InfCardLinkResultDataM> infCardLinkResults = getInfCardLinkResults(cjdRespTxt);
				
					if(infCardLinkResults.size() > 0)
					{
						for(InfCardLinkResultDataM infCardLinkResult : infCardLinkResults)
						{
							
								//Do task for each infCardLinkResult
								String cardLinkRefNo = infCardLinkResult.getRefId();
								logger.debug("cardLinkRefNo : "+cardLinkRefNo);
								if(!InfBatchUtil.empty(cardLinkRefNo))
								{
									String mainCardNo = Util.empty(infCardLinkResult.getMainCardNo1()) ? (Util.empty(infCardLinkResult.getMainCardNo2()) ? (Util.empty(infCardLinkResult.getMainCardNo3()) ? "" : infCardLinkResult.getMainCardNo3()) : infCardLinkResult.getMainCardNo2()) : infCardLinkResult.getMainCardNo1();
									String supCardNo = Util.empty(infCardLinkResult.getSupCardNo1()) ? (Util.empty(infCardLinkResult.getSupCardNo2()) ? (Util.empty(infCardLinkResult.getSupCardNo3()) ? "" : infCardLinkResult.getSupCardNo3()) : infCardLinkResult.getSupCardNo2()) : infCardLinkResult.getSupCardNo1();
									
									String applicationGroupId = CardLinkResultDAOFactory.getCardLinkResultDAO().isCardLinkRefNoExist(cardLinkRefNo, mainCardNo, supCardNo, conn);
									if(Util.empty(applicationGroupId))
									{
										logger.warn("[WARNING] Not found CardLinkRefNo : " + cardLinkRefNo + " with Approved jobstate and cardlinkFlag in (Y,W) in MLP_APP.");
										continue;
									}
									else
									{
										appGroupId = applicationGroupId;
									}
									
									CardLinkResultDAO cardLinkResultDAO = CardLinkResultDAOFactory.getCardLinkResultDAO();
									//Insert to INF_CARDLINK_RESULT for CardLink Maintenance Page
									cardLinkResultDAO.insertInfCardLinkResult(infCardLinkResult,conn);
									//Update Cust/Card cardLinkFlag according to infCardLinkResult + SEND_CJD_RESULT_TO_MLS Constant
									cardLinkResultDAO.updateCardLinkFlag(infCardLinkResult,conn, SEND_CJD_RESULT_TO_MLS);
									logger.debug("MainCustFlag : "+infCardLinkResult.getMainCustFlag());
									logger.debug("SupCustFlag : "+infCardLinkResult.getSupCustFlag());
									if(!InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL.equals(infCardLinkResult.getMainCustFlag())
											&&!InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL.equals(infCardLinkResult.getSupCustFlag())){
										logger.debug("update cardlink next day process.");
										cardLinkResultDAO.updateCardlinkNextDay(applicationGroupId, conn);
									}
									
									logger.debug(InfBatchConstant.ResultCode.SUCCESS);
								}
								else
								{
									logger.debug(InfBatchConstant.ResultCode.FAIL);
									logger.debug("Card Link Ref No is Null.");
								}
								
								//Delete from ONLINE_CARD_RESULT
								deleteFromOnlineCardlinkResult(conn, cardLinkRefNo);
								
								conn.commit();
						}
					}
				}
				
				if(!Util.empty(appGroupId))
				{
					//Try to close finished application
					CloseApplicationCardLinkDAOImpl cacl = new CloseApplicationCardLinkDAOImpl();
					cacl.updateMLPPostApproved(appGroupId, conn);
				}
			
			}
			
			conn.commit();
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			conn.rollback();
			throw e;
		}
		finally
		{
			if(null!=conn)
			{
				conn.close();
			}
		}
	}
	
	public static String sendCSRResultToMLS(String appGrpid, String result, String cardNoEncypt, String cardNo, String remark, String custNo)
	throws Exception
	{
		String resultCode = "";
		
		//Will Send to MLS only when SUCCESS or CANCELLED by CardMaintenance
		if(MConstant.FLAG_S.equals(result) || REASON_TYPE_CANCEL.equals(result))
		{
			MLSCardSetupResultRequest mlsCSRReq = new MLSCardSetupResultRequest();
			String appGrpNo = getApplicationGroupNoMLP(appGrpid);
			
			if(!Util.empty(appGrpNo))
			{
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("YYYYMMDD");
				SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("YYYYMMDDHHmmss");
				Date now = new Date();
				mlsCSRReq.getHeader().setAppId(KBANK_APP_ID);
				mlsCSRReq.getHeader().setMessageUid(KBANK_APP_ID + "_" + simpleDateFormat1.format(now) + "_" + now.getTime());
				mlsCSRReq.getHeader().setMessageDt(simpleDateFormat2.format(now));
				
				mlsCSRReq.getBody().setContractNo(appGrpNo);
				mlsCSRReq.getBody().setResult(("S".equals(result)) ? "00" : "10"); //00(S)/10(F)
				mlsCSRReq.getBody().setCardNo(cardNoEncypt); //encrptMainCardNo
				mlsCSRReq.getBody().setCardNoMask(cardNo); //mainCardNo
				mlsCSRReq.getBody().setRemark(remark); //blank
				mlsCSRReq.getBody().setCardLinkCustNo(custNo); //cardLinkCustNo
			}
			else
			{
				throw new Exception("Can't find AppGrpNo of AppGrpId " + appGrpid);
			}
		
			//Log request to MLS
			ServiceReqRespDataM serviceLogRequest = new ServiceReqRespDataM();
				serviceLogRequest.setReqRespId(ServiceUtil.generateServiceReqResId());
				serviceLogRequest.setRefCode(appGrpNo);
				serviceLogRequest.setActivityType(ServiceConstant.OUT);
				serviceLogRequest.setContentMsg(mlsCSRReq.toString());
				serviceLogRequest.setServiceId(ServiceConstant.ServiceId.CJDMLSResult);
				serviceLogRequest.setAppId(appGrpid);
				serviceLogRequest.setCreateBy("SYSTEM");
			CJDCardLinkAction.createServiceReqRespLog(serviceLogRequest);
			
			logger.debug("Prepare to send Message to MLS : " + appGrpNo);
			String errorMessage = null;
			MLSCardSetupResultResponse mlsResponse = null;
			try
			{
				mlsResponse = sendCSRResultToMLS(mlsCSRReq);
				if(mlsResponse != null && mlsResponse.getHeader() != null)
				{
					logger.debug("MLS response code >> "+mlsResponse.getHeader().getRespCode());
					if(Util.empty(mlsResponse.getHeader().getRespCode()))
					{
						resultCode = ServiceConstant.Status.BUSINESS_EXCEPTION;
						errorMessage = "Response code is null.";
					}
					else
					{
						if(!ServiceConstant.Status.SUCCESS.equals(mlsResponse.getHeader().getRespCode()))
						{
							resultCode = ServiceConstant.Status.BUSINESS_EXCEPTION;
							errorMessage = "MLS response with ErrorCode : " + mlsResponse.getHeader().getRespCode() + " - " + mlsResponse.getHeader().getRespDesc();
						}
						else
						{
							resultCode = ServiceConstant.Status.SUCCESS;
						}
					}
				}
				else
				{
					resultCode = ServiceConstant.Status.BUSINESS_EXCEPTION;
					errorMessage = "Null response from MLS.";
				}
			}
			catch(Exception e)
			{
				resultCode = ServiceConstant.Status.SYSTEM_EXCEPTION;
				errorMessage = StringUtils.abbreviate(e.getMessage(), 500);
			}
			
			//Log response to MLS
			ServiceReqRespDataM serviceLogResponse = new ServiceReqRespDataM();
			serviceLogResponse.setReqRespId(ServiceUtil.generateServiceReqResId());
			serviceLogResponse.setRefCode(appGrpNo);
			serviceLogResponse.setActivityType(ServiceConstant.IN);
			serviceLogResponse.setContentMsg((mlsResponse != null) ? mlsResponse.toString() : null);
			serviceLogResponse.setRespCode(resultCode);
			serviceLogResponse.setErrorMessage(errorMessage);
			serviceLogResponse.setServiceId(ServiceConstant.ServiceId.CJDMLSResult);
			serviceLogResponse.setAppId(appGrpid);
			serviceLogResponse.setCreateBy("SYSTEM");
			CJDCardLinkAction.createServiceReqRespLog(serviceLogResponse);
			
			//Case not SUCCESS, send exception to outside to make task FAIL
			if(!ServiceConstant.Status.SUCCESS.equals(resultCode))
			{
				throw new Exception(errorMessage);
			}
		}
		else
		{
			logger.debug("Not Success or Cancelled by CardMaintennance, not send to MLS for ApplicationGroupId - " + appGrpid);
			resultCode = ServiceConstant.Status.SUCCESS;
		}
		
		return resultCode;
	}
	
	public static MLSCardSetupResultResponse sendCSRResultToMLS(MLSCardSetupResultRequest mlsCSRReq)
	throws Exception
	{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String reqJson = ow.writeValueAsString(mlsCSRReq);
		
		HttpHeaders httpHeaderReq = new HttpHeaders();
		httpHeaderReq.setContentType(MediaType.APPLICATION_JSON);
		httpHeaderReq.set("Authorization","Basic "+SystemConfig.getProperty("CJD_MLS_AUTHORIZATION"));
				
		HttpEntity<String> requestEntity = new HttpEntity<String>(reqJson,httpHeaderReq);
		RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
		@Override
		protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
			if(connection instanceof HttpsURLConnection ){
				((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
					@Override
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
						}
					});
				}
					super.prepareConnection(connection, httpMethod);
				}
			});	
			
		String mlsResURL = SystemConfig.getProperty("MLS_CARDLINK_RESULT_URL");
		ResponseEntity<String> responseEntity = restTemplate.exchange(URI.create(mlsResURL),HttpMethod.POST,requestEntity,String.class);
		
		logger.debug("Status Code >> "+responseEntity.getStatusCode());
		logger.debug("MLS response >> "+responseEntity);
		
		Gson gson = new Gson();
		MLSCardSetupResultResponse response = gson.fromJson(responseEntity.getBody(), MLSCardSetupResultResponse.class);
			
		return response;
	}
	
	public static String getApplicationGroupNoMLP(String applicationGroupId)
	{
		String applicationGroupNo = null;
		Connection conn = null;
		try
		{
			conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
			applicationGroupNo = getApplicationGroupNo(conn, applicationGroupId);
		}
		catch(Exception e)
		{
			logger.fatal("ERROR "+e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return applicationGroupNo;
	}
	
	public static String getApplicationGroupNoByCardLinkRefNo(String cardlinkRefno)
	{
		String JOBSTATE_APPROVED = SystemConstant.getConstant("JOBSTATE_APPROVED");
		String applicationGroupNo = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
		
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_NO FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" WHERE AP.CARDLINK_REF_NO = ? ");
			sql.append(" AND AG.JOB_STATE = ? ");
			sql.append(" AND AP.CARDLINK_FLAG IN ('Y','W') ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, cardlinkRefno);
			ps.setString(2, JOBSTATE_APPROVED);
			rs = ps.executeQuery();			
			if(rs.next())
			{
				applicationGroupNo = rs.getString("APPLICATION_GROUP_NO");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn, rs, ps);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return applicationGroupNo;
	}
	
	public static String getApplicationGroupNo(Connection conn, String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String applicationGroupNo = null;		
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_NO FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			//logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId" + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);			
			rs = ps.executeQuery();			
			if(rs.next()){
				applicationGroupNo = rs.getString("APPLICATION_GROUP_NO");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				if(rs!=null)
				{rs.close();}
				if(ps!=null)
				{ps.close();}
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		return applicationGroupNo;
	}
	
	public static boolean isCJDApplication(String applicationGroupId)
	{
		boolean isCJD = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
		
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SOURCE FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			//logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId" + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);			
			rs = ps.executeQuery();			
			if(rs.next())
			{
				if(!Util.empty(rs.getString("SOURCE"))
					&& CJD_SOURCE.contains(rs.getString("SOURCE")))
				{
					isCJD = true;
				}
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn, rs, ps);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return isCJD;
	}
	
	public static ArrayList<String> getDistinctQR() throws ApplicationException {
		Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> distinctQR = new ArrayList<String>();
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DISTINCT OCR.APPLICATION_GROUP_NO ");
			sql.append(" FROM ONLINE_CARD_RESULT OCR ");
			sql.append(" JOIN ORIG_APPLICATION AP ON OCR.CARDLINK_REF_NO = AP.CARDLINK_REF_NO ");
			sql.append(" JOIN ORIG_APPLICATION_GROUP AG ON AP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" WHERE AP.CARDLINK_FLAG IN ('Y','W') AND AG.SOURCE = 'CJD' ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				String applitionGroupNo = rs.getString("APPLICATION_GROUP_NO");
				distinctQR.add(applitionGroupNo);
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn, rs, ps);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return distinctQR;
	}
	
	public static Pair<String,String> getCJDResponse(String applicationGroupNo) throws ApplicationException {
		Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cjdResponseTxt = "";
		Pair<String,String> pair = Pair.of("","");
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT OCR.CONTENT_MSG, OCR.SEND_TO_MLS ");
			sql.append(" FROM ONLINE_CARD_RESULT OCR ");
			sql.append(" JOIN ORIG_APPLICATION AP ON OCR.CARDLINK_REF_NO = AP.CARDLINK_REF_NO ");
			sql.append(" JOIN ORIG_APPLICATION_GROUP AG ON AP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" WHERE AP.CARDLINK_FLAG IN ('Y','W') AND AG.SOURCE = 'CJD' ");
			sql.append(" AND AG.APPLICATION_GROUP_NO = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupNo);
			rs = ps.executeQuery();			
			while(rs.next())
			{
				Clob clob = rs.getClob("CONTENT_MSG");
				if(clob != null)
				{
					cjdResponseTxt += clob.getSubString(1, (int)clob.length());
				}
				
				cjdResponseTxt += "\n";
				String SEND_TO_MLS = rs.getString("SEND_TO_MLS");
				pair = Pair.of(cjdResponseTxt, SEND_TO_MLS);
				
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn, rs, ps);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		return pair;
	}
	
	public static void updateInfBatchLogComplete(Connection conn, String cardLinkRefno) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE INF_BATCH_LOG SET INTERFACE_STATUS = 'C' ");
			sql.append(" WHERE INTERFACE_CODE = 'CL001' ");
			sql.append(" AND INTERFACE_STATUS = 'R' ");
			sql.append(" AND SYSTEM07 = 'ONLINE' ");
			sql.append(" AND REF_ID = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cardLinkRefno);
			ps.executeUpdate();	
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				if(rs != null){rs.close();}
				if(ps != null){ps.close();}
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}
	
	public static void insertOnlineCardlinkResult(String applicationGroupNo, String cardlinkRefno, String contentMsg) throws Exception {
		Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try
		{
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ONLINE_CARD_RESULT ");
			sql.append(" (APPLICATION_GROUP_NO, CARDLINK_REF_NO, CONTENT_MSG, SEND_TO_MLS) ");
			sql.append(" VALUES (?,?,?,?) ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupNo);
			ps.setString(2, cardlinkRefno);
			ps.setString(3, contentMsg);
			ps.setString(4, MConstant.FLAG.YES);
			ps.executeUpdate();	
		}
		catch(Exception e)
		{
			logger.fatal(e.getLocalizedMessage());
			throw e;
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn, rs, ps);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}
	
	public static void updateInfBatchLogError(String applicationGroupId, String errorText) throws ApplicationException {
		Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE INF_BATCH_LOG SET INTERFACE_STATUS = 'E' ");
			sql.append(" ,LOG_MESSAGE = ? ");
			sql.append(" WHERE INTERFACE_CODE = 'CL001' ");
			sql.append(" AND INTERFACE_STATUS = 'R' ");
			sql.append(" AND SYSTEM07 = 'ONLINE' ");
			sql.append(" AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, errorText);
			ps.setString(2, applicationGroupId);
			ps.executeUpdate();	
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				InfBatchObjectDAO.closeConnection(conn, rs, ps);
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}
	
	public static void deleteFromOnlineCardlinkResult(Connection conn, String cardlinkRefNo) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE FROM ONLINE_CARD_RESULT WHERE CARDLINK_REF_NO = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cardlinkRefNo);
			ps.executeUpdate();	
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				if(rs!=null){rs.close();}
				if(ps!=null){ps.close();}
			}
			catch(Exception e)
			{
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}
	
	public static ArrayList<InfCardLinkResultDataM> getInfCardLinkResults(String cjdResp) throws Exception 
	{
		ArrayList<InfCardLinkResultDataM> infCardLinkResults = new ArrayList<InfCardLinkResultDataM>();
		if(!Util.empty(cjdResp))
		{
			try 
			{
				Encryptor encrpt = EncryptorFactory.getDIHEncryptor();
				String cjdRespLine[] = cjdResp.split("\\r?\\n");
				for(int index = 0 ; index < cjdRespLine.length ; index++)
				{
					String sReadline = cjdRespLine[index];
					int countPipe = sReadline.length() - sReadline.replace("|", "").length();
					logger.debug("countPipe : "+countPipe);
					if(sReadline.contains("|"))
					{								
						String[] listData = sReadline.split("\\|");
						String cardlinkRefNo = getValue(0,listData);
						String applicationType =getValue(1,listData);
						String mainCustNo =getValue(2,listData);
						String mainCustRespCode =getValue(3,listData);
						String mainCustRespDesc =getValue(4,listData);
						
						String supCustNo =getValue(5,listData);
						String supCustRespCode =getValue(6,listData);
						String supCustRespDesc =getValue(7,listData);					
						
						String mainCardNo1 =getValue(8,listData);
						String mainCardRespCode1 =getValue(9,listData);
						String mainCardRespDesc1 =getValue(10,listData);
						String encrptMainCardNo1 ="";
						if(!InfBatchUtil.empty(mainCardNo1)){
							encrptMainCardNo1 =  encrpt.encrypt(mainCardNo1);
						}			
						
						String  supCardNo1=getValue(11,listData);
						String  supCardRespCode1=getValue(12,listData);
						String  supCardRespDesc1=getValue(13,listData);
						String encrptSupCardNo1 ="";
						if(!InfBatchUtil.empty(supCardNo1)){
							encrptSupCardNo1 =  encrpt.encrypt(supCardNo1);
						}
										
						String  mainCardNo2=getValue(14, listData);
						String  mainCardRespCode2=getValue(15,listData);
						String  mainCardRespDesc2=getValue(16,listData);
						String encrptMainCardNo2 ="";
						if(!InfBatchUtil.empty(mainCardNo2)){
							encrptMainCardNo2 =  encrpt.encrypt(mainCardNo2);
						}
						
						String  supCardNo2=getValue(17,listData);
						String  supCardRespCode2=getValue(18,listData);
						String  supCardRespDesc2=getValue(19,listData);
						String encrptSupCardNo2 ="";
						if(!InfBatchUtil.empty(supCardNo2)){
							encrptSupCardNo2 =  encrpt.encrypt(supCardNo2);
						}
						
						String  mainCardNo3=getValue(20,listData);
						String  mainCardRespCode3=getValue(21,listData);
						String  mainCardRespDesc3=getValue(22,listData);
						String encrptMainCardNo3 ="";
						if(!InfBatchUtil.empty(mainCardNo3)){
							encrptMainCardNo3 =  encrpt.encrypt(mainCardNo3);
						}
						
						String  supCardNo3=getValue(23,listData);
						String  supCardRespCode3=getValue(24,listData);
						String	supCardRespDesc3=getValue(25,listData);
						String encrptSupCardNo3 ="";
						if(!InfBatchUtil.empty(supCardNo3)){
							encrptSupCardNo3 =  encrpt.encrypt(supCardNo3);
						}
							
						InfCardLinkResultDataM infCardLinkResult = new InfCardLinkResultDataM();
							infCardLinkResult.setApplicationType(applicationType);
							infCardLinkResult.setRefId(cardlinkRefNo);
							infCardLinkResult.setMainCardNo1(mainCardNo1);
							infCardLinkResult.setMainCardNo2(mainCardNo2);
							infCardLinkResult.setMainCardNo3(mainCardNo3);
							infCardLinkResult.setEncryptMainCardNo1(encrptMainCardNo1);
							infCardLinkResult.setEncryptMainCardNo2(encrptMainCardNo2);
							infCardLinkResult.setEncryptMainCardNo3(encrptMainCardNo3);
							infCardLinkResult.setMainCardRespCode1(mainCardRespCode1);
							infCardLinkResult.setMainCardRespCode2(mainCardRespCode2);
							infCardLinkResult.setMainCardRespCode3(mainCardRespCode3);
							infCardLinkResult.setMainCardRespDesc1(mainCardRespDesc1);
							infCardLinkResult.setMainCardRespDesc2(mainCardRespDesc2);
							infCardLinkResult.setMainCardRespDesc3(mainCardRespDesc3);
							infCardLinkResult.setMainCardNo1Flag(getResponseCodeFlag(mainCardRespCode1));
							infCardLinkResult.setMainCardNo2Flag(getResponseCodeFlag(mainCardRespCode2));
							infCardLinkResult.setMainCardNo3Flag(getResponseCodeFlag(mainCardRespCode3));
							infCardLinkResult.setMainCustNo(mainCustNo);
							infCardLinkResult.setMainCustRespCode(mainCustRespCode);
							infCardLinkResult.setMainCustRespDesc(mainCustRespDesc);
							infCardLinkResult.setMainCustFlag(getResponseCodeFlag(mainCustRespCode));
							infCardLinkResult.setStatus(InfBatchConstant.CARDLINK_RESULT_STATUS.ACTIVE);
							infCardLinkResult.setSupCardNo1(supCardNo1);
							infCardLinkResult.setSupCardNo2(supCardNo2);
							infCardLinkResult.setSupCardNo3(supCardNo3);
							infCardLinkResult.setEncryptSupCardNo1(encrptSupCardNo1);
							infCardLinkResult.setEncryptSupCardNo2(encrptSupCardNo2);
							infCardLinkResult.setEncryptSupCardNo3(encrptSupCardNo3);
							infCardLinkResult.setSupCardRespCode1(supCardRespCode1);				
							infCardLinkResult.setSupCardRespCode2(supCardRespCode2);				
							infCardLinkResult.setSupCardRespCode3(supCardRespCode3);
							infCardLinkResult.setSupCardRespDesc1(supCardRespDesc1);
							infCardLinkResult.setSupCardRespDesc2(supCardRespDesc2);
							infCardLinkResult.setSupCardRespDesc3(supCardRespDesc3);
							infCardLinkResult.setSupCardNo1Flag(getResponseCodeFlag(supCardRespCode1));
							infCardLinkResult.setSupCardNo2Flag(getResponseCodeFlag(supCardRespCode2));
							infCardLinkResult.setSupCardNo3Flag(getResponseCodeFlag(supCardRespCode3));
							infCardLinkResult.setSupCustNo(supCustNo);
							infCardLinkResult.setSupCustRespCode(supCustRespCode);
							infCardLinkResult.setSupCustRespDesc(supCustRespDesc);			
							infCardLinkResult.setSupCustFlag(getResponseCodeFlag(supCustRespCode));
							
						infCardLinkResults.add(infCardLinkResult);
					}
					index++;
				}
			}
			catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				throw e;
			}
		}
		
		logger.debug("infCardLinkResults size : "+infCardLinkResults.size());
		return infCardLinkResults;
	}
	
	public static void createServiceReqRespLog(ServiceReqRespDataM servReqRespM)
	{
		Connection conn = InfBatchObjectDAO.getConnection(InfBatchServiceLocator.MLP_DB);
		try
		{
			createServiceReqRespLog(conn, servReqRespM);
		}
		catch(Exception e)
		{
			logger.fatal(e.getLocalizedMessage());
		}
		finally
		{
			try 
			{
				if(conn!=null)
				{conn.close();}
			} catch (Exception e)
			{
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public static void createServiceReqRespLog(Connection conn, ServiceReqRespDataM servReqRespM)
	{
		if(conn != null)
		{
			PreparedStatement ps = null;
			try
			{			
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
				
			}
			catch (Exception e) 
			{
				logger.fatal(e.getLocalizedMessage());
			} 
			finally 
			{
				try 
				{
					if(ps!=null)
					{ps.close();}
				} catch (Exception e)
				{
					logger.fatal(e.getLocalizedMessage());
				}
			}
		}
	}
	
	private static String getResponseCodeFlag(String respCode)
	{
		 if(InfBatchConstant.CARD_LINK_RESULT_RESPONSE_CODE.SUCCESS.equals(respCode)){
			 return InfBatchConstant.CARDLINK_RESULT_FLAG.SUCCESS;
		 }else if(InfBatchConstant.CARD_LINK_RESULT_RESPONSE_CODE.REJECT.equals(respCode)){
			 return InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL;
		 }
		 return "";
	}
	
	private static String getValue(int index,String[] result)
	{
		if(result.length<=index) return "";
		return result[index].trim();
	}
}
