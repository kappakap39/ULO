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
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDataM;
import com.eaf.orig.ulo.model.app.SavingAccountDetailDataM;

public class SavingAcctAmount  extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
				ArrayList<IncomeCategoryDataM> savAcctList = incomeM.getAllIncomes();
				if(!Util.empty(savAcctList)) {
					for(IncomeCategoryDataM incomeObj: savAcctList) {
						SavingAccountDataM savAcctM = (SavingAccountDataM) incomeObj;
						ArrayList<SavingAccountDetailDataM> detailList = savAcctM.getSavingAccountDetails();
						if(!Util.empty(detailList)) {
							for(SavingAccountDetailDataM monthDtl : detailList) {
								monthDtl.setAmount(null);
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
		ArrayList<IncomeCategoryDataM> savAcctList = incomeM.getAllIncomes();
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM personalInfoOrig = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeOrig = personalInfoOrig.getIncomeById(incomeM.getIncomeId());
		
		if(!Util.empty(savAcctList)) {
			for(IncomeCategoryDataM incomeObj: savAcctList) {
				SavingAccountDataM savAcctM = (SavingAccountDataM) incomeObj;
				ArrayList<SavingAccountDetailDataM> detailList = savAcctM.getSavingAccountDetails();
				if(savAcctM.getId() != null) {
					SavingAccountDataM savAcctOrig = (SavingAccountDataM)incomeOrig.getIncomeCategoryById(savAcctM.getId());
					
					if(!Util.empty(detailList) && savAcctOrig != null) {
						for(SavingAccountDetailDataM detailM: detailList) {
							if(!Util.empty(detailM.getSavingAccDetailId())) {
								SavingAccountDetailDataM detail2 = savAcctOrig.getSavingAcctDetailById(detailM.getSavingAccDetailId());
								if(detail2 != null &&  !CompareObject.compare(detailM.getAmount(), detail2.getAmount(),null)){
									formError.clearElement(getFieldName()+"_"+savAcctM.getSeq()+"_"+detailM.getSeq());
								}
							}
						}
					}
				} else {
					if(!Util.empty(detailList)) {
						for(SavingAccountDetailDataM detailM: detailList) {
							formError.clearElement(getFieldName()+"_"+savAcctM.getSeq()+"_"+detailM.getSeq());
						}
					}
				}
			}
		}
		return formError.getFormError();
	}
}
