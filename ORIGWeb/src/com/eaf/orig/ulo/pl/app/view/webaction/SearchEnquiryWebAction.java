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
import com.eaf.orig.ulo.pl.formcontrol.view.form.SearchHandler;

public class SearchEnquiryWebAction extends WebActionHelper implements WebAction {

	static Logger log = Logger.getLogger(SearchEnquiryWebAction.class);

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
		
		String role = getRequest().getParameter("roleElement");
		String idno = getRequest().getParameter("IDNO");
		String firstName = getRequest().getParameter("firstName");
		String lastName = getRequest().getParameter("lastName");
		String appNo = getRequest().getParameter("appNo");
		String priority = getRequest().getParameter("priority");
		String refNo = getRequest().getParameter("refNo");
		String product = getRequest().getParameter("product");
		String saleType = getRequest().getParameter("saleType");
		String searchType = getRequest().getParameter("searchType");
		
		SearchHandler handler = (SearchHandler)getRequest().getSession().getAttribute("SEARCH_DATAM");
		if(handler==null){
			handler = new SearchHandler();
		}
		SearchHandler.SearchDataM searchDataM = handler.getSearchM();
		
		if (searchDataM == null) {
			searchDataM = new SearchHandler.SearchDataM();
		}
		
		if(!OrigUtil.isEmptyString(searchType) && searchType.equals(OrigConstant.FLAG_Y)){
			
			searchDataM.setCitizenID(idno);
			searchDataM.setCustomerName(firstName);
			searchDataM.setCustomerLName(lastName);
			searchDataM.setApplicationNo(appNo);
			searchDataM.setPriority(priority);
			searchDataM.setProductCode(product);
			searchDataM.setSaleType(saleType);
			searchDataM.setRefNo(refNo);
			
		}
		
		handler.setSearchM(searchDataM);
		getRequest().getSession().setAttribute("SEARCH_DATAM", handler);

		try{
			StringBuilder SQL = new StringBuilder("");
			ValueListM valueListM = new ValueListM();
			int index = 0;

			SQL.append(" SELECT ");
			SQL.append("    (SELECT DISPLAY_NAME  FROM LIST_BOX_MASTER WHERE FIELD_ID = '61' AND CHOICE_NO = APP.PRIORITY ) PRIORITY,  ");
			SQL.append("     APP.APPLICATION_NO, ");
			SQL.append("     APP.REF_NO, ");
			SQL.append("     APP.APPLICATION_STATUS, ");
			SQL.append("     PI.TH_FIRST_NAME || ' ' || PI.TH_LAST_NAME NAME, ");
			SQL.append("     PI.IDNO, ");
			SQL.append("    (SELECT  MP.PRODUCT_DESC FROM  MS_PRODUCT MP WHERE MP.PRODUCT_CODE = BC.PRODUCT_ID) PRODUCT,");
			SQL.append("    (SELECT  SALE_TYPE_DESC  FROM MS_SALES_TYPE STD WHERE STD.SALE_TYPE_ID = APP.SALE_TYPE )  SALE_TYPE, ");
			SQL.append("     APP.APPLICATION_DATE, ");
			SQL.append("     APP.UPDATE_DATE, ");
			SQL.append("     APP.UPDATE_BY, ");
			SQL.append("     APP.APPLICATION_RECORD_ID, ");
			SQL.append("     WQ.OWNER OWNER ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION APP, ");
			SQL.append("     ORIG_PERSONAL_INFO PI, ");
			SQL.append("     BUSINESS_CLASS BC, ");
			SQL.append("     WF_WORK_QUEUE WQ, ");
			SQL.append("     WF_JOBID_MAPPING JM ");
			SQL.append(" WHERE ");
			SQL.append("     APP.APPLICATION_RECORD_ID = PI.APPLICATION_RECORD_ID(+) ");
			SQL.append(" AND NVL(PI.PERSONAL_TYPE,?) = ? ");
			SQL.append(" AND BC.BUS_CLASS_ID = APP.BUSINESS_CLASS_ID ");
			SQL.append(" AND APP.APPLICATION_RECORD_ID =JM.APPLICATION_RECORD_ID(+) ");
			SQL.append(" AND JM.JOB_ID = WQ.JOB_ID(+) ");
			SQL.append(" AND JM.JOB_VERSION = ( SELECT MAX(TJ.JOB_VERSION) FROM WF_JOBID_MAPPING TJ  ");
			SQL.append("                          WHERE  TJ.APPLICATION_RECORD_ID = APP.APPLICATION_RECORD_ID  ");
			SQL.append("                       ) ");
			
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			
			if (!OrigUtil.isEmptyString(searchDataM.getCitizenID())) {
				SQL.append(" AND PI.IDNO = ? ");
				valueListM.setString(++index, searchDataM.getCitizenID());
			}
			if (!OrigUtil.isEmptyString(searchDataM.getCustomerName())) {
				SQL.append(" AND PI.TH_FIRST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerName()).replace("%", "chr(37)")+"%");
			}
			if (!OrigUtil.isEmptyString(searchDataM.getCustomerLName())) {
				SQL.append(" AND PI.TH_LAST_NAME LIKE ? ");
				valueListM.setString(++index, (searchDataM.getCustomerLName()).replace("%", "chr(37)")+"%");
			}
			if (!OrigUtil.isEmptyString(searchDataM.getApplicationNo())) {
				SQL.append(" AND UPPER(APP.APPLICATION_NO) = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getApplicationNo());
			}
			if (!OrigUtil.isEmptyString(searchDataM.getPriority())) {
				SQL.append(" AND APP.PRIORITY = ? ");
				valueListM.setString(++index, searchDataM.getPriority());
			}
			if (!OrigUtil.isEmptyString(searchDataM.getRefNo())) {
				SQL.append(" AND UPPER(APP.REF_NO) = UPPER(?) ");
				valueListM.setString(++index, searchDataM.getRefNo());
			}
			if (!OrigUtil.isEmptyString(searchDataM.getProductCode())) {
				SQL.append(" AND BC.PRODUCT_ID = ? ");
				valueListM.setString(++index, searchDataM.getProductCode());
			}
			if (!OrigUtil.isEmptyString(searchDataM.getSaleType())) {
				SQL.append(" AND APP.SALE_TYPE = ? ");
				valueListM.setString(++index, searchDataM.getSaleType());
			}
			
			SQL.append(" ORDER BY APP.APPLICATION_NO ");

			log.debug("SQL = "+SQL);
			
			valueListM.setSQL(String.valueOf(SQL));
			valueListM.setNextPage(false);
			valueListM.setItemsPerPage(20);
			valueListM.setReturnToAction("page=ENQUIRY_DATA&role="+role);
			getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
			nextAction = "action=ValueListWebAction";

		}catch(Exception e){
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
	public boolean getCSRFToken(){
		return false;
	}

}
