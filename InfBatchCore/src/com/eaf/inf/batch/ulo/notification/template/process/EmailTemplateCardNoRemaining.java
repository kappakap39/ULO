package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.card.notification.model.CardNotificationDataM;

public class EmailTemplateCardNoRemaining extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(EmailTemplateCardNoRemaining.class);
	private String TH_FOR_PARAM_DESC = InfBatchProperty.getInfBatchConfig("TH_FOR_PARAM_DESC");
	private String TH_REMAINING_RUNNING_NO_DESC = InfBatchProperty.getInfBatchConfig("TH_REMAINING_RUNNING_NO_DESC");
	private String TH_NUMBER = InfBatchProperty.getInfBatchConfig("TH_NUMBER");
	private String PARAM_DESC = InfBatchProperty.getInfBatchConfig("PARAM_DESC");

	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		StringBuilder buildParamDesc = new StringBuilder("");
		try {
			logger.debug(">>Template builder card no notification>>");
			HashMap<String,Object> mapData = new HashMap<String, Object>();
			ArrayList<CardNotificationDataM> listCardNotification = (ArrayList<CardNotificationDataM>) templateBuilderRequest.getRequestObject();
			int countRunningNo = 0;
			for(CardNotificationDataM cardNotification : listCardNotification){
				if(!Util.empty(cardNotification.getCardTypeDesc())){
					if(!Util.empty(cardNotification.getResult())){
						buildParamDesc.append("<p>"+String.valueOf(++countRunningNo)+". "+TH_FOR_PARAM_DESC+" ");
						buildParamDesc.append(cardNotification.getCardTypeDesc()+" "+TH_REMAINING_RUNNING_NO_DESC+" ");
						buildParamDesc.append(cardNotification.getResult()+" "+TH_NUMBER+"</p>");
					}
				}
			}
			if(!Util.empty(buildParamDesc.toString())){
				mapData.put(PARAM_DESC, buildParamDesc.toString());
			}
			templateVariable.setTemplateVariable(mapData);
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return templateVariable;
	}		
}
