package com.eaf.orig.formcontrol.view.form;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.AddressDataM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.SearchDataM;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.view.form.ORIGFormTab;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.shared.view.form.properties.FormBusinessClassProperties;
import com.eaf.orig.shared.view.form.properties.SubFormProperties;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
@Deprecated
@SuppressWarnings("serial")
public class ORIGFormHandler extends FormHandler implements Serializable,Cloneable{
	static Logger log = Logger.getLogger(ORIGFormHandler.class);

	//
	HashMap subForms;
	HashMap tabs;

	boolean isLoadedSubForms;

	String BusinessClassID;
	String currentTab;
	String mode;
	String formID;
	String ButtonFile;
	String OtherFile;

	SearchDataM searchForm;
	ApplicationDataM appForm;
	ApplicationDataM appClone;
	AddressDataM addressDataM;
	//PersonalInfoDataM personalTmp;
	
	private Object popupForm;
	
	private int subFormIndex = 0;
	
	Vector errorFields;
	Vector notErrorFields;
	
	Vector resultAppMs;
	
	/**
	 * Constructor for NaosFormHandler.
	 */
	public ORIGFormHandler() {
		super();
		subForms = new HashMap();
		tabs = new HashMap();
		appForm = new ApplicationDataM();
		appClone = new ApplicationDataM();
		errorFields = new Vector();
		notErrorFields = new Vector();
		resultAppMs = new Vector();
	}

