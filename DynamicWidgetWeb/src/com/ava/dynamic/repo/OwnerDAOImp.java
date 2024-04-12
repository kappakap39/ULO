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

import com.ava.dynamic.model.Owner;

@Repository
public class OwnerDAOImp implements OwnerDAO{
	static final transient Logger log = LogManager.getLogger(OwnerDAOImp.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private NamedParameterJdbcTemplate uloNamedParameterJdbcTemplate;
	
	@Autowired
	public OwnerDAOImp(@Value("#{widgetDatasource}") DataSource widgetDatasource, @Value("#{uloDatasource}")DataSource uloDatasource){//Refer to DatabaseConfig.java
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
		uloNamedParameterJdbcTemplate = new NamedParameterJdbcTemplate(uloDatasource);
	}
	
	@Override
	public Owner getOwnerById(String userId) {
		String sql = "SELECT U.USER_ID, U.POSITION_ID, U.TEAM_ID, T.TEAM_NAME, (SELECT D.FIRSTNAME||' '||D.LASTNAME FROM US_USER_DETAIL D WHERE D.USER_NAME = U.USER_ID) NAME FROM MS_USER_TEAM U JOIN MS_TEAM T ON U.TEAM_ID = T.TEAM_ID WHERE UPPER(U.USER_ID) = UPPER(:userId) ORDER BY U.POSITION_ID FETCH FIRST ROW ONLY ";
		Map<String, String> params = new HashMap<String, String>();
		params.put("userId", userId);	
		Owner result = null;
		try {
			log.debug("sql = "+sql);
			result = uloNamedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Owner.class));
			if(result != null){
//				result.setOtherTeamMember(getOtherTeamMembers(result.getPositionId(), userId));//Move to Business Logic
				result.setDefaultGridId(getGridCodeByPositionLevel(result.getPositionId()));
			}
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		return result;
	}
	
	@Override
	public List<Owner> getOtherTeamMembers(String positionId, String userId){
		String sql = "SELECT U.USER_NAME USER_ID, (SELECT D.FIRSTNAME||' '||D.LASTNAME FROM US_USER_DETAIL D WHERE D.USER_NAME = U.USER_NAME) NAME FROM USER_DELEGATE U, APPLICATION_DATE A, USER_DELEGATE_DETAIL D WHERE UPPER(D.DELEGATE_USER_NAME) = UPPER(:userId) AND A.APP_DATE BETWEEN trunc(U.START_DATE) AND trunc(U.END_DATE)+.99999 AND U.DELEGATE_ID = D.DELEGATE_ID";
		Map<String, String> params = new HashMap<String, String>();
//		params.put("positionId", positionId);
		params.put("userId", userId);
		List<Owner> result = null;
		
		try {
			log.debug("sql = "+sql);
			result = uloNamedParameterJdbcTemplate.query(sql, params, BeanPropertyRowMapper.newInstance(Owner.class));
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		return result;
	}
	
	public String getGridCodeByPositionLevel(String positionLevelId){
		String sql = "SELECT CODE FROM GRID WHERE POSITION_LEVEL = :positionLevelId AND CHILD_ID IS NOT NULL";
		Map<String, String> params = new HashMap<String, String>();
		params.put("positionLevelId", positionLevelId);
		
		String result = null;
		try {
			log.debug("sql = "+sql);
//			result = namedParameterJdbcTemplate.query(sql, params, new ResultSetExtractor<String>(){
//				@Override
//				public String extractData(ResultSet arg0) throws SQLException, DataAccessException {
//					return arg0.next()?arg0.getString("CODE"):null;
//				}				
//			});
			result = namedParameterJdbcTemplate.queryForObject(sql, params, String.class);
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		return result;
	}

	@Override
	public String getTeamNameByID(String teamId) {
		String sql = "SELECT TEAM_NAME FROM MS_TEAM WHERE TEAM_ID = :teamId";
		Map<String, String> params = new HashMap<String, String>();
		params.put("teamId", teamId);
		
		String result = null;
		try {
			log.debug("sql = "+sql);
			result = uloNamedParameterJdbcTemplate.queryForObject(sql, params, String.class);
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		return result;
	}
	
	@Override
	public Owner getDelegateOwnerByTeamId(String teamId) {
		String sql = "SELECT U.USER_ID, U.POSITION_ID, U.TEAM_ID, T.TEAM_NAME FROM MS_USER_TEAM U JOIN MS_TEAM T ON U.TEAM_ID = T.TEAM_ID WHERE U.TEAM_ID  = :teamId AND ROWNUM = 1";
		Map<String, String> params = new HashMap<String, String>();
		params.put("teamId", teamId);
		log.debug("TeamId : "+teamId+", sql = "+sql);
		Owner result = null;
		try {			
			result = uloNamedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Owner.class));
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		return result;
	}
	
	@Override
	public Owner getDelegateOwnerByTeamId(String teamId, String positionId) {
		String sql = "SELECT U.USER_ID, U.POSITION_ID, U.TEAM_ID, T.TEAM_NAME FROM MS_USER_TEAM U JOIN MS_TEAM T ON U.TEAM_ID = T.TEAM_ID WHERE U.TEAM_ID  = :teamId AND POSITION_ID = :position_id AND ROWNUM = 1";
		Map<String, String> params = new HashMap<String, String>();
		params.put("teamId", teamId);
		params.put("position_id", positionId);
		log.debug("TeamId : "+teamId+", PostionID : "+positionId+", sql = "+sql);
		Owner result = null;
		try {			
			result = uloNamedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Owner.class));
		} catch (Exception e) {
			log.error("Error : " + e.getLocalizedMessage());
		}
		return result;
	}

}
