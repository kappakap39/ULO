package com.eaf.inf.batch.ulo.inf.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;
import com.eaf.core.ulo.common.postapproval.CardLinkAction;
import com.eaf.core.ulo.common.util.InfBatchResultController;
import com.eaf.inf.batch.ulo.inf.dao.InfFactory;
import com.eaf.orig.ulo.model.app.ApplicationDataM;

public class InfBatchCRMVlinkRtSalePerformance extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchCRMVlinkRtSalePerformance.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		String BATCH_ID = infBatchRequest.getBatchId();
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
			infBatchResponse.setBatchId(BATCH_ID);
		Connection conn = null;
		try{
			conn = InfBatchObjectDAO.getConnection();
			conn.setAutoCommit(false);
			List<com.eaf.orig.ulo.model.app.ApplicationGroupDataM> applicationVlinks = InfFactory.getInfDAO().loadApplicationVlink();
			if(null!=applicationVlinks){
				for(com.eaf.orig.ulo.model.app.ApplicationGroupDataM applicationGroup:applicationVlinks){
					logger.debug("applicationGroupId : "+applicationGroup.getApplicationGroupId());
					CardLinkAction cardLinkRefAction = new CardLinkAction();
						cardLinkRefAction.processCardlinkAction(applicationGroup);
					ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
					if(null!=applications){
						for(ApplicationDataM application:applications){
							logger.debug("applicationRecordId : "+application.getApplicationRecordId());
							logger.debug("finalAppDecision : "+application.getFinalAppDecision());
							if(InfBatchProperty.lookup("CRM_VLINK_RT_SALE_PERFORMANCE_GEN_CARDLINK_REF_NO_FINAL_APPDECISION",application.getFinalAppDecision())){
								InfFactory.getInfDAO().updateCardlinkRefNo(application,conn);
							}
						}
					}
				}
			}
			GenerateFileDataM generateFileData = getGenerateFileParamData(BATCH_ID, getGenerateType(BATCH_ID), Formatter.Format.YYYYMMDD);
			InfResultDataM infResult = generateFile(generateFileData,conn);
			infBatchResponse.setResultCode(infResult.getResultCode());
			infBatchResponse.setResultDesc(infResult.getResultDesc());
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			try{
				conn.rollback();
			}catch(SQLException sqlException){
				logger.fatal("ERROR",sqlException);
			}
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}finally{
			try{
				InfBatchObjectDAO.closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		}
		InfBatchResultController.setExecuteResultData(infBatchResponse);
		return infBatchResponse;
	}
}
