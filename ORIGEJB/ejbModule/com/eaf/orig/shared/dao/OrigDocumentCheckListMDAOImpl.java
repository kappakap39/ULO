/*
 * Created on Sep 28, 2007
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
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.dao.exceptions.OrigDocumentCheckListMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Sankom Sanpunya
 * 
 * Type: OrigDocumentCheckListMDAOImpl
 */
public class OrigDocumentCheckListMDAOImpl extends OrigObjectDAO implements
		OrigDocumentCheckListMDAO {
	private static Logger log = Logger
			.getLogger(OrigDocumentCheckListMDAOImpl.class);

	/**
	 *  
	 */
	public OrigDocumentCheckListMDAOImpl() {
		super();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigDocumentCheckListMDAO#createModelOrigDocumentCheckListM(com.eaf.orig.shared.model.DocumentCheckListDataM)
	 */
	public void createModelOrigDocumentCheckListM(
			DocumentCheckListDataM prmDocumentCheckListDataM)
			throws OrigDocumentCheckListMException {
		try {
			//Generate Doc ID;
			//int docId=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.DOC_LIST_ID));
			//prmDocumentCheckListDataM.setDocId(docId);
			ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
			int docId = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.DOC_LIST_ID));
			prmDocumentCheckListDataM.setDocId(docId);
			createTableORIG_DOCUMENT_CHECK_LIST(prmDocumentCheckListDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
		}

	}

	/**
	 * @param prmDocumentCheckListDataM
	 */
	private void createTableORIG_DOCUMENT_CHECK_LIST(
			DocumentCheckListDataM prmDocumentCheckListDataM)
			throws OrigDocumentCheckListMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_DOCUMENT_CHECK_LIST ");
			sql
					.append("( APPLICATION_RECORD_ID,DOC_ID,JOB_STATE  ,APPLICANT_TYPE , DOC_TYPE_ID  ");
			sql
					.append(", DOC_TYPE_DESC,REQUIRED,RECEIVE,ROLE,REMARK,CREATE_DATE,CREATE_BY,WAIVE  )");
			sql.append(" VALUES(?,?,?,?,?  ,?,?,?,?,?   ,SYSDATE  ,?,? ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);			 
			ps.setString(1, prmDocumentCheckListDataM.getApplicationRecordId());			
			ps.setInt(2, prmDocumentCheckListDataM.getDocId());
			ps.setString(3, prmDocumentCheckListDataM.getJobState());
			ps.setString(4, prmDocumentCheckListDataM.getApplicationType());
			ps.setString(5, prmDocumentCheckListDataM.getDocTypeId());
			ps.setString(6, prmDocumentCheckListDataM.getDocTypeDesc());
			ps.setString(7, prmDocumentCheckListDataM.getRequire());
			ps.setString(8, prmDocumentCheckListDataM.getReceive());
			ps.setString(9, prmDocumentCheckListDataM.getRole());
			ps.setString(10, prmDocumentCheckListDataM.getRemark());
			ps.setString(11, prmDocumentCheckListDataM.getCreateBy());
			ps.setString(12, prmDocumentCheckListDataM.getWaive());
			
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigDocumentCheckListMDAO#deleteModelOrigDocumentCheckListM(com.eaf.orig.shared.model.DocumentCheckListDataM)
	 */
	public void deleteModelOrigDocumentCheckListM(
			DocumentCheckListDataM prmDocumentCheckListDataM)
			throws OrigDocumentCheckListMException {
		try {
			deleteTableORIG_DOCUMENT_CHECK_LIST(prmDocumentCheckListDataM);
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
		}

	}

	/**
	 * @param prmDocumentCheckListDataM
	 */
	private void deleteTableORIG_DOCUMENT_CHECK_LIST(
			DocumentCheckListDataM prmDocumentCheckListDataM)
			throws OrigDocumentCheckListMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_DOCUMENT_CHECK_LIST ");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND DOC_ID= ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmDocumentCheckListDataM.getApplicationRecordId());
			ps.setInt(2, prmDocumentCheckListDataM.getDocId());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigDocumentCheckListMException(e.getMessage());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.eaf.orig.share.dao.OrigDocumentCheckListMDAO#loadModelOrigDocumentCheckListM(java.lang.String)
	 */
	public Vector loadModelOrigDocumentCheckListM(String applicationRecordId)
			throws OrigDocumentCheckListMException {
		try {
			Vector result = selectTableORIG_DOCUMENT_CHECK_LIST(applicationRecordId);
			return result;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
		}
	}

	/**
	 * @param applicationRecordId
	 * @return
	 */
	private Vector selectTableORIG_DOCUMENT_CHECK_LIST(
			String applicationRecordId) throws OrigDocumentCheckListMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql
					.append("SELECT   DOC_ID,JOB_STATE  ,APPLICANT_TYPE , DOC_TYPE_ID ,DOC_TYPE_DESC ");
			sql
					.append(",REQUIRED,RECEIVE,ROLE,REMARK,CREATE_DATE,CREATE_BY,WAIVE  ");
			sql
					.append(" FROM ORIG_DOCUMENT_CHECK_LIST WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vCustomerFinanceDataM = new Vector();
			while (rs.next()) {
				DocumentCheckListDataM documentCheckListDataM = new DocumentCheckListDataM();
				documentCheckListDataM
						.setApplicationRecordId(applicationRecordId);
				documentCheckListDataM.setDocId(rs.getInt(1));
				log.debug("rs.getInt(1)="+rs.getInt(1));
				documentCheckListDataM.setJobState(rs.getString(2));
				documentCheckListDataM.setApplicationType(rs.getString(3));
				documentCheckListDataM.setDocTypeId(rs.getString(4));
				documentCheckListDataM.setDocTypeDesc(rs.getString(5));
				documentCheckListDataM.setRequire(rs.getString(6));
				documentCheckListDataM.setReceive(rs.getString(7));
				documentCheckListDataM.setRole(rs.getString(8));
				documentCheckListDataM.setRemark(rs.getString(9));
				documentCheckListDataM.setCreateDate(rs.getTimestamp(10));
				documentCheckListDataM.setCreateBy(rs.getString(11));
				documentCheckListDataM.setWaive(rs.getString(12));
				vCustomerFinanceDataM.add(documentCheckListDataM);
			}
			return vCustomerFinanceDataM;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
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
	 * @see com.eaf.orig.share.dao.OrigDocumentCheckListMDAO#saveUpdateModelOrigDocumentCheckListM(com.eaf.orig.shared.model.DocumentCheckListDataM)
	 */
	public void saveUpdateModelOrigDocumentCheckListM(
			DocumentCheckListDataM prmDocumentCheckListDataM)
			throws OrigDocumentCheckListMException {
		double returnRows = 0;
		try {
			returnRows = updateTableORIG_DOCUMENT_CHECK_LIST(prmDocumentCheckListDataM);
			if (returnRows == 0) {
				log
						.debug("New record then can't update record in table ORIG_DOCUMENT_CHECK_LIST then call Insert method");
				//Generate Doc ID;
				//int docId=Integer.parseInt(ORIGDAOFactory.getUniqueIDGeneratorDAO2().getUniqByName(EJBConstant.DOC_LIST_ID));
				//prmDocumentCheckListDataM.setDocId(docId);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				int docId = Integer.parseInt(generatorManager.generateUniqueIDByName(EJBConstant.DOC_LIST_ID));
				prmDocumentCheckListDataM.setDocId(docId);
				createTableORIG_DOCUMENT_CHECK_LIST(prmDocumentCheckListDataM);
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
		}

	}

	/**
	 * @param prmDocumentCheckListDataM
	 * @return
	 */
	private double updateTableORIG_DOCUMENT_CHECK_LIST(
			DocumentCheckListDataM prmDocumentCheckListDataM)
			throws OrigDocumentCheckListMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_DOCUMENT_CHECK_LIST ");
			sql
					.append(" SET   JOB_STATE =? ,APPLICANT_TYPE=? , DOC_TYPE_ID=? ");
			sql
					.append(" ,DOC_TYPE_DESC =?,REQUIRED =? ,RECEIVE =?,ROLE =?,REMARK=?, WAIVE=?");
			sql.append(" WHERE APPLICATION_RECORD_ID = ? AND DOC_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			logger.debug("prmDocumentCheckListDataM.getApplicationRecordId() = "+prmDocumentCheckListDataM.getApplicationRecordId());
			logger.debug("prmDocumentCheckListDataM.getDocId() = "+prmDocumentCheckListDataM.getDocId());
			ps = conn.prepareStatement(dSql);			
			ps.setString(1, prmDocumentCheckListDataM.getJobState());
			ps.setString(2, prmDocumentCheckListDataM.getApplicationType());
			ps.setString(3, prmDocumentCheckListDataM.getDocTypeId());
			ps.setString(4, prmDocumentCheckListDataM.getDocTypeDesc());
			ps.setString(5, prmDocumentCheckListDataM.getRequire());
			ps.setString(6, prmDocumentCheckListDataM.getReceive());
			ps.setString(7, prmDocumentCheckListDataM.getRole());
			ps.setString(8, prmDocumentCheckListDataM.getRemark());
			ps.setString(9, prmDocumentCheckListDataM.getWaive());
			
			ps.setString(10, prmDocumentCheckListDataM.getApplicationRecordId());
			ps.setInt(11, prmDocumentCheckListDataM.getDocId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigDocumentCheckListMException(e.getMessage());
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
