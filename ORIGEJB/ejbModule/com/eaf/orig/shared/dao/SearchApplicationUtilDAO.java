package com.eaf.orig.shared.dao;

import java.util.Vector;

import com.eaf.orig.shared.dao.exceptions.SearchApplicationUtilDAOException;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface SearchApplicationUtilDAO {
	public Vector getSearchApplication_SG(String username,String searchType,String appNo,String cardNo,String citizenId,String chiFName,String chiLName,String chiName) throws SearchApplicationUtilDAOException;			 		
	public Vector getSearchApplicationEnhance_SG(String username,String searchType,String appNo,String cardNo,String citizenId,String chiName, String receiveDateFrom, String receiveDateTo, String status, String selectedUser) throws SearchApplicationUtilDAOException;
	public Vector getSearchImage(String searchType,String requestId,String dealer,String channelCode,String userName,String faxInDateFrom,String faxInDateTo,String nric,String firstName,String lastName,String sortBy, String appNo, String mainCustomerType, String dealerFaxNo) throws SearchApplicationUtilDAOException;
}
