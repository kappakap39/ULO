package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;

public class DecisionKPLSubFormElement extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(DecisionKPLSubFormElement.class);
	String SUB_FORM_ID ="DECISION_SUBFORM";
	String REC_RESULT_REFER = SystemConstant.getConstant("FINAL_RESULT_REFER");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String subFormId = (String)objectElement;
		return "/orig/ulo/subform/DecisionKPLSubform.jsp?subFormId="+subFormId;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ArrayList<ApplicationDataM> kplApplications=  (ArrayList<ApplicationDataM>)objectElement;
		if(!Util.empty(kplApplications)){
			for(ApplicationDataM  kplApplication:kplApplications){
				if(REC_RESULT_REFER.equals(kplApplication.getRecommendDecision()) || kplApplication.getLifeCycle()>1){
					String appRecordId =kplApplication.getApplicationRecordId();
					String KPL_FINAL_RESULT = request.getParameter("KPL_FINAL_RESULT_"+appRecordId);
					kplApplication.setFinalAppDecision(KPL_FINAL_RESULT);
				}
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
			
		FormErrorUtil formError = new FormErrorUtil();
		try {
			ArrayList<ApplicationDataM> kplApplications=  (ArrayList<ApplicationDataM>)objectElement;
			if(!Util.empty(kplApplications)){
				for(ApplicationDataM  kplApplication:kplApplications){
					if(REC_RESULT_REFER.equals(kplApplication.getRecommendDecision()) || kplApplication.getLifeCycle()>1){
						String appRecordId =kplApplication.getApplicationRecordId();
						String FINAL_RESULT_FIELD = "KPL_FINAL_RESULT_"+appRecordId;
						formError.mandatory(SUB_FORM_ID,"KPL_FINAL_DECISION", FINAL_RESULT_FIELD,kplApplication.getFinalAppDecision(), request);
					}
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
