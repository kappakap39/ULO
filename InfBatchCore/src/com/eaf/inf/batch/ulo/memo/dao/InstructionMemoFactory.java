package com.eaf.inf.batch.ulo.memo.dao;

public class InstructionMemoFactory {
	public static InstructionMemoDAO getInstructionMemoDAO() {
		return new InstructionMemoDAOImpl();
	}

	public static InstructionMemoCloseDAO getInstructionMemoCloseDAO() {
		return new InstructionMemoCloseDAOImpl();
	}
}
