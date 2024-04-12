package com.eaf.orig.ulo.search.view.webaction;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.ulo.app.dao.CallCenterDAO;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigFollowDocHistoryDAO;
import com.eaf.orig.ulo.app.view.util.kpl.CallCenterKPLDAOImpl;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.FollowDocHistoryDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.google.gson.Gson;

public class SearchCallCenterWebAction extends WebActionHelper implements WebAction {
	private static transient Logger logger = Logger.getLogger(SearchCallCenterWebAction.class);
	String WF_STATE_RUNNING = SystemConstant.getConstant("WF_STATE_RUNNING");
	String APPLICATION_STATIC_REJECTED = SystemConstant.getConstant("APPLICATION_STATIC_REJECTED");
	String APPLICATION_STATIC_CANCELLED = SystemConstant.getConstant("APPLICATION_STATIC_CANCELLED");
	String APPLICATION_STATIC_APPROVED = SystemConstant.getConstant("APPLICATION_STATIC_APPROVED");
	String APPLICATION_STATIC_INPROCESS = SystemConstant.getConstant("APPLICATION_STATIC_INPROCESS");
	String JOB_STATE_AFTER_CARDLINK = SystemConfig.getGeneralParam("JOB_STATE_AFTER_CARDLINK");
	String JOB_STATE_BEFORE_CARDLINK = SystemConfig.getGeneralParam("JOB_STATE_BEFORE_CARDLINK");
	String JOB_STATE_DE2_APPROVE = SystemConfig.getGeneralParam("JOB_STATE_DE2_APPROVE");
	String JOB_STATE_CANCELLED = SystemConfig.getGeneralParam("JOB_STATE_CANCELLED");
	String JOB_STATE_REJECTED = SystemConfig.getGeneralParam("JOB_STATE_REJECTED");
	String JOB_STATE_DE2_REJECT = SystemConfig.getGeneralParam("JOB_STATE_DE2_REJECT");
	String CACH_NAME_DOCUMENT_LIST = SystemConstant.getConstant("CACH_NAME_DOCUMENT_LIST");
	String CJD_SOURCE = SystemConfig.getGeneralParam("CJD_SOURCE");
	
