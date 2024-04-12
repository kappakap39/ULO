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
import com.eaf.orig.ulo.model.app.BundleHLDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BundleHLPopupForm extends FormHelper implements FormAction{
	
	private static transient Logger logger = Logger.getLogger(BundleHLPopupForm.class);
	@Override
	public Object getObjectForm(){		
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
			logger.debug("retrived appRecordID : "+appRecordID);
		}
		ApplicationDataM appM = appGroupM.getApplicationById(appRecordID);
		if(appM != null) {
			logger.info("Retrieve Application :"+appM);
			BundleHLDataM bundleData = appM.getBundleHL();
			if(Util.empty(bundleData)) {
				logger.debug("bundleData is null");
				bundleData = new BundleHLDataM();
				bundleData.setApplicationRecordId(appM.getApplicationRecordId());
				appM.setBundleHL(bundleData);
			} 
			bundleData.setApplicationRecordId(appM.getApplicationRecordId());
		} else {
			logger.fatal("Null application for Bundling");
		}
		return appM.getBundleHL();
	}
	
	@Override
	public String processForm() {
		String INC_TYPE_BUNDLE_HL = SystemConstant.getConstant("INC_TYPE_BUNDLE_HL");
		logger.debug("processForm ...");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		BundleHLDataM bundleHLM = (BundleHLDataM)objectForm;
		logger.debug("bundle ApplicationRecordId : "+bundleHLM.getApplicationRecordId());
		//ApplicationDataM appM = ((ApplicationGroupDataM) EntityForm.getObjectForm()).getApplicationById(bundleHLM.getApplicationRecordId());
		//appM.setBundleHL(bundleHLM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		ApplicationDataM appM = applicationGroup.getApplicationById(bundleHLM.getApplicationRecordId());
		if(!Util.empty(appM)){
			appM.setBundleHL(bundleHLM);
		}
		String roleId = FormControl.getFormRoleId(request);
		if(MConstant.ROLE.DE1_2.equals(roleId)){
			bundleHLM.setCompareFlag(MConstant.FLAG.NO);
		}
		// Defect UAT : 2347, 4980
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeInfo = personalInfo.getIncomeByType(INC_TYPE_BUNDLE_HL);
		if(!Util.empty(incomeInfo)){
			incomeInfo.setCompareFlag(bundleHLM.getCompareFlag());
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(!Util.empty(application)){
					if(!bundleHLM.getApplicationRecordId().equals(application.getApplicationRecordId())){
						BundleHLDataM otherBundleHL = application.getBundleHL();
						if(null==otherBundleHL){
							otherBundleHL = new BundleHLDataM();
							otherBundleHL.setApplicationRecordId(application.getApplicationRecordId());
							application.setBundleHL(otherBundleHL);
						}
						otherBundleHL.setVerifiedIncome(bundleHLM.getVerifiedIncome());
						otherBundleHL.setApprovedDate(bundleHLM.getApprovedDate());
						otherBundleHL.setCcApprovedAmt(bundleHLM.getCcApprovedAmt());	
						otherBundleHL.setKecApprovedAmt(bundleHLM.getKecApprovedAmt());
						otherBundleHL.setMortGageDate(bundleHLM.getMortGageDate());
						otherBundleHL.setAccountNo(bundleHLM.getAccountNo());
						otherBundleHL.setAccountOpenDate(bundleHLM.getAccountOpenDate());
						otherBundleHL.setAccountName(bundleHLM.getAccountName());
						otherBundleHL.setCompareFlag(bundleHLM.getCompareFlag());
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String, ArrayList<String>> onLoadNotifyForm() {
		BundleHLDataM bundleHLM = (BundleHLDataM)objectForm;
		//String type = getRequestData("type");
		String type = getRequestData("incomeType");
		String roleId = FormControl.getFormRoleId(request);
		// Mask exisiting data from DV1 if user is DV2 and screen compare is NO
		if(bundleHLM != null && MConstant.ROLE.DV.equals(roleId) 
				&& !IncomeTypeUtility.isBundleHLComplete(bundleHLM, roleId)){
			logger.info("Masking data from DV1 ");
			ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(type);
			if(!Util.empty(compareFieldElements)) {
				for(CompareIncomeInf element: compareFieldElements) {
					element.maskField(request, bundleHLM);
				}
			}
		}
		return null;
	}
}
