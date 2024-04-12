package com.eaf.orig.ulo.app.ncb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.ncb.NcbAccountDataM;
import com.eaf.orig.ulo.model.ncb.NcbAccountReportDataM;

public class OrigNCBAccountDAOImpl extends OrigObjectDAO implements
		OrigNCBAccountDAO {
	public OrigNCBAccountDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigNCBAccountDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigNcbAccountM(NcbAccountDataM ncbAccountM)
			throws ApplicationException {
		try {			
			ncbAccountM.setCreateBy(userId);
			ncbAccountM.setUpdateBy(userId);
			createTableNCB_ACCOUNT(ncbAccountM);
			
			ArrayList<NcbAccountReportDataM> reportList = ncbAccountM.getAccountReports();
			if(!Util.empty(reportList)) {
				OrigNCBAccountReportDAO reportDAO = ORIGDAOFactory.getNCBAccountReportDAO(userId);
				for(NcbAccountReportDataM reportM : reportList) {
					reportM.setTrackingCode(ncbAccountM.getTrackingCode());
					reportM.setSeq(ncbAccountM.getSeq());
					reportDAO.createOrigNcbAccountReportM(reportM);
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void createTableNCB_ACCOUNT(NcbAccountDataM ncbAccountM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO NCB_ACCOUNT ");
			sql.append("( TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, MEMBER_CODE, ");
			sql.append(" MEMBER_SHORT_NAME, ACCOUNT_NUMBER, ACCOUNT_TYPE, OWNERSHIP_INDICATOR, ");
			sql.append(" CURRENCY_CODE, DATE_ACC_OPENED, DATE_OF_LAST_PMT, DATE_ACC_CLOSED, ");
			sql.append(" AS_OF_DATE, CREDITLIM_ORLOANAMT, AMT_OWED, AMT_PAST_DUE, DEFAULT_DATE, ");
			sql.append(" INSTALL_FREQ, INSTALL_AMT, INSTALL_NOOFPAY, ACCOUNT_STATUS, LOAN_CLASS, ");
			
			sql.append(" PMT_HIST1, PMT_HIST2, PMT_HIST_STARTDATE, PMT_HIST_ENDDATE, LOAN_OBJECTIVE, ");
			sql.append(" COLLATERAL1, COLLATERAL2, COLLATERAL3, LAST_DEBT_REST_DATE, PERCENT_PAYMENT, ");
			sql.append(" CREDIT_CARD_TYPE, NUMBEROF_COBORR, UNIT_MAKE, UNIT_MODEL, CREDIT_TYPE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ");
			sql.append(" ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, ncbAccountM.getTrackingCode());
			ps.setInt(cnt++, ncbAccountM.getSeq());
			ps.setInt(cnt++, ncbAccountM.getGroupSeq());
			ps.setString(cnt++, ncbAccountM.getSegmentValue());
			ps.setString(cnt++, ncbAccountM.getMemberCode());
			
			ps.setString(cnt++, ncbAccountM.getMemberShortName());
			ps.setString(cnt++, ncbAccountM.getAccountNumber());
			ps.setString(cnt++, ncbAccountM.getAccountType());
			ps.setString(cnt++, ncbAccountM.getOwnershipIndicator());
			
			ps.setString(cnt++, ncbAccountM.getCurrencyCode());
			ps.setDate(cnt++, ncbAccountM.getDateAccOpened());
			ps.setDate(cnt++, ncbAccountM.getDateOfLastPmt());
			ps.setDate(cnt++, ncbAccountM.getDateAccClosed());
			
			ps.setDate(cnt++, ncbAccountM.getAsOfDate());
			ps.setBigDecimal(cnt++, ncbAccountM.getCreditlimOrloanamt());
			ps.setBigDecimal(cnt++, ncbAccountM.getAmtOwed());
			ps.setBigDecimal(cnt++, ncbAccountM.getAmtPastDue());
			ps.setDate(cnt++, ncbAccountM.getDefaultDate());
			
			ps.setBigDecimal(cnt++, ncbAccountM.getInstallFreq());
			ps.setBigDecimal(cnt++, ncbAccountM.getInstallAmt());
			ps.setBigDecimal(cnt++, ncbAccountM.getInstallNoofpay());
			ps.setString(cnt++, ncbAccountM.getAccountStatus());
			ps.setString(cnt++, ncbAccountM.getLoanClass());
			
			ps.setString(cnt++, ncbAccountM.getPmtHist1());
			ps.setString(cnt++, ncbAccountM.getPmtHist2());
			ps.setDate(cnt++, ncbAccountM.getPmtHistStartdate());
			ps.setDate(cnt++, ncbAccountM.getPmtHistEnddate());
			ps.setString(cnt++, ncbAccountM.getLoanObjective());
			
			ps.setString(cnt++, ncbAccountM.getCollateral1());
			ps.setString(cnt++, ncbAccountM.getCollateral2());
			ps.setString(cnt++, ncbAccountM.getCollateral3());
			ps.setDate(cnt++, ncbAccountM.getLastDebtRestDate());
			ps.setBigDecimal(cnt++, ncbAccountM.getPercentPayment());
			
			ps.setString(cnt++, ncbAccountM.getCreditCardType());
			ps.setBigDecimal(cnt++, ncbAccountM.getNumberofCoborr());
			ps.setString(cnt++, ncbAccountM.getUnitMake());
			ps.setString(cnt++, ncbAccountM.getUnitModel());
			ps.setString(cnt++, ncbAccountM.getCreditTypeFlag());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAccountM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, ncbAccountM.getUpdateBy());
			
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
	public void deleteOrigNcbAccountM(String trackingCode, int seq)
			throws ApplicationException {
		OrigNCBAccountReportDAO reportDAO = ORIGDAOFactory.getNCBAccountReportDAO();
		if(seq == 0) {
			ArrayList<NcbAccountDataM> accountList = loadOrigNcbAccountM(trackingCode, seq);
			if(!Util.empty(accountList)) {
				for(NcbAccountDataM accountM : accountList) {
					reportDAO.deleteOrigNcbAccountReportM(accountM.getTrackingCode(), accountM.getSeq(), null);
				}
			}
		} else {
			reportDAO.deleteOrigNcbAccountReportM(trackingCode, seq, null);
		}
		deleteTableNCB_ACCOUNT(trackingCode, seq);
	}

	private void deleteTableNCB_ACCOUNT(String trackingCode, int seq) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE NCB_ACCOUNT ");
			sql.append(" WHERE TRACKING_CODE = ?");
			if(seq != 0) {
				sql.append(" AND SEQ = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, trackingCode);
			if(seq != 0) {
				ps.setInt(2, seq);
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
	public ArrayList<NcbAccountDataM> loadOrigNcbAccountM(String trackingCode, int seq)
			throws ApplicationException {
		ArrayList<NcbAccountDataM> result = selectTableNCB_ACCOUNT(trackingCode, seq);
		if(!Util.empty(result)) {
			OrigNCBAccountReportDAO reportDAO = ORIGDAOFactory.getNCBAccountReportDAO();
			for(NcbAccountDataM accountM : result) {
				ArrayList<NcbAccountReportDataM> reportList = reportDAO.loadOrigNcbAccountReportM(accountM.getTrackingCode(), accountM.getSeq());
				if(!Util.empty(reportList)) {
					accountM.setAccountReports(reportList);
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<NcbAccountDataM> loadOrigNcbAccountM(String trackingCode, int seq,Connection conn)
			throws ApplicationException {
		ArrayList<NcbAccountDataM> result = selectTableNCB_ACCOUNT(trackingCode, seq,conn);
		if(!Util.empty(result)) {
			OrigNCBAccountReportDAO reportDAO = ORIGDAOFactory.getNCBAccountReportDAO();
			for(NcbAccountDataM accountM : result) {
				ArrayList<NcbAccountReportDataM> reportList = reportDAO.loadOrigNcbAccountReportM(accountM.getTrackingCode(), accountM.getSeq(),conn);
				if(!Util.empty(reportList)) {
					accountM.setAccountReports(reportList);
				}
			}
		}
		return result;
	}
	private ArrayList<NcbAccountDataM> selectTableNCB_ACCOUNT(
			String trackingCode, int seq) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableNCB_ACCOUNT(trackingCode,seq,conn);
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
	private ArrayList<NcbAccountDataM> selectTableNCB_ACCOUNT(
			String trackingCode, int seq,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<NcbAccountDataM> ncbAcctList = new ArrayList<NcbAccountDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, SEQ, GROUP_SEQ, SEGMENT_VALUE, MEMBER_CODE, ");
			sql.append(" MEMBER_SHORT_NAME, ACCOUNT_NUMBER, ACCOUNT_TYPE, OWNERSHIP_INDICATOR, ");
			sql.append(" CURRENCY_CODE, DATE_ACC_OPENED, DATE_OF_LAST_PMT, DATE_ACC_CLOSED, ");
			sql.append(" AS_OF_DATE, CREDITLIM_ORLOANAMT, AMT_OWED, AMT_PAST_DUE, DEFAULT_DATE, ");
			sql.append(" INSTALL_FREQ, INSTALL_AMT, INSTALL_NOOFPAY, ACCOUNT_STATUS, LOAN_CLASS, ");
			
			sql.append(" PMT_HIST1, PMT_HIST2, PMT_HIST_STARTDATE, PMT_HIST_ENDDATE, LOAN_OBJECTIVE, ");
			sql.append(" COLLATERAL1, COLLATERAL2, COLLATERAL3, LAST_DEBT_REST_DATE, PERCENT_PAYMENT, ");
			sql.append(" CREDIT_CARD_TYPE, NUMBEROF_COBORR, UNIT_MAKE, UNIT_MODEL, CREDIT_TYPE_FLAG, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM NCB_ACCOUNT WHERE TRACKING_CODE = ? ");
			if(seq != 0) {
				sql.append(" AND SEQ = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, trackingCode);
			if(seq != 0) {
				ps.setInt(2, seq);
			}
			rs = ps.executeQuery();

			while(rs.next()) {
				NcbAccountDataM ncbAcctM = new NcbAccountDataM();
				ncbAcctM.setTrackingCode(rs.getString("TRACKING_CODE"));
				ncbAcctM.setSeq(rs.getInt("SEQ"));
				ncbAcctM.setGroupSeq(rs.getInt("GROUP_SEQ"));
				ncbAcctM.setSegmentValue(rs.getString("SEGMENT_VALUE"));
				ncbAcctM.setMemberCode(rs.getString("MEMBER_CODE"));
				
				ncbAcctM.setMemberShortName(rs.getString("MEMBER_SHORT_NAME"));
				ncbAcctM.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
				ncbAcctM.setAccountType(rs.getString("ACCOUNT_TYPE"));
				ncbAcctM.setOwnershipIndicator(rs.getString("OWNERSHIP_INDICATOR"));
				
				ncbAcctM.setCurrencyCode(rs.getString("CURRENCY_CODE"));
				ncbAcctM.setDateAccOpened(rs.getDate("DATE_ACC_OPENED"));
				ncbAcctM.setDateOfLastPmt(rs.getDate("DATE_OF_LAST_PMT"));
				ncbAcctM.setDateAccClosed(rs.getDate("DATE_ACC_CLOSED"));
				
				ncbAcctM.setAsOfDate(rs.getDate("AS_OF_DATE"));
				ncbAcctM.setCreditlimOrloanamt(rs.getBigDecimal("CREDITLIM_ORLOANAMT"));
				ncbAcctM.setAmtOwed(rs.getBigDecimal("AMT_OWED"));
				ncbAcctM.setAmtPastDue(rs.getBigDecimal("AMT_PAST_DUE"));
				ncbAcctM.setDefaultDate(rs.getDate("DEFAULT_DATE"));
				
				ncbAcctM.setInstallFreq(rs.getBigDecimal("INSTALL_FREQ"));
				ncbAcctM.setInstallAmt(rs.getBigDecimal("INSTALL_AMT"));
				ncbAcctM.setInstallNoofpay(rs.getBigDecimal("INSTALL_NOOFPAY"));
				ncbAcctM.setAccountStatus(rs.getString("ACCOUNT_STATUS"));
				ncbAcctM.setLoanClass(rs.getString("LOAN_CLASS"));
				
				ncbAcctM.setPmtHist1(rs.getString("PMT_HIST1"));
				ncbAcctM.setPmtHist2(rs.getString("PMT_HIST2"));
				ncbAcctM.setPmtHistStartdate(rs.getDate("PMT_HIST_STARTDATE"));
				ncbAcctM.setPmtHistEnddate(rs.getDate("PMT_HIST_ENDDATE"));
				ncbAcctM.setLoanObjective(rs.getString("LOAN_OBJECTIVE"));
				
				ncbAcctM.setCollateral1(rs.getString("COLLATERAL1"));
				ncbAcctM.setCollateral2(rs.getString("COLLATERAL2"));
				ncbAcctM.setCollateral3(rs.getString("COLLATERAL3"));
				ncbAcctM.setLastDebtRestDate(rs.getDate("LAST_DEBT_REST_DATE"));
				ncbAcctM.setPercentPayment(rs.getBigDecimal("PERCENT_PAYMENT"));
				
				ncbAcctM.setCreditCardType(rs.getString("CREDIT_CARD_TYPE"));
				ncbAcctM.setNumberofCoborr(rs.getBigDecimal("NUMBEROF_COBORR"));
				ncbAcctM.setUnitMake(rs.getString("UNIT_MAKE"));
				ncbAcctM.setUnitModel(rs.getString("UNIT_MODEL"));
				ncbAcctM.setCreditTypeFlag(rs.getString("CREDIT_TYPE_FLAG"));
				
				ncbAcctM.setCreateBy(rs.getString("CREATE_BY"));
				ncbAcctM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				ncbAcctM.setUpdateBy(rs.getString("UPDATE_BY"));
				ncbAcctM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				ncbAcctList.add(ncbAcctM);
			}

			return ncbAcctList;
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
	public void saveUpdateOrigNcbAccountM(NcbAccountDataM ncbAccountM)
			throws ApplicationException {
		int returnRows = 0;
		ncbAccountM.setUpdateBy(userId);
		returnRows = updateTableNCB_ACCOUNT(ncbAccountM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table NCB_ACCOUNT then call Insert method");
			ncbAccountM.setCreateBy(userId);
			ncbAccountM.setUpdateBy(userId);
			createOrigNcbAccountM(ncbAccountM);
		} else {
			ArrayList<NcbAccountReportDataM> reportList = ncbAccountM.getAccountReports();
			OrigNCBAccountReportDAO reportDAO = ORIGDAOFactory.getNCBAccountReportDAO(userId);
			if(!Util.empty(reportList)) {
				for(NcbAccountReportDataM reportM : reportList) {
					reportM.setTrackingCode(ncbAccountM.getTrackingCode());
					reportM.setSeq(ncbAccountM.getSeq());
					reportDAO.saveUpdateOrigNcbAccountReportM(reportM);
				}
			}
			reportDAO.deleteNotInKeyNcbAccountReport(reportList, ncbAccountM.getTrackingCode(), ncbAccountM.getSeq());
		}
	}

	private int updateTableNCB_ACCOUNT(NcbAccountDataM ncbAccountM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE NCB_ACCOUNT ");
			sql.append(" SET GROUP_SEQ = ?, SEGMENT_VALUE = ?, MEMBER_CODE = ?, MEMBER_SHORT_NAME = ?, ");
			sql.append(" ACCOUNT_NUMBER = ?, ACCOUNT_TYPE = ?, OWNERSHIP_INDICATOR = ?, CURRENCY_CODE = ?,");
			sql.append(" DATE_ACC_OPENED = ?, DATE_OF_LAST_PMT = ?, DATE_ACC_CLOSED = ?, AS_OF_DATE = ?, ");
			sql.append(" CREDITLIM_ORLOANAMT = ?, AMT_OWED = ?, AMT_PAST_DUE = ?, DEFAULT_DATE = ?, ");
			sql.append(" INSTALL_FREQ = ?, INSTALL_AMT = ?, INSTALL_NOOFPAY = ?, ACCOUNT_STATUS = ?, ");
			
			sql.append(" LOAN_CLASS = ?, PMT_HIST1 = ?, PMT_HIST2 = ?, PMT_HIST_STARTDATE = ?, ");
			sql.append(" PMT_HIST_ENDDATE = ?, LOAN_OBJECTIVE = ?, COLLATERAL1 = ?, COLLATERAL2 = ?, ");
			sql.append(" COLLATERAL3 = ?, LAST_DEBT_REST_DATE = ?, PERCENT_PAYMENT = ?, ");
			sql.append(" CREDIT_CARD_TYPE = ?, NUMBEROF_COBORR = ?, UNIT_MAKE = ?, UNIT_MODEL = ?, ");
			sql.append(" CREDIT_TYPE_FLAG = ?, UPDATE_BY = ?, UPDATE_DATE = ? ");
			sql.append(" WHERE TRACKING_CODE = ? AND SEQ = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setInt(cnt++, ncbAccountM.getGroupSeq());
			ps.setString(cnt++, ncbAccountM.getSegmentValue());
			ps.setString(cnt++, ncbAccountM.getMemberCode());
			ps.setString(cnt++, ncbAccountM.getMemberShortName());
			
			ps.setString(cnt++, ncbAccountM.getAccountNumber());
			ps.setString(cnt++, ncbAccountM.getAccountType());
			ps.setString(cnt++, ncbAccountM.getOwnershipIndicator());
			ps.setString(cnt++, ncbAccountM.getCurrencyCode());
			
			ps.setDate(cnt++, ncbAccountM.getDateAccOpened());
			ps.setDate(cnt++, ncbAccountM.getDateOfLastPmt());
			ps.setDate(cnt++, ncbAccountM.getDateAccClosed());
			ps.setDate(cnt++, ncbAccountM.getAsOfDate());
			
			ps.setBigDecimal(cnt++, ncbAccountM.getCreditlimOrloanamt());
			ps.setBigDecimal(cnt++, ncbAccountM.getAmtOwed());
			ps.setBigDecimal(cnt++, ncbAccountM.getAmtPastDue());
			ps.setDate(cnt++, ncbAccountM.getDefaultDate());
			
			ps.setBigDecimal(cnt++, ncbAccountM.getInstallFreq());
			ps.setBigDecimal(cnt++, ncbAccountM.getInstallAmt());
			ps.setBigDecimal(cnt++, ncbAccountM.getInstallNoofpay());
			ps.setString(cnt++, ncbAccountM.getAccountStatus());
			
			ps.setString(cnt++, ncbAccountM.getLoanClass());
			ps.setString(cnt++, ncbAccountM.getPmtHist1());
			ps.setString(cnt++, ncbAccountM.getPmtHist2());
			ps.setDate(cnt++, ncbAccountM.getPmtHistStartdate());
			
			ps.setDate(cnt++, ncbAccountM.getPmtHistEnddate());
			ps.setString(cnt++, ncbAccountM.getLoanObjective());
			ps.setString(cnt++, ncbAccountM.getCollateral1());
			ps.setString(cnt++, ncbAccountM.getCollateral2());
			
			ps.setString(cnt++, ncbAccountM.getCollateral3());
			ps.setDate(cnt++, ncbAccountM.getLastDebtRestDate());
			ps.setBigDecimal(cnt++, ncbAccountM.getPercentPayment());
			
			ps.setString(cnt++, ncbAccountM.getCreditCardType());
			ps.setBigDecimal(cnt++, ncbAccountM.getNumberofCoborr());
			ps.setString(cnt++, ncbAccountM.getUnitMake());
			ps.setString(cnt++, ncbAccountM.getUnitModel());
			
			ps.setString(cnt++, ncbAccountM.getCreditTypeFlag());
			ps.setString(cnt++, ncbAccountM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, ncbAccountM.getTrackingCode());
			ps.setInt(cnt++, ncbAccountM.getSeq());
			
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
	public void deleteNotInKeyNcbAccount(
			ArrayList<NcbAccountDataM> ncbAccountList, String trackingCode)	throws ApplicationException {
		ArrayList<NcbAccountDataM> acctList = selectNotInKeyTableNCB_ACCOUNT(ncbAccountList,trackingCode);
		if(!Util.empty(acctList)) {
			OrigNCBAccountReportDAO reportDAO = ORIGDAOFactory.getNCBAccountReportDAO();
			for(NcbAccountDataM acctM: acctList) {
				reportDAO.deleteOrigNcbAccountReportM(acctM.getTrackingCode(), acctM.getSeq(), null);
			}
		}
		deleteNotInKeyNCB_ACCOUNT(ncbAccountList, trackingCode);
	}

	private ArrayList<NcbAccountDataM> selectNotInKeyTableNCB_ACCOUNT(
			ArrayList<NcbAccountDataM> ncbAccountList, String trackingCode) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<NcbAccountDataM> reportList = new ArrayList<NcbAccountDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT TRACKING_CODE, SEQ ");
			sql.append(" FROM NCB_ACCOUNT ");
            sql.append(" WHERE TRACKING_CODE = ? ");
			if (!Util.empty(ncbAccountList)) {
                sql.append(" AND SEQ NOT IN ( ");

                for (NcbAccountDataM acctM: ncbAccountList) {
                    sql.append(" " + acctM.getSeq() + " , ");
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

			rs = ps.executeQuery();

			while(rs.next()) {
				NcbAccountDataM acctM = new NcbAccountDataM();
				acctM.setTrackingCode(rs.getString("TRACKING_CODE"));
				acctM.setSeq(rs.getInt("SEQ"));
				reportList.add(acctM);
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

	private void deleteNotInKeyNCB_ACCOUNT(
			ArrayList<NcbAccountDataM> ncbAccountList, String trackingCode) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM NCB_ACCOUNT ");
            sql.append(" WHERE TRACKING_CODE = ? ");
            
            if (!Util.empty(ncbAccountList)) {
                sql.append(" AND SEQ NOT IN ( ");
                for (NcbAccountDataM ncbAcctM: ncbAccountList) {
                	logger.debug("ncbAcctM.getSeq() = "+ncbAcctM.getSeq());
                    sql.append(" " + ncbAcctM.getSeq() + ", ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, trackingCode);
            
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
