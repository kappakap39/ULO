package com.eaf.orig.ulo.app.inc.dao;

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
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;

public class OrigPayslipMonthlyDetailDAOImpl extends OrigObjectDAO implements
		OrigPayslipMonthlyDetailDAO {
	public OrigPayslipMonthlyDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPayslipMonthlyDetailDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPayslipMonthlyDetailM(
			PayslipMonthlyDetailDataM payslipMonthlyDetailM) throws ApplicationException {
		logger.debug("payslipMonthlyDetailM.getPayslipMonthlyDetailId() :" + 
				payslipMonthlyDetailM.getPayslipMonthlyDetailId());
	    if(Util.empty(payslipMonthlyDetailM.getPayslipMonthlyDetailId())){
			String detailId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_PAYSLIP_MONTHLY_DETAIL_PK);
			payslipMonthlyDetailM.setPayslipMonthlyDetailId(detailId);
			payslipMonthlyDetailM.setCreateBy(userId);
	    }
	    payslipMonthlyDetailM.setUpdateBy(userId);
		createTableINC_PAYSLIP_MONTHLY_DETAIL(payslipMonthlyDetailM);
	}

	private void createTableINC_PAYSLIP_MONTHLY_DETAIL(
			PayslipMonthlyDetailDataM payslipMonthlyDetailM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_PAYSLIP_MONTHLY_DETAIL ");
			sql.append("( PAYSLIP_MONTHLY_DETAIL_ID, PAYSLIP_MONTHLY_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payslipMonthlyDetailM.getPayslipMonthlyDetailId());
			ps.setString(cnt++, payslipMonthlyDetailM.getPayslipMonthlyId());
			
			ps.setString(cnt++, payslipMonthlyDetailM.getMonth());
			ps.setString(cnt++, payslipMonthlyDetailM.getYear());
			ps.setBigDecimal(cnt++, payslipMonthlyDetailM.getAmount());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payslipMonthlyDetailM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, payslipMonthlyDetailM.getUpdateBy());
			
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
	public void deleteOrigPayslipMonthlyDetailM(String payslipMonthlyId,
			String payslipMonthlyDetailId) throws ApplicationException {
		deleteTableINC_PAYSLIP_MONTHLY_DETAIL(payslipMonthlyId, payslipMonthlyDetailId);
	}

	private void deleteTableINC_PAYSLIP_MONTHLY_DETAIL(String payslipMonthlyId,
			String payslipMonthlyDetailId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_PAYSLIP_MONTHLY_DETAIL ");
			sql.append(" WHERE PAYSLIP_MONTHLY_ID = ? ");
			if(!Util.empty(payslipMonthlyDetailId)) {
				sql.append(" AND PAYSLIP_MONTHLY_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, payslipMonthlyId);
			if(!Util.empty(payslipMonthlyDetailId)) {
				ps.setString(2, payslipMonthlyDetailId);
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
	public ArrayList<PayslipMonthlyDetailDataM> loadOrigPayslipMonthlyDetailM(
			String payslipMonthlyId) throws ApplicationException {
		ArrayList<PayslipMonthlyDetailDataM> result = selectTableINC_PAYSLIP_MONTHLY_DETAIL(payslipMonthlyId);
		return result;
	}

	private ArrayList<PayslipMonthlyDetailDataM> selectTableINC_PAYSLIP_MONTHLY_DETAIL(
			String payslipMonthlyId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<PayslipMonthlyDetailDataM> payMonthlyDetailList = new ArrayList<PayslipMonthlyDetailDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYSLIP_MONTHLY_DETAIL_ID, PAYSLIP_MONTHLY_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_PAYSLIP_MONTHLY_DETAIL WHERE PAYSLIP_MONTHLY_ID = ? ");
			sql.append(" ORDER BY PAYSLIP_MONTHLY_DETAIL_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, payslipMonthlyId);

			rs = ps.executeQuery();

			while (rs.next()) {
				PayslipMonthlyDetailDataM payMonthlyDetailM = new PayslipMonthlyDetailDataM();
				payMonthlyDetailM.setSeq(payMonthlyDetailList.size()+1);
				payMonthlyDetailM.setPayslipMonthlyDetailId(rs.getString("PAYSLIP_MONTHLY_DETAIL_ID"));
				payMonthlyDetailM.setPayslipMonthlyId(rs.getString("PAYSLIP_MONTHLY_ID"));
				
				payMonthlyDetailM.setMonth(rs.getString("MONTH"));
				payMonthlyDetailM.setYear(rs.getString("YEAR"));
				payMonthlyDetailM.setAmount(rs.getBigDecimal("AMOUNT"));
				
				payMonthlyDetailM.setCreateBy(rs.getString("CREATE_BY"));
				payMonthlyDetailM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				payMonthlyDetailM.setUpdateBy(rs.getString("UPDATE_BY"));
				payMonthlyDetailM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				payMonthlyDetailList.add(payMonthlyDetailM);
			}

			return payMonthlyDetailList;
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

	@Override
	public void saveUpdateOrigPayslipMonthlyDetailM(
			PayslipMonthlyDetailDataM payslipMonthlyDetailM) throws ApplicationException {
		int returnRows = 0;
		payslipMonthlyDetailM.setUpdateBy(userId);
		returnRows = updateTableINC_PAYSLIP_MONTHLY_DETAIL(payslipMonthlyDetailM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_PAYSLIP_MONTHLY_DETAIL then call Insert method");
			payslipMonthlyDetailM.setCreateBy(userId);
			createOrigPayslipMonthlyDetailM(payslipMonthlyDetailM);
		}
	}

	private int updateTableINC_PAYSLIP_MONTHLY_DETAIL(
			PayslipMonthlyDetailDataM payslipMonthlyDetailM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_PAYSLIP_MONTHLY_DETAIL ");
			sql.append(" SET MONTH = ?, YEAR = ?, AMOUNT = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			
			sql.append(" WHERE PAYSLIP_MONTHLY_DETAIL_ID = ? AND PAYSLIP_MONTHLY_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, payslipMonthlyDetailM.getMonth());
			ps.setString(cnt++, payslipMonthlyDetailM.getYear());
			ps.setBigDecimal(cnt++, payslipMonthlyDetailM.getAmount());
			
			ps.setString(cnt++, payslipMonthlyDetailM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, payslipMonthlyDetailM.getPayslipMonthlyDetailId());
			ps.setString(cnt++, payslipMonthlyDetailM.getPayslipMonthlyId());
			
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
	public void deleteNotInKeyPayslipMonthlyDetail(
			ArrayList<PayslipMonthlyDetailDataM> payslipMonthlyDtlList,
			String payslipMonthlyId) throws ApplicationException {
		deleteNotInKeyTableINC_PAYSLIP_MONTHLY_DETAIL(payslipMonthlyDtlList, payslipMonthlyId);
	}

	private void deleteNotInKeyTableINC_PAYSLIP_MONTHLY_DETAIL(
			ArrayList<PayslipMonthlyDetailDataM> payslipMonthlyDtlList,
			String payslipMonthlyId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_PAYSLIP_MONTHLY_DETAIL ");
            sql.append(" WHERE PAYSLIP_MONTHLY_ID = ? ");
            
            if (!Util.empty(payslipMonthlyDtlList)) {
                sql.append(" AND PAYSLIP_MONTHLY_DETAIL_ID NOT IN ( ");

                for (PayslipMonthlyDetailDataM detailM: payslipMonthlyDtlList) {
                    sql.append(" '" + detailM.getPayslipMonthlyDetailId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, payslipMonthlyId);
            
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
