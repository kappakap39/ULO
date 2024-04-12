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
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class DecisionKCCSubFormElement extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(DecisionKCCSubFormElement.class);	
	String SUB_FORM_ID ="DECISION_SUBFORM";
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	String FINAL_RESULT_REFER = SystemConstant.getConstant("FINAL_RESULT_REFER");
	String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
	String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String subFormId = (String)objectElement;
		return "/orig/ulo/subform/DecisionKCCSubform.jsp?subFormId="+subFormId;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
		String APPLICATION_TYPE = applicationGroup.getApplicationType();
		ArrayList<ApplicationDataM> kccApplications=  (ArrayList<ApplicationDataM>)objectElement;
		if(!Util.empty(kccApplications)){
			for(ApplicationDataM  kccApplication:kccApplications){
				LoanDataM loan = kccApplication.getLoan();
				CardDataM card = kccApplication.getCard();
				String applicationCardtype= "";
				if(Util.empty(loan)){
					loan = new  LoanDataM();					
				}
				if(!Util.empty(loan)){
					applicationCardtype=card.getApplicationType();				
				}
				String appRecordId =kccApplication.getApplicationRecordId();
				
				if(FINAL_RESULT_REFER.equals(kccApplication.getRecommendDecision()) || kccApplication.getLifeCycle()>1){
					if((APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)) || (APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE))){
						String KCC_FINAL_RESULT = request.getParameter("KCC_FINAL_RESULT_"+appRecordId);
						String KCC_FINAL_CREDIT_LIMIT =request.getParameter("KCC_FINAL_CREDIT_LIMIT_"+appRecordId);
						kccApplication.setFinalAppDecision(KCC_FINAL_RESULT);
						loan.setLoanAmt(FormatUtil.toBigDecimal(KCC_FINAL_CREDIT_LIMIT,true));
					}else{
						if(APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardtype)){						
							String KCC_FINAL_RESULT = request.getParameter("KCC_FINAL_RESULT_"+appRecordId);
							String KCC_FINAL_CREDIT_LIMIT =request.getParameter("KCC_FINAL_CREDIT_LIMIT_"+appRecordId);
							kccApplication.setFinalAppDecision(KCC_FINAL_RESULT);
							loan.setLoanAmt(FormatUtil.toBigDecimal(KCC_FINAL_CREDIT_LIMIT,true));
						}else if(APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(applicationCardtype)){
							
							String SUP_KCC_FINAL_RESULT = request.getParameter("SUP_KCC_FINAL_RESULT_"+appRecordId);
							String SUP_KCC_FINAL_CREDIT_LIMIT =request.getParameter("SUP_KCC_FINAL_CREDIT_LIMIT_"+appRecordId);
							 
							kccApplication.setFinalAppDecision(SUP_KCC_FINAL_RESULT);
							loan.setLoanAmt(FormatUtil.toBigDecimal(SUP_KCC_FINAL_CREDIT_LIMIT,true));
						}
					}
				}
	
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		FormErrorUtil formError = new FormErrorUtil();
		try {
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
			String APPLICATION_TYPE = applicationGroup.getApplicationType();
			ArrayList<ApplicationDataM> kccApplications=  (ArrayList<ApplicationDataM>)objectElement;
			if(!Util.empty(kccApplications)){
				for(ApplicationDataM  kccApplication:kccApplications){
					LoanDataM loan = kccApplication.getLoan();
					CardDataM card = kccApplication.getCard();
					String applicationCardtype= "";
					if(Util.empty(loan)){
						loan = new  LoanDataM();					
					}
					if(!Util.empty(loan)){
						applicationCardtype=card.getApplicationType();				
					}
					String appRecordId =kccApplication.getApplicationRecordId();
					
					if(FINAL_RESULT_REFER.equals(kccApplication.getRecommendDecision()) ||kccApplication.getLifeCycle() >1){
						if((APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)) || (APPLICATION_TYPE_INCREASE.equals(APPLICATION_TYPE))){
							String FINAL_RESULT_FIELD = "KCC_FINAL_RESULT_"+appRecordId;
							String FINAL_CREDIT_LIMIT_FIELD ="KCC_FINAL_CREDIT_LIMIT_"+appRecordId;
							 formError.mandatory(SUB_FORM_ID,"KCC_FINAL_DECISION", FINAL_RESULT_FIELD,"",kccApplication.getFinalAppDecision(), request);	
							 if(Util.empty(loan.getLoanAmt())){
								 formError.mandatory(SUB_FORM_ID,"KCC_FINAL_CREDIT", FINAL_CREDIT_LIMIT_FIELD,"",loan.getLoanAmt(), request);	
								}else if(CardInfoUtil.valdiateCreditLimit(loan.getLoanAmt())){
									formError.error(FINAL_CREDIT_LIMIT_FIELD,MessageErrorUtil.getText("ERROR_CREDIT_LIMIT_MODE"));
								}
						}else{
							if(APPLICATION_CARD_TYPE_BORROWER.equals(applicationCardtype)){						
								String FINAL_RESULT_FIELD = "KCC_FINAL_RESULT_"+appRecordId;
								String FINAL_CREDIT_LIMIT_FIELD ="KCC_FINAL_CREDIT_LIMIT_"+appRecordId;
								 formError.mandatory(SUB_FORM_ID,"KCC_FINAL_DECISION", FINAL_RESULT_FIELD,"",kccApplication.getFinalAppDecision(), request);	
								 if(Util.empty(loan.getLoanAmt())){
									 formError.mandatory(SUB_FORM_ID,"KCC_FINAL_CREDIT", FINAL_CREDIT_LIMIT_FIELD,"",loan.getLoanAmt(), request);
									}else if(CardInfoUtil.valdiateCreditLimit(loan.getLoanAmt())){
										formError.error(FINAL_CREDIT_LIMIT_FIELD,MessageErrorUtil.getText("ERROR_CREDIT_LIMIT_MODE"));
									}
							}else if(APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(applicationCardtype)){								
								String SUP_FINAL_RESULT_FIELD = request.getParameter("SUP_KCC_FINAL_RESULT_"+appRecordId);
								String SUP_FINAL_CREDIT_LIMIT_FIELD =request.getParameter("SUP_KCC_FINAL_CREDIT_LIMIT_"+appRecordId);						 
								 formError.mandatory(SUB_FORM_ID,"KCC_FINAL_DECISION", SUP_FINAL_RESULT_FIELD,"",kccApplication.getFinalAppDecision(), request);	
								if(Util.empty(loan.getLoanAmt())){
								 formError.mandatory(SUB_FORM_ID,"KCC_FINAL_CREDIT", SUP_FINAL_CREDIT_LIMIT_FIELD,"",loan.getLoanAmt(), request);
								}else if(CardInfoUtil.valdiateCreditLimit(loan.getLoanAmt())){
									formError.error(SUP_FINAL_CREDIT_LIMIT_FIELD,MessageErrorUtil.getText("ERROR_CREDIT_LIMIT_MODE"));
								}
							}
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
