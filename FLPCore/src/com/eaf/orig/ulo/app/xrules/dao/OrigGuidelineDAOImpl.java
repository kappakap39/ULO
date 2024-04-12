package com.eaf.orig.ulo.app.xrules.dao;

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
import com.eaf.orig.ulo.model.app.GuidelineDataM;

public class OrigGuidelineDAOImpl extends OrigObjectDAO implements
		OrigGuidelineDAO {
	public OrigGuidelineDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigGuidelineDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigGuidelineM(GuidelineDataM guidelineM)	throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createOrigGuidelineM(guidelineM, conn);
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
	public void createOrigGuidelineM(GuidelineDataM guidelineM, Connection conn)
			throws ApplicationException {
		try {
			logger.debug("guidelineM.getGuideLineDataId() :"+guidelineM.getGuideLineDataId());
		    if(Util.empty(guidelineM.getGuideLineDataId())){
				String guidelineId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_GUIDELINE_DATA_PK,conn); 
				guidelineM.setGuideLineDataId(guidelineId);
			}
		    guidelineM.setCreateBy(userId);
		    guidelineM.setUpdateBy(userId);
			createTableXRULES_GUIDELINE_DATA(guidelineM,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void createTableXRULES_GUIDELINE_DATA(GuidelineDataM guidelineM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_GUIDELINE_DATA ");
			sql.append("( GUIDELINE_DATA_ID, OR_POLICY_RULES_DETAIL_ID, NAME, VALUE, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE )");

			sql.append(" VALUES(?,?,?,?,  ?,?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, guidelineM.getGuideLineDataId());
			ps.setString(cnt++, guidelineM.getOrPolicyRulesDetailId());
			ps.setString(cnt++, guidelineM.getName());
			ps.setString(cnt++, guidelineM.getValue());
			
			ps.setString(cnt++, guidelineM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, guidelineM.getUpdateBy());
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
	public void deleteOrigGuidelineM(String orPolicyRulesDetailId,
			String guidelineId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteOrigGuidelineM(orPolicyRulesDetailId, guidelineId, conn);
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
	public void deleteOrigGuidelineM(String orPolicyRulesDetailId,
			String guidelineId, Connection conn) throws ApplicationException {
		try {
			deleteTableXRULES_GUIDELINE_DATA(orPolicyRulesDetailId,guidelineId,conn);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private void deleteTableXRULES_GUIDELINE_DATA(String orPolicyRulesDetailId,
			String guidelineId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_GUIDELINE_DATA ");
			sql.append(" WHERE OR_POLICY_RULES_DETAIL_ID = ? ");
			if(!Util.empty(guidelineId)) {
				sql.append(" AND GUIDELINE_DATA_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, orPolicyRulesDetailId);
			if(!Util.empty(guidelineId)) {
				ps.setString(2, guidelineId);
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
	public ArrayList<GuidelineDataM> loadOrigGuidelineM(
			String orPolicyRulesDetailId) throws ApplicationException {
		ArrayList<GuidelineDataM> result = selectTableXRULES_GUIDELINE_DATA(orPolicyRulesDetailId);
		return result;
	}

	private ArrayList<GuidelineDataM> selectTableXRULES_GUIDELINE_DATA(
			String orPolicyRulesDetailId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<GuidelineDataM> guidelineList = new ArrayList<GuidelineDataM>();
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT GUIDELINE_DATA_ID, OR_POLICY_RULES_DETAIL_ID, NAME, VALUE, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM XRULES_GUIDELINE_DATA WHERE OR_POLICY_RULES_DETAIL_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, orPolicyRulesDetailId);

			rs = ps.executeQuery();

			while(rs.next()) {
				GuidelineDataM guidelineM = new GuidelineDataM();
				guidelineM.setGuideLineDataId(rs.getString("GUIDELINE_DATA_ID"));
				guidelineM.setOrPolicyRulesDetailId(rs.getString("OR_POLICY_RULES_DETAIL_ID"));
				guidelineM.setName(rs.getString("NAME"));
				guidelineM.setValue(rs.getString("VALUE"));
				
				guidelineM.setCreateBy(rs.getString("CREATE_BY"));
				guidelineM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				guidelineM.setUpdateBy(rs.getString("UPDATE_BY"));
				guidelineM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				guidelineList.add(guidelineM);
			}

			return guidelineList;
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
	public void saveUpdateOrigGuidelineM(GuidelineDataM guidelineM)
			throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			saveUpdateOrigGuidelineM(guidelineM, conn);
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
	public void saveUpdateOrigGuidelineM(GuidelineDataM guidelineM,
			Connection conn) throws ApplicationException {
		try { 
			int returnRows = 0;
			guidelineM.setUpdateBy(userId);
			returnRows = updateTableXRULES_GUIDELINE_DATA(guidelineM,conn);
			if (returnRows == 0) {
				guidelineM.setCreateBy(userId);
			    guidelineM.setUpdateBy(userId);
				createOrigGuidelineM(guidelineM,conn);
			} 
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	private int updateTableXRULES_GUIDELINE_DATA(GuidelineDataM guidelineM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_GUIDELINE_DATA ");
			sql.append(" SET NAME = ?, VALUE = ?, UPDATE_DATE = ?, UPDATE_BY = ? ");
			sql.append(" WHERE OR_POLICY_RULES_DETAIL_ID = ? AND GUIDELINE_DATA_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, guidelineM.getName());
			ps.setString(cnt++, guidelineM.getValue());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, guidelineM.getUpdateBy());
			
			// WHERE CLAUSE
			ps.setString(cnt++, guidelineM.getOrPolicyRulesDetailId());
			ps.setString(cnt++, guidelineM.getGuideLineDataId());
			
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
	public void deleteNotInKeyGuideline(ArrayList<GuidelineDataM> guidelineList,
			String orPolicyRulesDetailId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			deleteNotInKeyGuideline(guidelineList, orPolicyRulesDetailId, conn);
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
	public void deleteNotInKeyGuideline(
			ArrayList<GuidelineDataM> guidelineList,
			String orPolicyRulesDetailId, Connection conn)
			throws ApplicationException {
		deleteNotInKeyTableXRULES_GUIDELINE_DATA(guidelineList, orPolicyRulesDetailId, conn);
	}
	private void deleteNotInKeyTableXRULES_GUIDELINE_DATA(ArrayList<GuidelineDataM> guidelineList,
			String orPolicyRulesDetailId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
        try {
            // conn = Get Connection
//            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM XRULES_GUIDELINE_DATA ");
            sql.append(" WHERE OR_POLICY_RULES_DETAIL_ID = ? ");
            
            if (!Util.empty(guidelineList)) {
                sql.append(" AND GUIDELINE_DATA_ID NOT IN ( ");

                for (GuidelineDataM guidelineM: guidelineList) {
                    sql.append(" '" + guidelineM.getGuideLineDataId()+ "' , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            ps = conn.prepareStatement(dSql);
            ps.setString(1, orPolicyRulesDetailId);
            
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
