package com.eaf.orig.ulo.app.key.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.MathUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.dao.utility.EjbUtil;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.dao.exception.UniqueIDGeneratorException;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.membership.RunningParamStackDataM;

/**
 * @author Anu Mariam Elias
 *
 * Type: UniqueIDGeneratorDAOImpl
 */
public class UniqueIDGeneratorDAOImpl extends OrigObjectDAO implements UniqueIDGeneratorDAO {
	static Logger log = Logger.getLogger(UniqueIDGeneratorDAOImpl.class);
	/** Card Link Number = Prefix (PREFIX) + Running Number (RUNNING_PARAM-VALUE1) + Check digits (Modulus 10) */
	public UniqueIDGeneratorDAOImpl(String execution){
		this.execution = execution;
	}
	public UniqueIDGeneratorDAOImpl(){
		this.execution = OrigObjectDAO.EXECUTION_EJB;
	}
	private String execution = OrigObjectDAO.EXECUTION_EJB;
	@Override
	public String getCardLinkNo(CardDataM card) throws UniqueIDGeneratorException {
		String prefix = "";
		String cardParam = "";
		String runningCode = "";
		String chkDigits = "";
		try{
			/** Running Number **/
			Map<String,String> cardParamMap = getCardParamValues(card.getCardType(), MConstant.CARD_NO_GEN.CARDLINK_CUST_NO);
			cardParam = cardParamMap.get("VALUE1");
			String value2 = cardParamMap.get("VALUE2");
			logger.info("##### Getting Running Param CardType " + card.getCardType() + " : "+ cardParam);
			if (Util.empty(cardParam)) {
				throw new UniqueIDGeneratorException("Can not map data with table named CARD_PARAM(" + card.getCardType() + ")");
			}
			/** Prefix  **/
			prefix = getPrefixForCardLink(cardParam, MConstant.CARD_NO_GEN.CARDLINK_CUST_NO);
			int prefixLength = prefix.length();
			runningCode = getGenerateRunningNo(cardParam, MConstant.CARD_NO_GEN.CARDLINK_CUST_NO, value2);
			runningCode = EjbUtil.appendZero(runningCode, 15 - prefixLength);
			/** Check digits **/
			chkDigits = findChkDigit(prefix + runningCode );
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + cardParam);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return prefix + runningCode + chkDigits;
	}

	/** Membership Card Number = Running Number (RUNNING_PARAM-VALUE1)  */
	@Override
	public String getHisHerMembershipNo(CardDataM card) throws UniqueIDGeneratorException {
		String runningCode = "";
		try{
			runningCode = getGenerateRunningNo(MConstant.CARD_NO_GEN.HH_MEMBERSHIP_NO, MConstant.CARD_NO_GEN.PARAM_TYPE_MEMNO);
			runningCode = EjbUtil.appendZero(runningCode, 13);
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + MConstant.CARD_NO_GEN.HH_MEMBERSHIP_NO);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return runningCode;
	}

	/** Card Number = CardBinNo (CARD_TYPE-BIN) + Running Number (RUNNING_PARAM-VALUE1) + Check digits (Modulus 10) */
	@Override
	public String getPriorityPassNo(CardDataM card)	throws UniqueIDGeneratorException {
		String prefix = "";
		String cardParam = "";
		String runningCode = "";
		String chkDigits = "";
		try{
			Map<String,String> cardParamMap = getCardParamValues("PP",MConstant.CARD_NO_GEN.PARAM_CODE_PP_NO);
			cardParam = cardParamMap.get("VALUE1");
			String value2 = cardParamMap.get("VALUE2");
			logger.info("##### Getting Running Param CardType PP : "+ cardParam);
			if (Util.empty(cardParam)) {
				throw new UniqueIDGeneratorException("Can not map data with table named CARD_PARAM(" + card.getCardType() + ")");
			}
			/** Prefix  **/
			prefix = getPrefixForCardLink(cardParam, MConstant.CARD_NO_GEN.PP_CARD_NO);
			runningCode = getGenerateRunningNo(cardParam, MConstant.CARD_NO_GEN.PP_CARD_NO, value2);
			runningCode = EjbUtil.appendZero(runningCode, 7);
			/** Check digits **/
			chkDigits = findChkDigit(prefix + runningCode );
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + MConstant.CARD_NO_GEN.PP_CARD_NO);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return prefix + runningCode + chkDigits;
	}
	
