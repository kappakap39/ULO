package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.properties.CompareIncomeControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleKLDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BundleKLPopupForm extends FormHelper implements FormAction{
	
	private static transient Logger logger = Logger.getLogger(BundleKLPopupForm.class);
	@Override
	public Object getObjectForm() {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM appGroupM = (ApplicationGroupDataM) EntityForm.getObjectForm();
		ArrayList<ApplicationDataM> appList = appGroupM.filterApplicationLifeCycle();
		if(appList == null ) {
			appList = new ArrayList<ApplicationDataM>();
			appGroupM.setApplications(appList);
		}
		String appRecordID = getRequestData("appRecordID");
		//String type = getRequestData("type");
		String type = getRequestData("incomeType");
		logger.info("appRecordID :"+appRecordID);
		logger.info("Type :"+type);
		logger.info("rowId :"+rowId);
		//Add code for testing
		if(Util.empty(appRecordID)) {
			logger.info("empty appRecordID ");
			if(Util.empty(appList)) {
				logger.info("empty appList ");
				ApplicationDataM app = new ApplicationDataM();
				app.setApplicationRecordId("0000");
				appList.add(app);
			}
			appRecordID = appList.get(0).getApplicationRecordId();
		}
		ApplicationDataM appM = appGroupM.getApplicationById(appRecordID);
		if(appM != null) {
			logger.info("Retrieve Application :"+appM);
			BundleKLDataM bundleData = appM.getBundleKL();
			if(Util.empty(bundleData)) {
				bundleData = new BundleKLDataM();
				bundleData.setApplicationRecordId(appM.getApplicationRecordId());
				appM.setBundleKL(bundleData);
			}
			bundleData.setApplicationRecordId(appM.getApplicationRecordId());
		}else{
			logger.fatal("Null application for Bundling");
		}
		return appM.getBundleKL();
	}
	
	@Override
	public String processForm() {
		String INC_TYPE_BUNDLE_KL = SystemConstant.getConstant("INC_TYPE_BUNDLE_KL");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		BundleKLDataM bundleKLM = (BundleKLDataM)objectForm;
//		ApplicationDataM appM = ((ApplicationGroupDataM) EntityForm.getObjectForm()).getApplicationById(bundleKLM.getApplicationRecordId());
//		appM.setBundleKL(bundleKLM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		ApplicationDataM appM = applicationGroup.getApplicationById(bundleKLM.getApplicationRecordId());
		if(!Util.empty(appM)){
			appM.setBundleKL(bundleKLM);
		}
		String roleId = FormControl.getFormRoleId(request);
		if(MConstant.ROLE.DE1_2.equals(roleId)){
			bundleKLM.setCompareFlag(MConstant.FLAG.NO);
		}
		// Defect UAT : 2347, 4980
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeInfo = personalInfo.getIncomeByType(INC_TYPE_BUNDLE_KL);
		if(!Util.empty(incomeInfo)){
			incomeInfo.setCompareFlag(bundleKLM.getCompareFlag());
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(!Util.empty(application)){
					if(!bundleKLM.getApplicationRecordId().equals(application.getApplicationRecordId())){
						BundleKLDataM otherBundleKL = application.getBundleKL();
						if(null==otherBundleKL){
							otherBundleKL = new BundleKLDataM();
							otherBundleKL.setApplicationRecordId(application.getApplicationRecordId());
							application.setBundleKL(otherBundleKL);
						}
						otherBundleKL.setVerifiedIncome(bundleKLM.getVerifiedIncome());
						otherBundleKL.setVerifiedDate(bundleKLM.getVerifiedDate());
						otherBundleKL.setEstimated_income(bundleKLM.getEstimated_income());
						otherBundleKL.setCompareFlag(bundleKLM.getCompareFlag());
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String, ArrayList<String>> onLoadNotifyForm() {		
		BundleKLDataM bundleKLM = (BundleKLDataM)objectForm;
		//String type = getRequestData("type");
		String type = getRequestData("incomeType");
		String roleId = FormControl.getFormRoleId(request);
		// Mask exisiting data from DV1 if user is DV2 and screen compare is NO
		if(bundleKLM != null && MConstant.ROLE.DV.equals(roleId) 
				&& !IncomeTypeUtility.isBundleKLComplete(bundleKLM, roleId)){
			logger.info("Masking data from DV1 ");
			ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(type);
			if(!Util.empty(compareFieldElements)) {
				for(CompareIncomeInf element: compareFieldElements) {
					element.maskField(request, bundleKLM);
				}
			}
		}
		return null;
	}
}
