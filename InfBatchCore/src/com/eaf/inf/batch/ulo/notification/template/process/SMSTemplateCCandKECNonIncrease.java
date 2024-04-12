package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.google.gson.Gson;

public class SMSTemplateCCandKECNonIncrease extends TemplateBuilderHelper{

	private static transient Logger logger = Logger.getLogger(SMSTemplateCCandKECNonIncrease.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		//TEMPLATE {CARD_TYPE}
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			
			TemplateDAO  dao = NotificationFactory.getTemplateDAO();
			ArrayList<RecipientInfoDataM> receipientInfos = (ArrayList<RecipientInfoDataM>)templateBuilderRequest.getRequestObject();
			TemplateMasterDataM templateMaster = getTemplateMaster();
//			logger.debug(">>GET TEMPLATE VARIALBE<< By condition ="+ new Gson().toJson(receipientInfos));
			HashMap<String, Object> hData = dao.getSMSTemplateCCandKECNonIncrease(receipientInfos,templateMaster);
			templateVariable.setTemplateVariable(hData);
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return templateVariable;
	}

}
