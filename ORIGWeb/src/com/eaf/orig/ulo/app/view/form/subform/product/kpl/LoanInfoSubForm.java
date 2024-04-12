package com.eaf.orig.ulo.app.view.form.subform.product.kpl;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class LoanInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(LoanInfoSubForm.class);
	private String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("LoanInfoSubForm >>>>>>>>> ");
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM) appForm);
	
		String	amountLoan = request.getParameter("AMOUNT_LOAN");
		String	term = request.getParameter("TERM");
		
		ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		
		logger.debug("amountLoan :"+amountLoan);
		logger.debug("term :"+term);	

		if(Util.empty(application)){
			application  = new ApplicationDataM();
			application.setBusinessClassId(PRODUCT_K_PERSONAL_LOAN);
			applicationGroup.addApplications(application);
		}
				ArrayList<LoanDataM> loans=	application.getLoans();
				if(Util.empty(loans)){
					LoanDataM	loan =new LoanDataM();
					application.addLoan(loan);
				}
				if(!Util.empty(loans)){
					for(LoanDataM loan:loans){
						loan.setRequestLoanAmt(FormatUtil.toBigDecimal(amountLoan,true));
						loan.setRequestTerm(FormatUtil.toBigDecimal(term,true));
						//loan.setLoanAmt(FormatUtil.toBigDecimal(amountLoan,true));
						//loan.setTerm(FormatUtil.toBigDecimal(term,true));
					}
				}
			}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "KPL_LOAN_INFO_SUBFORM";
		String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
		logger.debug("subformId >> "+subformId);		
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ApplicationDataM applicationItem = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
		if(null == applicationItem){
			applicationItem = new ApplicationDataM();
		}
		
		ArrayList<LoanDataM> loans=	applicationItem.getLoans();
		if(!Util.empty(loans)){
			for(LoanDataM loan:loans){
				if(!Util.empty(loan)){
					//formError.mandatory(subformId,"AMOUNT_LOAN","AMOUNT_LOAN_"+PRODUCT_K_PERSONAL_LOAN,loan.getRequestLoanAmt(),request);
					formError.mandatory(subformId,"TERM","TERM_"+PRODUCT_K_PERSONAL_LOAN,loan.getRequestTerm(),request);
				}
			}
		}

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		String[] filedNames ={"AMOUNT_LOAN","TERM"};
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);	
			ArrayList<ApplicationDataM> applicationItems = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_PERSONAL_LOAN);
			if(!Util.empty(applicationItems)){
				for(ApplicationDataM applicationDataM : applicationItems){
					PersonalInfoDataM personalInfoM = applicationGroup.getPersonalInfoByRelation(applicationDataM.getApplicationRecordId());
					 for(String elementId:filedNames){
						 FieldElement fieldElement = new FieldElement();
						 fieldElement.setElementId(elementId);
						 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
						 fieldElement.setElementGroupId(personalInfoM.getPersonalId());
						 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfoM));
						 fieldElements.add(fieldElement);
					 }
				}
			}

		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM lastApplicationGroup = ((ApplicationGroupDataM)lastObjectForm);
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();		
		logger.debug("subformId >> "+subformId);
		
		LoanDataM loan = KPLUtil.getKPLLoanDataM(applicationGroup);
		LoanDataM lastLoan = KPLUtil.getKPLLoanDataM(lastApplicationGroup);
		
		auditFormUtil.auditForm(subformId,"AMOUNT_LOAN",loan,lastLoan,request);
		auditFormUtil.auditForm(subformId,"TERM",loan,lastLoan,request);
		
		return auditFormUtil.getAuditForm();
	}	

}
