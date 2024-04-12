package com.eaf.inf.batch.ulo.letter.reject;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;

public class InfBatchResultHelper implements InfBatchResult {

	private static transient Logger logger = Logger.getLogger(InfBatchResultHelper.class);

	@Override
	public void setExecuteTaskResultData(InfBatchResponseDataM infBatchResponse,ProcessTaskDataM processTask) {
		int success = 0;
		int error = 0;
		int warning = 0;
		ArrayList<TaskExecuteDataM> taskExecutes = processTask.getTaskExecutes();
		if(!InfBatchUtil.empty(taskExecutes)){
			for(TaskExecuteDataM taskExecute : taskExecutes){
				if(null!=taskExecute&&InfBatchConstant.ResultCode.FAIL.equals(taskExecute.getResultCode())){
					//logger.info("error taskId : "+taskExecute.getUniqueId());
					logger.info("error description : "+taskExecute.getResultDesc());
					error++;
					setResultFailed(infBatchResponse);
				}else if(null!=taskExecute&&InfBatchConstant.ResultCode.WARNING.equals(taskExecute.getResultCode())){
					warning++;
					logger.info("warning description : "+taskExecute.getResultDesc());
				}else{
					success++;
				}
			}
		}else{
			logger.warn("no task execute");
		}
		//infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		if(InfBatchUtil.empty(infBatchResponse.getResultCode()) || !InfBatchConstant.ResultCode.FAIL.equals(infBatchResponse.getResultCode())){
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		}
		infBatchResponse.setTotalTask(taskExecutes.size());
		infBatchResponse.setTotalSuccess(success);
		infBatchResponse.setTotalFail(error);
		infBatchResponse.setTotalWarning(warning);
		infBatchResponse.setTotalValid(success + error);
	}
	private void setResultFailed(InfBatchResponseDataM infBatchResponse){
		if(!InfBatchConstant.ResultCode.FAIL.equals(infBatchResponse.getResultCode())){
			infBatchResponse.setResultCode(InfBatchConstant.ResultCode.FAIL);
		}
	}
}
