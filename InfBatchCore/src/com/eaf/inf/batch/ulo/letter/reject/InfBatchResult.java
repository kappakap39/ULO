package com.eaf.inf.batch.ulo.letter.reject;

import com.eaf.core.ulo.common.model.InfBatchResponseDataM;
import com.eaf.core.ulo.common.task.model.ProcessTaskDataM;

public interface InfBatchResult {
	void setExecuteTaskResultData(InfBatchResponseDataM infBatchResponse,ProcessTaskDataM processTask);
}
