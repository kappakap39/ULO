package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;

public class KccProductPopupForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(KccProductPopupForm.class);
	@Override
	public Object getObjectForm() {
	    String uniqueId = getRequestData("uniqueId");
	    logger.debug("uniqueId >> "+uniqueId);
	    ApplicationDataM application = null;
	    Object masterObjectForm = FormControl.getMasterObjectForm(request);
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			application = applicationGroup.getApplicationById(uniqueId);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			application = personalApplicationInfo.getApplicationById(uniqueId);
		}
		return application;
	}
	public String processForm() {
		String uniqueId = getRequestData("uniqueId");
		logger.debug("uniqueId >> "+uniqueId);
		logger.debug("KccProductPopupForm.processForm...");
		Object masterObjectForm = FormControl.getMasterObjectForm(request);
		ApplicationDataM application = (ApplicationDataM)objectForm;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
			int applicationIndex = applicationGroup.getApplicationIndex(uniqueId);
			try{
				applications.set(applicationIndex,application);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			ArrayList<ApplicationDataM> applications = personalApplicationInfo.getApplications();
			int applicationIndex = personalApplicationInfo.getApplicationIndex(application.getApplicationRecordId());
			try{
				applications.set(applicationIndex,application);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
		}
		return null;
	}
	

}
