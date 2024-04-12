package com.eaf.inf.batch.ulo.card.stack.notification;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.card.stack.notification.model.RunningParamStackInfoM;

public class EmailTemplateInfBatchCardStack extends TemplateBuilderHelper {
	private static transient Logger logger = Logger.getLogger(EmailTemplateInfBatchCardStack.class);

	private String TOTAL_CURRENT_CARD = InfBatchProperty.getInfBatchConfig("TOTAL_CURRENT_CARD");

	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			logger.debug(">>Template builder EmailTemplateInfBatchCardStack>>");

			HashMap<String, Object> mapData = new HashMap<String, Object>();
			RunningParamStackInfoM runningParamStackInfoM = (RunningParamStackInfoM) templateBuilderRequest.getRequestObject();

			mapData.put(TOTAL_CURRENT_CARD, String.valueOf(runningParamStackInfoM.getNumOfParamType()));

			templateVariable.setTemplateVariable(mapData);
		} catch (Exception e) {
			logger.fatal("ERROR", e);
		}
		return templateVariable;
	}
}
