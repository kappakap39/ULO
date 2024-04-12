package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;
//import org.apache.log4j.Logger;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;

public class GetTextboxWithDisplayMode implements AjaxDisplayGenerateInf {

	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		PLOrigFormHandler plOrigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		PLApplicationDataM plAppM = plOrigform.getAppForm();
//		String currentProjectCode = plAppM.getProjectCode();
		
//		Logger logger = Logger.getLogger(GetTextboxWithDisplayMode.class);
		plAppM.setProjectCode(null);
		
		String div_name = request.getParameter("div_name");
		String value = request.getParameter("value");
		String mode = request.getParameter("mode");
		String maxlength = request.getParameter("maxlength");
		String scriptAction = request.getParameter("scriptAction");
		String fieldName = request.getParameter("fieldName");
		String cssClass = "buttonNew";
		String textboxClass = request.getParameter("textboxClass");
//		String popUpAction = request.getParameter("popUpAction");
//		String cacheName = request.getParameter("cacheName");
		
				if(mode!=null){
					JsonObjectUtil jsonResult = new JsonObjectUtil();
					String resultHtml = HTMLRenderUtil.displayPopUpTagNotSetScriptAction(value,mode,maxlength,fieldName,textboxClass," onblur="+scriptAction+" ",maxlength,"...",cssClass)+"&nbsp; <img align=\"bottom\" src=\"images/reset.png\"  onmouseover=\"style.cursor='pointer'\" onclick=\"javascript:resetProjectCode()\" id=\"changeIcon\"/>";
					
					jsonResult.CreateJsonObject(div_name, resultHtml);
					return jsonResult.returnJson();
				}	
		return "";
	}

}
