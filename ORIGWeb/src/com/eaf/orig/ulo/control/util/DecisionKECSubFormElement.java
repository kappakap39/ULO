package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.subform.product.manual.CardInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class DecisionKECSubFormElement extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(DecisionKECSubFormElement.class);
	String SUB_FORM_ID ="DECISION_SUBFORM";
	String REC_RESULT_REFER = SystemConstant.getConstant("FINAL_RESULT_REFER");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String subFormId = (String)objectElement;
		return "/orig/ulo/subform/DecisionKECSubform.jsp?subFormId="+subFormId;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		
		ArrayList<ApplicationDataM> kecApplications=  (ArrayList<ApplicationDataM>)objectElement;
		if(!Util.empty(kecApplications)){
			for(ApplicationDataM  kecApplication:kecApplications){
				LoanDataM loan = kecApplication.getLoan();
				if(Util.empty(loan)){
					loan = new  LoanDataM();
				}
				if(REC_RESULT_REFER.equals(kecApplication.getRecommendDecision()) ||kecApplication.getLifeCycle() >1 ){
					String appRecordId =kecApplication.getApplicationRecordId();
					String KEC_FINAL_RESULT = request.getParameter("KEC_FINAL_RESULT_"+appRecordId);
					String KEC_FINAL_CREDIT_LIMIT =request.getParameter("KEC_FINAL_CREDIT_LIMIT_"+appRecordId);
					logger.debug("KEC_FINAL_RESULT>>"+KEC_FINAL_RESULT);
					logger.debug("KEC_FINAL_CREDIT_LIMIT>>"+KEC_FINAL_CREDIT_LIMIT);
					kecApplication.setFinalAppDecision(KEC_FINAL_RESULT);
					loan.setLoanAmt(FormatUtil.toBigDecimal(KEC_FINAL_CREDIT_LIMIT));
				}
				
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		FormErrorUtil formError = new FormErrorUtil();
		try {
			ArrayList<ApplicationDataM> kecApplications=  (ArrayList<ApplicationDataM>)objectElement;
			if(!Util.empty(kecApplications)){
				for(ApplicationDataM  kecApplication:kecApplications){
					LoanDataM loan = kecApplication.getLoan();
					if(Util.empty(loan)){
						loan = new  LoanDataM();
					}
					if(REC_RESULT_REFER.equals(kecApplication.getRecommendDecision()) ||kecApplication.getLifeCycle() >1){
						String appRecordId =kecApplication.getApplicationRecordId();
						String FINAL_RESULT_FIELD = "KEC_FINAL_RESULT_"+appRecordId;
						String FINAL_CREDIT_LIMIT_FIELD ="KEC_FINAL_CREDIT_LIMIT_"+appRecordId;						 
						 formError.mandatory(SUB_FORM_ID,"KEC_FINAL_DECISION", FINAL_RESULT_FIELD,kecApplication.getFinalAppDecision(), request);	
						 if(Util.empty(loan.getLoanAmt())){
							 formError.mandatory(SUB_FORM_ID,"KEC_FINAL_CREDIT", FINAL_CREDIT_LIMIT_FIELD,loan.getLoanAmt(), request);
							}else if(CardInfoUtil.valdiateCreditLimit(loan.getLoanAmt())){
								formError.error(FINAL_CREDIT_LIMIT_FIELD,MessageErrorUtil.getText("ERROR_CREDIT_LIMIT_MODE"));
							}
						 
						
					}
					
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return formError.getFormError();
	}
}
