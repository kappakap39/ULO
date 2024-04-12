package com.eaf.orig.ulo.pl.app.view.webaction;

import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.model.app.search.PLSearchDataM;

public class SearchCashDay5WebAction extends WebActionHelper implements	WebAction {
	Logger logger = Logger.getLogger(SearchCashDay5WebAction.class);
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
		PLSearchDataM searchDCcashDay5DataM = (PLSearchDataM)getRequest().getSession().getAttribute("SEARCH_DC_CASHDAY5_DATAM");
		
		String searchType = (String)getRequest().getSession().getAttribute("searchType");
		logger.debug("searchType = "+searchType);
		if(searchType==null || searchType.equals("")){
 			searchType = getRequest().getParameter("searchType");
		}
		
		String clickSearch = getRequest().getParameter("clickSearch");

		if(("Y").equals(clickSearch)){
			searchDCcashDay5DataM = null;
		}
		
		String citizen_id;
		String application_no;
		String first_name;
		String last_name;
		if(searchDCcashDay5DataM == null){			
	        citizen_id = getRequest().getParameter("citizen_id");
	        application_no = getRequest().getParameter("application_no");
	        first_name = getRequest().getParameter("first_name");
	        last_name = getRequest().getParameter("last_name");
			if(citizen_id!=null){
			    citizen_id=ORIGDisplayFormatUtil.lrtrim(citizen_id);    
			}
			if(application_no!=null){
			    application_no=ORIGDisplayFormatUtil.lrtrim(application_no);
			}
			if(first_name!=null){
				first_name=ORIGDisplayFormatUtil.lrtrim(first_name);
			}
			if(last_name!=null){
				last_name=ORIGDisplayFormatUtil.lrtrim(last_name);
			}
			 
			searchDCcashDay5DataM = new PLSearchDataM();                                    
			searchDCcashDay5DataM.setCitizenID(citizen_id);
 
			searchDCcashDay5DataM.setApplicationNo(application_no);
			searchDCcashDay5DataM.setLastName(last_name);
			searchDCcashDay5DataM.setFirstName(first_name);
                         
            getRequest().getSession().setAttribute("SEARCH_DC_CASHDAY5_DATAM", searchDCcashDay5DataM);
		}else{
			 citizen_id = searchDCcashDay5DataM.getCitizenID();
			 application_no= searchDCcashDay5DataM.getApplicationNo();
			 first_name = searchDCcashDay5DataM.getFirstName() ;
			 last_name= searchDCcashDay5DataM.getLastName();
 		
		}
        
        try{
        	StringBuilder sql = new StringBuilder();
			ValueListM valueListM = new ValueListM();
			int index = 0;						 
			
			sql.append(" SELECT ");
			sql.append("     APP.APPLICATION_RECORD_ID APPLICATION_RECORD_ID, ");
			sql.append("     APP.APPLICATION_NO, ");
			sql.append("     APP.APPLICATION_STATUS, ");
			sql.append("     PER.TH_FIRST_NAME||' '||PER.TH_LAST_NAME TH_NAME, ");
			sql.append("     PER.IDNO IDNO, ");
			sql.append("     (SELECT PRODUCT_ID  FROM BUSINESS_CLASS ");
			sql.append("         WHERE BUSINESS_CLASS.BUS_CLASS_ID = APP.BUSINESS_CLASS_ID ");
			sql.append("     ) ");
			sql.append("     BUSS_CLASS, ");
			sql.append("     TO_CHAR (APP.APPLICATION_DATE, 'dd/MM/YYYY HH:MI:SS') ");
			sql.append(" FROM ");
			sql.append("     ORIG_APPLICATION APP, ");
			sql.append("     ORIG_PERSONAL_INFO PER , ");
			sql.append("     ORIG_CASH_TRANSFER TRAN, ");
			sql.append("     AC_ACCOUNT AC, ");
			sql.append("     AC_ACCOUNT_CARD AC_CARD ");
			sql.append(" WHERE ");
			sql.append("     APP.APPLICATION_RECORD_ID = PER.APPLICATION_RECORD_ID(+) ");
			sql.append(" AND APP.APPLICATION_RECORD_ID = TRAN.APPLICATION_RECORD_ID ");
			sql.append(" AND TRAN.CASH_TRANSFER_TYPE='CD5' ");
			sql.append(" AND TRAN.TRANSFER_ACCOUNT IS NULL ");
			sql.append(" AND APP.JOB_STATE IN ('STE0801','STE1801','STE2801') ");
			sql.append(" AND AC.APPLICATION_NO=APP.APPLICATION_NO ");
			sql.append(" AND AC.ACCOUNT_ID =AC_CARD.ACCOUNT_ID ");
			sql.append(" AND CARD_STATUS='A' ");
			  			
            if(!ORIGUtility.isEmptyString(citizen_id)){
			    sql.append("AND PER.IDNO = ? ");
			    valueListM.setString(++index,citizen_id);			  
			}
			 
			if(!ORIGUtility.isEmptyString(application_no)){
			    sql.append("AND APP.APPLICATION_NO = UPPER(?) ");
			    valueListM.setString(++index,application_no);
			}
			
			if(!ORIGUtility.isEmptyString(first_name)){
				    sql.append("AND PER.TH_FIRST_NAME like ? ");
				    valueListM.setString(++index,first_name+"%");			  
			}
				 
			if(!ORIGUtility.isEmptyString(last_name)){
			    sql.append("AND PER.TH_LAST_NAME like ? ");
			    valueListM.setString(++index,last_name+"%");
			}
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=DC_SEARCH_CASHDAY5_SCREEN");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";            
       }catch(Exception e){
           logger.error("exception ",e);
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
