package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;
import com.google.gson.Gson;

public class SMSTemplateKECCashTransfer extends TemplateBuilderHelper{
	
	private static transient Logger logger = Logger.getLogger(SMSTemplateKECCashTransfer.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		// TODO Auto-generated method stub
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			TemplateDAO  dao = NotificationFactory.getTemplateDAO();
			ArrayList<RecipientInfoDataM> receipientInfos = (ArrayList<RecipientInfoDataM>)templateBuilderRequest.getRequestObject();
			TemplateMasterDataM templateMaster = getTemplateMaster();
//			logger.debug(">>GET TEMPLATE VARIALBE<< By condition ="+ new Gson().toJson(receipientInfos));
			if(!InfBatchUtil.empty(receipientInfos)){
				HashMap<String, Object> hData = dao.getSMSTemplateKECCashTranfer(receipientInfos,templateMaster);
				templateVariable.setTemplateVariable(hData);
			}
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return templateVariable;
	}

}
