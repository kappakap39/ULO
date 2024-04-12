package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

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

public class CGNSelectBox implements AjaxDisplayGenerateInf{
	static Logger logger = Logger.getLogger(CGNSelectBox.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {			
		String product_feature = request.getParameter("product_feature");		
		logger.debug("product_feature >> "+product_feature);
		
		//get fieldID		
		ORIGCacheUtil origc = ORIGCacheUtil.getInstance();
		String fieldID = origc.getSystemIDFromListbox(product_feature,"35", "1");		
		logger.debug("fieldID >> "+fieldID);
		
		if(OrigUtil.isEmptyString(fieldID)){
			fieldID = "0";
		}
		
		PLOrigFormHandler origForm = (PLOrigFormHandler)request.getSession().getAttribute("PLORIGForm");
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		PLApplicationDataM applicationM = origForm.getAppForm();		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		
		PLXRulesVerificationResultDataM xVerM = personalM.getXrulesVerification();
		if(null == xVerM){
			xVerM = new PLXRulesVerificationResultDataM();
		}
		
		PLXRulesSpecialCustomerDataM specialM = xVerM.getXrulesSpecialCusM();
		if(null == specialM){
			specialM = new PLXRulesSpecialCustomerDataM();
		}
		
		JsonObjectUtil json = new JsonObjectUtil();
			
		String searchType = (String) request.getSession().getAttribute("searchType");
		
		ORIGFormUtil formUtil = new ORIGFormUtil();
	 	String displayMode = formUtil.getDisplayMode("SPECIAL_CUSTOMER_SUBFORM", userM.getRoles(), applicationM.getJobState(), origForm.getFormID(), searchType);
	 	
	 	String resultHtml = "";
		if(!OrigUtil.isEmptyString(fieldID)){
			resultHtml = HTMLRenderUtil.displaySelectTagScriptAction_ORIG(DataFormatUtility.StringToInt(fieldID), applicationM.getBusinessClassId(), specialM.getCustomerGroupName(), "customer_group_name", displayMode, "");
		}else{
			resultHtml = HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null, specialM.getCustomerGroupName(), "customer_group_name", displayMode, "");	
		}
		
		json.CreateJsonObject("div_customer_group_name", resultHtml);
		
		return json.returnJson();
	}

}
