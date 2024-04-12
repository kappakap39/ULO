package com.eaf.orig.ulo.formcontrol.view.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.CompareSensitiveUtility;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FormErrorDataM;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormDisplayModeUtil;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.subform.DocumentCheckList;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.AuditTrailDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
//import com.eaf.core.ulo.common.display.HtmlUtil;
//import com.eaf.core.ulo.common.util.FormDisplayModeUtil;
//import com.eaf.core.ulo.common.util.FormUtil;
//import com.eaf.orig.ulo.app.view.form.subform.DocumentCheckList;

@SuppressWarnings("serial")
public class ORIGFormHandler extends FormHandler implements Serializable,Cloneable{
	private static transient Logger logger = Logger.getLogger(ORIGFormHandler.class);
	public ORIGFormHandler(){
		super();
		objectForm = new ApplicationGroupDataM();
		subForms = new ArrayList<ORIGSubForm>();
	}
	public static final String PROCESS_APPLICATION_ACTION = "ProcessApplicationAction";
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");	
	public static final String PREFIX_VALIDATE = "VALIDATE_";
	public static final String ORIGForm = "ORIGForm";	
	public static final String CONDITION_TYPE_DEFAULT = "DEFAULT";	
	private ApplicationGroupDataM objectForm;
	private ApplicationGroupDataM lastObjectForm;
	private ArrayList<ORIGSubForm> subForms;
	private String subFormId;
	private String formId;
	private String roleId;
	private String tabId;
	private String templateId;
	private String buttonFile;
	private String formJS;
	private ArrayList<FormErrorDataM> errorForms;
	private HashMap<String,String> requestData;
	@Override
	public boolean validInSessionScope() {		
		return false;
	}	
	@Override
	public boolean validateForm(HttpServletRequest request) {
		String validateForm = request.getParameter("validateForm");
		logger.debug("validateForm >> "+validateForm);
		if(validateForm(validateForm)){
			if(!Util.empty(subForms)){
				for (ORIGSubForm subForm : subForms) {
					logger.debug("subForm >> "+subForm.subFormID+" renderSubformFlag >> "+subForm.renderSubformFlag(request,objectForm)+"  actionFlag >> "+subForm.actionFlag);
					if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request,objectForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
						logger.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
						HashMap<String,Object> errors = subForm.validateForm(request,objectForm);
						logger.debug("errors >> "+errors);
						if(!Util.empty(errors)){
							FormErrorDataM formError = new FormErrorDataM();
							formError.setSubformId(subForm.getSubFormID());
							formError.setErrors(errors);
							errorForms.add(formError);
						}
					}
				}
			}
			String buttonAction = request.getParameter("buttonAction");	
			logger.debug("buttonAction >> "+buttonAction);
			try{
				if(!Util.empty(buttonAction)){
//					ValidateFormInf validateFormInf = ImplementControl.getValidateForm(PREFIX_VALIDATE+buttonAction, roleId);
//					HashMap<String,Object> hashFormErrors = null;
//					 if(null!=validateFormInf){
//						 hashFormErrors = validateFormInf.validateForm(request,objectForm);
//					 }
//					 if(!Util.empty(hashFormErrors)){
//						FormErrorDataM formError = new FormErrorDataM();
//						formError.setErrors(hashFormErrors);
//						errorForms.add(formError);
//					}
					ProcessActionInf processAction = ImplementControl.getProcessAction(PROCESS_APPLICATION_ACTION,roleId);
					if(null != processAction){
						processAction.init(request,buttonAction,objectForm);
						HashMap<String,Object> hashFormErrors = processAction.validateAction(request, objectForm);
						if(!Util.empty(hashFormErrors)){
							FormErrorDataM formError = new FormErrorDataM();
							formError.setErrors(hashFormErrors);
							errorForms.add(formError);
						}
					}
				}
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
			return Util.empty(errorForms);
		}
		return true;
	}
	@Override
	public void postProcessForm(HttpServletRequest request) {		
		String buttonAction = request.getParameter("buttonAction");	
		logger.debug("postProcessForm().."+buttonAction);
		compareSensitiveFormAction(request, null);
//		#rawi comment change auditForm at SaveApplicationWebAction
//		auditForm(request);
		try{
			ProcessActionInf processAction = ImplementControl.getProcessAction(PROCESS_APPLICATION_ACTION,roleId);
			if(null != processAction){
				processAction.init(request,buttonAction,objectForm);
				processAction.preProcessAction();
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
	}	
	public void compareSensitiveFormAction(HttpServletRequest request,String compareAction){
		try{
			HashMap<String, CompareDataM> comparisonFields = CompareSensitiveUtility.getCompareFields(objectForm,subForms,compareAction,request);
			HashMap<String, CompareDataM> existingComparisonFields = objectForm.getComparisonField(CompareDataM.SoruceOfData.TWO_MAKER);
			String formId = getFormId();
			logger.debug("formId>>"+formId);
			boolean isDefaultConditionType = CompareSensitiveUtility.isFilterFormCondition(formId, CONDITION_TYPE_DEFAULT);
			logger.debug("isDefaultConditionType>>"+ isDefaultConditionType);
					
			if(isDefaultConditionType){				 
				if(!Util.empty(existingComparisonFields) && !Util.empty(comparisonFields)){
					 for(CompareDataM compareDataM : comparisonFields.values()){
						 CompareDataM  existingCompareData = existingComparisonFields.get(compareDataM.getFieldName());
						 if(!Util.empty(existingCompareData)){
							 if(MConstant.FLAG.SUBMIT.equals(existingCompareData.getCompareFlag())){
								 boolean isSame=CompareObject.compare(compareDataM.getValue(), existingCompareData.getValue(), null);
								 if(isSame){
									 compareDataM.setCompareFlag(existingCompareData.getCompareFlag());
								 }
							 }
							
						 }
					 }
				}
			}else{
				comparisonFields = updateCompareField(existingComparisonFields,comparisonFields);
			}

			logger.debug("compare sensitive roleId >> "+roleId);
			logger.debug("compare sensitive DataMap >> "+comparisonFields);
			if(!Util.empty(comparisonFields)){
				ComparisonGroupDataM comparisonGroup = objectForm.getComparisonGroups(CompareDataM.SoruceOfData.TWO_MAKER);
				if(null == comparisonGroup){
					comparisonGroup = new ComparisonGroupDataM();
					comparisonGroup.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
					objectForm.addComparisonGroups(comparisonGroup);
				}
				comparisonGroup.setComparisonFields(comparisonFields);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	
	private HashMap<String, CompareDataM> updateCompareField(HashMap<String, CompareDataM> updateCompare, HashMap<String, CompareDataM> hDataForUpdate){
		
		if(Util.empty(updateCompare)){
			updateCompare = new HashMap<String, CompareDataM>();
		}
		if(!Util.empty(hDataForUpdate)){
			updateCompare.putAll(hDataForUpdate);
		}
		return updateCompare;
	}
	
	
	public void checkCompareDataChangeForPrevRole(HttpServletRequest request){
		try{	
			HashMap<String, CompareDataM> comparisonFields = CompareSensitiveUtility.checkCompareDataChangeForPrevRole(request,objectForm);
			logger.debug("compare sensitive DataMap >> "+comparisonFields);
			if(!Util.empty(comparisonFields)){
				ComparisonGroupDataM comparisonGroup = objectForm.getComparisonGroups(CompareDataM.SoruceOfData.PREV_ROLE);
				if(null == comparisonGroup){
					comparisonGroup = new ComparisonGroupDataM();
					comparisonGroup.setSrcOfData(CompareDataM.SoruceOfData.PREV_ROLE);
					objectForm.addComparisonGroups(comparisonGroup);
				}
				comparisonGroup.setComparisonFields(comparisonFields);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}
	
	public void auditForm(HttpServletRequest request){	
		String auditFlag = objectForm.getAuditLogFlag();
		logger.debug("auditFlag >> "+auditFlag);
		ArrayList<AuditTrailDataM> auditTrailLogs = objectForm.getAuditTrailLogs();
		if(null == auditTrailLogs){
			auditTrailLogs = new ArrayList<AuditTrailDataM>();
			objectForm.setAuditTrailLogs(auditTrailLogs);
		}
		if(auditForm(auditFlag)){
			if(!Util.empty(subForms)){
				for (ORIGSubForm subForm : subForms) {
					if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request, objectForm))){
						ArrayList<AuditDataM> audits = subForm.auditForm(request,objectForm,lastObjectForm);
						if(!Util.empty(audits)){
							for (AuditDataM audit:audits) {
								AuditTrailDataM auditTrail = new AuditTrailDataM();
								auditTrail.setFieldName(audit.getFieldName());
								auditTrail.setNewValue(audit.getNewValue());
								auditTrail.setOldValue(audit.getOldValue());
								auditTrail.setRole(roleId);
								auditTrail.setCreateRole(roleId);
								auditTrailLogs.add(auditTrail);
							}
						}
					}
				}
			}
		}
	}
	public boolean auditForm(String auditFlag){
		return MConstant.AuditFlag.AUDIT_DATA.equals(auditFlag);
	}
	
	@Override
	public void setProperties(HttpServletRequest request) {
		if(null != objectForm){
			UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
			objectForm.setUserId(userM.getUserName());			
		}
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms) {
				logger.debug("subForm.getSubFormClass >> "+subForm.getSubFormClass());	
				if(MConstant.FLAG.YES.equals(subForm.renderSubformFlag(request, objectForm)) && MConstant.FLAG.YES.equals(subForm.actionFlag)){
					subForm.setProperties(request,objectForm);
				}
			}
		}
		
		DocumentCheckListUtil.defaultRequiredDocFlag(objectForm);		
		String DOCUMENT_CHECK_LIST_SUBFORM_ID = "DOCUMENT_CHECK_LIST";		
		FormUtil formUtil = new FormUtil("DOCUMENT_HEADER_FORM",DOCUMENT_CHECK_LIST_SUBFORM_ID,request);
		String displayMode = FormDisplayModeUtil.getDisplayMode("DOC_CHECKLIST_VAL", "", formUtil);	
		logger.debug("DOCUMENT_CHECK_LIST.displayMode : "+displayMode);
		if(HtmlUtil.EDIT.equals(displayMode)) {
			ORIGSubForm docSubForm = new DocumentCheckList();
				docSubForm.setProperties(request, objectForm);
		}			
		PersonalInfoUtil.clearNotMatchSoruceOfDataCis(request);
//		#rawi defaultNotReceivedDocumentReason
		DocumentCheckListUtil.defaultNotReceivedDocumentReason(objectForm);
		ApplicationUtil.setApplicationFinalDecision(objectForm.filterApplicationLifeCycle());		
		ApplicationUtil.defaultCardLinkCustId(objectForm);
		ApplicationUtil.clearNotMatchApplicationRelation(objectForm);
		ApplicationUtil.reGenerateApplicationNo(objectForm);
		
		String buttonAction = request.getParameter("buttonAction");	
		logger.debug("buttonAction >> "+buttonAction);
		try{
			if(!Util.empty(buttonAction)){
				ProcessActionInf processAction = ImplementControl.getProcessAction(PROCESS_APPLICATION_ACTION,roleId);
				if(null != processAction){
					processAction.init(request,buttonAction,objectForm);
					processAction.propertiesAction(request, objectForm);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		
	}
	@Override
	public void setProperties(HttpServletRequest request,String subformId) {
		logger.debug("subformId >> "+subformId);
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms) {
				if(subForm.getSubFormID().equals(subformId)){
					subForm.setProperties(request,objectForm);
				}
			}
		}
	}
	@Override
	public void clearForm() {
		
	}
	public static ORIGFormHandler getForm(HttpServletRequest request){
		ORIGFormHandler formHandler = (ORIGFormHandler)request.getSession().getAttribute(ORIGForm);
		if(null == formHandler){
			formHandler = new ORIGFormHandler();
			request.getSession().setAttribute(ORIGForm,formHandler);
		}
		return formHandler;		
	}
	public static ApplicationGroupDataM getObjectForm(HttpServletRequest request){
		ORIGFormHandler formHandler = (ORIGFormHandler)request.getSession().getAttribute(ORIGForm);
		if(null == formHandler){
			formHandler = new ORIGFormHandler();
			request.getSession().setAttribute(ORIGForm,formHandler);
		}
		ApplicationGroupDataM objectForm = formHandler.getObjectForm();
		if(null == objectForm){
			objectForm = new ApplicationGroupDataM();
			formHandler.setObjectForm(objectForm);
		}
		return objectForm;		
	}
	@Override
	public ApplicationGroupDataM getObjectForm() {
		if(null == objectForm){
			objectForm = new ApplicationGroupDataM();
		}
		return objectForm;
	}	
	@Override
	public void setObjectForm(Object objectForm) {
		if(objectForm instanceof ApplicationGroupDataM){
			this.objectForm = (ApplicationGroupDataM)objectForm;
		}
	}
	@Override
	public ArrayList<ORIGSubForm> getSubForm() {
		return subForms;
	}	
	@Override
	public void setSubForm(ArrayList<ORIGSubForm> subForms) {
		this.subForms = subForms;
	}
	public String getSubFormId() {
		return subFormId;
	}
	public void setSubFormId(String subFormId) {
		this.subFormId = subFormId;
	}
	@Override
	public String getFormId() {
		return formId;
	}
	@Override
	public void setFormId(String formId) {
		this.formId = formId;
	}
	@Override
	public String getRoleId() {
		return roleId;
	}
	@Override
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	@Override
	public String getTabId() {
		return tabId;
	}
	@Override
	public void setTabId(String tabId) {
		this.tabId = tabId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	@Override
	public String getButtonFile() {
		return buttonFile;
	}
	@Override
	public void setButtonFile(String buttonFile) {
		this.buttonFile = buttonFile;
	}		
	@Override
	public String getFormName(){
		return ORIGForm;
	}
	@Override
	public String getFormJS() {
		return formJS;
	}
	@Override
	public void setFormJS(String formJS) {
		this.formJS = formJS;
	}
	@Override
	public ArrayList<FormErrorDataM> getErrorForm() {
		return errorForms;
	}
	@Override
	public void setErrorForm(ArrayList<FormErrorDataM> errorForm) {
		this.errorForms = errorForm;
	}
	@Override
	public void clearErrorForm() {
		if(null != errorForms){
			errorForms.clear();
		}
		errorForms = new ArrayList<FormErrorDataM>();
	}
	@Override
	public HashMap<String, String> getRequestData() {
		return requestData;
	}
	@Override
	public void setRequestData(HashMap<String, String> requestData) {
		this.requestData = requestData;
	}
	@Override
	public void setRequestData(HttpServletRequest request) {
		if(null == requestData){
			requestData = new HashMap<String, String>();
		}
		Enumeration<String> enumeration = request.getParameterNames();
		if(null != enumeration){
		    while(enumeration.hasMoreElements()){
		        String name = (String) enumeration.nextElement();
		        String value = request.getParameter(name);
		        requestData.put(name,value);
		    }
		}
	    logger.debug("requestData >>"+requestData);
	}
	@Override
	public ApplicationGroupDataM getLastObjectForm() {
		return lastObjectForm;
	}
	@Override
	public void setLastObjectForm(Object lastObjectForm) {
		this.lastObjectForm = (ApplicationGroupDataM)lastObjectForm;
	}
	@Override
	public ORIGSubForm getSubForm(String subFormId) {
		if(!Util.empty(subForms)){
			for (ORIGSubForm subForm : subForms){
				if(subForm.getSubFormID().equals(subFormId)){
					return subForm;
				}
			}
		}
		return null;
	}
}
