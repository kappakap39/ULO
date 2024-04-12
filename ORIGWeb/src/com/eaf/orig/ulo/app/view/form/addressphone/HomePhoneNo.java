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

public class HomePhoneNo extends ElementHelper {
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	String VERIFY_CUSTOMER_DISPLAY = SystemConstant.getConstant("VERIFY_CUSTOMER_DISPLAY");
	String CUSTOMER_SUBFORM="CONTACT_CUSTOMER_VERIFY_SUBFORM";
	String HR_SUBFORM="CONTACT_CUSTOMER_HR_SUBFORM";
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String DISPLAY = (String)getObjectRequest();
		FormUtil formUtil = new FormUtil(DISPLAY.equals(VERIFY_CUSTOMER_DISPLAY)?CUSTOMER_SUBFORM:HR_SUBFORM,request);
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
		AddressDataM addressCurrent	= personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
		StringBuilder HTML = new StringBuilder();
		HTML.append(" <tr> ")
			.append(" 	<td width='250px'> ")
			.append(HtmlUtil.radioInline("CONTACT_TYPE_OFFICE_PHONE", "", "", "", "", "", ADDRESS_TYPE_CURRENT, "", FormatUtil.display(personalInfo.getPersonalType()!=null && PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType()) ? HtmlUtil.getText(request, "PRIMARY_CARD") : HtmlUtil.getText(request, "SUPPLEMENTARY_CARD") )+" "+HtmlUtil.getText(request, "M_HOME_TEL"), "col-sm-12", formUtil))
			.append("	</td> ")
			.append("	<td width='150px'>"+FormatUtil.getPhoneNo(addressCurrent.getPhone1())+"</td> ");
			
		if(!Util.empty(addressCurrent.getExt1())){ 
			HTML.append(" <td width='100px'>"+LabelUtil.getText(request, "TO") + "</td>")
				.append(" <td>"+FormatUtil.display(addressCurrent.getExt1()) + "</td>")
				.append(	HtmlUtil.hidden("EXT", addressCurrent.getExt1()));

		}else{
			HTML.append(" <td></td>")
				.append(" <td></td>");
		}
		HTML.append(	HtmlUtil.hidden("PHONE_NO", FormatUtil.getPhoneNo(addressCurrent.getPhone1())))
			.append(" </tr> ");
		return HTML.toString();
	}

	@Override
	public String renderElementFlag(HttpServletRequest request, Object objectElement) {
		String FLAG = MConstant.FLAG.NO;
		String DISPLAY = (String)getObjectRequest();
		if(VERIFY_CUSTOMER_DISPLAY.equals(DISPLAY)){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
			AddressDataM addressCurrent	= personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
			if(!Util.empty(addressCurrent) &&  !Util.empty(addressCurrent.getPhone1())){
				personalInfo.addVerCusPhoneNo(addressCurrent.getPhone1());
				FLAG = MConstant.FLAG.YES;
			}
		}
		return FLAG;
	}

}
