package com.eaf.orig.ulo.app.olddata.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.AttachmentDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class OldDataDAOImpl extends OrigObjectDAO implements OldDataDAO {
	private String userId = "";

	public OldDataDAOImpl() {

	}

	public OldDataDAOImpl(String userId) {
		this.userId = userId;
	}

	@Override
	public ApplicationGroupDataM loadOldAppGroup(String applicationGroupId) throws Exception 
	{
		ApplicationGroupDataM appGroup = new ApplicationGroupDataM();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			conn = getConnection();
			
			StringBuffer sql = new StringBuffer("");
			sql.append(" SELECT * FROM ORIG_APPLICATION_GROUP_CAT");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			if (rs.next()) 
			{
				//Set Application Group Transform data
				appGroup.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				appGroup.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				appGroup.setApplicationDate(rs.getDate("APPLICATION_DATE"));
				appGroup.setApplicationTemplate(rs.getString("APPLICATION_TEMPLATE"));
				appGroup.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				appGroup.setUpdateDate(rs.getTimestamp("ARC_DATE"));
				appGroup.setLifeCycle(rs.getInt("LIFE_CYCLE"));
			}
			
			if(!Util.empty(appGroup.getApplicationGroupId()))
			{
				appGroup.setApplications(loadOldApp(conn, appGroup.getApplicationGroupId()));
				
				appGroup.setPersonalInfos(loadOldPersonalInfo(conn, appGroup.getApplicationGroupId()));
				
				appGroup.setApplicationImages(loadOldImage(conn, appGroup.getApplicationGroupId()));
				
				appGroup.setAttachments(loadOldAttachment(conn, appGroup.getApplicationGroupId()));
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		
		return appGroup;
	}

	@Override
	public ArrayList<ApplicationDataM> loadOldApp(Connection conn, String applicationGroupId) throws Exception 
	{
		ArrayList<ApplicationDataM> appList = new ArrayList<ApplicationDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			conn = getConnection();
			
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT * FROM ORIG_APPLICATION_CAT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			while(rs.next()) 
			{
				//Set Application Group Transform data
				ApplicationDataM app = new ApplicationDataM();
				app.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				app.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				app.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				app.setApplicationType(rs.getString("APPLICATION_TYPE"));
				app.setBusinessClassId(rs.getString("BUSINESS_CLASS_ID"));
				app.setFinalAppDecisionDate(rs.getDate("FINAL_APP_DECISION_DATE"));
				app.setFinalAppDecision(rs.getString("FINAL_APP_DECISION"));
				app.setCardlinkRefNo(rs.getString("CARDLINK_REF_NO"));
				app.setCardlinkFlag(rs.getString("CARD_TYPE"));
				app.setTcbAccountNo(rs.getString("ACCOUNT_NO")); //ORIG_LOAN ACCOUNT_NO
				app.setBookingDate(rs.getDate("ACCOUNT_OPEN_DATE")); //ORIG_LOAN ACCOUNT_OPEN_DATE
				app.setUpdateBy(rs.getString("PERSONAL_ID")); //ORIG_PERSONAL_INFO PERSONAL_ID
				appList.add(app);
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return appList;
	}
	
	@Override
	public ArrayList<AttachmentDataM> loadOldAttachment(Connection conn, String applicationGroupId) throws Exception 
	{
		ArrayList<AttachmentDataM> attachmentList = new ArrayList<AttachmentDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT * FROM ORIG_ATTACHMENT_HISTORY_CAT WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			while (rs.next()) {
				AttachmentDataM attachmentDataM = new AttachmentDataM();
				attachmentDataM.setApplicationGroupId(applicationGroupId);
				attachmentDataM.setAttachId(rs.getString("ATTACH_ID"));
				attachmentDataM.setFileName(rs.getString("FILE_NAME"));
				attachmentDataM.setFileSize(rs.getBigDecimal("FILE_SIZE"));
				attachmentDataM.setFilePath(rs.getString("FILE_PATH"));
				attachmentDataM.setMimeType(rs.getString("MIME_TYPE"));
				attachmentDataM.setFileType(rs.getString("FILE_TYPE"));
				attachmentDataM.setFileTypeOth(rs.getString("FILE_TYPE_OTH"));
				attachmentDataM.setRefId(rs.getString("REF_ID"));
				attachmentList.add(attachmentDataM);
			}

			return attachmentList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public AttachmentDataM loadAttachmentInfo(String attachId)throws Exception 
	{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT APPLICATION_GROUP_ID ,ATTACH_ID ,FILE_NAME ,FILE_SIZE, FILE_PATH, MIME_TYPE, ");
			sql.append(" FILE_TYPE, FILE_TYPE_OTH, REF_ID ");
			sql.append(" FROM ORIG_ATTACHMENT_HISTORY_CAT WHERE ATTACH_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql="+dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, attachId);

			rs = ps.executeQuery();
			AttachmentDataM attachmentDataM = new AttachmentDataM();
			if (rs.next()) {
				attachmentDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				attachmentDataM.setAttachId(rs.getString("ATTACH_ID"));
				attachmentDataM.setFileName(rs.getString("FILE_NAME"));
				attachmentDataM.setFileSize(rs.getBigDecimal("FILE_SIZE"));
				attachmentDataM.setFilePath(rs.getString("FILE_PATH"));
				attachmentDataM.setFileType(rs.getString("FILE_TYPE"));
				attachmentDataM.setFileTypeOth(rs.getString("FILE_TYPE_OTH"));
				attachmentDataM.setRefId(rs.getString("REF_ID"));
			}

			return attachmentDataM;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	
	@Override
	public ArrayList<PersonalInfoDataM> loadOldPersonalInfo(Connection conn, String applicationGroupId) throws Exception 
	{
		ArrayList<PersonalInfoDataM> personalInfoList = new ArrayList<PersonalInfoDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT * FROM ORIG_PERSONAL_INFO_CAT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			while(rs.next())
			{
				//Set Application Group Transform data
				PersonalInfoDataM perInfo = new PersonalInfoDataM();
				
				perInfo.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				perInfo.setPersonalId(rs.getString("PERSONAL_ID"));
				perInfo.setPersonalType(rs.getString("PERSONAL_TYPE"));
				perInfo.setCisNo(rs.getString("CIS_ID"));
				perInfo.setIdno(rs.getString("IDNO"));
				perInfo.setCidType(rs.getString("CID_TYPE"));
				perInfo.setSorceOfIncome(rs.getString("SORCE_OF_INCOME"));
				perInfo.setTypeOfFin(rs.getString("TYPE_OF_FIN"));
				perInfo.setThFirstName(rs.getString("TH_FIRST_NAME"));
				perInfo.setThMidName(rs.getString("TH_MID_NAME"));
				perInfo.setThLastName(rs.getString("TH_LAST_NAME"));
				perInfo.setTotVerifiedIncome(rs.getBigDecimal("TOT_VERIFIED_INCOME"));
				personalInfoList.add(perInfo);
			}
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return personalInfoList;
	}
	
	@Override
	public ArrayList<ApplicationImageDataM> loadOldImage(Connection conn, String applicationGroupId) throws Exception
	{
		ArrayList<ApplicationImageDataM> imageList = new ArrayList<ApplicationImageDataM>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT * FROM IM_IMAGE_CAT ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			ApplicationImageDataM image = new ApplicationImageDataM();
			ArrayList<ApplicationImageSplitDataM> imageSplitList = new ArrayList<ApplicationImageSplitDataM>();
			
			while(rs.next())
			{
				//Set Application Group Transform data
				
				ApplicationImageSplitDataM split = new ApplicationImageSplitDataM();
				String docType = rs.getString("DOC_TYPE");
				if(!Util.empty(docType))
				{
					split.setDocType(("000" + docType).substring(docType.length()));
				}
				split.setDocTypeSeq(rs.getInt("PAGE_NUM"));
				split.setApplicantTypeIM(rs.getString("APPLICANT_TYPE"));
				split.setImageId(rs.getString("IMGPATH"));
				imageSplitList.add(split);
			}
			
			try
			{
				Collections.sort(imageSplitList, new ImSplitSeqComparator());
			}
			catch(Exception e)
			{
				logger.fatal(e.getLocalizedMessage());
			}
			
			image.setApplicationImageSplits(imageSplitList);
			imageList.add(image);
			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return imageList;
	}
	
	public class ImSplitSeqComparator implements Comparator<ApplicationImageSplitDataM> {
	    @Override
	    public int compare(ApplicationImageSplitDataM i1, ApplicationImageSplitDataM i2) {
	        return (new Integer(i1.getDocTypeSeq())).compareTo(new Integer(i2.getDocTypeSeq()));
	    }
	}
	
}
