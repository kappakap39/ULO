package com.eaf.inf.batch.ulo.dqe.addresszipcode;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.inf.InfBatchHelper;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.mf.dao.exception.UploadManualFileException;
import com.eaf.ulo.cache.controller.RefreshCacheController;

public class InfBatchImportZipCode extends InfBatchHelper{
	private static transient Logger logger = Logger.getLogger(InfBatchImportZipCode.class);
	@Override
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException {
		InfBatchResponseDataM infBatchResponse = new InfBatchResponseDataM();
		infBatchResponse.setBatchId(infBatchRequest.getBatchId());
		MsZipCodeDAOImpl deq = new MsZipCodeDAOImpl();
	try{
		logger.info("class >> InfBatchImportZipCode");
		deq.deleteMSZipCode();
		deq.insertMsZipCode();
		
		String cacheName = SystemConstant.getConstant("CACHE_ZIPCODE");
		RefreshCacheController.execute(cacheName);
		
//		deq.cvrsZipCode();
		infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
	} catch(Exception e){
//		logger.debug("(Payroll)#####Exist Code = 1####"+e);
		infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
		infBatchResponse.setResultDesc(e.getLocalizedMessage());
	}
		return infBatchResponse;
	}

}
