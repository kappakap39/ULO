package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.orig.ulo.app.model.ApplicationDataM;

public interface InfBatchOrigApplicationDAO{
	public ArrayList<ApplicationDataM> loadApplicationsForGroup(String applicationGroupId, Connection conn)throws InfBatchException;
}