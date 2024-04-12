package com.eaf.inf.batch.ulo.cardlink.result;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.inf.batch.ulo.cardlink.result.dao.CardLinkResultDAO;
import com.eaf.inf.batch.ulo.cardlink.result.dao.CardLinkResultDAOFactory;
import com.eaf.inf.batch.ulo.cardlink.result.model.InfCardLinkResultDataM;
import com.eaf.orig.ulo.constant.MConstant;

public class CardLinkResultTask implements TaskInf{
	private static transient Logger logger = Logger.getLogger(CardLinkResultTask.class);
	String CARD_LINK_RESULT_INPUT_PATH = SystemConfig.getProperty("CARD_LINK_RESULT_INPUT_PATH");
	String CARD_LINK_RESULT_INPUT_FILE_NAME = InfBatchProperty.getInfBatchConfig("CARD_LINK_RESULT_INPUT_FILE_NAME");
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<TaskObjectDataM>();
		FileReader reader = null;
		BufferedReader buffer =null;
		try {
			String inputPath = PathUtil.getPath("CARD_LINK_RESULT_INPUT_PATH");
			StringBuilder pathBuilder = new StringBuilder(inputPath)
				.append(File.separator)
				.append(CARD_LINK_RESULT_INPUT_FILE_NAME);
			String path = pathBuilder.toString();
			logger.debug("path>>"+path);
			Encryptor encrpt = EncryptorFactory.getDIHEncryptor();
			reader = new FileReader(path);
			buffer =new BufferedReader(reader);
			String sReadline;
			int index = 0;
			while((sReadline=buffer.readLine())!=null){
				int countPipe = sReadline.length() - sReadline.replace("|", "").length();
				logger.debug("countPipe : "+countPipe);
				if(index!=0  && sReadline.contains("|") && countPipe!=3/*Footer*/){								
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
						infCardLinkResult.setMainCardNo1Flag(this.getResponseCodeFlag(mainCardRespCode1));
						infCardLinkResult.setMainCardNo2Flag(this.getResponseCodeFlag(mainCardRespCode2));
						infCardLinkResult.setMainCardNo3Flag(this.getResponseCodeFlag(mainCardRespCode3));
						infCardLinkResult.setMainCustNo(mainCustNo);
						infCardLinkResult.setMainCustRespCode(mainCustRespCode);
						infCardLinkResult.setMainCustRespDesc(mainCustRespDesc);
						infCardLinkResult.setMainCustFlag(this.getResponseCodeFlag(mainCustRespCode));
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
						infCardLinkResult.setSupCardNo1Flag(this.getResponseCodeFlag(supCardRespCode1));
						infCardLinkResult.setSupCardNo2Flag(this.getResponseCodeFlag(supCardRespCode2));
						infCardLinkResult.setSupCardNo3Flag(this.getResponseCodeFlag(supCardRespCode3));
						infCardLinkResult.setSupCustNo(supCustNo);
						infCardLinkResult.setSupCustRespCode(supCustRespCode);
						infCardLinkResult.setSupCustRespDesc(supCustRespDesc);			
						infCardLinkResult.setSupCustFlag(this.getResponseCodeFlag(supCustRespCode));
					
					TaskObjectDataM queueObject = new TaskObjectDataM();
						queueObject.setUniqueId(cardlinkRefNo);
						queueObject.setObject(infCardLinkResult);
					taskObjects.add(queueObject);
				}
				index++;
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new TaskException(e);
		}finally{
			try{
				if(null!=buffer){
					buffer.close();
				}
				if(null!=reader){
					reader.close();
				}
			}catch(Exception e2){
				logger.fatal("ERROR",e2);
			}
		}
		logger.debug("taskObjects size : "+taskObjects.size());
		return taskObjects;
	}

	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException,Exception {
		
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		String taskId = task.getTaskId();
		Connection conn = null;
		Connection mlpConn = null;
		Connection targetConn = null;
		try{
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			TaskObjectDataM taskObject = task.getTaskObject();
			taskExecute.setResponseObject(taskObject.getObject());
			logger.debug("taskId : "+taskId);
			InfCardLinkResultDataM infCardLinkResult = (InfCardLinkResultDataM)taskObject.getObject();
			String cardLinkRefNo = infCardLinkResult.getRefId();
			logger.debug("cardLinkRefNo : "+cardLinkRefNo);
			if(!InfBatchUtil.empty(cardLinkRefNo))
			{
				conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
				mlpConn = infBatchService.getConnection(InfBatchServiceLocator.MLP_DB);
				conn.setAutoCommit(false);
				mlpConn.setAutoCommit(false);
				
				//Find source of cardLinkRefNo (ORIG_DB or MLP_DB) use available main, sub card
				String mainCardNo = Util.empty(infCardLinkResult.getMainCardNo1()) ? (Util.empty(infCardLinkResult.getMainCardNo2()) ? (Util.empty(infCardLinkResult.getMainCardNo3()) ? "" : infCardLinkResult.getMainCardNo3()) : infCardLinkResult.getMainCardNo2()) : infCardLinkResult.getMainCardNo1();
				String supCardNo = Util.empty(infCardLinkResult.getSupCardNo1()) ? (Util.empty(infCardLinkResult.getSupCardNo2()) ? (Util.empty(infCardLinkResult.getSupCardNo3()) ? "" : infCardLinkResult.getSupCardNo3()) : infCardLinkResult.getSupCardNo2()) : infCardLinkResult.getSupCardNo1();
				
				String applicationGroupId = CardLinkResultDAOFactory.getCardLinkResultDAO().isCardLinkRefNoExist(cardLinkRefNo, mainCardNo, supCardNo, conn);
				if(!Util.empty(applicationGroupId))
				{
					targetConn = conn;
				}
				else
				{
					applicationGroupId = CardLinkResultDAOFactory.getCardLinkResultDAO().isCardLinkRefNoExist(cardLinkRefNo, mainCardNo, supCardNo, mlpConn);
					if(!Util.empty(applicationGroupId))
					{
						targetConn = mlpConn;
					}
					else
					{
						taskExecute.setResultCode(InfBatchConstant.ResultCode.WARNING);
						taskExecute.setResultDesc("Not found CardLinkRefNo : " + cardLinkRefNo + " with Approved jobstate and cardlinkFlag in (Y,W) in ORIG_APP and MLP_APP .");
						return taskExecute;
					}
				}
				
				CardLinkResultDAO cardLinkResultDAO = CardLinkResultDAOFactory.getCardLinkResultDAO();
				cardLinkResultDAO.insertInfCardLinkResult(infCardLinkResult,targetConn);
				cardLinkResultDAO.updateCardLinkFlag(infCardLinkResult,targetConn);
				logger.debug("MainCustFlag : "+infCardLinkResult.getMainCustFlag());
				logger.debug("SupCustFlag : "+infCardLinkResult.getSupCustFlag());
				if(!InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL.equals(infCardLinkResult.getMainCustFlag())
						&&!InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL.equals(infCardLinkResult.getSupCustFlag())){
					logger.debug("update cardlink next day process.");
					cardLinkResultDAO.updateCardlinkNextDay(applicationGroupId, targetConn);
					
					//Copy cardlinkCustNo
					if(targetConn == conn) //FLP Results
					{
						//Copy CardLinkCustNo from FLP to MLP
						updateCardLinkCustNo(infCardLinkResult, applicationGroupId, conn, mlpConn);
					}
					else if(targetConn == mlpConn) //MLP Results
					{
						//Copy CardLinkCustNo from MLP to FLP
						updateCardLinkCustNo(infCardLinkResult, applicationGroupId, mlpConn, conn);
					}
				}
				
				taskExecute.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			}else{
				taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
				taskExecute.setResultDesc("Card Link Ref No is Null.");
			}
			
			conn.commit();
			mlpConn.commit();
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			conn.rollback();
			mlpConn.rollback();
			taskExecute.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecute.setResultDesc(e.getLocalizedMessage());
		}finally{
			if(null!=conn){
				conn.close();
			}
			if(null!=mlpConn){
				mlpConn.close();
			}
		}
		return taskExecute;
	}

