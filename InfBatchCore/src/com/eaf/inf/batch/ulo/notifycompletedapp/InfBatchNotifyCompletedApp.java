package com.eaf.inf.batch.ulo.notifycompletedapp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.inf.batch.ulo.notifycompletedapp.dao.NotifyCompletedAppDAO;
import com.eaf.inf.batch.ulo.notifycompletedapp.dao.NotifyCompletedAppFactory;
import com.eaf.inf.batch.ulo.notifycompletedapp.model.NotifyCompleteAppDataM;
import com.eaf.inf.batch.ulo.notifycompletedapp.model.NotifyCompletedAppUtil;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfBatchNotifyCompletedApp extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchNotifyCompletedApp.class);
	String NOTIFY_COMPLETED_APP_OUTPUT_NAME = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_OUTPUT_NAME");
	String NOTIFY_COMPLETED_APP_RECORD_TYPE_MAIN = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_RECORD_TYPE_MAIN");
	String NOTIFY_COMPLETED_APP_RECORD_TYPE_LIST = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_RECORD_TYPE_LIST");
	String NOTIFY_COMPLETED_APP_TOTAL_RECORD_TYPE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_TOTAL_RECORD_TYPE");
	String FIELD_DELIMETER = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_FIELD_DELIMETER");
	String NOTIFY_COMPLETED_APP_INTERFACE_CODE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_INTERFACE_CODE");
	String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_INTERFACE_STATUS_COMPLETE");
	String INTERFACE_STATUS_ERROR = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_INTERFACE_STATUS_ERROR");
	String SUFFIX_APP_NO = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_SUFFIX_APP_NO");
	String DOC_SET_STATUS = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_DOC_SET_STATUS");
	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		Writer writer = null;
		ArrayList<String> applicationGroupIds  = new ArrayList<String>();
		String fileOutputPath = "";
		try {
			NotifyCompletedAppDAO notifyCompletedAppDAO = NotifyCompletedAppFactory.getNotifyCompletedAppDAO();
			HashMap<String, Object> notifyCompletedData = notifyCompletedAppDAO.getNotifyCompletedApp();
			int totalRecord = 0;
			String NOTIFY_COMPLETED_APP_OUTPUT_PATH = PathUtil.getPath("NOTIFY_COMPLETED_APP_OUTPUT_PATH");
			FileControl.mkdir(NOTIFY_COMPLETED_APP_OUTPUT_PATH);
			String fileName = NOTIFY_COMPLETED_APP_OUTPUT_NAME.replace("yyyymmdd",Formatter.display(InfBatchProperty.getDate(),Formatter.EN,"yyyyMMdd"));
			fileOutputPath = NOTIFY_COMPLETED_APP_OUTPUT_PATH+File.separator+fileName;
			logger.debug("fileOutputPath >> "+fileOutputPath);
			StringBuilder text = new StringBuilder("");
			if(!InfBatchUtil.empty(notifyCompletedData)){
				applicationGroupIds = new ArrayList<String>(notifyCompletedData.keySet());
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputPath),"UTF-8"));
				for(String keyName : applicationGroupIds){
					HashMap<String,Object> hObject =  (HashMap<String,Object>)notifyCompletedData.get(keyName);
					String applicationGroupNo = (String) hObject.get(NotifyCompletedAppUtil.APPLICATION_GROUP_NO);			
					ArrayList<NotifyCompleteAppDataM> notifyComplateDataList = (ArrayList<NotifyCompleteAppDataM>)hObject.get(NotifyCompletedAppUtil.PERSONAL_LIST);
						totalRecord++;
						text  = new StringBuilder("");				 
						text.append(NOTIFY_COMPLETED_APP_RECORD_TYPE_MAIN);
						text.append(FIELD_DELIMETER+Formatter.displayText(applicationGroupNo));
						text.append(FIELD_DELIMETER+DOC_SET_STATUS);
						text.append(FIELD_DELIMETER+NotifyCompletedAppUtil.YES);
						int countSup=0;
						if(!InfBatchUtil.empty(notifyComplateDataList)){
							for(NotifyCompleteAppDataM notifyCompleteAppDataM: notifyComplateDataList){
								text.append(NotifyCompletedAppUtil.getNewLine()+NOTIFY_COMPLETED_APP_RECORD_TYPE_LIST);
								text.append(FIELD_DELIMETER+Formatter.displayText(notifyCompleteAppDataM.getIdNo()));
								text.append(FIELD_DELIMETER+Formatter.displayText(notifyCompleteAppDataM.getIdType()));
								text.append(FIELD_DELIMETER+Formatter.displayText(NotifyCompletedAppUtil.getApplicantTypeOfConsent(notifyCompleteAppDataM.getPersonalType(), countSup)));
								text.append(FIELD_DELIMETER+Formatter.displayText(notifyCompleteAppDataM.getCisId()));
							}
						}
						writer.write(text.toString());
						writer.write(NotifyCompletedAppUtil.getNewLine());
						totalRecord+=notifyComplateDataList.size();
				}
				logger.debug("totalRecord >> "+totalRecord);
				writer.write(NOTIFY_COMPLETED_APP_TOTAL_RECORD_TYPE+FIELD_DELIMETER+String.valueOf(totalRecord));
				infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			}else{
				infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				infBatchResponse.setResultDesc("Not found data.");
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileOutputPath),"UTF-8"));
				writer.write(text.toString());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
			FileControl.delete(fileOutputPath);
		}finally{
			if(null != writer){
				try{
					writer.close();
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
					infBatchResponse.setResultDesc(e.getLocalizedMessage());
					FileControl.delete(fileOutputPath);
				}
			}
			InfBatchResultController.setExecuteResultData(infBatchResponse);
		}
		insertInfBatchLog(applicationGroupIds,infBatchResponse.getResultCode());
		return infBatchResponse;
	}

    private void insertInfBatchLog(ArrayList<String> applicationGroupIds,String resultCode){
     	 Connection conn = InfBatchObjectDAO.getConnection();	
     	 try{
     		logger.debug("resultCode>>"+resultCode);
     		logger.debug("applicationGroupIds  size>>"+applicationGroupIds.size());
     		NotifyCompletedAppDAO notifyCompletedAppDAO = NotifyCompletedAppFactory.getNotifyCompletedAppDAO();
     		conn.setAutoCommit(false);
     		if(!InfBatchUtil.empty(applicationGroupIds)){
	  			for(String applicationGroupId :applicationGroupIds){
	   			InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
	   				infBatchLog.setApplicationGroupId(applicationGroupId);
	   				infBatchLog.setInterfaceCode(NOTIFY_COMPLETED_APP_INTERFACE_CODE);
	   				infBatchLog.setInterfaceStatus(InfBatchConstant.ResultCode.SUCCESS.equals(resultCode)?INTERFACE_STATUS_COMPLETE:INTERFACE_STATUS_ERROR);
	   				notifyCompletedAppDAO.insertInfBatchLog(infBatchLog, conn);
	   			}		
     		}
     		conn.commit();
  		}catch(Exception e){
  			logger.fatal("ERROR",e);
  			try {
  				conn.rollback();
  			}catch(SQLException e1){
  				logger.fatal("ERROR",e1);
  			}			
  		}finally{
  			try{
  				InfBatchObjectDAO.closeConnection(conn);
  			}catch(Exception e){
  				logger.fatal("ERROR ",e);
  			}
  		}
      }
}
