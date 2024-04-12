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
import com.eaf.orig.ulo.model.app.OpenedEndFundDetailDataM;

public class OrigOpenEndFundDetailDAOImpl extends OrigObjectDAO implements
		OrigOpenEndFundDetailDAO {
	public OrigOpenEndFundDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigOpenEndFundDetailDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigOpenEndFundDetailM(OpenedEndFundDetailDataM openEndFundDetailM)
			throws ApplicationException {
		logger.debug("openEndFundDetailM.getOpnEndFundDetailId() :"+ openEndFundDetailM.getOpnEndFundDetailId());
	    if(Util.empty(openEndFundDetailM.getOpnEndFundDetailId())){
			String opnEndFundDtlId = GenerateUnique.generate(
					OrigConstant.SeqNames.INC_OPN_END_FUND_DETAIL_PK);
			openEndFundDetailM.setOpnEndFundDetailId(opnEndFundDtlId);
			openEndFundDetailM.setCreateBy(userId);
	    }
	    openEndFundDetailM.setUpdateBy(userId);
		createTableINC_OPN_END_FUND_DETAIL(openEndFundDetailM);
	}

	private void createTableINC_OPN_END_FUND_DETAIL(
			OpenedEndFundDetailDataM openEndFundDetailM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO INC_OPN_END_FUND_DETAIL ");
			sql.append("( OPN_END_FUND_DETAIL_ID, OPN_END_FUND_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT,  ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ) ");

			sql.append(" VALUES(?,?,  ?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, openEndFundDetailM.getOpnEndFundDetailId());
			ps.setString(cnt++, openEndFundDetailM.getOpnEndFundId());
			
			ps.setString(cnt++, openEndFundDetailM.getMonth());
			ps.setString(cnt++, openEndFundDetailM.getYear());			
			ps.setBigDecimal(cnt++, openEndFundDetailM.getAmount());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, openEndFundDetailM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, openEndFundDetailM.getUpdateBy());
			
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
	public void deleteOrigOpenEndFundDetailM(String opnEndFundId, String opnEndFundDetailId) 
			throws ApplicationException {
		deleteTableINC_OPN_END_FUND_DETAIL(opnEndFundId, opnEndFundDetailId);
	}

	private void deleteTableINC_OPN_END_FUND_DETAIL(String opnEndFundId,
			String opnEndFundDetailId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE INC_OPN_END_FUND_DETAIL ");
			sql.append(" WHERE OPN_END_FUND_ID = ? ");
			if(!Util.empty(opnEndFundDetailId)) {
				sql.append(" AND OPN_END_FUND_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, opnEndFundId);
			if(!Util.empty(opnEndFundDetailId)) {
				ps.setString(2, opnEndFundDetailId);
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
	public ArrayList<OpenedEndFundDetailDataM> loadOrigOpenEndFundDetailM(
			String opnEndFundId) throws ApplicationException {
		ArrayList<OpenedEndFundDetailDataM> result = selectTableINC_OPN_END_FUND_DETAIL(opnEndFundId);
		return result;
	}

	private ArrayList<OpenedEndFundDetailDataM> selectTableINC_OPN_END_FUND_DETAIL(
			String opnEndFundId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<OpenedEndFundDetailDataM> opnEndDtlList = new ArrayList<OpenedEndFundDetailDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OPN_END_FUND_DETAIL_ID, OPN_END_FUND_ID, ");
			sql.append(" MONTH, YEAR, AMOUNT, ");
			sql.append(" CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY ");
			sql.append(" FROM INC_OPN_END_FUND_DETAIL WHERE OPN_END_FUND_ID = ? ");
			sql.append(" ORDER BY OPN_END_FUND_DETAIL_ID ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, opnEndFundId);

			rs = ps.executeQuery();

			while (rs.next()) {
				OpenedEndFundDetailDataM openEndFundDtlM = new OpenedEndFundDetailDataM();
				openEndFundDtlM.setSeq(opnEndDtlList.size()+1);
				openEndFundDtlM.setOpnEndFundDetailId(rs.getString("OPN_END_FUND_DETAIL_ID"));
				openEndFundDtlM.setOpnEndFundId(rs.getString("OPN_END_FUND_ID"));
				
				openEndFundDtlM.setMonth(rs.getString("MONTH"));
				openEndFundDtlM.setYear(rs.getString("YEAR"));
				openEndFundDtlM.setAmount(rs.getBigDecimal("AMOUNT"));
				
				openEndFundDtlM.setCreateBy(rs.getString("CREATE_BY"));
				openEndFundDtlM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				openEndFundDtlM.setUpdateBy(rs.getString("UPDATE_BY"));
				openEndFundDtlM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				opnEndDtlList.add(openEndFundDtlM);
			}

			return opnEndDtlList;
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
	public void saveUpdateOrigOpenEndFundDetailM(
			OpenedEndFundDetailDataM openEndFundDetailM) throws ApplicationException {
		int returnRows = 0;
		openEndFundDetailM.setUpdateBy(userId);
		returnRows = updateTableINC_OPN_END_FUND_DETAIL(openEndFundDetailM);
		if (returnRows == 0) {
			logger.debug("New record then can't update record in table INC_OPN_END_FUND_DETAIL then call Insert method");
			openEndFundDetailM.setCreateBy(userId);
			createOrigOpenEndFundDetailM(openEndFundDetailM);
		}
	}

	private int updateTableINC_OPN_END_FUND_DETAIL(
			OpenedEndFundDetailDataM openEndFundDetailM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE INC_OPN_END_FUND_DETAIL ");
			sql.append(" SET MONTH = ?, YEAR = ?, AMOUNT = ?, ");
			sql.append(" UPDATE_BY = ?,UPDATE_DATE = ? ");
			
			sql.append(" WHERE OPN_END_FUND_DETAIL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, openEndFundDetailM.getMonth());
			ps.setString(cnt++, openEndFundDetailM.getYear());
			ps.setBigDecimal(cnt++, openEndFundDetailM.getAmount());
			
			ps.setString(cnt++, openEndFundDetailM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			// WHERE CLAUSE
			ps.setString(cnt++, openEndFundDetailM.getOpnEndFundDetailId());
			
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
	public void deleteNotInKeyOpenEndFundDetail(
			ArrayList<OpenedEndFundDetailDataM> openEndFundDetailMList,
			String opnEndFundId) throws ApplicationException {
		deleteNotInKeyTableINC_OPN_END_FUND_DETAIL(openEndFundDetailMList, opnEndFundId);
	}

	private void deleteNotInKeyTableINC_OPN_END_FUND_DETAIL(
			ArrayList<OpenedEndFundDetailDataM> openEndFundDetailMList,
			String opnEndFundId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM INC_OPN_END_FUND_DETAIL ");
            sql.append(" WHERE OPN_END_FUND_ID = ? ");
            
            if (!Util.empty(openEndFundDetailMList)) {
                sql.append(" AND OPN_END_FUND_DETAIL_ID NOT IN ( ");

                for (OpenedEndFundDetailDataM openEndFundDtlM: openEndFundDetailMList) {
                    sql.append(" '" + openEndFundDtlM.getOpnEndFundDetailId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, opnEndFundId);
            
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
