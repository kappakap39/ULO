package com.eaf.inf.batch.ulo.card.stack.notification.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackInfoM;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackRequestDataM;
import com.eaf.orig.ulo.model.membership.RunningParamStackDataM;

public class RunningParamStackDAOImpl extends InfBatchObjectDAO implements RunningParamStackDAO {
	private static transient Logger logger = Logger.getLogger(RunningParamStackDAOImpl.class);

	@Override
	public ArrayList<RunningParamStackRequestDataM> getCardParams() throws InfBatchException {
		ArrayList<RunningParamStackRequestDataM> listRunningParamStackRequestDataM = new ArrayList<RunningParamStackRequestDataM>();
		Connection conn = null;

		try {
			conn = getConnection();
			ArrayList<String> listParamType = getParamType(conn);
			for (String paramType : listParamType) {
				logger.debug("paramType = " + paramType);
				RunningParamStackRequestDataM runningParamStackRequestDataM = new RunningParamStackRequestDataM(paramType);
				getRunningParamStackCardParams(conn, paramType, runningParamStackRequestDataM.getRunningParamStackInfoM());
				getRunningParamStackTempCardParams(conn, paramType, runningParamStackRequestDataM.getRunningParamStackInfoM());
				getRunningParamStackWarningCardParams(conn, paramType, runningParamStackRequestDataM.getRunningParamStackInfoM());
				listRunningParamStackRequestDataM.add(runningParamStackRequestDataM);
			}
		} catch (Exception e) {
			logger.fatal(e);
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return listRunningParamStackRequestDataM;
	}

	private void getRunningParamStackTempCardParams(Connection conn, String paramType, ArrayList<RunningParamStackInfoM> listRunningParamStackInfoM)
			throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try {

			sql.append(" SELECT PARAM_TYPE, COUNT(PARAM_TYPE) AS CNT_PARAM_TYPE  FROM ORIG_APP.RUNNING_PARAM_STACK_TEMP ");
			sql.append(" WHERE PARAM_TYPE IS NOT NULL AND PARAM_TYPE = ? GROUP BY PARAM_TYPE ");

			logger.debug("SQL : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, paramType);
			rs = ps.executeQuery();
			while (rs.next()) {
				String PARAM_TYPE = rs.getString("PARAM_TYPE");
				int numOfParamType = rs.getInt("CNT_PARAM_TYPE");
				RunningParamStackInfoM runningParamStackInfoM = new RunningParamStackInfoM(InfBatchConstant.RUNNING_PARAM_STACK.PROCESS_MIGRATE,
						PARAM_TYPE, numOfParamType);
				listRunningParamStackInfoM.add(runningParamStackInfoM);
			}

		} catch (SQLException e) {
			logger.fatal("ERROR ", e);
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
		}

	}

	private void getRunningParamStackCardParams(Connection conn, String paramType, ArrayList<RunningParamStackInfoM> listRunningParamStackInfoM)
			throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try {

			sql.append(" SELECT PARAM_TYPE, COUNT(PARAM_TYPE) AS CNT_PARAM_TYPE  FROM ORIG_APP.RUNNING_PARAM_STACK ");
			sql.append(" WHERE PARAM_TYPE IS NOT NULL AND PARAM_TYPE = ? GROUP BY PARAM_TYPE ");

			logger.debug("SQL : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, paramType);
			rs = ps.executeQuery();
			while (rs.next()) {
				String PARAM_TYPE = rs.getString("PARAM_TYPE");
				int numOfParamType = rs.getInt("CNT_PARAM_TYPE");
				RunningParamStackInfoM runningParamStackInfoM = new RunningParamStackInfoM(InfBatchConstant.RUNNING_PARAM_STACK.PROCESS_PURGE,
						PARAM_TYPE, numOfParamType);
				listRunningParamStackInfoM.add(runningParamStackInfoM);
			}

		} catch (SQLException e) {
			logger.fatal("ERROR ", e);
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
		}

	}

	private void getRunningParamStackWarningCardParams(Connection conn, String paramType, ArrayList<RunningParamStackInfoM> listRunningParamStackInfoM)
			throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		RunningParamStackInfoM runningParamStackInfoM = null;
		StringBuilder sql = new StringBuilder();
		try {

			sql.append(" SELECT PARAM_TYPE, COUNT(PARAM_TYPE) AS CNT_PARAM_TYPE  FROM ORIG_APP.RUNNING_PARAM_STACK ");
			sql.append(" WHERE  STATUS IN ('N', 'D') AND PARAM_TYPE = ?  GROUP BY PARAM_TYPE  ");

			logger.debug("SQL : " + sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, paramType);
			rs = ps.executeQuery();
			while (rs.next()) {

				String PARAM_TYPE = rs.getString("PARAM_TYPE");
				int numOfParamType = rs.getInt("CNT_PARAM_TYPE");
				runningParamStackInfoM = new RunningParamStackInfoM(InfBatchConstant.RUNNING_PARAM_STACK.PROCESS_WARNING, PARAM_TYPE, numOfParamType);
				listRunningParamStackInfoM.add(runningParamStackInfoM);
			}

		} catch (SQLException e) {
			logger.fatal("ERROR ", e);
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
		}

	}

