package com.eaf.orig.shared.dao.utility;
import java.util.*;
import java.sql.*;

import com.eaf.orig.shared.dao.utility.exception.BusinessClassByUserDAOException;
import com.eaf.orig.shared.model.BusinessClassM;


/**
 * 
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface BusinessClassByUserDAO {
	
  public Vector getBusinessClassByUser(String username) throws  BusinessClassByUserDAOException,SQLException;
  public Vector getUserProfile(String busID,String DealerID,String userName) throws  BusinessClassByUserDAOException,SQLException;
  public boolean  checkUserProfileExcepFlag3(String busID,String DealerID,String userName) throws  BusinessClassByUserDAOException,SQLException;
  public Vector getBusinessClassByUserDealer(String username,String dealerId) throws BusinessClassByUserDAOException;		 
  public boolean checkAccessBusinessClass(String username,String dealerId,String busID) throws BusinessClassByUserDAOException;
  public Vector getDealerByUserHaveExcep3(String username) throws BusinessClassByUserDAOException;
  public HashMap getAccessBusinessClassByUser(String username) throws  BusinessClassByUserDAOException,SQLException;
  public BusinessClassM getBusinessClassUserLevel(String userName) throws  BusinessClassByUserDAOException,SQLException;
  public HashMap getBusinessClassByOrgLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException,SQLException;
  public HashMap getBusinessClassByProdLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException,SQLException;
  public HashMap getBusinessGrpByOrgLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException,SQLException;
  public HashMap getBusinessGrpByProdLevel(BusinessClassM prmBusinessClassM) throws BusinessClassByUserDAOException,SQLException;
  public HashMap getSearchAccessBusinessClassByUser(String username) throws  BusinessClassByUserDAOException,SQLException;

}
