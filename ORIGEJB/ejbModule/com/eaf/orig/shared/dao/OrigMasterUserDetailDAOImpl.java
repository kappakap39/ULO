/*
 * Created on Nov 16, 2007
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

import com.eaf.ias.shared.model.UserM;
import com.eaf.orig.master.shared.model.UserBranchM;
import com.eaf.orig.master.shared.model.UserExceptActM;
import com.eaf.orig.master.shared.model.UserNameM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;


public class OrigMasterUserDetailDAOImpl extends OrigObjectDAO implements OrigMasterUserDetailDAO {
	Logger log = Logger.getLogger(OrigMasterUserDetailDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterUserDetailDAO#SearchAllGroupname()
	 */
	public Vector SearchAllGroupname() throws OrigApplicationMException {
		Vector groupNameVect = new Vector();
		
		/*approvAuthorVect = searchAllApprovAuthor();
		
		if(approvAuthorVect!=null && approvAuthorVect.size()>0){
			UserNameM userNameM; 
			for(int i=0;i<approvAuthorVect.size();i++){
				userNameM = (UserNameM)approvAuthorVect.get(i);
				userNameM = getloanType(userNameM);
				userNameM = getCusType(userNameM);
				approvAuthorVect.setElementAt(userNameM,i);
			}
		}*/
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT DISTINCT GROUP_NAME  ");
			sql.append(" FROM APPROVAL_AUTHORITY  ORDER BY GROUP_NAME");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
		
			rs = ps.executeQuery();
			UserNameM userNameM;
			while(rs.next()) {
				userNameM  = new UserNameM(); 
				userNameM.setGroupName(rs.getString(1));
				//userNameM.setLoanType(rs.getString(2));
				//userNameM.setCusType(rs.getString(3));
				groupNameVect.add(userNameM);
			}
			return groupNameVect;
		
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
		
		//return groupNameVect;
	}
	
	public Vector searchAllApprovAuthor()
			throws OrigApplicationMException {
		Vector approvAuthorVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT GROUP_NAME, LOAN_TYPE, CUSTOMER_TYPE ");
			sql.append(" FROM APPROVAL_AUTHORITY ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
		
			rs = ps.executeQuery();
			UserNameM userNameM;
			while(rs.next()) {
				userNameM  = new UserNameM(); 
				userNameM.setGroupName(rs.getString(1));
				userNameM.setLoanType(rs.getString(2));
				userNameM.setCusType(rs.getString(3));
				approvAuthorVect.add(userNameM);
			}
			return approvAuthorVect;
		
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
	
	public Vector SearchAllBranch() throws OrigApplicationMException {
		Vector branchSearchVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CMPCDE, AREA, THDESC ");
			sql.append(" FROM HPTBHP15 ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			
			while(rs.next()) {
				UserBranchM userBranchM = new UserBranchM(); 
				userBranchM.setCmpCde(rs.getString(1));
				userBranchM.setArea(rs.getString(2));
				userBranchM.setBranchDesc(rs.getString(3));
				branchSearchVect.add(userBranchM);
			}
			return branchSearchVect;

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
	
	public Vector SearchAllExcept() throws OrigApplicationMException {
		Vector exceptSearchVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT EXCEPTION_ID, EXCEPTION_DESC ");
			sql.append(" FROM EXCEPTION ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;

			rs = ps.executeQuery();
			UserExceptActM exceptActM;
			while(rs.next()) {
				exceptActM = new UserExceptActM(); 
				exceptActM.setExceptID(rs.getString(1));
				exceptActM.setExceptDesc(rs.getString(2));
				exceptSearchVect.add(exceptActM);
			}
			return exceptSearchVect;

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
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterUserDetailDAO#SearchUsernameByDesc(java.lang.String)
	 */
	public Vector SearchGroupnameByDesc(String groupName)
			throws OrigApplicationMException {
	    Vector groupNameVect=new Vector();
		/*Vector approvAuthorVect = new Vector();
		
		approvAuthorVect = searchApprovAuthorByGroupName(groupName);
		
		if(approvAuthorVect!=null && approvAuthorVect.size()>0){
			UserNameM userNameM; 
			for(int i=0;i<approvAuthorVect.size();i++){
				userNameM = (UserNameM)approvAuthorVect.get(i);
				userNameM = getloanType(userNameM);
				userNameM = getCusType(userNameM);
				approvAuthorVect.setElementAt(userNameM,i);
			}
		}*/
	    PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT DISTINCT GROUP_NAME  ");
			sql.append(" FROM APPROVAL_AUTHORITY ");
			sql.append(" WHERE GROUP_NAME=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1,groupName);
			rs = null;
		
			rs = ps.executeQuery();
			UserNameM userNameM;
			while(rs.next()) {
				userNameM  = new UserNameM(); 
				userNameM.setGroupName(rs.getString(1));
				//userNameM.setLoanType(rs.getString(2));
				//userNameM.setCusType(rs.getString(3));
				groupNameVect.add(userNameM);
			}
			return groupNameVect;
		
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
		
		
		
		//return approvAuthorVect;
	}
	
	public UserNameM getCusType(UserNameM userNameM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CUSTYP, THDESC ");
			sql.append(" FROM HPTBHP01  WHERE CUSTYP = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userNameM.getCusType());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(userNameM.getCusType())){
					userNameM.setCusDesc(rs.getString(2));
				}
			}
			return userNameM;
		
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
	
	public UserNameM getloanType(UserNameM userNameM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CHOICE_NO, DISPLAY_NAME ");
			sql.append(" FROM LIST_BOX_MASTER  WHERE FIELD_ID = 9 AND CHOICE_NO = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userNameM.getLoanType());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(userNameM.getLoanType())){
					userNameM.setLoanDesc(rs.getString(2));
				}
			}
			return userNameM;
		
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
	
	public Vector searchApprovAuthorByGroupName(String groupName)
			throws OrigApplicationMException {
		Vector approvAuthorVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT GROUP_NAME, LOAN_TYPE, CUSTOMER_TYPE ");
			sql.append(" FROM APPROVAL_AUTHORITY  WHERE GROUP_NAME like ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("groupName=" + groupName);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, groupName+"%");
		
			rs = ps.executeQuery();
			UserNameM userNameM;
			while(rs.next()) {
				userNameM  = new UserNameM(); 
				userNameM.setGroupName(rs.getString(1));
				userNameM.setLoanType(rs.getString(2));
				userNameM.setCusType(rs.getString(3));
				approvAuthorVect.add(userNameM);
			}
			return approvAuthorVect;
		
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
	
	public Vector SearchExceptByDesc(String exceptDesc)
			throws OrigApplicationMException {
		Vector exceptSearchVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT EXCEPTION_ID, EXCEPTION_DESC ");
			sql.append(" FROM EXCEPTION  WHERE EXCEPTION_DESC like ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("exceptDesc=" + exceptDesc);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, exceptDesc+"%");

			rs = ps.executeQuery();
			UserExceptActM exceptActM;
			while(rs.next()) {
				exceptActM = new UserExceptActM(); 
				exceptActM.setExceptID(rs.getString(1));
				exceptActM.setExceptDesc(rs.getString(2));
				exceptSearchVect.add(exceptActM);
			}
			return exceptSearchVect;

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
	
	public Vector SearchBranchByDesc(String branchDesc)
			throws OrigApplicationMException {
		Vector branchSearchVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CMPCDE, AREA, THDESC ");
			sql.append(" FROM HPTBHP15  WHERE THDESC like ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("branchDesc=" + branchDesc);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, branchDesc+"%");

			rs = ps.executeQuery();
			
			while(rs.next()) {
				UserBranchM userBranchM = new UserBranchM(); 
				userBranchM.setCmpCde(rs.getString(1));
				userBranchM.setArea(rs.getString(2));
				userBranchM.setBranchDesc(rs.getString(3));
				branchSearchVect.add(userBranchM);
			}
			return branchSearchVect;

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
	
	public boolean hvUsername(String userName)
			throws OrigApplicationMException{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT *");
			sql.append(" FROM US_USER_DETAIL  WHERE USER_NAME = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userName);

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}else{
				return false;
			}

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
	
	public void deleteOrigMasterUserDetailM(String[] userDetailToDelete)
			throws OrigApplicationMException{
		for(int i = 0; i < userDetailToDelete.length; i++){
			deleteUserApprov(userDetailToDelete[i]);
			deleteUserBranch(userDetailToDelete[i]);
			deleteUserExcept(userDetailToDelete[i]);
			deleteTeamMember(userDetailToDelete[i]);
//			deleteTeam(userDetailToDelete[i]);
			deleteUserDetail(userDetailToDelete[i]);
		}
	}
	
//	public void deleteTeam(String userID)
//			throws OrigApplicationMException {
//		
//		PreparedStatement ps = null;
//		try {
//				//conn = Get Connection
//				conn = getConnection();
//				StringBuffer sql = new StringBuffer("");
//				sql.append("DELETE TEAM ");
//				sql.append(" WHERE TEAM_NAME = ?");
//				String dSql = String.valueOf(sql);
//				log.debug("Sql=" + dSql);
//				ps = conn.prepareStatement(dSql);
//				ps.setString(1, userID);
//				ps.executeUpdate(); 
//		
//		} catch (Exception e) {
//			log.fatal(e.getStackTrace());
//			throw new OrigApplicationMException(e.getMessage());
//		} finally {
//			try {
//				closeConnection(conn, ps);
//			} catch (Exception e) {
//				log.fatal(e.getStackTrace());
//				throw new OrigApplicationMException(e.getMessage());
//			}
//		}
//
//}
	
	public void deleteTeamMember(String userID)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE TEAM_MEMBER ");
				sql.append(" WHERE MEMBER_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, userID);
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
	
	public void deleteUserExcept(String userID)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE USER_EXCEPTION ");
				sql.append(" WHERE USER_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, userID);
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
	
	public void deleteUserBranch(String userID)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE USER_BRANCH ");
				sql.append(" WHERE USER_ID = ? AND CMPCDE = ? ");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, userID);
				ps.setString(2, OrigConstant.ORIG_CMPCODE);
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
	
	public void deleteUserDetail(String userDetail)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE US_USER_DETAIL ");
				sql.append(" WHERE USER_NAME = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, userDetail);
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
	
	public double updateOrigMasterUserDetailM(UserDetailM userDetailM)
	throws OrigApplicationMException {
		double chkReturnRows = 0;
		
		updateUserDetailM(userDetailM);
		
		Vector userBranchVect = userDetailM.getUserBranchVect();
		if(userBranchVect!=null && userBranchVect.size()>0){
			log.debug("userBranchVect.size()=" + userBranchVect.size());
			UserBranchM userBranchM;
			for(int i=0;i<userBranchVect.size();i++){
				userBranchM = (UserBranchM)userBranchVect.get(i);
				chkReturnRows = updateUserBranch(userDetailM, userBranchM);
				if(chkReturnRows==0){
					saveUserBranch(userBranchM, userDetailM);
				}
			}
			deleteNotInKeyUserBranch(userDetailM, userBranchVect);
		}else{
			deleteUserBranch(userDetailM.getUserName());
		}
		
		Vector userApprovVect = userDetailM.getUserProfileVect();
		if(userApprovVect!=null && userApprovVect.size()>0){
			log.debug("userApprovVect.size()=" + userApprovVect.size());
			deleteUserApprov(userDetailM.getUserName());
			UserNameM userNameM;
			for(int i=0;i<userApprovVect.size();i++){
				userNameM = (UserNameM)userApprovVect.get(i);
				saveUserApprov(userNameM, userDetailM);
			}
		}else{
			deleteUserApprov(userDetailM.getUserName());
		}
		
		Vector userExceptVect = userDetailM.getUserExceptionVect();
		if(userExceptVect!=null && userExceptVect.size()>0){
			log.debug("userExceptVect.size()=" + userExceptVect.size());
			UserExceptActM exceptActM;
			for(int i=0;i<userExceptVect.size();i++){
				exceptActM = (UserExceptActM)userExceptVect.get(i);
				chkReturnRows = updateUserExcept(userDetailM, exceptActM);
				if(chkReturnRows==0){
					saveUserExcept(exceptActM, userDetailM);
				}
			}
			deleteNotInKeyUserExcept(userDetailM, userExceptVect);
		}else{
			deleteUserExcept(userDetailM.getUserName());
		}
		
		return 0;
	}
	
	public void deleteUserApprov(String userID)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("DELETE USER_APPROVAL_AUTHORITY ");
				sql.append(" WHERE USER_ID = ?");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				ps.setString(1, userID);
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
	
	public void deleteNotInKeyUserExcept(UserDetailM userDetailM, Vector userExceptVect)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		String cmpCode = null;
		try {
		    // conn = Get Connection
		    conn = getConnection();
		
		    StringBuffer sql = new StringBuffer("");
		    sql.append("DELETE FROM USER_EXCEPTION ");
		    sql.append(" WHERE USER_ID = ? ");
		    
		    if ((userExceptVect != null) && (userExceptVect.size() > 0)) {
		        sql.append(" AND EXCEPTION_ID NOT IN ( ");
		        UserExceptActM exceptActM;
		        for (int i = 0; i < userExceptVect.size(); i++) {
		        	exceptActM = (UserExceptActM) userExceptVect.get(i);
		            sql.append(" '" + exceptActM.getExceptID() + "' , ");
		        }
		        
		        if (sql.toString().trim().endsWith(",")) {
		            sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
		        }
		
		        sql.append(" ) ");
		    }
		
		    String dSql = String.valueOf(sql);
		    log.debug("dSql="+dSql);
		    ps = conn.prepareStatement(dSql);
		
		    ps.setString(1, userDetailM.getUserName());
		    
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
	
	public double updateUserExcept(UserDetailM userDetailM, UserExceptActM exceptActM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE USER_EXCEPTION ");
			sql.append(" SET USER_ID=?, EXCEPTION_ID=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE USER_ID=? AND EXCEPTION_ID=? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userDetailM.getUserName());
			ps.setString(2, exceptActM.getExceptID());	
			ps.setString(3, userDetailM.getUpdateBy());
			ps.setString(4, userDetailM.getUserName());
			ps.setString(5, exceptActM.getExceptID());
			
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
	
	public void deleteNotInKeyUserBranch(UserDetailM userDetailM, Vector userBranchVect)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		String cmpCode = null;
		try {
		    // conn = Get Connection
		    conn = getConnection();
		
		    StringBuffer sql = new StringBuffer("");
		    sql.append("DELETE FROM USER_BRANCH");
		    sql.append(" WHERE USER_ID = ? AND CMPCDE ="+" '" +OrigConstant.ORIG_CMPCODE + "'");
		    
		    if ((userBranchVect != null) && (userBranchVect.size() > 0)) {
		        sql.append(" AND AREA NOT IN ( ");
		        UserBranchM userBranchM;
		        for (int i = 0; i < userBranchVect.size(); i++) {
		        	userBranchM = (UserBranchM) userBranchVect.get(i);
		            sql.append(" '" + userBranchM.getArea() + "' , ");
		        }
		        
		        if (sql.toString().trim().endsWith(",")) {
		            sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
		        }
		
		        sql.append(" ) ");
		    }
		
		    String dSql = String.valueOf(sql);
		    log.debug("dSql="+dSql);
		    ps = conn.prepareStatement(dSql);
		
		    ps.setString(1, userDetailM.getUserName());
		    
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
	
	public double updateUserBranch(UserDetailM userDetailM, UserBranchM userBranchM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE USER_BRANCH ");
			sql.append(" SET USER_ID=?, AREA=?, CMPCDE=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE USER_ID=? AND AREA=? AND CMPCDE=? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userDetailM.getUserName());
			ps.setString(2, userBranchM.getArea());	
			ps.setString(3, userBranchM.getCmpCde());
			ps.setString(4, userDetailM.getUpdateBy());
			ps.setString(5, userDetailM.getUserName());
			ps.setString(6, userBranchM.getArea());
			ps.setString(7, userBranchM.getCmpCde());
			
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
	
	public double updateUserDetailM(UserDetailM userDetailM)
			throws OrigApplicationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE US_USER_DETAIL ");
			
			sql.append(" SET USER_NAME=?,DESCRIPTION=?,REGION=?,DEPARTMENT=?,FIRSTNAME=? ");
			sql.append(" ,LASTNAME=?,EMAIL_ADDRESS=?,TELEPHONE=?,MOBILEPHONE=? ");
			sql.append(" ,JOB_DESCRIPTION=?,ZONE_ID=?,POSITION=?,CONTACT_CHANNEL=?,STATUS=?,SKIP_IP_FLAG=?, LAST_UPDATE_DATE=SYSDATE, LAST_UPDATE_USER=?, DEFAULT_OFFICE_CODE=?, LOGON_FLAG=? ");
			
			sql.append(" WHERE USER_NAME = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userDetailM.getUserName());
			ps.setString(2, userDetailM.getDescription());	
			ps.setString(3, userDetailM.getRegion());
			ps.setString(4, userDetailM.getDepartment());
			ps.setString(5, userDetailM.getFirstName());
			ps.setString(6, userDetailM.getLastName());
			ps.setString(7, userDetailM.getEmail());
			ps.setString(8, userDetailM.getTelephone());
			ps.setString(9, userDetailM.getMobilePhone());
			ps.setString(10, userDetailM.getJobDescription());
			ps.setString(11, userDetailM.getZoneID());
			ps.setString(12, userDetailM.getPosition());
			ps.setString(13, userDetailM.getCommunicate_channel());
			ps.setString(14, userDetailM.getStatus());
			ps.setString(15, userDetailM.getSkipIP());
			ps.setString(16, userDetailM.getUpdateBy());
			ps.setString(17, userDetailM.getDefaultOfficeCode());
			ps.setString(18, userDetailM.getLogOnFlag());
			ps.setString(19, userDetailM.getUserName());
			
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
	
	public UserDetailM selectOrigMasterUserDetailM(String userNameEdit)
	throws OrigApplicationMException {
		
		UserDetailM userDetailM = selectUserDetailM(userNameEdit);
		
		Vector userBranchVect = selectUserBranch(userNameEdit);
		
		log.debug("userBranchVect = " + userBranchVect);
		if(userBranchVect!=null){
			log.debug("userBranchVect.size() = " + userBranchVect.size());
		}
		
		if(userBranchVect!=null && userBranchVect.size()>0){
			UserBranchM userBranchM;
			for(int i=0;i<userBranchVect.size();i++){
				userBranchM = (UserBranchM)userBranchVect.get(i);
				userBranchM = getBranchDesc(userBranchM);
				userBranchVect.setElementAt(userBranchM,i);
			}
		}
		
		Vector userApprovVect = selectUserApprov(userNameEdit);
		/*
		log.debug("userApprovVect = " + userApprovVect);
		if(userApprovVect!=null){
			log.debug("userApprovVect.size() = " + userApprovVect.size());
		}
		
		if(userApprovVect!=null && userApprovVect.size()>0){
			UserNameM userNameM;
			for(int i=0;i<userApprovVect.size();i++){
				userNameM = (UserNameM)userApprovVect.get(i);
				userNameM = getCusType(userNameM);
				userNameM = getloanType(userNameM);
				userApprovVect.setElementAt(userNameM,i);
			}
		}*/
		
		Vector userExceptVect = selectUserExcept(userNameEdit);
		
		log.debug("userExceptVect = " + userExceptVect);
		if(userExceptVect!=null){
			log.debug("userExceptVect.size() = " + userExceptVect.size());
		}
		
		if(userExceptVect!=null && userExceptVect.size()>0){
			UserExceptActM exceptActM;
			for(int i=0;i<userExceptVect.size();i++){
				exceptActM = (UserExceptActM)userExceptVect.get(i);
				exceptActM = getExceptDesc(exceptActM);
				userExceptVect.setElementAt(exceptActM,i);
			}
		}
		
		userDetailM.setUserBranchVect(userBranchVect);
		userDetailM.setUserProfileVect(userApprovVect);
		userDetailM.setUserExceptionVect(userExceptVect);
		
		return userDetailM;
	}
	
	public UserExceptActM getExceptDesc(UserExceptActM exceptActM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT EXCEPTION_DESC ");
			sql.append(" FROM EXCEPTION  WHERE EXCEPTION_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, exceptActM.getExceptID());
		
			rs = ps.executeQuery();
		
			if (rs.next()) {
				exceptActM.setExceptDesc(rs.getString(1));
				
			}
			return exceptActM;
		
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
	
	public UserBranchM getBranchDesc(UserBranchM userBranchM)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT THDESC ");
			sql.append(" FROM HPTBHP15  WHERE CMPCDE = ? AND AREA = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userBranchM.getCmpCde());
			ps.setString(2, userBranchM.getArea());
		
			rs = ps.executeQuery();
		
			if (rs.next()) {
				userBranchM.setBranchDesc(rs.getString(1));
				
			}
			return userBranchM;
		
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
	
	public Vector selectUserExcept(String userNameEdit)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT USER_ID, EXCEPTION_ID ");
			sql.append(" FROM USER_EXCEPTION  WHERE USER_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userNameEdit);
		
			rs = ps.executeQuery();
			UserExceptActM exceptActM = null;
			Vector userExceptVect = new Vector();
		
			while (rs.next()) {
				exceptActM = new UserExceptActM();
				
				exceptActM.setUserId(rs.getString(1));
				exceptActM.setExceptID(rs.getString(2));
				
				userExceptVect.add(exceptActM);
				
			}
			return userExceptVect;
		
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
	
	public Vector selectUserApprov(String userNameEdit)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			//sql.append(" SELECT   USER_ID, GROUP_NAME, LOAN_TYPE, CUSTOMER_TYPE ");
			sql.append(" SELECT  DISTINCT USER_ID,GROUP_NAME ");
			sql.append(" FROM USER_APPROVAL_AUTHORITY  WHERE USER_ID = ?  ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userNameEdit);
		
			rs = ps.executeQuery();
			UserNameM userNameM = null;
			Vector userApprovVect = new Vector();
		
			while (rs.next()) {
				userNameM = new UserNameM();
				
				userNameM.setUserId(rs.getString(1));
				userNameM.setGroupName(rs.getString(2));
				//userNameM.setLoanType(rs.getString(3));
				//userNameM.setCusType(rs.getString(4));
				
				userApprovVect.add(userNameM);
				
			}
			return userApprovVect;
		
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
	
	public Vector selectUserBranch(String userNameEdit)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT USER_ID, AREA, CMPCDE ");
			sql.append(" FROM USER_BRANCH  WHERE USER_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userNameEdit);
		
			rs = ps.executeQuery();
			UserBranchM userBranchM = null;
			Vector userBranchVect = new Vector();
		
			while (rs.next()) {
				userBranchM = new UserBranchM();
				
				userBranchM.setUserId(rs.getString(1));
				userBranchM.setArea(rs.getString(2));
				userBranchM.setCmpCde(rs.getString(3));
				
				userBranchVect.add(userBranchM);
				
			}
			return userBranchVect;
		
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
	
	public UserDetailM selectUserDetailM(String userNameEdit)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT USER_NAME, DESCRIPTION, REGION, DEPARTMENT, FIRSTNAME ");
			sql.append(" , LASTNAME, EMAIL_ADDRESS, TELEPHONE, MOBILEPHONE,JOB_DESCRIPTION ");
			sql.append(" , ZONE_ID, POSITION, CONTACT_CHANNEL, STATUS,SKIP_IP_FLAG ");
			sql.append(" , DEFAULT_OFFICE_CODE, LOGON_FLAG,HELPDESK_FLAG ");
			sql.append(" FROM US_USER_DETAIL  WHERE USER_NAME = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, userNameEdit);

			rs = ps.executeQuery();
			UserDetailM userDetailM2 = null;

			if (rs.next()) {
				userDetailM2 = new UserDetailM();
				userDetailM2.setUserName(rs.getString(1));
				userDetailM2.setDescription(rs.getString(2));
				userDetailM2.setRegion(rs.getString(3));
				userDetailM2.setDepartment(rs.getString(4));
				userDetailM2.setFirstName(rs.getString(5));
				userDetailM2.setLastName(rs.getString(6));
				userDetailM2.setEmail(rs.getString(7));
				userDetailM2.setTelephone(rs.getString(8));
				userDetailM2.setMobilePhone(rs.getString(9));
				userDetailM2.setJobDescription(rs.getString(10));
				userDetailM2.setZoneID(rs.getString(11));
				userDetailM2.setPosition(rs.getString(12));
				userDetailM2.setCommunicate_channel(rs.getString(13));
				userDetailM2.setStatus(rs.getString(14));
				userDetailM2.setSkipIP(rs.getString(15));
				userDetailM2.setDefaultOfficeCode(rs.getString(16));
				userDetailM2.setLogOnFlag(rs.getString(17));
				userDetailM2.setHelpDeskFlag(rs.getString(18));
			}
			return userDetailM2;

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
	
	
	public void createModelOrigMasterUserDetailM(UserDetailM userDetailM)
	throws OrigApplicationMException {
		saveUserDetailM(userDetailM);
		
		Vector userBranchVect = userDetailM.getUserBranchVect();
		if(userBranchVect!=null && userBranchVect.size()>0){
			UserBranchM userBranchM;
			for(int i=0;i<userBranchVect.size();i++){
				userBranchM = (UserBranchM)userBranchVect.get(i);
				saveUserBranch(userBranchM, userDetailM);
			}
		}
		
		Vector userApprovVect = userDetailM.getUserProfileVect();
		if(userApprovVect!=null && userApprovVect.size()>0){
			UserNameM userNameM;
			for(int i=0;i<userApprovVect.size();i++){
				userNameM = (UserNameM)userApprovVect.get(i);
				saveUserApprov(userNameM, userDetailM);
			}
		}
		
		Vector userExceptVect = userDetailM.getUserExceptionVect();
		if(userExceptVect!=null && userExceptVect.size()>0){
			UserExceptActM exceptActM;
			for(int i=0;i<userExceptVect.size();i++){
				exceptActM = (UserExceptActM)userExceptVect.get(i);
				saveUserExcept(exceptActM, userDetailM);
			}
		}
	}
	
	public void saveUserExcept(UserExceptActM exceptActM, UserDetailM userDetailM)
	throws OrigApplicationMException {
	PreparedStatement ps = null;
	Connection conn = null;
	try {
		//conn = Get Connection
		conn = getConnection();
		StringBuffer sql = new StringBuffer("");
		sql.append("INSERT INTO USER_EXCEPTION ");			
		sql.append(" ( USER_ID, EXCEPTION_ID, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE ) ");
		sql.append(" VALUES(?,?,?,?,SYSDATE,SYSDATE ) ");
		
		log.debug("userDetailM.getUserName()= "+userDetailM.getUserName());
		log.debug("exceptActM.getExceptID() ="+exceptActM.getExceptID());
		log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
		log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
		
		String dSql = String.valueOf(sql);
		log.debug("Sql="+dSql);
		ps = conn.prepareStatement(dSql);
		ps.setString(1, userDetailM.getUserName());
		ps.setString(2, exceptActM.getExceptID());	
		ps.setString(3, userDetailM.getUpdateBy());
		ps.setString(4, userDetailM.getUpdateBy());
		
		ps.executeUpdate();
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
	
	public void saveUserApprov(UserNameM userNameM, UserDetailM userDetailM)
	throws OrigApplicationMException {
	PreparedStatement ps = null;
	Connection conn = null;
	try {
		//conn = Get Connection
		conn = getConnection();
		StringBuffer sql = new StringBuffer("");
		sql.append("INSERT INTO USER_APPROVAL_AUTHORITY ");			
		sql.append(" ( USER_ID, GROUP_NAME, LOAN_TYPE, CUSTOMER_TYPE, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE ) ");
		sql.append(" VALUES(?,?,?,?,?,?,SYSDATE,SYSDATE ) ");
		
		log.debug("userDetailM.getUserName()= "+userDetailM.getUserName());
		log.debug("userNameM.getGroupName() ="+userNameM.getGroupName());
		log.debug("userNameM.getLoanType()= "+userNameM.getLoanType());
		log.debug("userNameM.getCusType() ="+userNameM.getCusType());
		log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
		log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
		
		String dSql = String.valueOf(sql);
		log.debug("Sql="+dSql);
		ps = conn.prepareStatement(dSql);
		ps.setString(1, userDetailM.getUserName());
		ps.setString(2, userNameM.getGroupName());	
		ps.setString(3, "-");
		ps.setString(4,"-");
		ps.setString(5, userDetailM.getUpdateBy());
		ps.setString(6, userDetailM.getUpdateBy());
		
		ps.executeUpdate();
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
	
	public void saveUserBranch(UserBranchM userBranchM, UserDetailM userDetailM)
		throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO USER_BRANCH ");			
			sql.append(" ( USER_ID, AREA, CMPCDE, CREATE_DATE, CREATE_BY , UPDATE_DATE, UPDATE_BY ) ");
			sql.append(" VALUES(?,?,?,SYSDATE,?,SYSDATE,? ) ");
			
			log.debug("userDetailM.getUserName()= "+userDetailM.getUserName());
			log.debug("userBranchM.getArea() ="+userBranchM.getArea());
			log.debug("userBranchM.getCmpCde()= "+userBranchM.getCmpCde());
			log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
			log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userDetailM.getUserName());
			ps.setString(2, userBranchM.getArea());	
			ps.setString(3, userBranchM.getCmpCde());
			ps.setString(4, userDetailM.getUpdateBy());
			ps.setString(5, userDetailM.getUpdateBy());
			
			ps.executeUpdate();
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
	
	public void saveUserDetailM(UserDetailM userDetailM)
	throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO US_USER_DETAIL ");
			
			sql.append(" ( USER_NAME,DESCRIPTION,REGION,DEPARTMENT,FIRSTNAME ");
			sql.append(" ,LASTNAME,EMAIL_ADDRESS,TELEPHONE,MOBILEPHONE ");
			sql.append(" ,JOB_DESCRIPTION,ZONE_ID,POSITION,CONTACT_CHANNEL,STATUS,SKIP_IP_FLAG, LAST_UPDATE_DATE, LAST_UPDATE_USER, DEFAULT_OFFICE_CODE, LOGON_FLAG ) ");
			
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ,SYSDATE,?,?,? ) ");
			
	/*		log.debug("userDetailM.getUserName()= "+userDetailM.getUserName());
			log.debug("userDetailM.getDescription() ="+userDetailM.getDescription());
			log.debug("userDetailM.getRegion()= "+userDetailM.getRegion());
			log.debug("userDetailM.getDepartment() ="+userDetailM.getDepartment());
			log.debug("userDetailM.getFirstName() ="+userDetailM.getFirstName());
			log.debug("userDetailM.getLastName() ="+userDetailM.getLastName());
			log.debug("userDetailM.getEmail() ="+userDetailM.getEmail());
			log.debug("userDetailM.getTelephone() ="+userDetailM.getTelephone());
			log.debug("userDetailM.getMobilePhone() ="+userDetailM.getMobilePhone());
			log.debug("userDetailM.getJobDescription() ="+userDetailM.getJobDescription());
			log.debug("userDetailM.getZoneID() ="+userDetailM.getZoneID());
			log.debug("userDetailM.getPosition() ="+userDetailM.getPosition());
			log.debug("userDetailM.getCommunicate_channel() ="+userDetailM.getCommunicate_channel());
			log.debug("userDetailM.getStatus() ="+userDetailM.getStatus());
			log.debug("userDetailM.getSkipIP() ="+userDetailM.getSkipIP());
			log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
			log.debug("userDetailM.getDefaultOfficeCode() ="+userDetailM.getDefaultOfficeCode());
			log.debug("userDetailM.getLogOnFlag() ="+userDetailM.getLogOnFlag());*/
			
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userDetailM.getUserName());
			ps.setString(2, userDetailM.getDescription());	
			ps.setString(3, userDetailM.getRegion());
			ps.setString(4, userDetailM.getDepartment());
			ps.setString(5, userDetailM.getFirstName());
			ps.setString(6, userDetailM.getLastName());
			ps.setString(7, userDetailM.getEmail());
			ps.setString(8, userDetailM.getTelephone());
			ps.setString(9, userDetailM.getMobilePhone());
			ps.setString(10, userDetailM.getJobDescription());
			ps.setString(11, userDetailM.getZoneID());
			ps.setString(12, userDetailM.getPosition());
			ps.setString(13, userDetailM.getCommunicate_channel());
			ps.setString(14, userDetailM.getStatus());
			ps.setString(15, userDetailM.getSkipIP());
			ps.setString(16, userDetailM.getUpdateBy());
			ps.setString(17, userDetailM.getDefaultOfficeCode());
			ps.setString(18, userDetailM.getLogOnFlag());
			
			ps.executeUpdate();
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
	
	public void saveUserM(UserM userDetailM)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO US_USER_DETAIL ");
			
			sql.append(" ( USER_NAME,DESCRIPTION,REGION,DEPARTMENT,FIRSTNAME ");
			sql.append(" ,LASTNAME,EMAIL_ADDRESS,TELEPHONE,MOBILEPHONE ");
			sql.append(" ,JOB_DESCRIPTION,ZONE_ID,POSITION,CONTACT_CHANNEL,STATUS,SKIP_IP_FLAG, LAST_UPDATE_DATE, LAST_UPDATE_USER, DEFAULT_OFFICE_CODE, LOGON_FLAG ) ");
			
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,?  ,?,?,?,?,? ,SYSDATE,?,?,? ) ");
			
	/*		log.debug("userDetailM.getUserName()= "+userDetailM.getUserName());
			log.debug("userDetailM.getDescription() ="+userDetailM.getDescription());
			log.debug("userDetailM.getRegion()= "+userDetailM.getRegion());
			log.debug("userDetailM.getDepartment() ="+userDetailM.getDepartment());
			log.debug("userDetailM.getFirstName() ="+userDetailM.getFirstName());
			log.debug("userDetailM.getLastName() ="+userDetailM.getLastName());
			log.debug("userDetailM.getEmail() ="+userDetailM.getEmail());
			log.debug("userDetailM.getTelephone() ="+userDetailM.getTelephone());
			log.debug("userDetailM.getMobilePhone() ="+userDetailM.getMobilePhone());
			log.debug("userDetailM.getJobDescription() ="+userDetailM.getJobDescription());
			log.debug("userDetailM.getZoneID() ="+userDetailM.getZoneID());
			log.debug("userDetailM.getPosition() ="+userDetailM.getPosition());
			log.debug("userDetailM.getCommunicate_channel() ="+userDetailM.getCommunicate_channel());
			log.debug("userDetailM.getStatus() ="+userDetailM.getStatus());
			log.debug("userDetailM.getSkipIP() ="+userDetailM.getSkipIP());
			log.debug("userDetailM.getUpdateBy() ="+userDetailM.getUpdateBy());
			log.debug("userDetailM.getDefaultOfficeCode() ="+userDetailM.getDefaultOfficeCode());
			log.debug("userDetailM.getLogOnFlag() ="+userDetailM.getLogOnFlag());*/
			
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, userDetailM.getUserName());
			ps.setString(2, userDetailM.getDescription());	
			ps.setString(3, userDetailM.getRegion());
			ps.setString(4, userDetailM.getDepartment());
			ps.setString(5, userDetailM.getThaiFirstName());
			ps.setString(6, userDetailM.getThaiLastName());
			ps.setString(7, null);
			ps.setString(8, userDetailM.getTelephone());
			ps.setString(9, userDetailM.getMobilePhone());
			ps.setString(10, userDetailM.getJobDescription());
			ps.setString(11, null);
			ps.setString(12, userDetailM.getPosition());
			ps.setString(13, null);
			ps.setString(14, userDetailM.getStatus());
			ps.setString(15, null);
			ps.setString(16, userDetailM.getCreatedBy());
			ps.setString(17, null);
			ps.setString(18, null);
			
			ps.executeUpdate();
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

	public String getUserApprovGroup(String userName)
			throws OrigApplicationMException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT  distinct  GROUP_NAME ");
			sql.append(" FROM USER_APPROVAL_AUTHORITY  WHERE USER_ID = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);			 
			ps.setString(1, userName);		
			rs = ps.executeQuery();
			UserNameM userNameM = null;
			//Vector userApprovVect = new Vector();		
			if (rs.next()) {
				return rs.getString(1);								 				
			}else{
			    return null;
			}
			 
		
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
