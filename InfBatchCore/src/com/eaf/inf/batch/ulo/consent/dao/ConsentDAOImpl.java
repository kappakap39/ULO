package com.eaf.inf.batch.ulo.consent.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.dao.InfBatchObjectDAO;
import com.eaf.core.ulo.common.exception.InfBatchException;
import com.eaf.inf.batch.ulo.consent.model.GenerateConsentDataM;
import com.eaf.inf.batch.ulo.consent.model.GenerateConsentModelDataM;
import com.eaf.orig.ulo.control.util.ApplicationUtil;

public class ConsentDAOImpl extends InfBatchObjectDAO implements ConsentDAO{
	private static transient Logger logger = Logger.getLogger(ConsentDAOImpl.class);
	String PERSONAL_TYPE_APPLICANT = InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = InfBatchProperty.getInfBatchConfig("PERSONAL_TYPE_SUPPLEMENTARY");
	String GENERATE_CONSENT_MAIN_APPLICANT = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_MAIN_APPLICANT");
	String GENERATE_CONSENT_SUP_APPLICANT = InfBatchProperty.getInfBatchConfig("GENERATE_CONSENT_SUP_APPLICANT");
	@Override
	public ArrayList<GenerateConsentDataM> getOutputConsent() throws InfBatchException {
		ArrayList<GenerateConsentDataM> generateConsents = new ArrayList<GenerateConsentDataM>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT AG.APPLICATION_GROUP_NO SET_ID, ");
				SQL.append("     DH.CONSENT_REF_NO          CONSENT_REF_NO, ");
				SQL.append("     AG.BRANCH_NO               BRANCH_NO, ");
				SQL.append("     P.PERSONAL_TYPE            PERSONAL_TYPE, ");
				SQL.append("     P.SEQ                      SEQ, ");
				SQL.append("     DH.NCB_DOC_HISTORY_ID      NCB_DOC_HISTORY_ID, ");
				SQL.append("     AG.APPLICATION_GROUP_ID    APPLICATION_GROUP_ID ");
				SQL.append(" FROM ORIG_APPLICATION_GROUP AG ");
				SQL.append(" JOIN ORIG_PERSONAL_INFO P         ON AG.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID ");
				SQL.append(" JOIN ORIG_NCB_DOCUMENT_HISTORY DH ON P.PERSONAL_ID = DH.PERSONAL_ID ");
				SQL.append(" WHERE DH.CONSENT_GEN_DATE IS NULL ");
				String eAppSQL = ApplicationUtil.eAppSQL(" AND ( AG.SOURCE IS NULL OR AG.SOURCE NOT IN( ");
				if ( eAppSQL.length() > 0 ) {
					SQL.append( eAppSQL );
					SQL.append(" ) "); 
				}
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				GenerateConsentDataM generateConsent = new GenerateConsentDataM();
					generateConsent.setNcbDocHistoryId(rs.getString("NCB_DOC_HISTORY_ID"));
					generateConsent.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					generateConsent.setSetID(rs.getString("SET_ID"));
					generateConsent.setConsentRefNo(rs.getString("CONSENT_REF_NO"));
					generateConsent.setBranchNo(rs.getString("BRANCH_NO"));
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				int SEQ = rs.getInt("SEQ");
				if(PERSONAL_TYPE_APPLICANT.equals(PERSONAL_TYPE)){
					generateConsent.setApplicantType(GENERATE_CONSENT_MAIN_APPLICANT);
				}else{
					generateConsent.setApplicantType(GENERATE_CONSENT_SUP_APPLICANT+String.valueOf(SEQ));
				}
				generateConsents.add(generateConsent);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		return generateConsents;
	}
	@Override
	public void updateConsentGenDate(String ncbDocHistoryId)throws InfBatchException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_NCB_DOCUMENT_HISTORY ");
			sql.append(" SET  CONSENT_GEN_DATE = ? ");
			sql.append(" WHERE NCB_DOC_HISTORY_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int index = 1;
			ps.setDate(index++,InfBatchProperty.getDate());
			ps.setString(index++, ncbDocHistoryId);
			ps.executeUpdate();			
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new InfBatchException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public ArrayList<GenerateConsentModelDataM> getOutputConsentModel() throws InfBatchException {
		ArrayList<GenerateConsentModelDataM> generateConsentModels = new ArrayList<GenerateConsentModelDataM>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = getConnection();
			StringBuilder SQL = new StringBuilder("");
				SQL.append(" SELECT DISTINCT AG.APPLICATION_GROUP_NO SET_ID, ");
				SQL.append("        P.PERSONAL_TYPE PERSONAL_TYPE, ");
				SQL.append("     	P.SEQ SEQ, ");
				SQL.append("        DH.CONSENT_REF_NO CONSENT_REF_NO, ");
				SQL.append("        AG.APPLICATION_GROUP_ID APPLICATION_GROUP_ID, ");
				SQL.append("        P.CONSENT_MODEL_FLAG,TO_CHAR(D.APP_DATE,'YYYY-MM-DD') APP_DATE ");
				SQL.append(" FROM APPLICATION_DATE D, ORIG_APPLICATION_GROUP AG ");
				SQL.append(" JOIN ORIG_PERSONAL_INFO P  ON AG.APPLICATION_GROUP_ID = P.APPLICATION_GROUP_ID ");
				SQL.append(" JOIN ORIG_NCB_DOCUMENT_HISTORY DH ON P.PERSONAL_ID = DH.PERSONAL_ID ");
				SQL.append(" JOIN ORIG_APPLICATION APP ON APP.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID "); 
				SQL.append("      AND APP.APPLICATION_TYPE IN ('NEW','UPG','INC') ");
				SQL.append(" WHERE P.CONSENT_MODEL_FLAG IS NOT NULL  ");
				SQL.append("       AND NOT EXISTS ");
				SQL.append("       (SELECT 1 FROM INF_BATCH_LOG L WHERE L.APPLICATION_GROUP_ID = AG.APPLICATION_GROUP_ID AND L.INTERFACE_CODE = 'CON002' AND L.INTERFACE_STATUS='C')  ");
				String eAppSQL = ApplicationUtil.eAppSQL(" AND ( AG.SOURCE IS NULL OR AG.SOURCE NOT IN( ");
				if ( eAppSQL.length() > 0 ) {
					SQL.append( eAppSQL );
					SQL.append(" ) ");
				}
			
			logger.debug("SQL >> "+SQL);
			ps = conn.prepareStatement(SQL.toString());
			rs = ps.executeQuery();
			while(rs.next()){
				GenerateConsentModelDataM GenerateConsentModelDataM = new GenerateConsentModelDataM();
					GenerateConsentModelDataM.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
					GenerateConsentModelDataM.setSetId(rs.getString("SET_ID"));
					GenerateConsentModelDataM.setConsentRefNo(rs.getString("CONSENT_REF_NO"));
					GenerateConsentModelDataM.setConsentModelFlag(rs.getString("CONSENT_MODEL_FLAG"));
				String PERSONAL_TYPE = rs.getString("PERSONAL_TYPE");
				int SEQ = rs.getInt("SEQ");
				if(PERSONAL_TYPE_APPLICANT.equals(PERSONAL_TYPE)){
					GenerateConsentModelDataM.setApplicantType(GENERATE_CONSENT_MAIN_APPLICANT);
				}else{
					GenerateConsentModelDataM.setApplicantType(GENERATE_CONSENT_SUP_APPLICANT+String.valueOf(SEQ));
				}
				generateConsentModels.add(GenerateConsentModelDataM);
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new InfBatchException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn,rs,ps);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new InfBatchException(e.getLocalizedMessage());
			}
		}
		logger.debug("generateConsentModels size : "+generateConsentModels.size());
		return generateConsentModels;
	}
}
