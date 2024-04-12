package com.eaf.orig.ulo.app.view.util.report.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.view.util.report.model.InfReportJobDataM;

public class ReportDAOImpl extends OrigObjectDAO implements ReportDAO{
	private static transient Logger logger = Logger.getLogger(ReportDAOImpl.class);
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	
	@Override
	public void insertReportIntoTable(InfReportJobDataM infReportJob) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO INF_REPORT_JOB( ");
			sql.append(" REPORT_JOB_ID, ");
			sql.append(" DATE_TYPE, DATE_FROM, DATE_TO, PROJECT_CODE, PRODUCT_CRITERIA, ");
			sql.append(" BRANCH_REGION, BRANCH_ZONE, DSA_ZONE, NBD_ZONE, CHANNEL, ");
			sql.append(" CREATE_BY, GENERATE_FLAG, CREATE_DATE, REPORT_TYPE, ");
			sql.append(" DOC_FIRST_COMPLETE_FLAG, STATION_FROM, STATION_TO, APPLICATION_STATUS ");
			sql.append(" )VALUES(");
			sql.append(" ?, ");
			sql.append(" ?, ?, ?, ?, ?, ");
			sql.append(" ?, ?, ?, ?, ?, ");
			sql.append(" ?, 'N', SYSDATE, ?, ");
			sql.append(" ?, ?, ?, ? ");
			sql.append(" ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setString(parameterIndex++, getSequence("REPORT_JOB_ID_PK"));
			ps.setString(parameterIndex++, infReportJob.getDateType());
			ps.setDate(parameterIndex++, parseThaiDateToEngDate(infReportJob.getDateFrom()));
			ps.setDate(parameterIndex++, parseThaiDateToEngDate(infReportJob.getDateTo()));
			ps.setString(parameterIndex++, infReportJob.getProjectCode());
			ps.setString(parameterIndex++, infReportJob.getProductCriteria());
			ps.setString(parameterIndex++, infReportJob.getBranchRegion());
			ps.setString(parameterIndex++, infReportJob.getBranchZone());
			ps.setString(parameterIndex++, infReportJob.getDsaZone());
			ps.setString(parameterIndex++, infReportJob.getNbdZone());
			ps.setString(parameterIndex++, infReportJob.getChannel());
			ps.setString(parameterIndex++, infReportJob.getCREATE_BY());
			ps.setString(parameterIndex++, infReportJob.getReportType());
			ps.setString(parameterIndex++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameterIndex++, infReportJob.getStationFrom());
			ps.setString(parameterIndex++, infReportJob.getStationTo());
			ps.setString(parameterIndex++, infReportJob.getApplicationStatus());
			
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	public String getSequence(String sequenceName){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sequenceVal = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT "+sequenceName+".NEXTVAL FROM DUAL ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				sequenceVal = rs.getString("NEXTVAL");
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);

		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return sequenceVal;
	}

	public Date parseThaiDateToEngDate(String thaiDate){
		Date oracleDateFormat = null;
		try{
			if(!Util.empty(thaiDate)){
				Calendar engDate = Calendar.getInstance();
				int year = Integer.parseInt(thaiDate.substring(6,thaiDate.length()))-543;
				int month = Integer.parseInt(thaiDate.substring(3,5));
				int day = Integer.parseInt(thaiDate.substring(0,2));
				engDate.set(year,month-1,day,0,0,0);
				oracleDateFormat = new Date(engDate.getTime().getTime());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return oracleDateFormat;
	}

	@Override
	public ArrayList<InfReportJobDataM> getReportData(String reportType) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<InfReportJobDataM> resultList = new ArrayList<>();
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT INF_REPORT_JOB.* ");
			sql.append(" FROM INF_REPORT_JOB ");
			sql.append(" WHERE REPORT_TYPE = ? ");
			sql.append(" AND (TO_CHAR(CREATE_DATE,'DD-MM-YYYY') = TO_CHAR(?,'DD-MM-YYYY') ");
			sql.append("		OR TO_CHAR(CREATE_DATE,'DD-MM-YYYY') = TO_CHAR(?-1,'DD-MM-YYYY')) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			logger.debug("reportType : "+reportType);
			ps.setString(parameterIndex++, reportType);
			ps.setDate(parameterIndex++, ApplicationDate.getDate());
			ps.setDate(parameterIndex++, ApplicationDate.getDate());
			rs = ps.executeQuery();
			while(rs.next()){
				InfReportJobDataM irj = new InfReportJobDataM();
				irj.setDateType(rs.getString("DATE_TYPE"));
				irj.setDateFrom(FormatUtil.display(rs.getDate("DATE_FROM"), FormatUtil.TH, FormatUtil.Format.ddMMyyyy));
				irj.setDateTo(FormatUtil.display(rs.getDate("DATE_TO"), FormatUtil.TH, FormatUtil.Format.ddMMyyyy));
				irj.setDocFirstCompleteFlag(rs.getString("DOC_FIRST_COMPLETE_FLAG"));
				irj.setProjectCode(rs.getString("PROJECT_CODE"));
				irj.setProductCriteria(rs.getString("PRODUCT_CRITERIA"));
				irj.setBranchRegion(rs.getString("BRANCH_REGION"));
				irj.setBranchZone(rs.getString("BRANCH_ZONE"));
				irj.setDsaZone(rs.getString("DSA_ZONE"));
				irj.setNbdZone(rs.getString("NBD_ZONE"));
				irj.setChannel(rs.getString("CHANNEL"));
				irj.setCREATE_BY(rs.getString("CREATE_BY"));
				irj.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				
				irj.setStationFrom(rs.getString("STATION_FROM"));
				irj.setStationTo(rs.getString("STATION_TO"));
				
				Date CreateDaye = rs.getDate("CREATE_DATE");
				String formatCreateDate = FormatUtil.display(CreateDaye, FormatUtil.TH, FormatUtil.Format.ddMMyyyy);
				
				irj.setCREATE_DATE(formatCreateDate);
				resultList.add(irj);				
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}		
		return resultList;
	}
}
