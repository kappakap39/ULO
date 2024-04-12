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

public class EmailTemplateAccountApproved extends TemplateBuilderHelper {
	private static transient Logger logger = Logger.getLogger(EmailTemplateAccountApproved.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			ArrayList<RecipientInfoDataM> receipientInfos = (ArrayList<RecipientInfoDataM>)templateBuilderRequest.getRequestObject();
//			logger.debug(">>GET TEMPLATE VARIALBE<< By condition ="+ new Gson().toJson(receipientInfos));
			TemplateDAO  dao = NotificationFactory.getTemplateDAO();
			if(receipientInfos.size()>0){
				RecipientInfoDataM receipientInfo  =receipientInfos.get(0);
				HashMap<String,Object> mapData = dao.getEmailAccountApprovedValues(receipientInfo.getApplicationGroupId(),receipientInfo.getMaxLifeCycle());
				templateVariable.setTemplateVariable(mapData);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return templateVariable;
	}

}
