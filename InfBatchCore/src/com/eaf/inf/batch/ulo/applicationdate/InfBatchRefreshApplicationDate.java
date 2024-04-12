package com.eaf.inf.batch.ulo.applicationdate;

import java.io.File;
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
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.PathUtil;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.RefreshCacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.dao.engine.SQLQueryController;


public class InfBatchRefreshApplicationDate extends InfBatchHelper{	
	private static transient Logger logger = Logger.getLogger(InfBatchRefreshApplicationDate.class);
	private static final String CURRENT_DATE = "CURRENT_DATE";
	private static final String CTE_DATE = "CTE_DATE";
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {	
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			SQLQueryController query = new SQLQueryController();
			int relativeDate = query.getRelativeDate(CacheServiceLocator.ORIG_DB);
			logger.debug("relativeDate >> "+relativeDate);
			query.updateApplicaitonDate(CacheServiceLocator.ORIG_DB, relativeDate);
	        RefreshCacheController.execute(CacheConstant.CacheName.APPLICATION_DATE);
	        readyFileForGenaralParam();
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		return infBatchResponse;
	}	 
	public void readyFileForGenaralParam() throws Exception{
	try {
		String LOCATION = PathUtil.getPath("CTE_IMPORT_PATH");
		File cteDateExcel = new File(LOCATION);
		if(!cteDateExcel.exists()){
			return;
		}
		String EXCEL_ID = InfBatchProperty.getInfBatchConfig("CTE_EXCEL_ID");
		String FILE_NAME = InfBatchProperty.getInfBatchConfig("CTE_FILE_NAME");
		Date appDate = ApplicationDateFactory.getInfChangeDateDAO().getCurrentDate();
		String applicationDate = appDate.toString();
//		String test = ServiceCache.getConstant("ROLE_CAMGR");ulocon
//		String INTERFACE_STATUS_COMPLETE = InfBatchProperty.getInfBatchConfig("NOTIFY_COMPLETED_APP_INTERFACE_STATUS_COMPLETE");infbatch
		String currentDate = null;
		String cteDate = null;
		List<String> currentDates = new ArrayList<>();
		List<String> cteDates = new ArrayList<>();
		ExcelUtil excelUtil = new ExcelUtil();
		HashMap<String,ArrayList<HashMap<String,Object>>> mapExcelList = excelUtil.read(LOCATION, EXCEL_ID, FILE_NAME);
		ArrayList<HashMap<String, Object>> listExcel= mapExcelList.get("0");
		if(!InfBatchUtil.empty(listExcel)){
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
		}
	} catch (Exception e) {
		logger.debug("ERROR",e);
		throw new Exception(e.getMessage());
	}
}
}
