package com.eaf.orig.ulo.pl.formcontrol.view.form;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.eaf.cache.KeySubForm;
import com.eaf.cache.SubFormCache;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.shared.view.form.properties.FormBusinessClassProperties;
import com.eaf.orig.ulo.pl.app.utility.AuditTrailTool;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.cache.TableLookupCache;
//import com.eaf.orig.shared.view.form.properties.SubFormProperties;

public class PLOrigFormHandler extends FormHandler {
	
	Logger log = Logger.getLogger(PLOrigFormHandler.class);
	
	public static final String PLORIGForm = "PLORIGForm";
	public static final String CloanPlApplication = "CloanPlApplication";
	public static final String PL_MAIN_APPFORM_SCREEN = "PL_MAIN_APPFORM";
	public static final String PLORIGSensitive = "PLORIGSensitive";
	
	private PLApplicationDataM appForm;
//	private HashMap subForms;
	private Vector<ORIGSubForm> subForms;
	private HashMap tabs;

	boolean isLoadedSubForms;

//	private String BusinessClassID;
	private String currentTab;
//	private String mode;
	private String formID;
	private String ButtonFile;
	private String OtherFile;
	private String currentPerson;
	private int mandatoryType;
	private String currentScreen;
	
	private JSONArray errorFields;
	private String saveType;
	private JSONArray errorPopupField;
	
	public PLOrigFormHandler() {
		super();
//		subForms = new HashMap();
		subForms = new Vector<ORIGSubForm>();
		tabs = new HashMap();
		appForm = new PLApplicationDataM();		
		errorFields = new JSONArray();
		errorPopupField = new JSONArray();		
	}
	
	public PLApplicationDataM getAppForm() {		
		return appForm;
	}
	public void setAppForm(PLApplicationDataM plAppForm) {
		this.appForm = plAppForm;
	}

	public void setProperties(HttpServletRequest request) {
		
		log.debug(">> Start Sub Form");
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		log.debug(">>Load New SubForm Start AppRecordId >> "+appForm.getAppRecordID());		
		
		if (appForm == null){				
			appForm = new PLApplicationDataM();
			this.setAppForm(appForm);
		}
		
		String busclassID = appForm.getBusinessClassId();
		
		String currentTab = request.getParameter("currentTab");
		
		if(ORIGUtility.isEmptyString(currentTab)){
			currentTab = "MAIN_TAB";
		}
		
		log.debug(">> currentTab " + currentTab);

		setFormSubform(currentTab, userM.getRoles(), "", busclassID, request);
		
//		#septem change logic subForm.setProperties() 
//		// get from cache which subForm should be used for setting properties
//		Vector allSubForms = new Vector(this.getSubForms().values());		
//		
//		Vector allSubFormIDs = new Vector(this.getSubForms().keySet());
//		
//		String subFormID = "";
//		Vector<ORIGSubForm> subFormVect = new Vector<ORIGSubForm>();
//		for(int i = 0; i < allSubFormIDs.size(); i++) { // for 1
//			
//			subFormID = (String) allSubFormIDs.get(i);
//			
//			ORIGSubForm subForm = (ORIGSubForm) getSubForms().get(subFormID);			
//			subFormVect.add(subForm);
//			try{		
//				log.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
//				subForm.setProperties(request, getAppForm());							
//			}catch (Exception e){
//				log.fatal("error set properties:",e);
//			}					
//			//} //end if 2
//
//		} // end for 1	
		
		Vector<ORIGSubForm> subFormVect = this.getSubForms();
		if(null != subFormVect){
			for(ORIGSubForm subForm : subFormVect){
				if (this.getClass() != null && currentTab.equals(subForm.getLocatedTab())){
					log.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
					subForm.setProperties(request, getAppForm());
				}
			}
		}
		//audit trail
		doAuditTrail(request, userM, this.getSubForms());
		
		log.debug(">> End Sub Form");
	}
	
	public void doAuditTrail(HttpServletRequest request,UserDetailM userM,Vector<ORIGSubForm> subFormVect){		
		try{
			AuditTrailTool auditTrail = new AuditTrailTool();
				auditTrail.AuditTrail(request, userM, subFormVect);
		}catch(Exception e){				 
			log.fatal("Exception ",e);
		}
	}
	
