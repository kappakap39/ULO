package com.eaf.orig.shared.dao.utility;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.RulesDetailsDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesPolicyRulesDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class ORIGRuleDetailsUtil {
	Logger logger = Logger.getLogger(this.getClass().getName());
	static ORIGRuleDetailsUtil me;
	
	public static ORIGRuleDetailsUtil getInstance() {
		if (me == null) {
			me = new ORIGRuleDetailsUtil();
		}
		return me;
	}
	
	public Vector<RulesDetailsDataM> getRulesDetailsConfig(String busClass){
		try{
			ORIGDAOUtilLocal origDAOBean = PLORIGEJBService.getORIGDAOUtilLocal();
			return origDAOBean.getRulesDetailsConfig(busClass);
		}catch (Exception e){
			logger.fatal("##### getRulesDetailsConfig error:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	public String getRuleDetatisResult(PLApplicationDataM appM, String policyRuleID){
		String result = null;
		
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM verResult = personalM.getXrulesVerification();		
		if(null == verResult){
			verResult = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(verResult);
		}	 
	    PLXRulesPolicyRulesDataM policyRulesM = verResult.getPLPolicyRuleByPolicyID(policyRuleID);  
		if(null != policyRulesM){
			result = policyRulesM.getResultDesc();
		}		 
		return result;
	}
	
	public String getRuleDetatisResultCode(PLApplicationDataM applicationM, String policyRuleID){
		String result = null;
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(OrigConstant.PERSONAL_TYPE_APPLICANT);
		PLXRulesVerificationResultDataM verResultM = personalM.getXrulesVerification();
		if(null == verResultM){
			verResultM = new PLXRulesVerificationResultDataM();
		}
		PLXRulesPolicyRulesDataM policyRulesM = verResultM.getPLPolicyRuleByPolicyID(policyRuleID);  
		if(null != policyRulesM){
			result = policyRulesM.getResultCode();
		};		 
		return result;
	}
}
