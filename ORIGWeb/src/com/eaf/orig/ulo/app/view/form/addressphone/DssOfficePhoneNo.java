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

public class DssOfficePhoneNo extends ElementHelper{
	String VERIFY_HR_DISPLAY = SystemConstant.getConstant("VERIFY_HR_DISPLAY");
	String CUSTOMER_SUBFORM="CONTACT_CUSTOMER_VERIFY_SUBFORM";
	String HR_SUBFORM="CONTACT_CUSTOMER_HR_SUBFORM";
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		StringBuilder HTML = new StringBuilder();
		String DISPLAY = (String)getObjectRequest();
		FormUtil formUtil = new FormUtil(DISPLAY.equals(VERIFY_HR_DISPLAY)?HR_SUBFORM:CUSTOMER_SUBFORM,request);
		HTML.append(" <tr> ")
			.append(" 	<td width='250px'> ") 
//			.append(		HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "","", "", HtmlUtil.EDIT, "","CONTACT_TYPE_OFFICE_PHONE_DSS","", request))
			.append(		HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "CONTACT_TYPE_OFFICE_PHONE", "", "", "", "", "CONTACT_TYPE_OFFICE_PHONE_DSS", "", formUtil))
			.append("	</td> ")
			.append(" 	<td width='150px'>"+FormatUtil.getPhoneNo(personalInfo.getPhoneNoBol())+"</td>")
			.append(	HtmlUtil.hidden("PHONE_NUMBER_OTH",FormatUtil.getPhoneNo(personalInfo.getPhoneNoBol())))
			.append(" 	<td width='100px'></td> ")
			.append(" 	<td></td> ")
			.append(" </tr> ");
		return HTML.toString();
	}

	@Override
	public String renderElementFlag(HttpServletRequest request, Object objectElement) {
		String FLAG = MConstant.FLAG.NO;
		String DISPLAY = (String)getObjectRequest();
		if(VERIFY_HR_DISPLAY.equals(DISPLAY)){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
			if(!Util.empty(personalInfo.getPhoneNoBol()) ){
				personalInfo.addVerCusPhoneNo(personalInfo.getPhoneNoBol());
				FLAG = MConstant.FLAG.YES;
			}
		}
		return FLAG;
	}
	
}
