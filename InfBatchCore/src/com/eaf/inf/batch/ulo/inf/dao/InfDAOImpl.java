package com.eaf.inf.batch.ulo.inf.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.UserM;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.InfBatchLogDataM;

public class InfDAOImpl extends InfBatchObjectDAO implements InfDAO {
	private static transient Logger logger = Logger.getLogger(InfDAOImpl.class);
	
	private Date getApplicationDate() throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date APPLICATION_DATE = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM APPLICATION_DATE ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			if(rs.next()){
				APPLICATION_DATE = rs.getDate("APP_DATE");
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return APPLICATION_DATE;
	}
	
	@Override
	public String getInfExport(String MODULE_ID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CONTENT = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
//			sql.append(" SELECT * FROM INF_EXPORT WHERE MODULE_ID = ? AND DATA_DATE = ( ");
//			sql.append(" SELECT MAX(DATA_DATE) FROM INF_EXPORT ");
//			sql.append(" WHERE MODULE_ID = ? AND TRUNC(DATA_DATE) = TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS') ");
//			sql.append(" ) ");
			sql.append(" SELECT * FROM INF_EXPORT WHERE MODULE_ID = ? AND EXPORT_ID = ( ");
			sql.append(" SELECT MAX(TO_NUMBER(EXPORT_ID)) FROM INF_EXPORT WHERE MODULE_ID = ? ");
			sql.append(" ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, MODULE_ID);
			ps.setString(2, MODULE_ID);
//			Date APPLICATION_DATE = getApplicationDate();
//			ps.setString(3, APPLICATION_DATE.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				Clob clob = rs.getClob("CONTENT");	
				if(clob != null)
				{
					CONTENT = clob.getSubString(1, (int)clob.length());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		//logger.debug("CONTENT >> "+CONTENT);
		return CONTENT;
	}
	
	@Override
	public String getInfExportMLP(String MODULE_ID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CONTENT = null;
		try{
			conn = getConnection(InfBatchServiceLocator.MLP_DB);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM INF_EXPORT WHERE MODULE_ID = ? AND EXPORT_ID = ( ");
			sql.append(" SELECT MAX(TO_NUMBER(EXPORT_ID)) FROM INF_EXPORT WHERE MODULE_ID = ? ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, MODULE_ID);
			ps.setString(2, MODULE_ID);
			rs = ps.executeQuery();
			while(rs.next()){
				Clob clob = rs.getClob("CONTENT");	
				if(clob != null)
				{
					CONTENT = clob.getSubString(1, (int)clob.length());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return CONTENT;
	}
	
	@Override
	public String getInfExport(String MODULE_ID,Connection conn) throws Exception {
//		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CONTENT = null;
		try{
//			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM INF_EXPORT WHERE MODULE_ID = ? AND DATA_DATE = ( ");
			sql.append(" SELECT MAX(DATA_DATE) FROM INF_EXPORT ");
			sql.append(" WHERE MODULE_ID = ? AND TRUNC(DATA_DATE) = TO_DATE( ?, 'YYYY-MM-DD HH24:MI:SS') ");
			sql.append(" ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, MODULE_ID);
			ps.setString(2, MODULE_ID);
			Date APPLICATION_DATE = getApplicationDate();
			ps.setString(3, APPLICATION_DATE.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				Clob clob = rs.getClob("CONTENT");				
				CONTENT = clob.getSubString(1, (int)clob.length());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		//logger.debug("CONTENT >> "+CONTENT);
		return CONTENT;
	}
	
	@Override
	public String getCardLinkAccountSetUp(String USER, String OUTPUT_NAME) throws Exception 
	{
		String CARDLINK_ACCOUNT_SETUP_MODULE_ID = InfBatchProperty.getInfBatchConfig(InfBatchConstant.CARDLINK.ACCOUNT_SETUP+"_"+InfBatchConstant.ReportParam.MODULE_ID);

		//Call store procedure
		String acctSetup = getCardLinkAccountSetUp(getConnection(), USER, OUTPUT_NAME);
		String mobileAcctSetup = getCardLinkAccountSetUp(getConnection(InfBatchServiceLocator.MLP_DB), USER, OUTPUT_NAME);		
		
		logger.debug("executeResult >> " + acctSetup);
		logger.debug("mlpExecuteResult >> " + mobileAcctSetup);
		
		//Get clob from INF_EXPORT
		String acctSetupText = InfFactory.getInfDAO().getInfExport(CARDLINK_ACCOUNT_SETUP_MODULE_ID);
		String mobileAcctSetupText = InfFactory.getInfDAO().getInfExportMLP(CARDLINK_ACCOUNT_SETUP_MODULE_ID);
		
		if(Util.empty(acctSetupText) && !Util.empty(mobileAcctSetupText))
		{
			acctSetupText = mobileAcctSetupText;
			mobileAcctSetupText = null;
		}
		
		if(!Util.empty(mobileAcctSetupText))
		{
			acctSetupText += "MLP_DATA" + System.lineSeparator() +  mobileAcctSetupText;
		}
		
		return acctSetupText;
	}
	
	@Override
	public String getCardLinkAccountSetUp(Connection conn, String USER, String OUTPUT_NAME) throws Exception {
		//Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{
			//conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_acc_setup(?, ?) }");
			logger.debug("sql >> "+sql);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}
	
	@Override
	public String getCardLinkAccountSetUpCJD(String USER, String applicationGroupId) throws Exception 
	{
		//There can be only one source per CJDCardLinkAction
		String acctSetupClob = getCardLinkAccountSetUpCJD(getConnection(InfBatchServiceLocator.MLP_DB), USER, applicationGroupId);
		if(!Util.empty(acctSetupClob))
		{
			return acctSetupClob;
		}
		return null;
	}
	
	@Override
	public String getCardLinkAccountSetUpCJD(Connection conn, String USER, String applicationGroupId) throws Exception {
		CallableStatement st = null;
		String executeResult = "";
		Clob clob = conn.createClob();
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_acc_setup_clob(?, ?, ?, ?) }");
			logger.debug("sql >> "+sql);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, null);
			st.registerOutParameter(4, Types.CLOB);
			st.setClob(4, clob);
			st.setString(5, applicationGroupId);
			st.execute();
			if(st.getClob(4) != null && st.getClob(4).length() > 0)
			{
				logger.debug("clob.length(): " + st.getClob(4).length());
				executeResult = IOUtils.toString(st.getClob(4).getCharacterStream());
			}
			else
			{
				logger.info("Empty cardLinkAcctSetup results for " + applicationGroupId);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		//logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}
	
	@Override
	public String getCardLinkCashDay1(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_dayone(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}
	
	@Override
	public String getCardLinkCustomerOccupation(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_occupation(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	@Override
	public String getSalePerformance(String USER, String OUTPUT_NAME)	throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_vlink_acc_setup(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	@Override
	public String getSalePerformance(String USER, String OUTPUT_NAME,Connection con)	throws Exception {
//		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
//			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_vlink_acc_setup(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = con.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}
	
	@Override
	public String getKmAlertService(String USER, String OUTPUT_NAME) throws Exception {
		String KM_ALERT_PERFORMANCE_MODULE_ID = InfBatchProperty.getInfBatchConfig(InfBatchConstant.KM_ALERT_PERFORMANCE+"_"+InfBatchConstant.ReportParam.MODULE_ID);
		Connection conn = null;
		CallableStatement st = null;
		Connection mlpConn = null;
		CallableStatement mlpSt = null;
		String executeResult = "";
		String mlpExecuteResult = "";
		String outputString = "";
		try{			
			conn = getConnection();
			mlpConn = getConnection(InfBatchServiceLocator.MLP_DB);
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_km_alert(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
			
			mlpSt = mlpConn.prepareCall(sql.toString());
			mlpSt.registerOutParameter(1, Types.VARCHAR);
			mlpSt.setString(2, USER);
			mlpSt.setString(3, OUTPUT_NAME);
			mlpSt.execute();
			mlpExecuteResult = mlpSt.getString(1);
			
			//merge ORIG schema data with MLP schema
			String kmAlertString = InfFactory.getInfDAO().getInfExport(KM_ALERT_PERFORMANCE_MODULE_ID);
			String mlpKmAlertString = InfFactory.getInfDAO().getInfExportMLP(KM_ALERT_PERFORMANCE_MODULE_ID);
			
			if(!Util.empty(mlpKmAlertString) 
					&& mlpKmAlertString.split("\\r?\\n").length > 2)
			{
				String[] mlpData = mlpKmAlertString.split("\\r?\\n");
				ArrayList<String> dataList = new ArrayList<String>(Arrays.asList(kmAlertString.split("\\r?\\n")));
				dataList.remove(dataList.size()-1);
				
				for(String line : mlpData)
				{
					if(line.startsWith("B01"))
					{
						dataList.add(line);
					}
				}
				
				//Add end line T01|000000000
				dataList.add("T01|" + String.format("%09d", dataList.size()-1));
				
				//Join arrayList of String into single String
				outputString = StringUtils.join(dataList, "\n");
				
			}
			else
			{
				outputString = kmAlertString;
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
				closeConnection(mlpConn, mlpSt);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		logger.debug("mlpExecuteResult >> "+mlpExecuteResult);
		return outputString;
	}
	//No function
	@Override
	public String getTCB(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_occupation(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}	
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	@Override
	public String getAFPPrintRejectNonNCB(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_makeafp_reject_letter1(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	@Override
	public String getAFPPrintRejectNCB(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_makeafp_reject_letter2(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	@Override
	public String getAFPPrintApprove(String USER, String OUTPUT_NAME)	throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_makeafp_approve_letter1(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	@Override
	public String getPDFRejectLetter(String USER, String OUTPUT_NAME)	throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_makepdf_reject_letter1(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	@Override
	public String getKVICloseApplication(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_kvi_close_job(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}
	//No Function
	@Override
	public String getIMGenerateConsent(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_occupation(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	//No function
	@Override
	public String getIMNotifyCompletedApplication(String USER, String OUTPUT_NAME) throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_occupation(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}

	//No function
	@Override
	public String getLotusNoteGetDSA(String USER, String OUTPUT_NAME)	throws Exception {
		Connection conn = null;
		CallableStatement st = null;
		String executeResult = "";
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("{ ? = call PKA_INTERFACE_MASTER.f_exp_cardlink_occupation(?, ?) }");
			logger.debug("sql : "+sql);
			logger.debug(USER);
			logger.debug(OUTPUT_NAME);
			st = conn.prepareCall(sql.toString());
			st.registerOutParameter(1, Types.VARCHAR);
			st.setString(2, USER);
			st.setString(3, OUTPUT_NAME);
			st.execute();
			executeResult = st.getString(1);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, st);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("executeResult >> "+executeResult);
		return executeResult;
	}
	
	public void insertFileIntoTable() throws InfBatchException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO INF_EXPORT ");
			sql.append(" ( ");
			sql.append(" CONTENT, FILE_NAME, EXPORT_ID ");
			sql.append(" )VALUES( ");
			sql.append(" ?, ?, INF_EXPORT_PK.NEXTVAL ");
			sql.append(" ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
//			ps.setBinaryStream(parameterIndex, x);
			ps.executeUpdate();
		}catch(Exception e){
			logger.debug("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.debug("ERROR "+e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException {
		try{
//			#rawi comment change to only insert table INF_BATCH_LOG
//			int returnRow = this.updateTableInfBatchLog(infBatchLog, conn);
//			if(0==returnRow){
//				this.insertTableInfBatchLog(infBatchLog, conn);
//			}
//			
			this.insertTableInfBatchLog(infBatchLog, conn);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}		
	}
	
	public void insertTableInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder("");			
			sql.append("INSERT INTO INF_BATCH_LOG ");
			sql.append(" (INTERFACE_LOG_ID, ");
			sql.append(" APPLICATION_RECORD_ID, ");
			sql.append(" INTERFACE_CODE, ");
			sql.append(" INTERFACE_DATE, ");
			sql.append(" CREATE_DATE, ");
			sql.append(" CREATE_BY, ");
			sql.append(" INTERFACE_STATUS, ");
			sql.append(" REF_ID, ");
			sql.append(" REF_SEQ, ");
			sql.append(" SYSTEM01, ");
			sql.append(" SYSTEM02, ");
			sql.append(" SYSTEM03, ");
			sql.append(" SYSTEM04, ");
			sql.append(" SYSTEM05, ");
			sql.append(" SYSTEM06, ");
			sql.append(" SYSTEM07, ");
			sql.append(" SYSTEM08, ");
			sql.append(" SYSTEM09, ");
			sql.append(" SYSTEM10, ");
			sql.append(" APPLICATION_GROUP_ID, ");
			sql.append(" LIFE_CYCLE,  ");
			sql.append(" LOG_MESSAGE )");
			sql.append(" SELECT INF_BATCH_LOG_PK.NEXTVAL, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, "); //sql.append(" SYSDATE, ");
			sql.append(" ?, "); //sql.append(" SYSDATE, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ?, ");
			sql.append(" ? ");
			sql.append(" FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int index = 1;
			ps.setString(index++,infBatchLog.getApplicationRecordId());
			ps.setString(index++,infBatchLog.getInterfaceCode());
			ps.setTimestamp(index++,infBatchLog.getInterfaceDate());
			ps.setTimestamp(index++,infBatchLog.getCreateDate());
			ps.setString(index++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(index++,infBatchLog.getInterfaceStatus());
			ps.setString(index++,infBatchLog.getRefId());
			ps.setString(index++,infBatchLog.getRefSeq());
			ps.setString(index++,infBatchLog.getSystem01());
			ps.setString(index++,infBatchLog.getSystem02());
			ps.setString(index++,infBatchLog.getSystem03());
			ps.setString(index++,infBatchLog.getSystem04());
			ps.setString(index++,infBatchLog.getSystem05());
			ps.setString(index++,infBatchLog.getSystem06());
			ps.setString(index++,infBatchLog.getSystem07());
			ps.setString(index++,infBatchLog.getSystem08());
			ps.setString(index++,infBatchLog.getSystem09());
			ps.setString(index++,infBatchLog.getSystem10());
			ps.setString(index++,infBatchLog.getApplicationGroupId());
			ps.setInt(index++, infBatchLog.getLifeCycle());
			ps.setString(index++, infBatchLog.getLogMessage());
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				if (ps != null){
					try{
						ps.close();
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new InfBatchException(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	public int updateTableInfBatchLog(InfBatchLogDataM infBatchLog,Connection conn) throws InfBatchException {
		PreparedStatement ps = null;
		int returnRows = 0;
		try {
			StringBuilder sql = new StringBuilder("");			
			sql.append("UPDATE INF_BATCH_LOG ");
			sql.append(" SET INTERFACE_DATE=SYSDATE, ");
			sql.append(" CREATE_DATE =SYSDATE, ");
			sql.append(" CREATE_BY=?, ");
			sql.append(" INTERFACE_STATUS=?, ");
			sql.append(" REF_ID=?, ");
			sql.append(" REF_SEQ=?");
			sql.append(" WHERE APPLICATION_RECORD_ID=? AND INTERFACE_CODE=? "); 
			
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int intdex = 1;
			ps.setString(intdex++,InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID"));
			ps.setString(intdex++,infBatchLog.getInterfaceStatus());
			ps.setString(intdex++,infBatchLog.getRefId());
			ps.setString(intdex++,infBatchLog.getRefSeq());
			
			ps.setString(intdex++,infBatchLog.getApplicationRecordId());
			ps.setString(intdex++,infBatchLog.getInterfaceCode());
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				if (ps != null){
					try{
						ps.close();
					} catch (Exception e) {
						logger.fatal("ERROR",e);
						throw new InfBatchException(e.getLocalizedMessage());
					}
				}
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void insertInfBatchLog(InfBatchLogDataM infBatchLog) {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");			
			sql.append("INSERT INTO INF_BATCH_LOG (INTERFACE_LOG_ID,APPLICATION_RECORD_ID,INTERFACE_CODE,INTERFACE_DATE ")
			.append(" ,CREATE_DATE,CREATE_BY,INTERFACE_STATUS,REF_ID,REF_SEQ,SYSTEM01,SYSTEM02,APPLICATION_GROUP_ID, LIFE_CYCLE ")
			.append(" ,LOG_MESSAGE,SYSTEM03,SYSTEM04,SYSTEM05,SYSTEM06,SYSTEM07,SYSTEM08,SYSTEM09,SYSTEM10 ) ")
			.append(" VALUES(INF_BATCH_LOG_PK.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,? ")
			.append(" ,?,?,?,?,?,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++,infBatchLog.getApplicationRecordId());
			ps.setString(index++,infBatchLog.getInterfaceCode());
			ps.setTimestamp(index++,infBatchLog.getInterfaceDate());
			ps.setTimestamp(index++,InfBatchProperty.getTimestamp());
			ps.setString(index++,infBatchLog.getCreateBy());
			ps.setString(index++,infBatchLog.getInterfaceStatus());
			ps.setString(index++,infBatchLog.getRefId());
			ps.setString(index++,infBatchLog.getRefSeq());
			ps.setString(index++,infBatchLog.getSystem01());
			ps.setString(index++,infBatchLog.getSystem02());
			ps.setString(index++,infBatchLog.getApplicationGroupId());
			ps.setInt(index++,infBatchLog.getLifeCycle());
			ps.setString(index++,infBatchLog.getLogMessage());
			ps.setString(index++,infBatchLog.getSystem03());
			ps.setString(index++,infBatchLog.getSystem04());
			ps.setString(index++,infBatchLog.getSystem05());
			ps.setString(index++,infBatchLog.getSystem06());
			ps.setString(index++,infBatchLog.getSystem07());
			ps.setString(index++,infBatchLog.getSystem08());
			ps.setString(index++,infBatchLog.getSystem09());
			ps.setString(index++,infBatchLog.getSystem10());
			ps.executeUpdate();			
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}finally{
			try{
				closeConnection(conn,ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		}
	}

	@Override
	public String getInfExportByDate(String MODULE_ID) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CONTENT = null;
		StringBuilder str = new StringBuilder();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM INF_EXPORT ");
			sql.append(" WHERE MODULE_ID = ? ");
			sql.append(" AND TRUNC(DATA_DATE) = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, MODULE_ID);
			Date APPLICATION_DATE = getApplicationDate();
			ps.setDate(2, APPLICATION_DATE);
			rs = ps.executeQuery();
			while(rs.next()){
				Clob clob = rs.getClob("CONTENT");	
				if(null != clob){
					str.append(clob.getSubString(1, (int)clob.length()));
					str.append("\n");
				}
				
			}
			CONTENT = str.toString();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return CONTENT;
	}

	@Override
	public ArrayList<String> getGenerateCancelCardLinkRefNoApplicationGroupId()throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> applicationGroupIds=null;	
		String[] finalDecisions =InfBatchProperty.getArrayInfBatchConfig("GENERATE_CARDLINKE_REF_FINAL_DECISION");
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT  G.APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP G");
			sql.append(" INNER JOIN  ORIG_APPLICATION  A  ON A.APPLICATION_GROUP_ID= G.APPLICATION_GROUP_ID");
			sql.append(" WHERE TRUNC(A.FINAL_APP_DECISION_DATE) =? AND  A.CARDLINK_REF_NO IS NULL");
			sql.append(" AND A.FINAL_APP_DECISION IN (");
			String comma="";
			for(String finalDecision : finalDecisions){
				sql.append(comma+"?");
				comma=",";
			}
			sql.append(" )");
			
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int index=1;
			ps.setDate(index++, getApplicationDate());
			for(String finalDecision : finalDecisions){
			ps.setString(index++, finalDecision);
			}
			rs = ps.executeQuery();
			while(rs.next()){
				if(null==applicationGroupIds){
					applicationGroupIds = new ArrayList<String>();
				}		
				applicationGroupIds.add(rs.getString("APPLICATION_GROUP_ID"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return applicationGroupIds;
	}
	
	@Override
	public ArrayList<String> getGenerateCancelCardLinkRefNoApplicationGroupId(Connection con)throws Exception {
//		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> applicationGroupIds=null;	
		String[] finalDecisions =InfBatchProperty.getArrayInfBatchConfig("GENERATE_CARDLINKE_REF_FINAL_DECISION");
		String[] saleChannels =InfBatchProperty.getArrayInfBatchConfig("CRM_VLINK_RT_SALE_PERFORMANCE_SALE_CHENNEL");
		String saleTypeNormal = InfBatchProperty.getInfBatchConfig("CRM_VLINK_RT_SALE_PERFORMANCE_SALE_TYPE_NORMAL");
		try{
//			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT  G.APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP G");
			sql.append(" INNER JOIN  ORIG_APPLICATION  A  ON A.APPLICATION_GROUP_ID= G.APPLICATION_GROUP_ID");
			sql.append(" INNER JOIN ORIG_SALE_INFO S ON S.APPLICATION_GROUP_ID = A.APPLICATION_GROUP_ID AND S.SALES_TYPE = ? ");
			sql.append(" WHERE TRUNC(A.FINAL_APP_DECISION_DATE) =? AND  A.CARDLINK_REF_NO IS NULL");
			sql.append(" AND A.FINAL_APP_DECISION IN (");
			String comma="";
			for(String finalDecision : finalDecisions){
				sql.append(comma+"?");
				comma=",";
			}
			sql.append(" )");
			
			comma="";
			sql.append(" AND S.SALE_CHANNEL IN (");
			for(String saleChannel : saleChannels){
				sql.append(comma+"?");
				comma=",";
			}
			sql.append(" )");
			
			logger.debug("sql : "+sql);
			ps = con.prepareStatement(sql.toString());
			int index=1;
			ps.setString(index++, saleTypeNormal);
			ps.setDate(index++, getApplicationDate());
			for(String finalDecision : finalDecisions){
				ps.setString(index++, finalDecision);
			}
			for(String saleChannel : saleChannels){
				ps.setString(index++, saleChannel);
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				if(null==applicationGroupIds){
					applicationGroupIds = new ArrayList<String>();
				}		
				applicationGroupIds.add(rs.getString("APPLICATION_GROUP_ID"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return applicationGroupIds;
	}
	private List<String> loadUniuqeIdVlink() throws Exception {
		List<String> uniqueIds = new ArrayList<String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
				sql.append(" SELECT DISTINCT ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID ");
				sql.append(" FROM ORIG_APPLICATION_GROUP ");
				sql.append(" JOIN ORIG_APPLICATION  ");
				sql.append(" ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_APPLICATION.APPLICATION_GROUP_ID  ");
				sql.append(" AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ORIG_APPLICATION.LIFE_CYCLE ");
				sql.append(" AND ORIG_APPLICATION.CARDLINK_REF_NO IS NULL ");
				sql.append(" AND ORIG_APPLICATION.FINAL_APP_DECISION " +
						" IN ("+InfBatchProperty.getSQLParameter("CRM_VLINK_RT_SALE_PERFORMANCE_GEN_CARDLINK_REF_NO_FINAL_APPDECISION")+")");
				sql.append(" AND ORIG_APPLICATION_GROUP.APPLICATION_STATUS " +
						" IN ("+InfBatchProperty.getSQLParameter("CRM_VLINK_RT_SALE_PERFORMANCE_GEN_CARDLINK_REF_NO_APP_STATUS")+")");
				//Fix Defect cardlinkRefno skip
				sql.append(" JOIN BUSINESS_CLASS ON BUSINESS_CLASS.BUS_CLASS_ID = ORIG_APPLICATION.BUSINESS_CLASS_ID AND BUSINESS_CLASS.ORG_ID IN ('CC','KEC') ");
				
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				uniqueIds.add(rs.getString("APPLICATION_GROUP_ID"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return uniqueIds;
	}
	@Override
	public List<com.eaf.orig.ulo.model.app.ApplicationGroupDataM> loadApplicationVlink() throws Exception {
		List<String> uniqueIds = loadUniuqeIdVlink();
		List<com.eaf.orig.ulo.model.app.ApplicationGroupDataM> applicationVlinks = new ArrayList<com.eaf.orig.ulo.model.app.ApplicationGroupDataM>();
		if(null!=uniqueIds){
			for(String applicationGroupId :uniqueIds){
				Connection conn = null;
				try{
					conn = getConnection();
					logger.debug("applicationGroupId : "+applicationGroupId);
					OrigApplicationGroupDAO origApplicationGroupDAO = ORIGDAOFactory.getApplicationGroupDAO();
					applicationVlinks.add(origApplicationGroupDAO.loadApplicationVlink(applicationGroupId,conn));
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					throw e; 
				}finally{
					closeConnection(conn);
				}
			}
		}
		return applicationVlinks;
	}
	@Override
	public void updateCardlinkRefNo(ApplicationDataM applicaiton, Connection conn) throws Exception {
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE ORIG_APPLICATION SET CARDLINK_REF_NO = ?,CARDLINK_SEQ = ?,VLINK_FLAG = ? WHERE APPLICATION_RECORD_ID = ?");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			ps.setString(index++,applicaiton.getCardlinkRefNo());
			ps.setInt(index++,applicaiton.getCardlinkSeq());
			ps.setString(index++,applicaiton.getVlinkFlag());
			ps.setString(index++,applicaiton.getApplicationRecordId());
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public List<UserM> loadTableImpUser()throws Exception {
		List<UserM> users = new ArrayList<UserM>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT USER_NAME,DEPARTMENT,NAME_ENG,SURNAME_ENG,TELEPHONE,  ");
			sql.append(" TELEPHONE_EXT,MOBILE,EMAIL,JOB_LEVEL,DLA_LEVEL,PROFILE,SKILL, ");
			sql.append(" WORK_POSITION,ORGANIZE_POSITION,ACTIVE_STATUS,NAME_THAI, ");
			sql.append(" EMPOLYEE_ID,SURNAME_THAI,CREATE_BY,CREATE_DATE,ROLE  ");
			sql.append(" FROM IMP_USER ");
			String dSql = String.valueOf(sql);
			logger.debug("sql loadTableImpUser: "+sql);
			ps = conn.prepareStatement(dSql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				UserM user = new UserM();
				user.setUserName(rs.getString(1));
				user.setDepartment(rs.getString(2));
				user.setEngFirstName(rs.getString(3));
				user.setEngLastName(rs.getString(4));
				user.setTelephone(rs.getString(5));
				user.setTelephoneExt(rs.getString(6));
				user.setMobilePhone(rs.getString(7));
				user.setEmailAddress(rs.getString(8));
				user.setJobDescription(rs.getString(9));
				user.setDlaId(rs.getString(10));
				user.setProfileId(rs.getString(11));
				user.setSkillSetId(rs.getString(12));
				user.setPosition(rs.getString(13));
				user.setDefaultOfficeCode(rs.getString(14));
				user.setStatus(rs.getString(15));
				user.setThaiFirstName(rs.getString(16));
				user.setUserNo(rs.getString(17));
				user.setThaiLastName(rs.getString(18));	
				user.setCreatedBy(rs.getString((19)));
				user.setCreatedDate(rs.getDate(20));
				Vector<RoleM> roles = new Vector<RoleM>();
				RoleM roleObj = new RoleM();
				roleObj.setRoleID(rs.getString(21));
				roles.add(roleObj);
				user.setRoles(roles);
				users.add(user);
			}
			
			return users;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
	}
	public RoleM getROLEByUserName(String userName)throws Exception {
		RoleM result = new RoleM();
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ROLE.CREATED_DATE, ROLE.CREATED_BY, ROLE.ROLE_DESC, ROLE.ROLE_ID, ROLE.ROLE_NAME FROM  ROLE,USER_ROLE ");
			sql.append(" WHERE ROLE.ROLE_ID =  USER_ROLE.ROLE_ID AND UPPER(USER_ROLE.USER_NAME) = UPPER(?)");
			String dSql = String.valueOf( sql);

			ps = conn.prepareStatement(dSql);
			ps.setString(1, userName);

			rs = ps.executeQuery();			
			while (rs.next()) {
				result.setRoleID(rs.getString("ROLE_ID"));
				result.setRoleName(rs.getString("ROLE_NAME"));
				result.setRoleDesc(rs.getString("ROLE_DESC"));
			}
		}catch(Exception e){
			logger.error("ERROR "+e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		}finally{
			try{	
				if(null != rs){
					rs.close();
				}
				if(null != ps){
					ps.close();
				}
				if(null != conn){
					conn.close();
				}
			}catch(Exception e){
				logger.error("ERROR "+e.getLocalizedMessage());
				throw new InfBatchException(e.getMessage());
			}
		}
		return result;
	}
	public UserM loadTableUsers(String userName)throws Exception {
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CREATED_BY, CREATED_DATE, DEPARTMENT, DESCRIPTION, ENG_FIRSTNAME, ENG_LASTNAME, EXPIRED_DATE_OF_PASSWORD, EXPIRED_DATE, EXTENT_DATE, FAX, JOB_DESCRIPTION, MOBILEPHONE, POSITION, REGION, STATUS, TELEPHONE, THAI_FIRSTNAME, THAI_LASTNAME, USER_NAME FROM USERS ");
			sql.append(" WHERE UPPER(USER_NAME) = UPPER(?)");
			String dSql = String.valueOf( sql);

			PreparedStatement ps = conn.prepareStatement(dSql);
			ResultSet rs = null;

			ps.setString(1, userName);

			rs = ps.executeQuery();
			UserM prmUserM = new UserM();
			while (rs.next()) {
				prmUserM.setCreatedBy(rs.getString(1));
				prmUserM.setCreatedDate(rs.getDate(2));
				prmUserM.setDepartment(rs.getString(3));
				prmUserM.setDescription(rs.getString(4));
				prmUserM.setEngFirstName(rs.getString(5));
				prmUserM.setEngLastName(rs.getString(6));
				prmUserM.setExpireDateOfPassword(rs.getDate(7));
				prmUserM.setExpiredDate(rs.getDate(8));
				prmUserM.setExtendDate(rs.getInt(9));
				prmUserM.setFax(rs.getString(10));
				prmUserM.setJobDescription(rs.getString(11));
				prmUserM.setMobilePhone(rs.getString(12));
				prmUserM.setPosition(rs.getString(13));
				prmUserM.setRegion(rs.getString(14));
				prmUserM.setStatus(rs.getString(15));
				prmUserM.setTelephone(rs.getString(16));
				prmUserM.setThaiFirstName(rs.getString(17));
				prmUserM.setThaiLastName(rs.getString(18));
				prmUserM.setUserName(rs.getString(19));			
			}
			rs.close();
			ps.close();

			return prmUserM;
		}catch(Exception e){
			e.printStackTrace();
			throw new InfBatchException(e.getMessage());
		}finally{
			try{		
				if(null!=conn)conn.close();
			}catch(Exception e){
				e.printStackTrace();
				throw new InfBatchException(e.getMessage());
			}
		}
	}
	public void deleteTableIMP_USER(UserM prmUserM)throws Exception {
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE FROM IMP_USER ");
			sql.append(" WHERE UPPER(USER_NAME)= UPPER(?) ");
			String dSql = String.valueOf( sql);
			logger.debug("Sql=" + dSql);
			PreparedStatement ps = conn.prepareStatement(dSql);

			ps.setString(1, prmUserM.getUserName());

			ps.executeUpdate();
			ps.close();

		}catch(Exception e){
			e.printStackTrace();
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				if(null!=conn)conn.close();
			}catch(Exception ex){
				ex.printStackTrace();
				throw new InfBatchException(ex.getMessage());
			}
		}
	}

	@Override
	public InfBatchLogDataM getFullFraudInfBatchLogByRefId(String refId, int lifeCycle) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InfBatchLogDataM infBatchLogDataM = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT INTERFACE_LOG_ID,APPLICATION_RECORD_ID,INTERFACE_CODE,INTERFACE_DATE ")
				.append(" ,CREATE_DATE,CREATE_BY,INTERFACE_STATUS,REF_ID,REF_SEQ,SYSTEM01,SYSTEM02,APPLICATION_GROUP_ID, LIFE_CYCLE ")
				.append(" ,LOG_MESSAGE,SYSTEM03,SYSTEM04,SYSTEM05,SYSTEM06,SYSTEM07,SYSTEM08,SYSTEM09,SYSTEM10 ")
				.append("FROM INF_BATCH_LOG ")
				.append("WHERE REF_ID=? AND INTERFACE_CODE=? AND LIFE_CYCLE=? ")
				.append("ORDER BY CREATE_DATE DESC ")
				.append("FETCH FIRST 1 ROWS ONLY ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int cnt = 1;
			ps.setString(cnt++, refId);
			ps.setString(cnt++, InfBatchProperty.getInfBatchConfig("FULL_FRAUD_MODULE_ID"));
			ps.setInt(cnt++, lifeCycle);
			rs = ps.executeQuery();
			if (rs.next()) {
				infBatchLogDataM = new InfBatchLogDataM();
				infBatchLogDataM.setInterfaceLogId(rs.getString("INTERFACE_LOG_ID"));
				infBatchLogDataM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				infBatchLogDataM.setInterfaceCode(rs.getString("INTERFACE_CODE"));
				infBatchLogDataM.setInterfaceDate(rs.getTimestamp("INTERFACE_DATE"));
				infBatchLogDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				infBatchLogDataM.setCreateBy(rs.getString("CREATE_BY"));
				infBatchLogDataM.setInterfaceStatus(rs.getString("INTERFACE_STATUS"));
				infBatchLogDataM.setRefId(rs.getString("REF_ID"));
				infBatchLogDataM.setRefSeq(rs.getString("REF_SEQ"));
				infBatchLogDataM.setSystem01(rs.getString("SYSTEM01"));
				infBatchLogDataM.setSystem02(rs.getString("SYSTEM02"));
				infBatchLogDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				infBatchLogDataM.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				infBatchLogDataM.setLogMessage(rs.getString("LOG_MESSAGE"));
				infBatchLogDataM.setSystem03(rs.getString("SYSTEM03"));
				infBatchLogDataM.setSystem04(rs.getString("SYSTEM04"));
				infBatchLogDataM.setSystem05(rs.getString("SYSTEM05"));
				infBatchLogDataM.setSystem06(rs.getString("SYSTEM06"));
				infBatchLogDataM.setSystem07(rs.getString("SYSTEM07"));
				infBatchLogDataM.setSystem08(rs.getString("SYSTEM08"));
				infBatchLogDataM.setSystem09(rs.getString("SYSTEM09"));
				infBatchLogDataM.setSystem10(rs.getString("SYSTEM10"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return infBatchLogDataM;
	}

	private String getBigDecimalAsString(BigDecimal value, int decimalPoint, String defaultValue) {
		if (null == value)
			return defaultValue;
		if (decimalPoint >= 0)
			return value.setScale(decimalPoint, RoundingMode.UP).toPlainString();
		return value.toPlainString();
	}
	
	private String getTimestampAsString(Timestamp value, String defaultValue) {
		if (null == value)
			return defaultValue;
		return Formatter.display(new Date(value.getTime()), Formatter.EN, Formatter.Format.ddMMyyyy);
	}
	
	private String getString(String value, String defaultValue) {
		if (null == value)
			return defaultValue;
		return value;
	}
	
	/**
	 * Method to generate record to auto loan setup. At this moment, we support only single-tier FIX interest
	 * so part of code cannot work if we have multi-tier rate
	 * 
	 * @param USER
	 * @param OUTPUT_NAME
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getSetupLoan(String USER, String OUTPUT_NAME) throws Exception {
		String approveJobState = SystemConfig.getGeneralParam("JOB_STATE_DE2_APPROVE_SUBMIT");
		String escateJobState = SystemConstant.getConstant("JOBSTATE_ESCALATE");
		String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
		String interfaceCode = InfBatchProperty.getInfBatchConfig("SETUP_LOAN_MODULE_ID");
		StringBuilder sql = new StringBuilder();
		InfDAO dao = InfFactory.getInfDAO();

		// Construct SQL for get record for setup loan
		sql.append("SELECT AG.APPLICATION_GROUP_NO, AG.APPLICATION_GROUP_ID, ");
			sql.append("AP.PROJECT_CODE, AP.APPLICATION_TYPE, ");
			sql.append("PSN.CIS_NO, PSN.TH_TITLE_DESC, PSN.TH_FIRST_NAME, PSN.TH_MID_NAME, PSN.TH_LAST_NAME, PSN.IDNO, ");
			sql.append("PSN.EN_TITLE_DESC, PSN.EN_FIRST_NAME, PSN.EN_MID_NAME, PSN.EN_LAST_NAME, ");
			sql.append("LN.CB_PRODUCT, LN.CB_SUB_PRODUCT, LN.CB_MARKET_CODE, LN.LOAN_AMT, LN.TERM, LN.FIRST_INSTALLMENT_DATE, LN.INSTALLMENT_AMT, LN.STAMP_DUTY, ");
			sql.append("LN.INTEREST_RATE, ");
			sql.append("PMT.ACCOUNT_NO ");
		sql.append("FROM ORIG_APPLICATION_GROUP AG ");
			sql.append("JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND AP.FINAL_APP_DECISION='Approve' ");
			sql.append("JOIN BUSINESS_CLASS BCLS ON BCLS.BUS_CLASS_ID=AP.BUSINESS_CLASS_ID AND BCLS.ORG_ID='KPL' ");
			sql.append("JOIN ORIG_LOAN LN ON LN.APPLICATION_RECORD_ID=AP.APPLICATION_RECORD_ID ");
			sql.append("JOIN ORIG_LOAN_TIER LNTR ON LNTR.LOAN_ID=LN.LOAN_ID AND LNTR.SEQ=0 ");
			sql.append("JOIN ORIG_PAYMENT_METHOD PMT ON PMT.PAYMENT_METHOD_ID=LN.PAYMENT_METHOD_ID ");
			sql.append("JOIN ORIG_PERSONAL_INFO PSN ON PSN.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND PSN.PERSONAL_TYPE='A' ");
		sql.append("WHERE AG.JOB_STATE='").append(approveJobState).append("' ");
			sql.append("AND NOT EXISTS (");
				sql.append("SELECT 1 FROM INF_BATCH_LOG BLOG WHERE BLOG.APPLICATION_GROUP_ID=AG.APPLICATION_GROUP_ID AND BLOG.INTERFACE_STATUS='C' AND BLOG.INTERFACE_CODE='").append(interfaceCode).append("'");
			sql.append(")");
			String eAppSQL = ApplicationUtil.eAppSQL(" AND ( AG.SOURCE IS NULL OR AG.SOURCE NOT IN( ");
			if ( eAppSQL.length() > 0 ) {
				sql.append( eAppSQL );
				sql.append(" ) "); 
			}
		String setupLoanInfoSQL = sql.toString();
		logger.debug("setupLoanInfoSQL=" + setupLoanInfoSQL);
		
		// Construct SQL for get approver record for setup loan
		sql.setLength(0);
		sql.append("SELECT ALOG.JOB_STATE, ALOG.CREATE_BY, USR.THAI_FIRSTNAME, USR.THAI_LASTNAME ");
		sql.append("FROM ORIG_APPLICATION_LOG ALOG ");
			sql.append("JOIN US_USER_DETAIL USR ON USR.USER_NAME=ALOG.CREATE_BY ");
		sql.append("WHERE ALOG.APPLICATION_GROUP_ID=? ");
			sql.append("AND ALOG.JOB_STATE IN ('").append(escateJobState).append("','").append(approveJobState).append("') ");
			sql.append("AND ALOG.ACTION='Submit' ");
		sql.append("ORDER BY ALOG.CREATE_DATE DESC");
		String approverSQL = sql.toString();
		logger.debug("SetupLoanTask: sql=" + approverSQL);

		// Getting parameters
		String toDepartment = InfBatchProperty.getGeneralParam("LOAN_SETUP_DEPARTMENT");
		String newAction = InfBatchProperty.getGeneralParam("LOAN_SETUP_ACTION_FOR_NEW");		// Different action for each apply type
		String incAction = InfBatchProperty.getGeneralParam("LOAN_SETUP_ACTION_FOR_INC");		// Different action for each apply type
		String relationship = InfBatchProperty.getGeneralParam("LOAN_SETUP_RELATIONSHIP");
		String productGroup = InfBatchProperty.getGeneralParam("LOAN_SETUP_PRODUCT_GROUP");
		String currency = InfBatchProperty.getGeneralParam("LOAN_SETUP_CURRENCY");
		String termUnit = InfBatchProperty.getGeneralParam("LOAN_SETUP_TERM_UNIT");
		//String paymentFreq = InfBatchProperty.getGeneralParam("LOAN_SETUP_PAYMENT_FREQ");
		String businessCode = InfBatchProperty.getGeneralParam("LOAN_SETUP_BUSINESS_CODE");
		String finalityCode = InfBatchProperty.getGeneralParam("LOAN_SETUP_FINALITY_CODE");
		String branchCode = InfBatchProperty.getGeneralParam("LOAN_SETUP_BRANCH_CODE");
		String accountType = InfBatchProperty.getGeneralParam("LOAN_SETUP_ACCOUNT_TYPE");
		String penalty = InfBatchProperty.getGeneralParam("LOAN_SETUP_PENALTY");
		String intIndex = InfBatchProperty.getGeneralParam("LOAN_SETUP_INT_INDEX");
		String paymentCal = InfBatchProperty.getGeneralParam("LOAN_SETUP_PAYMENT_CAL");

		InfBatchLogDataM infBatchLog = new InfBatchLogDataM();
		infBatchLog.setCreateBy(SYSTEM_USER_ID);
		infBatchLog.setInterfaceCode(interfaceCode);
		String appDate = Formatter.display(InfBatchProperty.getDate(), Formatter.EN, Formatter.Format.ddMMyyyy);
		Connection conn = null;
		PreparedStatement setupLoanInfoStmt = null; 
		PreparedStatement approverStmt = null; 
		ResultSet setupLoanInfoResult = null;
		ResultSet approverResult = null;
		String executeResult = "";
		StringBuilder stringBuilder = new StringBuilder();
		try {			
			conn = getConnection();
			approverStmt = conn.prepareStatement(approverSQL);
			setupLoanInfoStmt = conn.prepareStatement(setupLoanInfoSQL);
			setupLoanInfoResult = setupLoanInfoStmt.executeQuery();
			while(setupLoanInfoResult.next()) {
				
				String appGroupNo = setupLoanInfoResult.getString("APPLICATION_GROUP_NO");
				String appGroupId = setupLoanInfoResult.getString("APPLICATION_GROUP_ID");
				logger.debug("Application Group Id: " + appGroupId);
				
				//check mandatory fields
				String checkNull = "";
				if(Util.empty(setupLoanInfoResult.getString("ACCOUNT_NO")))
				{ checkNull += " Account No is NULL. ";}
				if(Util.empty(setupLoanInfoResult.getBigDecimal("INSTALLMENT_AMT")))
				{ checkNull += " Installment Amount is NULL. ";}
				if(Util.empty(setupLoanInfoResult.getBigDecimal("INTEREST_RATE")))
				{ checkNull += " Interest Rate is NULL. ";}
				if(Util.empty(setupLoanInfoResult.getTimestamp("FIRST_INSTALLMENT_DATE")))
				{ checkNull += " First Installment Date is NULL. ";}
				if(Util.empty(setupLoanInfoResult.getBigDecimal("LOAN_AMT")))
				{ checkNull += " Loan Amount is NULL. ";}
				if(Util.empty(setupLoanInfoResult.getBigDecimal("TERM")))
				{ checkNull += " Term is NULL. ";}
				
				if(!Util.empty(checkNull))
				{
					infBatchLog.setApplicationGroupId(appGroupId);
					infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
					infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
					infBatchLog.setLogMessage(checkNull);
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_ERROR);
					dao.insertInfBatchLog(infBatchLog);
					executeResult += "[ERROR] " + appGroupNo + " " + checkNull + "\n";
				}
				else
				{
					String nameTh = setupLoanInfoResult.getString("TH_TITLE_DESC") + " " + setupLoanInfoResult.getString("TH_FIRST_NAME") + " " + FormatUtil.displayText(setupLoanInfoResult.getString("TH_MID_NAME")) + " " + setupLoanInfoResult.getString("TH_LAST_NAME"); 
					String nameEn = setupLoanInfoResult.getString("EN_TITLE_DESC") + " " + setupLoanInfoResult.getString("EN_FIRST_NAME") + " " + FormatUtil.displayText(setupLoanInfoResult.getString("EN_MID_NAME")) + " " + setupLoanInfoResult.getString("EN_LAST_NAME");
					String installmentDate = getTimestampAsString(setupLoanInfoResult.getTimestamp("FIRST_INSTALLMENT_DATE"), "");
					String paymentFreq = "1MA" + StringUtils.stripStart(installmentDate.substring(0, 2),"0");
					
					// Finding approver and analyst
					approverStmt.setString(1, appGroupId);
					approverResult = approverStmt.executeQuery();
					String approverId = "";
					String approverName = "";
					String analystId = "";
					String analystName = "";
					String nm0901user = null;
					String nm0602user = null;
					String nm0901name = null;
					String nm0602name = null;
					while (approverResult.next()) {
						String jobState = approverResult.getString("JOB_STATE");
						if ("NM0901".equals(jobState) && null==nm0901user) {
							nm0901user = approverResult.getString("CREATE_BY");
							nm0901name = approverResult.getString("THAI_FIRSTNAME") + " " + approverResult.getString("THAI_LASTNAME");
						}
						else if ("NM0602".equals(jobState)) {
							nm0602user = approverResult.getString("CREATE_BY");
							nm0602name = approverResult.getString("THAI_FIRSTNAME") + " " + approverResult.getString("THAI_LASTNAME");
						}
					}
					if (null != nm0901user)
						if (null != nm0602user) {
							approverId = nm0602user;
							approverName = nm0602name;
							analystId = nm0901user;
							analystName = nm0901name;
						}
						else {
							approverId = nm0901user;
							approverName = nm0901name;
						}
					approverResult.close();
					approverResult = null;
					
						// Construct output
						stringBuilder.append(appGroupNo).append("|");	// 0
						stringBuilder.append(appGroupNo).append("|"); // 1.1
						stringBuilder.append(toDepartment).append("|"); // 1.2
						stringBuilder.append(appDate).append("|"); // 1.3
						String applyType = getString(setupLoanInfoResult.getString("APPLICATION_TYPE"), "");
						if ("NEW".equals(applyType))
							stringBuilder.append(newAction); // 1.4
						else if ("INC".equals(applyType))
							stringBuilder.append(incAction); // 1.4
						stringBuilder.append("|");
						stringBuilder.append("|"); // 1.5
						stringBuilder.append(relationship).append("|"); // 2.1
						stringBuilder.append(getString(setupLoanInfoResult.getString("CIS_NO"), "")).append("|"); // 3.1
						stringBuilder.append(nameTh).append("|"); // 3.2
						stringBuilder.append(productGroup).append("|"); // 4.1
						stringBuilder.append(getString(setupLoanInfoResult.getString("CB_PRODUCT"), "")).append("|"); // 4.2
						stringBuilder.append(getString(setupLoanInfoResult.getString("CB_SUB_PRODUCT"), "")).append("|"); // 4.3
						stringBuilder.append(getString(setupLoanInfoResult.getString("CB_MARKET_CODE"), "")).append("|"); // 4.4
						stringBuilder.append(nameEn).append("|"); // 5.x ACCT_TITILE_ENGLISH
						stringBuilder.append(nameTh).append("|"); // 5.1 ACCT_TITILE_THAI
						stringBuilder.append(getString(setupLoanInfoResult.getString("IDNO"), "")).append("|"); // 5.2
						stringBuilder.append(getBigDecimalAsString(setupLoanInfoResult.getBigDecimal("LOAN_AMT"), -1, "")).append("|"); // 5.3
						stringBuilder.append("|"); // 5.4
						stringBuilder.append(getBigDecimalAsString(setupLoanInfoResult.getBigDecimal("LOAN_AMT"), -1, "")).append("|"); // 5.5
						stringBuilder.append(currency).append("|"); // 5.6
						stringBuilder.append(appDate).append("|"); // 5.7
						stringBuilder.append(getBigDecimalAsString(setupLoanInfoResult.getBigDecimal("TERM"), 0, "")).append("|"); // 5.8
						stringBuilder.append(termUnit).append("|"); // 5.9
						stringBuilder.append(appDate).append("|"); // 5.10
						stringBuilder.append(paymentFreq).append("|"); // 5.11
						stringBuilder.append(installmentDate).append("|"); // 5.12
						stringBuilder.append(getString(setupLoanInfoResult.getString("ACCOUNT_NO"), "")).append("|"); // 5.13
						stringBuilder.append(businessCode).append("|"); // 6.1
						stringBuilder.append(getString(setupLoanInfoResult.getString("PROJECT_CODE"), "")).append("|"); // 6.2
						stringBuilder.append(finalityCode).append("|"); // 6.3
						stringBuilder.append(branchCode).append("|"); // 7.1
						stringBuilder.append(accountType).append("|"); // 7.2
						stringBuilder.append(penalty).append("|"); // 7.3
						stringBuilder.append(appDate).append("|"); // 8.1
						stringBuilder.append(intIndex).append("|"); // 8.2
						stringBuilder.append(getBigDecimalAsString(setupLoanInfoResult.getBigDecimal("INTEREST_RATE"), -1, "")).append("|"); // 8.3
						stringBuilder.append(appDate).append("|"); // 8.4
						stringBuilder.append(paymentCal).append("|"); // 8.5
						stringBuilder.append(getBigDecimalAsString(setupLoanInfoResult.getBigDecimal("INSTALLMENT_AMT"), -1, "")).append("|"); // 8.6
						stringBuilder.append(getBigDecimalAsString(setupLoanInfoResult.getBigDecimal("LOAN_AMT"), -1, "")).append("|"); // 9.1
						stringBuilder.append("|"); // 9.2
						stringBuilder.append("|"); // 9.3
						stringBuilder.append(getString(setupLoanInfoResult.getString("ACCOUNT_NO"), "")).append("|"); // 9.4
						BigDecimal loanAmt = setupLoanInfoResult.getBigDecimal("LOAN_AMT");
						if (null == loanAmt)
							loanAmt = BigDecimal.ZERO;
						BigDecimal stampDuty = setupLoanInfoResult.getBigDecimal("STAMP_DUTY");
						if (null == stampDuty)
							stampDuty = BigDecimal.ZERO;
						BigDecimal transferAmt = loanAmt.subtract(stampDuty);
						stringBuilder.append(getBigDecimalAsString(transferAmt, -1, "")).append("|"); // 9.5
						stringBuilder.append("|"); // 9.6
						stringBuilder.append("|"); // 9.7
						stringBuilder.append("|"); // 9.8
						stringBuilder.append("|"); // 9.9
						stringBuilder.append("|"); // 10.1
						stringBuilder.append(getBigDecimalAsString(setupLoanInfoResult.getBigDecimal("STAMP_DUTY"), -1, "")).append("|"); // 10.2
						stringBuilder.append("|"); // 10.3
						stringBuilder.append("|"); // 10.4
						stringBuilder.append("|"); // 10.5
						stringBuilder.append("|"); // 10.6
						stringBuilder.append("|"); // 10.7
						stringBuilder.append("|"); // 10.8
						stringBuilder.append("|"); // 11.1
						stringBuilder.append("|"); // 11.2
						stringBuilder.append("|"); // 11.3
						stringBuilder.append(approverName).append("|"); // 11.4
						stringBuilder.append(approverId).append("|"); // 11.5
						stringBuilder.append("|"); // 11.6
						stringBuilder.append(analystName).append("|"); // 11.7
						stringBuilder.append(analystId).append("|"); // 11.8
						stringBuilder.append("|"); // 11.9
						stringBuilder.append("|"); // 12.1
						stringBuilder.append("|"); // 12.2
						stringBuilder.append("|"); // 12.3
						stringBuilder.append("|"); // 12.4
						stringBuilder.append("|"); // 12.5
						stringBuilder.append("\n");
					
					// Add record into INF_BATCH_LOG
					infBatchLog.setApplicationGroupId(appGroupId);
					infBatchLog.setInterfaceDate(InfBatchProperty.getTimestamp());
					infBatchLog.setCreateDate(InfBatchProperty.getTimestamp());
					infBatchLog.setInterfaceStatus(InfBatchConstant.STATUS_COMPLETE);
					infBatchLog.setLogMessage(null);
					dao.insertInfBatchLog(infBatchLog);
					executeResult += "[OK] " + appGroupNo + "\n";
				}
				
			}
			
			//INSERT Content to INF_EXPORT
			insertInfExport(stringBuilder.toString(), interfaceCode);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try {
				if (null != approverResult)
					approverResult.close();
				if (null != approverStmt)
					approverStmt.close();
				if (null != setupLoanInfoResult)
					setupLoanInfoResult.close();
				if (null != setupLoanInfoStmt)
					setupLoanInfoStmt.close();
				if (null != conn)
					conn.close();
			}
			catch (Exception e) {
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		logger.debug("Text Content >> " + stringBuilder);
		return executeResult;
	}

	@Override
	public InfBatchLogDataM getSetupLoanInfBatchLogByRefId(String refId) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InfBatchLogDataM infBatchLogDataM = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT INTERFACE_LOG_ID,APPLICATION_RECORD_ID,INTERFACE_CODE,INTERFACE_DATE ")
				.append(" ,CREATE_DATE,CREATE_BY,INTERFACE_STATUS,REF_ID,REF_SEQ,SYSTEM01,SYSTEM02,APPLICATION_GROUP_ID, LIFE_CYCLE ")
				.append(" ,LOG_MESSAGE,SYSTEM03,SYSTEM04,SYSTEM05,SYSTEM06,SYSTEM07,SYSTEM08,SYSTEM09,SYSTEM10 ")
				.append("FROM INF_BATCH_LOG ")
				.append("WHERE REF_ID=? AND INTERFACE_CODE=? ")
				.append("ORDER BY CREATE_DATE DESC ")
				.append("FETCH FIRST 1 ROWS ONLY ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int cnt = 1;
			ps.setString(cnt++, refId);
			ps.setString(cnt++, InfBatchProperty.getInfBatchConfig("SETUP_LOAN_RESULT_MODULE_ID"));
			rs = ps.executeQuery();
			if (rs.next()) {
				infBatchLogDataM = new InfBatchLogDataM();
				infBatchLogDataM.setInterfaceLogId(rs.getString("INTERFACE_LOG_ID"));
				infBatchLogDataM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				infBatchLogDataM.setInterfaceCode(rs.getString("INTERFACE_CODE"));
				infBatchLogDataM.setInterfaceDate(rs.getTimestamp("INTERFACE_DATE"));
				infBatchLogDataM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				infBatchLogDataM.setCreateBy(rs.getString("CREATE_BY"));
				infBatchLogDataM.setInterfaceStatus(rs.getString("INTERFACE_STATUS"));
				infBatchLogDataM.setRefId(rs.getString("REF_ID"));
				infBatchLogDataM.setRefSeq(rs.getString("REF_SEQ"));
				infBatchLogDataM.setSystem01(rs.getString("SYSTEM01"));
				infBatchLogDataM.setSystem02(rs.getString("SYSTEM02"));
				infBatchLogDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				infBatchLogDataM.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				infBatchLogDataM.setLogMessage(rs.getString("LOG_MESSAGE"));
				infBatchLogDataM.setSystem03(rs.getString("SYSTEM03"));
				infBatchLogDataM.setSystem04(rs.getString("SYSTEM04"));
				infBatchLogDataM.setSystem05(rs.getString("SYSTEM05"));
				infBatchLogDataM.setSystem06(rs.getString("SYSTEM06"));
				infBatchLogDataM.setSystem07(rs.getString("SYSTEM07"));
				infBatchLogDataM.setSystem08(rs.getString("SYSTEM08"));
				infBatchLogDataM.setSystem09(rs.getString("SYSTEM09"));
				infBatchLogDataM.setSystem10(rs.getString("SYSTEM10"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return infBatchLogDataM;
	}
	
	public void insertInfExport(String contentMessage,String MODULE_ID) throws InfBatchException 
	{
		String SYSTEM_USER_ID = InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
		PreparedStatement ps = null;
		Connection conn = null;
		try {			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");//INF_EXPORT_PK.NEXTVAL
			sql.append(" INSERT INTO INF_EXPORT ");
			sql.append(" (EXPORT_ID, MODULE_ID,   ");
			sql.append(" DATA_DATE, CONTENT, UPDATE_DATE, UPDATE_BY) ");
			sql.append(" VALUES (INF_EXPORT_PK.NEXTVAL,?,?,?,?,?) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, MODULE_ID);
			ps.setTimestamp(2, ApplicationDate.getTimestamp());
			ps.setString(3, contentMessage);
			ps.setTimestamp(4, ApplicationDate.getTimestamp());
			ps.setString(5, SYSTEM_USER_ID);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public ArrayList<String> getInfExportKMobile(String MODULE_ID) throws Exception {
		ArrayList<String> dataList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String CONTENT = null;
		StringBuilder str = new StringBuilder();
		String appGroupIdList = "";
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM INF_EXPORT INF ");
			sql.append(" WHERE MODULE_ID = ? ");
			sql.append(" AND TRUNC(DATA_DATE) = ? ");
			sql.append(" AND NOT EXISTS ( ");
			sql.append(" SELECT 1 FROM INF_BATCH_LOG BLOG WHERE ");
			sql.append(" BLOG.APPLICATION_GROUP_ID = INF.FILE_NAME AND BLOG.INTERFACE_STATUS = 'C' ");
			sql.append(" AND BLOG.INTERFACE_CODE = ? AND TRUNC(BLOG.CREATE_DATE) = ? ) ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, MODULE_ID);
			Date APPLICATION_DATE = getApplicationDate();
			ps.setDate(2, APPLICATION_DATE);
			ps.setString(3, MODULE_ID);
			ps.setDate(4, APPLICATION_DATE);
			rs = ps.executeQuery();
			while(rs.next()){
				
				String appGroupId = rs.getString("FILE_NAME");
				if(!Util.empty(appGroupId))
				{
					appGroupIdList = appGroupIdList + appGroupId + "," ;
				
					Clob clob = rs.getClob("CONTENT");	
					if(null != clob){
						str.append(clob.getSubString(1, (int)clob.length()));
						str.append("\n");
					}
				}
				
			}
			CONTENT = str.toString();
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		
		//Remove trailing comma
		appGroupIdList = appGroupIdList.replaceAll(",$", "");
		
		dataList.add(appGroupIdList);
		dataList.add(CONTENT);
		
		return dataList;
	}


}
