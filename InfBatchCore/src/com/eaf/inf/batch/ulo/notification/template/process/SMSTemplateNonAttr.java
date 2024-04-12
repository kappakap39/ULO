package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;


public class SMSTemplateNonAttr extends TemplateBuilderHelper{

	private static transient Logger logger = Logger.getLogger(SMSTemplateCCIncrease.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		//TEMPLATE NON ATTR
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		try {
			HashMap<String, Object> hData = new HashMap<String, Object>();
			hData.put("NON_ATTR","");
			templateVariable.setTemplateVariable(hData);
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return templateVariable;
	}
}
