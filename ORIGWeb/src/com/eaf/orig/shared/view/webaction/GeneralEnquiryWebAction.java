/*
 * Created on Oct 4, 2007
 * Created by Sankom Sanpunya
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

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.SearchDataM; 


/**
 * @author Sankom Sanpunya
 * 
 * Type: GeneralEnquiryWebAction
 */
public class GeneralEnquiryWebAction extends WebActionHelper implements
		WebAction {
	static Logger logger = Logger.getLogger(GeneralEnquiryWebAction.class);

	String nextAction = "";

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
		logger.debug("generalEnquiryWebAction[Start]");

		getRequest().getSession().removeAttribute("VALUE_LIST");
		getRequest().getSession().removeAttribute("mode");
		UserDetailM userM = (UserDetailM) getRequest().getSession()
				.getAttribute("ORIGUser");
		String mode = getRequest().getParameter("mode");
		String orderBy = "";
		SearchDataM searchM = new SearchDataM();

		ORIGFormHandler form = (ORIGFormHandler) getRequest().getSession()
				.getAttribute("ORIGForm");
		if (form == null)
			form = new ORIGFormHandler();

		Vector userRoles = userM.getRoles();
		// set current role
		String role = getRequest().getParameter("role");
		userRoles.set(0, role);
		userM.setRoles(userRoles);
		// end set

		logger.debug("LoadApplicationWebAction : userRoles(0) = "
				+ userRoles.get(0));

		//searchM.setApplicationNo(getRequest().getParameter("appNo"));
		//searchM.setAccountNo(getRequest().getParameter("accNo"));
		//searchM.setNric(getRequest().getParameter("nric"));
		//searchM.setFaxDateFrom(getRequest().getParameter("appDateFrom"));
		//searchM.setFaxDateTo(getRequest().getParameter("appDateTo"));
		//searchM.setGivenName(getRequest().getParameter("givenName"));
		//searchM.setSureName(getRequest().getParameter("surName"));
		//searchM.setImgId(getRequest().getParameter("imageID"));
		//searchM.setChannelCode(getRequest().getParameter("channel"));
		//searchM.setVehicleNo(getRequest().getParameter("vehicleNo"));
		//searchM.setDealerCode(getRequest().getParameter("dealerName"));
		//searchM.setDealerFaxNo(getRequest().getParameter("dealerFax"));
		searchM.setSortBy(getRequest().getParameter("sorting"));

		//searchM.setBusinessEntity(getRequest().getParameter("businessEntity"));
		//searchM.setMainCustomerType(getRequest().getParameter(
			//	"mainCustomerType"));
		//searchM.setCreditFacility(getRequest().getParameter("creditFacility"));
		//searchM.setPriority(getRequest().getParameter("priority"));

		form.setSearchForm(searchM);

		if (searchM.getSortBy() != null && !searchM.getSortBy().equals("")) {
			orderBy = searchM.getSortBy();
		} else {
			orderBy = "0";
		}

		if (mode != null) {
			getRequest().getSession().removeAttribute("SearchMenu");
			getRequest().getSession().setAttribute("mode", mode);
			StringBuffer sql = new StringBuffer();
			//	sql.append("select
			// (P.ENG_FIRST_NAME||DECODE(P.ENG_LAST_NAME,'','','
			// '||P.ENG_LAST_NAME))
			// NAME,p.citizen_id,a.application_no,ac.account_no,d.dealer_desc,o.org_desc,a.application_status,a.application_record_id,p.PERSONAL_INFO_TYPE,a.update_date,a.update_by,
			// ");
			//// sql.append("to_char(decode(a.ENTRY_TYPE,'',
			// A.APPLY_RECEIVE_DATE, i.FAX_IN_DATE_FIRST) , 'DD/MM/YYYY
			// HH24:MI:SS'), to_char(a.DE_COMPLETE_DATE, 'DD/MM/YYYY
			// hh24:mi:ss'), ");
			//	sql.append("decode(a.ENTRY_TYPE,'', A.APPLY_RECEIVE_DATE,
			// i.FAX_IN_DATE_FIRST) ARRIVAL_DATE,a.DE_COMPLETE_DATE ");
			//	sql.append(", v.VEHICLE_NO, a.BUSINESS_ENTITY,
			// a.MAIN_CUSTOMER_TYPE, a.CREDIT_FACILITY, d.dealer_desc,
			// d.DEALER_FAX_NO, a.DEALER_CODE, a.PRIORITY");
			//	sql.append("from application a, personal_info p, account
			// ac,img_request i, application_img_request app, dealer d,
			// organization o , vehicle_detail v ");
			//	sql.append("where a.application_record_id =
			// p.application_record_id ");
			sql
					.append("select decode(P.MAIN_CUSTOMER_TYPE,'Individual',(P.ENG_FIRST_NAME||DECODE(P.ENG_LAST_NAME,'','',' '||P.ENG_LAST_NAME)), P.CORP_COMPANY_NAME) NAME, ");
			sql
					.append("decode(p.MAIN_CUSTOMER_TYPE,'Individual', p.citizen_id, p.CORP_COMPANY_ROC_NO)  NRIC, a.application_no, ac.account_no, (d.dealer_desc) SOURCE, o.org_desc, a.application_status, ");
			sql
					.append("a.application_record_id, p.PERSONAL_INFO_TYPE, a.update_date, a.update_by, ");
			//	sql.append("to_char(decode(a.ENTRY_TYPE,'', A.APPLY_RECEIVE_DATE,
			// i.FAX_IN_DATE_FIRST) , 'DD/MM/YYYY HH24:MI:SS'),
			// to_char(a.DE_COMPLETE_DATE, 'DD/MM/YYYY hh24:mi:ss'), ");
			sql
					.append("decode(a.ENTRY_TYPE,'', A.APPLY_RECEIVE_DATE, i.FAX_IN_DATE_FIRST)  ARRIVAL_DATE, ");
			sql.append("a.DE_COMPLETE_DATE");
			sql
					.append(", v.VEHICLE_NO, a.BUSINESS_ENTITY, p.MAIN_CUSTOMER_TYPE, a.CREDIT_FACILITY, ");
			sql
					.append("decode(d.dealer_id,null,'',d.dealer_id||'/'||d.dealer_desc) DEALER_NAME, d.DEALER_FAX_NO, a.DEALER_CODE, a.PRIORITY,a.JOB_STATE, a.channel_code  ");
			sql
					.append("from application a, personal_info p, account ac, img_request i, application_img_request app, dealer d, organization o , vehicle_detail v ");
			sql
					.append("where  a.application_record_id = p.application_record_id ");
			/*if ((searchM.getNric() == null || searchM.getNric().equals(""))
					&& (searchM.getGivenName() == null || searchM
							.getGivenName().equals(""))
					&& (searchM.getSureName() == null || searchM.getSureName()
							.equals(""))) {
				sql.append("and p.PERSONAL_INFO_TYPE = 'Applicant' ");
			}*/

			sql
					.append("and a.application_record_id = v.application_record_id ");

			sql
					.append("and a.application_record_id = app.application_record_id(+) and app.IMG_REQUEST = i.REQUEST_ID(+) ");
			sql
					.append("and a.account_id = ac.account_id(+) and a.dealer_code = d.dealer_id(+) and a.org_id = o.org_id(+) AND ");
			//sql.append(" A.BUSINESS_CLASS_ID IN(select bus_class_id from
			// buss_class_grp_map where buss_grp_id in (select pb.bus_grp_id
			// from profile_bus_grp pb, user_profile up where up.profile_id =
			// pb.profile_id and up.user_name = '"+userM.getUserName()+"')) AND
			// ");
			//	sql.append("A.BUSINESS_CLASS_ID IN
			// "+getStringStatementIn(getAccessBusinessClass(userM.getUserName()))+"
			// AND ");

			/*if (searchM.getApplicationNo() != null
					&& !searchM.getApplicationNo().equals("")) {
				sql.append("a.application_no = '" + searchM.getApplicationNo()
						+ "' AND ");
			}
			if (searchM.getNric() != null && !searchM.getNric().equals("")) {
				sql
						.append("decode(p.MAIN_CUSTOMER_TYPE,'Individual', p.citizen_id, p.CORP_COMPANY_ROC_NO) = '"
								+ searchM.getNric() + "' AND ");
			}
			if (searchM.getFaxDateFrom() != null
					&& !searchM.getFaxDateFrom().equals("")) {
				logger.debug("faxDateFrom.............= "
						+ searchM.getFaxDateFrom());
				sql.append("i.fax_in_date_first >= to_date('"
						+ searchM.getFaxDateFrom() + "','dd/mm/yyyy')  AND ");
			}
			if (searchM.getFaxDateTo() != null
					&& !searchM.getFaxDateTo().equals("")) {
				logger.debug("faxDateTo.............= "
						+ searchM.getFaxDateTo());
				sql.append("i.fax_in_date_first <= to_date('"
						+ searchM.getFaxDateTo()
						+ " 23:59:59','dd/mm/yyyy hh24:mi:ss')  AND ");
			}
			if (searchM.getGivenName() != null
					&& !searchM.getGivenName().equals("")) {
				sql
						.append("decode(p.MAIN_CUSTOMER_TYPE,'Individual', p.eng_first_name, p.CORP_COMPANY_NAME)   = '"
								+ searchM.getGivenName() + "' AND ");*/
			}
			//	if(searchM.getSureName()!= null &&
			// !searchM.getSureName().equals("")){
			//		sql.append("p.eng_last_name = '"+searchM.getSureName()+"' AND ");
			//	}
			//	if(searchM.getImgId()!= null && !searchM.getImgId().equals("")){
			//		sql.append("i.request_id = '"+searchM.getImgId()+"' AND ");
			//	}
			//	if(searchM.getChannelCode()!= null &&
			// !searchM.getChannelCode().equals("ALL")){
			/*if (searchM.getChannelCode() != null
					&& !searchM.getChannelCode().equals("")) {
				sql.append("a.channel_code = '" + searchM.getChannelCode()
						+ "' AND ");
			}*/
			//	}
			//if(searchM.getVehicle()!= null &&
			// !searchM.getVehicle().equals("")){
			//	sql.append("DEALER_ID LIKE
			// '%"+searchM.getVehicle().toUpperCase()+"%' AND ");
			//}
			/*if (searchM.getDealerCode() != null
					&& !searchM.getDealerCode().equals("")) {
				sql.append("a.dealer_code = '" + searchM.getDealerCode()
						+ "' AND ");
			}*/

			//	if(searchM.getBusinessEntity()!= null &&
			// !searchM.getBusinessEntity().equals("")){
			//		sql.append("a.BUSINESS_ENTITY = '"+searchM.getBusinessEntity()+"'
			// AND ");
			//	}

			/*if (searchM.getMainCustomerType() != null
					&& !searchM.getMainCustomerType().equals("")) {
				sql.append("p.MAIN_CUSTOMER_TYPE = '"
						+ searchM.getMainCustomerType() + "' AND ");
			}

			if (searchM.getCreditFacility() != null
					&& !searchM.getCreditFacility().equals("")) {
				sql.append("a.CREDIT_FACILITY = '"
						+ searchM.getCreditFacility() + "' AND ");
			}

			if (searchM.getPriority() != null
					&& !searchM.getPriority().equals("")) {
				sql.append("a.PRIORITY = '" + searchM.getPriority() + "' AND ");
			}

			if (searchM.getAccountNo() != null
					&& !searchM.getAccountNo().equals("")) {
				sql.append("ac.ACCOUNT_NO = '" + searchM.getAccountNo()
						+ "' AND ");
			}

			if (searchM.getDealerFaxNo() != null
					&& !searchM.getDealerFaxNo().equals("")) {
				sql.append("d.DEALER_FAX_NO = '" + searchM.getDealerFaxNo()
						+ "' AND ");
			}

			if (sql.toString().trim().endsWith("AND")) {
				sql.delete(sql.toString().lastIndexOf("AND"), sql.toString()
						.length());
			}

			if (sql.toString().trim().endsWith("WHERE")) {
				sql.delete(sql.toString().lastIndexOf("WHERE"), sql.toString()
						.length());
			}

			switch (Integer.parseInt(orderBy)) {
			case 1:
				sql.append(" ORDER BY p.eng_first_name");
				break;
			case 2:
				sql.append(" ORDER BY NRIC");
				break;
			case 3:
				sql.append(" ORDER BY a.application_no");
				break;
			case 4:
				sql.append(" ORDER BY ac.account_no");
				break;
			case 5:
				sql.append(" ORDER BY DEALER_NAME");
				break;
			case 6:
				sql.append(" ORDER BY o.org_desc");
				break;
			case 7:
				sql.append(" ORDER BY a.application_status");
				break;
			case 8:
				sql.append(" ORDER BY a.DE_COMPLETE_DATE");
				break;
			case 9:
				sql.append(" ORDER BY p.PERSONAL_INFO_TYPE");
				break;
			case 10:
				sql.append(" ORDER BY ARRIVAL_DATE");
				break;
			case 11:
				sql.append(" ORDER BY a.update_date");
				break;
			case 12:
				sql.append(" ORDER BY a.update_by");
				break;
			case 13:
				sql.append(" ORDER BY v.VEHICLE_NO");
				break;
			case 14:
				sql.append(" ORDER BY a.BUSINESS_ENTITY");
				break;
			case 15:
				sql.append(" ORDER BY a.MAIN_CUSTOMER_TYPE");
				break;
			case 16:
				sql.append(" ORDER BY a.CREDIT_FACILITY");
				break;
			case 17:
				sql.append(" ORDER BY d.dealer_desc");
				break;
			case 18:
				sql.append(" ORDER BY d.DEALER_FAX_NO");
				break;
			case 19:
				sql.append(" ORDER BY a.DEALER_CODE");
				break;
			case 20:
				sql.append(" ORDER BY a.PRIORITY");
				break;
			default:
				sql.append(" ORDER BY p.eng_first_name");
				break;
			}
			System.out.println("2 : " + String.valueOf(sql));
			try {

				ValueListM valueListM = new ValueListM();
				valueListM.setSQL(String.valueOf(sql));
				valueListM.setNextPage(false);
				valueListM.setItemsPerPage(10);
				valueListM.setReturnToPage("GENERAL_ENQUIRY");
				getRequest().getSession()
						.setAttribute("VALUE_LIST", valueListM);
				nextAction = "action=VALUELIST_WEBACTION";

				getRequest().getSession().setAttribute("MENU",
						"GENERAL_ENQUIRY");
				getRequest().getSession().setAttribute("searchType", null);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.debug("load menu ="
				+ getRequest().getSession().getAttribute("SearchMenu"));*/
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
