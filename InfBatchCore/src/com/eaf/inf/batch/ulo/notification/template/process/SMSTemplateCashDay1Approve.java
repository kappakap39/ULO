package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationDAO;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.google.gson.Gson;

public class SMSTemplateCashDay1Approve   extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(SMSTemplateCashDay1Approve.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			NotificationDAO  dao = NotificationFactory.getNotificationDAO();
			ArrayList<RecipientInfoDataM> receipientInfos = (ArrayList<RecipientInfoDataM>)templateBuilderRequest.getRequestObject();
//			logger.debug(">>GET TEMPLATE VARIALBE<< By condition ="+ new Gson().toJson(receipientInfos));
			if(receipientInfos.size()>0){
				String applicationGroupId = receipientInfos.get(0).getApplicationGroupId();
				if(dao.isCashDay1Approve(applicationGroupId)){
					HashMap<String, Object> hData = new HashMap<>();
					templateVariable.setTemplateVariable(hData);
				}
			}
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return templateVariable;
	}
}
