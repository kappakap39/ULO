package com.eaf.orig.ulo.app.view.form.subform.income;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.CompareIncomeControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SalaryCertDataM;

public class VerifyDebtSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(VerifyDebtSubForm.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup=(ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<DebtInfoDataM> debtList = personalInfo.getDebtInfos();
		String roleId = FormControl.getFormRoleId(request);
		
		if(!Util.empty(debtList)) {
			for(DebtInfoDataM debtM: debtList) {
				int debtSeq = debtM.getSeq();
				String debtAmt = request.getParameter("DEBT_AMOUNT_"+debtSeq);
				logger.debug("debtAmt >>"+debtSeq+":"+debtAmt);	
				debtM.setDebtAmt(FormatUtil.toBigDecimal(debtAmt));
			}
		}
		/*if(MConstant.ROLE.DV.equals(roleId)){
			ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(MConstant.COMPARE_SCREEN_TYPE.DEBT_INFO);
			if(!Util.empty(compareFieldElements)) {
				for(CompareIncomeInf element: compareFieldElements) {
					element.compareField(request, personalInfo);
				}
			}
		}*/
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup=(ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<DebtInfoDataM> debtList = personalInfo.getDebtInfos();
		
			for(DebtInfoDataM debtM: debtList) {
				int debtSeq = debtM.getSeq();
				String debtAmt = request.getParameter("DEBT_AMOUNT_"+debtSeq);
				logger.debug("validateForm debtAmt >>"+debtSeq+":"+debtAmt);	
				if(Util.empty(debtAmt)) {
					formError.error("DEBT_AMOUNT_"+debtSeq,MessageErrorUtil.getText(request,"ERROR_DEPT_INFO"));
				}
			}
		
		if(Util.empty(formError.getFormError())) {
			String roleId = FormControl.getFormRoleId(request);	
			if(MConstant.ROLE.DV.equals(roleId)){
				ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(MConstant.COMPARE_SCREEN_TYPE.DEBT_INFO);
				if(!Util.empty(compareFieldElements)) {
					for(CompareIncomeInf element: compareFieldElements) {
						formError.addAll(element.compareField(request, personalInfo));
					}
				}
			}
			
			if(!Util.empty(formError.getFormError())) {
				//push error message
				formError.error(MessageUtil.getText(request, "MSG_DV2_COMPARE"));
			}
		}
		
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}

}
