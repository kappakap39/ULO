package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayModeHelper;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DE2AssetDisplayMode extends FormDisplayModeHelper{
	private static transient Logger logger = Logger.getLogger(DE2AssetDisplayMode.class);
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> "+applicationGroup.getJobState());
		logger.debug("fieldConfigId >> "+fieldConfigId);
		return displayMode;
	}
	
	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup){	
		 
		
		/*boolean isHasDataPreviousRole = false;
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
		}*/
		return CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldConfigId,objectElement);
	}
}
