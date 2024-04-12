package com.eaf.orig.ulo.app.view.form.popup.income;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;

public class OtherSavingAccountPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(OtherSavingAccountPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		Calendar calen = Calendar.getInstance();
		Date appDate = mainForm.getObjectForm().getApplicationDate();
		if(Util.empty(appDate)) {
			appDate = ApplicationDate.getDate();
		}
		
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> othSavingList = incomeM.getAllIncomes();
		if(!Util.empty(othSavingList)) {
			for(IncomeCategoryDataM incomeObj: othSavingList) {
				SavingAccountDataM othSavingAcctM = (SavingAccountDataM) incomeObj;
				String bankCode = request.getParameter("FIN_INSTITUTION_"+othSavingAcctM.getSeq());
				String accountNo = request.getParameter("ACCOUNT_NO_"+othSavingAcctM.getSeq());
				String accountName = request.getParameter("ACCOUNT_NAME_"+othSavingAcctM.getSeq());
				String holdingRatio = request.getParameter("PORTION_"+othSavingAcctM.getSeq());
				
				logger.debug("bankCode >>"+bankCode);
				logger.debug("accountNo >>"+accountNo);
				logger.debug("accountName >>"+accountName);
				logger.debug("holdingRatio >>"+holdingRatio);
				if(!Util.empty(accountNo)){
					accountNo = FormatUtil.removeDash(accountNo);
				}
				othSavingAcctM.setBankCode(bankCode);
				othSavingAcctM.setAccountNo(accountNo);
				othSavingAcctM.setAccountName(accountName);
				othSavingAcctM.setHoldingRatio(FormatUtil.toBigDecimal(holdingRatio, true));
				
				//For Monthly Acct details
				ArrayList<SavingAccountDetailDataM> detailList = othSavingAcctM.getSavingAccountDetails();
				if(Util.empty(detailList)) {
					detailList = new ArrayList<SavingAccountDetailDataM>();
					othSavingAcctM.setSavingAccountDetails(detailList);
					for(int i = 0;i<SavingAccountDetailDataM.MONTH_COUNT; i++) {
						SavingAccountDetailDataM monthDtl = new SavingAccountDetailDataM();
						monthDtl.setSeq(i+1);
						detailList.add(monthDtl);
					}
				}
				for(int i = 0; i < detailList.size(); i++) {
					SavingAccountDetailDataM monthDtl = detailList.get(i);
					if(monthDtl == null) {
						monthDtl = new SavingAccountDetailDataM();
						detailList.set(i, monthDtl);
					}
							
					Date prevMonth = Util.getPreviousMonth(appDate, i);
					calen.setTime(prevMonth);
					String year = Util.getYear(prevMonth);		
					monthDtl.setAmount(FormatUtil.toBigDecimal(request.getParameter("AMOUNT_"+othSavingAcctM.getSeq()+"_"+(i+1)), true));
					monthDtl.setMonth(FormatUtil.toString(calen.get(Calendar.MONTH)+1));
					monthDtl.setYear(year);
				}
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
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		String subformId = "OTH_SAVING_ACCT_POPUP";
		ArrayList<IncomeCategoryDataM> savAcctList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(savAcctList)) {
			for(IncomeCategoryDataM incomeObj: savAcctList) {
				SavingAccountDataM othSavingAcctM = (SavingAccountDataM) incomeObj;
				formError.mandatory(subformId,"FIN_INSTITUTION","FIN_INSTITUTION_"+othSavingAcctM.getSeq(), othSavingAcctM.getBankCode(),request);
				formError.mandatory(subformId,"ACCOUNT_NO","ACCOUNT_NO_"+othSavingAcctM.getSeq(), othSavingAcctM.getAccountNo(),request);
				formError.mandatory(subformId,"ACCOUNT_NAME","ACCOUNT_NAME_"+othSavingAcctM.getSeq(), othSavingAcctM.getAccountName(),request);
				formError.mandatory(subformId,"PORTION","PORTION_"+othSavingAcctM.getSeq(), othSavingAcctM.getHoldingRatio(),request);
				formError.mandatory(subformId,"SAVING_ACCT_AMT","", othSavingAcctM,request);
			}
		} else {
			formError.error("SAVING_ACCT_FUND",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
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
