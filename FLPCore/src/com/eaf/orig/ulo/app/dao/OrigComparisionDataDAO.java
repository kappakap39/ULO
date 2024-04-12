package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public interface OrigComparisionDataDAO {
	public void saveUpdateOrigComparisionData(CompareDataM compareData) throws ApplicationException;	
	public void saveUpdateOrigComparisionData(ArrayList<ComparisonGroupDataM> comparisonGroups,String applicationGroupId,int lifeCycle,Connection conn) throws ApplicationException;
	public void saveUpdateOrigComparisionData(HashMap<String, CompareDataM> comparisonFields,String applicationGroupId,int lifeCycle,Connection conn) throws ApplicationException;
	public ComparisonGroupDataM loadComparisonGroup(String applicationGroupId,String srcOfData)throws ApplicationException;
	public Map<String,ComparisonGroupDataM> loadComparisonGroupData(String applicationGroupId,String roleId,String prevRoleId,int lifeCycle)throws ApplicationException;
	public ComparisonGroupDataM loadComparisonGroup(String applicationGroupId,String srcOfData,String roleId,int lifeCycle)throws ApplicationException;
	public ArrayList<CompareDataM> loadComparison(String applicationGroupId,String srcOfData,String roleId,int lifeCycle)throws ApplicationException;
	public void deleteComparisonDataForRoleForGroup(String applicationGroupId,String srcOfData,String roleId,int lifeCycle)throws ApplicationException;
	public boolean isContainIASuplementtary(String applicationGroupId,String refLevel,String srcOfData,int lifeCycle)throws ApplicationException;
	public void deleteComparisonDataNotMatchUniqueId(String applicationId,String srcOfData,ArrayList<String> uniqueId,int lifeCycle)throws ApplicationException;
	public void deleteComparisonDataNotMatchCIS_NUMBER(String applicationId,String srcOfData,String REF_ID,int lifeCycle)throws ApplicationException;
	public void deleteOrigComparisionData(String applicationGroupId,String roleId,int lifeCycle)throws ApplicationException;
	public void updateCompareFlag(String srcOfData,HashMap<String,CompareDataM> comparisonFields,Connection conn)throws ApplicationException;
	void deleteOrigComparisionData(String applicationGroupId, String roleId,
			int lifeCycle, Connection conn) throws ApplicationException;
	public void deleteOrigComparisionDataMatchSrc(String applicationGroupId,String srcOfData,int lifeCycle)throws ApplicationException;
	public void deleteOrigComparisionDataMatchSrc(String applicationGroupId,String srcOfData,int lifeCycle,Connection conn)throws ApplicationException;
}
