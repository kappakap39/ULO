package com.eaf.orig.ulo.service.ejb.view;

import java.util.ArrayList;
import java.util.HashMap;

import javax.ejb.EJBException;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.service.module.model.FullFraudInfoDataM;

public interface ServiceCenterManager {
	public void saveApplication(ApplicationGroupDataM applicationGroup,String userId,boolean updateDM);
	public void updateImageDocument(ApplicationGroupDataM applicationGroup,String userId,boolean updateDM);
	public void cancelApplication(String applicationGroupId,ArrayList<String> cancelUniqueIds,ApplicationReasonDataM applicationReason,String userId);
	public void savePersonalCisData(PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields,String applicationGroupId,int lifeCycle, String userId);
	public void savePersonalCisFailed(PersonalInfoDataM personalInfo,String applicationGroupId,int lifeCycle, String userId);
	public void saveApplicationAndLoanSetup(ApplicationGroupDataM applicationGroup, String userId, boolean updateDM, FullFraudInfoDataM fullFraudInfo) throws EJBException;
	public void reprocessApplication(ApplicationGroupDataM applicationGroup, String username, String userRole, String logMessage);
}
