package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.comparesignature.CompareSignatureDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class IADisplayObject extends FormDisplayObjectHelper {
	private static transient Logger logger = Logger.getLogger(IADisplayObject.class);
	String COMPARE_SIGNATURE_YES = SystemConstant.getConstant("COMPARE_SIGNATURE_YES");
	String COMPARE_SIGNATURE_NO = SystemConstant.getConstant("COMPARE_SIGNATURE_NO");
	@Override
	public void init(String objectId, String objectType, FormEffects formEffect) {
		// TODO Auto-generated method stub
		super.init(objectId, objectType, formEffect);
	}

	@Override
	public String getObjectFormMode(String objectId, String objectType,Object objectElement) {
		String displayMode = FormEffects.Effects.DISABLED;
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		String docCompleteFlag = applicationGroup.getDocCompletedFlag();
		CompareSignatureDataM  compareSignatureDataM = new CompareSignatureDataM();
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		String actionType = flowControl.getActionType();
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY", actionType)){
			return FormEffects.Effects.HIDE;
		}
		logger.debug("docCompleteFlag >> "+docCompleteFlag);
		if(!Util.empty(docCompleteFlag)){
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
			for (PersonalInfoDataM personalInfo : personalInfos) {
				VerificationResultDataM verificationResult = personalInfo.getVerificationResult();
				if(null != verificationResult){
					if(COMPARE_SIGNATURE_YES.equals(verificationResult.getCompareSignatureResult())){
						displayMode = FormEffects.Effects.ENABLE;
					}
				}
			}
		}
		return displayMode;
	}
	

}
