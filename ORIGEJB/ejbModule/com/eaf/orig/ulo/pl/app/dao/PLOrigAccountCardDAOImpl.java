package com.eaf.orig.ulo.pl.app.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.model.app.PLAccountDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class PLOrigAccountCardDAOImpl extends OrigObjectDAO implements	PLOrigAccountCardDAO {

	private static Logger log = Logger.getLogger(PLOrigAccountCardDAOImpl.class);
	
	@Override
	public Vector<PLAccountCardDataM> loadAccountCard(String accId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT ACCOUNT_CARD_ID ,CARD_NO ,HASHING_CARD_NO ,CARD_TYPE , CARD_FACE  ");
			sql.append(", CARD_STATUS, EMBOSS_NAME, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY, SEQ ");
			//20130405  sankom  add filter card_status
			sql.append("FROM AC_ACCOUNT_CARD WHERE ACCOUNT_ID =? and CARD_STATUS='A'  ORDER BY CREATE_DATE ASC");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, accId);

			rs = ps.executeQuery();
			
			Vector<PLAccountCardDataM> accCardVector = new Vector<PLAccountCardDataM>();
			
			while (rs.next()) {
				int index = 1;
				PLAccountCardDataM accCardM = new PLAccountCardDataM();
				accCardM.setAccountId(rs.getString(index++));
				accCardM.setCardNo(rs.getString(index++));
				accCardM.setHashingCardNo(rs.getString(index++));
				accCardM.setAccountId(accId);
				accCardM.setCardType(rs.getString(index++));
				accCardM.setCardFace(rs.getString(index++));
				
				accCardM.setCardStatus(rs.getString(index++));
				accCardM.setEmbossName(rs.getString(index++));
				accCardM.setCreateDate(rs.getTimestamp(index++));
				accCardM.setCreateBy(rs.getString(index++));
				accCardM.setUpdateDate(rs.getTimestamp(index++));
				
				accCardM.setUpdateBy(rs.getString(index++));
				accCardM.setSeq(rs.getInt(index++));
				
				accCardVector.add(accCardM);
				
			}
			
			return accCardVector;
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

	private PLAccountCardDataM copyCardData(String accId, PLAccountCardDataM accCardM) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CARD_TYPE , CARD_FACE, EMBOSS_NAME, CARD_NO ");
			sql.append("FROM AC_ACCOUNT_CARD WHERE ACCOUNT_ID =? AND CARD_STATUS = 'A'");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, accId);

			rs = ps.executeQuery();
			int index = 1;
			
			if (rs.next()) {
				
				accCardM.setCardType(rs.getString(index++));
				accCardM.setCardFace(rs.getString(index++));
				accCardM.setEmbossName(rs.getString(index++));
				accCardM.setCardNo(rs.getString(index++));
				
			}
			
			return accCardM;
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
	
	private String loadCardNo(String accId) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String cardNo = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CARD_NO ");
			sql.append("FROM AC_ACCOUNT_CARD WHERE ACCOUNT_ID =? AND CARD_STATUS = 'A'");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, accId);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				cardNo = rs.getString(1);
			}
			
			return cardNo;
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

	@Override
	public void reIssueCardNo(PLAccountCardDataM accCardM, UserDetailM userM) throws PLOrigApplicationException {			
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" CALL pka_account_card.process_reissue(?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ reIssueCardNo SQL:"+dSql);
			logger.debug("@@@@@ accCardM.getAccountId() :"+accCardM.getAccountId());
			ps = conn.prepareCall(dSql);
			ps.setString(1,accCardM.getAccountId());
			ps.setString(2,userM.getUserName());
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	
	private int selectMaxSeq (String accId) throws PLOrigApplicationException {
		
		int seq = 1;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT MAX(SEQ) FROM AC_ACCOUNT_CARD WHERE ACCOUNT_ID = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, accId);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				seq = rs.getInt(1) + 1;
			}
			
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
		
		return seq;
	}
	
		
	private void insertTableAC_ACCOUNT_CARD(PLAccountCardDataM accCardM) throws PLOrigApplicationException {
		
		String cardNo = this.getCardNo(accCardM.getCardNo());
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			log.debug("accCardM.getAccountCardId() = "+accCardM.getAccountCardId());
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO AC_ACCOUNT_CARD ");
			sql.append("(ACCOUNT_CARD_ID, CARD_NO, HASHING_CARD_NO, ACCOUNT_ID, CARD_TYPE ");
			sql.append(", CARD_FACE, CARD_STATUS, EMBOSS_NAME, CREATE_DATE, CREATE_BY ");
			sql.append(", UPDATE_DATE, UPDATE_BY, SEQ) ");
			sql.append(" VALUES (?,pka_crypto.encrypt_field(?), pka_crypto.HASHING_FIELD(?),?,?    ,?,?,?,SYSDATE,?     ,SYSDATE,?,?)");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, accCardM.getAccountCardId());
			ps.setString(index++, cardNo);
			ps.setString(index++, cardNo);
			ps.setString(index++, accCardM.getAccountId());
			ps.setString(index++, accCardM.getCardType());
			
			ps.setString(index++, accCardM.getCardFace());
			ps.setString(index++, accCardM.getCardStatus());
			ps.setString(index++, accCardM.getEmbossName());
			ps.setString(index++, accCardM.getCreateBy());
			
			ps.setString(index++, accCardM.getUpdateBy());
			ps.setInt(index++, accCardM.getSeq());
			
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
	
	private String getCardNo (String cardNo) throws PLOrigApplicationException {
		Connection conn = null;
		conn = getConnection();
		CallableStatement callStmt = null;
		ResultSet rs = null;		
		try{
			callStmt = conn.prepareCall("{? = call f_gen_card_no(?) }");			
			logger.debug("{? = call f_gen_card_no(?) }");
			callStmt.registerOutParameter(1, OracleTypes.VARCHAR);
			callStmt.setString(2, cardNo);	
			callStmt.executeQuery();			
			return callStmt.getString(1);			
		} catch (Exception e) {
			logger.fatal("getCardNo " + e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, callStmt);
			} catch (Exception e) {
				throw new PLOrigApplicationException(e.getMessage());
			}			
		}
		
		
	}
	
	private void updateStatusTableAC_ACCOUNT_CARD(String accId) throws PLOrigApplicationException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE AC_ACCOUNT_CARD SET CARD_STATUS = ? WHERE ACCOUNT_ID = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, OrigConstant.Status.STATUS_INACTIVE);
			ps.setString(2, accId);
			
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
	public PLAccountCardDataM loadAccountCardByCardNo(String cardNo) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLAccountCardDataM accountCardM = null;
		Connection conn = null;
		try{			
			conn = getConnection();			
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     ACCOUNT_CARD_ID, ");
				SQL.append("     PKA_CRYPTO.DECRYPT_FIELD(AC.CARD_NO) CARD_NO, ");
				SQL.append("     AC.HASHING_CARD_NO, ");
				SQL.append("     AC.ACCOUNT_ID, ");
				SQL.append("     AC.CARD_TYPE, ");
				SQL.append("     AC.CARD_FACE, ");
				SQL.append("     AC.CARD_STATUS, ");
				SQL.append("     AC.EMBOSS_NAME, ");
				SQL.append("     AC.CREATE_DATE, ");
				SQL.append("     AC.CREATE_BY, ");
				SQL.append("     AC.UPDATE_DATE, ");
				SQL.append("     AC.UPDATE_BY, ");
				SQL.append("     AC.SEQ, ");
				SQL.append("     AC.PROJECT_CODE, ");
				SQL.append("     ACC.IDNO ");
				SQL.append(" FROM AC_ACCOUNT_CARD AC ");
				SQL.append(" INNER JOIN AC_ACCOUNT ACC ");
				SQL.append(" ON ACC.ACCOUNT_ID = AC.ACCOUNT_ID ");
				SQL.append(" WHERE ");
				SQL.append("     AC.HASHING_CARD_NO = PKA_CRYPTO.HASHING_FIELD(?) ");
				
			log.debug("SQL >> "+SQL);
				
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, cardNo);
			rs = ps.executeQuery();
			
			if(rs.next()){
				accountCardM = new PLAccountCardDataM();
				accountCardM.setAccountCardId(rs.getString("ACCOUNT_CARD_ID"));
				accountCardM.setAccountId(rs.getString("ACCOUNT_ID"));
				accountCardM.setCardFace(rs.getString("CARD_FACE"));
				accountCardM.setCardNo(rs.getString("CARD_NO"));
				accountCardM.setCardStatus(rs.getString("CARD_STATUS"));
				accountCardM.setCardType(rs.getString("CARD_TYPE"));
				accountCardM.setCreateBy(rs.getString("CREATE_BY"));
				accountCardM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				accountCardM.setEmbossName(rs.getString("EMBOSS_NAME"));
				accountCardM.setHashingCardNo(rs.getString("HASHING_CARD_NO"));
				accountCardM.setSeq(rs.getInt("SEQ"));
				accountCardM.setUpdateBy(rs.getString("UPDATE_BY"));
				accountCardM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				accountCardM.setProjectCode(rs.getString("PROJECT_CODE"));
				accountCardM.setIdNo(rs.getString("IDNO"));
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return accountCardM;
	}
	
	@Override
	public String getAppRecordIdByCardNo(String cardNo) throws PLOrigApplicationException {
		String appId = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select app.application_record_id ");
			sql.append("from ac_account_card ac ");
			sql.append("inner join ac_account a on ac.account_id = a.account_id ");
			sql.append("inner join orig_application app on app.application_no = a.application_no ");
			sql.append("where ac.card_no = pka_crypto.encrypt_field(?) ");
			
			String dSql = String.valueOf(sql);
			log.debug("@@@@@ getAppRecordIdByCardNo Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cardNo);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				appId = rs.getString("application_record_id");
			}
			
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
		
		return appId;
	}

	@Override
	public Vector<PLAccountCardDataM> loadAccountCardAndCustNo(String accId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<PLAccountCardDataM> accCardVector = new Vector<PLAccountCardDataM>();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" SELECT ");
				sql.append("     AAC.ACCOUNT_CARD_ID , ");
				sql.append("     PKA_CRYPTO.DECRYPT_FIELD(AAC.CARD_NO) , ");
				sql.append("     AAC.HASHING_CARD_NO , ");
				sql.append("     AAC.CARD_TYPE , ");
				sql.append("     AAC.CARD_FACE , ");
				sql.append("     AAC.CARD_STATUS, ");
				sql.append("     AAC.EMBOSS_NAME, ");
				sql.append("     AAC.CREATE_DATE, ");
				sql.append("     AAC.CREATE_BY, ");
				sql.append("     AAC.UPDATE_DATE , ");
				sql.append("     AAC.UPDATE_BY, ");
				sql.append("     AAC.SEQ, ");
				sql.append("     AC.CARDLINK_CUST_NO ");
				sql.append(" FROM AC_ACCOUNT_CARD AAC, ");
				sql.append("     AC_ACCOUNT AC ");
				sql.append(" WHERE ");
				sql.append("     AAC.ACCOUNT_ID = AC.ACCOUNT_ID ");
				sql.append(" AND AAC.ACCOUNT_ID = ? ");
				sql.append(" ORDER BY CREATE_DATE ASC ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, accId);

			rs = ps.executeQuery();
						
			while (rs.next()) {
				int index = 1;
				PLAccountCardDataM accCardM = new PLAccountCardDataM();
				accCardM.setAccountId(rs.getString(index++));
				accCardM.setCardNo(rs.getString(index++));
				accCardM.setHashingCardNo(rs.getString(index++));
				accCardM.setAccountId(accId);
				accCardM.setCardType(rs.getString(index++));
				accCardM.setCardFace(rs.getString(index++));
				
				accCardM.setCardStatus(rs.getString(index++));
				accCardM.setEmbossName(rs.getString(index++));
				accCardM.setCreateDate(rs.getTimestamp(index++));
				accCardM.setCreateBy(rs.getString(index++));
				accCardM.setUpdateDate(rs.getTimestamp(index++));
				
				accCardM.setUpdateBy(rs.getString(index++));
				accCardM.setSeq(rs.getInt(index++));
				accCardM.setCardLinkCustNo(rs.getString(index++));
				accCardVector.add(accCardM);
				
			}
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
		return accCardVector;
	}

	@Override
	public PLAccountCardDataM loadAccountCardByAppNo(String appNo) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLAccountCardDataM accountCardM = null;
		Connection conn = null;
		try {
			
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     CARD.ACCOUNT_CARD_ID, ");
				SQL.append("     CARD.ACCOUNT_ID, ");
				SQL.append("     CARD.CARD_FACE, ");
				SQL.append("     PKA_CRYPTO.DECRYPT_FIELD(CARD.CARD_NO) CARD_NO, ");
				SQL.append("     CARD.CARD_STATUS, ");
				SQL.append("     CARD.CARD_TYPE, ");
				SQL.append("     CARD.CREATE_BY, ");
				SQL.append("     CARD.CREATE_DATE, ");
				SQL.append("     CARD.EMBOSS_NAME, ");
				SQL.append("     CARD.HASHING_CARD_NO, ");
				SQL.append("     CARD.SEQ, ");
				SQL.append("     CARD.UPDATE_BY, ");
				SQL.append("     CARD.UPDATE_DATE ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_APPLICATION APP ");
				SQL.append(" INNER JOIN AC_ACCOUNT_CARD CARD ");
				SQL.append(" ON ");
				SQL.append("     APP.ACCOUNT_CARD_ID = CARD.ACCOUNT_CARD_ID ");
				SQL.append(" WHERE ");
				SQL.append("     APP.APPLICATION_NO = ?");
			
			String dSql = SQL.toString();
			log.debug("@@@@@ loadAccountCardByAppNo = " + appNo +" Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, appNo);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				accountCardM = new PLAccountCardDataM();
				accountCardM.setAccountCardId(rs.getString("ACCOUNT_CARD_ID"));
				accountCardM.setAccountId(rs.getString("ACCOUNT_ID"));
				accountCardM.setCardFace(rs.getString("CARD_FACE"));
				accountCardM.setCardNo(rs.getString("CARD_NO"));
				accountCardM.setCardStatus(rs.getString("CARD_STATUS"));
				accountCardM.setCardType(rs.getString("CARD_TYPE"));
				accountCardM.setCreateBy(rs.getString("CREATE_BY"));
				accountCardM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				accountCardM.setEmbossName(rs.getString("EMBOSS_NAME"));
				accountCardM.setHashingCardNo(rs.getString("HASHING_CARD_NO"));
				accountCardM.setSeq(rs.getInt("SEQ"));
				accountCardM.setUpdateBy(rs.getString("UPDATE_BY"));
				accountCardM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
			}
			
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return accountCardM;
	}

	@Override
	public String getCitizenNoByCardNo(String cardNo){
		PreparedStatement ps = null;
		ResultSet rs = null;
		String IDNO = null;
		Connection conn = null;
		try{			
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT IDNO ");
			sql.append(" FROM AC_ACCOUNT AC, AC_ACCOUNT_CARD ACD ");
			sql.append(" WHERE AC.ACCOUNT_ID = ACD.ACCOUNT_ID ");
			sql.append(" 	AND CARD_NO = PKA_CRYPTO.ENCRYPT_FIELD(?) ");
			
			String dSql = String.valueOf(sql);
			log.debug("SQL >> " + dSql);
			
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cardNo);

			rs = ps.executeQuery();
			
			if(rs.next()){
				IDNO = rs.getString(1);
			}
			
		}catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
			}
		}
		log.debug("getCitizenNoByCardNo() >> "+IDNO);
		return IDNO;
	}

	@Override
	public void InactiveAccountCard(String appNo, UserDetailM userM) throws PLOrigApplicationException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE AC_ACCOUNT_CARD ACC SET ACC.CARD_STATUS = ?, ACC.UPDATE_BY = ?, ACC.UPDATE_DATE = SYSDATE ");
				sql.append(" WHERE ACC.ACCOUNT_ID IN (SELECT AC.ACCOUNT_ID FROM AC_ACCOUNT AC WHERE AC.APPLICATION_NO = ?) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, OrigConstant.Status.STATUS_INACTIVE);
			ps.setString(index++, userM.getUserName());
			ps.setString(index++, appNo);
			
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
	public PLAccountDataM loadAccountNoCardData(String accountId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		PLAccountDataM accountM = new PLAccountDataM();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("select * from ac_account acc where acc.account_id = ?");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, accountId);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				accountM.setAccountId(accountId);
				accountM.setApplicationNo(rs.getString("ACCOUNT_ID"));
				accountM.setApproveDate(rs.getTimestamp("APPROVE_DATE"));
				accountM.setBusinessId(rs.getString("BUSINESS_CLASS_ID"));
				accountM.setCardlinkCustNo(rs.getString("CARDLINK_CUST_NO"));
				accountM.setCardLinkDate(rs.getTimestamp("CARDLINK_DATE"));
				accountM.setIDNO(rs.getString("IDNO"));
				accountM.setProjectCode(rs.getString("PROJECT_CODE"));
				accountM.setThFirstName(rs.getString("TH_FIRST_NAME"));
				accountM.setThLastName(rs.getString("TH_LAST_NAME"));
				accountM.setThMidName(rs.getString("TH_MID_NAME"));
				accountM.setThTitleCode(rs.getString("TH_TITLE_CODE"));
			}			
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return accountM;
	}

	@Override
	public boolean isCitizenRelateCardNo(String citizenId, String cardNo) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean result = false;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");			
				SQL.append(" SELECT ");
				SQL.append("     COUNT(1) AS CNT ");
				SQL.append(" FROM ");
				SQL.append("     AC_ACCOUNT_CARD CARD ");
				SQL.append(" INNER JOIN AC_ACCOUNT ACC ");
				SQL.append(" ON ");
				SQL.append("     ACC.ACCOUNT_ID = CARD.ACCOUNT_ID ");
				SQL.append(" WHERE ");
				SQL.append("     CARD.HASHING_CARD_NO = PKA_CRYPTO.HASHING_FIELD(?) ");
				SQL.append(" AND ACC.IDNO = ? ");
				