	/** Card Number = CardBinNo (CARD_TYPE-BIN) + Running Number (RUNNING_PARAM-VALUE1) + Check digits (Modulus 10) */
	public String getCardNo(CardDataM card) throws UniqueIDGeneratorException {
		String cardBinNo = "";
		String cardParam = "";
		String runningCode = "";
		String chkDigits = "";
		try{
			/** CardBinNo **/
			cardBinNo = getCardBinNo(card.getCardType());
			/** Running Number **/
			Map<String,String> cardParamMap = getCardParamValues(card.getCardType(),MConstant.CARD_NO_GEN.CARD_NO);
			String value1 = cardParamMap.get("VALUE1");
			String value2 = cardParamMap.get("VALUE2");
			cardParam = value1;
			logger.info("##### Getting Running Param CardType " + card.getCardType() + " : "+ cardParam);
			if (Util.empty(cardParam)) {
				throw new UniqueIDGeneratorException("Can not map data with table named CARD_PARAM(" + card.getCardType() + ")");
			}
			runningCode = getGenerateRunningNo(cardParam, MConstant.CARD_NO_GEN.PARAM_TYPE_CARD,value2);
			runningCode = EjbUtil.appendZero(runningCode, 15 - cardBinNo.length());
			/** Check digits **/
			chkDigits = findChkDigit(cardBinNo + runningCode );
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + cardParam);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return cardBinNo + runningCode + chkDigits;
	}
	
	private String getCardBinNo(String cardTypeId) throws UniqueIDGeneratorException {
		String appFrontageCode = "";
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT BIN FROM CARD_TYPE WHERE CARD_TYPE_ID = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cardTypeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				appFrontageCode = rs.getString("BIN");
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
			    closeConnection(conn, rs, ps);
			}catch(Exception e){
			    logger.error("Error >> ", e);
			    throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
		return appFrontageCode;
	}
	
