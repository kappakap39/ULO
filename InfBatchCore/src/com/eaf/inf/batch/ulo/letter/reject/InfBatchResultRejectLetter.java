package com.eaf.inf.batch.ulo.letter.reject;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;

public class InfBatchResultRejectLetter extends InfBatchResultHelper {
	private static transient Logger logger = Logger.getLogger(InfBatchResultRejectLetter.class);
	
	@Override
	public void setExecuteTaskResultData(InfBatchResponseDataM infBatchResponse, ProcessTaskDataM processTask) {
		ArrayList<TaskExecuteDataM> taskExecutes = processTask.getTaskExecutes();
		if(!InfBatchUtil.empty(taskExecutes)){
			for(TaskExecuteDataM taskExecute : taskExecutes){
				if(null!=taskExecute&&InfBatchConstant.ResultCode.FAIL.equals(taskExecute.getResultCode())){
					//logger.info("error taskId : "+taskExecute.getUniqueId());
					logger.info("error description : "+taskExecute.getResultDesc());
				}else if(null!=taskExecute&&InfBatchConstant.ResultCode.WARNING.equals(taskExecute.getResultCode())){
					logger.info("warning description : "+taskExecute.getResultDesc());
				}
			}
		}else{
			logger.warn("no task execute");
		}
		infBatchResponse.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
		infBatchResponse.setTotalTask(infBatchResponse.getTotalTask());
		infBatchResponse.setTotalSuccess(infBatchResponse.getTotalSuccess());
		infBatchResponse.setTotalFail(infBatchResponse.getTotalFail());
		infBatchResponse.setTotalWarning(infBatchResponse.getTotalWarning());
		infBatchResponse.setTotalValid(infBatchResponse.getTotalValid());
	}

}
