package com.eaf.orig.ulo.app.view.form.website;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

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
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WebVerificationDataM;

public class RevenueDeptWebsiteInfo extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(RevenueDeptWebsiteInfo.class);
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
		String SSO_WEBSITE_CODE = SystemConstant.getConstant("SSO_WEBSITE_CODE");
		String WEBSITE_UNAVAILABLE = SystemConstant.getConstant("WEBSITE_UNAVAILABLE");
		String NHSO_WEBSITE_CODE = SystemConstant.getConstant("NHSO_WEBSITE_CODE");
		String WEBSITE_SSO = SystemConstant.getConstant("WEBSITE_SSO");
		
		EntityFormHandler tabForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		VerificationResultDataM verificationResultM = (VerificationResultDataM)tabForm.getObjectForm();
		WebVerificationDataM webM = (WebVerificationDataM)objectElement;
		
		ArrayList<WebVerificationDataM> webList = verificationResultM.getWebVerifications();
		if(!Util.empty(webList)) {
			for(WebVerificationDataM mainWebM: webList) {
				if(NHSO_WEBSITE_CODE.equals(mainWebM.getWebCode())) {
					logger.info("SSO ver result: "+mainWebM.getVerResult());
					if(!WEBSITE_SSO.equals(mainWebM.getVerResult())) {
						logger.info("RD ver result: "+webM.getVerResult());
						if(Util.empty(webM.getVerResult())) {
							formError.error("VERIFICATION_RESULT_"+webM.getWebCode(),PREFIX_ERROR+LabelUtil.getText(request,"VERIFICATION_RESULT"));
						}
					}
					break;
				}
			}
		}
		return formError.getFormError();
	}
}
