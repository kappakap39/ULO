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
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DetailDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class Monthly50TawiAmount  extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
				String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
				ArrayList<IncomeCategoryDataM> monthlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_MONTHLY_50TAWI);
				if(!Util.empty(monthlyList)) {
					for(IncomeCategoryDataM monthlyM: monthlyList) {
						if(monthlyM instanceof MonthlyTawi50DataM) {
							MonthlyTawi50DataM monthlyData = (MonthlyTawi50DataM) monthlyM;
							ArrayList<MonthlyTawi50DetailDataM> detailList = monthlyData.getMonthlyTaxi50Details();
							if(!Util.empty(detailList)) {
								for(MonthlyTawi50DetailDataM monthDtl : detailList) {
									monthDtl.setAmount(null);
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
		String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
		ArrayList<IncomeCategoryDataM> monthlyList = incomeM.getAllIncomeCategoryByType(INC_TYPE_MONTHLY_50TAWI);
		
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM personalInfoOrig = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeOrig = personalInfoOrig.getIncomeById(incomeM.getIncomeId());
		
		if(!Util.empty(monthlyList)){
			for(IncomeCategoryDataM monthlyM: monthlyList) {
				if(monthlyM instanceof MonthlyTawi50DataM) {
					MonthlyTawi50DataM monthlyData = (MonthlyTawi50DataM) monthlyM;
					ArrayList<MonthlyTawi50DetailDataM> detailList = monthlyData.getMonthlyTaxi50Details();
					if(monthlyData.getId() != null) {
						MonthlyTawi50DataM monthlyOrig = (MonthlyTawi50DataM)incomeOrig.getIncomeCategoryById(monthlyData.getId());
						
						if(!Util.empty(detailList) && monthlyOrig != null) {
							for(MonthlyTawi50DetailDataM detailM: detailList) {
								if(!Util.empty(detailM.getMonthlyTawiDetailId())) {
									MonthlyTawi50DetailDataM detail2 = monthlyOrig.getMonthlyTawiDetailById(detailM.getMonthlyTawiDetailId());
									if(detail2 != null &&  !CompareObject.compare(detailM.getAmount(), detail2.getAmount(),null)){
										formError.clearElement(getFieldName()+"_"+monthlyData.getSeq()+"_"+detailM.getSeq());
									}
								}
							}
						}
					} else {
						if(!Util.empty(detailList)) {
							for(MonthlyTawi50DetailDataM detailM: detailList) {
								formError.clearElement(getFieldName()+"_"+monthlyData.getSeq()+"_"+detailM.getSeq());
							}
						}
					}
				}
			}
		}
		return formError.getFormError();
	}
}
