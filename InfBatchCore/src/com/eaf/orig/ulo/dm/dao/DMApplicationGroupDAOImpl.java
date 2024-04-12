package com.eaf.orig.ulo.dm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.dm.dao.exception.DMException;
import com.eaf.orig.ulo.model.dm.DMApplicationInfoDataM;
import com.eaf.orig.ulo.model.dm.DMCardInfoDataM;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class DMApplicationGroupDAOImpl extends OrigObjectDAO implements DMApplicationGroupDAO{
	private static transient Logger logger = Logger.getLogger(DMApplicationGroupDAOImpl.class);
	@Override
	public void setApplicationInfo(DocumentManagementDataM dmManageDataM) throws DMException {
		try {
			
			String appGroupID  = dmManageDataM.getRefNo1();
			if(Util.empty(appGroupID)){
				HashMap hApPGroup  = this.getApplicationGroupId(dmManageDataM.getRefNo2());
				appGroupID =(String)hApPGroup.get("APPLICATION_GROUP_ID");
				dmManageDataM.setRefNo1(appGroupID);
			}			
			
			ArrayList<DMApplicationInfoDataM> dmAppInfoList  = this.selectTableOrigApplication(appGroupID);			
			if(!Util.empty(dmAppInfoList)){
				for(DMApplicationInfoDataM dmAppInfo :dmAppInfoList){
					ArrayList<DMCardInfoDataM>  dmCardInfoList  =  this.selectDMCardInfoDataM(dmAppInfo.getApplicationRecordId());
					dmAppInfo.setCardInfos(dmCardInfoList);
				}
			}
			
			dmManageDataM.setApplicationInfos(dmAppInfoList);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}		 
	}
	
	private  ArrayList<DMApplicationInfoDataM> selectTableOrigApplication(String appGroupId)  throws DMException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<DMApplicationInfoDataM> appGroupMList = new ArrayList<DMApplicationInfoDataM>();
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT  APPLICATION_RECORD_ID,BUSINESS_CLASS_ID,APPLICATION_TYPE,FINAL_APP_DECISION");
			sql.append(" FROM ORIG_APPLICATION APP  ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?  AND APP.LIFE_CYCLE = (SELECT MAX(LIFE_CYCLE) FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID=?)");			
			sql.append(" ORDER BY BUSINESS_CLASS_ID ");			
						
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("appGroupId=" + appGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appGroupId);
			ps.setString(2, appGroupId);
			rs = ps.executeQuery();					
			while (rs.next()) {			
				DMApplicationInfoDataM  dmApp  = new DMApplicationInfoDataM();
				dmApp.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				dmApp.setBusinessClassId(rs.getString("BUSINESS_CLASS_ID"));
				dmApp.setApplicationType(rs.getString("APPLICATION_TYPE"));
				dmApp.setFinalAppDecision(rs.getString("FINAL_APP_DECISION"));	
				appGroupMList.add(dmApp);
			}						
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
		return appGroupMList;
		
	}
	private  ArrayList<DMCardInfoDataM> selectDMCardInfoDataM(String applicationRecordId)  throws DMException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<DMCardInfoDataM> cardInfoList = new ArrayList<DMCardInfoDataM>();
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT DISTINCT ORIG_CARD.CARD_TYPE,P_INFO.PERSONAL_TYPE,P_INFO.TH_FIRST_NAME ||' '|| P_INFO.TH_LAST_NAME  AS TH_FULLNAME,");
			sql.append(" P_INFO.IDNO ");
			sql.append(" FROM  ORIG_APPLICATION APP");
			sql.append(" INNER JOIN ORIG_LOAN ON  ORIG_LOAN.APPLICATION_RECORD_ID = APP.APPLICATION_RECORD_ID");
			sql.append(" LEFT  JOIN ORIG_CARD ON  ORIG_LOAN.LOAN_ID  =   ORIG_CARD.LOAN_ID");
			sql.append(" LEFT JOIN ORIG_PERSONAL_RELATION PR ON APP.APPLICATION_RECORD_ID = PR.REF_ID AND PR.RELATION_LEVEL='"+SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL")+"'");
			sql.append(" LEFT JOIN ORIG_PERSONAL_INFO  P_INFO ON  PR.PERSONAL_ID  =P_INFO.PERSONAL_ID");
			sql.append(" WHERE APP.APPLICATION_RECORD_ID =? ");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql); 
			logger.debug("applicationRecordId=" + applicationRecordId); 
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);
			rs = ps.executeQuery();					
			while (rs.next()) {			
				DMCardInfoDataM  cardInfo  = new DMCardInfoDataM();
				cardInfo.setCardType(rs.getString("CARD_TYPE"));
				cardInfo.setPersonalType(rs.getString("PERSONAL_TYPE"));
				cardInfo.setThFullName(rs.getString("TH_FULLNAME"));
				cardInfo.setIdNo(rs.getString("IDNO"));
				cardInfoList.add(cardInfo);
			}						
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
		return cardInfoList;
		
	}

 
	private HashMap  getApplicationGroupId(String appGroupNo) throws DMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap  hAppGroup  = new HashMap();
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP  ");
			sql.append("WHERE APPLICATION_GROUP_NO = ? ");			
						
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql); 
			logger.debug("appGroupNo=" + appGroupNo); 
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, appGroupNo);
			rs = ps.executeQuery();					
			if (rs.next()) {		
				hAppGroup.put("APPLICATION_GROUP_ID",rs.getString("APPLICATION_GROUP_ID"));
			}						
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new DMException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new DMException(e.getLocalizedMessage());
			}
		}
		return hAppGroup;
		
	}

	@Override
	public ArrayList<DMApplicationInfoDataM> getProduct(String applicationGroupID) throws DMException {
		return null;
	}
}
