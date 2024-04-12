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
import com.eaf.orig.ulo.model.app.GuidelineDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;

public class OrigORPolicyRulesDetailDAOImpl extends OrigObjectDAO implements
		OrigORPolicyRulesDetailDAO {
	public OrigORPolicyRulesDetailDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigORPolicyRulesDetailDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigORPolicyRulesDetailM(
			ORPolicyRulesDetailDataM orPolicyRulesDtlM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigORPolicyRulesDetailM(orPolicyRulesDtlM, conn);
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
	public void createOrigORPolicyRulesDetailM(
			ORPolicyRulesDetailDataM orPolicyRulesDtlM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("orPolicyRulesDtlM.getOrPolicyRulesDetailId() :"+orPolicyRulesDtlM.getOrPolicyRulesDetailId());
		    if(Util.empty(orPolicyRulesDtlM.getOrPolicyRulesDetailId())){
				String orPolicyRulesDetailId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_OR_POLICY_RULES_DTL_PK,conn); 
				orPolicyRulesDtlM.setOrPolicyRulesDetailId(orPolicyRulesDetailId);
			}
		    orPolicyRulesDtlM.setCreateBy(userId);
		    orPolicyRulesDtlM.setUpdateBy(userId);
			createTableXRULES_OR_POLICY_RULES_DETAIL(orPolicyRulesDtlM,conn);
			
			ArrayList<GuidelineDataM> guidelineList = orPolicyRulesDtlM.getGuidelines();
			if(!Util.empty(guidelineList)) {
				OrigGuidelineDAO guidelineDAO = ORIGDAOFactory.getGuidelineDAO(userId);
				for(GuidelineDataM guidelineM: guidelineList) {
					guidelineM.setOrPolicyRulesDetailId(orPolicyRulesDtlM.getOrPolicyRulesDetailId());
					guidelineDAO.createOrigGuidelineM(guidelineM,conn);
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableXRULES_OR_POLICY_RULES_DETAIL(
			ORPolicyRulesDetailDataM orPolicyRulesDtlM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_OR_POLICY_RULES_DETAIL ");
			sql.append("( OR_POLICY_RULES_DETAIL_ID, OR_POLICY_RULES_ID, RESULT, ");
			sql.append(" GUIDELINE_CODE, VERIFIED_RESULT, REASON, RANK, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, orPolicyRulesDtlM.getOrPolicyRulesDetailId());
			ps.setString(cnt++, orPolicyRulesDtlM.getOrPolicyRulesId());
			ps.setString(cnt++, orPolicyRulesDtlM.getResult());
			
			ps.setString(cnt++, orPolicyRulesDtlM.getGuidelineCode());
			ps.setString(cnt++, orPolicyRulesDtlM.getVerifiedResult());
			ps.setString(cnt++, orPolicyRulesDtlM.getReason());
			ps.setInt(cnt++, orPolicyRulesDtlM.getRank());
			
			ps.setString(cnt++, orPolicyRulesDtlM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, orPolicyRulesDtlM.getUpdateBy());
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
	public void deleteOrigORPolicyRulesDetailM(String orPolicyRulesId,String orPolicyRulesDetailId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigORPolicyRulesDetailM(orPolicyRulesId, orPolicyRulesDetailId, conn);
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
	public void deleteOrigORPolicyRulesDetailM(String orPolicyRulesId,String orPolicyRulesDetailId,Connection conn) throws ApplicationException {
		try{
			OrigGuidelineDAO guidelineDAO = ORIGDAOFactory.getGuidelineDAO();
			if(Util.empty(orPolicyRulesDetailId)) {
				ArrayList<ORPolicyRulesDetailDataM> orPolicyDtlList = selectTableXRULES_OR_POLICY_RULES_DETAIL(orPolicyRulesId,conn);
				if(!Util.empty(orPolicyDtlList)) {
					for(ORPolicyRulesDetailDataM orPolicyRuleDtlM: orPolicyDtlList) {
						guidelineDAO.deleteOrigGuidelineM(orPolicyRuleDtlM.getOrPolicyRulesDetailId(), null,conn);
					}
				}
			}else{
				guidelineDAO.deleteOrigGuidelineM(orPolicyRulesDetailId, null,conn);
			}			
			deleteTableXRULES_OR_POLICY_RULES_DETAIL(orPolicyRulesId, orPolicyRulesDetailId,conn);
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	
	private void deleteTableXRULES_OR_POLICY_RULES_DETAIL(
			String orPolicyRulesId, String orPolicyRulesDetailId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_OR_POLICY_RULES_DETAIL ");
			sql.append(" WHERE OR_POLICY_RULES_ID = ? ");
			if(!Util.empty(orPolicyRulesDetailId)) {
				sql.append(" AND OR_POLICY_RULES_DETAIL_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, orPolicyRulesId);
			if(!Util.empty(orPolicyRulesDetailId)) {
				ps.setString(2, orPolicyRulesDetailId);
			}
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
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	
	@Override
	public ArrayList<ORPolicyRulesDetailDataM> loadOrigORPolicyRulesDetailM(
			String orPolicyRulesId, Connection conn)
			throws ApplicationException {
		ArrayList<ORPolicyRulesDetailDataM> result = selectTableXRULES_OR_POLICY_RULES_DETAIL(orPolicyRulesId,conn);
		if(!Util.empty(result)) {
			OrigGuidelineDAO guidelineDAO = ORIGDAOFactory.getGuidelineDAO();
			for(ORPolicyRulesDetailDataM orPolicyRuleDtlM: result) {
				ArrayList<GuidelineDataM> guidelineList = guidelineDAO.loadOrigGuidelineM(orPolicyRuleDtlM.getOrPolicyRulesDetailId());
				if(!Util.empty(guidelineList)) {
					orPolicyRuleDtlM.setGuidelines(guidelineList);					
				}
			}
		}
		return result;
	}
	
	@Override
	public ArrayList<ORPolicyRulesDetailDataM> loadOrigORPolicyRulesDetailM(
			String orPolicyRulesId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return loadOrigORPolicyRulesDetailM(orPolicyRulesId, conn);
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

	private ArrayList<ORPolicyRulesDetailDataM> selectTableXRULES_OR_POLICY_RULES_DETAIL(
			String orPolicyRulesId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDtlList = new ArrayList<ORPolicyRulesDetailDataM>();
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OR_POLICY_RULES_DETAIL_ID, OR_POLICY_RULES_ID, RESULT, ");
			sql.append(" GUIDELINE_CODE, VERIFIED_RESULT, REASON, RANK, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_OR_POLICY_RULES_DETAIL WHERE OR_POLICY_RULES_ID = ? ");
			sql.append(" ORDER BY GUIDELINE_CODE ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, orPolicyRulesId);

			rs = ps.executeQuery();

			while(rs.next()) {
				ORPolicyRulesDetailDataM orPolicyRuleDtlM = new ORPolicyRulesDetailDataM();
				orPolicyRuleDtlM.setOrPolicyRulesDetailId(rs.getString("OR_POLICY_RULES_DETAIL_ID"));
				orPolicyRuleDtlM.setOrPolicyRulesId(rs.getString("OR_POLICY_RULES_ID"));
				orPolicyRuleDtlM.setResult(rs.getString("RESULT"));
				
				orPolicyRuleDtlM.setGuidelineCode(rs.getString("GUIDELINE_CODE"));
				orPolicyRuleDtlM.setVerifiedResult(rs.getString("VERIFIED_RESULT"));
				orPolicyRuleDtlM.setReason(rs.getString("REASON"));
				orPolicyRuleDtlM.setRank(rs.getInt("RANK"));
				
				orPolicyRuleDtlM.setCreateBy(rs.getString("CREATE_BY"));
				orPolicyRuleDtlM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				orPolicyRuleDtlM.setUpdateBy(rs.getString("UPDATE_BY"));
				orPolicyRuleDtlM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				orPolicyRulesDtlList.add(orPolicyRuleDtlM);
			}

			return orPolicyRulesDtlList;
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

	@Override
	public void saveUpdateOrigORPolicyRulesDetailM(
			ORPolicyRulesDetailDataM orPolicyRulesDtlM)	throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigORPolicyRulesDetailM(orPolicyRulesDtlM, conn);
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
	public void saveUpdateOrigORPolicyRulesDetailM(
			ORPolicyRulesDetailDataM orPolicyRulesDtlM,Connection conn)	throws ApplicationException {
		try { 
			int returnRows = 0;
			orPolicyRulesDtlM.setUpdateBy(userId);
			returnRows = updateTableXRULES_OR_POLICY_RULES_DETAIL(orPolicyRulesDtlM,conn);
			if (returnRows == 0) {
				orPolicyRulesDtlM.setCreateBy(userId);
			    orPolicyRulesDtlM.setUpdateBy(userId);
				createOrigORPolicyRulesDetailM(orPolicyRulesDtlM,conn);
			} else {
				OrigGuidelineDAO guidelineDAO = ORIGDAOFactory.getGuidelineDAO(userId);
				ArrayList<GuidelineDataM> guidelineList = orPolicyRulesDtlM.getGuidelines();
				if(!Util.empty(guidelineList)) {
					for(GuidelineDataM guidelineM: guidelineList) {
						guidelineM.setOrPolicyRulesDetailId(orPolicyRulesDtlM.getOrPolicyRulesDetailId());
						guidelineDAO.saveUpdateOrigGuidelineM(guidelineM,conn);
					}
				}
				guidelineDAO.deleteNotInKeyGuideline(guidelineList, orPolicyRulesDtlM.getOrPolicyRulesDetailId(),conn);
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_OR_POLICY_RULES_DETAIL(
			ORPolicyRulesDetailDataM orPolicyRulesDtlM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_OR_POLICY_RULES_DETAIL ");
			sql.append(" SET RESULT = ?, GUIDELINE_CODE = ?, VERIFIED_RESULT = ?, REASON = ?, ");
			sql.append(" RANK = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE OR_POLICY_RULES_ID = ? AND OR_POLICY_RULES_DETAIL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, orPolicyRulesDtlM.getResult());
			ps.setString(cnt++, orPolicyRulesDtlM.getGuidelineCode());
			ps.setString(cnt++, orPolicyRulesDtlM.getVerifiedResult());			
			ps.setString(cnt++, orPolicyRulesDtlM.getReason());
			
			ps.setInt(cnt++, orPolicyRulesDtlM.getRank());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, orPolicyRulesDtlM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, orPolicyRulesDtlM.getOrPolicyRulesId());
			ps.setString(cnt++, orPolicyRulesDtlM.getOrPolicyRulesDetailId());
			
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
	public void deleteNotInKeyORPolicyRulesDetail(
			ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDtlList,
			String orPolicyRulesId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyORPolicyRulesDetail(orPolicyRulesDtlList, orPolicyRulesId, conn);
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
	public void deleteNotInKeyORPolicyRulesDetail(
			ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDtlList,
			String orPolicyRulesId, Connection conn)
			throws ApplicationException {
		ArrayList<String> notInKeyList = selectNotInKeyTableXRULES_OR_POLICY_RULES_DETAIL(orPolicyRulesDtlList, orPolicyRulesId,conn);
		if(!Util.empty(notInKeyList)) {
			OrigGuidelineDAO guidelineDAO = ORIGDAOFactory.getGuidelineDAO();
			for(String orPolicyRuleDtlId: notInKeyList) {
				guidelineDAO.deleteOrigGuidelineM(orPolicyRuleDtlId,null,conn);
			}
		}
		deleteNotInKeyTableXRULES_OR_POLICY_RULES_DETAIL(orPolicyRulesDtlList,orPolicyRulesId,conn);
	}
	private ArrayList<String> selectNotInKeyTableXRULES_OR_POLICY_RULES_DETAIL(
			ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDtlList, String orPolicyRulesId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		ArrayList<String> notInKeyList = new ArrayList<String>();
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT OR_POLICY_RULES_DETAIL_ID ");
			sql.append(" FROM XRULES_OR_POLICY_RULES_DETAIL WHERE OR_POLICY_RULES_ID = ? ");
			if (!Util.empty(orPolicyRulesDtlList)) {
                sql.append(" AND OR_POLICY_RULES_DETAIL_ID NOT IN ( ");

                for (ORPolicyRulesDetailDataM orPolicyRulesDtlM: orPolicyRulesDtlList) {
                    sql.append(" '" + orPolicyRulesDtlM.getOrPolicyRulesDetailId() + "' , ");
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
			ps.setString(1, orPolicyRulesId);

			rs = ps.executeQuery();

			while (rs.next()) {
				String notInKeyId = rs.getString("OR_POLICY_RULES_DETAIL_ID");
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

	private void deleteNotInKeyTableXRULES_OR_POLICY_RULES_DETAIL(
			ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDtlList,
			String orPolicyRulesId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_OR_POLICY_RULES_DETAIL ");
            sql.append(" WHERE OR_POLICY_RULES_ID = ? ");
            
            if (!Util.empty(orPolicyRulesDtlList)) {
                sql.append(" AND OR_POLICY_RULES_DETAIL_ID NOT IN ( ");

                for (ORPolicyRulesDetailDataM orPolicyRuleDtlM: orPolicyRulesDtlList) {
                    sql.append(" '" + orPolicyRuleDtlM.getOrPolicyRulesDetailId()+ "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, orPolicyRulesId);
            
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
