package com.eaf.orig.ulo.pl.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLCashTransferDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLNCBDocDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesHRVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPhoneVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ApplicationDAOImpl extends OrigObjectDAO implements ApplicationInfoDAO{
	static Logger logger = Logger.getLogger(ApplicationDAOImpl.class);
	
	@Override
	public void LoadAppInfo(PLApplicationDataM applicationM, String appRecID)throws PLOrigApplicationException {
		try{
			LoadORIG_APPLICATION(appRecID,applicationM);
			applicationM.setCashTransfer(LoadORIG_CASH_TRANSFER(appRecID));
			applicationM.setDocCheckListVect(LoadORIG_DOC_CHECK_LIST(appRecID));
			applicationM.setSaleInfo(LoadORIG_SALE_INFO(appRecID));
			applicationM.setPersonalInfoVect(LoadORIG_PERSONAL_INFO(appRecID));
			applicationM.setReasonVect(LoadORIG_REASON(appRecID));		
			String busclassID = applicationM.getBusinessClassId();
			if(null != busclassID){
				String [] object = busclassID.split("\\_");
				applicationM.setProduct(object[1]);
				applicationM.setSaleType(object[2]);
			}			
		}catch(Exception e){
			logger.fatal("Exception >> "+e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
	}
	
	@Override
	public PLApplicationDataM LoadAppInfo(String appRecID)throws PLOrigApplicationException{
		PLApplicationDataM applicationM = new PLApplicationDataM();
		try{
			LoadORIG_APPLICATION(appRecID,applicationM);
			applicationM.setCashTransfer(LoadORIG_CASH_TRANSFER(appRecID));
			applicationM.setDocCheckListVect(LoadORIG_DOC_CHECK_LIST(appRecID));
			applicationM.setSaleInfo(LoadORIG_SALE_INFO(appRecID));
			applicationM.setPersonalInfoVect(LoadORIG_PERSONAL_INFO(appRecID));
			applicationM.setReasonVect(LoadORIG_REASON(appRecID));		
			String busclassID = applicationM.getBusinessClassId();
			if(null != busclassID){
				String [] object = busclassID.split("\\_");
				applicationM.setProduct(object[1]);
				applicationM.setSaleType(object[2]);
			}
			
		}catch(Exception e){
			logger.fatal("Exception >> "+e.getMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}
		return applicationM;
	}
	
	private Vector<PLReasonDataM> LoadORIG_REASON(String appRecID) throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<PLReasonDataM> reasonVect = new Vector<PLReasonDataM>();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     APPLICATION_RECORD_ID, ");
				SQL.append("     REASON_TYPE, ");
				SQL.append("     REASON_CODE, ");
				SQL.append("     CREATE_DATE , ");
				SQL.append("     CREATE_BY, ");
				SQL.append("     UPDATE_DATE, ");
				SQL.append("     UPDATE_BY, ");
				SQL.append("     ROLE, ");
				SQL.append("     REMARK ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_REASON ");
				SQL.append(" WHERE ");
				SQL.append("     APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(SQL);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			int index = 1;
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
			PLReasonDataM reasonM = null;
			
			while(rs.next()){
				reasonM = new PLReasonDataM();
				reasonM.setApplicationRecordId(rs.getString(1));
				reasonM.setReasonType(rs.getString(2));
				reasonM.setReasonCode(rs.getString(3));
				reasonM.setCreateDate(rs.getTimestamp(4));
				reasonM.setCreateBy(rs.getString(5));
				
				reasonM.setUpdateDate(rs.getTimestamp(6));
				reasonM.setUpdateBy(rs.getString(7));
				reasonM.setRole(rs.getString(8));
				reasonM.setRemark(rs.getString(9));	
				reasonM.setLoadFLAG(OrigConstant.FLAG_Y);
				reasonVect.add(reasonM);				
			}			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return reasonVect;
	}
	
	private void LoadORIG_APPLICATION(String appRecID,PLApplicationDataM applicationM)throws PLOrigApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn  = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     APPLICATION_RECORD_ID, ");
				SQL.append("     APPLICATION_NO, ");
				SQL.append("     REF_NO, ");
				SQL.append("     SALE_TYPE, ");
				SQL.append("     BUSINESS_CLASS_ID, ");
				SQL.append("     APPLICATION_STATUS, ");
				SQL.append("     JOB_STATE, ");
				SQL.append("     APPLICATION_DATE, ");
				SQL.append("     JOB_TYPE, ");
				SQL.append("     REOPEN_FLAG,");
				SQL.append("     LIFE_CYCLE,");
				SQL.append("     PRIORITY,");
				SQL.append("     CREATE_BY, ");
				SQL.append("     CREATE_DATE, ");
				SQL.append("     UPDATE_BY, ");
				SQL.append("     UPDATE_DATE, ");
				SQL.append("     ICDC_FLAG, ");
				SQL.append("     BLOCK_FLAG ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_APPLICATION ");
				SQL.append(" WHERE ");
				SQL.append("     APPLICATION_RECORD_ID = ? ");
			
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
			
			if(rs.next()){
				applicationM.setAppRecordID(rs.getString("APPLICATION_RECORD_ID"));
				applicationM.setApplicationNo(rs.getString("APPLICATION_NO"));
				applicationM.setRefNo(rs.getString("REF_NO"));
				applicationM.setSaleType(rs.getString("SALE_TYPE"));
				applicationM.setBusinessClassId(rs.getString("BUSINESS_CLASS_ID"));
				applicationM.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				applicationM.setJobState(rs.getString("JOB_STATE"));
				applicationM.setAppDate(rs.getTimestamp("APPLICATION_DATE"));
				applicationM.setJobType(rs.getString("JOB_TYPE"));
				applicationM.setReopenFlag(rs.getString("REOPEN_FLAG"));
				applicationM.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				applicationM.setPriority(rs.getString("PRIORITY"));
				applicationM.setCreateBy(rs.getString("CREATE_BY"));
				applicationM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				applicationM.setUpdateBy(rs.getString("UPDATE_BY"));
				applicationM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				applicationM.setICDCFlag(rs.getString("ICDC_FLAG"));
				applicationM.setBlockFlag(rs.getString("BLOCK_FLAG"));
			}			
		}catch (Exception e) {
			logger.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Exception ",e);
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
	}
	private PLSaleInfoDataM LoadORIG_SALE_INFO(String appRecID)throws PLOrigApplicationException {
		PLSaleInfoDataM saleInfoM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			
			StringBuilder SQL = new StringBuilder("");			
				SQL.append(" SELECT ");
				SQL.append("     SALES_ID, ");
				SQL.append("     SALES_GUARANTEE, ");
				SQL.append("     REF_NAME, ");
				SQL.append("     REF_BRANCH_CODE, ");
				SQL.append("     REF_PHONE_NO , ");
				SQL.append("     REF_FAX_NO, ");
				SQL.append("     SALES_NAME , ");
				SQL.append("     SALES_BRANCH_CODE, ");
				SQL.append("     SALES_PHONE_NO, ");
				SQL.append("     SALES_FAX_NO , ");
				SQL.append("     SALES_COMMENT, ");
				SQL.append("     CREATE_DATE, ");
				SQL.append("     CREATE_BY, ");
				SQL.append("     UPDATE_DATE, ");
				SQL.append("     UPDATE_BY , ");
				SQL.append("     SALES_POSITION, ");
				SQL.append("     CASH_DAYONE_NAME, ");
				SQL.append("     CASH_DAYONE_BRANCH_CODE, ");
				SQL.append("     CASH_DAYONE_PHONE_NO, ");
				SQL.append("     CASH_DAYONE_FAX_NO , ");
				SQL.append("     REMARK ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_SALE_INFO ");
				SQL.append(" WHERE ");
				SQL.append("     APPLICATION_RECORD_ID = ? ");
			
			String dSql = String.valueOf(SQL);
			logger.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			int index = 1;
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
			if (rs.next()){	
				saleInfoM = new PLSaleInfoDataM();
				saleInfoM.setSalesId(rs.getString("SALES_ID"));
				saleInfoM.setSalesGuarantee(rs.getString("SALES_GUARANTEE"));
				saleInfoM.setRefName(rs.getString("REF_NAME"));
				saleInfoM.setRefBranchCode(rs.getString("REF_BRANCH_CODE"));
				saleInfoM.setRefPhoneNo(rs.getString("REF_PHONE_NO"));
				
				saleInfoM.setRefFaxNo(rs.getString("REF_FAX_NO"));
				saleInfoM.setSalesName(rs.getString("SALES_NAME"));
				saleInfoM.setSalesBranchCode(rs.getString("SALES_BRANCH_CODE"));
				saleInfoM.setSalesPhoneNo(rs.getString("SALES_PHONE_NO"));
				saleInfoM.setSalesFaxNo(rs.getString("SALES_FAX_NO"));
				
				saleInfoM.setSalesComment(rs.getString("SALES_COMMENT"));
				saleInfoM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				saleInfoM.setCreateBy(rs.getString("CREATE_BY"));
				saleInfoM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				saleInfoM.setUpdateBy(rs.getString("UPDATE_BY"));
				saleInfoM.setSalesPosition(rs.getString("SALES_POSITION"));
				
				saleInfoM.setCashDayOneName(rs.getString("CASH_DAYONE_NAME"));
				saleInfoM.setCashDayOneBranchCode(rs.getString("CASH_DAYONE_BRANCH_CODE"));
				saleInfoM.setCashDayOnePhoneNo(rs.getString("CASH_DAYONE_PHONE_NO"));
				saleInfoM.setCashDayOneFax(rs.getString("CASH_DAYONE_FAX_NO"));

				saleInfoM.setRemark(rs.getString("REMARK"));				
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return saleInfoM;
	}
	private Vector<PLDocumentCheckListDataM> LoadORIG_DOC_CHECK_LIST(String appRecID)throws PLOrigApplicationException {
		Vector<PLDocumentCheckListDataM> docCheckListVect = new Vector<PLDocumentCheckListDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     DOC_CHECK_LIST_ID, ");
				SQL.append("     DOCUMENT_CODE, ");
				SQL.append("     JOB_STATE, ");
				SQL.append("     APPLICANT_TYPE, ");
				SQL.append("     DOC_TYPE_ID , ");
				SQL.append("     DOC_TYPE_DESC, ");
				SQL.append("     REQUIRED, ");
				SQL.append("     RECEIVE, ");
				SQL.append("     CREATE_BY, ");
				SQL.append("     CREATE_DATE , ");
				SQL.append("     ROLE, ");
				SQL.append("     REMARK, ");
				SQL.append("     WAIVE, ");
				SQL.append("     REQUEST_DOC ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_DOC_CHECK_LIST ");
				SQL.append(" WHERE ");
				SQL.append("     APPLICATION_RECORD_ID = ?");
				
			String dSql = String.valueOf(SQL);
			
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			
			int index = 1;
			
			ps.setString(index++, appRecID);
			
			PLDocumentCheckListDataM docChkListM = null;
			
			rs = ps.executeQuery();
			while (rs.next()){
				docChkListM = new PLDocumentCheckListDataM();
				
				docChkListM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docChkListM.setDocCode(rs.getString("DOCUMENT_CODE"));
				docChkListM.setJobState(rs.getString("JOB_STATE"));
				docChkListM.setApplicationType(rs.getString("APPLICANT_TYPE"));
				docChkListM.setDocTypeId(rs.getString("DOC_TYPE_ID"));				
				docChkListM.setDocTypeDesc(rs.getString("DOC_TYPE_DESC"));
				docChkListM.setRequired(rs.getString("REQUIRED"));
				docChkListM.setReceive(rs.getString("RECEIVE"));
				docChkListM.setCreateBy(rs.getString("CREATE_BY"));
				docChkListM.setCreateDate(rs.getTimestamp("CREATE_DATE"));				
				docChkListM.setRole(rs.getString("ROLE"));
				docChkListM.setRemark(rs.getString("REMARK"));
				docChkListM.setWaive(rs.getString("WAIVE"));
				docChkListM.setRequestDoc(rs.getString("REQUEST_DOC"));
				
				docChkListM.setDocCkReasonVect(LoadORIG_DOC_CHECK_LIST_REASON(docChkListM.getDocCheckListId(),docChkListM.getDocCode()));
				
				docCheckListVect.add(docChkListM);
			}			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return docCheckListVect;
	}
	private Vector<PLDocumentCheckListReasonDataM> LoadORIG_DOC_CHECK_LIST_REASON(String docChklistID,String docCode) throws PLOrigApplicationException{
		Vector<PLDocumentCheckListReasonDataM> docReasonVect = new Vector<PLDocumentCheckListReasonDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append("SELECT ");
				SQL.append("     DOC_CHECK_LIST_ID, ");
				SQL.append("     DOC_REASON, ");
				SQL.append("     CREATE_DATE, ");
				SQL.append("     CREATE_BY, ");
				SQL.append("     UPDATE_DATE , ");
				SQL.append("     UPDATE_BY ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_DOC_CHECK_LIST_REASON ");
				SQL.append(" WHERE ");
				SQL.append("     DOC_CHECK_LIST_ID = ? ");
				
			String dSql = String.valueOf(SQL);
			ps = conn.prepareStatement(dSql);
			rs = null;
			int index = 1;
			ps.setString(index++, docChklistID);

			rs = ps.executeQuery();
			PLDocumentCheckListReasonDataM docListReasonM = null;
			while(rs.next()){
				docListReasonM = new PLDocumentCheckListReasonDataM();
				docListReasonM.setDocCode(docCode);
				docListReasonM.setDocCheckListId(rs.getString("DOC_CHECK_LIST_ID"));
				docListReasonM.setDocReasonID(rs.getString("DOC_REASON"));
				docListReasonM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				docListReasonM.setCreateBy(rs.getString("CREATE_BY"));
				docListReasonM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));				
				docListReasonM.setUpdateBy(rs.getString("UPDATE_BY"));
				docReasonVect.add(docListReasonM);
			}						
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		
		return docReasonVect;
	}
	
	private PLCashTransferDataM LoadORIG_CASH_TRANSFER(String appRecID)throws PLOrigApplicationException {
		PLCashTransferDataM cashTransferM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn  = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     PRODUCT_TYPE, ");
				SQL.append("     TRANSFER_ACCOUNT, ");
				SQL.append("     ACCOUNT_NAME, ");
				SQL.append("     PERCENT_TRANSFER, ");
				SQL.append("     FIRST_TRANSFER_AMOUNT , ");
				SQL.append("     EXPRESS_TRANSFER, ");
				SQL.append("     CREATE_DATE, ");
				SQL.append("     CREATE_BY, ");
				SQL.append("     UPDATE_DATE, ");
				SQL.append("     UPDATE_BY , ");
				SQL.append("     CASH_TRANSFER_TYPE, ");
				SQL.append("     BANK_TRANSFER, ");
				SQL.append("     REMARK ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_CASH_TRANSFER ");
				SQL.append(" WHERE ");
				SQL.append("     APPLICATION_RECORD_ID = ? ");					
			
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
			
			if(rs.next()){
				cashTransferM = new PLCashTransferDataM();
				cashTransferM.setProductType(rs.getString("PRODUCT_TYPE"));
				cashTransferM.setTransAcc(rs.getString("TRANSFER_ACCOUNT"));
				cashTransferM.setAccName(rs.getString("ACCOUNT_NAME"));
				cashTransferM.setPercentTrans(rs.getBigDecimal("PERCENT_TRANSFER"));
				cashTransferM.setFirstTransAmount(rs.getBigDecimal("FIRST_TRANSFER_AMOUNT"));
				
				cashTransferM.setExpressTrans(rs.getString("EXPRESS_TRANSFER"));
				cashTransferM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				cashTransferM.setCreateBy(rs.getString("CREATE_BY"));
				cashTransferM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				cashTransferM.setUpdateBy(rs.getString("UPDATE_BY"));
				
				cashTransferM.setCashTransferType(rs.getString("CASH_TRANSFER_TYPE"));
				cashTransferM.setBankTransfer(rs.getString("BANK_TRANSFER"));
				cashTransferM.setRemark(rs.getString("REMARK"));	
			}
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Exception ",e);
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return cashTransferM;
	}
	
	private Vector<PLPersonalInfoDataM> LoadORIG_PERSONAL_INFO(String appRecID)throws PLOrigApplicationException {
		Vector<PLPersonalInfoDataM> personalVect = new Vector<PLPersonalInfoDataM>();
		PLPersonalInfoDataM personalM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn  = getConnection();
			
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     APPLICATION_RECORD_ID, ");
				SQL.append("     PERSONAL_ID, ");
				SQL.append("     PERSONAL_TYPE, ");
				SQL.append("     TH_TITLE_CODE, ");
				SQL.append("     TH_FIRST_NAME, ");
				SQL.append("     TH_LAST_NAME, ");
				SQL.append("     IDNO, ");
				SQL.append("     BIRTH_DATE, ");
				SQL.append("     CREATE_DATE, ");
				SQL.append("     CREATE_BY, ");
				SQL.append("     UPDATE_DATE, ");
				SQL.append("     UPDATE_BY, ");
				SQL.append("     CUSTOMER_TYPE, ");
				SQL.append("     CID_TYPE, ");
				SQL.append("     CID_ISSUE_DATE ");
				SQL.append(" FROM ");
				SQL.append("     ORIG_PERSONAL_INFO ");
				SQL.append(" WHERE ");
				SQL.append("     APPLICATION_RECORD_ID = ? ");
				
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			
			ps.setString(index++, appRecID);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				personalM = new PLPersonalInfoDataM();
				
				personalM.setAppRecordID(rs.getString("APPLICATION_RECORD_ID"));
				personalM.setPersonalID(rs.getString("PERSONAL_ID"));
				personalM.setPersonalType(rs.getString("PERSONAL_TYPE"));
				personalM.setThaiTitleName(rs.getString("TH_TITLE_CODE"));
				personalM.setThaiFirstName(rs.getString("TH_FIRST_NAME"));
				personalM.setThaiLastName(rs.getString("TH_LAST_NAME"));
				personalM.setIdNo(rs.getString("IDNO"));
				personalM.setBirthDate(rs.getDate("BIRTH_DATE"));
				personalM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				personalM.setCreateBy(rs.getString("CREATE_BY"));
				personalM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				personalM.setUpdateBy(rs.getString("UPDATE_BY"));				
				personalM.setCustomerType(rs.getString("CUSTOMER_TYPE"));
				personalM.setCidType(rs.getString("CID_TYPE"));
				personalM.setCidIssueDate(rs.getDate("CID_ISSUE_DATE"));
				
				logger.debug("PERSONAL_ID >> "+personalM.getPersonalID());
				
				personalM.setNcbDocVect(LoadORIG_NCB_DOCUMENT(personalM.getPersonalID()));
				personalM.setXrulesVerification(LoadXRULES_VERIFICATION_RESULT(personalM.getPersonalID()));
				personalVect.add(personalM);
			}
			
		}catch (Exception e) {
			logger.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Exception ",e);
				throw new PLOrigApplicationException(e.getMessage());
			}
		}		
		return personalVect;
	}
	
	public Vector<PLNCBDocDataM> LoadORIG_NCB_DOCUMENT(String personalID)throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Vector<PLNCBDocDataM> ncbDocVect = new Vector<PLNCBDocDataM>();
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT DOCUMENT_CODE, IMG_ID, CREATE_DATE, CREATE_BY, UPDATE_DATE ");
			sql.append(", UPDATE_BY ");
			sql.append(" FROM ORIG_NCB_DOCUMENT WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);

			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalID);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				PLNCBDocDataM ncbDocM = new PLNCBDocDataM();
				
				ncbDocM.setDocCode(rs.getString(1));
				ncbDocM.setImgID(rs.getString(2));
				ncbDocM.setCreateDate(rs.getTimestamp(3));
				ncbDocM.setCreateBy(rs.getString(4));
				
				ncbDocM.setUpdateDate(rs.getTimestamp(5));
				ncbDocM.setUpdateBy(rs.getString(6));
				ncbDocM.setPersonalID(personalID);
				
				ncbDocVect.add(ncbDocM);
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return ncbDocVect;
	}
	
	private PLXRulesVerificationResultDataM LoadXRULES_VERIFICATION_RESULT(String personalID)throws PLOrigApplicationException{
		PLXRulesVerificationResultDataM xrulesverM = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn  = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT ");
				SQL.append("     NCB_REQUESTER, ");
				SQL.append("     NCB_RQ_APPROVER, ");
				SQL.append("     NCB_RESULT, ");
				SQL.append("     NCB_RESULT_CODE, ");
				SQL.append("     NCB_SUP_COMMENT, ");
				SQL.append("     VER_HR_UPDATE_DATE, ");
				SQL.append("     VER_CUS_UPDATE_DATE, ");
				SQL.append("     NCB_CONSENT_REF_NO ");
				SQL.append(" FROM ");
				SQL.append("     XRULES_VERIFICATION_RESULT ");
				SQL.append(" WHERE ");
				SQL.append("     PERSONAL_ID = ? ");
			
			ps = conn.prepareStatement(SQL.toString());
			int index = 1;
			ps.setString(index++, personalID);

			rs = ps.executeQuery();
			
			if(rs.next()){
				xrulesverM = new PLXRulesVerificationResultDataM();
				xrulesverM.setNCBRequester(rs.getString("NCB_REQUESTER"));
				xrulesverM.setNcbRQapprover(rs.getString("NCB_RQ_APPROVER"));
				xrulesverM.setNCBResult(rs.getString("NCB_RESULT"));
				xrulesverM.setNCBCode(rs.getString("NCB_RESULT_CODE"));
				xrulesverM.setNcbSupComment(rs.getString("NCB_SUP_COMMENT"));
				xrulesverM.setVerHRUpdateDate(rs.getTimestamp("VER_HR_UPDATE_DATE"));
				xrulesverM.setVerCusUpdateDate(rs.getTimestamp("VER_CUS_UPDATE_DATE"));
				xrulesverM.setNCBConsentRefNo(rs.getString("NCB_CONSENT_REF_NO"));
				xrulesverM.setxRulesHRVerificationDataMs(loadPLXRulesHRVerification(personalID));
				xrulesverM.setVXRulesPhoneVerificationDataM(loadPLXRulesPhoneVerification(personalID));
			}
		}catch(Exception e){
			logger.fatal("Exception ",e);
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("Exception ",e);
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return xrulesverM;
	}
	
	private Vector<PLXRulesPhoneVerificationDataM> loadPLXRulesPhoneVerification(String personalId) throws PLOrigApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;		
		Vector<PLXRulesPhoneVerificationDataM> phoneVerifyVect = new Vector<PLXRulesPhoneVerificationDataM>();
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CALL_TYPE, CONTACT_TYPE, TELEPHONE_NUMBER, PHONE_VER_STATUS, UPDATE_BY ");
			sql.append(", UPDATE_DATE, REMARK, SEQ, CREATE_DATE, CREATE_BY ");
			sql.append(" FROM XRULES_PHONE_VERIFICATION WHERE PERSONAL_ID=?  ORDER BY TO_NUMBER(SEQ) ASC ");
			String dSql = String.valueOf(sql);
			
			logger.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				PLXRulesPhoneVerificationDataM phoneVerifyM = new PLXRulesPhoneVerificationDataM();				
				int index = 1;
				phoneVerifyM.setCallType(rs.getString(index++));
				phoneVerifyM.setContactType(rs.getString(index++));
				phoneVerifyM.setTelno(rs.getString(index++));
				phoneVerifyM.setPhoneVerStatus(rs.getString(index++));
				phoneVerifyM.setUpdateBy(rs.getString(index++));
				
				phoneVerifyM.setUpdateDate(rs.getTimestamp(index++));
				phoneVerifyM.setRemark(rs.getString(index++));
				phoneVerifyM.setSeq(rs.getInt(index++));
				phoneVerifyM.setCreateDate(rs.getTimestamp(index++));
				phoneVerifyM.setCreateBy(rs.getString(index++));
				
				phoneVerifyVect.add(phoneVerifyM);				
			}			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return phoneVerifyVect;
	}
	
	private Vector<PLXRulesHRVerificationDataM> loadPLXRulesHRVerification(String personalId) throws PLOrigApplicationException {

		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		Vector<PLXRulesHRVerificationDataM> hrVerifyVect = new Vector<PLXRulesHRVerificationDataM>();
		try {
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CALL_TYPE, SEQ, CONTACT_TYPE, PHONE_NO, PHONE_VER_STATUS ");
			sql.append(", UPDATE_BY, UPDATE_DATE, REMARK, CREATE_DATE, CREATE_BY ");
			sql.append(" FROM XRULES_HR_VERIFICATION WHERE PERSONAL_ID = ? ORDER BY TO_NUMBER(SEQ) ASC ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();
						
			while (rs.next()) {				
				PLXRulesHRVerificationDataM hrVerifyM = new PLXRulesHRVerificationDataM();				
				int index = 1;
				hrVerifyM.setCallType(rs.getString(index++));
				hrVerifyM.setSeq(rs.getInt(index++));
				hrVerifyM.setContactType(rs.getString(index++));
				hrVerifyM.setPhoneNo(rs.getString(index++));
				hrVerifyM.setPhoneVerStatus(rs.getString(index++));
				
				hrVerifyM.setUpdateBy(rs.getString(index++));
				hrVerifyM.setUpdateDate(rs.getTimestamp(index++));
				hrVerifyM.setRemark(rs.getString(index++));
				hrVerifyM.setCreateDate(rs.getTimestamp(index++));
				hrVerifyM.setCreateBy(rs.getString(index++));				
				hrVerifyVect.add(hrVerifyM);				
			}			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new PLOrigApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new PLOrigApplicationException(e.getMessage());
			}
		}
		return hrVerifyVect;
	}
}
