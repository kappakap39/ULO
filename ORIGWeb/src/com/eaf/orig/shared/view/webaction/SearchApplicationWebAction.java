/*
 * Created on Oct 9, 2007
 * Created by weeraya
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
import com.eaf.orig.shared.model.SearchDataM;
import com.eaf.orig.shared.model.ValueListM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.wf.shared.ORIGWFConstant;

/**
 * @author weeraya
 * 
 * Type: SearchApplicationWebAction
 */
public class SearchApplicationWebAction extends WebActionHelper implements WebAction {
    Logger logger = Logger.getLogger(SearchApplicationWebAction.class);

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
        SearchDataM searchDEDataM = (SearchDataM) getRequest().getSession().getAttribute("SEARCH_DE_DATAM");
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
            searchDEDataM = null;
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
		String groupID;
        //Wiroon 20100315
        String co_citizen_id;
        String co_fname;
        String co_lname;

        if (searchDEDataM == null) {

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
	         groupID = getRequest().getParameter("group_id");

            //Wiroon 20100315
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
            logger.debug("priority = " + priority);
	        logger.debug("groupID = "+groupID);
            logger.debug("co_citizen_id = " + co_citizen_id);
            logger.debug("co_fname = " + co_fname);
            logger.debug("co_lname = " + co_lname);

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

            //Wiroon 20100315
            if (co_citizen_id != null) {
                co_citizen_id = ORIGDisplayFormatUtil.lrtrim(co_citizen_id);
            }
            if (co_fname != null) {
                co_fname = ORIGDisplayFormatUtil.lrtrim(co_fname);
            }
            if (co_lname != null) {
                co_lname = ORIGDisplayFormatUtil.lrtrim(co_lname);
            }

            searchDEDataM = new SearchDataM();

            searchDEDataM.setCitizenID(citizen_id);
            searchDEDataM.setCustomerName(company_name);
            searchDEDataM.setCustomerLName(lastname);
            searchDEDataM.setApplicationNo(application_no);
            searchDEDataM.setRegisterNo(register_no);
            searchDEDataM.setChannel(channel);
            searchDEDataM.setDealerCode(dealer_code);
            searchDEDataM.setCustomerType(customer_type);
            searchDEDataM.setSortBy(sort_by);
            searchDEDataM.setPriority(priority);
            searchDEDataM.setRequestID(request_id);
            searchDEDataM.setGroupID(groupID);
            //Wiroon 20100315
            searchDEDataM.setCo_citizenID(co_citizen_id);
            searchDEDataM.setCo_customerFName(co_fname);
            searchDEDataM.setCo_customerLName(co_lname);

            getRequest().getSession().setAttribute("SEARCH_DE_DATAM", searchDEDataM);
        } else {
            citizen_id = searchDEDataM.getCitizenID();
            company_name = searchDEDataM.getCustomerName();
            lastname = searchDEDataM.getCustomerLName();
            application_no = searchDEDataM.getApplicationNo();
            register_no = searchDEDataM.getRegisterNo();
            channel = searchDEDataM.getChannel();
            dealer_code = searchDEDataM.getDealerCode();
            customer_type = searchDEDataM.getCustomerType();
            sort_by = searchDEDataM.getSortBy();
            priority = searchDEDataM.getPriority();
            request_id = searchDEDataM.getRequestID();
			 groupID = searchDEDataM.getGroupID();
            //Wiroon 20100315
            co_citizen_id = searchDEDataM.getCo_citizenID();
            co_fname = searchDEDataM.getCo_customerFName();
            co_lname = searchDEDataM.getCo_customerLName();

        }

