package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.VerificationResultDataM;

public interface InfBatchXrulesVerificationResultDAO {
	public VerificationResultDataM loadVerificationResult(String refId,String verLevel,Connection conn)throws InfBatchException;
	public VerificationResultDataM loadVerificationResult(String refId,String verLevel)throws InfBatchException;
}
