package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.ObjectDAOFactory;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGLogic;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchReassignWebAction extends WebActionHelper implements WebAction{
	
	static Logger logger = Logger.getLogger(SearchSetPriorityWebAction.class);	
	
	private String nextAction = null;
		
	public SearchReassignWebAction(){
		super();
	}
	
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
	public boolean preModelRequest(){

		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String role = userM.getCurrentRole();
		
		logger.debug(" role >> "+role);
		
		SearchHandler handler = (SearchHandler) getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(handler == null){
			handler = new SearchHandler();
		}

		SearchHandler.SearchDataM searchDataM = handler.getSearchM();
		if(searchDataM == null){
			searchDataM = new SearchHandler.SearchDataM();
		}
		
		String searchType = getRequest().getParameter("searchType");
		
		logger.debug("searchType >> "+searchType);
		
		if(searchType != null && searchType.equals(OrigConstant.FLAG_Y)){
			handler.setErrorFields(null);
		}
		
		setPARAMETER(getRequest(),searchDataM,searchType);
		
		handler.setSearchM(searchDataM);
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);

		try{			
			ValueListM valueListM = new ValueListM();			
			int index = 0;
			
			if(ORIGLogic.superReassignSearch(role)){
				getSQLSUPER_REASSIGN(role,valueListM,searchDataM,userM,index);
			}else{
				getSQLDEFAULT(role,valueListM,searchDataM,userM,index);
			}
		
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setReturnToAction("page=REASSIGN_SCREEN");
			
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			nextAction = "action=ValueListWebAction";

		}catch(Exception e){
			logger.error("exception ", e);
		}
		return true;

	}
	public void setPARAMETER(HttpServletRequest request ,SearchHandler.SearchDataM searchDataM ,String searchType){
		
		String citizen_id = getRequest().getParameter("citizen_id");
		String firstname = getRequest().getParameter("firstname");
		String lastname = getRequest().getParameter("lastname");
		String application_no = getRequest().getParameter("application_no");
		String SearchPriority = getRequest().getParameter("SearchPriority");
		String product = getRequest().getParameter("product");
		String overSLA = getRequest().getParameter("overSLA");
		String empFirstName = getRequest().getParameter("empFirstName");
		String empLastName = getRequest().getParameter("empLastName");
		String empId = getRequest().getParameter("empId");
		String role = getRequest().getParameter("role_reassign");

		if(OrigConstant.FLAG_Y.equals(searchType)){
			searchDataM.setCitizenID(citizen_id);
			searchDataM.setCustomerName(firstname);
			searchDataM.setCustomerLName(lastname);
			searchDataM.setApplicationNo(application_no);
			searchDataM.setPriority(SearchPriority);
			searchDataM.setProductCode(product);
			searchDataM.setEmpFirstName(empFirstName);
			searchDataM.setEmpLastName(empLastName);
			searchDataM.setEmpId(empId);
			searchDataM.setOverSLA(overSLA);
			searchDataM.setRole(role);
		}	
		
	}
	
	public void getSQLSUPER_REASSIGN(String role,ValueListM valueListM,SearchHandler.SearchDataM searchDataM,UserDetailM userM,int index){
		
		StringBuilder SQL = new StringBuilder("");
				
		SQL.append(" SELECT ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             DISPLAY_NAME ");
		SQL.append("         FROM ");
		SQL.append("             LIST_BOX_MASTER ");
		SQL.append("         WHERE ");
		SQL.append("             FIELD_ID = '61' ");
		SQL.append("         AND CHOICE_NO = A.PRIORITY ");
		SQL.append("     ) ");
		SQL.append("     PRIORITY, ");
		SQL.append("     A.APPLICATION_NO, ");
		SQL.append("     A.APPLICATION_STATUS, ");
		SQL.append("     P.TH_FIRST_NAME || ' ' || P.TH_LAST_NAME CUS_NAME, ");
		SQL.append("     P.IDNO, ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             MP.PRODUCT_DESC ");
		SQL.append("         FROM ");
		SQL.append("             MS_PRODUCT MP ");
		SQL.append("         WHERE ");
		SQL.append("             MP.PRODUCT_CODE = BC.PRODUCT_ID ");
		SQL.append("     ) ");
		SQL.append("     PRODUCT, ");
		SQL.append("     A.APPLICATION_DATE, ");
		SQL.append("     A.UPDATE_DATE, ");
		SQL.append("     NVL( ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             US.THAI_FIRSTNAME || ' ' || US.THAI_LASTNAME ");
		SQL.append("         FROM ");
		SQL.append("             US_USER_DETAIL US ");
		SQL.append("         WHERE ");
		SQL.append("             US.USER_NAME = WQ.OWNER ");
		SQL.append("     ) ");
		SQL.append("     , 'Central Queue') OWNER, ");
		SQL.append("     WQ.CLAIMED_BY, ");
		SQL.append("     A.APPLICATION_RECORD_ID, ");
		SQL.append("     A.JOB_TYPE, ");
		SQL.append("     WT.ROLE_ID, ");
		SQL.append("     WT.PTID, ");
		SQL.append("     A.ICDC_FLAG ");
		SQL.append(" FROM ");
		SQL.append("     ORIG_APPLICATION A, ");
		SQL.append("     WF_JOBID_MAPPING J, ");
		SQL.append("     ORIG_PERSONAL_INFO P, ");
		SQL.append("     TABLE(BUS_CLASS.GETBUSCLASSBYUSER(?)) BU, ");
		SQL.append("     BUSINESS_CLASS BC, ");
		SQL.append("     US_USER_DETAIL U, ");
		SQL.append("     WF_WORK_QUEUE WQ, ");
		SQL.append("     WF_INSTANT I, ");
		SQL.append("     WF_ACTIVITY_TEMPLATE WT ");
		SQL.append(" WHERE ");
		SQL.append("     A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
		SQL.append(" AND A.BUSINESS_CLASS_ID = BU.BUS_CLASS_ID ");
		SQL.append(" AND J.JOB_STATUS = 'ACTIVE' ");
		SQL.append(" AND A.BLOCK_FLAG IS NULL ");
		SQL.append(" AND A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
		SQL.append(" AND P.PERSONAL_TYPE = ? ");
		SQL.append(" AND A.BUSINESS_CLASS_ID = BC.BUS_CLASS_ID ");
		SQL.append(" AND J.JOB_ID = WQ.JOB_ID ");
		SQL.append(" AND U.USER_NAME = WQ.OWNER(+) ");
		SQL.append(" AND J.JOB_ID = I.JOB_ID ");
		SQL.append(" AND WQ.ATID = WT.ATID ");

		valueListM.setString(++index, userM.getUserName());
		valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
		
		if(!OrigUtil.isEmptyString(searchDataM.getRole())){
			SQL.append("AND PKA_APP_KLOP.F_CHECK_STATIONBYUSERNAME(WQ.ATID,?,?,A.BUSINESS_CLASS_ID) = 1 ");
			valueListM.setString(++index,WebActionUtil.getJobState(searchDataM.getRole()));
			valueListM.setString(++index,userM.getUserName());
		}else{
			SQL.append(" AND ( ");
				int i = 0;
				ORIGCacheUtil origCache = ORIGCacheUtil.getInstance();
				Vector<ORIGCacheDataM> vRole = origCache.getRoleReAssign();
				for(ORIGCacheDataM param:vRole){
					if(i != 0){
						SQL.append(" OR ");
					}
					SQL.append(" PKA_APP_KLOP.F_CHECK_STATIONBYUSERNAME(WQ.ATID,?,?,A.BUSINESS_CLASS_ID) = 1 ");
					valueListM.setString(++index,WebActionUtil.getJobState(param.getCode()));
					valueListM.setString(++index,userM.getUserName());
					i++;
				}
			SQL.append(" ) ");
		}
		
		if(OrigConstant.OverSLA.APPLICATION_TIME.equals(searchDataM.getOverSLA())){
			SQL.append(" AND SUBSTR(WORKFLOW_SEARCH.GETREMAINAPPLICATIONTIME(I.FIELD_01,WT.ACTIVITY_TYPE,I.APPLICATION_DATE,SYSDATE,I.UPDATE_DATE),0,1) = ? ");
			valueListM.setString(++index,OrigConstant.SLA.OVER_TIME);
		}else if(OrigConstant.OverSLA.USER_TIME.equals(searchDataM.getOverSLA())){
			SQL.append(" AND SUBSTR( ");
			SQL.append(" WORKFLOW_SEARCH.GETREMAINUSERTIME(I.FIELD_01,WT.ACTIVITY_TYPE,WT.ROLE_ID,WT.ROLE_ID,I.JOB_ID,SYSDATE,I.UPDATE_DATE) ");
			SQL.append(" ,0,1) = ? ");
			valueListM.setString(++index,OrigConstant.SLA.OVER_TIME);
		}

		if(!OrigUtil.isEmptyString(searchDataM.getCitizenID())){
			SQL.append(" AND P.IDNO = ? ");
			valueListM.setString(++index, searchDataM.getCitizenID());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getCustomerName())){
			SQL.append(" AND P.TH_FIRST_NAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
		}
		if (!OrigUtil.isEmptyString(searchDataM.getCustomerLName())){
			SQL.append(" AND P.TH_LAST_NAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
		}
		if(!OrigUtil.isEmptyString(searchDataM.getApplicationNo())){
			SQL.append(" AND UPPER(A.APPLICATION_NO) = UPPER(?) ");
			valueListM.setString(++index, searchDataM.getApplicationNo());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getPriority())) {
			SQL.append(" AND A.PRIORITY = ? ");
			valueListM.setString(++index, searchDataM.getPriority());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getProductCode())) {
			SQL.append(" AND BC.PRODUCT_ID = ? ");
			valueListM.setString(++index, searchDataM.getProductCode());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getEmpFirstName())) {
			SQL.append(" AND U.THAI_FIRSTNAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getEmpFirstName()).replace("%", "chr(37)")+"%");
		}
		if(!OrigUtil.isEmptyString(searchDataM.getEmpLastName())) {
			SQL.append(" AND U.THAI_LASTNAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getEmpLastName()).replace("%", "chr(37)")+"%");
		}
		if(!OrigUtil.isEmptyString(searchDataM.getEmpId())){
			SQL.append(" AND U.USER_NO = ? ");
			valueListM.setString(++index, searchDataM.getEmpId());
		}

		SQL.append(" ORDER BY NVL(A.PRIORITY, 0) DESC, A.APPLICATION_DATE ASC");
			
		logger.debug("SQL >> "+SQL.toString());
		
		valueListM.setSQL(SQL.toString());		
		
	}
	
	public void getSQLDEFAULT(String role,ValueListM valueListM,SearchHandler.SearchDataM searchDataM,UserDetailM userM,int index){
				
		String param_jobstate = WebActionUtil.getJobState(WebActionUtil.getUnderRole(role));
		
		UtilityDAO utilifyDAO = ObjectDAOFactory.getUtilityDAO();
		ArrayList<String> list = utilifyDAO.getParamByUserID(param_jobstate, userM.getUserName());
		
		StringBuilder SQL = new StringBuilder("");
				
		SQL.append(" SELECT ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             DISPLAY_NAME ");
		SQL.append("         FROM ");
		SQL.append("             LIST_BOX_MASTER ");
		SQL.append("         WHERE ");
		SQL.append("             FIELD_ID = '61' ");
		SQL.append("         AND CHOICE_NO = A.PRIORITY ");
		SQL.append("     ) ");
		SQL.append("     PRIORITY, ");
		SQL.append("     A.APPLICATION_NO, ");
		SQL.append("     A.APPLICATION_STATUS, ");
		SQL.append("     P.TH_FIRST_NAME || ' ' || P.TH_LAST_NAME CUS_NAME, ");
		SQL.append("     P.IDNO, ");
		SQL.append("     ( ");
		SQL.append("         SELECT ");
		SQL.append("             MP.PRODUCT_DESC ");
		SQL.append("         FROM ");
		SQL.append("             MS_PRODUCT MP ");
		SQL.append("         WHERE ");
		SQL.append("             MP.PRODUCT_CODE = BC.PRODUCT_ID ");
		SQL.append("     ) ");
		SQL.append("     PRODUCT, ");
		SQL.append("     A.APPLICATION_DATE, ");
		SQL.append("     A.UPDATE_DATE, ");
		SQL.append("     NVL( ( U.THAI_FIRSTNAME || ' ' || U.THAI_LASTNAME ) , 'Central Queue') OWNER, ");
		SQL.append("     WQ.CLAIMED_BY, ");
		SQL.append("     A.APPLICATION_RECORD_ID, ");
		SQL.append("     A.JOB_TYPE, ");
		SQL.append("     WT.ROLE_ID, ");
		SQL.append("     WT.PTID, ");
		SQL.append("     A.ICDC_FLAG ");
		SQL.append(" FROM ");
		SQL.append("     ORIG_APPLICATION A, ");
		SQL.append("     WF_JOBID_MAPPING J, ");
		SQL.append("     ORIG_PERSONAL_INFO P, ");
		
		SQL.append("     TABLE(BUS_CLASS.GETBUSCLASSBYUSER(?)) BU, ");
		valueListM.setString(++index, userM.getUserName());
				
		SQL.append("     BUSINESS_CLASS BC, ");
		SQL.append("     US_USER_DETAIL U, ");
		SQL.append("     WF_WORK_QUEUE WQ, ");
		SQL.append("     WF_INSTANT I, ");
		SQL.append("     WF_ACTIVITY_TEMPLATE WT ");
		SQL.append(" WHERE ");
		SQL.append("     A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
		SQL.append(" AND A.BUSINESS_CLASS_ID = BU.BUS_CLASS_ID ");
		SQL.append(" AND J.JOB_STATUS = 'ACTIVE' ");
		SQL.append(" AND A.BLOCK_FLAG IS NULL ");
		SQL.append(" AND A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
		
		SQL.append(" AND P.PERSONAL_TYPE = ? ");
		valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
		
		SQL.append(" AND A.BUSINESS_CLASS_ID = BC.BUS_CLASS_ID ");
		SQL.append(" AND J.JOB_ID = WQ.JOB_ID ");
		SQL.append(" AND U.USER_NAME = WQ.OWNER(+) ");
		SQL.append(" AND J.JOB_ID = I.JOB_ID ");
		SQL.append(" AND WQ.ATID = WT.ATID ");
		
		if(null != list && list.size() > 0){			
			SQL.append(" AND WQ.ATID IN ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             SUBSTR(CSV, INSTR(CSV,',',1,LEV) + 1, INSTR(CSV,',',1,LEV+1 )-INSTR(CSV,',',1,LEV)-1) ");
			SQL.append("         FROM ");
			SQL.append("             ( ");
			SQL.append("                 SELECT ");
			SQL.append("                     ','||PARAM_VALUE||',' CSV ");
			SQL.append("                 FROM ");
			SQL.append("                     GENERAL_PARAM GP ");
			SQL.append("                 WHERE ");
			
			SQL.append("                     GP.PARAM_CODE = ? ");
			valueListM.setString(++index, param_jobstate);
					
				SQL.append(" AND BUS_CLASS_ID IN ( ");
				for(int i=0; i<list.size(); i++){
					String param = (String) list.get(i);
					if(i == list.size()-1){
						SQL.append("?");
					}else{
						SQL.append("?,");
					}
					valueListM.setString(++index, param);
				}
				SQL.append(" )");
				
			SQL.append("             ) ");
			SQL.append("             , ");
			SQL.append("             ( ");
			SQL.append("                 SELECT ");
			SQL.append("                     LEVEL LEV ");
			SQL.append("                 FROM ");
			SQL.append("                     DUAL CONNECT BY LEVEL <= 100 ");
			SQL.append("             ) ");
			SQL.append("         WHERE ");
			SQL.append("             LEV <= LENGTH(CSV)-LENGTH(REPLACE(CSV,','))-1 ");
			SQL.append("     ) ");			
		}else{
			 SQL.append(" AND WQ.ATID = 'NOT_FOUND' ");
		}
				
//		#septemwi comment
//		if(OrigConstant.ROLE_DF_SUP.equals(userM.getCurrentRole())){
//			SQL.append(" AND PKA_APP_KLOP.F_CHECK_STATIONBYUSERNAME(WQ.ATID,'JOBSTATE_APPROVE',?,A.BUSINESS_CLASS_ID) = 1 ");
//		}else{
//			SQL.append(" AND PKA_APP_KLOP.F_CHECK_STATIONBYUSERNAME(WQ.ATID,?,?,A.BUSINESS_CLASS_ID) = 1 ");
//			valueListM.setString(++index,param_jobstate);
//		}
//		valueListM.setString(++index,userM.getUserName());
				
		if(OrigConstant.OverSLA.APPLICATION_TIME.equals(searchDataM.getOverSLA())){
			SQL.append(" AND SUBSTR(WORKFLOW_SEARCH.GETREMAINAPPLICATIONTIME(I.FIELD_01,WT.ACTIVITY_TYPE,I.APPLICATION_DATE,SYSDATE,I.UPDATE_DATE),0,1) = ? ");
			valueListM.setString(++index,OrigConstant.SLA.OVER_TIME);
		}else if(OrigConstant.OverSLA.USER_TIME.equals(searchDataM.getOverSLA())){
			SQL.append(" AND SUBSTR( ");
			SQL.append(" WORKFLOW_SEARCH.REMAINUSERTIME(I.FIELD_01,WT.ACTIVITY_TYPE,WT.ROLE_ID,WT.ROLE_ID,I.JOB_ID,SYSDATE,I.UPDATE_DATE) ");
			SQL.append(" ,0,1) = ? ");
			valueListM.setString(++index,OrigConstant.SLA.OVER_TIME);
		}

		if(!OrigUtil.isEmptyString(searchDataM.getCitizenID())){
			SQL.append(" AND P.IDNO = ? ");
			valueListM.setString(++index, searchDataM.getCitizenID());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getCustomerName())){
			SQL.append(" AND P.TH_FIRST_NAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
		}
		if (!OrigUtil.isEmptyString(searchDataM.getCustomerLName())){
			SQL.append(" AND P.TH_LAST_NAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
		}
		if(!OrigUtil.isEmptyString(searchDataM.getApplicationNo())){
			SQL.append(" AND UPPER(A.APPLICATION_NO) = UPPER(?) ");
			valueListM.setString(++index, searchDataM.getApplicationNo());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getPriority())) {
			SQL.append(" AND A.PRIORITY = ? ");
			valueListM.setString(++index, searchDataM.getPriority());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getProductCode())) {
			SQL.append(" AND BC.PRODUCT_ID = ? ");
			valueListM.setString(++index, searchDataM.getProductCode());
		}
		if(!OrigUtil.isEmptyString(searchDataM.getEmpFirstName())) {
			SQL.append(" AND U.THAI_FIRSTNAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getEmpFirstName()).replace("%", "chr(37)")+"%");
		}
		if(!OrigUtil.isEmptyString(searchDataM.getEmpLastName())) {
			SQL.append(" AND U.THAI_LASTNAME LIKE ? ");
			valueListM.setString(++index, (searchDataM.getEmpLastName()).replace("%", "chr(37)")+"%");
		}
		if(!OrigUtil.isEmptyString(searchDataM.getEmpId())){
			SQL.append(" AND U.USER_NO = ? ");
			valueListM.setString(++index, searchDataM.getEmpId());
		}

		SQL.append(" ORDER BY NVL(A.PRIORITY, 0)DESC, A.APPLICATION_DATE ASC");
			
		logger.debug("SQL >> "+SQL.toString());
		
		valueListM.setSQL(SQL.toString());	
		
	}
		
	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter() {
		return nextAction;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}
	
}
