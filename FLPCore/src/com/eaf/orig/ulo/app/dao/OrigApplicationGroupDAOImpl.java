package com.eaf.orig.ulo.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.xrules.dao.OrigVerificationResultDAO;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationIncreaseDataM;
import com.eaf.orig.ulo.model.app.ApplicationLogDataM;
import com.eaf.orig.ulo.model.app.AttachmentDataM;
import com.eaf.orig.ulo.model.app.AuditTrailDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCommentDataM;
import com.eaf.orig.ulo.model.app.DocumentRelationDataM;
import com.eaf.orig.ulo.model.app.HistoryDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.NotePadDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.ReasonDataM;
import com.eaf.orig.ulo.model.app.ReasonLogDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class OrigApplicationGroupDAOImpl extends OrigObjectDAO implements OrigApplicationGroupDAO{
	private static transient Logger logger = Logger.getLogger(OrigApplicationGroupDAOImpl.class);
	public OrigApplicationGroupDAOImpl(){
		
	}
	public OrigApplicationGroupDAOImpl(String userId){
		this.userId = userId;
	}
	public OrigApplicationGroupDAOImpl(String userId,String transactionId){
		this.userId = userId;
		this.transactionId = transactionId;
	}
	private String userId = "";
	private String transactionId = "";
	@Override
	public void createOrigApplicationGroupM(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		Connection conn = null;
		try{
			conn  = getConnection();
			applicationGroup.setLifeCycle(applicationGroup.getMaxLifeCycle());
			logger.debug("applicationGroup.getApplicationGroupId() :"+ applicationGroup.getApplicationGroupId());
		    if(Util.empty(applicationGroup.getApplicationGroupId())){
				String applicationGroupID = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_GROUP_PK,conn); 
				applicationGroup.setApplicationGroupId(applicationGroupID);
				applicationGroup.setCreateBy(userId);
			}
			//Get Application Group Number
		    logger.debug("applicationGroup.getApplicationGroupNo() :"+applicationGroup.getApplicationGroupNo());
		    if(Util.empty(applicationGroup.getApplicationGroupNo())){
		    	logger.fatal("Application Group number is null");
//				String applicationNo = generatorManager.generateApplicationNo(applicationDataM);
//				applicationDataM.setApplicationNo(applicationNo);
		    }
		    applicationGroup.setUpdateBy(userId);
			createTableORIG_APPLICATION_GROUP(applicationGroup);
						
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
			if(personalInfos != null && !personalInfos.isEmpty()) {
				OrigPersonalInfoDAO personalInfoDAO = ORIGDAOFactory.getPersonalInfoDAO(userId);
				for(PersonalInfoDataM personalInfo: personalInfos) {
					personalInfo.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					personalInfoDAO.createOrigPersonalInfoM(personalInfo);
				}
			}

			ArrayList<PaymentMethodDataM> paymentMethods = applicationGroup.getPaymentMethods();
			if(!Util.empty(paymentMethods)){
				OrigPaymentMethodDAO paymentDAO = ORIGDAOFactory.getPaymentMethodDAO(userId);
				for(PaymentMethodDataM payment: paymentMethods) {
					paymentDAO.createOrigPaymentMethodM(payment,conn);
				}
			}
			
			// Create Addtional Services
			ArrayList<SpecialAdditionalServiceDataM> allServices = applicationGroup.getSpecialAdditionalServices();
			if (!Util.empty(allServices)) {
				OrigAdditionalServiceDAO origAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceDAO(userId);
				for(SpecialAdditionalServiceDataM additionalServiceDataM: allServices) {
					origAdditionalServiceDAO.createOrigAdditionalServiceM(additionalServiceDataM,conn);
				}
			}
			
			ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
			if(applications != null && !applications.isEmpty()) {
				OrigApplicationDAO appDAO = ORIGDAOFactory.getApplicationDAO(userId);
				for(ApplicationDataM application : applications){
					application.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					appDAO.createOrigApplicationM(application);
				}
			}
			
			ArrayList<ApplicationIncreaseDataM> applicationIncreases = applicationGroup.getApplicationIncreases();
			if(applicationIncreases != null && !applicationIncreases.isEmpty()){
				OrigApplicationInceaseDAO appInceaseDAO = ORIGDAOFactory.getApplicationIncreaseDAO(userId);
				for(ApplicationIncreaseDataM applicationIncease:applicationIncreases){
					applicationIncease.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					appInceaseDAO.createOrigApplicationIncreaseM(applicationIncease,conn);
				}
			}
			
			ArrayList<ApplicationImageDataM> images = applicationGroup.getApplicationImages();
			if(images != null && !images.isEmpty()) {
				OrigApplicationImageDAO imageDAO = ORIGDAOFactory.getApplicationImageDAO(userId);
				for(ApplicationImageDataM applicationImage : images){
					applicationImage.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					imageDAO.createOrigApplicationImageM(applicationImage,conn);
				}
			}
			
//			ArrayList<ApplicationPointDataM> pointList = applicationGroup.getApplicationPoints();
//			if(pointList != null && !pointList.isEmpty()) {
//				OrigApplicationPointDAO pointDAO = ORIGDAOFactory.getApplicationPointDAO(userId);
//				for(ApplicationPointDataM appPointM : pointList){
//					appPointM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
//					pointDAO.createOrigApplicationPointM(appPointM);
//				}
//			}
			
			ArrayList<AttachmentDataM> attachments = applicationGroup.getAttachments();
			if(attachments != null && !attachments.isEmpty()) {
				OrigAttachmentHistoryDAO attachDAO = ORIGDAOFactory.getAttachmentHistoryDAO(userId);
				for(AttachmentDataM attachment : attachments){
					attachment.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					attachDAO.createOrigAttachmentHistoryM(attachment);
				}
			}
			
			ArrayList<NotePadDataM> notepads = applicationGroup.getNotePads();
			if(notepads != null && !notepads.isEmpty()) {
				OrigNotepadDAO notepadDAO = ORIGDAOFactory.getNotepadDAO(userId);
				for(NotePadDataM notepad : notepads){
					notepad.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					notepadDAO.createOrigNotePadM(notepad);
				}
			}
			
			ArrayList<DocumentCommentDataM> documentComments = applicationGroup.getDocumentComments();
			if(documentComments != null && !documentComments.isEmpty()) {
				OrigDocumentCommentDAO documentCommentDAO = ORIGDAOFactory.getDocumentCommentDAO(userId);
				for(DocumentCommentDataM documentComment : documentComments){
					documentComment.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					documentCommentDAO.createOrigDocumentCommentM(documentComment);
				}
			}
			
//			ArrayList<ProjectCodeDataM> projectCodeList = applicationGroup.getProjectCodes();
//			if(projectCodeList != null && !projectCodeList.isEmpty()) {
//				OrigProjectCodeDAO projectCodeDAO = ORIGDAOFactory.getProjectCodeDAO(userId);
//				for(ProjectCodeDataM projectCodeM: projectCodeList) {
//					projectCodeM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
//					projectCodeDAO.createOrigProjectCodeM(projectCodeM);
//				}
//			}
			
			ArrayList<ReferencePersonDataM> referencePersons = applicationGroup.getReferencePersons();
			if (referencePersons != null) {
				OrigReferencePersonDAO referencePersonDAO = ORIGDAOFactory.getReferencePersonDAO(userId);
				for(ReferencePersonDataM referencePerson: referencePersons) {
					referencePerson.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					referencePersonDAO.createOrigReferencePersonM(referencePerson);
				}
			}
			
			ArrayList<SaleInfoDataM> saleInfos = applicationGroup.getSaleInfos();
			if(saleInfos != null && !saleInfos.isEmpty()) {
				OrigSaleInfoDAO saleInfoDAO = ORIGDAOFactory.getSaleInfoDAO(userId);
				for(SaleInfoDataM saleInfo: saleInfos) {
					if(!Util.empty(saleInfo.getSalesId())) {
						saleInfo.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						saleInfoDAO.createOrigSaleInfoM(saleInfo);
					}
				}
			}
			
			VerificationResultDataM verification = applicationGroup.getVerificationResult();
			if(!Util.empty(verification)) {
				OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO(userId);
				verification.setRefId(applicationGroup.getApplicationGroupId());
				verification.setVerLevel(MConstant.REF_LEVEL.APPLICATION_GROUP);
				verificationDAO.createOrigVerificationResultM(verification,conn);
			}
			
			ArrayList<DocumentCheckListDataM> docChkList = applicationGroup.getDocumentCheckLists();
			if(docChkList != null && !docChkList.isEmpty()) {
				OrigDocumentCheckListDAO docChkListDAO = ORIGDAOFactory.getDocumentCheckListDAO(userId);
				for(DocumentCheckListDataM docChkM : docChkList){
					docChkM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					docChkListDAO.createOrigDocumentCheckListM(docChkM,conn);
				}
			}
			/*HashMap<String,ArrayList<DocumentRelationDataM>> docRelationMap = applicationGroup.getDocumentRelations();
			OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO(userId);
			if(docRelationMap != null && !docRelationMap.isEmpty()) {
				for(String applicantType : docRelationMap.keySet()){
					ArrayList<DocumentRelationDataM> docRelationList = docRelationMap.get(applicantType);
					for(DocumentRelationDataM docRelationM : docRelationList){
						docRelationDAO.createOrigDocumentRelationM(docRelationM);
					}
				}
			}*/
			
//			#rawi comment change logic to get model ComparisonGroupDataM
//			HashMap<String, CompareDataM> compareSensitiveList = applicationGroup.getCompareSensitiveFields();
//			if(!Util.empty(compareSensitiveList)) {
//				Collection<CompareDataM> compareList = compareSensitiveList.values();
//				OrigComparisionDataDAO compareDAO = ModuleFactory.getOrigComparisionDataDAO();
//				for(CompareDataM compareM : compareList){
//					compareM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
//					compareDAO.saveUpdateOrigComparisionData(compareM);
//				}
//			}
			
//			ArrayList<ComparisonGroupDataM> comparisonGroups = applicationGroup.getComparisonGroups();
//			if(!Util.empty(comparisonGroups)){
//				OrigComparisionDataDAO compareDAO = ModuleFactory.getOrigComparisionDataDAO(userId);
//				for (ComparisonGroupDataM comparisonGroup : comparisonGroups) {
//					HashMap<String, CompareDataM> comparisonFieldHash = comparisonGroup.getComparisonFields();
//					if(!Util.empty(comparisonFieldHash)){
//						Collection<CompareDataM> comparisonFields = comparisonFieldHash.values();
//						for(CompareDataM comparisonField : comparisonFields){
//							if(!CompareDataM.CompareDataType.DUMMY.equals(comparisonField.getCompareDataType())){
//								comparisonField.setApplicationGroupId(applicationGroup.getApplicationGroupId());
//								compareDAO.saveUpdateOrigComparisionData(comparisonField);
//							}
//						}
//					}
//				}
//			}
			
			if(!Util.empty(applications)) {
				for(ApplicationDataM application : applications) {
					List<LoanDataM> loans = application.getLoans();
					if(!Util.empty(loans)) {
						for(LoanDataM loan : loans) {
							ArrayList<String> additionalServiceIds = loan.getSpecialAdditionalServiceIds();
							if (!Util.empty(additionalServiceIds)) {
								OrigAdditionalServiceMapDAO origAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceMapDAO(userId);
								for(String serviceId: additionalServiceIds) {
									origAdditionalServiceDAO.createOrigAdditionalServiceMapM(loan.getLoanId(), serviceId);
								}
							}
						}
					}
				}
			}else if(Util.empty(applications)){
				ArrayList<ReasonDataM> reasonList = applicationGroup.getReasons();
				OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO(userId);
				if(reasonList != null && !reasonList.isEmpty()) {
					for(ReasonDataM reasonM :reasonList){
						reasonM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						reasonDAO.saveUpdateOrigReasonM(reasonM);
					}				
				}
				ArrayList<ReasonLogDataM> reasonLogList = applicationGroup.getReasonLogs();
				OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO(userId);
				if(reasonLogList != null && !reasonLogList.isEmpty()) {
					for(ReasonLogDataM reasonLogM :reasonLogList){
						reasonLogM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						reasonLogDAO.saveUpdateOrigReasonLogM(reasonLogM);
					}				
				}
			}
			
			ArrayList<AuditTrailDataM> auditTrails = applicationGroup.getAuditTrailLogs();
			if(auditTrails != null && !auditTrails.isEmpty()) {
				OrigAuditTrailDAO auditTrailDAO = ORIGDAOFactory.getAuditTrailDAO(userId);
				for(AuditTrailDataM auditTrail : auditTrails){
					auditTrail.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					auditTrailDAO.createOrigAuditTrailM(auditTrail,conn);
				}
			}
			
//			HistoryDataM histData = applicationGroup.getHistoryData();
//			if(!Util.empty(histData)) {
//				histData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
//				OrigHistoryDataDAO histDao = ORIGDAOFactory.getHistoryDataDAO(userId);
//				histDao.createOrigHistoryDataM(histData);
//			}
			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getMessage());
			}
		}
	}

	private void createTableORIG_APPLICATION_GROUP(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_APPLICATION_GROUP ");
			sql.append("( APPLICATION_GROUP_ID, APPLICATION_GROUP_NO, APPLICATION_DATE, ");
			sql.append(" APPLICATION_STATUS, APPLICATION_TEMPLATE, APPLY_CHANNEL, APPLY_DATE, ");
			sql.append(" APPROVER_REMARK, BRANCH_NO, BUNDLE_FLAG, COVERPAGE_TYPE, INSTANT_ID, ");
			sql.append(" JOB_STATE, LAST_DECISION, REF_ID, VETO_REMARK, CA_REMARK, FRAUD_REMARK, ");
			sql.append(" PRIORITY, APPLICATION_TYPE, LAST_DECISION_DATE, BRANCH_NAME, ");
			sql.append(" LEG_TIME1, LEG_TIME2, LEG_TIME3, LEG_TIME4, ");
			sql.append(" AUDIT_LOG_FLAG, IS_VETO_ELIGIBLE, DOC_SET_NO, CLAIM_BY, ");
			sql.append(" BRANCH_REGION, BRANCH_ZONE, RC_CODE, PREV_JOB_STATE, ");
			sql.append(" POLICY_EX_SIGN_OFF_DATE, POLICY_EX_SIGN_OFF_BY, ");
			sql.append(" CREATE_BY, CREATE_DATE, UPDATE_BY, UPDATE_DATE, RANDOM_NO1, ");
			sql.append(" RANDOM_NO2, RANDOM_NO3, FRAUD_APPLICANT_FLAG, FRAUD_COMPANY_FLAG, ");
			sql.append(" COVERPAGE_PRIORITY, CALL_OPERATOR, PRIORITY_MODE,WEB_SCAN_USER, EXECUTE_FLAG, OVER_PERCENT_OR_FLAG ,DECISION_LOG,LIFE_CYCLE, ");
			sql.append(" EMPLOYEE_NAME, EMPLOYEE_NO, TRANSACT_NO, REPROCESS_FLAG, FRAUD_DECISION, FULL_FRAUD_FLAG,SOURCE, SOURCE_ACTION, LH_REF_ID, SOURCE_USER_ID) ");

			sql.append(" VALUES(?,?,?,  ?,?,?,?,  ?,?,?,?,?,  ?,?,?,?,?,?,  ?,?,?,?, ");
			sql.append(" ?,?,?,?,  ?,?,?,?,  ?,?,?,?,  ?,?,   ?,?,?,?,?,  ?,?,?,?,");
			sql.append(" ?,?,?,?,?,? ,?,? ,?,?,?,?,?,?,?,  ?,?,? )");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
		  
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, applicationGroup.getApplicationGroupId());
			ps.setString(cnt++, applicationGroup.getApplicationGroupNo());
			ps.setDate(cnt++, applicationGroup.getApplicationDate());
			
			ps.setString(cnt++, applicationGroup.getApplicationStatus());
			ps.setString(cnt++, applicationGroup.getApplicationTemplate());
			ps.setString(cnt++, applicationGroup.getApplyChannel());
			ps.setDate(cnt++, applicationGroup.getApplyDate());
			
			ps.setString(cnt++, applicationGroup.getApproverRemark());
			ps.setString(cnt++, applicationGroup.getBranchNo());
			ps.setString(cnt++, applicationGroup.getBundleFlag());
			ps.setString(cnt++, applicationGroup.getCoverpageType());			
			Integer instantId = applicationGroup.getInstantId();
			if(null == instantId){
				instantId = 0;
			}
			ps.setInt(cnt++, instantId);
			
			ps.setString(cnt++, applicationGroup.getJobState());
			ps.setString(cnt++, applicationGroup.getLastDecision());
			ps.setString(cnt++, applicationGroup.getRefId());
			ps.setString(cnt++, applicationGroup.getVetoRemark());
			ps.setString(cnt++, applicationGroup.getCaRemark());
			ps.setString(cnt++, applicationGroup.getFraudRemark());
			
			ps.setInt(cnt++, applicationGroup.getPriority());
			ps.setString(cnt++, applicationGroup.getApplicationType());
			ps.setDate(cnt++, applicationGroup.getLastDecisionDate());
			ps.setString(cnt++, applicationGroup.getBranchName());
			
			ps.setBigDecimal(cnt++, applicationGroup.getLegtime1());
			ps.setBigDecimal(cnt++, applicationGroup.getLegtime2());
			ps.setBigDecimal(cnt++, applicationGroup.getLegtime3());
			ps.setBigDecimal(cnt++, applicationGroup.getLegtime4());
			
			ps.setString(cnt++, applicationGroup.getAuditLogFlag());
			ps.setString(cnt++, applicationGroup.getIsVetoEligible());
			ps.setString(cnt++, applicationGroup.getDocSetNo());
			ps.setString(cnt++, applicationGroup.getClaimBy());
			
			ps.setString(cnt++, applicationGroup.getBranchRegion());
			ps.setString(cnt++, applicationGroup.getBranchZone());
			ps.setString(cnt++, applicationGroup.getRcCode());
			ps.setString(cnt++, applicationGroup.getPrevJobState());
			
			ps.setDate(cnt++, applicationGroup.getPolicyExSignOffDate());
			ps.setString(cnt++, applicationGroup.getPolicyExSignOffBy());
			
			ps.setString(cnt++, applicationGroup.getCreateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationGroup.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setInt(cnt++, applicationGroup.getRandomNo1());
			
			ps.setInt(cnt++, applicationGroup.getRandomNo2());
			ps.setInt(cnt++, applicationGroup.getRandomNo3());
			ps.setString(cnt++, applicationGroup.getFraudApplicantFlag());
			ps.setString(cnt++, applicationGroup.getFraudCompanyFlag());
			
			ps.setString(cnt++, applicationGroup.getCoverpagePriority());
			ps.setString(cnt++, applicationGroup.getCallOperator());
			ps.setString(cnt++, applicationGroup.getPriorityMode());
			ps.setString(cnt++, applicationGroup.getWebScanUser());
			ps.setString(cnt++, applicationGroup.getExecuteFlag());
			ps.setString(cnt++, applicationGroup.getOverPercentORFlag());
			ps.setString(cnt++, applicationGroup.getDecisionLog());
			ps.setInt(cnt++, applicationGroup.getLifeCycle());
			
			ps.setString(cnt++,applicationGroup.getEmployeeName());
			ps.setString(cnt++,applicationGroup.getEmployeeNo());
			ps.setString(cnt++,applicationGroup.getTransactNo());
			ps.setString(cnt++,applicationGroup.getReprocessFlag());
			ps.setString(cnt++,applicationGroup.getFraudFinalDecision());
			ps.setString(cnt++,applicationGroup.getFullFraudFlag());
			ps.setString(cnt++,applicationGroup.getSource());
			
			ps.setString(cnt++, applicationGroup.getSourceAction());
			ps.setString(cnt++, applicationGroup.getLhRefId());
			ps.setString(cnt++, applicationGroup.getSourceUserId());
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
	public void deleteOrigApplicationGroupM(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		ArrayList<ApplicationDataM> applicationList = applicationGroup.getApplications();
		OrigApplicationDAO appDAO = ORIGDAOFactory.getApplicationDAO();
		if(!Util.empty(applicationList)) {
			for(ApplicationDataM appM : applicationList) {
				appDAO.deleteOrigApplicationM(appM);
			}
		}
		
		deleteTableORIG_APPLICATION_GROUP(applicationGroup);
		
	}

	private void deleteTableORIG_APPLICATION_GROUP(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("DELETE ORIG_APPLICATION_GROUP ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroup.getApplicationGroupId());
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
	public ApplicationGroupDataM loadApplicationGroupDocument(String applicationGroupId,String personalId)throws ApplicationException {
		Connection conn = null;
		ApplicationGroupDataM applicationGroup = new ApplicationGroupDataM();
		try{
			conn = getConnection();
			applicationGroup = selectTableORIG_APPLICATION_GROUP(applicationGroupId,conn);
			OrigDocumentCheckListDAO documentCheckListDAO = ORIGDAOFactory.getDocumentCheckListDAO();
			ArrayList<DocumentCheckListDataM> documentCheckLists = documentCheckListDAO.loadOrigDocumentCheckListM(applicationGroupId,conn);
			if(!Util.empty(documentCheckLists)) {
				applicationGroup.setDocumentCheckLists(documentCheckLists);
			}
			OrigPersonalInfoDAO personalInfoDAO = ORIGDAOFactory.getPersonalInfoDAO();
			ArrayList<PersonalInfoDataM> personalInfos = personalInfoDAO.loadPersonalInfoDocument(applicationGroupId,personalId,conn);
			if(!Util.empty(personalInfos)) {
				applicationGroup.setPersonalInfos(personalInfos);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e);
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e);
			}
		}
		return applicationGroup;
	}
	@Override
	public ApplicationGroupDataM loadOrigApplicationGroupM(String applicationGroupId) throws ApplicationException {
		TraceController trace = new TraceController("loadApplicationGroup",transactionId);
		Connection conn = null;
		try{
			conn = getConnection();
			trace.create("ApplicationGroup");
			ApplicationGroupDataM result = selectTableORIG_APPLICATION_GROUP(applicationGroupId,conn);
			trace.end("ApplicationGroup");
			if(!Util.empty(result)){
				trace.create("Application");
				OrigApplicationDAO applicationDAO = ORIGDAOFactory.getApplicationDAO();
				ArrayList<ApplicationDataM> applicationList = applicationDAO.loadApplicationsForGroup(applicationGroupId,conn);
				if(!Util.empty(applicationList)) {
					result.setApplications(applicationList);
				}else{
					OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO();
					ArrayList<ReasonDataM> reasonList = reasonDAO.loadOrigReasonMByAppGroupId(applicationGroupId,conn);
					if(reasonList != null && !reasonList.isEmpty()) {
						result.setReasons(reasonList);
					}
				}
				trace.end("Application");
				
				trace.create("ApplicationIncrease");
				OrigApplicationInceaseDAO appIncreases = ORIGDAOFactory.getApplicationInceaseDAO();
				ArrayList<ApplicationIncreaseDataM> applicationIncreaseList = appIncreases.loadOrigApplicationIncreaseM(applicationGroupId,conn);
				if(!Util.empty(applicationIncreaseList)){
					result.setApplicationIncreases(applicationIncreaseList);
				}
				trace.end("ApplicationIncrease");
				
				trace.create("ApplicationImage");
				OrigApplicationImageDAO imageDAO = ORIGDAOFactory.getApplicationImageDAO();
				ArrayList<ApplicationImageDataM> imageList = imageDAO.loadOrigApplicationImageM(applicationGroupId,conn);
				if(!Util.empty(imageList)) {
					result.setApplicationImages(imageList);
				}
				trace.end("ApplicationImage");
				
				trace.create("ApplicationLog");
				OrigApplicationLogDAO appLogDAO = ORIGDAOFactory.getApplicationLogDAO();
				ArrayList<ApplicationLogDataM> logList = appLogDAO.loadOrigApplicationLogM(applicationGroupId,conn);
				if(!Util.empty(logList)) {
					result.setApplicationLogs(logList);
				}
				trace.end("ApplicationLog");
				
//				OrigApplicationPointDAO pointDAO = ORIGDAOFactory.getApplicationPointDAO();
//				ArrayList<ApplicationPointDataM> pointList = pointDAO.loadOrigApplicationPointM(applicationGroupId);
//				if(!Util.empty(pointList)) {
//					result.setApplicationPoints(pointList);
//				}
				
				trace.create("AttachmentHistory");
				OrigAttachmentHistoryDAO attachDAO = ORIGDAOFactory.getAttachmentHistoryDAO();
				ArrayList<AttachmentDataM> attachList = attachDAO.loadOrigAttachmentHistoryM(applicationGroupId,conn);
				if(!Util.empty(attachList)) {
					result.setAttachments(attachList);
				}
				trace.end("AttachmentHistory");
				
				trace.create("AuditTrail");
				OrigAuditTrailDAO auditDAO = ORIGDAOFactory.getAuditTrailDAO();
				ArrayList<AuditTrailDataM> auditList = auditDAO.loadOrigAuditTrailM(applicationGroupId,conn);
				if(!Util.empty(auditList)) {
					result.setAuditTrails(auditList);
				}
				trace.end("AuditTrail");
				
//				OrigContactLogDAO contactLogDAO = ORIGDAOFactory.getContactLogDAO();
//				ArrayList<ContactLogDataM> contactLogList = contactLogDAO.loadOrigContactLogM(applicationGroupId);
//				if(!Util.empty(contactLogList)) {
//					result.setContactLogs(contactLogList);
//				}
				
//				OrigEnquiryLogDAO enquiryLogDAO = ORIGDAOFactory.getEnquiryLogDAO();
//				ArrayList<EnquiryLogDataM> enquiryLogList = enquiryLogDAO.loadOrigEnquiryLogM(applicationGroupId);
//				if(!Util.empty(enquiryLogList)) {
//					result.setEnquiryLogs(enquiryLogList);
//				}
				
				trace.create("NotePad");
				OrigNotepadDAO notepadDAO = ORIGDAOFactory.getNotepadDAO();
				ArrayList<NotePadDataM> notepadList = notepadDAO.loadOrigNotePadM(applicationGroupId,conn);
				if(!Util.empty(notepadList)) {
					result.setNotePads(notepadList);
				}
				trace.end("NotePad");
				
				trace.create("DocumentComment");
				OrigDocumentCommentDAO documentCommentDAO = ORIGDAOFactory.getDocumentCommentDAO();
				ArrayList<DocumentCommentDataM> documentComments = documentCommentDAO.loadOrigDocumentCommentM(applicationGroupId,conn);
				if(!Util.empty(documentComments)) {
					result.setDocumentComments(documentComments);
				}
				trace.end("DocumentComment");
				
				trace.create("PersonalInfo");
				OrigPersonalInfoDAO personalInfoDAO = ORIGDAOFactory.getPersonalInfoDAO();
				ArrayList<PersonalInfoDataM> personList = personalInfoDAO.loadOrigPersonalInfoM(applicationGroupId,conn);
				if(!Util.empty(personList)) {
					result.setPersonalInfos(personList);
				}
				trace.end("PersonalInfo");
				
				ArrayList<String> personalIds = result.getPersonalIds();
				
				trace.create("PaymentMethod");
				OrigPaymentMethodDAO paymentDAO = ORIGDAOFactory.getPaymentMethodDAO();
				result.setPaymentMethods(paymentDAO.loadOrigPaymentMethod(personalIds,conn));
				trace.end("PaymentMethod");
				
				trace.create("AdditionalService");
				OrigAdditionalServiceDAO specialAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceDAO();
				ArrayList<SpecialAdditionalServiceDataM> services = specialAdditionalServiceDAO.loadOrigAdditionalService(personalIds,conn);
				result.setSpecialAdditionalServices(services);
				trace.end("AdditionalService");
				
				trace.create("ReferencePerson");
				OrigReferencePersonDAO referencePersonDAO = ORIGDAOFactory.getReferencePersonDAO();
				ArrayList<ReferencePersonDataM> referencePersonList = referencePersonDAO.loadOrigReferencePersonM(applicationGroupId,conn);
				if (referencePersonList != null) {
					result.setReferencePersons(referencePersonList);
				}
				trace.end("ReferencePerson");
				
				trace.create("SaleInfo");
				OrigSaleInfoDAO saleInfoDAO = ORIGDAOFactory.getSaleInfoDAO();
				ArrayList<SaleInfoDataM> saleInfoList = saleInfoDAO.loadOrigSaleInfoM(applicationGroupId,conn);
				if(!Util.empty(saleInfoList)) {
					result.setSaleInfos(saleInfoList);
				}
				trace.end("SaleInfo");
				
//				OrigSMSLogDAO smsLogDAO = ORIGDAOFactory.getSMSLogDAO();
//				ArrayList<SMSLogDataM> smsLogList = smsLogDAO.loadOrigSMSLogM(applicationGroupId);
//				if(!Util.empty(smsLogList)) {
//					result.setSmsLogs(smsLogList);
//				}
				
				trace.create("DocumentCheckList");
				OrigDocumentCheckListDAO docChkListDAO = ORIGDAOFactory.getDocumentCheckListDAO();
				ArrayList<DocumentCheckListDataM> docChkList = docChkListDAO.loadOrigDocumentCheckListM(applicationGroupId,conn);
				if(!Util.empty(docChkList)) {
					result.setDocumentCheckLists(docChkList);
				}
				ArrayList<DocumentCheckListDataM> docChkListManual = docChkListDAO.loadOrigDocumentCheckListManualM(applicationGroupId,conn);
				if(!Util.empty(docChkListManual)) {
					result.setDocumentCheckListManuals(docChkListManual);
				}
				ArrayList<DocumentCheckListDataM> docChkListManualAdditional = docChkListDAO.loadOrigDocumentCheckListManualAdditionalM(applicationGroupId,conn);
				if(!Util.empty(docChkListManualAdditional)) {
					result.setDocumentCheckListManualAdditionals(docChkListManualAdditional);
				}
				trace.end("DocumentCheckList");
				
				trace.create("VerificationResult");
				OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO();
				VerificationResultDataM verificationM = verificationDAO.loadOrigVerificationResultM(result.getApplicationGroupId(), MConstant.REF_LEVEL.APPLICATION_GROUP,conn);
				if(!Util.empty(verificationM)) {
					result.setVerificationResult(verificationM);
				}
				trace.end("VerificationResult");
			}
			trace.trace();
			return result;
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e);
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
				throw new ApplicationException(e);
			}
		}
	}
	
	public ApplicationGroupDataM loadSingleOrigApplicationGroupM(String applicationGroupId) throws ApplicationException {
		return selectTableORIG_APPLICATION_GROUP(applicationGroupId);
	}
	private ApplicationGroupDataM selectTableORIG_APPLICATION_GROUP(String applicationGroupId) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			return selectTableORIG_APPLICATION_GROUP(applicationGroupId,conn);
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	private ApplicationGroupDataM selectTableORIG_APPLICATION_GROUP(String applicationGroupId,Connection conn) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ApplicationGroupDataM applicationGroup = null;
		try {
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_ID, APPLICATION_GROUP_NO, APPLICATION_DATE, APPLICATION_STATUS ");
			sql.append(" ,APPLICATION_TEMPLATE, APPLY_CHANNEL, APPLY_DATE, APPROVER_REMARK, BRANCH_NO");
			sql.append(" ,BUNDLE_FLAG, COVERPAGE_TYPE, INSTANT_ID, JOB_STATE, LAST_DECISION, REF_ID,VETO_REMARK");
			sql.append(" ,CA_REMARK, FRAUD_REMARK, PRIORITY, APPLICATION_TYPE, LAST_DECISION_DATE");
			sql.append(" ,BRANCH_NAME, LEG_TIME1, LEG_TIME2, LEG_TIME3, LEG_TIME4 ");
			sql.append(" ,AUDIT_LOG_FLAG, IS_VETO_ELIGIBLE, DOC_SET_NO, CLAIM_BY ");
			sql.append(" ,BRANCH_REGION, BRANCH_ZONE, RC_CODE, PREV_JOB_STATE ");
			sql.append(" ,POLICY_EX_SIGN_OFF_DATE, POLICY_EX_SIGN_OFF_BY ");
			sql.append(" ,CREATE_BY,CREATE_DATE,UPDATE_BY,UPDATE_DATE, RANDOM_NO1, ");
			sql.append(" RANDOM_NO2, RANDOM_NO3, FRAUD_APPLICANT_FLAG, FRAUD_COMPANY_FLAG, ");
			sql.append(" CALL_OPERATOR, COVERPAGE_PRIORITY,PRIORITY_MODE,WEB_SCAN_USER, EXECUTE_FLAG, OVER_PERCENT_OR_FLAG, DECISION_LOG ,WF_STATE");
			sql.append(" ,LIFE_CYCLE ,EMPLOYEE_NAME ,EMPLOYEE_NO ,TRANSACT_NO, REPROCESS_FLAG, FRAUD_DECISION, FULL_FRAUD_FLAG,SOURCE, SOURCE_ACTION, LH_REF_ID, SOURCE_USER_ID ");
			sql.append(" FROM ORIG_APPLICATION_GROUP  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();

			if(rs.next()){
				applicationGroup = new ApplicationGroupDataM();
				applicationGroup.setApplicationGroupId(rs.getString("APPLICATION_GROUP_ID"));
				applicationGroup.setApplicationGroupNo(rs.getString("APPLICATION_GROUP_NO"));
				applicationGroup.setApplicationDate(rs.getDate("APPLICATION_DATE"));
				applicationGroup.setApplicationStatus(rs.getString("APPLICATION_STATUS"));
				
				applicationGroup.setApplicationTemplate(rs.getString("APPLICATION_TEMPLATE"));
				applicationGroup.setApplyChannel(rs.getString("APPLY_CHANNEL"));
				applicationGroup.setApplyDate(rs.getDate("APPLY_DATE"));
				applicationGroup.setApproverRemark(rs.getString("APPROVER_REMARK"));
				applicationGroup.setBranchNo(rs.getString("BRANCH_NO"));
				
				applicationGroup.setBundleFlag(rs.getString("BUNDLE_FLAG"));
				applicationGroup.setCoverpageType(rs.getString("COVERPAGE_TYPE"));
				applicationGroup.setInstantId(rs.getInt("INSTANT_ID"));
				applicationGroup.setJobState(rs.getString("JOB_STATE"));
				applicationGroup.setLastDecision(rs.getString("LAST_DECISION"));
				applicationGroup.setRefId(rs.getString("REF_ID"));
				applicationGroup.setVetoRemark(rs.getString("VETO_REMARK"));
				
				applicationGroup.setCaRemark(rs.getString("CA_REMARK"));
				applicationGroup.setFraudRemark(rs.getString("FRAUD_REMARK"));
				applicationGroup.setPriority(rs.getInt("PRIORITY"));
				applicationGroup.setApplicationType(rs.getString("APPLICATION_TYPE"));
				applicationGroup.setLastDecisionDate(rs.getDate("LAST_DECISION_DATE"));
				applicationGroup.setBranchName(rs.getString("BRANCH_NAME"));
				
				applicationGroup.setLegtime1(rs.getBigDecimal("LEG_TIME1"));
				applicationGroup.setLegtime2(rs.getBigDecimal("LEG_TIME2"));
				applicationGroup.setLegtime3(rs.getBigDecimal("LEG_TIME3"));
				applicationGroup.setLegtime4(rs.getBigDecimal("LEG_TIME4"));
				
				applicationGroup.setAuditLogFlag(rs.getString("AUDIT_LOG_FLAG"));
				applicationGroup.setIsVetoEligible(rs.getString("IS_VETO_ELIGIBLE"));
				applicationGroup.setDocSetNo(rs.getString("DOC_SET_NO"));
				applicationGroup.setClaimBy(rs.getString("CLAIM_BY"));
				
				applicationGroup.setBranchRegion(rs.getString("BRANCH_REGION"));
				applicationGroup.setBranchZone(rs.getString("BRANCH_ZONE"));
				applicationGroup.setRcCode(rs.getString("RC_CODE"));
				applicationGroup.setPrevJobState(rs.getString("PREV_JOB_STATE"));
				
				applicationGroup.setPolicyExSignOffDate(rs.getDate("POLICY_EX_SIGN_OFF_DATE"));
				applicationGroup.setPolicyExSignOffBy(rs.getString("POLICY_EX_SIGN_OFF_BY"));
				
				applicationGroup.setCreateBy(rs.getString("CREATE_BY"));
				applicationGroup.setCreateDate(rs.getTimestamp("CREATE_DATE"));
				applicationGroup.setUpdateBy(rs.getString("UPDATE_BY"));
				applicationGroup.setUpdateDate(rs.getTimestamp("UPDATE_DATE"));
				applicationGroup.setRandomNo1(rs.getInt("RANDOM_NO1"));
				
				applicationGroup.setRandomNo2(rs.getInt("RANDOM_NO2"));
				applicationGroup.setRandomNo3(rs.getInt("RANDOM_NO3"));
				applicationGroup.setFraudApplicantFlag(rs.getString("FRAUD_APPLICANT_FLAG"));
				applicationGroup.setFraudCompanyFlag(rs.getString("FRAUD_COMPANY_FLAG"));
				
				applicationGroup.setCallOperator(rs.getString("CALL_OPERATOR"));
				applicationGroup.setCoverpagePriority(rs.getString("COVERPAGE_PRIORITY"));
				applicationGroup.setPriorityMode(rs.getString("PRIORITY_MODE"));
				
				applicationGroup.setWebScanUser(rs.getString("WEB_SCAN_USER"));
				applicationGroup.setExecuteFlag(rs.getString("EXECUTE_FLAG"));
				applicationGroup.setOverPercentORFlag(rs.getString("OVER_PERCENT_OR_FLAG"));
				applicationGroup.setDecisionLog(rs.getString("DECISION_LOG"));
				applicationGroup.setWfState(rs.getString("WF_STATE"));
				
				applicationGroup.setLifeCycle(rs.getInt("LIFE_CYCLE"));
				applicationGroup.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
				applicationGroup.setEmployeeNo(rs.getString("EMPLOYEE_NO"));
				applicationGroup.setTransactNo(rs.getString("TRANSACT_NO"));
				applicationGroup.setReprocessFlag(rs.getString("REPROCESS_FLAG"));
				applicationGroup.setFraudFinalDecision(rs.getString("FRAUD_DECISION"));
				applicationGroup.setFullFraudFlag(rs.getString("FULL_FRAUD_FLAG"));
				applicationGroup.setSource(rs.getString("SOURCE"));
				
				applicationGroup.setSourceAction(rs.getString("SOURCE_ACTION"));
				applicationGroup.setLhRefId(rs.getString("LH_REF_ID"));
				applicationGroup.setSourceUserId(rs.getString("SOURCE_USER_ID"));
			}
			return applicationGroup;
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(rs,ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void saveUpdateOrigApplicationGroupM(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		Connection conn = null;
		try{
			conn = getConnection();
			int returnRows = 0;
			TraceController trace = new TraceController("saveApplicationGroup",transactionId);
			applicationGroup.setUpdateBy(userId);
			applicationGroup.setLifeCycle(applicationGroup.getMaxLifeCycle());
			trace.create("ApplicationGroup");
			returnRows = updateTableORIG_APPLICATION_GROUP(applicationGroup,conn);
			trace.end("ApplicationGroup");
			if(returnRows == 0){
				applicationGroup.setCreateBy(userId);
				createOrigApplicationGroupM(applicationGroup);
			}else{
				trace.create("PersonalInfo");			
				ArrayList<PersonalInfoDataM> personalInfoList = applicationGroup.getPersonalInfos();
				OrigPersonalInfoDAO personalInfoDAO = ORIGDAOFactory.getPersonalInfoDAO(userId,transactionId);
				if(personalInfoList != null && !personalInfoList.isEmpty()) {
					for(PersonalInfoDataM personalInfoM: personalInfoList) {
						personalInfoM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						personalInfoDAO.saveUpdateOrigPersonalInfoM(personalInfoM,conn);
					}
				}
				trace.end("PersonalInfo");
				trace.create("PaymentMethod");
				ArrayList<PaymentMethodDataM> paymentList = applicationGroup.getPaymentMethods();
				if(!Util.empty(paymentList)) {
					OrigPaymentMethodDAO paymentDAO = ORIGDAOFactory.getPaymentMethodDAO(userId);
					for(PaymentMethodDataM paymentM: paymentList) {
						paymentDAO.saveUpdateOrigPaymentMethodM(paymentM,conn);
					}
				}
				trace.end("PaymentMethod");
				trace.create("SpecialAdditional");
				ArrayList<SpecialAdditionalServiceDataM> additionalServiceList = applicationGroup.getSpecialAdditionalServices();
				OrigAdditionalServiceDAO origAddiServiceDAO = ORIGDAOFactory.getAdditionalServiceDAO(userId);
				//TODO : Check logic
				origAddiServiceDAO.preDeleteSpecialAdditionalServices(applicationGroup);
				
				if (!Util.empty(additionalServiceList)) {				
					for(SpecialAdditionalServiceDataM additionalServiceDataM: additionalServiceList) {
						origAddiServiceDAO.saveUpdateOrigAdditionalServiceM(additionalServiceDataM,conn);
					}				
				}
				trace.end("SpecialAdditional");
				trace.create("Application");
				ArrayList<ApplicationDataM> appList = applicationGroup.getApplications();
				OrigApplicationDAO appDAO = ORIGDAOFactory.getApplicationDAO(userId);
				if(appList != null && !appList.isEmpty()) {
					for(ApplicationDataM applicationM : appList){
						applicationM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						appDAO.saveUpdateOrigApplicationM(applicationM);
					}
				}
				appDAO.deleteNotInKeyApplication(appList, applicationGroup.getApplicationGroupId());
				trace.end("Application");
				
				trace.create("ApplicationIncrease");
				ArrayList<ApplicationIncreaseDataM> appIncreases = applicationGroup.getApplicationIncreases();
				OrigApplicationInceaseDAO appIncreaseDAO = ORIGDAOFactory.getApplicationIncreaseDAO(userId);
				if(appIncreases != null && !appIncreases.isEmpty()){
					for(ApplicationIncreaseDataM appIncrease:appIncreases){
						appIncrease.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						appIncreaseDAO.saveOrigApplicationIncreaseM(appIncrease,conn);
					}
				}
				appIncreaseDAO.deleteNotInKeyApplicationIncrease(appIncreases,applicationGroup.getApplicationGroupId(),conn);
				trace.end("ApplicationIncrease");
				
				trace.create("ApplicationImage");
				ArrayList<ApplicationImageDataM> imageList = applicationGroup.getApplicationImages();
				OrigApplicationImageDAO imageDAO = ORIGDAOFactory.getApplicationImageDAO(userId,transactionId);
				if(imageList != null && !imageList.isEmpty()) {
					for(ApplicationImageDataM appImageM : imageList){
						appImageM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						imageDAO.saveUpdateOrigApplicationImageM(appImageM,conn);
					}
				}
				imageDAO.deleteNotInKeyApplicationImage(imageList, applicationGroup.getApplicationGroupId(),conn);
				trace.end("ApplicationImage");
				
				trace.create("Attachment");
				ArrayList<AttachmentDataM> attachList = applicationGroup.getAttachments();
				OrigAttachmentHistoryDAO attachDAO = ORIGDAOFactory.getAttachmentHistoryDAO(userId);
				if(attachList != null && !attachList.isEmpty()) {
					for(AttachmentDataM attachM : attachList){
						attachM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						attachDAO.saveUpdateOrigAttachmentHistoryM(attachM);
					}
				}
				attachDAO.deleteNotInKeyAttachmentHistory(attachList, applicationGroup.getApplicationGroupId(),conn);
				trace.end("Attachment");
				
				trace.create("NotePad");
				ArrayList<NotePadDataM> notepadList = applicationGroup.getNotePads();
				OrigNotepadDAO notepadDAO = ORIGDAOFactory.getNotepadDAO(userId);
				if(notepadList != null && !notepadList.isEmpty()) {
					for(NotePadDataM notepadM : notepadList){
						notepadM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						notepadDAO.saveUpdateOrigNotePadM(notepadM);
					}
				}
				notepadDAO.deleteNotInKeyNotePad(notepadList, applicationGroup.getApplicationGroupId(),conn);
				trace.end("NotePad");
				
				trace.create("DocumentComment");
				ArrayList<DocumentCommentDataM> documentComments = applicationGroup.getDocumentComments();
				OrigDocumentCommentDAO documentCommentDAO = ORIGDAOFactory.getDocumentCommentDAO(userId);
				if(documentComments != null && !documentComments.isEmpty()) {
					for(DocumentCommentDataM documentComment : documentComments){
						documentComment.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						documentCommentDAO.saveUpdateOrigDocumentCommentM(documentComment);
					}
				}
				trace.end("DocumentComment");
				
				trace.create("DeletePersonal");
				personalInfoDAO.deleteNotInKeyPersonalInfo(personalInfoList, applicationGroup.getApplicationGroupId());
				trace.end("DeletePersonal");
				
				trace.create("ReferencePerson");
				ArrayList<ReferencePersonDataM> referencePersonList = applicationGroup.getReferencePersons();
				OrigReferencePersonDAO referencePersonDAO = ORIGDAOFactory.getReferencePersonDAO(userId);
				if (referencePersonList != null) {
					for(ReferencePersonDataM referencePersonM: referencePersonList) {
						referencePersonM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						referencePersonDAO.saveUpdateOrigReferencePersonM(referencePersonM);
					}
				}
				referencePersonDAO.deleteNotInKeyReferencePerson(referencePersonList, applicationGroup.getApplicationGroupId());
				trace.end("ReferencePerson");
				
				trace.create("SaleInfo");
				ArrayList<SaleInfoDataM> saleInfoList = applicationGroup.getSaleInfos();
				OrigSaleInfoDAO saleInfoDAO = ORIGDAOFactory.getSaleInfoDAO(userId);
				if(saleInfoList != null && !saleInfoList.isEmpty()) {
					for(SaleInfoDataM saleInfoM: saleInfoList) {
						saleInfoM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						saleInfoDAO.saveUpdateOrigSaleInfoM(saleInfoM);
					}
				}
				saleInfoDAO.deleteNotInKeySaleInfo(saleInfoList, applicationGroup.getApplicationGroupId());
				trace.end("SaleInfo");
				
				trace.create("DocumentCheckList");
				ArrayList<DocumentCheckListDataM> docChkList = applicationGroup.getDocumentCheckLists();
				ArrayList<DocumentCheckListDataM> docChkListManual = applicationGroup.getDocumentCheckListManuals();
				ArrayList<DocumentCheckListDataM> docChkListManualAdditional = applicationGroup.getDocumentCheckListManualAdditionals();
				OrigDocumentCheckListDAO docChkListDAO = ORIGDAOFactory.getDocumentCheckListDAO(userId);
				OrigDocumentRelationDAO docRelationDAO = ORIGDAOFactory.getDocumentRelationDAO(userId);
				if(docChkList != null && !docChkList.isEmpty()) {
					ArrayList<DocumentCheckListDataM> docManualList = new ArrayList<>();
					if(null != docChkListManual  && !docChkListManual.isEmpty()) {
						docManualList.addAll(docChkListManual);
					}
					if(null != docChkListManualAdditional && !docChkListManualAdditional.isEmpty()) {
						docManualList.addAll(docChkListManualAdditional);
					}
					ArrayList<String> imgSplits = applicationGroup.getImageSplitsDocType();
					logger.debug("!!IMGSPLITS!! :: " + imgSplits);
					if(null == imgSplits) {
						imgSplits = new ArrayList<>();
					}
					for(DocumentCheckListDataM doc : docManualList) {
						logger.debug("!!DOCCODE!! :: " + doc.getDocumentCode());
						if(!imgSplits.contains(doc.getDocumentCode())) {
							docChkList.add(doc);
						}
					}
					boolean isPersonalEmpty = Util.empty(applicationGroup.getPersonalInfos());
					for(DocumentCheckListDataM docChkM : docChkList){
						docChkM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						docChkListDAO.saveUpdateOrigDocumentCheckListM(docChkM,conn);
						
						ArrayList<DocumentRelationDataM> documentRelations = docChkM.getDocumentRelations();
						if(!Util.empty(documentRelations)){
							for(DocumentRelationDataM docRelationM : documentRelations){
								docRelationM.setDocCheckListId(docChkM.getDocCheckListId());
								docRelationDAO.saveUpdateOrigDocumentRelationM(docRelationM,conn);
							}
						}
						if(isPersonalEmpty){
							docRelationDAO.deleteOrigDocumentRelation(docChkM.getDocCheckListId(),conn);
						}
					}
					
				}
				 
				docChkListDAO.deleteNotInKeyDocumentCheckList(docChkList, applicationGroup.getApplicationGroupId(),conn);
				trace.end("DocumentCheckList");
				trace.create("VerificationResult");
				VerificationResultDataM verificationM = applicationGroup.getVerificationResult();
				if(!Util.empty(verificationM)) {
					OrigVerificationResultDAO verificationDAO = ORIGDAOFactory.getVerificationResultDAO(userId);
					verificationM.setRefId(applicationGroup.getApplicationGroupId());
					verificationM.setVerLevel(MConstant.REF_LEVEL.APPLICATION_GROUP);
					verificationDAO.saveUpdateOrigVerificationResultM(verificationM,conn);
				}
				trace.end("VerificationResult");
				
				trace.create("ComparisonGroup");
				int lifeCycle = applicationGroup.getMaxLifeCycle();
				logger.debug("lifeCycle : "+lifeCycle);
				
				ArrayList<ComparisonGroupDataM> comparisonGroups = applicationGroup.getComparisonGroups();
				if(!Util.empty(comparisonGroups)){
					OrigComparisionDataDAO compareDAO = ModuleFactory.getOrigComparisionDataDAO(userId);
					ComparisonGroupDataM comparisonGroup = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.TWO_MAKER);
					compareDAO.deleteOrigComparisionData(applicationGroup.getApplicationGroupId(),comparisonGroup.getRoleId(),lifeCycle,conn);
					compareDAO.updateCompareFlag(CompareDataM.SoruceOfData.PREV_ROLE, applicationGroup.getComparisonField(CompareDataM.SoruceOfData.PREV_ROLE),conn);
					compareDAO.saveUpdateOrigComparisionData(comparisonGroups,applicationGroup.getApplicationGroupId(),lifeCycle,conn);
				}
				trace.end("ComparisonGroup");
				
				trace.create("AdditionalServiceMap");
				OrigAdditionalServiceMapDAO origAdditionalServiceDAO = ORIGDAOFactory.getAdditionalServiceMapDAO(userId);
				if(!Util.empty(appList)) {
					for(ApplicationDataM appM : appList) {
						List<LoanDataM> loanList = appM.getLoans();
						if(!Util.empty(loanList)) {
							for(LoanDataM loanM : loanList) {
								ArrayList<String> additionalServiceIdList = loanM.getSpecialAdditionalServiceIds();
								if (!Util.empty(additionalServiceIdList)) {
									for(String serviceId: additionalServiceIdList) {
										origAdditionalServiceDAO.saveUpdateOrigAdditionalServiceM(loanM.getLoanId(),serviceId,conn);
									}
								}
								origAdditionalServiceDAO.deleteNotInKeyAdditionalService(additionalServiceIdList,loanM.getLoanId(),conn);
							}
						}
					}
				}
				trace.end("AdditionalServiceMap");
				OrigPaymentMethodDAO paymentDAO = ORIGDAOFactory.getPaymentMethodDAO(userId);
				paymentDAO.deleteInactivePaymentMethod(applicationGroup);
				trace.create("AuditTrailLog");
				ArrayList<AuditTrailDataM> auditTrailList = applicationGroup.getAuditTrailLogs();
				if(auditTrailList != null && !auditTrailList.isEmpty()) {
					OrigAuditTrailDAO auditTrailDAO = ORIGDAOFactory.getAuditTrailDAO(userId);
					ArrayList<AuditTrailDataM> lastAuditTrailList =  auditTrailDAO.loadAllOrigAuditTrailM(applicationGroup.getApplicationGroupId(), conn);
					AuditTrailDataM.sortAuditTrialData(lastAuditTrailList);
					for(AuditTrailDataM auditTrail : auditTrailList){
						AuditTrailDataM firstAuditTrail = AuditTrailDataM.getCreateFirstAuditTrail(auditTrail.getFieldName(), lastAuditTrailList);
						if(!Util.empty(firstAuditTrail)){
							auditTrail.setCreateRole(firstAuditTrail.getCreateRole());
							auditTrail.setCreateBy(firstAuditTrail.getCreateBy());
						}else{
							auditTrail.setFirstValueFlag(SystemConstant.getConstant("FLAG_YES"));
						}
						auditTrail.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						auditTrailDAO.createOrigAuditTrailM(auditTrail,conn);
					}
				}
				trace.end("AuditTrailLog");
				HistoryDataM histData = applicationGroup.getHistoryData();
				if(!Util.empty(histData)) {
					histData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
					OrigHistoryDataDAO histDao = ORIGDAOFactory.getHistoryDataDAO(userId);
					histDao.saveUpdateOrigHistoryDataM(histData,conn);
				}
				
				ArrayList<ReasonDataM> reasonList = applicationGroup.getReasons();
				OrigReasonDAO reasonDAO = ORIGDAOFactory.getReasonDAO(userId);
				if(reasonList != null && !reasonList.isEmpty()) {
					for(ReasonDataM reasonM :reasonList){
						reasonM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						reasonDAO.saveUpdateOrigReasonM(reasonM);
					}				
				}
				
				ArrayList<ReasonLogDataM> reasonLogList = applicationGroup.getReasonLogs();
				OrigReasonLogDAO reasonLogDAO = ORIGDAOFactory.getReasonLogDAO(userId);
				if(reasonLogList != null && !reasonLogList.isEmpty()) {
					for(ReasonLogDataM reasonLogM :reasonLogList){
						reasonLogM.setApplicationGroupId(applicationGroup.getApplicationGroupId());
						reasonLogDAO.saveUpdateOrigReasonLogM(reasonLogM);
					}				
				}
				
			}	
			trace.trace();
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new ApplicationException(e);
		}finally{
			try{
				closeConnection(conn);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				throw new ApplicationException(e);
			}
		}
	}

	private int updateTableORIG_APPLICATION_GROUP(ApplicationGroupDataM applicationGroup,Connection conn) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
//		Connection conn = null;
		try {
			//conn = Get Connection
//			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION_GROUP ");
			sql.append(" SET APPLICATION_DATE = ?, APPLICATION_TEMPLATE = ? ");
			sql.append(" ,APPLY_CHANNEL = ?, APPLY_DATE = ?, APPROVER_REMARK = ?, BRANCH_NO = ?");
			sql.append(" ,BUNDLE_FLAG = ?, COVERPAGE_TYPE = ?");
			sql.append(" ,LAST_DECISION = ?,REF_ID = ?,VETO_REMARK = ?,CA_REMARK = ?");
			if(SystemConstant.getConstant("ROLE_FRAUD").equals(applicationGroup.getRoleId()))
			sql.append(" ,FRAUD_REMARK = ? ");
			sql.append(" , APPLICATION_TYPE = ?, LAST_DECISION_DATE = ?, BRANCH_NAME = ?");
			sql.append(" ,AUDIT_LOG_FLAG = ?, IS_VETO_ELIGIBLE = ?, DOC_SET_NO = ?, CLAIM_BY = ? ");
			sql.append(" ,BRANCH_REGION = ?, BRANCH_ZONE = ?, RC_CODE = ?");
			sql.append(" ,POLICY_EX_SIGN_OFF_DATE = ?, POLICY_EX_SIGN_OFF_BY = ? ");
			sql.append(" ,UPDATE_BY = ?, UPDATE_DATE = ?, RANDOM_NO1=? ");
			sql.append(" ,RANDOM_NO2=?, RANDOM_NO3=?, FRAUD_APPLICANT_FLAG =?, FRAUD_COMPANY_FLAG = ? ");
			sql.append(" ,COVERPAGE_PRIORITY = ?, CALL_OPERATOR = ?, PRIORITY_MODE = ? ,WEB_SCAN_USER = ?, EXECUTE_FLAG=?, OVER_PERCENT_OR_FLAG=?, DECISION_LOG=? ");
			sql.append(" ,LIFE_CYCLE = ? ,EMPLOYEE_NAME = ?, EMPLOYEE_NO = ?, TRANSACT_NO = ?, REPROCESS_FLAG = ?, FRAUD_DECISION = ?, FULL_FRAUD_FLAG = ? ,SOURCE = ? ");
			sql.append(" ,SOURCE_ACTION = ?, LH_REF_ID = ?, SOURCE_USER_ID = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			
			int cnt = 1;
			ps.setDate(cnt++, applicationGroup.getApplicationDate());		
			ps.setString(cnt++, applicationGroup.getApplicationTemplate());
			
			ps.setString(cnt++, applicationGroup.getApplyChannel());
			ps.setDate(cnt++, applicationGroup.getApplyDate());
			ps.setString(cnt++, applicationGroup.getApproverRemark());
			ps.setString(cnt++, applicationGroup.getBranchNo());
			
			ps.setString(cnt++, applicationGroup.getBundleFlag());
			ps.setString(cnt++, applicationGroup.getCoverpageType());
			
			ps.setString(cnt++, applicationGroup.getLastDecision());
			ps.setString(cnt++, applicationGroup.getRefId());
			ps.setString(cnt++, applicationGroup.getVetoRemark());
			ps.setString(cnt++, applicationGroup.getCaRemark());
			if(SystemConstant.getConstant("ROLE_FRAUD").equals(applicationGroup.getRoleId()))
			ps.setString(cnt++, applicationGroup.getFraudRemark());
			
			ps.setString(cnt++, applicationGroup.getApplicationType());
			ps.setDate(cnt++, applicationGroup.getLastDecisionDate());
			ps.setString(cnt++, applicationGroup.getBranchName());
					
			ps.setString(cnt++, applicationGroup.getAuditLogFlag());
			ps.setString(cnt++, applicationGroup.getIsVetoEligible());
			ps.setString(cnt++, applicationGroup.getDocSetNo());
			ps.setString(cnt++, applicationGroup.getClaimBy());
			
			ps.setString(cnt++, applicationGroup.getBranchRegion());
			ps.setString(cnt++, applicationGroup.getBranchZone());
			ps.setString(cnt++, applicationGroup.getRcCode());
			
			ps.setDate(cnt++, applicationGroup.getPolicyExSignOffDate());
			ps.setString(cnt++, applicationGroup.getPolicyExSignOffBy());
			
			ps.setString(cnt++, applicationGroup.getUpdateBy());
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setInt(cnt++,applicationGroup.getRandomNo1());
			
			ps.setInt(cnt++,applicationGroup.getRandomNo2());
			ps.setInt(cnt++,applicationGroup.getRandomNo3());
			ps.setString(cnt++, applicationGroup.getFraudApplicantFlag());
			ps.setString(cnt++, applicationGroup.getFraudCompanyFlag());
			
			ps.setString(cnt++, applicationGroup.getCoverpagePriority());
			ps.setString(cnt++, applicationGroup.getCallOperator());
			ps.setString(cnt++, applicationGroup.getPriorityMode());
			ps.setString(cnt++, applicationGroup.getWebScanUser());
			ps.setString(cnt++, applicationGroup.getExecuteFlag());
			
			ps.setString(cnt++, applicationGroup.getOverPercentORFlag());
			ps.setString(cnt++, applicationGroup.getDecisionLog());
			
			ps.setInt(cnt++, applicationGroup.getLifeCycle());
			
			ps.setString(cnt++, applicationGroup.getEmployeeName());
			ps.setString(cnt++, applicationGroup.getEmployeeNo());
			ps.setString(cnt++, applicationGroup.getTransactNo());
			ps.setString(cnt++, applicationGroup.getReprocessFlag());
			ps.setString(cnt++, applicationGroup.getFraudFinalDecision());
			ps.setString(cnt++, applicationGroup.getFullFraudFlag());
			ps.setString(cnt++, applicationGroup.getSource());
			
			ps.setString(cnt++, applicationGroup.getSourceAction());
			ps.setString(cnt++, applicationGroup.getLhRefId());
			ps.setString(cnt++, applicationGroup.getSourceUserId());
			
			//WHERE CLAUSE
			ps.setString(cnt++, applicationGroup.getApplicationGroupId());
						
			returnRows = ps.executeUpdate();
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
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
	public int updateStatus(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION_GROUP ");
			sql.append(" SET  APPLICATION_STATUS = ?, JOB_STATE = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);						 
			ps.setString(1, applicationGroup.getApplicationStatus());
			ps.setString(2, applicationGroup.getJobState());
			ps.setString(3, applicationGroup.getApplicationGroupId());
			returnRows = ps.executeUpdate();			
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
		return returnRows;
	}
	
	
	@Override
	public void createAppGroupToStartFlow(ApplicationGroupDataM appGroup) throws ApplicationException{
		if(appGroup == null){
			return;
		}
		int result = createMinimalAppGroup(appGroup);
		if(result == 0){
			throw new ApplicationException("Fail to insert new ApplicationGroup : "+appGroup.toString());
		}		
	}
	
	private int createMinimalAppGroup(ApplicationGroupDataM appGroup) throws ApplicationException {
		if(appGroup == null){
			return 0;
		}
		String applicationNo = appGroup.getApplicationGroupNo();
		String appGroupId = appGroup.getApplicationGroupId();
		String username = appGroup.getCreateBy();
		if(applicationNo == null || applicationNo.isEmpty() || appGroupId == null || appGroupId.isEmpty()){
			return 0;
		}
		PreparedStatement ps = null;
		Connection conn = null;
		int rows = 0;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("INSERT INTO ORIG_APPLICATION_GROUP ");
			sql.append("( APPLICATION_GROUP_ID, APPLICATION_GROUP_NO, CREATE_DATE, CREATE_BY, UPDATE_DATE,");
			sql.append(" UPDATE_BY, INSTANT_ID) ");

			
			sql.append(" VALUES(?,?, SYSDATE ,?, SYSDATE, ?, ?)");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			int cnt = 1;
			ps.setString(cnt++, appGroupId);
			ps.setString(cnt++, applicationNo);
			ps.setString(cnt++, username);
			ps.setString(cnt++, username);			
			ps.setInt(cnt++, appGroup.getInstantId());			
			rows = ps.executeUpdate();
			
			
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
		return rows;
	}

	public String getClaimBy(String applicationGroupId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		String claimedBy = null;
		
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT CLAIM_BY FROM ORIG_APPLICATION_GROUP  WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				claimedBy = rs.getString("CLAIM_BY");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return claimedBy;
	}
	
	public int updateClaimBy(String applicationGroupId, String userName) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION_GROUP ");
			sql.append(" SET  CLAIM_BY = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);						 
			ps.setString(1, userName);
			ps.setString(2, applicationGroupId);
			returnRows = ps.executeUpdate();			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch (Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return returnRows;
	}

	@Override
	public String getWfState(String applicationGroupId)	throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		String wfState = null;		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT WF_STATE FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);
			rs = ps.executeQuery();			
			if(rs.next()){
				wfState = rs.getString("WF_STATE");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return wfState;
	}
	
	@Override
	public int setWfState(String applicationGroupId, String wfState)	throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;	
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION_GROUP SET WF_STATE = ? WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, wfState);
			ps.setString(2, applicationGroupId);
			return ps.executeUpdate();			
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return 0;
	}
	
	@Override
	public ApplicationGroupDataM loadApplicationGroupByInstanceId(String instanceId) throws ApplicationException {
		String applicationGoupId = getApplicationGroupIdByInstanceId(instanceId);
		logger.debug("instance :" + instanceId);
		logger.debug("applicationGoupId :" + applicationGoupId);
		ApplicationGroupDataM applicationGroupM = loadOrigApplicationGroupM(applicationGoupId);
		return applicationGroupM;
	}
	
	private String getApplicationGroupIdByInstanceId(String instanceId) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;

		String applicationGroupId = null;
		
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP WHERE INSTANT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, instanceId);

			rs = ps.executeQuery();
			
			if (rs.next()) {
				applicationGroupId = rs.getString("APPLICATION_GROUP_ID");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return applicationGroupId;
	}
	
	@Override
	public String getApplicationGroupIdByApplicationGroupNo(String applicationGroupNo) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		
		String applicationGroupId = null;
		
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupNo" + applicationGroupNo);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupNo);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				applicationGroupId = rs.getString("APPLICATION_GROUP_ID");
			}
		} catch (Exception e) {
			logger.fatal(e.getLocalizedMessage());
		} finally {
			try {
				closeConnection(conn, rs, ps);
			} catch (Exception e) {
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return applicationGroupId;
	}

	@Override
	public ApplicationGroupDataM loadSingleApplicationGroupByApplicationGroupNo(String applicationGroupNo) throws ApplicationException {
		return loadApplicationGroupByApplicationGroupNo(applicationGroupNo);
	}
	
	private ApplicationGroupDataM loadApplicationGroupByApplicationGroupNo(String applicationGroupNo) throws ApplicationException {
		String applicationGroupId = getApplicationGroupIdByApplicationGroupNo(applicationGroupNo);
		logger.debug("applicationGroupId : "+applicationGroupId);
		return selectTableORIG_APPLICATION_GROUP(applicationGroupId);
	}

	@Override
	public String getApplicationGroupIdByQr2(String qr2)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String applicationGroupId = null;		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("qr2 :" + qr2);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, qr2);			
			rs = ps.executeQuery();			
			if (rs.next()) {
				applicationGroupId = rs.getString("APPLICATION_GROUP_ID");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		return applicationGroupId;
	}

	@Override
	public int updatePriorityMode(ApplicationGroupDataM applicationGroup) throws ApplicationException {
		int returnRows = 0;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			//conn = Get Connection
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("UPDATE ORIG_APPLICATION_GROUP ");
			sql.append(" SET PRIORITY_MODE = ? ");
			sql.append(" WHERE APPLICATION_GROUP_ID = ?");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			ps.setString(1, applicationGroup.getPriorityMode());
			ps.setString(2, applicationGroup.getApplicationGroupId());
			returnRows = ps.executeUpdate();			
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
		return returnRows;
	}

	@Override
	public String getApplicationGroupNo(String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String applicationGroupNo = null;		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_NO FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId" + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);			
			rs = ps.executeQuery();			
			if(rs.next()){
				applicationGroupNo = rs.getString("APPLICATION_GROUP_NO");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		logger.debug("applicationGroupNo >> "+applicationGroupNo);
		return applicationGroupNo;
	}
	@Override
	public int getMaxLifeCycle(String applicationGroupId)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		int maxLifeCycle = 1;		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT NVL(MAX(LIFE_CYCLE),1) MAX_LIFE_CYCLE FROM ORIG_APPLICATION WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId" + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);			
			rs = ps.executeQuery();			
			if(rs.next()){
				maxLifeCycle = rs.getInt("MAX_LIFE_CYCLE");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		logger.debug("maxLifeCycle : "+maxLifeCycle);
		return maxLifeCycle;
	}
	@Override
	public int getLifeCycle(String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		int lifeCycle = 1;		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT NVL(MAX(LIFE_CYCLE),1) LIFE_CYCLE FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId" + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);			
			rs = ps.executeQuery();			
			if(rs.next()){
				lifeCycle = rs.getInt("LIFE_CYCLE");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		logger.debug("lifeCycle : "+lifeCycle);
		return lifeCycle;
	}
	public String getInstantId(String applicationGroupId)throws ApplicationException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String instantId = "";		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT INSTANT_ID FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			logger.debug("applicationGroupId" + applicationGroupId);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, applicationGroupId);			
			rs = ps.executeQuery();			
			if(rs.next()){
				instantId = rs.getString("INSTANT_ID");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
		logger.debug("instantId : "+instantId);
		return instantId;
	}
	
	public void updateLastDecision(String applicationGroupId, String lastDecision) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			String sql = "UPDATE ORIG_APPLICATION_GROUP SET LAST_DECISION = ?, LAST_DECISION_DATE=? " +
					", UPDATE_BY = ?, UPDATE_DATE = ? WHERE APPLICATION_GROUP_ID = ? ";
			logger.debug("sql >> "+sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql);			
			int cnt = 1;
			ps.setString(cnt++, lastDecision);	
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, userId);			
			ps.setTimestamp(cnt++, ApplicationDate.getTimestamp());
			ps.setString(cnt++, applicationGroupId);			
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public void updateFraudRemark(String applicationGroupId,String fraudRemark, String userId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			String sql = "UPDATE ORIG_APPLICATION_GROUP SET FRAUD_REMARK = ?, UPDATE_BY = ?, UPDATE_DATE = ? WHERE APPLICATION_GROUP_ID = ? ";
			logger.debug("sql >> "+sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql);			
			int index = 1;
			ps.setString(index++, fraudRemark);
			ps.setString(index++, userId);			
			ps.setTimestamp(index++, ApplicationDate.getTimestamp());
			ps.setString(index++, applicationGroupId);			
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public String getApplicationGroupIdByInstantId(String instantId)throws ApplicationException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;		
		String applicationGroupId = "";		
		try{
			conn = getConnection();
			StringBuilder sql = new StringBuilder("");
			sql.append("SELECT APPLICATION_GROUP_ID FROM ORIG_APPLICATION_GROUP WHERE INSTANT_ID = ? ");
			String dSql = String.valueOf(sql);
			logger.debug("Sql=" + dSql);
			ps = conn.prepareStatement(dSql);
			rs = null;
			ps.setString(1, instantId);			
			rs = ps.executeQuery();			
			if(rs.next()){
				applicationGroupId = rs.getString("APPLICATION_GROUP_ID");
			}
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getLocalizedMessage());
		}finally{
			try{
				closeConnection(conn, rs, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
				throw new ApplicationException(e.getLocalizedMessage());
			}
		}
		logger.debug("applicationGroupId : "+applicationGroupId);
		return applicationGroupId;
	}
	@Override
	public void clearInstantId(String applicationGroupId) throws ApplicationException {
		PreparedStatement ps = null;
		Connection conn = null;
		try{
			String sql = "UPDATE ORIG_APPLICATION_GROUP SET INSTANT_ID = 0 WHERE APPLICATION_GROUP_ID = ? ";
			logger.debug("sql >> "+sql);
			conn = getConnection();
			ps = conn.prepareStatement(sql);			
			int index = 1;
			ps.setString(index++, applicationGroupId);			
			ps.executeUpdate();
		}catch(Exception e){
			logger.fatal(e.getLocalizedMessage());
			throw new ApplicationException(e.getMessage());
		}finally{
			try{
				closeConnection(conn, ps);
			}catch(Exception e){
				logger.fatal(e.getLocalizedMessage());
			}
		}
	}
	@Override
	public ApplicationGroupDataM loadApplicationGroup(String applicationGroupId)throws ApplicationException {
		return selectTableORIG_APPLICATION_GROUP(applicationGroupId);
	}
	@Override
	public ApplicationGroupDataM loadApplicationVlink(String applicationGroupId, Connection conn) throws ApplicationException {
		ApplicationGroupDataM applicationGroup = selectTableORIG_APPLICATION_GROUP(applicationGroupId,conn);
		if(!Util.empty(applicationGroup)){
			OrigApplicationDAO applicationDAO = ORIGDAOFactory.getApplicationDAO();
			ArrayList<ApplicationDataM> applications = applicationDAO.loadApplicationsVlink(applicationGroupId,conn);
			if(!Util.empty(applications)){
				applicationGroup.setApplications(applications);
			}
			OrigPersonalInfoDAO personalInfoDAO = ORIGDAOFactory.getPersonalInfoDAO();
			ArrayList<PersonalInfoDataM> personals = personalInfoDAO.loadOrigPersonalInfoVlink(applicationGroupId,conn);
			if(!Util.empty(personals)){
				applicationGroup.setPersonalInfos(personals);
			}
			OrigSaleInfoDAO saleInfoDAO = ORIGDAOFactory.getSaleInfoDAO();
			ArrayList<SaleInfoDataM> saleInfos = saleInfoDAO.loadOrigSaleInfoM(applicationGroupId,conn);
			if(!Util.empty(saleInfos)) {
				applicationGroup.setSaleInfos(saleInfos);
			}
		}
		return applicationGroup;
	}
}
