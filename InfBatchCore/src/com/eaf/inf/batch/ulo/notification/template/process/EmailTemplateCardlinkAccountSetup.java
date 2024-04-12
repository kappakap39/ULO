package com.eaf.inf.batch.ulo.notification.template.process;

import java.io.File;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.model.GenerateFileDataM;
import com.eaf.core.ulo.service.template.inf.TemplateBuilderHelper;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;

public class EmailTemplateCardlinkAccountSetup extends TemplateBuilderHelper{
	private static transient Logger logger = Logger.getLogger(EmailTemplateCardlinkAccountSetup.class);
	private String ERROR_FILE = InfBatchProperty.getInfBatchConfig("ERROR_FILE");
	
	@Override
	public TemplateVariableDataM getTemplateVariable() {
		TemplateVariableDataM templateVariable = new TemplateVariableDataM();
		TemplateBuilderRequest templateBuilderRequest = getTemplateBuilderRequest();
		try {
			GenerateFileDataM generateFileData = (GenerateFileDataM)templateBuilderRequest.getRequestObject();
			if(null!=generateFileData){
				String excelPath = generateFileData.getFileOutputPath()+File.separator+generateFileData.getFileOutputName()+ERROR_FILE;
				logger.debug("excelPath : "+excelPath);
				ArrayList<String> attachments = new ArrayList<String>();
				attachments.add(excelPath);
				templateVariable.setAttachments(attachments);
				templateVariable.setUniqueId(generateFileData.getUniqueId());
			}
			
			
		} catch (Exception e) {
			logger.debug("ERROR",e);
		}
		return templateVariable;
	}
}