package com.eaf.orig.ulo.service.followup.result.dao;

import java.util.ArrayList;
import java.util.List;

import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.DocumentScenarioDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpCSVContentDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.service.common.exception.ServiceControlException;

public interface FollowUpResultDAO {
	public void updateFollowupResultCVSContent(FollowUpCSVContentDataM csvContentDataM,String applicationGroupId)throws ServiceControlException;
	public FollowUpResultApplicationDataM loadApplicationData(String applicationGroupNo)throws ServiceControlException;
	public List<String> selectApplicationRecordIds(String applicationGroupId)throws ServiceControlException;
	public SaleInfoDataM loadSaleInfoByType(String applicationGroupId,String saleType)throws ServiceControlException;
	public ArrayList<DocumentCheckListDataM> selectDocumentCheckListDataM(String applicationGroupId);
	public ArrayList<DocumentScenarioDataM> SelectDocumentScenarioDataM(String applicationGroupId);
}
