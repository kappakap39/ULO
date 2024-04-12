package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;

public class chkDocTypePolicyDE2DisplayMode extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(chkDocTypePolicyDE2DisplayMode.class);
	String DOC_TYPE_POLICY_EXCEPTION = SystemConfig.getGeneralParam("DOC_TYPE_POLICY_EXCEPTION");
	@Override
	public boolean invokeDisplayMode() {
		return true;
	}
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request){	
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> "+applicationGroup.getJobState());
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			boolean isPrevData = CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldConfigId,applicationGroup);
			if(!isPrevData && isContainDocPolicyExceptionSignOff(applicationGroup.getApplicationImages())){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			if(isContainDocPolicyExceptionSignOff(applicationGroup.getApplicationImages())){
				displayMode =  HtmlUtil.EDIT;
			}
		}
		return displayMode;
	}
	
	private boolean isContainDocPolicyExceptionSignOff(ArrayList<ApplicationImageDataM> applicationImages){
		if(null != applicationImages){
			for (ApplicationImageDataM applicationImage : applicationImages) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
					if(null != applicationImageSplits){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
						if(null != applicationImageSplit){
							if(DOC_TYPE_POLICY_EXCEPTION.equals(applicationImageSplit.getDocType())){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
		
	}
	
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		Object masterObjectForm = FormControl.getMasterObjectForm(request);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
		ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
		if(null != applicationImages){
			for (ApplicationImageDataM applicationImage : applicationGroup.getApplicationImages()) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
					if(null != applicationImageSplits){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
						if(null != applicationImageSplit){
							if(DOC_TYPE_POLICY_EXCEPTION.equals(applicationImageSplit.getDocType())){
								return ValidateFormInf.VALIDATE_SUBMIT;
							}
						}
					}
				}
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
}
