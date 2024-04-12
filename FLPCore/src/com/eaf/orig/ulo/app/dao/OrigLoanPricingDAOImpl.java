package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.LoanPricingDataM;

public class OrigLoanPricingDAOImpl extends OrigObjectDAO implements OrigLoanPricingDAO {
	private String userId = "";
	
	public OrigLoanPricingDAOImpl(){}
	
	public OrigLoanPricingDAOImpl(String userId){
		this.userId = userId;
	}
	
	@Override
	public void saveUpdateOrigLoanPricing(LoanPricingDataM loanPricing) throws ApplicationException {
		if(loanPricing == null)return;
		loanPricing.setUpdateBy(userId);
		loanPricing.setCreateBy(userId);
		int updatedRow = updateTable_ORIG_LOAN_PRICING(loanPricing);
		if(updatedRow == 0){
			createOrigLoanPricing(loanPricing);
		}
	}
	
	public void createOrigLoanPricing(LoanPricingDataM loanPricing) throws ApplicationException {
		if(loanPricing == null)return;
		if(loanPricing.getPricingId() == null){
			String id = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_LOAN_PRICING_PK );
			loanPricing.setPricingId(id);
			loanPricing.setCreateBy(userId);
		}
		
		createTable_ORIG_LOAN_PRICING(loanPricing);
	}
	
	private int createTable_ORIG_LOAN_PRICING(LoanPricingDataM loanPricing) throws ApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_LOAN_PRICING ");
			sql.append("( PRICING_ID, LOAN_ID, FEE_START_MONTH, FEE_END_MONTH, TERMINATION_FEE_FLAG," +
					" TERMINATION_FEE_CODE, FEE_WAIVE_CODE, CREATE_DATE, CREATE_BY, UPDATE_DATE," +
					" UPDATE_BY ) ");

			sql.append(" VALUES(?,?,?,?,?    ,?,?,?,?,?   ,?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, loanPricing.getPricingId());
			ps.setString(cnt++, loanPricing.getLoanId());
			setInteger(ps, cnt++, loanPricing.getFeeStartMonth());
			setInteger(ps, cnt++, loanPricing.getFeeEndMonth());
			ps.setString(cnt++, loanPricing.getTerminationFeeFlag());
			
			ps.setString(cnt++, loanPricing.getTerminationFeeCode());
			ps.setString(cnt++, loanPricing.getFeeWaiveCode());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, loanPricing.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setString(cnt++, loanPricing.getUpdateBy());
				
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
		
		return 0;
	}
	
	private int updateTable_ORIG_LOAN_PRICING(LoanPricingDataM loanPricing) throws ApplicationException{
		PreparedStatement ps = null;
		Connection conn = null;
		int result = 0;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_LOAN_PRICING SET");
			sql.append(" LOAN_ID = ?, FEE_START_MONTH = ?, FEE_END_MONTH = ?, TERMINATION_FEE_FLAG = ?, TERMINATION_FEE_CODE = ?," +
					" FEE_WAIVE_CODE = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE PRICING_ID = ? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, loanPricing.getLoanId());
			setInteger(ps, cnt++, loanPricing.getFeeStartMonth());
			setInteger(ps, cnt++, loanPricing.getFeeEndMonth());
			ps.setString(cnt++, loanPricing.getTerminationFeeFlag());			
			ps.setString(cnt++, loanPricing.getTerminationFeeCode());
			
			ps.setString(cnt++, loanPricing.getFeeWaiveCode());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());			
			ps.setString(cnt++, loanPricing.getUpdateBy());
			
			//Where clause
			ps.setString(cnt++, loanPricing.getPricingId());
			
			result = ps.executeUpdate();
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
		
		return result;
	}
	
	@Override
	public void deleteNotInKeyLoanPricing(List<LoanPricingDataM> loanPricings, String loanId) throws ApplicationException{
		
		deleteNotInKeyTableORIG_LOAN_PRICING(loanPricings, loanId);
	}
	
	private int deleteNotInKeyTableORIG_LOAN_PRICING(List<LoanPricingDataM> loanPricings, String loanId) throws ApplicationException{
		Connection conn = null;
		PreparedStatement ps = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_LOAN_PRICING ");
            sql.append(" WHERE LOAN_ID = ? ");
            
            if (!Util.empty(loanPricings)) {
                sql.append(" AND PRICING_ID NOT IN ( ");
                
                String prefix = "";
                final String secondPrefix = ", ";
                for (LoanPricingDataM loanTierM: loanPricings) {
                    sql.append(prefix+"'" + loanTierM.getPricingId()+"'");
                    prefix = secondPrefix;
                }
                
                sql.append(")");
            }

            String dSql = String.valueOf(sql);
            logger.debug("deleteNotInKeyTableORIG_LOAN_PRICING sql = "+dSql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, loanId);
            
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
		
		return 0;
	}
	
	@Override
	public ArrayList<LoanPricingDataM> loadLoanPricingByLoanId(String loanId) throws ApplicationException {
		if(loanId == null)return null;
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_LOAN_PRICING(loanId,conn);
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
	public ArrayList<LoanPricingDataM> loadLoanPricingByLoanId(String loanId,
			Connection conn) throws ApplicationException {
		return selectTableORIG_LOAN_PRICING(loanId, conn);
	}
	private ArrayList<LoanPricingDataM> selectTableORIG_LOAN_PRICING(String loanId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LoanPricingDataM> loanPricings = new ArrayList<LoanPricingDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT  ");
			sql.append(" PRICING_ID, LOAN_ID, FEE_START_MONTH, FEE_END_MONTH, TERMINATION_FEE_FLAG," +
					" TERMINATION_FEE_CODE, FEE_WAIVE_CODE, CREATE_DATE, CREATE_BY, UPDATE_DATE," +
					" UPDATE_BY  ");
			sql.append(" FROM ORIG_LOAN_PRICING WHERE LOAN_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanId);

			rs = ps.executeQuery();

			while(rs.next()) {
				LoanPricingDataM loanPricing = new LoanPricingDataM();
				loanPricing.setPricingId(rs.getString("PRICING_ID"));
				loanPricing.setLoanId(rs.getString("LOAN_ID"));
				loanPricing.setFeeStartMonth(getInteger(rs,"FEE_START_MONTH"));
				loanPricing.setFeeEndMonth(getInteger(rs,"FEE_END_MONTH"));
				loanPricing.setTerminationFeeFlag(rs.getString("TERMINATION_FEE_FLAG"));
				loanPricing.setTerminationFeeCode(rs.getString("TERMINATION_FEE_CODE"));
				loanPricing.setFeeWaiveCode(rs.getString("FEE_WAIVE_CODE"));
				loanPricing.setCreateDate(rs.getDate("CREATE_DATE"));
				loanPricing.setCreateBy(rs.getString("CREATE_BY"));
				loanPricing.setUpdateDate(rs.getDate("UPDATE_DATE"));
				loanPricing.setUpdateBy(rs.getString("UPDATE_BY"));
			
				loanPricings.add(loanPricing);
			}

			return loanPricings;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection( rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteLoanPricing(String loanId, String pricingId)throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_LOAN_PRICING ");
			sql.append(" WHERE LOAN_ID = ? ");
			if(!Util.empty(pricingId)) {
				sql.append(" AND PRICING_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("loanId=" + loanId);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, loanId);
			if(!Util.empty(pricingId)) {
				ps.setString(2, pricingId);
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
}
