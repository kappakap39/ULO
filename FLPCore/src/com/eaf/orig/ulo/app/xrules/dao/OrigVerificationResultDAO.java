package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public interface OrigVerificationResultDAO {	
	public void createOrigVerificationResultM(VerificationResultDataM verificationResultM)throws ApplicationException;
	public void deleteOrigVerificationResultM(String refId, String verLevel, String verResultId)throws ApplicationException;
	public VerificationResultDataM loadOrigVerificationResultM(String refId, String verLevel)throws ApplicationException;
	public VerificationResultDataM loadOrigVerificationResultM(String refId, String verLevel,Connection conn)throws ApplicationException;	 
	public VerificationResultDataM loadVerificationResultDocument(String refId, String verLevel,Connection conn)throws ApplicationException;
	public VerificationResultDataM selectTableXRULES_VERIFICATION_RESULT(String refId, String verLevel) throws ApplicationException;
	public void saveUpdateOrigVerificationResultM(VerificationResultDataM verificationResultM)throws ApplicationException;
	public void saveUpdateOrigVerificationResultM(VerificationResultDataM verificationResultM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyVerificationResult(ArrayList<VerificationResultDataM> verificationResultList,String refId) throws ApplicationException;
	public void createTableXRULES_VERIFICATION_RESULT(VerificationResultDataM verificationResultM) throws ApplicationException;
	public int updateTableXRULES_VERIFICATION_RESULT(VerificationResultDataM verificationResultM) throws ApplicationException;
	void createOrigVerificationResultM(
			VerificationResultDataM verificationResultM, Connection conn)
			throws ApplicationException;
}
