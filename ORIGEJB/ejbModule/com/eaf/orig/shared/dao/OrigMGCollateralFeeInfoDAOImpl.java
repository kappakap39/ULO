/*
 * Created on Mar 14, 2010
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;
//import com.eaf.orig.shared.dao.utility.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.key.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.CollateralDataM;
import com.eaf.orig.shared.model.FeeInformationDataM;

/**
 * @author wichaya
 *
 * Type: OrigMGCollateralFeeInfoDAOImpl
 */
public class OrigMGCollateralFeeInfoDAOImpl extends OrigObjectDAO implements OrigMGCollateralFeeInfoDAO {

    private static Logger log = Logger.getLogger(OrigMGCollateralFeeInfoDAOImpl.class);

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGCollateralFeeInfoDAO#createCollateralFeeInfoM(java.util.Vector, java.lang.String, java.lang.String)
     */
    public void createCollateralFeeInfoM(Vector vFeeInfoM, String collateral_id, String username) throws OrigApplicationMException, UniqueIDGeneratorException {
        FeeInformationDataM feeInfoM;
        if(vFeeInfoM!=null && vFeeInfoM.size()>0){
            for(int i=0; i<vFeeInfoM.size(); i++){
                //collateral_id = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName("COLLATERAL_ID");
                feeInfoM = (FeeInformationDataM)vFeeInfoM.elementAt(i);
                insertORIG_MG_FEE_INFO(feeInfoM, collateral_id, username);
            }
        } 

    }
    
    private void insertORIG_MG_FEE_INFO(FeeInformationDataM feeInfoM, String collateral_id, String username)throws OrigApplicationMException{
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_MG_FEE_INFO ");
			sql.append("(MG_COLLATERAL_ID, SEQ, FEE_TYPE, FEE_AMOUNT, FEE_PAYMENT_OPTION, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY) ");
			sql.append(" VALUES(?,?,?,?,? ,SYSDATE,?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, collateral_id);
			ps.setInt(2, feeInfoM.getSeq());
			ps.setString(3, feeInfoM.getFeeType());
			ps.setBigDecimal(4, feeInfoM.getFeeAmount());
			ps.setString(5, feeInfoM.getFeePaymentOption());
			ps.setString(6, username);
			ps.setString(7, username);
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
    }
    
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGCollateralFeeInfoDAO#deleteCollateralFeeInfoM(java.lang.String)
     */
    public void deleteCollateralFeeInfoM(String collatera_id) throws OrigApplicationMException {
        deleteORIG_MG_FEE_INFO(collatera_id);
    }
    
    private void deleteORIG_MG_FEE_INFO(String collatera_id)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_MG_FEE_INFO ");
			sql.append(" WHERE MG_COLLATERAL_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, collatera_id);
			
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
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGCollateralFeeInfoDAO#loadCollateralFeeInfoM(java.lang.String)
     */
    public Vector loadCollateralFeeInfoM(String collatera_id) throws OrigApplicationMException {
        return selectORIG_MG_FEE_INFO(collatera_id);
    }
    
    private Vector selectORIG_MG_FEE_INFO(String collatera_id) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT MG_COLLATERAL_ID, SEQ, FEE_TYPE, FEE_AMOUNT, FEE_PAYMENT_OPTION, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_MG_FEE_INFO WHERE MG_COLLATERAL_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, collatera_id);

			rs = ps.executeQuery();
			Vector vFeeInfoM = new Vector();
			
			while (rs.next()) {
			    FeeInformationDataM feeInfoM = new FeeInformationDataM();
			    feeInfoM.setSeq(rs.getInt("SEQ"));
			    feeInfoM.setFeeType(rs.getString("FEE_TYPE"));
			    feeInfoM.setFeeAmount(rs.getBigDecimal("FEE_AMOUNT"));
			    feeInfoM.setFeePaymentOption(rs.getString("FEE_PAYMENT_OPTION"));
			    vFeeInfoM.add(feeInfoM);
			}
			return vFeeInfoM;
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
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigMGCollateralFeeInfoDAO#updateCollateralFeeInfoM(java.util.Vector, java.lang.String, java.lang.String)
     */
    public void updateCollateralFeeInfoM(Vector vFeeInfoM, String collateral_id, String username) throws OrigApplicationMException, UniqueIDGeneratorException {
        deleteORIG_MG_FEE_INFO(collateral_id);
        createCollateralFeeInfoM(vFeeInfoM, collateral_id, username);

    }
    
    public void deleteAllCollateralFeeInfoM(String appRecID) throws OrigApplicationMException {
        deleteAllORIG_MG_FEE_INFO(appRecID);
    }
    
    private void deleteAllORIG_MG_FEE_INFO(String appRecID)throws OrigApplicationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_MG_FEE_INFO ");
			sql.append(" WHERE MG_COLLATERAL_ID in (Select MG_COLLATERAL_ID from ORIG_MG_COLLATERAL where APPLICATION_RECORD_ID = ?)");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, appRecID);
			
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
}
