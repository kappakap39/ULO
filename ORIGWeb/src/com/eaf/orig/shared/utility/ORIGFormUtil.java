package com.eaf.orig.shared.utility;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.KeySubForm;
import com.eaf.cache.SubFormCache;
import com.eaf.cache.TableLookupCache;
import com.eaf.cache.data.CacheDataM;
import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.orig.cache.properties.BusinessClassProperties;
import com.eaf.orig.cache.properties.FormTabProperties;
import com.eaf.orig.cache.properties.SubFormDisplayModeProperties;
//import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGFormTab;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.shared.view.form.properties.FormBusinessClassProperties;
import com.eaf.orig.shared.view.form.properties.SubFormProperties;
import com.eaf.orig.shared.view.form.properties.SubFormRoleProperties;

/**
 * @author Administrator
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ORIGFormUtil extends DisplayFormatUtil {
	static Logger log = Logger.getLogger(ORIGFormUtil.class);

	public ORIGFormUtil() {
		super();
	}

	private static ORIGFormUtil me;

	public static ORIGFormUtil getInstance() {
		if (me == null) {
			me = new ORIGFormUtil();
		}
		return me;
	}

	public void removeNaosFormFromSession(HttpServletRequest request) {
		request.getSession().removeAttribute("ORIGForm");
	}
	
	public Vector getSortedFileNameByCurrentTab(String currentTab, HashMap allSubForms) {
//		HashMap h = TableLookupCache.getCacheStructure();
		// get All subForm from Hash
		Vector subFormsVec = new Vector(allSubForms.values());
		Vector subFormsVecWithCurrentTab = new Vector();
		// filtor with current tab
		for (int i = 0; i < subFormsVec.size(); i++) {
			ORIGSubForm subForm = (ORIGSubForm) subFormsVec.get(i);
			if (subForm != null && subForm.getLocatedTab() != null && subForm.getLocatedTab().equalsIgnoreCase(currentTab)) {
				subFormsVecWithCurrentTab.add(subFormsVec.get(i));
			}
		}
		// sort Subform put in Vector
		Vector vSort = new Vector();
		int index = 0;
		while (subFormsVecWithCurrentTab.size() != 0) {
			// do until remove all object in this vector
			int seq = 100000; // assume seq to maximum
			for (int j = 0; j < subFormsVecWithCurrentTab.size(); j++) { //for 1 loop for sort minimum seq
				ORIGSubForm subForm1 = (ORIGSubForm) subFormsVecWithCurrentTab.get(j);
				int con = subForm1.getSubFormSeq();
				if (seq > con) {
					seq = con;
				}
			} // end for 1
			for (int k = 0;
				k < subFormsVecWithCurrentTab.size();
				k++) { //for 1 add object to new vector (vSort) and remove vector(subFormsVecWithCurrentTab)
				ORIGSubForm subForm2 = (ORIGSubForm) subFormsVecWithCurrentTab.get(k);
				if (subForm2.getSubFormSeq() == seq) {
					subFormsVecWithCurrentTab.remove(k);
					// remove object that equal seq minimum
					vSort.add(index, subForm2);
					// add minimum seq
					//log.debug(" index ========>" + index);
					//log.debug(" subForm2.getFileName() ========>" + subForm2.getFileName());
					index++; // increase index of vector vSort 
				}
			} // end for 1
		}
		return vSort;
	}
	public HashMap getSubFormIDByRole(Vector allRoleSubForm, Vector userRoles, String formID) {
		HashMap allSubFormID = new HashMap();
		//log.debug("in getSubFormIDByRole method");
		
		if(allRoleSubForm!=null){
			//log.debug("in util allRoleSubForm size:" + allRoleSubForm.size());
			try{
				// loop for check subFormID is in user roles
			//	log.debug("The value of allRoleSubForm size is: "+allRoleSubForm.size());
				for (int i = 0; i < allRoleSubForm.size(); i++) { //for 1
					SubFormRoleProperties subFormRole = (SubFormRoleProperties) allRoleSubForm.get(i);
					String roleID = subFormRole.getRoleID();
					String formIDAll = subFormRole.getFormID();
					//check role id in user role
					if (roleID != null && userRoles.contains(roleID)) { // if2
						if ((formIDAll !=null && formID != null) && formIDAll.equals(formID)) {
							allSubFormID.put(subFormRole.getSubFormID(), subFormRole.getSeq() + "");
						}
					} // end if2
				} //end for 1
			}catch(Exception e){
				log.debug(e);
			}
		}
		//log.debug("allSubFormID size is: "+allSubFormID.size());
		return allSubFormID;
	}
	/**
	 * 
	 * @param allRoleSubForm
	 * @param userRoles
	 * @param formID
	 * @return
	 */
	public HashMap getSubFormIDByUserRole(Vector allRoleSubForm, String userRole, String formID) {
		HashMap allSubFormID = new HashMap();
		//log.debug("in getSubFormIDByRole method");
//		log.debug("@@@@@ getSubFormIDByUserRole userRole:"+ userRole);
		if(allRoleSubForm!=null){
			//log.debug("in util allRoleSubForm size:" + allRoleSubForm.size());
			try{
				// loop for check subFormID is in user roles
				//log.debug("The value of allRoleSubForm size is: "+allRoleSubForm.size());
				for (int i = 0; i < allRoleSubForm.size(); i++) { //for 1
					SubFormRoleProperties subFormRole = (SubFormRoleProperties) allRoleSubForm.get(i);
					String roleID = subFormRole.getRoleID();
					String formIDAll = subFormRole.getFormID();
					//check role id in user role
					if (roleID != null && userRole.equals(roleID)) { // if2
						if ((formIDAll !=null && formID != null) && formIDAll.equals(formID)) {
							allSubFormID.put(subFormRole.getSubFormID(), subFormRole.getSeq() + "");
						}
					} // end if2
				} //end for 1
			}catch(Exception e){
				log.debug(e);
			}
		}
		//log.debug("allSubFormID size is: "+allSubFormID.size());
		return allSubFormID;
	}
	/***
	 * @author Kanokwan Chaikulseriwat
	 * @param String of SubForm ID
	 * @param Vector of User Roles
	 * @param String of application status
	 * @return String of display mode (if not found return edit mode)
	 * 
	 */
	public String getDisplayMode(String subFormID, Vector roleIDs, String appJobState, String formID, String searchType) {
//		log.debug("@@@@@ cal display mode : subFormId["+subFormID+"] roleIDs["+roleIDs+"] appJobState["+appJobState+"] formID["+formID+"] searchType["+searchType+"]"); 
		// implement from cache
		// get all display mode data from cache
		if(!("Enquiry").equals(searchType)){
			
//			#septemwi comment change Logic get DisplayMode From >> SubFormCache.getDisplayMode()
//			HashMap h = TableLookupCache.getCacheStructure();
//			Vector allDisplayMode = (Vector) (h.get("SubFormDisplayModeCacheDataM"));
//			//log.debug(">>>>>>>>>>>>>>>>>>>>>sub form id :" + subFormID);
//			// loop for get display mode for subForm
//			for (int i = 0; i < allDisplayMode.size(); i++) {
//				SubFormDisplayModeProperties displayMode = (SubFormDisplayModeProperties) allDisplayMode.get(i);
//				String subFormIDInMode = displayMode.getSubFormID();
//				String roleID = displayMode.getRoleID();
//				String jobState = displayMode.getJobState();
//				String FormID = displayMode.getFormID();
//				//filter by subFormID
//				if (null != formID && formID.equals(FormID)) { //if 1
//					// filter by form ID
//					if (subFormID != null && subFormID.equals(subFormIDInMode)) { // if2
//						//filter by roleID (if role id of this display mode is in user roles)
//						if (roleID != null && roleIDs.contains(roleID)) { //if 3
//							//filter by application status
//							if (appJobState != null //if 4
//							&& appJobState.trim().equals(jobState.trim())) {
//								//log.debug(">>>>>>>>>>>>>>>>>>>>>display mode1 :" + displayMode.getDisplayMode());
//								return displayMode.getDisplayMode();
//								//return DisplayFormatUtil.DISPLAY_MODE_EDIT;
//							} // end if 4
//						} // end if 3
//					} // end if 2
//				} // end if 1
//			}
			
			String roleID = getCurrentRole(roleIDs);
			String key = KeySubForm.getKeyDisplayMode(subFormID, roleID, appJobState, formID);
			if(null != key){
				SubFormDisplayModeProperties prop = SubFormCache.getDisplayMode().get(key);
				if(null != prop){
					return prop.getDisplayMode();
				}
			}			
		}
		return DisplayFormatUtil.DISPLAY_MODE_VIEW;
	}
	
	public static String getCurrentRole(Vector<String> roleIDs){		
		if(roleIDs != null && roleIDs.size() > 0){
			return roleIDs.elementAt(0);
		}
		return "";
	}
	/***
	 * @author Kanokwan Chaikulseriwat
	 * @param Vector of sub forms
	 * @return HashMap of SubForm Tab
	 * 
	 */
	public HashMap getTabsBySubForms(Vector subForms) {
		/////log.debug("Start >> getTabsBySubForms");
		HashMap subFormTabID = new HashMap();
		// get all unique tabs in subForms
		// loop for get tab from subForm and get its data and put it into HashMap
		for (int i = 0; i < subForms.size(); i++) { // for 1
			ORIGSubForm subForm = (ORIGSubForm) subForms.get(i);
			String tabId = subForm.getLocatedTab();
			/////log.debug("tabId="+tabId);			
			subFormTabID.put(tabId, getFormTabDetailByFormTabID(tabId));
		} // end for 1
		/////log.debug("End >> getTabsBySubForms");		
		return subFormTabID;
	}
	/***
	 * @author Kanokwan Chaikulseriwat
	 * @param String of Tab ID
	 * @return NaosFormTab 
	 */
	public ORIGFormTab getFormTabDetailByFormTabID(String formTabID) {
		////log.debug("Start >>getFormTabDetailByFormTabID");		
		// get all tab from cache
		//log.debug("[NaosFormTab] getFormTabDetailByFormTabID(String FormTabID]");
		HashMap h = TableLookupCache.getCacheStructure();
		Vector allTab = (Vector) (h.get("FormTabCacheDataM"));
		ORIGFormTab naosFormTab = new ORIGFormTab();
		// loop for get tab's data from tabID
		for (int i = 0; i < allTab.size(); i++) { // for 1 
			FormTabProperties formTab = (FormTabProperties) allTab.get(i);
			if (formTabID != null && formTabID.equals(formTab.getFormTabID())) { // if 1
				naosFormTab.setFormID(formTab.getFormID());
				naosFormTab.setFormTabID(formTab.getFormTabID());
				naosFormTab.setFormTabName(formTab.getFormTabName());
				naosFormTab.setFormTabURL(formTab.getFormTabURL());
				////log.debug("naosFormTab.getFormID()="+naosFormTab.getFormID());				
				////log.debug("naosFormTab.getFormTabID()="+naosFormTab.getFormTabID());				
				return naosFormTab;
			} // end if 1
		} // end for 1
		////log.debug("End >>getFormTabDetailByFormTabID");		
		return naosFormTab;
	}
	/***
	 * @author Kanokwan Chaikulseriwat
	 * @param Vector of User Roles
	 * @return String of default tab
	 */
	public String getDefaultActiveFormTabIDByUserRoles(Vector userRoles) {
		// get role from cache 
		HashMap h = TableLookupCache.getCacheStructure();
		Vector allRole = (Vector) (h.get("SubFormRoleCacheDataM"));
		// loop for get default tab by user roles
		for (int i = 0; i < allRole.size(); i++) { // for 1
			// find role in cache which match in user roles
			// get subFormID from role 
			// get tab by subFormID
			SubFormRoleProperties subFormRole = (SubFormRoleProperties) allRole.get(i);
			String roleID = subFormRole.getRoleID();
			if (userRoles.contains(roleID) && subFormRole.isDefault()) { // if 1
				String subFormID = subFormRole.getSubFormID();
				return getTabIDBySubFormID(subFormID);
			} // end if 1
		} // end for 2
		return "";
	}

	/***
	 * @param Vector of User Roles
	 * @param Vector of subForms	 
	 * @return String of default tab
	 */
	public String getDefaultActiveFormTabIDByUserRolesSubForms(Vector userRoles, Vector subForms) {
		// get role from cache 
		HashMap h = TableLookupCache.getCacheStructure();
		Vector allRole = (Vector) (h.get("SubFormRoleCacheDataM"));
		// loop for get default tab by user roles
		for (int i = 0; i < allRole.size(); i++) { // for 1
			// find role in cache which match in user roles
			// get subFormID from role 
			// get tab by subFormID
			SubFormRoleProperties subFormRole = (SubFormRoleProperties) allRole.get(i);
			String roleID = subFormRole.getRoleID();
			if (userRoles.contains(roleID) && subFormRole.isDefault()) { // if 1
				String subFormID = subFormRole.getSubFormID();
				for (int j = 0; j < subForms.size(); j++) { // for 1
					ORIGSubForm subForm = (ORIGSubForm) subForms.get(j);
					String tabId = subForm.getLocatedTab();
					//log.debug("in getDefaultActiveFormTabIDByUserRolesSubForms ===> tabId = " + tabId + ", subformID = " + subFormID);
					if (tabId.equals(subFormID))
						return getTabIDBySubFormID(subFormID);
				} // end for1
			} // end if 1
		} // end for 2
		return "";
	}

	/***
	 * @author Kanokwan Chaikulseriwat
	 * @param String of subForm ID
	 * @return String of tab ID 
	 */
	public String getTabIDBySubFormID(String subFormID) {
		// get subform from cache
		HashMap h = TableLookupCache.getCacheStructure();
		Vector allSubForm = (Vector) (h.get("SubFormPropertiesCacheDataM"));
		// loop for get tabID by subFormID
		for (int i = 0; i < allSubForm.size(); i++) { // for 1
			SubFormProperties subForm = (SubFormProperties) allSubForm.get(i);
			//filter by subFormID
			if (subFormID != null && subFormID.equals(subForm.getSubFormID())) { // if 1
				return subForm.getFormTab();
			} // end if 1
		} // end for 1
		return "";
	}
	/***
	 * @author Kanokwan Chaikulseriwat
	 * @param HashMap of all form tabs
	 * @return String of active tab
	 */
	public String getActiveTab(HashMap allTabs) {
		//log.debug("alllTabs values is: "+allTabs);
		Vector subFormTabs = new Vector(allTabs.values());
		// loop for check tab is active and return tab ID
		for (int i = 0; i < subFormTabs.size(); i++) { // for 1
			ORIGFormTab naosFormTab = (ORIGFormTab) subFormTabs.get(i);
			if (naosFormTab.isActive()) { // if 1.
				//log.debug("naosFormTab is Active");
				return naosFormTab.getFormTabID();
			} // end if 1
		} // end for 1
		//log.debug("naosFormTab is NOT Active");
		return "";
	}
	public Vector getDummyData() {
		Vector v = new Vector();
		for (int i = 0; i < 7; i++) {
			CacheDataM data = new CacheDataM();
			data.setCode(i + "");
			data.setShortDesc("short" + i);
			data.setLongDesc("long " + i);
			data.setValueDesc("value =" + i);
			data.setQty(i);
			v.add(data);
		}
		return v;
	}
	public static void listBeanProperties(Object bean, PrintStream out) throws Exception {
		Class aClass = bean.getClass();
		Method methodList[] = aClass.getMethods();
		String methodName = "";
		for (int i = 0, len = methodList.length; i < len; i++) {
			/**
			 * Focus only "get" begining method.
			 */
			if ((methodName = methodList[i].getName()).startsWith("get") && !methodName.equals("getClass")) {
				Object val = methodList[i].invoke(bean, new Object[] {
				});
				out.println(methodName + " :" + val);
			}
		}
	}
	public static void listBeanProperties(Object bean, PrintWriter out) throws Exception {
		Class aClass = bean.getClass();
		Method methodList[] = aClass.getMethods();
		String methodName = "";
		for (int i = 0, len = methodList.length; i < len; i++) {
			/**
			 * Focus only "get" begining method.
			 */
			if ((methodName = methodList[i].getName()).startsWith("get") && !methodName.equals("getClass")) {
				Object val = methodList[i].invoke(bean, new Object[] {
				});
				out.println(methodName + " :" + val);
			}
		}
	}
	public static String displayDoubleZeroToEmpty(double doubleZero) {
		String EmpString = "";
		if (doubleZero == 0) {
			EmpString = "";
		} else {
			EmpString = Double.toString(doubleZero);
		}
		return EmpString;
	}
	public static String displayDoubleZeroToEmpty(String doubleZero) {
		String EmpString = "";
		if ((doubleZero.equals("0")) || (doubleZero.equals("0.00")) || (doubleZero.equals(".00"))) {
			EmpString = "";
		} else {
			EmpString = doubleZero;
		}
		return EmpString;
	}
	public static String displayTransaction(String doubleZero, double flag) {
		String EmpString = "";
		if ((doubleZero.equals("0")) || (doubleZero.equals("0.00")) || (doubleZero.equals(".00"))) {
			if (flag != 0) {
				EmpString = "0.00";
			}
		} else {
			EmpString = doubleZero;
		}
		return EmpString;
	}
	public static double displayEmptyTODoubleZero(String EmptyString) {
		double doubleZero = 0;
		if (null == EmptyString) {
			doubleZero = 0;
		} else if (EmptyString.equals("")) {
			doubleZero = 0;
		} else {
			doubleZero = Double.parseDouble(EmptyString);
		}
		return doubleZero;
	}
	public static Vector split(String separator, String str) {
		Vector vResult = new Vector();
		int start = 0;
		int index = str.indexOf(separator);
		while (index != -1) {
			vResult.add(str.substring(start, index));
			str = str.substring(index + separator.length());
			index = str.indexOf(separator);
		}
		vResult.add(str);
		return vResult;
	}
	public static HashMap checkMatchBusiness(HashMap hMap, BusinessClassProperties bcData) {
		String orgU = "";
		String proU = "";
		String chaU = "";
		//(prdId == null) ? "" : prdId.trim();
		if (bcData != null) {
			orgU = bcData.getOrgID();
			proU = bcData.getProductID();
			chaU = bcData.getChannelID();
		}
		HashMap mmap = new HashMap();
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vecB = (Vector) (h.get("BusinessClassCacheDataM"));
		BusinessClassProperties userBis = new BusinessClassProperties();
		
//		log.debug("[checkMatchBusiness]..Organize ="+orgU);
//		log.debug("[checkMatchBusiness]..Product ="+proU);
//		log.debug("[checkMatchBusiness]..Channel ="+chaU);
		
		if(orgU==null){orgU="";}
		if(proU==null){proU="";}
		if(chaU==null){chaU="";}

		for (int i = 0; i < vecB.size(); i++) { // for
			BusinessClassProperties data = (BusinessClassProperties) vecB.elementAt(i);
			String orgT = "";
			String proT = "";
			String chaT = "";
			orgT = data.getOrgID();
			proT = data.getProductID();
			chaT = data.getChannelID();
//			log.debug("orgT="+orgT);
//			log.debug("proT="+proT);
//			log.debug("chaT="+chaT);
			if(orgT==null){orgT="";}
			if(proT==null){proT="";}
			if(chaT==null){chaT="";}
			
			if ((orgU.equalsIgnoreCase("ALL") ? true : (orgT.equalsIgnoreCase("ALL"))) || (orgU.equalsIgnoreCase(orgT))) { // 1
				if ((proU.equalsIgnoreCase("ALL") ? true : (proT.equalsIgnoreCase("ALL"))) || (proU.equalsIgnoreCase(proT))) { // 2
					if ((chaU.equalsIgnoreCase("ALL") ? true : (chaT.equalsIgnoreCase("ALL"))) || (chaU.equalsIgnoreCase(chaT))) { // 3
						/***
								SAME BUSINESS					
						*/ ////
						mmap.put(data.getBusID(), data);
					} // E3
				} //E 2
			} // E 1
		} // E for
		
//		log.debug(">>> checkMatchBusiness = "+mmap.toString());

		return mmap;
	}

	/*public static HashMap checkRelateBusGroup(String busId) {
		HashMap mmap = new HashMap();
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vecBG = (Vector) (h.get("BusinessClassMapGroupCacheDataM"));
		BusinessClassMapGroupProperties busG = new BusinessClassMapGroupProperties();
		BusinessClassProperties bData = getBusinessProperties(busId);
		HashMap bMap = checkMatchBusiness(null, bData);
		Set set = bMap.keySet();
		Object o[] = set.toArray();
		for (int k = 0; k < o.length; k++) {
			for (int i = 0; i < vecBG.size(); i++) { // for
				BusinessClassMapGroupProperties data = (BusinessClassMapGroupProperties) vecBG.elementAt(i);
				if (o[k].equals(data.getBusId()) && !mmap.containsKey(data.getBusGroupId())) {
					mmap.put(data.getBusGroupId(), data.getBusGroupId());
				}
			}
		}
		return mmap;
	}*/

	public static boolean validateDouble(String strNum) {
		boolean data = true;
		try {
//			double num = Double.parseDouble(strNum);
			data = true;
		} catch (NumberFormatException e) {
			data = false;
		}
		return data;
	}

	public static boolean validateInteger(String strNum) {
		boolean data = true;
		try {
//			double num = Integer.parseInt(strNum);
			data = true;
		} catch (NumberFormatException e) {
			data = false;
		}
		return data;
	}

	public static boolean validateDate(String strDate) {
		boolean data = true;
		java.sql.Date tDate = null;
		if (strDate != null && !strDate.trim().equals("")) {
			try {
				java.util.Date tmpTransferDate = DisplayFormatUtil.StringToDate(DisplayFormatUtil.stringDelChar(strDate, '/'), "ddMMyyyy");
				if (tmpTransferDate != null) {
					tDate = new java.sql.Date(tmpTransferDate.getTime());
				}
			} catch (Exception e) {
				e.printStackTrace();
				data = false;
			}
		}
		return data;
	}

	/*public static Vector parseFromBcToCacheDataM(Vector vecB) {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vPro = (Vector) (h.get("ProductChannelCacheDataM"));
		Vector reVec = new Vector();
		try {
			for (int i = 0; i < vecB.size(); i++) {
				BusinessClassProperties bData = (BusinessClassProperties) vecB.elementAt(i);
				String pid = bData.getProductID();
				String cid = bData.getChannelID();
				CacheDataM cache = new CacheDataM();
				for (int j = 0; j < vPro.size(); j++) {
					ProductChannelProperties proData = (ProductChannelProperties) vPro.elementAt(j);
					if (pid.equalsIgnoreCase(proData.getProductID()) && cid.equalsIgnoreCase(proData.getChannelID())) {

						cache.setCode(bData.getBusID());
						cache.setShortDesc(proData.getProductDesc() + "/" + proData.getChannelDesc());
						reVec.add(cache);
						break;
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/

	/*public static Vector parseProductTypeToCacheDataM() {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vPro = (Vector) (h.get("ProductClassCacheDataM"));
		Vector reVec = new Vector();
		try {
			CacheDataM cache;
			for (int j = 0; j < vPro.size(); j++) {
				cache = new CacheDataM();
				ProductClassProperties proData = (ProductClassProperties) vPro.elementAt(j);
				cache.setCode(proData.getProductid());
				cache.setShortDesc(proData.getProductdesc());
				reVec.add(cache);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/
	
	/*public static Vector displayProductChannelUpDown(String productIDForm,HttpServletRequest request,String userName){
		Vector re =new Vector();
		String flag =(String)request.getSession().getAttribute("FLAG_UPDOWNGRADE");
		HashMap h = TableLookupCache.getCacheStructure();
		if(flag==null){
			flag="";
		}
		String chParam="";
		if(flag.equals("U")){
			chParam="UPG";
		}else if(flag.equals("D")){
			chParam="DWG";
		}
		flag =(flag==null)?"":flag;
		//log.debug("*****flag*****"+flag);
		//log.debug("*****productIDForm*****"+productIDForm);		
		Vector vPro = (java.util.Vector) (h.get("ProductChannelCacheDataM"));
		HashMap	hAccessBus= getAccessBusinessClassByUserProfile(userName);		
		HashMap	OrgId =new HashMap();
		HashMap	ProId =new HashMap();				
		Set	set =hAccessBus.keySet();
			Object o3[]=set.toArray();	
			for(int i=0;i<o3.length;i++){
				BusinessClassProperties accessbus=(BusinessClassProperties) hAccessBus.get((String)o3[i]);
				 if(!OrgId.containsKey(accessbus.getOrgID())){
				 	OrgId.put(accessbus.getOrgID(),accessbus.getOrgID());
				 }
				 if(!ProId.containsKey(accessbus.getProductID())){
				 	ProId.put(accessbus.getProductID(),accessbus.getProductID());
				 }
		}
		CacheDataM cache;
		for(int i=0; i<vPro.size(); i++){
			com.ge.naosasia.view.form.properties.ProductChannelProperties proData =
				(com.ge.naosasia.view.form.properties.ProductChannelProperties) vPro.elementAt(i);
				if((OrgId.containsKey(proData.getOrgID()))&&(ProId.containsKey(proData.getProductID()))){								
				//if(nUtil.checkmatchString(chParam,proData.getChannelID())){//if check UPG DWG
				if(proData.getChannelID().indexOf(chParam)!=-1){//if check UPG DWG						
				//add
				if(productIDForm.equals(proData.getProductID())){
					cache = new CacheDataM();
					cache.setCode(proData.getChannelID());
					cache.setShortDesc(proData.getProductDesc()+"/"+proData.getChannelDesc());
					re.add(cache);
				}
		 		}//if check UPG DWG
				}
		}	
		return re;
	}*/
	
	/*public static Vector parseProductTypeToCacheDataM(String username) {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vPro = (Vector) (h.get("ProductClassCacheDataM"));
		Vector reVec = new Vector();
		try {
			HashMap hAccessBus = getAccessBusinessClassByUserProfile(username);
			HashMap OrgId = new HashMap();
			HashMap ProId = new HashMap();
			HashMap tmpbus = new HashMap();
			Set set = hAccessBus.keySet();
			Object o[] = set.toArray();

			for (int i = 0; i < o.length; i++) {
				BusinessClassProperties accessbus = (BusinessClassProperties) hAccessBus.get((String) o[i]);
				if (!OrgId.containsKey(accessbus.getOrgID())) {
					OrgId.put(accessbus.getOrgID(), accessbus.getOrgID());
				}
				if (!ProId.containsKey(accessbus.getProductID())) {
					ProId.put(accessbus.getProductID(), accessbus.getProductID());
				}
			}
			CacheDataM cache;
			for (int j = 0; j < vPro.size(); j++) {
				cache = new CacheDataM();
				ProductClassProperties proData = (ProductClassProperties) vPro.elementAt(j);
				if (OrgId.containsKey(proData.getOrgid()) && ProId.containsKey(proData.getProductid())) {
					cache.setCode(proData.getProductid());
					cache.setShortDesc(proData.getProductdesc());
					reVec.add(cache);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reVec;
	}*/
	//------------------------------------------------------------------------------
	public static Vector parseDealerToCacheDataM() {
		HashMap hash=TableLookupCache.getCacheStructure();
		Vector vDealerCode = (Vector)(hash.get("DealerAutoCacheDataM")); 	
		log.debug(vDealerCode);
		Vector reVec = new Vector();
		CacheDataM cache;
		String allValue = "";
	
		for (int j = 0; j < vDealerCode.size(); j++) { 
			cache = new CacheDataM();
			CacheDataM dealerData = (CacheDataM) vDealerCode.elementAt(j);
			allValue = allValue + dealerData.getCode() + "|";
			cache.setCode(dealerData.getCode());
			cache.setShortDesc(dealerData.getShortDesc());
			reVec.add(cache);
		}
		if(vDealerCode.size()>0) 
			allValue = allValue.substring(0,allValue.length()-1);
		
		CacheDataM allCache  = new CacheDataM();
		allCache.setCode(allValue);
		allCache.setShortDesc("All");
		reVec.add(allCache);				
		return reVec;
	}
	
	public static Vector parseSchemeToCacheDataM() {
		HashMap hash=TableLookupCache.getCacheStructure();
		Vector vSchemeCode = (Vector)(hash.get("IntSchemeSGAutoCacheDataM")); 	
		Vector reVec = new Vector();
		CacheDataM cache;
		String allValue = "";
	
		for (int j = 0; j < vSchemeCode.size(); j++) { 
			cache = new CacheDataM();
			CacheDataM schemeData = (CacheDataM) vSchemeCode.elementAt(j);
			allValue = allValue + schemeData.getCode() + "|";
			cache.setCode(schemeData.getCode());
			cache.setShortDesc(schemeData.getShortDesc());
			reVec.add(cache);
		}
		if(vSchemeCode.size()>0) 
			allValue = allValue.substring(0,allValue.length()-1);
		
		CacheDataM allCache  = new CacheDataM();
		allCache.setCode(allValue);
		allCache.setShortDesc("All");
		reVec.add(allCache);				
		return reVec;
	}	

	public static Vector parseSaleNameToCacheDataM() {
		HashMap hash=TableLookupCache.getCacheStructure();
		Vector vSchemeCode = (Vector)(hash.get("SalePersonNameCacheDataM")); 	
		Vector reVec = new Vector();
		CacheDataM cache;
		String allValue = "";
	
		for (int j = 0; j < vSchemeCode.size(); j++) { 
			cache = new CacheDataM();
			CacheDataM schemeData = (CacheDataM) vSchemeCode.elementAt(j);
			allValue = allValue + schemeData.getCode() + "|";
			cache.setCode(schemeData.getCode());
			cache.setShortDesc(schemeData.getShortDesc());
			reVec.add(cache);
		}
		if(vSchemeCode.size()>0) 
			allValue = allValue.substring(0,allValue.length()-1);
		
		CacheDataM allCache  = new CacheDataM();
		allCache.setCode(allValue);
		allCache.setShortDesc("All");
		reVec.add(allCache);				
		return reVec;
	}
	
	public static Vector parseSaleNricToCacheDataM() {
		HashMap hash=TableLookupCache.getCacheStructure();
		Vector vSchemeCode = (Vector)(hash.get("SalePersonNameCacheDataM")); 	
		Vector reVec = new Vector();
		CacheDataM cache;
		String allValue = "";
	
		for (int j = 0; j < vSchemeCode.size(); j++) { 
			cache = new CacheDataM();
			CacheDataM schemeData = (CacheDataM) vSchemeCode.elementAt(j);
			allValue = allValue + schemeData.getCode() + "|";
			cache.setCode(schemeData.getCode());
			cache.setShortDesc(schemeData.getLongDesc());
			reVec.add(cache);
		}
		if(vSchemeCode.size()>0) 
			allValue = allValue.substring(0,allValue.length()-1);
		
		CacheDataM allCache  = new CacheDataM();
		allCache.setCode(allValue);
		allCache.setShortDesc("All");
		reVec.add(allCache);				
		return reVec;
	}		

	public static Vector parseChannelToCacheDataM() {
		HashMap hash = TableLookupCache.getCacheStructure();
		Vector vChannelCode = (Vector)(hash.get("ChannelSGAutoCacheDataM"));
		Vector reVec = new Vector();
		CacheDataM cache;
		String allValue = "";
	
		for (int j = 0; j < vChannelCode.size(); j++) { 
			cache = new CacheDataM();
			CacheDataM channelData = (CacheDataM) vChannelCode.elementAt(j);			
			if(channelData.getCode()!="ALL") {
			allValue = allValue + channelData.getCode() + "|";
			cache.setCode(channelData.getCode());
			cache.setShortDesc(channelData.getShortDesc());
			reVec.add(cache);
			}
		}
		if(vChannelCode.size()>0) 
		allValue = allValue.substring(0,allValue.length()-1);
		
		CacheDataM allCache  = new CacheDataM();
		allCache.setCode(allValue);
		allCache.setShortDesc("All");
		reVec.add(allCache);				
		return reVec;
	}
	
	public static String getBusinessID(String prodCode, String prodChannel, String orgID) {
		//log.debug("**************** Start getBusinessID *****************");
		//log.debug(">>>>> prodCode="+prodCode);
		//log.debug(">>>>> prodChannel="+prodChannel);
		//log.debug(">>>>> orgID="+orgID);		
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vecB = (Vector) (h.get("BusinessClassCacheDataM"));
		//log.debug(">>>>> vecB="+vecB);		
		try {
			for (int i = 0; i < vecB.size(); i++) {
				BusinessClassProperties bData = (BusinessClassProperties) vecB.elementAt(i);
				String pid = bData.getProductID();
				String cid = bData.getChannelID();
				String oid = bData.getOrgID();
				if (pid.equals(prodCode) && cid.equals(prodChannel)) {					
					return bData.getBusID();
				}
			}
		} catch (Exception e) {		
			e.printStackTrace();		
		}
		return null;
	}
	
	public BusinessClassProperties getBusinessProperties(String busID) {
		//log.debug("**************** Start getBusinessProperties *****************");
		//log.debug(">>>>> busID="+busID);		
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vecB = (Vector) (h.get("BusinessClassCacheDataM"));
		//log.debug(">>>>> vecB="+vecB);		
		try {
			for (int i = 0; i < vecB.size(); i++) {
				BusinessClassProperties bData = (BusinessClassProperties) vecB.elementAt(i);
				String pid = bData.getProductID();
				String cid = bData.getChannelID();
				String oid = bData.getOrgID();
				if (bData.getBusID().equals(busID)) {
					return bData;
				}
			}
		} catch (Exception e) {		
			e.printStackTrace();		
		}
		return null;
	}

	
	public static String getFormIDByBus(String busClassID) {
//		#septemwi comment change Logic >> SubFormCache.getFormBusiness()
//	    //log.debug("***************** Start getFormIDByBus ***************");				
//	   // log.debug(">>>>> bus="+bus);			
//		if(bus==null || bus.trim().equals("")){		
//			return re;
//		}		
//		try {
//			HashMap h = TableLookupCache.getCacheStructure();
//			Vector formBus = (Vector) (h.get("FormBusinessClassCacheDataM"));
////			log.debug(">>>>> formBus="+formBus);	
////			log.debug(">>>>> bus="+bus);	
//			for (int i = 0; i < formBus.size(); i++) {
//				FormBusinessClassProperties model = (FormBusinessClassProperties) formBus.elementAt(i);
//				if (bus.equalsIgnoreCase(model.getBusClassID())) {				
//					return model.getFormID();
//				}// if
//			}// for
//		} catch (Exception e) {
//			e.printStackTrace();						
//		}
//		return re;
		String formID = "";
		if(null == busClassID || "".equals(busClassID.trim())){
			return formID;
		}
		FormBusinessClassProperties dataM = SubFormCache.getFormBusiness().get(busClassID);
		if(null != dataM){
			formID = dataM.getFormID();
		}
		return formID;
	}
	
	public Vector<ORIGSubForm> loadSubFormsForValidation(Vector<String> userRoles, String formID) {
		Vector<ORIGSubForm> vSubForms = new Vector<ORIGSubForm>();
		
//		//log.debug(">>>>> Start loadSubForms");	
//		//log.debug("userRoles="+userRoles);
//		//log.debug("formID="+formID);
//		// get From Cache
//		HashMap h = TableLookupCache.getCacheStructure();
//		Vector allSubForm = (Vector) (h.get("SubFormPropertiesCacheDataM"));
//		Vector allRoleSubForm = (Vector) (h.get("SubFormRoleCacheDataM"));
//		Vector allFormBusiness = (Vector) (h.get("FormBusinessClassCacheDataM"));
//		
//		HashMap allSubFormIDInRole = getSubFormIDByUserRole(allRoleSubForm, (String) userRoles.elementAt(0), formID);
//		
//		try {
//			//loop for put sub form to hash map
//			//log.debug("allSubFormIDInRole.size()="+allSubFormIDInRole.size());				
//			for (int i = 0; i < allSubForm.size(); i++) { //for 1
//				SubFormProperties sp = (SubFormProperties) allSubForm.get(i);
//				String formIDfromSubForm = sp.getFormID();
//				//log.debug("formIDfromSubForm="+formIDfromSubForm);				
//				if (formIDfromSubForm != null && formIDfromSubForm.equals(formID)) { //if 1
//					String className = sp.getSubFormClass();
//					String formTab = sp.getFormTab();
//					String fileName = sp.getFileName();
//					String subFormID = sp.getSubFormID();
//					String subformDesc = sp.getSubformDesc();
//					String subformClass = sp.getSubFormClass();
//					if (allSubFormIDInRole.containsKey(subFormID) && !subFormID.equals(OrigConstant.SubformName.PRE_SCORE_SUBFORM)) {																					
//						// if 2							
//						if (className != null ) { // if 3
//							ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(className)).newInstance());
//							// change for naos get seq from role
//							int seq = 0;
//							try {
//								String seqS = (String) allSubFormIDInRole.get(subFormID);
//								seq = Integer.parseInt(seqS);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//							// end change
//							subForm.setLocatedTab(formTab);
//							subForm.setFileName(fileName);
//							subForm.setSubFormID(subFormID);
//							subForm.setSubFormSeq(seq);
//							subForm.setSubformDesc(subformDesc);
//							subForm.setSubFormClass(subformClass);
//							//log.debug("put hash map");
//							//log.debug("put --> (seq=" + seq + ") (formTab=" + formTab + ") (subFormID=" + subFormID + ") ");
//							vSubForms.add(subForm);
//							//log.debug("subFormID = " + subFormID);
//						} // end if 3
//					} // endif 2
//				} // end if 1
//			} //end for1
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// end of  get From Cache		
		
		String key = KeySubForm.getKeySubFormRole(formID, getCurrentRole(userRoles));		
		vSubForms = SubFormCache.getSubFormRole().get(key);
		
		return vSubForms;
	}
	
	/*
	 * Praisan for load validation sub form by string role
	 */
	public Vector<ORIGSubForm> loadSubFormsForValidation(String validateRole, String formID) {
		Vector<ORIGSubForm> vSubForms = new Vector<ORIGSubForm>();
		
//		#septemwi change logic loadSubFormsForValidation()
//		//log.debug(">>>>> Start loadSubForms");	
//		//log.debug("userRoles="+userRoles);
//		//log.debug("formID="+formID);
//		// get From Cache
//		HashMap h = TableLookupCache.getCacheStructure();
//		Vector allSubForm = (Vector) (h.get("SubFormPropertiesCacheDataM"));
//		Vector allRoleSubForm = (Vector) (h.get("SubFormRoleCacheDataM"));
//		Vector allFormBusiness = (Vector) (h.get("FormBusinessClassCacheDataM"));
//		
//		HashMap allSubFormIDInRole = getSubFormIDByUserRole(allRoleSubForm, validateRole, formID);
//		
//		try {
//			//loop for put sub form to hash map
//			//log.debug("allSubFormIDInRole.size()="+allSubFormIDInRole.size());				
//			for (int i = 0; i < allSubForm.size(); i++) { //for 1
//				SubFormProperties sp = (SubFormProperties) allSubForm.get(i);
//				String formIDfromSubForm = sp.getFormID();
//				//log.debug("formIDfromSubForm="+formIDfromSubForm);				
//				if (formIDfromSubForm != null && formIDfromSubForm.equals(formID)) { //if 1
//					String className = sp.getSubFormClass();
//					String formTab = sp.getFormTab();
//					String fileName = sp.getFileName();
//					String subFormID = sp.getSubFormID();
//					String subformDesc = sp.getSubformDesc();
//					String subformClass = sp.getSubFormClass();
//					if (allSubFormIDInRole.containsKey(subFormID) && !subFormID.equals(OrigConstant.SubformName.PRE_SCORE_SUBFORM)) {																					
//						// if 2							
//						if (className != null ) { // if 3
//							ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(className)).newInstance());
//							// change for naos get seq from role
//							int seq = 0;
//							try {
//								String seqS = (String) allSubFormIDInRole.get(subFormID);
//								seq = Integer.parseInt(seqS);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//							// end change
//							subForm.setLocatedTab(formTab);
//							subForm.setFileName(fileName);
//							subForm.setSubFormID(subFormID);
//							subForm.setSubFormSeq(seq);
//							subForm.setSubformDesc(subformDesc);
//							subForm.setSubFormClass(subformClass);
//							//log.debug("put hash map");
//							//log.debug("put --> (seq=" + seq + ") (formTab=" + formTab + ") (subFormID=" + subFormID + ") ");
//							vSubForms.add(subForm);
//							//log.debug("subFormID = " + subFormID);
//						} // end if 3
//					} // endif 2
//				} // end if 1
//			} //end for1
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// end of  get From Cache		
		
		String key = KeySubForm.getKeySubFormRole(formID, validateRole);		
		vSubForms = SubFormCache.getSubFormRole().get(key);
		
		return vSubForms;
	}
	
	public static String getOrgID(String busID) {
		HashMap h = TableLookupCache.getCacheStructure();
		Vector vecB = (Vector) (h.get("BusinessClassCacheDataM"));
		String org = "";

		for (int i = 0; i < vecB.size(); i++) { // for
			BusinessClassProperties data = (BusinessClassProperties) vecB.elementAt(i);
			if(data.getBusID().equals(busID)){
			    org = data.getOrgID();
			    return org;
			}
		} // E for

		return org;
	}
	
}

