
package com.eaf.orig.ulo.app.view.form.subform.product.kec;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormDisplayValueUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
//import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.dih.AccountInformationDataM;

@SuppressWarnings("serial")
public class CashDay1SubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CashDay1SubForm.class);
	String subformId = "KEC_CASH_DAY1_SUBFORM";
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
	String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
	String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
	String CALL_FOR_CASH = SystemConstant.getConstant("CALL_FOR_CASH");
	String INFO_IS_CORRECT = SystemConstant.getConstant("INFO_IS_CORRECT");
	String SAVING_TRANFER_ACC_TYPE = SystemConstant.getConstant("SAVING_ACCOUNT_TYPE");
	String CURRENT_TRANFER_ACC_TYPE = SystemConstant.getConstant("CURRENT_ACCOUNT_TYPE");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ArrayList<ApplicationDataM> applicationItems = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
		DIHProxy dihService = new DIHProxy();
		
//		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};	
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
		String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
	
		String CASH_TRANSFER_TYPE = request.getParameter("CASH_TRANSFER_TYPE_"+SUFFIX_CASH_TRANSFER);
		String PERCENT_TRANSFER = request.getParameter("PERCENT_TRANSFER_"+SUFFIX_CASH_TRANSFER);
		String ACCOUNT_NO = request.getParameter("ACCOUNT_NO_"+SUFFIX_CASH_TRANSFER);
		ACCOUNT_NO = FormatUtil.removeDash(ACCOUNT_NO);
		String COMPLETE_DATA_CASH_TRANSFER = request.getParameter("COMPLETE_DATA_"+SUFFIX_CASH_TRANSFER);
		
		String CALL_FOR_CASH_FLAG = request.getParameter("CASH_1_HOUR_"+SUFFIX_CALL_FOR_CASH);
		String flagDisplay = CALL_FOR_CASH_FLAG;
		if(Util.empty(CALL_FOR_CASH_FLAG)){
			flagDisplay = MConstant.FLAG.NO;
		}
		
		if(CASH_DAY_1.equals(CASH_TRANSFER_TYPE) || CASH_1_HOUR.equals(CASH_TRANSFER_TYPE)){
			CALL_FOR_CASH_FLAG = MConstant.FLAG.YES;
		}
		String CALL_FOR_CASH_ACCOUNT_NO = request.getParameter("ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH);
		CALL_FOR_CASH_ACCOUNT_NO = FormatUtil.removeDash(CALL_FOR_CASH_ACCOUNT_NO);
		String COMPLETE_DATA_CALL_FOR_CASH = request.getParameter("COMPLETE_DATA_"+SUFFIX_CALL_FOR_CASH);
		
		DIHQueryResult<String> accountNameResult = dihService.getAccountName(ACCOUNT_NO);
		String ACCOUNT_NAME = accountNameResult.getResult();
		DIHQueryResult<String> dihCIS_SRC_STM_CD= dihService.getCisInfoByAccountNo(ACCOUNT_NO, "CIS_SRC_STM_CD");
		String CIS_SRC_STM_CD = "";
		if(ResponseData.SUCCESS.equals(dihCIS_SRC_STM_CD.getStatusCode())){
			CIS_SRC_STM_CD = dihCIS_SRC_STM_CD.getResult();
		}
		DIHQueryResult<String> callForCashAccountNameResult = dihService.getAccountName(CALL_FOR_CASH_ACCOUNT_NO);
		String CALL_FOR_CASH_ACCOUNT_NAME = callForCashAccountNameResult.getResult();
		
		if(Util.empty(COMPLETE_DATA_CASH_TRANSFER)) {
			COMPLETE_DATA_CASH_TRANSFER = INFO_IS_CORRECT;
		}
		if(Util.empty(COMPLETE_DATA_CALL_FOR_CASH)) {
			COMPLETE_DATA_CALL_FOR_CASH = INFO_IS_CORRECT;
		}
		logger.debug("COMPLETE_DATA_CASH_TRANSFER >> "+COMPLETE_DATA_CASH_TRANSFER);
		logger.debug("CASH_TRANSFER_TYPE >> "+CASH_TRANSFER_TYPE);
		logger.debug("PERCENT_TRANSFER >> "+PERCENT_TRANSFER);
		logger.debug("ACCOUNT_NO >> "+ACCOUNT_NO);
		logger.debug("CALL_FOR_CASH_FLAG >> "+CALL_FOR_CASH_FLAG);
		logger.debug("CALL_FOR_CASH_ACCOUNT_NO >> "+CALL_FOR_CASH_ACCOUNT_NO);
		logger.debug("ACCOUNT_NAME >> "+ACCOUNT_NAME);
		logger.debug("CIS_SRC_STM_CD >> "+CIS_SRC_STM_CD);
		
		if(!Util.empty(applicationItems)){
			for (ApplicationDataM applicationItem : applicationItems) {
				ArrayList<LoanDataM> loans = applicationItem.getLoans();
				for(LoanDataM loan : loans){
					ArrayList<CashTransferDataM> cashTransfers = loan.getCashTransfers();
					if(null == cashTransfers){
						cashTransfers = new ArrayList<CashTransferDataM>();
						loan.setCashTransfers(cashTransfers);					
					}
					CashTransferDataM cashTransfer = null;
					if(!Util.empty(CASH_TRANSFER_TYPE)){
						cashTransfer = loan.getCashTransfer(cashTransFerType);
						if(null == cashTransfer){
							cashTransfer = new CashTransferDataM();
							cashTransfer.setCashTransferType(CASH_TRANSFER_TYPE);
							loan.addCashTransfer(cashTransfer);
						}
						cashTransfer.setCashTransferType(CASH_TRANSFER_TYPE);
						if(!Util.empty(PERCENT_TRANSFER)){
							cashTransfer.setPercentTransfer(new BigDecimal(PERCENT_TRANSFER));
							
						}
						cashTransfer.setCompleteData(COMPLETE_DATA_CASH_TRANSFER);
						if(!Util.empty(ACCOUNT_NAME)){
							cashTransfer.setAccountName(ACCOUNT_NAME);
						}
						cashTransfer.setTransferAccount(ACCOUNT_NO);
						if(!Util.empty(CIS_SRC_STM_CD)){
							if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.CURRENT.getCode())){
								cashTransfer.setTransferAccountType(CURRENT_TRANFER_ACC_TYPE);
							}
							else if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.SAVING.getCode())){
								cashTransfer.setTransferAccountType(SAVING_TRANFER_ACC_TYPE);
							} 
						}
					}else{
						loan.removeCashTransfer(cashTransFerType);
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
						if(!Util.empty(CALL_FOR_CASH_ACCOUNT_NAME)){
							callForCash.setAccountName(CALL_FOR_CASH_ACCOUNT_NAME);
						}
						callForCash.setCompleteData(COMPLETE_DATA_CALL_FOR_CASH);
						
						// To set default value
						if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getTransferAccount())) {
							if(CASH_DAY_1.equals(CASH_TRANSFER_TYPE) || CASH_1_HOUR.equals(CASH_TRANSFER_TYPE) ){
								if(Util.empty(callForCash.getTransferAccount())) {
									callForCash.setTransferAccount(cashTransfer.getTransferAccount());
									callForCash.setCompleteData(COMPLETE_DATA_CASH_TRANSFER);
								}
								if(Util.empty(callForCash.getAccountName())) {
									callForCash.setAccountName(cashTransfer.getAccountName());
								}
								if(!Util.empty(CIS_SRC_STM_CD)){
									if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.CURRENT.getCode())){
										callForCash.setTransferAccountType(CURRENT_TRANFER_ACC_TYPE);
									}
									else if(CIS_SRC_STM_CD.equals(AccountInformationDataM.AcctTableName.SAVING.getCode())){
										callForCash.setTransferAccountType(SAVING_TRANFER_ACC_TYPE);
									} 
								}
							}
						}
					}else{
						loan.removeCashTransfer(CALL_FOR_CASH);
					}
					loan.removeEmptyCashTransferType();
				}
			}
		}		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		logger.debug("subformId >> "+subformId);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);	
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
//		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		
		String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
		String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
		
		CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
		if(null != cashTransfer){
			formError.mandatory(subformId,"CASH_TRANSFER_TYPE_"+SUFFIX_CASH_TRANSFER,cashTransfer.getCashTransferType(),request);
			formError.mandatory(subformId,"PERCENT_TRANSFER_"+SUFFIX_CASH_TRANSFER,"","PERCENT_TRANSFER",cashTransfer.getPercentTransfer(),request);
			formError.mandatory(subformId,"ACCOUNT_NO_"+SUFFIX_CASH_TRANSFER,"","ACCOUNT_NO",cashTransfer.getTransferAccount(),request);
		}		
		CashTransferDataM callForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
		if(null != callForCash){
			formError.mandatory(subformId,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,callForCash.getTransferAccount(),request);
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public void displayValueForm(HttpServletRequest request, Object objectForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		FormDisplayValueUtil formValue = new FormDisplayValueUtil(formId,formLevel,roleId,subformId,applicationGroup,request);
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		if(null == applicationItem){
			applicationItem = new ApplicationDataM();
		}
//		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};	
		String []cashTransFerType = ApplicationUtil.getCashTranferTypes();
		CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
		if(null == cashTransfer){
			cashTransfer = new CashTransferDataM();
		}
		CashTransferDataM callForCash = applicationItem.getCashTransfer(CALL_FOR_CASH);
//		String CALL_FOR_CASH_FLAG = MConstant.FLAG.YES;
		if(null == callForCash){
			callForCash = new CashTransferDataM();
//			CALL_FOR_CASH_FLAG = MConstant.FLAG.NO;
		}
		String SUFFIX_CASH_TRANSFER = PRODUCT_K_EXPRESS_CASH+"_CASH_TRANSFER";
		String SUFFIX_CALL_FOR_CASH = PRODUCT_K_EXPRESS_CASH+"_"+CALL_FOR_CASH;
		cashTransfer.setCashTransferType(formValue.getValue("CASH_TRANSFER_TYPE","CASH_TRANSFER_TYPE_"+SUFFIX_CASH_TRANSFER,cashTransfer.getCashTransferType()));
		cashTransfer.setPercentTransfer(formValue.getValue("PERCENT_TRANSFER","PERCENT_TRANSFER_"+SUFFIX_CASH_TRANSFER,cashTransfer.getPercentTransfer()));
		cashTransfer.setTransferAccount(formValue.getValue("ACCOUNT_NO_"+SUFFIX_CASH_TRANSFER,"ACCOUNT_NO_"+SUFFIX_CASH_TRANSFER,cashTransfer.getTransferAccount()));
		
//		applicationItem.getCashTransfer(CALL_FOR_CASH);
		callForCash.setTransferAccount(formValue.getValue("ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,"ACCOUNT_NO_"+SUFFIX_CALL_FOR_CASH,callForCash.getTransferAccount()));
		callForCash.setAccountName(formValue.getValue("ACCOUNT_NAME_"+SUFFIX_CALL_FOR_CASH,"ACCOUNT_NAME_"+SUFFIX_CALL_FOR_CASH,callForCash.getAccountName()));
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm) {
		logger.debug("CashDay1SubForm.auditForm");
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
		auditFormUtil.auditForm(subFormId, "CASH_TRANSFER_TYPE", cashTransfer, lastCashTransfer, request);
		auditFormUtil.auditForm(subFormId, "ACCOUNT_NO", cashTransfer, lastCashTransfer, request);
		auditFormUtil.auditForm(subFormId, "CALL_FOR_CASH", callForCash, lastCallForCash, request);
		auditFormUtil.auditForm(subFormId, "ACCOUNT_NO_KEC_04", callForCash, lastCallForCash, request);
		auditFormUtil.auditForm(subFormId, "PERCENT_TRANSFER_KEC_CASH_TRANSFER", cashTransfer, lastCashTransfer, request);
		auditFormUtil.auditForm(subFormId, "ACCOUNT_NO_KEC_CASH_TRANSFER", cashTransfer, lastCashTransfer, request);
		return auditFormUtil.getAuditForm();
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("CASH_TRANSFER_TYPE");
		filedNames.add("PERCENT_TRANSFER");
		filedNames.add("TRANSFER_ACCOUNT");
		filedNames.add("CALL_FOR_CASH_ACCOUNT_NO");
		filedNames.add("CALL_FOR_CASH_ACCOUNT_NAME");
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);	
			ArrayList<ApplicationDataM> applicationItems = applicationGroup.filterApplicationLifeCycle(PRODUCT_K_EXPRESS_CASH);
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
}