        try {
            StringBuffer sql = new StringBuffer();
            ValueListM valueListM = new ValueListM();
            int index = 0;
            logger.debug("searchType = " + searchType);
            if (OrigConstant.SEARCH_TYPE_IMAGE.equals(searchType)) {

                sql
                        .append("SELECT IR.REQUEST_ID, IR.FIRST_NAME, IR.LAST_NAME, IR.NRIC, IR.LOAN_TYPE, IR.MAIN_CUSTOMER_TYPE, IR.LASTUPD_DATE, IR.LASTUPD_BY, IA.ATTACH_ID ");
                sql.append("FROM IMG_REQUEST IR, APPLICATION_IMG_REQUEST AIR, IMG_ATTACH IA ");
                sql.append("WHERE IR.REQUEST_ID = AIR.IMG_REQUEST ");
                sql.append("AND AIR.IMG_REQUEST = IA.IMG_REQUEST ");

                sql.append("AND REQUEST_STATUS <> ?");
                valueListM.setString(++index, OrigConstant.IMG_STATUS_COMPLETE);

                if (!ORIGUtility.isEmptyString(citizen_id)) {
                    sql.append("AND IR.NRIC = ? ");
                    valueListM.setString(++index, citizen_id);
                }
                if (!ORIGUtility.isEmptyString(company_name)) {
                    sql.append("AND IR.FIRST_NAME = ? ");
                    valueListM.setString(++index, company_name);
                }
                if (!ORIGUtility.isEmptyString(lastname)) {
                    sql.append("AND IR.LAST_NAME = ? ");
                    valueListM.setString(++index, lastname);
                }
                if (!ORIGUtility.isEmptyString(customer_type)) {
                    sql.append("AND IR.MAIN_CUSTOMER_TYPE = ? ");
                    valueListM.setString(++index, customer_type);
                }
                if (!ORIGUtility.isEmptyString(request_id)) {
                    sql.append("AND IR.REQUEST_ID = ? ");
                    valueListM.setString(++index, request_id);
                }
                if (!ORIGUtility.isEmptyString(sort_by)) {
                    sql.append("ORDER BY IR." + sort_by);
                } else {
                    sql.append("ORDER BY IR.LASTUPD_DATE ");
                }
                System.out.println("sysout Search Application SQL>>> " + sql);
                logger.debug("Search Application SQL>>> " + sql);

                valueListM.setSQL(String.valueOf(sql));
                valueListM.setNextPage(false);
                valueListM.setItemsPerPage(10);
                valueListM.setReturnToAction("page=DE_SEARCH_IMAGE_SCREEN");
                getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
                nextAction = "action=ValueListWebAction";

            } else {
                /*sql	//	original
                        .append("SELECT A.PRIORITY, A.UPDATE_DATE, A.APPLICATION_NO, P.THNAME, P.THSURN, P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTYP, A.DEALER_CODE, A.SRCOFCUS, A.APPLICATION_RECORD_ID, A.APPLICATION_STATUS, A.JOB_STATE, A.CMR_CODE, AC.PERSONAL_TYPE, L.MARKETING_CODE, A.GROUP_APP_ID ");
                if (OrigConstant.SERACH_TYPE_ENQUIRY.equals(searchType)) {
                    sql.append(" ,V.CONTRACT_NO ");
                }                
                
                sql.append("FROM ORIG_APPLICATION A, ORIG_PERSONAL_INFO AC, HPMSHP00 P, USER_BRANCH B, ORIG_LOAN L ");
                if (!ORIGUtility.isEmptyString(register_no) || OrigConstant.SERACH_TYPE_ENQUIRY.equals(searchType)) {
                    sql.append(", ORIG_VEHICLE V ");
                }*/
                //	Tanawat 20110721
                sql	//	original
                        .append("SELECT A.PRIORITY, to_char(A.UPDATE_DATE,'dd/mm/yyyy hh24:mi:ss'), A.APPLICATION_NO, P.TH_FIRST_NAME, P.TH_LAST_NAME, P.IDNO, A.LOAN_TYPE, A.UPDATE_BY, P.CUSTOMER_TYPE, A.DEALER_CODE, A.SRCOFCUS, A.APPLICATION_RECORD_ID, A.APPLICATION_STATUS, A.JOB_STATE, A.CMR_CODE, P.PERSONAL_TYPE, L.MARKETING_CODE, A.GROUP_APP_ID ");
                if (OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)) {
                    sql.append(" ,V.CONTRACT_NO ");
                }                
                
                sql.append("FROM ORIG_APPLICATION A, ORIG_PERSONAL_INFO P, USER_BRANCH B, ORIG_LOAN L ");
                if (!ORIGUtility.isEmptyString(register_no) || OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)) {
                    sql.append(", ORIG_VEHICLE V ");
                }
                //Wiroon 20100315
//                if (!ORIGUtility.isEmptyString(co_citizen_id) || !ORIGUtility.isEmptyString(co_fname) || !ORIGUtility.isEmptyString(co_lname)
//                        || OrigConstant.SERACH_TYPE_ENQUIRY.equals(searchType)) {
//                    sql.append(", AHP200711.HPMSHP00 co_ac ");
//                }

