package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.common.utility.IncomeTypeUtility;
import com.eaf.core.ulo.common.display.CompareIncomeInf;
import com.eaf.core.ulo.common.display.FormatUtil;
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
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class BundleSMEPopupForm extends FormHelper implements FormAction{
	
	private static transient Logger logger = Logger.getLogger(BundleSMEPopupForm.class);
	@Override
	public Object getObjectForm() {
		String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
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
			logger.info("Retrieve Application :"+appM.getBusinessClassId());
			BundleSMEDataM bundleData = appM.getBundleSME();
			if(Util.empty(bundleData)) {
				bundleData = new BundleSMEDataM();
				appM.setBundleSME(bundleData);
			}
			if(PRODUCT_CRADIT_CARD.equals(appM.getProduct())) {
				bundleData.setBorrowingType(SystemConstant.getConstant("LOAN_TYPE_SINGLE"));
				bundleData.setIndividualRatio(FormatUtil.toBigDecimal("1",true));	
			}
			else if(PRODUCT_K_EXPRESS_CASH.equals(appM.getProduct())) {
				bundleData.setApplicantQuality(SystemConstant.getConstant("HIGH_QUALITY"));
			}
			bundleData.setApplicationRecordId(appM.getApplicationRecordId());
		} else {
			logger.fatal("Null application for Bundling");
		}
		return appM.getBundleSME();
	}
	
	@Override
	public String processForm() {
		String INC_TYPE_BUNDLE_SME = SystemConstant.getConstant("INC_TYPE_BUNDLE_SME");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		BundleSMEDataM bundleSMEM = (BundleSMEDataM)objectForm;
//		ApplicationDataM appM = ((ApplicationGroupDataM) EntityForm.getObjectForm()).getApplicationById(bundleSMEM.getApplicationRecordId());
//		appM.setBundleSME(bundleSMEM);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)EntityForm.getObjectForm();
		ApplicationDataM appM = applicationGroup.getApplicationById(bundleSMEM.getApplicationRecordId());
		if(!Util.empty(appM)){
			appM.setBundleSME(bundleSMEM);
		}
		String roleId = FormControl.getFormRoleId(request);
		if(MConstant.ROLE.DE1_2.equals(roleId)){
			bundleSMEM.setCompareFlag(MConstant.FLAG.NO);
		}
		// Defect UAT : 2347, 4980
		PersonalInfoDataM personalInfo = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeInfo = personalInfo.getIncomeByType(INC_TYPE_BUNDLE_SME);
		if(!Util.empty(incomeInfo)){
			incomeInfo.setCompareFlag(bundleSMEM.getCompareFlag());
		}
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			for(ApplicationDataM application : applications){
				if(!Util.empty(application)){
					if(!bundleSMEM.getApplicationRecordId().equals(application.getApplicationRecordId())){
						BundleSMEDataM otherBundleSME = application.getBundleSME();
						if(null==otherBundleSME){
							otherBundleSME = new BundleSMEDataM();
							otherBundleSME.setApplicationRecordId(application.getApplicationRecordId());
							application.setBundleSME(otherBundleSME);
						}
						otherBundleSME.setBorrowingType(bundleSMEM.getBorrowingType());
						otherBundleSME.setApplicantQuality(bundleSMEM.getApplicantQuality());
						otherBundleSME.setBusinessCode(bundleSMEM.getBusinessCode());
						otherBundleSME.setApprovalDate(bundleSMEM.getApprovalDate());
						otherBundleSME.setApprovalLimit(bundleSMEM.getApprovalLimit());	
						otherBundleSME.setIndividualRatio(bundleSMEM.getIndividualRatio());	
						otherBundleSME.setCorporateRatio(bundleSMEM.getCorporateRatio());	
						otherBundleSME.setgTotExistPayment(bundleSMEM.getgTotExistPayment());	
						otherBundleSME.setgTotNewPayReq(bundleSMEM.getgTotNewPayReq());	
						otherBundleSME.setgDscrReq(bundleSMEM.getgDscrReq());
						otherBundleSME.setAccountName(bundleSMEM.getAccountName());
						otherBundleSME.setAccountNo(bundleSMEM.getAccountNo());
						otherBundleSME.setTypeLimit(bundleSMEM.getTypeLimit());
						otherBundleSME.setAccountDate(bundleSMEM.getAccountDate());
						otherBundleSME.setCompareFlag(bundleSMEM.getCompareFlag());
						otherBundleSME.setVerifiedIncome(bundleSMEM.getVerifiedIncome());
					}
				}
			}
		}
		return null;
	}
	
	@Override
	public HashMap<String, ArrayList<String>> onLoadNotifyForm() {
		BundleSMEDataM bundleSMEM = (BundleSMEDataM)objectForm;
		//String type = getRequestData("type");
		String type = getRequestData("incomeType");
		String roleId = FormControl.getFormRoleId(request);
		// Mask exisiting data from DV1 if user is DV2 and screen compare is NO
		if(bundleSMEM != null && MConstant.ROLE.DV.equals(roleId) 
				&& !IncomeTypeUtility.isBundleSMEComplete(bundleSMEM, roleId)){
			logger.info("Masking data from DV1 ");
			ArrayList<CompareIncomeInf> compareFieldElements = CompareIncomeControl.getFields(type);
			if(!Util.empty(compareFieldElements)) {
				for(CompareIncomeInf element: compareFieldElements) {
					element.maskField(request, bundleSMEM);
				}
			}
		}
		return null;
	}
}
