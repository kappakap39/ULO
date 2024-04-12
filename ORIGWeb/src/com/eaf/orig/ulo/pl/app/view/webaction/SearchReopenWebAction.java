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
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM;

public class SearchReopenWebAction extends WebActionHelper implements WebAction {
	
    private Logger log = Logger.getLogger(SearchReopenWebAction.class);
    private String nextAction = null;
    
    public Event toEvent() {
        return null;
    }

    public boolean requiredModelRequest(){
        return false;
    }

    public boolean processEventResponse(EventResponse response){
        return false;
    }

    public boolean preModelRequest() {
        PLSearchDataM searchReopenDataM = (PLSearchDataM) getRequest().getSession().getAttribute("SEARCH_REOPEN_DATAM");
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        
        String fromSearch = getRequest().getParameter("fromSearch");
        if (("Y").equals(fromSearch)) {
        	searchReopenDataM = null;
        }else if (searchReopenDataM == null){
        	nextAction = "page=SEARCH_REOPEN_SCREEN";
        	return true;
        }
        String applicationNo;
    	String referenceNo;
    	String product;
    	String thaiFirstName;
    	String thaiLastName;
    	String citizenId;
    	String saleType;
    	String priority;

        if (searchReopenDataM == null) {

        	applicationNo 	=  getRequest().getParameter("application_no");
        	referenceNo = getRequest().getParameter("reference_no");
        	product = getRequest().getParameter("product");
        	thaiFirstName = getRequest().getParameter("th_firstname");
        	thaiLastName = getRequest().getParameter("th_lastname");
        	citizenId 	= getRequest().getParameter("citizen_id");
        	saleType = getRequest().getParameter("saleType");
        	priority 	= getRequest().getParameter("priority");

            //trim Space
            if (applicationNo != null) {
            	applicationNo = applicationNo.trim();
            }
            if (referenceNo != null) {
            	referenceNo = referenceNo.trim();
            }
            if (product != null) {
            	product = product.trim();
            }
            if (thaiFirstName != null) {
            	thaiFirstName = thaiFirstName.trim();
            }
            if (thaiLastName != null) {
            	thaiLastName = thaiLastName.trim();
            }
            if (citizenId != null) {
            	citizenId = citizenId.trim();
            }
            if (saleType != null) {
            	saleType = saleType.trim();
            }
            if (priority != null) {
            	priority = priority.trim();
            }

            searchReopenDataM = new PLSearchDataM();
            
            searchReopenDataM.setApplicationNo(applicationNo);
            searchReopenDataM.setRefNo(referenceNo);
            searchReopenDataM.setProductCode(product);
            searchReopenDataM.setFirstName(thaiFirstName);
            searchReopenDataM.setLastName(thaiLastName);
            searchReopenDataM.setCitizenID(citizenId);
            searchReopenDataM.setSaleType(saleType);
            searchReopenDataM.setPriority(priority);

            getRequest().getSession().setAttribute("SEARCH_REOPEN_DATAM", searchReopenDataM);
        } else {
        	applicationNo 	=  searchReopenDataM.getApplicationNo();
        	referenceNo = searchReopenDataM.getRefNo();
        	product = searchReopenDataM.getProductCode();
        	thaiFirstName = searchReopenDataM.getFirstName();
        	thaiLastName = searchReopenDataM.getLastName();
        	citizenId 	= searchReopenDataM.getCitizenID();
        	saleType = searchReopenDataM.getSaleType();
        	priority 	= searchReopenDataM.getPriority();
        }

        try{        	
        	StringBuilder SQL = new StringBuilder("");
			ValueListM valueListM = new ValueListM();
			int index = 0;

			SQL.append(" SELECT ");
			SQL.append("     (SELECT MS.SALE_TYPE_DESC FROM MS_SALES_TYPE MS WHERE MS.SALE_TYPE_ID = A.SALE_TYPE) SALE_TYPE, ");
			SQL.append("     (SELECT DISPLAY_NAME FROM LIST_BOX_MASTER WHERE FIELD_ID = '61'  AND CHOICE_NO = A.PRIORITY ) PRIORITY, ");
			SQL.append("     (SELECT LB.DISPLAY_NAME  FROM LIST_BOX_MASTER LB WHERE LB.FIELD_ID = '50' AND LB.CHOICE_NO = A.APPLY_CHANNEL ) CHANNEL, ");
			SQL.append("     NVL(S.SALES_BRANCH_CODE, '-'), ");
			SQL.append("     A.APPLICATION_NO, ");
			SQL.append("     NVL(A.REF_NO, '-'), ");
			SQL.append("     P.TH_FIRST_NAME || ' ' || P.TH_LAST_NAME, ");
			SQL.append("     P.IDNO, ");
			SQL.append("     (SELECT  MP.PRODUCT_DESC  FROM  MS_PRODUCT MP WHERE  MP.PRODUCT_CODE = B.PRODUCT_ID) PRODUCT, ");
			SQL.append("     PKA_UTIL.DATE_TO_STRING_THAI_DATE(A.APPLICATION_DATE, 'dd/mm/yyyy hh24:mi:ss') APP_DATE, ");
			SQL.append("     PKA_UTIL.DATE_TO_STRING_THAI_DATE(A.UPDATE_DATE, 'dd/mm/yyyy hh24:mi:ss') UPDATE_DATE, ");
			SQL.append("     A.APPLICATION_RECORD_ID ");
//			SQL.append("     (SELECT LISTAGG(R.REASON_TYPE ||'|'|| R.REASON_CODE || ',')  ");
//			SQL.append("     WITHIN GROUP (ORDER BY R.APPLICATION_RECORD_ID )  ");
//			SQL.append("     FROM  ORIG_REASON R WHERE R.APPLICATION_RECORD_ID = A.APPLICATION_RECORD_ID ) REASON ");
			SQL.append(" FROM ");
			SQL.append("     ORIG_APPLICATION A, ");
			SQL.append("     ORIG_PERSONAL_INFO P, ");
			SQL.append("     WF_JOBID_MAPPING J, ");
			SQL.append("     BUSINESS_CLASS B, ");
			SQL.append("     ORIG_SALE_INFO S ");
			SQL.append(" WHERE ");
			SQL.append("     A.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append(" AND A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			SQL.append(" AND NVL(P.PERSONAL_TYPE,?) = ? ");
			SQL.append(" AND B.BUS_CLASS_ID = A.BUSINESS_CLASS_ID ");
			SQL.append(" AND A.APPLICATION_RECORD_ID = S.APPLICATION_RECORD_ID ");
			SQL.append(" AND PKA_APP_KLOP.F_CHECK_STATIONBYUSERNAME(A.JOB_STATE,'JOBSTATE_REOPENABLE',?, A.BUSINESS_CLASS_ID) = 1 ");
			SQL.append(" AND J.JOB_STATUS != 'ACTIVE' ");
			SQL.append(" AND J.JOB_VERSION = ");
			SQL.append("     ( ");
			SQL.append("         SELECT ");
			SQL.append("             MAX(SJM.JOB_VERSION) ");
			SQL.append("         FROM ");
			SQL.append("             WF_JOBID_MAPPING SJM ");
			SQL.append("         WHERE ");
			SQL.append("             SJM.APPLICATION_RECORD_ID = J.APPLICATION_RECORD_ID ");
			SQL.append("     ) ");
			
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
			valueListM.setString(++index, userM.getUserName());
			
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getApplicationNo())) {
            	SQL.append("AND UPPER(A.APPLICATION_NO) = UPPER(?) ");
                valueListM.setString(++index, searchReopenDataM.getApplicationNo());
            }
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getRefNo())) {
            	SQL.append("AND UPPER(A.REF_NO) = UPPER(?) ");
                valueListM.setString(++index, searchReopenDataM.getRefNo());
            }
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getProductCode())) {
            	SQL.append("AND B.PRODUCT_ID = ? ");
                valueListM.setString(++index, searchReopenDataM.getProductCode());
            }
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getFirstName())) {
            	SQL.append("AND P.TH_FIRST_NAME LIKE ? ");
                valueListM.setString(++index, (searchReopenDataM.getFirstName()).replace("%", "chr(37)")+"%");
            }
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getLastName())) {
            	SQL.append("AND P.TH_LAST_NAME LIKE ? ");
                valueListM.setString(++index, (searchReopenDataM.getLastName()).replace("%", "chr(37)")+"%");
            }
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getCitizenID())) {
            	SQL.append("AND P.IDNO = ? ");
                valueListM.setString(++index, searchReopenDataM.getCitizenID());
            }
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getSaleType())) {
            	SQL.append("AND A.SALE_TYPE = ? ");
                valueListM.setString(++index, searchReopenDataM.getSaleType());
            }
            if (!ORIGUtility.isEmptyString(searchReopenDataM.getPriority())) {
            	SQL.append("AND A.PRIORITY = ? ");
                valueListM.setString(++index, searchReopenDataM.getPriority());
            }

            SQL.append("ORDER BY A.APPLICATION_NO");
			
			log.debug("String.valueOf(sql) = " + String.valueOf(SQL));
			
            valueListM.setSQL(String.valueOf(SQL));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=SEARCH_REOPEN_SCREEN");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";

        } catch (Exception e) {
        	log.fatal("exception ", e);
        }
        return true;
    }

    public int getNextActivityType() {
        return FrontController.ACTION;
    }

    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken(){
		return false;
	}

}