	public void loadSubForms(Vector userRoles, String formID) {
		
		log.debug(">>>>> Start loadSubForms");	
		log.debug("getIsLoadedSubForms()  = " + getIsLoadedSubForms());
		log.debug("userRoles="+userRoles);
		log.debug("formID="+formID);
		if (getIsLoadedSubForms() == false) {
			// get From Cache
			HashMap h = TableLookupCache.getCacheStructure();
			Vector allSubForm = (Vector) (h.get("SubFormPropertiesCacheDataM"));
			//System.out.println("allSubForm="+allSubForm);
			Vector allRoleSubForm = (Vector) (h.get("SubFormRoleCacheDataM"));
			//System.out.println("allRoleSubForm="+allRoleSubForm);
			Vector allFormBusiness = (Vector) (h.get("FormBusinessClassCacheDataM"));
			//System.out.println("allFormBusiness="+allFormBusiness);
			//set button and other File
			

			for (int i = 0; i < allFormBusiness.size(); i++) {
				FormBusinessClassProperties form = (FormBusinessClassProperties) allFormBusiness.elementAt(i);
				
				


				if (formID.equals(form.getFormID())) {
					

					//System.out.println("buttton File name" + form.getButtonFile());
					//System.out.println("Other File name" + form.getOtherFile());
					setButtonFile(form.getButtonFile());
					setOtherFile(form.getOtherFile());
					log.debug("button file="+form.getButtonFile());
					break;
				}
			}
			
			ORIGFormUtil naosUtil = ORIGFormUtil.getInstance();
			//HashMap allSubFormIDInRole = naosUtil.getSubFormIDByRole(allRoleSubForm, userRoles, formID);
			HashMap allSubFormIDInRole = naosUtil.getSubFormIDByUserRole(allRoleSubForm, (String) userRoles.elementAt(0), formID);
			
			//System.out.println("allSubFormIDInRole = " + allSubFormIDInRole);
			try {
				//loop for put sub form to hash map
				//log.debug("//loop for put sub form to hash map");
				log.debug("allSubFormIDInRole.size()="+allSubFormIDInRole.size());				
				for (int i = 0; i < allSubForm.size(); i++) { //for 1
					SubFormProperties sp = (SubFormProperties) allSubForm.get(i);
					String formIDfromSubForm = sp.getFormID();
					//filter by formID
					//log.debug("//filter by formID i="+i);	
//					log.debug("formIDfromSubForm="+formIDfromSubForm);				
					if (formIDfromSubForm != null && formIDfromSubForm.equals(formID)) { //if 1
						
						
						String className = sp.getSubFormClass();
						String formTab = sp.getFormTab();
						String fileName = sp.getFileName();
						String subFormID = sp.getSubFormID();
						String subformDesc = sp.getSubformDesc();
						String subFormPosition = sp.getSubFormPosition();

						
						if (allSubFormIDInRole.containsKey(subFormID)) {																					
							// if 2							
							if (className != null ) { // if 3
								ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(className)).newInstance());
								// change for naos get seq from role
								int seq = 0;
								try {
									String seqS = (String) allSubFormIDInRole.get(subFormID);
									seq = Integer.parseInt(seqS);
								} catch (Exception e) {
									e.printStackTrace();
								}
								// end change
								subForm.setLocatedTab(formTab);
								subForm.setFileName(fileName);
								subForm.setSubFormID(subFormID);
								subForm.setSubFormSeq(seq);
								subForm.setSubformDesc(subformDesc);
								subForm.setSubFormPosition(subFormPosition);
								//log.debug("put hash map");
								//log.debug("put --> (seq=" + seq + ") (formTab=" + formTab + ") (subFormID=" + subFormID + ") ");
								getSubForms().put(subFormID, subForm);
								log.debug("subFormID = " + subFormID);
							} // end if 3
							//System.out.println("================================");
						} // endif 2
					} // end if 1
				} //end for1
			} catch (Exception e) {
				e.printStackTrace();
			}
			// end of  get From Cache			
			this.setIsLoadedSubForms(true);
		}
		log.debug(">>>>> End loadSubForms");
	}
	
	/**
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#validInSessionScope()
	 */
	public boolean validInSessionScope() {
		return false;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#validateForm(HttpServletRequest)
	 */
	public boolean validateForm(HttpServletRequest request) {
		log.debug(">>>>> Start validateForm");
		ORIGFormUtil formUtil = ORIGFormUtil.getInstance();
		String currentTab = this.getCurrentTab();
		// get from cache which subForm should be used for setting properties
		Vector allSubForms = new Vector(this.getSubForms().values());
		Vector allSubFormNames = new Vector(this.getSubForms().keySet());
		String subFormName = "";
		for (int i = 0; i < allSubFormNames.size(); i++) { // for 1
			subFormName = (String) allSubFormNames.get(i);
			ORIGSubForm subForm = (ORIGSubForm) getSubForms().get(subFormName);
			if (this.getClass() != null && this.getCurrentTab().equalsIgnoreCase(subForm.getLocatedTab())) { // if 1
				// check Roles & Application status for display mode
				// if edit mode , setproperties
				// else do nothing
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				// get user roles
				Vector userRoles = userM.getRoles();
				// please add application status into below method
				/*				
								if (formUtil
									.getDisplayMode(
										subForm.getSubFormID(),
										userRoles,
										this.getAppForm().getJobState(),
										this.getFormID())
									.equalsIgnoreCase(
										DisplayFormatUtil.DISPLAY_MODE_EDIT)) { // if 2				
									subForm.getErrors().clear();
									subForm.validateForm(request, getAppForm());
									System.out.println("SUB FORM HAS ERROR +++++++  >>>>>>>" + subForm.hasErrors());
									if (subForm.hasErrors()) { // if 4
										concadErrors(subForm.getErrors());
										return false;
									} // end if 4
								} // en if 2
				*/
				subForm.validateForm(request, getAppForm());

			} // end if 1
		} // end for 1	
		log.debug(">>>>> End validateForm");
		return true;
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#postProcessForm(HttpServletRequest)
	 */
	public void postProcessForm(HttpServletRequest request) {
	}

	/**
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#setProperties(HttpServletRequest)
	 */
	public void setProperties(HttpServletRequest request) {
		log.debug("***************** Start setProperties ******************");
		ORIGFormUtil formUtil = ORIGFormUtil.getInstance();
		//****************** Start - New Load Form , SubForm ******************
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		//log.debug(">>>>> userM="+userM);		
		Vector userRoles = userM.getRoles();
		//log.debug(">>>>> userRoles="+userRoles);		

		ApplicationDataM ApplicationDataM = (ApplicationDataM) this.getAppForm();
		log.debug(">>>>> ApplicationDataM="+ApplicationDataM.getAppRecordID());		
		if (ApplicationDataM == null) {
			//log.debug(">>>>>>>>> New ApplicationDataM()");			
			ApplicationDataM = new ApplicationDataM();
			this.setAppForm(ApplicationDataM);
		}

		/*log.debug(">>>>> ApplicationDataM.getProductCode()="+ApplicationDataM.getProductCode());
		log.debug(">>>>> ApplicationDataM.getChannelCode()="+ApplicationDataM.getChannelCode());
		log.debug(">>>>> userM.getOrganizeID()="+userM.getOrganizeID());
		*/
		String busID = com.eaf.orig.shared.view.form.util.ORIGFormUtil.getBusinessID(ApplicationDataM.getProductCode(), ApplicationDataM.getChannelCode(), userM.getOrganizeID());
		//log.debug(">>>>> busID="+busID);
		//this.setBusinessClassID(busID);
		this.setBusinessClassID("NAOS_BUS");
		log.debug(">>>>> this.getBusinessClassID()=" + this.getBusinessClassID());

		//String formID = formUtil.getFormIDByBus(busID);
		//formID = (formID == null) ? "" : formID;
		
		String formID = request.getParameter("formID");
		if(formID==null) formID="";
		
/*	commended by Tiwa because formID must be "" for Image Searching
 		if(formID.equals("")){
			formID="DE_FORM";
		}
*/
		
		
//		if (formUtil.checkCurrentMenu(request, NaosConstant.MENU_CA_ADDSUP)) {
//			formID = NaosConstant.FORM_ID_ADD_SUBAPP_FORM;
//		} else if (formUtil.checkCurrentMenu(request, NaosConstant.MENU_CA_INSTANT_CREDIT) || formUtil.checkCurrentMenu(request, NaosConstant.MENU_CA_INSTANT_CREDIT_MAKE_DECISION) || formUtil.checkCurrentMenu(request, NaosConstant.MENU_CA_INSTANT_CREDIT_PRINT_SALE_SLIP)) {
//			formID = NaosConstant.FORM_ID_INSTANT_CREDIT_FORM;
//		}
		log.debug(">>>>> formID=" + formID);

		String currentTab = request.getParameter("currentTab");
		currentTab = (currentTab == null) ? "" : currentTab;
		log.debug(">>>>> currentTab=" + currentTab);

		if (!formID.equals("") || !currentTab.equals("")) {
			this.setFormID(formID);
			this.setCurrentTab(currentTab);
			this.setBusinessClassID(busID);
			//***********		
			this.getSubForms().clear();
			this.setIsLoadedSubForms(false);
			this.loadSubForms(userRoles, this.getFormID());
			//***********
			Vector tSubForms = new Vector(this.getSubForms().values());
			HashMap formTabs = (HashMap) formUtil.getTabsBySubForms(tSubForms);
			log.debug(">>>>> formTabs=" + formTabs);
			String activeFormTab = this.getCurrentTab();
			ORIGFormTab formTab = (ORIGFormTab) formTabs.get(activeFormTab);
			log.debug(">>>>> formTab=" + formTab);
			if (formTab != null) {
				formTab.setIsActive(true);
			}
			this.setTabs(formTabs);
		}
		//****************** End - New Load Form , SubForm ******************

		// get from cache which subForm should be used for setting properties
		Vector allSubForms = new Vector(this.getSubForms().values());
		Vector allSubFormIDs = new Vector(this.getSubForms().keySet());
		String subFormID = "";
		for (int i = 0; i < allSubFormIDs.size(); i++) { // for 1
			subFormID = (String) allSubFormIDs.get(i);
			ORIGSubForm subForm = (ORIGSubForm) getSubForms().get(subFormID);
			//if (this.getClass() != null && this.getCurrentTab().equalsIgnoreCase(subForm.getLocatedTab())) { // if 1
				// check Roles & Application status for display mode
				// if edit mode , setproperties
				// else do nothing
				// please add application status into below method
				String searchType = (String) request.getSession().getAttribute("searchType");
				if (formUtil.getDisplayMode(subForm.getSubFormID(), userRoles, this.getAppForm().getJobState(), this.getFormID(), searchType).equalsIgnoreCase(ORIGDisplayFormatUtil.DISPLAY_MODE_EDIT)) { // if 2
					//System.out.println("check set pro perty ==========>" + getAppForm());
					subForm.setProperties(request, getAppForm());
				} //end if 2

				//subForm.setProperties(request, getAppForm());

			//} // end if 1
		} // end for 1
		log.debug("***************** End setProperties ******************");
	}
	
	public void loadSubFormsForDrawDown(Vector userRoles, String formID, String drawDownFlag) {
		
		log.debug(">>>>> Start loadSubForms");	
		log.debug("getIsLoadedSubForms()  = " + getIsLoadedSubForms());
		log.debug("userRoles="+userRoles);
		log.debug("formID="+formID);
		if (getIsLoadedSubForms() == false) {
			// get From Cache
			HashMap h = TableLookupCache.getCacheStructure();
			Vector allSubForm = (Vector) (h.get("SubFormPropertiesCacheDataM"));
			log.debug("allSubForm="+allSubForm);
			Vector allRoleSubForm = (Vector) (h.get("SubFormRoleCacheDataM"));
			log.debug("allRoleSubForm="+allRoleSubForm);
			Vector allFormBusiness = (Vector) (h.get("FormBusinessClassCacheDataM"));
			log.debug("allFormBusiness="+allFormBusiness);
			//set button and other File
			
			for (int i = 0; i < allFormBusiness.size(); i++) {
				FormBusinessClassProperties form = (FormBusinessClassProperties) allFormBusiness.elementAt(i);
				
				if (formID.equals(form.getFormID())) {
					
					//System.out.println("buttton File name" + form.getButtonFile());
					//System.out.println("Other File name" + form.getOtherFile());
					setButtonFile(form.getButtonFile());
					setOtherFile(form.getOtherFile());
					log.debug("button file="+form.getButtonFile());
					break;
				}
			}
			
			ORIGFormUtil naosUtil = ORIGFormUtil.getInstance();
			//HashMap allSubFormIDInRole = naosUtil.getSubFormIDByRole(allRoleSubForm, userRoles, formID);
			HashMap allSubFormIDInRole = naosUtil.getSubFormIDByUserRole(allRoleSubForm, (String) userRoles.elementAt(0), formID);
			
			//System.out.println("allSubFormIDInRole = " + allSubFormIDInRole);
			try {
				//loop for put sub form to hash map
				//log.debug("//loop for put sub form to hash map");
				log.debug("allSubFormIDInRole.size()="+allSubFormIDInRole.size());				
				for (int i = 0; i < allSubForm.size(); i++) { //for 1
					SubFormProperties sp = (SubFormProperties) allSubForm.get(i);
					String formIDfromSubForm = sp.getFormID();
					//filter by formID
					//log.debug("//filter by formID i="+i);	
								
					if (formIDfromSubForm != null && formIDfromSubForm.equals(formID)) { //if 1
						
//						log.debug("formIDfromSubForm="+formIDfromSubForm);	
						
						String className = sp.getSubFormClass();
						String formTab = sp.getFormTab();
						String fileName = sp.getFileName();
						String subFormID = sp.getSubFormID();
						String subformDesc = sp.getSubformDesc();
						String subformClass = sp.getSubFormClass();
						String subFormPosition = sp.getSubFormPosition();
						
						
						//if (allSubFormIDInRole.containsKey(subFormID) && !(("Y").equals(drawDownFlag)&&subFormID.equals(OrigConstant.SubformName.PRE_SCORE_SUBFORM))) {
							if (allSubFormIDInRole.containsKey(subFormID) && !(("Y").equals(drawDownFlag))) {
							// if 2							
							if (className != null ) { // if 3
								ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(className)).newInstance());
								// change for naos get seq from role
								int seq = 0;
								try {
									String seqS = (String) allSubFormIDInRole.get(subFormID);
									seq = Integer.parseInt(seqS);
								} catch (Exception e) {
									e.printStackTrace();
								}
								// end change
								subForm.setLocatedTab(formTab);
								subForm.setFileName(fileName);
								subForm.setSubFormID(subFormID);
								subForm.setSubFormSeq(seq);
								subForm.setSubformDesc(subformDesc);
								subForm.setSubFormPosition(subFormPosition);
								subForm.setSubFormClass(subformClass);
								//log.debug("put hash map");
								//log.debug("put --> (seq=" + seq + ") (formTab=" + formTab + ") (subFormID=" + subFormID + ") ");
								getSubForms().put(subFormID, subForm);
								log.debug("subFormID = " + subFormID);
							} // end if 3
							//System.out.println("================================");
						} // endif 2
					} // end if 1
				} //end for1
			} catch (Exception e) {
				e.printStackTrace();
			}
			// end of  get From Cache			
			this.setIsLoadedSubForms(true);
		}
		log.debug(">>>>> End loadSubForms");
	}
	/**
	 * @see com.eaf.j2ee.pattern.view.form.FormHandler#clearForm()
	 */
	public void clearForm() {
	}

	/**
	 * Returns the currentTab.
	 * @return String
	 */
	public String getCurrentTab() {
		return currentTab;
	}

	/**
	 * Returns the formID.
	 * @return String
	 */
	public String getFormID() {
		return formID;
	}

	/**
	 * Sets the currentTab.
	 * @param currentTab The currentTab to set
	 */
	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}

	/**
	 * Sets the formID.
	 * @param formID The formID to set
	 */
	public void setFormID(String formID) {
		this.formID = formID;
	}

	/**
	 * Returns the subForms.
	 * @return HashMap
	 */
	public HashMap getSubForms() {
		return subForms;
	}

	/**
	 * Sets the subForms.
	 * @param subForms The subForms to set
	 */
	public void setSubForms(HashMap subForms) {
		this.subForms = subForms;
	}

	public boolean getIsLoadedSubForms() {
		return isLoadedSubForms;
	}

	public void setIsLoadedSubForms(boolean isLoadedSubForms) {
		this.isLoadedSubForms = isLoadedSubForms;
	}

	/**
	 * Returns the buttonFile.
	 * @return String
	 */
	public String getButtonFile() {
		return ButtonFile;
	}

	/**
	 * Returns the otherFile.
	 * @return String
	 */
	public String getOtherFile() {
		return OtherFile;
	}

	/**
	 * Sets the buttonFile.
	 * @param buttonFile The buttonFile to set
	 */
	public void setButtonFile(String buttonFile) {
		ButtonFile = buttonFile;
	}

	/**
	 * Sets the otherFile.
	 * @param otherFile The otherFile to set
	 */
	public void setOtherFile(String otherFile) {
		OtherFile = otherFile;
	}

	/**
	 * Returns the appForm.
	 * @return ApplicationDataM
	 */
	public ApplicationDataM getAppForm() {
		return appForm;
	}

	/**
	 * Sets the appForm.
	 * @param appForm The appForm to set
	 */
	public void setAppForm(ApplicationDataM appForm) {
		this.appForm = appForm;
	}

	/**
	 * Returns the businessClassID.
	 * @return String
	 */
	public String getBusinessClassID() {
		return BusinessClassID;
	}

	/**
	 * Returns the isLoadedSubForms.
	 * @return boolean
	 */
	public boolean isLoadedSubForms() {
		return isLoadedSubForms;
	}

	/**
	 * Returns the mode.
	 * @return String
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * Returns the tabs.
	 * @return HashMap
	 */
	public HashMap getTabs() {
		return tabs;
	}

	/**
	 * Sets the businessClassID.
	 * @param businessClassID The businessClassID to set
	 */
	public void setBusinessClassID(String businessClassID) {
		BusinessClassID = businessClassID;
	}

	/**
	 * Sets the mode.
	 * @param mode The mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * Sets the tabs.
	 * @param tabs The tabs to set
	 */
	public void setTabs(HashMap tabs) {
		this.tabs = tabs;
	}

    /**
     * @return Returns the addressDataM.
     */
    public AddressDataM getAddressDataM() {
        return addressDataM;
    }
    /**
     * @param addressDataM The addressDataM to set.
     */
    public void setAddressDataM(AddressDataM addressDataM) {
        this.addressDataM = addressDataM;
    }
    /**
     * @return Returns the popupForm.
     */
    public Object getPopupForm() {
        return popupForm;
    }
    /**
     * @param popupForm The popupForm to set.
     */
    public void setPopupForm(Object popupForm) {
        this.popupForm = popupForm;
    }
	/**
	 * @return Returns the searchForm.
	 */
	public SearchDataM getSearchForm() {
		return searchForm;
	}
	/**
	 * @param searchForm The searchForm to set.
	 */
	public void setSearchForm(SearchDataM searchForm) {
		this.searchForm = searchForm;
	}
	/**
	 * @return Returns the errorFields.
	 */
	public Vector getErrorFields() {
		return errorFields;
	}
	/**
	 * @param errorFields The errorFields to set.
	 */
	public void setErrorFields(Vector errorFields) {
		this.errorFields = errorFields;
	}
	/**
	 * @return Returns the notErrorFields.
	 */
	public Vector getNotErrorFields() {
		return notErrorFields;
	}
	/**
	 * @param notErrorFields The notErrorFields to set.
	 */
	public void setNotErrorFields(Vector notErrorFields) {
		this.notErrorFields = notErrorFields;
	}
	
	
    /**
     * @return Returns the resultAppMs.
     */
    public Vector getResultAppMs() {
        return resultAppMs;
    }
    /**
     * @param resultAppMs The resultAppMs to set.
     */
    public void setResultAppMs(Vector resultAppMs) {
        this.resultAppMs = resultAppMs;
    }
}
