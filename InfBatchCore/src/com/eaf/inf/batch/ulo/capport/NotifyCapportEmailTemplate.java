package com.eaf.inf.batch.ulo.capport;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.service.common.util.ServiceUtil;

public class NotifyCapportEmailTemplate extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(NotifyCapportEmailTemplate.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		NotifyCapportRequest requestObject = (NotifyCapportRequest)templateBuilderRequest.getRequestObject();
		String uniqueId = templateBuilderRequest.getUniqueId().getId();
		logger.debug("uniqueId >> "+uniqueId);
		Map<String,Object> mapVariable = new HashMap<String, Object>();
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.CAP_PORT_NAME,ServiceUtil.displayText(requestObject.getCapportName()));
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.CAP_AMOUNT,requestObject.getCapAmount());
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.GRANTED_AMOUNT,requestObject.getGrantedAmount());
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.CAP_APPLICATION,requestObject.getCapApplication());
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.GRANTED_APPLICATION,requestObject.getGrantedApplication());
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.WARNING_POINT,requestObject.getWarningPoint());

			
			templateVariable.setTemplateVariable(mapVariable);
		return templateVariable;
	}

}
