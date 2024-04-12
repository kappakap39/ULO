/*
 * Created on Nov 28, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.UserTeamM;
import com.eaf.orig.master.shared.model.UserTeamMemberM;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * Type: OrigMasterUserTeamDAOImpl
 */
public class OrigMasterUserTeamDAOImpl extends OrigObjectDAO implements
		OrigMasterUserTeamDAO {
	Logger log = Logger.getLogger(OrigMasterUserTeamDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterUserTeamDAO#deleteOrigMasterUserTeam(java.lang.String[])
	 */
	public void deleteOrigMasterUserTeam(String[] userTeamIdToDelete)
			throws OrigApplicationMException {
		if(userTeamIdToDelete!=null && userTeamIdToDelete.length>0){
			for(int i=0;i<userTeamIdToDelete.length;i++){
				deleteUsrTeamMember(userTeamIdToDelete[i]);
				deleteUsrTeam(userTeamIdToDelete[i]);
			}
		}

	}
	
	public void deleteUsrTeam(String teamID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE TEAM ");
				sql.append(" WHERE TEAM_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, teamID);
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
}
	
	public void deleteUsrTeamMember(String teamID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE TEAM_MEMBER ");
				sql.append(" WHERE TEAM_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, teamID);
				ps.executeUpdate(); 
		
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterUserTeamDAO#updateOrigMasterUserTeamM(com.eaf.orig.master.shared.model.UserTeamM)
	 */
	public double updateOrigMasterUserTeamM(UserTeamM userTeamM)
			throws OrigApplicationMException {
		double chkReturnRows = 0;
		
		updateUserTeam(userTeamM);
		
		Vector memberVect = userTeamM.getTeamMemberVect();
		if(memberVect!=null && memberVect.size()>0){
			UserTeamMemberM memberM;
			for(int i=0;i<memberVect.size();i++){
				memberM = (UserTeamMemberM)memberVect.get(i);
				chkReturnRows = updateUserTeamMember(memberM,userTeamM);
				if(chkReturnRows == 0){
					insertUserTeamMember(memberM,userTeamM);
				}
			}
			deleteNotInKeyUserTeamMember(userTeamM);
		}else{
			deleteUserTeamMember(userTeamM.getTeamId());
		}
		
		
		return 0;
	}
	
	public void deleteUserTeamMember(String teamID)
			throws OrigApplicationMException{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE TEAM_MEMBER ");
				sql.append(" WHERE TEAM_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, teamID);
				ps.executeUpdate(); 
				
		} catch (Exception e) {
			log.fatal(e.getStackTrace());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getStackTrace());
				throw new OrigApplicationMException(e.getMessage());
			}
		}
}
	
	public void deleteNotInKeyUserTeamMember(UserTeamM userTeamM)
			throws OrigApplicationMException {
		
		Vector memberVect = new Vector();
		memberVect = userTeamM.getTeamMemberVect();
		
		PreparedStatement ps = null;
		String cmpCode = null;
		Connection conn = null;
		try {
		    // conn = Get Connection
		    conn = getConnection();
		
		    StringBuffer sql = new StringBuffer("");
		    sql.append("DELETE FROM TEAM_MEMBER");
		    sql.append(" WHERE TEAM_ID = ? ");
		    
		    if ((memberVect != null) && (memberVect.size() > 0)) {
		        sql.append(" AND MEMBER_ID NOT IN ( ");
		        UserTeamMemberM memberM;
		        for (int i = 0; i < memberVect.size(); i++) {
		        	memberM = (UserTeamMemberM) memberVect.get(i);
		            sql.append(" '" + memberM.getMemberID() + "' , ");
		        }
		        
		        if (sql.toString().trim().endsWith(",")) {
		            sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
		        }
		
		        sql.append(" ) ");
		    }
		
		    String dSql = String.valueOf(sql);
		    log.debug("dSql="+dSql);
		    ps = conn.prepareStatement(dSql);
		
		    ps.setString(1, userTeamM.getTeamId());
		    
		    ps.executeUpdate();
		    ps.close();
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
				throw new OrigApplicationMException(e.getMessage());
			}
		}
}
	
	public void insertUserTeamMember(UserTeamMemberM memberM, UserTeamM userTeamM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" INSERT INTO TEAM_MEMBER ");
			sql.append(" ( TEAM_ID, MEMBER_ID, LEADER_FLAG, DESCRIPTION, CREATE_BY, UPDATE_BY, UPDATE_DATE, CREATE_DATE ) ");
			sql.append(" VALUES (?,?,?,?,?,?,SYSDATE,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, userTeamM.getTeamId());
			ps.setString(2, memberM.getMemberID());
			ps.setString(3, memberM.getLeaderFlag());
			ps.setString(4, memberM.getDescription());
			ps.setString(5, userTeamM.getCreateBy());
			ps.setString(6, userTeamM.getUpdateBy());
		
			ps.executeUpdate();
			
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public double updateUserTeamMember(UserTeamMemberM memberM, UserTeamM userTeamM)
			throws OrigApplicationMException {
		//ORIGUtility utility = new ORIGUtility();
		double returnRows = 0;
		double chkReturnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE TEAM_MEMBER ");
			sql.append(" SET TEAM_ID=?, MEMBER_ID=?, LEADER_FLAG = ?, DESCRIPTION=?, UPDATE_BY = ?, UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE TEAM_ID = ? AND MEMBER_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userTeamM.getTeamId());
			ps.setString(2, memberM.getMemberID());	
			ps.setString(3, memberM.getLeaderFlag());
			ps.setString(4, memberM.getDescription());
			ps.setString(5, userTeamM.getUpdateBy());
			ps.setString(6, userTeamM.getTeamId());
			ps.setString(7, memberM.getMemberID());
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;
}
	
	public double updateUserTeam(UserTeamM userTeamM)
			throws OrigApplicationMException {
		//ORIGUtility utility = new ORIGUtility();
		double returnRows = 0;
		double chkReturnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE TEAM ");
			sql.append(" SET TEAM_ID=? , TEAM_NAME=? , TEAM_DESC = ? ,UPDATE_BY = ?, UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE TEAM_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userTeamM.getTeamId());
			ps.setString(2, userTeamM.getTeamName());	
			ps.setString(3, userTeamM.getTeamDesc());
			ps.setString(4, userTeamM.getUpdateBy());
			ps.setString(5, userTeamM.getTeamId());
			
			returnRows = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		return returnRows;
}
	
	public UserTeamM selectOrigMasterUserTeamM(String teamEditID)
			throws OrigApplicationMException {
		Vector userTeamMemVect = selectUserTeamMember(teamEditID);
		
		UserTeamM userTeamM = selectUserTeam(teamEditID);
		userTeamM.setTeamMemberVect(userTeamMemVect);
		
		return userTeamM;
	}
	
	public Vector selectUserTeamMember(String teamEditID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT TEAM_ID, MEMBER_ID, LEADER_FLAG, DESCRIPTION ");
			sql.append(" FROM TEAM_MEMBER  WHERE TEAM_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, teamEditID);
		
			rs = ps.executeQuery();
			UserTeamMemberM memberM = null;
			Vector memberVect = new Vector();
			while (rs.next()) {
				memberM = new UserTeamMemberM();
				memberM.setTeamId(rs.getString(1));
				memberM.setMemberID(rs.getString(2));
				memberM.setLeaderFlag(rs.getString(3));
				memberM.setDescription(rs.getString(4));
				
				memberVect.add(memberM);
				
			}
			return memberVect;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public UserTeamM selectUserTeam(String teamEditID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT TEAM_ID, TEAM_NAME, TEAM_DESC ");
			sql.append(" FROM TEAM  WHERE TEAM_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, teamEditID);
		
			rs = ps.executeQuery();
			UserTeamM userTeamM = null;
		
			if (rs.next()) {
				userTeamM = new UserTeamM();
				userTeamM.setTeamId(rs.getString(1));
				userTeamM.setTeamName(rs.getString(2));
				userTeamM.setTeamDesc(rs.getString(3));
				
			}
			return userTeamM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public void createModelOrigMasterUserTeamM(UserTeamM userTeamM)
			throws OrigApplicationMException {
		
		try {
			//String teamID = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.TEAM_ID);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			String teamID = generatorManager.generateUniqueIDByName(EJBConstant.TEAM_ID);
			teamID = EjbUtil.appendZero(teamID, 3);
			userTeamM.setTeamId(teamID);
			saveUserTeamM(userTeamM);
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		}
			
			Vector selTeamMemVect = userTeamM.getTeamMemberVect();
			if(selTeamMemVect!=null && selTeamMemVect.size()>0){
				UserTeamMemberM memberM; 
				for(int i=0;i<selTeamMemVect.size();i++){
					memberM = (UserTeamMemberM)selTeamMemVect.get(i);
					saveUserTeamMember(memberM,userTeamM);
				}
			}

	}
	
	public void saveUserTeamMember(UserTeamMemberM memberM,UserTeamM userTeamM)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO TEAM_MEMBER ");
			sql.append(" (TEAM_ID, MEMBER_ID, LEADER_FLAG, DESCRIPTION, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,?,?,SYSDATE,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, userTeamM.getTeamId());
			ps.setString(2, memberM.getMemberID());
			ps.setString(3, memberM.getLeaderFlag());
			ps.setString(4, memberM.getDescription());
			ps.setString(5, userTeamM.getCreateBy());
			ps.setString(6, userTeamM.getUpdateBy());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public void saveUserTeamM(UserTeamM userTeamM)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO TEAM ");
			sql.append(" (TEAM_ID, TEAM_NAME, TEAM_DESC, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE) ");
			sql.append(" VALUES (?,?,?,?,?,SYSDATE,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, userTeamM.getTeamId());
			ps.setString(2, userTeamM.getTeamName());
			ps.setString(3, userTeamM.getTeamDesc());
			ps.setString(4, userTeamM.getCreateBy());
			ps.setString(5, userTeamM.getUpdateBy());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}

	
	public Vector getAllUserNameCMR() throws OrigApplicationMException {
		Vector userNameVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT DISTINCT U.USER_NAME ");
			sql.append("    FROM US_USER_DETAIL U, USER_ROLE UR, ROLE R ");
			sql.append("   WHERE UPPER (U.USER_NAME) = UPPER (UR.USER_NAME) ");
			sql.append("   	AND UR.ROLE_ID  = R.ROLE_ID ");
			sql.append("     AND R.ROLE_NAME = 'CMR' ");
			sql.append(" ORDER BY UPPER (USER_NAME) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			
			while(rs.next()) {
				userNameVect.add(rs.getString(1));
			}
			return userNameVect;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	public Vector getAllUserName() throws OrigApplicationMException {
		Vector userNameVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT U.USER_NAME  FROM US_USER_DETAIL U");
			sql.append(" ORDER BY UPPER(USER_NAME) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			
			while(rs.next()) {
				userNameVect.add(rs.getString(1));
			}
			return userNameVect;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}

}
