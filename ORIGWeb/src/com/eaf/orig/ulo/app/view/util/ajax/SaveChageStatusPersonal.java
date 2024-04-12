package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class SaveChageStatusPersonal implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(SaveChageStatusPersonal.class);
	private String COMPLETE_FLAG_FIRST_LOAD_PERSONAL = SystemConstant.getConstant("COMPLETE_FLAG_FIRST_LOAD_PERSONAL");
	private String LOAD_PERSONAL_COMPLETE = SystemConstant.getConstant("LOAD_PERSONAL_COMPLETE");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.SAVECHNAGESTATUSPERSONAL);
		try{
			
			Object object = FormControl.getMasterObjectForm(request);
			PersonalInfoDataM personalInfo  = null;
			if(object instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)object;
				ArrayList<PersonalInfoDataM> personals = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
				for(PersonalInfoDataM personal : personals){
					if(COMPLETE_FLAG_FIRST_LOAD_PERSONAL.equals(personal.getCompleteData())){
						personalInfo = personal;
						break;
					}
				}
			}else if(object instanceof PersonalInfoDataM){
				personalInfo = (PersonalInfoDataM)object;
			}else if(object instanceof PersonalApplicationInfoDataM){
				PersonalApplicationInfoDataM personalInfoApp = (PersonalApplicationInfoDataM)object;
				personalInfo = personalInfoApp.getPersonalInfo();
			}
				if(!Util.empty(personalInfo)){
					logger.debug("personalInfo.getCompleteData() >>"+personalInfo.getCompleteData());
					personalInfo.setCompleteData(LOAD_PERSONAL_COMPLETE);
				}
			return responseData.success();
		}catch(Exception e){
			return responseData.error(e.getMessage());
		}
	
	}

}
