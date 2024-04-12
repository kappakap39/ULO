package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.util.OrigUtil;

public class SearchCBConsentWebAction extends WebActionHelper implements WebAction {
	
	static Logger log = Logger.getLogger(SearchCBConsentWebAction.class);
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
		
		String dateFrom = getRequest().getParameter("dateFrom");
		String dateTo = getRequest().getParameter("dateTo");
		
		getRequest().getSession().setAttribute("FIRST_SEARCH_dateFrom", dateFrom);
		getRequest().getSession().setAttribute("FIRST_SEARCH_dateTo", dateTo);
		
		if(!OrigUtil.isEmptyString(dateFrom)){
			int year = Integer.parseInt(dateFrom.substring(6, 10));
			dateFrom = dateFrom.substring(0,6)+String.valueOf(year- 543);
		}
		
		if(!OrigUtil.isEmptyString(dateTo)){
			int year = Integer.parseInt(dateTo.substring(6, 10));
			dateTo = dateTo.substring(0,6)+String.valueOf(year- 543);
		}
		
		try{
			StringBuilder SQL = new StringBuilder();
			
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			SQL.append("  SELECT ");
			SQL.append("     TO_CHAR(X.NCB_CONSENT_REF_NO_DATE,'yyyy-mm-dd hh24:mi:ss') CONSENT_DATE, ");
			SQL.append("     A.APPLICATION_NO, ");
			SQL.append("     P.TH_FIRST_NAME, ");
			SQL.append("     P.TH_LAST_NAME, ");
			SQL.append("     P.IDNO, ");
			SQL.append("     TO_CHAR(P.BIRTH_DATE,'yyyy-mm-dd') BIRTHDATE, ");
			SQL.append("     X.NCB_REQUESTER, ");
			SQL.append("     X.NCB_RQ_APPROVER, ");
			SQL.append("     X.NCB_CONSENT_REF_NO, ");
			SQL.append("     N.CB_IN_QUEUE_DATE, ");
			SQL.append("     A.APPLICATION_RECORD_ID , ");
			SQL.append("     A.SALE_TYPE SALE_TYPE ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION A, ");
			SQL.append("     ORIG_PERSONAL_INFO P, ");
			SQL.append("     XRULES_VERIFICATION_RESULT X, ");
			SQL.append("     NCB_REQ_RESP N ");
			SQL.append(" WHERE ");
			SQL.append("     A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND P.PERSONAL_ID = X.PERSONAL_ID ");
			SQL.append(" AND NVL(P.PERSONAL_TYPE,?) = ? ");
			SQL.append(" AND TRUNC(X.NCB_CONSENT_REF_NO_DATE) BETWEEN TRUNC(TO_DATE(?,'dd-mm-yyyy') ) AND TRUNC(TO_DATE(?,'dd-mm-yyyy')) ");
			SQL.append(" AND A.APPLICATION_NO = N.APPLICATION_NO ");
			SQL.append(" AND X.NCB_CONSENT_REF_NO IS NOT NULL ");
			SQL.append(" AND X.NCB_TRACKING_CODE = N.TRACKING_CODE ");
			
			SQL.append(" AND NOT EXISTS ( ");
			SQL.append(" SELECT 1 FROM BUS_CLASS_GRP_MAP B WHERE B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID AND B.BUS_GRP_ID = ? ");
			SQL.append(" ) ");
			
			SQL.append(" ORDER BY ");
			SQL.append("     X.NCB_CONSENT_REF_NO_DATE ");
			
			
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, dateFrom);
			valueListM.setString(++index, dateTo);
			valueListM.setString(++index, OrigConstant.BusGroup.KEC_CC_FLOW);

			log.debug("sql >> "+SQL);
			
			valueListM.setSQL(SQL.toString());
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setReturnToAction("page=CONSENT_ENQUIRY_SCREEN");
			
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			
			nextAction = "action=ValueListWebAction";

		}catch(Exception e){
			log.error("exception ", e);
			return false;
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
	public boolean getCSRFToken(){
		return false;
	}

}

