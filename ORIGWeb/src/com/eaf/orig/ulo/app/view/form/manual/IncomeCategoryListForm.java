package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.properties.CompareIncomeControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.MonthlyTawi50DataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.YearlyTawi50DataM;

public class IncomeCategoryListForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(IncomeCategoryListForm.class);
	@Override
	public Object getObjectForm() {
		ArrayList<String> INCOME_TYPE_EXCEPTION = SystemConfig.getArrayListGeneralParam("INCOME_TYPE_EXCEPTION");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		 ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<IncomeInfoDataM> incomeList = personalInfo.getIncomeInfos();
		boolean containIncomeTypeException =personalInfo.isContainIncomeTypeException(INCOME_TYPE_EXCEPTION);
		if(Util.empty(incomeList)) {
			incomeList = new ArrayList<IncomeInfoDataM>();
			personalInfo.setIncomeInfos(incomeList);
		}
		request.getSession().removeAttribute("chk50Tawi_YEAR");
		request.getSession().removeAttribute("chk50Tawi_MON");
		String incomeID = getRequestData("incomeID");
		String incomeType = getRequestData("incomeType");
		logger.info("Income ID :"+incomeID);
		logger.info("Income Type :"+incomeType);
		IncomeInfoDataM incomeM = personalInfo.getIncomeByType(incomeType);;
		if("0".equals(incomeID)) {
			incomeM = new IncomeInfoDataM();
			incomeM.setIncomeType(incomeType);			
			incomeM.setSeq(containIncomeTypeException?incomeList.size():incomeList.size()+1);
		} /*else {
			int seq = FormatUtil.getInt(incomeID);
			incomeM = personalInfo.getIncomeBySeq(seq);
		}*/
		
		if(incomeM != null) {
			logger.info("Retrieve Income ID :"+incomeM);
			ArrayList<IncomeCategoryDataM> allIncomeList = incomeM.getAllIncomes();
			if(allIncomeList == null) {
				allIncomeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(allIncomeList);
			}
			incomeM.setAllIncomes(new ArrayList<>(allIncomeList));
		}
		return incomeM;
	}
	@Override
	public String processForm() {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup =(ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeM = (IncomeInfoDataM)objectForm;
		String roleId = FormControl.getFormRoleId(request);
		if(MConstant.ROLE.DE1_2.equals(roleId)){
			incomeM.setCompareFlag(MConstant.FLAG.NO);
		}
		logger.debug("incomeM.getIncomeType()>>"+incomeM.getIncomeType());
		ArrayList<IncomeCategoryDataM> allIncomeList = incomeM.getAllIncomes();
		ArrayList<IncomeInfoDataM> incomeInfos = personal.getIncomeInfos();
		IncomeInfoDataM incomeOrig = personal.getIncomeBySeq(incomeM.getSeq());
		//IncomeInfoDataM incomeOrig = personal.getIncomeByType(incomeM.getIncomeType());
		if(!Util.empty(allIncomeList)) {
			if(Util.empty(incomeOrig)) {
				incomeInfos.add(incomeM);
			} else {
				incomeInfos.set(incomeM.getSeq() - 1, incomeM);
			}
		}else{
			if(Util.empty(incomeOrig)) {
				incomeInfos.add(incomeM);
			} else {
				incomeInfos.set(incomeM.getSeq() - 1, incomeM);
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String, ArrayList<String>> onLoadNotifyForm() {
		IncomeInfoDataM incomeM = (IncomeInfoDataM)objectForm;
		String incomeType = getRequestData("incomeType");
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
		
		//For Yearly and Monthly checkbox
		String INC_TYPE_YEARLY_50TAWI = SystemConstant.getConstant("INC_TYPE_YEARLY_50TAWI");
		String INC_TYPE_MONTHLY_50TAWI = SystemConstant.getConstant("INC_TYPE_MONTHLY_50TAWI");
		String chk50Tawi_YEAR = null;
		String chk50Tawi_MON = null;
		if(INC_TYPE_YEARLY_50TAWI.equals(incomeType) || INC_TYPE_MONTHLY_50TAWI.equals(incomeType)) {
			ArrayList<IncomeCategoryDataM> allIncomeList = incomeM.getAllIncomes();
			if(!Util.empty(allIncomeList)) {
				for(IncomeCategoryDataM incomeObj : allIncomeList) {
					if(incomeObj instanceof YearlyTawi50DataM) {
						chk50Tawi_YEAR = INC_TYPE_YEARLY_50TAWI;
					}
					if(incomeObj instanceof MonthlyTawi50DataM) {
						chk50Tawi_MON = INC_TYPE_MONTHLY_50TAWI;
					}
				}
			}
			request.getSession().setAttribute("chk50Tawi_YEAR", chk50Tawi_YEAR);
			request.getSession().setAttribute("chk50Tawi_MON", chk50Tawi_MON);
		}
		return null;
	}
}
