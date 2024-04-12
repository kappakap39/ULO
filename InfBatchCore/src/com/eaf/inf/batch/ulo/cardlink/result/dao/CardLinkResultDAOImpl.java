package com.eaf.inf.batch.ulo.cardlink.result.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.postapproval.CJDCardLinkAction;
import com.eaf.core.ulo.common.postapproval.CardLinkAction.CardLinkRow;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.cardlink.result.model.CardLinkResultConditionDataM;
import com.eaf.inf.batch.ulo.cardlink.result.model.InfCardLinkResultDataM;
import com.eaf.inf.batch.ulo.inf.text.DecryptionTextFileCore;
import com.eaf.orig.ulo.constant.MConstant;

public class CardLinkResultDAOImpl extends InfBatchObjectDAO implements CardLinkResultDAO{
 private static transient Logger logger = Logger.getLogger(CardLinkResultDAOImpl.class);
 	String CARDLINK_ACCOUNT_SETUP_MODULE_ID = SystemConfig.getProperty("CARDLINK_ACCOUNT_SETUP_MODULE_ID");
 	String JOBSTATE_APPROVED = SystemConstant.getConstant("JOBSTATE_APPROVED");
	public void insertInfCardLinkResult(InfCardLinkResultDataM  infCardLinkResult,Connection conn)  throws InfBatchException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");			
			sql.append("INSERT INTO INF_CARDLINK_RESULT ");
			sql.append(" (RESULT_ID, PROCESSIONG_DATE, REF_ID, MAIN_CUST_NO, MAIN_CUST_RESP_CODE, ");
			sql.append(" MAIN_CUST_RESP_DESC, SUP_CUST_NO, SUP_CUST_RESP_CODE, SUP_CUST_RESP_DESC, MAIN_CARD_NO1, ");
			sql.append(" MAIN_CARD_RESP_CODE1, MAIN_CARD_RESP_DESC1, MAIN_CARD_NO2 , MAIN_CARD_RESP_CODE2 , MAIN_CARD_RESP_DESC2 ,");
			sql.append(" MAIN_CARD_NO3 , MAIN_CARD_RESP_CODE3 , MAIN_CARD_RESP_DESC3 , SUP_CARD_NO1 ,SUP_CARD_RESP_CODE1 ,");
			sql.append(" SUP_CARD_RESP_DESC1 , SUP_CARD_NO2 , SUP_CARD_RESP_CODE2 ,SUP_CARD_RESP_DESC2 , SUP_CARD_NO3 ,");
			sql.append(" SUP_CARD_RESP_CODE3 ,SUP_CARD_RESP_DESC3 , STATUS ) ");
			sql.append(" SELECT INF_BATCH_LOG_PK.NEXTVAL, SYSDATE, ?, ?, ?, ");
			sql.append(" ?,?,?,?,?, ");
			sql.append(" ?,?,?,?,?, ");
			sql.append(" ?,?,?,?,?, ");
			sql.append(" ?,?,?,?,?, ");
			sql.append(" ?,?,? ");	 
			sql.append(" FROM DUAL");
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			int index = 1;
			ps.setString(index++,infCardLinkResult.getRefId());
			ps.setString(index++,infCardLinkResult.getMainCustNo());
			ps.setString(index++,infCardLinkResult.getMainCustRespCode());
			
			ps.setString(index++,infCardLinkResult.getMainCustRespDesc());
			ps.setString(index++,infCardLinkResult.getSupCustNo());
			ps.setString(index++,infCardLinkResult.getSupCustRespCode());
			ps.setString(index++,infCardLinkResult.getSupCustRespDesc());
			ps.setString(index++,infCardLinkResult.getEncryptMainCardNo1());
			
			ps.setString(index++,infCardLinkResult.getMainCardRespCode1());
			ps.setString(index++,infCardLinkResult.getMainCardRespDesc1());
			ps.setString(index++,infCardLinkResult.getEncryptMainCardNo2());
			ps.setString(index++,infCardLinkResult.getMainCardRespCode2());
			ps.setString(index++,infCardLinkResult.getMainCardRespDesc2());
			
