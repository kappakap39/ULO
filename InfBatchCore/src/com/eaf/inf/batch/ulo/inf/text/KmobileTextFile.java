package com.eaf.inf.batch.ulo.inf.text;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfKmobileTextDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.util.FileUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class KmobileTextFile extends InfBatchObjectDAO implements GenerateFileInf {

	private static transient Logger logger = Logger.getLogger(KmobileTextFile.class);
	private static String KMOBILE_TEXT_HEADER = InfBatchProperty.getInfBatchConfig("KMOBILE_TEXT_HEADER");
	private static String KMOBILE_TEXT_FOOTER = InfBatchProperty.getInfBatchConfig("KMOBILE_TEXT_FOOTER");
	
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile)throws Exception{InfResultDataM infResult = new InfResultDataM();
		try{
			
//			NotificationDAO dao = NotificationFactory.getNotificationDAO();
//			InfKmobileTextDataM kMobile = new InfKmobileTextDataM();
//			kMobile.setSendBy("MobileNO");
//			kMobile.setCustomer("0889023466");
//			kMobile.setMessageEN("Your Credit Card card is approved and sent within 5-7 days");
//			kMobile.setSendFlag("schedule");
//			kMobile.setSchedule("dd/mm/yyyyhh:mm");
//			String contentMessage = buildMessageContent(kMobile);
//			dao.insertInfExportKmobile(contentMessage, "KMB001");

			InfDAO dao = InfFactory.getInfDAO();
			String MODULE_ID = generateFile.getUniqueId();
			String FILE_PATH = generateFile.getFileOutputPath();
			String FILE_NAME = generateFile.getFileOutputName();
			String ENCODE = generateFile.getEncode();
			StringBuilder str = new StringBuilder();
			
			//String body = InfFactory.getInfDAO().getInfExportByDate(MODULE_ID);
			ArrayList<String> dataList = dao.getInfExportKMobile(MODULE_ID);
			
			String appGroupIdList = dataList.get(0);
			String body = dataList.get(1);
			
			str.append(KMOBILE_TEXT_HEADER);
			str.append("\n");
			str.append(body);
			str.append(KMOBILE_TEXT_FOOTER);
			String contentText = str.toString();
				
			//KPL Additional Replace hh-mm-ss with hour-minute-second
			if(!Util.empty(FILE_NAME))
			{
				SimpleDateFormat hmsTime = new SimpleDateFormat("HH-mm-ss");
				String HHmmss = hmsTime.format(new Date());
				FILE_NAME = FILE_NAME.replace("HH-mm-ss", HHmmss);
			}
				
			FileUtil.generateFile(FILE_PATH, FILE_NAME, contentText, ENCODE);
				
			//Insert to INF_BATCH_LOG
			if(!Util.empty(appGroupIdList))
			{
				for(String appGroupId : appGroupIdList.split(","))
				{
						InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
						infBatchLog.setInterfaceCode(MODULE_ID);
						infBatchLog.setApplicationGroupId(appGroupId);
						infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
						infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
						dao.insertInfBatchLog(infBatchLog);
				}
			}
			else
			{
				//Case no appGroup to send KMobile
				InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
				infBatchLog.setInterfaceCode(MODULE_ID);
				infBatchLog.setSystem01("Generate Empty file. No eligible KMB001 task at this time.");
				infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
				infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
				infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
				dao.insertInfBatchLog(infBatchLog);
			}
			
			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infResult.setResultDesc(e.getLocalizedMessage());
			infResult.setErrorMsg(e.getLocalizedMessage());
		}
		
		return infResult;
	}
	
	private static String buildMessageContent(InfKmobileTextDataM kMobile){
		StringBuilder str = new StringBuilder();
		
		if(!Util.empty(kMobile)){
			if(!Util.empty(kMobile.getSendBy())){
				str.append(kMobile.getSendBy());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getCustomer())){
				str.append(kMobile.getCustomer());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getMessageTH())){
				str.append(kMobile.getMessageTH());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getMessageEN())){
				str.append(kMobile.getMessageEN());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getAlertMessageTH())){
				str.append(kMobile.getAlertMessageTH());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getAlertMessageEN())){
				str.append(kMobile.getAlertMessageEN());
				str.append(",");
			}else{
				str.append(",");
			}
			if(!Util.empty(kMobile.getImageTH())){
				str.append(kMobile.getImageTH());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getImageEN())){
				str.append(kMobile.getImageEN());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getSendFlag())){
				str.append(kMobile.getSendFlag());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getSchedule())){
				str.append(kMobile.getSchedule());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getPageCode())){
				str.append(kMobile.getPageCode());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getMessageName())){
				str.append(kMobile.getMessageName());
				str.append(",");
			}else{
				str.append(",");
			}if(!Util.empty(kMobile.getCampaignName())){
				str.append(kMobile.getCampaignName());
				str.append(",");
			}else{
				str.append(",");
			}
		}
		return str.toString();
	}

	@Override
	public InfResultDataM create(GenerateFileDataM generateFile, Connection con)throws Exception{
		return null;
	}
	

}
