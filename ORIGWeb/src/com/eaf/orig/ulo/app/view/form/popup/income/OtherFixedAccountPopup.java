package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.FixedAccountDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

public class OtherFixedAccountPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OtherFixedAccountPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> othFixedList = incomeM.getAllIncomes();
		if(!Util.empty(othFixedList)) {
			for(IncomeCategoryDataM incomeObj: othFixedList) {
				FixedAccountDataM othFixedAcctM = (FixedAccountDataM) incomeObj;
				String bankCode = request.getParameter("FIN_INSTITUTION_"+othFixedAcctM.getSeq());
				String accountNo = request.getParameter("ACCOUNT_NO_"+othFixedAcctM.getSeq());
				String accountName = request.getParameter("ACCOUNT_NAME_"+othFixedAcctM.getSeq());
				String holdingRatio = request.getParameter("PORTION_"+othFixedAcctM.getSeq());
				String accountBalance = request.getParameter("OUTSTANDING_BALANCE_"+othFixedAcctM.getSeq());
				
				logger.debug("bankCode >>"+bankCode);	
				logger.debug("accountNo >>"+accountNo);	
				logger.debug("accountName >>"+accountName);	
				logger.debug("holdingRatio >>"+holdingRatio);	
				logger.debug("accountBalance >>"+accountBalance);
				
				othFixedAcctM.setBankCode(bankCode);
				othFixedAcctM.setAccountNo(accountNo);
				othFixedAcctM.setAccountName(accountName);
				othFixedAcctM.setHoldingRatio(FormatUtil.toBigDecimal(holdingRatio, true));
				othFixedAcctM.setAccountBalance(FormatUtil.toBigDecimal(accountBalance, true));
			}
		}
		
		//to check if final value from screen match with previous or not
		if(MConstant.FLAG.TEMP.equals(incomeM.getCompareFlag())) {
			FormErrorUtil formError = new FormErrorUtil();
			formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
			if(Util.empty(formError.getFormError())) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			} else {
				incomeM.setCompareFlag(MConstant.FLAG.WRONG);
			}
		}
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "OTH_FIXED_ACCT_POPUP";
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> fixedAcctList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(fixedAcctList)) {
			for(IncomeCategoryDataM incomeObj: fixedAcctList) {
				FixedAccountDataM othFixedAcctM = (FixedAccountDataM) incomeObj;
				formError.mandatory(subformId,"FIN_INSTITUTION","FIN_INSTITUTION_"+othFixedAcctM.getSeq(),"", othFixedAcctM.getBankCode(),request);
				formError.mandatory(subformId,"ACCOUNT_NO","ACCOUNT_NO_"+othFixedAcctM.getSeq(),"", othFixedAcctM.getAccountNo(),request);
				formError.mandatory(subformId,"ACCOUNT_NAME","ACCOUNT_NAME_"+othFixedAcctM.getSeq(),"", othFixedAcctM.getAccountName(),request);
				formError.mandatory(subformId,"PORTION","PORTION_"+othFixedAcctM.getSeq(),"", othFixedAcctM.getHoldingRatio(),request);
				formError.mandatory(subformId,"OUTSTANDING_BALANCE","OUTSTANDING_BALANCE_"+othFixedAcctM.getSeq(),"", othFixedAcctM.getAccountBalance(),request);
			}
		} else {
			formError.error("FIX_ACCT_FUND",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}

		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
/*			if(ApplicationUtil.eApp(source)) {
				incomeM.setCompareFlag(MConstant.FLAG.YES);
			}else{*/
				String roleId = FormControl.getFormRoleId(request);
				if(MConstant.ROLE.DV.equals(roleId) && !IncomeTypeUtility.isIncomeComplete(incomeM, roleId)) {
					formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
					
					if(Util.empty(formError.getFormError())) {
						incomeM.setCompareFlag(MConstant.FLAG.YES);
					} else {
						incomeM.setCompareFlag(MConstant.FLAG.TEMP);
					}
				}
			/*}*/
		}
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
