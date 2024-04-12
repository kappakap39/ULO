package com.eaf.inf.batch.ulo.pega;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.CsvSerializer;
import org.jsefa.csv.config.CsvConfiguration;
import org.jsefa.csv.lowlevel.config.QuoteMode;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.inf.dao.InfDAO;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.inf.batch.ulo.pega.dao.UpdateApprovalStatusDAO;
import com.eaf.inf.batch.ulo.pega.dao.UpdateApprovalStatusFactory;
import com.eaf.inf.batch.ulo.pega.model.CSVContentDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;
import com.eaf.orig.ulo.model.pega.UpdateApprovalStatusRequest;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.UpdateApprovalStatusServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;

public class InfBatchUpdateApprovalStatus extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchUpdateApprovalStatus.class);
	private static String UPDATE_APPROVAL_STATUS_CSV_HEADER = InfBatchProperty.getInfBatchConfig("UPDATE_APPROVAL_STATUS_CSV_HEADER");	
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		try{
			UpdateApprovalStatusDAO updateApprovalStatusDAO = UpdateApprovalStatusFactory.getUpdateApprovalStatusDAO();
			ArrayList<ApplicationGroupDataM> applicationGroupList = updateApprovalStatusDAO.loadUpdateApprovalStatusApplicationGroup();
			processPegaUpdateApprovalStatus(applicationGroupList);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
			InfBatchResultController.setExecuteResultData(infBatchResponse);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}
		return infBatchResponse;
	}
	
	public void processPegaUpdateApprovalStatus(ArrayList<ApplicationGroupDataM> applicationGroupList) throws Exception{
		Connection conn = InfBatchObjectDAO.getConnection();
		try{
			conn.setAutoCommit(false);
			logger.debug("applicationGroups size >> "+applicationGroupList.size());
			if(!InfBatchUtil.empty(applicationGroupList)){
				ArrayList<CSVContentDataM> csvContents = mapCSVContent(applicationGroupList,InfBatchConstant.FLAG_YES);
				UpdateApprovalStatusRequest notifyPegaRequest = new UpdateApprovalStatusRequest();
					notifyPegaRequest.setCSVContent(getCSVContent(csvContents));
				logger.debug("setCSVContent >> "+getCSVContent(csvContents));				
				String url = InfBatchProperty.getInfBatchConfig("UPDATE_APPROVAL_STATUS_URL");
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
	    			serviceRequest.setEndpointUrl(url);
	    			serviceRequest.setServiceId(UpdateApprovalStatusServiceProxy.serviceId);
	    			serviceRequest.setUserId(InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
	    			serviceRequest.setObjectData(notifyPegaRequest);    			
	    		ServiceCenterProxy proxy = new ServiceCenterProxy();
	    		ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);	
	    		String updateApprovalResult = serivceResponse.getStatusCode();
	    		logger.debug("updateApprovalResult : "+updateApprovalResult);
	    		if(ServiceResponse.Status.SUCCESS.equals(updateApprovalResult)){
	    			InfDAO dao = InfFactory.getInfDAO();
					for(ApplicationGroupDataM applicationGroup : applicationGroupList){
						InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
							infBatchLog.setApplicationGroupId(applicationGroup.getApplicationGroupId());
							infBatchLog.setInterfaceCode(InfBatchProperty.getInfBatchConfig("UPDATE_APPROVAL_STATUS_MODULE_ID"));
							infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
							infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
							infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
						dao.insertInfBatchLog(infBatchLog, conn);
					}
	    		}
			}
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			try{
				conn.rollback();
			}catch(SQLException e1){
				logger.fatal("ERROR ",e1);
			}
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
	}
	
	public static String getCSVContent(ArrayList<CSVContentDataM> cvsContents){
        CsvConfiguration csvConfiguration = new CsvConfiguration();
        	csvConfiguration.setFieldDelimiter(',');
        	csvConfiguration.setDefaultNoValueString("");
			csvConfiguration.setDefaultQuoteMode(QuoteMode.ALWAYS);
//			csvConfiguration.getSimpleTypeConverterProvider().registerConverterType(String.class, CsvUniCode.class);
			csvConfiguration.setLineBreak("\n");
        CsvSerializer serializer = (CsvSerializer) CsvIOFactory.createFactory(csvConfiguration,CSVContentDataM.class).createSerializer();
        StringWriter writer = new StringWriter();
        serializer.open(writer);
        serializer.getLowLevelSerializer().writeLine(UPDATE_APPROVAL_STATUS_CSV_HEADER); // the header       
        if(!InfBatchUtil.empty(cvsContents)){
        	for(CSVContentDataM cvsContent : cvsContents){
                serializer.write(cvsContent);
        	}
        }
        serializer.close(true);
        return writer.toString();
	}
	public static ArrayList<CSVContentDataM> mapCSVContent(ArrayList<ApplicationGroupDataM> applicationGroups,String isClose,String vetoEligible){
		ArrayList<CSVContentDataM> csvContentDataMList = new ArrayList<CSVContentDataM>();
		for(ApplicationGroupDataM applicationGroup : applicationGroups){
			CSVContentDataM csvContent = new CSVContentDataM();	
				csvContent.setCaseID(applicationGroup.getApplicationGroupNo());		
				csvContent.setIsClose(isClose);
				csvContent.setIsVetoEligible(vetoEligible);
			csvContentDataMList.add(csvContent);
		}
		return csvContentDataMList;
	}
	public static ArrayList<CSVContentDataM> mapCSVContent(ArrayList<ApplicationGroupDataM> applicationGroups,String isClose){
		ArrayList<CSVContentDataM> csvContentDataMList = new ArrayList<CSVContentDataM>();
		for(ApplicationGroupDataM applicationGroup : applicationGroups){
			CSVContentDataM csvContent = new CSVContentDataM();	
				csvContent.setCaseID(applicationGroup.getApplicationGroupNo());		
				csvContent.setIsClose(isClose);
				csvContent.setIsVetoEligible(applicationGroup.getIsVetoEligible());
			csvContentDataMList.add(csvContent);
		}
		return csvContentDataMList;
	}
}
