package com.eaf.orig.ulo.dm.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.engine.QueryResultController;
import com.eaf.core.ulo.common.model.SQLDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.shared.service.ServiceLocatorException;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.dm.DocumentDataM;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class DocumentManageService {
	static transient Logger logger = Logger.getLogger(DocumentManageService.class);
	public static final String serviceId = "CreateWarehouse";
	String APPLICATION_TYPE_ADD_SUP =SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String PERSONAL_TYPE_APPLICANT= SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY= SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String DM_DOC_SET_TYPE = SystemConstant.getConstant("DM_DOC_SET_TYPE");
	ArrayList<String> DM_DISPOSED_APPLICATION_STATUS = SystemConstant.getArrayListConstant("DM_DISPOSED_APPLICATION_STATUS");
	String DM_SYSTEM_ID = SystemConstant.getConstant("DM_SYSTEM_ID");
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String CREATE_WAREHOUSE_JOB_STATE_REJECTED = InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_JOB_STATE_REJECTED");
	String CREATE_WAREHOUSE_JOB_STATE_CANCELLED = InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_JOB_STATE_CANCELLED");
	String CREATE_WAREHOUSE_JOB_STATE_APPROVED = InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_JOB_STATE_APPROVED");


	@SuppressWarnings("rawtypes")
	public DocumentManagementDataM createDMProcess(String applicationGroupId) throws Exception{
		
		String userId = InfBatchProperty.getInfBatchConfig("SYSTEM_USER");
		HashMap  hAppGroup   =  getApplicationGroupData(applicationGroupId,true);
		String applicationType = (String)hAppGroup.get("APPLICATION_TYPE");
		String lastDecision = (String)hAppGroup.get("LAST_DECISION");
		String APPLICATION_STATUS = (String)hAppGroup.get("APPLICATION_STATUS");
		logger.debug("applicationGroupId : "+applicationGroupId);
		logger.debug("applicationType : "+applicationType);
		logger.debug("lastDecision : "+lastDecision);
		logger.debug("APPLICATION_STATUS : "+APPLICATION_STATUS);
		logger.debug("userId : "+userId);		
		HashMap hPerson = getPersonalInfo(applicationGroupId, applicationType);		
		DocumentManagementDataM docManageM = new DocumentManagementDataM();
		String DM_ID = getDMdocID((String)hAppGroup.get("APPLICATION_GROUP_NO"));
		logger.debug("DM_ID : "+DM_ID);
		if(!Util.empty(DM_ID)){
			docManageM.setDmId(DM_ID);
		}
		docManageM.setRefNo2((String)hAppGroup.get("APPLICATION_GROUP_NO"));
		docManageM.setBranchId((String)hAppGroup.get("BRANCH_NO"));
		docManageM.setParam1(getDMApplicationStatus((String)hAppGroup.get("JOB_STATE")));
		docManageM.setParam2((String)hAppGroup.get("LAST_DECISION_DATE"));
		docManageM.setParam3((String)hAppGroup.get("APPLICATION_DATE"));				
		docManageM.setParam5((String)hAppGroup.get("BRANCH_NAME"));		
		
		ProductInfo productInfo = getProductInfo(applicationGroupId);
		if(!Util.empty(productInfo.getBusClassIds())){
			docManageM.setParam4(StringUtils.join(productInfo.getBusClassIds(),"|"));
		}
		if(!Util.empty(productInfo.getCardTypeIds())){
			docManageM.setParam6(StringUtils.join(productInfo.getCardTypeIds(),","));
		}
		if(!Util.empty(productInfo.getCardTypeDescs())){
			docManageM.setParam7(StringUtils.join(productInfo.getCardTypeDescs(),","));
		}
		
		docManageM.setIdno((String)hPerson.get("IDNO"));
		docManageM.setDocSetId((String)hAppGroup.get("APPLICATION_GROUP_NO"));
		docManageM.setIdType((String)hPerson.get("CID_TYPE"));
		docManageM.setThTitleName((String)hPerson.get("TH_TITLE_CODE"));
		docManageM.setThFirstName((String)hPerson.get("TH_FIRST_NAME"));
		docManageM.setThLastName((String)hPerson.get("TH_LAST_NAME"));
		docManageM.setEnTitleName((String)hPerson.get("EN_TITLE_CODE"));
		docManageM.setEnFirstName((String)hPerson.get("EN_FIRST_NAME"));
		docManageM.setEnLastName((String)hPerson.get("EN_LAST_NAME"));
		docManageM.setStatus(MConstant.DM_STATUS.NOT_IN_WAREHOUSE);
		docManageM.setCreateBy(userId);
		docManageM.setCreateDate(ApplicationDate.getTimestamp());
		docManageM.setUpdateBy(userId);
		docManageM.setUpdateDate(ApplicationDate.getTimestamp());
		docManageM.setDocSetType(DM_DOC_SET_TYPE);
		if(DM_DISPOSED_APPLICATION_STATUS.contains(APPLICATION_STATUS)){
			docManageM.setPolicyId(getPolicyId(DM_DOC_SET_TYPE));
		}
		docManageM.setSystemId(DM_SYSTEM_ID);		
		Vector<HashMap> vDocumentList = getApplicationImgSplit(applicationGroupId);
		ArrayList<DocumentDataM> documents = new ArrayList<DocumentDataM>();
		if(!Util.empty(vDocumentList)){
			for(HashMap hDocument :vDocumentList){
				String documentType =(String)hDocument.get("DOC_TYPE");
				String noOfPage = (String) hDocument.get("NO_OF_PAGE");
				DocumentDataM document = new  DocumentDataM();
				document.setDocType(documentType);
				document.setNoOfPage(!Util.empty(noOfPage)?Integer.parseInt(noOfPage):null);
				document.setCreateBy(userId);	
				document.setCreateDate(ApplicationDate.getTimestamp());
				document.setUpdateBy(userId);
				document.setUpdateDate(ApplicationDate.getTimestamp());
//					document.setStatus(MConstant.DM_STATUS.NOT_IN_WAREHOUSE);
				documents.add(document);
			}
		}
		docManageM.setDocument(documents);
		return docManageM;
	}
	
	@SuppressWarnings("rawtypes")
	private HashMap getApplicationGroupData(String uniqueId,boolean searchByAppGroupId) throws Exception{
		QueryResultController queryEngine = new QueryResultController();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT BRANCH_NO, ");
		sql.append("     APPLICATION_GROUP_ID, ");
		sql.append("     TO_CHAR(LAST_DECISION_DATE,'yyyy-mm-dd hh24:mi:ss') AS LAST_DECISION_DATE, ");
		sql.append("     TO_CHAR(APPLICATION_DATE,'yyyy-mm-dd hh24:mi:ss') AS APPLICATION_DATE, ");
		sql.append("     APPLICATION_GROUP_NO, ");
		sql.append("     JOB_STATE, ");
		sql.append("     APPLICATION_TYPE, ");
		sql.append("     UPDATE_BY, ");
		sql.append("     LAST_DECISION, ");
		sql.append("     BRANCH_NAME, ");
		sql.append("     APPLICATION_STATUS ");
		sql.append(" FROM ORIG_APPLICATION_GROUP ");
		if(searchByAppGroupId){
			sql.append(" WHERE APPLICATION_GROUP_ID = ? ");
		}else{
			sql.append(" WHERE INSTANT_ID = ? ");
		}
		return queryEngine.loadResult(sql.toString(),uniqueId);
	}	
	@SuppressWarnings("rawtypes")
	private ProductInfo getProductInfo(String applicationGroupId) throws Exception{		
		ProductInfo productInfo = new ProductInfo();
		QueryResultController queryEngine = new QueryResultController();
		StringBuilder sql = new StringBuilder();
			sql.append(" SELECT DISTINCT A.APPLICATION_GROUP_ID, ");
			sql.append("     A.BUSINESS_CLASS_ID, ");
			sql.append("     B.ORG_ID, ");
			sql.append("     B.BUS_CLASS_DESC, ");
			sql.append("     C.CARD_TYPE, ");
			sql.append("     CT.CARD_TYPE_DESC ");
			sql.append(" FROM ORIG_APPLICATION A ");
			sql.append(" JOIN BUSINESS_CLASS B  ON B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID ");
			sql.append(" JOIN ORIG_LOAN L       ON A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID ");
			sql.append(" LEFT JOIN ORIG_CARD C  ON L.LOAN_ID = C.LOAN_ID ");
			sql.append(" LEFT JOIN CARD_TYPE CT ON C.CARD_TYPE = CT.CARD_TYPE_ID ");
			sql.append(" WHERE A.APPLICATION_GROUP_ID = ? ");
			sql.append(" ORDER BY  A.BUSINESS_CLASS_ID,C.CARD_TYPE ");
		Vector<HashMap> results = queryEngine.loadResults(sql.toString(),applicationGroupId);
		if(!Util.empty(results)){
			for (HashMap result : results) {
				String ORG_ID = (String)result.get("ORG_ID");
				logger.debug("ORG_ID : "+ORG_ID);
				if(PRODUCT_K_PERSONAL_LOAN.equals(ORG_ID)){
					String busClassId = (String)result.get("BUSINESS_CLASS_ID");
					String cardTypeId = (String)result.get("BUSINESS_CLASS_ID");
					String cardTypeDesc = (String)result.get("BUS_CLASS_DESC");
					mapProductInfo(productInfo, busClassId, cardTypeId, cardTypeDesc);
				}else{
					String busClassId = (String)result.get("BUSINESS_CLASS_ID");
					String cardTypeId = (String)result.get("CARD_TYPE");
					String cardTypeDesc = (String)result.get("CARD_TYPE_DESC");
					mapProductInfo(productInfo, busClassId, cardTypeId, cardTypeDesc);
				}
			}
		}
		return productInfo;
	}	
	private void mapProductInfo(ProductInfo productInfo,String busClassId,String cardTypeId,String cardTypeDesc){
		productInfo.addBusClassId(busClassId);
		productInfo.addCardTypeId(cardTypeId);
		productInfo.addCardTypeDesc(cardTypeDesc);
	}
	@SuppressWarnings("serial")
	public class ProductInfo implements Serializable,Cloneable{
		private ArrayList<String> cardTypeIds = new ArrayList<String>();
		private ArrayList<String> cardTypeDescs = new ArrayList<String>();
		private ArrayList<String> busClassIds = new ArrayList<String>();
		public ArrayList<String> getCardTypeIds() {
			return cardTypeIds;
		}
		public void setCardTypeIds(ArrayList<String> cardTypeIds) {
			this.cardTypeIds = cardTypeIds;
		}		
		public ArrayList<String> getCardTypeDescs() {
			return cardTypeDescs;
		}
		public void setCardTypeDescs(ArrayList<String> cardTypeDescs) {
			this.cardTypeDescs = cardTypeDescs;
		}
		public ArrayList<String> getBusClassIds() {
			return busClassIds;
		}
		public void setBusClassIds(ArrayList<String> busClassIds) {
			this.busClassIds = busClassIds;
		}	
		public void addCardTypeId(String cardTypeId){
			if(!cardTypeIds.contains(cardTypeId)){
				cardTypeIds.add(cardTypeId);
			}
		}
		public void addCardTypeDesc(String cardTypeDesc){
			if(!cardTypeDescs.contains(cardTypeDesc)){
				cardTypeDescs.add(cardTypeDesc);
			}
		}
		public void addBusClassId(String busClassId){
			if(!busClassIds.contains(busClassId)){
				busClassIds.add(busClassId);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private HashMap getPersonalInfo(String applicationGroupId,String applicationType) throws Exception{
		QueryResultController queryEngine = new QueryResultController();
		StringBuilder sql  = new StringBuilder();		
		sql.append(" SELECT PERSONAL_TYPE, ");
		sql.append(" 	IDNO, ");
		sql.append("    CID_TYPE, ");
		sql.append("    TH_TITLE_CODE, ");
		sql.append("    TH_FIRST_NAME , ");
		sql.append("    TH_LAST_NAME, ");
		sql.append("    EN_TITLE_CODE, ");
		sql.append("    EN_FIRST_NAME, ");
		sql.append("    EN_LAST_NAME ");
		sql.append(" FROM  ORIG_PERSONAL_INFO ");
		sql.append(" WHERE APPLICATION_GROUP_ID = ? ");		
		Vector<HashMap> vPersonInfo = queryEngine.loadResults(sql.toString(),applicationGroupId);			
		if(!Util.empty(vPersonInfo)){
			for(HashMap personInfo:vPersonInfo){
				String personalType = (String)personInfo.get("PERSONAL_TYPE");					
				if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
					if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
						return personInfo;
					}
				}else{
					if(PERSONAL_TYPE_APPLICANT.equals(personalType)){
						return personInfo;
					}
				}
			}			
		}
		return !Util.empty(vPersonInfo)?vPersonInfo.get(0):new HashMap();
	}
	
	@SuppressWarnings("rawtypes")
	private Vector<HashMap> getApplicationImgSplit(String applicationGroupId) throws Exception{
		QueryResultController queryEngine = new QueryResultController();
		StringBuilder sql  = new StringBuilder();		
		sql.append(" SELECT IMG_SPLIT.DOC_TYPE, ");
		sql.append("     TO_CHAR(COUNT(IMG_SPLIT.DOC_TYPE)) AS NO_OF_PAGE ");
		sql.append(" FROM ORIG_APPLICATION_IMG IMG ");
		sql.append(" INNER JOIN ORIG_APPLICATION_IMG_SPLIT IMG_SPLIT ON IMG_SPLIT.APP_IMG_ID =IMG.APP_IMG_ID ");
		sql.append(" WHERE DOC_TYPE IS NOT NULL AND IMG.APPLICATION_GROUP_ID = ? ");
		sql.append(" GROUP BY IMG_SPLIT.DOC_TYPE ");			
		return queryEngine.loadResults(sql.toString(),applicationGroupId);
	}
	
	@SuppressWarnings("rawtypes")
	private String getDMdocID(String applicationGroupNo) throws ServiceLocatorException, Exception{	
		QueryResultController queryEngine = new QueryResultController(OrigServiceLocator.WAREHOUSE_DB);
		StringBuilder sql  = new StringBuilder();
			sql.append("SELECT DM_ID FROM DM_DOC WHERE REF_NO2 = ? ");
		SQLDataM sqlM = new SQLDataM();
		sqlM.setSQL(sql.toString());
		sqlM.setString(1,applicationGroupNo);
		Vector<HashMap> vDMList = queryEngine.loadResults(sqlM);
		if(!Util.empty(vDMList)){
			return  (String)vDMList.get(0).get("DM_ID");
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private String getPolicyId(String docSetType) throws Exception{
		QueryResultController queryEngine = new QueryResultController(OrigServiceLocator.WAREHOUSE_DB);
		StringBuilder sql  = new StringBuilder();
			sql.append("SELECT POLICY_ID FROM MS_DM_DISPOSE_POLICY WHERE DOC_SET_TYPE = ?");
		SQLDataM sqlM = new SQLDataM();
		sqlM.setSQL(sql.toString());
		sqlM.setString(1,docSetType);	
		Vector<HashMap> vDMList = queryEngine.loadResults(sqlM);
		if(!Util.empty(vDMList)){
			return  (String)vDMList.get(0).get("POLICY_ID");
		}
		return null;
	}	
	
	private String getDMApplicationStatus(String jobState){
		logger.debug("jobState : "+jobState);
		if(CREATE_WAREHOUSE_JOB_STATE_REJECTED.equals(jobState)){
			return InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_APP_STATUS_REJECTED");
		}else if(CREATE_WAREHOUSE_JOB_STATE_CANCELLED.equals(jobState)){
			return InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_APP_STATUS_CANCELLED");
		}else if(CREATE_WAREHOUSE_JOB_STATE_APPROVED.equals(jobState)){
			return InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_APP_STATUS_APPROVED");
		}
		return InfBatchProperty.getInfBatchConfig("CREATE_WAREHOUSE_APP_STATUS_IN_PROCESS");
	}
}
