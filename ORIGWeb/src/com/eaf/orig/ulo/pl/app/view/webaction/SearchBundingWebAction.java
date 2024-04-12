package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchBundingWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SearchBundingWebAction.class);
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
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){
		
		SearchHandler HandlerM = (SearchHandler) getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(HandlerM == null){
			HandlerM = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = HandlerM.getSearchM();
		if(searchDataM == null){
			searchDataM = new SearchHandler.SearchDataM();
			HandlerM.setSearchM(searchDataM);
		}

		String searchType = (String) getRequest().getSession().getAttribute("searchType");
		
		logger.debug("searchType = " + searchType);
		
		if(OrigUtil.isEmptyString(searchType)){
			searchType = getRequest().getParameter("searchType");
		}
		
		String clickSearch = getRequest().getParameter("clickSearch");
		
		logger.debug("clickSearch = " + clickSearch);
		
		if(("Y").equals(clickSearch)){
			searchDataM = null;
		}
		
		String citizen_id;
		String application_no;
        String refNo; 
        
		if(null == searchDataM){
			citizen_id = getRequest().getParameter("citizen_id");
			refNo = (String)getRequest().getParameter("refNo");
			application_no = getRequest().getParameter("application_no");
			
			logger.debug("citizen_id = " + citizen_id);
			logger.debug("application_no = " + application_no);
			logger.debug("refNo = " + refNo);
			
			if(!OrigUtil.isEmptyString(citizen_id)){
				citizen_id = ORIGDisplayFormatUtil.lrtrim(citizen_id);
			}
			if(!OrigUtil.isEmptyString(application_no)){
				application_no = ORIGDisplayFormatUtil.lrtrim(application_no);
			}
			if(!OrigUtil.isEmptyString(refNo)){
				refNo = ORIGDisplayFormatUtil.lrtrim(refNo);
			}
			searchDataM = new SearchHandler.SearchDataM();
			
			searchDataM.setCitizenID(citizen_id);

			searchDataM.setApplicationNo(application_no);
			searchDataM.setRefNo(refNo);
			getRequest().getSession().setAttribute("SEARCH_DATAM",HandlerM);
		}else{
			citizen_id = searchDataM.getCitizenID();
			application_no = searchDataM.getApplicationNo();
			refNo = searchDataM.getRefNo();
		}

		try{
			StringBuilder SQL = new StringBuilder();
			
			ValueListM valueListM = new ValueListM();
			int index = 0;

			SQL.append(" SELECT ");
			SQL.append("     A.APPLICATION_RECORD_ID APPLICATION_RECORD_ID, ");
			SQL.append("     ( ");
			SQL.append("         SELECT DISTINCT ");
			SQL.append("             SALE_TYPE_DESC ");
			SQL.append("         FROM ");
			SQL.append("             MS_SALES_TYPE ");
			SQL.append("         WHERE ");
			SQL.append("             MS_SALES_TYPE.SALE_TYPE_ID = A.SALE_TYPE ");
			SQL.append("         AND ROWNUM = 1 ");
			SQL.append("     ) ");
			SQL.append("     SALE_TYPE, ");
			SQL.append("     A.PRIORITY, ");
			SQL.append("     A.APPLICATION_NO, ");
			SQL.append("     A.APPLICATION_STATUS , ");
			SQL.append("     P.TH_FIRST_NAME||' '||P.TH_LAST_NAME TH_NAME, ");
			SQL.append("     P.IDNO IDNO, ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             PRODUCT_DESC ");
			SQL.append("         FROM ");
			SQL.append("             MS_PRODUCT ");
			SQL.append("         WHERE ");
			SQL.append("             PRODUCT_CODE IN ");
			SQL.append("             ( ");
			SQL.append("                 SELECT ");
			SQL.append("                     PRODUCT_ID ");
			SQL.append("                 FROM ");
			SQL.append("                     BUSINESS_CLASS ");
			SQL.append("                 WHERE ");
			SQL.append("                     BUS_CLASS_ID = A.BUSINESS_CLASS_ID ");
			SQL.append("             ) ");
			SQL.append("         AND ROWNUM = 1 ");
			SQL.append("     ) ");
			SQL.append("     PRODUCT_DESC, ");
			SQL.append("     TO_CHAR(A.APPLICATION_DATE,'DD/MM/')|| TO_CHAR((TO_NUMBER(TO_CHAR(A.APPLICATION_DATE,'YYYY')))+543) || ' '||TO_CHAR(A.APPLICATION_DATE ,'HH24:MI:SS') DATETH , ");
			SQL.append("     OB.CREDIT_CARD_APP_SCORE, ");
			SQL.append("     OB.CREDIT_CARD_RESULT ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION A, ");
			SQL.append("     ORIG_PERSONAL_INFO P, ");
			SQL.append("     ORIG_BUNDLE_CC OB, ");
			SQL.append("     WF_JOBID_MAPPING J ");
			SQL.append(" WHERE ");
			SQL.append("     A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID(+) ");
			SQL.append(" AND A.APPLICATION_RECORD_ID = OB.APPLICATION_RECORD_ID(+) ");
			SQL.append(" AND A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND J.JOB_STATUS = 'ACTIVE' ");
			SQL.append(" AND P.PERSONAL_TYPE = 'A' ");		
			SQL.append(" AND A.JOB_STATE = ? ");
			valueListM.setString(++index, WorkflowConstant.JobState.DC_BUNDLE_CQ);			
			
			if (!OrigUtil.isEmptyString(citizen_id)) {
				SQL.append(" AND P.IDNO = ? ");
				valueListM.setString(++index, citizen_id);
			}
			 if(!OrigUtil.isEmptyString(refNo)){
				SQL.append(" AND A.REF_NO = UPPER(?) ");
 		 		valueListM.setString(++index, refNo);
            }
			if (!OrigUtil.isEmptyString(application_no)) {
				SQL.append(" AND A.APPLICATION_NO = UPPER(?) ");
				valueListM.setString(++index, application_no);
			}
			
			SQL.append(" ORDER BY A.PRIORITY DESC , A.APPLICATION_DATE ");
			
			logger.debug("SQL >> "+SQL);
			
			valueListM.setSQL(String.valueOf(SQL));
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setReturnToAction("page=DC_PL_SEARCH_BUNDLE_SCREEN");
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			
			nextAction = "action=ValueListWebAction";
			
		}catch(Exception e){
			logger.error("exception ", e);
		}
		return true;
	}

	@Override
	public int getNextActivityType(){
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
