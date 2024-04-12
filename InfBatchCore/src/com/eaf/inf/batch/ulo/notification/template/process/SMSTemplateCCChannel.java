package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.google.gson.Gson;

public class SMSTemplateCCChannel extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(SMSTemplateCCChannel.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		//TEMPLATE {CARD_TYPE}{CUSTOMER_NAME_SURNAME}
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			TemplateDAO  dao = NotificationFactory.getTemplateDAO();
			ArrayList<RecipientInfoDataM> receipientInfos = (ArrayList<RecipientInfoDataM>)templateBuilderRequest.getRequestObject();
//			logger.debug(">>GET TEMPLATE VARIALBE<< By condition ="+ new Gson().toJson(receipientInfos));
			HashMap<String, Object> hData = dao.getSMSTemplateCCChannel(receipientInfos);
			templateVariable.setTemplateVariable(hData);
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return templateVariable;
	}
	

}
