package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.WebActionUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchSetPriorityWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(SearchSetPriorityWebAction.class);

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

	@Override
	public boolean preModelRequest() {

		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		SearchHandler handler = (SearchHandler)getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(handler==null){
			handler = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = handler.getSearchM();
		
		if (searchDataM == null) {
			searchDataM = new SearchHandler.SearchDataM();
		}
		String citizen_id = getRequest().getParameter("citizen_id");
		String firstname = getRequest().getParameter("firstname");
		String lastname = getRequest().getParameter("lastname");
		String application_no = getRequest().getParameter("application_no");
		String priority = getRequest().getParameter("priority");
		String productCode = getRequest().getParameter("product");
		String empFirstName = getRequest().getParameter("empFirstName");
		String empLastName = getRequest().getParameter("empLastName");
		String empId = getRequest().getParameter("empId");
		String projectCode = getRequest().getParameter("project_code");
		String searchType = getRequest().getParameter("searchType");
		
		
		String clickSearch = getRequest().getParameter("clickSearch");
		if(clickSearch!=null && clickSearch.equals(OrigConstant.FLAG_Y)){
			getRequest().getSession().removeAttribute("EventResponse");
			handler.setErrorFields(null);
		}

		if(!OrigUtil.isEmptyString(searchType) && searchType.equals(OrigConstant.FLAG_Y)){
			searchDataM.setCitizenID(citizen_id);
			searchDataM.setCustomerName(firstname);
			searchDataM.setCustomerLName(lastname);
			searchDataM.setApplicationNo(application_no);
			searchDataM.setPriority(priority);
			searchDataM.setProductCode(productCode);
			searchDataM.setEmpFirstName(empFirstName);
			searchDataM.setEmpLastName(empLastName);
			searchDataM.setEmpId(empId);
			searchDataM.setProjectCode(projectCode);
		}
		handler.setSearchM(searchDataM);
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);
	    
	    
		try {
			StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			String searchRole = WebActionUtil.getUnderRole(userM.getCurrentRole());
			String jobState = WebActionUtil.getJobState(searchRole);

			sql.append("SELECT ( SELECT DISPLAY_NAME FROM LIST_BOX_MASTER WHERE FIELD_ID='61' AND CHOICE_NO=APP.PRIORITY ) PRIORITY, ");
			sql.append("APP.APPLICATION_NO, APP.APPLICATION_STATUS, PI.TH_FIRST_NAME || ' ' || PI.TH_LAST_NAME NAME, PI.IDNO, ");
			sql.append("( SELECT MP.PRODUCT_DESC FROM MS_PRODUCT MP WHERE MP.PRODUCT_CODE = BC.PRODUCT_ID ) PRODUCT, ");
			sql.append("APP.APPLICATION_DATE, APP.UPDATE_DATE, NVL(US.THAI_FIRSTNAME || US.THAI_LASTNAME ,'Central Queue') OWNER, WQ.CLAIMED_BY, APP.APPLICATION_RECORD_ID ");
			sql.append("FROM ORIG_APPLICATION APP, ORIG_PERSONAL_INFO PI, US_USER_DETAIL US, ");
			sql.append("WF_JOBID_MAPPING JM, WF_WORK_QUEUE WQ, BUSINESS_CLASS BC ");
			sql.append("WHERE APP.APPLICATION_RECORD_ID = JM.APPLICATION_RECORD_ID AND APP.APPLICATION_RECORD_ID = PI.APPLICATION_RECORD_ID(+) ");
			sql.append("AND JM.JOB_ID = WQ.JOB_ID AND APP.BLOCK_FLAG IS NULL AND NVL(PI.PERSONAL_TYPE, ?) = ? ");  //A, A
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			sql.append("AND WQ.OWNER = US.USER_NAME(+) AND WQ.JOB_ID = JM.JOB_ID AND BC.BUS_CLASS_ID = APP.BUSINESS_CLASS_ID ");
			sql.append("AND PKA_APP_KLOP.f_check_stationByUserName(WQ.ATID , '"+jobState+"', ?, APP.BUSINESS_CLASS_ID) = 1 ");
			valueListM.setString(++index, userM.getUserName());
			sql.append("AND NVL(US.ACTIVE_STATUS, ?) = ?"); //A
			valueListM.setString(++index, OrigConstant.Status.STATUS_ACTIVE);
			valueListM.setString(++index, OrigConstant.Status.STATUS_ACTIVE);
			
			if (!ORIGUtility.isEmptyString(searchDataM.getCitizenID())) {
				sql.append(" AND PI.IDNO = ? ");
				valueListM.setString(++index, searchDataM.getCitizenID());
			}
			if (!OrigUtil.isEmptyString(searchDataM.getCustomerName())) {
				sql.append(" AND PI.TH_FIRST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
			}
			if (!OrigUtil.isEmptyString(searchDataM.getCustomerLName())) {
				sql.append(" AND PI.TH_LAST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getApplicationNo())) {
				sql.append(" AND UPPER(APP.APPLICATION_NO) = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getApplicationNo());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getPriority())) {
				sql.append(" AND APP.PRIORITY = ? ");
				valueListM.setString(++index, searchDataM.getPriority());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getProductCode())) {
				sql.append(" AND BC.PRODUCT_ID = ? ");
				valueListM.setString(++index, searchDataM.getProductCode());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getProjectCode())) {
				sql.append(" AND APP.PROJECT_CODE = ? ");
				valueListM.setString(++index, searchDataM.getProjectCode());
			}
			if(!ORIGUtility.isEmptyString(searchDataM.getEmpFirstName())){
        		sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
            	valueListM.setString(++index,(searchDataM.getEmpFirstName()).replace("%", "chr(37)")+"%");
            }
            if(!ORIGUtility.isEmptyString(searchDataM.getEmpLastName())){
        		sql.append(" AND US.THAI_LASTNAME LIKE ? ");
            	valueListM.setString(++index,(searchDataM.getEmpLastName()).replace("%", "chr(37)")+"%");
            }
            if(!ORIGUtility.isEmptyString(searchDataM.getEmpId())){
        		sql.append(" AND US.USER_NAME = ? ");
            	valueListM.setString(++index,searchDataM.getEmpId());
            }

			sql.append(" ORDER BY NVL(APP.PRIORITY,0) DESC , APP.APPLICATION_DATE ASC");
			
			log.debug("String.valueOf(sql) = " + String.valueOf(sql));
			valueListM.setSQL(String.valueOf(sql));
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(10);
			valueListM.setReturnToAction("page=SET_PRIORITY_SCREEN");
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			nextAction = "action=ValueListWebAction";
		} catch (Exception e) {
			log.error("exception ", e);
		}
		return true;
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
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
