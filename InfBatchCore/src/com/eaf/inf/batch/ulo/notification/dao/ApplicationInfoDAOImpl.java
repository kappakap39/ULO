package com.eaf.inf.batch.ulo.notification.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.notification.model.ApplicationResult;
import com.eaf.inf.batch.ulo.notification.model.NotifyApplication;

public class ApplicationInfoDAOImpl extends InfBatchObjectDAO implements ApplicationInfoDAO {
	private static transient Logger logger = Logger.getLogger(ApplicationInfoDAOImpl.class);
	@Override
	public List<NotifyApplication> loadNotifyApplication(String applicationGroupId) throws InfBatchException{
		String NOTIFICATION_SALE_TYPE_NORMAL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_NORMAL");
		String NOTIFICATION_SALE_TYPE_REFERENCE = InfBatchProperty.getInfBatchConfig("NOTIFICATION_SALE_TYPE_REFERENCE");
		String NOTIFICATION_CC = InfBatchProperty.getInfBatchConfig("NOTIFICATION_CC_PRODUCT");
		String NOTIFICATION_KEC = InfBatchProperty.getInfBatchConfig("NOTIFICATION_KEC_PRODUCT");
		String NOTIFICATION_KPL = InfBatchProperty.getInfBatchConfig("NOTIFICATION_KPL_PRODUCT");
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<NotifyApplication> notifyApplications = new ArrayList<NotifyApplication>(); 
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append(" SELECT ");
			sql.append("     ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID, ");
			sql.append("     ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO, ");
			sql.append("     ORIG_APPLICATION_GROUP.LIFE_CYCLE, ");
			sql.append("     ORIG_APPLICATION_GROUP.JOB_STATE, ");
			sql.append("     ORIG_PERSONAL_INFO.TH_FIRST_NAME ||' '|| ORIG_PERSONAL_INFO.TH_LAST_NAME AS CUSTOMER_NAME, ");
			sql.append("     ORIG_PERSONAL_INFO.MOBILE_NO, ");
			sql.append("     ORIG_PERSONAL_INFO.PERSONAL_ID, ");
			sql.append("     SALE.SALES_ID AS SALE_ID , ");
			sql.append("     RECOMMEND.SALES_ID AS RECOMMEND_ID , ");
			sql.append("     ORIG_APPLICATION.FINAL_APP_DECISION, ");
			sql.append("     ORIG_APPLICATION.APPLICATION_RECORD_ID, ");
			sql.append("     ORIG_CARD.CARD_TYPE, ");
			sql.append("     ORIG_CARD.CARD_LEVEL, ");
			sql.append("     ORIG_PERSONAL_INFO.PERSONAL_TYPE,");
			sql.append("     SUBSTR(ORIG_APPLICATION.BUSINESS_CLASS_ID,1,INSTR(ORIG_APPLICATION.BUSINESS_CLASS_ID,'_')-1) AS PRODUCT_NAME, ");
			sql.append("     XRULES_POLICY_RULES.REASON, ");
			sql.append("     XRULES_POLICY_RULES.POLICY_CODE, ");
			sql.append("     XRULES_POLICY_RULES.VERIFIED_RESULT, ");
			sql.append("     XRULES_POLICY_RULES.RANK ");
			sql.append(" FROM ORIG_APPLICATION_GROUP ");
			sql.append(" JOIN ORIG_PERSONAL_INFO ON ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = ORIG_PERSONAL_INFO.APPLICATION_GROUP_ID ");
			sql.append(" JOIN ORIG_PERSONAL_RELATION ON ORIG_PERSONAL_INFO.PERSONAL_ID = ORIG_PERSONAL_RELATION.PERSONAL_ID ");
			sql.append(" JOIN ORIG_APPLICATION ON ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_PERSONAL_RELATION.REF_ID");
			sql.append(" AND ORIG_PERSONAL_RELATION.RELATION_LEVEL = 'A' AND ORIG_APPLICATION_GROUP.LIFE_CYCLE = ORIG_APPLICATION.LIFE_CYCLE ");
			sql.append(" JOIN ORIG_LOAN ON ORIG_APPLICATION.APPLICATION_RECORD_ID = ORIG_LOAN.APPLICATION_RECORD_ID ");
			// KPL: Change INNER JOIN to LEFT JOIN on ORIG_CARD
			sql.append(" LEFT JOIN ORIG_CARD ON ORIG_LOAN.LOAN_ID = ORIG_CARD.LOAN_ID ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO SALE ON SALE.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_NORMAL+"' AND ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = SALE.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN ORIG_SALE_INFO RECOMMEND ON RECOMMEND.SALES_TYPE = '"+NOTIFICATION_SALE_TYPE_REFERENCE+"' AND ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID = RECOMMEND.APPLICATION_GROUP_ID ");
			sql.append(" LEFT JOIN XRULES_VERIFICATION_RESULT ON ORIG_APPLICATION.APPLICATION_RECORD_ID = XRULES_VERIFICATION_RESULT.REF_ID AND XRULES_VERIFICATION_RESULT.VER_LEVEL = 'A' ");
			sql.append(" LEFT JOIN XRULES_POLICY_RULES ON XRULES_VERIFICATION_RESULT.VER_RESULT_ID = XRULES_POLICY_RULES.VER_RESULT_ID ");
			sql.append(" WHERE ORIG_APPLICATION_GROUP.APPLICATION_GROUP_ID=? ");
			sql.append(" AND (CASE WHEN (SUBSTR(ORIG_APPLICATION.BUSINESS_CLASS_ID,1,INSTR(ORIG_APPLICATION.BUSINESS_CLASS_ID,'_')-1)) = '"+NOTIFICATION_KPL+"' THEN 1 ");
			sql.append(" WHEN (SUBSTR(ORIG_APPLICATION.BUSINESS_CLASS_ID,1,INSTR(ORIG_APPLICATION.BUSINESS_CLASS_ID,'_')-1)) IN ('"+NOTIFICATION_CC+"','"+NOTIFICATION_KEC+"') AND ORIG_CARD.CARD_ID IS NOT NULL THEN 1 ");
			sql.append(" ELSE 0 END) = 1 ");
			sql.append(" ORDER BY ORIG_APPLICATION_GROUP.APPLICATION_GROUP_NO,ORIG_PERSONAL_INFO.PERSONAL_TYPE ");
			String dSql = sql.toString();
			logger.debug("SQL : " + dSql);
			ps = conn.prepareStatement(dSql);
				int index = 1;
				ps.setString(index++, applicationGroupId);
			rs = ps.executeQuery();
			while(rs.next()){
				String applicationRecordId = rs.getString("APPLICATION_RECORD_ID");
				NotifyApplication notifyApplication = findNotifyApplication(applicationRecordId, notifyApplications);
				if(null==notifyApplication){
					notifyApplication = new NotifyApplication();
					notifyApplication.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
					notifyApplication.setApplicationRecordId(rs.getString("APPLICATION_RECORD_ID"));
					notifyApplication.setApplpicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					notifyApplication.setPersonalId(rs.getString("PERSONAL_ID"));
					notifyApplication.setCardLevel(rs.getString("CARD_LEVEL"));
					notifyApplication.setCardType(rs.getString("CARD_TYPE"));
					notifyApplication.setCustomerName(rs.getString("CUSTOMER_NAME"));
					notifyApplication.setFinalAppDecision(rs.getString("FINAL_APP_DECISION"));
					notifyApplication.setJobState(rs.getString("JOB_STATE"));
					notifyApplication.setMobileNo(rs.getString("MOBILE_NO"));
					notifyApplication.setPersonalType(rs.getString("PERSONAL_TYPE"));
					notifyApplication.setProductName(rs.getString("PRODUCT_NAME"));
					notifyApplication.setRecommendId(rs.getString("RECOMMEND_ID"));
					notifyApplication.setSaleId(rs.getString("SALE_ID"));
					notifyApplication.setVerifiedResult(rs.getString("VERIFIED_RESULT"));
					notifyApplication.setLifeCycle(rs.getInt("LIFE_CYCLE"));
					notifyApplications.add(notifyApplication);
				}
				ApplicationResult applicationResult = new ApplicationResult();
				applicationResult.setRuleId(rs.getString("POLICY_CODE"));
				applicationResult.setRuleResult(rs.getString("VERIFIED_RESULT"));
				applicationResult.setReasonCode(rs.getString("REASON"));
				applicationResult.setReasonRank(rs.getInt("RANK"));
				List<ApplicationResult> applicationResults  = notifyApplication.getApplicationResults();
				if(null==applicationResults){
					applicationResults = new ArrayList<ApplicationResult>();
					notifyApplication.setApplicationResults(applicationResults);
				}
				applicationResults.add(applicationResult);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e);
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		}
		return notifyApplications;
	}
	private NotifyApplication findNotifyApplication(String applicationRecordId,List<NotifyApplication> notifyApplications){
		if(null!=notifyApplications){
			for (NotifyApplication notifyApplication : notifyApplications) {
				if(null!=notifyApplication.getApplicationRecordId()&&notifyApplication.getApplicationRecordId().equals(applicationRecordId))
					return notifyApplication;
			}
		}
		return null;
	}
}
