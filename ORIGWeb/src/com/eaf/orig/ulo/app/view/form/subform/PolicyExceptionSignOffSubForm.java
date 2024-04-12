package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class PolicyExceptionSignOffSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PolicyExceptionSignOffSubForm.class);
	String subformId ="POLICY_EXCEPTION_SIGN_OFF";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		
		String POLICY_EXCEPTION_SIGN_OFF_DATE = request.getParameter("POLICY_EXCEPTION_SIGN_OFF_DATE");
		String POLICY_EXCEPTION_AUTHORIZED_BY = request.getParameter("POLICY_EXCEPTION_AUTHORIZED_BY");
		
		logger.debug("POLICY_EXCEPTION_SIGN_OFF_DATE >> "+POLICY_EXCEPTION_SIGN_OFF_DATE);	
		logger.debug("POLICY_EXCEPTION_AUTHORIZED_BY >> "+POLICY_EXCEPTION_AUTHORIZED_BY);			
		
		applicationGroup.setPolicyExSignOffBy(POLICY_EXCEPTION_AUTHORIZED_BY);
		applicationGroup.setPolicyExSignOffDate(FormatUtil.toDate(POLICY_EXCEPTION_SIGN_OFF_DATE,HtmlUtil.TH));
		
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ApplicationDataM application = applicationGroup.getApplication(0);
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "POLICY_EXCEPTION_SIGN_OFF";
		logger.debug("subformId >> "+subformId);
		if(null != application){
			formError.mandatoryDate(subformId,"POLICY_EXCEPTION_SIGN_OFF_DATE",applicationGroup.getPolicyExSignOffDate(),request);	
			formError.mandatory(subformId,"POLICY_EXCEPTION_AUTHORIZED_BY",applicationGroup.getPolicyExSignOffBy(),request);
		}
		return formError.getFormError();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		
		String SUFFIX_ELEMENT_ID=CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.APPLICATION_GROUP,applicationGroup.getApplicationGroupId());
		applicationGroup.setPolicyExSignOffBy(formValue.getValue("POLICY_EXCEPTION_AUTHORIZED_BY","POLICY_EXCEPTION_AUTHORIZED_BY_"+SUFFIX_ELEMENT_ID,applicationGroup.getPolicyExSignOffBy()));
		applicationGroup.setPolicyExSignOffDate(formValue.getValue("POLICY_EXCEPTION_SIGN_OFF_DATE","POLICY_EXCEPTION_SIGN_OFF_DATE_"+SUFFIX_ELEMENT_ID,applicationGroup.getPolicyExSignOffDate()));
		
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("POLICY_EXCEPTION_SIGN_OFF_DATE");
		filedNames.add("POLICY_EXCEPTION_AUTHORIZED_BY");
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
 				 fieldElement.setElementGroupId(applicationGroup.getApplicationGroupId());
				 fieldElement.setElementGroupLevel(CompareDataM.GroupDataLevel.APPLICATION_GROUP); 
				 fieldElements.add(fieldElement);
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.auditForm(subformId, "POLICY_EXCEPTION_SIGN_OFF_DATE", applicationGroup, lastApplicationGroup, request);
		auditFormUtil.auditForm(subformId, "POLICY_EXCEPTION_AUTHORIZED_BY", applicationGroup, lastApplicationGroup, request);
		
		return auditFormUtil.getAuditForm();
	}
}
