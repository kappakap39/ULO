package com.eaf.orig.ulo.app.dao;

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
import com.eaf.orig.ulo.model.app.SaleInfoDataM;

public class OrigSaleInfoDAOImpl extends OrigObjectDAO implements
		OrigSaleInfoDAO {
	public OrigSaleInfoDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigSaleInfoDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigSaleInfoM(SaleInfoDataM saleInfoM)
			throws ApplicationException {
		logger.debug("saleInfoM.getSalesId() :"+saleInfoM.getSalesId());
	    if(Util.empty(saleInfoM.getSalesInfoId())){
			String salesInfoId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_SALE_INFO_PK);
			saleInfoM.setSalesInfoId(salesInfoId);
			saleInfoM.setCreateBy(userId);
	    }
	    saleInfoM.setUpdateBy(userId);
		createTableORIG_SALE_INFO(saleInfoM);
	}

	private void createTableORIG_SALE_INFO(SaleInfoDataM saleInfoM) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_SALE_INFO ");
			sql.append("( SALES_INFO_ID, APPLICATION_GROUP_ID, SALES_ID, ");
			sql.append(" SALES_NAME, SALES_BRANCH_CODE, SALES_PHONE_NO, SALES_COMMENT, ");
			sql.append(" SALES_TYPE, REGION, ZONE, SALES_DEPARTMENT_NAME, SALES_RC_CODE, ");
			sql.append(" SALE_CHANNEL, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,");
			sql.append(" SALES_BRANCH_NAME) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?, ?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, saleInfoM.getSalesInfoId());
			ps.setString(cnt++, saleInfoM.getApplicationGroupId());
			ps.setString(cnt++, saleInfoM.getSalesId());
			
			ps.setString(cnt++, saleInfoM.getSalesName());
			ps.setString(cnt++, saleInfoM.getSalesBranchCode());
			ps.setString(cnt++, saleInfoM.getSalesPhoneNo());
			ps.setString(cnt++, saleInfoM.getSalesComment());
			
			ps.setString(cnt++, saleInfoM.getSalesType());
			ps.setString(cnt++, saleInfoM.getRegion());
			ps.setString(cnt++, saleInfoM.getZone());
			ps.setString(cnt++, saleInfoM.getSalesDeptName());
			ps.setString(cnt++, saleInfoM.getSalesRCCode());
			
			ps.setString(cnt++, saleInfoM.getSaleChannel());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, saleInfoM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, saleInfoM.getUpdateBy());
			ps.setString(cnt++, saleInfoM.getSalesBranchName());
			
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
	public void deleteOrigSaleInfoM(String applicationGroupId, String salesInfoId)
			throws ApplicationException {
		deleteTableORIG_SALE_INFO(applicationGroupId, salesInfoId);
	}

	private void deleteTableORIG_SALE_INFO(String applicationGroupId, String salesInfoId) 
			throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE ORIG_SALE_INFO ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			if(salesInfoId != null && !salesInfoId.isEmpty()) {
				sql.append(" AND SALES_INFO_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			if(salesInfoId != null && !salesInfoId.isEmpty()) {
				ps.setString(2, salesInfoId);
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
	public ArrayList<SaleInfoDataM> loadOrigSaleInfoM(
			String applicationGroupId, Connection conn)
			throws ApplicationException {
		return selectTableORIG_SALE_INFO(applicationGroupId, conn);
	}
	@Override
	public ArrayList<SaleInfoDataM> loadOrigSaleInfoM(String applicationGroupId)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_SALE_INFO(applicationGroupId,conn);
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

	private ArrayList<SaleInfoDataM> selectTableORIG_SALE_INFO(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<SaleInfoDataM> saleInfoList = new ArrayList<SaleInfoDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT SALES_INFO_ID, APPLICATION_GROUP_ID, SALES_ID, ");
			sql.append(" SALES_NAME, SALES_BRANCH_CODE, SALES_PHONE_NO, SALES_COMMENT, ");
			sql.append(" SALES_TYPE, REGION, ZONE, SALES_DEPARTMENT_NAME, SALES_RC_CODE, ");
			sql.append(" SALE_CHANNEL, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY,");
			sql.append(" SALES_BRANCH_NAME ");
			sql.append(" FROM ORIG_SALE_INFO WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			while(rs.next()) {
				SaleInfoDataM saleInfoM = new SaleInfoDataM();
				saleInfoM.setSalesInfoId(rs.getString("SALES_INFO_ID"));
				saleInfoM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				saleInfoM.setSalesId(rs.getString("SALES_ID"));
				
				saleInfoM.setSalesName(rs.getString("SALES_NAME"));
				saleInfoM.setSalesBranchCode(rs.getString("SALES_BRANCH_CODE"));
				saleInfoM.setSalesPhoneNo(rs.getString("SALES_PHONE_NO"));
				saleInfoM.setSalesComment(rs.getString("SALES_COMMENT"));
				
				saleInfoM.setSalesType(rs.getString("SALES_TYPE"));
				saleInfoM.setRegion(rs.getString("REGION"));
				saleInfoM.setZone(rs.getString("ZONE"));
				saleInfoM.setSalesDeptName(rs.getString("SALES_DEPARTMENT_NAME"));
				saleInfoM.setSalesRCCode(rs.getString("SALES_RC_CODE"));
				saleInfoM.setSaleChannel(rs.getString("SALE_CHANNEL"));
				
				saleInfoM.setCreateBy(rs.getString("CREATE_BY"));
				saleInfoM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				saleInfoM.setUpdateBy(rs.getString("UPDATE_BY"));
				saleInfoM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				saleInfoM.setSalesBranchName(rs.getString("SALES_BRANCH_NAME"));
				
				saleInfoList.add(saleInfoM);
			}

			return saleInfoList;
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
	public void saveUpdateOrigSaleInfoM(SaleInfoDataM saleInfoM)
			throws ApplicationException {
		int returnRows = 0;
		saleInfoM.setUpdateBy(userId);
		returnRows = updateTableORIG_SALE_INFO(saleInfoM);
		if (returnRows == 0) {
			saleInfoM.setCreateBy(userId);
			createOrigSaleInfoM(saleInfoM);
		}
	}

	private int updateTableORIG_SALE_INFO(SaleInfoDataM saleInfoM) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_SALE_INFO ");
			sql.append(" SET SALES_ID = ?, SALES_NAME = ?, ");
			sql.append(" SALES_BRANCH_CODE = ?, SALES_PHONE_NO = ?, SALES_COMMENT = ?, ");
			sql.append(" SALES_TYPE = ?, REGION = ?, ZONE = ?, SALES_DEPARTMENT_NAME = ?, ");
			sql.append(" SALES_RC_CODE = ?, SALE_CHANNEL = ?, UPDATE_DATE = ?, UPDATE_BY = ?, ");			
			sql.append(" SALES_BRANCH_NAME=? ");			
			sql.append(" WHERE SALES_INFO_ID = ? AND APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, saleInfoM.getSalesId());
			ps.setString(cnt++, saleInfoM.getSalesName());
			
			ps.setString(cnt++, saleInfoM.getSalesBranchCode());
			ps.setString(cnt++, saleInfoM.getSalesPhoneNo());
			ps.setString(cnt++, saleInfoM.getSalesComment());
			
			ps.setString(cnt++, saleInfoM.getSalesType());
			ps.setString(cnt++, saleInfoM.getRegion());
			ps.setString(cnt++, saleInfoM.getZone());
			ps.setString(cnt++, saleInfoM.getSalesDeptName());
			
			ps.setString(cnt++, saleInfoM.getSalesRCCode());
			ps.setString(cnt++, saleInfoM.getSaleChannel());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, saleInfoM.getUpdateBy());
			
			ps.setString(cnt++, saleInfoM.getSalesBranchName());
			// WHERE CLAUSE
			ps.setString(cnt++, saleInfoM.getSalesInfoId());
			ps.setString(cnt++, saleInfoM.getApplicationGroupId());
			
			returnRows = ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeySaleInfo(ArrayList<SaleInfoDataM> saleInfoList,
			String applicationGroupId) throws ApplicationException {
		deleteNotInKeyTableORIG_SALE_INFO(saleInfoList, applicationGroupId);
	}

	private void deleteNotInKeyTableORIG_SALE_INFO(
			ArrayList<SaleInfoDataM> saleInfoList, String applicationGroupId) 
					throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_SALE_INFO ");
            sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
            
            if (saleInfoList != null && !saleInfoList.isEmpty()) {
                sql.append(" AND SALES_INFO_ID NOT IN ( ");
                for (SaleInfoDataM saleInfoM: saleInfoList) {
                	logger.debug("saleInfoM.getSalesInfoId() = "+saleInfoM.getSalesInfoId());
                    sql.append(" '" + saleInfoM.getSalesInfoId() + "', ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, applicationGroupId);
            
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
