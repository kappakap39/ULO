package com.eaf.orig.ulo.app.view.form.website;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;

public class JudiciaryWebsiteInfo extends ElementHelper implements ElementInf{
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		WebVerificationDataM webM = (WebVerificationDataM)objectElement;
		String FIELD_ID_WEBSITE = SystemConstant.getConstant("FIELD_ID_WEBSITE");
		String webUrl = CacheControl.getName(SystemConstant.getConstant("WEBSITE_CACHE"), webM.getWebCode(), "WEBSITE_URL");
		String websiteName = CacheControl.getName(SystemConstant.getConstant("WEBSITE_CACHE"), webM.getWebCode());
		String displayMode = (String)getObjectRequest();
		String subformId = "VERIFY_WEBSITE_SUBFORM";
		
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>");
		sb.append("<td colspan='4'>"+FormatUtil.display(websiteName)+" : "
				+HtmlUtil.hyperlink("WEBSITE_URL", webUrl, webUrl, HtmlUtil.TARGET, displayMode, "", request)+"</td>");
		sb.append("</tr>");
		sb.append("<tr>");
		sb.append("<td>"+HtmlUtil.getSubFormLabel(request,subformId,"VERIFICATION_RESULT", "VERIFICATION_RESULT")+"</td>");
		sb.append("<td>"+HtmlUtil.dropdown("VERIFICATION_RESULT",webM.getWebCode(),"WEBSITE_LIST_FILTER",webM.getVerResult(),FIELD_ID_WEBSITE,webM.getWebCode(),displayMode,"",request)+"</td>");
		sb.append("<td>"+HtmlUtil.getSubFormLabel(request, "REMARK")+"</td>");
		sb.append("<td>"+HtmlUtil.textBox("REMARK",webM.getWebCode(),webM.getRemark(),"","100",displayMode,"",request)+"</td>");
		sb.append("</tr>");
		return sb.toString();
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		WebVerificationDataM webM = (WebVerificationDataM)objectElement;
		webM.setVerResult(request.getParameter("VERIFICATION_RESULT"+"_"+webM.getWebCode()));
		webM.setRemark(request.getParameter("REMARK"+"_"+webM.getWebCode()));
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		WebVerificationDataM webM = (WebVerificationDataM)objectElement;
		
		if(Util.empty(webM.getVerResult())) {
			formError.error("VERIFICATION_RESULT_"+webM.getWebCode(),PREFIX_ERROR+LabelUtil.getText(request,"VERIFICATION_RESULT"));
		}
		return formError.getFormError();
	}
}
