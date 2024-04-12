package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationDataM;

public interface OrigApplicationDAO {	
	public void createOrigApplicationM(ApplicationDataM applicationDataM)throws ApplicationException;
	public void deleteOrigApplicationM(ApplicationDataM applicationDataM)throws ApplicationException;
	public ArrayList<ApplicationDataM> loadOrigApplicationM(String applicationGroupId)throws ApplicationException;	 
	public void saveUpdateOrigApplicationM(ApplicationDataM applicationDataM)throws ApplicationException;
	public void saveUpdateOrigApplicationM(ApplicationDataM applicationDataM,Connection conn)throws ApplicationException;
	public void saveUpdateSigleOrigApplicationM(ApplicationDataM applicationDataM)throws ApplicationException;
	public void saveUpdateSigleOrigApplicationM(ApplicationDataM applicationDataM,Connection conn)throws ApplicationException;
	public ArrayList<ApplicationDataM> loadApplicationsForGroup(String applicationGroupId) throws ApplicationException;
	public ArrayList<ApplicationDataM> loadApplicationsForGroup(String applicationGroupId,Connection conn) throws ApplicationException;
	public ArrayList<ApplicationDataM> loadApplicationsVlink(String applicationGroupId,Connection conn) throws ApplicationException;
	public int updateFinalAppDecision(String applicationRecordId, String decision) throws ApplicationException;
//	#rawi comment used updateFinalAppDecision to update ORIG_APPLICATION.FINAL_APP_DECISION_BY
//	public int updateFinalAppDecisionBy(String applicationId, String userName) throws ApplicationException;
	public void deleteNotInKeyApplication(ArrayList<ApplicationDataM> appList,
			String applicationGroupId) throws ApplicationException;
	public boolean isAllCancelled(String applicationId) throws ApplicationException;
	public ArrayList<ApplicationDataM> loadTableORIG_APPLICATION(String applicationGroupId)throws ApplicationException;
	public void setApplicationFinalDecision(ApplicationDataM applicationDataM,ArrayList<String> finalDecision)throws ApplicationException;
	public ArrayList<String> loadApplicationUniqueIds(String applicationGroupId,int lifeCycle)throws ApplicationException;
	public ArrayList<String> loadFinalAppDecision(String applicationGroupId,int lifeCycle)throws ApplicationException;
	public ArrayList<String> loadRefUniqueIds(String applicationGroupId,String applicationRecordId)throws ApplicationException;
	public Date getFinalAppDecisionDate(String applicationGroupId,int lifeCycle)throws ApplicationException;
}
