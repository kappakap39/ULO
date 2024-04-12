package com.eaf.orig.ulo.app.view.form.subform.increase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

@SuppressWarnings("serial")
public class CashTransferSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CashTransferSubForm.class);
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		DIHProxy dihService = new DIHProxy();
		ArrayList<ApplicationDataM> applicationItems = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
		String CALL_FOR_CASH_FLAG = request.getParameter("CASH_1_HOUR_"+SUFFIX_CALL_FOR_CASH);		
		String flagDisplay = CALL_FOR_CASH_FLAG;
		if(Util.empty(CALL_FOR_CASH_FLAG)){
			flagDisplay = MConstant.FLAG.NO;
		}		
		String CALL_FOR_CASH_ACCOUNT_NO = request.getParameter("ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH);
		CALL_FOR_CASH_ACCOUNT_NO = FormatUtil.removeDash(CALL_FOR_CASH_ACCOUNT_NO);
		String COMPLETE_DATA_CALL_FOR_CASH = request.getParameter("COMPLETE_DATA_"+SUFFIX_CALL_FOR_CASH);
		DIHQueryResult<String> queryResult = dihService.getAccountName(CALL_FOR_CASH_ACCOUNT_NO);
		String CALL_FOR_CASH_ACCOUNT_NAME = queryResult.getResult();
		if(Util.empty(COMPLETE_DATA_CALL_FOR_CASH)) {
			COMPLETE_DATA_CALL_FOR_CASH = INFO_IS_CORRECT;
		}
		logger.debug("CALL_FOR_CASH_FLAG >> "+CALL_FOR_CASH_FLAG);
		logger.debug("CALL_FOR_CASH_ACCOUNT_NO >> "+CALL_FOR_CASH_ACCOUNT_NO);
		logger.debug("CALL_FOR_CASH_ACCOUNT_NAME >> "+CALL_FOR_CASH_ACCOUNT_NAME);
		try{
			if(!Util.empty(applicationItems)){
				for (ApplicationDataM applicationItem : applicationItems) {
					ArrayList<LoanDataM> loans = applicationItem.getLoans();
					for(LoanDataM loan : loans){
						ArrayList<CashTransferDataM> cashTransfers = loan.getCashTransfers();
						if(null == cashTransfers){
							cashTransfers = new ArrayList<CashTransferDataM>();
							loan.setCashTransfers(cashTransfers);					
						}
						if(MConstant.FLAG.YES.equals(CALL_FOR_CASH_FLAG)){
							CashTransferDataM callForCash = loan.getCashTransfer(CALL_FOR_CASH);
							if(null == callForCash){
								callForCash = new CashTransferDataM();
								callForCash.setCashTransferType(CALL_FOR_CASH);
								loan.addCashTransfer(callForCash);
							}
							callForCash.setCallForCashFlag(flagDisplay);
							callForCash.setTransferAccount(FormatUtil.removeDash(CALL_FOR_CASH_ACCOUNT_NO));
							callForCash.setAccountName(Util.empty(CALL_FOR_CASH_ACCOUNT_NAME)?"":CALL_FOR_CASH_ACCOUNT_NAME);
							callForCash.setCompleteData(COMPLETE_DATA_CALL_FOR_CASH);
						}else{
							loan.removeCashTransfer(CALL_FOR_CASH);
						}
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {	
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "INCREASE_CASH_TRANSFER_SUBFORM";
		logger.debug("subformId >> "+subformId);
		ArrayList<ApplicationDataM> applicationItems = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		if(!Util.empty(applicationItems)) {
			ApplicationDataM applicationM = applicationItems.get(0);
			CashTransferDataM cashTransFer = applicationM.getCashTransfer(CALL_FOR_CASH);
			formError.mandatory(subformId,"CASH_1_HOUR",cashTransFer,request);
			if(!Util.empty(cashTransFer)) {
				formError.mandatory(subformId,"ACCOUNT_NO_KEC_04", cashTransFer.getTransferAccount(),request);
				formError.mandatory(subformId,"ACCOUNT_NAME", cashTransFer.getAccountName(),request);
			}
		}

		return formError.getFormError();
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request) {		
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request, Object objectForm){
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		String templateId = applicationGroup.getApplicationTemplate();
		logger.debug("templateId >> "+templateId);		
		RenderSubform render = new RenderSubform();
		String[] INCREASE_PRODUCT_CODE_KEC_BUNDING = SystemConstant.getArrayConstant("INCREASE_PRODUCT_CODE_KEC_BUNDING");
		ArrayList<String> conditions = new ArrayList<String>(Arrays.asList(INCREASE_PRODUCT_CODE_KEC_BUNDING));
		String checkProductType = render.determineProductType(templateId);
		logger.debug("checkProductType >> "+checkProductType);		
		logger.debug("conditions >> "+conditions);
		if(conditions.contains(checkProductType) && !isEmptyKecProduct(applicationGroup)){
			return MConstant.FLAG_Y;
		}
		return MConstant.FLAG_N;
	}
	
	private boolean isEmptyKecProduct(ApplicationGroupDataM applicationGroup){
		boolean isEmptyKecProduct = true;
		if(!Util.empty(applicationGroup.getApplicationsProduct(PRODUCT_K_EXPRESS_CASH))){
			isEmptyKecProduct = false;
		}
		return isEmptyKecProduct;
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("CashTransferSubForm.auditForm");
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		if(Util.empty(applicationItem)){
			applicationItem = new ApplicationDataM();
		}
		
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
		ApplicationDataM lastApplicationItem = lastApplicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		if(Util.empty(lastApplicationItem)){
			lastApplicationItem = new ApplicationDataM();
		}
		
//		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);
		if(null == cashTransfer){
			cashTransfer = new CashTransferDataM();
		}
		
		CashTransferDataM lastCashTransfer = lastApplicationItem.getCashTransfer(cashTransFerType);
		if(null == lastCashTransfer){
			lastCashTransfer = new CashTransferDataM();
		}
		
		CashTransferDataM callForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
		if(null == callForCash){
			callForCash = new CashTransferDataM();
		}
		
		CashTransferDataM lastCallForCash = lastApplicationItem.getCashTransfer(CALL_FOR_CASH);
		if(null == lastCallForCash){
			lastCallForCash = new CashTransferDataM();
		}
		
		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subFormId = getSubFormID();
		
		auditFormUtil.setObjectValue(PERSONAL_TYPE_APPLICANT);
		auditFormUtil.auditForm(subFormId, "CASH_1_HOUR", callForCash, lastCallForCash, request);
		auditFormUtil.auditForm(subFormId, "ACCOUNT_NO_KEC_04", callForCash, lastCallForCash, request);
		return auditFormUtil.getAuditForm();
	}
	
}
