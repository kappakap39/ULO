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
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.ibm.rules.decisionservice.flp_uis.flp_decision.ApplicationGroup;

public class BankStatementPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(BankStatementPopup.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		IncomeInfoDataM incomeM = ((IncomeInfoDataM)appForm);
		ArrayList<IncomeCategoryDataM> monthlyList = incomeM.getAllIncomes();
	
		String note = request.getParameter("NOTE");
		incomeM.setRemark(note);
		Calendar calen = Calendar.getInstance();
		Date appDate = mainForm.getObjectForm().getApplicationDate();
		if(Util.empty(appDate)) {
			appDate = ApplicationDate.getDate();
		}
		if(!Util.empty(monthlyList)) {
			for(IncomeCategoryDataM statementM: monthlyList) {
				BankStatementDataM monthlyData = (BankStatementDataM) statementM;
				String bankCode = request.getParameter("BANK_NAME_"+monthlyData.getSeq());
				String statementCode = request.getParameter("SALARY_CODE_"+monthlyData.getSeq());
				String additionalCode = request.getParameter("ADDITIONAL_"+monthlyData.getSeq());
				String mon1Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_1");
				String mon2Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_2");
				String mon3Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_3");
				String mon4Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_4");
				String mon5Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_5");
				String mon6Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_6");
				String mon7Income = request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_7");
				
				logger.debug("bankCode >>"+bankCode);
				logger.debug("statementCode >>"+statementCode);
				logger.debug("additionalCode >>"+additionalCode);
				logger.debug("mon1Income >>"+mon1Income);
				logger.debug("mon2Income >>"+mon2Income);
				logger.debug("mon3Income >>"+mon3Income);
				logger.debug("mon4Income >>"+mon4Income);
				logger.debug("mon5Income >>"+mon5Income);
				logger.debug("mon6Income >>"+mon6Income);
				logger.debug("mon7Income >>"+mon7Income);
				
				monthlyData.setBankCode(bankCode);
				monthlyData.setStatementCode(statementCode);
				monthlyData.setAdditionalCode(additionalCode);
				
				ArrayList<BankStatementDetailDataM> detailList = monthlyData.getBankStatementDetails();
				if(Util.empty(detailList)) {
					detailList = new ArrayList<BankStatementDetailDataM>();
					monthlyData.setBankStatementDetails(detailList);
					for(int i = 0;i<BankStatementDetailDataM.MONTH_COUNT; i++) {
						BankStatementDetailDataM monthDtl = new BankStatementDetailDataM();
						monthDtl.setSeq(i+1);
						detailList.add(monthDtl);
					}
				}
				for(int i = 0; i < detailList.size(); i++) {
					BankStatementDetailDataM monthDtl = detailList.get(i);
					if(monthDtl == null) {
						monthDtl = new BankStatementDetailDataM();
						detailList.set(i, monthDtl);
					}
							
					Date prevMonth = Util.getPreviousMonth(appDate, i);
					calen.setTime(prevMonth);
					String year = Util.getYear(prevMonth);
					monthDtl.setAmount(FormatUtil.toBigDecimal(request.getParameter("AMOUNT_"+monthlyData.getSeq()+"_"+(i+1)), true));
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
		String subformId = "BANK_STATEMENT_POPUP";
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> bankStatementList = incomeM.getAllIncomes();
		ORIGFormHandler mainForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		String source = mainForm.getObjectForm().getSource();
		
		if(!Util.empty(bankStatementList)) {
			for(IncomeCategoryDataM incomeObj: bankStatementList) {
				BankStatementDataM monthlyData = (BankStatementDataM) incomeObj;
				formError.mandatory(subformId,"BANK_NAME","BANK_NAME_"+monthlyData.getSeq(),"", monthlyData.getBankCode(),request);
				formError.mandatory(subformId,"SALARY_CODE","SALARY_CODE_"+monthlyData.getSeq(),"", monthlyData.getStatementCode(),request);
				formError.mandatory(subformId,"ADDITIONAL","ADDITIONAL_"+monthlyData.getSeq(),"", monthlyData.getAdditionalCode(),request);
				formError.mandatory(subformId,"BANK_STATEMENT_AMT","", monthlyData,request);
			}
		} else {
			formError.error("BANK_STATEMENT",MessageErrorUtil.getText(request,"ERROR_ID_ATLEAST_ONE_RECORD"));
		}
		
		if(Util.empty(formError.getFormError())) {
			//Check source e-app for ignore compare field
			/*if(ApplicationUtil.eApp(source)) {
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
//			}
		}
		return formError.getFormError();
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
