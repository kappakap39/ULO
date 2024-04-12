package com.eaf.orig.ulo.app.view.util.kpl;

import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.orig.profile.model.UserM;
import com.eaf.orig.ulo.model.app.ReasonDataM;

public abstract interface CallCenterKPLDAO
{
	public ArrayList<HashMap<String,Object>> SearchSubSQL(String applicationGroupId,int lifeCycle,String applicationStatus) throws Exception;
	public void saveReason(ReasonDataM reasonDataM , UserM userM) throws Exception;
	public ArrayList<HashMap<String,Object>> SearchSalesSQL(String applicationGroupId) throws Exception;
	public ArrayList<HashMap<String,Object>> SearchDocRequestSQL(String applicationGroupId) throws Exception;
}