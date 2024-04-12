/*
 * Created on Nov 1, 2007
 * Created by Prawit Limwattanachai
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.view.webaction;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.SearchXCMRDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Administrator
 *
 * Type: SearchXCMRWebAction
 */
public class SearchXCMRWebAction extends WebActionHelper implements WebAction {
	Logger logger = Logger.getLogger(SearchXCMRWebAction.class);
    private String nextAction = null;
	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		 
		return null;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		 
		return false;
	}

	/* (non-Javadoc)
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		ORIGUtility utility = new ORIGUtility();
		SearchXCMRDataM searchXCMRDataM = (SearchXCMRDataM)getRequest().getSession().getAttribute("SEARCH_XCMR_DATAM");
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String userName =userM.getUserName();
		
		String searchType = (String)getRequest().getSession().getAttribute("searchType");
		if(searchType==null || searchType.equals("")){
 			searchType = getRequest().getParameter("searchType");
 			getRequest().getSession().setAttribute("searchType",searchType);
		}
		String fromSearch = getRequest().getParameter("fromSearch");
		logger.debug("fromSearch = "+fromSearch);
		if(("Y").equals(fromSearch)){
			searchXCMRDataM = null;
		}
		String citizen_id;
		String company_name;
		String lastname;
		String application_no;
		String register_no;
		String channel;
		String dealer_code;
		String customer_type;
		String sort_by;
		String priority;
		String request_id;
		
		if(searchXCMRDataM == null){			
			
	         citizen_id = getRequest().getParameter("citizen_id");
	         company_name = getRequest().getParameter("company_name");
	         lastname = getRequest().getParameter("lastname");
	         application_no = getRequest().getParameter("application_no");
	         register_no = getRequest().getParameter("register_no");
	         channel = getRequest().getParameter("channel");
	         dealer_code = getRequest().getParameter("dealer_code");
	     //   String dealer_fax = getRequest().getParameter("dealer_fax");
	         customer_type = getRequest().getParameter("customer_type");
	         sort_by = getRequest().getParameter("sort_by");
	         priority = getRequest().getParameter("priority");
	         request_id = getRequest().getParameter("request_id");
			
	        
	        logger.debug("citizen_id = "+citizen_id);
	        logger.debug("company_name = "+company_name);
	        logger.debug("application_no = "+application_no);
	        logger.debug("register_no = "+register_no);
	        logger.debug("channel = "+channel);
	        logger.debug("dealer_code = "+dealer_code);
	     //   logger.debug("dealer_fax = "+dealer_fax);
	        logger.debug("customer_type = "+customer_type);
	        logger.debug("sort_by = "+sort_by);
	        logger.debug("priority = "+priority);
	        
	        //trim Space
			if(citizen_id!=null){
			    citizen_id=ORIGDisplayFormatUtil.lrtrim(citizen_id);    
			}
			if(application_no!=null){
			    application_no=ORIGDisplayFormatUtil.lrtrim(application_no);
			}
			if(company_name!=null){
			    company_name=ORIGDisplayFormatUtil.lrtrim(company_name);
			}
			if(lastname!=null){
			    lastname=ORIGDisplayFormatUtil.lrtrim(lastname);    
			}
	        if(register_no!=null){
	            register_no=ORIGDisplayFormatUtil.lrtrim(register_no);
	        }
	        
	        searchXCMRDataM = new SearchXCMRDataM();
	        
	        searchXCMRDataM.setCitizenID(citizen_id);
            searchXCMRDataM.setCustomerName(company_name);
            searchXCMRDataM.setCustomerLName(lastname);
            searchXCMRDataM.setApplicationNo(application_no);
            searchXCMRDataM.setRegisterNo(register_no);
            searchXCMRDataM.setChannel(channel);
            searchXCMRDataM.setDealerCode(dealer_code);
            searchXCMRDataM.setCustomerType(customer_type);
            searchXCMRDataM.setSortBy(sort_by);
            searchXCMRDataM.setPriority(priority);
            searchXCMRDataM.setRequestID(request_id);
            
            getRequest().getSession().setAttribute("SEARCH_XCMR_DATAM", searchXCMRDataM);
            
		}else{
			 citizen_id = searchXCMRDataM.getCitizenID();
			 company_name = searchXCMRDataM.getCustomerName();
			 lastname = searchXCMRDataM.getCustomerLName();
			 application_no= searchXCMRDataM.getApplicationNo();
			 register_no = searchXCMRDataM.getRegisterNo();
			 channel = searchXCMRDataM.getChannel();
			 dealer_code = searchXCMRDataM.getDealerCode();
			 customer_type = searchXCMRDataM.getCustomerType();
			 sort_by = searchXCMRDataM.getSortBy();
			 priority = searchXCMRDataM.getPriority();
			 request_id = searchXCMRDataM.getRequestID();			
		}
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
//			sql.append("SELECT A.PRIORITY, A.UPDATE_DATE, A.APPLICATION_NO, P.THNAME, P.THSURN, P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTYP, A.DEALER_CODE, A.SRCOFCUS, A.APPLICATION_RECORD_ID, A.APPLICATION_STATUS, A.JOB_STATE, A.CMR_CODE, AC.PERSONAL_TYPE, L.MARKETING_CODE ");
//			sql.append(" ,AIR.IMG_REQUEST  ");
			
			sql.append("SELECT A.PRIORITY, to_char(A.UPDATE_DATE,'dd/mm/yyyy hh24:mi:ss'),A.APPLICATION_NO,P.TH_FIRST_NAME,"+
					" P.TH_LAST_NAME,P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTOMER_TYPE, A.DEALER_CODE,   A.SRCOFCUS, A.APPLICATION_RECORD_ID,"+
					" A.APPLICATION_STATUS,A.JOB_STATE, A.CMR_CODE, P.PERSONAL_TYPE, L.MARKETING_CODE, img.IMG_REQUEST ");
			
			if( OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
				sql.append(" ,V.CONTRACT_NO ");      
				}
//			sql.append("FROM ORIG_APPLICATION A, ORIG_APPLICATION_CUSTOMER AC, HPMSHP00 P, USER_BRANCH B, ORIG_LOAN L ");
			sql.append("FROM ORIG_APPLICATION A, ORIG_PERSONAL_INFO P, USER_BRANCH B, ORIG_LOAN L ");
			if(!ORIGUtility.isEmptyString(register_no)||OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
				sql.append(", ORIG_VEHICLE V ");
			}		 
			
//		    sql.append(", APPLICATION_IMG_REQUEST AIR ");			 
//            sql.append("WHERE A.APPLICATION_RECORD_ID = AC.APPLICATION_RECORD_ID ");
//            sql.append("      AND AC.CMPCDE = P.CMPCDE ");
//            sql.append("      AND AC.IDNO = P.IDNO ");
//            sql.append("      AND A.AREA_CODE = B.AREA ");
//            sql.append("      AND AC.CMPCDE = B.CMPCDE "); 
//            sql.append("	  AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");                                    
//            sql.append(" AND AIR.APPLICATION_RECORD_ID(+) = A.APPLICATION_RECORD_ID ");
			
			sql.append(",APPLICATION_IMG_REQUEST    img " );
            sql.append(" WHERE A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
            sql.append("  AND A.AREA_CODE = B.AREA ");
            sql.append("  AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");
            sql.append("  AND A.APPLICATION_RECORD_ID = img.APPLICATION_RECORD_ID(+) ");
            
            if(!ORIGUtility.isEmptyString(register_no)||OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType) ){
				sql.append("  AND A.APPLICATION_RECORD_ID=V.APPLICATION_RECORD_ID(+) ");
			}	
            
            if(!ORIGUtility.isEmptyString(application_no) || !ORIGUtility.isEmptyString(register_no) 
            	|| !ORIGUtility.isEmptyString(dealer_code) || !ORIGUtility.isEmptyString(customer_type) 
				|| !ORIGUtility.isEmptyString(channel)){
            	sql.append("AND P.PERSONAL_TYPE = ? ");
            	//sql.append("AND AC.PERSONAL_TYPE = ? ");
            	valueListM.setString(++index,OrigConstant.PERSONAL_TYPE_APPLICANT);
            }
            
            if("Override".equals(searchType)){
	            sql.append("AND A.APPLICATION_STATUS = ? ");
			    valueListM.setString(++index,ORIGWFConstant.ApplicationStatus.CAMPAIGN_EXCEPTION);
			
			    sql.append("AND A.JOB_STATE = ? ");
			    valueListM.setString(++index,ORIGWFConstant.JobState.CMREX_EXCEPTION_STATE);
            }else if("Enquiry".equals(searchType)){
	            	
            }
            
		    sql.append("AND UPPER(B.USER_ID) = UPPER(?) ");
		    valueListM.setString(++index,userName);
                       
            if(!ORIGUtility.isEmptyString(citizen_id)){
			    sql.append("AND P.IDNO = ? ");
			    valueListM.setString(++index,citizen_id);
			}
			if(!ORIGUtility.isEmptyString(company_name)){
			    sql.append("AND P.THNAME = ? ");
			    valueListM.setString(++index,company_name);
			}
			if(!ORIGUtility.isEmptyString(lastname)){
			    sql.append("AND P.THSURN = ? ");
			    valueListM.setString(++index,lastname);
			}
			if(!ORIGUtility.isEmptyString(application_no)){
			    sql.append("AND A.APPLICATION_NO = ? ");
			    valueListM.setString(++index,application_no);
			}
			if(!ORIGUtility.isEmptyString(dealer_code)){
			    sql.append("AND A.DEALER_CODE = ? ");
			    valueListM.setString(++index,dealer_code);
			}
			if(!ORIGUtility.isEmptyString(priority)){
			    sql.append("AND A.PRIORITY = ? ");
			    valueListM.setString(++index,priority);
			}
			if(!ORIGUtility.isEmptyString(customer_type)){
			    sql.append("AND P.CUSTYP = ? ");
			    valueListM.setString(++index,customer_type);
			}
			if(!ORIGUtility.isEmptyString(register_no)){
			    sql.append("AND V.REGISTER_NUMBER = ? ");
			    valueListM.setString(++index,register_no);
			}
			if(!ORIGUtility.isEmptyString(channel)){
			    sql.append("AND A.SRCOFCUS = ? ");
			    valueListM.setString(++index,channel);
			}
			if(!ORIGUtility.isEmptyString(request_id )){
				  sql.append("AND img.IMG_REQUEST = ? ");
			    valueListM.setString(++index,request_id);
			}
			
			
			if(!ORIGUtility.isEmptyString(sort_by)){
				sql.append("ORDER BY "+sort_by);
			}else{
				sql.append("ORDER BY PRIORITY, A.CREATE_DATE ");
			}
			
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=XCMR_SEARCH_SCREEN");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";
            
            
       } catch (Exception e) {
           logger.error("exception ",e);
       }
		return true;
	}

	/* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.ACTION;
    }
    /* (non-Javadoc)
     * @see com.eaf.j2ee.pattern.view.webaction.WebActionHelper#getNextActionParameter()
     */
    public String getNextActionParameter() {
        return nextAction;
    }

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
