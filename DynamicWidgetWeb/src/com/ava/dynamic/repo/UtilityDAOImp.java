package com.ava.dynamic.repo;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ava.dynamic.exception.DashboardException;

@Repository
public class UtilityDAOImp implements UtilityDAO {
	static final transient Logger log = LogManager.getLogger(UtilityDAOImp.class);
	private NamedParameterJdbcTemplate uloNamedParameterJdbcTemplate;
	 
	 @Autowired
		public UtilityDAOImp(@Value("#{uloDatasource}") DataSource uloDatasource) {// Refer to DatabaseConfig.java
			uloNamedParameterJdbcTemplate = new NamedParameterJdbcTemplate(uloDatasource);
		}
	 
	 @Override
	 public void refreshDashBoardData(String dhbType) throws DashboardException{
		 Map<String, Object> paramMap = null;
		 System.out.println("dhbType : "+dhbType);
		 try{
			 if("01".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_01()}", paramMap);
			 }
			 else if("02".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_02()}", paramMap);
			 }
			 else if("03".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_03()}", paramMap);
			 }
			 else if("04".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_04()}", paramMap);
			 }
			 else if("05".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_05_06()}", paramMap);
			 }
			 else if("06".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_05_06()}", paramMap);
			 }
			 else if("07".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_07()}", paramMap);
			 }
			 else if("08".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_08()}", paramMap);
			 }
			 else if("09".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_09()}", paramMap);
			 }
			 else if("10".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_10()}", paramMap);
			 }
			 else if("11".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_11()}", paramMap);
			 }
			 else if("12".equals(dhbType)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_dashboard_12()}", paramMap);
			 }
		 }
		 catch (Exception e){
			 log.error(e.getLocalizedMessage());
			 throw new DashboardException("ERROR 500", e.getLocalizedMessage());
		 }
	 }

	@Override
	public void refreshTeamPerformanceData(String typeClass)
			throws DashboardException {
		Map<String, Object> paramMap = null;
		 try{
			 if("TeamPerformance".equals(typeClass)){
				 uloNamedParameterJdbcTemplate.update("{call PKA_DASHBOARD.p_gen_staff_perf_dashboard()}", paramMap);
			 }
		 }
		 catch (Exception e){
			 log.error(e.getLocalizedMessage());
			 throw new DashboardException("ERROR 500", e.getLocalizedMessage());
		 }
	}
}
