package com.eaf.orig.ulo.app.view.form.subform.verifycustomer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.form.verification.VerifyUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

@SuppressWarnings("serial")
public class DiffReqSubform extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(DiffReqSubform.class);
	private String subformId = "DIFF_REQ_SUBFORM";
	private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");	
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ApplicationDataM applicationM = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		
		String AMOUNT_LOAN = request.getParameter("AMOUNT_LOAN_"+PRODUCT_K_PERSONAL_LOAN);
		String TERM = request.getParameter("TERM_"+PRODUCT_K_PERSONAL_LOAN);
		String DIFF_REQ_CONTACT_RESULT = request.getParameter("DIFF_REQ_CONTACT_RESULT_"+PRODUCT_K_PERSONAL_LOAN);
		
		logger.debug("AMOUNT_LOAN : " + AMOUNT_LOAN);
		logger.debug("TERM : " + TERM);
		logger.debug("DIFF_REQ_CONTACT_RESULT : " + DIFF_REQ_CONTACT_RESULT);
		
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if(!Util.empty(applications)){
			for (ApplicationDataM application : applications) 
			{
				ArrayList<LoanDataM> loans = application.getLoans();
				if(!Util.empty(loans)){
					for(LoanDataM loan : loans)
					{
						loan.setLoanAmt(FormatUtil.toBigDecimal(AMOUNT_LOAN,true));
						loan.setTerm(FormatUtil.toBigDecimal(TERM,true));
					}
				}
			}
		}
		
		applicationM.setDiffRequestResult(DIFF_REQ_CONTACT_RESULT);
	}

	@Override
	public HashMap<String, Object> validateForm(HttpServletRequest request, Object appForm) 
	{
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("subformId >> "+subformId);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ApplicationDataM applicationItem = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if(null == applicationItem){
			applicationItem = new ApplicationDataM();
		}
		String calcu = request.getParameter("calcu");
		logger.debug("calcu = " + calcu);
		if(!MConstant.FLAG_Y.equals(calcu))
		{
			logger.debug("Mandatory field check >> DIFF_REQ_CONTACT_RESULT = " + applicationItem.getDiffRequestResult());
			formError.mandatory(subformId,"DIFF_REQ_CONTACT_RESULT","DIFF_REQ_CONTACT_RESULT_"+PRODUCT_K_PERSONAL_LOAN,applicationItem.getDiffRequestResult(),request);
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) 
	{
		String renderFlag = MConstant.FLAG_N;
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String source = applicationGroup.getSource();
		if(KPLUtil.isKPL(applicationGroup)||ApplicationUtil.eApp(source))
		{
			ApplicationDataM kplApp = KPLUtil.getKPLApplication(applicationGroup);
			String diffReqFlag = kplApp.getDiffRequestFlag();
			logger.debug("renderSubformFlag - diffReqFlag = " + diffReqFlag);
			if(MConstant.FLAG.YES.equals(diffReqFlag) || !Util.empty(kplApp.getDiffRequestResult()))
			{
				//Render only KPL and diffRequestFlag not Empty (set at DecisionDE1)
				// or diffRequestResult is not NULL (set at station DV - DIFF_REQUEST_SUBFORM)
				renderFlag = MConstant.FLAG_Y;
			}
		}	
		
		return renderFlag;
	}

}
