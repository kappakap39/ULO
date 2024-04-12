package com.eaf.inf.batch.ulo.inf.text;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.inf.GenerateFileInf;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.util.FileUtil;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;

public class CsvFileCore extends InfBatchObjectDAO implements GenerateFileInf{
	private static transient Logger logger = Logger.getLogger(CsvFileCore.class);
	String USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
	String CRM_VLINK_RT_SALE_PERFORMANCE = InfBatchProperty.getInfBatchConfig(InfBatchConstant.CRM_VLINK_RT_SALE_PERFORMANCE+"_"+InfBatchConstant.ReportParam.MODULE_ID);
	
//	public String initContent(String MODULE_ID, String OUTPUT_NAME) throws Exception{
//		String executeResult = "";
//		if(CRM_VLINK_RT_SALE_PERFORMANCE.equals(MODULE_ID)){			
//			generateCancelAppCardLinkRefNo();
//			executeResult = InfFactory.getInfDAO().getSalePerformance(USER_ID, OUTPUT_NAME);
//		}
//		return executeResult;
//	}
	public String generateContent(String MODULE_ID, String OUTPUT_NAME,Connection conn) throws Exception{
		String executeResult = "";
		if(CRM_VLINK_RT_SALE_PERFORMANCE.equals(MODULE_ID)){			
//			processCardlinkRefNo(conn);
			executeResult = InfFactory.getInfDAO().getSalePerformance(USER_ID,OUTPUT_NAME,conn);
		}
		return executeResult;
	}
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile)throws Exception{
		InfResultDataM infResult = new InfResultDataM();		
//		try{
//			String MODULE_ID = generateFile.getUniqueId();
//			String FILE_PATH = generateFile.getFileOutputPath();
//			String FILE_NAME = generateFile.getFileOutputName();
//			String ENCODE = generateFile.getEncode();
//			String executeResult = initContent(MODULE_ID,FILE_NAME);
//			logger.debug("executeResult : "+executeResult);
//			String CONTENT = InfFactory.getInfDAO().getInfExport(MODULE_ID);
//			logger.debug("MODULE_ID >> "+MODULE_ID);
//			logger.debug("FILE_PATH >> "+FILE_PATH);
//			logger.debug("FILE_NAME >> "+FILE_NAME);
//			FileUtil.generateCSVFile(FILE_PATH, FILE_NAME, CONTENT, ENCODE);
//			FileUtil.generateFile(FILE_PATH, FILE_NAME, CONTENT, ENCODE);
//			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
//		}catch(Exception e){
//			logger.fatal("ERROR ",e);
//			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
//			infResult.setResultDesc(e.getLocalizedMessage());
//		}
		return infResult;
	}
//	public void generateCancelAppCardLinkRefNo(){
//		try{
//			ArrayList<String>  applicationGroupIds=InfFactory.getInfDAO().getGenerateCancelCardLinkRefNoApplicationGroupId();
//			if(null!=applicationGroupIds){
//				ArrayList<ApplicationGroupDataM> applicationGroups = ProcessApplicationManager.loadCardLinkApplicationGroups(applicationGroupIds);
//				if(null!=applicationGroups && applicationGroups.size()>0){
//					CardLinkAction cardLinkRefAction = new CardLinkAction();
//					for(ApplicationGroupDataM applicationGroup : applicationGroups){
//						cardLinkRefAction.doAction(applicationGroup);
//						ProcessApplicationManager.saveCardLinkRefApplications(applicationGroup.getApplications());
//					}
//				}
//			}
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//		}
//	}
	
//	public void processCardlinkRefNo(Connection conn)throws Exception{
//		try{
//			ArrayList<String> applicationGroupIds = InfFactory.getInfDAO().loadApplicationLifeCycle(conn);
//			if(null!=applicationGroupIds){
//				ArrayList<ApplicationGroupDataM> applicationGroups = ProcessApplicationManager.loadCardLinkApplicationGroups(applicationGroupIds);
//				if(null!=applicationGroups && applicationGroups.size()>0){
//					CardLinkAction cardLinkRefAction = new CardLinkAction();
//					for(ApplicationGroupDataM applicationGroup : applicationGroups){
//						cardLinkRefAction.processCardlinkAction(applicationGroup);
//						ProcessApplicationManager.saveCardLinkRefApplications(applicationGroup.filterApplicationLifeCycle(),conn);
//					}
//				}
//			}
//			
//		}catch(Exception e){
//			logger.fatal("ERROR",e);
//			throw new Exception(e.getMessage());
//		}
//	}
	@Override
	public InfResultDataM create(GenerateFileDataM generateFile, Connection conn)throws Exception{
		InfResultDataM infResult = new InfResultDataM();		
		try{
			String MODULE_ID = generateFile.getUniqueId();
			String FILE_PATH = generateFile.getFileOutputPath();
			String FILE_NAME = generateFile.getFileOutputName();
			String ENCODE = generateFile.getEncode();
			String executeResult = generateContent(MODULE_ID,FILE_NAME,conn);
			logger.debug("executeResult : "+executeResult);
			String CONTENT = InfFactory.getInfDAO().getInfExport(MODULE_ID,conn);
			logger.debug("MODULE_ID >> "+MODULE_ID);
			logger.debug("FILE_PATH >> "+FILE_PATH);
			logger.debug("FILE_NAME >> "+FILE_NAME);
//			FileUtil.generateCSVFile(FILE_PATH, FILE_NAME, CONTENT, ENCODE);
			FileUtil.generateFile(FILE_PATH, FILE_NAME, CONTENT, ENCODE);
			infResult.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infResult.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infResult.setResultDesc(e.getLocalizedMessage());
		}
		return infResult;
	}
}
