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
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchReworkWebAction extends WebActionHelper implements WebAction {

	static Logger log = Logger.getLogger(SearchReworkWebAction.class);

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
		String productCode = getRequest().getParameter("productCode");
		String projectCode = getRequest().getParameter("projectCode");
		String saleType = getRequest().getParameter("saleType");
		String empFirstName = getRequest().getParameter("empFirstName");
		String empLastName = getRequest().getParameter("empLastName");
		String empId = getRequest().getParameter("empId");

		if (citizen_id != null) {
			citizen_id = ORIGDisplayFormatUtil.lrtrim(citizen_id);
		}
		if (application_no != null) {
			application_no = ORIGDisplayFormatUtil.lrtrim(application_no);
		}
		if (firstname != null) {
			firstname = ORIGDisplayFormatUtil.lrtrim(firstname);
		}
		if (lastname != null) {
			lastname = ORIGDisplayFormatUtil.lrtrim(lastname);
		}
		
		if (!ORIGUtility.isEmptyString(citizen_id)
				|| !ORIGUtility.isEmptyString(firstname)
				|| !ORIGUtility.isEmptyString(lastname)
				|| !ORIGUtility.isEmptyString(application_no)
				|| !ORIGUtility.isEmptyString(priority)
				|| !ORIGUtility.isEmptyString(productCode)
				|| !ORIGUtility.isEmptyString(empFirstName)
				|| !ORIGUtility.isEmptyString(empLastName)
				|| !ORIGUtility.isEmptyString(empId)
				|| !ORIGUtility.isEmptyString(projectCode)
				|| !ORIGUtility.isEmptyString(saleType)) {
			
			searchDataM.setCitizenID(citizen_id);
			searchDataM.setCustomerName(firstname);
			searchDataM.setCustomerLName(lastname);
			searchDataM.setApplicationNo(application_no);
			searchDataM.setPriority(priority);
			searchDataM.setProductCode(productCode);
			searchDataM.setProjectCode(projectCode);
			searchDataM.setSaleType(saleType);
			searchDataM.setEmpFirstName(empFirstName);
			searchDataM.setEmpLastName(empLastName);
			searchDataM.setEmpId(empId);
		}
		handler.setSearchM(searchDataM);
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);

		try {
			StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			sql.append("SELECT ( SELECT DISPLAY_NAME FROM LIST_BOX_MASTER WHERE FIELD_ID = '61' AND CHOICE_NO = APP.PRIORITY ) PRIORITY, ");
			sql.append("APP.APPLICATION_NO, APP.APPLICATION_STATUS, PI.TH_FIRST_NAME || ' ' || PI.TH_LAST_NAME NAME, PI.IDNO, ");
			sql.append("(SELECT MP.PRODUCT_DESC FROM MS_PRODUCT MP WHERE MP.PRODUCT_CODE = BC.PRODUCT_ID) PRODUCT, APP.APPLICATION_DATE, ");
			sql.append("APP.UPDATE_DATE, APP.CA_LAST_ID, WQ.CLAIMED_BY, APP.APPLICATION_RECORD_ID ");
			sql.append("FROM ORIG_APPLICATION APP, ORIG_PERSONAL_INFO PI, WF_JOBID_MAPPING JM, ");
			sql.append("WF_WORK_QUEUE WQ, BUSINESS_CLASS BC, AC_ACCOUNT AC, US_USER_DETAIL US ");
			sql.append("WHERE JM.JOB_STATUS = 'ACTIVE' AND APP.APPLICATION_RECORD_ID = PI.APPLICATION_RECORD_ID(+) ");
			sql.append("AND NVL(PI.PERSONAL_TYPE, ?) = ? AND JM.APPLICATION_RECORD_ID = APP.APPLICATION_RECORD_ID AND WQ.JOB_ID = JM.JOB_ID ");  //A,A
			sql.append("AND BC.BUS_CLASS_ID = APP.BUSINESS_CLASS_ID AND APP.CA_LAST_ID = US.USER_NAME(+) ");
			sql.append("AND (AC.CARDLINK_STATUS = '1' or AC.CARDLINK_STATUS is null) AND APP.APPLICATION_NO = AC.APPLICATION_NO "); //N,Y
			sql.append("AND PKA_APP_KLOP.f_check_stationByUserName(APP.JOB_STATE , '"+OrigConstant.generalParam_JobState.REWORKABLE+"', ?, APP.BUSINESS_CLASS_ID) = 1 "); //userName
			
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			
			valueListM.setString(++index, userM.getUserName());

			if (!ORIGUtility.isEmptyString(searchDataM.getCitizenID())) {
				sql.append(" AND PI.IDNO = ? ");
				valueListM.setString(++index, searchDataM.getCitizenID());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getCustomerName())) {
				sql.append(" AND PI.TH_FIRST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getCustomerLName())) {
				sql.append(" AND PI.TH_LAST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getApplicationNo())) {
				sql.append(" AND UPPER(APP.APPLICATION_NO) = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getApplicationNo());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getProductCode())) {
				sql.append(" AND BC.PRODUCT_ID = ? ");
				valueListM.setString(++index, searchDataM.getProductCode());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getSaleType())) {
				sql.append(" AND BC.CHANNEL_ID = ? ");
				valueListM.setString(++index, searchDataM.getSaleType());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getPriority())) {
				sql.append(" AND APP.PRIORITY = ? ");
				valueListM.setString(++index, searchDataM.getPriority());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getProductCode())) {
				sql.append(" AND APP.PROJECT_CODE = ? ");
				valueListM.setString(++index, searchDataM.getProductCode());
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getEmpFirstName())) {
				sql.append(" AND US.THAI_FIRSTNAME LIKE ? ");
				valueListM.setString(++index, searchDataM.getEmpFirstName().replace("%", "chr(37)")+"%");
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getEmpLastName())) {
				sql.append(" AND US.THAI_LASTNAME LIKE ? ");
				valueListM.setString(++index, searchDataM.getEmpLastName().replace("%", "chr(37)")+"%");
			}
			if (!ORIGUtility.isEmptyString(searchDataM.getEmpId())) {
				sql.append(" AND US.USER_NO = ? ");
				valueListM.setString(++index, searchDataM.getEmpId());
			}
			
			sql.append("ORDER BY NVL(APP.PRIORITY, 0) DESC, APP.APPLICATION_DATE ASC");

			log.debug("String.valueOf(sql) = " + String.valueOf(sql));
			valueListM.setSQL(String.valueOf(sql));
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(10);
			valueListM.setReturnToAction("page=REWORK_SCREEN");
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
