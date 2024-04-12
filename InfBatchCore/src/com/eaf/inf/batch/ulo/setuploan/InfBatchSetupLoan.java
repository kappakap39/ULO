package com.eaf.inf.batch.ulo.setuploan;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.model.InfResultDataM;

public class InfBatchSetupLoan extends InfBatchHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchSetupLoan.class);

	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		String BATCH_ID = infBatchRequest.getBatchId();
		Connection conn = null;
		
		try{
			InfBatchServiceLocator infBatchService = InfBatchServiceLocator.getInstance();
			conn = infBatchService.getConnection(InfBatchServiceLocator.ORIG_DB);
			conn.setAutoCommit(false);
			GenerateFileDataM generateFileData = getGenerateTextFileParamData(BATCH_ID, getGenerateType(BATCH_ID), Formatter.Format.YYYYMMDD);
			InfResultDataM infResult = generateFile(generateFileData);
			infBatchResponse.setResultCode(infResult.getResultCode());
			infBatchResponse.setResultDesc(infResult.getResultDesc());
			conn.commit();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			try {conn.rollback();} catch (SQLException e1) {logger.fatal("ERROR ",e);}
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
			infBatchResponse.setResultDesc(e.getLocalizedMessage());
		}finally{
			try {
				if(null!=conn)
					conn.close();
			}
			catch (Exception e) {
				logger.fatal("ERROR ",e);
			}
		}
		return infBatchResponse;
	}
}
