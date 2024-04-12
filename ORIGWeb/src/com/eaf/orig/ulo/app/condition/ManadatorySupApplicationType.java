package com.eaf.orig.ulo.app.condition;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ConditionHelper;
import com.eaf.core.ulo.common.display.ConditionInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class ManadatorySupApplicationType extends ConditionHelper{
	private static transient Logger logger = Logger.getLogger(ManadatorySupApplicationType.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public String getConditionFlag(HttpServletRequest request, Object objectForm) {
		String conditionFlag = ConditionInf.FLAG_NO;
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		Object masterObjectForm = FormControl.getMasterObjectForm(request);	
		if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			String applicationType = applicationGroup.getApplicationType();
			if(SystemConstant.lookup("MANDATORY_APPLICATION_TYPE", applicationType)){
				conditionFlag = ConditionInf.FLAG_YES;
			}
		}else if(masterObjectForm instanceof ApplicationGroupDataM){
			conditionFlag = ConditionInf.FLAG_YES;
		}
		logger.debug("conditionFlag >> "+conditionFlag);
		return conditionFlag;
	}
}
