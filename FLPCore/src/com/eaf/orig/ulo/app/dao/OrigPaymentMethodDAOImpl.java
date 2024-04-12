package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;

public class OrigPaymentMethodDAOImpl extends OrigObjectDAO implements OrigPaymentMethodDAO {
	public OrigPaymentMethodDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPaymentMethodDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPaymentMethodM(PaymentMethodDataM paymentMethodM,Connection conn) throws ApplicationException {
		if(null != paymentMethodM){
			logger.debug("paymentMethodM.getPaymentMethodId() :"+paymentMethodM.getPaymentMethodId());
		    if(Util.empty(paymentMethodM.getPaymentMethodId())){
				String paymentMethodId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PAYMENT_METHOD_PK,conn);
				paymentMethodM.setPaymentMethodId(paymentMethodId);
				paymentMethodM.setCreateBy(userId);
		    }
		    paymentMethodM.setUpdateBy(userId);
			createTableORIG_PAYMENT_METHOD(paymentMethodM,conn);
		}
	}
	@Override
	public void createOrigPaymentMethodM(PaymentMethodDataM paymentMethodM)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigPaymentMethodM(paymentMethodM, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	private void createTableORIG_PAYMENT_METHOD(
			PaymentMethodDataM paymentMethodM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PAYMENT_METHOD ");
			sql.append("( PAYMENT_METHOD_ID, PAYMENT_METHOD, PAYMENT_RATIO, ");
			sql.append(" ACCOUNT_NO, ACCOUNT_NAME, DUE_CYCLE, ");
			sql.append(" ACCOUNT_TYPE, BANK_CODE, PRODUCT_TYPE, COMPLETE_DATA, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, PERSONAL_ID ) ");

			sql.append(" VALUES(?,?,?, ?,?,?, ?,?,?,?, ?,?,?,?, ?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, paymentMethodM.getPaymentMethodId());
			ps.setString(cnt++, paymentMethodM.getPaymentMethod());
			ps.setBigDecimal(cnt++, paymentMethodM.getPaymentRatio());
			
			ps.setString(cnt++, paymentMethodM.getAccountNo());
			ps.setString(cnt++, paymentMethodM.getAccountName());
			ps.setBigDecimal(cnt++, paymentMethodM.getDueCycle());
			
			ps.setString(cnt++, paymentMethodM.getAccountType());
			ps.setString(cnt++, paymentMethodM.getBankCode());
			ps.setString(cnt++, paymentMethodM.getProductType());
			ps.setString(cnt++, paymentMethodM.getCompleteData());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, paymentMethodM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, paymentMethodM.getUpdateBy());
			ps.setString(cnt++, paymentMethodM.getPersonalId());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e);
			}
		}
	}

	@Override
	public void deleteOrigPaymentMethodM(String loanId, String paymentMethodId)
			throws ApplicationException {
		deleteTableORIG_PAYMENT_METHOD(loanId, paymentMethodId);
	}

	private void deleteTableORIG_PAYMENT_METHOD(String loanId, 
			String paymentMethodId) throws ApplicationException {
//		PreparedStatement ps = null;
//		Connection conn = null;
//		try {
//			//conn = Get Connection
//			conn = getConnection();
//			StringBuffer sql = new StringBuffer("");
//			sql.append("DELETE ORIG_PAYMENT_METHOD ");
//			sql.append(" WHERE LOAN_ID = ?");
//			if(paymentMethodId != null && !paymentMethodId.isEmpty()) {
//				sql.append(" AND PAYMENT_METHOD_ID = ? ");
//			}
//			String dSql = String.valueOf(sql);
//			logger.debug("Sql=" + dSql);
//			ps = conn.prepareStatement(dSql);
//			ps.setString(1, loanId);
//			if(paymentMethodId != null && !paymentMethodId.isEmpty()) {
//				ps.setString(2, paymentMethodId);
//			}
//			ps.executeUpdate();
//
//		} catch (Exception e) {
//			logger.fatal(e);
//			throw new ApplicationException(e.getMessage());
//		} finally {
//			try {
//				closeConnection(conn, ps);
//			} catch (Exception e) {
//				logger.fatal(e);
//				throw new ApplicationException(e.getMessage());
//			}
//		}
	}

	@Override
	public ArrayList<PaymentMethodDataM> loadOrigPaymentMethodM(List<String> paymentIds) throws ApplicationException {
		ArrayList<PaymentMethodDataM> result = new ArrayList<PaymentMethodDataM>();
		for(String paymentMethodId : paymentIds) {
			PaymentMethodDataM paymentMethodM = selectTableORIG_PAYMENT_METHOD(paymentMethodId);
			if(!Util.empty(paymentMethodM)) {
				result.add(paymentMethodM);
			}
		}
		return result;
	}
	@Override
	public ArrayList<PaymentMethodDataM> loadOrigPaymentMethod(ArrayList<String> personalIds) throws ApplicationException {
		if(Util.empty(personalIds)){
			return new ArrayList<PaymentMethodDataM>();
		}
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_PAYMENT_METHOD(personalIds,conn);
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
	public ArrayList<PaymentMethodDataM> loadOrigPaymentMethod(
			ArrayList<String> personalIds, Connection conn)
			throws ApplicationException {
		return selectTableORIG_PAYMENT_METHOD(personalIds, conn);
	}
	private PaymentMethodDataM selectTableORIG_PAYMENT_METHOD(
			String paymentMethodId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PAYMENT_METHOD_ID, PAYMENT_METHOD, PAYMENT_RATIO, ");
			sql.append(" ACCOUNT_NO, ACCOUNT_NAME, DUE_CYCLE, ");
			sql.append(" ACCOUNT_TYPE, BANK_CODE, PRODUCT_TYPE, COMPLETE_DATA, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, PERSONAL_ID ");
			sql.append(" FROM ORIG_PAYMENT_METHOD WHERE PAYMENT_METHOD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, paymentMethodId);

			rs = ps.executeQuery();

			if(rs.next()) {
				PaymentMethodDataM payMethodM = new PaymentMethodDataM();
				payMethodM.setPaymentMethodId(rs.getString("PAYMENT_METHOD_ID"));
				payMethodM.setPaymentMethod(rs.getString("PAYMENT_METHOD"));
				payMethodM.setPaymentRatio(rs.getBigDecimal("PAYMENT_RATIO"));
				
				payMethodM.setAccountNo(rs.getString("ACCOUNT_NO"));
				payMethodM.setAccountName(rs.getString("ACCOUNT_NAME"));
				payMethodM.setDueCycle(rs.getBigDecimal("DUE_CYCLE"));
				
				payMethodM.setAccountType(rs.getString("ACCOUNT_TYPE"));
				payMethodM.setBankCode(rs.getString("BANK_CODE"));
				payMethodM.setProductType(rs.getString("PRODUCT_TYPE"));
				payMethodM.setCompleteData(rs.getString("COMPLETE_DATA"));
				
				payMethodM.setCreateBy(rs.getString("CREATE_BY"));
				payMethodM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				payMethodM.setUpdateBy(rs.getString("UPDATE_BY"));
				payMethodM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				payMethodM.setPersonalId(rs.getString("PERSONAL_ID"));
				
				return payMethodM;
			}

			return null;
		} catch (Exception e) {
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	/**If there is some personal ids, the process will pick PaymentMethod by personalIds
	 * 
	 * @param appGroupId
	 * @param personalIds
	 * @return List of PaymentMethod
	 * @throws ApplicationException
	 */
	private ArrayList<PaymentMethodDataM> selectTableORIG_PAYMENT_METHOD(ArrayList<String> personalIds,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PaymentMethodDataM> result = new ArrayList<PaymentMethodDataM>();
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT PAYMENT_METHOD_ID, PAYMENT_METHOD, PAYMENT_RATIO, ACCOUNT_NO, ACCOUNT_NAME, DUE_CYCLE, ");
			sql.append(" ACCOUNT_TYPE, BANK_CODE, PRODUCT_TYPE, COMPLETE_DATA, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, PERSONAL_ID ");
			sql.append(" FROM ORIG_PAYMENT_METHOD ");
			sql.append(" WHERE PERSONAL_ID IN (");
			sql.append(joinSQLString(personalIds));
			sql.append("  )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(sql.toString());
			rs = null;
			rs = ps.executeQuery();
			while(rs.next()) {
				PaymentMethodDataM payMethodM = new PaymentMethodDataM();
				payMethodM.setPaymentMethodId(rs.getString("PAYMENT_METHOD_ID"));
				payMethodM.setPaymentMethod(rs.getString("PAYMENT_METHOD"));
				payMethodM.setPaymentRatio(rs.getBigDecimal("PAYMENT_RATIO"));
				
				payMethodM.setAccountNo(rs.getString("ACCOUNT_NO"));
				payMethodM.setAccountName(rs.getString("ACCOUNT_NAME"));
				payMethodM.setDueCycle(rs.getBigDecimal("DUE_CYCLE"));
				
				payMethodM.setAccountType(rs.getString("ACCOUNT_TYPE"));
				payMethodM.setBankCode(rs.getString("BANK_CODE"));
				payMethodM.setProductType(rs.getString("PRODUCT_TYPE"));
				payMethodM.setCompleteData(rs.getString("COMPLETE_DATA"));
				
				payMethodM.setCreateBy(rs.getString("CREATE_BY"));
				payMethodM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				payMethodM.setUpdateBy(rs.getString("UPDATE_BY"));
				payMethodM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				payMethodM.setPersonalId(rs.getString("PERSONAL_ID"));
				result.add(payMethodM);
			}
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return result;
	}
	@Override
	public void saveUpdateOrigPaymentMethodM(PaymentMethodDataM paymentMethodM,Connection conn) throws ApplicationException {
		if(null != paymentMethodM){
			paymentMethodM.setUpdateBy(userId);
			int returnRows = updateTableORIG_PAYMENT_METHOD(paymentMethodM,conn);
			if (returnRows == 0) {
				paymentMethodM.setCreateBy(userId);
				createOrigPaymentMethodM(paymentMethodM,conn);
			}
		}
	}
	@Override
	public void saveUpdateOrigPaymentMethodM(PaymentMethodDataM paymentMethodM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigPaymentMethodM(paymentMethodM, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private int updateTableORIG_PAYMENT_METHOD(PaymentMethodDataM paymentMethodM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_PAYMENT_METHOD ");
			sql.append(" SET PAYMENT_METHOD = ?, PAYMENT_RATIO = ?, ACCOUNT_NO = ?, ");
			sql.append(" ACCOUNT_NAME = ?, DUE_CYCLE = ?, ACCOUNT_TYPE = ?, ");
			sql.append(" BANK_CODE = ?, PRODUCT_TYPE = ?, COMPLETE_DATA = ?, ");
			sql.append(" UPDATE_DATE = ?, UPDATE_BY = ?, PERSONAL_ID = ? ");			
			sql.append(" WHERE PAYMENT_METHOD_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, paymentMethodM.getPaymentMethod());
			ps.setBigDecimal(cnt++, paymentMethodM.getPaymentRatio());			
			ps.setString(cnt++, paymentMethodM.getAccountNo());
			
			ps.setString(cnt++, paymentMethodM.getAccountName());
			ps.setBigDecimal(cnt++, paymentMethodM.getDueCycle());
			ps.setString(cnt++, paymentMethodM.getAccountType());
			
			ps.setString(cnt++, paymentMethodM.getBankCode());
			ps.setString(cnt++, paymentMethodM.getProductType());
			ps.setString(cnt++, paymentMethodM.getCompleteData());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, paymentMethodM.getUpdateBy());
			ps.setString(cnt++, paymentMethodM.getPersonalId());
			
			// WHERE CLAUSE
			ps.setString(cnt++, paymentMethodM.getPaymentMethodId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
	@Override
	public void deleteNotInKeyOrigPaymentMethod(String personalId,String paymentMethodId)throws ApplicationException {
		Connection conn = null;
		try {
			conn = getConnection();
			deleteNotInKeyOrigPaymentMethod(personalId,paymentMethodId,conn);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public void deleteNotInKeyOrigPaymentMethod(String personalId,String paymentMethodId,Connection conn)throws ApplicationException {
		PreparedStatement ps = null;
		try {
			StringBuffer sql = new StringBuffer("");
				sql.append(" DELETE FROM ORIG_PAYMENT_METHOD ");
				sql.append(" WHERE PERSONAL_ID = ? ");
				sql.append(" AND NOT EXISTS ( ");
				sql.append("     SELECT 1 FROM ORIG_PAYMENT_METHOD innerPayment ");
				sql.append("     WHERE innerPayment.PAYMENT_METHOD_ID = ORIG_PAYMENT_METHOD.PAYMENT_METHOD_ID ");
				sql.append("	 AND innerPayment.PERSONAL_ID = ORIG_PAYMENT_METHOD.PERSONAL_ID	");
				sql.append("     AND innerPayment.PAYMENT_METHOD_ID = ? ");
				sql.append(" ) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
				ps.setString(index++,personalId);
				ps.setString(index++,paymentMethodId);
				logger.debug("personalId : "+personalId);
				logger.debug("paymentMethodId : "+paymentMethodId);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	@Override
	public void deleteInactivePaymentMethod(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
		Connection conn = null;
		try{
			conn = getConnection();
			ArrayList<String> personalIds = applicationGroup.getPersonalIds();
			logger.debug("applicationType : "+applicationGroup.getApplicationType());
			logger.debug("personalIds : "+personalIds);
			if(APPLICATION_TYPE_ADD_SUP.equals(applicationGroup.getApplicationType())){				
				if(!Util.empty(personalIds)){
					for(String personalId : personalIds){
						ArrayList<String> activePaymentMethodIds = new ArrayList<String>();
						ArrayList<PaymentMethodDataM> filterPaymentMethod = applicationGroup.getListPaymentMethodByPersonalId(personalId);
						if(!Util.empty(filterPaymentMethod)){
							for(PaymentMethodDataM paymentMethod : filterPaymentMethod){
								activePaymentMethodIds.add(paymentMethod.getPaymentMethodId());
							}
						}
						if(!Util.empty(activePaymentMethodIds)){
							deleteNotInKeyOrigPaymentMethod(personalId,activePaymentMethodIds,conn);
						}
					}
				}
			}else{
				ArrayList<String> paymentMethodIds = applicationGroup.getPaymentMethodIds();
				logger.debug("paymentMethodIds : "+paymentMethodIds);
				if(!Util.empty(personalIds)){
					for(String personalId : personalIds){
						ArrayList<String> activePaymentMethodIds = new ArrayList<String>(); 
						ArrayList<PaymentMethodDataM> filterPaymentMethod = applicationGroup.getListPaymentMethodByPersonalId(personalId);
						if(!Util.empty(filterPaymentMethod)){
							for(PaymentMethodDataM paymentMethod : filterPaymentMethod){
								if(paymentMethodIds.contains(paymentMethod.getPaymentMethodId())){
									activePaymentMethodIds.add(paymentMethod.getPaymentMethodId());
								}
							}
							if(!Util.empty(activePaymentMethodIds)){
								deleteNotInKeyOrigPaymentMethod(personalId,activePaymentMethodIds,conn);
							}
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	private void deleteNotInKeyOrigPaymentMethod(String personalId,ArrayList<String> paymentMethodIds, Connection conn)throws ApplicationException {
		PreparedStatement ps = null;
		try{
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE FROM ORIG_PAYMENT_METHOD WHERE PERSONAL_ID = ? ");
			sql.append(" AND PAYMENT_METHOD_ID NOT IN ( ");
			String comma = "";
			for(String id : paymentMethodIds){
				sql.append(comma+"?");
				comma = ",";
			}
			sql.append(" ) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
				ps.setString(index++,personalId);
				for(String id : paymentMethodIds){
					ps.setString(index++,id);
				}
				logger.debug("personalId : "+personalId);
				logger.debug("paymentMethodIds : "+paymentMethodIds);
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}
}
