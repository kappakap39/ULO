package com.eaf.orig.ulo.app.view.form.subform.product.kec;

import java.sql.Date;
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
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;


public class SpSignOffSubForm  extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(SpSignOffSubForm.class);
	String subformId ="KEC_SP_SIGN_OFF_SUBFORM";
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(MConstant.Product.K_EXPRESS_CASH);
		
		String spSignOffDate = request.getParameter("SP_SIGN_OFF_DATE_KEC");	
		String spSignOffBy = request.getParameter("SP_SIGN_OFF_AUTHORIZED_BY_KEC");	
			
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				application.setSpSignoffDate(FormatUtil.toDate(spSignOffDate,HtmlUtil.TH));
				application.setSpSignoffAuthBy(spSignOffBy);
			}
		}	
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(MConstant.Product.K_EXPRESS_CASH);
		Date spSignOffDate = application.getSpSignoffDate();	
		String spSignOffBy = application.getSpSignoffAuthBy();
		FormErrorUtil formError = new FormErrorUtil();	
		formError.mandatoryDate(subformId, "SP_SIGN_OFF_DATE", "SP_SIGN_OFF_DATE_KEC", spSignOffDate, request);
		formError.mandatory(subformId,"SP_SIGN_OFF_AUTHORIZED_BY","SP_SIGN_OFF_AUTHORIZED_BY_KEC",spSignOffBy,request);
		return formError.getFormError();
	}
	
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(MConstant.Product.K_EXPRESS_CASH);
		if(!Util.empty(applications)){
			for(ApplicationDataM  applicationDataM : applications){
				String SUFFIX_ELEMENT_ID=CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.APPLICATION,applicationDataM.getApplicationRecordId());
				applicationDataM.setSpSignoffDate(formValue.getValue("SP_SIGN_OFF_DATE_KEC","SP_SIGN_OFF_DATE_"+SUFFIX_ELEMENT_ID,applicationDataM.getSpSignoffDate()));
				applicationDataM.setSpSignoffAuthBy(formValue.getValue("SP_SIGN_OFF_AUTHORIZED_BY_KEC","SP_SIGN_OFF_AUTHORIZED_BY_"+SUFFIX_ELEMENT_ID,applicationDataM.getSpSignoffAuthBy()));
			}
		}
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("SP_SIGN_OFF_DATE");
		filedNames.add("SP_SIGN_OFF_AUTHORIZED_BY");
		try {
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
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
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(MConstant.Product.CREDIT_CARD);
		ApplicationDataM lastApplication = lastApplicationGroup.filterApplicationProductLifeCycle(MConstant.Product.CREDIT_CARD);
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		auditFormUtil.auditForm(subformId, "SP_SIGN_OFF_DATE", application, lastApplication, request);
		auditFormUtil.auditForm(subformId, "SP_SIGN_OFF_AUTHORIZED_BY", application, lastApplication, request);
		return auditFormUtil.getAuditForm();
	}
}
