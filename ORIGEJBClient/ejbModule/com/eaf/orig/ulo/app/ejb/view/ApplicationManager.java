package com.eaf.orig.ulo.app.ejb.view;

import java.util.ArrayList;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationReasonDataM;

public interface ApplicationManager {
	public void saveApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM);
	public void createApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM);
	public void cancelApplication(String applicationGroupId,ArrayList<String> cancelUniqueIds,ApplicationReasonDataM applicationReason,UserDetailM userM);
	public void updateFraudRemark(String applicationGroupId,String fraudRemark,String userId);
	public void reprocessApplication(ApplicationGroupDataM applicationGroup,UserDetailM userM,String logMessage);
}
