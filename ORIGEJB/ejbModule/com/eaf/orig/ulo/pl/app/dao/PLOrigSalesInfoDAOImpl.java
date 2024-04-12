package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Vector;

import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigSalesInfoDAOImpl extends OrigObjectDAO implements PLOrigSalesInfoDAO{
	private static Logger log = Logger.getLogger(PLOrigSalesInfoDAOImpl.class);	
	@Override
	public void saveUpdatePLOrigSaleInfo(PLSaleInfoDataM saleInfoM, String appRecId) throws PLOrigApplicationException {
		try{
			if(null != saleInfoM){
				if(OrigUtil.isEmptyString(saleInfoM.getSalesId())){
					String saleID = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.SALES_ID);
					saleInfoM.setSalesId(saleID);
				}			
				int returnRows = this.updateTableOrig_sale_Info(saleInfoM, appRecId);
				if(returnRows == 0){
					this.insertTableOrig_Sale_Info(saleInfoM, appRecId);
				}		
			}
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	public void DeleteTableORIG_SALE_INFO(Vector<String> salesIDVect, String appRecId)throws PLOrigApplicationException{	
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_SALE_INFO ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND SALES_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(salesIDVect));			
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql= "+dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, appRecId);			
			PreparedStatementParameter(index, ps, salesIDVect);		
			ps.executeUpdate();

		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	
	
	public int updateTableOrig_sale_Info (PLSaleInfoDataM saleInfoM, String appRecId) throws PLOrigApplicationException {
		int returnRows;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			if(null == saleInfoM.getCloneBranchCode()){
				saleInfoM.setCloneBranchCode("");
			}
			if(null == saleInfoM.getSalesBranchCode()){
				saleInfoM.setSalesBranchCode("");
			}
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_SALE_INFO ");
			sql.append(" SET SALES_ID=?, SALES_GUARANTEE=?, REF_NAME=?, REF_BRANCH_CODE=?, REF_PHONE_NO=? ");
			sql.append(", REF_FAX_NO=?, SALES_NAME=?, SALES_BRANCH_CODE=?, SALES_PHONE_NO=?, SALES_FAX_NO=? ");
			sql.append(", SALES_COMMENT=?, UPDATE_DATE=SYSDATE, UPDATE_BY=?, SALES_POSITION=?, CASH_DAYONE_NAME=? ");
			sql.append(", CASH_DAYONE_BRANCH_CODE=?, CASH_DAYONE_PHONE_NO=?, CASH_DAYONE_FAX_NO=?, REMARK=? ");
			
			if(!saleInfoM.getCloneBranchCode().equals(saleInfoM.getSalesBranchCode())){
				sql.append(" ,REGION=PKA_RPT_R10_REGION.GETKBANKREGIONNO(?),ZONE=PKA_RPT_R10_REGION.GETKBANKZONENO(?) ");
			}
			
			sql.append(" WHERE SALES_ID=? AND APPLICATION_RECORD_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, saleInfoM.getSalesId());
			ps.setString(index++, saleInfoM.getSalesGuarantee());
			ps.setString(index++, saleInfoM.getRefName());
			ps.setString(index++, saleInfoM.getRefBranchCode());
			ps.setString(index++, saleInfoM.getRefPhoneNo());
			
			ps.setString(index++, saleInfoM.getRefFaxNo());
			ps.setString(index++, saleInfoM.getSalesName());
			ps.setString(index++, saleInfoM.getSalesBranchCode());
			ps.setString(index++, saleInfoM.getSalesPhoneNo());
			ps.setString(index++, saleInfoM.getSalesFaxNo());
			
			ps.setString(index++, saleInfoM.getSalesComment());
			ps.setString(index++, saleInfoM.getUpdateBy());
			ps.setString(index++, saleInfoM.getSalesPosition());
			ps.setString(index++, saleInfoM.getCashDayOneName());
			
			ps.setString(index++, saleInfoM.getCashDayOneBranchCode());
			ps.setString(index++, saleInfoM.getCashDayOnePhoneNo());
			ps.setString(index++, saleInfoM.getCashDayOneFax());
			ps.setString(index++, saleInfoM.getRemark());
			
			if(!saleInfoM.getCloneBranchCode().equals(saleInfoM.getSalesBranchCode())){
				ps.setString(index++, saleInfoM.getSalesBranchCode());
				ps.setString(index++, saleInfoM.getSalesBranchCode());
			}
			
			ps.setString(index++, saleInfoM.getSalesId());
			ps.setString(index++, appRecId);
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return returnRows;
	}
	
	private void insertTableOrig_Sale_Info (PLSaleInfoDataM saleInfoM, String appRecId) throws PLOrigApplicationException{		
		PreparedStatement ps = null;	
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_SALE_INFO ");
			sql.append("( SALES_ID, APPLICATION_RECORD_ID, SALES_GUARANTEE, REF_NAME, REF_BRANCH_CODE");
			sql.append(", REF_PHONE_NO, REF_FAX_NO, SALES_NAME ,SALES_BRANCH_CODE, SALES_PHONE_NO");
			sql.append(", SALES_FAX_NO, SALES_COMMENT, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, SALES_POSITION, CASH_DAYONE_NAME, CASH_DAYONE_BRANCH_CODE, CASH_DAYONE_PHONE_NO ");
			sql.append(", CASH_DAYONE_FAX_NO, REMARK ,REGION ,ZONE) ");
			sql.append(" VALUES (?,?,?,?,?   ,?,?,?,?,?   ,?,?,SYSDATE,?,SYSDATE  ,?,?,?,?,? ");
			sql.append(" ,?,?,PKA_RPT_R10_REGION.GETKBANKREGIONNO(?),PKA_RPT_R10_REGION.GETKBANKZONENO(?) ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, saleInfoM.getSalesId());
			ps.setString(index++, appRecId);
			ps.setString(index++, saleInfoM.getSalesGuarantee());
			ps.setString(index++, saleInfoM.getRefName());
			ps.setString(index++, saleInfoM.getRefBranchCode());
			
			ps.setString(index++, saleInfoM.getRefPhoneNo());
			ps.setString(index++, saleInfoM.getRefFaxNo());
			ps.setString(index++, saleInfoM.getSalesName());
			ps.setString(index++, saleInfoM.getSalesBranchCode());
			ps.setString(index++, saleInfoM.getSalesPhoneNo());
			
			ps.setString(index++, saleInfoM.getSalesFaxNo());
			ps.setString(index++, saleInfoM.getSalesComment());
			ps.setString(index++, saleInfoM.getCreateBy());
			
			ps.setString(index++, saleInfoM.getUpdateBy());
			ps.setString(index++, saleInfoM.getSalesPosition());
			ps.setString(index++, saleInfoM.getCashDayOneName());
			ps.setString(index++, saleInfoM.getCashDayOneBranchCode());
			ps.setString(index++, saleInfoM.getCashDayOnePhoneNo());

			ps.setString(index++, saleInfoM.getCashDayOneFax());
			ps.setString(index++, saleInfoM.getRemark());
			ps.setString(index++, saleInfoM.getSalesBranchCode());
			ps.setString(index++, saleInfoM.getSalesBranchCode());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	public PLSaleInfoDataM loadPLOrigSaleInfoDataM (String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLSaleInfoDataM saleInfoDataM = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SALES_ID, SALES_GUARANTEE, REF_NAME, REF_BRANCH_CODE, REF_PHONE_NO ");
			sql.append(", REF_FAX_NO, SALES_NAME ,SALES_BRANCH_CODE, SALES_PHONE_NO, SALES_FAX_NO ");
			sql.append(", SALES_COMMENT, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(", SALES_POSITION, CASH_DAYONE_NAME, CASH_DAYONE_BRANCH_CODE, CASH_DAYONE_PHONE_NO, CASH_DAYONE_FAX_NO ");
			sql.append(", REMARK ");
			sql.append("FROM ORIG_SALE_INFO WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			if (rs.next()){	
				saleInfoDataM = new PLSaleInfoDataM();
				int index = 1;				
				saleInfoDataM.setSalesId(rs.getString(index++));
				saleInfoDataM.setSalesGuarantee(rs.getString(index++));
				saleInfoDataM.setRefName(rs.getString(index++));
				saleInfoDataM.setRefBranchCode(rs.getString(index++));
				saleInfoDataM.setRefPhoneNo(rs.getString(index++));
				
				saleInfoDataM.setRefFaxNo(rs.getString(index++));
				saleInfoDataM.setSalesName(rs.getString(index++));
				saleInfoDataM.setSalesBranchCode(rs.getString(index++));
				saleInfoDataM.setSalesPhoneNo(rs.getString(index++));
				saleInfoDataM.setSalesFaxNo(rs.getString(index++));
				
				saleInfoDataM.setSalesComment(rs.getString(index++));
				saleInfoDataM.setCreateDate(rs.getTimestamp(index++));
				saleInfoDataM.setCreateBy(rs.getString(index++));
				saleInfoDataM.setUpdateDate(rs.getTimestamp(index++));
				saleInfoDataM.setUpdateBy(rs.getString(index++));
				
				saleInfoDataM.setUpdateBy(rs.getString(index++));
				saleInfoDataM.setCashDayOneName(rs.getString(index++));
				saleInfoDataM.setCashDayOneBranchCode(rs.getString(index++));
				saleInfoDataM.setCashDayOnePhoneNo(rs.getString(index++));
				saleInfoDataM.setCashDayOneFax(rs.getString(index++));

				saleInfoDataM.setRemark(rs.getString(index++));		
				
				saleInfoDataM.setCloneBranchCode(saleInfoDataM.getSalesBranchCode());
				
			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return saleInfoDataM;
	}

	@Override
	public String getBranchDecription(String branchCode, Locale localeID)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String result = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			if(Locale.ENGLISH.equals(localeID)){
				sql.append("select vb.ADD_ENG_SHORT_ADDR from vw_ms_branch_code vb where vb.KBANK_BR_NO = ?");
			}else {
				sql.append("select vb.ADD_THAI_SHORT_ADDR branch_desc from vw_ms_branch_code vb where vb.KBANK_BR_NO = ?");
			}
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, branchCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("branch_desc");
			}			
			return result;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	public PLSaleInfoDataM loadPLOrigSaleInfoDataM_IncreaseDecrease (String appRecId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SALES_GUARANTEE, REF_NAME, REF_BRANCH_CODE, REF_PHONE_NO ");
			sql.append(", REF_FAX_NO, SALES_NAME ,SALES_BRANCH_CODE, SALES_PHONE_NO, SALES_FAX_NO ");
			sql.append(", SALES_COMMENT, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(", SALES_POSITION, CASH_DAYONE_NAME, CASH_DAYONE_BRANCH_CODE, CASH_DAYONE_PHONE_NO, CASH_DAYONE_FAX_NO ");
			sql.append(", REMARK ");
			sql.append("FROM ORIG_SALE_INFO WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appRecId);

			rs = ps.executeQuery();
			PLSaleInfoDataM saleInfoDataM = new PLSaleInfoDataM();
			if (rs.next()) {				
				int index = 1;				
				saleInfoDataM.setSalesGuarantee(rs.getString(index++));
				saleInfoDataM.setRefName(rs.getString(index++));
				saleInfoDataM.setRefBranchCode(rs.getString(index++));
				saleInfoDataM.setRefPhoneNo(rs.getString(index++));
				
				saleInfoDataM.setRefFaxNo(rs.getString(index++));
				saleInfoDataM.setSalesName(rs.getString(index++));
				saleInfoDataM.setSalesBranchCode(rs.getString(index++));
				saleInfoDataM.setSalesPhoneNo(rs.getString(index++));
				saleInfoDataM.setSalesFaxNo(rs.getString(index++));
				
				saleInfoDataM.setSalesComment(rs.getString(index++));
				saleInfoDataM.setCreateDate(rs.getTimestamp(index++));
				saleInfoDataM.setCreateBy(rs.getString(index++));
				saleInfoDataM.setUpdateDate(rs.getTimestamp(index++));
				saleInfoDataM.setUpdateBy(rs.getString(index++));
				
				saleInfoDataM.setUpdateBy(rs.getString(index++));
				saleInfoDataM.setCashDayOneName(rs.getString(index++));
				saleInfoDataM.setCashDayOneBranchCode(rs.getString(index++));
				saleInfoDataM.setCashDayOnePhoneNo(rs.getString(index++));
				saleInfoDataM.setCashDayOneFax(rs.getString(index++));

				saleInfoDataM.setRemark(rs.getString(index++));
				
				saleInfoDataM.setCloneBranchCode(saleInfoDataM.getSalesBranchCode());
				
			}			
			return saleInfoDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

}
