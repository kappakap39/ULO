package com.eaf.inf.batch.ulo.letter.approve.dao;

public class ApproveLetterFactory {
	public static ApproveLetterDAO getApproveLetterDAO(){
		return new ApproveLetterDAOImpl();
	}
}
