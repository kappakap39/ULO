package com.eaf.inf.batch.ulo.pega;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.task.api.TaskInf;
import com.eaf.core.ulo.common.task.exception.TaskException;
import com.eaf.core.ulo.common.task.model.TaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.task.model.TaskObjectDataM;
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

public class UpdateApprovalStatusTask extends InfBatchObjectDAO implements TaskInf{
	private static transient Logger logger = Logger.getLogger(UpdateApprovalStatusTask.class);	
	@Override
	public ArrayList<TaskObjectDataM> getTaskObjects() throws TaskException {
		ArrayList<TaskObjectDataM> taskObjects = new ArrayList<>();
		try{
			UpdateApprovalStatusDAO dao = UpdateApprovalStatusFactory.getUpdateApprovalStatusDAO();
			ArrayList<ApplicationGroupDataM> applicationGroups = dao.loadUpdateApprovalStatusApplicationGroup();
			TaskObjectDataM taskObject = new TaskObjectDataM();
				taskObject.setUniqueId(String.valueOf(applicationGroups.size()));
				taskObject.setObject(applicationGroups);
			taskObjects.add(taskObject);
			logger.debug("applicationGroups size >> "+applicationGroups.size());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return taskObjects;
	}
	@SuppressWarnings("unchecked")
	@Override
	public TaskExecuteDataM onTask(TaskDataM task) throws TaskException, Exception {
		TaskExecuteDataM taskExecute = new TaskExecuteDataM();
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		try{
			TaskObjectDataM taskObject = task.getTaskObject();
			ArrayList<ApplicationGroupDataM> applicationGroups = (ArrayList<ApplicationGroupDataM>)taskObject.getObject();
			logger.debug("applicationGroups size >> "+applicationGroups.size());
			if(!InfBatchUtil.empty(applicationGroups)){
				ArrayList<CSVContentDataM> csvContents = mapCSVContent(applicationGroups);
				UpdateApprovalStatusRequest notifyPegaRequest = new UpdateApprovalStatusRequest();
					notifyPegaRequest.setCSVContent(getCSVContent(csvContents));				
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
	    		if(InfBatchUtil.empty(updateApprovalResult)){
	    			InfDAO dao = InfFactory.getInfDAO();
					for(ApplicationGroupDataM applicationGroup : applicationGroups){
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
			try {
				conn.rollback();
			} catch (SQLException e1) {
				logger.fatal("ERROR ",e1);
			}
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return taskExecute;
	}	
	public static String getCSVContent(ArrayList<CSVContentDataM> cvsContents){
       return InfBatchUpdateApprovalStatus.getCSVContent(cvsContents);
	}	
	public static ArrayList<CSVContentDataM> mapCSVContent(ArrayList<ApplicationGroupDataM> applicationGroups){
		return InfBatchUpdateApprovalStatus.mapCSVContent(applicationGroups,InfBatchConstant.FLAG_YES);
	}
}
