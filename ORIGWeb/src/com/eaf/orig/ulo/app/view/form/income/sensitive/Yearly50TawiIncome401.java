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
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class Yearly50TawiIncome401 extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
				String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
				ArrayList<IncomeCategoryDataM> yearlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_YEARLY_50TAWI);
				if(!Util.empty(yearlyList)) {
					for(IncomeCategoryDataM incomeObj: yearlyList) {
						if(incomeObj instanceof YearlyTawi50DataM) {
							YearlyTawi50DataM yearlyDataM = (YearlyTawi50DataM) incomeObj;
							yearlyDataM.setIncome401(null);
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
		String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
		ArrayList<IncomeCategoryDataM> yearlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_YEARLY_50TAWI);
		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM personalInfoOrig = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeOrig = personalInfoOrig.getIncomeById(incomeM.getIncomeId());
		
		if(!Util.empty(yearlyList)) {
			for(IncomeCategoryDataM incomeObj: yearlyList) {
				if(incomeObj instanceof YearlyTawi50DataM) {
					YearlyTawi50DataM yearlyDataM = (YearlyTawi50DataM) incomeObj;
					if(yearlyDataM.getId() != null) {
						YearlyTawi50DataM yearlyOrig = (YearlyTawi50DataM)incomeOrig.getIncomeCategoryById(yearlyDataM.getId());
						if(yearlyOrig != null &&  !CompareObject.compare(yearlyDataM.getIncome401(), yearlyOrig.getIncome401(),null)) {
							formError.clearElement(getFieldName()+"_"+yearlyDataM.getSeq());
						} 
					} else {
						formError.clearElement(getFieldName()+"_"+yearlyDataM.getSeq());
					}
				}
			}
		}
		return formError.getFormError();
	}
}
