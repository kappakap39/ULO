package com.eaf.orig.ulo.app.xrules.dao;

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
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;

public class OrigORPolicyRulesDAOImpl extends OrigObjectDAO implements
		OrigORPolicyRulesDAO {
	public OrigORPolicyRulesDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigORPolicyRulesDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigORPolicyRulesM(orPolicyRulesM, conn);
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
	@Override
	public void createOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM,
			Connection conn) throws ApplicationException {
		try {
			logger.debug("orPolicyRulesM.getOrPolicyRulesId() :"+orPolicyRulesM.getOrPolicyRulesId());
		    if(Util.empty(orPolicyRulesM.getOrPolicyRulesId())){
				String orPolicyRulesId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_OR_POLICY_RULES_PK,conn); 
				orPolicyRulesM.setOrPolicyRulesId(orPolicyRulesId);
			}
		    orPolicyRulesM.setCreateBy(userId);
		    orPolicyRulesM.setUpdateBy(userId);
			createTableXRULES_OR_POLICY_RULES(orPolicyRulesM,conn);
			
			ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDetailList = orPolicyRulesM.getOrPolicyRulesDetails();
			if(!Util.empty(orPolicyRulesDetailList)) {
				OrigORPolicyRulesDetailDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDetailDAO(userId);
				for(ORPolicyRulesDetailDataM orDetailM: orPolicyRulesDetailList) {
					orDetailM.setOrPolicyRulesId(orPolicyRulesM.getOrPolicyRulesId());
					detailDAO.createOrigORPolicyRulesDetailM(orDetailM,conn);
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableXRULES_OR_POLICY_RULES(
			ORPolicyRulesDataM orPolicyRulesM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_OR_POLICY_RULES ");
			sql.append("( OR_POLICY_RULES_ID, POLICY_RULES_ID, RESULT, ");
			sql.append(" POLICY_CODE, VERIFIED_RESULT, REASON, MIN_APPROVAL_AUTH, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, orPolicyRulesM.getOrPolicyRulesId());
			ps.setString(cnt++, orPolicyRulesM.getPolicyRulesId());
			ps.setString(cnt++, orPolicyRulesM.getResult());
			
			ps.setString(cnt++, orPolicyRulesM.getPolicyCode());
			ps.setString(cnt++, orPolicyRulesM.getVerifiedResult());
			ps.setString(cnt++, orPolicyRulesM.getReason());
			ps.setString(cnt++, orPolicyRulesM.getMinApprovalAuth());
			
			ps.setString(cnt++, orPolicyRulesM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, orPolicyRulesM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigORPolicyRulesM(String policyRulesId,String orPolicyRulesId) throws ApplicationException {
		try {
			OrigORPolicyRulesDetailDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDetailDAO();
			if(Util.empty(orPolicyRulesId)) {
				ArrayList<ORPolicyRulesDataM> orPolicyList = selectTableXRULES_OR_POLICY_RULES(policyRulesId);
				if(!Util.empty(orPolicyList)) {
					for(ORPolicyRulesDataM orPolicyRuleM: orPolicyList) {
						detailDAO.deleteOrigORPolicyRulesDetailM(orPolicyRuleM.getOrPolicyRulesId(), null);
					}
				}
			}else{
				detailDAO.deleteOrigORPolicyRulesDetailM(orPolicyRulesId, null);
			}
			deleteTableXRULES_OR_POLICY_RULES(policyRulesId, orPolicyRulesId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	private void deleteTableXRULES_OR_POLICY_RULES(String policyRulesId,
			String orPolicyRulesId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_OR_POLICY_RULES ");
			sql.append(" WHERE POLICY_RULES_ID = ? ");
			if(!Util.empty(orPolicyRulesId)) {
				sql.append(" AND OR_POLICY_RULES_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, policyRulesId);
			if(!Util.empty(orPolicyRulesId)) {
				ps.setString(2, orPolicyRulesId);
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
	public ArrayList<ORPolicyRulesDataM> loadOrigORPolicyRulesM(
			String policyRulesId, Connection conn) throws ApplicationException {
		ArrayList<ORPolicyRulesDataM> result = selectTableXRULES_OR_POLICY_RULES(policyRulesId,conn);
		if(!Util.empty(result)) {
			OrigORPolicyRulesDetailDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDetailDAO();
			for(ORPolicyRulesDataM orPolicyRuleM: result) {
				ArrayList<ORPolicyRulesDetailDataM> detailList = detailDAO.loadOrigORPolicyRulesDetailM(orPolicyRuleM.getOrPolicyRulesId(),conn);
				if(!Util.empty(detailList)) {
					orPolicyRuleM.setOrPolicyRulesDetails(detailList);					
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<ORPolicyRulesDataM> loadOrigORPolicyRulesM(
			String policyRulesId) throws ApplicationException {
		ArrayList<ORPolicyRulesDataM> result = selectTableXRULES_OR_POLICY_RULES(policyRulesId);
		if(!Util.empty(result)) {
			OrigORPolicyRulesDetailDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDetailDAO();
			for(ORPolicyRulesDataM orPolicyRuleM: result) {
				ArrayList<ORPolicyRulesDetailDataM> detailList = detailDAO.loadOrigORPolicyRulesDetailM(orPolicyRuleM.getOrPolicyRulesId());
				if(!Util.empty(detailList)) {
					orPolicyRuleM.setOrPolicyRulesDetails(detailList);					
				}
			}
		}
		return result;
	}
	private ArrayList<ORPolicyRulesDataM> selectTableXRULES_OR_POLICY_RULES(
			String policyRulesId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_OR_POLICY_RULES(policyRulesId,conn);
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
	private ArrayList<ORPolicyRulesDataM> selectTableXRULES_OR_POLICY_RULES(
			String policyRulesId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ORPolicyRulesDataM> orPolicyRulesList = new ArrayList<ORPolicyRulesDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OR_POLICY_RULES_ID, POLICY_RULES_ID, RESULT, ");
			sql.append(" POLICY_CODE, VERIFIED_RESULT, REASON, MIN_APPROVAL_AUTH, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_OR_POLICY_RULES WHERE POLICY_RULES_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyRulesId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ORPolicyRulesDataM orPolicyRuleM = new ORPolicyRulesDataM();
				orPolicyRuleM.setOrPolicyRulesId(rs.getString("OR_POLICY_RULES_ID"));
				orPolicyRuleM.setPolicyRulesId(rs.getString("POLICY_RULES_ID"));
				orPolicyRuleM.setResult(rs.getString("RESULT"));
				
				orPolicyRuleM.setPolicyCode(rs.getString("POLICY_CODE"));
				orPolicyRuleM.setVerifiedResult(rs.getString("VERIFIED_RESULT"));
				orPolicyRuleM.setReason(rs.getString("REASON"));
				orPolicyRuleM.setMinApprovalAuth(rs.getString("MIN_APPROVAL_AUTH"));
				
				orPolicyRuleM.setCreateBy(rs.getString("CREATE_BY"));
				orPolicyRuleM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				orPolicyRuleM.setUpdateBy(rs.getString("UPDATE_BY"));
				orPolicyRuleM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				orPolicyRulesList.add(orPolicyRuleM);
			}

			return orPolicyRulesList;
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
	public void saveUpdateOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigORPolicyRulesM(orPolicyRulesM, conn);
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
	@Override
	public void saveUpdateOrigORPolicyRulesM(ORPolicyRulesDataM orPolicyRulesM,
			Connection conn) throws ApplicationException {
		try { 
			int returnRows = 0;
			orPolicyRulesM.setUpdateBy(userId);
			returnRows = updateTableXRULES_OR_POLICY_RULES(orPolicyRulesM,conn);
			if (returnRows == 0) {
				orPolicyRulesM.setCreateBy(userId);
			    orPolicyRulesM.setUpdateBy(userId);
				createOrigORPolicyRulesM(orPolicyRulesM,conn);
			} else {
				OrigORPolicyRulesDetailDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDetailDAO(userId);
				ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDetailList = orPolicyRulesM.getOrPolicyRulesDetails();
				if(!Util.empty(orPolicyRulesDetailList)) {
					for(ORPolicyRulesDetailDataM orDetailM: orPolicyRulesDetailList) {
						orDetailM.setOrPolicyRulesId(orPolicyRulesM.getOrPolicyRulesId());
						detailDAO.saveUpdateOrigORPolicyRulesDetailM(orDetailM,conn);
					}
				}
				detailDAO.deleteNotInKeyORPolicyRulesDetail(orPolicyRulesDetailList, orPolicyRulesM.getOrPolicyRulesId(),conn);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_OR_POLICY_RULES(
			ORPolicyRulesDataM orPolicyRulesM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_OR_POLICY_RULES ");
			sql.append(" SET RESULT = ?, POLICY_CODE = ?, VERIFIED_RESULT = ?, ");
			sql.append(" REASON = ?, MIN_APPROVAL_AUTH=?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE POLICY_RULES_ID = ? AND OR_POLICY_RULES_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, orPolicyRulesM.getResult());
			ps.setString(cnt++, orPolicyRulesM.getPolicyCode());
			ps.setString(cnt++, orPolicyRulesM.getVerifiedResult());
			
			ps.setString(cnt++, orPolicyRulesM.getReason());
			ps.setString(cnt++, orPolicyRulesM.getMinApprovalAuth());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, orPolicyRulesM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, orPolicyRulesM.getPolicyRulesId());
			ps.setString(cnt++, orPolicyRulesM.getOrPolicyRulesId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
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
	public void deleteNotInKeyORPolicyRules(
			ArrayList<ORPolicyRulesDataM> orPolicyRulesList,
			String policyRulesId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyORPolicyRules(orPolicyRulesList, policyRulesId, conn);
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
	@Override
	public void deleteNotInKeyORPolicyRules(
			ArrayList<ORPolicyRulesDataM> orPolicyRulesList,
			String policyRulesId, Connection conn) throws ApplicationException {
		ArrayList<String> notInKeyList = selectNotInKeyTableXRULES_OR_POLICY_RULES(orPolicyRulesList, policyRulesId,conn);
		if(!Util.empty(notInKeyList)) {
			OrigORPolicyRulesDetailDAO detailDAO = ORIGDAOFactory.getORPolicyRulesDetailDAO();
			for(String orPolicyRuleId: notInKeyList) {
				detailDAO.deleteOrigORPolicyRulesDetailM(orPolicyRuleId,null,conn);
			}
		}
		deleteNotInKeyTableXRULES_OR_POLICY_RULES(orPolicyRulesList, policyRulesId,conn);
	}
	
	private ArrayList<String> selectNotInKeyTableXRULES_OR_POLICY_RULES(
			ArrayList<ORPolicyRulesDataM> orPolicyRulesList,
			String policyRulesId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		ArrayList<String> notInKeyList = new ArrayList<String>();
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OR_POLICY_RULES_ID ");
			sql.append(" FROM XRULES_OR_POLICY_RULES WHERE POLICY_RULES_ID = ? ");
			if (!Util.empty(orPolicyRulesList)) {
                sql.append(" AND OR_POLICY_RULES_ID NOT IN ( ");

                for (ORPolicyRulesDataM orPolicyRulesM: orPolicyRulesList) {
                    sql.append(" '" + orPolicyRulesM.getOrPolicyRulesId() + "' , ");
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
			ps.setString(1, policyRulesId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String notInKeyId = rs.getString("OR_POLICY_RULES_ID");
				notInKeyList.add(notInKeyId);
			}

			return notInKeyList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, rs, ps);
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	private void deleteNotInKeyTableXRULES_OR_POLICY_RULES(
			ArrayList<ORPolicyRulesDataM> orPolicyRulesList,
			String policyRulesId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_OR_POLICY_RULES ");
            sql.append(" WHERE POLICY_RULES_ID = ? ");
            
            if (!Util.empty(orPolicyRulesList)) {
                sql.append(" AND OR_POLICY_RULES_ID NOT IN ( ");

                for (ORPolicyRulesDataM orPolicyRuleM: orPolicyRulesList) {
                    sql.append(" '" + orPolicyRuleM.getOrPolicyRulesId() + "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, policyRulesId);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new ApplicationException(e.getMessage());
			}
		}
	}

}
