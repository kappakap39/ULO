package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.Date;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public interface OrigApplicationGroupDAO {	
	public void createOrigApplicationGroupM(ApplicationGroupDataM applicationGroupDataM)throws ApplicationException;
	public void deleteOrigApplicationGroupM(ApplicationGroupDataM applicationGroupDataM)throws ApplicationException;
	public ApplicationGroupDataM loadOrigApplicationGroupM(String applicationGroupId)throws ApplicationException;
	public ApplicationGroupDataM loadApplicationGroupDocument(String applicationGroupId,String personalId)throws ApplicationException;
	public ApplicationGroupDataM loadApplicationGroup(String applicationGroupId) throws ApplicationException;
	public ApplicationGroupDataM loadSingleOrigApplicationGroupM(String applicationGroupId) throws ApplicationException;
	public void saveUpdateOrigApplicationGroupM(ApplicationGroupDataM applicationGroup)throws ApplicationException;
	public int updatePriorityMode(ApplicationGroupDataM applicationGroupDataM) throws ApplicationException;
	public int updateStatus(ApplicationGroupDataM applicationGroupDataM)throws ApplicationException;
	void createAppGroupToStartFlow(ApplicationGroupDataM appGroup) throws ApplicationException;
	public String getClaimBy(String applicationGroupId);
	public int updateClaimBy(String applicationGroupId, String userName) throws ApplicationException;
	public String getWfState(String applicationGroupId)throws ApplicationException;
	public ApplicationGroupDataM loadApplicationGroupByInstanceId(String instanceId) throws ApplicationException;
	public String getApplicationGroupIdByApplicationGroupNo(String applicationGroupNo) throws ApplicationException;
	public ApplicationGroupDataM loadSingleApplicationGroupByApplicationGroupNo(String applicationGroupNo) throws ApplicationException;
	public String getApplicationGroupIdByQr2(String qr2) throws ApplicationException;
	public String getApplicationGroupNo(String applicationGroupId) throws ApplicationException;
	public int getMaxLifeCycle(String applicationGroupId) throws ApplicationException;
	public int getLifeCycle(String applicationGroupId) throws ApplicationException;
	public String getInstantId(String applicationGroupId) throws ApplicationException;
	public void updateLastDecision(String applicationGroupId, String lastDecision) throws ApplicationException;
	public void updateFraudRemark(String applicationGroupId,String fraudRemark,String userId)throws ApplicationException;
	public String getApplicationGroupIdByInstantId(String instantId) throws ApplicationException;
	public void clearInstantId(String applicationGroupId)throws ApplicationException;
	int setWfState(String applicationGroupId, String wfState)
			throws ApplicationException;
	public ApplicationGroupDataM loadApplicationVlink(String applicationGroupId,Connection conn)throws ApplicationException;
}
