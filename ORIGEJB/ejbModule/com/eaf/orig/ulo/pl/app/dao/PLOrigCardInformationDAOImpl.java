package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.shared.constant.EJBConstant;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.OrigBusinessClassUtil;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLOrigCardInformationDAOImpl extends OrigObjectDAO implements PLOrigCardInformationDAO {
	
	private static Logger log = Logger.getLogger(PLOrigCardInformationDAOImpl.class);

	@Override
	public void saveUpdateOrigCard(PLCardDataM cardM, String personalID, PLApplicationDataM appM) throws PLOrigApplicationException{		
		try{
			int updateRec = 0;
//			Vector<String> cardIDVect = new Vector<String>();		
			if(null != cardM){
//				OrigBusinessClassUtil busClassUtil = new OrigBusinessClassUtil();			
//				if(OrigUtil.isEmptyString(cardM.getCardId())){
//					String cardId = PLORIGEJBService.getGeneratorManager().generateUniqueIDByName(EJBConstant.CARD_ID);
//					cardM.setCardId(cardId);
//				}
				
//				if(busClassUtil.isContainsBusGroup(appM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
//					updateRec = updateTableOrig_CardWithCardNo(cardM, personalID);
//				}else{
//					updateRec = updateTableOrig_Card(cardM, personalID);
//				}
//				if(updateRec == 0){
//					if(busClassUtil.isContainsBusGroup(appM, OrigConstant.BusGroup.KEC_ICDC_FLOW)){
//						insertTableOrig_CardWithCardNo(cardM, personalID);
//					}else{
//						insertTableOrig_Card(cardM, personalID);
//					}
//				}
				
			}
//			if(!OrigUtil.isEmptyVector(cardIDVect)){
//				DeleteTableORIG_CARD(cardIDVect, personalID);
//			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	public void DeleteTableORIG_CARD(Vector<String> cardIDVect, String personalID)throws PLOrigApplicationException{	
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" DELETE ORIG_CARD ");
			sql.append(" WHERE PERSONAL_ID = ? AND CARD_ID NOT IN ");
			sql.append("( ");
			sql.append(ParameterINVect(cardIDVect));			
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql= "+dSql);
			int index = 1;
			ps = conn.prepareStatement(dSql);
			ps.setString(index++, personalID);			
			PreparedStatementParameter(index, ps, cardIDVect);		
			ps.executeUpdate();

		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
	}
	private int updateTableOrig_Card(PLCardDataM cardM, String personalID) throws PLOrigApplicationException{
		Connection conn = null;
		int updateRec=0;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_CARD ");
			sql.append(" SET APPLICATION_TYPE=?, CARD_TYPE=?, CARD_STATUS=?, CARD_FACE=?, EMBOSS_NAME=? ");
			sql.append(", VALID_FROM_DATE=?, VALID_THRU_DATE=?, UPDATE_BY=?, UPDATE_DATE=SYSDATE, ID_NO=? ");
			sql.append(", REQUEST_CREDIT_LINE=?, CURRENT_CREDIT_LINE=? ");
			sql.append(" WHERE CARD_ID=? AND PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, cardM.getApplicationType());
			ps.setString(index++, cardM.getCardType());
			ps.setString(index++, cardM.getCardStatus());
			ps.setString(index++, cardM.getCardFace());
			ps.setString(index++, cardM.getEmbossName());
			
			ps.setTimestamp(index++, cardM.getValidFromDate());
			ps.setTimestamp(index++, cardM.getValidThruDate());
			ps.setString(index++, cardM.getUpdatedBy());
			ps.setString(index++, cardM.getIdNo());
			
			ps.setBigDecimal(index++, cardM.getReqCreditLine());
			ps.setBigDecimal(index++, cardM.getCurCreditLine());
			
			ps.setString(index++, cardM.getCardId());
			ps.setString(index++, personalID);
			
			updateRec=ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return updateRec;
	}
	
	private int updateTableOrig_CardWithCardNo(PLCardDataM cardM, String personalID) throws PLOrigApplicationException{		
		int updateRec=0;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("UPDATE ORIG_CARD ");
			sql.append(" SET CARD_ID=?, PERSONAL_ID=?, CARD_NO=pka_crypto.encrypt_field(?), APPLICATION_TYPE=?, CARD_TYPE=? ");
			sql.append(", CARD_STATUS=?, CARD_FACE=?, EMBOSS_NAME=?, VALID_FROM_DATE=?, VALID_THRU_DATE=? ");
			sql.append(", UPDATE_BY=?, UPDATE_DATE=SYSDATE, ID_NO=?, REQUEST_CREDIT_LINE=?, CURRENT_CREDIT_LINE=? ");
			sql.append(" WHERE CARD_ID=? AND PERSONAL_ID=?");
			String dSql = String.valueOf(sql);
			
			log.debug("updateTableOrig_CardWithCardNo Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, cardM.getCardId());
			ps.setString(index++, cardM.getPersonalId());
			ps.setString(index++, cardM.getCardNo());
			ps.setString(index++, cardM.getApplicationType());
			ps.setString(index++, cardM.getCardType());
			
			ps.setString(index++, cardM.getCardStatus());
			ps.setString(index++, cardM.getCardFace());
			ps.setString(index++, cardM.getEmbossName());
			ps.setTimestamp(index++, cardM.getValidFromDate());
			ps.setTimestamp(index++, cardM.getValidThruDate());
			
			ps.setString(index++, cardM.getUpdatedBy());
			ps.setString(index++, cardM.getIdNo());
			ps.setBigDecimal(index++, cardM.getReqCreditLine());
			ps.setBigDecimal(index++, cardM.getCurCreditLine());
			
			ps.setString(index++, cardM.getCardId());
			ps.setString(index++, personalID);
			
			updateRec = ps.executeUpdate();
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return updateRec;
	}
	
	private void insertTableOrig_Card(PLCardDataM cardM, String personalID) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_CARD ");
			sql.append("( CARD_ID, PERSONAL_ID, APPLICATION_TYPE, CARD_TYPE, CARD_STATUS ");
			sql.append(", CARD_FACE, EMBOSS_NAME, VALID_FROM_DATE, VALID_THRU_DATE, UPDATE_BY ");
			sql.append(", UPDATE_DATE, CREATE_BY, CREATE_DATE, ID_NO, REQUEST_CREDIT_LINE ");
			sql.append(", CURRENT_CREDIT_LINE )");
			sql.append(" VALUES(?,?,?,?,?    ,?,?,?,?,?   ,SYSDATE,?,SYSDATE,?  ,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, cardM.getCardId());
			ps.setString(index++, personalID);
			ps.setString(index++, cardM.getApplicationType());
			ps.setString(index++, cardM.getCardType());
			ps.setString(index++, cardM.getCardStatus());
			
			ps.setString(index++, cardM.getCardFace());
			ps.setString(index++, cardM.getEmbossName());
			ps.setTimestamp(index++, cardM.getValidFromDate());
			ps.setTimestamp(index++, cardM.getValidThruDate());
			ps.setString(index++, cardM.getUpdatedBy());
			
			ps.setString(index++, cardM.getCreatedBy());
			ps.setString(index++, cardM.getIdNo());
			ps.setBigDecimal(index++, cardM.getReqCreditLine());
			
			ps.setBigDecimal(index++, cardM.getCurCreditLine());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}
	
	private void deleteTableOrig_Card(String personalId,Vector<String> cardId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_CARD ");
			sql.append(" WHERE PERSONAL_ID = ? AND CARD_ID NOT IN ");
			sql.append("( ");
			if (cardId != null && cardId.size() > 0) {
				for (int i=0; i<cardId.size(); i++){
					sql.append("?,");
				}
				sql.deleteCharAt((sql.length()-1));
				
			}else{
				sql.append(" ? ");
			}
			sql.append(" )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, personalId);
			if (cardId != null && cardId.size() > 0) {
				for (int i=0; i<cardId.size(); i++){
					ps.setString((i+2), cardId.get(i));
				}
			}else{
				ps.setString(2, null);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public PLCardDataM loadOrigCard(String personalId, String busClassId) throws PLOrigApplicationException{		
//		if(OrigUtil.isEmptyString(busClassId) 
//					|| !OrigBusinessClassUtil.getInstance().isContainsBusClass(busClassId, OrigConstant.BusGroup.KEC_ICDC_FLOW)) {
//			return selectTable_OrigCard(personalId);
//		}else{
//			return selectTable_OrigCardIncreaseDecrease(personalId);
//		}
		return null;
	}
	
	@Override
	public PLCardDataM loadOrigCard_IncreaseDecrease(String personalId, PLApplicationDataM currentAppM) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CASE WHEN APP.ICDC_FLAG in (?,?) ");
			sql.append("THEN pka_crypto.decrypt_field(CRD.CARD_NO) ");
			sql.append("ELSE (SELECT pka_crypto.decrypt_field(CARD_NO) FROM AC_ACCOUNT_CARD WHERE ACCOUNT_CARD_ID = APP.ACCOUNT_CARD_ID) END CARD_NO, ");
			sql.append("CRD.APPLICATION_TYPE, ");
			sql.append("CRD.CARD_TYPE, ");
			sql.append("CRD.CARD_STATUS, ");
			sql.append("CRD.CARD_FACE, ");
			sql.append("CRD.EMBOSS_NAME, ");
			sql.append("CRD.VALID_FROM_DATE, ");
			sql.append("CRD.VALID_THRU_DATE, ");
			sql.append("CRD.UPDATE_BY, ");
			sql.append("CRD.UPDATE_DATE, ");
			sql.append("CRD.CREATE_BY, ");
			sql.append("CRD.CREATE_DATE, ");
			sql.append("CRD.ID_NO, ");
			sql.append("CRD.REQUEST_CREDIT_LINE, ");
			sql.append("CRD.CURRENT_CREDIT_LINE ");
			sql.append("FROM ORIG_CARD CRD ");
			sql.append("INNER JOIN ORIG_PERSONAL_INFO PI ON PI.PERSONAL_ID = CRD.PERSONAL_ID ");
			sql.append("INNER JOIN ORIG_APPLICATION APP  ON APP.APPLICATION_RECORD_ID = PI.APPLICATION_RECORD_ID ");
			sql.append("WHERE CRD.PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, OrigConstant.BusClass.FCP_KEC_IC);
			ps.setString(2, OrigConstant.BusClass.FCP_KEC_DC);
			ps.setString(3, personalId);

			rs = ps.executeQuery();
			
			PLCardDataM cardM = new PLCardDataM();
			PLPersonalInfoDataM currentPersonM = currentAppM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
			if(currentPersonM == null) currentPersonM = new PLPersonalInfoDataM();
			PLCardDataM currentCardM = currentPersonM.getCardInformation();
			if(currentCardM == null) currentCardM = new PLCardDataM();
			
			int index = 1;
			
			if (rs.next()) {
				cardM.setCardNo(rs.getString(index++));
				cardM.setApplicationType(rs.getString(index++));
				cardM.setCardType(rs.getString(index++));
				cardM.setCardStatus(rs.getString(index++));
				cardM.setCardFace(rs.getString(index++));
				cardM.setEmbossName(rs.getString(index++));
				cardM.setValidFromDate(rs.getTimestamp(index++));
				cardM.setValidThruDate(rs.getTimestamp(index++));
				
				cardM.setUpdatedBy(rs.getString(index++));
				cardM.setUpdatedDate(rs.getTimestamp(index++));
				cardM.setCreatedBy(rs.getString(index++));
				cardM.setCreatedDate(rs.getTimestamp(index++));
				cardM.setIdNo(rs.getString(index++));
				
				cardM.setReqCreditLine(rs.getBigDecimal(index++));
				cardM.setCurCreditLine(rs.getBigDecimal(index++));
				
				cardM.setPersonalId(currentPersonM.getPersonalID()); //set personal id to current personal id
				cardM.setCardId(currentCardM.getCardId()); //set card id to current card id
				
			}
			
			return cardM;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private void insertTableOrig_CardWithCardNo(PLCardDataM cardM, String personalID) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_CARD ");
			sql.append("( CARD_ID, PERSONAL_ID, CARD_NO, APPLICATION_TYPE, CARD_TYPE ");
			sql.append(", CARD_STATUS, CARD_FACE, EMBOSS_NAME, VALID_FROM_DATE, VALID_THRU_DATE ");
			sql.append(", UPDATE_BY, UPDATE_DATE, CREATE_BY, CREATE_DATE, ID_NO ");
			sql.append(", REQUEST_CREDIT_LINE, CURRENT_CREDIT_LINE ) ");
			sql.append(" VALUES(?,?,pka_crypto.encrypt_field(?),?,?   ,?,?,?,?,?   ,?,SYSDATE,?,SYSDATE,?   ,?,?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			
			ps.setString(index++, cardM.getCardId());
			ps.setString(index++, personalID);
			ps.setString(index++, cardM.getCardNo());
			ps.setString(index++, cardM.getApplicationType());
			ps.setString(index++, cardM.getCardType());
			
			ps.setString(index++, cardM.getCardStatus());
			ps.setString(index++, cardM.getCardFace());
			ps.setString(index++, cardM.getEmbossName());
			ps.setTimestamp(index++, cardM.getValidFromDate());
			ps.setTimestamp(index++, cardM.getValidThruDate());
			
			ps.setString(index++, cardM.getUpdatedBy());
			ps.setString(index++, cardM.getCreatedBy());
			ps.setString(index++, cardM.getIdNo());
			
			ps.setBigDecimal(index++, cardM.getReqCreditLine());
			ps.setBigDecimal(index++, cardM.getCurCreditLine());
			
			ps.executeUpdate();
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
	}

	@Override
	public String loadCardId(String accId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ocard.CARD_ID ");
			sql.append("FROM AC_ACCOUNT ac, AC_ACCOUNT_CARD card, ORIG_CARD ocard, ");
			sql.append("ORIG_PERSONAL_INFO pi, ORIG_APPLICATION app ");
			sql.append("WHERE card.ACCOUNT_ID = ac.ACCOUNT_ID AND ac.APPLICATION_NO = app.APPLICATION_NO ");
			sql.append("AND app.APPLICATION_RECORD_ID = pi.APPLICATION_RECORD_ID AND ocard.PERSONAL_ID = pi.PERSONAL_ID ");
			sql.append("AND ac.ACCOUNT_ID = ? AND pi.PERSONAL_TYPE = ? AND card.CARD_STATUS = ? AND app.BLOCK_FLAG IS NULL ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, accId);
			ps.setString(2, OrigConstant.PERSONAL_TYPE_APPLICANT);
			ps.setString(3, OrigConstant.Status.STATUS_ACTIVE);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getString(1);
			}
			
			return null;
			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private PLCardDataM selectTable_OrigCard(String personalId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		PLCardDataM cardM = null;
		try {
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");
			
			SQL.append(" SELECT ");
			SQL.append("     CARD_ID, ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             PKA_CRYPTO.DECRYPT_FIELD(AAC.CARD_NO) ");
			SQL.append("         FROM ");
			SQL.append("             AC_ACCOUNT AA, ");
			SQL.append("             AC_ACCOUNT_CARD AAC, ");
			SQL.append("             ORIG_PERSONAL_INFO OPI ");
			SQL.append("         WHERE ");
			SQL.append("             AA.ACCOUNT_ID = AAC.ACCOUNT_ID ");
			SQL.append("         AND OPI.PERSONAL_ID = ? ");
			SQL.append("         AND AAC.CARD_STATUS = 'A' ");
			SQL.append("         AND AA.APPLICATION_NO = ");
			SQL.append("             ( ");
			SQL.append("                 SELECT ");
			SQL.append("                     APP.APPLICATION_NO ");
			SQL.append("                 FROM ");
			SQL.append("                     ORIG_APPLICATION APP ");
			SQL.append("                 WHERE ");
			SQL.append("                     APP.APPLICATION_RECORD_ID = OPI.APPLICATION_RECORD_ID ");
			SQL.append("             ) ");
			SQL.append("         AND AA.BUSINESS_CLASS_ID IN ");
			SQL.append("             ( ");
			SQL.append("                 SELECT ");
			SQL.append("                     BM.BUS_CLASS_ID ");
			SQL.append("                 FROM ");
			SQL.append("                     BUS_CLASS_GRP_MAP BM ");
			SQL.append("                 WHERE ");
			SQL.append("                     AA.BUSINESS_CLASS_ID = BM.BUS_CLASS_ID ");
			SQL.append("                 AND BM.BUS_GRP_ID = 'FCP_KEC_ALL' ");
			SQL.append("             ) ");
			SQL.append("         AND ROWNUM = 1 ");
			SQL.append("     ) ");
			SQL.append("     CARD_NO, ");
			SQL.append("     APPLICATION_TYPE, ");
			SQL.append("     CARD_TYPE , ");
			SQL.append("     CARD_STATUS, ");
			SQL.append("     CARD_FACE, ");
			SQL.append("     EMBOSS_NAME, ");
			SQL.append("     VALID_FROM_DATE, ");
			SQL.append("     VALID_THRU_DATE , ");
			SQL.append("     UPDATE_BY, ");
			SQL.append("     UPDATE_DATE, ");
			SQL.append("     CREATE_BY, ");
			SQL.append("     CREATE_DATE, ");
			SQL.append("     ID_NO , ");
			SQL.append("     REQUEST_CREDIT_LINE, ");
			SQL.append("     CURRENT_CREDIT_LINE ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_CARD ");
			SQL.append(" WHERE ");
			SQL.append("     PERSONAL_ID = ? ");
			
//			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
			
			log.debug("SQL >> "+SQL);
			
			ps = conn.prepareStatement(SQL.toString());
			rs = null;
			ps.setString(1, personalId);
			ps.setString(2, personalId);

			rs = ps.executeQuery();
			int index = 1;
						
			if (rs.next()) {
				cardM = new PLCardDataM();
				cardM.setCardId(rs.getString(index++));
				
				cardM.setCardNo(rs.getString(index++));
				cardM.setApplicationType(rs.getString(index++));
				cardM.setCardType(rs.getString(index++));
				
				cardM.setCardStatus(rs.getString(index++));
				cardM.setCardFace(rs.getString(index++));
				cardM.setEmbossName(rs.getString(index++));
				cardM.setValidFromDate(rs.getTimestamp(index++));
				cardM.setValidThruDate(rs.getTimestamp(index++));
				
				cardM.setUpdatedBy(rs.getString(index++));
				cardM.setUpdatedDate(rs.getTimestamp(index++));
				cardM.setCreatedBy(rs.getString(index++));
				cardM.setCreatedDate(rs.getTimestamp(index++));
				cardM.setIdNo(rs.getString(index++));
				
				cardM.setReqCreditLine(rs.getBigDecimal(index++));
				cardM.setCurCreditLine(rs.getBigDecimal(index++));
				
				cardM.setPersonalId(personalId);
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return cardM;
	}
	
	private PLCardDataM selectTable_OrigCardIncreaseDecrease(String personalId) throws PLOrigApplicationException {
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		PLCardDataM cardM = null;
		try {
			conn = getConnection();
			
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CARD_ID, pka_crypto.decrypt_field(CARD_NO), APPLICATION_TYPE, CARD_TYPE ");
			sql.append(", CARD_STATUS, CARD_FACE, EMBOSS_NAME, VALID_FROM_DATE, VALID_THRU_DATE ");
			sql.append(", UPDATE_BY, UPDATE_DATE, CREATE_BY, CREATE_DATE, ID_NO ");
			sql.append(", REQUEST_CREDIT_LINE, CURRENT_CREDIT_LINE ");
			sql.append(" FROM ORIG_CARD WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();
			int index = 1;
						
			if (rs.next()) {
				cardM = new PLCardDataM();
				cardM.setCardId(rs.getString(index++));
				cardM.setCardNo(rs.getString(index++));
				cardM.setApplicationType(rs.getString(index++));
				cardM.setCardType(rs.getString(index++));
				
				cardM.setCardStatus(rs.getString(index++));
				cardM.setCardFace(rs.getString(index++));
				cardM.setEmbossName(rs.getString(index++));
				cardM.setValidFromDate(rs.getTimestamp(index++));
				cardM.setValidThruDate(rs.getTimestamp(index++));
				
				cardM.setUpdatedBy(rs.getString(index++));
				cardM.setUpdatedDate(rs.getTimestamp(index++));
				cardM.setCreatedBy(rs.getString(index++));
				cardM.setCreatedDate(rs.getTimestamp(index++));
				cardM.setIdNo(rs.getString(index++));
				
				cardM.setReqCreditLine(rs.getBigDecimal(index++));
				cardM.setCurCreditLine(rs.getBigDecimal(index++));
				
				cardM.setPersonalId(personalId);
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return cardM;
	}

}
