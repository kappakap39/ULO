package com.eaf.inf.batch.ulo.applicationdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.file.ExcelUtil;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.core.ulo.flp.util.FLPPasswordUtil;
import com.eaf.ulo.cache.dao.engine.SQLQueryController;
import com.orig.bpm.workflow.proxy.BPMTimeMachineProxy;
import com.orig.bpm.workflow.service.model.EventManagerDetail;
import com.orig.bpm.workflow.service.model.TimeMachineResponse;

public class InfBatchChangeDate extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchChangeDate.class);
	private static final String CURRENT_DATE = "CURRENT_DATE";
	private static final String CTE_DATE = "CTE_DATE";
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			String LOCATION = PathUtil.getPath("CTE_IMPORT_PATH");
			String EXCEL_ID = InfBatchProperty.getInfBatchConfig("CTE_EXCEL_ID");
			String FILE_NAME = InfBatchProperty.getInfBatchConfig("CTE_FILE_NAME");
			SQLQueryController query = new SQLQueryController();
			/*int relativeDate = query.getRelativeDate(CacheServiceLocator.ORIG_DB);
			logger.debug("relativeDate >> "+relativeDate);
			query.updateApplicaitonDate(CacheServiceLocator.ORIG_DB, relativeDate);
			logger.info("Table APPLICATION_DATE Data : "+query.getApplicationDate(CacheServiceLocator.ORIG_DB));*/
			String BPM_DB_HOST = InfBatchProperty.getInfBatchConfig("BPM_DB_HOST");
			String BPM_DB_USER = InfBatchProperty.getInfBatchConfig("BPM_DB_USER");
			String BPM_DB_PASSWORD = InfBatchProperty.getInfBatchConfig("BPM_DB_PASSWORD");
			Connection connection = DriverManager.getConnection(BPM_DB_HOST,BPM_DB_USER,FLPPasswordUtil.decrypt(BPM_DB_PASSWORD));	
			BPMTimeMachineProxy proxy = new BPMTimeMachineProxy();
			TimeMachineResponse result = proxy.resetBPMScheduler(connection);
			List<EventManagerDetail> eventManagerDetail = result.getEventManagerDetail();
			if(null != eventManagerDetail){
				for (EventManagerDetail eventManager: eventManagerDetail) {
					logger.info(eventManager);
				}
			}
			
			Date appDate = ApplicationDateFactory.getInfChangeDateDAO().getCurrentDate();
			String applicationDate = appDate.toString();
//			String test = ServiceCache.getConstant("ROLE_CAMGR");ulocon
//			String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_INTERFACE_STATUS_COMPLETE");infbatch
			String currentDate = null;
			String cteDate = null;
			List<String> currentDates = new ArrayList<>();
			List<String> cteDates = new ArrayList<>();
			ExcelUtil excelUtil = new ExcelUtil();
			HashMap<String,ArrayList<HashMap<String,Object>>> mapExcelList = excelUtil.read(LOCATION, EXCEL_ID, FILE_NAME);
			ArrayList<HashMap<String, Object>> listExcel= mapExcelList.get("0");
			HashMap<String, Object> excelfile = listExcel.get(0);
			for(int i=1;i<listExcel.size();i++){
				HashMap<String,Object> excelRow = listExcel.get(i);
				currentDate = (String) excelRow.get(CURRENT_DATE);
				cteDate = (String)excelRow.get(CTE_DATE);
				
				currentDates.add(currentDate);
				cteDates.add(cteDate);
				
				if(currentDate.equals(applicationDate)){
					ApplicationDateFactory.getInfChangeDateDAO().updateChangeDate(cteDate);
				}
			}
			
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		return infBatchResponse;
		
	}
}
