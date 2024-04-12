package com.eaf.orig.ulo.app.view.util.uloOrigApp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;

public class UloOrigAppDAOImpl extends OrigObjectDAO implements UloOrigAppDAO{
	private static transient Logger logger = Logger.getLogger(UloOrigAppDAOImpl.class);
	private String CC_CST_NO_SUP = SystemConstant.getConstant("CC_CST_NO_SUP");
	@Override
	public String getActionApp(String applicationGroupId,String jobState) throws ApplicationException {
		logger.debug("applicationGroupId : "+applicationGroupId);
		logger.debug("jobState : "+jobState);
		String action = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuilder SQL = new StringBuilder();
				SQL.append(" SELECT A.* FROM ORIG_APPLICATION_LOG A ");
				SQL.append(" WHERE A.APPLICATION_GROUP_ID = ? ");// --Customer Current Record 
				SQL.append(" AND A.JOB_STATE = ? ");
				SQL.append(" AND A.APP_LOG_ID = (SELECT MAX(APP_LOG_ID) FROM ORIG_APPLICATION_LOG WHERE APPLICATION_GROUP_ID =? AND JOB_STATE = ? ) ");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,jobState);	
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,jobState);		 	
			rs = ps.executeQuery();
			if(rs.next()){
				action = rs.getString("ACTION");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		logger.debug("action : "+action);	
		return action;
	}
	@Override
	public String getApplicationStatus(String applicationGroupId,String jobState) throws ApplicationException {
		logger.debug("applicationGroupId : "+applicationGroupId);
		logger.debug("jobState : "+jobState);
		String applicationStatus = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuilder SQL = new StringBuilder();
				SQL.append(" SELECT APPLICATION_STATUS FROM ORIG_APPLICATION_LOG ");
				SQL.append(" WHERE APPLICATION_GROUP_ID = ? ");// --Customer Current Record 
				SQL.append(" AND JOB_STATE = ? ");
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,jobState);	
			rs = ps.executeQuery();
			if (rs.next()) {
				applicationStatus	= rs.getString("APPLICATION_STATUS");
			}
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
		logger.debug("applicationStatus : "+applicationStatus);	
		return applicationStatus;
	}
	@Override
	public HashMap<String, String> getCountSupCardInfoByPersonalId(String personalId) throws ApplicationException {
		logger.debug("getCountSupCardInfoByPersonalId...");
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap<String, String> hCountSupCard = new HashMap<String, String>();
		try {
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuilder SQL = new StringBuilder();
			SQL.append("SELECT C_CARD ,MIN(CARD_GROUP) AS CARD_GROUP ");  
			SQL.append(" FROM  ( ");  
			SQL.append("   SELECT COUNT (1) AS C_CARD,CARD.COA_PRODUCT_CODE ");  
			SQL.append("   FROM ORIG_CARDLINK_CARD CARD ");  
			SQL.append("   INNER JOIN ORIG_CARDLINK_CUSTOMER  CARD_CUST ON  CARD.CARDLINK_CUST_NO = CARD_CUST.CARDLINK_CUST_NO ");
			SQL.append("   AND CARD.PERSONAL_ID = CARD_CUST.PERSONAL_ID ");
			SQL.append("   AND CARD.SUP_CUST_NO <> ? ");
			SQL.append("   WHERE  CARD.SUP_CUST_NO is not null AND   CARD_CUST.PERSONAL_ID =? ");  
			SQL.append("   GROUP BY CARD.COA_PRODUCT_CODE) C_LINK  ");  
			SQL.append(" INNER JOIN CARD_TYPE C  ON C_LINK.COA_PRODUCT_CODE = C.MAPPING1 ");  
			SQL.append(" GROUP BY  C_CARD ,CARD_GROUP ");  
			
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1,CC_CST_NO_SUP);
			ps.setString(2,personalId);
			rs = ps.executeQuery();
			while (rs.next()) {
				String C_CARD	= rs.getString("C_CARD");
				String CARD_GROUP	= rs.getString("CARD_GROUP");
				hCountSupCard.put(CARD_GROUP, C_CARD);
			}
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
		return hCountSupCard;
	}

}