//				#septemwi
//				SQL.append(" AND ACC.ACCOUNT_STATUS = ? ");
//				SQL.append(" AND CARD.CARD_STATUS = ? ");
			
//			String dSql = String.valueOf(sql);
			log.debug("SQL=" + SQL);
			ps = conn.prepareStatement(SQL.toString());
//			rs = null;
			int index = 1;
			ps.setString(index++, cardNo);
			ps.setString(index++, citizenId);
			
//			ps.setString(3, OrigConstant.Status.STATUS_ACTIVE);
//			ps.setString(4, OrigConstant.Status.STATUS_ACTIVE);

			rs = ps.executeQuery();
			
			if(rs.next()){
				if(rs.getInt("cnt") > 0){
					result =  true;
				}
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
		return result;
	}
	
	@Override
	public void updateCardLinkStatus(String cardLinkStatus, String applicationNo, UserDetailM userM) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {

			conn = getConnection();
			StringBuilder sql = new StringBuilder("");

			sql.append("update ac_account acc set acc.cardlink_status = ?, acc.update_date = sysdate, acc.update_by = ? where acc.application_no = ?");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, cardLinkStatus);
			ps.setString(index++, userM.getUserName());
			ps.setString(index++, applicationNo);
			
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
	public void processAccountCardForRework(PLApplicationDataM appM, UserDetailM userM)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" CALL pka_account_card.process_rework(?,?)");
			String dSql = String.valueOf(sql);
			logger.debug("@@@@@ processAccountCardForRework SQL:"+dSql);
			logger.debug("@@@@@ appM.getAppRecordID() :"+appM.getAppRecordID());
			ps = conn.prepareCall(dSql);
			ps.setString(1,appM.getAppRecordID());
			ps.setString(2,userM.getUserName());
			ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PLOrigApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public void setInActiveAccountCard(String accountID, UserDetailM userM)	throws PLOrigApplicationException {		
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE AC_ACCOUNT_CARD SET CARD_STATUS = ?, UPDATE_BY = ?, UPDATE_DATE = SYSDATE ");
				sql.append(" WHERE ACCOUNT_ID = ? ");
				sql.append(" AND SEQ = (SELECT MAX(TMP.SEQ) FROM AC_ACCOUNT_CARD TMP WHERE TMP.ACCOUNT_ID = ? )");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int index = 1;
			ps.setString(index++, OrigConstant.Status.STATUS_INACTIVE);
			ps.setString(index++, userM.getUserName());
			ps.setString(index++, accountID);
			ps.setString(index++, accountID);
			
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
}