	/** Get VALUE1 from table CARD_PARAM For Generate Card No. */
	public Map<String,String> getCardParamValues(String cardTypeId, String paramCode) throws UniqueIDGeneratorException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		HashMap<String, String> cardParamMap = new HashMap<String, String>();
		try{
			//conn = Get Connection
			conn = getConnection(OrigServiceLocator.ORIG_DB);
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT VALUE1, VALUE2 FROM CARD_PARAM ");
			sql.append(" WHERE CARD_TYPE_ID = ? AND PARAMCD = ? ");
			String dSql = String.valueOf(sql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, cardTypeId);
			ps.setString(2, paramCode);
			rs = ps.executeQuery();
			if (rs.next()) {
				cardParamMap.put("VALUE1", rs.getString("VALUE1"));
				cardParamMap.put("VALUE2", rs.getString("VALUE2"));
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally {
			try{
			    closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.error(e.getMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
		return cardParamMap;
	}
	
//	private String getGenerateRunningNo(String paramID, String paramType, String param2,Connection conn) throws UniqueIDGeneratorException {
//		return getGenerateRunningNo(paramID,paramType,param2,conn);
//	}
//	private String getGenerateRunningNo(String paramID, String paramType, String param2,boolean isBatch) throws UniqueIDGeneratorException {
//		return getGenerateRunningNoBatch(paramID,paramType,param2,isBatch);
//	}
	private String getGenerateRunningNo(String paramID, String paramType, String param2) throws UniqueIDGeneratorException {
		Connection conn = null;
		try{
			conn = getConnection();
			return getGenerateRunningNo(paramID,paramType,param2,conn);
		}catch(Exception e){
			log.fatal("ERROR",e);
			throw new UniqueIDGeneratorException(e);
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				log.fatal("ERROR",e);
				throw new UniqueIDGeneratorException(e);
			}
		}
	}
	/** Get VALUE1 from table RUNNING_PARAM For Generate Card No. */
	private String getGenerateRunningNo(String paramID, String paramType, String param2,Connection conn) throws UniqueIDGeneratorException {
		long testbig = 0;
		try{
			log.debug("paramID = " + paramID);
			log.debug("paramType = " + paramType);
			log.debug("param2 = " + param2);
			testbig = getNextKeyAfterIncrementing(paramID,paramType,conn);
			if(testbig == 0 && Util.empty(param2)){
				throw new UniqueIDGeneratorException("Running Param is Full.!!");
			}else if(testbig == 0 && !Util.empty(param2)) {
				log.debug("over shoot the max value. Need to reset ");
				boolean chkFullFlag = checkFullRunningParam(paramType,param2);
				log.debug("chkFullFlag ==> " + chkFullFlag);
				if(chkFullFlag){
					throw new UniqueIDGeneratorException("Running Param is Full.!!");
				}else{				
					copyRunningParamValuesAndUpdate(paramID,paramType,param2,conn);
					testbig = getNextKeyAfterIncrementing(paramID,paramType,conn);
					if (testbig == 0) {
						throw new UniqueIDGeneratorException("Running Param is Full.!!");
					} 				
				}							
			}
			log.debug("testbig = " + testbig);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return String.valueOf(testbig);

	}
	
	private double copyRunningParamValuesAndUpdate(String paramID, String paramType,String param2,Connection conn) throws UniqueIDGeneratorException {
		double returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
//			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("");
				sql.append(" UPDATE RUNNING_PARAM SET (VALUE_FROM, VALUE_TO, VALUE1, VALUE2) = ");
				sql.append(" (SELECT VALUE_FROM, VALUE_TO, VALUE1, VALUE2 FROM RUNNING_PARAM ");
				sql.append(" WHERE PARAM_ID = ? AND PARAM_TYPE = ?) ");
				sql.append(" WHERE PARAM_ID = ? AND PARAM_TYPE = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, param2);
			ps.setString(2, paramType);
			ps.setString(3, paramID);
			ps.setString(4, paramType);
			returnRows = ps.executeUpdate();
			
			// Update value_from and value_to to 0
			sql = new StringBuffer("");
			sql.append(" UPDATE RUNNING_PARAM SET VALUE_FROM = 0, VALUE_TO = 0, VALUE1 = 0 ");
			sql.append(" WHERE PARAM_ID = ? AND PARAM_TYPE = ? ");
			dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, param2);
			ps.setString(2, paramType);
			returnRows = ps.executeUpdate();
//			logger.debug("isBatch : "+isBatch);
//			if(isBatch){
//				conn.commit();
//			}
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
//				closeConnection(conn, ps);
				closeConnection(ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
		return returnRows;
		
	}

	
	private boolean checkFullRunningParam(String paramType,String param2) throws UniqueIDGeneratorException {
		PreparedStatement ps = null;
		Connection conn = null;
		ResultSet rs = null;
		boolean isFull= false;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT COUNT(1) AS COUNT_RUNNING_PARAM ");
			sql.append(" FROM RUNNING_PARAM WHERE PARAM_ID = ? AND PARAM_TYPE = ? ");
			sql.append(" AND VALUE_FROM = 0 AND VALUE_TO = 0 ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, param2);
			ps.setString(2, paramType);
			rs = ps.executeQuery();
			if(rs.next()){
				if(rs.getInt("COUNT_RUNNING_PARAM")>0){
					isFull = true;
				}
			}
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
		return isFull;
		
	}
	
//	private long getNextKeyAfterIncrementingBatch(String paramID, String paramType) throws UniqueIDGeneratorException {
//		long value_1 = 0;
//		long value_2 = 0;
//		long value_from = 0;
//		long value_to = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection conn = null;
//		try {
//			//conn = Get Connection
//			conn = getConnection();
//			StringBuffer sql = new StringBuffer("");
//			sql.append("SELECT VALUE_FROM, VALUE_TO, VALUE1, VALUE2 ");
//			sql.append(" FROM RUNNING_PARAM WHERE PARAM_ID = ? AND PARAM_TYPE = ? FOR UPDATE ");
//			String dSql = String.valueOf(sql);
//			log.debug("Sql="+dSql);
//			log.debug("paramID="+paramID);
//			log.debug("paramType="+paramType);
//			conn.setAutoCommit(false);
//			ps = conn.prepareStatement(dSql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
//			rs = null;
////			int increment = 1;
//			ps.setString(1, paramID);
//			ps.setString(2, paramType);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				value_from = rs.getLong("VALUE_FROM");
//				value_to = rs.getLong("VALUE_TO");
//				value_1 = rs.getLong("VALUE1");
//				value_2 = rs.getLong("VALUE2");
//			}
//			long tmpID = value_from + value_1;        
//				//check from+value1 < value to
//			if(tmpID <= value_to){			
//				value_1++;
//			}else{
//				log.debug("tmpID = " + tmpID+" max value "+value_to);
//				return 0;			 
//			}		
//			log.debug("tmpID =>>>" + tmpID);
//			return tmpID;
//		} catch (Exception e) {
//			log.fatal(e.getLocalizedMessage());
//			throw new UniqueIDGeneratorException(e.getMessage());
//		} finally {
//			try {
//				updateRunningCodeBatch(paramID, paramType, value_1,value_2,conn);
//				closeConnection(conn, rs, ps);
//			} catch (Exception e) {
//				log.fatal(e.getLocalizedMessage());
//				throw new UniqueIDGeneratorException(e.getMessage());
//			}
//		}
//	}
	
	private long getNextKeyAfterIncrementing(String paramID, String paramType,Connection conn) throws UniqueIDGeneratorException {
		long value_1 = 0;
		long value_2 = 0;
		long value_from = 0;
		long value_to = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT VALUE_FROM, VALUE_TO, VALUE1, VALUE2 ");
			sql.append(" FROM RUNNING_PARAM WHERE PARAM_ID = ? AND PARAM_TYPE = ? FOR UPDATE ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			log.debug("paramID="+paramID);
			log.debug("paramType="+paramType);
			if(OrigObjectDAO.EXECUTION_MANUAL.equals(execution)){
				ps = conn.prepareStatement(dSql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			}else{
				ps = conn.prepareStatement(dSql);
			}
			rs = null;
//			int increment = 1;
			ps.setString(1, paramID);
			ps.setString(2, paramType);
			rs = ps.executeQuery();
			if (rs.next()) {
				value_from = rs.getLong("VALUE_FROM");
				value_to = rs.getLong("VALUE_TO");
				value_1 = rs.getLong("VALUE1");
				value_2 = rs.getLong("VALUE2");
			}
			long tmpID = value_from + value_1;        
				//check from+value1 < value to
			if(tmpID <= value_to){			
				value_1++;
			}else{
				log.debug("tmpID = " + tmpID+" max value "+value_to);
				return 0;			 
			}		
			log.debug("tmpID =>>>" + tmpID);
			return tmpID;
		} catch (Exception e) {
			log.fatal(e.getLocalizedMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, rs, ps);
				closeConnection(rs, ps);
				updateRunningCode(paramID,paramType,value_1,value_2,conn);
			} catch (Exception e) {
				log.fatal(e.getLocalizedMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
	}
//	private long getNextKeyAfterIncrementingWithoutLock(String paramID, String paramType, int size) 
//			throws UniqueIDGeneratorException {
//		long value_1 = 0;
//		long value_2 = 0;
//		long value_from = 0;
//		long value_to = 0;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		Connection conn = null;
//		try {
//			//conn = Get Connection
//			conn = getConnection();
//			StringBuffer sql = new StringBuffer("");
//			sql.append("SELECT VALUE_FROM, VALUE_TO, VALUE1, VALUE2 ");
//			sql.append(" FROM RUNNING_PARAM WHERE PARAM_ID = ? AND PARAM_TYPE = ? ");
//			String dSql = String.valueOf(sql);
//			log.debug("Sql="+dSql);
//			ps = conn.prepareStatement(dSql);
//			rs = null;
//			ps.setString(1, paramID);
//			ps.setString(2, paramType);
//			rs = ps.executeQuery();
//			if (rs.next()) {
//				value_from = rs.getLong("VALUE_FROM");
//				value_to = rs.getLong("VALUE_TO");
//				value_1 = rs.getLong("VALUE1");
//				value_2 = rs.getLong("VALUE2");
//			}
//			
//			long tmpID = value_from + value_1;        
//			//check from+value1 < value to
//			if(tmpID <= value_to){			
//				value_1 += size;
//			}else{
//				log.debug("tmpID = " + tmpID+" max value "+value_to);
//				return 0;			 
//			}		
//			return tmpID;
//		} catch (Exception e) {
//			log.fatal(e.getLocalizedMessage());
//			throw new UniqueIDGeneratorException(e.getMessage());
//		} finally {
//			try {
//				closeConnection(conn, rs, ps);
//				updateRunningCode(paramID, paramType, value_1,value_2);
//			} catch (Exception e) {
//				log.fatal(e.getLocalizedMessage());
//				throw new UniqueIDGeneratorException(e.getMessage());
//			}
//		}
//	}
	private String getPrefixForCardLink(String paramID, String paramType) throws UniqueIDGeneratorException {
		String prefix = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PREFIX ");
			sql.append(" FROM RUNNING_PARAM WHERE PARAM_ID = ? AND PARAM_TYPE = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			ps.setString(1, paramID);
			ps.setString(2, paramType);
			rs = ps.executeQuery();
			if (rs.next()) {
				prefix = rs.getString("PREFIX");
			}
			return prefix;
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
	}
//	private double updateRunningCodeBatch(String paramID, String paramType, long value_1, long value_2,Connection conn) 
//			throws UniqueIDGeneratorException {
//		double returnRows = 0;
//		PreparedStatement ps = null;
////		Connection conn = null;
//		try {
//			//conn = Get Connection
////			conn = getConnection();
//			StringBuffer sql = new StringBuffer("");
//			sql.append(" UPDATE RUNNING_PARAM SET VALUE1 = ?, VALUE2 = ? ");
//			sql.append(" WHERE PARAM_ID = ? AND PARAM_TYPE = ? ");
//			String dSql = String.valueOf(sql);
//			log.debug("Sql=" + dSql);
//			log.debug("value_1=" + value_1);
//			log.debug("value_2=" + value_2);
//			ps = conn.prepareStatement(dSql);
//			ps.setLong(1, value_1);
//			ps.setLong(2, value_2);
//			ps.setString(3, paramID);
//			ps.setString(4, paramType);
//			returnRows = ps.executeUpdate();
//		} catch (Exception e) {
//			log.fatal(e.getLocalizedMessage());
//			throw new UniqueIDGeneratorException(e.getMessage());
//		} finally {
//			try {
//				closeConnection(conn, ps);
//			} catch (Exception e) {
//				log.fatal(e.getLocalizedMessage());
//				throw new UniqueIDGeneratorException(e.getMessage());
//			}
//		}
//		return returnRows;
//	}
	private double updateRunningCode(String paramID, String paramType, long value_1, long value_2,Connection conn) 
			throws UniqueIDGeneratorException {
		double returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try{
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" UPDATE RUNNING_PARAM SET VALUE1 = ?, VALUE2 = ? ");
			sql.append(" WHERE PARAM_ID = ? AND PARAM_TYPE = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("value_1=" + value_1);
			log.debug("value_2=" + value_2);
			ps = conn.prepareStatement(dSql);
			ps.setLong(1, value_1);
			ps.setLong(2, value_2);
			ps.setString(3, paramID);
			ps.setString(4, paramType);
			returnRows = ps.executeUpdate();
		}catch(Exception e){
			log.fatal(e.getLocalizedMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
//				closeConnection(conn, ps);
				closeConnection(ps);
			}catch(Exception e){
				log.fatal(e.getLocalizedMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
		return returnRows;
	}
	/** This method design base on user comment krub.
	 * If you need any other information please contact SA */
	private String findChkDigit(String cardDigits) {
		String totRetStr = "";
		int sum = 0;
		int[] constantArray = { 0, 2, 4, 6, 8, 10, 12, 14, 16};
		for (int i = 0; i < cardDigits.length(); i++) {
			int modDigit = Integer.parseInt(cardDigits.substring(i, i + 1));
			//if check digit position then multiply by 2
			if(ArrayUtils.contains(constantArray, i)) {
				modDigit *= 2;
				//if value have 2 digits, sum the digits
				if(modDigit >= 10) {
					String cardStr = String.valueOf(modDigit);
					char[] cardChr = cardStr.toCharArray();
					modDigit = EjbUtil.charToInt(cardChr[0]) + EjbUtil.charToInt(cardChr[1]);
				}
			} 
			sum += modDigit;
		}
		// step 4 find difference from multiple of 10
		int mod = sum%10;
		if(mod == 0) {
			totRetStr = String.valueOf(mod);
		} else {
			// step 5 if have remainder find difference from 10
			int checkDigit = 10 - mod;
			totRetStr = String.valueOf(checkDigit);
		}
		return totRetStr;
	}

	@Override
	public String getGenerateRunningNo(String paramId,String paramType) throws UniqueIDGeneratorException {
		Connection conn = null;
		try{
			conn = getConnection();
			return getGenerateRunningNo(paramId, paramType, conn);
		}catch(Exception e){
			log.fatal("ERROR",e);
			throw new UniqueIDGeneratorException(e);
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				log.fatal("ERROR",e);
				throw new UniqueIDGeneratorException(e);
			}
		}
	}
	@Override
	public String getGenerateRunningNo(String paramId,String paramType,Connection conn) throws UniqueIDGeneratorException {
		return getGenerateRunningNo(paramId,paramType,null,conn);
	}
//	@Override
//	public String getGenerateRunningNo(String paramId, String paramType,boolean isBatch) throws UniqueIDGeneratorException {
//		return getGenerateRunningNo(paramId, paramType, null,isBatch);
//	}
	@Override
	public String getGenerateRunningNoStack(String paramId,String paramType)throws UniqueIDGeneratorException{
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		String runningParamStackPercentRemaining = SystemConstant.getConstant("RUNNING_PARAM_STACK_PERCENT_REMAINING");
		BigDecimal RUNNING_PARAM_STACK_PERCENT_REMAINING = new BigDecimal(runningParamStackPercentRemaining);
		String memberShipNo = "";
		Connection conn = null;
		try{
			conn = getConnection();
			RunningParamStackDataM runningParamStack = selectRunningParamStack(paramId,paramType,conn);
			if(Util.empty(runningParamStack) || RUNNING_PARAM_STACK_PERCENT_REMAINING.compareTo(runningParamStack.getRemainingPercent()) > 0 || RUNNING_PARAM_STACK_PERCENT_REMAINING.compareTo(runningParamStack.getRemainingPercent())==0){
				enableNextRunningParamStack(paramId,paramType,conn);
				runningParamStack = selectRunningParamStack(paramId,paramType,conn);
			}
			if(!Util.empty(runningParamStack)){
				String paramValue = runningParamStack.getParamValue();
				if(!Util.empty(paramValue)){
					memberShipNo = paramValue;
					runningParamStack.setStatus(FLAG_YES);
					updateStatusRunningParamStack(runningParamStack,conn);
				}
			}else{
				throw new UniqueIDGeneratorException("Running Param Stack is Full.!!");
			}
			if(Util.empty(memberShipNo)){
				throw new UniqueIDGeneratorException("Running Param Stack is Full.!!");
			}
		}catch (Exception e){
			log.fatal("ERROR",e);
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch (Exception e){
				log.fatal("ERROR",e);
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
		return memberShipNo;
	}
	private RunningParamStackDataM selectRunningParamStack(String paramId,String paramType,Connection conn)throws UniqueIDGeneratorException{
		String RUNNING_PARAM_STACK_FETCH_ROW = SystemConstant.getConstant("RUNNING_PARAM_STACK_FETCH_ROW");
		String FLAG_NO = SystemConstant.getConstant("FLAG_NO");
		RunningParamStackDataM runningParamStack = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT PARAM_ID, PARAM_TYPE, PARAM_VALUE,STATUS,ROWID ");
			sql.append(" FROM RUNNING_PARAM_STACK WHERE PARAM_ID = ? AND PARAM_TYPE = ? AND STATUS = '"+FLAG_NO+"'  ");
			sql.append(" ORDER BY PARAM_VALUE FOR UPDATE  ");
			String dSql = String.valueOf(sql);
			log.debug("dSql : "+dSql);
			log.debug("paramId : "+paramId);
			log.debug("paramType : "+paramType);
			if(OrigObjectDAO.EXECUTION_MANUAL.equals(execution)){
				ps = conn.prepareStatement(dSql,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
			}else{
				ps = conn.prepareStatement(dSql);
			}
			int index = 1;
			ps.setString(index++,paramId);
			ps.setString(index++,paramType);
			rs = ps.executeQuery();
			if(rs.next()){
				String PARAM_ID = rs.getString("PARAM_ID");
				String PARAM_TYPE = rs.getString("PARAM_TYPE");
				String PARAM_VALUE = rs.getString("PARAM_VALUE");
				String STATUS = rs.getString("STATUS");
				String ROWID = rs.getString("ROWID");
				runningParamStack = new RunningParamStackDataM();
					runningParamStack.setParamId(PARAM_ID);
					runningParamStack.setParamType(PARAM_TYPE);
					runningParamStack.setParamValue(PARAM_VALUE);
					runningParamStack.setStatus(STATUS);
					runningParamStack.setRowId(ROWID);
			}
			if(!Util.empty(runningParamStack)){
				int rowCount = 0;
				while(rs.next()){
					rowCount++;
				}
				log.debug("rowCount : "+rowCount);
				BigDecimal REMAINING_PERCENT = MathUtil.findPercentRemaining(new BigDecimal(rowCount),new BigDecimal(RUNNING_PARAM_STACK_FETCH_ROW));
				log.debug("REMAINING_PERCENT : "+REMAINING_PERCENT);
				runningParamStack.setRemainingPercent(REMAINING_PERCENT);
			}
		}catch(Exception e){
			log.fatal(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				log.fatal(e.getMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
		return runningParamStack;
	}
	private void updateStatusRunningParamStack(RunningParamStackDataM runningParamStack,Connection conn)throws UniqueIDGeneratorException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE RUNNING_PARAM_STACK SET STATUS = ? ");
			sql.append(" WHERE ROWID = ? ");
			String dSql = String.valueOf(sql);
			log.debug("Sql : " + dSql);
			log.debug("Status : "+runningParamStack.getStatus());
			log.debug("RowId : "+runningParamStack.getRowId());
			ps = conn.prepareStatement(dSql);
				int index = 1;
				ps.setString(index++, runningParamStack.getStatus());
				ps.setString(index++, runningParamStack.getRowId());
			ps.executeUpdate();
		}catch (Exception e){
			log.fatal(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				log.fatal(e.getMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
	}
	private void enableNextRunningParamStack(String paramId,String paramType,Connection conn)throws UniqueIDGeneratorException{
		String RUNNING_PARAM_STACK_FETCH_ROW = SystemConstant.getConstant("RUNNING_PARAM_STACK_FETCH_ROW");
		String FLAG_NO = SystemConstant.getConstant("FLAG_NO");
		String FLAG_DISABLE = SystemConstant.getConstant("FLAG_DISABLE");
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE RUNNING_PARAM_STACK SET STATUS = '"+FLAG_NO+"' ");
				sql.append(" WHERE ROWID IN ( ");
				sql.append("         SELECT ROWID FROM ( ");
				sql.append("                 SELECT ROWID ");
				sql.append("                 FROM RUNNING_PARAM_STACK WHERE PARAM_ID = ? AND PARAM_TYPE = ? AND STATUS = '"+FLAG_DISABLE+"' ");
				sql.append("                 ORDER BY PARAM_VALUE ");
				sql.append("         ) WHERE ROWNUM <= "+RUNNING_PARAM_STACK_FETCH_ROW+" ");
				sql.append(" ) ");
			String dSql = String.valueOf(sql);
			log.debug("Sql : " + dSql);
			log.debug("paramId : "+paramId);
			log.debug("paramType : "+paramType);
			ps = conn.prepareStatement(dSql);
				int index = 1;
				ps.setString(index++, paramId);
				ps.setString(index++, paramType);
			ps.executeUpdate();
		}catch(Exception e){
			log.fatal(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				log.fatal(e.getMessage());
				throw new UniqueIDGeneratorException(e.getMessage());
			}
		}
	}
	@Override
	public String getCardNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException {
		String cardBinNo = "";
		String cardParam = "";
		String runningCode = "";
		String chkDigits = "";
		try{
			/** CardBinNo **/
			cardBinNo = getCardBinNo(card.getCardType());
			/** Running Number **/
			Map<String,String> cardParamMap = getCardParamValues(card.getCardType(),MConstant.CARD_NO_GEN.CARD_NO);
			String value1 = cardParamMap.get("VALUE1");
			String value2 = cardParamMap.get("VALUE2");
			cardParam = value1;
			logger.info("##### Getting Running Param CardType " + card.getCardType() + " : "+ cardParam);
			if (Util.empty(cardParam)) {
				throw new UniqueIDGeneratorException("Can not map data with table named CARD_PARAM(" + card.getCardType() + ")");
			}
			runningCode = getGenerateRunningNo(cardParam, MConstant.CARD_NO_GEN.PARAM_TYPE_CARD,value2, conn);
			runningCode = EjbUtil.appendZero(runningCode, 15 - cardBinNo.length());
			/** Check digits **/
			chkDigits = findChkDigit(cardBinNo + runningCode );
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + cardParam);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return cardBinNo + runningCode + chkDigits;
	}
	@Override
	public String getCardLinkNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException {
		String prefix = "";
		String cardParam = "";
		String runningCode = "";
		String chkDigits = "";
		try{
			/** Running Number **/
			Map<String,String> cardParamMap = getCardParamValues(card.getCardType(), MConstant.CARD_NO_GEN.CARDLINK_CUST_NO);
			cardParam = cardParamMap.get("VALUE1");
			String value2 = cardParamMap.get("VALUE2");
			logger.info("##### Getting Running Param CardType " + card.getCardType() + " : "+ cardParam);
			if (Util.empty(cardParam)) {
				throw new UniqueIDGeneratorException("Can not map data with table named CARD_PARAM(" + card.getCardType() + ")");
			}
			/** Prefix  **/
			prefix = getPrefixForCardLink(cardParam, MConstant.CARD_NO_GEN.CARDLINK_CUST_NO);
			int prefixLength = prefix.length();
			runningCode = getGenerateRunningNo(cardParam, MConstant.CARD_NO_GEN.CARDLINK_CUST_NO, value2, conn);
			runningCode = EjbUtil.appendZero(runningCode, 15 - prefixLength);
			/** Check digits **/
			chkDigits = findChkDigit(prefix + runningCode );
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + cardParam);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return prefix + runningCode + chkDigits;
	}
	@Override
	public String getHisHerMembershipNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException {
		String runningCode = "";
		try{
			runningCode = getGenerateRunningNo(MConstant.CARD_NO_GEN.HH_MEMBERSHIP_NO, MConstant.CARD_NO_GEN.PARAM_TYPE_MEMNO, conn);
			runningCode = EjbUtil.appendZero(runningCode, 13);
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + MConstant.CARD_NO_GEN.HH_MEMBERSHIP_NO);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return runningCode;
	}
	@Override
	public String getPriorityPassNo(CardDataM card, Connection conn) throws UniqueIDGeneratorException {
		String prefix = "";
		String cardParam = "";
		String runningCode = "";
		String chkDigits = "";
		try{
			Map<String,String> cardParamMap = getCardParamValues("PP",MConstant.CARD_NO_GEN.PARAM_CODE_PP_NO);
			cardParam = cardParamMap.get("VALUE1");
			String value2 = cardParamMap.get("VALUE2");
			logger.info("##### Getting Running Param CardType PP : "+ cardParam);
			if (Util.empty(cardParam)) {
				throw new UniqueIDGeneratorException("Can not map data with table named CARD_PARAM(" + card.getCardType() + ")");
			}
			/** Prefix  **/
			prefix = getPrefixForCardLink(cardParam, MConstant.CARD_NO_GEN.PP_CARD_NO);
			runningCode = getGenerateRunningNo(cardParam, MConstant.CARD_NO_GEN.PP_CARD_NO, value2, conn);
			runningCode = EjbUtil.appendZero(runningCode, 7);
			/** Check digits **/
			chkDigits = findChkDigit(prefix + runningCode );
		}catch(Exception e){
			logger.info("##### Err : in getCardNo ApplyType(" + card.getCardType() + ") " + MConstant.CARD_NO_GEN.PP_CARD_NO);
			logger.error(e.getMessage());
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return prefix + runningCode + chkDigits;
	}
	
	@Override
	public String getGenerateRunningNoStack(String paramId, String paramType, Connection conn) throws UniqueIDGeneratorException {
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		String runningParamStackPercentRemaining = SystemConstant.getConstant("RUNNING_PARAM_STACK_PERCENT_REMAINING");
		BigDecimal RUNNING_PARAM_STACK_PERCENT_REMAINING = new BigDecimal(runningParamStackPercentRemaining);
		String memberShipNo = "";
		try{
			RunningParamStackDataM runningParamStack = selectRunningParamStack(paramId,paramType,conn);
			if(Util.empty(runningParamStack) || RUNNING_PARAM_STACK_PERCENT_REMAINING.compareTo(runningParamStack.getRemainingPercent()) > 0 || RUNNING_PARAM_STACK_PERCENT_REMAINING.compareTo(runningParamStack.getRemainingPercent())==0){
				enableNextRunningParamStack(paramId,paramType,conn);
				runningParamStack = selectRunningParamStack(paramId,paramType,conn);
			}
			if(!Util.empty(runningParamStack)){
				String paramValue = runningParamStack.getParamValue();
				if(!Util.empty(paramValue)){
					memberShipNo = paramValue;
					runningParamStack.setStatus(FLAG_YES);
					updateStatusRunningParamStack(runningParamStack,conn);
				}
			}else{
				throw new UniqueIDGeneratorException("Running Param Stack is Full.!!");
			}
			if(Util.empty(memberShipNo)){
				throw new UniqueIDGeneratorException("Running Param Stack is Full.!!");
			}
		}catch (Exception e){
			log.fatal("ERROR",e);
			throw new UniqueIDGeneratorException(e.getMessage());
		}
		return memberShipNo;
	}
	
}