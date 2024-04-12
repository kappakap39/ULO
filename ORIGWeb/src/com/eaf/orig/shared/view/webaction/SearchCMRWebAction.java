/*
 * Created on Oct 25, 2007
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
import com.eaf.orig.shared.model.SearchCMRDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Administrator
 *
 * Type: SearchCMRWebaction
 */
public class SearchCMRWebAction extends WebActionHelper implements WebAction {

	Logger logger = Logger.getLogger(SearchCMRWebAction.class);
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
		SearchCMRDataM searchCMRDataM = (SearchCMRDataM)getRequest().getSession().getAttribute("SEARCH_CMR_DATAM");
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		String userName =userM.getUserName();
		
		String searchType = (String)getRequest().getSession().getAttribute("searchType");
		logger.debug("searchType = "+searchType);
		if(searchType==null || searchType.equals("")){
 			searchType = getRequest().getParameter("searchType");
		}
		String fromSearch = getRequest().getParameter("fromSearch");
		logger.debug("fromSearch = "+fromSearch);
		if(("Y").equals(fromSearch)){
			searchCMRDataM = null;
		}
		logger.debug("searchCMRDataM = "+searchCMRDataM);
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
	//	int request_id;
		
		if(searchCMRDataM == null){			
		
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
	  //       request_id = utility.stringToInt(getRequest().getParameter("request_id"));
			
	        
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
//	      trim Space
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
            searchCMRDataM = new SearchCMRDataM();                                    
            searchCMRDataM.setCitizenID(citizen_id);
            searchCMRDataM.setCustomerName(company_name);
            searchCMRDataM.setCustomerLName(lastname);
            searchCMRDataM.setApplicationNo(application_no);
            searchCMRDataM.setRegisterNo(register_no);
            searchCMRDataM.setChannel(channel);
            searchCMRDataM.setDealerCode(dealer_code);
            searchCMRDataM.setCustomerType(customer_type);
            searchCMRDataM.setSortBy(sort_by);
            searchCMRDataM.setPriority(priority);
            
            getRequest().getSession().setAttribute("SEARCH_CMR_DATAM", searchCMRDataM);
		}else{
			 citizen_id = searchCMRDataM.getCitizenID();
			 company_name = searchCMRDataM.getCustomerName();
			 lastname = searchCMRDataM.getCustomerLName();
			 application_no= searchCMRDataM.getApplicationNo();
			 register_no = searchCMRDataM.getRegisterNo();
			 channel = searchCMRDataM.getChannel();
			 dealer_code = searchCMRDataM.getDealerCode();
			 customer_type = searchCMRDataM.getCustomerType();
			 sort_by = searchCMRDataM.getSortBy();
			 priority = searchCMRDataM.getPriority();
		//	 request_id = searchDataM.getRequestID();			
		}
        
