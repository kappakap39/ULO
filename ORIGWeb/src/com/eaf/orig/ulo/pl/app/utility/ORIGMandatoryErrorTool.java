package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.KeySubForm;
import com.eaf.cache.MandatoryFieldCache;
import com.eaf.cache.SubFormCache;
import com.eaf.cache.data.MandatoryCacheDataM;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class ORIGMandatoryErrorTool {

	private static ORIGMandatoryErrorTool me;

	public static Logger logger = Logger.getLogger(ORIGMandatoryErrorTool.class);
	private static String ERROR_PREFIX = null;

	public synchronized static ORIGMandatoryErrorTool getInstance() {
		if (me == null) {
			me = new ORIGMandatoryErrorTool();
		}
		return me;
	}

	public ORIGMandatoryErrorTool() {
		super();
	}

	private String getDisplayName(String fields, String subFormID,HttpServletRequest request) throws Exception{
//		HashMap hash = TableLookupCache.getCacheStructure();
//		Vector vMandatory = (Vector) (hash.get("MandatoryField"));
//		MandatoryCacheDataM cacheM = new MandatoryCacheDataM();
//		for (int i = 0; i < vMandatory.size(); i++) {
//			cacheM = (MandatoryCacheDataM) vMandatory.elementAt(i);
//			if ((cacheM.getFieldName()).equals(fields)
//					&& cacheM.getSubFormName().equals(subFormID)) {
//				// return MessageResourceUtil.getTextDescription(request,
//				// cacheM.getErrorMessage()) + " is required.";
//				return cacheM.getErrorMessage();
//			}
//		}
//		return "";
		String key = KeySubForm.getKeyMandatory(fields, subFormID);
		MandatoryCacheDataM cacheM = SubFormCache.getMandatory().get(key);
		if(null != cacheM){
			return cacheM.getErrorMessage();
		}
		return "";
	}

	public boolean getMandateErrorBySubForm(HttpServletRequest request,String customerType, String formID) throws Exception{
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		String decision = request.getParameter("decision_option");
		boolean result = false;
		logger.debug("[getMandateErrorBySubForm]");
		if (null == ERROR_PREFIX) {
			ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"ERROR_PREFIX");
		}

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);

		ORIGFormUtil origFormUtil = new ORIGFormUtil();

		Vector<ORIGSubForm> vSubForms = null;
		
		//If DC submit sent to CB need validate same DE role
		//Praisan 20130103
		String mandateRole = userM.getCurrentRole();
		if(OrigConstant.Action.REQUEST_CB.equals(decision) && OrigConstant.ROLE_DC.equals(userM.getCurrentRole())){
			mandateRole = OrigConstant.ROLE_DE;
		}
		
		logger.debug(" >> formID:" + formID);
		if (!OrigUtil.isEmptyString(formID)) {
			vSubForms = origFormUtil.loadSubFormsForValidation(mandateRole, formID);
		}else{
//			HashMap subForms = formHandler.getSubForms();
//			vSubForms = new Vector(subForms.values());
			vSubForms = formHandler.getSubForms();
		}
		
		if(null != vSubForms){
			for (ORIGSubForm subForm : vSubForms) {
				logger.debug("Mandatory SubFormID >> "+subForm.getSubFormID());
				if (this.getErrorMessage(subForm.getSubFormID(), request,customerType, subForm.getSubFormClass())) {
					result = true;
				}
			}
		}
		
		//#SeptemWi ManualMandatory
		if(null != vSubForms){
			for (ORIGSubForm subForm : vSubForms) {
				ManualMandatory.MandatorySubForm(subForm.getSubFormID(), request);
			}
		}
		return result;
	}

	public boolean GetMassageMandatory(HttpServletRequest request,String customerType, String formID, String mandatoryType,String flag) throws Exception{
		boolean result = false;
		if(null == ERROR_PREFIX){
			ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"ERROR_PREFIX");
		}
		logger.debug("GetMassageMandatory >> " + flag);

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);

		ORIGFormUtil origFormUtil = new ORIGFormUtil();

		Vector<ORIGSubForm> vSubForms = null;
		if (!OrigUtil.isEmptyString(formID)) {
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");			
			vSubForms = origFormUtil.loadSubFormsForValidation(userM.getRoles(), formID);
		}else{
//			HashMap subForms = formHandler.getSubForms();
//			vSubForms = new Vector(subForms.values());
			vSubForms = formHandler.getSubForms();
		}
		
		if(null != vSubForms){
			for(ORIGSubForm subForm:vSubForms) {
				if (this.GetErrorMessageMandatory(subForm.getSubFormID(), request,customerType, subForm.getSubFormClass(), flag,mandatoryType)) {
					result = true;
				}
			}
		}
		return result;
	}

	public boolean GetErrorMessageMandatory(String subFormID,HttpServletRequest request, String customerType,
													String subFormClass, String flag, String mandatoryType)  throws Exception{
		if (null == ERROR_PREFIX) {
			ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"ERROR_PREFIX");
		}

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);

		if (null == formHandler) formHandler = new PLOrigFormHandler();

		PLApplicationDataM applicationM = formHandler.getAppForm();

		if (null == applicationM) applicationM = new PLApplicationDataM();

		String fields = "";
		String value = null;
		boolean resultFlag = false;

		String errorMsg = null;
		Vector manFields = MandatoryFieldCache.GetMandatoryExecuteForm(customerType, mandatoryType, subFormID, flag);

		if (!OrigUtil.isEmptyVector(manFields)) {
			for (int i = 0; i < manFields.size(); i++) {
				fields = (String) manFields.elementAt(i);
				value = request.getParameter(fields);
				if (OrigUtil.isEmptyString(value)) {
					errorMsg = getDisplayName(fields, subFormID, request);
					formHandler.PushErrorMessage(fields, ERROR_PREFIX + ""+ errorMsg);
					resultFlag = true;
				}
			}
		}

		/** Validate by Subform Class **/
