package com.eaf.orig.ulo.app.view.form.addressphone;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class DummyPhoneNo extends ElementHelper{
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String VERIFY_CUSTOMER_DISPLAY = SystemConstant.getConstant("VERIFY_CUSTOMER_DISPLAY");
	String DUMMY_PHONE_NUMBER = SystemConstant.getConstant("DUMMY_PHONE_NUMBER");
	String CUSTOMER_SUBFORM="CONTACT_CUSTOMER_VERIFY_SUBFORM";
	String HR_SUBFORM="CONTACT_CUSTOMER_HR_SUBFORM";
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		String DISPLAY = (String)getObjectRequest();
		FormUtil formUtil = new FormUtil(DISPLAY.equals(VERIFY_CUSTOMER_DISPLAY)?CUSTOMER_SUBFORM:HR_SUBFORM,request);
		StringBuilder HTML = new StringBuilder();
		HTML.append(" <tr> ")
			.append(" 	<td width='250px'> ")
			/*.append( 		HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "", "", HtmlUtil.EDIT, "",
							FormatUtil.display(personalInfo.getPersonalType()!=null && 
							PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) ? 
							HtmlUtil.getText(request, "PRIMARY_CARD") : 
							HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") )+" "+HtmlUtil.getText(request, "PHONE_NUMBER") , "col-sm-12", request) )*/
			.append( 		HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "", "", "", "", "",
							FormatUtil.display(personalInfo.getPersonalType()!=null && 
							PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) ? 
							HtmlUtil.getText(request, "PRIMARY_CARD") : 
							HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") )+" "+HtmlUtil.getText(request, "PHONE_NUMBER"), "col-sm-12", formUtil) )
			.append(" 	</td>")
			.append(" 	<td>"+FormatUtil.getMobileNo(DUMMY_PHONE_NUMBER)+"</td>")
			.append("	<td>"+HtmlUtil.hidden("PHONE_NO", FormatUtil.getMobileNo(DUMMY_PHONE_NUMBER))+"</td>")
			.append(" 	<td></td>")
			.append(" </tr> ");
		return HTML.toString();
	}

	@Override
	public String renderElementFlag(HttpServletRequest request, Object objectElement) {
		String FLAG = MConstant.FLAG.NO;
		String DISPLAY = (String)getObjectRequest();
		if(VERIFY_CUSTOMER_DISPLAY.equals(DISPLAY)){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
			if(!Util.empty(personalInfo)){
				FLAG = MConstant.FLAG.YES;
			}
		}
		return FLAG;
	}
}
