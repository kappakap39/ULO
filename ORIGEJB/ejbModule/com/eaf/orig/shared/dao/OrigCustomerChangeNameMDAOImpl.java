/*
 * Created on Sep 27, 2007
 * Created by Sankom Sanpunya
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
import java.text.SimpleDateFormat;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigCustomerAddressMException;
import com.eaf.orig.shared.dao.exceptions.OrigCustomerChangeNameMException;
import com.eaf.orig.shared.model.ChangeNameDataM;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigCustomerChangeNameMDAOImpl
 */
public class OrigCustomerChangeNameMDAOImpl extends OrigObjectDAO implements
		OrigCustomerChangeNameMDAO {
	private static Logger log = Logger.getLogger(OrigCustomerChangeNameMDAOImpl.class);

	/**
	 *  
	 */
	public OrigCustomerChangeNameMDAOImpl() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerChangeNameMDAO#createModelOrigCustomerChangeNameM(com.eaf.orig.shared.model.ChangeNameDataM)
	 */
	public void createModelOrigCustomerChangeNameM(
			ChangeNameDataM prmOrigchaneNameDataM)
			throws OrigCustomerChangeNameMException {
		try {
			createTableORIG_CUSTOMER_CHANGE_NAME(prmOrigchaneNameDataM, "");
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		}

	}

	/**
	 * @param prmOrigchaneNameDataM
	 */
	private void createTableORIG_CUSTOMER_CHANGE_NAME(
			ChangeNameDataM prmOrigchaneNameDataM, String idNO)
			throws OrigCustomerChangeNameMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO CUSTOMER_CHANGE_NAME_HISTORY ");
			sql.append("( IDNO,SEQ,CHGDTE,OLDNME,OLDSURN");
			sql.append(" ,NEWNME,NEWSURN,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY)");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,SYSDATE,?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, idNO);
			ps.setInt(2, prmOrigchaneNameDataM.getSeq());
			ps.setDate(3, this.parseDate(prmOrigchaneNameDataM.getChangeDate()));
			ps.setString(4, prmOrigchaneNameDataM.getOldName());
			ps.setString(5, prmOrigchaneNameDataM.getOldSurName());
			
			ps.setString(6, prmOrigchaneNameDataM.getNewName());
			ps.setString(7, prmOrigchaneNameDataM.getNewSurname());
			ps.setString(8, prmOrigchaneNameDataM.getCreateBy());
			ps.setString(9, prmOrigchaneNameDataM.getUpdateBy());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerChangeNameMDAO#deleteModelOrigCustomerChangeNameM(com.eaf.orig.shared.model.ChangeNameDataM)
	 */
	public void deleteModelOrigCustomerChangeNameM(
			ChangeNameDataM prmOrigchaneNameDataM)
			throws OrigCustomerChangeNameMException {
		try {
			deleteTableORIG_CUSTOMER_CHANGE_NAME(prmOrigchaneNameDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		}

	}

	/**
	 * @param prmOrigchaneNameDataM
	 */
	private void deleteTableORIG_CUSTOMER_CHANGE_NAME(
			ChangeNameDataM prmOrigchaneNameDataM)
			throws OrigCustomerChangeNameMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CUSTOMER_CHANGE_NAME ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE=? AND IDNO=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmOrigchaneNameDataM.getApplicationRecordId());
			ps.setString(2, prmOrigchaneNameDataM.getCmpCode());
			ps.setString(3, prmOrigchaneNameDataM.getIdNo());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerChangeNameMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerChangeNameMDAO#loadModelOrigCustomerChangeNameM(java.lang.String,
	 *      int)
	 */
	public Vector loadModelOrigCustomerChangeNameM(
			String applicationRecordId,String idNo, String providerUrlEXT, String jndiEXT )
			throws OrigCustomerChangeNameMException {
		try {
			Vector result = selectTableORIG_CUSTOMER_CHANGE_NAME(applicationRecordId, idNo, providerUrlEXT, jndiEXT);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_CUSTOMER_CHANGE_NAME(
			String applicationRecordId,String idNO, String providerUrlEXT, String jndiEXT) throws OrigCustomerChangeNameMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT ");
			sql.append(" SEQ,CHGDTE,OLDNME,OLDSURN,NEWNME");
			sql.append(" ,NEWSURN,CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY");
			sql.append(" FROM CUSTOMER_CHANGE_NAME_HISTORY WHERE IDNO = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, idNO);
			
			rs = ps.executeQuery();
			Vector vCustomerChageName=new Vector();
			int seq = 1;
			while (rs.next()) {
				ChangeNameDataM	changeNameDataM = new ChangeNameDataM();
				changeNameDataM.setIdNo(idNO);
				changeNameDataM.setSeq(rs.getInt(1));
				changeNameDataM.setChangeDate(rs.getDate(2));
				changeNameDataM.setOldName(rs.getString(3));
				changeNameDataM.setOldSurName(rs.getString(4));
				changeNameDataM.setNewName(rs.getString(5));
				
				changeNameDataM.setNewSurname(rs.getString(6));			
				changeNameDataM.setCreateDate(rs.getTimestamp(7));
				changeNameDataM.setCreateBy(rs.getString(8));			
				changeNameDataM.setUpdateDate(rs.getTimestamp(9));
				changeNameDataM.setUpdateBy(rs.getString(10));

				seq++;
				logger.debug("seq = "+seq);
				//Load in EXT
				//ApplicationEXTManager applicationEXTManager = ORIGEJBService.getApplicationEXTManager(providerUrlEXT, jndiEXT);
				//changeNameDataM = applicationEXTManager.loadPersonalChangeName(changeNameDataM);
				
				vCustomerChageName.add(changeNameDataM);
			}
			return vCustomerChageName;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigCustomerChangeNameMDAO#saveUpdateModelOrigCustomerChangeNameM(com.eaf.orig.shared.model.ChangeNameDataM)
	 */
	public void saveUpdateModelOrigCustomerChangeNameM(
			ChangeNameDataM prmchaneNameDataM, String idNO)
			throws OrigCustomerChangeNameMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_CUSTOMER_CHANGE_NAME(prmchaneNameDataM, idNO);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_CUSTOMER_CHANGENAME then call Insert method");
				createTableORIG_CUSTOMER_CHANGE_NAME(prmchaneNameDataM, idNO);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		}

	}

	/**
	 * @param prmchaneNameDataM
	 * @return
	 */
	private double updateTableORIG_CUSTOMER_CHANGE_NAME(
			ChangeNameDataM prmChangeNameDataM, String idNO)
			throws OrigCustomerChangeNameMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE CUSTOMER_CHANGE_NAME_HISTORY ");
			sql.append(" SET CHGDTE = ?,OLDNME = ?,OLDSURN = ?,NEWNME = ?,NEWSURN = ?");
			sql.append(" ,UPDATE_DATE = SYSDATE,UPDATE_BY = ?");
			sql.append(" WHERE IDNO = ? AND SEQ = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);			
			ps.setDate(1, this.parseDate(prmChangeNameDataM.getChangeDate()));
			ps.setString(2, prmChangeNameDataM.getOldName());
			ps.setString(3, prmChangeNameDataM.getOldSurName());
			ps.setString(4, prmChangeNameDataM.getNewName());
			ps.setString(5, prmChangeNameDataM.getNewSurname());
			
			ps.setString(6, prmChangeNameDataM.getUpdateBy());
			ps.setString(7, idNO);
			ps.setInt(8, prmChangeNameDataM.getSeq());

			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;

	}
	
	/** delete not in key**/
	public void deleteNotInKeyTableORIG_CUSTOMER_CHANGE_NAME(Vector changeVect, String appRecordID, String cmpCode, String idNo) throws OrigCustomerAddressMException{
		PreparedStatement ps = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_CUSTOMER_CHANGE_NAME");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? AND CMPCDE = ? AND IDNO = ? ");
            
            if ((changeVect != null) && (changeVect.size() != 0)) {
                sql.append(" AND CHGDTE NOT IN ( ");
                ChangeNameDataM changeNameDataM;
                for (int i = 0; i < changeVect.size(); i++) {
                    changeNameDataM = (ChangeNameDataM) changeVect.get(i);
                    logger.debug("datetoString(changeNameDataM.getChangeDate() = "+datetoString(changeNameDataM.getChangeDate()));
                    sql.append(" to_date('"+ datetoString(changeNameDataM.getChangeDate()) + "','dd/MM/yyyy') , ");
                }

                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);

            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            ps.setString(2, cmpCode);
            ps.setString(3, idNo);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigCustomerAddressMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.error("ERROR >> ",e);
				throw new OrigCustomerAddressMException(e.getMessage());
			}
		}
	}
	
	/**
	 * @param prmOrigchaneNameDataM
	 */
	public void deleteTableORIG_CUSTOMER_CHANGE_NAME(String appRecordID, String cmpCode, String idNo) throws OrigCustomerChangeNameMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CUSTOMER_CHANGE_NAME ");
			sql.append(" WHERE CMPCDE = ? AND APPLICATION_RECORD_ID = ? AND IDNO = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cmpCode);
			ps.setString(2, appRecordID);
			ps.setString(3, idNo);
			
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigCustomerChangeNameMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigCustomerChangeNameMException(e.getMessage());
			}
		}

	}
	
}
