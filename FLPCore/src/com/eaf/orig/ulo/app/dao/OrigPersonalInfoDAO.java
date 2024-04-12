package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public interface OrigPersonalInfoDAO {
	
	public void createOrigPersonalInfoM(PersonalInfoDataM personalInfoM)throws ApplicationException;
	public void deleteOrigPersonalInfoM(String applicationGroupId, String personalId)throws ApplicationException;	
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoM(String applicationGroupId)throws ApplicationException;	
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoM(String applicationGroupId,Connection conn)throws ApplicationException;
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoVlink(String applicationGroupId,Connection conn)throws ApplicationException;
	public ArrayList<PersonalInfoDataM> loadPersonalInfoDocument(String applicationGroupId,String personalId,Connection conn)throws ApplicationException;
	public void saveUpdateOrigPersonalInfoM(PersonalInfoDataM personalInfoM)throws ApplicationException;
	public void saveUpdateOrigPersonalInfoM(PersonalInfoDataM personalInfoM,Connection conn)throws ApplicationException;
	public void deleteNotInKeyPersonalInfo(ArrayList<PersonalInfoDataM> personalInfoList, String applicationGroupId) throws ApplicationException;
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfoForCis(String applicationGroupId)throws ApplicationException;
	public void saveUpdateOrigPersonalInfoForCis(PersonalInfoDataM personalInfo)throws ApplicationException;
	public void saveUpdateCis(PersonalInfoDataM personalInfo,HashMap<String,CompareDataM> comparisonFields,String applicationGroupId,String srcOfData,int lifeCycle)throws ApplicationException;
	public void saveUpdateCisFailed(PersonalInfoDataM personalInfo,String applicationGroupId,String srcOfData,int lifeCycle)throws ApplicationException;
}
