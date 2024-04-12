package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.properties.CompareIncomeControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormNotifyUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DebtInfoDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PreviousIncomeDataM;

public class IncomeCategoryPopupForm extends FormHelper implements FormAction{
	
	private static transient Logger logger = Logger.getLogger(IncomeCategoryPopupForm.class);
	@Override
	public Object getObjectForm() {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup=(ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<IncomeInfoDataM> incomeList = personal.getIncomeInfos();
		if(Util.empty(incomeList)) {
			incomeList = new ArrayList<IncomeInfoDataM>();
			personal.setIncomeInfos(incomeList);
		}
		String incomeID = getRequestData("incomeID");
		String incomeType = getRequestData("incomeType");
		logger.info("Income ID :"+incomeID);
		logger.info("Income Type :"+incomeType);
		logger.info("rowId :"+rowId);
		IncomeInfoDataM incomeM = null;
		if("0".equals(incomeID)) {
			incomeM = new IncomeInfoDataM();
			incomeM.setIncomeType(incomeType);
			incomeM.setSeq(incomeList.size()+1);
		} else {
			int seq = FormatUtil.getInt(incomeID);
			incomeM = personal.getIncomeBySeq(seq);
		}
		if(incomeM != null) {
			logger.info("Retrieve Income ID :"+incomeM);
			ArrayList<IncomeCategoryDataM> incomeTypeList = incomeM.getAllIncomes();
			if(Util.empty(incomeTypeList)) {
				incomeTypeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(incomeTypeList);
				String className = IncomeTypeUtility.getClassNameByIncomeType(incomeType);
				try {
					IncomeCategoryDataM incomeCategoryM = (IncomeCategoryDataM)(Class.forName(className)).newInstance();
					incomeCategoryM.setIncomeId(incomeM.getIncomeId());
					incomeCategoryM.setSeq(incomeTypeList.size()+1);
					incomeCategoryM.setIncomeType(incomeType);
					incomeTypeList.add(incomeCategoryM);
				} catch (IllegalAccessException | InstantiationException
						| ClassNotFoundException e) {
					logger.fatal("Not class defined for incomeType: "+incomeType+" and className :"+className);
				}
			}
		}
		ArrayList<DebtInfoDataM> debtList = personal.getDebtInfos();
		if(!Util.empty(debtList)) {
			for(DebtInfoDataM debtM: debtList) {
				logger.debug("debtM:"+debtM.getDebtAmt());
				logger.debug("debtM:"+debtM.getDebtType());
			}
		}
		
		return incomeM;
	}
	
	@Override
	public String processForm() {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		 ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeM = (IncomeInfoDataM)objectForm;
		String roleId = FormControl.getFormRoleId(request);
		if(MConstant.ROLE.DE1_2.equals(roleId)){
			incomeM.setCompareFlag(MConstant.FLAG.NO);
		}
		IncomeInfoDataM incomeOrig = personal.getIncomeBySeq(incomeM.getSeq());
		ArrayList<IncomeInfoDataM> incomeInfos = personal.getIncomeInfos();
		if(Util.empty(incomeOrig)) {
			incomeInfos.add(incomeM);
		} else {
			incomeInfos.set(incomeM.getSeq() - 1, incomeM);
		}
		return null;
	}
	@Override
	public HashMap<String, ArrayList<String>> onLoadNotifyForm() {
		FormNotifyUtil notifyUtil = new FormNotifyUtil();
		String incomeType = getRequestData("incomeType");
		logger.info("onLoadNotifyForm ::: Income Type :"+incomeType);
		String INC_TYPE_PREVIOUS_INCOME = SystemConstant.getConstant("INC_TYPE_PREVIOUS_INCOME");
		String INC_TYPE_PREVIOUS_INCOME2 = SystemConstant.getConstant("INC_TYPE_PREVIOUS_INCOME2");
		IncomeInfoDataM incomeM = (IncomeInfoDataM)objectForm;
		if(INC_TYPE_PREVIOUS_INCOME.equals(incomeType) || INC_TYPE_PREVIOUS_INCOME2.equals(incomeType) ) {
			if(incomeM != null) {
				logger.info("onLoadNotifyForm ::: Retrieve Income ID :"+incomeM);
				ArrayList<IncomeCategoryDataM> incomeTypeList = incomeM.getAllIncomes();
				if(incomeTypeList.size() > 0){
					PreviousIncomeDataM previousIncomeM = (PreviousIncomeDataM)incomeTypeList.get(0);
					if(Util.empty(previousIncomeM.getIncomeDate()) || 
							Util.empty(previousIncomeM.getProduct())) {
								notifyUtil.warning(MessageUtil.getText(request, "PREV_INCOME_WARNING"));
							}
				} 
			}
		}		
		String roleId = FormControl.getFormRoleId(request);
		// Mask exisiting data from DV1 if user is DV2 and screen compare is NO
		if(incomeM != null && MConstant.ROLE.DV.equals(roleId) 
				&& !IncomeTypeUtility.isIncomeComplete(incomeM,roleId)){
			logger.info("Masking data from DV1 ");
			ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(incomeType);
			if(!Util.empty(compareFieldElements)) {
				for(CompareIncomeInf element: compareFieldElements) {
					element.maskField(request, incomeM);
				}
			}
		}
		
		if(MConstant.ROLE.DV.equals(roleId)) {
			EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
			ApplicationGroupDataM applicationGroupM = (ApplicationGroupDataM) EntityForm.getObjectForm();
			PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroupM);
			ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(MConstant.COMPARE_SCREEN_TYPE.DEBT_INFO);
			if(!Util.empty(compareFieldElements)) {
				for(CompareIncomeInf element: compareFieldElements) {
					element.maskField(request, personalInfo);
				}
			}
		}
		
		return notifyUtil.getFormNotification();
	}
}
