package com.eaf.inf.batch.ulo.notification.memo.dao;

import com.eaf.inf.batch.ulo.notification.memo.model.InstructionMemoVariableDataM;

public interface InstructionMemoTemplateDAO {
	public InstructionMemoVariableDataM getVariable(String applicationGroupId) throws Exception;
}
