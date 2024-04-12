package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLCommisssionException;
import com.eaf.orig.ulo.pl.model.app.SATeamDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionAppDetialDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionDataM;
import com.eaf.orig.ulo.pl.model.app.SAUserCommissionDetialsDataM;

public class PLCommisionDAOImpl extends OrigObjectDAO implements PLCommisionDAO {
	private static Logger log = Logger.getLogger(PLCommisionDAOImpl.class);

	@Override
	public Vector<SAUserCommissionAppDetialDataM> selectApplicaionCalulateCommisson(java.util.Date cardLinkDate) throws PLCommisssionException {
		Vector<SAUserCommissionAppDetialDataM> vResult = new Vector<SAUserCommissionAppDetialDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("select APP.APPLICATION_NO ");
			sql.append(" from orig_application app,ac_account account,orig_cash_transfer tran ");
			sql.append(" where APP.APPLICATION_NO=ACCOUNT.APPLICATION_NO  ");
			sql.append("   and ACCOUNT.CARDLINK_STATUS='0' ");
			sql.append("  and  trunc(ACCOUNT.CARDLINK_DATE)= trunc(?)  ");
			sql.append("  and APP.APPLY_CHANNEL='02' ");
			sql.append( " and     (SELECT BUS_GRP_ID FROM BUS_CLASS_GRP_MAP bgm ");
			sql.append( "          WHERE     bgm.BUS_CLASS_ID = APP.BUSINESS_CLASS_ID ");
			sql.append( "             AND bgm.bus_grp_id IN ('KEC_NORMAL', 'KEC_BUNDLING')) is not null");
			sql.append("  AND APP.APPLICATION_RECORD_ID = TRAN.APPLICATION_RECORD_ID(+)  ");
			sql.append(" AND TRAN.CASH_TRANSFER_TYPE <> 'CD5'  ");			 
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("cardLinkDate=" + cardLinkDate);
			ps = conn.prepareStatement(dSql);
			logger.debug("dSql=" + dSql);
			rs = null;
			ps.setDate(1, parseDate(cardLinkDate));
			rs = ps.executeQuery();
			while (rs.next()) {
				SAUserCommissionAppDetialDataM saUserComApp = new SAUserCommissionAppDetialDataM();
				saUserComApp.setApplicationNo(rs.getString(1));
				vResult.add(saUserComApp);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}

	@Override
	public int createUserCommissionAppDetial(Vector<SAUserCommissionAppDetialDataM> vectorUserAppComision) throws PLCommisssionException {
		// Vector<SAUserCommissionAppDetialDataM> vResult=new
		// Vector<SAUserCommissionAppDetialDataM>();
		if (vectorUserAppComision.size() == 0) {
			log.info("No CommisionApp Add ");
			return 0;
		}
		PreparedStatement ps = null;
		Connection conn = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("Insert into  SA_USER_COM_APP_DETAIL ");
			sql.append("(USER_NAME, APPLICATION_NO, CREDIT_LINE, SALE_TYPE, AMOUNT,");
			sql.append(" POINT, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,PERIOD_NO)  ");
			sql.append(" VALUES(?,?,?,?,?   ,?,SYSDATE,?,SYSDATE,? ,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);

			for (int i = 0; i < vectorUserAppComision.size(); i++) {
				int index = 1;
				SAUserCommissionAppDetialDataM userCommision = vectorUserAppComision.get(i);
				ps.setString(index++, userCommision.getUserName());
				ps.setString(index++, userCommision.getApplicationNo());
				ps.setBigDecimal(index++, userCommision.getCreditLine());
				ps.setString(index++, userCommision.getSaleType());
				ps.setBigDecimal(index++, userCommision.getAmount());
				ps.setBigDecimal(index++, userCommision.getPoint());
				ps.setString(index++, userCommision.getCreateBy());
				ps.setString(index++, userCommision.getUpdateBy());
				ps.setString(index++, userCommision.getPeriodNo());
				ps.addBatch();
			}
			ps.executeBatch();
			return vectorUserAppComision.size();
		} catch (Exception e) {
			log.fatal("Error Exception", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error Exception", e);
				throw new PLCommisssionException(e.getMessage());
			}
		}
	}

	@Override
	public Vector<SAUserCommissionDataM> selectCommisionPeriod(String period) throws PLCommisssionException {
		Vector<SAUserCommissionDataM> vResult = new Vector<SAUserCommissionDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT USER_TEAM.USER_ID,calCom.period_no,nvl(calCom.comAmount,0),nvl(calCom.noOfApp,0)");
			sql.append(" FROM ms_sa_user_team user_team, (  SELECT comm_app.USER_NAME,comm_app.period_no,SUM (comm_app.amount) comAmount,");
			sql.append("             COUNT (APP.APPLICATION_NO) noOfApp FROM sa_user_com_app_detail comm_app, orig_application app ");
			sql.append("           WHERE APP.APPLICATION_NO = comm_app.APPLICATION_NO  ");
			sql.append("           and comm_app.period_no=? ");
			sql.append("        GROUP BY comm_app.user_name, period_no) calCom");
			sql.append("  WHERE user_team.user_id = calCom.user_name(+)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("period=" + period);
			ps = conn.prepareStatement(dSql);
			logger.debug("dSql=" + dSql);
			rs = null;
			ps.setString(1, period);
			rs = ps.executeQuery();
			while (rs.next()) {
				SAUserCommissionDataM saUserComission = new SAUserCommissionDataM();
				saUserComission.setUserName(rs.getString(1));
				saUserComission.setPeriodNo(period);
				saUserComission.setIncomeAmount(rs.getBigDecimal(3));
				saUserComission.setIncomeType(OrigConstant.COMMISSION_INCOME_TYPE_COM);
				saUserComission.setTotalApplication(rs.getInt(4));
				vResult.add(saUserComission);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}

	@Override
	public Vector<SAUserCommissionDetialsDataM> selectCommisionPeriodDetail(String period) throws PLCommisssionException {
		Vector<SAUserCommissionDetialsDataM> vResult = new Vector<SAUserCommissionDetialsDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT user_name,credit_line_group,application_type, COUNT (application_no) no_of_app,SUM (amount) amt");
			sql.append(" FROM (SELECT COMM_APP.USER_NAME,APP.APPLICATION_NO, ");
			sql.append("          (SELECT param_code  FROM ms_report_param  ");
			sql.append("           WHERE     param_type LIKE 'R8_CREDIT_LINE_GROUP' AND APP.FINAL_CREDIT_LIMIT BETWEEN TO_NUMBER (param_value2) AND TO_NUMBER (param_value3)) credit_line_group");
		    sql.append("           ,  DECODE (bgm.BUS_GRP_ID,'KEC_NORMAL', 'Generic','KEC_BUNDLING', 'Bundle')  application_type ");		     
		    sql.append("           , COMM_APP.AMOUNT,COMM_APP.PERIOD_NO  ");
		    sql.append("           FROM SA_USER_COM_APP_DETAIL comm_app, orig_application app ,BUS_CLASS_GRP_MAP bgm  ");
		    sql.append("       WHERE     APP.APPLICATION_NO = COMM_APP.APPLICATION_NO AND COMM_APP.PERIOD_NO =?)");
		    sql.append("            AND bgm.BUS_CLASS_ID = APP.BUSINESS_CLASS_ID AND bgm.bus_grp_id IN ('KEC_NORMAL', 'KEC_BUNDLING') ");		    		    
		    sql.append(" GROUP BY user_name, credit_line_group, application_type ");
		    
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("priod=" + period);
			ps = conn.prepareStatement(dSql);
			logger.debug("dSql=" + dSql);
			rs = null;
			ps.setString(1, period);
			rs = ps.executeQuery();
			while (rs.next()) {
				SAUserCommissionDetialsDataM commDetail = new SAUserCommissionDetialsDataM();
                 commDetail.setUserName(rs.getString(1));
                 commDetail.setCreditLineGroup(rs.getString(2));
                 commDetail.setApplicationType(rs.getString(3));
                 commDetail.setNoOfApplication(rs.getInt(4));
                 commDetail.setAmount(rs.getBigDecimal(5));
                 commDetail.setUpdateBy(OrigConstant.SYSTEM);
                 commDetail.setCreateBy(OrigConstant.SYSTEM);                  
				vResult.add(commDetail);
			}
			return vResult;
		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}

	@Override
	public int createUserCommissionData(Vector<SAUserCommissionDataM> vectorUserComission) throws PLCommisssionException {
		if (vectorUserComission==null||vectorUserComission.size() == 0) {
			log.info("No Commision Add ");
			return 0;
		}
		log.debug("save Size=" + vectorUserComission.size());
		for (int i = 0; i < vectorUserComission.size(); i++) {

			SAUserCommissionDataM userCommision = vectorUserComission.get(i);
			
			if (0 == updateUserCommissionData(userCommision)) {
				insertUserCommissionData(userCommision);
			}
		}
		return 0;
	}

	private int updateUserCommissionData(SAUserCommissionDataM userCommission) throws PLCommisssionException {
		Connection conn = getConnection();
		StringBuilder sql = new StringBuilder("");
		sql.append("UPDATE SA_USER_COMMISSION ");
		sql.append(" SET INCOME_AMT=? ");
		sql.append("  ,UPDATE_DATE=SYSDATE, UPDATE_BY=?, COM_POINT=?, TOTAL_APPLICATION=?  ");
		sql.append(" where USER_NAME=? AND  PERIOD_NO=?  AND INCOME_TYPE=? ");
		PreparedStatement ps = null;
		String dSql = String.valueOf(sql);
		log.debug("Sql=" + dSql);
		try {
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setBigDecimal(index++, userCommission.getIncomeAmount());
			ps.setString(index++, userCommission.getUpdateBy());
			ps.setBigDecimal(index++, userCommission.getComPoint());
			ps.setInt(index++, userCommission.getTotalApplication());
			ps.setString(index++, userCommission.getUserName());
			ps.setString(index++, userCommission.getPeriodNo());
			ps.setString(index++, userCommission.getIncomeType());
			return ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error Exception", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error Exception", e);
				throw new PLCommisssionException(e.getMessage());
			}
		}
	}

	private int insertUserCommissionData(SAUserCommissionDataM userCommission) throws PLCommisssionException {
		Connection conn = getConnection();
		StringBuilder sql = new StringBuilder("");
		sql.append("Insert into SA_USER_COMMISSION");
		sql.append("(USER_NAME, INCOME_TYPE, INCOME_AMT, PERIOD_NO, CREATE_DATE, ");
		sql.append(" CREATE_BY, UPDATE_DATE, UPDATE_BY, COM_POINT, TOTAL_APPLICATION)  ");
		sql.append(" VALUES(?,?,?,?,SYSDATE   ,?,SYSDATE,?,?,? ) ");
		PreparedStatement ps = null;
		String dSql = String.valueOf(sql);
		log.debug("Sql=" + dSql);
		try {
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setString(index++, userCommission.getUserName());
			ps.setString(index++, userCommission.getIncomeType());
			ps.setBigDecimal(index++, userCommission.getIncomeAmount());
			ps.setString(index++, userCommission.getPeriodNo());
			ps.setString(index++, userCommission.getCreateBy());
			ps.setString(index++, userCommission.getUpdateBy());
			ps.setBigDecimal(index++, userCommission.getComPoint());
			ps.setInt(index++, userCommission.getTotalApplication());
			return ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error Exception", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error Exception", e);
				throw new PLCommisssionException(e.getMessage());
			}
		}
	}
    
	@Override
	public int deleteUserCommissionDetailData( String period) throws PLCommisssionException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE FROM  SA_USER_COMMISSION_DETAIL ");			 			 
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql); 
			//ps.setString(1, period);
			return ps.executeUpdate();
		} catch (Exception e) {
			log.fatal("Error Exception", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error Exception", e);
				throw new PLCommisssionException(e.getMessage());
			}
		}
	}
	
	@Override
	public int createUserCommissionDetailsData(Vector<SAUserCommissionDetialsDataM> vectorUserComission) throws PLCommisssionException {
		if (vectorUserComission.size() == 0) {
			log.info("No Commision data Add ");
			return 0;
		}
		PreparedStatement ps = null;
		Connection conn = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("Insert into  SA_USER_COMMISSION_DETAIL ");
			sql.append("(USER_NAME, CREDIT_LINE_GROUP, APPLICATION_TYPE, NO_OF_APPLICATION, AMOUNT, ");
			sql.append("  CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY)  ");
			sql.append(" VALUES(?,?,?,?,?   ,SYSDATE,?,SYSDATE,?  ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);

			for (int i = 0; i < vectorUserComission.size(); i++) {
				int index = 1;
				SAUserCommissionDetialsDataM userCommision = vectorUserComission.get(i);
				ps.setString(index++, userCommision.getUserName());
				ps.setString(index++, userCommision.getCreditLineGroup());
				ps.setString(index++, userCommision.getApplicationType());
				ps.setInt(index++, userCommision.getNoOfApplication());
				ps.setBigDecimal(index++, userCommision.getAmount());
				ps.setString(index++, userCommision.getCreateBy());
				ps.setString(index++, userCommision.getUpdateBy());
				ps.addBatch();
			}
			ps.executeBatch();
			return vectorUserComission.size();
		} catch (Exception e) {
			log.fatal("Error Exception", e);
			throw new PLCommisssionException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("Error Exception", e);
				throw new PLCommisssionException(e.getMessage());
			}
		}
	}

	@Override
	public SATeamDataM getTeamData(String period, String userId) throws PLCommisssionException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		SATeamDataM result = new SATeamDataM();
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     TEAM_MAS.POSITION_ID, ");
				SQL.append("     TEAM_CAL.AMOUNT, ");
				SQL.append("     TEAM_CAL.NO_OF_APP ");
				SQL.append(" FROM ");
				SQL.append("     MS_SA_USER_TEAM TEAM_MAS , ");
				SQL.append("     ( ");
				SQL.append("         SELECT ");
				SQL.append("             TEAM.TEAM_ID, ");
				SQL.append("             SUM (COMM_APP.AMOUNT) AMOUNT, ");
				SQL.append("             COUNT(COMM_APP.APPLICATION_NO) NO_OF_APP ");
				SQL.append("         FROM ");
				SQL.append("             MS_SA_USER_TEAM TEAM, ");
				SQL.append("             SA_USER_COM_APP_DETAIL COMM_APP ");
				SQL.append("         WHERE ");
				SQL.append("             COMM_APP.USER_NAME = TEAM.USER_ID ");
				SQL.append("         AND COMM_APP.PERIOD_NO = ? ");
				SQL.append("         GROUP BY ");
				SQL.append("             TEAM.TEAM_ID ");
				SQL.append("     ) ");
				SQL.append("     TEAM_CAL ");
				SQL.append(" WHERE ");
				SQL.append("     TEAM_CAL.TEAM_ID=TEAM_MAS.TEAM_ID ");
				SQL.append(" AND TEAM_MAS.USER_ID=? ");
			
			String dSql = String.valueOf(SQL);
			
			logger.debug("Sql=" + dSql);
			
			logger.debug("priod=" + period);
			logger.debug("userId=" + userId);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			int index = 1;
			
			ps.setString(index++, period);
			ps.setString(index++, userId);
			rs = ps.executeQuery();
			if(rs.next()){
				result = new SATeamDataM();
				result.setTeamName(rs.getString(1));
				result.setTeamAmount(rs.getBigDecimal(2));
				result.setTotalApp(rs.getInt(3));
			}
		}catch(Exception e){
			logger.error("Error >> ", e);
			throw new PLCommisssionException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error("Error >> ", e);
			}
		}
		return result;
	}
}
