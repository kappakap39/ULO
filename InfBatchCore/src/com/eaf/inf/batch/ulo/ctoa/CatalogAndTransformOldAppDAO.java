package com.eaf.inf.batch.ulo.ctoa;

import java.sql.Connection;
import java.util.ArrayList;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public interface CatalogAndTransformOldAppDAO 
{
	public ArrayList<String> loadOldAppGroupToCatalog() throws Exception;
	public ArrayList<String> loadAppToTransform() throws Exception;
	public void transformAppGroup(ArrayList<String> appGroupIdList) throws Exception;
	public ArrayList<ApplicationGroupDataM> loadAppGroupData(ArrayList<String> applicationGroupId) throws Exception;
	public boolean isPendingJobExist() throws Exception;
	public ArrayList<ApplicationGroupDataM> loadAppGroupTransformData(ArrayList<String> applicationGroupIdList) throws ApplicationException;
	public void catalogAppGroup(ArrayList<String> appGroupIdList) throws Exception;
	public void updateArcStatusSL(String value, ArrayList<String> appGroupIdList) throws Exception;
	public void updateArcStatusSL(String value, ArrayList<String> appGroupIdList, String comment) throws Exception;
	public ArrayList<String> getImageSplitHashId(Connection conn, String appGroupId) throws Exception;
}
