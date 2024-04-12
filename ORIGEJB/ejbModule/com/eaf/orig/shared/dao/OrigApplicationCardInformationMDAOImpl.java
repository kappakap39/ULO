/*
 * Created on Dec 18, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.eaf.orig.shared.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.dao.exceptions.OrigApplicationCardInformationMException;
import com.eaf.orig.shared.ejb.ORIGGeneratorManager;
import com.eaf.orig.shared.model.CardInformationDataM;
import com.eaf.orig.shared.service.ORIGEJBService;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class OrigApplicationCardInformationMDAOImpl extends OrigObjectDAO implements OrigApplicationCardInformationMDAO {
	private static Logger log = Logger.getLogger(OrigApplicationCardInformationMDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigApplicationCardInformationMDAO#createModelOrigApplicationCardInformationDataM(com.eaf.orig.shared.model.CardInformationDataM)
	 */
	public void createModelOrigApplicationCardInformationDataM(CardInformationDataM prmCardInformationDataM) throws OrigApplicationCardInformationMException {
		// TODO Auto-generated method stub
		try {
			if(prmCardInformationDataM.getCardNo()==null||"".equals(prmCardInformationDataM.getCardNo())){
				//String cardNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getCardNo(prmCardInformationDataM);
				//prmCardInformationDataM.setCardNo(cardNo);
				ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
				String cardNo = generatorManager.generateCardNo(prmCardInformationDataM);
				prmCardInformationDataM.setCardNo(cardNo);
			}
			createTableORIG_APPLICATION_CARD_INFORMATION(prmCardInformationDataM);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigApplicationCardInformationMDAO#deleteModelOrigApplicationCardInformationDataM(com.eaf.orig.shared.model.CardInformationDataM)
	 */
	public void deleteModelOrigApplicationCardInformationDataM(CardInformationDataM prmCardInformationDataM) throws OrigApplicationCardInformationMException {
		// TODO Auto-generated method stub
		try {
			deleteTableORIG_APPLICATION_CARD_INFORMATION(prmCardInformationDataM);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigApplicationCardInformationMDAO#deleteNotInKeyTableORIG_APPLICATION_CARD_INFORMATION(java.util.Vector, java.lang.String)
	 */
	public void deleteNotInKeyTableORIG_APPLICATION_CARD_INFORMATION(Vector cardInfoVect, String appRecordID) throws OrigApplicationCardInformationMException {
		// TODO Auto-generated method stub
		try {
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigApplicationCardInformationMDAO#loadModelOrigApplicationCardInformationDataM(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Vector loadModelOrigApplicationCardInformationDataM(String applicationRecordId, String providerUrlEXT, String jndiEXT) throws OrigApplicationCardInformationMException {
		// TODO Auto-generated method stub
		try {
			return selectTableORIG_APPLICATION_CARD_INFORMATION(applicationRecordId, providerUrlEXT, jndiEXT);
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		}
	}
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigApplicationCardInformationMDAO#saveUpdateModelOrigApplicationCardInformationDataM(com.eaf.orig.shared.model.CardInformationDataM)
	 */
	public void saveUpdateModelOrigApplicationCardInformationDataM(CardInformationDataM prmCardInformationDataM) throws OrigApplicationCardInformationMException {
		// TODO Auto-generated method stub
		try {
			double returnRows = 0;
			returnRows = updateTableORIG_APPLICATION_CARD_INFORMATION(prmCardInformationDataM);
			if (returnRows == 0) {
				log.debug("New record then can't update record in table ORIG_CARD then call Insert method");
				if(prmCardInformationDataM.getCardNo()==null||"".equals(prmCardInformationDataM.getCardNo())){
					//String cardNo = ORIGDAOFactory.getUniqueIDGeneratorDAO2().getCardNo(prmCardInformationDataM);
					//prmCardInformationDataM.setCardNo(cardNo);
					ORIGGeneratorManager generatorManager = ORIGEJBService.getGeneratorManager();
					String cardNo = generatorManager.generateCardNo(prmCardInformationDataM);
					prmCardInformationDataM.setCardNo(cardNo);
				}
				createTableORIG_APPLICATION_CARD_INFORMATION(prmCardInformationDataM);
			}
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		}
	}

	private void createTableORIG_APPLICATION_CARD_INFORMATION(CardInformationDataM prmCardInformationDataM) throws OrigApplicationCardInformationMException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO ORIG_CARD ");
			sql.append("( APPLICATION_RECORD_ID, CARD_SEQ, CARD_NO, APPLICATION_TYPE, CARD_TYPE");	//1,2,3,4,5
			sql.append(", CARD_STATUS, CARD_FACE, EMBOSS_NAME ,VALID_FROM_DATE, VALID_THRU_DATE");	//6,7,8,9,10
			sql.append(", UPDATE_BY, UPDATE_DATE, CREATE_BY, CREATE_DATE, ID_NO) ");				//11,12,13,14,15
			sql.append("VALUES(?,?,?,?,?,?,?,?,SYSDATE,SYSDATE,?,SYSDATE,?,SYSDATE,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmCardInformationDataM.getApplication_record_id());					//1
			ps.setInt(2, prmCardInformationDataM.getCardSeq());										//2
			ps.setString(3, prmCardInformationDataM.getCardNo());									//3
			ps.setString(4, prmCardInformationDataM.getApplicationType());							//4
			ps.setString(5, prmCardInformationDataM.getCardType());									//5
			ps.setString(6, prmCardInformationDataM.getCardStatus());								//6
			ps.setString(7, prmCardInformationDataM.getCardFace());									//7
			ps.setString(8, prmCardInformationDataM.getEmbossName());								//8
			ps.setString(9, prmCardInformationDataM.getUpdatedBy());								//11
			ps.setString(10, prmCardInformationDataM.getCreatedBy());								//13
			ps.setString(11,prmCardInformationDataM.getIdNo());										//15
			ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationCardInformationMException(e.getMessage());
			}
		}
	}
	
	private void deleteTableORIG_APPLICATION_CARD_INFORMATION(CardInformationDataM prmCardInformationDataM) throws OrigApplicationCardInformationMException {
		PreparedStatement ps = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("DELETE ORIG_CARD ");
			sql.append("WHERE APPLICATION_RECORD_ID = ? AND CARD_SEQ=? AND ID_NO=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmCardInformationDataM.getApplication_record_id());
			ps.setInt(2, prmCardInformationDataM.getCardSeq());
			ps.setString(3, prmCardInformationDataM.getIdNo());
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationCardInformationMException(e.getMessage());
			}
		}

	}

	private Vector selectTableORIG_APPLICATION_CARD_INFORMATION(String applicationRecordId, String providerUrlEXT, String jndiEXT) throws OrigApplicationCardInformationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT * ");
			sql.append("FROM ORIG_CARD WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();
			Vector vt = new Vector();
//			int seq = 0;
			while (rs.next()) {
				CardInformationDataM prmCardInformationDataM = new CardInformationDataM();
				prmCardInformationDataM.setApplication_record_id(rs.getString("APPLICATION_RECORD_ID"));
				prmCardInformationDataM.setApplicationType(rs.getString("APPLICATION_TYPE"));
				prmCardInformationDataM.setCardFace(rs.getString("CARD_FACE"));
				prmCardInformationDataM.setCardNo(rs.getString("CARD_NO"));
				prmCardInformationDataM.setCardSeq(rs.getInt("CARD_SEQ"));
				prmCardInformationDataM.setCardStatus(rs.getString("CARD_STATUS"));
				prmCardInformationDataM.setCardType(rs.getString("CARD_TYPE"));
				prmCardInformationDataM.setCreatedBy(rs.getString("CREATE_BY"));
				prmCardInformationDataM.setCreatedDate(rs.getTimestamp("CREATE_DATE"));
				prmCardInformationDataM.setEmbossName(rs.getString("EMBOSS_NAME"));
				prmCardInformationDataM.setIdNo(rs.getString("ID_NO"));
				prmCardInformationDataM.setUpdatedBy(rs.getString("UPDATE_BY"));
				prmCardInformationDataM.setUpdatedDate(rs.getTimestamp("UPDATE_DATE"));
				prmCardInformationDataM.setValidFromDate(rs.getTimestamp("VALID_FROM_DATE"));
				prmCardInformationDataM.setValidThruDate(rs.getTimestamp("VALID_THRU_DATE"));
				vt.add(prmCardInformationDataM);
			}
			
			return vt;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationCardInformationMException(e.getMessage());
			}
		}
	}

	private double updateTableORIG_APPLICATION_CARD_INFORMATION(CardInformationDataM prmCardInformationDataM) throws OrigApplicationCardInformationMException {
		double returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE ORIG_CARD ");
			sql.append("SET APPLICATION_RECORD_ID=?, CARD_SEQ=?, CARD_NO=?, APPLICATION_TYPE=?, CARD_TYPE=?");	//1,2,3,4,5
			sql.append(", CARD_STATUS=?, CARD_FACE=?, EMBOSS_NAME=?,VALID_FROM_DATE=?, VALID_THRU_DATE=?");		//6,7,8,9,10
			sql.append(", UPDATE_BY=?, UPDATE_DATE=?, CREATE_BY=?, CREATE_DATE=?, ID_NO=? ");					//11,12,13,14,15
			sql.append("WHERE APPLICATION_RECORD_ID = ? AND CARD_SEQ=? AND ID_NO=? ");							//16,17,18
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, prmCardInformationDataM.getApplication_record_id());					//1
			ps.setInt(2, prmCardInformationDataM.getCardSeq());										//2
			ps.setString(3, prmCardInformationDataM.getCardNo());									//3
			ps.setString(4, prmCardInformationDataM.getApplicationType());							//4
			ps.setString(5, prmCardInformationDataM.getCardType());									//5
			ps.setString(6, prmCardInformationDataM.getCardStatus());								//6
			ps.setString(7, prmCardInformationDataM.getCardFace());									//7
			ps.setString(8, prmCardInformationDataM.getEmbossName());								//8
			ps.setTimestamp(9, prmCardInformationDataM.getValidFromDate());							//9
			ps.setTimestamp(10, prmCardInformationDataM.getValidThruDate());						//10
			ps.setString(11, prmCardInformationDataM.getUpdatedBy());								//11
			ps.setTimestamp(12, prmCardInformationDataM.getUpdatedDate());							//12
			ps.setString(13, prmCardInformationDataM.getCreatedBy());								//13
			ps.setTimestamp(14, prmCardInformationDataM.getCreatedDate());							//14
			ps.setString(15,prmCardInformationDataM.getIdNo());										//15
			ps.setString(16, prmCardInformationDataM.getApplication_record_id());					//16
			ps.setInt(17, prmCardInformationDataM.getCardSeq());									//17
			ps.setString(18,prmCardInformationDataM.getIdNo());										//18
			log.debug("prmCardInformationDataM.getApplication_record_id() " + prmCardInformationDataM.getApplication_record_id());
			log.debug("prmCardInformationDataM.getCardSeq() " + prmCardInformationDataM.getCardSeq());
			log.debug("prmCardInformationDataM.getIdNo() " + prmCardInformationDataM.getIdNo());
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new OrigApplicationCardInformationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new OrigApplicationCardInformationMException(e.getMessage());
			}
		}
		return returnRows;
	}
}
