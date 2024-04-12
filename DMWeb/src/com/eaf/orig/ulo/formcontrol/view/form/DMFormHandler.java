
package com.eaf.orig.ulo.formcontrol.view.form;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FormErrorDataM;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.ulo.app.view.form.subform.DmDocCheckList;
import com.eaf.orig.ulo.app.view.form.subform.DmDocManagement;
import com.eaf.orig.ulo.app.view.form.subform.DmWareHouseInfo;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class DMFormHandler extends FormHandler {
	private static transient Logger logger = Logger.getLogger(DMFormHandler.class);
	public static final String DMForm = "DMForm";	
	private String formId;
	private String formActivity;
	private String displayMode;
	private DocumentManagementDataM objectForm;
	private ArrayList<FormErrorDataM> errorForms;
	public DMFormHandler(){
		super();
		objectForm = new DocumentManagementDataM();
	}
	@Override
	public boolean validInSessionScope() {		
		return false;
	}
	@Override
	public boolean validateForm(HttpServletRequest request) {	
		String validateForm = request.getParameter("validateForm");
		logger.debug("validateForm >> "+validateForm);
		if(validateForm(validateForm)){			
			if(MConstant.DM_FORM_NAME.DM_STORE_FORM.equals(formId)){
				DmDocCheckList docCheckList  = new DmDocCheckList();
				HashMap<String,Object> errorsDocCheckList = docCheckList.validateForm(request,objectForm);
				logger.debug("errorsDocCheckList >> "+errorsDocCheckList);
				if(!Util.empty(errorsDocCheckList)){
					FormErrorDataM formError = new FormErrorDataM();
					formError.setSubformId("DM_DOC_CHECK_LIST");
					formError.setErrors(errorsDocCheckList);
					errorForms.add(formError);
				}
				DmWareHouseInfo docWereHouse  = new DmWareHouseInfo();
				HashMap<String,Object> errorsdocWereHouse = docWereHouse.validateForm(request,objectForm);
				logger.debug("errorsdocWereHouse >> "+errorsdocWereHouse);
				if(!Util.empty(errorsdocWereHouse)){
					FormErrorDataM formError = new FormErrorDataM();
					formError.setSubformId("DM_WAREHOUSE_INFO");
					formError.setErrors(errorsdocWereHouse);
					errorForms.add(formError);
				}
			}else if(MConstant.DM_FORM_NAME.DM_BORROW_FORM.equals(formId)){
				DmDocManagement docManagement = new DmDocManagement();
				HashMap<String,Object> errorsdocManagement = docManagement.validateForm(request,objectForm);
				logger.debug("errorsdocManagement >> "+errorsdocManagement);
				if(!Util.empty(errorsdocManagement)){
					FormErrorDataM formError = new FormErrorDataM();
					formError.setSubformId("DM_DOC_MANAGEMENT");
					formError.setErrors(errorsdocManagement);
					errorForms.add(formError);
				}
			}
			return Util.empty(errorForms);
		}
		return true;
	}
	@Override
	public void postProcessForm(HttpServletRequest request) {
		
	}
	@Override
	public void setProperties(HttpServletRequest request) {
		logger.debug("formId >> "+formId);
		if(MConstant.DM_FORM_NAME.DM_STORE_FORM.equals(formId)){
			DmDocCheckList docCheckList  = new DmDocCheckList() ;
			docCheckList.setProperties(request,objectForm);
			DmWareHouseInfo docWereHouse  = new DmWareHouseInfo();
			docWereHouse.setProperties(request,objectForm);
		}else if(MConstant.DM_FORM_NAME.DM_BORROW_FORM.equals(formId)){
			DmDocManagement docManagement = new DmDocManagement();
			docManagement.setProperties(request,objectForm);
		}
	}
	@Override
	public void clearForm() {
		
	}
	public static DMFormHandler getForm(HttpServletRequest request){
		DMFormHandler formHandler = (DMFormHandler)request.getSession().getAttribute(DMForm);
		if(null == formHandler){
			formHandler = new DMFormHandler();
			request.getSession().setAttribute(DMForm,formHandler);
		}
		return formHandler;		
	}
	public static DocumentManagementDataM getObjectForm(HttpServletRequest request){
		DMFormHandler formHandler = (DMFormHandler)request.getSession().getAttribute(DMForm);
		if(null == formHandler){
			formHandler = new DMFormHandler();
			request.getSession().setAttribute(DMForm,formHandler);
		}
		DocumentManagementDataM objectForm = formHandler.getObjectForm();
		if(null == objectForm){
			objectForm = new DocumentManagementDataM();
			formHandler.setObjectForm(objectForm);
		}
		return objectForm;		
	}
	@Override
	public DocumentManagementDataM getObjectForm() {
		return objectForm;
	}
	@Override
	public void setObjectForm(Object objectForm) {
		this.objectForm = (DocumentManagementDataM)objectForm;
	}	
	public String getFormActivity() {
		return formActivity;
	}
	public void setFormActivity(String formActivity) {
		this.formActivity = formActivity;
	}
	public String getDisplayMode() {
		return displayMode;
	}
	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
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
	public String getFormId(){
		return formId;
	}
	@Override
	public void setFormId(String formId) {
		this.formId = formId;
	}
	@Override
	public String getFormName() {
		return DMForm;
	}
}
