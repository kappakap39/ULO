package com.eaf.inf.batch.ulo.fraudresult.dao;

import java.sql.Connection;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public interface FraudResultDAO {
	public void updateApplicationStatus(String applicationGroupId,String applicationRecordId,String finalDecision,String finalDecisionBy,String finalDecisionDate,String vetoEligible,Connection conn) throws InfBatchException;
	public String selectApplicationGroupId(String applicationGroupNo,Connection conn) throws InfBatchException;
	public ApplicationGroupDataM loadApplicationGroup(String applicationGroupId) throws InfBatchException;
	public void saveApplication(ApplicationGroupDataM applicationGroup,String userId) throws InfBatchException;
	public String selectJobStateByApplicationGroupNo(String applicationGroupNo) throws InfBatchException;
}
