package com.eaf.orig.ulo.app.view.form.referencePerson;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;

public class ReferencePersonFull1 extends ElementHelper {
	private static transient Logger logger = Logger.getLogger(ImplementControl.class);
	String CC_THAIBEV = SystemConfig.getGeneralParam("CC_THAIBEV");
	private int refPersonSeq = 1;
	@Override
	public String renderElementFlag(HttpServletRequest request,Object objectElement) {
			return MConstant.FLAG_Y;
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		logger.debug("ReferencePerson....");		
		return "/orig/ulo/subform/element/ReferencePersonFullElement.jsp?refPersonSeq="+refPersonSeq;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectElement);
		String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
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
//		String TH_TITLE_CODE = request.getParameter("REF_TITLE_CODE_"+refPerson);
		String TH_TITLE_DESC = request.getParameter("REF_TITLE_DESC_"+refPerson);
		String TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME_"+refPerson);
		String TH_LAST_NAME = request.getParameter("TH_LAST_NAME_"+refPerson);
		String RELATION = request.getParameter("RELATION_"+refPerson);
		String OFFICE_PHONE = request.getParameter("OFFICE_PHONE_"+refPerson);
		String OFFICE_PHONE_EXT = request.getParameter("OFFICE_PHONE_EXT_"+refPerson);
		String PHONE1 = request.getParameter("PHONE1_"+refPerson);
		String MOBILE = request.getParameter("MOBILE_"+refPerson);
		String TITLE_CODE = ListBoxControl.getName(FIELD_ID_TH_TITLE_CODE,"VALUE",TH_TITLE_DESC,"CODE");
		
		logger.debug("TH_TITLE_CODE >>"+TITLE_CODE);
		logger.debug("TH_TITLE_DESC >>"+TH_TITLE_DESC);
		logger.debug("TH_FIRST_NAME >>"+TH_FIRST_NAME);
		logger.debug("TH_LAST_NAME >>"+TH_LAST_NAME);
		logger.debug("RELATION >>"+RELATION);
		logger.debug("OFFICE_PHONE >>"+OFFICE_PHONE);
		logger.debug("OFFICE_PHONE_EXT >>"+OFFICE_PHONE_EXT);
		logger.debug("PHONE1 >>"+PHONE1);
		logger.debug("MOBILE >>"+MOBILE);
		
		referencePerson.setThTitleCode(TITLE_CODE);
		referencePerson.setThTitleDesc(TH_TITLE_DESC);
		referencePerson.setThFirstName(TH_FIRST_NAME);
		referencePerson.setThLastName(TH_LAST_NAME);
		referencePerson.setRelation(RELATION);
		referencePerson.setOfficePhone(FormatUtil.removeDash(OFFICE_PHONE));
		referencePerson.setOfficePhoneExt(OFFICE_PHONE_EXT);
		referencePerson.setPhone1(FormatUtil.removeDash(PHONE1));
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
		
		formError.mandatory(subformId,"REFERENCE_NAME",referencePerson,request);
		formError.mandatory(subformId,"RELATION",referencePerson.getRelation(),request);	
		formError.mandatory(subformId,"OFFICE_PHONE",referencePerson.getOfficePhone(),request);
		formError.mandatory(subformId,"OFFICE_PHONE_EXT",referencePerson.getOfficePhoneExt(),request);
		formError.mandatory(subformId,"PHONE1",referencePerson.getPhone1(),request);	
		formError.mandatory(subformId,"MOBILE",referencePerson.getMobile(),request);
		
		return formError.getFormError();
	}
	
}
