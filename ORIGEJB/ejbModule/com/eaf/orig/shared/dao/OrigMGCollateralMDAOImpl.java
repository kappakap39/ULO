/*
 * Created on Mar 13, 2010
 * Created by wichaya
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

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
//import com.eaf.orig.shared.dao.utility.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author wichaya
 *
 * Type: OrigMGCollateralMDAO
 */
class OrigMGCollateralMDAOImpl extends OrigObjectDAO  implements OrigMGCollateralMDAO {
    private static Logger log = Logger.getLogger(OrigMGCollateralMDAOImpl.class);
    
    public void createCollateralM(Vector vCollatralM, String appRecordID, String username) throws OrigApplicationMException, UniqueIDGeneratorException, RemoteException {
        CollateralDataM collatralM;
        String collateral_id;
        if(vCollatralM!=null && vCollatralM.size()>0){
            for(int i=0; i<vCollatralM.size(); i++){
                //collateral_id = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName("COLLATERAL_ID");
            	ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
            	collateral_id = generatorManager.generateUniqueIDByName(EJBConstant.COLLATERAL_ID);
            	collatralM = (CollateralDataM)vCollatralM.elementAt(i);
                insertORIG_MG_COLLATERAL(collatralM, appRecordID, collateral_id, username, collatralM.getCreateDate());
                ORIGDAOFactory.getOrigMGCollateralAddressDAO().createCollateralAddressM(collatralM.getAddress(), collateral_id, username);
                ORIGDAOFactory.getOrigMGCollateralFeeInfoDAO().createCollateralFeeInfoM(collatralM.getFeeInformationVect(), collateral_id, username);
                ORIGDAOFactory.getOrigMGAppraisalDAO().createAppraisalDataM(collatralM.getAppraisal(), collateral_id, username);
            }
        }
        
    }
    
    private void insertORIG_MG_COLLATERAL(CollateralDataM collatralM, String appRecordID,String collateral_id, String username, Timestamp createDate)throws OrigApplicationMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_MG_COLLATERAL ");
			sql.append("( APPLICATION_RECORD_ID, SEQ, COLLATERAL_TYPE, PROPERTY_SUB_TYPE, ACCREDITED_PROPERTY, WITH_UNDER_TAKING, AREA, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, MG_COLLATERAL_ID, PROPERTY_TYPE, PROPERTY_DEVELOPERS) ");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?,SYSDATE ,?,?,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appRecordID);
			ps.setInt(2, collatralM.getSeq());
			ps.setString(3, collatralM.getCollateralType());
			ps.setString(4, collatralM.getPropertySubType());
			ps.setString(5, collatralM.getAccreditedProperty());
			ps.setString(6, collatralM.getWithUndertaking());
			ps.setBigDecimal(7, collatralM.getArea());
			ps.setString(8, username);
			ps.setString(9, username);
			ps.setString(10, collateral_id);
			ps.setString(11, collatralM.getPropertyType());
			ps.setString(12, collatralM.getPropertyDevelopers());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage(),e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
    }
    
    public void updateCollateralM(Vector vCollatralM, String appRecordID, String username) throws OrigApplicationMException {
		double returnRows = 0;
		try {
		    ORIGDAOFactory.getOrigMGCollateralFeeInfoDAO().deleteAllCollateralFeeInfoM(appRecordID);
		    ORIGDAOFactory.getOrigMGCollateralAddressDAO().deleteAllCollateralAddressM(appRecordID);
		    ORIGDAOFactory.getOrigMGAppraisalDAO().deleteAllAppraisalDataM(appRecordID);
			deleteORIG_MG_COLLATERAL(appRecordID);
			createCollateralM(vCollatralM, appRecordID, username);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}
	}

    private void deleteORIG_MG_COLLATERAL(String appRecordID)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_MG_COLLATERAL ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appRecordID);
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationMException(e.getMessage());
			}
		}

	}
    
    public Vector loadCollateralM(String applicationRecordId)throws OrigApplicationMException {
		try {
			Vector result = selectORIG_MG_COLLATERAL(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectORIG_MG_COLLATERAL(String applicationRecordId) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, SEQ, COLLATERAL_TYPE, PROPERTY_SUB_TYPE, ACCREDITED_PROPERTY, WITH_UNDER_TAKING, AREA, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, MG_COLLATERAL_ID, PROPERTY_TYPE, PROPERTY_DEVELOPERS ");
			sql.append(" FROM ORIG_MG_COLLATERAL WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vCollatralM = new Vector();
			
			while (rs.next()) {
			    CollateralDataM collatralM = new CollateralDataM();
			    collatralM.setSeq(rs.getInt("SEQ"));
			    collatralM.setCollateralType(rs.getString("COLLATERAL_TYPE"));
			    log.debug(">>> rs.getString(COLLATERAL_TYPE)="+rs.getString("COLLATERAL_TYPE"));
			    collatralM.setPropertySubType(rs.getString("PROPERTY_SUB_TYPE"));
			    collatralM.setAccreditedProperty(rs.getString("ACCREDITED_PROPERTY"));
			    collatralM.setWithUndertaking(rs.getString("WITH_UNDER_TAKING"));
			    collatralM.setArea(rs.getBigDecimal("AREA"));
			    collatralM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
			    collatralM.setCreateBy(rs.getString("CREATE_BY"));
			    collatralM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			    collatralM.setUpdateBy(rs.getString("UPDATE_BY"));
			    collatralM.setCollateral_id(rs.getString("MG_COLLATERAL_ID"));
			    collatralM.setPropertyType(rs.getString("PROPERTY_TYPE"));
			    collatralM.setPropertyDevelopers(rs.getString("PROPERTY_DEVELOPERS"));
			    
			    collatralM.setAddress(ORIGDAOFactory.getOrigMGCollateralAddressDAO().loadCollateralAddressM(rs.getString("MG_COLLATERAL_ID")));
			    collatralM.setFeeInformationVect(ORIGDAOFactory.getOrigMGCollateralFeeInfoDAO().loadCollateralFeeInfoM(rs.getString("MG_COLLATERAL_ID")));
			    collatralM.setAppraisal(ORIGDAOFactory.getOrigMGAppraisalDAO().loadAppraisalDataM(rs.getString("MG_COLLATERAL_ID")));
			    
			    vCollatralM.add(collatralM);
			}
			return vCollatralM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

    
}
