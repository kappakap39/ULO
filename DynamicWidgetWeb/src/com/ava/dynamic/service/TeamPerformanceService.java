package com.ava.dynamic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ava.dynamic.model.widget.TeamPerformanceDetail;
import com.ava.dynamic.repo.TeamPerformanceDAO;

@Service
public class TeamPerformanceService {
	@Autowired
	TeamPerformanceDAO teamDao;
	public List<TeamPerformanceDetail> getTeamPerformance(){
		return teamDao.getTeamPerformance();
	}
}