	private String nextAction = null;	
	@Override
	public Event toEvent() {
		return null;
	}
	@Override
	public boolean requiredModelRequest() {
		return false;
	}
	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}
	public boolean preModelRequest() {
		SearchFormHandler searchForm = new SearchFormHandler(getRequest()) {
			public void postSearchResult(ArrayList<HashMap<String, Object>> List) {
				logger.debug("do postSearchResult()..");
				CallCenterDAO dao = ORIGDAOFactory.getCallCenterDAO();
				try {
					for (int i = 0; i < List.size(); i++) {
						try{
							String applicationGroupId = (String) List.get(i).get("APPLICATION_GROUP_ID");
							String jobState = (String) List.get(i).get("JOB_STATE");
							logger.debug("jobState : "+jobState);
							logger.debug("applicationGroupId : " + applicationGroupId);
							if (!Util.empty(applicationGroupId)) {
								int lifeCycle = ORIGDAOFactory.getApplicationGroupDAO().getMaxLifeCycle(applicationGroupId);
								ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadApplicationGroup(applicationGroupId);
								ArrayList<PersonalInfoDataM> personalInfos = ORIGDAOFactory.getPersonalInfoDAO().loadOrigPersonalInfoM(applicationGroupId);
								List.get(i).put("MAX_LIFE_CYCLE", lifeCycle);
								
								if(!KPLUtil.isKPL(applicationGroup))
								{
									List.get(i).put("isKPL", "false");
									List.get(i).put("subData", dao.SearchSubSQL(applicationGroupId,lifeCycle,jobState));
								}
								else
								{
									//Get addtional fields for KPL PRODUCT
									CallCenterKPLDAOImpl kplCCDAO = new CallCenterKPLDAOImpl();
									List.get(i).put("isKPL", "true");
									List.get(i).put("subData", kplCCDAO.SearchSubSQL(applicationGroupId, lifeCycle, jobState));
								}
								
								List.get(i).put("salesData", dao.SearchSalesSQL(applicationGroupId));
								//#Chatmongkol Commment For change Logic search docchecklist
//								List.get(i).put("docData", dao.SearchDocRequestSQL(applicationGroupId));
								List.get(i).put("docData",getDocumentFollow(applicationGroup,personalInfos));
							}
						}catch(Exception e){
							logger.fatal("ERROR",e);
						}
					}
				} catch (Exception e) {
					logger.fatal("ERROR ",e);
				}
			}
		};

		logger.info(" CallCenterWebAction.... ");
		searchForm.setNextPage(false);
		searchForm.setAtPage(1);
		searchForm.setSQL(setParameter(searchForm));
		try{
			SearchQueryEngine SearchEngine = new SearchQueryEngine();
			SearchEngine.search(searchForm);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		getRequest().getSession().setAttribute(SearchFormHandler.SearchForm, searchForm);
		return true;
	}
	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}	
	private String setParameter(SearchFormHandler searchForm) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT " +
				" DISTINCT AG.* " +
				" FROM ORIG_APPLICATION_GROUP AG " +
				" LEFT JOIN ORIG_APPLICATION APP ON AG.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID " +
				" LEFT JOIN ORIG_PERSONAL_INFO PI ON AG.APPLICATION_GROUP_ID = PI.APPLICATION_GROUP_ID " +
				" LEFT JOIN ORIG_SALE_INFO S ON AG.APPLICATION_GROUP_ID = S.APPLICATION_GROUP_ID   " + 
				" LEFT JOIN BUSINESS_CLASS BUS ON APP.BUSINESS_CLASS_ID = BUS.BUS_CLASS_ID   " + 
				" LEFT JOIN ORGANIZATION ORG ON BUS.ORG_ID = ORG.ORG_ID " +
				" WHERE (1=1) " +
				" AND AG.JOB_STATE IS NOT NULL ");
		if(!searchForm.empty("APPLICATION_NO")) {
			sql.append(" AND AG.APPLICATION_GROUP_NO = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_NO"));
		}
		if(!searchForm.empty("APPLICATION_STATUS")) {
//			sql.append(" AND AG.JOB_STATE = ? ");
			if(APPLICATION_STATIC_APPROVED.equals(searchForm.getString("APPLICATION_STATUS"))){
				//sql.append(" AND AG.JOB_STATE IN ( ? , ? , ?) ");
				//searchForm.setString(JOB_STATE_AFTER_CARDLINK);
				//searchForm.setString(JOB_STATE_BEFORE_CARDLINK);
				//searchForm.setString(JOB_STATE_DE2_APPROVE);
				
				//Defect#3314
				sql.append(" AND ((ORG.ORG_ID = 'KPL' AND AG.JOB_STATE = ? ) OR (ORG.ORG_ID <> 'KPL' AND AG.JOB_STATE IN ( ? , ? , ?))) ");
				//KPL
				searchForm.setString(JOB_STATE_AFTER_CARDLINK);
				//Non-KPL
				searchForm.setString(JOB_STATE_AFTER_CARDLINK);
				searchForm.setString(JOB_STATE_BEFORE_CARDLINK);
				searchForm.setString(JOB_STATE_DE2_APPROVE);
				
			}else if(APPLICATION_STATIC_REJECTED.equals(searchForm.getString("APPLICATION_STATUS"))){
				sql.append(" AND AG.JOB_STATE IN ( ? , ?) ");
				searchForm.setString(JOB_STATE_REJECTED);
				searchForm.setString(JOB_STATE_DE2_REJECT);
			}else if(APPLICATION_STATIC_CANCELLED.equals(searchForm.getString("APPLICATION_STATUS"))){
				sql.append(" AND AG.JOB_STATE IN (?) ");
				searchForm.setString(JOB_STATE_CANCELLED);
			}else if(APPLICATION_STATIC_INPROCESS.equals(searchForm.getString("APPLICATION_STATUS"))){
				sql.append(" AND ((ORG.ORG_ID = 'KPL' AND AG.JOB_STATE NOT IN (?,?,?,?)) OR (ORG.ORG_ID <> 'KPL' AND AG.JOB_STATE NOT IN ( ? , ? , ? , ? , ? , ?))) ");
				
				//Defect#3403
				//KPL
				searchForm.setString(JOB_STATE_AFTER_CARDLINK);
				searchForm.setString(JOB_STATE_REJECTED);
				searchForm.setString(JOB_STATE_DE2_REJECT);
				searchForm.setString(JOB_STATE_CANCELLED);
				//Non-KPL
				searchForm.setString(JOB_STATE_AFTER_CARDLINK);
				searchForm.setString(JOB_STATE_BEFORE_CARDLINK);
				searchForm.setString(JOB_STATE_DE2_APPROVE);
				searchForm.setString(JOB_STATE_REJECTED);
				searchForm.setString(JOB_STATE_DE2_REJECT);
				searchForm.setString(JOB_STATE_CANCELLED);
			}
//			searchForm.setString(searchForm.getString("APPLICATION_STATUS"));
		}
		if(!searchForm.empty("PRODUCT")){
			sql.append(" AND ORG.ORG_ID = ? ");
			
			//Add function to recognize KPL Product
			String product = searchForm.getString("PRODUCT");
			product = KPLUtil.checkKPLOrg(product);
			logger.debug("product = " + product);
			searchForm.setString(product);
			
		}
		if(!searchForm.empty("TH_FIRST_NAME")) {
			sql.append(" AND PI.TH_FIRST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_FIRST_NAME").trim());
		}
		if(!searchForm.empty("TH_LAST_NAME")) {
			sql.append(" AND PI.TH_LAST_NAME LIKE ( ? || '%') ");
			searchForm.setString(searchForm.getString("TH_LAST_NAME").trim());
		}
		if(!searchForm.empty("IDNO")) {
			sql.append(" AND PI.IDNO = ? ");
			searchForm.setString(searchForm.getString("IDNO"));
		}
		if(!searchForm.empty("APPLY_CHANNEL")){
			sql.append(" AND AG.APPLY_CHANNEL = ? ");
			searchForm.setString(searchForm.getString("APPLY_CHANNEL"));
		}
		if(!searchForm.empty("SCAN_DATE_FROM") && !searchForm.empty("SCAN_DATE_TO")){
			sql.append(" AND AG.APPLICATION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("SCAN_DATE_TO", FormatUtil.TH));
		}
		if(!searchForm.empty("SCAN_DATE_FROM") && searchForm.empty("SCAN_DATE_TO")){
			sql.append(" AND AG.APPLICATION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("SCAN_DATE_FROM", FormatUtil.TH));
		}
		
		//Add FINAL_DECISION_DATE_FROM/TO as KPL Implements
		if(!searchForm.empty("FINAL_DECISION_DATE_FROM") && !searchForm.empty("FINAL_DECISION_DATE_TO")){
			sql.append(" AND APP.FINAL_APP_DECISION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_TO", FormatUtil.TH));
		}
		if(!searchForm.empty("FINAL_DECISION_DATE_FROM") && searchForm.empty("FINAL_DECISION_DATE_TO")){
			sql.append(" AND APP.FINAL_APP_DECISION_DATE BETWEEN TRUNC( ? ) AND TRUNC( ? )+0.99999 ");
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
			searchForm.setDate(searchForm.getDate("FINAL_DECISION_DATE_FROM", FormatUtil.TH));
		}
		
		sql.append(" AND NVL(AG.WF_STATE,'FAIL') != ? ");
		searchForm.setString(WF_STATE_RUNNING);
		// Add Source <> CJD
		String cjdSQL = ApplicationUtil.cjdSQL(" AND ( AG.SOURCE IS NULL OR AG.SOURCE NOT IN( ");
		if ( cjdSQL.length() > 0 ) {
			sql.append( cjdSQL );
			sql.append(" ) "); 
		}
		sql.append(" ORDER BY AG.APPLICATION_GROUP_NO DESC ");
		logger.debug("SQL : " + sql.toString());
		return sql.toString();
	}
	private ArrayList<HashMap<String, Object>> getDocumentFollow(ApplicationGroupDataM applicationGroup,ArrayList<PersonalInfoDataM> personalInfos){
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		String APPLICATION_GROUP_ID = applicationGroup.getApplicationGroupId();
		for(PersonalInfoDataM personalInfo : personalInfos){
			try{
				String PERSONAL_ID = personalInfo.getPersonalId();
				ApplicationGroupDataM applicationGroupDoc = ORIGDAOFactory.getApplicationGroupDAO().loadApplicationGroupDocument(APPLICATION_GROUP_ID, PERSONAL_ID);
				ArrayList<FollowDocHistoryDataM> followDocHistorys = findFollowDocHistorys(applicationGroupDoc);
				if(null!=followDocHistorys){
					for(FollowDocHistoryDataM followDocHistory : followDocHistorys){
						if(!Util.empty(followDocHistory.getDocumentCode())){
							HashMap<String, Object> dataResult = new HashMap<String, Object>();
							String docName = CacheControl.getName(CACH_NAME_DOCUMENT_LIST,followDocHistory.getDocumentCode());
							dataResult.put("TH_DESC", docName);
							resultList.add(dataResult);
						}
					}
				}
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}
		}
		return resultList;
	}
	
	private ArrayList<FollowDocHistoryDataM> findFollowDocHistorys(ApplicationGroupDataM applicationGroup) throws Exception{
		ArrayList<FollowDocHistoryDataM> followDocHistorys = new ArrayList<FollowDocHistoryDataM>();
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		OrigFollowDocHistoryDAO followDocHistoryDAO = ORIGDAOFactory.getFollowDocHistoryDAO();
		int maxSeq = followDocHistoryDAO.selectMaxFollowupSeq(applicationGroupId);
		int currentSeq = maxSeq+1;
		ArrayList<DocumentCheckListDataM> documentCheckLists = applicationGroup.getDocumentCheckLists();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo:personalInfos){
				if(!Util.empty(documentCheckLists)){
					for(DocumentCheckListDataM documentCheckList :documentCheckLists){
						if(!isDocComplete(personalInfo, documentCheckList)){
							String documentCode = documentCheckList.getDocumentCode();
							ArrayList<DocumentCheckListReasonDataM> docCheckListReasons = documentCheckList.getDocumentCheckListReasons();
							if(!Util.empty(docCheckListReasons)){
								for(DocumentCheckListReasonDataM docCheckListReason : docCheckListReasons){
									if(!isContainDocReasonAndDocCode(followDocHistorys,docCheckListReason.getDocReason(),documentCode)){
										FollowDocHistoryDataM followDocHistory = new FollowDocHistoryDataM();
										followDocHistory.setApplicationGroupId(applicationGroup.getApplicationGroupId());
										followDocHistory.setDocumentCode(documentCode);
										followDocHistory.setFollowupSeq(currentSeq);
										followDocHistory.setDocReason(docCheckListReason.getDocReason());
										followDocHistorys.add(followDocHistory);
									}
								}							
							}
						}
					}
				}
			}
		}
		return followDocHistorys;
	}
	
	private static boolean isDocComplete(PersonalInfoDataM personalInfo, DocumentCheckListDataM  documentCheckList) {
		boolean isDocComplete = true; 
		String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
		String REASON_CODE_NO_DOC = SystemConstant.getConstant("REASON_CODE_NO_DOC");
		String chkListDocCode = documentCheckList.getDocumentCode();
		String chkListDocTypeId = documentCheckList.getDocTypeId();
		boolean foundGeneratedReasonNoDoc = false;
		ArrayList<DocumentCheckListReasonDataM> documentCheckListReasons = documentCheckList.getDocumentCheckListReasons();
		if(null!=documentCheckListReasons){
			for(DocumentCheckListReasonDataM documentCheckReasons : documentCheckListReasons){
				String reasonCode = documentCheckReasons.getDocReason();
				String generateFlag = documentCheckReasons.getGenerateFlag();
				if(REASON_CODE_NO_DOC.equals(reasonCode) && FLAG_YES.equals(generateFlag)){
					foundGeneratedReasonNoDoc = true;
					break; 
				}
			}
		}
		logger.debug("chkListDocCode : "+chkListDocCode);	// ORIG_DOC_CHECK_LIST.DOCUMENT_CODE
		logger.debug("chkListDocTypeId : "+chkListDocTypeId); // ORIG_DOC_CHECK_LIST.DOC_TYPE_ID
		
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		if(null==verificationResult) {
			verificationResult = new VerificationResultDataM();
		}		
		ArrayList<RequiredDocDataM> requiredDocList = verificationResult.getRequiredDocs();
		if(!ServiceUtil.empty(requiredDocList)) {
			for(RequiredDocDataM requiredDoc : requiredDocList) {
				if(null==requiredDoc) {
					requiredDoc = new RequiredDocDataM();
				}
				ArrayList<RequiredDocDetailDataM> requiredDocDetailList = requiredDoc.getRequiredDocDetails();
				if(!ServiceUtil.empty(requiredDocDetailList)) {
					for(RequiredDocDetailDataM requiredDocDetail : requiredDocDetailList) {
						if(null==requiredDocDetail) {
							requiredDocDetail = new RequiredDocDetailDataM();
						}
						String reqDocScnType = requiredDoc.getScenarioType();
						String reqDocDocCompletedFlag = requiredDoc.getDocCompletedFlag();
						String reqDocDetailDocCode = requiredDocDetail.getDocumentCode();
						logger.debug("reqDocScnType : " + reqDocScnType);	// XRULES_REQUIRED_DOC.SCENARIO_TYPE
						logger.debug("reqDocDocCompletedFlag : " + reqDocDocCompletedFlag);	// XRULES_REQUIRED_DOC.DOC_COMPLETED_FLAG
						logger.debug("reqDocDetailDocCode : " + reqDocDetailDocCode); // XRULES_REQUIRED_DOC_DETAIL.DOCUMENT_CODE
						if(!ServiceUtil.empty(chkListDocCode) && !ServiceUtil.empty(reqDocScnType) && !ServiceUtil.empty(chkListDocTypeId) && !ServiceUtil.empty(reqDocDetailDocCode)	// check null
								&& chkListDocTypeId.equals(reqDocScnType) && chkListDocCode.equals(reqDocDetailDocCode)) {		// check match
							if((null==reqDocDocCompletedFlag || !FLAG_YES.equals(reqDocDocCompletedFlag)) || (FLAG_YES.equals(reqDocDocCompletedFlag) && !foundGeneratedReasonNoDoc)) {	// check detail
								isDocComplete = false;
							}
						}
					}
				}
			}
		}
		
		logger.info("result isDocComplete : "+isDocComplete);
		return isDocComplete;
	}
	
	private static boolean isContainDocReasonAndDocCode(ArrayList<FollowDocHistoryDataM> followDocHistoryList,String docReason,String docCode){
		if(!Util.empty(followDocHistoryList)){
			for(FollowDocHistoryDataM followDocHis:followDocHistoryList){
				String docCodeFollow = followDocHis.getDocumentCode();
				String docReasonFollow = followDocHis.getDocReason();
				if(!Util.empty(docCodeFollow) && !Util.empty(docReasonFollow) && docCodeFollow.equals(docCode) && docReasonFollow.equals(docReason)){
					return true;
				}
			}
		}
		return false;
	}
	
}