	 private String getResponseCodeFlag(String respCode){
		 if(InfBatchConstant.CARD_LINK_RESULT_RESPONSE_CODE.SUCCESS.equals(respCode)){
			 return InfBatchConstant.CARDLINK_RESULT_FLAG.SUCCESS;
		 }else if(InfBatchConstant.CARD_LINK_RESULT_RESPONSE_CODE.REJECT.equals(respCode)){
			 return InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL;
		 }
		 return "";
	 }
	 
	 private String getValue(int index,String[] result){
		 if(result.length<=index) return "";
		 return result[index].trim();
	 }
	 
	private void updateCardLinkCustNo(InfCardLinkResultDataM infCardLinkResult, String applicationGroupId, Connection connFrom, Connection connTo) throws Exception
	{
		CardLinkResultDAO cardLinkResultDAO = CardLinkResultDAOFactory.getCardLinkResultDAO();
		String idNoMain = null;
		String idNoSup = null;
		
		//Copy for main
		if(!InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL.equals(infCardLinkResult.getMainCustFlag()))
		{
			String mainCustNo = infCardLinkResult.getMainCustNo();
			
			if(!Util.empty(mainCustNo) 
			&& (!Util.empty(infCardLinkResult.getMainCardNo1()) || !Util.empty(infCardLinkResult.getMainCardNo2()) || !Util.empty(infCardLinkResult.getMainCardNo3())))
			{
				//Select IDNO from connFrom using cardLinkRefNo
				HashMap<String,String> cardLinkInfo = cardLinkResultDAO.selectCardLinkInfoWithAppGroupId(mainCustNo, applicationGroupId, connFrom);
				idNoMain = cardLinkInfo.get("IDNO");
				if(!Util.empty(idNoMain))
				{
					String mainCustNoFirstTwoDigit = mainCustNo.substring(0, 2);
					
					if("10".equals(mainCustNoFirstTwoDigit))
					{
						//Find out if match Scenario 1 (New ORIG_APP - New MLP_APP)
						boolean isNewCardLinkCustFrom = !MConstant.FLAG.NO.equals(cardLinkInfo.get("NEW_CARDLINK_CUST_FLAG"));
								
						String personalIdNewCardLinkCustTo = cardLinkResultDAO.getPersonalIdNewCardLinkCustTo(mainCustNoFirstTwoDigit, idNoMain, connTo);
						boolean isNewCardLinkCustTo = !Util.empty(personalIdNewCardLinkCustTo);	
						
						if(isNewCardLinkCustFrom && isNewCardLinkCustTo)
						{
							//Copy CustNumber from connFrom to connTo with same mainCustNoFirstTwoDigit
							cardLinkResultDAO.updateCardLinkCustNo(mainCustNo, personalIdNewCardLinkCustTo, connTo);
						}
					}
				
					cardLinkResultDAO.updateCardlinkFlagGenerate(connTo, idNoMain);
				}
			}
		}
		
		//Copy for Sup
		if(!InfBatchConstant.CARDLINK_RESULT_FLAG.FAIL.equals(infCardLinkResult.getSupCustFlag()))
		{
			String supCustNo = infCardLinkResult.getSupCustNo();
			
			if(!Util.empty(supCustNo) 
			&& (!Util.empty(infCardLinkResult.getSupCardNo1()) || !Util.empty(infCardLinkResult.getSupCardNo2()) || !Util.empty(infCardLinkResult.getSupCardNo3())))
			{
				//Select IDNO from ORIG_APP using cardLinkRefNo
				HashMap<String,String> cardLinkInfoSup = cardLinkResultDAO.selectCardLinkInfoWithAppGroupId(supCustNo, applicationGroupId, connFrom);
				idNoSup = cardLinkInfoSup.get("IDNO");
				if(!Util.empty(idNoSup))
				{
					String supCustNoFirstTwoDigit = supCustNo.substring(0, 2);
					
					if("10".equals(supCustNoFirstTwoDigit))
					{
						//Find out if match Scenario 1 (New ORIG_APP - New MLP_APP)
						boolean isNewCardLinkCustSupFrom = !MConstant.FLAG.NO.equals(cardLinkInfoSup.get("NEW_CARDLINK_CUST_FLAG"));
							
						String personalIdNewCardLinkCustSupTo = cardLinkResultDAO.getPersonalIdNewCardLinkCustTo(supCustNoFirstTwoDigit, idNoSup, connTo);
						boolean isNewCardLinkCustSupTo = !Util.empty(personalIdNewCardLinkCustSupTo);	
						
						if(isNewCardLinkCustSupFrom && isNewCardLinkCustSupTo)
						{
							//Copy CustNumber from ConnFrom to ConnTo with same mainCustNoFirstTwoDigit
							cardLinkResultDAO.updateCardLinkCustNo(supCustNo, personalIdNewCardLinkCustSupTo, connTo);
						}
					}
				
					cardLinkResultDAO.updateCardlinkFlagGenerate(connTo, idNoSup);
				}
			}
		}
	}
}
