package com.eaf.orig.ulo.app.view.form.displaymode;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.KYCDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
//import com.eaf.orig.ulo.constant.MConstant;

public class KYCDE2FormDisplayMode extends DE2FormDisplayMode{
	private static transient Logger logger = Logger.getLogger(KYCDE2FormDisplayMode.class);
	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		ApplicationGroupDataM applicationGroup = ORIGFormHandler.getObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		KYCDataM kyc = personalInfo.getKyc();
		if(Util.empty(kyc)){
			kyc = new KYCDataM();
		}
		String displayMode = HtmlUtil.VIEW;
		logger.debug("JobState >> "+applicationGroup.getJobState());
		if(SystemConstant.lookup("JOBSTATE_APPROVE",applicationGroup.getJobState())){
			if(!isHasDataPreviousRole(applicationGroup)){
				displayMode =  HtmlUtil.EDIT;
			}else{
				displayMode =  HtmlUtil.VIEW;
			}
		}else if(SystemConstant.lookup("JOBSTATE_REJECT",applicationGroup.getJobState())){
			displayMode =  HtmlUtil.EDIT;
		}
//		if(!Util.empty(kyc.getRelationFlag())){
//			if(MConstant.FLAG.YES.equals(kyc.getRelationFlag())){
//				displayMode =  HtmlUtil.EDIT;
//			}else{
//				displayMode =  HtmlUtil.VIEW;				
//			}
//		}
		logger.debug("objectElement : "+objectElement);
		logger.debug("displayMode : "+displayMode);
		return displayMode;
	}
}
