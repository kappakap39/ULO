/*
 * Created on Jul 21, 2008
 * Created by Avalant
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

import com.eaf.orig.shared.dao.exceptions.OrigInstallmentMException;
import com.eaf.orig.shared.model.InstallmentDataM;
 

/**
 * @author Avalant
 *
 * Type: OrigInstallmentMDAOImpl
 */
public class OrigInstallmentMDAOImpl extends OrigObjectDAO implements OrigInstallmentMDAO {
	private static Logger log = Logger.getLogger(OrigInstallmentMDAOImpl.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigInstallmentMDAO#createModelOrigInstallmentM(com.eaf.orig.shared.model.InstallmentDataM)
     */
    public void createModelOrigInstallmentM(InstallmentDataM prmInstallmentDataM) throws OrigInstallmentMException {
    	try {
    	    createTableORIG_INSTALLMENT(prmInstallmentDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		}
        
    }

    /**
	 * @param prmInstallmentDataM
	 */
	private void createTableORIG_INSTALLMENT(InstallmentDataM prmInstallmentDataM)
			throws OrigInstallmentMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_INSTALLMENT ");
			sql
					.append("( APPLICATION_RECORD_ID,INSTALLMENT_SEQ,INSTALLMENT_FROM ,INSTALLMENT_TO,TERM_DURATION ");
			sql.append( " ,INSTALLMENT_AMT,INSTALLMENT_VAT,AMOUNT,TOTAL_INSTALLMENT_AMT,CREATE_BY ");
			sql.append("  ,CREATE_DATE,UPDATE_BY,UPDATE_DATE)");
			sql.append(" VALUES(?,?,?,?,?   ,?,?,?,?,?  ,SYSDATE,?,SYSDATE ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
		    log.debug("prmInstallmentDataM.getInstallmentSEQ = "+prmInstallmentDataM.getSeq());
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmInstallmentDataM.getApplicationRecordId());
			ps.setInt(2, prmInstallmentDataM.getSeq());
			ps.setInt(3, prmInstallmentDataM.getInstallmentForm());
			ps.setInt(4, prmInstallmentDataM.getInstallmentTo());
			ps.setInt(5, prmInstallmentDataM.getTermDuration());
			ps.setBigDecimal(6, prmInstallmentDataM.getInstallmentAmount());
			ps.setBigDecimal(7, prmInstallmentDataM.getInstallmentVat());
			ps.setBigDecimal(8, prmInstallmentDataM.getAmount());
			ps.setBigDecimal(9, prmInstallmentDataM.getInstallmentTotal());
			ps.setString(10, prmInstallmentDataM.getCreateBy());
			//ps.setTimestamp(11, prmInstallmentDataM.getCreateDate());
			ps.setString(11, prmInstallmentDataM.getUpdateBy());
			//ps.setString(13, prmInstallmentDataM.getRole());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigInstallmentMDAO#deleteModelOrigInstallmentM(com.eaf.orig.shared.model.InstallmentDataM)
     */
    public void deleteModelOrigInstallmentM(InstallmentDataM prmInstallmentDataM) throws OrigInstallmentMException {
    	try {
			deleteTableORIG_INSTALLMENT(prmInstallmentDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		}
        
    }
	/**
	 * @param prmInstallmentDataM
	 */
	private void deleteTableORIG_INSTALLMENT(InstallmentDataM prmInstallmentDataM)
			throws OrigInstallmentMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_INSTALLMENT ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND INSTALLMENT_SEQ=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmInstallmentDataM.getApplicationRecordId());
			ps.setInt(2, prmInstallmentDataM.getSeq());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigInstallmentMException(e.getMessage());
			}
		}

	}

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigInstallmentMDAO#loadModelOrigInstallmentM(java.lang.String)
     */
    public Vector loadModelOrigInstallmentM(String applicationRecordId) throws OrigInstallmentMException {
    	try {
			Vector result = selectTableORIG_INSTALLMENT(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		}
    }
	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_INSTALLMENT(String applicationRecordId)
			throws OrigInstallmentMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT  APPLICATION_RECORD_ID,INSTALLMENT_SEQ,INSTALLMENT_FROM ,INSTALLMENT_TO,TERM_DURATION");
			sql.append(" ,INSTALLMENT_AMT,INSTALLMENT_VAT,AMOUNT,TOTAL_INSTALLMENT_AMT ");
		    sql.append("  ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE ");
			sql.append(" FROM ORIG_INSTALLMENT WHERE APPLICATION_RECORD_ID = ?   ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vInstallment= new Vector();
			while (rs.next()) {
				InstallmentDataM installmentDataM = new InstallmentDataM();
				installmentDataM.setApplicationRecordId(applicationRecordId);
				installmentDataM.setSeq(rs.getInt(2));
				installmentDataM.setInstallmentForm(rs.getInt(3));
				installmentDataM.setInstallmentTo(rs.getInt(4));
				installmentDataM.setTermDuration(rs.getInt(5));
				installmentDataM.setInstallmentAmount(rs.getBigDecimal(6));
				installmentDataM.setInstallmentVat(rs.getBigDecimal(7));
				installmentDataM.setAmount(rs.getBigDecimal(8));
				installmentDataM.setInstallmentTotal(rs.getBigDecimal(9));
				installmentDataM.setCreateBy(rs.getString(10));
				installmentDataM.setCreateDate(rs.getTimestamp(11));
				installmentDataM.setUpdateBy(rs.getString(12));
				installmentDataM.setUpdateDate(rs.getTimestamp(13));
				vInstallment.add(installmentDataM);
			}
			return vInstallment;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
	}
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigInstallmentMDAO#saveUpdateModelOrigInstallmentM(com.eaf.orig.shared.model.InstallmentDataM)
     */
    public void saveUpdateModelOrigInstallmentM(InstallmentDataM prmInstallmentDataM) throws OrigInstallmentMException {
        double returnRows = 0;
		try {
			returnRows = updateTableORIG_INSTALLMENT(prmInstallmentDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_INSTALLMENT then call Insert method");
				createTableORIG_INSTALLMENT(prmInstallmentDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		}
        
    }
    /**
	 * @param prmInstallmentDataM
	 * @return
	 */
	private double updateTableORIG_INSTALLMENT(InstallmentDataM prmInstallmentDataM)
			throws OrigInstallmentMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_INSTALLMENT ");
			sql
					.append(" SET  INSTALLMENT_FROM=? ,INSTALLMENT_TO=?,TERM_DURATION=?,INSTALLMENT_AMT=?,INSTALLMENT_VAT=?,AMOUNT=?,TOTAL_INSTALLMENT_AMT=?  ,UPDATE_BY=?,UPDATE_DATE=SYSDATE ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND INSTALLMENT_SEQ=? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setInt(1, prmInstallmentDataM.getInstallmentForm());
			ps.setInt(2, prmInstallmentDataM.getInstallmentTo());
			ps.setInt(3, prmInstallmentDataM.getTermDuration());
			ps.setBigDecimal(4, prmInstallmentDataM.getInstallmentAmount());
			ps.setBigDecimal(5, prmInstallmentDataM.getInstallmentVat());
			ps.setBigDecimal(6, prmInstallmentDataM.getAmount());
			ps.setBigDecimal(7, prmInstallmentDataM.getInstallmentTotal());
			ps.setString(8, prmInstallmentDataM.getUpdateBy());
			ps.setString(9, prmInstallmentDataM.getApplicationRecordId());
			ps.setInt(10, prmInstallmentDataM.getSeq());		 			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigInstallmentMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigInstallmentMDAO#deleteNotInKeyTableORIG_Installment(java.util.Vector, java.lang.String)
     */
    public void deleteNotInKeyTableORIG_Installment(Vector vOrigInstallment, String appRecordID) throws OrigInstallmentMException {
    	PreparedStatement ps = null;
		String cmpCode = null;
		Connection conn = null;
        try {
            // conn = Get Connection
            conn = getConnection();

            StringBuffer sql = new StringBuffer("");
            sql.append("DELETE FROM ORIG_INSTALLMENT ");
            sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
            
            if ((vOrigInstallment != null) && (vOrigInstallment.size() != 0)) {
                sql.append(" AND INSTALLMENT_SEQ NOT IN ( ");
                InstallmentDataM installMentDataM;
                for (int i = 0; i < vOrigInstallment.size(); i++) {
                    installMentDataM = (InstallmentDataM) vOrigInstallment.get(i);
                	logger.debug("installMentDataM.getSeq() = "+installMentDataM.getSeq());
                    sql.append(" " + installMentDataM.getSeq() + " , ");
                }
                
                if (sql.toString().trim().endsWith(",")) {
                    sql.delete(sql.toString().lastIndexOf(","), sql.toString().length());
                }

                sql.append(" ) ");
            }

            String dSql = String.valueOf(sql);
            logger.debug("SQL = "+dSql+ ": appRecordID ="+appRecordID);
            ps = conn.prepareStatement(dSql);

            ps.setString(1, appRecordID);
            //ps.setString(2, roleID);
            
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
        	logger.error("ERROR >> ",e);
			throw new OrigInstallmentMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigInstallmentMException(e.getMessage());
			}
		}
        
    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.dao.OrigInstallmentMDAO#deleteTableORIG_INSTALLMENT(java.lang.String)
     */
    public void deleteTableORIG_INSTALLMENT(String appRecordID) throws OrigInstallmentMException {
        PreparedStatement ps = null;
        Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_INSTALLMENT ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appRecordID);
			ps.executeUpdate();
		} catch (Exception e) {
			logger.error("ERROR >> ",e);
			throw new OrigInstallmentMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.error("Close Connection Error >> ", e);
				throw new OrigInstallmentMException(e.getMessage());
			}
		}

	//}
        
    }

}
