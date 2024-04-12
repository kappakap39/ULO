package com.eaf.orig.ulo.app.view.form.income.sensitive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.CompareIncomeHelper;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class FixedGuaranteeAccount  extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
				ArrayList<IncomeCategoryDataM> fixedList = incomeM.getAllIncomes();
				if(!Util.empty(fixedList)){
					incomeM.setAllIncomes(new ArrayList<IncomeCategoryDataM>());
				}
			}
		}
	}
	
	@Override
	public HashMap<String,Object> compareField(HttpServletRequest request, Object objectElement){
		FormErrorUtil formError = new FormErrorUtil();
		IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
		ArrayList<IncomeCategoryDataM> fixedList = incomeM.getAllIncomes();
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM personalInfoOrig = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeOrig = personalInfoOrig.getIncomeById(incomeM.getIncomeId());
		
		if(!Util.empty(fixedList)) {
			
			if(!Util.empty(incomeOrig) && !Util.empty(incomeOrig.getAllIncomes())) {
				ArrayList<IncomeCategoryDataM> fixedListOrig = incomeOrig.getAllIncomes();
				if(fixedListOrig.size() > fixedList.size()) {
					formError.error(MessageUtil.getText(request, "MSG_FIXED_GUARANTEE_NOOF_RECORDS"));
				}
				Iterator<IncomeCategoryDataM> incomeIter = fixedList.iterator();
				while(incomeIter.hasNext()) {
					FixedGuaranteeDataM fixedM = (FixedGuaranteeDataM) incomeIter.next();
					boolean foundDeposit = false;
					for(IncomeCategoryDataM incomeCategOrig: fixedListOrig) {
						FixedGuaranteeDataM fixedOrig = (FixedGuaranteeDataM) incomeCategOrig;
						if( CompareObject.compare(fixedM.getAccountNo(),fixedOrig.getAccountNo(),null)){
							if( CompareObject.compare(fixedM.getSub(),fixedOrig.getSub(),null)){
								if( CompareObject.compare(fixedM.getRetentionAmt(),fixedOrig.getRetentionAmt(),null)){
									foundDeposit = true;
									break;
								}
							}
						}
					} 
					if(!foundDeposit && MConstant.FLAG.NO.equals(incomeM.getCompareFlag())){
						formError.response("NotFound", "true");
						formError.error(MessageUtil.getText(request, "MSG_FIXED_GUARANTEE_NOT_MATCH")+" "+fixedM.getAccountNo()+"/"+fixedM.getSub());
						incomeIter.remove();
					}
				}
			} else {
				//If De1.2 has no records but DV has
				formError.error(MessageUtil.getText(request, "MSG_FIXED_GUARANTEE_NOOF_RECORDS"));
				formError.response("NotFound", "true");
				incomeM.setAllIncomes(new ArrayList<IncomeCategoryDataM>());
			}
		} else {
			//If De1.2 has records but not DV
			if(!Util.empty(incomeOrig) && !Util.empty(incomeOrig.getAllIncomes())) {
				formError.error(MessageUtil.getText(request, "MSG_FIXED_GUARANTEE_NOOF_RECORDS"));
			}
		}
		return formError.getFormError();
	}
}
