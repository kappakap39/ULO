package com.eaf.orig.ulo.service.app.submit.dao;

import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SubmitApplicationManager {
	public static String selectApplicationGroupId(String qr2) throws ApplicationException{
		return ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByApplicationGroupNo(qr2);
	}	
	public static ApplicationGroupDataM loadApplicationGroup(String applicationGroupId) throws ApplicationException{
		OrigApplicationGroupDAO  origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
		return origApplicationGroup.loadOrigApplicationGroupM(applicationGroupId);
	}
	public static ApplicationGroupDataM loadSingleApplicationGroup(String qr2) throws ApplicationException{
		OrigApplicationGroupDAO  origApplicationGroup = ORIGDAOFactory.getApplicationGroupDAO();
		return origApplicationGroup.loadSingleApplicationGroupByApplicationGroupNo(qr2);
	}
	public static ArrayList<PersonalInfoDataM> loadPersonalInfo(String applicationGroupId) throws ApplicationException{
		return ORIGDAOFactory.getPersonalInfoDAO().loadOrigPersonalInfoM(applicationGroupId);
	}	
}
