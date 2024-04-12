package com.eaf.orig.profile.DAO;

import java.util.HashMap;
import java.util.Vector;

import com.eaf.orig.profile.DAO.exception.UserProfileException;
import com.eaf.orig.profile.model.UserDetailM;

/**
 * @author wpsadmin
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */  


	public interface UserProfileDAO {

		/**
		 * Developed by Surojjana
		 * Gets Role of User
		 * @param String is User Name
		 * @return HashMap of Role
		*	Key and Value of HashMap :String is Role ID 
		 * */
		HashMap getRoleOfUser(String userName) throws UserProfileException;

		/**
		 * Developed by Surojjana
		 * Gets User Profile
		 * @param String is User Name
		 * @return UserDetailM
		 * */
		public UserDetailM getUserProfile(String userName)throws UserProfileException;

		/**
		 * Developed by Surojjana
		 * Gets User Profile
		 * @param String is User Name
		 * @param String is Password
		 * @return UserDetailM 
		 * **/
		public UserDetailM getSupplierUserProfile(
			String userName,
			String password)
			throws UserProfileException;

		/**
		 * Developed by Surojjana
		 * Gets Manager of User
		 * @param String is User Name
		 * @return Vector of manager 
		 */
		public Vector getManagersOfUser(String userName)
			throws UserProfileException;

		public Vector getUserNameInManagerName(String managerName, String role)
			throws UserProfileException;

		public String getListOfUserNameInManagerName(
			String managerName,
			String role)
			throws UserProfileException;

		public String convertUserVector2String(Vector vt)
			throws UserProfileException;

		public String getZoneByUserName(String userName)
			throws UserProfileException;
		public Vector getRegionOfficerInZone(String zoneID)
			throws UserProfileException;
		

		/**
		 * Developed by Surojjana
		 * @param String is Organize ID
		 * @return String is Cost Center ID
		 */
		public String getCostCenter(String organizeID)
			throws UserProfileException;

		/**
		 *Developed by Surojjana
		 * Check Manager of this User has role SO (Store Officer)
		 * @param String user name
		 * @return boolean :true is manager of this user is Store Officer
		 */
		public boolean managerIsStoreOfficer(String userName)
			throws UserProfileException;

		public String getZoneDescription(String zoneID)
			throws UserProfileException;
		public Vector getResponceZone(String region)
			throws UserProfileException;

		public String getListUsersByOrganizeAndRole(
			String orgID,
			String roleID)
			throws UserProfileException;
		public String getListUsersByRegionAndRole(
			String regionID,
			String roleID)
			throws UserProfileException;
		public Vector getUsersByOrganizeAndRole(String orgID, String roleID)
			throws UserProfileException;
		public Vector getUsersByRegionAndRole(String regionID, String roleID)
			throws UserProfileException;
		public Vector getRegionOfficerInRegion(String regionID)
			throws UserProfileException;

		/**
		 *Developed by Surojjana
		 * Check Organize ID of this User can select Destination in all region or not
		 * @param String organize ID
		 * @return boolean :true is User can Select Destination in all region
		 */
		public boolean isSelectAllRegion(String organizeID)
			throws UserProfileException;

		public Vector getRegionByOrganizeID(String organizeID)
			throws UserProfileException;
		public Vector getUserDetailByUserDetailM(UserDetailM userDetailM)
			throws UserProfileException;
		public boolean checkUserRole(UserDetailM userM, String role)
			throws UserProfileException;


		/**
		 * Get Full Name of User
		 * @param String is user name
		 * @return String is fist name concatenate with last name
		 */
		public String getFullName(String userName) throws UserProfileException;

	}


