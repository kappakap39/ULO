package com.eaf.orig.ulo.app.condition;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ConditionHelper;
import com.eaf.core.ulo.common.display.ConditionInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class ManadatoryApplicationType extends ConditionHelper{
	private static transient Logger logger = Logger.getLogger(ManadatoryApplicationType.class);
	@Override
	public String getConditionFlag(HttpServletRequest request, Object objectForm) {
		String conditionFlag = ConditionInf.FLAG_NO;
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		String applicationType = applicationGroup.getApplicationType();
		if(SystemConstant.lookup("MANDATORY_APPLICATION_TYPE", applicationType)){
			conditionFlag = ConditionInf.FLAG_YES;
		}
		logger.debug("conditionFlag >> "+conditionFlag);
		return conditionFlag;
	}
}
