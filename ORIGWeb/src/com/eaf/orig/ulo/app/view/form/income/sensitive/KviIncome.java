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
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;
import com.eaf.orig.ulo.model.app.PayrollDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class KviIncome  extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				ArrayList<IncomeCategoryDataM> kviList = incomeM.getAllIncomes();
				if(!Util.empty(kviList)) {
					for(IncomeCategoryDataM incomeTypeM: kviList) {
						KVIIncomeDataM kviM = (KVIIncomeDataM)incomeTypeM;
						kviM.setVerifiedIncome(null);
					}
				}
			}
		}
	}
	
	@Override
	public HashMap<String,Object> compareField(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
		ArrayList<IncomeCategoryDataM> kviList = incomeM.getAllIncomes();
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM personalInfoOrig = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeOrig = personalInfoOrig.getIncomeById(incomeM.getIncomeId());
		
		if(!Util.empty(kviList) && incomeOrig != null) {
			for(IncomeCategoryDataM incomeTypeM: kviList) {
				KVIIncomeDataM kviM = (KVIIncomeDataM)incomeTypeM;
				if(kviM.getId() != null) {
					KVIIncomeDataM kviOrig = (KVIIncomeDataM)incomeOrig.getIncomeCategoryById(kviM.getId());
					if(kviOrig != null &&  !CompareObject.compare(kviM.getVerifiedIncome(), kviOrig.getVerifiedIncome(),null)) {
						formError.clearElement(getFieldName());
					}
				} else {
					formError.clearElement(getFieldName());
				}
			}
		}
		return formError.getFormError();
	}
}