        try {
        	StringBuffer sql = new StringBuffer();
			ValueListM valueListM = new ValueListM();
			int index = 0;
			
			//Comment By Rawi Songchaisin
			//sql.append("SELECT A.PRIORITY, A.UPDATE_DATE, A.APPLICATION_NO, P.THNAME, P.THSURN, P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTYP, A.DEALER_CODE, A.SRCOFCUS, A.APPLICATION_RECORD_ID, A.APPLICATION_STATUS, A.JOB_STATE, A.CMR_CODE, AC.PERSONAL_TYPE, L.MARKETING_CODE ");
			sql.append(" SELECT A.PRIORITY,to_char(A.UPDATE_DATE,'dd/mm/yyyy hh24:mm:ss'),A.APPLICATION_NO,P.TH_FIRST_NAME,P.TH_LAST_NAME,P.IDNO,A.LOAN_TYPE,A.UPDATE_BY,P.CUSTOMER_TYPE,A.DEALER_CODE, ");
			sql.append(" A.SRCOFCUS,A.APPLICATION_RECORD_ID,A.APPLICATION_STATUS,A.JOB_STATE,A.CMR_CODE,P.PERSONAL_TYPE,L.MARKETING_CODE ");
			
			if( OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
				sql.append(" ,' ' ");      
			}
			sql.append(" ,a.CREATE_DATE create_date ");
			
			//Comment
			//sql.append("FROM ORIG_APPLICATION A, ORIG_APPLICATION_CUSTOMER AC, HPMSHP00 P, USER_BRANCH B, ORIG_LOAN L ");
			
			sql.append(" FROM ORIG_APPLICATION A,ORIG_PERSONAL_INFO P,USER_BRANCH B,ORIG_LOAN L ");
			
			// Comment
			//if(!ORIGUtility.isEmptyString(register_no)||OrigConstant.SERACH_TYPE_ENQUIRY.equals(searchType)){
			//	sql.append(", ORIG_VEHICLE V ");
			//}
			
			sql.append(" WHERE A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
			sql.append(" AND A.AREA_CODE = B.AREA ");
			sql.append(" AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");
			
			// Comment
            //sql.append("WHERE A.APPLICATION_RECORD_ID = AC.APPLICATION_RECORD_ID ");
            //sql.append("      AND AC.CMPCDE = P.CMPCDE ");
            //sql.append("      AND AC.IDNO = P.IDNO ");
            //sql.append("      AND A.AREA_CODE = B.AREA ");
            //sql.append("      AND AC.CMPCDE = B.CMPCDE ");
            //sql.append("	  AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");
            
            //Comment
            //if(!ORIGUtility.isEmptyString(register_no)||OrigConstant.SERACH_TYPE_ENQUIRY.equals(searchType) ){
			//	sql.append("  AND A.APPLICATION_RECORD_ID=V.APPLICATION_RECORD_ID(+) ");
			//}	
            
            if(!ORIGUtility.isEmptyString(application_no) || !ORIGUtility.isEmptyString(register_no) 
                	|| !ORIGUtility.isEmptyString(dealer_code) || !ORIGUtility.isEmptyString(customer_type) 
    				|| !ORIGUtility.isEmptyString(channel)){
                	//Comment
                	//sql.append("AND AC.PERSONAL_TYPE = ? ");
            		sql.append(" AND P.PERSONAL_TYPE = ? ");
                	valueListM.setString(++index,OrigConstant.PERSONAL_TYPE_APPLICANT);
            }
            
            if("Draft".equals(searchType)){
	            sql.append("AND A.APPLICATION_STATUS = ? ");
			    valueListM.setString(++index,ORIGWFConstant.ApplicationStatus.DRAFT);
			
			    sql.append("AND A.JOB_STATE = ? ");
			    valueListM.setString(++index,ORIGWFConstant.JobState.CMR_DRAFT_STATE);
            }else if("Enquiry".equals(searchType)){
	            	
            } 
			    
		    sql.append("AND UPPER(B.USER_ID) = UPPER(?) ");			    
		    valueListM.setString(++index,userName);
		    logger.debug("userName = "+userName);
		    
		    //Comment
		    //sql.append(" and exists (");
		    //sql.append("        select 'x'");
		    //sql.append("          from team_member tm");
		    //sql.append("         where upper(tm.member_id) = upper(a.cmr_first_id)");
		   	//	// sql.append("                    WHERE ( ( UPPER (tm.member_id) = UPPER (a.cmr_first_id))  or  ( UPPER (tm.member_id) = UPPER ( (select user_id from HPTBHP31 where loanoff= l.MARKETING_CODE)) ) )");
		    //sql.append("           and (   upper(tm.member_id) = upper(?)");
		    //sql.append("                or exists (");
		    //sql.append("                      select 'x'");
		    //sql.append("                        from team_member tmt");
		    //sql.append("                       where upper(tmt.member_id) = upper(?)");
		    //sql.append("                        and tmt.leader_flag = 'Y'");
		    //sql.append("                         and tmt.team_id = tm.team_id)");
		    //sql.append("              ))");
		    
		    sql.append(" and exists (select 'x' ");
		    sql.append(" from team_member tm ");
		    sql.append(" where upper(tm.member_id) = upper(a.cmr_first_id) ");
		    sql.append(" and (upper(tm.member_id) = upper(?) or exists ");
		    sql.append(" (select 'x' ");
		    sql.append(" from team_member tmt ");
		    sql.append(" where upper(tmt.member_id) = upper(?) ");
		    sql.append(" and tmt.leader_flag = 'Y' ");
		    sql.append(" and tmt.team_id = tm.team_id))) ");
		  
		    
		    valueListM.setString(++index,userName);
		    valueListM.setString(++index,userName);
			  			
            if(!ORIGUtility.isEmptyString(citizen_id)){
			    sql.append("AND P.IDNO = ? ");
			    valueListM.setString(++index,citizen_id);			  
			}
			if(!ORIGUtility.isEmptyString(company_name)){
			    sql.append("AND P.TH_FIRST_NAME = ? ");
			    valueListM.setString(++index,company_name);
			}
			if(!ORIGUtility.isEmptyString(lastname)){
			    sql.append("AND P.TH_LAST_NAME = ? ");
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
			    sql.append("AND P.CUSTOMER_TYPE = ? ");
			    valueListM.setString(++index,customer_type);
			}
//			if(!ORIGUtility.isEmptyString(register_no)){
//			    sql.append("AND V.REGISTER_NUMBER = ? ");
//			    valueListM.setString(++index,register_no);
//			}
			if(!ORIGUtility.isEmptyString(channel)){
			    sql.append("AND A.SRCOFCUS = ? ");
			    valueListM.setString(++index,channel);
			}
			
			
			 
			//==========================Union=======================================
		    if("Enquiry".equals(searchType)){
				sql.append(" UNION SELECT A.PRIORITY, to_char(A.UPDATE_DATE,'dd/mm/yyyy hh24:mm:ss'), A.APPLICATION_NO, P.TH_FIRST_NAME,P.TH_LAST_NAME, P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTOMER_TYPE, A.DEALER_CODE, A.SRCOFCUS, A.APPLICATION_RECORD_ID, A.APPLICATION_STATUS, A.JOB_STATE, A.CMR_CODE, P.PERSONAL_TYPE, L.MARKETING_CODE");
				if( OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
				sql.append(" ,V.CONTRACT_NO ");      
				}
				sql.append(" ,a.CREATE_DATE create_date ");
				//sql.append("FROM ORIG_APPLICATION A, ORIG_APPLICATION_CUSTOMER AC, HPMSHP00 P, USER_BRANCH B, ORIG_LOAN L ");
				sql.append("FROM ORIG_APPLICATION A, ORIG_PERSONAL_INFO P, ORIG_LOAN L ");
				if(!ORIGUtility.isEmptyString(register_no)||OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)){
					sql.append(", ORIG_VEHICLE V ");
				}
				sql.append(" ,      (SELECT *    FROM MS_LOAN_OFFICER ");
	            sql.append(" WHERE  UPPER(userid)  in  ");  
	            sql.append("     ( SELECT  UPPER(tm.MEMBER_ID)  FROM team_member tm ");
	            sql.append("               WHERE  UPPER (tm.member_id) = UPPER (?) ");
	            sql.append("              OR EXISTS ( ");
	            sql.append("              SELECT 'x'   FROM team_member tmt ");
	            sql.append("              WHERE UPPER (tmt.member_id) = UPPER (?) ");
	            sql.append("                 AND tmt.leader_flag = 'Y' ");
	            sql.append("                 AND tmt.team_id = tm.team_id ");                   
				sql.append(" 	 ) )           ) mkt ");
				valueListM.setString(++index,userName);
				valueListM.setString(++index,userName);
	            sql.append("WHERE A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
//	            sql.append("      AND AC.CMPCDE = P.CMPCDE ");
//	            sql.append("      AND AC.IDNO = P.IDNO ");
	            //sql.append("      AND A.AREA_CODE = B.AREA ");
	            //sql.append("      AND AC.CMPCDE = B.CMPCDE ");
	            sql.append("	  AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");
	            if(!ORIGUtility.isEmptyString(register_no)||OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType) ){
					sql.append("  AND A.APPLICATION_RECORD_ID=V.APPLICATION_RECORD_ID(+) ");
				}	
	            
	            if(!ORIGUtility.isEmptyString(application_no) || !ORIGUtility.isEmptyString(register_no) 
	                	|| !ORIGUtility.isEmptyString(dealer_code) || !ORIGUtility.isEmptyString(customer_type) 
	    				|| !ORIGUtility.isEmptyString(channel)){
	                	
	                	sql.append("AND P.PERSONAL_TYPE = ? ");
	                	valueListM.setString(++index,OrigConstant.PERSONAL_TYPE_APPLICANT);
	            }                         
				sql.append(" and l.MARKETING_CODE=mkt.LOAN_OFFICER ");    
			   // sql.append("AND UPPER(B.USER_ID) = UPPER(?) ");			    
			   // valueListM.setString(++index,userName);
			  //  logger.debug("userName = "+userName);
			    
			  //  sql.append(" and exists (");
			  //  sql.append("        select 'x'");
			  //  sql.append("          from team_member tm");
			  // sql.append("         where upper(tm.member_id) = upper(a.cmr_first_id)");
			   // sql.append("                    WHERE ( ( UPPER (tm.member_id) = UPPER (a.cmr_first_id))  or  ( UPPER (tm.member_id) = UPPER ( (select user_id from HPTBHP31 where loanoff= l.MARKETING_CODE)) ) )");
			   // sql.append("           and (   upper(tm.member_id) = upper(?)");
			   // sql.append("                or exists (");
			   // sql.append("                      select 'x'");
			   // sql.append("                        from team_member tmt");
			   // sql.append("                       where upper(tmt.member_id) = upper(?)");
			   // sql.append("                        and tmt.leader_flag = 'Y'");
			   // sql.append("                         and tmt.team_id = tm.team_id)");
			  //  sql.append("              ))");
			   // valueListM.setString(++index,userName);
			   // valueListM.setString(++index,userName);
				  			
	            if(!ORIGUtility.isEmptyString(citizen_id)){
				    sql.append("AND P.IDNO = ? ");
				    valueListM.setString(++index,citizen_id);			  
				}
				if(!ORIGUtility.isEmptyString(company_name)){
				    sql.append("AND P.TH_FIRST_NAME = ? ");
				    valueListM.setString(++index,company_name);
				}
				if(!ORIGUtility.isEmptyString(lastname)){
				    sql.append("AND P.TH_LAST_NAME = ? ");
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
				    sql.append("AND P.CUSTOMER_TYPE = ? ");
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
				//=====================================
		    }
			
			if(!ORIGUtility.isEmptyString(sort_by)){
				sql.append("ORDER BY "+sort_by);
			}else{
				//sql.append("ORDER BY PRIORITY, A.CREATE_DATE ");
				sql.append("ORDER BY PRIORITY, CREATE_DATE ");
			}
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=CMR_SEARCH_SCREEN");
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