			ps.setString(index++,infCardLinkResult.getEncryptMainCardNo3());
			ps.setString(index++,infCardLinkResult.getMainCardRespCode3());
			ps.setString(index++,infCardLinkResult.getMainCardRespDesc3());
			ps.setString(index++,infCardLinkResult.getEncryptSupCardNo1());
			ps.setString(index++,infCardLinkResult.getSupCardRespCode1());
			
			ps.setString(index++,infCardLinkResult.getSupCardRespDesc1());
			ps.setString(index++,infCardLinkResult.getEncryptSupCardNo2());
			ps.setString(index++,infCardLinkResult.getSupCardRespCode2());
			ps.setString(index++,infCardLinkResult.getSupCardRespDesc2());
			ps.setString(index++,infCardLinkResult.getEncryptSupCardNo3());
			
			ps.setString(index++,infCardLinkResult.getSupCardRespCode3());
			ps.setString(index++,infCardLinkResult.getSupCardRespDesc3());
			ps.setString(index++,infCardLinkResult.getStatus());
						
			ps.executeUpdate();
			
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	 }
	@Override
	public void updateCardLinkFlag(InfCardLinkResultDataM infCardLinkResult,Connection conn)throws InfBatchException{
		updateCardLinkFlag(infCardLinkResult, conn, false);
	}
	
	@Override
	public void updateCardLinkFlag(InfCardLinkResultDataM infCardLinkResult,Connection conn, boolean sendtoMLS)throws InfBatchException{
		try{
			String  cardLinkRefNo = infCardLinkResult.getRefId();
			String mainCustNo = infCardLinkResult.getMainCustNo();
			String supCustNo = infCardLinkResult.getSupCustNo();
			logger.debug("cardLinkRefNo : "+cardLinkRefNo);
			logger.debug("mainCustNo : "+mainCustNo);
			logger.debug("supCustNo : "+supCustNo);
			CardLinkResultConditionDataM cardLinkResultCondition = null;
			if(!InfBatchUtil.empty(mainCustNo)){
				String mainCardLinkCustId="";
				if(!InfBatchUtil.empty(infCardLinkResult.getMainCardNo1())){
					cardLinkResultCondition = selectCardLinkResultCondition(mainCustNo, cardLinkRefNo,infCardLinkResult.getEncryptMainCardNo1(),conn);
					updateCardLinkFlagResult(cardLinkResultCondition,infCardLinkResult.getMainCardNo1Flag(),conn);		
					if(sendtoMLS && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationGroupId()) && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationRecordId()))
					{
						CJDCardLinkAction.sendCSRResultToMLS(cardLinkResultCondition.getApplicationGroupId(), 
						infCardLinkResult.getMainCardNo1Flag(), infCardLinkResult.getEncryptMainCardNo1(), infCardLinkResult.getMainCardNo1(), infCardLinkResult.getMainCardRespDesc1(), mainCustNo);
					}
				}
				if(!InfBatchUtil.empty(infCardLinkResult.getMainCardNo2())){
					cardLinkResultCondition = selectCardLinkResultCondition(mainCustNo, cardLinkRefNo,infCardLinkResult.getEncryptMainCardNo2(),conn);
					updateCardLinkFlagResult(cardLinkResultCondition,infCardLinkResult.getMainCardNo2Flag(),conn);
					if(sendtoMLS && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationGroupId()) && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationRecordId()))
					{
						CJDCardLinkAction.sendCSRResultToMLS(cardLinkResultCondition.getApplicationGroupId(), 
						infCardLinkResult.getMainCardNo2Flag(), infCardLinkResult.getEncryptMainCardNo2(), infCardLinkResult.getMainCardNo2(), infCardLinkResult.getMainCardRespDesc2(), mainCustNo);
					}
				}
				if(!InfBatchUtil.empty(infCardLinkResult.getMainCardNo3())){
					cardLinkResultCondition = selectCardLinkResultCondition(mainCustNo, cardLinkRefNo,infCardLinkResult.getEncryptMainCardNo3(),conn);
					if(sendtoMLS && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationGroupId()) && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationRecordId()))
					{
						CJDCardLinkAction.sendCSRResultToMLS(cardLinkResultCondition.getApplicationGroupId(), 
						infCardLinkResult.getMainCardNo3Flag(), infCardLinkResult.getEncryptMainCardNo3(), infCardLinkResult.getMainCardNo3(), infCardLinkResult.getMainCardRespDesc3(), mainCustNo);
					}
				}
				cardLinkResultCondition = selectCardLinkResultCondition(mainCustNo,cardLinkRefNo,conn);
				if (!InfBatchUtil.empty(cardLinkResultCondition)) {
					mainCardLinkCustId = cardLinkResultCondition.getCardLinkCustId();
					updateOrigCardLinkCustomerStatus(infCardLinkResult.getMainCustFlag(),mainCardLinkCustId,conn);
				}
			}
			if(!InfBatchUtil.empty(supCustNo)){
				String supCardLinkCustId="";
				if(!InfBatchUtil.empty(infCardLinkResult.getSupCardNo1())){
					cardLinkResultCondition = selectCardLinkResultCondition(supCustNo, cardLinkRefNo,infCardLinkResult.getEncryptSupCardNo1(),conn);
					updateCardLinkFlagResult(cardLinkResultCondition,infCardLinkResult.getSupCardNo1Flag(),conn);
					if(sendtoMLS && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationGroupId()) && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationRecordId()))
					{
						CJDCardLinkAction.sendCSRResultToMLS(cardLinkResultCondition.getApplicationGroupId(), infCardLinkResult.getSupCardNo1Flag(), 
						infCardLinkResult.getEncryptSupCardNo1(), infCardLinkResult.getSupCardNo1(), infCardLinkResult.getSupCardRespDesc1(), supCustNo);
					}
				}
				
				if(!InfBatchUtil.empty(infCardLinkResult.getSupCardNo2())){
					cardLinkResultCondition = selectCardLinkResultCondition(supCustNo, cardLinkRefNo,infCardLinkResult.getEncryptSupCardNo2(),conn);
					updateCardLinkFlagResult(cardLinkResultCondition,infCardLinkResult.getSupCardNo2Flag(),conn);
					if(sendtoMLS && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationGroupId()) && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationRecordId()))
					{
						CJDCardLinkAction.sendCSRResultToMLS(cardLinkResultCondition.getApplicationGroupId(), infCardLinkResult.getSupCardNo2Flag(), 
						infCardLinkResult.getEncryptSupCardNo2(), infCardLinkResult.getSupCardNo2(), infCardLinkResult.getSupCardRespDesc2(), supCustNo);
					}
				}
				
				if(!InfBatchUtil.empty(infCardLinkResult.getSupCardNo3())){
					cardLinkResultCondition = selectCardLinkResultCondition(supCustNo, cardLinkRefNo,infCardLinkResult.getEncryptSupCardNo3(),conn);
					updateCardLinkFlagResult(cardLinkResultCondition,infCardLinkResult.getSupCardNo3Flag(),conn);
					if(sendtoMLS && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationGroupId()) && !InfBatchUtil.empty(cardLinkResultCondition.getApplicationRecordId()))
					{
						CJDCardLinkAction.sendCSRResultToMLS(cardLinkResultCondition.getApplicationGroupId(), infCardLinkResult.getSupCardNo3Flag(), 
						infCardLinkResult.getEncryptSupCardNo3(), infCardLinkResult.getSupCardNo3(), infCardLinkResult.getSupCardRespDesc3(), supCustNo);
					}
				}
							
				cardLinkResultCondition = selectCardLinkResultCondition(supCustNo,cardLinkRefNo,conn);
				if (!InfBatchUtil.empty(cardLinkResultCondition)) {
					supCardLinkCustId = cardLinkResultCondition.getCardLinkCustId();
					updateOrigCardLinkCustomerStatus(infCardLinkResult.getSupCustFlag(),supCardLinkCustId,conn);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	}
	
	private void updateCardLinkFlagResult(CardLinkResultConditionDataM cardLinkResultCondition,String cardLinkFlag,Connection conn) throws InfBatchException{
		try{
			if(null!=cardLinkResultCondition){
				String applicationGroupId = cardLinkResultCondition.getApplicationGroupId();
				String applicationRecordId = cardLinkResultCondition.getApplicationRecordId();
				logger.debug("applicationGroupId : "+applicationGroupId);
				logger.debug("applicationRecordId : "+applicationRecordId);
				if(!InfBatchUtil.empty(applicationGroupId) && !InfBatchUtil.empty(applicationRecordId)){
					updateInfBatchLogStatus(CARDLINK_ACCOUNT_SETUP_MODULE_ID,cardLinkFlag,applicationGroupId,applicationRecordId,conn);
					updateOrigApplicationCardLinkFlag(cardLinkFlag,applicationGroupId,applicationRecordId,conn);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
	 }
		
	private void updateInfBatchLogStatus(String moduleId,String flag,String applicationGroupId,String applicationRecordId,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE INF_BATCH_LOG ");
			sql.append(" SET INTERFACE_STATUS = ? ");
			sql.append(" WHERE INTERFACE_CODE = ? AND APPLICATION_GROUP_ID=? AND APPLICATION_RECORD_ID =? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;		
			ps.setString(index++,flag);
	
			ps.setString(index++,moduleId);
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,applicationRecordId);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	private void updateOrigApplicationCardLinkFlag(String flag,String applicationGroupId,String applicationRecordId,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_APPLICATION ");
			sql.append(" SET CARDLINK_FLAG = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID=? AND APPLICATION_RECORD_ID =? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;		
			ps.setString(index++,flag);
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,applicationRecordId);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	private void updateOrigCardLinkCustomerStatus(String flag,String cardLinkCustId,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_CARDLINK_CUSTOMER ");
			sql.append(" SET CARDLINK_STATUS = ? ");
			sql.append(" WHERE CARDLINK_CUST_ID=?  ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;		
			ps.setString(index++,flag);
			
			ps.setString(index++,cardLinkCustId);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	private CardLinkResultConditionDataM selectCardLinkResultCondition(String cardLinkCustNo ,String cardLinkRefNo,String cardNoEncript,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		CardLinkResultConditionDataM cardLinkResultCondition = null;
		ResultSet rs = null;
		try{
			StringBuilder SQL = new StringBuilder("");
			SQL.append(" SELECT A.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,CUS.CARDLINK_CUST_ID  FROM ORIG_APPLICATION  A");
			SQL.append(" INNER JOIN  ORIG_LOAN L ON L.APPLICATION_RECORD_ID =  A.APPLICATION_RECORD_ID");
			SQL.append(" INNER JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID");
			SQL.append(" INNER JOIN ORIG_CARDLINK_CUSTOMER CUS ON CUS.CARDLINK_CUST_ID = C.CARDLINK_CUST_ID");
			SQL.append(" WHERE CUS.CARDLINK_CUST_NO= ? AND A.CARDLINK_REF_NO =? AND C.CARD_NO =?");
			logger.debug("SQL >> "+SQL);
			logger.debug("cardLinkCustNo>>"+cardLinkCustNo);
			logger.debug("cardLinkRefNo >> "+cardLinkRefNo);
			logger.debug("cardNoEncript >> "+cardNoEncript);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1, cardLinkCustNo);
			ps.setString(2, cardLinkRefNo);
			ps.setString(3, cardNoEncript);
			rs = ps.executeQuery();		
			if(rs.next()){
				cardLinkResultCondition = new CardLinkResultConditionDataM();
				cardLinkResultCondition.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				cardLinkResultCondition.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				cardLinkResultCondition.setCardLinkCustId(rs.getString("CARDLINK_CUST_ID"));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return cardLinkResultCondition;
	}
	
	private CardLinkResultConditionDataM selectCardLinkResultCondition(String cardLinkCustNo ,String cardLinkRefNo,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		CardLinkResultConditionDataM cardLinkResultCondition = null;
		ResultSet rs = null;
		try{
			StringBuilder SQL = new StringBuilder("");
			SQL.append(" SELECT A.APPLICATION_GROUP_ID,A.APPLICATION_RECORD_ID,CUS.CARDLINK_CUST_ID  FROM ORIG_APPLICATION  A");
			SQL.append(" INNER JOIN  ORIG_LOAN L ON L.APPLICATION_RECORD_ID =  A.APPLICATION_RECORD_ID");
			SQL.append(" INNER JOIN ORIG_CARD C ON C.LOAN_ID = L.LOAN_ID");
			SQL.append(" INNER JOIN ORIG_CARDLINK_CUSTOMER CUS ON CUS.CARDLINK_CUST_ID = C.CARDLINK_CUST_ID");
			SQL.append(" WHERE CUS.CARDLINK_CUST_NO= ? AND A.CARDLINK_REF_NO =? ");
			logger.debug("SQL >> "+SQL);
			logger.debug("cardLinkCustNo>>"+cardLinkCustNo);
			logger.debug("cardLinkRefNo >> "+cardLinkRefNo);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1, cardLinkCustNo);
			ps.setString(2, cardLinkRefNo);
			rs = ps.executeQuery();		
			if(rs.next()){
				cardLinkResultCondition = new CardLinkResultConditionDataM();
				cardLinkResultCondition.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				cardLinkResultCondition.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				cardLinkResultCondition.setCardLinkCustId(rs.getString("CARDLINK_CUST_ID"));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return cardLinkResultCondition;
	}
	@Override
	public void updateCardlinkNextDay(String applicationGroupId, Connection conn)
			throws InfBatchException {
		PreparedStatement ps = null;
		try{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_APPLICATION SET CARDLINK_FLAG = ? "); 
			sql.append(" WHERE CARDLINK_FLAG = ? AND APPLICATION_GROUP_ID = ? "); //use app grp id from isCardLinkRefNoExist
			
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;		
			ps.setString(index++,CardLinkRow.Flag.GENERATE);
			ps.setString(index++,CardLinkRow.Flag.NEXT_DAY);
			ps.setString(index++,applicationGroupId);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public String isCardLinkRefNoExist(String cardLinkRefNo, String mainCardNo, String supCardNo, Connection conn) throws InfBatchException
	{
		String applicationGroupId = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder SQL = new StringBuilder("");
			SQL.append(" SELECT DISTINCT OC.CARD_NO, AG.APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP AG ");
			SQL.append(" JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			SQL.append(" JOIN ORIG_LOAN OL ON OL.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			SQL.append(" JOIN ORIG_CARD OC ON OC.LOAN_ID = OL.LOAN_ID ");
			SQL.append(" WHERE AP.CARDLINK_REF_NO = ? ");
			SQL.append(" AND AG.JOB_STATE = ? ");				//align with PL cardsetup
			SQL.append(" AND AP.CARDLINK_FLAG IN ('Y','W') ");	//align with PL cardsetup

			logger.debug("SQL >> "+SQL);
			logger.debug("cardLinkRefNo >> "+cardLinkRefNo);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1, cardLinkRefNo);
			ps.setString(2, JOBSTATE_APPROVED);
			rs = ps.executeQuery();		
			DecryptionTextFileCore dtfc = new DecryptionTextFileCore();
			while(rs.next())
			{
				String cardNo = "<card_no>" + rs.getString("CARD_NO") + "</card_no>";
				String deCryptCardNo = dtfc.replaceCardNoWithDecryptedValue(cardNo);
				if(!Util.empty(deCryptCardNo) && 
						(deCryptCardNo.equals(mainCardNo) || deCryptCardNo.equals(supCardNo)))
				{
					applicationGroupId = rs.getString("APPLICATION_GROUP_ID");
					break;
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return applicationGroupId;
	}
	@Override
	public HashMap<String, String> selectCardLinkInfoWithAppGroupId(String cardLinkCustNo, String applicationGroupId,  Connection conn) throws InfBatchException{
		HashMap<String, String> results = new HashMap<String, String>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			StringBuilder SQL = new StringBuilder("");
			SQL.append(" SELECT PI.IDNO, CC.NEW_CARDLINK_CUST_FLAG, CC.CARDLINK_STATUS "); 
			SQL.append(" FROM ORIG_PERSONAL_INFO PI ");
			SQL.append(" JOIN ORIG_CARDLINK_CUSTOMER CC ON CC.PERSONAL_ID = PI.PERSONAL_ID ");
			SQL.append(" WHERE CC.CARDLINK_CUST_NO = ? ");
			SQL.append(" AND PI.APPLICATION_GROUP_ID = ? "); //use app grp id from isCardLinkRefNoExist
			
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			ps.setString(1, cardLinkCustNo);
			ps.setString(2, applicationGroupId);
			rs = ps.executeQuery();		
			if(rs.next())
			{
				results.put("IDNO",rs.getString("IDNO"));
				results.put("NEW_CARDLINK_CUST_FLAG",rs.getString("NEW_CARDLINK_CUST_FLAG"));
				results.put("CARDLINK_STATUS",rs.getString("CARDLINK_STATUS"));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return results;
	}
	@Override
	public void updateNewCardlinkCustFlag(String flag, String idNo,Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		try
		{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_CARDLINK_CUSTOMER ");
			sql.append(" SET NEW_CARDLINK_CUST_FLAG = ? ");
			sql.append(" WHERE PERSONAL_ID IN ");
			sql.append(" ( SELECT PERSONAL_ID FROM ORIG_PERSONAL_INFO WHERE IDNO = ? ) ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;		
			ps.setString(index++,flag);
			
			ps.setString(index++,idNo);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public String getPersonalIdNewCardLinkCustTo(String firstTwoDigitCardLinkCustNo, String idNo, Connection conn) throws InfBatchException
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		String personalId = null;
		try
		{
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT AG.APPLICATION_GROUP_NO, AG.APPLICATION_GROUP_ID, PI.PERSONAL_ID ");
			sql.append(" FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" JOIN ORIG_PERSONAL_INFO PI ON PI.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_CARDLINK_CUSTOMER CC ON CC.PERSONAL_ID = PI.PERSONAL_ID ");
			sql.append(" WHERE AG.JOB_STATE = ? "); //align with PL cardsetup
			sql.append(" AND PI.IDNO = ? "); //use idno from selectIdNoWithCardLinkRefNo()
			sql.append(" AND CC.CARDLINK_CUST_NO LIKE '" + firstTwoDigitCardLinkCustNo + "%' ");
			sql.append(" AND CC.NEW_CARDLINK_CUST_FLAG = ? "); 
			sql.append(" AND ( CC.CARDLINK_STATUS <> ? OR CARDLINK_STATUS IS NULL ) ");
			logger.debug("sql >> "+sql);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, JOBSTATE_APPROVED);
			ps.setString(2, idNo);
			ps.setString(3, MConstant.FLAG_Y);
			ps.setString(4, MConstant.FLAG_S);
			rs = ps.executeQuery();		
			while(rs.next())
			{
				personalId = rs.getString("PERSONAL_ID");
			}
		}
		catch(Exception e)
		{
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}
		finally
		{
			try{
				closeConnection(ps,rs);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return personalId;
	}
	@Override
	public void updateCardLinkCustNo(String cardLinkCustNo, String personalId, Connection conn) throws InfBatchException{
		PreparedStatement ps = null;
		try
		{
			StringBuilder sql = new StringBuilder("");
			sql.append(" UPDATE ORIG_CARDLINK_CUSTOMER ");
			sql.append(" SET CARDLINK_CUST_NO = ? , CARDLINK_STATUS = 'S' ");
			sql.append(" WHERE PERSONAL_ID = ? "); //personal id from isNewCardLinkCustTo
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);	
			ps.setString(1,cardLinkCustNo);
			ps.setString(2,personalId);
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void updateCardlinkFlagGenerate(Connection conn, String idNo) throws InfBatchException 
	{
	 	if(!Util.empty(idNo))
		{
			PreparedStatement ps = null;
			try
			{
				StringBuilder sql = new StringBuilder("");
				sql.append(" UPDATE ORIG_APPLICATION SET CARDLINK_FLAG = ? WHERE CARDLINK_FLAG = ? ");
				sql.append(" AND CARDLINK_REF_NO IN ");
				sql.append(" ( SELECT AP.CARDLINK_REF_NO FROM ORIG_APPLICATION_GROUP AG ");
				sql.append(" JOIN ORIG_APPLICATION AP ON AP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
				sql.append(" JOIN ORIG_PERSONAL_INFO PI ON PI.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID ");
				sql.append(" WHERE PI.IDNO = ? AND AG.JOB_STATE = ? ) ");
				
				String dSql = String.valueOf(sql);
				logger.debug("Sql=" + dSql);
				ps = conn.prepareStatement(dSql);
				int index = 1;		
				ps.setString(index++,CardLinkRow.Flag.GENERATE);
				ps.setString(index++,CardLinkRow.Flag.NEXT_DAY);
				ps.setString(index++,idNo);
				ps.setString(index++,JOBSTATE_APPROVED);
				ps.executeUpdate();			
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new InfBatchException(e.getMessage());
			} finally {
				try {
					closeConnection(ps);
				} catch (Exception e) {
					logger.fatal(e.getLocalizedMessage());
				}
			}
		}
	}
	
}
