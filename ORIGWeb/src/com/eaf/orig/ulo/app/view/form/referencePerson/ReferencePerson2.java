package com.eaf.orig.ulo.app.view.form.referencePerson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ReferencePerson2 extends ElementHelper {
	private static transient Logger logger = Logger.getLogger(ImplementControl.class);
	String CC_THAIBEV = SystemConfig.getGeneralParam("CC_THAIBEV");
	String APPLICATION_TEMPLATE_THAIBEV = SystemConstant.getConstant("APPLICATION_TEMPLATE_THAIBEV");
	private int refPersonSeq = 2;
	
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)getObjectRequest();
		if(!Util.empty(applicationGroup) ){
			if(APPLICATION_TEMPLATE_THAIBEV.equals(applicationGroup.getApplicationTemplate())){
				return MConstant.FLAG_Y;
			}
		}
		return MConstant.FLAG_N;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		logger.debug("ReferencePerson....");
		return "/orig/ulo/subform/element/ReferencePersonShortElement.jsp?refPersonSeq="+refPersonSeq;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectElement);
		ArrayList<ReferencePersonDataM> referencePersons = applicationGroup.getReferencePersons();
		if(null == referencePersons){
			referencePersons = new ArrayList<ReferencePersonDataM>();
			applicationGroup.setReferencePersons(referencePersons);
		}
		ReferencePersonDataM referencePerson = applicationGroup.getReferencePersons(refPersonSeq);
		if(null == referencePerson){
			referencePerson = new ReferencePersonDataM();
			referencePerson.setSeq(refPersonSeq);
			referencePersons.add(referencePerson);
		}
		String refPerson = "PERSON_"+refPersonSeq;

		String PHONE1 = request.getParameter("REF_PHONE1_"+refPerson);		
		String OFFICE_PHONE = request.getParameter("OFFICE_PHONE_"+refPerson);		
		String OFFICE_PHONE_EXT1 = request.getParameter("OFFICE_PHONE_EXT1_"+refPerson);		
		String MOBILE = request.getParameter("MOBILE_"+refPerson);			
		logger.debug("PHONE1 >>"+PHONE1);		
		logger.debug("OFFICE_PHONE >>"+OFFICE_PHONE);		
		logger.debug("OFFICE_PHONE_EXT1 >>"+OFFICE_PHONE_EXT1);		
		logger.debug("MOBILE >>"+MOBILE);		
		referencePerson.setPhone1(FormatUtil.removeDash(PHONE1));	
		referencePerson.setOfficePhone(FormatUtil.removeDash(OFFICE_PHONE));
		referencePerson.setOfficePhoneExt(FormatUtil.removeDash(OFFICE_PHONE_EXT1));
		referencePerson.setMobile(FormatUtil.removeDash(MOBILE));
		return null;
	}
	@Override
	public HashMap<String, Object> validateElement(HttpServletRequest request,Object objectElement) {
		ReferencePersonDataM referencePerson  = (ReferencePersonDataM)objectElement;
		if(null == referencePerson){
			referencePerson = new ReferencePersonDataM();			
		}	
		String subformId = request.getParameter("subFormId");
		FormErrorUtil formError = new FormErrorUtil();
		formError.mandatory(subformId,"PHONE1",referencePerson.getPhone1(),request);
		return formError.getFormError();
	}
	@Override
	public void displayValueElement(HttpServletRequest request,Object objectElement,FormDisplayValueUtil formValue) {
		ReferencePersonDataM referencePerson  = (ReferencePersonDataM)objectElement;
		referencePerson.setPhone1(formValue.getValue("REF_PHONE1","REF_PHONE1",referencePerson.getPhone1()));
		referencePerson.setOfficePhone(formValue.getValue("OFFICE_PHONE","OFFICE_PHONE",referencePerson.getOfficePhone()));
		referencePerson.setOfficePhoneExt(formValue.getValue("OFFICE_PHONE","OFFICE_PHONE_EXT1",referencePerson.getOfficePhoneExt()));
		referencePerson.setMobile(formValue.getValue("MOBILE","MOBILE",referencePerson.getMobile()));
	}

	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
	ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
	String[] fieldNameList={"REF_PHONE1_PERSON","REF_MOBILE_NO", "REF_OFFICE_PHONE_EXT", "REF_OFFICE_PHONE_NO"};
	try {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)getObjectRequest();
		ReferencePersonDataM referencePerson = (ReferencePersonDataM)objectForm;				
		 for(String elementId:fieldNameList){
			 FieldElement fieldElement = new FieldElement();
			 fieldElement.setElementId(elementId);
			 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION_GROUP);
			 fieldElement.setElementGroupId(applicationGroup.getApplicationGroupId());
			 fieldElement.setElementGroupLevel(CompareDataM.UniqueLevel.APPLICATION_GROUP);
			 fieldElements.add(fieldElement);
		 }
	} catch (Exception e) {
		logger.fatal("ERROR",e);
	}
	return fieldElements;
	}
}
