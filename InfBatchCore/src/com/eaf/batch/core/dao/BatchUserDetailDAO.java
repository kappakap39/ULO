package com.eaf.batch.core.dao;

import java.sql.Connection;
import java.util.List;

import com.eaf.core.ulo.common.exception.InfBatchException;

public interface BatchUserDetailDAO {

	public String getGeneralParam(String paramId) throws InfBatchException;
	public List<String> getexpireUsers(String USERDETAIL_DATE_EXPIRE) throws InfBatchException;
	public void deleteOrganizationChart (String userName)throws InfBatchException;
	public void deleteUserProfile(String userName,Connection conn)throws InfBatchException;
	public void deleteUserLeave(String userName,Connection conn)throws InfBatchException;
	public void deleteUserPerformance(String userName,Connection conn)throws InfBatchException;
	public void deleteUserTeam(String userName,Connection conn)throws InfBatchException;
	public void deleteUserRole(String userName)throws InfBatchException;
	public void deleteUserDetail(String userName)throws InfBatchException;
	public void deleteIasUser(String userName)throws InfBatchException;
	
}