	@Override
	public int deleteRunningParamStackTemp(Connection conn, String paramType) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" DELETE FROM RUNNING_PARAM_STACK_TEMP RP WHERE RP.PARAM_TYPE = ? ");
			logger.debug("SQL_CMD :: " + sql.toString());

			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, paramType);
			result = ps.executeUpdate();

		} catch (SQLException e) {
			logger.error("Error >> ", e);
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
		return result;
	}

	@Override
	public int migrateRunningParamStack(Connection conn, String paramType) throws InfBatchException {
		String userSystem =  InfBatchProperty.getInfBatchConfig("SYSTEM_USER_ID");
		PreparedStatement ps = null;
		ResultSet rs = null;
		int execRows = 0;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" INSERT INTO RUNNING_PARAM_STACK  (PARAM_ID,PARAM_TYPE,PARAM_DESC,PARAM_VALUE,STATUS, ");
			sql.append(" CREATE_BY,CREATE_DATE, UPDATE_BY, UPDATE_DATE ) ");
			sql.append(" SELECT RPST.PARAM_ID,RPST.PARAM_TYPE, RPST.PARAM_DESC, RPST.PARAM_VALUE, RPST.STATUS, ");
			sql.append(" ? , SYSDATE, ?, SYSDATE   FROM RUNNING_PARAM_STACK_TEMP RPST  ");
			sql.append(" WHERE RPST.PARAM_TYPE = ? ");

			logger.debug("SQL_CMD :: " + sql.toString());

			ps = conn.prepareStatement(sql.toString());
			int cnt = 1;
			ps.setString(cnt++, userSystem);
			ps.setString(cnt++, userSystem);
			ps.setString(cnt++, paramType);
			execRows = ps.executeUpdate();

		} catch (SQLException e) {
			logger.error("Error >> ", e);
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
		return execRows;
	}

	@Override
	public int purgeRunningParamStack(Connection conn, String paramType, java.sql.Date applicationDate, int purgeDay) throws InfBatchException {
		ArrayList<RunningParamStackDataM> listRunningParamStackDataM = new ArrayList<RunningParamStackDataM>();
		try {
			listRunningParamStackDataM = selectRunningParamStack(conn, paramType, applicationDate, purgeDay);
			deleteRunningParamStack(conn, listRunningParamStackDataM);
		} catch (Exception e) {
			logger.fatal(e);
			throw new InfBatchException(e.getMessage());
		}
		return listRunningParamStackDataM.size();
	}

	private ArrayList<RunningParamStackDataM> selectRunningParamStack(Connection conn, String paramType, java.sql.Date applicationDate, int purgeDay) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<RunningParamStackDataM> list = new ArrayList<RunningParamStackDataM>();
		RunningParamStackDataM runningParamStackDataM = null;
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT  rps.PARAM_VALUE, rps.PARAM_TYPE FROM  RUNNING_PARAM_STACK RPS ");
			sql.append(" WHERE  RPS.STATUS = 'Y' AND TRUNC(?) - TRUNC(RPS.UPDATE_DATE ) >= ?  AND RPS.PARAM_TYPE = ? ");
			logger.debug("SQL_CMD :: " + sql.toString());

			ps = conn.prepareStatement(sql.toString());

			int cnt = 1;
			ps.setDate(cnt++, applicationDate);
			ps.setInt(cnt++, purgeDay);
			ps.setString(cnt++, paramType);

			rs = ps.executeQuery();

			while (rs.next()) {
				runningParamStackDataM = new RunningParamStackDataM();
				runningParamStackDataM.setParamType(rs.getString("PARAM_TYPE"));
				runningParamStackDataM.setParamValue(rs.getString("PARAM_VALUE"));
				list.add(runningParamStackDataM);
			}
		} catch (SQLException e) {
			logger.error("Error >> ", e);
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
		return list;
	}

	private ArrayList<String> getParamType(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> listParamType = new ArrayList<String>();
		try {
			StringBuilder sql = new StringBuilder();

			sql.append(" SELECT DISTINCT PARAM_TYPE FROM (SELECT RPS.PARAM_TYPE FROM ORIG_APP.RUNNING_PARAM_STACK  RPS ");
			sql.append(" UNION  SELECT RPST.PARAM_TYPE FROM ORIG_APP.RUNNING_PARAM_STACK_TEMP RPST) ");

			logger.debug("SQL_CMD :: " + sql.toString());

			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				listParamType.add(rs.getString("PARAM_TYPE"));
			}
		} catch (SQLException e) {
			logger.error("Error >> ", e);
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
		return listParamType;
	}

	private void deleteRunningParamStack(Connection conn, ArrayList<RunningParamStackDataM> listRunningParamStackDataM) throws InfBatchException {
		if (!InfBatchUtil.empty(listRunningParamStackDataM)) {
			for (RunningParamStackDataM runningParamStackDataM : listRunningParamStackDataM) {
				deleteRunningParamStack(conn, runningParamStackDataM.getParamValue(), runningParamStackDataM.getParamType());
			}
		}
	}

	private void deleteRunningParamStack(Connection conn, String paramValue, String paramType) throws InfBatchException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append(" DELETE FROM RUNNING_PARAM_STACK RP WHERE RP.PARAM_VALUE = ? AND RP.PARAM_TYPE = ? ");
			logger.debug("SQL_CMD :: " + sql.toString());

			ps = conn.prepareStatement(sql.toString());
			int cnt = 1;
			ps.setString(cnt++, paramValue);
			ps.setString(cnt++, paramType);

			ps.executeUpdate();

		} catch (Exception e) {
			logger.error("Error >> ", e);
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.error("Error >> ", e);
			}
		}
	}

}
