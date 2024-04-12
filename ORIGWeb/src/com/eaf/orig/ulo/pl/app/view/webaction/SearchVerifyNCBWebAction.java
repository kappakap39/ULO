package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchVerifyNCBWebAction extends WebActionHelper implements WebAction {

	static Logger log = Logger.getLogger(SearchVerifyNCBWebAction.class);
	
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
		if(handler == null){
			handler = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = handler.getSearchM();
		
		if(searchDataM == null){
			searchDataM = new SearchHandler.SearchDataM();
			handler.setSearchM(searchDataM);
		}
		String appNo = getRequest().getParameter("application_no");
		String IDNO =  getRequest().getParameter("citizen_id");
		String firstName =  getRequest().getParameter("firstname");
		String lastName =  getRequest().getParameter("lastname");
		String searchType =  getRequest().getParameter("searchType");
		
		if(OrigConstant.FLAG_Y.equalsIgnoreCase(searchType)){
			searchDataM.setCitizenID(IDNO);
			searchDataM.setCustomerName(firstName);
			searchDataM.setCustomerLName(lastName);
			searchDataM.setApplicationNo(appNo);
		}
				
		handler.setSearchM(searchDataM);
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);
		
		try{
			
			StringBuilder SQL = new StringBuilder("");
			ValueListM valueListM = new ValueListM();
			
			
			SQL.append(" SELECT ");
			SQL.append("     A.APPLICATION_RECORD_ID, ");
			SQL.append("     H.CREATE_DATE, ");
			SQL.append("     A.PRIORITY, ");
			SQL.append("     A.APPLICATION_NO, ");
			SQL.append("     P.TH_FIRST_NAME, ");
			SQL.append("     P.TH_LAST_NAME, ");
			SQL.append("     P.IDNO, ");
			SQL.append("     P.BIRTH_DATE, ");
			SQL.append("     V.NCB_REQUESTER, ");
			SQL.append("     V.NCB_RQ_APPROVER, ");
			SQL.append("     V.NCB_SUP_COMMENT ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION A,  ");
			SQL.append("     ORIG_PERSONAL_INFO P, ");
			SQL.append("     XRULES_VERIFICATION_RESULT V,       ");
			SQL.append("     WF_JOBID_MAPPING J, ");
			SQL.append("     WF_INSTANT_HISTORY H ");
			SQL.append(" WHERE ");
			SQL.append("        A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID     ");
			SQL.append(" AND A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND P.APPLICATION_RECORD_ID  = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND P.PERSONAL_TYPE = ? ");
			SQL.append(" AND J.JOB_STATUS = 'ACTIVE' ");
			SQL.append(" AND P.PERSONAL_ID = V.PERSONAL_ID(+) ");
			SQL.append(" AND J.JOB_ID = H.JOB_ID ");
			SQL.append(" AND H.SEQ = ");
			SQL.append("     (SELECT MIN(SEQ)   ");
			SQL.append("         FROM ");
			SQL.append("             WF_INSTANT_HISTORY TMP_WIH , ");
			SQL.append("             WF_ACTIVITY_TEMPLATE TMP_WAT ");
			SQL.append("         WHERE TMP_WIH.JOB_ID = J.JOB_ID  ");
			SQL.append("         AND TMP_WIH.ATID = TMP_WAT.ATID ");
			SQL.append("         AND TMP_WAT.ROLE_ID = 'CB' ");
			SQL.append("     ) ");
			SQL.append(" AND PKA_APP_KLOP.F_CHECK_STATIONBYUSERNAME(A.JOB_STATE , 'JOBSTATE_CB_SUP', ? , A.BUSINESS_CLASS_ID) = 1  ");
			
			int index = 0;
			
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, userM.getUserName());
			
			if (!OrigUtil.isEmptyString(searchDataM.getCustomerName())) {
				SQL.append(" AND P.TH_FIRST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
			}
			if (!OrigUtil.isEmptyString(searchDataM.getCustomerLName())) {
				SQL.append(" AND P.TH_LAST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
			}
			if (!OrigUtil.isEmptyString(searchDataM.getApplicationNo())) {
				SQL.append(" AND UPPER(A.APPLICATION_NO) = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getApplicationNo());
			}
			if (!OrigUtil.isEmptyString(searchDataM.getCitizenID())) {
				SQL.append(" AND P.IDNO = ? ");
				valueListM.setString(++index, searchDataM.getCitizenID());
			}

			SQL.append(" ORDER BY NVL(A.PRIORITY,0) DESC, A.APPLICATION_DATE ASC");
			
			log.debug(" SQL = "+SQL);
			valueListM.setSQL(SQL.toString());
			
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setReturnToAction("page=VERIFY_NCB_SCREEN");
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			nextAction = "action=ValueListWebAction";
			
		}catch(Exception e){
//			log.error("exception ", e);
//			SearchHandler.PushErrorMessage(getRequest(), ErrorUtil.getShortErrorMessage(getRequest(),"INTERNAL_SERVER_ERROR"));
			String MSG = Message.error(e);
			SearchHandler.PushErrorMessage(getRequest(), MSG);
			return false;
		}		
		return true;
	}

	@Override
	public int getNextActivityType() {
		return FrontController.ACTION;
	}

	@Override
	public String getNextActionParameter(){
		return nextAction;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
