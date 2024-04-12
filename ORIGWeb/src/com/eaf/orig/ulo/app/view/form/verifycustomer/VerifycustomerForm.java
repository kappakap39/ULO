package com.eaf.orig.ulo.app.view.form.verifycustomer;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;

public class VerifycustomerForm  extends FormHelper implements FormAction  {
	private static transient Logger logger = Logger.getLogger(VerifycustomerForm.class);
	@Override
	public String processForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ORIGForm.setObjectForm((ApplicationGroupDataM)objectForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		return null;
	}
	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		logger.debug("applicationGroup >>> "+applicationGroup);	
		return applicationGroup;
	}
	
	@Override
	public HashMap<String, ArrayList<String>> onLoadNotifyForm() {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		PersonalInfoDataM personalInfo =PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
		verificationResult.setFicoFlag(MConstant.FLAG.NO);
		return null;
	}
	
	@Override
	public ArrayList<ORIGSubForm> filterSubform(HttpServletRequest request,ArrayList<ORIGSubForm> subforms, Object objectForm) {
		logger.debug("filterSubform...");
		ArrayList<ORIGSubForm> filterSubforms = new ArrayList<ORIGSubForm>();
		if(!Util.empty(subforms)){
			for (ORIGSubForm origSubForm : subforms) {
				String subformId = origSubForm.getSubFormID();
				String renderSubformFlag = origSubForm.renderSubformFlag(request,objectForm);
				logger.debug("[subformId] "+subformId+" renderSubformFlag >> "+renderSubformFlag);
				if(MConstant.FLAG.YES.equals(renderSubformFlag)){
					filterSubforms.add(origSubForm);
				}
			}
		}
		return filterSubforms;
	}
}
