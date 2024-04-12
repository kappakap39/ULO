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
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class chkDocTypeSPsignoffDE2DisplayMode extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(chkDocTypeSPsignoffDE2DisplayMode.class);
	String DOC_TYPE_SP_SIGN_OFF = SystemConfig.getGeneralParam("DOC_TYPE_SP_SIGN_OFF");	
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
			if(!isPrevData && isContainSpSignOffDocument(applicationGroup.getApplicationImages())){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			if(isContainSpSignOffDocument(applicationGroup.getApplicationImages())){
				displayMode =  HtmlUtil.EDIT;
			}
		}
		return displayMode;
	}
	
	private boolean isContainSpSignOffDocument(ArrayList<ApplicationImageDataM> applicationImages){
		if(null != applicationImages){
			for (ApplicationImageDataM applicationImage : applicationImages) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
					if(null != applicationImageSplits){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
						if(null != applicationImageSplit){
							if(DOC_TYPE_SP_SIGN_OFF.equals(applicationImageSplit.getDocType())){
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
							if(DOC_TYPE_SP_SIGN_OFF.equals(applicationImageSplit.getDocType())){
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
	
	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup){
		boolean isHasDataPreviousRole = false;
		String fieldNameType = fieldConfigId;
		String uniqueId = "";
		String uniqueLevel = "";
		ArrayList<CompareDataM> compares = new ArrayList<>();
		if(objectElement instanceof ApplicationGroupDataM){
			compares = CompareSensitiveFieldUtil.getPrevCompareDataMByFieldNameType(applicationGroup, fieldNameType);
			uniqueId = applicationGroup.getApplicationGroupId();
			uniqueLevel = CompareDataM.UniqueLevel.APPLICATION_GROUP;
		}else if(objectElement instanceof PersonalInfoDataM){
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectElement;
			compares = CompareSensitiveFieldUtil.getPrevCompareDataMByPersonalInfo(applicationGroup, fieldNameType, personalInfo);
			uniqueId = personalInfo.getPersonalId();
			uniqueLevel = CompareDataM.UniqueLevel.PERSONAL;
		}else if(objectElement instanceof AddressDataM){
			AddressDataM address = (AddressDataM)objectElement;
			compares = CompareSensitiveFieldUtil.getPrevCompareDataMByFieldNameType(applicationGroup, fieldNameType);
			uniqueId = address.getAddressId();
			uniqueLevel = CompareDataM.UniqueLevel.ADDRESS;
		}
		
		logger.debug("fieldNameType >> "+fieldNameType);
		logger.debug("uniqueId >> "+uniqueId);
		logger.debug("uniqueLevel >> "+uniqueLevel);
		if(!Util.empty(compares)){
			for(CompareDataM compare : compares){
				if(uniqueId.equals(compare.getUniqueId()) && uniqueLevel.equals(compare.getUniqueLevel())){
					isHasDataPreviousRole = true;
				}
			}
		}
		return isHasDataPreviousRole;
	}
	
}
