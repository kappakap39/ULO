package com.eaf.orig.ulo.app.view.form.addressphone;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class OfficePhoneNo extends ElementHelper{
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
	String VERIFY_HR_DISPLAY = SystemConstant.getConstant("VERIFY_HR_DISPLAY");
	String CUSTOMER_SUBFORM="CONTACT_CUSTOMER_VERIFY_SUBFORM";
	String HR_SUBFORM="CONTACT_CUSTOMER_HR_SUBFORM";
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		AddressDataM addressWork = personalInfo.getAddress(ADDRESS_TYPE_WORK);
		String DISPLAY = (String)getObjectRequest();
		FormUtil formUtil = new FormUtil(DISPLAY.equals(VERIFY_HR_DISPLAY)?HR_SUBFORM:CUSTOMER_SUBFORM,request);
		StringBuilder HTML = new StringBuilder();
		HTML.append(" <tr> ")
			.append(" 	<td width='250px'> ");
		if(VERIFY_HR_DISPLAY.equals(DISPLAY)){
			/*HTML.append(HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "", ADDRESS_TYPE_WORK, HtmlUtil.EDIT, "",
				HtmlUtil.getText(request, "OFFICE_PHONE") , "col-sm-12", request))*/
				HTML.append(HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "","", ADDRESS_TYPE_WORK, "", HtmlUtil.getText(request, "OFFICE_PHONE"), "col-sm-12", formUtil))
			.append("	</td> ");
		}else{
			/*HTML.append(HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "", ADDRESS_TYPE_WORK, HtmlUtil.EDIT, "",
				FormatUtil.display(personalInfo.getPersonalType()!=null && PERSONAL_TYPE_APPLICANT
				.equals(personalInfo.getPersonalType()) ? HtmlUtil.getText(request, "PRIMARY_CARD") 
				: HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") )+" "+HtmlUtil.getText(request, "OFFICE_PHONE") , "col-sm-12", request))*/
			HTML.append(HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "", "", ADDRESS_TYPE_WORK, "", 
					FormatUtil.display(personalInfo.getPersonalType()!=null && PERSONAL_TYPE_APPLICANT
					.equals(personalInfo.getPersonalType()) ? HtmlUtil.getText(request, "PRIMARY_CARD") 
					: HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") )+" "+HtmlUtil.getText(request, "OFFICE_PHONE"), "col-sm-12", formUtil) )
				.append("	</td> ");
		}
		HTML.append(" 	<td width='150px'>"+FormatUtil.getPhoneNo(addressWork.getPhone1())+"</td>")
			.append(	HtmlUtil.hidden("PHONE_NO", FormatUtil.getPhoneNo(addressWork.getPhone1())));
		if(!Util.empty(addressWork.getExt1())){
			HTML.append(" 	<td width='100px'> "+ LabelUtil.getText(request, "TO") + " </td>")
				.append("	<td> "+ FormatUtil.display(addressWork.getExt1()) + "</td>")
				.append(HtmlUtil.hidden("EXT", addressWork.getExt1()));
		}else{
			HTML.append(" <td></td>")
				.append(" <td></td>");
		}
		HTML.append(" </tr>");
		return HTML.toString();
	}

	@Override
	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
		String FLAG = MConstant.FLAG.NO;
		PersonalInfoDataM personalinfo = (PersonalInfoDataM)objectElement;
		AddressDataM addressWork = personalinfo.getAddress(ADDRESS_TYPE_WORK);
		if(!Util.empty(addressWork) && !Util.empty(addressWork.getPhone1())){
			personalinfo.addVerCusPhoneNo(addressWork.getPhone1());
			FLAG = MConstant.FLAG.YES;
		}
		return FLAG;
	}
	
}
