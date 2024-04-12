package com.eaf.inf.batch.ulo.notification.memo.dao;

public class InstructionMemoTemplateDAOFactory {
	public static InstructionMemoTemplateDAO getInstructionMemoTemplateDAO(){
		return new InstructionMemoTemplateDAOImpl();
	}
}
