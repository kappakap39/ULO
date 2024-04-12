package com.eaf.orig.ulo.app.view.form.referencePerson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

public class ReferencePersonDE11_2 extends ElementHelper {
	private static transient Logger logger = Logger.getLogger(ImplementControl.class);
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
		return "/orig/ulo/subform/element/ReferencePersonDE1_1Element.jsp?refPersonSeq="+refPersonSeq;
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
		logger.debug("PHONE1 >>"+PHONE1);		

		referencePerson.setPhone1(FormatUtil.removeDash(PHONE1));	
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
	}
	
}