	public boolean validateForm(HttpServletRequest request) {
		log.debug(">>>>> Start FormHandler validateForm");
	
//		ORIGFormUtil formUtil = ORIGFormUtil.getInstance();
		
		String currentTab = this.getCurrentTab();
		
		// get from cache which subForm should be used for setting properties
//		Vector allSubForms = new Vector(this.getSubForms().values());
//		Vector allSubFormNames = new Vector(this.getSubForms().keySet());
//		String subFormName = "";		
//		#septem change logic validateForm
//		for (int i = 0; i < allSubFormNames.size(); i++) { // for 1
//			subFormName = (String) allSubFormNames.get(i);
//			ORIGSubForm subForm = (ORIGSubForm) getSubForms().get(subFormName);
//			if (this.getClass() != null && this.getCurrentTab().equalsIgnoreCase(subForm.getLocatedTab())) { // if 1
//				// check Roles & Application status for display mode
//				// if edit mode , setproperties
//				// else do nothing
//				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
//				// get user roles
//				Vector userRoles = userM.getRoles();
//				// please add application status into below method
//				
//				subForm.validateForm(request, getAppForm());
//
//			} // end if 1
//		} // end for 1	
		
		Vector<ORIGSubForm> subFormVect = this.getSubForms();
		if(null != subFormVect){
			for(ORIGSubForm subForm : subFormVect){
				if (this.getClass() != null && currentTab.equals(subForm.getLocatedTab())){
					subForm.validateForm(request, getAppForm());
				}
			}
		}
		log.debug(">>>>> End validateForm");
		return true;
	}

	
	public static PLApplicationDataM getPLApplicationDataM(HttpServletRequest request){		
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		if(null == formHandler) formHandler = new PLOrigFormHandler();
		PLApplicationDataM applicationM = formHandler.getAppForm();		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();	
			formHandler.setAppForm(applicationM);
		}
		return applicationM;
	}
	
	public static PLApplicationDataM getCloanPLApplicationDataM(HttpServletRequest request){		
		PLApplicationDataM applicationM = (PLApplicationDataM) request.getSession().getAttribute(PLOrigFormHandler.CloanPlApplication);				
		if(null == applicationM) applicationM = new PLApplicationDataM();		
		return applicationM;
	}
	public static PLApplicationDataM getSensitivePLAppM(HttpServletRequest request){		
		PLApplicationDataM applicationM = (PLApplicationDataM) request.getSession().getAttribute(PLOrigFormHandler.PLORIGSensitive);				
		if(applicationM == null) applicationM = new PLApplicationDataM();		
		return applicationM;
	}
	
	public void setFormSubform(String currentTab, Vector<String> userRoles, String drawDownFlag , String busClassID,HttpServletRequest request){
		/*********************** Loan Form-SubForm ********************/
					
		String formID = ORIGFormUtil.getFormIDByBus(busClassID);
		
		String searchType = (String) request.getSession().getAttribute("searchType");  
		
		if(OrigConstant.SEARCH_TYPE_CASH_DAY5.equals(searchType)){
			formID = "KEC_CASHDAY5_FORM";
		}		
		log.debug(">>> Loading searchType="+searchType);
		log.debug(">>> Loading formID="+formID);
		//****************************************
//		this.getSubForms().clear();
		this.setLoadedSubForms( false);
		//****************************************
		this.loadSubFormsForDrawDown(userRoles, formID, drawDownFlag);
		this.setCurrentTab(currentTab);
		this.setFormID(formID);
		//*****************************************	
	}
		
	public JSONArray getErrorFields() {
		return errorFields;
	}
	public void setErrorFields(JSONArray errorFields) {
		this.errorFields = errorFields;
	}

	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	
