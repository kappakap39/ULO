package com.eaf.inf.batch.ulo.report.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.report.model.AuditTrailDataM;
import com.eaf.inf.batch.ulo.report.model.InfReportJobDataM;
import com.eaf.inf.batch.ulo.report.model.OperatingResultDataM;
import com.eaf.inf.batch.ulo.report.model.OverrideResultDataM;
import com.eaf.inf.batch.ulo.report.model.PeriodDataM;
import com.eaf.inf.batch.ulo.report.model.ProcessingTimeDataM;
import com.eaf.inf.batch.ulo.report.model.SlaDataM;

public class ReportFileDAOImp extends InfBatchObjectDAO implements ReportFileDAO{
	private static transient Logger logger = Logger.getLogger(ReportFileDAOImp.class);
	
	public Date convertDateFormat(String date){
		Date oracleDateFormat = null;
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			java.util.Date d = sdf.parse(date);
			sdf.applyPattern("yyyy-MM-dd");
			date = sdf.format(d);
			oracleDateFormat = Date.valueOf(date);
		}catch(Exception e){
			logger.fatal("system can not to convert date");
		}
		
		return oracleDateFormat;
	}
	
	public String getSequence(String sequenceName){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sequenceVal = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
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

	@Override
	public void insertReportIntoTable(InfReportJobDataM infReportJob ) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
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
			ps.setDate(parameterIndex++, convertDateFormat(infReportJob.getDateFrom()));
			ps.setDate(parameterIndex++, convertDateFormat(infReportJob.getDateTo()));
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
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public int getInfReportJob(String reportType) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int reportJob = 0;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) COUNT FROM INF_REPORT_JOB ");
			sql.append(" WHERE REPORT_TYPE = ? ");
			sql.append(" AND TO_CHAR(CREATE_DATE,'DD-MM-YYYY') = TO_CHAR(?,'DD-MM-YYYY') ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameterIndex = 1;
			ps.setString(parameterIndex++, reportType);
			ps.setDate(parameterIndex++, InfBatchProperty.getDate());
			rs = ps.executeQuery();
			if(rs.next()){
				reportJob = Integer.parseInt(rs.getString("COUNT"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		} 
		return reportJob;
	}
	@Override
	public List<String> getRegion() throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> regionList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT RGON_CD FROM IP_SHR.VC_EMP ORDER BY RGON_CD ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				regionList.add(FormatUtil.trim(rs.getString("RGON_CD")));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return regionList;
	}

	@Override
	public List<String> getZone(String region) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> zoneList = new ArrayList<>();
		try{
			String[] criterias = StringUtils.split(region,',');
			
			conn = getConnection(InfBatchServiceLocator.DIH);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT ZON_CD FROM IP_SHR.VC_EMP")
				.append(" WHERE ZON_CD IS NOT NULL ");
			if(!InfBatchUtil.empty(region) && !region.equals("ALL")){
				String[] conditionSize = new String[criterias.length];
				String sqlCondition = " AND ( RGON_CD = ? "+StringUtils.join(conditionSize," OR RGON_CD = ? ")+" ) ";
				sql.append(sqlCondition);
			}
			sql.append(" ORDER BY ZON_CD ");
			logger.debug("sql : "+sql);
			
			ps = conn.prepareStatement(sql.toString());
			int indexParameter = 1;
			if(!InfBatchUtil.empty(region) && !region.equals("ALL")){
				for(String criteria : criterias){
					logger.debug("indexParameter >> "+indexParameter);
					logger.debug("criteria >> "+criteria);
					ps.setString(indexParameter++, criteria);
				}
			}
			rs = ps.executeQuery();
			while(rs.next()){
				zoneList.add(FormatUtil.trim(rs.getString("ZON_CD")));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return zoneList;
	}

	@Override
	public List<String> getTeamZone() throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String> teamZoneList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT TEAM_ZONE FROM MS_SALES_INFO ORDER BY TEAM_ZONE ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				teamZoneList.add(rs.getString("TEAM_ZONE"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return teamZoneList;
	}
	
	@Override
	public ArrayList<SlaDataM> getReportSLA1(String P_APP_TYPE) throws InfBatchException {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R006_SLA_01(?)) WHERE PERIOD IS NOT NULL ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, P_APP_TYPE);
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setMonth(rs.getString("PERIOD"));
				reR006Data.setAppIn(rs.getString("APP_IN"));
				
				reR006Data.setCapacitySLA(rs.getString("CAPACITY_SLA"));
				reR006Data.setWipSLA(rs.getString("WIP_SLA"));
				reR006Data.setSlaInfinite(rs.getString("SLA_INFINITE"));
				reR006Data.setSlaWisdom(rs.getString("SLA_WISDOM"));
				reR006Data.setSlaPremier(rs.getString("SLA_PREMIER"));
				reR006Data.setSlaPlatinum(rs.getString("SLA_PLATINUM"));
				reR006Data.setSlaGerneric(rs.getString("SLA_GERNERIC"));
				reR006Data.setSlaKEC(rs.getString("SLA_KEC"));
				reR006Data.setSlaKPL(rs.getString("SLA_KPL"));
				
				reR006Data.setCapacityOLA(rs.getString("CAPACITY_OLA"));
				reR006Data.setWipOLA(rs.getString("WIP_OLA"));
				reR006Data.setOlaInfinite(rs.getString("OLA_INFINITE"));
				reR006Data.setOlaWisdom(rs.getString("OLA_WISDOM"));
				reR006Data.setOlaPremier(rs.getString("OLA_PREMIER"));
				reR006Data.setOlaPlatinum(rs.getString("OLA_PLATINUM"));
				reR006Data.setOlaGerneric(rs.getString("OLA_GERNERIC"));
				reR006Data.setOlaKEC(rs.getString("OLA_KEC"));
				reR006Data.setOlaKPL(rs.getString("OLA_KPL"));
				reR006Data.setPercentSlaTarget(rs.getString("PERCENT_SLA_TARGET"));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}

	@Override
	public ArrayList<SlaDataM> getReportSLA2(String P_APP_TYPE) throws InfBatchException {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R006_SLA_02(?)) WHERE PERIOD IS NOT NULL ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, P_APP_TYPE);
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setPeriod(rs.getString("PERIOD"));
				reR006Data.setNoAppSlaInf(rs.getString("NO_APP_SLA_INF"));
				reR006Data.setTargetSlaInf(rs.getString("TARGET_SLA_INF"));
				reR006Data.setNoAppSlaWis(rs.getString("NO_APP_SLA_WIS"));
				reR006Data.setTargetSlaWis(rs.getString("TARGET_SLA_WIS"));
				reR006Data.setNoAppSlaPre(rs.getString("NO_APP_SLA_PRE"));
				reR006Data.setTargetSlaPre(rs.getString("TARGET_SLA_PRE"));
				reR006Data.setNoAppSlaPlt(rs.getString("NO_APP_SLA_PLT"));
				reR006Data.setTargetSlaPlt(rs.getString("TARGET_SLA_PLT"));
				reR006Data.setNoAppSlaGen(rs.getString("NO_APP_SLA_GEN"));
				reR006Data.setTargetSlaGen(rs.getString("TARGET_SLA_GEN"));
				reR006Data.setNoAppSlaKEC(rs.getString("NO_APP_SLA_KEC"));
				reR006Data.setTargetSlaKEC(rs.getString("TARGET_SLA_KEC"));
				reR006Data.setNoAppSlaKPL(rs.getString("NO_APP_SLA_KPL"));
				reR006Data.setTargetSlaKPL(rs.getString("TARGET_SLA_KPL"));
				reR006Data.setNoAppOlaInf(rs.getString("NO_APP_OLA_INF"));
				reR006Data.setTargetOlaInf(rs.getString("TARGET_OLA_INF"));
				reR006Data.setNoAppOlaWis(rs.getString("NO_APP_OLA_WIS"));
				reR006Data.setTargetOlaWis(rs.getString("TARGET_OLA_WIS"));
				reR006Data.setNoAppOlaPre(rs.getString("NO_APP_OLA_PRE"));
				reR006Data.setTargetOlaPre(rs.getString("TARGET_OLA_PRE"));
				reR006Data.setNoAppOlaPlt(rs.getString("NO_APP_OLA_PLT"));
				reR006Data.setTargetOlaPlt(rs.getString("TARGET_OLA_PLT"));
				reR006Data.setNoAppOlaGen(rs.getString("NO_APP_OLA_GEN"));
				reR006Data.setTargetOlaGen(rs.getString("TARGET_OLA_GEN"));
				reR006Data.setNoAppOlaKEC(rs.getString("NO_APP_OLA_KEC"));
				reR006Data.setTargetOlaKEC(rs.getString("TARGET_OLA_KEC"));
				reR006Data.setNoAppOlaKPL(rs.getString("NO_APP_OLA_KPL"));
				reR006Data.setTargetOlaKPL(rs.getString("TARGET_OLA_KPL"));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}
	
	@Override
	public ArrayList<SlaDataM> getReportSLA3(String P_APP_TYPE) throws InfBatchException {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R006_SLA_03(?)) WHERE PERIOD IS NOT NULL ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, P_APP_TYPE);
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setWipPeriod(rs.getString("PERIOD"));
				reR006Data.setCcInfinite(rs.getString("CC_INFINITE"));
				reR006Data.setCcWisdom(rs.getString("CC_WISDOM"));
				reR006Data.setCcPremier(rs.getString("CC_PREMIER"));
				reR006Data.setCcPlatinum(rs.getString("CC_PLATINUM"));
				reR006Data.setCcGeneric(rs.getString("CC_GENERIC"));
				reR006Data.setCcGrandTotal(rs.getString("CC_GRAND_TOTAL"));
				reR006Data.setKec(rs.getString("KEC"));
				reR006Data.setKpl(rs.getString("KPL"));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}
	

	
	@Override
	public ArrayList<SlaDataM> getReportSLA4(String P_APP_TYPE) throws InfBatchException {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R006_SLA_04(?)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, P_APP_TYPE);
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setSlaType(rs.getString("SLA_TYPE"));
				reR006Data.setE2eAcheive(getIntegerValue(rs.getString("E2E_ACHEIVE")));
				reR006Data.setE2eTarget(getIntegerValue(rs.getString("E2E_TARGET")));
				reR006Data.setE2eCountApp(getIntegerValue(rs.getString("E2E_CNT_APP")));
				
				reR006Data.setLeg1Acheive(getIntegerValue(rs.getString("LEG1_ACHEIVE")));
				reR006Data.setLeg1Target(getIntegerValue(rs.getString("LEG1_TARGET")));
				reR006Data.setLeg1CountApp(getIntegerValue(rs.getString("LEG1_CNT_APP")));
				
				reR006Data.setLeg2Acheive(getIntegerValue(rs.getString("LEG2_ACHEIVE")));
				reR006Data.setLeg2Target(getIntegerValue(rs.getString("LEG2_TARGET")));
				reR006Data.setLeg2CountApp(getIntegerValue(rs.getString("LEG2_CNT_APP")));
				
				reR006Data.setLeg3Acheive(getIntegerValue(rs.getString("LEG3_ACHEIVE")));
				reR006Data.setLeg3Target(getIntegerValue(rs.getString("LEG3_TARGET")));
				reR006Data.setLeg3CountApp(getIntegerValue(rs.getString("LEG3_CNT_APP")));
				
				reR006Data.setLeg4Acheive(getIntegerValue(rs.getString("LEG4_ACHEIVE")));
				reR006Data.setLeg4Target(getIntegerValue(rs.getString("LEG4_TARGET")));
				reR006Data.setLeg4CountApp(getIntegerValue(rs.getString("LEG4_CNT_APP")));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}
	
	private String getIntegerValue(String data){
		String value = "";
		if(!InfBatchUtil.empty(data)){
			value = data;
		}else{
			value = "0";
		}
		return value;
	}

	@Override
	public ArrayList<ProcessingTimeDataM> getReportR012() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProcessingTimeDataM> reR012DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R012_ProcessingTime()) WHERE CARD_TYPE_DESC IS NOT NULL ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				ProcessingTimeDataM reR012Data = new ProcessingTimeDataM();
				reR012Data.setCardTypeDesc(rs.getString("CARD_TYPE_DESC"));
				reR012Data.setRoleName(rs.getString("ROLE_NAME"));
				reR012Data.setStandard(rs.getString("STANDARD"));
				reR012Data.setProcessTime(rs.getString("PROCESS_TIME"));
				reR012Data.setWaitingTime(rs.getString("WAITING_TIME"));
				reR012DataList.add(reR012Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR012DataList;
	}
	
	@Override
	public ArrayList<ProcessingTimeDataM> getReportR012_Top_Bottom(String role, String position) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProcessingTimeDataM> processingTimeList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(PKA_RPT.f_rpt_R012_Process_top_bottom(0, ?, ?)) ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++, role);
			ps.setString(index++, position);
			rs = ps.executeQuery();
			while(rs.next()){
				ProcessingTimeDataM processingTimeM = new ProcessingTimeDataM();
				processingTimeM.setCardTypeDesc(rs.getString("CARD_TYPE_DESC"));
				processingTimeM.setRoleName(rs.getString("ROLE_NAME"));
				processingTimeM.setStandard(rs.getString("STANDARD"));
				processingTimeM.setProcessTime(rs.getString("PROCESS_TIME"));
				processingTimeM.setWaitingTime(rs.getString("WAITING_TIME"));
				processingTimeList.add(processingTimeM);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return processingTimeList;
	}
	
	@Override
	public ArrayList<ProcessingTimeDataM> getReportR012Summary() throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProcessingTimeDataM> processingTimeList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(PKA_RPT.f_rpt_R012_Summary()) ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				ProcessingTimeDataM processingTimeM = new ProcessingTimeDataM();
				processingTimeM.setCardTypeDesc(rs.getString("CARD_TYPE_DESC"));
				processingTimeM.setRoleName(rs.getString("ROLE_NAME"));
				processingTimeM.setStandard(rs.getString("STANDARD"));
				processingTimeM.setProcessTime(rs.getString("PROCESS_TIME"));
				processingTimeM.setWaitingTime(rs.getString("WAITING_TIME"));
				processingTimeList.add(processingTimeM);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return processingTimeList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_01() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_01(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getF_RPT_R004_OR_1_5_PER_PERIOD(String productCode,String reportType,String period,String periodCon,String sortField) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OR_1_5_PER_PERIOD(?,?,?,?,?)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameterPatam=1;
			ps.setString(parameterPatam++, productCode);
			ps.setString(parameterPatam++, reportType);
			ps.setString(parameterPatam++, period);
			ps.setString(parameterPatam++, periodCon);
			ps.setString(parameterPatam++, sortField);
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	
	@Override
	public ArrayList<PeriodDataM> getPeriodDatas(String reportType) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PeriodDataM> results = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ");
			sql.append(" DECODE(LEVEL, 1, 'CC ALL', 2, 'CC-INFINITE', 3, 'CC-WISDOM', 4, 'CC-PREMIER', 5, ");
			sql.append(" 'CC-PLATINUM', 6, 'CC-GENERIC', 7, 'KEC', 8, 'KPL') PERIOD, ");
			sql.append(" DECODE(LEVEL, 1, 'CC%', 2, 'CC_INF%', 3, 'CC_WIS%', 4, 'CC_PRE%', 5, 'CC_PLT%', 6, ");
			sql.append(" 'CC_GEN%', 7, 'KEC%', 8, 'KPL%')    PERIOD_CON, ");
			sql.append(" LEVEL                            AS SORT_FIELD ");
			sql.append(" FROM ");
			sql.append(" DUAL CONNECT BY LEVEL <= ");
			sql.append(" CASE ");
			sql.append("  WHEN ? IN ('3', '4') ");
			sql.append(" THEN 6 ELSE 8 END  ORDER BY SORT_FIELD ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, reportType);
			rs = ps.executeQuery();
			while(rs.next()){
				PeriodDataM result = new PeriodDataM();
				result.setPeriod(rs.getString("PERIOD"));
				result.setPeriodCon(rs.getString("PERIOD_CON"));
				result.setSortField(rs.getString("SORT_FIELD"));
				results.add(result);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return results;
	}
	@Override
	public ArrayList<PeriodDataM> getPeriod68Datas() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PeriodDataM> results = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT ");
			sql.append(" DECODE(LEVEL, 1, 'CC + KEC', 2, 'CC-INFINITE + KEC', 3, 'CC-WISDOM + KEC', 4, ");
			sql.append(" 'CC-PREMIER + KEC', 5, 'CC-PLATINUM + KEC', 6, 'CC-GENERIC + KEC') PERIOD_DESC, ");
			sql.append(" DECODE(LEVEL, 1, 'CC ALL', 2, 'CC-INFINITE', 3, 'CC-WISDOM', 4, 'CC-PREMIER', 5, ");
			sql.append(" 'CC-PLATINUM', 6, 'CC-GENERIC')    PERIOD, ");
			sql.append("  LEVEL                           AS SORT_FIELD ");
			sql.append(" FROM ");
			sql.append(" DUAL CONNECT BY LEVEL <= 6");
		
			sql.append("  ORDER BY SORT_FIELD ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				PeriodDataM result = new PeriodDataM();
				result.setPeriod(rs.getString("PERIOD"));
				result.setPeriodDesc(rs.getString("PERIOD_DESC"));
				result.setSortField(rs.getString("SORT_FIELD"));
				results.add(result);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return results;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_02() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_02(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_03() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_03(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_04() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_04(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_05() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_05(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_06() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_06(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_07() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_07(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_08() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_08(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_09() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_09(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_10() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_10(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_11() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_11(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_12() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_12(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_13() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_13(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_14() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_14(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<OperatingResultDataM> getReportR004_15() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> reR004DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R004_OperatingResult_15(NULL)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM reR004Data = new OperatingResultDataM();
				reR004Data.setPeriod(rs.getString("PERIOD"));
				reR004Data.setCountApprove(rs.getString("COUNT_APPROVE"));
				reR004Data.setCountReject(rs.getString("COUNT_REJECT"));
				reR004Data.setCountCancel(rs.getString("COUNT_CANCEL"));
				reR004Data.setAppIn(rs.getString("APP_IN"));
				reR004Data.setWip(rs.getString("WIP"));
				reR004Data.setWipFollow(rs.getString("WIP_FOLLOW"));
				reR004Data.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				reR004Data.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				reR004Data.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				reR004Data.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				reR004Data.setVetoSubject(rs.getString("VETO_SUBJECT"));
				reR004Data.setVetoPass(rs.getString("VETO_PASS"));
				reR004DataList.add(reR004Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR004DataList;
	}
	
	@Override
	public ArrayList<InfReportJobDataM> getInfReportJob() throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<InfReportJobDataM> infReportJobList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM INF_REPORT_JOB ");
			sql.append(" WHERE GENERATE_FLAG = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, InfBatchConstant.FLAG_NO);
			rs = ps.executeQuery();
			while(rs.next()){
				InfReportJobDataM infReportJob = new InfReportJobDataM();
				infReportJob.setReportJobId(rs.getString("REPORT_JOB_ID"));
				infReportJob.setProjectCode(rs.getString("PROJECT_CODE"));
				infReportJob.setChannel(rs.getString("CHANNEL"));
				infReportJob.setBranchRegion(rs.getString("BRANCH_REGION"));
				infReportJob.setBranchZone(rs.getString("BRANCH_ZONE"));
				infReportJob.setDsaZone(rs.getString("DSA_ZONE"));
				infReportJob.setNbdZone(rs.getString("NBD_ZONE"));
				infReportJob.setDateType(rs.getString("DATE_TYPE"));
				infReportJob.setDateFrom(rs.getString("DATE_FROM"));
				infReportJob.setDateTo(rs.getString("DATE_TO"));
				infReportJob.setProductCriteria(rs.getString("PRODUCT_CRITERIA"));
				infReportJob.setDocFirstCompleteFlag(rs.getString("DOC_FIRST_COMPLETE_FLAG"));
				infReportJob.setStationFrom(rs.getString("STATION_FROM"));
				infReportJob.setStationTo(rs.getString("STATION_TO"));
				infReportJob.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				infReportJob.setReportType(rs.getString("REPORT_TYPE"));
				infReportJobList.add(infReportJob);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return infReportJobList;
	}

	@Override
	public ArrayList<OperatingResultDataM> getOperatingResult(InfReportJobDataM infReportJob) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OperatingResultDataM> operatingResultList = new ArrayList<>(); 
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE( pka_rpt.f_rpt_R004_OperatingResult(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ");
			logger.debug("sql : "+sql);
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			ps.setString(parameter++, infReportJob.getDateType());
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getStationFrom());
			ps.setString(parameter++, infReportJob.getStationTo());
			ps.setString(parameter++, infReportJob.getApplicationStatus());
			rs = ps.executeQuery();
			while(rs.next()){
				OperatingResultDataM operatingResult = new OperatingResultDataM();
				operatingResult.setPeriod(rs.getString("PERIOD"));
				operatingResult.setCountApprove(rs.getString("COUNT_APPROVE"));
				operatingResult.setCountReject(rs.getString("COUNT_REJECT"));
				operatingResult.setCountCancel(rs.getString("COUNT_CANCEL"));
				operatingResult.setAppIn(rs.getString("APP_IN"));
				operatingResult.setWip(rs.getString("WIP"));
				operatingResult.setWipFollow(rs.getString("WIP_FOLLOW"));
				
				operatingResult.setPercentDocComplete(rs.getString("PERCENT_DOC_COMPLETE"));
				operatingResult.setTop5DocNotComplete(rs.getString("TOP_5_DOC_NOT_COMPLETE"));
				operatingResult.setTop5ReasonReject(rs.getString("TOP_5_RESON_REJECT"));
				operatingResult.setTop5CauseNotComplete(rs.getString("TOP_5_CAUSE_NOT_COMPLETE"));
				operatingResult.setVetoSubject(rs.getString("VETO_SUBJECT"));
				operatingResult.setVetoPass(rs.getString("VETO_PASS"));
				
				operatingResultList.add(operatingResult);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return operatingResultList;
	}
	
	@Override
	public ArrayList<SlaDataM> getSLA1(InfReportJobDataM infReportJob) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>(); 
		ResultSet rs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(pka_rpt.f_rpt_R006_SLA1(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			logger.debug("getDateFrom : "+infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			logger.debug("getDateTo : "+infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			int parameter = 1;
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setMonth(rs.getString("PERIOD"));
				reR006Data.setAppIn(rs.getString("APP_IN"));
				
				reR006Data.setWipSLA(rs.getString("WIP_SLA"));
				reR006Data.setCapacitySLA(rs.getString("CAPACITY_SLA"));
				reR006Data.setSlaInfinite(rs.getString("SLA_INFINITE"));
				reR006Data.setSlaWisdom(rs.getString("SLA_WISDOM"));
				reR006Data.setSlaPremier(rs.getString("SLA_PREMIER"));
				reR006Data.setSlaPlatinum(rs.getString("SLA_PLATINUM"));
				reR006Data.setSlaGerneric(rs.getString("SLA_GERNERIC"));
				reR006Data.setSlaKEC(rs.getString("SLA_KEC"));
				reR006Data.setSlaKPL(rs.getString("SLA_KPL"));
				
				reR006Data.setCapacityOLA(rs.getString("CAPACITY_OLA"));
				reR006Data.setWipOLA(rs.getString("WIP_OLA"));
				reR006Data.setOlaInfinite(rs.getString("OLA_INFINITE"));
				reR006Data.setOlaWisdom(rs.getString("OLA_WISDOM"));
				reR006Data.setOlaPremier(rs.getString("OLA_PREMIER"));
				reR006Data.setOlaPlatinum(rs.getString("OLA_PLATINUM"));
				reR006Data.setOlaGerneric(rs.getString("OLA_GERNERIC"));
				reR006Data.setOlaKEC(rs.getString("OLA_KEC"));
				reR006Data.setOlaKPL(rs.getString("OLA_KPL"));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}
	
	@Override
	public ArrayList<SlaDataM> getSLA2(InfReportJobDataM infReportJob) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>(); 
		ResultSet rs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(pka_rpt.f_rpt_R006_SLA2(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setPeriod(rs.getString("PERIOD"));
				reR006Data.setNoAppSlaInf(rs.getString("NO_APP_SLA_INF"));
				reR006Data.setTargetSlaInf(rs.getString("TARGET_SLA_INF"));
				reR006Data.setNoAppSlaWis(rs.getString("NO_APP_SLA_WIS"));
				reR006Data.setTargetSlaWis(rs.getString("TARGET_SLA_WIS"));
				reR006Data.setNoAppSlaPre(rs.getString("NO_APP_SLA_PRE"));
				reR006Data.setTargetSlaPre(rs.getString("TARGET_SLA_PRE"));
				reR006Data.setNoAppSlaPlt(rs.getString("NO_APP_SLA_PLT"));
				reR006Data.setTargetSlaPlt(rs.getString("TARGET_SLA_PLT"));
				reR006Data.setNoAppSlaGen(rs.getString("NO_APP_SLA_GEN"));
				reR006Data.setTargetSlaGen(rs.getString("TARGET_SLA_GEN"));
				reR006Data.setNoAppSlaKEC(rs.getString("NO_APP_SLA_KEC"));
				reR006Data.setTargetSlaKEC(rs.getString("TARGET_SLA_KEC"));
				reR006Data.setNoAppSlaKPL(rs.getString("NO_APP_SLA_KPL"));
				reR006Data.setTargetSlaKPL(rs.getString("TARGET_SLA_KPL"));
				reR006Data.setNoAppOlaInf(rs.getString("NO_APP_OLA_INF"));
				reR006Data.setTargetOlaInf(rs.getString("TARGET_OLA_INF"));
				reR006Data.setNoAppOlaWis(rs.getString("NO_APP_OLA_WIS"));
				reR006Data.setTargetOlaWis(rs.getString("TARGET_OLA_WIS"));
				reR006Data.setNoAppOlaPre(rs.getString("NO_APP_OLA_PRE"));
				reR006Data.setTargetOlaPre(rs.getString("TARGET_OLA_PRE"));
				reR006Data.setNoAppOlaPlt(rs.getString("NO_APP_OLA_PLT"));
				reR006Data.setTargetOlaPlt(rs.getString("TARGET_OLA_PLT"));
				reR006Data.setNoAppOlaGen(rs.getString("NO_APP_OLA_GEN"));
				reR006Data.setTargetOlaGen(rs.getString("TARGET_OLA_GEN"));
				reR006Data.setNoAppOlaKEC(rs.getString("NO_APP_OLA_KEC"));
				reR006Data.setTargetOlaKEC(rs.getString("TARGET_OLA_KEC"));
				reR006Data.setNoAppOlaKPL(rs.getString("NO_APP_OLA_KPL"));
				reR006Data.setTargetOlaKPL(rs.getString("TARGET_OLA_KPL"));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}
	
	@Override
	public ArrayList<ProcessingTimeDataM> getProcessingTime(InfReportJobDataM infReportJob) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<ProcessingTimeDataM> reR012DataList = new ArrayList<>(); 
		ResultSet rs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			
			sql.append(" SELECT * FROM TABLE(pka_rpt.f_rpt_R012_ProcessingTimeC(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ");
			logger.debug("sql : "+sql);
//			logger.debug("infReportJob : "+new Gson().toJson(infReportJob));
			ps = conn.prepareStatement(sql.toString());
			logger.debug("getDateFrom : "+infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			logger.debug("getDateTo : "+infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			ps.setString(parameter++, infReportJob.getDateType());
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getStationFrom());
			ps.setString(parameter++, infReportJob.getStationTo());
			ps.setString(parameter++, infReportJob.getApplicationStatus());
			rs = ps.executeQuery();
			while(rs.next()){
				ProcessingTimeDataM reR012Data = new ProcessingTimeDataM();
				reR012Data.setCardTypeDesc(rs.getString("CARD_TYPE_DESC"));
				reR012Data.setRoleName(rs.getString("ROLE_NAME"));
				reR012Data.setStandard(rs.getString("STANDARD"));
				reR012Data.setProcessTime(rs.getString("PROCESS_TIME"));
				reR012Data.setWaitingTime(rs.getString("WAITING_TIME"));
				reR012DataList.add(reR012Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR012DataList;
	}
	
	@Override
	public void updateInfReportJobFlag(String infReportJobId) throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE INF_REPORT_JOB ");
			sql.append(" SET ");
			sql.append(" GENERATE_FLAG = ? ");
			sql.append(" WHERE REPORT_JOB_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, InfBatchProperty.getInfBatchConfig("INF_UPDATE_FLAG"));
			ps.setString(parameter++, infReportJobId);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public ArrayList<ProcessingTimeDataM> getReportR012SummaryC(InfReportJobDataM infReportJob) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProcessingTimeDataM> processingTimeList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(PKA_RPT.f_rpt_R012_SummaryC(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			ps.setString(parameter++, infReportJob.getDateType());
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getStationFrom());
			ps.setString(parameter++, infReportJob.getStationTo());
			ps.setString(parameter++, infReportJob.getApplicationStatus());
			rs = ps.executeQuery();
			while(rs.next()){
				ProcessingTimeDataM processingTimeM = new ProcessingTimeDataM();
				processingTimeM.setCardTypeDesc(rs.getString("CARD_TYPE_DESC"));
				processingTimeM.setRoleName(rs.getString("ROLE_NAME"));
				processingTimeM.setStandard(rs.getString("STANDARD"));
				processingTimeM.setProcessTime(rs.getString("PROCESS_TIME"));
				processingTimeM.setWaitingTime(rs.getString("WAITING_TIME"));
				processingTimeList.add(processingTimeM);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return processingTimeList;
	}
	
	@Override
	public ArrayList<ProcessingTimeDataM> getReportR012_Top_BottomC(InfReportJobDataM infReportJob, String role, String position) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ProcessingTimeDataM> processingTimeList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(PKA_RPT.f_rpt_R012_top_bottomC(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps = conn.prepareStatement(sql.toString());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			ps.setString(parameter++, infReportJob.getDateType());
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getStationFrom());
			ps.setString(parameter++, infReportJob.getStationTo());
			ps.setString(parameter++, infReportJob.getApplicationStatus());
			ps.setString(parameter++, role);
			ps.setString(parameter++, position);
			rs = ps.executeQuery();
			while(rs.next()){
				ProcessingTimeDataM processingTimeM = new ProcessingTimeDataM();
				processingTimeM.setCardTypeDesc(rs.getString("CARD_TYPE_DESC"));
				processingTimeM.setRoleName(rs.getString("ROLE_NAME"));
				processingTimeM.setStandard(rs.getString("STANDARD"));
				processingTimeM.setProcessTime(rs.getString("PROCESS_TIME"));
				processingTimeM.setWaitingTime(rs.getString("WAITING_TIME"));
				processingTimeList.add(processingTimeM);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new Exception(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new Exception(e.getLocalizedMessage());
			}
		}
		return processingTimeList;
	}
	
	@Override
	public ArrayList<SlaDataM> getSLA3(InfReportJobDataM infReportJob) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>(); 
		ResultSet rs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(pka_rpt.f_rpt_R006_SLA3(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ORDER BY LENGTH(NULL) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setPeriod(rs.getString("PERIOD"));
				reR006Data.setWipPeriod(rs.getString("PERIOD"));
				reR006Data.setCcInfinite(rs.getString("CC_INFINITE"));
				reR006Data.setCcWisdom(rs.getString("CC_WISDOM"));
				reR006Data.setCcPremier(rs.getString("CC_PREMIER"));
				reR006Data.setCcPlatinum(rs.getString("CC_PLATINUM"));
				reR006Data.setCcGeneric(rs.getString("CC_GENERIC"));
				reR006Data.setCcGrandTotal(rs.getString("CC_GRAND_TOTAL"));
				reR006Data.setKec(rs.getString("KEC"));
				reR006Data.setKpl(rs.getString("KPL"));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}
	
	@Override
	public ArrayList<SlaDataM> getSLA4(InfReportJobDataM infReportJob) throws InfBatchException {		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SlaDataM> reR006DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE(pka_rpt.f_rpt_R006_SLA4(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int parameter = 1;
			ps.setString(parameter++, infReportJob.getDateFrom().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDateTo().substring(0, 10).replace("-", ""));
			ps.setString(parameter++, infReportJob.getDocFirstCompleteFlag());
			ps.setString(parameter++, infReportJob.getProjectCode());
			ps.setString(parameter++, infReportJob.getProductCriteria());
			ps.setString(parameter++, infReportJob.getChannel());
			ps.setString(parameter++, infReportJob.getBranchRegion());
			ps.setString(parameter++, infReportJob.getBranchZone());
			ps.setString(parameter++, infReportJob.getDsaZone());
			ps.setString(parameter++, infReportJob.getNbdZone());
			rs = ps.executeQuery();
			while(rs.next()){
				SlaDataM reR006Data = new SlaDataM();
				reR006Data.setSlaType(rs.getString("SLA_TYPE"));
				reR006Data.setE2eAcheive(getIntegerValue(rs.getString("E2E_ACHEIVE")));
				reR006Data.setE2eTarget(getIntegerValue(rs.getString("E2E_TARGET")));
				reR006Data.setE2eCountApp(getIntegerValue(rs.getString("E2E_CNT_APP")));
				
				reR006Data.setLeg1Acheive(getIntegerValue(rs.getString("LEG1_ACHEIVE")));
				reR006Data.setLeg1Target(getIntegerValue(rs.getString("LEG1_TARGET")));
				reR006Data.setLeg1CountApp(getIntegerValue(rs.getString("LEG1_CNT_APP")));
				
				reR006Data.setLeg2Acheive(getIntegerValue(rs.getString("LEG2_ACHEIVE")));
				reR006Data.setLeg2Target(getIntegerValue(rs.getString("LEG2_TARGET")));
				reR006Data.setLeg2CountApp(getIntegerValue(rs.getString("LEG2_CNT_APP")));
				
				reR006Data.setLeg3Acheive(getIntegerValue(rs.getString("LEG3_ACHEIVE")));
				reR006Data.setLeg3Target(getIntegerValue(rs.getString("LEG3_TARGET")));
				reR006Data.setLeg3CountApp(getIntegerValue(rs.getString("LEG3_CNT_APP")));
				
				reR006Data.setLeg4Acheive(getIntegerValue(rs.getString("LEG4_ACHEIVE")));
				reR006Data.setLeg4Target(getIntegerValue(rs.getString("LEG4_TARGET")));
				reR006Data.setLeg4CountApp(getIntegerValue(rs.getString("LEG4_CNT_APP")));
				reR006DataList.add(reR006Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR006DataList;
	}

	@Override
	public void callPrepareReportData() throws Exception {
		Connection conn = null;
		CallableStatement cs = null;
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" { call PKA_RPT.P_PRE_REPORT() } ");
			logger.debug("sql : "+sql);
			cs = conn.prepareCall(sql.toString());
			cs.execute();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, cs);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public ArrayList<AuditTrailDataM> getReportR021(String P_AS_OF_DATE,String P_JOB_STATE) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<AuditTrailDataM> reR021DataList = new ArrayList<>();
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM TABLE (PKA_RPT.F_RPT_R021_AUDIT_TRAIL(?,?)) ");
			logger.debug("sql : "+sql);
			logger.debug("P_AS_OF_DATE : "+P_AS_OF_DATE);
			logger.debug("P_JOB_STATE : "+P_JOB_STATE);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, P_AS_OF_DATE);
			ps.setString(2, P_JOB_STATE);
			rs = ps.executeQuery();
			while(rs.next()){
				AuditTrailDataM reR021Data = new AuditTrailDataM();
				reR021Data.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				reR021Data.setFieldName(rs.getString("FIELD_NAME"));
				reR021Data.setOldValue(rs.getString("OLD_VALUE"));
				reR021Data.setNewValue(rs.getString("NEW_VALUE"));
				reR021Data.setCreateBy(rs.getString("CREATE_BY"));
				reR021Data.setCreateDate(rs.getString("CREATE_DATE"));
				reR021Data.setCreateRole(rs.getString("CREATE_ROLE"));
				reR021Data.setUpdateBy(rs.getString("UPDATE_BY"));
				reR021Data.setUpdateDate(rs.getString("UPDATE_DATE"));
				reR021Data.setRole(rs.getString("UPDATE_ROLE"));
				reR021DataList.add(reR021Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR021DataList;
	}

	@Override 
	public HashMap<String, ArrayList<OverrideResultDataM>> getOverrideResult(Date asOfDate, String product) throws InfBatchException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<OverrideResultDataM> reR005product;
		HashMap<String, ArrayList<OverrideResultDataM>> reR005DataList = new HashMap<String, ArrayList<OverrideResultDataM>>();
		String asOfYear = new SimpleDateFormat("yyyy").format(asOfDate);
		
		try{
			conn = getConnection(InfBatchServiceLocator.REPORT_DB);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ors.*, (ors.WITH_OR - ors.WITH_NONLOWSIDE_OR) AS WITH_LOWSIDE_OR ");
			sql.append("FROM OR_PERIOD_SUM ors ");
			sql.append("WHERE ors.PERIOD LIKE '").append(asOfYear).append("%' ");
			//sql.append("AND ors.APPROVE > 0 ");
			if (product != null)
				sql.append("AND ors.PRODUCT = '").append(product).append("' ");
			sql.append("ORDER BY ors.PERIOD, ors.PRODUCT ");
			logger.debug("sql : "+sql);
			logger.debug("asOfDate : "+asOfDate);
			logger.debug("product : "+product);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()) {
				product = rs.getString("PRODUCT");
				reR005product = reR005DataList.get(product);
				if (reR005product == null) {
					reR005product = new ArrayList<OverrideResultDataM>();
					reR005DataList.put(product, reR005product);
				}
				
				OverrideResultDataM reR005Data = new OverrideResultDataM();
				reR005Data.setPeriod(rs.getString("PERIOD"));
				reR005Data.setTotalAppIn(rs.getInt("APP_IN"));
				reR005Data.setTotalApprove(rs.getInt("APPROVE"));
				reR005Data.setTotalReject(rs.getInt("REJECT"));
				reR005Data.setTotalApproveWithOR(rs.getInt("WITH_OR"));
				reR005Data.setTotalApproveWithExemptionOR(rs.getInt("WITH_EXEMPT_OR"));
				reR005Data.setTotalApproveWithNonLowsideOR(rs.getInt("WITH_NONLOWSIDE_OR"));
				reR005Data.setTotalApproveWithLowsideOR(rs.getInt("WITH_LOWSIDE_OR"));
				reR005Data.setTotalApproveWithOR_01_02_03(rs.getInt("WITH_OR_010203"));
				reR005Data.setTotalApproveWithOR_04(rs.getInt("WITH_OR_04"));
				reR005Data.setTotalApproveWithOR_XX(rs.getInt("WITH_OR_XX"));
				reR005Data.setNumberOfOR_01(rs.getInt("OR_01"));
				reR005Data.setNumberOfOR_02(rs.getInt("OR_02"));
				reR005Data.setNumberOfOR_03(rs.getInt("OR_03"));
				reR005Data.setNumberOfOR_04(rs.getInt("OR_04"));
				reR005Data.setNumberOfOR_05(rs.getInt("OR_05"));
				reR005Data.setNumberOfOR_06(rs.getInt("OR_06"));
				reR005Data.setNumberOfOR_07(rs.getInt("OR_07"));
				reR005Data.setNumberOfOR_08(rs.getInt("OR_08"));
				reR005Data.setNumberOfOR_09(rs.getInt("OR_09"));
				reR005Data.setNumberOfOR_10(rs.getInt("OR_10"));
				reR005Data.setNumberOfOR_11(rs.getInt("OR_11"));
				reR005Data.setNumberOfOR_12(rs.getInt("OR_12"));
				reR005Data.setNumberOfOR_13(rs.getInt("OR_13"));
				reR005Data.setNumberOfOR_14(rs.getInt("OR_14"));
				reR005Data.setNumberOfOR_15(rs.getInt("OR_15"));
				reR005Data.setNumberOfOR_16(rs.getInt("OR_16"));
				reR005Data.setNumberOfOR_17(rs.getInt("OR_17"));
				reR005Data.setNumberOfOR_18(rs.getInt("OR_18"));
				reR005Data.setNumberOfOR_19(rs.getInt("OR_19"));
				reR005Data.setNumberOfOR_20(rs.getInt("OR_20"));
				reR005Data.setNumberOfOR_21(rs.getInt("OR_21"));
				reR005Data.setNumberOfOR_22(rs.getInt("OR_22"));
				reR005Data.setNumberOfOR_23(rs.getInt("OR_23"));
				reR005Data.setNumberOfOR_24(rs.getInt("OR_24"));
				reR005Data.setNumberOfOR_25(rs.getInt("OR_25"));
				reR005product.add(reR005Data);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return reR005DataList;
		
	}
}
