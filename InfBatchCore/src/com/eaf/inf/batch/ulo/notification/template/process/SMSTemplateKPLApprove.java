package com.eaf.inf.batch.ulo.notification.template.process;

import java.math.BigDecimal;
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
import com.eaf.core.ulo.common.display.FormatUtil;

public class SMSTemplateKPLApprove extends TemplateBuilderHelper {
	private static transient Logger logger = Logger.getLogger(SMSTemplateKPLApprove.class);

	@Override
	public TemplateVariableDataM getTemplateVariable() {
		//TEMPLATE {PRODUCT_NAME}
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			TemplateDAO  dao = NotificationFactory.getTemplateDAO();
			ArrayList<RecipientInfoDataM> receipientInfos = (ArrayList<RecipientInfoDataM>)templateBuilderRequest.getRequestObject();
//			logger.debug(">>GET TEMPLATE VARIALBE<< By condition ="+ new Gson().toJson(receipientInfos));	
			TemplateMasterDataM templateMaster = getTemplateMaster();
			if(!InfBatchUtil.empty(receipientInfos)){
				HashMap<String, Object> hData = dao.getSMSTemplateKPLApprove(receipientInfos, templateMaster);
				
				BigDecimal installmentAmt = (hData.get("INSTALLMENT") != null) ? new BigDecimal((String)hData.get("INSTALLMENT")) : BigDecimal.ZERO;
				BigDecimal transferAmt = (hData.get("TRANSFER_AMT") != null) ? new BigDecimal((String)hData.get("TRANSFER_AMT")) : BigDecimal.ZERO;
				BigDecimal creditLine = (hData.get("CREDIT_LINE") != null) ? new BigDecimal((String)hData.get("CREDIT_LINE")) : BigDecimal.ZERO;
				
				hData.put("INSTALLMENT", FormatUtil.display(installmentAmt,FormatUtil.Format.NUMBER_FORMAT));
				hData.put("TRANSFER_AMT", FormatUtil.display(transferAmt,FormatUtil.Format.NUMBER_FORMAT));
				hData.put("CREDIT_LINE", FormatUtil.display(transferAmt,FormatUtil.Format.NUMBER_FORMAT));
				
				templateVariable.setTemplateVariable(hData);
			}
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return templateVariable;
	}

	public void debug(HashMap<String, Object> hData) {
		StringBuilder s = new StringBuilder();
		for (String key:hData.keySet()) {
			s.append(key).append("=").append(hData.get(key)).append(",");
		}
		System.out.println("TemplateVariable: " + s.toString());
	}
}