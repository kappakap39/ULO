package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.condition.SendToCompliance;
import com.eaf.inf.batch.ulo.notification.dao.NotificationFactory;
import com.eaf.inf.batch.ulo.notification.dao.TemplateDAO;
import com.eaf.inf.batch.ulo.notification.model.RecipientInfoDataM;

public class EmailTemplateSanctionListRejected extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(EmailTemplateSanctionListRejected.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() throws Exception {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		TemplateDAO  templateDAO = NotificationFactory.getTemplateDAO();
		ArrayList<RecipientInfoDataM> receipientInfos = (ArrayList<RecipientInfoDataM>)templateBuilderRequest.getRequestObject();
		if(!InfBatchUtil.empty(receipientInfos)){
			RecipientInfoDataM  recipientInfo = receipientInfos.get(0);
//			HashMap<String, Object> templateVariableHash  = new HashMap<String, Object>();
//			SendToCompliance sendToCompliance = new SendToCompliance();
//			if(sendToCompliance.foundSanctionList(recipientInfo.getApplicationGroupId())){
//				templateVariableHash = templateDAO.findRejectedVariable(recipientInfo.getApplicationGroupId(),recipientInfo.getMaxLifeCycle());
//				if(null==templateVariableHash){
//					templateVariableHash = new HashMap<String, Object>();
//				}
//			}
//			templateVariable.setTemplateVariable(templateVariableHash);
			HashMap<String, Object> templateVariableHash = templateDAO.findRejectedVariable(recipientInfo.getApplicationGroupId(),recipientInfo.getMaxLifeCycle());
			if(null==templateVariableHash){
				templateVariableHash = new HashMap<String, Object>();
			}
			templateVariable.setTemplateVariable(templateVariableHash);
		}
		return templateVariable;
	}
}