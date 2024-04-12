package com.eaf.inf.batch.ulo.letter.reject.dao;

public class RejectLetterFactory {
	public  static RejectLetterDAO getRejectLetterDAO() {
		return new RejectLetterDAOImpl();
	}
	
	public  static RejectLetterPDFDAO getRejectLetterPDFDAO() {
		return new RejectLetterPDFDAOImpl();
	}
}
