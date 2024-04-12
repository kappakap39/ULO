package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.PersonalRelationDataM;

public interface InfBatchOrigPersonalRelationDAO {
	public ArrayList<PersonalRelationDataM> loadOrigPersonalRelationM(String personalId, Connection conn) throws InfBatchException;
}
