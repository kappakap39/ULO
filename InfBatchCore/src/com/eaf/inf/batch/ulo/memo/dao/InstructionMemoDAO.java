package com.eaf.inf.batch.ulo.memo.dao;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoDataM;

public interface InstructionMemoDAO {
    InstructionMemoDataM getInstructionMemo(String applicationGroupId) throws InfBatchException;
}
