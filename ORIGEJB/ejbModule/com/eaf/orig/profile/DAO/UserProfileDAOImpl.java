package com.eaf.orig.profile.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.DAO.exception.UserProfileException;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.model.UserExceptionDataM;
/*import com.ge.xenoz.model.profile.ProfileM;
import com.ge.xenoz.model.profile.UserProfileM;*/

/**
 * @author wpsadmin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class UserProfileDAOImpl extends OrigObjectDAO implements UserProfileDAO {
	/**
	 * Constructor for UserProfileDAOImpl.
	 */
	
	Logger logger = Logger.getLogger(this.getClass());
	
	public UserProfileDAOImpl() {
		super();
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getRoleOfUser(String)
	 */
	public HashMap getRoleOfUser(String userName) throws UserProfileException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		try {
			conn = getConnection();
			String dSql = "SELECT DISTINCT USER_NAME , ROLE_ID  FROM US_USER_ROLE_MAPPING  WHERE  UPPER(USER_NAME) = UPPER('"+userName+"') AND STATUS = 'ACTIVATED' "; 
			ps = conn.prepareStatement( dSql	 , ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY);
			logger.debug("dSql>"+dSql);
		//	ps.setMaxRows(NAS3Constant.MAX_ROWS);
  			rs = ps.executeQuery();
			HashMap roleMap=new HashMap();
			while (rs.next()) {
					String roleID = rs.getString( "ROLE_ID" );
					roleMap.put(roleID,roleID)	;			
			}
			return roleMap;

		} catch (SQLException sqlx) {
			sqlx.printStackTrace();
			throw new UserProfileException(sqlx.getMessage());
		
		}finally{
			try{
//				try{
//					if(conn!=null)conn.commit();
//				}catch(Exception e){}
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
				rs=null;
				ps=null;
				conn=null;
			}catch(Exception e){
				e.printStackTrace();
				logger.error("UserProfileDAPImpl : getRoleOfUser : cannot close connection.");
			}
		}	

	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getUserProfile(String)
	 */
	public UserDetailM getUserProfile(String userName) throws UserProfileException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserDetailM userM = new UserDetailM();
		try{
			conn = getConnection();										
			StringBuilder dSql = new StringBuilder();			
			dSql.append("SELECT * FROM US_USER_DETAIL  WHERE UPPER(US_USER_DETAIL.USER_NAME) = UPPER(?) ");			
			logger.debug(dSql);			
			ps = conn.prepareStatement(dSql.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1,userName);
  			rs = ps.executeQuery();
			HashMap roleMap = new HashMap();			
			while(rs.next()){
				userM.setUserName(userName);					
				userM.setDescription(rs.getString("DESCRIPTION"));
				userM.setRegion(rs.getString("REGION"));
				userM.setDepartment(rs.getString("DEPARTMENT"));
				userM.setFirstName(rs.getString("FIRSTNAME"));
				userM.setLastName(rs.getString("LASTNAME"));
				userM.setPosition(rs.getString("POSITION"));
				userM.setTelephone(rs.getString("TELEPHONE"));
				userM.setMobilePhone(rs.getString("MOBILEPHONE"));		
				userM.setThaiFirstName(rs.getString("THAI_FIRSTNAME"));
				userM.setThaiLastName(rs.getString("THAI_LASTNAME"));
				userM.setStatus(rs.getString("ACTIVE_STATUS"));
				userM.setJobDescription(rs.getString("JOB_DESCRIPTION"));
				userM.setZoneID(rs.getString("ZONE_ID"));
				userM.setOrganizeID(rs.getString("ORGANIZE_ID"));
				userM.setDefaultAcceptAlertFlag(rs.getString("DEFAULT_ALERT_FLAG"));
				userM.setDefaultNotiInterval(rs.getDouble("DEFAULT_NOTI_INTERVAL"));
				userM.setDefaultAcceptNoitFlag(rs.getString("DEFAULT_NOTI_FLAG"));		
				userM.setEmail(rs.getString("EMAIL_ADDRESS"));
				userM.setCommunicate_channel(rs.getString("CONTACT_CHANNEL"));
				userM.setDefaultOfficeCode(rs.getString("DEFAULT_OFFICE_CODE")); 
				userM.setHelpDeskFlag(rs.getString("HELPDESK_FLAG"));
				userM.setUserNo(rs.getString("USER_NO"));
				userM.setUserExceptionVect(getUserException(userName));					 
			}		 
			return userM;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new UserProfileException(e.getLocalizedMessage());		
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				throw new UserProfileException(e.getLocalizedMessage());		
			}
		}
	}
	public Vector getUserException(String userId) throws UserProfileException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		Vector vUserException = new Vector();
		try {
			conn = getConnection();
			StringBuilder dSql = new StringBuilder();
			dSql.append(" SELECT USER_ID ,EXCEPTION_ID FROM USER_EXCEPTION WHERE UPPER(USER_ID) = UPPER(?) ORDER BY 2 ");			
			logger.debug(dSql);			
			ps = conn.prepareStatement( dSql.toString() , ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY);			
			ps.setString(1, userId);
  			rs = ps.executeQuery();
			while(rs.next()){
			      UserExceptionDataM userExceptionDataM=new UserExceptionDataM();
			      userExceptionDataM.setUserId( rs.getString(1) );
			      userExceptionDataM.setExceptionId( rs.getString(2) );
			      vUserException.add(userExceptionDataM);
			}
			return vUserException;
		} catch (SQLException sqlx) {
			sqlx.printStackTrace();
			throw new UserProfileException(sqlx.getMessage());		
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				throw new UserProfileException(e.toString());    
			}
		}	
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getSupplierUserProfile(String, String)
	 */
	public UserDetailM getSupplierUserProfile(String userName, String password)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getManagersOfUser(String)
	 */
	public Vector getManagersOfUser(String userName) throws UserProfileException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		

		Vector managers = new Vector();
		try {
			conn = getConnection();
			String dSql = "SELECT   USER_NAME  ,"+
										"  MANAGER_NAME ,"+
										"  DESCRIPTION  "+
										"  FROM US_USER_MANAGER_MAPPING "+
										"	WHERE UPPER(USER_NAME) = UPPER('"+ userName +"')  "+
										"	ORDER BY 2 "; 
										
			ps = conn.prepareStatement( dSql	 , ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY);
			logger.debug("dSql>"+dSql);
		//	ps.setMaxRows(NAS3Constant.MAX_ROWS);
  			rs = ps.executeQuery();
			HashMap roleMap=new HashMap();
			while (rs.next()) {
					managers.add( rs.getString("MANAGER_NAME") );
			}
			return managers;

		} catch (SQLException sqlx) {
			sqlx.printStackTrace();
			throw new UserProfileException(sqlx.getMessage());
		
		}finally{
			try{
//				if(conn!=null)conn.commit();
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
				rs=null;
				ps=null;
				conn=null;
			}catch(Exception e){
				e.printStackTrace();
				throw new UserProfileException(e.toString());    
			}
		}	

	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getUserNameInManagerName(String, String)
	 */
	public Vector getUserNameInManagerName(String managerName, String role)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getListOfUserNameInManagerName(String, String)
	 */
	public String getListOfUserNameInManagerName(
		String managerName,
		String role)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#convertUserVector2String(Vector)
	 */
	public String convertUserVector2String(Vector vt)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getZoneByUserName(String)
	 */
	public String getZoneByUserName(String userName)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getRegionOfficerInZone(String)
	 */
	public Vector getRegionOfficerInZone(String zoneID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getCostCenter(String)
	 */
	public String getCostCenter(String organizeID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#managerIsStoreOfficer(String)
	 */
	public boolean managerIsStoreOfficer(String userName)
		throws UserProfileException {
		return false;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getZoneDescription(String)
	 */
	public String getZoneDescription(String zoneID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getResponceZone(String)
	 */
	public Vector getResponceZone(String region) throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getListUsersByOrganizeAndRole(String, String)
	 */
	public String getListUsersByOrganizeAndRole(String orgID, String roleID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getListUsersByRegionAndRole(String, String)
	 */
	public String getListUsersByRegionAndRole(String regionID, String roleID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getUsersByOrganizeAndRole(String, String)
	 */
	public Vector getUsersByOrganizeAndRole(String orgID, String roleID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getUsersByRegionAndRole(String, String)
	 */
	public Vector getUsersByRegionAndRole(String regionID, String roleID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getRegionOfficerInRegion(String)
	 */
	public Vector getRegionOfficerInRegion(String regionID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#isSelectAllRegion(String)
	 */
	public boolean isSelectAllRegion(String organizeID)
		throws UserProfileException {
		return false;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getRegionByOrganizeID(String)
	 */
	public Vector getRegionByOrganizeID(String organizeID)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getUserDetailByUserDetailM(UserDetailM)
	 */
	public Vector getUserDetailByUserDetailM(UserDetailM userDetailM)
		throws UserProfileException {
		return null;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#checkUserRole(UserDetailM, String)
	 */
	public boolean checkUserRole(UserDetailM userM, String role)
		throws UserProfileException {
		return false;
	}

	/**
	 * @see com.eaf.orig.profile.DAO.UserProfileDAO#getFullName(String)
	 */
	public String getFullName(String userName) throws UserProfileException {
		return null;
	}
	
	
	private Vector loadUserProfile(String userName) throws UserProfileException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		

		Vector userProfileVt = new Vector();
		try {
			conn = getConnection();
			String dSql = "SELECT u.USER_NAME, u.PROFILE_ID, u.EXCEP_NOTI_FLAG, u.LENDING_LIMIT, u.EXCEP_THRESHOLD, "
							+" p.PROFILE_ID, p.PROFILE_DESC, p.BUS_CLASS_ID, p.CAIQ_QUOTA, p.PROFILE_GRP_ID, "
							+" u.ALERT_FLAG, u.NOTI_INTERVAL, u.NOTI_FLAG "
							+" FROM USER_PROFILE u, PROFILE p "
							+" WHERE u.PROFILE_ID= p.PROFILE_ID and u.USER_NAME = ?"; 
										
			ps = conn.prepareStatement( dSql	 , ResultSet.TYPE_SCROLL_INSENSITIVE , ResultSet.CONCUR_READ_ONLY);
			logger.debug("dSql>"+dSql);
		//	ps.setMaxRows(NAS3Constant.MAX_ROWS);
			ps.setString(1, userName);
  			rs = ps.executeQuery();
			HashMap roleMap=new HashMap();
			while (rs.next()) {
/*				UserProfileM userProfileM = new UserProfileM();
				userProfileM.setUserName(rs.getString(1));
				userProfileM.setProfileId(rs.getString(2));
				userProfileM.setExcepNotiFlag(rs.getString(3));
				userProfileM.setLendingLimit(rs.getDouble(4));
				userProfileM.setExcepThreshold(rs.getDouble(5));
				userProfileM.setNotiInterval(rs.getDouble(12));
				userProfileM.setAcceptAlertFlag(rs.getString(11));
				userProfileM.setAcceptNotiFlag(rs.getString(13));

				ProfileM profileM = new ProfileM();
				profileM.setId(rs.getString(6));
				profileM.setDescription(rs.getString(7));
				profileM.setBusinessClassId(rs.getString(8));
				profileM.setProfileLocation(null);
				profileM.setQuotaCAIQ(rs.getInt(9));
				profileM.setGroupId(rs.getString(10));
				
				userProfileM.setProfile(profileM);
				
				userProfileVt.add(userProfileM);*/
			}
			return userProfileVt;

		} catch (SQLException sqlx) {
			sqlx.printStackTrace();
			throw new UserProfileException(sqlx.getMessage());
		
		}finally{
			try{
//				try{
//					if(conn!=null)conn.commit();
//				}catch(Exception e){}
				if(rs!=null)rs.close();
				if(ps!=null)ps.close();
				if(conn!=null)conn.close();
				rs=null;
				ps=null;
				conn=null;
			}catch(Exception e){
				e.printStackTrace();
				logger.error("UserProfileDAPImpl : loadUserProfile : cannot close connection.");
			}
		}	

	}
	
//	public Connection getConnection(){
//		try{
//			OrigServiceLocator geService=OrigServiceLocator.getInstance();
//			return geService.getConnection(OrigServiceLocator.ORIG_DB); 
//		}catch(Exception e){ 
//	     logger.error("Connection is null");
//			e.printStackTrace();
//		}
//		return null;    
//	}

}
