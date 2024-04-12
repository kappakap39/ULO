package com.eaf.orig.ulo.app.view.form.income.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.CompareIncomeHelper;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDataM;
import com.eaf.orig.ulo.model.app.PayslipMonthlyDetailDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class PayslipMonthlyAmount extends CompareIncomeHelper implements CompareIncomeInf{
	String PAYSLIP_INCOME_SSO = SystemConstant.getConstant("PAYSLIP_INCOME_SSO");
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
				ArrayList<IncomeCategoryDataM> payslipList = incomeM.getAllIncomes();
				if(!Util.empty(payslipList)) {
					for(IncomeCategoryDataM incomeTypeM: payslipList) {
						PayslipDataM payslipM = (PayslipDataM) incomeTypeM;
						ArrayList<PayslipMonthlyDataM> monthly = payslipM.getPayslipMonthlys();
						if(!Util.empty(monthly)) {
							for(PayslipMonthlyDataM monthlyData: monthly) {
								if(PAYSLIP_INCOME_SSO.equals(monthlyData.getIncomeType())) {
				 					continue;
				 				}
								
								ArrayList<PayslipMonthlyDetailDataM> detailList = monthlyData.getPayslipMonthlyDetails();
								if(!Util.empty(detailList)) {
									for(PayslipMonthlyDetailDataM monthDtl : detailList) {
										monthDtl.setAmount(null);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	public HashMap<String,Object> compareField(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
		ArrayList<IncomeCategoryDataM> payslipList = incomeM.getAllIncomes();
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM personalInfoOrig = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeOrig = personalInfoOrig.getIncomeById(incomeM.getIncomeId());
		
		if(!Util.empty(payslipList) && incomeOrig != null) {
			for(IncomeCategoryDataM incomeTypeM: payslipList) {
				PayslipDataM payslipM = (PayslipDataM) incomeTypeM;
				ArrayList<PayslipMonthlyDataM> payslipMonthlyList = payslipM.getPayslipMonthlys();
				if(payslipM.getId() != null) {
					PayslipDataM payslipOrig = (PayslipDataM)incomeOrig.getIncomeCategoryById(payslipM.getId());
					if(!Util.empty(payslipMonthlyList)) {
						if(Util.empty(payslipOrig))payslipOrig = new PayslipDataM();
						for(PayslipMonthlyDataM payslipMonM: payslipMonthlyList) {
							ArrayList<PayslipMonthlyDetailDataM> detailListTest = payslipMonM.getPayslipMonthlyDetails();
							if(PAYSLIP_INCOME_SSO.equals(payslipMonM.getIncomeType())) {
			 					continue;
			 				}
//							if(!Util.empty(payslipMonM.getPayslipMonthlyId())) {
								PayslipMonthlyDataM payslipMon2 = payslipOrig.getPayslipMonthlyById(payslipMonM.getPayslipMonthlyId());
								ArrayList<PayslipMonthlyDetailDataM> detailList = payslipMonM.getPayslipMonthlyDetails();
								if(!Util.empty(detailList)) {
									for(PayslipMonthlyDetailDataM detailM: detailList) {
										if(!Util.empty(detailM.getPayslipMonthlyDetailId())) {
											if(Util.empty(payslipMon2)){
												payslipMon2 = new PayslipMonthlyDataM();
											}
											PayslipMonthlyDetailDataM detail2 = payslipMon2.getPayslipMonthlyDetailById(detailM.getPayslipMonthlyDetailId());
												if(detail2 != null &&  !CompareObject.compare(detailM.getAmount(), detail2.getAmount(),null)){
													formError.clearElement(getFieldName()+"_"+payslipMonM.getSeq()+"_"+detailM.getSeq());
												}
											}else if(Util.empty(detailM.getPayslipMonthlyDetailId())){
												formError.clearElement(getFieldName()+"_"+payslipMonM.getSeq()+"_"+detailM.getSeq());
											}
									}
								}
//							}
						}
					}
				} else {
					if(!Util.empty(payslipMonthlyList)) {
						for(PayslipMonthlyDataM payslipMonM: payslipMonthlyList) {
							ArrayList<PayslipMonthlyDetailDataM> detailList = payslipMonM.getPayslipMonthlyDetails();
							if(!Util.empty(detailList)) {
								for(PayslipMonthlyDetailDataM detailM: detailList) {
									formError.clearElement(getFieldName()+"_"+payslipMonM.getSeq()+"_"+detailM.getSeq());
								}
							}
						}
					}
				}
			}
		}
		return formError.getFormError();
	}
}
