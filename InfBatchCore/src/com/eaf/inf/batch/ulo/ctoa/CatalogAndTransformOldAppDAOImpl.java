package com.eaf.inf.batch.ulo.ctoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.dao.InfBatchServiceLocator;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;

public class CatalogAndTransformOldAppDAOImpl extends InfBatchObjectDAO implements CatalogAndTransformOldAppDAO
{
	private static transient Logger logger = Logger.getLogger(CatalogAndTransformOldAppDAOImpl.class); 
	String CATALOG_AND_TRANSFORM_OLD_APP_MODULE_ID = InfBatchProperty.getInfBatchConfig("CATALOG_AND_TRANSFORM_OLD_APP_MODULE_ID");
	String CATALOG_AND_TRANSFORM_OLD_APP_CUT_OFF = InfBatchProperty.getInfBatchConfig("CATALOG_AND_TRANSFORM_OLD_APP_CUT_OFF");
	String CATALOG_OLD_APP_TASK_LIMIT = InfBatchProperty.getInfBatchConfig("CATALOG_OLD_APP_TASK_LIMIT");
	String TRANSFORM_OLD_APP_TASK_LIMIT = InfBatchProperty.getInfBatchConfig("TRANSFORM_OLD_APP_TASK_LIMIT");
	@Override
	public ArrayList<String> loadOldAppGroupToCatalog() throws Exception 
	{
		String WIP_JOBSTATE_END = InfBatchProperty.getGeneralParam("WIP_JOBSTATE_END");
		String[] CONDITION = WIP_JOBSTATE_END.split(",");
		String JOB_STATE = "'"+StringUtils.join(CONDITION, "','")+"'";
		ArrayList<String> appGroupIdList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resultLimit = Integer.parseInt(CATALOG_OLD_APP_TASK_LIMIT);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP AG ");
			sql.append(" WHERE (SYSDATE - TRUNC(LAST_DECISION_DATE)) > " + CATALOG_AND_TRANSFORM_OLD_APP_CUT_OFF);
			sql.append(" AND JOB_STATE IN ("+JOB_STATE+") ");
			sql.append(" AND NOT EXISTS ( ");
			sql.append(" SELECT 1 FROM ORIG_APPLICATION_GROUP_CAT AGTF WHERE ");
			sql.append(" AG.APPLICATION_GROUP_ID = AGTF.APPLICATION_GROUP_ID )");
			logger.debug("sql : " + sql);
			logger.debug("resultLimit : " + resultLimit);
			ps = conn.prepareStatement(sql.toString());
			if(resultLimit > 0)
			{
				ps.setMaxRows(resultLimit);
			}
			rs = ps.executeQuery();
			while(rs.next())
			{
				if(!Util.empty(rs.getString("APPLICATION_GROUP_ID")))
				{appGroupIdList.add(rs.getString("APPLICATION_GROUP_ID"));}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return appGroupIdList;
	}
	
	@Override
	public ArrayList<ApplicationGroupDataM> loadAppGroupData(ArrayList<String> applicationGroupIdList) throws ApplicationException 
	{
		Connection conn = null;
		ArrayList<ApplicationGroupDataM> appGroupList = new ArrayList<ApplicationGroupDataM>();
		
		try
		{
			conn = getConnection();
			for(String appGroupId : applicationGroupIdList)
			{
					ApplicationGroupDataM tempAppGroup = selectAppGroupInfo(appGroupId, conn);
					appGroupList.add(tempAppGroup);
			}
		}
		catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e);
		}finally
		{
			try
			{
				closeConnection(conn);
			}catch(Exception e)
			{
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e);
			}
		}
		
		return appGroupList;
	}
	
	@Override
	public ArrayList<ApplicationGroupDataM> loadAppGroupTransformData(ArrayList<String> applicationGroupIdList) throws ApplicationException {
		Connection conn = null;
		ArrayList<ApplicationGroupDataM> appGroupList = new ArrayList<ApplicationGroupDataM>();
		
			try{
				conn = getConnection();
				for(String appGroupId : applicationGroupIdList)
				{
					ApplicationGroupDataM tempAppGroup = selectAppGroupInfo(appGroupId, conn);
					
					if(!Util.empty(tempAppGroup))
					{
						ArrayList<ApplicationDataM> applicationList = selectApplicationInfo(appGroupId,conn);
						if(!Util.empty(applicationList)) 
						{
							for(ApplicationDataM application : applicationList)
							{
									ArrayList<LoanDataM> loans = selectLoanInfo(application.getApplicationRecordId(),conn);
									if(!Util.empty(loans)) {
										application.setLoans(loans);
									}
							}
							tempAppGroup.addAll(applicationList);
						}
						
						ArrayList<PersonalInfoDataM> personList = selectPersonalInfo(appGroupId,conn);
						if(!Util.empty(personList))
						{
							for(PersonalInfoDataM personalInfoM: personList) 
							{
								ArrayList<PersonalRelationDataM> personalRelationList = selectOrigPersonalRelation(personalInfoM.getPersonalId(),conn);
								if(!Util.empty(personalRelationList)) {
									personalInfoM.setPersonalRelations(personalRelationList);
								}
							}
							tempAppGroup.setPersonalInfos(personList);
						}
				}
				appGroupList.add(tempAppGroup);
			}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e);
			}finally
			{
				try
				{
					closeConnection(conn);
				}catch(Exception e)
				{
					logger.fatal("ERROR ",e);
				}
			}
		
		return appGroupList;
	}
	
	@Override
	public void catalogAppGroup(ArrayList<String> appGroupIdList)throws Exception 
	{
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		
		PreparedStatement psAg = null;
		
		try{

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ORIG_APPLICATION_GROUP_CAT(");
			sql.append("APPLICATION_GROUP_NO, APPLICATION_GROUP_ID, APPLICATION_DATE, ");
			sql.append("APPLICATION_TEMPLATE, APPLICATION_STATUS, LIFE_CYCLE, ARC_DATE, ARC_STATUS  ");
			sql.append(") SELECT APPLICATION_GROUP_NO, APPLICATION_GROUP_ID, APPLICATION_DATE, ");
			sql.append(" APPLICATION_TEMPLATE, APPLICATION_STATUS, LIFE_CYCLE, SYSDATE, 0 ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			psAg = conn.prepareStatement(sql.toString());
			
			for(String appGroupId : appGroupIdList)
			{
				psAg.setString(1, appGroupId);
				psAg.addBatch();
			}

			psAg.executeBatch();
			
			conn.commit();
			conn.setAutoCommit(true);
			logger.info("Catalog Complete...");
		}
		catch(Exception e)
		{
			conn.rollback();
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}
		finally
		{
			try
			{
				closeConnection(conn, psAg);
			}catch(Exception e)
			{
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	@Override
	public void transformAppGroup(ArrayList<String> appGroupIdList)throws Exception 
	{
	
		Connection imConn = getConnection(InfBatchServiceLocator.IMG_DB);
		Connection conn = getConnection();
		conn.setAutoCommit(false);
		
		PreparedStatement psAp = null;
		PreparedStatement psPer = null;
		PreparedStatement psAttHist = null;
		PreparedStatement psIM = null;
		
		try{

			psAp = conn.prepareStatement(TransformSQLString.getAppQueryISL());
			psPer = conn.prepareStatement(TransformSQLString.getPersonalInfoQueryISL());
			psAttHist = conn.prepareStatement(TransformSQLString.getAttachmentHistoryQueryISL());
			psIM = conn.prepareStatement(TransformSQLString.getIMQuery());
			
			int count = 0;
			for(String appGroupId : appGroupIdList)
			{
				//app
				psAp.setString(1, appGroupId);
				psAp.addBatch();
				
				//per
				psPer.setString(1, appGroupId);
				psPer.addBatch();
				
				//attHist
				psAttHist.setString(1, appGroupId);
				psAttHist.addBatch();
				
				ArrayList<String> imageHashIdList = getImageSplitHashId(conn, appGroupId);
				ArrayList<IMImageCatDataM> imDataList = getIMData(imConn, imageHashIdList);
				for(IMImageCatDataM imData : imDataList)
				{
					int cntt = 1;
					psIM.setString(cntt++, appGroupId);
					psIM.setInt(cntt++, imData.getPageNum());
					psIM.setString(cntt++, imData.getDocTypeId());
					psIM.setString(cntt++, imData.getCmsNewId());
					psIM.setString(cntt++, imData.getApplicantType());
					psIM.setString(cntt++, imData.getImgPath());
					psIM.addBatch();
				}
				
				if(count >= 29)
				{
					psAp.executeBatch();
					psPer.executeBatch();
					psIM.executeBatch();
					count = 0;
				}
				else{count++;}
			}
			
			psAp.executeBatch();
			psPer.executeBatch();
			psAttHist.executeBatch();
			psIM.executeBatch();
			
			//Update ARC_STATUS to 1
			updateArcStatusSL("1", appGroupIdList);
			
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			conn.rollback();
			throw new InfBatchException(e.getMessage());
		}
		finally
		{
			try
			{
				closeConnection(psAp);
				closeConnection(psPer);
				closeConnection(psAttHist);
				closeConnection(psIM);
				closeConnection(imConn);
				closeConnection(conn);
			}catch(Exception e)
			{
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	@Override
	public ArrayList<String> getImageSplitHashId(Connection conn, String appGroupId) throws Exception
	{
		ArrayList<String> ImageSplitHashIdList = new ArrayList<String>();
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IMS.IMG_ID ");
		sql.append(" FROM ORIG_APPLICATION_IMG IM ");
		sql.append(" JOIN ORIG_APPLICATION_IMG_SPLIT IMS ON IM.APP_IMG_ID = IMS.APP_IMG_ID ");
		sql.append(" WHERE IM.APPLICATION_GROUP_ID = ? ");
		try
		{
			ps = conn.prepareStatement(sql.toString());
			
			ps.setString(1, appGroupId);
			rs = ps.executeQuery();
			while(rs.next())
			{
				ImageSplitHashIdList.add(rs.getString("IMG_ID"));
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
		
		return ImageSplitHashIdList;
	}

	public static ArrayList<IMImageCatDataM> getIMData(Connection imConn, ArrayList<String> hashIdList) throws Exception
	{
		ArrayList<IMImageCatDataM> imDATaList = new ArrayList<IMImageCatDataM>();
		
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PAGENUMBER, DOCTYPEID, ");
		sql.append(" CMS_NEW_ID, APPLICANT_TYPE, HASHING_ID");
		sql.append(" FROM IM_IMAGE ");
		sql.append(" WHERE HASHING_ID = ? ");
		try
		{
			ps = imConn.prepareStatement(sql.toString());
			for(String hashId : hashIdList)
			{
				ps.setString(1, hashId);
				rs = ps.executeQuery();
				while(rs.next())
				{
					IMImageCatDataM imTemp = new IMImageCatDataM();
					imTemp.setPageNum(rs.getInt("PAGENUMBER"));
					imTemp.setDocTypeId(rs.getString("DOCTYPEID"));
					imTemp.setCmsNewId(rs.getString("CMS_NEW_ID"));
					imTemp.setApplicantType(rs.getString("APPLICANT_TYPE"));
					//imTemp.setImgPath(rs.getString("IMGPATH"));
					imTemp.setImgPath(rs.getString("HASHING_ID"));
					imDATaList.add(imTemp);
				}
				if(rs != null){rs.close();}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				if(ps != null){ps.close();}
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		
		return imDATaList;
	}

	@Override
	public ArrayList<String> loadAppToTransform() throws Exception 
	{
		//Load data to transform - ARC_STATUS = 0
		ArrayList<String> appGroupIdList = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int resultLimit = Integer.parseInt(TRANSFORM_OLD_APP_TASK_LIMIT);
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" WHERE ARC_STATUS = '0' ");
			logger.debug("sql : " + sql);
			logger.debug("resultLimit : " + resultLimit);
			ps = conn.prepareStatement(sql.toString());
			ps.setMaxRows(resultLimit);
			rs = ps.executeQuery();
			while(rs.next())
			{
				appGroupIdList.add(rs.getString("APPLICATION_GROUP_ID"));
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR "+e.getLocalizedMessage());
			}
		}
		return appGroupIdList;
	}
	
	@Override
	public void updateArcStatusSL(String value, ArrayList<String> appGroupIdList) throws Exception 
	{
		updateArcStatusSL(value, appGroupIdList, null);
	}

	@Override
	public void updateArcStatusSL(String value, ArrayList<String> appGroupIdList, String comment) throws Exception {
		PreparedStatement ps = null;
		Connection conn = InfBatchObjectDAO.getConnection();
		try
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" SET ARC_STATUS = " + value);
			if(!Util.empty(comment))
			{
				sql.append(" , PURGE_COMMENT = ? ");
			}
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			logger.debug("sql : "+sql);
			ps = conn.prepareStatement(sql.toString());
			for(String appGroupId : appGroupIdList)
			{
				int cntt = 1;
				if(!Util.empty(comment))
				{
					ps.setString(cntt++, comment);
				}
				ps.setString(cntt++, appGroupId);
				ps.addBatch();
			}
			ps.executeBatch();
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}
		finally
		{
			try
			{
				closeConnection(conn, ps);
			}catch(Exception e)
			{
				logger.fatal("ERROR ",e);
			}
		}
	}
	
	@Override
	public boolean isPendingJobExist() throws Exception 
	{
		boolean pendingJobExist = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) AS PENDING_COUNT FROM ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" WHERE ARC_STATUS <> '2' AND ARC_STATUS <> 'F' ");
			logger.debug("sql : " + sql);
			ps = conn.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				if(rs.getInt("PENDING_COUNT") > 0)
				{
					pendingJobExist = true;
				}
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new InfBatchException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
		return pendingJobExist;
	}
	
	public ApplicationGroupDataM selectAppGroupInfo(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApplicationGroupDataM applicationGroup = null;
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_ID, APPLICATION_GROUP_NO, APPLICATION_DATE, APPLICATION_STATUS ");
			sql.append(" ,APPLICATION_TEMPLATE, LIFE_CYCLE ");
			sql.append(" FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			//logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			if(rs.next()){
				applicationGroup = new ApplicationGroupDataM();
				applicationGroup.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				applicationGroup.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				applicationGroup.setApplicationDate(rs.getDate("APPLICATION_DATE"));
				applicationGroup.setApplicationTemplate(rs.getString("APPLICATION_TEMPLATE"));
				applicationGroup.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				applicationGroup.setLifeCycle(rs.getInt("LIFE_CYCLE"));
			}
			return applicationGroup;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	public ArrayList<ApplicationDataM> selectApplicationInfo(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<ApplicationDataM> applications = new ArrayList<ApplicationDataM>();
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_NO, APPLICATION_RECORD_ID, LIFE_CYCLE, APPLICATION_TYPE, ");
			sql.append(" BUSINESS_CLASS_ID, FINAL_APP_DECISION, FINAL_APP_DECISION_DATE, CARDLINK_REF_NO ");
			sql.append(" FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? ");
			sql.append(" ORDER BY LIFE_CYCLE ASC, APPLICATION_NO ASC ");
			String dSql = sql.toString();
			//logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();

			while(rs.next()){
				ApplicationDataM application = new ApplicationDataM();
				
				application.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				application.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				application.setApplicationType(rs.getString("APPLICATION_TYPE"));
				application.setBusinessClassId(rs.getString("BUSINESS_CLASS_ID"));
				application.setFinalAppDecision(rs.getString("FINAL_APP_DECISION"));
				application.setFinalAppDecisionDate(rs.getDate("FINAL_APP_DECISION_DATE"));
				application.setCardlinkRefNo(rs.getString("CARDLINK_REF_NO"));
				
				applications.add(application);
			}
			return applications;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(ps, rs);
			}catch(Exception e){
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	public ArrayList<PersonalInfoDataM> selectPersonalInfo(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalInfoDataM> personalInfoList = new ArrayList<PersonalInfoDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT PERSONAL_ID, PERSONAL_TYPE, CIS_NO, IDNO, CID_TYPE, ");
			sql.append(" SORCE_OF_INCOME, TYPE_OF_FIN, TH_FIRST_NAME, TH_MID_NAME, TH_LAST_NAME, TOT_VERIFIED_INCOME ");
			sql.append(" FROM ORIG_PERSONAL_INFO WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			//logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();

			while(rs.next()) 
			{
				PersonalInfoDataM personalInfoM = new PersonalInfoDataM();
				personalInfoM.setPersonalId(rs.getString("PERSONAL_ID"));
				personalInfoM.setPersonalType(rs.getString("PERSONAL_TYPE"));
				personalInfoM.setCisNo(rs.getString("CIS_NO"));
				personalInfoM.setIdno(rs.getString("IDNO"));
				personalInfoM.setCidType(rs.getString("CID_TYPE"));
				personalInfoM.setSorceOfIncome(rs.getString("SORCE_OF_INCOME"));
				personalInfoM.setTypeOfFin(rs.getString("TYPE_OF_FIN"));
				personalInfoM.setThFirstName(rs.getString("TH_FIRST_NAME"));
				personalInfoM.setThMidName(rs.getString("TH_MID_NAME"));
				personalInfoM.setThLastName(rs.getString("TH_LAST_NAME"));
				personalInfoM.setTotVerifiedIncome(rs.getBigDecimal("TOT_VERIFIED_INCOME"));
				
				personalInfoList.add(personalInfoM);
			}

			return personalInfoList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	public ArrayList<PersonalRelationDataM> selectOrigPersonalRelation(
			String personalId, Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PersonalRelationDataM> personalRelationList = new ArrayList<PersonalRelationDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT REF_ID ");
			sql.append(" FROM ORIG_PERSONAL_RELATION WHERE PERSONAL_ID = ? ");
			String dSql = String.valueOf(sql);
			//logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, personalId);

			rs = ps.executeQuery();

			while(rs.next()) 
			{
				PersonalRelationDataM personalRelationM = new PersonalRelationDataM();
				personalRelationM.setRefId(rs.getString("REF_ID"));
				personalRelationList.add(personalRelationM);
			}

			return personalRelationList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	public ArrayList<LoanDataM> selectLoanInfo(String applicationRecordId,Connection conn) 
			throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<LoanDataM> loanList = new ArrayList<LoanDataM>();
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT LOAN_ID, APPLICATION_RECORD_ID, ACCOUNT_NO, ACCOUNT_OPEN_DATE ");
			sql.append(" FROM ORIG_LOAN WHERE APPLICATION_RECORD_ID = ? ");
			String dSql = String.valueOf(sql);
			//logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationRecordId);

			rs = ps.executeQuery();

			while(rs.next()) 
			{
				LoanDataM loanM = new LoanDataM();
				loanM.setLoanId(rs.getString("LOAN_ID"));
				loanM.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
				loanM.setAccountNo(rs.getString("ACCOUNT_NO"));
				loanM.setAccountOpenDate(rs.getDate("ACCOUNT_OPEN_DATE"));
				loanList.add(loanM);
			}
			
			for(LoanDataM loan: loanList){
				CardDataM cardDataM = selectCardInfo(loan.getLoanId(),conn);
				if (cardDataM != null) {
					loan.setCard(cardDataM);
				}
			}

			return loanList;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	public CardDataM selectCardInfo(String loanID,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		CardDataM cardM = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT CARD_ID, CARD_NO, CARD_TYPE, CARDLINK_CARD_CODE ");
			sql.append(" FROM ORIG_CARD WHERE LOAN_ID = ? ");
			String dSql = String.valueOf(sql);
			//logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, loanID);

			rs = ps.executeQuery();

			if (rs.next()) {
				cardM = new CardDataM();
				cardM.setCardId(rs.getString("CARD_ID"));
				cardM.setCardNo(rs.getString("CARD_NO"));
				cardM.setCardType(rs.getString("CARD_TYPE"));
				cardM.setCardLinkCardCode(rs.getString("CARDLINK_CARD_CODE"));
			}

			return cardM;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(ps, rs);
			} catch (Exception e) {
				logger.fatal("ERROR ", e);
			}
		}
	}
	
	public static void successReport(String name, int[] successCount) 
	{
		int success = 0;
		int noInfo = 0;
		int failCount = 0;
		for (int i = 0; i < successCount.length; i++) 
		{
            if (successCount[i] >= 0) 
            {
                success++;
            } else if (successCount[i] == Statement.SUCCESS_NO_INFO)
            {
            	noInfo++;
            } else if (successCount[i] == Statement.EXECUTE_FAILED) 
            {
                failCount++;
            }
        }
		
		 logger.debug("Batch Execute Report of [" + name + "] - Success : " + success + " - No Info : " + noInfo + " - Failed Count : " + failCount);
	}
	
	static class TransformSQLString
	{
		public static String getAppGroupQuery()
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ORIG_APPLICATION_GROUP_CAT ");
			sql.append(" ( ");
			sql.append(" APPLICATION_GROUP_NO, APPLICATION_GROUP_ID, ");
			sql.append(" APPLICATION_DATE, APPLICATION_TEMPLATE, APPLICATION_STATUS, ");
			sql.append(" LIFE_CYCLE, ARC_DATE, ARC_STATUS ");
			sql.append(" )VALUES( ");
			sql.append(" ? , ? , ? , ? , ? , ? , SYSDATE , ? ");
			sql.append(" ) ");
			return sql.toString();
		}
		
		public static String getAppQuery()
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ORIG_APPLICATION_CAT ");
			sql.append(" ( ");
			sql.append(" APPLICATION_GROUP_ID, APPLICATION_RECORD_ID, LIFE_CYCLE, "); 
			sql.append(" APPLICATION_TYPE, BUSINESS_CLASS_ID, FINAL_APP_DECISION, ");
			sql.append(" FINAL_APP_DECISION_DATE, CARDLINK_REF_NO, CARD_TYPE, "); 
			sql.append(" CARDLINK_CARD_CODE, CARD_NO, ACCOUNT_NO, ACCOUNT_OPEN_DATE ");
			sql.append(" )VALUES( ");
			sql.append(" ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ");
			sql.append(" ) ");
			return sql.toString();
		}
		
		public static String getPersonalInfoQuery()
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ORIG_PERSONAL_INFO_CAT ");
			sql.append(" ( ");
			sql.append(" APPLICATION_GROUP_ID, PERSONAL_ID, PERSONAL_TYPE, CIS_ID, ");
			sql.append(" BIRTH_DATE, IDNO, CID_TYPE, SORCE_OF_INCOME, ");
			sql.append(" TYPE_OF_FIN, TH_FIRST_NAME, TH_MID_NAME, ");
			sql.append(" TH_LAST_NAME, TOT_VERIFIED_INCOME, ");
			sql.append(" APPLICATION_RECORD_ID ");
			sql.append(" )VALUES( ");
			sql.append(" ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?  ");
			sql.append(" ) ");
			return sql.toString();
		}
		
		public static String getIMQuery()
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO IM_IMAGE_CAT ");
			sql.append(" ( ");
			sql.append(" APPLICATION_GROUP_ID, PAGE_NUM, DOC_TYPE, ");
			sql.append(" CMS_NEW_ID, APPLICANT_TYPE, IMGPATH ");
			sql.append(" )VALUES( ");
			sql.append(" ? , ? , ? , ? , ? , ? ");
			sql.append(" ) ");
			return sql.toString();
		}
		
		public static String getAppQueryISL()
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ORIG_APPLICATION_CAT ");
			sql.append(" ( ");
			sql.append(" APPLICATION_GROUP_ID, APPLICATION_RECORD_ID, LIFE_CYCLE, "); 
			sql.append(" APPLICATION_TYPE, BUSINESS_CLASS_ID, FINAL_APP_DECISION, ");
			sql.append(" FINAL_APP_DECISION_DATE, CARDLINK_REF_NO, CARD_TYPE, "); 
			sql.append(" CARDLINK_CARD_CODE, CARD_NO, ACCOUNT_NO, ACCOUNT_OPEN_DATE ");
			sql.append(" ,PERSONAL_ID ");
			sql.append(" ) ");
			sql.append(" SELECT ");
			sql.append(" AP.APPLICATION_GROUP_ID, AP.APPLICATION_RECORD_ID, AP.LIFE_CYCLE, "); 
			sql.append(" AP.APPLICATION_TYPE, AP.BUSINESS_CLASS_ID, AP.FINAL_APP_DECISION, ");
			sql.append(" AP.FINAL_APP_DECISION_DATE, AP.CARDLINK_REF_NO, CARD.CARD_TYPE, "); 
			sql.append(" CARD.CARDLINK_CARD_CODE, CARD.CARD_NO, LN.ACCOUNT_NO, LN.ACCOUNT_OPEN_DATE ");
			sql.append(" ,REL.PERSONAL_ID ");
			sql.append(" FROM ORIG_APPLICATION AP "); 
			sql.append(" LEFT JOIN ORIG_PERSONAL_RELATION REL ON REL.REF_ID = AP.APPLICATION_RECORD_ID ");
			sql.append(" LEFT JOIN ORIG_LOAN LN ON LN.APPLICATION_RECORD_ID = AP.APPLICATION_RECORD_ID ");
			sql.append(" LEFT JOIN ORIG_CARD CARD ON LN.LOAN_ID = CARD.LOAN_ID ");
			sql.append(" WHERE AP.APPLICATION_GROUP_ID = ? ");
			return sql.toString();
		}
		
		public static String getPersonalInfoQueryISL()
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ORIG_PERSONAL_INFO_CAT ");
			sql.append(" ( ");
			sql.append(" APPLICATION_GROUP_ID, PERSONAL_ID, PERSONAL_TYPE, CIS_ID, ");
			sql.append(" BIRTH_DATE, IDNO, CID_TYPE, SORCE_OF_INCOME, ");
			sql.append(" TYPE_OF_FIN, TH_FIRST_NAME, TH_MID_NAME, ");
			sql.append(" TH_LAST_NAME, TOT_VERIFIED_INCOME ");
			sql.append(" ) ");
			sql.append(" SELECT ");
			sql.append(" APPLICATION_GROUP_ID, PERSONAL_ID, PERSONAL_TYPE, CIS_NO, ");
			sql.append(" BIRTH_DATE, IDNO, CID_TYPE, SORCE_OF_INCOME, ");
			sql.append(" TYPE_OF_FIN, TH_FIRST_NAME, TH_MID_NAME, ");
			sql.append(" TH_LAST_NAME, TOT_VERIFIED_INCOME ");
			sql.append(" FROM ORIG_PERSONAL_INFO ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			return sql.toString();
		}
		
		public static String getAttachmentHistoryQueryISL()
		{
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO ORIG_ATTACHMENT_HISTORY_CAT ");
			sql.append(" ( ");
			sql.append(" APPLICATION_RECORD_ID, ATTACH_ID, FILE_NAME, FILE_SIZE, ");
			sql.append(" FILE_TYPE, FILE_PATH, MIME_TYPE, APPLICATION_GROUP_ID, ");
			sql.append(" FILE_TYPE_OTH, REF_ID ");
			sql.append(" ) ");
			sql.append(" SELECT ");
			sql.append(" APPLICATION_RECORD_ID, ATTACH_ID, FILE_NAME, FILE_SIZE, ");
			sql.append(" FILE_TYPE, FILE_PATH, MIME_TYPE, APPLICATION_GROUP_ID, ");
			sql.append(" FILE_TYPE_OTH, REF_ID ");
			sql.append(" FROM ORIG_ATTACHMENT_HISTORY ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
			return sql.toString();
		}
		
	}
}
