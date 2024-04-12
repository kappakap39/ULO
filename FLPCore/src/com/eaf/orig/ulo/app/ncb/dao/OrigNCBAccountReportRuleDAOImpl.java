package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportRuleDataM;

public class OrigNCBAccountReportRuleDAOImpl extends OrigObjectDAO implements
		OrigNCBAccountReportRuleDAO {
	public OrigNCBAccountReportRuleDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNCBAccountReportRuleDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigNcbAccountReportRuleM(
			NcbAccountReportRuleDataM ncbAccountReportRuleM) throws ApplicationException {
		try {
			if(Util.empty(ncbAccountReportRuleM.getRuleId())) {
				String ruleId = GenerateUnique.generate(OrigConstant.SeqNames.NCB_ACCOUNT_RULE_REPORT_PK);
				ncbAccountReportRuleM.setRuleId(ruleId);
			}
			ncbAccountReportRuleM.setCreateBy(userId);
			ncbAccountReportRuleM.setUpdateBy(userId);
			createTableNCB_ACCOUNT_RULE_REPORT(ncbAccountReportRuleM);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableNCB_ACCOUNT_RULE_REPORT(
			NcbAccountReportRuleDataM ncbAccountReportRuleM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO NCB_ACCOUNT_RULE_REPORT ");
			sql.append("( ACCOUNT_REPORT_ID, RULE_ID, RULE_RESULT, PRODUCT_CODE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbAccountReportRuleM.getAccountReportId());
			ps.setString(cnt++, ncbAccountReportRuleM.getRuleId());
			ps.setString(cnt++, ncbAccountReportRuleM.getRuleResult());
			ps.setString(cnt++, ncbAccountReportRuleM.getProductCode());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAccountReportRuleM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAccountReportRuleM.getUpdateBy());
			
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
	public void deleteOrigNcbAccountReportRuleM(String accountReportId,
			String ruleId) throws ApplicationException {
		deleteTableNCB_ACCOUNT_RULE_REPORT(accountReportId, ruleId);
	}

	private void deleteTableNCB_ACCOUNT_RULE_REPORT(String accountReportId,
			String ruleId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE NCB_ACCOUNT_RULE_REPORT ");
			sql.append(" WHERE ACCOUNT_REPORT_ID = ? ");
			if(!Util.empty(ruleId)) {
				sql.append(" AND RULE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accountReportId);
			
			if(!Util.empty(ruleId)) {
				ps.setString(2, ruleId);
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
	public ArrayList<NcbAccountReportRuleDataM> loadOrigNcbAccountReportRuleM(
			String accountReportId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableNCB_ACCOUNT_RULE_REPORT(accountReportId,conn);
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
	@Override
	public ArrayList<NcbAccountReportRuleDataM> loadOrigNcbAccountReportRuleM(
			String accountReportId,Connection conn) throws ApplicationException {
		ArrayList<NcbAccountReportRuleDataM> result = selectTableNCB_ACCOUNT_RULE_REPORT(accountReportId,conn);
		return result;
	}
	private ArrayList<NcbAccountReportRuleDataM> selectTableNCB_ACCOUNT_RULE_REPORT(
			String accountReportId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NcbAccountReportRuleDataM> ruleList = new ArrayList<NcbAccountReportRuleDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ACCOUNT_REPORT_ID, RULE_ID, RULE_RESULT, PRODUCT_CODE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_ACCOUNT_RULE_REPORT WHERE ACCOUNT_REPORT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, accountReportId);

			rs = ps.executeQuery();

			while(rs.next()) {
				NcbAccountReportRuleDataM ruleM = new NcbAccountReportRuleDataM();
				ruleM.setAccountReportId(rs.getString("ACCOUNT_REPORT_ID"));
				ruleM.setRuleId(rs.getString("RULE_ID"));
				ruleM.setRuleResult(rs.getString("RULE_RESULT"));
				ruleM.setProductCode(rs.getString("PRODUCT_CODE"));
				
				ruleM.setCreateBy(rs.getString("CREATE_BY"));
				ruleM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ruleM.setUpdateBy(rs.getString("UPDATE_BY"));
				ruleM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ruleList.add(ruleM);
			}

			return ruleList;
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
	public void saveUpdateOrigNcbAccountReportRuleM(NcbAccountReportRuleDataM ncbAccountReportRuleM)
			throws ApplicationException {
		int returnRows = 0;
		ncbAccountReportRuleM.setUpdateBy(userId);
		returnRows = updateTableNCB_ACCOUNT_RULE_REPORT(ncbAccountReportRuleM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table NCB_ACCOUNT_RULE_REPORT then call Insert method");
			ncbAccountReportRuleM.setCreateBy(userId);
			ncbAccountReportRuleM.setUpdateBy(userId);
			createOrigNcbAccountReportRuleM(ncbAccountReportRuleM);
		}
	}

	private int updateTableNCB_ACCOUNT_RULE_REPORT(
			NcbAccountReportRuleDataM ncbAccountReportRuleM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE NCB_ACCOUNT_RULE_REPORT ");
			sql.append(" SET RULE_RESULT = ?, PRODUCT_CODE = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			sql.append(" WHERE ACCOUNT_REPORT_ID = ? ");
			if(!Util.empty(ncbAccountReportRuleM.getRuleId())) {
				sql.append(" AND RULE_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbAccountReportRuleM.getRuleResult());
			ps.setString(cnt++, ncbAccountReportRuleM.getProductCode());
			
			ps.setString(cnt++, ncbAccountReportRuleM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbAccountReportRuleM.getAccountReportId());
			ps.setString(cnt++, ncbAccountReportRuleM.getRuleId());
			
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
	public void deleteNotInKeyNcbAccountReportRule(
			ArrayList<NcbAccountReportRuleDataM> ncbAccountReportRuleList,
			String accountReportId) throws ApplicationException {
		deleteNotInKeyNCB_ACCOUNT_RULE_REPORT(ncbAccountReportRuleList, accountReportId);
	}

	private void deleteNotInKeyNCB_ACCOUNT_RULE_REPORT(
			ArrayList<NcbAccountReportRuleDataM> ncbAccountReportRuleList,
			String accountReportId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM NCB_ACCOUNT_RULE_REPORT ");
            sql.append(" WHERE ACCOUNT_REPORT_ID = ? ");
            
            if (!Util.empty(ncbAccountReportRuleList)) {
                sql.append(" AND RULE_ID NOT IN ( ");
                for (NcbAccountReportRuleDataM ruleM: ncbAccountReportRuleList) {
                	logger.debug("ruleM.getRuleId() = "+ruleM.getRuleId());
                    sql.append(" '" + ruleM.getRuleId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, accountReportId);
            
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
