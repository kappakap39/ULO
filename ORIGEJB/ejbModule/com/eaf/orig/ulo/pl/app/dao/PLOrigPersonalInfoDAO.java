package com.eaf.orig.ulo.pl.app.dao;

import java.util.Vector;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public interface PLOrigPersonalInfoDAO {
	
	public void saveUpdateOrigPersonalInfo (Vector<PLPersonalInfoDataM> personalVect, String appRecId, PLApplicationDataM appM) throws PLOrigApplicationException;
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo (String appRecId, String busClassId) throws PLOrigApplicationException;
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo (String appRecId) throws PLOrigApplicationException;
	public PLPersonalInfoDataM loadPersonalMForNCB (String appRecId) throws PLOrigApplicationException;
	public PLPersonalInfoDataM LoadPersonalInfoModel(String idNo, String customerType) throws PLOrigApplicationException;
	public PLPersonalInfoDataM loadPersonalM (String appRecId) throws PLOrigApplicationException;
	public Vector<PLPersonalInfoDataM> loadOrigPersonalInfo_IncreaseDecrease(String appRecId, PLApplicationDataM currentAppM)throws PLOrigApplicationException;
	public void SaveUpdateNcbORIGPersonalInfo(Vector<PLPersonalInfoDataM> personalVect, String appRecId)throws PLOrigApplicationException;
	public void insertTableOrig_Personal_Info(PLPersonalInfoDataM personalM, String appRecId) throws PLOrigApplicationException;
	public int updateTableOrig_Personal_Info(PLPersonalInfoDataM personalM, String appRecId) throws PLOrigApplicationException;
	public Vector<PLPersonalInfoDataM> ImageORIGPersonalInfo (String appRecID) throws PLOrigApplicationException;
}
