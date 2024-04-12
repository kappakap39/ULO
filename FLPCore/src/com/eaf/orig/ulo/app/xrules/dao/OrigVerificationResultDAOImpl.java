package com.eaf.orig.ulo.app.xrules.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.DocumentVerificationDataM;
import com.eaf.orig.ulo.model.app.HRVerificationDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionDataM;
import com.eaf.orig.ulo.model.app.IdentifyQuestionSetDataM;
import com.eaf.orig.ulo.model.app.PhoneVerificationDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;
import com.google.gson.Gson;

public class OrigVerificationResultDAOImpl extends OrigObjectDAO implements	OrigVerificationResultDAO {
	public OrigVerificationResultDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigVerificationResultDAOImpl(){
		
	}
	private String userId = "";
	@Override
	public void createOrigVerificationResultM(VerificationResultDataM verificationResultM) throws ApplicationException {
		try {
			logger.debug("verificationResultM.getVerResultId() :"+verificationResultM.getVerResultId());
		    if(Util.empty(verificationResultM.getVerResultId())){
				String verResultId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_VERIFICATION_RESULT_PK); 
				verificationResultM.setVerResultId(verResultId);
			}
		    verificationResultM.setCreateBy(userId);
		    verificationResultM.setUpdateBy(userId);
			createTableXRULES_VERIFICATION_RESULT(verificationResultM);
			if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verificationResultM.getVerLevel())){
				ArrayList<HRVerificationDataM> hrVerificationList = verificationResultM.getHrVerifications();
				if(!Util.empty(hrVerificationList)) {
					OrigHRVerificationDAO hrVerificationDAO = ORIGDAOFactory.getHRVerificationDAO(userId);
					for(HRVerificationDataM hrVerificationM: hrVerificationList){
						hrVerificationM.setVerResultId(verificationResultM.getVerResultId());
						hrVerificationDAO.createOrigHRVerificationM(hrVerificationM);
					}
				}
				
				ArrayList<IdentifyQuestionDataM> identifyQuestionList = verificationResultM.getIndentifyQuestions();
				if(!Util.empty(identifyQuestionList)) {
					OrigIdentifyQuestionDAO identifyQuestionDAO = ORIGDAOFactory.getIdentifyQuestionDAO(userId);
					for(IdentifyQuestionDataM identifyQueM: identifyQuestionList){
						identifyQueM.setVerResultId(verificationResultM.getVerResultId());
						identifyQuestionDAO.createOrigIdentifyQuestionM(identifyQueM);
					}
				}
				
				ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList = verificationResultM.getIndentifyQuesitionSets();
				if(!Util.empty(identifyQuestionSetList)) {
					OrigIdentifyQuestionSetDAO identifyQuestionSetDAO = ORIGDAOFactory.getIdentifyQuestionSetDAO(userId);
					for(IdentifyQuestionSetDataM identifyQueSetM: identifyQuestionSetList){
						identifyQueSetM.setVerResultId(verificationResultM.getVerResultId());
						identifyQuestionSetDAO.createOrigIdentifyQuestionSetM(identifyQueSetM);
					}
				}
				
				ArrayList<PhoneVerificationDataM> phoneVerificationList = verificationResultM.getPhoneVerifications();
				if(!Util.empty(phoneVerificationList)) {
					OrigPhoneVerificationDAO phoneVerificationDAO = ORIGDAOFactory.getPhoneVerificationDAO(userId);
					for(PhoneVerificationDataM phoneVerifM: phoneVerificationList){
						phoneVerifM.setVerResultId(verificationResultM.getVerResultId());
						phoneVerificationDAO.createOrigPhoneVerificationM(phoneVerifM);
					}
				}
				
				ArrayList<WebVerificationDataM> webVerificationList = verificationResultM.getWebVerifications();
				if(!Util.empty(webVerificationList)) {
					OrigWebVerificationDAO webVerificationDAO = ORIGDAOFactory.getWebVerificationDAO(userId);
					for(WebVerificationDataM webVerifM: webVerificationList){
						webVerifM.setVerResultId(verificationResultM.getVerResultId());
						webVerificationDAO.createOrigWebVerificationM(webVerifM);
					}
				}
				
				ArrayList<PrivilegeProjectCodeDataM> projCodeList = verificationResultM.getPrivilegeProjectCodes();
				if(!Util.empty(projCodeList)) {
					OrigPrivilegeProjectCodeDAO prvlgProjectCodeDAO = ORIGDAOFactory.getPrivilegeProjectCodeDAO(userId);
					for(PrivilegeProjectCodeDataM prvlgProjCodeM: projCodeList){
						prvlgProjCodeM.setVerResultId(verificationResultM.getVerResultId());
						prvlgProjectCodeDAO.createOrigPrivilegeProjectCodeM(prvlgProjCodeM);
					}
				}
				
				ArrayList<DocumentVerificationDataM> docVerificationList = verificationResultM.getDocumentVerifications();
				if(!Util.empty(docVerificationList)) {
					OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO(userId);
					for(DocumentVerificationDataM documentVerifM: docVerificationList){
						documentVerifM.setVerResultId(verificationResultM.getVerResultId());
						docVerificationDAO.createOrigDocumentVerificationM(documentVerifM);
					}
				}
				
				ArrayList<RequiredDocDataM> requiredDocList = verificationResultM.getRequiredDocs();
				if(!Util.empty(requiredDocList)) {
					OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO(userId);
					for(RequiredDocDataM requiredDocM: requiredDocList){
						requiredDocM.setVerResultId(verificationResultM.getVerResultId());
						requiredDocDAO.createOrigRequiredDocM(requiredDocM);
					}
				}
				
				ArrayList<PolicyRulesDataM> policyRulesList = verificationResultM.getPolicyRules();
				if(!Util.empty(policyRulesList)) {
					OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO(userId);
					for(PolicyRulesDataM policyRulesM: policyRulesList){
						policyRulesM.setVerResultId(verificationResultM.getVerResultId());
						policyRulesDAO.createOrigPolicyRulesM(policyRulesM);
					}
				}
				
			}else if(MConstant.REF_LEVEL.APPLICATION.equals(verificationResultM.getVerLevel())){				
				ArrayList<PolicyRulesDataM> policyRulesList = verificationResultM.getPolicyRules();
				if(!Util.empty(policyRulesList)) {
					OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO(userId);
					for(PolicyRulesDataM policyRulesM: policyRulesList){
						policyRulesM.setVerResultId(verificationResultM.getVerResultId());
						policyRulesDAO.createOrigPolicyRulesM(policyRulesM);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void createOrigVerificationResultM(VerificationResultDataM verificationResultM,Connection conn) throws ApplicationException {
		try {
			logger.debug("verificationResultM.getVerResultId() :"+verificationResultM.getVerResultId());
		    if(Util.empty(verificationResultM.getVerResultId())){
				String verResultId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_VERIFICATION_RESULT_PK,conn); 
				verificationResultM.setVerResultId(verResultId);
			}
		    verificationResultM.setCreateBy(userId);
		    verificationResultM.setUpdateBy(userId);
			createTableXRULES_VERIFICATION_RESULT(verificationResultM,conn);
			if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verificationResultM.getVerLevel())){
				ArrayList<HRVerificationDataM> hrVerificationList = verificationResultM.getHrVerifications();
				if(!Util.empty(hrVerificationList)) {
					OrigHRVerificationDAO hrVerificationDAO = ORIGDAOFactory.getHRVerificationDAO(userId);
					for(HRVerificationDataM hrVerificationM: hrVerificationList){
						hrVerificationM.setVerResultId(verificationResultM.getVerResultId());
						hrVerificationDAO.createOrigHRVerificationM(hrVerificationM,conn);
					}
				}
				
				ArrayList<IdentifyQuestionDataM> identifyQuestionList = verificationResultM.getIndentifyQuestions();
				if(!Util.empty(identifyQuestionList)) {
					OrigIdentifyQuestionDAO identifyQuestionDAO = ORIGDAOFactory.getIdentifyQuestionDAO(userId);
					for(IdentifyQuestionDataM identifyQueM: identifyQuestionList){
						identifyQueM.setVerResultId(verificationResultM.getVerResultId());
						identifyQuestionDAO.createOrigIdentifyQuestionM(identifyQueM,conn);
					}
				}
				
				ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList = verificationResultM.getIndentifyQuesitionSets();
				if(!Util.empty(identifyQuestionSetList)) {
					OrigIdentifyQuestionSetDAO identifyQuestionSetDAO = ORIGDAOFactory.getIdentifyQuestionSetDAO(userId);
					for(IdentifyQuestionSetDataM identifyQueSetM: identifyQuestionSetList){
						identifyQueSetM.setVerResultId(verificationResultM.getVerResultId());
						identifyQuestionSetDAO.createOrigIdentifyQuestionSetM(identifyQueSetM,conn);
					}
				}
				
				ArrayList<PhoneVerificationDataM> phoneVerificationList = verificationResultM.getPhoneVerifications();
				if(!Util.empty(phoneVerificationList)) {
					OrigPhoneVerificationDAO phoneVerificationDAO = ORIGDAOFactory.getPhoneVerificationDAO(userId);
					for(PhoneVerificationDataM phoneVerifM: phoneVerificationList){
						phoneVerifM.setVerResultId(verificationResultM.getVerResultId());
						phoneVerificationDAO.createOrigPhoneVerificationM(phoneVerifM,conn);
					}
				}
				
				ArrayList<WebVerificationDataM> webVerificationList = verificationResultM.getWebVerifications();
				if(!Util.empty(webVerificationList)) {
					OrigWebVerificationDAO webVerificationDAO = ORIGDAOFactory.getWebVerificationDAO(userId);
					for(WebVerificationDataM webVerifM: webVerificationList){
						webVerifM.setVerResultId(verificationResultM.getVerResultId());
						webVerificationDAO.createOrigWebVerificationM(webVerifM,conn);
					}
				}
				
				ArrayList<PrivilegeProjectCodeDataM> projCodeList = verificationResultM.getPrivilegeProjectCodes();
				if(!Util.empty(projCodeList)) {
					OrigPrivilegeProjectCodeDAO prvlgProjectCodeDAO = ORIGDAOFactory.getPrivilegeProjectCodeDAO(userId);
					for(PrivilegeProjectCodeDataM prvlgProjCodeM: projCodeList){
						prvlgProjCodeM.setVerResultId(verificationResultM.getVerResultId());
						prvlgProjectCodeDAO.createOrigPrivilegeProjectCodeM(prvlgProjCodeM);
					}
				}
				
				ArrayList<DocumentVerificationDataM> docVerificationList = verificationResultM.getDocumentVerifications();
				if(!Util.empty(docVerificationList)) {
					OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO(userId);
					for(DocumentVerificationDataM documentVerifM: docVerificationList){
						documentVerifM.setVerResultId(verificationResultM.getVerResultId());
						docVerificationDAO.createOrigDocumentVerificationM(documentVerifM,conn);
					}
				}
				
				ArrayList<RequiredDocDataM> requiredDocList = verificationResultM.getRequiredDocs();
				if(!Util.empty(requiredDocList)) {
					OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO(userId);
					for(RequiredDocDataM requiredDocM: requiredDocList){
						requiredDocM.setVerResultId(verificationResultM.getVerResultId());
						requiredDocDAO.createOrigRequiredDocM(requiredDocM,conn);
					}
				}
				
				ArrayList<PolicyRulesDataM> policyRulesList = verificationResultM.getPolicyRules();
				if(!Util.empty(policyRulesList)) {
					OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO(userId);
					for(PolicyRulesDataM policyRulesM: policyRulesList){
						policyRulesM.setVerResultId(verificationResultM.getVerResultId());
						policyRulesDAO.createOrigPolicyRulesM(policyRulesM,conn);
					}
				}
				
			}else if(MConstant.REF_LEVEL.APPLICATION.equals(verificationResultM.getVerLevel())){				
				ArrayList<PolicyRulesDataM> policyRulesList = verificationResultM.getPolicyRules();
				if(!Util.empty(policyRulesList)) {
					OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO(userId);
					for(PolicyRulesDataM policyRulesM: policyRulesList){
						policyRulesM.setVerResultId(verificationResultM.getVerResultId());
						policyRulesDAO.createOrigPolicyRulesM(policyRulesM,conn);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	public void createTableXRULES_VERIFICATION_RESULT(
			VerificationResultDataM verificationResultM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			createTableXRULES_VERIFICATION_RESULT(verificationResultM, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	public void createTableXRULES_VERIFICATION_RESULT(
			VerificationResultDataM verificationResultM,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("INSERT INTO XRULES_VERIFICATION_RESULT ");
			sql.append("( VER_RESULT_ID, REF_ID, VER_LEVEL, SUMMARY_INCOME_RESULT, SUMMARY_INCOME_RESULT_CODE, ");
			sql.append(" VER_CUS_RESULT, VER_CUS_RESULT_CODE, VER_HR_RESULT, VER_HR_RESULT_CODE, ");
			sql.append(" DOC_COMPLETED_FLAG, REQUIRED_VER_CUST_FLAG, REQUIRED_VER_HR_FLAG, ");
			sql.append(" REQUIRED_VER_INCOME_FLAG, REQUIRED_VER_PRIVILEGE_FLAG, REQUIRED_VER_WEB_FLAG, ");
			sql.append(" VER_PRIVILEGE_RESULT, VER_PRIVILEGE_RESULT_CODE, VER_WEB_RESULT, VER_WEB_RESULT_CODE, ");
			sql.append(" BUS_REGIST_VER_RESULT, VER_CUS_COMPLETE, RISK_GRADE_COARSE_CODE, RISK_GRADE_FINE_CODE, ");
			sql.append(" COMPARE_SIGNATURE_RESULT, REQUIRED_PRIORITY_PASS, DOC_FOLLOW_UP_STATUS, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, A_SCORE_CODE  )");

			sql.append(" VALUES(?,?,?,?,?,  ?,?,?,?,  ?,?,?,  ?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,?,  ?,?,?,?, ? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, verificationResultM.getVerResultId());
			ps.setString(cnt++, verificationResultM.getRefId());
			ps.setString(cnt++, verificationResultM.getVerLevel());
			ps.setString(cnt++, verificationResultM.getSummaryIncomeResult());
			ps.setString(cnt++, verificationResultM.getSummaryIncomeResultCode());
			
			ps.setString(cnt++, verificationResultM.getVerCusResult());
			ps.setString(cnt++, verificationResultM.getVerCusResultCode());
			ps.setString(cnt++, verificationResultM.getVerHrResult());
			ps.setString(cnt++, verificationResultM.getVerHrResultCode());

			ps.setString(cnt++, verificationResultM.getDocCompletedFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerCustFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerHrFlag());
			
			ps.setString(cnt++, verificationResultM.getRequiredVerIncomeFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerPrivilegeFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerWebFlag());
			
			ps.setString(cnt++, verificationResultM.getVerPrivilegeResult());
			ps.setString(cnt++, verificationResultM.getVerPrivilegeResultCode());
			ps.setString(cnt++, verificationResultM.getVerWebResult());
			ps.setString(cnt++, verificationResultM.getVerWebResultCode());
			
			ps.setString(cnt++, verificationResultM.getBusRegistVerResult());
			ps.setString(cnt++, verificationResultM.getVerCusComplete());
			ps.setString(cnt++, verificationResultM.getRiskGradeCoarseCode());
			ps.setString(cnt++, verificationResultM.getRiskGradeFineCode());
			
			ps.setString(cnt++, verificationResultM.getCompareSignatureResult());
			ps.setString(cnt++, verificationResultM.getRequirePriorityPass());
			ps.setString(cnt++, verificationResultM.getDocFollowUpStatus());
			
			ps.setString(cnt++, verificationResultM.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, verificationResultM.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			
			ps.setString(cnt++, verificationResultM.getAScoreCode());
			
			ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void deleteOrigVerificationResultM(String refId, String verLevel,String verResultId) throws ApplicationException {
		try {
			OrigHRVerificationDAO hrVerificationDAO = ORIGDAOFactory.getHRVerificationDAO();
			OrigIdentifyQuestionDAO identifyQuestionDAO = ORIGDAOFactory.getIdentifyQuestionDAO();
			OrigIdentifyQuestionSetDAO identifyQuestionSetDAO = ORIGDAOFactory.getIdentifyQuestionSetDAO();
			OrigPhoneVerificationDAO phoneVerificationDAO = ORIGDAOFactory.getPhoneVerificationDAO();
			OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO();
			OrigWebVerificationDAO webVerificationDAO = ORIGDAOFactory.getWebVerificationDAO();
			OrigPrivilegeProjectCodeDAO prvlgProjectCodeDAO = ORIGDAOFactory.getPrivilegeProjectCodeDAO();
			OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO();
			OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO();
			
			if(Util.empty(verResultId)) {
				String verificationResultId = loadVerResultIdXRULES_VERIFICATION_RESULT(refId, verLevel);
				if(!Util.empty(verificationResultId)) {
					hrVerificationDAO.deleteOrigHRVerificationM(verificationResultId, null);
					identifyQuestionDAO.deleteOrigIdentifyQuestionM(verificationResultId, null);
					identifyQuestionSetDAO.deleteOrigIdentifyQuestionSetM(verificationResultId, null);
					phoneVerificationDAO.deleteOrigPhoneVerificationM(verificationResultId, null);
					policyRulesDAO.deleteOrigPolicyRulesM(verificationResultId, null);
					webVerificationDAO.deleteOrigWebVerificationM(verificationResultId, null);
					prvlgProjectCodeDAO.deleteOrigPrivilegeProjectCodeM(verificationResultId, null);
					docVerificationDAO.deleteOrigDocumentVerificationM(verificationResultId, null);
					requiredDocDAO.deleteOrigRequiredDocM(verificationResultId, null);
				}
			} else {
				hrVerificationDAO.deleteOrigHRVerificationM(verResultId, null);
				identifyQuestionDAO.deleteOrigIdentifyQuestionM(verResultId, null);
				identifyQuestionSetDAO.deleteOrigIdentifyQuestionSetM(verResultId, null);
				phoneVerificationDAO.deleteOrigPhoneVerificationM(verResultId, null);
				policyRulesDAO.deleteOrigPolicyRulesM(verResultId, null);
				webVerificationDAO.deleteOrigWebVerificationM(verResultId, null);
				prvlgProjectCodeDAO.deleteOrigPrivilegeProjectCodeM(verResultId, null);
				docVerificationDAO.deleteOrigDocumentVerificationM(verResultId, null);
				requiredDocDAO.deleteOrigRequiredDocM(verResultId, null);
			}
			
			deleteTableXRULES_VERIFICATION_RESULT(refId, verLevel, verResultId);
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}

	}

	private String loadVerResultIdXRULES_VERIFICATION_RESULT(
			String refId, String verLevel) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT VER_RESULT_ID ");
			sql.append(" FROM XRULES_VERIFICATION_RESULT WHERE REF_ID = ? AND VER_LEVEL = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, refId);
			ps.setString(2, verLevel);

			rs = ps.executeQuery();

			if(rs.next()) {
				return rs.getString("VER_RESULT_ID");
			}

			return null;
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

	private void deleteTableXRULES_VERIFICATION_RESULT(String refId,
			String verLevel, String verResultId) throws ApplicationException {
		if(Util.empty(refId) || Util.empty(verLevel)) {
			return;
		}
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append(" DELETE XRULES_VERIFICATION_RESULT ");
			sql.append(" WHERE REF_ID = ? ");
			sql.append(" AND VER_LEVEL = ? ");
			if(!Util.empty(verResultId)) {
				sql.append(" AND VER_RESULT_ID = ? ");
			}
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, refId);
			ps.setString(2, verLevel);
			if(!Util.empty(verResultId)) {
				ps.setString(3, verResultId);
			}
			ps.executeUpdate();

		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	@Override
	public VerificationResultDataM loadOrigVerificationResultM(
			String refId, String verLevel) throws ApplicationException {
		VerificationResultDataM verificationResultM = selectTableXRULES_VERIFICATION_RESULT(refId, verLevel);
		if(!Util.empty(verificationResultM)) {
			OrigHRVerificationDAO hrVerificationDAO = ORIGDAOFactory.getHRVerificationDAO();
			OrigIdentifyQuestionDAO identifyQuestionDAO = ORIGDAOFactory.getIdentifyQuestionDAO();
			OrigIdentifyQuestionSetDAO identifyQuestionSetDAO = ORIGDAOFactory.getIdentifyQuestionSetDAO();
			OrigPhoneVerificationDAO phoneVerificationDAO = ORIGDAOFactory.getPhoneVerificationDAO();
			OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO();
			OrigWebVerificationDAO webVerificationDAO = ORIGDAOFactory.getWebVerificationDAO();
			OrigPrivilegeProjectCodeDAO prvlgProjectCodeDAO = ORIGDAOFactory.getPrivilegeProjectCodeDAO();
			OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO();
			OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO();
			
			if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verLevel)){
				ArrayList<HRVerificationDataM> hrVerificationList = hrVerificationDAO.loadOrigHRVerificationM(verificationResultM.getVerResultId());
				if(!Util.empty(hrVerificationList)) {
					verificationResultM.setHrVerifications(hrVerificationList);
				}
				ArrayList<IdentifyQuestionDataM> identifyQuestionList = identifyQuestionDAO.loadOrigIdentifyQuestionM(verificationResultM.getVerResultId());
				if(!Util.empty(identifyQuestionList)) {
					verificationResultM.setIndentifyQuestions(identifyQuestionList);
				}
				ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList = identifyQuestionSetDAO.loadOrigIdentifyQuestionSetM(verificationResultM.getVerResultId());
				if(!Util.empty(identifyQuestionSetList)) {
					verificationResultM.setIndentifyQuesitionSets(identifyQuestionSetList);
				}
				ArrayList<PhoneVerificationDataM> phoneVerificationList = phoneVerificationDAO.loadOrigPhoneVerificationM(verificationResultM.getVerResultId());
				if(!Util.empty(phoneVerificationList)) {
					verificationResultM.setPhoneVerifications(phoneVerificationList);
				}
				ArrayList<WebVerificationDataM> webVerificationList = webVerificationDAO.loadOrigWebVerificationM(verificationResultM.getVerResultId());
				if(!Util.empty(webVerificationList)) {
					verificationResultM.setWebVerifications(webVerificationList);
				}
				ArrayList<PrivilegeProjectCodeDataM> projCodeList = prvlgProjectCodeDAO.loadOrigPrivilegeProjectCodeM(verificationResultM.getVerResultId());
				if(!Util.empty(projCodeList)) {
					verificationResultM.setPrivilegeProjectCodes(projCodeList);
				}
				ArrayList<DocumentVerificationDataM> docVerificationList = docVerificationDAO.loadOrigDocumentVerificationM(verificationResultM.getVerResultId());
				if(!Util.empty(docVerificationList)) {
					verificationResultM.setDocumentVerifications(docVerificationList);
				}
				ArrayList<RequiredDocDataM> requiredDocList = requiredDocDAO.loadOrigRequiredDocM(verificationResultM.getVerResultId());
				if(!Util.empty(requiredDocList)) {
					verificationResultM.setRequiredDocs(requiredDocList);
				}
				
				ArrayList<PolicyRulesDataM> policyRulesList = policyRulesDAO.loadOrigPolicyRulesM(verificationResultM.getVerResultId());
				if(!Util.empty(policyRulesList)) {
					verificationResultM.setPolicyRules(policyRulesList);
				}
				
			}else if(MConstant.REF_LEVEL.APPLICATION.equals(verLevel)){
				ArrayList<PolicyRulesDataM> policyRulesList = policyRulesDAO.loadOrigPolicyRulesM(verificationResultM.getVerResultId());
				if(!Util.empty(policyRulesList)) {
					verificationResultM.setPolicyRules(policyRulesList);
				}
			}
		}
		return verificationResultM;
	}
	
	@Override
	public VerificationResultDataM loadVerificationResultDocument(String refId,String verLevel, Connection conn) throws ApplicationException {
		VerificationResultDataM verificationResult = selectTableXRULES_VERIFICATION_RESULT(refId,verLevel,conn);
		if(!Util.empty(verificationResult)) {
			OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO();
			OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO();
			if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verLevel)){
				ArrayList<DocumentVerificationDataM> documentVerifications = docVerificationDAO.loadOrigDocumentVerificationM(verificationResult.getVerResultId(),conn);
				if(!Util.empty(documentVerifications)) {
					verificationResult.setDocumentVerifications(documentVerifications);
				}
				ArrayList<RequiredDocDataM> requiredDocs = requiredDocDAO.loadOrigRequiredDocM(verificationResult.getVerResultId(),conn);
				if(!Util.empty(requiredDocs)) {
					verificationResult.setRequiredDocs(requiredDocs);
				}
			}
		}
		return verificationResult;
	}
	@Override
	public VerificationResultDataM loadOrigVerificationResultM(
			String refId, String verLevel,Connection conn) throws ApplicationException {
		VerificationResultDataM verificationResultM = selectTableXRULES_VERIFICATION_RESULT(refId,verLevel,conn);
		if(!Util.empty(verificationResultM)) {
			OrigHRVerificationDAO hrVerificationDAO = ORIGDAOFactory.getHRVerificationDAO();
			OrigIdentifyQuestionDAO identifyQuestionDAO = ORIGDAOFactory.getIdentifyQuestionDAO();
			OrigIdentifyQuestionSetDAO identifyQuestionSetDAO = ORIGDAOFactory.getIdentifyQuestionSetDAO();
			OrigPhoneVerificationDAO phoneVerificationDAO = ORIGDAOFactory.getPhoneVerificationDAO();
			OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO();
			OrigWebVerificationDAO webVerificationDAO = ORIGDAOFactory.getWebVerificationDAO();
			OrigPrivilegeProjectCodeDAO prvlgProjectCodeDAO = ORIGDAOFactory.getPrivilegeProjectCodeDAO();
			OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO();
			OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO();
			
			if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verLevel)){
				ArrayList<HRVerificationDataM> hrVerificationList = hrVerificationDAO.loadOrigHRVerificationM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(hrVerificationList)) {
					verificationResultM.setHrVerifications(hrVerificationList);
				}
				ArrayList<IdentifyQuestionDataM> identifyQuestionList = identifyQuestionDAO.loadOrigIdentifyQuestionM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(identifyQuestionList)) {
					verificationResultM.setIndentifyQuestions(identifyQuestionList);
				}
				ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList = identifyQuestionSetDAO.loadOrigIdentifyQuestionSetM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(identifyQuestionSetList)) {
					verificationResultM.setIndentifyQuesitionSets(identifyQuestionSetList);
				}
				ArrayList<PhoneVerificationDataM> phoneVerificationList = phoneVerificationDAO.loadOrigPhoneVerificationM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(phoneVerificationList)) {
					verificationResultM.setPhoneVerifications(phoneVerificationList);
				}
				ArrayList<WebVerificationDataM> webVerificationList = webVerificationDAO.loadOrigWebVerificationM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(webVerificationList)) {
					verificationResultM.setWebVerifications(webVerificationList);
				}
				ArrayList<PrivilegeProjectCodeDataM> projCodeList = prvlgProjectCodeDAO.loadOrigPrivilegeProjectCodeM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(projCodeList)) {
					verificationResultM.setPrivilegeProjectCodes(projCodeList);
				}
				ArrayList<DocumentVerificationDataM> docVerificationList = docVerificationDAO.loadOrigDocumentVerificationM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(docVerificationList)) {
					verificationResultM.setDocumentVerifications(docVerificationList);
				}
				ArrayList<RequiredDocDataM> requiredDocList = requiredDocDAO.loadOrigRequiredDocM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(requiredDocList)) {
					verificationResultM.setRequiredDocs(requiredDocList);
				}
				ArrayList<PolicyRulesDataM> policyRulesList = policyRulesDAO.loadOrigPolicyRulesM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(policyRulesList)) {
					verificationResultM.setPolicyRules(policyRulesList);
				}
			}else if(MConstant.REF_LEVEL.APPLICATION.equals(verLevel)){
				ArrayList<PolicyRulesDataM> policyRulesList = policyRulesDAO.loadOrigPolicyRulesM(verificationResultM.getVerResultId(),conn);
				if(!Util.empty(policyRulesList)) {
					verificationResultM.setPolicyRules(policyRulesList);
				}
			}
		}
		return verificationResultM;
	}
	public VerificationResultDataM selectTableXRULES_VERIFICATION_RESULT(
			String refId, String verLevel) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableXRULES_VERIFICATION_RESULT(refId,verLevel,conn);
		}catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}
	public VerificationResultDataM selectTableXRULES_VERIFICATION_RESULT(
			String refId, String verLevel,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer("");
			sql.append("SELECT VER_RESULT_ID, REF_ID, VER_LEVEL, SUMMARY_INCOME_RESULT, SUMMARY_INCOME_RESULT_CODE, ");
			sql.append(" VER_CUS_RESULT, VER_CUS_RESULT_CODE, VER_HR_RESULT, VER_HR_RESULT_CODE, ");
			sql.append(" DOC_COMPLETED_FLAG, REQUIRED_VER_CUST_FLAG, REQUIRED_VER_HR_FLAG, ");
			sql.append(" REQUIRED_VER_INCOME_FLAG, REQUIRED_VER_PRIVILEGE_FLAG, REQUIRED_VER_WEB_FLAG, ");
			sql.append(" VER_PRIVILEGE_RESULT, VER_PRIVILEGE_RESULT_CODE, VER_WEB_RESULT, VER_WEB_RESULT_CODE, ");
			sql.append(" BUS_REGIST_VER_RESULT, VER_CUS_COMPLETE, RISK_GRADE_COARSE_CODE, RISK_GRADE_FINE_CODE, ");
			sql.append(" COMPARE_SIGNATURE_RESULT, REQUIRED_PRIORITY_PASS, DOC_FOLLOW_UP_STATUS, ");
			sql.append(" CREATE_DATE, CREATE_BY, UPDATE_DATE, UPDATE_BY, A_SCORE_CODE ");
			sql.append(" FROM XRULES_VERIFICATION_RESULT WHERE REF_ID = ? AND VER_LEVEL = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, refId);
			ps.setString(2, verLevel);

			rs = ps.executeQuery();

			if(rs.next()) {
				VerificationResultDataM verResultM = new VerificationResultDataM();
				verResultM.setVerResultId(rs.getString("VER_RESULT_ID"));
				verResultM.setRefId(rs.getString("REF_ID"));
				verResultM.setVerLevel(rs.getString("VER_LEVEL"));
				verResultM.setSummaryIncomeResult(rs.getString("SUMMARY_INCOME_RESULT"));
				verResultM.setSummaryIncomeResultCode(rs.getString("SUMMARY_INCOME_RESULT_CODE"));
				
				verResultM.setVerCusResult(rs.getString("VER_CUS_RESULT"));
				verResultM.setVerCusResultCode(rs.getString("VER_CUS_RESULT_CODE"));
				verResultM.setVerHrResult(rs.getString("VER_HR_RESULT"));
				verResultM.setVerHrResultCode(rs.getString("VER_HR_RESULT_CODE"));
				
				verResultM.setDocCompletedFlag(rs.getString("DOC_COMPLETED_FLAG"));
				verResultM.setRequiredVerCustFlag(rs.getString("REQUIRED_VER_CUST_FLAG"));
				verResultM.setRequiredVerHrFlag(rs.getString("REQUIRED_VER_HR_FLAG"));
				
				verResultM.setRequiredVerIncomeFlag(rs.getString("REQUIRED_VER_INCOME_FLAG"));
				verResultM.setRequiredVerPrivilegeFlag(rs.getString("REQUIRED_VER_PRIVILEGE_FLAG"));
				verResultM.setRequiredVerWebFlag(rs.getString("REQUIRED_VER_WEB_FLAG"));
				
				verResultM.setVerPrivilegeResult(rs.getString("VER_PRIVILEGE_RESULT"));
				verResultM.setVerPrivilegeResultCode(rs.getString("VER_PRIVILEGE_RESULT_CODE"));
				verResultM.setVerWebResult(rs.getString("VER_WEB_RESULT"));
				verResultM.setVerWebResultCode(rs.getString("VER_WEB_RESULT_CODE"));
				
				verResultM.setBusRegistVerResult(rs.getString("BUS_REGIST_VER_RESULT"));
				verResultM.setVerCusComplete(rs.getString("VER_CUS_COMPLETE"));
				verResultM.setRiskGradeCoarseCode(rs.getString("RISK_GRADE_COARSE_CODE"));
				verResultM.setRiskGradeFineCode(rs.getString("RISK_GRADE_FINE_CODE"));
				
				verResultM.setCompareSignatureResult(rs.getString("COMPARE_SIGNATURE_RESULT"));
				verResultM.setRequirePriorityPass(rs.getString("REQUIRED_PRIORITY_PASS"));
				verResultM.setDocFollowUpStatus(rs.getString("DOC_FOLLOW_UP_STATUS"));
				
				verResultM.setCreateBy(rs.getString("CREATE_BY"));
				verResultM.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				verResultM.setUpdateBy(rs.getString("UPDATE_BY"));
				verResultM.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				
				verResultM.setAScoreCode(rs.getString("A_SCORE_CODE"));
						
				return verResultM;
			}

			return null;
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
				closeConnection( rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void saveUpdateOrigVerificationResultM(
			VerificationResultDataM verificationResultM, Connection conn)
			throws ApplicationException {
		try { 
			int returnRows = 0;
			verificationResultM.setUpdateBy(userId);
			returnRows = updateTableXRULES_VERIFICATION_RESULT(verificationResultM,conn);
			if (returnRows == 0) {
				verificationResultM.setCreateBy(userId);
			    verificationResultM.setUpdateBy(userId);
				createOrigVerificationResultM(verificationResultM,conn);
			} else {
				OrigHRVerificationDAO hrVerificationDAO = ORIGDAOFactory.getHRVerificationDAO(userId);
				OrigIdentifyQuestionDAO identifyQuestionDAO = ORIGDAOFactory.getIdentifyQuestionDAO(userId);
				OrigIdentifyQuestionSetDAO identifyQuestionSetDAO = ORIGDAOFactory.getIdentifyQuestionSetDAO(userId);
				OrigPhoneVerificationDAO phoneVerificationDAO = ORIGDAOFactory.getPhoneVerificationDAO(userId);
				OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO(userId);
				OrigWebVerificationDAO webVerificationDAO = ORIGDAOFactory.getWebVerificationDAO(userId);
				OrigPrivilegeProjectCodeDAO prvlgProjectCodeDAO = ORIGDAOFactory.getPrivilegeProjectCodeDAO(userId);
				OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO(userId);
				OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO(userId);
				
				if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verificationResultM.getVerLevel())){

					ArrayList<HRVerificationDataM> hrVerificationList = verificationResultM.getHrVerifications();
					if(!Util.empty(hrVerificationList)) {
						for(HRVerificationDataM hrVerificationM: hrVerificationList){
							hrVerificationM.setVerResultId(verificationResultM.getVerResultId());
							hrVerificationDAO.saveUpdateOrigHRVerificationM(hrVerificationM,conn);
						}
					}
					
					ArrayList<IdentifyQuestionDataM> identifyQuestionList = verificationResultM.getIndentifyQuestions();
					if(!Util.empty(identifyQuestionList)) {
						for(IdentifyQuestionDataM identifyQueM: identifyQuestionList){
							identifyQueM.setVerResultId(verificationResultM.getVerResultId());
							identifyQuestionDAO.saveUpdateOrigIdentifyQuestionM(identifyQueM,conn);
						}
					}
					
					ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList = verificationResultM.getIndentifyQuesitionSets();
					if(!Util.empty(identifyQuestionSetList)) {
						for(IdentifyQuestionSetDataM identifyQueSetM: identifyQuestionSetList){
							identifyQueSetM.setVerResultId(verificationResultM.getVerResultId());
							identifyQuestionSetDAO.saveUpdateOrigIdentifyQuestionSetM(identifyQueSetM,conn);
						}
					}
					
					ArrayList<PhoneVerificationDataM> phoneVerificationList = verificationResultM.getPhoneVerifications();
					if(!Util.empty(phoneVerificationList)) {
						for(PhoneVerificationDataM phoneVerifM: phoneVerificationList){
							phoneVerifM.setVerResultId(verificationResultM.getVerResultId());
							phoneVerificationDAO.saveUpdateOrigPhoneVerificationM(phoneVerifM,conn);
						}
					}
					
					ArrayList<WebVerificationDataM> webVerificationList = verificationResultM.getWebVerifications();
					if(!Util.empty(webVerificationList)) {
						for(WebVerificationDataM webVerifM: webVerificationList){
							webVerifM.setVerResultId(verificationResultM.getVerResultId());
							webVerificationDAO.saveUpdateOrigWebVerificationM(webVerifM,conn);
						}
					}
					
					ArrayList<PrivilegeProjectCodeDataM> projCodeList = verificationResultM.getPrivilegeProjectCodes();
					if(!Util.empty(projCodeList)) {
						for(PrivilegeProjectCodeDataM prvlgProjCodeM: projCodeList){
							prvlgProjCodeM.setVerResultId(verificationResultM.getVerResultId());
							prvlgProjectCodeDAO.saveUpdateOrigPrivilegeProjectCodeM(prvlgProjCodeM);
						}
					}
					
					ArrayList<DocumentVerificationDataM> docVerificationList = verificationResultM.getDocumentVerifications();
					if(!Util.empty(docVerificationList)) {
						for(DocumentVerificationDataM documentVerifM: docVerificationList){
							documentVerifM.setVerResultId(verificationResultM.getVerResultId());
							docVerificationDAO.saveUpdateOrigDocumentVerificationM(documentVerifM,conn);
						}
					}
					
					ArrayList<RequiredDocDataM> requiredDocList = verificationResultM.getRequiredDocs();
					if(!Util.empty(requiredDocList)) {
						for(RequiredDocDataM requiredDocM: requiredDocList){
							requiredDocM.setVerResultId(verificationResultM.getVerResultId());
							requiredDocDAO.saveUpdateOrigRequiredDocM(requiredDocM,conn);
						}
					}
					
					ArrayList<PolicyRulesDataM> policyRulesList = verificationResultM.getPolicyRules();
					if(!Util.empty(policyRulesList)) {
//						policyRulesDAO.batchSaveUpdateOrigPolicyRulesM(policyRulesList);
						
						for(PolicyRulesDataM policyRulesM: policyRulesList){
							policyRulesM.setVerResultId(verificationResultM.getVerResultId());
							policyRulesDAO.saveUpdateOrigPolicyRulesM(policyRulesM,conn);
						}
					}
					policyRulesDAO.deleteNotInKeyPolicyRules(policyRulesList, verificationResultM.getVerResultId());
					hrVerificationDAO.deleteNotInKeyHRVerification(hrVerificationList, verificationResultM.getVerResultId(),conn);
					identifyQuestionDAO.deleteNotInKeyIdentifyQuestion(identifyQuestionList, verificationResultM.getVerResultId(),conn);
					identifyQuestionSetDAO.deleteNotInKeyIdentifyQuestionSet(identifyQuestionSetList, verificationResultM.getVerResultId(),conn);
					phoneVerificationDAO.deleteNotInKeyPhoneVerification(phoneVerificationList, verificationResultM.getVerResultId(),conn);
					webVerificationDAO.deleteNotInKeyWebVerification(webVerificationList, verificationResultM.getVerResultId(),conn);
					prvlgProjectCodeDAO.deleteNotInKeyPrivilegeProjectCode(projCodeList, verificationResultM.getVerResultId());
					docVerificationDAO.deleteNotInKeyDocumentVerification(docVerificationList, verificationResultM.getVerResultId(),conn);
					requiredDocDAO.deleteNotInKeyRequiredDoc(requiredDocList, verificationResultM.getVerResultId(),conn);
				}else if(MConstant.REF_LEVEL.APPLICATION.equals(verificationResultM.getVerLevel())){
					ArrayList<PolicyRulesDataM> policyRulesList = verificationResultM.getPolicyRules();
					if(!Util.empty(policyRulesList)) {
//						policyRulesDAO.batchSaveUpdateOrigPolicyRulesM(policyRulesList);
						
						for(PolicyRulesDataM policyRulesM: policyRulesList){
							policyRulesM.setVerResultId(verificationResultM.getVerResultId());
							policyRulesDAO.saveUpdateOrigPolicyRulesM(policyRulesM,conn);
						}
					}
					policyRulesDAO.deleteNotInKeyPolicyRules(policyRulesList, verificationResultM.getVerResultId());
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	@Override
	public void saveUpdateOrigVerificationResultM(VerificationResultDataM verificationResultM) throws ApplicationException {
		try { 
			int returnRows = 0;
			verificationResultM.setUpdateBy(userId);
			returnRows = updateTableXRULES_VERIFICATION_RESULT(verificationResultM);
			if (returnRows == 0) {
				verificationResultM.setCreateBy(userId);
			    verificationResultM.setUpdateBy(userId);
				createOrigVerificationResultM(verificationResultM);
			} else {
				OrigHRVerificationDAO hrVerificationDAO = ORIGDAOFactory.getHRVerificationDAO(userId);
				OrigIdentifyQuestionDAO identifyQuestionDAO = ORIGDAOFactory.getIdentifyQuestionDAO(userId);
				OrigIdentifyQuestionSetDAO identifyQuestionSetDAO = ORIGDAOFactory.getIdentifyQuestionSetDAO(userId);
				OrigPhoneVerificationDAO phoneVerificationDAO = ORIGDAOFactory.getPhoneVerificationDAO(userId);
				OrigPolicyRulesDAO policyRulesDAO = ORIGDAOFactory.getPolicyRulesDAO(userId);
				OrigWebVerificationDAO webVerificationDAO = ORIGDAOFactory.getWebVerificationDAO(userId);
				OrigPrivilegeProjectCodeDAO prvlgProjectCodeDAO = ORIGDAOFactory.getPrivilegeProjectCodeDAO(userId);
				OrigDocVerificationDAO docVerificationDAO = ORIGDAOFactory.getDocVerificationDAO(userId);
				OrigRequiredDocDAO requiredDocDAO = ORIGDAOFactory.getRequiredDocDAO(userId);
				
				if(MConstant.REF_LEVEL.PERSONAL_INFO.equals(verificationResultM.getVerLevel())){

					ArrayList<HRVerificationDataM> hrVerificationList = verificationResultM.getHrVerifications();
					if(!Util.empty(hrVerificationList)) {
						for(HRVerificationDataM hrVerificationM: hrVerificationList){
							hrVerificationM.setVerResultId(verificationResultM.getVerResultId());
							hrVerificationDAO.saveUpdateOrigHRVerificationM(hrVerificationM);
						}
					}
					
					ArrayList<IdentifyQuestionDataM> identifyQuestionList = verificationResultM.getIndentifyQuestions();
					if(!Util.empty(identifyQuestionList)) {
						for(IdentifyQuestionDataM identifyQueM: identifyQuestionList){
							identifyQueM.setVerResultId(verificationResultM.getVerResultId());
							identifyQuestionDAO.saveUpdateOrigIdentifyQuestionM(identifyQueM);
						}
					}
					
					ArrayList<IdentifyQuestionSetDataM> identifyQuestionSetList = verificationResultM.getIndentifyQuesitionSets();
					if(!Util.empty(identifyQuestionSetList)) {
						for(IdentifyQuestionSetDataM identifyQueSetM: identifyQuestionSetList){
							identifyQueSetM.setVerResultId(verificationResultM.getVerResultId());
							identifyQuestionSetDAO.saveUpdateOrigIdentifyQuestionSetM(identifyQueSetM);
						}
					}
					
					ArrayList<PhoneVerificationDataM> phoneVerificationList = verificationResultM.getPhoneVerifications();
					if(!Util.empty(phoneVerificationList)) {
						for(PhoneVerificationDataM phoneVerifM: phoneVerificationList){
							phoneVerifM.setVerResultId(verificationResultM.getVerResultId());
							phoneVerificationDAO.saveUpdateOrigPhoneVerificationM(phoneVerifM);
						}
					}
					
					ArrayList<WebVerificationDataM> webVerificationList = verificationResultM.getWebVerifications();
					if(!Util.empty(webVerificationList)) {
						for(WebVerificationDataM webVerifM: webVerificationList){
							webVerifM.setVerResultId(verificationResultM.getVerResultId());
							webVerificationDAO.saveUpdateOrigWebVerificationM(webVerifM);
						}
					}
					
					ArrayList<PrivilegeProjectCodeDataM> projCodeList = verificationResultM.getPrivilegeProjectCodes();
					if(!Util.empty(projCodeList)) {
						for(PrivilegeProjectCodeDataM prvlgProjCodeM: projCodeList){
							prvlgProjCodeM.setVerResultId(verificationResultM.getVerResultId());
							prvlgProjectCodeDAO.saveUpdateOrigPrivilegeProjectCodeM(prvlgProjCodeM);
						}
					}
					
					ArrayList<DocumentVerificationDataM> docVerificationList = verificationResultM.getDocumentVerifications();
					if(!Util.empty(docVerificationList)) {
						for(DocumentVerificationDataM documentVerifM: docVerificationList){
							documentVerifM.setVerResultId(verificationResultM.getVerResultId());
							docVerificationDAO.saveUpdateOrigDocumentVerificationM(documentVerifM);
						}
					}
					
					ArrayList<RequiredDocDataM> requiredDocList = verificationResultM.getRequiredDocs();
					if(!Util.empty(requiredDocList)) {
						for(RequiredDocDataM requiredDocM: requiredDocList){
							requiredDocM.setVerResultId(verificationResultM.getVerResultId());
							requiredDocDAO.saveUpdateOrigRequiredDocM(requiredDocM);
						}
					}
					
					hrVerificationDAO.deleteNotInKeyHRVerification(hrVerificationList, verificationResultM.getVerResultId());
					identifyQuestionDAO.deleteNotInKeyIdentifyQuestion(identifyQuestionList, verificationResultM.getVerResultId());
					identifyQuestionSetDAO.deleteNotInKeyIdentifyQuestionSet(identifyQuestionSetList, verificationResultM.getVerResultId());
					phoneVerificationDAO.deleteNotInKeyPhoneVerification(phoneVerificationList, verificationResultM.getVerResultId());
					webVerificationDAO.deleteNotInKeyWebVerification(webVerificationList, verificationResultM.getVerResultId());
					prvlgProjectCodeDAO.deleteNotInKeyPrivilegeProjectCode(projCodeList, verificationResultM.getVerResultId());
					docVerificationDAO.deleteNotInKeyDocumentVerification(docVerificationList, verificationResultM.getVerResultId());
					requiredDocDAO.deleteNotInKeyRequiredDoc(requiredDocList, verificationResultM.getVerResultId());
				}else if(MConstant.REF_LEVEL.APPLICATION.equals(verificationResultM.getVerLevel())){
					ArrayList<PolicyRulesDataM> policyRulesList = verificationResultM.getPolicyRules();
					if(!Util.empty(policyRulesList)) {
//						policyRulesDAO.batchSaveUpdateOrigPolicyRulesM(policyRulesList);
						
						for(PolicyRulesDataM policyRulesM: policyRulesList){
							policyRulesM.setVerResultId(verificationResultM.getVerResultId());
							policyRulesDAO.saveUpdateOrigPolicyRulesM(policyRulesM);
						}
					}
					policyRulesDAO.deleteNotInKeyPolicyRules(policyRulesList, verificationResultM.getVerResultId());
				}
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}
	}
	public int updateTableXRULES_VERIFICATION_RESULT(
			VerificationResultDataM verificationResultM) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return updateTableXRULES_VERIFICATION_RESULT(verificationResultM, conn);
		}catch(Exception e){
			logger.fatal(e);
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	public int updateTableXRULES_VERIFICATION_RESULT(
			VerificationResultDataM verificationResultM,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuffer sql = new StringBuffer("");
			sql.append("UPDATE XRULES_VERIFICATION_RESULT ");
			sql.append(" SET SUMMARY_INCOME_RESULT = ?, SUMMARY_INCOME_RESULT_CODE = ?, VER_CUS_RESULT = ?, ");
			sql.append(" VER_CUS_RESULT_CODE = ?, VER_HR_RESULT = ?, VER_HR_RESULT_CODE = ?, ");
			sql.append(" DOC_COMPLETED_FLAG = ?, REQUIRED_VER_CUST_FLAG = ?, REQUIRED_VER_HR_FLAG = ?, ");
			sql.append(" REQUIRED_VER_INCOME_FLAG = ?, REQUIRED_VER_PRIVILEGE_FLAG = ?, REQUIRED_VER_WEB_FLAG = ?, ");
			sql.append(" VER_PRIVILEGE_RESULT = ?, VER_PRIVILEGE_RESULT_CODE = ?, VER_WEB_RESULT = ?, ");
			sql.append(" VER_WEB_RESULT_CODE = ?, BUS_REGIST_VER_RESULT = ?, VER_CUS_COMPLETE = ?, ");
			sql.append(" RISK_GRADE_COARSE_CODE = ?, RISK_GRADE_FINE_CODE = ?, COMPARE_SIGNATURE_RESULT = ?, ");
			sql.append(" REQUIRED_PRIORITY_PASS = ?, DOC_FOLLOW_UP_STATUS=?, UPDATE_DATE = ?, UPDATE_BY = ?, A_SCORE_CODE = ? ");
			sql.append(" WHERE REF_ID = ? AND VER_LEVEL = ? AND VER_RESULT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, verificationResultM.getSummaryIncomeResult());
			ps.setString(cnt++, verificationResultM.getSummaryIncomeResultCode());
			
			ps.setString(cnt++, verificationResultM.getVerCusResult());
			ps.setString(cnt++, verificationResultM.getVerCusResultCode());
			ps.setString(cnt++, verificationResultM.getVerHrResult());
			ps.setString(cnt++, verificationResultM.getVerHrResultCode());

			ps.setString(cnt++, verificationResultM.getDocCompletedFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerCustFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerHrFlag());
			
			ps.setString(cnt++, verificationResultM.getRequiredVerIncomeFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerPrivilegeFlag());
			ps.setString(cnt++, verificationResultM.getRequiredVerWebFlag());
			
			ps.setString(cnt++, verificationResultM.getVerPrivilegeResult());
			ps.setString(cnt++, verificationResultM.getVerPrivilegeResultCode());
			ps.setString(cnt++, verificationResultM.getVerWebResult());
			
			ps.setString(cnt++, verificationResultM.getVerWebResultCode());
			ps.setString(cnt++, verificationResultM.getBusRegistVerResult());
			ps.setString(cnt++, verificationResultM.getVerCusComplete());
			
			ps.setString(cnt++, verificationResultM.getRiskGradeCoarseCode());
			ps.setString(cnt++, verificationResultM.getRiskGradeFineCode());
			ps.setString(cnt++, verificationResultM.getCompareSignatureResult());
			
			ps.setString(cnt++, verificationResultM.getRequirePriorityPass());
			ps.setString(cnt++, verificationResultM.getDocFollowUpStatus());
			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, verificationResultM.getUpdateBy());
			
			ps.setString(cnt++, verificationResultM.getAScoreCode());
			
			// WHERE CLAUSE
			ps.setString(cnt++, verificationResultM.getRefId());
			ps.setString(cnt++, verificationResultM.getVerLevel());
			ps.setString(cnt++, verificationResultM.getVerResultId());
			
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		} finally {
			try {
//				closeConnection(conn, ps);
				closeConnection(ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public void deleteNotInKeyVerificationResult(
			ArrayList<VerificationResultDataM> verificationResultList,
			String refId) throws ApplicationException {
		// TODO Auto-generated method stub

	}

}
