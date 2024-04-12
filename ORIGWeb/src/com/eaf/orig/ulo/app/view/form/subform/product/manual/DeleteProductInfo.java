package com.eaf.orig.ulo.app.view.form.subform.product.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class DeleteProductInfo implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteProductInfo.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String APPLICATION_CARD_TYPE_BORROWER = SystemConstant.getConstant("APPLICATION_CARD_TYPE_BORROWER");
	String COMPARE_SIGNATURE_NO = SystemConstant.getConstant("COMPARE_SIGNATURE_NO");
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_PRODUCT);
		try{
			String uniqueId = request.getParameter("uniqueId");
			logger.debug("uniqueId >> " + uniqueId);
			// This statement make sure you use correct model
			String ROLE_IA = SystemConstant.getConstant("ROLE_IA");
			ArrayList<PersonalInfoDataM> personalApplicationInfos = new ArrayList<PersonalInfoDataM>();
			ApplicationGroupDataM applicationGroupDataM = ORIGFormHandler.getObjectForm(request);
			Object masterObjectForm = FormControl.getMasterObjectForm(request);
			String roleId = FormControl.getFormRoleId(request);
			ArrayList<ApplicationDataM> applications = null;
			if (masterObjectForm instanceof ApplicationGroupDataM) {
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) masterObjectForm;
				applications = applicationGroup.getApplications();
				removeApplications(uniqueId,applications);
				personalApplicationInfos = applicationGroup.getPersonalInfos();
				ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);
				clearCompareDataByApplicationId(uniqueId,request);
			} else if (masterObjectForm instanceof PersonalApplicationInfoDataM) {
				PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM) masterObjectForm;
				applications = personalApplicationInfo.getApplications();
				removePersonalApplicationInfo(uniqueId,applications);
				ApplicationUtil.clearNotMatchApplicationRelation(personalApplicationInfo);
				personalApplicationInfos.add(personalApplicationInfo.getPersonalInfo());
				clearCompareDataByApplicationId(uniqueId,request);
			}		
			if(ROLE_IA.equals(roleId)){
				for(PersonalInfoDataM personalApplicationInfo:personalApplicationInfos){
//					ArrayList<RequiredDocDataM> requiredDocs = new ArrayList<RequiredDocDataM>();
//					VerificationResultDataM verfification = personalApplicationInfo.getVerificationResult();
//					if(Util.empty(verfification)){
//						verfification = new VerificationResultDataM();
//						personalApplicationInfo.setVerificationResult(verfification);
//					}
//					verfification.setRequiredDocs(requiredDocs);
//					verfification.setDocCompletedFlag(null);
//					verfification.setCompareSignatureResult(COMPARE_SIGNATURE_NO);
					PersonalInfoUtil.clearDocumentList(personalApplicationInfo);
				}
				applicationGroupDataM.setExecuteFlag(MConstant.FLAG.NO);
			}
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	
	public static void clearCompareDataByApplicationId(String uniqueId,HttpServletRequest request){
		logger.debug("clearCompareDataByApplicationId...");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.TWO_MAKER);
		logger.debug("comparisonFields >> "+comparisonFields);
		logger.debug("uniqueId >>"+uniqueId);
		if(null != comparisonFields){
			for(Iterator<Map.Entry<String, CompareDataM>> it = comparisonFields.entrySet().iterator(); it.hasNext();) {
			      Map.Entry<String, CompareDataM> entry = it.next();
			      CompareDataM compareData = entry.getValue();
			      String uniqueIdCompare = compareData.getUniqueId();
			      logger.debug("uniqueIdCompare >>"+uniqueIdCompare);
			      if(uniqueId.equals(uniqueIdCompare)) {
			    	  it.remove();
			      }
			}
		}
		
	}
	
	private void removePersonalApplicationInfo(String uniqueId, ArrayList<ApplicationDataM> applications) throws Exception{
		if (null != applications) {
			Iterator<ApplicationDataM> appIter = applications.iterator();
			while(appIter.hasNext()) {
				ApplicationDataM application = appIter.next();
				String product = application.getProduct();
				String applicationRecordId = application.getApplicationRecordId();
				if (PRODUCT_CRADIT_CARD.equals(product)) {
					String referApplicationRecordId = application.getMaincardRecordId();
					if (null != applicationRecordId && applicationRecordId.equals(uniqueId)) {
						appIter.remove();
					} else if (null != referApplicationRecordId && referApplicationRecordId.equals(uniqueId)) {
						appIter.remove();
					}
				} else {
					if (null != applicationRecordId && applicationRecordId.equals(uniqueId)) {
						appIter.remove();
					}
				}
			}
		}
	}
	private void removeApplications(String uniqueId, ArrayList<ApplicationDataM> applications) throws Exception{
		logger.debug("Applications size (before removed):" + applications.size());
		if (null != applications) {
			Iterator<ApplicationDataM> appIter = applications.iterator();
			while(appIter.hasNext()) {
				ApplicationDataM application = appIter.next();
				String product = application.getProduct();
				String applicationRecordId = application.getApplicationRecordId();
				if (PRODUCT_CRADIT_CARD.equals(product)) {
					String referApplicationRecordId = application.getMaincardRecordId();
					if (null != applicationRecordId && applicationRecordId.equals(uniqueId)) {
						appIter.remove();
					} else if (null != referApplicationRecordId && referApplicationRecordId.equals(uniqueId)) {
						appIter.remove();
					}
				} else {
					if (null != applicationRecordId && applicationRecordId.equals(uniqueId)) {
						appIter.remove();
					}
				}
			}
		}
	}
}
