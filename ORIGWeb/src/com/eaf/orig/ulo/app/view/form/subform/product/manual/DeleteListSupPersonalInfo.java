
package com.eaf.orig.ulo.app.view.form.subform.product.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.FieldConfigDataM;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalRelationDataM;
import com.eaf.orig.ulo.model.app.SpecialAdditionalServiceDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
//import com.eaf.orig.ulo.model.app.ApplicationDataM;

public class DeleteListSupPersonalInfo implements AjaxInf {
//	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String COMPLETE_FLAG_FIRST_LOAD_PERSONAL = SystemConstant.getConstant("COMPLETE_FLAG_FIRST_LOAD_PERSONAL");
	private static transient Logger logger = Logger.getLogger(DeleteListSupPersonalInfo.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_SUPPLEMENTARY_PERSONAL);
		try{
			String subformId = request.getParameter("subformId");
			String PERSONAL_ID = request.getParameter("PERSONAL_ID");
			String ROLE_IA = SystemConstant.getConstant("ROLE_IA");
			logger.debug("subformId >> "+subformId);
			logger.debug("PERSONAL_ID >> "+PERSONAL_ID);
			ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
			FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
			String roleId = flowControl.getRole();
			String persosnalId  = "";
			String uniqueRemoveId = "";
			boolean roleFlag = true;
	//		logger.debug("PERSONAL_TYPE_SUPPLEMENTARY >> "+PERSONAL_TYPE_SUPPLEMENTARY);	
			ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos();
			if(null != personals){
				for (Iterator<PersonalInfoDataM> iterator = personals.iterator(); iterator.hasNext();) {
					PersonalInfoDataM personal = iterator.next();
					if(null != PERSONAL_ID && PERSONAL_ID.equals(personal.getPersonalId())){
	//					ApplicationDataM appRemove = applicationGroup.getApplication(PERSONAL_ID, personal.getPersonalType(), APPLICATION_LEVEL);
	//					if(null != appRemove && null != applicationGroup.getApplications()){
	//						applicationGroup.getApplications().remove(appRemove);
	//					}
						PersonalInfoUtil.clearCompareDataByPersonalId(PERSONAL_ID,personal,request);
						PersonalInfoUtil.clearCompareDataCISByPersonalId(PERSONAL_ID,personal,request);
						if(!ROLE_IA.equals(roleId)){
							personal.clearValue();
							personal.setCompleteData(COMPLETE_FLAG_FIRST_LOAD_PERSONAL);
							persosnalId = personal.getPersonalId();
							uniqueRemoveId = CompareSensitiveFieldUtil.generateCISUniqueObjectId(personal);
						}else{
							roleFlag = false;
							iterator.remove();
						}	
					}
				}
			}
			logger.debug("roleId : "+roleId);
			logger.debug("roleFlag : "+roleFlag);
			if(ROLE_IA.equals(roleId)){
				applicationGroup.setExecuteFlag(null);
				clearDocumentListAllPerson(applicationGroup);
			}
			if(roleFlag){
				clearApplicationRelationByPersonalId(persosnalId,applicationGroup);
				clearNotMatchSoruceOfDataCisByPersonalId(uniqueRemoveId,request);
			}else{
				ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);
				PersonalInfoUtil.clearNotMatchSoruceOfDataCis(request);
			}
			return responseData.success(subformId);
			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	
	
	
	
	private static void clearApplicationRelationByPersonalId(String personalRemoveId,ApplicationGroupDataM applicationGroup){
		String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
		String applicationType = applicationGroup.getApplicationType();
		ArrayList<String> personalIds = applicationGroup.getPersonalIds();
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		for (Iterator<ApplicationDataM> iterator = applications.iterator(); iterator.hasNext();) {
			ApplicationDataM application = iterator.next();
			String applicationRecordId = application.getApplicationRecordId();
			PersonalRelationDataM personalRelation =  applicationGroup.getPersonalRelationByRefId(applicationRecordId);
			if(null != personalRelation){
				String personalId = personalRelation.getPersonalId();
				if(personalRemoveId.equals(personalId)){
					iterator.remove();
				}
			}else{
				iterator.remove();
			}			
		}		
		ArrayList<String> applicationIds = applicationGroup.getApplicationIds();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null != personalInfos){
			for (PersonalInfoDataM personalInfo : personalInfos) {
				ArrayList<PersonalRelationDataM> personalRelations = personalInfo.getPersonalRelations();
				if(null != personalRelations){
					for (Iterator<PersonalRelationDataM> iterator = personalRelations.iterator(); iterator.hasNext();) {
						PersonalRelationDataM personalRelation = iterator.next();
						String applicationRecordId = personalRelation.getRefId();
						if(!applicationIds.contains(applicationRecordId)){
							iterator.remove();
						}
					}
				}
			}
		}
		
