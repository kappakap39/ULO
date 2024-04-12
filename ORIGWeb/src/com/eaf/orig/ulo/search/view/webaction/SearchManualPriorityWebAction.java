package com.eaf.orig.ulo.search.view.webaction;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.engine.SearchFormHandler;
import com.eaf.core.ulo.common.engine.SearchQueryEngine;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.SearchResultComparator;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.ias.shared.model.ObjectM;
import com.eaf.ias.shared.model.RoleM;
import com.eaf.ias.shared.model.service.IASServiceRequest;
import com.eaf.ias.shared.model.service.IASServiceResponse;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
import com.orig.bpm.workflow.model.BPMInboxInstance;
//import com.eaf.core.ulo.common.rest.RESTClient;
//import com.eaf.core.ulo.common.rest.RESTResponse;
//import com.eaf.ias.shared.model.helper.IASServiceResponseMapper;

public class SearchManualPriorityWebAction extends WebActionHelper	implements WebAction{
	private static transient Logger logger = Logger.getLogger(SearchManualPriorityWebAction.class);
	String IAS_SERVICE_PARENTROLE_URL = SystemConfig.getProperty("IAS_SERVICE_PARENTROLE_URL");
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

	@Override
	public boolean preModelRequest() {
		SearchFormHandler searchForm = new SearchFormHandler(getRequest()){
			@Override
			public void postSearchResult(ArrayList<HashMap<String, Object>> searchResult) {
				logger.debug("do postSearchResult()..");
				if(!Util.empty(searchResult)){
					for(HashMap<String, Object> Row : searchResult){						
						logger.debug("SQLQueryEngine.display : "+SQLQueryEngine.display(Row,"CUSTOMER_NAME_IDNO"));

						// df 2560
						PersonalInfoUtil.setPersonalInfoForSearch(Row);
						try{
							String applicationGroupId = (String) Row.get("APPLICATION_GROUP_ID");
							if(!Util.empty(applicationGroupId)){
								int lifeCycle = ORIGDAOFactory.getApplicationGroupDAO().getMaxLifeCycle(applicationGroupId);
								Row.put("MAX_LIFE_CYCLE", lifeCycle);
							}
						}catch(Exception e){
							logger.fatal("ERROR ",e);
						}
					}
				}
			}
		};	
		Vector<ObjectM> imObjects = (Vector<ObjectM>)getRequest().getSession().getAttribute("iamObjects");	
		try {
			ArrayList<String> removeRoleList = this.getRemoveRoleList(getRequest());
			StringBuilder roleList = new StringBuilder();
			String COMMA = "";
			if (!Util.empty(imObjects)) {
				for (ObjectM objectDetail : imObjects) {
					String objectType =objectDetail.getObjectType();
					String objectName =objectDetail.getObjectName();
					logger.debug("getObjectType::" + objectType);					
					if (SystemConstant.getConstant("OBJECT_TYPE").equals(objectType) && !removeRoleList.contains(objectName)) {
						roleList.append(COMMA+objectName.replace("_", "."));	
						COMMA = ",";
						setReferenceRole(objectName,roleList,COMMA);
						logger.debug("Role Name::" +objectName);
						 
					}

				}
			}
			 
			logger.debug(">>>>roleList>>>"+roleList.toString());
			searchForm.setNextPage(false);
			searchForm.setAtPage(1);	
			SearchQueryEngine SearchEngine = new SearchQueryEngine();
			searchForm.setSQL(getSelectSerchSQL(searchForm,roleList.toString()));
			searchForm.setSubSQL(subSQL());
			SearchEngine.search(searchForm);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
	 	
		getRequest().getSession().setAttribute(SearchFormHandler.SearchForm,searchForm);
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.PAGE;
	}
	private String getSelectSerchSQL(SearchFormHandler searchForm,String  roleList){
		StringBuilder sql = new StringBuilder();
		
        sql.append("  SELECT APP_GROUP.APPLICATION_GROUP_ID,");
        sql.append("  	APP_GROUP.PRIORITY,");
        sql.append("  	APP_GROUP.INSTANT_ID,");
        sql.append(" 	APP_GROUP.JOB_STATE,");
		//sql.append(" (SELECT U.USER_NAME FROM LSW_TASK T JOIN LSW_USR_XREF U ON T.USER_ID = U.USER_ID WHERE APP_GROUP.INSTANT_ID = T.BPD_INSTANCE_ID AND T.STATUS = '12') OWNER,");
		//sql.append(" (SELECT T.TASK_ID FROM LSW_TASK T WHERE APP_GROUP.INSTANT_ID = T.BPD_INSTANCE_ID AND T.STATUS = '12') TASK_ID,");
        sql.append(" 	U.USER_NAME OWNER,");
        sql.append(" 	T.TASK_ID TASK_ID,");
        sql.append(" 	APP_GROUP.APPLICATION_GROUP_NO,");
        sql.append(" 	APP_GROUP.CLAIM_BY,");
        sql.append(" 	APP_GROUP.APPLICATION_STATUS,");
        sql.append(" 	TO_CHAR(APP_GROUP.UPDATE_DATE,'dd/mm/yyyy | HH24:MI','NLS_CALENDAR=''THAI BUDDHA'' NLS_DATE_LANGUAGE=THAI') AS LAST_UPDATE ");
        sql.append(" 	FROM  ORIG_APPLICATION_GROUP APP_GROUP");
        sql.append(" JOIN LSW_BPD_INSTANCE I ON APP_GROUP.INSTANT_ID = I.BPD_INSTANCE_ID");
        sql.append(" JOIN LSW_TASK T ON T.BPD_INSTANCE_ID = I.BPD_INSTANCE_ID AND T.STATUS='12'");
        sql.append(" LEFT JOIN LSW_USR_XREF U ON U.USER_ID = T.USER_ID"); //Use left join to compensate select in select which can return null
        sql.append(" JOIN LSW_PARTICIPANT_GROUP G ON T.PARTICIPANT_ID = G.PARTICIPANT_ID AND I.SNAPSHOT_ID = G.SNAPSHOT_ID");
        sql.append(" JOIN LSW_PARTICIPANT P ON G.PARTICIPANT_ID = P.PARTICIPANT_ID AND G.CACHED_PART_VERSION_ID = P.VERSION_ID");        
//        sql.append(" 	LEFT JOIN ORIG_APPLICATION APP ON  APP_GROUP.APPLICATION_GROUP_ID = APP.APPLICATION_GROUP_ID");
        /*sql.append(" 	LEFT JOIN ORIG_PERSONAL_RELATION PER_RELATION  ON APP.APPLICATION_RECORD_ID = PER_RELATION.REF_ID ");*/
//       sql.append(" 	LEFT JOIN ORIG_PERSONAL_INFO PERS  ON APP_GROUP.APPLICATION_GROUP_ID = PERS.APPLICATION_GROUP_ID");
//		sql.append(" 	LEFT JOIN BUSINESS_CLASS BU_CLASS  ON  APP.BUSINESS_CLASS_ID = BU_CLASS.BUS_CLASS_ID");
//        sql.append(" 	LEFT JOIN ORGANIZATION ORG ON  BU_CLASS.ORG_ID = ORG.ORG_ID");
//        sql.append(" 	LEFT JOIN ORIG_LOAN  LOAN ON APP.APPLICATION_RECORD_ID = LOAN.APPLICATION_RECORD_ID");
//        sql.append("  	LEFT JOIN ORIG_CARD CARD ON  LOAN.LOAN_ID = CARD.LOAN_ID");
//        sql.append(" 	INNER JOIN (SELECT * FROM TABLE(BPM_INBOX.GET_REASSIGNABLE_INSTANCE(?,?))) RE_ASSIGN ON RE_ASSIGN.BPD_INSTANCE_ID = APP_GROUP.INSTANT_ID");
        sql.append("  	WHERE (1=1)");
//        sql.append(" 	AND APP_GROUP.JOB_STATE IS NOT NULL");
//        searchForm.setString(roleList);
//		searchForm.setString(searchForm.getString("STAFF_NAME"));
		
    	if(!searchForm.empty("APPLICATION_GROUP_NO")){
			sql.append(" AND   APP_GROUP.APPLICATION_GROUP_NO = ? ");
			searchForm.setString(searchForm.getString("APPLICATION_GROUP_NO"));
		}
    	/*if(!searchForm.empty("PRIORITY")){
    		sql.append(" AND   APP_GROUP.PRIORITY= ? ");
			searchForm.setString(searchForm.getString("PRIORITY"));
    	}
    	if(!searchForm.empty("PRODUCT")){
    		sql.append(" AND  ORG.ORG_ID= ? ");
    		String ORG_ID = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_PRODUCT_TYPE"),searchForm.getString("PRODUCT"), "SYSTEM_ID1");
			searchForm.setString(ORG_ID);
    	}
    	if(!searchForm.empty("FIRST_NAME")){
    		sql.append(" AND   UPPER(PERS.TH_FIRST_NAME) LIKE UPPER(?) ");
			searchForm.setString(searchForm.getString("FIRST_NAME")+"%");
    	}
    	if(!searchForm.empty("LAST_NAME")){
    		sql.append(" AND   UPPER(PERS.TH_LAST_NAME) LIKE UPPER(?) ");
			searchForm.setString(searchForm.getString("LAST_NAME")+"%");
    	}
    	if(!searchForm.empty("ID_NO")){
    		sql.append(" AND   UPPER(PERS.IDNO) = UPPER(?) ");
			searchForm.setString(searchForm.getString("ID_NO"));
    	}   	
    	if(!searchForm.empty("PROJECT_CODE")){
    		sql.append(" AND   APP.PROJECT_CODE LIKE ? ");
			searchForm.setString(searchForm.getString("PROJECT_CODE")+"%");
    	}
    	if(!searchForm.empty("BRANCH_NO")){
    		sql.append(" AND APP_GROUP.RC_CODE=? ");
    		searchForm.setString(searchForm.getString("BRANCH_NO"));
    		sql.append(" AND   APP_GROUP.BRANCH_NO =?  OR APP_GROUP.RC_CODE=? ");
    		searchForm.setString(searchForm.getString("BRANCH_NO"));
    		searchForm.setString(searchForm.getString("BRANCH_NO"));
    	}*/ 
    	 
		//make sure there're only assignable tasks
/*		String owner = searchForm.getString("STAFF_NAME");
		if(owner != null && !owner.isEmpty()){//Search App by user_name
			String assignableClause = " AND APP_GROUP.INSTANT_ID IN (" +
					" SELECT T.BPD_INSTANCE_ID" +
					" FROM LSW_TASK T JOIN LSW_USR_XREF U ON T.USER_ID = U.USER_ID" +
					" WHERE T.STATUS = 12 AND UPPER(U.USER_NAME) = UPPER(?) )";
			sql.append(assignableClause);

			searchForm.setString(owner);
		}
		else{*/
			String assignableClause = 
//					" AND EXISTS (SELECT 1" +
//					" FROM LSW_TASK T JOIN LSW_BPD_INSTANCE I ON T.BPD_INSTANCE_ID = I.BPD_INSTANCE_ID AND T.STATUS = 12" +
//					" JOIN LSW_PARTICIPANT_GROUP G ON T.PARTICIPANT_ID = G.PARTICIPANT_ID AND I.SNAPSHOT_ID = G.SNAPSHOT_ID" +
//					" JOIN LSW_PARTICIPANT P ON G.PARTICIPANT_ID = P.PARTICIPANT_ID AND G.CACHED_PART_VERSION_ID = P.VERSION_ID " +
//					" WHERE APP_GROUP.INSTANT_ID = I.BPD_INSTANCE_ID " +
					" AND P.NAME IN ( SELECT TRIM(REGEXP_SUBSTR(?, '[^,]+', 1, COLUMN_VALUE)) BPM_GROUP" +
					" FROM TABLE(CAST(MULTISET ( SELECT LEVEL FROM DUAL CONNECT BY LEVEL <= regexp_count (?,',') + 1) AS SYS.ODCINUMBERLIST )) LEVELS )";
			sql.append(assignableClause);
	
			searchForm.setString(roleList);
			searchForm.setString(roleList);
//		}
    	   	
    	sql.append(" GROUP BY");
        sql.append(" APP_GROUP.APPLICATION_GROUP_ID,");
        sql.append(" APP_GROUP.INSTANT_ID,");
        sql.append(" APP_GROUP.JOB_STATE,");
        sql.append(" APP_GROUP.CLAIM_BY,");
        sql.append(" APP_GROUP.PRIORITY,");
        sql.append(" APP_GROUP.APPLICATION_GROUP_NO,");
        sql.append(" APP_GROUP.APPLICATION_STATUS,");
        sql.append(" APP_GROUP.UPDATE_DATE,");
        sql.append(" U.USER_NAME,"); 
        sql.append(" T.TASK_ID");
        sql.append(" ORDER BY APP_GROUP.PRIORITY");
		return sql.toString();
	}
	
	private String subSQL() {
		String SQL = " PKA_SEARCH_INFO.GET_PRODUCT(M_RESULT.APPLICATION_GROUP_ID) AS PRODUCT_NAME ";
		return SQL;
	}
	
	private BPMInboxInstance getInstant(List<BPMInboxInstance> instants,int instantId){
		if(null != instants){
			for (BPMInboxInstance instant : instants) {
				if(instantId==instant.getInstanceId()){	
					return instant;
				}
			}
		}
		return null;
	}
	private void sortResults(ArrayList<HashMap<String, Object>> searchResult){
		HashMap<String,String> keyNameAndSortingType =  new HashMap<String,String>();
		keyNameAndSortingType.put("PRIORITY",SearchResultComparator.ASC);
		keyNameAndSortingType.put("SLA_DATE",SearchResultComparator.DESC);
		Collections.sort(searchResult, new SearchResultComparator(keyNameAndSortingType));
	}
	
	private void setReferenceRole(String pRole,StringBuilder rolesList,String comma){
		try{
			if(!Util.empty(pRole)){
				IASServiceRequest serviceRequest = new IASServiceRequest();
				serviceRequest.setSystemName(OrigConstant.SYSTEM_NAME);		
				serviceRequest.setRoleName(pRole);
//				RESTClient restClient = new RESTClient();
//				RESTResponse restResponse = restClient.executeRESTCall(IAS_SERVICE_PARENTROLE_URL,serviceRequest);
//				Vector<RoleM> vparentRoles  = IASServiceResponseMapper.getRole(restResponse.getJsonResponse());
				RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
					@Override
					protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
				        if(connection instanceof HttpsURLConnection ){
				            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
								@Override
								public boolean verify(String arg0, SSLSession arg1) {
									return true;
								}
							});
				        }
						super.prepareConnection(connection, httpMethod);
					}
				});
				ResponseEntity<IASServiceResponse> responseEntity = restTemplate.postForEntity(IAS_SERVICE_PARENTROLE_URL,serviceRequest,IASServiceResponse.class);
				IASServiceResponse serviceResponse = responseEntity.getBody();
				Vector<RoleM> vparentRoles = serviceResponse.getRoles();
				if(!Util.empty(vparentRoles)){
					for(RoleM  role :vparentRoles){
						logger.debug(">>role.getRoleName()>>"+role.getRoleName());
						if(!Util.empty(role.getRoleName())){
							rolesList.append(comma+role.getRoleName());
						} 
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}
	private ArrayList<String> getRemoveRoleList(HttpServletRequest request){
		ArrayList<String> removeRoles = new ArrayList<String>();
		try {
			FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
			String PAGE_NAME = flowControl.getStoreAction("page");
			String CONSTANT_REMOVE_ROLE_NAME ="REMOVE_ROLE_"+PAGE_NAME+"_"+flowControl.getRole();
			String[] REMOVE_ROLE_VALUE_LIST = SystemConstant.getArrayConstant(CONSTANT_REMOVE_ROLE_NAME);					
			logger.debug("==CONSTANT_REMOVE_ROLE_NAME=="+CONSTANT_REMOVE_ROLE_NAME);	
			if(!Util.empty(REMOVE_ROLE_VALUE_LIST)){
				removeRoles =  new ArrayList<String>(Arrays.asList(REMOVE_ROLE_VALUE_LIST));
			}
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return removeRoles;
	}
}
