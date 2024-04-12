package com.eaf.orig.ulo.app.view.form.card.inc.kec;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class SpSignOff extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(SpSignOff.class);
	private static final String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String subformId ="KEC_SP_SIGN_OFF_SUBFORM";
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/product/kec/SpSignOffSubForm.jsp";
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String renderElementFlag(HttpServletRequest request, Object objectElement) {
		String FLAG = MConstant.FLAG.YES;
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectElement;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(Util.empty(applications)){
			FLAG = MConstant.FLAG.NO;
		}
		return FLAG;
	}
	
	@Override
	public  HashMap<String,Object> validateElement(HttpServletRequest request, Object appForm) {
		String  spSignOffDate = request.getParameter("SP_SIGN_OFF_DATE_KEC");	
		String  spSignOffBy = request.getParameter("SP_SIGN_OFF_AUTHORIZED_BY_KEC");	
		FormErrorUtil formError = new FormErrorUtil();	
		formError.mandatory(subformId, "SP_SIGN_OFF_DATE", "SP_SIGN_OFF_DATE_KEC","",spSignOffDate, request);
		formError.mandatory(subformId,"SP_SIGN_OFF_AUTHORIZED_BY","SP_SIGN_OFF_AUTHORIZED_BY_KEC","",spSignOffBy,request);
		return formError.getFormError();
	}
	@Override
	public String processElement(HttpServletRequest request, Object objectElement) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectElement);	
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		logger.debug("SpSignOffSubForm...");
		String  spSignOffDate = request.getParameter("SP_SIGN_OFF_DATE_KEC");	
		String  spSignOffBy = request.getParameter("SP_SIGN_OFF_AUTHORIZED_BY_KEC");	
		logger.debug("spSignOffDate :"+spSignOffDate);	
		logger.debug("spSignOffBy :"+spSignOffBy);	
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				application.setSpSignoffDate(FormatUtil.toDate(spSignOffDate,HtmlUtil.TH));
				application.setSpSignoffAuthBy(spSignOffBy);
			}
		}
		return null;
	}
	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"SP_SIGN_OFF_DATE","SP_SIGN_OFF_AUTHORIZED_BY"};
		try {
			ApplicationDataM applicationM = ((ApplicationDataM)objectForm);	
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationM.getApplicationRecordId());
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				 fieldElement.setElementGroupId(personalInfoM.getPersonalId());
				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfoM));
				 fieldElements.add(fieldElement);
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	
	@Override
	public void displayValueElement(HttpServletRequest request,	Object objectElement, FormDisplayValueUtil formValue) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectElement;
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(MConstant.Product.K_EXPRESS_CASH);
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				String SUFFIX_ELEMENT_ID = CompareSensitiveFieldUtil.getSuffixFieldName(CompareDataM.UniqueLevel.APPLICATION, application.getApplicationRecordId());
				application.setSpSignoffDate(formValue.getValue("SP_SIGN_OFF_DATE", "SP_SIGN_OFF_DATE_"+SUFFIX_ELEMENT_ID, application.getSpSignoffDate()));
				application.setSpSignoffAuthBy(formValue.getValue("SP_SIGN_OFF_AUTHORIZED_BY", "SP_SIGN_OFF_AUTHORIZED_BY_"+SUFFIX_ELEMENT_ID, application.getSpSignoffAuthBy()));
			}
		}
	}
}
