package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.ApplicationLogDataM;

public interface InfBatchOrigApplicationLogDAO {
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(String applicationGroupId, Connection conn)throws InfBatchException;
	public ArrayList<ApplicationLogDataM> loadOrigApplicationLogM(String applicationGroupId)throws InfBatchException;
}
