/*
 * Created on Oct 1, 2007
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

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.exceptions.OrigProposalMException;
import com.eaf.orig.shared.model.ProposalDataM;

/**
 * @author Baworn Srisatjalertwaja
 * 
 * Type: OrigProposalMDAOImpl
 */
public class OrigProposalMDAOImpl extends OrigObjectDAO implements
	OrigProposalMDAO {
	private static Logger log = Logger.getLogger(OrigProposalMDAOImpl.class);

	/**
	 *  
	 */
	public OrigProposalMDAOImpl() {
		super();
		
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigProposalMDAO#createModelOrigProposalM(com.eaf.orig.shared.model.ProposalDataM)
	 */
	public void createModelOrigProposalM(ProposalDataM prmProposalDataM)
			throws OrigProposalMException {
		try {
			createTableORIG_PROPOSAL(prmProposalDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigProposalMException(e.getMessage());
		}

	}
	/**
	 * @param prmProposalDataM
	 * @throws OrigProposalMException
	 */
	private void createTableORIG_PROPOSAL(ProposalDataM prmProposalDataM)
			throws OrigProposalMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_PROPOSAL ");
			sql.append("(APPLICATION_RECORD_ID, CLIENT_GROUP,FINAL_CREDIT_LIMIT,CREDIT_EXPIRY_DATE");
			sql.append(",CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY)");

			sql.append(" VALUES(?,?,?,?,SYSDATE,?,SYSDATE,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmProposalDataM.getAppRecID());
			ps.setString(2, prmProposalDataM.getClientGroup());
			ps.setBigDecimal(3, prmProposalDataM.getFinalCreditLimit());
			ps.setDate(4, this.parseDate(prmProposalDataM.getProposalExpireDate()));
			ps.setString(5, prmProposalDataM.getCreateBy());
			ps.setString(6, prmProposalDataM.getUpdateBy());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigProposalMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigProposalMDAO#deleteModelOrigProposalM(com.eaf.orig.shared.model.ProposalDataM)
	 */
	public void deleteModelOrigProposalM(ProposalDataM prmProposalDataM)
			throws OrigProposalMException {
		try {
			deleteTableORIG_PROPOSAL(prmProposalDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigProposalMException(e.getMessage());
		}

	}

	/**
	 * @param prmProposalDataM
	 * @throws OrigProposalMException
	 */
	private void deleteTableORIG_PROPOSAL(ProposalDataM prmProposalDataM)
			throws OrigProposalMException {
		PreparedStatement ps = null;		
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_PROPOSAL ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmProposalDataM.getAppRecID());
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigProposalMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigProposalMException(e.getMessage());
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigProposalMDAO#loadModelOrigProposalM(java.lang.String)
	 */
	public ProposalDataM loadModelOrigProposalM(String applicationRecordId)
			throws OrigProposalMException {
		try {
			ProposalDataM result = selectTableORIG_PROPOSAL(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigProposalMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 * @throws OrigProposalMException
	 */
	private ProposalDataM selectTableORIG_PROPOSAL(String applicationRecordId)
			throws OrigProposalMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ProposalDataM proposalDataM = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_RECORD_ID, CLIENT_GROUP,FINAL_CREDIT_LIMIT,CREDIT_EXPIRY_DATE");
			sql.append(",CREATE_DATE,CREATE_BY,UPDATE_DATE,UPDATE_BY");
			sql.append(" FROM ORIG_PROPOSAL WHERE APPLICATION_RECORD_ID = ?  ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			if (rs.next()) {
				proposalDataM = new ProposalDataM();
				proposalDataM.setAppRecID(rs.getString(1));
				proposalDataM.setClientGroup(rs.getString(2));
				proposalDataM.setFinalCreditLimit(rs.getBigDecimal(3));
				proposalDataM.setProposalExpireDate(rs.getDate(4));
				proposalDataM.setCreateDate(rs.getTimestamp(5));
				proposalDataM.setCreateBy(rs.getString(6));
				proposalDataM.setUpdateDate(rs.getTimestamp(7));
				proposalDataM.setUpdateBy(rs.getString(8));
			}
			return proposalDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigProposalMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigProposalMDAO#saveUpdateModelOrigProposalM(com.eaf.orig.shared.model.ProposalDataM)
	 */
	public void saveUpdateModelOrigProposalM(ProposalDataM prmProposalDataM)
			throws OrigProposalMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_PROPOSAL(prmProposalDataM);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_PROPOSAL then call Insert method");
				createTableORIG_PROPOSAL(prmProposalDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigProposalMException(e.getMessage());
		}

	}

	/**
	 * @param prmProposalDataM
	 * @return
	 * @throws OrigProposalMException
	 */
	private double updateTableORIG_PROPOSAL(ProposalDataM prmProposalDataM)
			throws OrigProposalMException {
			double returnRows = 0;
			PreparedStatement ps = null;
			Connection conn = null;
			try {
				//conn = Get Connection
				conn = getConnection();
				StringBuffer sql = new StringBuffer("");
				sql.append("UPDATE ORIG_PROPOSAL ");
				sql.append(" SET CLIENT_GROUP=?,FINAL_CREDIT_LIMIT=?,CREDIT_EXPIRY_DATE=?,UPDATE_BY=?,UPDATE_DATE=SYSDATE,CREATE_BY=?,CREATE_DATE=? ");
				sql.append(" WHERE APPLICATION_RECORD_ID = ? ");
				String dSql = String.valueOf(sql);
				log.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
	            ps.setString(1,prmProposalDataM.getClientGroup());
	            ps.setBigDecimal(2,prmProposalDataM.getFinalCreditLimit());
	            ps.setDate(3,this.parseDate(prmProposalDataM.getProposalExpireDate()));
	            ps.setString(4,prmProposalDataM.getUpdateBy());
	            ps.setString(5,prmProposalDataM.getCreateBy());
	            ps.setDate(6,this.parseDate(prmProposalDataM.getCreateDate()));
	            ps.setString(7,prmProposalDataM.getAppRecID());
				returnRows = ps.executeUpdate();
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigProposalMException(e.getMessage());
			} finally {
				try {
					closeConnection(conn, ps);
				} catch (Exception e) {
					log.fatal(e.getLocalizedMessage());
				}
			}
			return returnRows;
		}

}
