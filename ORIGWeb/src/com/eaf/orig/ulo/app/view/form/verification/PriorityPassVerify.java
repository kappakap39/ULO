package com.eaf.orig.ulo.app.view.form.verification;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.app.WisdomDataM;


public class PriorityPassVerify extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(PriorityPassVerify.class);
	private String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String REQ_PRIORITY_PASS_SUP_DEFAULT =SystemConstant.getConstant("REQ_PRIORITY_PASS_SUP_DEFAULT");
	String PERSONAL_TYPE_SUPPLEMENTARY =SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {		
		return "/orig/ulo/subform/verification/PriorityPassVerifySubForm.jsp";
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		String REQ_PRIORITY_PASS_SUP = request.getParameter("REQ_PRIORITY_PASS_SUP");
		String PRIORITY_PASS_FLAG = request.getParameter("PRIORITY_PASS_FLAG");
		String PRIORITY_PASS_MAIN = request.getParameter("PRIORITY_PASS_MAIN");
		String NO_PRIORITY_PASS_SUP = request.getParameter("NO_PRIORITY_PASS_SUP");
		logger.debug("REQ_PRIORITY_PASS_SUP >> "+REQ_PRIORITY_PASS_SUP);
		logger.debug("PRIORITY_PASS_FLAG >> "+PRIORITY_PASS_FLAG);
		logger.debug("PRIORITY_PASS_MAIN >> "+PRIORITY_PASS_MAIN);
		logger.debug("NO_PRIORITY_PASS_SUP >> "+NO_PRIORITY_PASS_SUP);		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_CRADIT_CARD);
		if(!Util.empty(applications)){
			for(ApplicationDataM application:applications){
				LoanDataM loan = application.getLoan();
				CardDataM card = loan.getCard();
				if(null==card){
					card = new CardDataM();
					loan.setCard(card);
				}
				WisdomDataM wisdom = card.getWisdom();
				if(null==wisdom){
					wisdom = new WisdomDataM();
					card.setWisdom(wisdom);
				}
				if(Util.empty(wisdom.getReqPriorityPassSup())){
					wisdom.setReqPriorityPassSup(REQ_PRIORITY_PASS_SUP_DEFAULT);
				}
				wisdom.setPriorityPassMemo(PRIORITY_PASS_FLAG);
				wisdom.setReqPriorityPassSup(REQ_PRIORITY_PASS_SUP);
				wisdom.setPriorityPassMain(PRIORITY_PASS_MAIN);
				wisdom.setNoPriorityPassSup(FormatUtil.toBigDecimal(NO_PRIORITY_PASS_SUP));
				PersonalInfoDataM personalInfoSupplement = applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY);
				if(!Util.empty(personalInfoSupplement) && !Util.empty(personalInfoSupplement.getVerificationResult())){
					VerificationResultDataM verificationResult = personalInfoSupplement.getVerificationResult();
					wisdom.setPriorityPassFlag(verificationResult.getRequirePriorityPass());
				}
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String,Object> validateElement(HttpServletRequest request, Object objectElement) {	
		logger.debug("validateElement >>> PriorityPassVerify");
		FormErrorUtil formError = new FormErrorUtil();
		String subformId ="VERIFICATION_VALIDATION_SUBFROM";
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();		
		WisdomDataM wisdomCard = applicationGroup.findWisdom(PRODUCT_CRADIT_CARD);
		if(null==wisdomCard){
			wisdomCard = new WisdomDataM();
		}
		formError.mandatory(subformId, "REQ_PRIORITY_PASS_SUP", wisdomCard, request);
		return formError.getFormError();
		
	}
	
	@Override
	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		ArrayList<PersonalInfoDataM>    personalInfos =  applicationGroup.getPersonalInfos();
		if(!Util.empty(personalInfos)){
			for(PersonalInfoDataM personalInfo : personalInfos){
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if(!Util.empty(verificationResult)) {
					String requiredFlag = verificationResult.getRequirePriorityPass();
					if(!Util.empty(requiredFlag) && MConstant.FLAG.YES.equals(requiredFlag)) {
						return MConstant.FLAG.YES;
					}
				}
			}
		}
		return MConstant.FLAG.NO;
	}
}