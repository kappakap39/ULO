package com.eaf.orig.ulo.service.followup.result.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.model.DocumentCheckListDataM;
import com.eaf.orig.shared.model.DocumentScenarioDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.service.dao.WfApplicationGroupDAOImpl;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpCSVContentDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;
import com.eaf.service.common.exception.ServiceControlException;
import com.eaf.service.common.master.ObjectDAO;

public class FollowUpResultDAOImpl extends ObjectDAO implements FollowUpResultDAO{
	private static transient Logger logger = Logger.getLogger(WfApplicationGroupDAOImpl.class);
	String PERSONAL_TYPE_APPLICANT =SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_INFO =SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	String SALE_TYPE_REFERENCE_SALES =SystemConstant.getConstant("SALE_TYPE_REFERENCE_SALES");
	String FIELD_ID_CONTACT_TIME =SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
	String SYSTEM_USER_ID =SystemConstant.getConstant("SYSTEM_USER_ID");
	private String userId;
	public FollowUpResultDAOImpl(String userId){
		this.userId = userId;
	}
	
	@Override
	public void updateFollowupResultCVSContent(FollowUpCSVContentDataM csvContent,String applicationGroupId)throws ServiceControlException {
		try{
//			#rawi comment not update branch code
//			String branchCode = csvContent.getBranchCode();
//			logger.debug("branchCode : "+branchCode);
//			if(!Util.empty(branchCode)){
//				updateBranchCode(branchCode);
//			}
			this.updateOrigPersonalInfo(csvContent,applicationGroupId);		
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}
	}
	private void updateBranchCode(String branchCode)throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = getConnection();
			String sql = "UPDATE ORIG_APPLICATION_GROUP SET BRANCH_NO = ?,UPDATE_DATE = ?, UPDATE_BY = ?";
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;	
			ps.setString(index++,branchCode);	
			ps.setTimestamp(index++,ApplicationDate.getTimestamp());
			ps.setString(index++,userId);	
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceControlException(e.getLocalizedMessage());
			}
		}
	}
	private void updateOrigPersonalInfo(FollowUpCSVContentDataM folloUpCSVContent,String applicationGroupId)throws ServiceControlException{
		Connection conn = null;
		PreparedStatement ps = null;
		String cusAvailble = folloUpCSVContent.getCustAvailableTime();
		logger.debug("cusAvailble : "+cusAvailble);
		String contactTime = CacheControl.getName(FIELD_ID_CONTACT_TIME,"MAPPING8",cusAvailble,"CHOICE_NO");
		logger.debug("contactTime : "+contactTime);
		String telType = folloUpCSVContent.getTelType();
		String telNo = folloUpCSVContent.getTelNo();
		String telExt = folloUpCSVContent.getTelExt();
		logger.debug("telType : "+telType);
		logger.debug("telNo : "+telNo);
		logger.debug("telExt : "+telExt);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE ORIG_PERSONAL_INFO SET UPDATE_DATE = ?, UPDATE_BY = ? "); 
			if(!Util.empty(contactTime)){
				sql.append(" ,CONTACT_TIME = ? "); 
			}
			if(!Util.empty(telType)){
				sql.append(" ,PEGA_PHONE_TYPE = ? "); 
			}
			if(!Util.empty(telNo)){
				sql.append(" ,PEGA_PHONE_NO = ? "); 
			}
			if(!Util.empty(telExt)){
				sql.append(" ,PEGA_PHONE_EXT = ? "); 
			}
			sql.append("WHERE APPLICATION_GROUP_ID = ? AND (PERSONAL_TYPE = ? OR PERSONAL_TYPE=?) ");
			
			logger.debug("sql : "+sql);
			logger.debug("CONTACT_TIME : "+contactTime);
			ps = conn.prepareStatement(sql.toString());
			int index = 1;	
			
			ps.setTimestamp(index++,ApplicationDate.getTimestamp());
			ps.setString(index++,userId);			
			if(!Util.empty(contactTime)){
				ps.setString(index++,contactTime);
			}
			if(!Util.empty(telType)){
				ps.setString(index++,telType);
			}
			if(!Util.empty(telNo)){
				ps.setString(index++,telNo);
			}
			if(!Util.empty(telExt)){
				ps.setString(index++,telExt);
			}			
			ps.setString(index++,applicationGroupId);
			ps.setString(index++,PERSONAL_TYPE_APPLICANT);
			ps.setString(index++,PERSONAL_TYPE_INFO);
			ps.executeUpdate();			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceControlException(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public FollowUpResultApplicationDataM  loadApplicationData(String applicationGroupId)throws ServiceControlException {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		FollowUpResultApplicationDataM followUpResult  = new FollowUpResultApplicationDataM();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APPLICATION_GROUP_ID ,APPLICATION_GROUP_NO, APPLICATION_TYPE, JOB_STATE, INSTANT_ID ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			logger.debug("sql : "+sql);
			logger.debug("applicationGroupId : "+applicationGroupId);
			ps = conn.prepareStatement(sql.toString());
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			if(rs.next()){
				String JOB_STATE = rs.getString("JOB_STATE");
				String APPLICATION_GROUP_ID = rs.getString("APPLICATION_GROUP_ID");
				String APPLICATION_GROUP_NO = rs.getString("APPLICATION_GROUP_NO");
				String APPLICATION_TYPE = rs.getString("APPLICATION_TYPE");
				String INSTANT_ID = rs.getString("INSTANT_ID");				
				logger.debug("APPLICATION_GROUP_NO : "+APPLICATION_GROUP_NO);
				logger.debug("APPLICATION_GROUP_ID : "+APPLICATION_GROUP_ID);
				logger.debug("APPLICATION_TYPE : "+APPLICATION_TYPE);
				logger.debug("JOB_STATE : "+JOB_STATE);
				logger.debug("INSTANT_ID : "+INSTANT_ID);
				followUpResult.setApplicationGroupId(APPLICATION_GROUP_ID);
				followUpResult.setApplicationGroupNo(APPLICATION_GROUP_NO);
				followUpResult.setApplicationType(APPLICATION_TYPE);
				followUpResult.setJobState(JOB_STATE);
				followUpResult.setInstantId(INSTANT_ID);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ServiceControlException(e.getLocalizedMessage());
			}
		}
		return followUpResult;
	}	
	
	public ArrayList<DocumentScenarioDataM> SelectDocumentScenarioDataM(String applicationGroupId){
		logger.debug("SelectDocumentScenarioDataM appId:"+applicationGroupId);
		ArrayList<DocumentScenarioDataM> documentScenarioListDataM = new ArrayList<DocumentScenarioDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT P.PERSONAL_TYPE,RQ.SCENARIO_TYPE,RQ.DOC_COMPLETED_FLAG FROM ORIG_PERSONAL_INFO P ");
			sql.append("JOIN XRULES_VERIFICATION_RESULT XV ON P.PERSONAL_ID = XV.REF_ID AND XV.VER_LEVEL = 'P' ");
			sql.append("JOIN XRULES_REQUIRED_DOC RQ ON RQ.VER_RESULT_ID = XV.VER_RESULT_ID ");
			sql.append("WHERE P.APPLICATION_GROUP_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql="+ dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();
			DocumentScenarioDataM documentScenarioDataM = new DocumentScenarioDataM();
			while(rs.next()){
				documentScenarioDataM = new DocumentScenarioDataM();
				documentScenarioDataM.setScenarioType(rs.getString("SCENARIO_TYPE"));
				documentScenarioDataM.setPersonalType(rs.getString("PERSONAL_TYPE"));
				documentScenarioDataM.setDocCompleteFlag(rs.getString("DOC_COMPLETED_FLAG"));
				documentScenarioListDataM.add(documentScenarioDataM);
			}
			closeConnection(conn, rs, ps);
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return documentScenarioListDataM;
	}
	
//	DocumentCheckListDataM
	public ArrayList<DocumentCheckListDataM> selectDocumentCheckListDataM(String applicationGroupId){
		logger.debug("selectDocumentCheckListDataM appId:"+applicationGroupId);
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<DocumentCheckListDataM> documentchecklistdataM = new ArrayList<DocumentCheckListDataM>();
		try {
		conn = getConnection();
		StringBuilder sql = new StringBuilder("");
		sql.append("SELECT DCL.DOC_TYPE_ID,DCL.APPLICANT_TYPE_IM,DLR.GENERATE_FLAG ");
		sql.append("FROM ORIG_DOC_CHECK_LIST DCL JOIN ORIG_DOC_CHECK_LIST_REASON DLR ON DCL.DOC_CHECK_LIST_ID = DLR.DOC_CHECK_LIST_ID ");
		sql.append("WHERE DCL.APPLICATION_GROUP_ID = ? ");
		String dSql = sql.toString();
		logger.debug("Sql=" + dSql);
		ps = conn.prepareStatement(dSql);
		ps.setString(1, applicationGroupId);
		rs = ps.executeQuery();
		DocumentCheckListDataM documentListdataM = new DocumentCheckListDataM();
		while (rs.next()) {
			documentListdataM = new DocumentCheckListDataM();
			logger.debug("Doc type id =" + rs.getString("DOC_TYPE_ID"));
			documentListdataM.setDocTypeId(rs.getString("DOC_TYPE_ID"));
			documentListdataM.setGenarateFlag(rs.getString("GENERATE_FLAG"));
			documentListdataM.setApplicationTypeIm(rs.getString("APPLICANT_TYPE_IM"));
			documentchecklistdataM.add(documentListdataM);
		}
			closeConnection(conn, rs, ps);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return documentchecklistdataM;
	}
	
	@Override
	public List<String> selectApplicationRecordIds(String applicationGroupId) throws ServiceControlException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<String> appRecordLst = new ArrayList<String>();
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_RECORD_ID ");
			sql.append(" FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = sql.toString();
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			while (rs.next()) {
				appRecordLst.add(rs.getString("APPLICATION_RECORD_ID"));
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ServiceControlException(e.getLocalizedMessage());
			}
		}
		return appRecordLst;
	}

	@Override
	public SaleInfoDataM loadSaleInfoByType(String applicationGroupId,String saleType) throws ServiceControlException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		SaleInfoDataM saleInfoM = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT SALES_INFO_ID, APPLICATION_GROUP_ID, SALES_ID, ");
			sql.append(" SALES_NAME, SALES_BRANCH_CODE, SALES_PHONE_NO, SALES_COMMENT, ");
			sql.append(" SALES_TYPE, REGION, ZONE, SALES_DEPARTMENT_NAME, SALES_RC_CODE, ");
			sql.append(" SALE_CHANNEL, CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY ");
			sql.append(" FROM ORIG_SALE_INFO WHERE APPLICATION_GROUP_ID = ? AND SALES_TYPE = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			ps.setString(2, saleType);
			
			rs = ps.executeQuery();

			if(rs.next()) {
				saleInfoM = new SaleInfoDataM();
				saleInfoM.setSalesInfoId(rs.getString("SALES_INFO_ID"));
				saleInfoM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				saleInfoM.setSalesId(rs.getString("SALES_ID"));
				
				saleInfoM.setSalesName(rs.getString("SALES_NAME"));
				saleInfoM.setSalesBranchCode(rs.getString("SALES_BRANCH_CODE"));
				saleInfoM.setSalesPhoneNo(rs.getString("SALES_PHONE_NO"));
				saleInfoM.setSalesComment(rs.getString("SALES_COMMENT"));
				
				saleInfoM.setSalesType(rs.getString("SALES_TYPE"));
				saleInfoM.setRegion(rs.getString("REGION"));
				saleInfoM.setZone(rs.getString("ZONE"));
				saleInfoM.setSalesDeptName(rs.getString("SALES_DEPARTMENT_NAME"));
				saleInfoM.setSalesRCCode(rs.getString("SALES_RC_CODE"));
				saleInfoM.setSaleChannel(rs.getString("SALE_CHANNEL"));
				
				saleInfoM.setCreateBy(rs.getString("CREATE_BY"));
				saleInfoM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				saleInfoM.setUpdateBy(rs.getString("UPDATE_BY"));
				saleInfoM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));				
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ServiceControlException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ServiceControlException(e.getLocalizedMessage());
			}
		}
		return saleInfoM;
	}
}