//	public HashMap getSubForms() {
//		return subForms;
//	}
//	public void setSubForms(HashMap subForms) {
//		this.subForms = subForms;
//	}
	
	public Vector<ORIGSubForm> getSubForms() {
		return subForms;
	}
	
	public void setSubForms(Vector<ORIGSubForm> subForms) {
		this.subForms = subForms;
	}
	
	public HashMap getTabs() {
		return tabs;
	}

	public void setTabs(HashMap tabs) {
		this.tabs = tabs;
	}

	public String getCurrentTab() {
		return currentTab;
	}

	public void setCurrentTab(String currentTab) {
		this.currentTab = currentTab;
	}

	public boolean isLoadedSubForms() {
		return isLoadedSubForms;
	}

	public void setLoadedSubForms(boolean isLoadedSubForms) {
		this.isLoadedSubForms = isLoadedSubForms;
	}

	public String getFormID() {
		return formID;
	}

	public void setFormID(String formID) {
		this.formID = formID;
	}

	@Override
	public boolean validInSessionScope() {
 
		return false;
	}

	@Override
	public void postProcessForm(HttpServletRequest request) {
	 
		
	}

	@Override
	public void clearForm() {
		 		
	}
	
	public void loadSubFormsForDrawDown(Vector<String> userRoles, String formID, String drawDownFlag) {
		
//		log.debug(">>>>> Start loadSubForms");	
//		log.debug("getIsLoadedSubForms()  = " + isLoadedSubForms());
//		log.debug("userRoles="+userRoles);
		
		log.debug("formID="+formID);
		
		if (isLoadedSubForms() == false) {
			
//			#septem change Logic loadSubForms()
//			// get From Cache
//			HashMap h = TableLookupCache.getCacheStructure();
//			Vector allSubForm = (Vector) (h.get("SubFormPropertiesCacheDataM"));
////			log.debug("allSubForm="+allSubForm);
//			Vector allRoleSubForm = (Vector) (h.get("SubFormRoleCacheDataM"));
////			log.debug("allRoleSubForm="+allRoleSubForm);
//			Vector allFormBusiness = (Vector) (h.get("FormBusinessClassCacheDataM"));
////			log.debug("allFormBusiness="+allFormBusiness);
//			//set button and other File
//			
//			for (int i = 0; i < allFormBusiness.size(); i++) {
//				FormBusinessClassProperties form = (FormBusinessClassProperties) allFormBusiness.elementAt(i);
//				
//				if (formID.equals(form.getFormID())) {
//					
//					//System.out.println("buttton File name" + form.getButtonFile());
//					//System.out.println("Other File name" + form.getOtherFile());
//					setButtonFile(form.getButtonFile());
//					setOtherFile(form.getOtherFile());
////					log.debug("button file="+form.getButtonFile());
//					break;
//				}
//			}
//			
//			ORIGFormUtil naosUtil = ORIGFormUtil.getInstance();
//			//HashMap allSubFormIDInRole = naosUtil.getSubFormIDByRole(allRoleSubForm, userRoles, formID);
//			HashMap allSubFormIDInRole = naosUtil.getSubFormIDByUserRole(allRoleSubForm, (String) userRoles.elementAt(0), formID);
//			
//			//System.out.println("allSubFormIDInRole = " + allSubFormIDInRole);
//			try {
//				//loop for put sub form to hash map
//				//log.debug("//loop for put sub form to hash map");
////				log.debug("allSubFormIDInRole.size()="+allSubFormIDInRole.size());				
//				for (int i = 0; i < allSubForm.size(); i++) { //for 1
//					SubFormProperties sp = (SubFormProperties) allSubForm.get(i);
//					String formIDfromSubForm = sp.getFormID();
//					//filter by formID
//					//log.debug("//filter by formID i="+i);	
//								
//					if (formIDfromSubForm != null && formIDfromSubForm.equals(formID)) { //if 1
//						
////						log.debug("formIDfromSubForm="+formIDfromSubForm);	
//						
//						String className = sp.getSubFormClass();
//						String formTab = sp.getFormTab();
//						String fileName = sp.getFileName();
//						String subFormID = sp.getSubFormID();
//						String subformDesc = sp.getSubformDesc();
//						String subformClass = sp.getSubFormClass();
//						String subFormPosition = sp.getSubFormPosition();
//						
//						
//						//if (allSubFormIDInRole.containsKey(subFormID) && !(("Y").equals(drawDownFlag)&&subFormID.equals(OrigConstant.SubformName.PRE_SCORE_SUBFORM))) {
//							if (allSubFormIDInRole.containsKey(subFormID) && !(("Y").equals(drawDownFlag))) {
//							// if 2							
//							if (className != null ) { // if 3
//								ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(className)).newInstance());
//								// change for naos get seq from role
//								int seq = 0;
//								try {
//									String seqS = (String) allSubFormIDInRole.get(subFormID);
//									seq = Integer.parseInt(seqS);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//								// end change
//								subForm.setLocatedTab(formTab);
//								subForm.setFileName(fileName);
//								subForm.setSubFormID(subFormID);
//								subForm.setSubFormSeq(seq);
//								subForm.setSubformDesc(subformDesc);
//								subForm.setSubFormPosition(subFormPosition);
//								subForm.setSubFormClass(subformClass);
//								//log.debug("put hash map");
//								//log.debug("put --> (seq=" + seq + ") (formTab=" + formTab + ") (subFormID=" + subFormID + ") ");
//								getSubForms().put(subFormID, subForm);
//								log.debug("subFormID = " + subFormID);
//							} // end if 3
//							//System.out.println("================================");
//						} // endif 2
//					} // end if 1
//				} //end for1
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			
			FormBusinessClassProperties form = SubFormCache.getForm().get(formID);			
			if(null != form){
				this.setButtonFile(form.getButtonFile());
				this.setOtherFile(form.getOtherFile());
			}
			
			String key = KeySubForm.getKeySubFormRole(formID, ORIGFormUtil.getCurrentRole(userRoles));	
			log.debug("KEY >> "+key);
			Vector<ORIGSubForm> subFormVect = SubFormCache.getSubFormRole().get(key);
			
			setSubForms(subFormVect);
			
			// end of  get From Cache			
			setLoadedSubForms(true);
		}
		log.debug(">>>>> End loadSubForms");
	}

	public String getButtonFile() {
		return ButtonFile;
	}
	
	public void setButtonFile(String buttonFile) {
		ButtonFile = buttonFile;
	}
	
	public String getOtherFile() {
		return OtherFile;
	}
	
	public void setOtherFile(String otherFile) {
		OtherFile = otherFile;
	}
	public void loadSubForms(Vector<String> userRoles, String formID) {	
		log.debug(">>>>> Start loadSubForms");	
	//	log.debug("getIsLoadedSubForms()  = " + isLoadedSubForms());
	//	log.debug("userRoles="+userRoles);
		log.debug("formID="+formID);
		if (isLoadedSubForms() == false) {
			
//			// get From Cache
//			HashMap h = TableLookupCache.getCacheStructure();
//			Vector allSubForm = (Vector) (h.get("SubFormPropertiesCacheDataM"));
//			//System.out.println("allSubForm="+allSubForm);
//			Vector allRoleSubForm = (Vector) (h.get("SubFormRoleCacheDataM"));
//			//System.out.println("allRoleSubForm="+allRoleSubForm);
//			
//			Vector allFormBusiness = (Vector) (h.get("FormBusinessClassCacheDataM"));
//			//System.out.println("allFormBusiness="+allFormBusiness);
//			//set button and other File
//			for (int i = 0; i < allFormBusiness.size(); i++) {
//				FormBusinessClassProperties form = (FormBusinessClassProperties) allFormBusiness.elementAt(i);
//				
//				if (formID.equals(form.getFormID())) {				
//	
//					//System.out.println("buttton File name" + form.getButtonFile());
//					//System.out.println("Other File name" + form.getOtherFile());
//					setButtonFile(form.getButtonFile());
//					setOtherFile(form.getOtherFile());
//	//				log.debug("button file="+form.getButtonFile());
//					break;
//				}
//			}
//			
//			ORIGFormUtil naosUtil = ORIGFormUtil.getInstance();
//			//HashMap allSubFormIDInRole = naosUtil.getSubFormIDByRole(allRoleSubForm, userRoles, formID);
//			HashMap allSubFormIDInRole = naosUtil.getSubFormIDByUserRole(allRoleSubForm, (String) userRoles.elementAt(0), formID);
//			
//			//System.out.println("allSubFormIDInRole = " + allSubFormIDInRole);
//			try {
//				//loop for put sub form to hash map
//				//log.debug("//loop for put sub form to hash map");
//	//			log.debug("allSubFormIDInRole.size()="+allSubFormIDInRole.size());				
//				for (int i = 0; i < allSubForm.size(); i++) { //for 1
//					SubFormProperties sp = (SubFormProperties) allSubForm.get(i);
//					String formIDfromSubForm = sp.getFormID();
//					//filter by formID
//					//log.debug("//filter by formID i="+i);	
//	//				log.debug("formIDfromSubForm="+formIDfromSubForm);				
//					if (formIDfromSubForm != null && formIDfromSubForm.equals(formID)) { //if 1
//											
//						String className = sp.getSubFormClass();
//						String formTab = sp.getFormTab();
//						String fileName = sp.getFileName();
//						String subFormID = sp.getSubFormID();
//						String subformDesc = sp.getSubformDesc();
//						String subFormPosition = sp.getSubFormPosition();
//						String  subFormClass=sp.getSubFormClass(); 
//						
//						if (allSubFormIDInRole.containsKey(subFormID)) {																					
//							// if 2							
//							if (className != null ) { // if 3
//								ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(className)).newInstance());
//								// change for naos get seq from role
//								int seq = 0;
//								try {
//									String seqS = (String) allSubFormIDInRole.get(subFormID);
//									seq = Integer.parseInt(seqS);
//								} catch (Exception e) {
//									e.printStackTrace();
//								}
//								// end change
//								subForm.setLocatedTab(formTab);
//								subForm.setFileName(fileName);
//								subForm.setSubFormID(subFormID);
//								subForm.setSubFormSeq(seq);
//								subForm.setSubformDesc(subformDesc);
//								subForm.setSubFormPosition(subFormPosition);
//								subForm.setSubFormClass(subFormClass);
//								//log.debug("put hash map");
//								//log.debug("put --> (seq=" + seq + ") (formTab=" + formTab + ") (subFormID=" + subFormID + ") ");
//								getSubForms().put(subFormID, subForm);
//								log.debug("subFormID = " + subFormID);
//							} // end if 3
//							//System.out.println("================================");
//						} // endif 2
//					} // end if 1
//				} //end for1
//			}catch(Exception e){
//				log.fatal("ERROR ",e);
//			}
			
			FormBusinessClassProperties form = SubFormCache.getForm().get(formID);			
			if(null != form){
				this.setButtonFile(form.getButtonFile());
				this.setOtherFile(form.getOtherFile());
			}
			
			String key = KeySubForm.getKeySubFormRole(formID, ORIGFormUtil.getCurrentRole(userRoles));		
			Vector<ORIGSubForm> subFormVect = SubFormCache.getSubFormRole().get(key);
			this.setSubForms(subFormVect);
			
			// end of  get From Cache			
			this.setLoadedSubForms(true);
		}
		log.debug(">>>>> End loadSubForms");
	}

	public void PushErrorMessage(String fieldId ,String message){
		if(null == this.errorFields){
			this.errorFields = new JSONArray();
		}
//			JSONObject json = new JSONObject();
//				json.put("id", fieldId);
//				json.put("value", message);
//		this.errorFields.put(json);
	}
	public void DestoryErrorFields(){
		this.errorFields = new JSONArray();
	}
	public String GetErrorFields(){
		if(null == this.errorFields) this.errorFields = new JSONArray();
		return this.errorFields.toString();
	}

	public void PushErrorPopupMessage(String fieldId ,String message){
		if(null == this.errorPopupField) this.errorPopupField = new JSONArray();
//			JSONObject jsonObject = new JSONObject();
//				jsonObject.put("id", fieldId);
//				jsonObject.put("value", message);
//		this.errorPopupField.put(jsonObject);
	}
	public void DestoryErrorPopupFields(){
		this.errorPopupField = new JSONArray();
	}
	public String GetErrorPopupFields(){
		if(null == this.errorPopupField) this.errorPopupField = new JSONArray();
		return this.errorPopupField.toString();
	}
	
	public void setCurrentPerson(String currentPerson) {
		this.currentPerson = currentPerson;
	}

	public String getCurrentPerson() {
		return currentPerson;
	}
	
	public int getMandatoryType() {
		return mandatoryType;
	}
	public void setMandatoryType(int mandatoryType) {
		this.mandatoryType = mandatoryType;
	}

	public String getCurrentScreen() {
		return currentScreen;
	}
	
	public void setCurrentScreen(String currentScreen) {
		this.currentScreen = currentScreen;
	}	
}