                sql.append("WHERE A.APPLICATION_RECORD_ID = P.APPLICATION_RECORD_ID ");
                //sql.append("      AND AC.CMPCDE = P.CMPCDE ");
                //sql.append("      AND AC.IDNO = P.IDNO ");
                sql.append("      AND A.AREA_CODE = B.AREA ");
                //sql.append("      AND AC.CMPCDE = B.CMPCDE ");
                sql.append("	  AND A.APPLICATION_RECORD_ID = L.APPLICATION_RECORD_ID(+) ");
                if (!ORIGUtility.isEmptyString(register_no) || OrigConstant.SEARCH_TYPE_ENQUIRY.equals(searchType)) {
                    sql.append("  AND A.APPLICATION_RECORD_ID=V.APPLICATION_RECORD_ID(+) ");
                }
                
                //Wiroon 20100315
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
                        sql.append("AND P.TH_LAST_NAME = ? ");
                        valueListM.setString(++index, co_lname);
                    }
                }
                
                
                if (!ORIGUtility.isEmptyString(application_no) || !ORIGUtility.isEmptyString(register_no) || !ORIGUtility.isEmptyString(dealer_code)
                        || !ORIGUtility.isEmptyString(customer_type) || !ORIGUtility.isEmptyString(channel)) {

                    sql.append("AND P.PERSONAL_TYPE = ? ");
                    valueListM.setString(++index, OrigConstant.PERSONAL_TYPE_APPLICANT);
                }

                if ("Draft".equals(searchType)) {
                    sql.append("AND A.APPLICATION_STATUS = ? ");
                    valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.DRAFT);

                    sql.append("AND A.JOB_STATE = ? ");
                    valueListM.setString(++index, ORIGWFConstant.JobState.DE_DRAFT_STATE);
                } else if ("Pending".equals(searchType)) {
                    sql.append("AND A.APPLICATION_STATUS = ? ");
                    valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.PENDING);

                    sql.append("AND A.JOB_STATE = ? ");
                    valueListM.setString(++index, ORIGWFConstant.JobState.DE_PENDING_STATE);
                } else if ("Rework".equals(searchType)) {
                    sql.append("AND A.APPLICATION_STATUS = ? ");
                    valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.REWORK);

                    sql.append("AND A.JOB_STATE = ? ");
                    valueListM.setString(++index, ORIGWFConstant.JobState.DE_REWORK_STATE);
                } else if ("Inbox".equals(searchType)) {
                    sql.append("AND A.APPLICATION_STATUS = ? ");
                    valueListM.setString(++index, ORIGWFConstant.ApplicationStatus.INITIAL);

                    sql.append("AND A.JOB_STATE = ? ");
                    valueListM.setString(++index, ORIGWFConstant.JobState.DE_INITIAL_STATE);
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
				if(!ORIGUtility.isEmptyString(groupID)){
				    sql.append("AND A.GROUP_APP_ID = ? ");
				    valueListM.setString(++index,groupID);
				}
                

                if (!ORIGUtility.isEmptyString(sort_by)) {
                    sql.append("ORDER BY " + sort_by);
                } else {
                    sql.append("ORDER BY PRIORITY, A.CREATE_DATE ");
                }

                System.out.println("sysout Search Application SQL>>> " + sql);
                logger.debug("Search Application SQL>>> " + sql);
                valueListM.setSQL(String.valueOf(sql));
                valueListM.setNextPage(false);
                valueListM.setItemsPerPage(10);
                valueListM.setReturnToAction("page=DE_SEARCH_SCREEN");
                getRequest().getSession().setAttribute("VALUE_LIST", valueListM);
                nextAction = "action=ValueListWebAction";
            }

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
