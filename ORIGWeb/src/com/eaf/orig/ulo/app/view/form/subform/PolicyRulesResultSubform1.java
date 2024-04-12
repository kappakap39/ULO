package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDataM;
import com.eaf.orig.ulo.model.app.ORPolicyRulesDetailDataM;
import com.eaf.orig.ulo.model.app.PolicyRulesDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class PolicyRulesResultSubform1 extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(PolicyRulesResultSubform1.class);
	String GUIDE_LINE_VER_RESULT_BLANK = SystemConstant.getConstant("GUIDE_LINE_VER_RESULT_BLANK");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);		
		String VETO_REMARK = request.getParameter("VETO_REMARK");
		logger.debug("VETO_REMARK >> "+VETO_REMARK);
		applicationGroup.setVetoRemark(VETO_REMARK);				
		ArrayList<ApplicationDataM> applicationList = applicationGroup.getApplications();
		int maxLifeCycle = applicationGroup.getMaxLifeCycleFromApplication();
		logger.debug("maxLifeCycle >> "+maxLifeCycle);
		if(!Util.empty(applicationList)){
			for(ApplicationDataM appData :applicationList){	
				if(appData.getLifeCycle()==maxLifeCycle){
					VerificationResultDataM  verificationResult =  appData.getVerificationResult();
					if(Util.empty(verificationResult)){
						verificationResult = new VerificationResultDataM();
					}
					ArrayList<PolicyRulesDataM> policyRuleList = verificationResult.getPolicyRules();
					if(Util.empty(policyRuleList)){
						policyRuleList = new ArrayList<PolicyRulesDataM>();
					}
					if(!Util.empty(policyRuleList)){							
						for(PolicyRulesDataM policyRule :policyRuleList) {
							String policyFieldElementName ="POLICY_RESULT_"+maxLifeCycle+"_"+policyRule.getPolicyRulesId();
							String policyResult = request.getParameter(policyFieldElementName);
							logger.debug("policyFieldElementName >> "+policyFieldElementName);
							logger.debug("policyResult >> "+policyResult);
							if(request.getParameterMap().containsKey(policyFieldElementName)){
								policyRule.setResult(policyResult);
							}  
							ArrayList<ORPolicyRulesDataM> orPolicyRuleList = policyRule.getOrPolicyRules();
							if(!Util.empty(orPolicyRuleList)){
								for(ORPolicyRulesDataM orPolicyRules :orPolicyRuleList){									
									String orPilicyResultElementName = "OR_POLICY_"+maxLifeCycle+"_"+orPolicyRules.getOrPolicyRulesId();
									String orPolicyResult = request.getParameter(orPilicyResultElementName);	
									if(request.getParameterMap().containsKey(orPilicyResultElementName)){
										 orPolicyRules.setResult(orPolicyResult);
									 }
									ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDetailList = orPolicyRules.getOrPolicyRulesDetails();
									if(!Util.empty(orPolicyRulesDetailList)){
										for(ORPolicyRulesDetailDataM orPolicyRuleDetail : orPolicyRulesDetailList){
											String guideLineResultElementName = "GUIDE_LINE_RESULT_"+maxLifeCycle+"_"+orPolicyRuleDetail.getOrPolicyRulesDetailId();
											String guideLineResult = request.getParameter(guideLineResultElementName);											
											if(request.getParameterMap().containsKey(guideLineResultElementName)){
												orPolicyRuleDetail.setResult(guideLineResult);
											}
										}
									}
								}
								
							}
						}
					}	
				}
			}		
		}
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId ="POLICY_RULES_RESULT_SUBFORM_1";
		FormErrorUtil formError = new FormErrorUtil();		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);				
		ArrayList<ApplicationDataM> applicationList = applicationGroup.getApplications();
		int maxLifeCycle = applicationGroup.getMaxLifeCycleFromApplication();
		if(!Util.empty(applicationList)){
			for(ApplicationDataM appData :applicationList){	
				if(appData.getLifeCycle()==maxLifeCycle){
					VerificationResultDataM  verificationResult =  appData.getVerificationResult();
					if(Util.empty(verificationResult)){
						verificationResult = new VerificationResultDataM();
					}
					ArrayList<PolicyRulesDataM> policyRuleList = verificationResult.getPolicyRules();
					if(Util.empty(policyRuleList)){
						policyRuleList = new ArrayList<PolicyRulesDataM>();
					}
					if(!Util.empty(policyRuleList)){							
						for(PolicyRulesDataM policyRule :policyRuleList) {
							String policyElementName ="POLICY_RESULT_"+maxLifeCycle+"_"+policyRule.getPolicyRulesId();
							logger.debug(">>>>policyElementName>>"+policyElementName);
							if(request.getParameterMap().containsKey(policyElementName) && Util.empty(policyRule.getVerifiedResult())){
								formError.mandatory(subformId, "POLICY_RESULT", policyElementName, policyRule.getResult(), request);								
							}
							ArrayList<ORPolicyRulesDataM> orPolicyRuleList = policyRule.getOrPolicyRules();
							if(!Util.empty(orPolicyRuleList)){
								for(ORPolicyRulesDataM orPolicyRule :orPolicyRuleList){	
									String orPolicyResultElementName = "OR_POLICY_"+maxLifeCycle+"_"+orPolicyRule.getOrPolicyRulesId();
									logger.debug("orPolicyResultElementName >> "+orPolicyResultElementName);
									if(request.getParameterMap().containsKey(orPolicyResultElementName)){
										formError.mandatory(subformId,"OR_POLICY",orPolicyResultElementName,"",orPolicyRule.getResult(), request);
									}
									ArrayList<ORPolicyRulesDetailDataM> orPolicyRulesDetailList = orPolicyRule.getOrPolicyRulesDetails();
									if(!Util.empty(orPolicyRulesDetailList)){
										for(ORPolicyRulesDetailDataM orPolicyRuleDetail : orPolicyRulesDetailList){
											String guideLineResultElementName = "GUIDE_LINE_RESULT_"+maxLifeCycle+"_"+orPolicyRuleDetail.getOrPolicyRulesDetailId();
											logger.debug("guideLineResultElementName >> "+guideLineResultElementName);
											if(request.getParameterMap().containsKey(guideLineResultElementName) &&  
												(GUIDE_LINE_VER_RESULT_BLANK.equals(orPolicyRuleDetail.getVerifiedResult()) 
														|| Util.empty(orPolicyRuleDetail.getVerifiedResult()))){
												formError.mandatory(subformId, "GUIDE_LINE_RESULT", guideLineResultElementName,"",orPolicyRuleDetail.getResult(), request);
											}
										}
									}
								}								
							}
						}
					}	
				}
			}		
		}
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
}
