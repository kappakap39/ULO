package com.eaf.orig.rest.controller.smartdata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.eaf.orig.rest.controller.smartdata.model.SMImgM;
import com.eaf.orig.rest.controller.smartdata.model.SMMainM;

@Repository
public class SmartDataDAOImp implements SmartDataDAO {
	static final transient Logger logger = Logger.getLogger(SmartDataDAOImp.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;	
	@Autowired
	public SmartDataDAOImp(@Value("#{smartDataSource}") DataSource smartDataSource){
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(smartDataSource);
	}	
	@Override
	public List<SMImgM> selectImageByTemplateId(String templateId){
		String sql = "SELECT * FROM SM_IMAGE WHERE TEMPLATEID = :TEMPLATEID ORDER BY DOCTYPEID, DOC_TYPE_SEQ, PERSONAL_TYPE";
		logger.debug("SQL : "+sql);		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TEMPLATEID",templateId);
		List<SMImgM> result = namedParameterJdbcTemplate.query(sql,params,BeanPropertyRowMapper.newInstance(SMImgM.class));
		return result;
	}
	
	@Override
	public List<SMMainM> selectFieldByTemplateId(String templateId){
		String sql = "SELECT T1.*,T2.GROUPNAME FROM SM_MAIN T1 LEFT JOIN SM_SUBFORMGROUP T2 ON T2.PKID = T1.SUBFORMID WHERE T1.TEMPLATEID = :TEMPLATEID ORDER BY T1.IMGID";
		logger.debug("SQL : "+sql);		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("TEMPLATEID",templateId);
		List<SMMainM> result = namedParameterJdbcTemplate.query(sql,params,BeanPropertyRowMapper.newInstance(SMMainM.class));
		return result;
	}
}
