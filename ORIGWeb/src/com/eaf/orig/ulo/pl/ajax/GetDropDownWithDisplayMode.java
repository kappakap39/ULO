package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesSpecialCustomerDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class GetDropDownWithDisplayMode implements AjaxDisplayGenerateInf {

	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		PLOrigFormHandler plOrigform = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		PLApplicationDataM plAppM = plOrigform.getAppForm();
		PLPersonalInfoDataM perM = plAppM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		if(OrigUtil.isEmptyObject(perM)){perM = new PLPersonalInfoDataM();}
		
		PLXRulesVerificationResultDataM xVerM = perM.getXrulesVerification();
		if(OrigUtil.isEmptyObject(xVerM)){xVerM = new PLXRulesVerificationResultDataM();}
		
		PLXRulesSpecialCustomerDataM specialM = xVerM.getXrulesSpecialCusM();
		if(OrigUtil.isEmptyObject(specialM)){specialM = new PLXRulesSpecialCustomerDataM();}
		
		String div_name = request.getParameter("div_name");
		String FieldID = request.getParameter("fieldID"); /*** FieldID for Get Listbox Vector*/
		String DisplayMode = request.getParameter("displayMode"); /*** Dropdown Display mode*/
		String fieldName = request.getParameter("fieldName"); /*** Dropdown Name*/
		JsonObjectUtil jsonResult = new JsonObjectUtil();
		
		ORIGFormUtil formUtil = new ORIGFormUtil();
		String searchType = (String) request.getSession().getAttribute("searchType");
	 	DisplayMode = formUtil.getDisplayMode("SPECIAL_CUSTOMER_SUBFORM", userM.getRoles(), plAppM.getJobState(), plOrigform.getFormID(), searchType);
		
		ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
		if(!OrigUtil.isEmptyString(FieldID)){
			Vector vFieldID = (Vector)origc.getNaosCacheDataMs(plAppM.getBusinessClassId(), DataFormatUtility.StringToInt(FieldID));
			
			if(!OrigUtil.isEmptyVector(vFieldID)){
				String resultHtml = HTMLRenderUtil.displaySelectTagScriptAction_ORIG(vFieldID, specialM.getCustomerGroupName(), fieldName, DisplayMode, "");
				jsonResult.CreateJsonObject(div_name, resultHtml);
				return jsonResult.returnJson();
			}else{
				String resultHtml = HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null, specialM.getCustomerGroupName(), fieldName, DisplayMode, "");
				jsonResult.CreateJsonObject(div_name, resultHtml);
			}
		}
		return jsonResult.returnJson();
	}

}
