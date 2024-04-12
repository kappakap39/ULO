package com.eaf.orig.formcontrol.view.webaction;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGFormTab;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class LoadMenuFormWebAction extends WebActionHelper implements WebAction {
	static Logger log = Logger.getLogger(LoadMenuFormWebAction.class);
	
	 /* @see com.bara.j2ee.pattern.view.webaction.WebAction#toEvent()
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
	
	public boolean preModelRequest() { 
		try{
			System.out.println("===getRequest().getParameter===> " +getRequest().getParameter("currentTab"));
			
			boolean flag = false;
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			ORIGFormHandler ORIGForm = (ORIGFormHandler) getRequest().getSession().getAttribute("ORIGForm");

			// get user roles
			Vector  userRoles=new Vector();

			userRoles = userM.getRoles();

			Vector userProfile = userM.getUserProfiles();
			String businessClassId = "ORIG_BUS"; //"BUS_NAOS";	
			
			// should get form id (should get it as parameter)****************************************************
			String formID = getRequest().getParameter("formID");			

			String role = (String)userRoles.get(0);
			
			
			//set role by jobState
			String jobState = "";
			String roleTemp = "";
			/*if(ORIGForm!=null){
				jobState =  ORIGForm.getAppForm().getJobState(); 
				System.out.println("jobState is :"+jobState);
				if(jobState.equals("ST0401")||jobState.equals("ST0402")||jobState.equals("ST1401")||jobState.equals("ST1402")||jobState.equals("ST0403")||jobState.equals("ST1403")){
						roleTemp = "DB";
				}else if(jobState.equals("ST0301")||jobState.equals("ST0302")||jobState.equals("ST0400")||jobState.equals("ST1301")||jobState.equals("ST1302")||jobState.equals("ST1400")){
						roleTemp = "PD";
				}else if(jobState.equals("ST0100")||jobState.equals("ST0101")||jobState.equals("ST0103")||jobState.equals("ST0200")){
						roleTemp = "DE";
				}else{
						roleTemp = "UW";
				}
			}*/
			
			//end set
			
			// set current role
			//role = getRequest().getParameter("role");
			//userRoles.set(0,role);
			//userM.setRoles(userRoles);
			// end set
			
			log.debug("LoadApplicationWebAction : userRoles(0) = "+userRoles.get(0));			
						

			// get all sub form from cache by user roles and formID	
			if(ORIGForm==null) ORIGForm = new ORIGFormHandler();                        		
			//ORIGForm.loadSubForms(userRoles,formID);			
			ORIGForm.setSubForms(ORIGUtility.filterSubformByMainCusType(ORIGForm.getSubForms(), ORIGForm.getAppForm().getMainCustomerType()));
			
			log.debug("LoadApplicationWebAction : formID="+formID);	
			log.debug("LoadApplicationWebAction : subform size="+ORIGForm.getSubForms().size());	
		
			//ORIGForm.setFormID(formID);
			ORIGForm.setBusinessClassID(businessClassId);
	
			
			// load Tabs by subForm
			/*Vector subForms = new Vector(ORIGForm.getSubForms().values());
			ORIGFormUtil naosUtil = ORIGFormUtil.getInstance();
			HashMap formTabs = naosUtil.getTabsBySubForms(subForms);
			System.out.println("formTabs  = " + formTabs);
			
			// get default tab and set it active
			String activeFormTab = naosUtil.getDefaultActiveFormTabIDByUserRoles(userRoles);			
			System.out.println("activeFormTab = " + activeFormTab);
			ORIGFormTab formTab = (ORIGFormTab)formTabs.get(activeFormTab);
			if(formTab!=null){
				formTab.setIsActive(true);
			}*/
			
			// put all tabs into XenozForm		
			//ORIGForm.setTabs(formTabs);
			System.out.println("ORIGForm Tabs = "  + ORIGForm.getTabs());
			
			//set current tab into XenozForm 
			if (null == ORIGForm.getCurrentTab() || ORIGForm.getCurrentTab().equals(null)  ){ // if 1
				log.debug("current tab ==============>default ===="+ORIGForm.getCurrentTab());
				//ORIGForm.setCurrentTab(xenozUtil.getActiveTab(formTabs));
				ORIGForm.setCurrentTab(this.getRequest().getParameter("currentTab"));				
				log.debug("set current in frst ===getRequest().getParameter===> " +getRequest().getParameter("currentTab"));
				log.debug("set current in frst ====================>>>> 111 = " +ORIGForm.getCurrentTab());
			} // end if1
			
			//set xenoz form into session
            getRequest().getSession().setAttribute("ORIGForm",ORIGForm);
			
			/* Tiwa added */
            if(getRequest().getParameter("DETaskList")!=null && getRequest().getParameter("DETaskList").equals("Y")){
            	System.out.println("redirecting to "+getRequest().getContextPath()+"/FrontController?action=searchImg&handleForm=Y&tab_ID=TAB_SEARCH");
				getResponse().sendRedirect(getRequest().getContextPath()+"/FrontController?action=searchImg&handleForm=Y&tab_ID=TAB_SEARCH");
            }
            /* end Tiwa added */
				
			log.debug("************** SETUP SEARCH TYPE **********************");
			log.debug(">>>>>>> getRequest().getParameter('searchType')="+getRequest().getParameter("searchType"));
			//get search type form left menu and set variable in session
			if( getRequest().getParameter("searchType")!=null){
				String searchType = getRequest().getParameter("searchType");	
				getRequest().getSession().setAttribute("SEARCH_TYPE", searchType);
				getRequest().getSession().removeAttribute("VSEARCHAPP");
				getRequest().getSession().removeAttribute("SEARCH_APP");
				getRequest().getSession().removeAttribute("SEARCH_LIST");				
	
			}
			log.debug(">>>>>>> getRequest().getSession().getAttribute('SEARCH_TYPE')="+getRequest().getSession().getAttribute("SEARCH_TYPE"));		
			log.debug("******************************************************************");
		
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public int getNextActivityType(){ 
		return FrontController.PAGE;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
