package com.eaf.inf.batch.ulo.letter.history.dao;

import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.inf.batch.ulo.letter.history.model.LetterHistoryDataM;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.constant.MConstant;

public class LetterHistoryDAOImpl extends InfBatchObjectDAO implements LetterHistoryDAO{
	private static transient Logger logger = Logger.getLogger(LetterHistoryDAOImpl.class);
	
	@Override
	public ArrayList<LetterHistoryDataM> getLetterHistoryData() throws Exception 
	{
		return getLetterHistoryData(null);
	}
	
	public ArrayList<LetterHistoryDataM> getLetterHistoryData(String appGroupNo) throws InfBatchException
	{
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LetterHistoryDataM> letterHistoryList = new ArrayList<>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * ");
			sql.append(" FROM LETTER_HISTORY WHERE SEND_FLAG = 'Y' ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			
			if(!Util.empty(appGroupNo))
			{
				sql.append(" WHERE APPLICATION_GROUP_NO = ? ");
				ps.setString(1, appGroupNo);
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				LetterHistoryDataM letterHistory = new LetterHistoryDataM();
				letterHistory.setLetterNo(rs.getString("LETTER_NUMBER"));
				letterHistory.setAppGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				letterHistory.setIdNo(rs.getString("IDNO"));
				letterHistory.setFirstName(rs.getString("FIRST_NAME"));
				letterHistory.setMidName(rs.getString("MID_NAME"));
				letterHistory.setLastName(rs.getString("LAST_NAME"));
				
				letterHistory.setDe2SubmitDate(rs.getDate("DE2_SUBMIT_DATE"));
				letterHistory.setLetterType(rs.getString("LETTER_TYPE"));
				letterHistory.setLetterTemplate(rs.getString("LETTER_TEMPLATE"));
				letterHistory.setLetterContent(rs.getString("LETTER_CONTENT"));
				
				letterHistory.setSendFlag(rs.getString("SEND_FLAG"));
				letterHistory.setCustomerSendFlag(rs.getString("CUSTOMER_SEND_FLAG"));
				letterHistory.setCustomerSendMethod(rs.getString("CUSTOMER_SEND_METHOD"));
				letterHistory.setCustomerEmailAddress(rs.getString("CUSTOMER_EMAIL_ADDRESS"));
			    letterHistory.setCustomerEmailTemplate(rs.getString("CUSTOMER_EMAIL_TEMPLATE"));
				letterHistory.setSellerSendFlag(rs.getString("SELLER_SEND_FLAG"));
				letterHistory.setSellerSendMethod(rs.getString("SELLER_SEND_METHOD"));
				letterHistory.setSellerEmailAddress(rs.getString("SELLER_EMAIL_ADDRESS"));
				letterHistory.setSellerEmailTemplate(rs.getString("SELLER_EMAIL_TEMPLATE"));
				letterHistory.setSellerLanguage(rs.getString("SELLER_LANGUAGE"));
				letterHistory.setSellerAttachFile(rs.getString("SELLER_ATTACH_FILE"));
				letterHistory.setCustomerResendSendMethod(rs.getString("CUSTOMER_RESEND_SEND_METHOD"));
				letterHistory.setCustomerResendEmailAddress(rs.getString("CUSTOMER_RESEND_EMAIL_ADDRESS"));
				letterHistory.setCustomerResendEmailTemplate(rs.getString("CUSTOMER_RESEND_EMAIL_TEMPLATE"));
				letterHistory.setCustomerLanguage(rs.getString("CUSTOMER_LANGUAGE"));
				letterHistory.setCustomerAttachFile(rs.getString("CUSTOMER_ATTACH_FILE"));
				letterHistory.setCustomerResendDate(rs.getDate("CUSTOMER_RESEND_DATE"));
				letterHistory.setCustomerResendBy(rs.getString("CUSTOMER_RESEND_BY"));
				letterHistory.setCustomerResendCount(rs.getInt("CUSTOMER_RESEND_COUNT"));
				
				letterHistoryList.add(letterHistory);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return letterHistoryList;
	}

	@Override
	public void updateLetterHistory(LetterHistoryDataM letterHistoryDataM) throws Exception 
	{
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			
			sql.append("UPDATE LETTER_HISTORY ");
			sql.append(" SET CUSTOMER_RESEND_SEND_METHOD = ? , ");
			sql.append(" CUSTOMER_RESEND_EMAIL_ADDRESS = ? , CUSTOMER_RESEND_EMAIL_TEMPLATE = ? , ");
			sql.append(" CUSTOMER_RESEND_DATE = ? , CUSTOMER_RESEND_BY = ? , CUSTOMER_RESEND_COUNT = ? , SEND_FLAG = ? "); 
			sql.append(" WHERE LETTER_NUMBER = ? "); 
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;

			ps.setString(cnt++, letterHistoryDataM.getCustomerResendSendMethod());
			ps.setString(cnt++, letterHistoryDataM.getCustomerResendEmailAddress());
			ps.setString(cnt++, letterHistoryDataM.getCustomerResendEmailTemplate());
			ps.setString(cnt++, letterHistoryDataM.getCustomerResendBy());
			ps.setDate(cnt++, letterHistoryDataM.getCustomerResendDate());
			ps.setInt(cnt++, letterHistoryDataM.getCustomerResendCount());
			ps.setString(cnt++, letterHistoryDataM.getSendFlag());
			
			//Where clause
			ps.setString(cnt++, letterHistoryDataM.getLetterNo());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void createLetterHistory(LetterHistoryDataM letterHistoryDataM) throws Exception
	{
		createLetterHistory(letterHistoryDataM, null);
	}

	@Override
	public void createLetterHistory(LetterHistoryDataM letterHistoryDataM, Connection conn) throws Exception {
		PreparedStatement ps = null;
		boolean autoCommitMode = false;
		
		try {
			
			if(conn == null)
			{
				autoCommitMode = true;
				conn = getConnection();
			}
			
			Clob clob = conn.createClob();
			StringBuilder sql = new StringBuilder("");
		
			sql.append("INSERT INTO LETTER_HISTORY ");
			sql.append("( LETTER_NUMBER, APPLICATION_GROUP_NO, IDNO, FIRST_NAME, ");
			sql.append(" MID_NAME, LAST_NAME, DE2_SUBMIT_DATE, ");
			sql.append(" LETTER_TYPE, LETTER_TEMPLATE, LETTER_CONTENT, ");
			sql.append(" SEND_FLAG, CUSTOMER_SEND_FLAG, CUSTOMER_SEND_METHOD, ");
			sql.append(" CUSTOMER_EMAIL_ADDRESS, CUSTOMER_EMAIL_TEMPLATE, SELLER_SEND_FLAG, ");
			sql.append(" SELLER_SEND_METHOD, SELLER_EMAIL_ADDRESS, SELLER_EMAIL_TEMPLATE, ");
			sql.append(" SELLER_LANGUAGE, SELLER_ATTACH_FILE, ");
			sql.append(" CUSTOMER_RESEND_SEND_METHOD, CUSTOMER_RESEND_EMAIL_ADDRESS, ");
			sql.append(" CUSTOMER_RESEND_EMAIL_TEMPLATE, CUSTOMER_LANGUAGE, CUSTOMER_ATTACH_FILE, CUSTOMER_RESEND_DATE, ");
			sql.append(" CUSTOMER_RESEND_BY, CUSTOMER_RESEND_COUNT ) ");

			sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, letterHistoryDataM.getLetterNo());
			ps.setString(cnt++, letterHistoryDataM.getAppGroupNo());
			ps.setString(cnt++, letterHistoryDataM.getIdNo());
			
			ps.setString(cnt++, letterHistoryDataM.getFirstName());
			ps.setString(cnt++, letterHistoryDataM.getMidName());
			ps.setString(cnt++, letterHistoryDataM.getLastName());
			ps.setDate(cnt++, letterHistoryDataM.getDe2SubmitDate());
			
			ps.setString(cnt++, letterHistoryDataM.getLetterType());
			ps.setString(cnt++, letterHistoryDataM.getLetterTemplate());
			
			clob.setString(1, letterHistoryDataM.getLetterContent());
			ps.setClob(cnt++, clob);

			ps.setString(cnt++, letterHistoryDataM.getSendFlag());
			ps.setString(cnt++, letterHistoryDataM.getCustomerSendFlag());
			ps.setString(cnt++, letterHistoryDataM.getCustomerSendMethod());
			ps.setString(cnt++, letterHistoryDataM.getCustomerEmailAddress());
			ps.setString(cnt++, letterHistoryDataM.getCustomerEmailTemplate());
			ps.setString(cnt++, letterHistoryDataM.getSellerSendFlag());
			ps.setString(cnt++, letterHistoryDataM.getSellerSendMethod());
			ps.setString(cnt++, letterHistoryDataM.getSellerEmailAddress());
			ps.setString(cnt++, letterHistoryDataM.getSellerEmailTemplate());
			ps.setString(cnt++, letterHistoryDataM.getSellerLanguage());
			ps.setString(cnt++, letterHistoryDataM.getSellerAttachFile());
			ps.setString(cnt++, letterHistoryDataM.getCustomerResendSendMethod());
			ps.setString(cnt++, letterHistoryDataM.getCustomerResendEmailAddress());
			ps.setString(cnt++, letterHistoryDataM.getCustomerResendEmailTemplate());
			ps.setString(cnt++, letterHistoryDataM.getCustomerLanguage());
			ps.setString(cnt++, letterHistoryDataM.getCustomerAttachFile());
			ps.setDate(cnt++, letterHistoryDataM.getCustomerResendDate());
			ps.setString(cnt++, letterHistoryDataM.getCustomerResendBy());
			ps.setInt(cnt++, letterHistoryDataM.getCustomerResendCount());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				if(autoCommitMode)
				{closeConnection(conn, ps);}
				else
				{ps.close();}
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
	}

	private String prepareCommaParamToSQLList(String commaParam) {
		List<String> paramList = Arrays.asList(commaParam.split(","));
		String separator = "";
		StringBuilder sqlList = new StringBuilder();
		for (String param:paramList) {
			sqlList.append(separator);
			sqlList.append("'").append(param.trim()).append("'");
			separator = ",";
		}
		return sqlList.toString();
	}
	
	@Override
	public void setEmailProperties(String letterNo, String sendTo, String email, String language, Connection conn) throws Exception {
		PreparedStatement ps = null;
		String REJECT_LETTER_PDF_SENDTO_CUSTOMER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SENDTO_CUSTOMER");
		// KPL: Build list of EDOC template which will not attach letter to email
		String REJECT_LETTER_PDF_TEMPLATE_NO_ATTACH = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_TEMPLATE_NO_ATTACH");
		logger.debug("REJECT_LETTER_PDF_TEMPLATE_NO_ATTACH = " + REJECT_LETTER_PDF_TEMPLATE_NO_ATTACH);
		String noAttachTemplateList = prepareCommaParamToSQLList(REJECT_LETTER_PDF_TEMPLATE_NO_ATTACH);
		String REJECT_LETTER_PDF_NCB_TEMPLATE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_NCB_TEMPLATE");
		logger.debug("REJECT_LETTER_PDF_NCB_TEMPLATE = " + REJECT_LETTER_PDF_NCB_TEMPLATE);
		String ncbTemplateList = prepareCommaParamToSQLList(REJECT_LETTER_PDF_NCB_TEMPLATE);
		
		try {
			String emailAddr = email.trim();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE LETTER_HISTORY ");
			sql.append("SET SEND_FLAG='Y' ");
			if (REJECT_LETTER_PDF_SENDTO_CUSTOMER.equals(sendTo)) {
				sql.append(",CUSTOMER_SEND_FLAG='Y'");
				sql.append(",CUSTOMER_SEND_METHOD='3'");
				sql.append(",CUSTOMER_LANGUAGE='").append(language).append("'");
				sql.append(",CUSTOMER_ATTACH_FILE='1'");
				sql.append(",CUSTOMER_EMAIL_TEMPLATE='TPL01'");
				sql.append(",CUSTOMER_EMAIL_ADDRESS='").append(emailAddr).append("'");
			}
			else {
				sql.append(",SELLER_SEND_FLAG='Y'");
				sql.append(",SELLER_SEND_METHOD='3'");
				sql.append(",SELLER_LANGUAGE='").append(language).append("'");
				sql.append(",SELLER_ATTACH_FILE=");
				sql.append("    CASE");
				sql.append("       WHEN(LETTER_TEMPLATE IN (").append(noAttachTemplateList).append(")) THEN '0'");
				sql.append("       ELSE '1'");
				sql.append("    END");
				sql.append(",SELLER_EMAIL_TEMPLATE=");
				sql.append("    CASE");
				sql.append("       WHEN(LETTER_TEMPLATE IN (").append(ncbTemplateList).append(")) THEN 'TPL03'");
				sql.append("       ELSE 'TPL02'");
				sql.append("    END");
				sql.append(",SELLER_EMAIL_ADDRESS=");
				sql.append("    CASE");
				sql.append("       WHEN(SELLER_EMAIL_ADDRESS IS NULL) THEN '").append(emailAddr).append("'");
				sql.append("       WHEN(REGEXP_INSTR(SELLER_EMAIL_ADDRESS,'(^").append(emailAddr).append("$|^").append(emailAddr).append(",|,").append(emailAddr).append(",|,").append(emailAddr).append("$)') > 0) THEN SELLER_EMAIL_ADDRESS");
				sql.append("       ELSE SELLER_EMAIL_ADDRESS||','||'").append(emailAddr).append("'");
				sql.append("    END");
			}
			sql.append(" WHERE LETTER_NUMBER='").append(letterNo).append("'"); 
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int updateRows = ps.executeUpdate();
			if (updateRows == 0) {
				String errMsg = "Update nothing (letter no="+letterNo+")";
				logger.error(errMsg);
				throw(new ApplicationException(errMsg));
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}

	@Override
	public void updateSendFlag(String cusOrSeller, String letterNo,
			String sendFlag, Connection conn) throws Exception {
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder("");
			String sendFlagType = (MConstant.FLAG_C.equals(cusOrSeller)) ? "CUSTOMER_SEND_FLAG" : "SELLER_SEND_FLAG";
			sql.append("UPDATE LETTER_HISTORY ");
			sql.append(" SET SEND_FLAG = ? , ");
			sql.append(" " + sendFlagType + " = ? ");
			sql.append(" WHERE LETTER_NUMBER = ? "); 
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, sendFlag);
			ps.setString(cnt++, sendFlag);
			//Where clause
			ps.setString(cnt++, letterNo);
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
}
