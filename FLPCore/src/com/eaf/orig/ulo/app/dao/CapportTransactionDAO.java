package com.eaf.orig.ulo.app.dao;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.CapportTransactionDataM;

public interface CapportTransactionDAO {
	public void createCapportTransaction(CapportTransactionDataM capportTranDataM) throws ApplicationException;
	public CapportTransactionDataM loadCapportTransaction(String capportId) throws ApplicationException;
}
