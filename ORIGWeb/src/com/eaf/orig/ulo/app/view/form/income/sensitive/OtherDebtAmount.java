package com.eaf.orig.ulo.app.view.form.income.sensitive;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.CompareIncomeHelper;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class OtherDebtAmount  extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		if(MConstant.FLAG.YES.equals(getClearFlag())) {
			PersonalInfoDataM personalInfo = (PersonalInfoDataM) objectElement;
			ArrayList<DebtInfoDataM> debtList = personalInfo.getDebtInfos();
			if(!Util.empty(debtList)) {
				for(DebtInfoDataM debtInfoM: debtList) {
					if(!(MConstant.FLAG.YES.equals(debtInfoM.getCompareFlag()) 
							|| MConstant.FLAG.WRONG.equals(debtInfoM.getCompareFlag()))) {
						debtInfoM.setDebtAmt(null);
					}
				}
			}
		}
	}
	
	@Override
	public HashMap<String,Object> compareField(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		PersonalInfoDataM personalInfo = (PersonalInfoDataM) objectElement;
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalById(personalInfo.getPersonalId());
		
		ArrayList<DebtInfoDataM> debtList = personalInfo.getDebtInfos();
		if(!Util.empty(debtList)){
			for(DebtInfoDataM debtInfoM: debtList) {
				DebtInfoDataM debtInfoOrig = personalInfoOrig.getDebtInfoByType(debtInfoM.getDebtType());
				if(MConstant.FLAG.TEMP.equals(debtInfoM.getCompareFlag())) {
					if(debtInfoOrig != null && !CompareObject.compare(debtInfoM.getDebtAmt(), debtInfoOrig.getDebtAmt(),null)) {
						debtInfoM.setCompareFlag(MConstant.FLAG.WRONG);
					} else {
						debtInfoM.setCompareFlag(MConstant.FLAG.YES);
					}
				} else if(!(MConstant.FLAG.YES.equals(debtInfoM.getCompareFlag()) 
						|| MConstant.FLAG.WRONG.equals(debtInfoM.getCompareFlag()))){
					if(debtInfoOrig != null &&  !CompareObject.compare(debtInfoM.getDebtAmt(), debtInfoOrig.getDebtAmt(),null)) {
						formError.clearElement(getFieldName()+"_"+debtInfoM.getSeq());
						debtInfoM.setCompareFlag(MConstant.FLAG.TEMP);
					} else {
						debtInfoM.setCompareFlag(MConstant.FLAG.YES);
					}
				}
				
			}
		}
		return formError.getFormError();
	}
}
