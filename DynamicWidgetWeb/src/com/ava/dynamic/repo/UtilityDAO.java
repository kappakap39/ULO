package com.ava.dynamic.repo;

import com.ava.dynamic.exception.DashboardException;

public interface UtilityDAO {

	void refreshDashBoardData(String dhbType) throws DashboardException;
	void refreshTeamPerformanceData(String typeClass) throws DashboardException;
	
}
