/*
 * Created on Oct 30, 2007
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
import com.eaf.orig.shared.model.SearchUWDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author Administrator
 * 
 * Type: SearchUWWebAction
 */
public class SearchUWWebAction extends WebActionHelper implements WebAction {

    Logger logger = Logger.getLogger(SearchUWWebAction.class);

    private String nextAction = null;

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
     */
    public Event toEvent() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
     */
    public boolean requiredModelRequest() {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(com.eaf.j2ee.pattern.control.event.EventResponse)
     */
    public boolean processEventResponse(EventResponse response) {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
     */
    public boolean preModelRequest() {
        ORIGUtility utility = new ORIGUtility();
        SearchUWDataM searchUWDataM = (SearchUWDataM) getRequest().getSession().getAttribute("SEARCH_UW_DATAM");
        UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
        String userName = userM.getUserName();

        String searchType = (String) getRequest().getSession().getAttribute("searchType");
        if (searchType == null || searchType.equals("")) {
            searchType = getRequest().getParameter("searchType");
            getRequest().getSession().setAttribute("searchType", searchType);
        }
        String fromSearch = getRequest().getParameter("fromSearch");
        logger.debug("fromSearch = " + fromSearch);
        if (("Y").equals(fromSearch)) {
            searchUWDataM = null;
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
		String  groupID;
        //Wiroon 20100315
        String co_citizen_id;
        String co_fname;
        String co_lname;

        if (searchUWDataM == null) {

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
	         groupID =  getRequest().getParameter("group_id");

            co_citizen_id = getRequest().getParameter("co_citizen_id");
            co_fname = getRequest().getParameter("co_fname");
            co_lname = getRequest().getParameter("co_lname");

            logger.debug("citizen_id = " + citizen_id);
            logger.debug("company_name = " + company_name);
            logger.debug("application_no = " + application_no);
            logger.debug("register_no = " + register_no);
            logger.debug("channel = " + channel);
            logger.debug("dealer_code = " + dealer_code);
            //   logger.debug("dealer_fax = "+dealer_fax);
            logger.debug("customer_type = " + customer_type);
            logger.debug("sort_by = " + sort_by);

            logger.debug("co_citizen_id = " + co_citizen_id);
            logger.debug("co_fname = " + co_fname);
            logger.debug("co_lname = " + co_lname);

            logger.debug("priority = " + priority);
            logger.debug("request_id = " + getRequest().getParameter("request_id"));
	        logger.debug("group_id = "+getRequest().getParameter("group_id"));
            //trim Space
            if (citizen_id != null) {
                citizen_id = ORIGDisplayFormatUtil.lrtrim(citizen_id);
            }
            if (application_no != null) {
                application_no = ORIGDisplayFormatUtil.lrtrim(application_no);
            }
            if (company_name != null) {
                company_name = ORIGDisplayFormatUtil.lrtrim(company_name);
            }
            if (lastname != null) {
                lastname = ORIGDisplayFormatUtil.lrtrim(lastname);
            }
            if (register_no != null) {
                register_no = ORIGDisplayFormatUtil.lrtrim(register_no);
            }
            if (request_id != null) {
                request_id = ORIGDisplayFormatUtil.lrtrim(request_id);
            }

            if (co_citizen_id != null) {
                co_citizen_id = ORIGDisplayFormatUtil.lrtrim(co_citizen_id);
            }
            if(groupID!=null){
	            groupID=ORIGDisplayFormatUtil.lrtrim(groupID);		          
		    }
            
            if (co_fname != null) {
                co_fname = ORIGDisplayFormatUtil.lrtrim(co_fname);
            }
            if (co_lname != null) {
                co_lname = ORIGDisplayFormatUtil.lrtrim(co_lname);
            }

            searchUWDataM = new SearchUWDataM();

            searchUWDataM.setCitizenID(citizen_id);
            searchUWDataM.setCustomerName(company_name);
            searchUWDataM.setCustomerLName(lastname);
            searchUWDataM.setApplicationNo(application_no);
            searchUWDataM.setRegisterNo(register_no);
            searchUWDataM.setChannel(channel);
            searchUWDataM.setDealerCode(dealer_code);
            searchUWDataM.setCustomerType(customer_type);
            searchUWDataM.setSortBy(sort_by);
            searchUWDataM.setPriority(priority);
            searchUWDataM.setRequestID(request_id);
            searchUWDataM.setGroupID(groupID);
            //Wiroon 20100315
            searchUWDataM.setCo_citizenID(co_citizen_id);
            searchUWDataM.setCo_customerFName(co_fname);
            searchUWDataM.setCo_customerLName(co_lname);

            getRequest().getSession().setAttribute("SEARCH_UW_DATAM", searchUWDataM);
        } else {
            citizen_id = searchUWDataM.getCitizenID();
            company_name = searchUWDataM.getCustomerName();
            lastname = searchUWDataM.getCustomerLName();
            application_no = searchUWDataM.getApplicationNo();
            register_no = searchUWDataM.getRegisterNo();
            channel = searchUWDataM.getChannel();
            dealer_code = searchUWDataM.getDealerCode();
            customer_type = searchUWDataM.getCustomerType();
            sort_by = searchUWDataM.getSortBy();
            priority = searchUWDataM.getPriority();
            request_id = searchUWDataM.getRequestID();
			 groupID = searchUWDataM.getGroupID();

            //Wiroon 20100315
            co_citizen_id = searchUWDataM.getCo_citizenID();
            co_fname = searchUWDataM.getCo_customerFName();
            co_lname = searchUWDataM.getCo_customerLName();
        }

        try {
            StringBuffer sql = new StringBuffer();
            ValueListM valueListM = new ValueListM();
            int index = 0;

            sql
                    .append("SELECT A.PRIORITY, to_char(A.UPDATE_DATE, 'dd/mm/yyyy hh24:mi:ss'), A.APPLICATION_NO, P.TH_FIRST_NAME, P.TH_LAST_NAME, P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTOMER_TYPE, A.DEALER_CODE, A.SRCOFCUS, A.APPLICATION_RECORD_ID, A.APPLICATION_STATUS, A.JOB_STATE, A.CMR_CODE, A.DE_START_DATE, P.PERSONAL_TYPE, L.MARKETING_CODE, A.CREATE_DATE ");
            sql.append(" ,img.IMG_REQUEST  ");
			sql.append(" ,A.ESCALATE_TO , A.GROUP_APP_ID ");
            if (OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)) {
                sql.append(" ,V.CONTRACT_NO ");
            }

            sql.append("FROM ORIG_APPLICATION A, ORIG_PERSONAL_INFO P, USER_BRANCH B, ORIG_LOAN L ");
            if (!ORIGUtility.isEmptyString(register_no) || "Reopen".equals(searchType) || OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)) {
                sql.append(", ORIG_VEHICLE V ");
            }
            sql.append(",APPLICATION_IMG_REQUEST    img ");
            sql.append("WHERE A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
            sql.append("      AND A.AREA_CODE = B.AREA ");
            sql.append("	  AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");
            sql.append("      AND  a.APPLICATION_RECORD_ID=img.APPLICATION_RECORD_ID(+) ");

            if (!ORIGUtility.isEmptyString(register_no) || "Reopen".equals(searchType) || OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)) {
                sql.append("  AND A.APPLICATION_RECORD_ID=V.APPLICATION_RECORD_ID(+) ");
            }

            //Wiroon 20100315
            if (!ORIGUtility.isEmptyString(co_citizen_id) || !ORIGUtility.isEmptyString(co_fname) || !ORIGUtility.isEmptyString(co_lname)) {

                sql.append(" AND PERSONAL_TYPE='G' ");

                if (!ORIGUtility.isEmptyString(co_citizen_id) && ORIGUtility.isEmptyString(citizen_id)) {
                    sql.append("AND P.IDNO = ? ");
                    valueListM.setString(++index, co_citizen_id);
                }
                if (!ORIGUtility.isEmptyString(co_fname) && ORIGUtility.isEmptyString(company_name)) {
                    sql.append("AND P.TH_FIRST_NAME = ? ");
                    valueListM.setString(++index, co_fname);
                }
                if (!ORIGUtility.isEmptyString(co_lname) && ORIGUtility.isEmptyString(lastname)) {
                    sql.append("AND P.TH_URN = ? ");
                    valueListM.setString(++index, co_lname);
                }
            }

            if (!ORIGUtility.isEmptyString(application_no) || !ORIGUtility.isEmptyString(register_no) || !ORIGUtility.isEmptyString(dealer_code)
                    || !ORIGUtility.isEmptyString(customer_type) || !ORIGUtility.isEmptyString(channel)) {

                sql.append("AND P.PERSONAL_TYPE = ? ");
                valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
            }

            if ("Inbox".equals(searchType)) {
                sql.append("AND (( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ) OR ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ))");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.NEW);
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_NEW_STATE);

                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.REASIGNED);
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_REASSIGNED_STATE);

