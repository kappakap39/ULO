package com.ava.dynamic.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ava.dynamic.model.MenuTeam;

@Repository
public class MenuDAOImp implements MenuDAO{
	static final transient Logger log = LogManager.getLogger(MenuDAO.class);
	private NamedParameterJdbcTemplate uloNamedParameterJdbcTemplate;
	
	@Autowired
	public MenuDAOImp(@Value("#{uloDatasource}")DataSource uloDatasource){//Refer to DatabaseConfig.java
		uloNamedParameterJdbcTemplate = new NamedParameterJdbcTemplate(uloDatasource);
	}
	@Override
	public List<MenuTeam> loadTeamByUsername(String username, String teamname) throws Exception{
		if(username == null && teamname == null){
			throw new IllegalArgumentException("Either username or teamname is not presented!");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT MTE.TEAM_ID,MTE.TEAM_NAME " +
				" FROM MS_USER_TEAM MT JOIN MS_TEAM MTE ON MT.TEAM_ID = MTE.UNDER");
		
//		Map<String, String> paramMap = new HashMap<String,String>();
//		if(username != null ){
//			sql.append(" WHERE MT.USER_ID = '"+username+"'");
//		}
//		else{
			sql.append(" WHERE MT.TEAM_ID = '"+teamname+"'");
//		}
			log.debug("Team ID = "+teamname+" , SQL = "+sql.toString());
		List<MenuTeam> result = uloNamedParameterJdbcTemplate.query(sql.toString(),new RowMapper<MenuTeam>(){
			@Override
			public MenuTeam mapRow(ResultSet rs, int row) throws SQLException {				
				MenuTeam result = new MenuTeam();
				result.setTeamId(rs.getString("TEAM_ID"));
				result.setTeamName(rs.getString("TEAM_NAME"));
				return result;
			}			
		});
		
		return result;
	}
	
	@Override
	public List<MenuTeam> loadChildrenTeamByTeamId(String teamId) throws Exception{
		if(teamId == null){
			throw new IllegalArgumentException("Teamname is not presented!");
		}
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T.TEAM_ID, T.TEAM_NAME FROM MS_TEAM T WHERE T.UNDER = '"+teamId+"'");

		log.debug("Team ID = "+teamId+" , SQL = "+sql.toString());
		List<MenuTeam> result = uloNamedParameterJdbcTemplate.query(sql.toString(),new RowMapper<MenuTeam>(){
			@Override
			public MenuTeam mapRow(ResultSet rs, int row) throws SQLException {				
				MenuTeam result = new MenuTeam();
				result.setTeamId(rs.getString("TEAM_ID"));
				result.setTeamName(rs.getString("TEAM_NAME"));
				return result;
			}			
		});
		
		return result;
	}
}
