package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.model.app.PolicyRulesConditionDataM;

public class OrigPolicyRulesConditionDAOImpl extends OrigObjectDAO implements OrigPolicyRulesConditionDAO{
	
	public OrigPolicyRulesConditionDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigPolicyRulesConditionDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigPolicyRuleCondition(PolicyRulesConditionDataM policyRulesConditionDataM)throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigPolicyRuleCondition(policyRulesConditionDataM, conn);
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
	public void createOrigPolicyRuleCondition(
			PolicyRulesConditionDataM policyRulesConditionDataM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("policyRulesConditionDataM.getPolicyRulesConditionId() :"+policyRulesConditionDataM.getPolicyRulesConditionId());
		    if(Util.empty(policyRulesConditionDataM.getPolicyRulesConditionId())){
				String policyRulesConditionId = GenerateUnique.generate(OrigConstant.SeqNames.POLICY_RULES_CONDITION_ID_PK,conn); 
				policyRulesConditionDataM.setPolicyRulesConditionId(policyRulesConditionId);
			}
		    policyRulesConditionDataM.setCreateBy(userId);
		    policyRulesConditionDataM.setUpdateBy(userId);
		    createTableXRULES_POLICY_RULES_CONDITION(policyRulesConditionDataM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableXRULES_POLICY_RULES_CONDITION(PolicyRulesConditionDataM policyRulesConditionDataM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_POLICY_RULES_CONDITION ");
			sql.append("( POLICY_RULES_CONDITION_ID, POLICY_RULES_ID, CONDITION_CODE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE ) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, policyRulesConditionDataM.getPolicyRulesConditionId());
			ps.setString(cnt++, policyRulesConditionDataM.getPolicyRulesId());
			ps.setString(cnt++, policyRulesConditionDataM.getConditionCode());
			
			ps.setString(cnt++, policyRulesConditionDataM.getCreateBy());
			ps.setDate(cnt++, policyRulesConditionDataM.getCreateDate());
			ps.setString(cnt++, policyRulesConditionDataM.getUpdateBy());
			ps.setDate(cnt++, policyRulesConditionDataM.getUpdateDate());
			
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
	public void deleteOrigPolicyRuleCondition(String policyRulesId,String policyRulesConditionId) throws ApplicationException {
		try {
			deleteTableXRULES_POLICY_RULES_CONDITION(policyRulesId, policyRulesConditionId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
		
	}

	private void deleteTableXRULES_POLICY_RULES_CONDITION(String policyRulesId,String policyRulesConditionId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_POLICY_RULES_CONDITION ");
			sql.append(" WHERE POLICY_RULES_ID = ? ");
			if(!Util.empty(policyRulesConditionId)) {
				sql.append(" AND POLICY_RULES_CONDITION_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, policyRulesId);
			if(!Util.empty(policyRulesConditionId)) {
				ps.setString(2, policyRulesConditionId);
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
	public ArrayList<PolicyRulesConditionDataM> loadOrigPolicyRuleCondition(String policyRulesId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectXRULES_POLICY_RULES_CONDITION(policyRulesId,conn);
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
	public ArrayList<PolicyRulesConditionDataM> loadOrigPolicyRuleCondition(
			String policyRulesId, Connection conn) throws ApplicationException {
		return selectXRULES_POLICY_RULES_CONDITION(policyRulesId, conn);
	}
	
	private ArrayList<PolicyRulesConditionDataM> selectXRULES_POLICY_RULES_CONDITION(String policyRulesId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PolicyRulesConditionDataM> policyRulesConditionList = new ArrayList<PolicyRulesConditionDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT POLICY_RULES_CONDITION_ID, POLICY_RULES_ID, CONDITION_CODE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE ");
			sql.append(" FROM XRULES_POLICY_RULES_CONDITION WHERE POLICY_RULES_ID = ? ");

			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, policyRulesId);

			rs = ps.executeQuery();

			while(rs.next()) {
				PolicyRulesConditionDataM policyRuleConditionM = new PolicyRulesConditionDataM();
				policyRuleConditionM.setPolicyRulesConditionId(rs.getString("POLICY_RULES_CONDITION_ID"));
				policyRuleConditionM.setPolicyRulesId(rs.getString("POLICY_RULES_ID"));
				policyRuleConditionM.setConditionCode(rs.getString("CONDITION_CODE"));
				
				policyRuleConditionM.setCreateBy(rs.getString("CREATE_BY"));
				policyRuleConditionM.setCreateDate(rs.getDate("CREATE_DATE"));
				policyRuleConditionM.setUpdateBy(rs.getString("UPDATE_BY"));
				policyRuleConditionM.setUpdateDate(rs.getDate("UPDATE_DATE"));
				
				policyRulesConditionList.add(policyRuleConditionM);
			}

			return policyRulesConditionList;
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
	public void saveUpdateOrigPolicyRuleCondition(PolicyRulesConditionDataM policyRulesConditionDataM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigPolicyRuleCondition(policyRulesConditionDataM, conn);
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
	public void saveUpdateOrigPolicyRuleCondition(PolicyRulesConditionDataM policyRulesConditionDataM,Connection 
			conn) throws ApplicationException {
		try { 
			int returnRows = 0;
			policyRulesConditionDataM.setUpdateBy(userId);
			returnRows = updateTableXRULES_POLICY_RULES_CONDITION(policyRulesConditionDataM,conn);
			if (returnRows == 0) {
				policyRulesConditionDataM.setCreateBy(userId);
				policyRulesConditionDataM.setUpdateBy(userId);
				if(Util.empty(policyRulesConditionDataM.getPolicyRulesConditionId())){
					policyRulesConditionDataM.setPolicyRulesConditionId(GenerateUnique.generate(OrigConstant.SeqNames.POLICY_RULES_CONDITION_ID_PK,conn));
				}
				createTableXRULES_POLICY_RULES_CONDITION(policyRulesConditionDataM,conn);
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}		
	}
	private int updateTableXRULES_POLICY_RULES_CONDITION(PolicyRulesConditionDataM policyRuleCondition,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_POLICY_RULES_CONDITION ");
			sql.append(" SET CONDITION_CODE = ?, CREATE_BY = ?, CREATE_DATE = ?, ");
			sql.append(" UPDATE_BY = ?, UPDATE_DATE = ? ");
			sql.append(" WHERE POLICY_RULES_ID = ? AND POLICY_RULES_CONDITION_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, policyRuleCondition.getConditionCode());
			ps.setString(cnt++, policyRuleCondition.getCreateBy());
			ps.setDate(cnt++, policyRuleCondition.getCreateDate());
			
			ps.setString(cnt++, policyRuleCondition.getUpdateBy());
			ps.setDate(cnt++, policyRuleCondition.getUpdateDate());
			
			// WHERE CLAUSE
			ps.setString(cnt++, policyRuleCondition.getPolicyRulesId());
			ps.setString(cnt++, policyRuleCondition.getPolicyRulesConditionId());
			
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
	public void deleteNotInKeyPolicyRuleCondition(ArrayList<PolicyRulesConditionDataM> policyRulesConditionList, String policyRulesId) throws ApplicationException {
		try{
			deleteNotInKeyTableXRULES_POLICY_RULES_CONDITION(policyRulesConditionList, policyRulesId);
		}catch(Exception e){
			throw new ApplicationException(e.getMessage());
		};
	}
	
	//#GET KEY ID OF POLICYRULECONDITION 
	private ArrayList<String> selectNotInKeyTableXRULES_POLICY_RULES_CONDITION(ArrayList<PolicyRulesConditionDataM> policyRulesConditionList, String policyRulesId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> notInKeyList = new ArrayList<String>();
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT POLICY_RULES_CONDITION_ID");
			sql.append(" FROM XRULES_POLICY_RULES_CONDITION WHERE POLICY_RULES_ID = ? ");
			if (!Util.empty(policyRulesConditionList)) {
                sql.append(" AND POLICY_RULES_CONDITION_ID NOT IN ( ");

                for (PolicyRulesConditionDataM policyRulesConditionM : policyRulesConditionList) {
                    sql.append(" '" + policyRulesConditionM.getPolicyRulesConditionId() + "' , ");
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
				String notInKeyId = rs.getString("POLICY_RULES_CONDITION_ID");
				notInKeyList.add(notInKeyId);
			}

			return notInKeyList;
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

	//#DELETE FIELD FROM KEY ID/PARENTS KEY 
	private void deleteNotInKeyTableXRULES_POLICY_RULES_CONDITION(ArrayList<PolicyRulesConditionDataM> policyRulesConditionList, String policyRulesId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_POLICY_RULES_CONDITION ");
            sql.append(" WHERE POLICY_RULES_ID = ? ");
            
            if (!Util.empty(policyRulesConditionList)) {
                sql.append(" AND POLICY_RULES_CONDITION_ID NOT IN ( ");
                for (PolicyRulesConditionDataM policyConditionM: policyRulesConditionList) {
                    sql.append(" '" + policyConditionM.getPolicyRulesConditionId() + "' , ");
                }
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }
                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("dSql >> "+dSql);
            
            ps = conn.prepareStatement(dSql);
            ps.setString(1, policyRulesId);
            
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
