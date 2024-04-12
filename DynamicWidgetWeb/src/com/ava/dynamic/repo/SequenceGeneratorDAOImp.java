package com.ava.dynamic.repo;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ava.dynamic.model.Sequence;

@Repository
public class SequenceGeneratorDAOImp implements SeqDAO{
	static final transient Logger log = LogManager.getLogger(GridDAOImp.class);
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Autowired
	public SequenceGeneratorDAOImp(@Value("#{widgetDatasource}")DataSource widgetDatasource){//Refer to DatabaseConfig.java
		namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(widgetDatasource);
	}
	
	@Override
	public Long getNextVal(String seqName) throws Exception{
		String sql = "SELECT "+seqName+".NEXTVAL as longVal FROM DUAL";
		Map<String, String> params = null;
		Sequence seq = namedParameterJdbcTemplate.queryForObject(sql, params, BeanPropertyRowMapper.newInstance(Sequence.class));
		return seq.getLongVal();
	}
	
}
