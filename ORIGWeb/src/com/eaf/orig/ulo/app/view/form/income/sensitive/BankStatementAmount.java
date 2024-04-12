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
import com.eaf.orig.ulo.model.app.BankStatementDataM;
import com.eaf.orig.ulo.model.app.BankStatementDetailDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BankStatementAmount extends CompareIncomeHelper implements CompareIncomeInf{
	@Override
	public void maskField(HttpServletRequest request,Object objectElement) {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		if(Util.empty(applicationGroup.getReprocessFlag())){
			if(MConstant.FLAG.YES.equals(getClearFlag())) {
				IncomeInfoDataM incomeM = (IncomeInfoDataM) objectElement;
				ArrayList<IncomeCategoryDataM> bankStatementList = incomeM.getAllIncomes();
				if(!Util.empty(bankStatementList)) {
					for(IncomeCategoryDataM incomeObj: bankStatementList) {
						BankStatementDataM statementM = (BankStatementDataM) incomeObj;
						ArrayList<BankStatementDetailDataM> detailList = statementM.getBankStatementDetails();
						if(!Util.empty(detailList)) {
							for(BankStatementDetailDataM monthDtl : detailList) {
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
		ArrayList<IncomeCategoryDataM> bankStatementList = incomeM.getAllIncomes();
		
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
//		String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
//		PersonalInfoDataM personalInfoOrig = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		PersonalInfoDataM personalInfoOrig = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeOrig = personalInfoOrig.getIncomeById(incomeM.getIncomeId());
		
		if(!Util.empty(bankStatementList)) {
			for(IncomeCategoryDataM incomeObj: bankStatementList) {
				BankStatementDataM statementM = (BankStatementDataM) incomeObj;
				ArrayList<BankStatementDetailDataM> detailList = statementM.getBankStatementDetails();
				
				if(statementM.getId() != null) {
					BankStatementDataM statementOrig = (BankStatementDataM)incomeOrig.getIncomeCategoryById(statementM.getId());
					if(!Util.empty(detailList) && statementOrig != null) {
						for(BankStatementDetailDataM detailM: detailList) {
							if(!Util.empty(detailM.getBankStatementDetailId())) {
								BankStatementDetailDataM detail2 = statementOrig.getBankStatementDetailById(detailM.getBankStatementDetailId());
								if(detail2 != null && !CompareObject.compare(detailM.getAmount(), detail2.getAmount(),null) ){
									formError.clearElement(getFieldName()+"_"+statementM.getSeq()+"_"+detailM.getSeq());
								}
							}
						}
					}
				} else {
					if(!Util.empty(detailList)) {
						for(BankStatementDetailDataM detailM: detailList) {
							formError.clearElement(getFieldName()+"_"+statementM.getSeq()+"_"+detailM.getSeq());
						}
					}
				}
			}
		}
		return formError.getFormError();
	}
}
