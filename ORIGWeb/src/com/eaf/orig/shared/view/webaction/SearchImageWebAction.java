package com.eaf.orig.shared.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.util.ValueListHandler;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
//import com.eaf.orig.shared.dao.SearchApplicationUtilDAO;
import com.eaf.orig.shared.model.SearchImageM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author 
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SearchImageWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(SearchImageWebAction.class);

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#toEvent()
	 */
	public Event toEvent() {
		return null;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#requiredModelRequest()
	 */
	public boolean requiredModelRequest() {

		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#processEventResponse(EventResponse)
	 */
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#preModelRequest()
	 */
	public boolean preModelRequest() {
		System.out.println("*****Search Application*****");
		try {

			getRequest().getSession().removeAttribute("ERROR_MSG_IMG");

			Vector result = new Vector();
			Vector resultFilter = new Vector();
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			userM = (userM == null) ? new UserDetailM() : userM;
			String username = userM.getUserName();
			System.out.println("userName="+username);
			username = (username == null) ? "" : username;
			

			/***** Criteria *****/
			String requestId = (getRequest().getParameter("requestId") != null) ? getRequest().getParameter("requestId") : "";
//			String org = (getRequest().getParameter("org") != null) ? getRequest().getParameter("org") : "";
//			String productType = (getRequest().getParameter("productType") != null) ? getRequest().getParameter("productType") : "";
			String channelCode = (getRequest().getParameter("channelCode") != null) ? getRequest().getParameter("channelCode") : "";
//			String createDate = (getRequest().getParameter("createDate") != null) ? getRequest().getParameter("createDate") : "";
			String faxInDateFrom = (getRequest().getParameter("faxInDateFrom") != null) ? getRequest().getParameter("faxInDateFrom") : "";
			String faxInDateTo = (getRequest().getParameter("faxInDateTo") != null) ? getRequest().getParameter("faxInDateTo") : "";
			String nric = (getRequest().getParameter("nric") != null) ? getRequest().getParameter("nric") : "";
			String firstName = (getRequest().getParameter("firstName") != null) ? getRequest().getParameter("firstName") : "";
			String lastName = (getRequest().getParameter("lastName") != null) ? getRequest().getParameter("lastName") : "";
			String sortBy = (getRequest().getParameter("sortBy") != null) ? getRequest().getParameter("sortBy") : "";
			String dealer = (getRequest().getParameter("dealer") != null) ? getRequest().getParameter("dealer") : "";
			String appNo = (getRequest().getParameter("appNo") != null) ? getRequest().getParameter("appNo") : "";
			String mainCustomerType = (getRequest().getParameter("mainCustomerType") != null) ? getRequest().getParameter("mainCustomerType") : "";
			String dealerFaxNo = (getRequest().getParameter("dealerFaxNo") != null) ? getRequest().getParameter("dealerFaxNo") : "";

			System.out.println("channelCode="+channelCode);
			
			String searchType = "searchImage";
			if(appNo!=null){
			    appNo=ORIGDisplayFormatUtil.lrtrim(appNo);
			}
			if(firstName!=null){
			    firstName=ORIGDisplayFormatUtil.lrtrim(firstName);
			}
			if(lastName!=null){
			    lastName=ORIGDisplayFormatUtil.lrtrim(lastName);    
			}
			if(requestId!=null){
			    requestId=ORIGDisplayFormatUtil.lrtrim(requestId);    
			}
			SearchImageM searchImage = new SearchImageM();
			searchImage.setRequestID(requestId);
//			searchImage.setOrg(org);
//			searchImage.setProduct(productType);
//			searchImage.setMktSourceCode(channelCode);
			  //trim Space
			 			        			
			searchImage.setBusClassID(channelCode);
			searchImage.setFaxInDateFrom(faxInDateFrom);
			searchImage.setFaxInDateTo(faxInDateTo);
			searchImage.setNric(nric);
			searchImage.setFirstName(firstName);
			searchImage.setLastName(lastName);
			searchImage.setApplicationNo(appNo);
			searchImage.setSortBy(sortBy);
			searchImage.setDealer(dealer);
			searchImage.setDealerFaxNo(dealerFaxNo);
			searchImage.setMainCustomerType(mainCustomerType);
			getRequest().getSession().setAttribute("SEARCH_TYPE", searchType);	
			getRequest().getSession().setAttribute(searchType, searchImage);
			/***** End Criteria *****/


//			SearchApplicationUtilDAO dao = ORIGDAOFactory.getSearchApplicationUtilDAO();
			System.out.println("*****Search Type***** :" + searchType);

//			result = dao.getSearchImage(searchType, requestId, dealer,channelCode,username,faxInDateFrom,faxInDateTo,nric,firstName,lastName,sortBy,appNo,mainCustomerType,dealerFaxNo);
			
			
			result = PLORIGEJBService.getORIGDAOUtilLocal().getSearchImage(searchType, requestId, dealer, channelCode, username, faxInDateFrom, faxInDateTo, nric, firstName, lastName, sortBy, appNo, mainCustomerType, dealerFaxNo);
			
			System.out.println("*****Search Application*****Result :" + result.size());

			/*****End Filter Application*****/

			/*****set result to valueList handler*****/
			ValueListHandler list = new ValueListHandler();
			list.setList(result);
			/*****save list into session*****/
			getRequest().getSession().setAttribute("SEARCH_LIST", list);

			getRequest().getSession().removeAttribute("startIndex");
			getRequest().getSession().removeAttribute("count");
			getRequest().getSession().removeAttribute("pageIndex");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}


	/**
	 * @see com.eaf.j2ee.pattern.view.webaction.WebAction#getNextActivityType()
	 */
	public int getNextActivityType() {
		return 0;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
