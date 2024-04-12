package com.eaf.inf.batch.ulo.notification.template.process;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.notification.memo.model.InstructionMemoRequest;
import com.eaf.service.common.util.ServiceUtil;

public class InstructionMemoEmailTemplate extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(InstructionMemoEmailTemplate.class);
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		InstructionMemoRequest requestObject = (InstructionMemoRequest)templateBuilderRequest.getRequestObject();
		String applicationGroupId = templateBuilderRequest.getUniqueId().getId();
		logger.debug("applicationGroupId >> "+applicationGroupId);
		Map<String,Object> mapVariable = new HashMap<String, Object>();
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.CUSTOMER_NAME,ServiceUtil.displayText(requestObject.getCustomerName()));
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.IDNO, ServiceUtil.displayText(requestObject.getIdNo()));
			mapVariable.put(TemplateBuilderConstant.TemplateVariableName.PRODUCT_NAME, ServiceUtil.displayText(requestObject.getProductName()));
			templateVariable.setTemplateVariable(mapVariable);
		return templateVariable;
	}

}
