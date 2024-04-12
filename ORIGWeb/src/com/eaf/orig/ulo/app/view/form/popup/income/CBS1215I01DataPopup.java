package com.eaf.orig.ulo.app.view.form.popup.income;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;

@SuppressWarnings("serial")
public class CBS1215I01DataPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CBS1215I01DataPopup.class);
	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ModuleFormHandler moduleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
		IncomeInfoDataM incomeM = (IncomeInfoDataM)moduleForm.getObjectForm();
		ArrayList<IncomeCategoryDataM> incomeTypeList = incomeM.getAllIncomes();
		if(Util.empty(incomeTypeList)) {
			incomeTypeList = new ArrayList<IncomeCategoryDataM>();
			incomeM.setAllIncomes(incomeTypeList);
		}		
		ArrayList<FixedGuaranteeDataM> fixedList = (ArrayList<FixedGuaranteeDataM>)appForm;
		String dataSeq = request.getParameter("FIXED_DATA_SEQ");
		logger.info("dataSeq : "+dataSeq);
		if(!Util.empty(dataSeq)) {
			int seq = Integer.parseInt(dataSeq);
			if(!Util.empty(fixedList)) {
				for(FixedGuaranteeDataM fixedM: fixedList) {
					if(fixedM.getSeq() == seq) {
						fixedM.setIncomeType(incomeM.getIncomeType());
						fixedM.setIncomeId(incomeM.getIncomeId());
						fixedM.setSeq(incomeTypeList.size() + 1);
						incomeTypeList.add(fixedM);
						break;
					}
				}
			}
		}		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		String dataSeq = request.getParameter("FIXED_DATA_SEQ");
		if(Util.empty(dataSeq)) {
			formError.error("FIXED_DATA_SEQ",MessageErrorUtil.getText(request,"ERROR_ID_SELECT_ATLEAST_ONE"));
		}else{			
			ModuleFormHandler moduleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = (IncomeInfoDataM)moduleForm.getObjectForm();
			ArrayList<IncomeCategoryDataM> incomeTypeList = incomeM.getAllIncomes();
			if(Util.empty(incomeTypeList)) {
				incomeTypeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(incomeTypeList);
			}			
			ArrayList<FixedGuaranteeDataM> fixedList = (ArrayList<FixedGuaranteeDataM>)appForm;
			if(!Util.empty(dataSeq)) {
				if(!Util.empty(fixedList)) {
					for(FixedGuaranteeDataM fixedM: fixedList) {
						int countExistingData = countExistingData(incomeTypeList, fixedM);
						 logger.debug("countExistingData : "+countExistingData);
						 if(countExistingData>1){
							 incomeTypeList.remove(incomeTypeList.size()- 1);
							 formError.error("FIXED_DATA_SEQ",MessageErrorUtil.getText(request,"ERROR_EXISTING_ACCOUNT_SUB"));
							 break;
						 }
					}
				}
			}
			
		}
		return formError.getFormError();
	}

	private int countExistingData(ArrayList<IncomeCategoryDataM> incomeTypeList ,FixedGuaranteeDataM fixedM ){
		if(!Util.empty(incomeTypeList) && !Util.empty(fixedM)){
			int count=0;
			for(IncomeCategoryDataM incomeTypeObj : incomeTypeList){
				FixedGuaranteeDataM fixedGaranreeDataM = (FixedGuaranteeDataM)incomeTypeObj;
				if(fixedGaranreeDataM.getAccountNo().equals(fixedM.getAccountNo()) 
				&& fixedGaranreeDataM.getRetentionAmt().compareTo(fixedM.getRetentionAmt())==0){
					count++;
					if(count>1){
						return count;
					}
				}
			}
		}
		return 0;
	}
	
	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
