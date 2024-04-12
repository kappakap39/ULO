package com.eaf.inf.batch.ulo.notification.memo.dao;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.inf.batch.ulo.notification.memo.model.InstructionMemoVariableDataM;

public class InstructionMemoTemplateDAOImpl extends InfBatchObjectDAO implements InstructionMemoTemplateDAO{
	private static transient Logger logger = Logger.getLogger(InstructionMemoTemplateDAOImpl.class);
	@Override
	public InstructionMemoVariableDataM getVariable(String applicationGroupId)throws Exception {
		InstructionMemoVariableDataM variable = new InstructionMemoVariableDataM();
		return variable;
	}
	
}
