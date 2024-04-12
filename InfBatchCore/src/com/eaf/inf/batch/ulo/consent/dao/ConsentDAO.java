package com.eaf.inf.batch.ulo.consent.dao;

import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.consent.model.GenerateConsentDataM;
import com.eaf.inf.batch.ulo.consent.model.GenerateConsentModelDataM;

public interface ConsentDAO {
	public ArrayList<GenerateConsentDataM> getOutputConsent() throws InfBatchException;
	public void updateConsentGenDate(String ncbDocHistoryId) throws InfBatchException;
	public ArrayList<GenerateConsentModelDataM> getOutputConsentModel() throws InfBatchException;
}
