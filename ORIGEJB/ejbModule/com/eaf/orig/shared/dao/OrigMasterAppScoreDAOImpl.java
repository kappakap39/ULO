/*
 * Created on Dec 11, 2007
 * Created by Prawit Limwattanachai
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.master.shared.model.ScoreCharacterM;
import com.eaf.orig.master.shared.model.ScoreM;
import com.eaf.orig.master.shared.model.ScoreTypeM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.exceptions.OrigApplicationMException;

/**
 * @author Administrator
 *
 * Type: OrigMasterAppScoreDAOImpl
 */
public class OrigMasterAppScoreDAOImpl extends OrigObjectDAO implements
		OrigMasterAppScoreDAO {
	Logger log = Logger.getLogger(OrigMasterAppScoreDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterAppScoreDAO#getOriginalScoreM(java.lang.String)
	 */
	public ScoreM getOriginalScoreM(String cusType)
			throws OrigApplicationMException {
		ScoreM scoreM = new ScoreM();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT ACCEPT_SCORE, REJECT_SCORE, SCORING_CONSTANT, ACCEPT_SCORE_USED_CAR, REJECT_SCORE_USED_CAR ");
			sql.append(" FROM SCORING  WHERE CUSTOMER_TYPE = ? AND SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_TYPE S WHERE  S.CUSTOMER_TYPE = ? )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("cusType=" + cusType);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, cusType);
			ps.setString(2, cusType);

			rs = ps.executeQuery();
			
			if(rs.next()) {
				scoreM = new ScoreM(); 
				scoreM.setAcceptScore(rs.getDouble(1));
				scoreM.setRejectScore(rs.getDouble(2));
				scoreM.setScoreConstant(rs.getDouble(3));
				scoreM.setAcceptScore_used(rs.getDouble(4));
				scoreM.setRejectScore_used(rs.getDouble(5));
			}
			return scoreM;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterAppScoreDAO#createModelOrigMasterAppScore(com.eaf.orig.master.shared.model.ScoreM)
	 */
	public void createModelOrigMasterAppScore(ScoreM scoreM)
			throws OrigApplicationMException {
		
		saveScoreM(scoreM);
		
		Vector scoreTypeVect = scoreM.getScoreTypeMVect();
		
		if(scoreTypeVect!=null && scoreTypeVect.size()>0){
			ScoreTypeM scoreTypeM;
			for(int i=0;i<scoreTypeVect.size();i++){
				scoreTypeM = (ScoreTypeM)scoreTypeVect.get(i);
				saveScoreTypeM(scoreTypeM);//********** save scoreType
			}
		}
		
		if(scoreTypeVect!=null && scoreTypeVect.size()>0){
			ScoreTypeM scoreTypeM;
			for(int i=0;i<scoreTypeVect.size();i++){
				scoreTypeM = (ScoreTypeM)scoreTypeVect.get(i);
				Vector charTypeVect = scoreTypeM.getCharTypeMVect();
				
				if(charTypeVect!=null && charTypeVect.size()>0){
					ScoreCharacterM characterM;
					for(int j=0;j<charTypeVect.size();j++){
						characterM = (ScoreCharacterM)charTypeVect.get(j);
						
						if("S".equals(characterM.getCharType())){
							savecharTypeMAsS(characterM);//********** save charType as charType S
						}else if("R".equals(characterM.getCharType())){
							savecharTypeMAsR(characterM);//********** save charType as charType R
						}else{
							savecharTypeMAsN(characterM);//********** save charType as charType N
						}
						
					}
				}
			}
		}
		

	}
	
	public void savecharTypeMAsN(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO SCORING_CHARECTERISTIC ");
			sql.append(" (CHAR_CODE, CHAR_DESCRIPTION, SCORE_CODE, MIN_RANGE, MAX_RANGE, SPECIFIC, SCORE, SCORING_SEQ, SEQ, CHAR_TYPE, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE ) ");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?,?,  ?,?,SYSDATE,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, characterM.getCharCode());
			ps.setString(2, characterM.getCharDesc());
			ps.setString(3, characterM.getScoreCode());
			ps.setDouble(4, characterM.getMinRange());
			ps.setDouble(5, characterM.getMaxRange());
			ps.setString(6, characterM.getSpecific());
			ps.setDouble(7, characterM.getScore());
			ps.setInt(8, characterM.getScoreSeq());
			ps.setInt(9, characterM.getSeq());
			ps.setString(10, characterM.getCharType());
			ps.setString(11, characterM.getCreateBy());
			ps.setString(12, characterM.getUpdateBy());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}

}
	
	public void savecharTypeMAsR(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO SCORING_CHARECTERISTIC ");
			sql.append(" (CHAR_CODE, CHAR_DESCRIPTION, SCORE_CODE, MIN_RANGE, MAX_RANGE, SCORE, SCORING_SEQ, SEQ, CHAR_TYPE, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE ) ");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?,?,  ?,SYSDATE,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, characterM.getCharCode());
			ps.setString(2, characterM.getCharDesc());
			ps.setString(3, characterM.getScoreCode());
			ps.setDouble(4, characterM.getMinRange());
			ps.setDouble(5, characterM.getMaxRange());
			ps.setDouble(6, characterM.getScore());
			ps.setInt(7, characterM.getScoreSeq());
			ps.setInt(8, characterM.getSeq());
			ps.setString(9, characterM.getCharType());
			ps.setString(10, characterM.getCreateBy());
			ps.setString(11, characterM.getUpdateBy());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}

}
	
	public void savecharTypeMAsS(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO SCORING_CHARECTERISTIC ");
			sql.append(" (CHAR_CODE, CHAR_DESCRIPTION, SCORE_CODE, SPECIFIC, SCORE, SCORING_SEQ, SEQ, CHAR_TYPE, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE ) ");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,?,?,  SYSDATE,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, characterM.getCharCode());
			ps.setString(2, characterM.getCharDesc());
			ps.setString(3, characterM.getScoreCode());
			ps.setString(4, characterM.getSpecific());
			ps.setDouble(5, characterM.getScore());
			ps.setInt(6, characterM.getScoreSeq());
			ps.setInt(7, characterM.getSeq());
			ps.setString(8, characterM.getCharType());
			ps.setString(9, characterM.getCreateBy());
			ps.setString(10, characterM.getUpdateBy());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}

}
	
	public void saveScoreTypeM(ScoreTypeM scoreTypeM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO SCORING_TYPE ");
			sql.append(" (SCORE_CODE, SCORE_TYPE, SCORE_WEIGHT, DESCRIPTION, CUSTOMER_TYPE, SCORING_SEQ, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE ) ");
			sql.append(" VALUES (?,?,?,?,?  ,?,?,?,SYSDATE,SYSDATE)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, scoreTypeM.getScoreCode());
			ps.setString(2, scoreTypeM.getScoreType());
			ps.setDouble(3, scoreTypeM.getScoreWeight());
			ps.setString(4, scoreTypeM.getDescription());
			ps.setString(5, scoreTypeM.getCusType());
			ps.setInt(6, scoreTypeM.getScoreSeq());
			ps.setString(7, scoreTypeM.getCreateBy());
			ps.setString(8, scoreTypeM.getUpdateBy());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}

}
	
	public void saveScoreM(ScoreM scoreM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" INSERT INTO SCORING ");
			sql.append(" (CUSTOMER_TYPE, ACCEPT_SCORE, REJECT_SCORE, SCORING_SEQ, CREATE_BY, UPDATE_BY, CREATE_DATE, UPDATE_DATE, SCORING_CONSTANT, ACCEPT_SCORE_USED_CAR, REJECT_SCORE_USED_CAR ) ");
			sql.append(" VALUES (?,?,?,?,?  ,?,SYSDATE,SYSDATE,?,?,?)");
			String dSql = String.valueOf( sql);
			log.debug("dSql="+dSql);
			ps = conn.prepareStatement(dSql);
			
			ps.setString(1, scoreM.getCusType());
			ps.setDouble(2, scoreM.getAcceptScore());
			ps.setDouble(3, scoreM.getRejectScore());
			ps.setInt(4, scoreM.getScoreSeq());
			ps.setString(5, scoreM.getCreateBy());
			ps.setString(6, scoreM.getUpdateBy());
			ps.setDouble(7, scoreM.getScoreConstant());
			ps.setDouble(8, scoreM.getAcceptScore_used());
			ps.setDouble(9, scoreM.getRejectScore_used());
			
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		
}
	
	public int getMaxScoreSeq(String custype) throws OrigApplicationMException {
		int maxScoreSeq = -1;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT MAX(S.SCORING_SEQ) FROM SCORING S WHERE S.CUSTOMER_TYPE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, custype);
		
			rs = ps.executeQuery();
			ScoreCharacterM characterM;
			if(rs.next()) {
				maxScoreSeq = rs.getInt(1);
			}
			return maxScoreSeq;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public HashMap getCharTypeHashMapByScoreCode(Vector scoreTypeVect)
			throws OrigApplicationMException {
		Vector charTypeVect = new Vector();
		HashMap charTypeHash = new HashMap();
	
		if(scoreTypeVect!=null && scoreTypeVect.size()>0){
			ScoreTypeM scoreTypeM;
			ScoreCharacterM characterM;
			for(int i=0;i<scoreTypeVect.size();i++){
				scoreTypeM = (ScoreTypeM)scoreTypeVect.get(i);
				log.debug("scoreTypeM.getScoreCode()=" + scoreTypeM.getScoreCode());
				log.debug("scoreTypeM.getScoreType()=" + scoreTypeM.getScoreType());
				charTypeVect = getCharTypeVectByScoreCode(scoreTypeM);
				log.debug("charTypeVect=" + charTypeVect);
				log.debug("charTypeVect.size()=" + charTypeVect.size());
				
				if(charTypeVect!=null && charTypeVect.size()>0){
					ScoreCharacterM characterM2;
					for(int j=0;j<charTypeVect.size();j++){
						characterM2 = (ScoreCharacterM)charTypeVect.get(j);
						characterM2 = getNewSpecDesc(characterM2);
						charTypeVect.setElementAt(characterM2,j);
					}
				}
				
				charTypeHash.put(scoreTypeM.getScoreCode(),charTypeVect);
				log.debug("charTypeHash=" + charTypeHash);
			}
		}
		
		return charTypeHash;
	}
	
	public Vector getCharTypeVectByScoreCode(ScoreTypeM scoreTypeM)
		throws OrigApplicationMException {
		
		Vector charMVect = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT SC.MIN_RANGE, SC.MAX_RANGE, SC.SPECIFIC, SC.SCORE, SC.CHAR_CODE, SC.SCORE_CODE, SC.SCORING_SEQ, SC.SEQ, SC.CHAR_DESCRIPTION, SC.CHAR_TYPE ");
			sql.append(" FROM SCORING_CHARECTERISTIC SC WHERE SC.SCORE_CODE = ? AND SC.SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_CHARECTERISTIC S WHERE S.SCORE_CODE = ? )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("scoreTypeM.getScoreCode()=" + scoreTypeM.getScoreCode());
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, scoreTypeM.getScoreCode());
			ps.setString(2, scoreTypeM.getScoreCode());
		
			rs = ps.executeQuery();
			ScoreCharacterM characterM;
			while(rs.next()) {
				characterM = new ScoreCharacterM();
				characterM.setMinRange(rs.getDouble(1));
				characterM.setMaxRange(rs.getDouble(2));
				characterM.setSpecific(rs.getString(3));
				characterM.setScore(rs.getDouble(4));
				characterM.setCharCode(rs.getString(5));
				characterM.setScoreCode(rs.getString(6));
				characterM.setScoreSeq(rs.getInt(7));
				characterM.setSeq(rs.getInt(8));
				characterM.setCharDesc(rs.getString(9));
				characterM.setCharType(rs.getString(10));
				
				charMVect.add(characterM);
			}
			return charMVect;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public Vector getScoreTypeVect(String cusType)
			throws OrigApplicationMException {
		Vector scoreTypeVect = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT SC.SCORE_CODE, SC.SCORE_TYPE, SC.SCORE_WEIGHT, SC.CUSTOMER_TYPE, SC.SCORING_SEQ ");
			sql.append(" FROM SCORING_TYPE SC WHERE SC.CUSTOMER_TYPE = ? AND SC.SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_TYPE S WHERE S.CUSTOMER_TYPE = ? )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("cusType=" + cusType);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, cusType);
			ps.setString(2, cusType);
		
			rs = ps.executeQuery();
			ScoreTypeM scoreTypeM;
			while(rs.next()) {
				scoreTypeM = new ScoreTypeM();
				scoreTypeM.setScoreCode(rs.getString(1));
				scoreTypeM.setScoreType(rs.getString(2));
				scoreTypeM.setScoreWeight(rs.getDouble(3));
				scoreTypeM.setCusType(rs.getString(4));
				scoreTypeM.setScoreSeq(rs.getInt(5));
				
				scoreTypeVect.add(scoreTypeM);
			}
			return scoreTypeVect;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public ScoreCharacterM getNewSpecDesc(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		String charCode = characterM.getCharCode();
		
		if("002".equals(charCode)){
			characterM = getSpecMaritialStatus(characterM);
		}else if("003".equals(charCode)){
			characterM = getSpecEducateLevel(characterM);
		}else if("004".equals(charCode)){
			characterM = getSpecSex(characterM);
		}else if("006".equals(charCode)){
			characterM = getSpecOccupation(characterM);
		}else if("008".equals(charCode)){
			characterM = getSpecAccomStatus(characterM);
		}else if("012".equals(charCode)){
			characterM = getSpecCarBrand(characterM);
		}else if("013".equals(charCode)){
			characterM = getSpecCarType(characterM);
		}else if("020".equals(charCode)){
			characterM = getSpecNPL1(characterM);
		}else if("023".equals(charCode)){
			characterM = getSpecOfficeType(characterM);
		}else if("029".equals(charCode)){
			characterM = getSpecCarBrand(characterM);
		}else if("030".equals(charCode)){
			characterM = getSpecCarType(characterM);
		}else if("036".equals(charCode)){
			characterM = getSpecNPL2(characterM);
		}
		
		if(characterM.getSpecDesc()==null){
			characterM.setSpecDesc("");
		}
		
		return characterM;
	}
	
	public Vector getCharTypeSpecificVect(String scoreCode,
			String charCode) throws OrigApplicationMException {
		Vector charTypeSpecificVect = new Vector();
		
//		Vector charTypeSpecificVect = getCharTypeVect(scoreSeq, scoreCode, charCode);
		if("002".equals(charCode) || "003".equals(charCode) || "004".equals(charCode) || "006".equals(charCode)
				|| "008".equals(charCode) || "012".equals(charCode) || "013".equals(charCode) || "020".equals(charCode)
				|| "023".equals(charCode) || "029".equals(charCode) || "030".equals(charCode) || "036".equals(charCode)){
			
			charTypeSpecificVect = getCharM(scoreCode,charCode);
		}
		
		if(charTypeSpecificVect!=null && charTypeSpecificVect.size()>0){
			ScoreCharacterM characterM;
			for(int i=0;i<charTypeSpecificVect.size();i++){
				characterM = (ScoreCharacterM)charTypeSpecificVect.get(i);
				
				if("002".equals(charCode)){
					characterM = getSpecMaritialStatus(characterM);
				}else if("003".equals(charCode)){
					characterM = getSpecEducateLevel(characterM);
				}else if("004".equals(charCode)){
					characterM = getSpecSex(characterM);
				}else if("006".equals(charCode)){
					characterM = getSpecOccupation(characterM);
				}else if("008".equals(charCode)){
					characterM = getSpecAccomStatus(characterM);
				}else if("012".equals(charCode)){
					characterM = getSpecCarBrand(characterM);
				}else if("013".equals(charCode)){
					characterM = getSpecCarType(characterM);
				}else if("020".equals(charCode)){
					characterM = getSpecNPL1(characterM);
				}else if("023".equals(charCode)){
					characterM = getSpecOfficeType(characterM);
				}else if("029".equals(charCode)){
					characterM = getSpecCarBrand(characterM);
				}else if("030".equals(charCode)){
					characterM = getSpecCarType(characterM);
				}else if("036".equals(charCode)){
					characterM = getSpecNPL2(characterM);
				}
				
				if(characterM.getSpecDesc()==null){
					characterM.setSpecDesc("");
				}
				
			}
		}
		
		return charTypeSpecificVect;
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterAppScoreDAO#getCharTypeMEdit(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ScoreCharacterM getCharTypeMEdit(String scoreSeq, String seq,
			String scoreCode, String charCode) throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT SCORING_SEQ, SEQ, SCORE_CODE, CHAR_CODE, MIN_RANGE, MAX_RANGE, SPECIFIC, SCORE, CHAR_TYPE ");
			sql.append(" FROM SCORING_CHARECTERISTIC  WHERE SCORING_SEQ = ? AND SEQ = ? AND SCORE_CODE = ? AND CHAR_CODE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("scoreSeq=" + scoreSeq);
			log.debug("seq=" + seq);
			log.debug("scoreCode=" + scoreCode);
			log.debug("charCode=" + charCode);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, scoreSeq);
			ps.setString(2, seq);
			ps.setString(3, scoreCode);
			ps.setString(4, charCode);

			rs = ps.executeQuery();
			
			ScoreCharacterM characterM = new ScoreCharacterM();
			if(rs.next()) {
				characterM.setScoreSeq(rs.getInt(1));
				characterM.setSeq(rs.getInt(2));
				characterM.setScoreCode(rs.getString(3));
				characterM.setCharCode(rs.getString(4));
				characterM.setMinRange(rs.getDouble(5));
				characterM.setMaxRange(rs.getDouble(6));
				characterM.setSpecific(rs.getString(7));
				characterM.setScore(rs.getDouble(8));
				characterM.setCharType(rs.getString(9));
			}
			return characterM;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	
	public Vector selectCharM(String scoreCode, String charCode)
			throws OrigApplicationMException {
		Vector charMVect = new Vector();
		
		charMVect = getCharM(scoreCode, charCode);
		
		if(charMVect!=null && charMVect.size()>0){
			ScoreCharacterM characterM;
			for(int i=0;i<charMVect.size();i++){
				characterM = (ScoreCharacterM)charMVect.get(i);
				
				if("002".equals(charCode)){
					characterM = getSpecMaritialStatus(characterM);
				}else if("003".equals(charCode)){
					characterM = getSpecEducateLevel(characterM);
				}else if("004".equals(charCode)){
					characterM = getSpecSex(characterM);
				}else if("006".equals(charCode)){
					characterM = getSpecOccupation(characterM);
				}else if("008".equals(charCode)){
					characterM = getSpecAccomStatus(characterM);
				}else if("012".equals(charCode)){
					characterM = getSpecCarBrand(characterM);
				}else if("013".equals(charCode)){
					characterM = getSpecCarType(characterM);
				}else if("020".equals(charCode)){
					characterM = getSpecNPL1(characterM);
				}else if("023".equals(charCode)){
					characterM = getSpecOfficeType(characterM);
				}else if("029".equals(charCode)){
					characterM = getSpecCarBrand(characterM);
				}else if("030".equals(charCode)){
					characterM = getSpecCarType(characterM);
				}else if("036".equals(charCode)){
					characterM = getSpecNPL2(characterM);
				}
				
				if(characterM.getSpecDesc()==null){
					characterM.setSpecDesc("");
				}
				
			}
		}
		
		return charMVect;
	}
	
	public ScoreCharacterM getSpecNPL2(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CHOICE_NO, DISPLAY_NAME ");
			sql.append(" FROM LIST_BOX_MASTER  WHERE FIELD_ID = 18 AND CHOICE_NO = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecNPL1(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CHOICE_NO, DISPLAY_NAME ");
			sql.append(" FROM LIST_BOX_MASTER  WHERE FIELD_ID = 17 AND CHOICE_NO = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecAccomStatus(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT ADRSTS, THDESC ");
			sql.append(" FROM HPTBHP11  WHERE CMPCDE = "+OrigConstant.ORIG_CMPCODE+" AND ADRSTS = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecSex(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT PRMCDE, THDESC ");
			sql.append(" FROM HPTBHP91  WHERE PRMTYP = 'GENDERCDE' AND PRMCDE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecOfficeType(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CHOICE_NO, DISPLAY_NAME ");
			sql.append(" FROM LIST_BOX_MASTER  WHERE FIELD_ID = 6 AND CHOICE_NO = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecCarType(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CATEGORY, TH_DESC ");
			sql.append(" FROM MS_CAR_CATEGORY_TYPE  WHERE CATEGORY = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecCarBrand(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT BRAND, TH_DESC ");
			sql.append(" FROM MS_CAR_BRAND  WHERE BRAND = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecOccupation(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT OCCCDE, THDESC ");
			sql.append(" FROM HPTBHP08  WHERE OCCCDE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecEducateLevel(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CHOICE_NO, DISPLAY_NAME ");
			sql.append(" FROM LIST_BOX_MASTER  WHERE FIELD_ID = 2 AND CHOICE_NO = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public ScoreCharacterM getSpecMaritialStatus(ScoreCharacterM characterM)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT MARCDE, THDESC ");
			sql.append(" FROM HPTBHP06  WHERE MARCDE = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, characterM.getSpecific());
		
			rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString(1).equals(characterM.getSpecific())){
					characterM.setSpecDesc(rs.getString(2));
				}
			}
			return characterM;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public Vector getCharM(String scoreCode, String charCode)
			throws OrigApplicationMException {
		Vector charMVect = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT SC.MIN_RANGE, SC.MAX_RANGE, SC.SPECIFIC, SC.SCORE, SC.CHAR_CODE, SC.SCORE_CODE, SC.SCORING_SEQ, SC.SEQ, SC.CHAR_DESCRIPTION, SC.CHAR_TYPE ");
			sql.append(" FROM SCORING_CHARECTERISTIC SC WHERE SC.SCORE_CODE = ? AND SC.CHAR_CODE = ? AND SC.SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_CHARECTERISTIC S WHERE S.SCORE_CODE = ? AND S.CHAR_CODE = ? )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("scoreCode=" + scoreCode);
			log.debug("charCode=" + charCode);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, scoreCode);
			ps.setString(2, charCode);
			ps.setString(3, scoreCode);
			ps.setString(4, charCode);
		
			rs = ps.executeQuery();
			ScoreCharacterM characterM;
			while(rs.next()) {
				characterM = new ScoreCharacterM();
				characterM.setMinRange(rs.getDouble(1));
				characterM.setMaxRange(rs.getDouble(2));
				characterM.setSpecific(rs.getString(3));
				characterM.setScore(rs.getDouble(4));
				characterM.setCharCode(rs.getString(5));
				characterM.setScoreCode(rs.getString(6));
				characterM.setScoreSeq(rs.getInt(7));
				characterM.setSeq(rs.getInt(8));
				characterM.setCharDesc(rs.getString(9));
				characterM.setCharType(rs.getString(10));
				
				charMVect.add(characterM);
			}
			return charMVect;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public String getCusThaiDesc(String cusType)
			throws OrigApplicationMException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT THDESC ");
			sql.append(" FROM HPTBHP01  WHERE CUSTYP = ? ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("cusType=" + cusType);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, cusType);
		
			rs = ps.executeQuery();
			
			String cusThaiDesc="";
			if(rs.next()) {
				cusThaiDesc = rs.getString(1);
			}
			return cusThaiDesc;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	public Vector getCharTypeVect(String scoreCode)
			throws OrigApplicationMException {
		Vector charTypeMVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT DISTINCT CHAR_CODE, CHAR_DESCRIPTION ");
			sql.append(" FROM SCORING_CHARECTERISTIC  WHERE SCORE_CODE = ? AND SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_CHARECTERISTIC S WHERE  S.SCORE_CODE = ? ) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("scoreCode=" + scoreCode);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, scoreCode);
			ps.setString(2, scoreCode);
		
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ScoreCharacterM characterM = new ScoreCharacterM(); 
				characterM.setCharCode(rs.getString(1));
				characterM.setCharDesc(rs.getString(2));
				charTypeMVect.add(characterM);
			}
			return charTypeMVect;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
}
	
	public Vector getScoreFacterType(String cusType)
			throws OrigApplicationMException {
		Vector scoreTypeMVect = new Vector() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT SCORE_CODE, SCORE_TYPE ");
			sql.append(" FROM SCORING_TYPE  WHERE CUSTOMER_TYPE = ? AND SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_TYPE S WHERE  S.CUSTOMER_TYPE = ?)");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("cusType=" + cusType);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, cusType);
			ps.setString(2, cusType);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				ScoreTypeM scoreTypeM = new ScoreTypeM(); 
				scoreTypeM.setScoreCode(rs.getString(1));
				scoreTypeM.setScoreType(rs.getString(2));
				scoreTypeMVect.add(scoreTypeM);
			}
			return scoreTypeMVect;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterAppScoreDAO#getOriginalCharTypeHash(java.util.Vector)
	 */
	public HashMap getOriginalCharTypeHash(Vector scoreCodeVect)
			throws OrigApplicationMException {
		HashMap charTypeHash = new HashMap() ;
		
		if(scoreCodeVect!=null && scoreCodeVect.size()>0){
			String scoreCode;
			Vector charTypeVect;
			for(int i=0;i<scoreCodeVect.size();i++){
				charTypeVect = new Vector();
				scoreCode = (String)scoreCodeVect.get(i);
				log.debug("scoreCode=" + scoreCode);
				
				charTypeVect = getCharTypeVect(scoreCode);
				
				if(charTypeVect!=null && charTypeVect.size()>0){
					log.debug("charTypeVect.size()=" + charTypeVect.size());
					ScoreCharacterM characterM;
					for(int j=0;j<charTypeVect.size();j++){
						characterM = (ScoreCharacterM)charTypeVect.get(j);
						
						charTypeHash.put(characterM.getCharCode(),"xxx");
					}
				}
				
			}
			log.debug("!!!! charTypeHash.size()=" + charTypeHash.size());
		}
		
		if(charTypeHash!=null && charTypeHash.size()>0){
			
			Set chTypekeySet = charTypeHash.keySet();
			Iterator chTypekeyIt = chTypekeySet.iterator();
			String chTypekey;
			Vector charTypeVect;
			
			while(chTypekeyIt.hasNext()){
				chTypekey = (String)chTypekeyIt.next();
				 
				charTypeVect = new Vector();
				charTypeVect = getCharTypeVectByCharCode(chTypekey);
				
				//*** Get SpecDesc
				if(charTypeVect!=null && charTypeVect.size()>0){
					ScoreCharacterM characterM;
					for(int i=0;i<charTypeVect.size();i++){
						characterM = (ScoreCharacterM)charTypeVect.get(i);
						characterM = getNewSpecDesc(characterM);
						
						charTypeVect.setElementAt(characterM,i);
					}
				}
				
				charTypeHash.put(chTypekey,charTypeVect);
			}
			log.debug("!!!! Last charTypeHash.size()=" + charTypeHash.size());
		}
		
		return charTypeHash;
	}
	
	public Vector getCharTypeVectByCharCode(String charCode)
			throws OrigApplicationMException {
		Vector charTypeVect = new Vector();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT CHAR_CODE, CHAR_DESCRIPTION, SCORE_CODE, MIN_RANGE, MAX_RANGE, SPECIFIC, SCORE, SCORING_SEQ, SEQ, CHAR_TYPE ");
			sql.append(" FROM SCORING_CHARECTERISTIC  WHERE CHAR_CODE = ? AND SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_CHARECTERISTIC S  WHERE S.CHAR_CODE = ? ) ");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("charCode=" + charCode);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, charCode);
			ps.setString(2, charCode);
		
			rs = ps.executeQuery();
			
			ScoreCharacterM characterM; 
			
			while(rs.next()) {
				characterM = new ScoreCharacterM();
				characterM.setCharCode(rs.getString(1));
				characterM.setCharDesc(rs.getString(2));
				characterM.setScoreCode(rs.getString(3));
				characterM.setMinRange(rs.getDouble(4));
				characterM.setMaxRange(rs.getDouble(5));
				characterM.setSpecific(rs.getString(6));
				characterM.setScore(rs.getDouble(7));
				characterM.setScoreSeq(rs.getInt(8));
				characterM.setSeq(rs.getInt(9));
				characterM.setCharType(rs.getString(10));
				
				charTypeVect.add(characterM);
			}
			return charTypeVect;
		
		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.eaf.orig.shared.dao.OrigMasterAppScoreDAO#getOriginalScoreTypeHash(java.lang.String)
	 */
	public HashMap getOriginalScoreTypeHash(String cusType)
			throws OrigApplicationMException {
		HashMap scoreTypeHash = new HashMap() ;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			
			sql.append(" SELECT SCORE_CODE, SCORE_TYPE, SCORE_WEIGHT, DESCRIPTION, CUSTOMER_TYPE, SCORING_SEQ ");
			sql.append(" FROM SCORING_TYPE  WHERE CUSTOMER_TYPE = ? AND SCORING_SEQ = (SELECT MAX(S.SCORING_SEQ) FROM SCORING_TYPE S WHERE  S.CUSTOMER_TYPE = ? )");
			
			String dSql = String.valueOf(sql);
			log.debug("Sql=" + dSql);
			log.debug("cusType=" + cusType);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, cusType);
			ps.setString(2, cusType);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				ScoreTypeM scoreTypeM = new ScoreTypeM(); 
				scoreTypeM.setScoreCode(rs.getString(1));
				scoreTypeM.setScoreType(rs.getString(2));
				scoreTypeM.setScoreWeight(rs.getDouble(3));
				scoreTypeM.setDescription(rs.getString(4));
				scoreTypeM.setCusType(rs.getString(5));
				scoreTypeM.setScoreSeq(rs.getInt(6));
				
				scoreTypeHash.put(rs.getString(1),scoreTypeM);
			}
			return scoreTypeHash;

		} catch (Exception e) {
			log.fatal("",e);
			throw new OrigApplicationMException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				log.fatal("",e);
			}
		}
		
	}
}