                //valueListM.setString(++index,ORIGWFConstant.ApplicationStatus.ESCALATED);
                //valueListM.setString(++index,ORIGWFConstant.JobState.UW_ESCALATED_STATE);
            } else if ("Pending".equals(searchType)) {
                sql.append("AND A.APPLICATION_STATUS = ? ");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.PENDING);

                sql.append("AND A.JOB_STATE = ? ");
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_PENDING_STATE);
            } else if ("Reopen".equals(searchType)) {
                sql
                        .append("AND ( ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ) OR ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ) OR ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ) OR ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ) OR ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ) OR ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? ) )");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.REJECTED);
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_REJECTED_STATE);

                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.CANCELLED);
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_CANCELLED_STATE);

                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.WITHDREW);
                valueListM.setString(++index, ORIGWFConstant.JobState.PD_WITHDREW_STATE);

                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.AUTO_CANCELLED);
                valueListM.setString(++index, ORIGWFConstant.JobState.DE_AUTO_CANCELLED_STATE);

                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.AUTO_CANCELLED);
                valueListM.setString(++index, ORIGWFConstant.JobState.CMREX_AUTO_CANCELLED_STATE);

                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.AUTO_CANCELLED);
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_AUTO_CANCELLED_STATE);

                sql.append("AND (A.DRAW_DOWN_FLAG <> 'Y' OR A.DRAW_DOWN_FLAG IS NULL) ");

            } else if ("ConditionApproval".equals(searchType)) {
                sql.append("AND A.APPLICATION_STATUS = ? ");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.CONDITIONAL_APPROVED);

                sql.append("AND A.JOB_STATE = ? ");
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_CONDITIONAL_APPROVED_STATE);
            } else if ("Reassign".equals(searchType)) {
                sql.append("AND A.APPLICATION_STATUS = ? ");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.ESCALATED);

                sql.append("AND A.JOB_STATE = ? ");
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_ESCALATED_STATE);

                //				    sql.append("AND A.ESCALATE_TO IN(SELECT DISTINCT
                // UA.GROUP_NAME");
                //				    sql.append(" FROM USER_APPROVAL_AUTHORITY UA");
                //				    sql.append(" WHERE UA.USER_ID = ?)");
                //				    valueListM.setString(++index,userName);
            } else if ("SearchProposal".equals(searchType)) {
                sql.append("AND A.APPLICATION_STATUS = ? ");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.NEW);

                sql.append("AND A.JOB_STATE = ? ");
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_NEW_PROPOSAL_STATE);
            } else if ("SearchPendingProposal".equals(searchType)) {
                sql.append("AND A.APPLICATION_STATUS = ? ");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.PENDING);

                sql.append("AND A.JOB_STATE = ? ");
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_PROPOSAL_PENDING_STATE);
            } else if ("Enquiry".equals(searchType)) {

            }

            sql.append("AND UPPER(B.USER_ID) = UPPER(?) ");
            valueListM.setString(++index, userName);

            if (!ORIGUtility.isEmptyString(citizen_id)) {
                sql.append("AND P.IDNO = ? ");
                valueListM.setString(++index, citizen_id);
            }
            if (!ORIGUtility.isEmptyString(company_name)) {
                sql.append("AND P.TH_FIRST_NAME = ? ");
                valueListM.setString(++index, company_name);
            }
            if (!ORIGUtility.isEmptyString(lastname)) {
                sql.append("AND P.TH_LAST_NAME = ? ");
                valueListM.setString(++index, lastname);
            }
            if (!ORIGUtility.isEmptyString(application_no)) {
                sql.append("AND A.APPLICATION_NO = ? ");
                valueListM.setString(++index, application_no);
            }
            if (!ORIGUtility.isEmptyString(dealer_code)) {
                sql.append("AND A.DEALER_CODE = ? ");
                valueListM.setString(++index, dealer_code);
            }
            if (!ORIGUtility.isEmptyString(priority)) {
                sql.append("AND A.PRIORITY = ? ");
                valueListM.setString(++index, priority);
            }
            if (!ORIGUtility.isEmptyString(customer_type)) {
                sql.append("AND P.CUSTOMER_TYPE = ? ");
                valueListM.setString(++index, customer_type);
            }
            if (!ORIGUtility.isEmptyString(register_no)) {
                sql.append("AND V.REGISTER_NUMBER = ? ");
                valueListM.setString(++index, register_no);
            }
            if (!ORIGUtility.isEmptyString(channel)) {
                sql.append("AND A.SRCOFCUS = ? ");
                valueListM.setString(++index, channel);
            }
            if (!ORIGUtility.isEmptyString(request_id)) {
                sql.append("AND img.IMG_REQUEST = ? ");
                valueListM.setString(++index, request_id);
            }
			if(!ORIGUtility.isEmptyString(groupID)){
			    sql.append("AND A.GROUP_APP_ID = ? ");
			    valueListM.setString(++index, groupID);
			}			
            if ("Inbox".equals(searchType)) {
                sql.append("UNION ");

                sql
                        .append("SELECT A.PRIORITY, to_char(A.UPDATE_DATE, 'dd/mm/yyyy hh24:mi:ss'), A.APPLICATION_NO, P.TH_FIRST_NAME, P.TH_LAST_NAME, P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTOMER_TYPE, A.DEALER_CODE, A.SRCOFCUS, A.APPLICATION_RECORD_ID, A.APPLICATION_STATUS, A.JOB_STATE, A.CMR_CODE, A.DE_START_DATE, P.PERSONAL_TYPE, L.MARKETING_CODE, A.CREATE_DATE ");
                sql.append(" ,img.IMG_REQUEST  ");
				sql.append(" ,A.ESCALATE_TO , A.GROUP_APP_ID ");
                sql.append("FROM ORIG_APPLICATION A, ORIG_PERSONAL_INFO P, USER_BRANCH B, ORIG_LOAN L ");
                if (!ORIGUtility.isEmptyString(register_no) || "Reopen".equals(searchType)) {
                    sql.append(", ORIG_VEHICLE V ");
                }
                sql.append(",APPLICATION_IMG_REQUEST    img ");
                sql.append("WHERE A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
                sql.append("      AND A.AREA_CODE = B.AREA ");
                sql.append("	  AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");
                sql.append("      AND  a.APPLICATION_RECORD_ID=img.APPLICATION_RECORD_ID(+) ");

                if (!ORIGUtility.isEmptyString(register_no) || "Reopen".equals(searchType)) {
                    sql.append("  AND A.APPLICATION_RECORD_ID=V.APPLICATION_RECORD_ID(+) ");
                }
                if (!ORIGUtility.isEmptyString(application_no) || !ORIGUtility.isEmptyString(register_no) || !ORIGUtility.isEmptyString(dealer_code)
                        || !ORIGUtility.isEmptyString(customer_type) || !ORIGUtility.isEmptyString(channel)) {

                    sql.append("AND P.PERSONAL_TYPE = ? ");
                    valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
                }

                sql.append("AND ( A.APPLICATION_STATUS = ? AND A.JOB_STATE = ? )");
                valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.ESCALATED);
                valueListM.setString(++index, ORIGWFConstant.JobState.UW_ESCALATED_STATE);
                sql.append("AND UPPER(B.USER_ID) = UPPER(?) ");
                valueListM.setString(++index, userName);

                if (!ORIGUtility.isEmptyString(citizen_id)) {
                    sql.append("AND P.IDNO = ? ");
                    valueListM.setString(++index, citizen_id);
                }
                if (!ORIGUtility.isEmptyString(company_name)) {
                    sql.append("AND P.TH_FIRST_NAME = ? ");
                    valueListM.setString(++index, company_name);
                }
                if (!ORIGUtility.isEmptyString(lastname)) {
                    sql.append("AND P.TH_LAST_NAME = ? ");
                    valueListM.setString(++index, lastname);
                }
                if (!ORIGUtility.isEmptyString(application_no)) {
                    sql.append("AND A.APPLICATION_NO = ? ");
                    valueListM.setString(++index, application_no);
                }
                if (!ORIGUtility.isEmptyString(dealer_code)) {
                    sql.append("AND A.DEALER_CODE = ? ");
                    valueListM.setString(++index, dealer_code);
                }
                if (!ORIGUtility.isEmptyString(priority)) {
                    sql.append("AND A.PRIORITY = ? ");
                    valueListM.setString(++index, priority);
                }
                if (!ORIGUtility.isEmptyString(customer_type)) {
                    sql.append("AND P.CUSTOMER_TYPE = ? ");
                    valueListM.setString(++index, customer_type);
                }
                if (!ORIGUtility.isEmptyString(register_no)) {
                    sql.append("AND V.REGISTER_NUMBER = ? ");
                    valueListM.setString(++index, register_no);
                }
                if (!ORIGUtility.isEmptyString(channel)) {
                    sql.append("AND A.SRCOFCUS = ? ");
                    valueListM.setString(++index, channel);
                }
                if (!ORIGUtility.isEmptyString(request_id)) {
                    sql.append("AND img.IMG_REQUEST = ? ");
                    valueListM.setString(++index, request_id);
                }
				if(!ORIGUtility.isEmptyString(groupID)){
				    sql.append("AND A.GROUP_APP_ID = ? ");
				    valueListM.setString(++index, groupID);
				}							
                sql.append("AND exists (select ag.application_record_id ");
                sql.append("			from orig_application_appv_grp ag, user_approval_authority ua, orig_application aa");
                sql.append("			where ag.group_name = ua.group_name");
                //	sql.append(" and ag.loan_type = ua.loan_type");
                //	sql.append(" and ag.customer_type = ua.customer_type");
                sql.append("				and upper(ua.user_id) = upper(?)");
                sql.append("				and ag.application_record_id = aa.application_record_id");
                sql.append("				and a.application_record_id = aa.application_record_id) ");
                valueListM.setString(++index, userName);

            }

            if (!ORIGUtility.isEmptyString(sort_by)) {
                sql.append("ORDER BY " + sort_by);
            } else {
                sql.append("ORDER BY PRIORITY, CREATE_DATE ");
            }

            logger.debug("Search Application SQL>>> " + sql);
            valueListM.setSQL(String.valueOf(sql));
            valueListM.setNextPage(false);
            valueListM.setItemsPerPage(10);
            valueListM.setReturnToAction("page=UW_SEARCH_SCREEN");
            getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
            nextAction = "action=ValueListWebAction";

        } catch (Exception e) {
            logger.error("exception ", e);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
     */
    public int getNextActivityType() {
        return FrontController.ACTION;
    }

    /*
     * (non-Javadoc)
     * 
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
