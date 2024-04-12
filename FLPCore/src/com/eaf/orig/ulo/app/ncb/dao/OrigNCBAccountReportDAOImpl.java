package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportDataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportRuleDataM;

public class OrigNCBAccountReportDAOImpl extends OrigObjectDAO implements
		OrigNCBAccountReportDAO {
	public OrigNCBAccountReportDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNCBAccountReportDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigNcbAccountReportM(NcbAccountReportDataM ncbAccountReportM)
			throws ApplicationException {
		try {
			if(Util.empty(ncbAccountReportM.getAccountReportId())) {
				String acctReportId = GenerateUnique.generate(OrigConstant.SeqNames.NCB_ACCOUNT_REPORT_PK);
				ncbAccountReportM.setAccountReportId(acctReportId);
			}
			ncbAccountReportM.setCreateBy(userId);
			ncbAccountReportM.setUpdateBy(userId);
			createTableNCB_ACCOUNT_REPORT(ncbAccountReportM);
			
			ArrayList<NcbAccountReportRuleDataM> rules = ncbAccountReportM.getRules();
			if(!Util.empty(rules)) {
				OrigNCBAccountReportRuleDAO ruleDAO = ORIGDAOFactory.getNCBAccountReportRuleDAO(userId);
				for(NcbAccountReportRuleDataM ruleM : rules) {
					ruleM.setAccountReportId(ncbAccountReportM.getAccountReportId());
					ruleDAO.createOrigNcbAccountReportRuleM(ruleM);
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableNCB_ACCOUNT_REPORT(NcbAccountReportDataM ncbAccountReportM) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO NCB_ACCOUNT_REPORT ");
			sql.append("( TRACKING_CODE, SEQ, ACCOUNT_REPORT_ID, RESTRUCTURE_GT_1YEAR, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbAccountReportM.getTrackingCode());
			ps.setInt(cnt++, ncbAccountReportM.getSeq());
			ps.setString(cnt++, ncbAccountReportM.getAccountReportId());
			ps.setString(cnt++, ncbAccountReportM.getRestructureGT1Yr());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAccountReportM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAccountReportM.getUpdateBy());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigNcbAccountReportM(String trackingCode, int seq,
			String accountReportId) throws ApplicationException {
		OrigNCBAccountReportRuleDAO ruleDAO = ORIGDAOFactory.getNCBAccountReportRuleDAO();
		if(Util.empty(accountReportId)) {
			ArrayList<NcbAccountReportDataM> acctReportList = loadOrigNcbAccountReportM(trackingCode, seq);
			if(!Util.empty(acctReportList)) {
				for(NcbAccountReportDataM reportM : acctReportList) {
					ruleDAO.deleteOrigNcbAccountReportRuleM(reportM.getAccountReportId(), null);
				}
			}
		} else {
			ruleDAO.deleteOrigNcbAccountReportRuleM(accountReportId, null);
		}
		deleteTableNCB_ACCOUNT_REPORT(trackingCode, seq, accountReportId);
	}

	private void deleteTableNCB_ACCOUNT_REPORT(String trackingCode, int seq,
			String accountReportId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE NCB_ACCOUNT_REPORT ");
			sql.append(" WHERE TRACKING_CODE = ? AND SEQ = ? ");
			if(!Util.empty(accountReportId)) {
				sql.append(" AND ACCOUNT_REPORT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, trackingCode);
			ps.setInt(2, seq);
			
			if(!Util.empty(accountReportId)) {
				ps.setString(3, accountReportId);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<NcbAccountReportDataM> loadOrigNcbAccountReportM(
			String trackingCode, int seq) throws ApplicationException {
		ArrayList<NcbAccountReportDataM> result = selectTableNCB_ACCOUNT_REPORT(trackingCode, seq);
		if(!Util.empty(result)) {
			OrigNCBAccountReportRuleDAO ruleDAO = ORIGDAOFactory.getNCBAccountReportRuleDAO();
			for(NcbAccountReportDataM reportM : result) {
				ArrayList<NcbAccountReportRuleDataM> rules = ruleDAO.loadOrigNcbAccountReportRuleM(reportM.getAccountReportId());
				if(!Util.empty(rules)){
					reportM.setRules(rules);
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<NcbAccountReportDataM> loadOrigNcbAccountReportM(
			String trackingCode, int seq, Connection conn)
			throws ApplicationException {
		ArrayList<NcbAccountReportDataM> result = selectTableNCB_ACCOUNT_REPORT(trackingCode, seq,conn);
		if(!Util.empty(result)) {
			OrigNCBAccountReportRuleDAO ruleDAO = ORIGDAOFactory.getNCBAccountReportRuleDAO();
			for(NcbAccountReportDataM reportM : result) {
				ArrayList<NcbAccountReportRuleDataM> rules = ruleDAO.loadOrigNcbAccountReportRuleM(reportM.getAccountReportId(),conn);
				if(!Util.empty(rules)){
					reportM.setRules(rules);
				}
			}
		}
		return result;
	}
	private ArrayList<NcbAccountReportDataM> selectTableNCB_ACCOUNT_REPORT(
			String trackingCode, int seq) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableNCB_ACCOUNT_REPORT(trackingCode,seq,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	private ArrayList<NcbAccountReportDataM> selectTableNCB_ACCOUNT_REPORT(
			String trackingCode, int seq,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NcbAccountReportDataM> ncbAcctReportList = new ArrayList<NcbAccountReportDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, SEQ, ACCOUNT_REPORT_ID, RESTRUCTURE_GT_1YEAR, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_ACCOUNT_REPORT WHERE TRACKING_CODE = ? AND SEQ = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, trackingCode);
			ps.setInt(2, seq);

			rs = ps.executeQuery();

			while(rs.next()) {
				NcbAccountReportDataM ncbAcctReportM = new NcbAccountReportDataM();
				ncbAcctReportM.setTrackingCode(rs.getString("TRACKING_CODE"));
				ncbAcctReportM.setSeq(rs.getInt("SEQ"));
				ncbAcctReportM.setAccountReportId(rs.getString("ACCOUNT_REPORT_ID"));
				ncbAcctReportM.setRestructureGT1Yr(rs.getString("RESTRUCTURE_GT_1YEAR"));
				
				ncbAcctReportM.setCreateBy(rs.getString("CREATE_BY"));
				ncbAcctReportM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbAcctReportM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbAcctReportM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbAcctReportList.add(ncbAcctReportM);
			}

			return ncbAcctReportList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigNcbAccountReportM(
			NcbAccountReportDataM ncbAccountReportM) throws ApplicationException {
		int returnRows = 0;
		ncbAccountReportM.setUpdateBy(userId);
		returnRows = updateTableNCB_ACCOUNT_REPORT(ncbAccountReportM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table NCB_ACCOUNT_REPORT then call Insert method");
			ncbAccountReportM.setCreateBy(userId);
			ncbAccountReportM.setUpdateBy(userId);
			createOrigNcbAccountReportM(ncbAccountReportM);
		} else {
			ArrayList<NcbAccountReportRuleDataM> rules = ncbAccountReportM.getRules();
			OrigNCBAccountReportRuleDAO ruleDAO = ORIGDAOFactory.getNCBAccountReportRuleDAO(userId);
			if(!Util.empty(rules)) {
				for(NcbAccountReportRuleDataM ruleM : rules) {
					ruleM.setAccountReportId(ncbAccountReportM.getAccountReportId());
					ruleDAO.saveUpdateOrigNcbAccountReportRuleM(ruleM);
				}
			}
			ruleDAO.deleteNotInKeyNcbAccountReportRule(rules, ncbAccountReportM.getAccountReportId());
		}
	}

	private int updateTableNCB_ACCOUNT_REPORT(NcbAccountReportDataM ncbAccountReportM) 
			throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE NCB_ACCOUNT_REPORT ");
			sql.append(" SET RESTRUCTURE_GT_1YEAR = ?, UPDATE_BY = ?, UPDATE_DATE = ? ");
			sql.append(" WHERE ACCOUNT_REPORT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbAccountReportM.getRestructureGT1Yr());
			ps.setString(cnt++, ncbAccountReportM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbAccountReportM.getAccountReportId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyNcbAccountReport(ArrayList<NcbAccountReportDataM> ncbAccountReportList,
			String trackingCode, int seq) throws ApplicationException {
		ArrayList<String> reportList = selectNotInKeyTableNCB_ACCOUNT_REPORT(ncbAccountReportList, trackingCode, seq);
		if(!Util.empty(reportList)) {
			OrigNCBAccountReportRuleDAO ruleDAO = ORIGDAOFactory.getNCBAccountReportRuleDAO();
			for(String acctReportId: reportList) {
				ruleDAO.deleteOrigNcbAccountReportRuleM(acctReportId, null);
			}
		}
		deleteNotInKeyNCB_ACCOUNT_REPORT(ncbAccountReportList, trackingCode, seq);
	}

	private ArrayList<String> selectNotInKeyTableNCB_ACCOUNT_REPORT(
			ArrayList<NcbAccountReportDataM> ncbAccountReportList,
			String trackingCode, int seq) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> reportList = new ArrayList<String>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ACCOUNT_REPORT_ID ");
			sql.append(" FROM NCB_ACCOUNT_REPORT ");
            sql.append(" WHERE TRACKING_CODE = ? AND SEQ = ?  ");
			if (!Util.empty(ncbAccountReportList)) {
                sql.append(" AND ACCOUNT_REPORT_ID NOT IN ( ");

                for (NcbAccountReportDataM reportM: ncbAccountReportList) {
                    sql.append(" '" + reportM.getAccountReportId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
            ps.setString(1, trackingCode);
			ps.setInt(2, seq);

			rs = ps.executeQuery();

			while(rs.next()) {
				String acctReportId = rs.getString("ACCOUNT_REPORT_ID");
				reportList.add(acctReportId);
			}

			return reportList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void deleteNotInKeyNCB_ACCOUNT_REPORT(
			ArrayList<NcbAccountReportDataM> ncbAccountReportList,
			String trackingCode, int seq) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM NCB_ACCOUNT_REPORT ");
            sql.append(" WHERE TRACKING_CODE = ? AND SEQ = ? ");
            
            if (!Util.empty(ncbAccountReportList)) {
                sql.append(" AND ACCOUNT_REPORT_ID NOT IN ( ");
                for (NcbAccountReportDataM ncbAcctReportM: ncbAccountReportList) {
                	logger.debug("ncbAcctReportM.getAccountReportId() = "+ncbAcctReportM.getAccountReportId());
                    sql.append(" '" + ncbAcctReportM.getAccountReportId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, trackingCode);
			ps.setInt(2, seq);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
