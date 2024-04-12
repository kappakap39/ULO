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
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationIncreaseDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class DeleteCardRequestInfo implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteCardRequestInfo.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_PRODUCT);
		try{
			String subFormID = request.getParameter("subformId");
			String APP_RECORD_ID = request.getParameter("APP_RECORD_ID");		
			String ROLE_IA = SystemConstant.getConstant("ROLE_IA");
			String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
			ArrayList<PersonalInfoDataM> personalApplicationInfos = new ArrayList<PersonalInfoDataM>();
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();	
			personalApplicationInfos = applicationGroup.getPersonalInfos();
			String roleId = FormControl.getFormRoleId(request);
			ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();		
			Iterator<ApplicationDataM> iterator = applications.iterator();
			if(!Util.empty(APP_RECORD_ID)){
				while(iterator.hasNext()){
					ApplicationDataM Application = iterator.next();
					if(APP_RECORD_ID.equals(Application.getApplicationRecordId())){
						iterator.remove();
					}
				}
				if(ROLE_IA.equals(roleId)){
					for(PersonalInfoDataM personalApplicationInfo:personalApplicationInfos){
						ArrayList<RequiredDocDataM> requiredDocs = new ArrayList<RequiredDocDataM>();
						if(Util.empty(personalApplicationInfo.getVerificationResult())){
							VerificationResultDataM verResult = new VerificationResultDataM();
							personalApplicationInfo.setVerificationResult(verResult);
						}
						personalApplicationInfo.getVerificationResult().setRequiredDocs(requiredDocs);
						personalApplicationInfo.getVerificationResult().setDocCompletedFlag(null);
					}
					
					ArrayList<ApplicationIncreaseDataM> applicationIncreases = applicationGroup.getApplicationIncreases();
					if(!Util.empty(applicationIncreases)){
						Iterator<ApplicationIncreaseDataM> iteratorIncease = applicationIncreases.iterator();
						while(iteratorIncease.hasNext()){
							ApplicationIncreaseDataM ApplicationIncrease = iteratorIncease.next();
							if(APP_RECORD_ID.equals(ApplicationIncrease.getApplicationRecordId())){
								iteratorIncease.remove();
							}
						}
					}
					applicationGroup.setExecuteFlag(MConstant.FLAG.NO);
				}
			}
			PersonalInfoDataM personinfo = applicationGroup.getPersonalInfoApplication(APP_RECORD_ID, PERSONAL_RELATION_APPLICATION_LEVEL);
			if(!Util.empty(personinfo)){
				clearCompareDataCardLinkByPersonalId(personinfo.getPersonalId(),personinfo,request);
			}
			ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);
			return responseData.success(subFormID);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	
	public static void clearCompareDataCardLinkByPersonalId(String personalId,PersonalInfoDataM personalInfo,HttpServletRequest request){
		logger.debug("clearCompareDataCardLinkByPersonalId...");
		logger.debug("personalId >>"+personalId);	
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CARD_LINK);
		logger.debug("comparisonFields >> "+comparisonFields);
		if(null != comparisonFields){
			for(Iterator<Map.Entry<String, CompareDataM>> it = comparisonFields.entrySet().iterator(); it.hasNext();) {
			      Map.Entry<String, CompareDataM> entry = it.next();
			      CompareDataM compareData = entry.getValue();
			      String uniqueId = compareData.getUniqueId();
			      if(personalId.equals(uniqueId)) {
			    	  it.remove();
			      }
			}
		}
		
	}
	
}
