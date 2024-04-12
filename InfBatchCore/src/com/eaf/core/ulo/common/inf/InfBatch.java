package com.eaf.core.ulo.common.inf;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.model.InfBatchRequestDataM;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.mf.dao.exception.UploadManualFileException;

public interface InfBatch {
	public void preProcessAction(InfBatchRequestDataM infBatchRequest)throws InfBatchException;
	public InfBatchResponseDataM processAction(InfBatchRequestDataM infBatchRequest) throws InfBatchException, UploadManualFileException; 
	public void postProcessAction(InfBatchRequestDataM infBatchRequest,InfBatchResponseDataM infBatchResponse)throws InfBatchException;
}