//		try {
			ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(subFormClass)).newInstance());
			boolean result = subForm.validateSubForm(request);
			if (result) {
				resultFlag = result;
			}
//		} catch (InstantiationException e) {
//			logger.error(e);
//		} catch (IllegalAccessException e) {
//			logger.error(e);
//		} catch (ClassNotFoundException e) {
//			logger.error(e);
//		}
		return resultFlag;
	}

	public boolean getErrorMessage(String subFormID,HttpServletRequest request, String customerType, String subFormClass) throws Exception{
		if(null == ERROR_PREFIX){
			ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"ERROR_PREFIX");
		}
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		String decision = request.getParameter("decision_option");

		if (formHandler == null) formHandler = new PLOrigFormHandler();

		PLApplicationDataM applicationM = formHandler.getAppForm();

		if (applicationM == null) applicationM = new PLApplicationDataM();

		String fields = "";
		String value = null;
		boolean resultFlag = false;

		String errorMsg = null;
		
		//If DC submit sent to CB need get error message same DE role
		//Praisan 20130103
		String mandateRole = userM.getCurrentRole();
		if(OrigConstant.Action.REQUEST_CB.equals(decision) && OrigConstant.ROLE_DC.equals(userM.getCurrentRole())){
			mandateRole = OrigConstant.ROLE_DE;
		}
		
		Vector manFields = MandatoryFieldCache.getMandatoryFieldsInOneForm(customerType, mandateRole, subFormID, "Y");

		if (!OrigUtil.isEmptyVector(manFields)) {
			for (int i = 0; i < manFields.size(); i++) {
				fields = (String) manFields.elementAt(i);
				value = request.getParameter(fields);
				if (OrigUtil.isEmptyString(value)) {
					errorMsg = getDisplayName(fields, subFormID, request);
					formHandler.PushErrorMessage(fields, ERROR_PREFIX + ""+ errorMsg);
					resultFlag = true;
				}
			}
		}

		/** Validate by Subform Class **/
//		try {
			ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(subFormClass)).newInstance());
			boolean result = subForm.validateSubForm(request);
			if (result) {
				resultFlag = result;
			}
//		} catch (InstantiationException e) {
//			logger.error(e);
//		} catch (IllegalAccessException e) {
//			logger.error(e);
//		} catch (ClassNotFoundException e) {
//			logger.error(e);
//		}
		return resultFlag;
	}
	

	public boolean getMandatoryCashDay5(HttpServletRequest request,String customerType, String formID) throws Exception{
		logger.debug("[getMandatoryCashDay5]");		
		ManualMandatory.MandatorySubForm("CASH_DAY_ONE_SUBFROM", request);		
		return true;
	}
	
	public boolean getMandateErrorBySubFormDraft(HttpServletRequest request,String customerType, String formID) throws Exception{
		if(null == ERROR_PREFIX){
			ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"ERROR_PREFIX");
		}
		boolean result = false;
		logger.debug("[GetMandateErrorBySubFormDraft]");

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);

		ORIGFormUtil origFormUtil = new ORIGFormUtil();

		// Get Sub Form
		Vector<ORIGSubForm> vSubForms = null;
		if (!OrigUtil.isEmptyString(formID)) {
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			vSubForms = origFormUtil.loadSubFormsForValidation(userM.getRoles(), formID);
		} else {
//			HashMap subForms = formHandler.getSubForms();
//			vSubForms = new Vector(subForms.values());
			vSubForms = formHandler.getSubForms();
		}

		if (!OrigUtil.isEmptyVector(vSubForms)) {
			for (ORIGSubForm subForm :vSubForms) {
				if (this.getErrorMessageDraft(subForm.getSubFormID(), request,customerType, subForm.getSubFormClass())) {
					result = true;
				}
			}
		}
		return result;
	}

	public boolean getErrorMessageDraft(String subFormID,HttpServletRequest request, String customerType, String subFormClass) throws Exception{
		if (null == ERROR_PREFIX) {
			ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"ERROR_PREFIX");
		}
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");

		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);

		if (null == formHandler)formHandler = new PLOrigFormHandler();

		PLApplicationDataM applicationM = formHandler.getAppForm();

		if (null == applicationM) applicationM = new PLApplicationDataM();

		String fields = "";
		String value = null;
		boolean resultFlag = false;

		String errorMsg = null;

		Vector manFields = MandatoryFieldCache.getMandatoryFieldsInOneForm(customerType, userM.getRoles(), subFormID, "D");

		if (!OrigUtil.isEmptyVector(manFields)) {
			for (int i = 0; i < manFields.size(); i++) {
				fields = (String) manFields.elementAt(i);
				value = request.getParameter(fields);
				if (OrigUtil.isEmptyString(value)) {
					errorMsg = getDisplayName(fields, subFormID, request);
					formHandler.PushErrorMessage(fields, ERROR_PREFIX + ""+ errorMsg);
					resultFlag = true;
				}
			}
		}

		/** Validate by Subform Class **/
//		try {
			ORIGSubForm subForm = (ORIGSubForm) ((Class.forName(subFormClass)).newInstance());
			boolean result = subForm.validateSubForm(request);
			if (result) {
				resultFlag = result;
			}
//		} catch (InstantiationException e) {
//			logger.error(e);
//		} catch (IllegalAccessException e) {
//			logger.error(e);
//		} catch (ClassNotFoundException e) {
//			logger.error(e);
//		}
		return resultFlag;
	}
}
