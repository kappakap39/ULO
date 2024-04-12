package com.ava.dynamic.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ava.dynamic.model.widget.TeamPerformanceDetail;

@Repository
public class TeamPerformanceDAOImp implements TeamPerformanceDAO {
	static final transient Logger log = LogManager.getLogger(TeamPerformanceDAOImp.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public TeamPerformanceDAOImp(@Value("#{uloDatasource}")DataSource uloDatasource) {
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(uloDatasource);
	}

	@Override
	public List<TeamPerformanceDetail> getTeamPerformance() {
		String sql = "SELECT USER_ID as userId," +
				" USER_NAME as name," +
				" APP_INPUT as input," +
				" APP_ASSIGN as assign," +
				" APP_OUTPUT as output," +
				" APP_TARGET as target," +
				" PERFORMANCE as performance," +
				" AVG_WORKING_TIME as averageWorkingTime," +
				" ON_OFF_FLAG as status," +
				" CREATE_DATE as CREATE_DATE," +
				" TEAM_NAME as teamName FROM DHB_STAFF_PERFORMANCE";
		List<TeamPerformanceDetail> teams = this.namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(TeamPerformanceDetail.class));
		log.debug("getTeamPerformance list : "+(teams == null? null : teams.toString()));
		return teams;
	}
	@Override
	public List<TeamPerformanceDetail> getTeamPerformance(String teamId) {
		String sql = "SELECT USER_ID as userId," +
				" USER_NAME as name," +
				" APP_INPUT as input," +
				" APP_ASSIGN as assign," +
				" APP_OUTPUT as output," +
				" APP_TARGET as target," +
				" PERFORMANCE as performance," +
				" AVG_WORKING_TIME as averageWorkingTime," +
				" ON_OFF_FLAG as status," +
				" CREATE_DATE as CREATE_DATE," +
				" TEAM_NAME as teamName FROM DHB_STAFF_PERFORMANCE" +
				" WHERE STATUS = 'A' ";
		
		Map<String, Object> params = null;
		if(teamId != null && !teamId.isEmpty()){
			sql += " and TEAM_NAME = :team_name";
			params = new HashMap<String, Object>();
			params.put("team_name", teamId);
		}

		List<TeamPerformanceDetail> teams = this.namedParameterJdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(TeamPerformanceDetail.class));
		log.debug("getTeamPerformance list size "+(teams == null? null : teams.size()));
		return teams;
	}

}
