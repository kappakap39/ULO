package com.ava.dynamic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dynamic.exception.DashboardException;
import com.ava.dynamic.repo.UtilityDAO;

@Service
public class DashboardService {
	
	@Autowired
	private UtilityDAO utilityDAO;

	public void refreshDashboardData(String dhbType) throws DashboardException{
		utilityDAO.refreshDashBoardData(dhbType);
	}
	
	public void refreshTeamPerformanceData(String typeClass) throws DashboardException{
		utilityDAO.refreshTeamPerformanceData(typeClass);
	}
}
