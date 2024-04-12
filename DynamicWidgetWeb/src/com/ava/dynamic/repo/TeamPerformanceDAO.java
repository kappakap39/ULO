package com.ava.dynamic.repo;

import java.util.List;

import com.ava.dynamic.model.widget.TeamPerformanceDetail;

public interface TeamPerformanceDAO {
	List<TeamPerformanceDetail> getTeamPerformance();

	List<TeamPerformanceDetail> getTeamPerformance(String teamname);
}
