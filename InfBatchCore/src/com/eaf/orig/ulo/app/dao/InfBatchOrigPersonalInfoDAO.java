package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.PersonalInfoDataM;

public interface InfBatchOrigPersonalInfoDAO {
	public ArrayList<PersonalInfoDataM> loadOrigPersonalInfo(String applicationGroupId, Connection conn)throws InfBatchException;
}