		if(APPLICATION_TYPE_ADD_SUP.equals(applicationType)){
			ArrayList<PaymentMethodDataM> paymentMethods = applicationGroup.getPaymentMethods();
			if(null != paymentMethods){
				for (Iterator<PaymentMethodDataM> iterator = paymentMethods.iterator(); iterator.hasNext();) {
					PaymentMethodDataM paymentMethod = iterator.next();
					if(personalRemoveId.equals(paymentMethod.getPersonalId())){
						iterator.remove();
					}
				}
			}
			ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = applicationGroup.getSpecialAdditionalServices();
			if(null != specialAdditionalServices){
				for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
					SpecialAdditionalServiceDataM specialAdditionalService = iterator.next();	
					if(personalRemoveId.equals(specialAdditionalService.getPersonalId())){
						iterator.remove();
					}
				}
			}
		}else{
			ArrayList<String> paymentMethodIds = applicationGroup.getPaymentMethodIds();
			ArrayList<PaymentMethodDataM> paymentMethods = applicationGroup.getPaymentMethods();
			if(null != paymentMethods){
				for (Iterator<PaymentMethodDataM> iterator = paymentMethods.iterator(); iterator.hasNext();) {
					PaymentMethodDataM paymentMethod = iterator.next();
					if(Util.empty(paymentMethodIds)){
						iterator.remove();
					}else{
						if(!paymentMethodIds.contains(paymentMethod.getPaymentMethodId())){
							iterator.remove();
						}
					}
				}
			}
			ArrayList<String> specialAdditionalServiceIds = applicationGroup.getSpecialAdditionalServiceIds();
			ArrayList<SpecialAdditionalServiceDataM> specialAdditionalServices = applicationGroup.getSpecialAdditionalServices();
			if(null != specialAdditionalServices){
				for (Iterator<SpecialAdditionalServiceDataM> iterator = specialAdditionalServices.iterator(); iterator.hasNext();) {
					SpecialAdditionalServiceDataM specialAdditionalService = iterator.next();
					if(Util.empty(specialAdditionalServiceIds)){
						iterator.remove();
					}else{
						if(!specialAdditionalServiceIds.contains(specialAdditionalService.getServiceId())){
							iterator.remove();
						}
					}
				}
			}
		}
	
	}
	private static void clearNotMatchSoruceOfDataCisByPersonalId(String uniqRemoveId,HttpServletRequest request){
		logger.debug("clearNotMatchSoruceOfDataCis..");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		logger.debug("uniqRemoveId >> "+uniqRemoveId);
		HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
		logger.debug("comparisonFields >> "+comparisonFields);
		if(null != comparisonFields){
			for(Iterator<Map.Entry<String, CompareDataM>> it = comparisonFields.entrySet().iterator(); it.hasNext();) {
			      Map.Entry<String, CompareDataM> entry = it.next();
			      CompareDataM compareData = entry.getValue();
			      FieldConfigDataM filedConfigData = CompareSensitiveFieldUtil.JSonStringToFieldConfigDataM(compareData.getConfigData());
			      String uniqueId = CompareSensitiveFieldUtil.generateCISUniqueObjectId(filedConfigData);
			      if(uniqRemoveId.equals(uniqueId)) {
			    	  it.remove();
			      }
			}
		}
	
	}
	private void clearDocumentListAllPerson(ApplicationGroupDataM applicationGroup){
		ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos();
		if(!Util.empty(personals)){
			for(PersonalInfoDataM personal : personals){
				if(!Util.empty(personal)){
					PersonalInfoUtil.clearDocumentList(personal);
				}
			}
		}
	}
}
