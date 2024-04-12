package com.eaf.inf.batch.ulo.letter.reject.controller;

import java.util.Map;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.common.cont.InfBatchConstant;
import com.eaf.core.ulo.common.display.Formatter;
import com.eaf.core.ulo.common.task.model.TaskExecuteDataM;
import com.eaf.core.ulo.common.util.InfBatchUtil;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.controller.TemplateController;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateBuilderResponse;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterInterfaceResponse;
import com.eaf.inf.batch.ulo.letter.reject.model.RejectLetterPDFDataM;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.GenerateRejectLetetrTemplate;
import com.eaf.inf.batch.ulo.letter.reject.template.generate.inf.GenerateRejectLetterInf;
import com.eaf.inf.batch.ulo.letter.reject.util.RejectLetterUtil;

public class RejectLetterPDFController {
	private static transient Logger logger = Logger.getLogger(RejectLetterPDFController.class);

	public TaskExecuteDataM create(RejectLetterPDFDataM rejectLetterPdf){
		TaskExecuteDataM taskExecuteDataM = new TaskExecuteDataM();
		try {
			TemplateBuilderResponse templateBuilderResponse = this.getTemplateBuilderResponse(rejectLetterPdf);
			if(!InfBatchUtil.empty(templateBuilderResponse)){
				if(validateRejectLetterPDFInterface(taskExecuteDataM,templateBuilderResponse)){
					GenerateRejectLetetrTemplate generateRejectLetetrTemplate = new GenerateRejectLetetrTemplate();
					GenerateRejectLetterInf generateRejectLetterInf = generateRejectLetetrTemplate.getTemplateInterfaceFormat(RejectLetterUtil.PDF);
					RejectLetterInterfaceResponse rejectLetterInterfaceResponse= (RejectLetterInterfaceResponse)generateRejectLetterInf.generateTemplate(templateBuilderResponse,rejectLetterPdf);
					taskExecuteDataM.setResponseObject(rejectLetterInterfaceResponse);
					taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.SUCCESS);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecuteDataM.setResultDesc(e.getLocalizedMessage());
		}
		return taskExecuteDataM;
	}
	
	private TemplateBuilderResponse getTemplateBuilderResponse(RejectLetterPDFDataM rejectLetterPdf) throws Exception{
		String APPLICATION_TYPE_INCREASE = InfBatchProperty.getInfBatchConfig("APPLICATION_TYPE_INCREASE");
		String REJECT_LETTER_PDF_SENDTO_CUSTOMER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SENDTO_CUSTOMER");
		String REJECT_LETTER_PDF_SENDTO_SELLER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_SENDTO_SELLER");
		String REJECT_LETTER_PDF_APP_OTHER = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_APP_OTHER");
		TemplateBuilderResponse templateBuilderResponse  = new TemplateBuilderResponse();
		TemplateController templateController = new TemplateController();
		TemplateBuilderRequest templateBuilderRequest = new TemplateBuilderRequest();
		String LANGUAGE ="";
		String SEND_TO=rejectLetterPdf.getSendTo();
		String applicationType = Formatter.display(rejectLetterPdf.getApplicationType());
		if(!InfBatchUtil.empty(rejectLetterPdf.getLanguage())){
			LANGUAGE = "_"+rejectLetterPdf.getLanguage().toUpperCase();
		}
		logger.debug("SEND_TO : "+SEND_TO);
		//String TEMPLATE_ID  = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PDF_"+SEND_TO+"_TEMPLATE_ID"+LANGUAGE);	
		//logger.debug("TEMPLATE_ID_CONFIG_NAME :"+"REJECT_LETTER_PDF_"+SEND_TO+"_TEMPLATE_ID"+LANGUAGE);
		//logger.debug("TEMPLATE_ID :"+TEMPLATE_ID);
		String TEMPLATE_ID = "";
		String configId = "";
		if(SEND_TO.equals(REJECT_LETTER_PDF_SENDTO_CUSTOMER)){
			String app = (!InfBatchUtil.empty(applicationType) && applicationType.equals(APPLICATION_TYPE_INCREASE)) ? APPLICATION_TYPE_INCREASE : REJECT_LETTER_PDF_APP_OTHER ;
			configId = "REJECT_LETTER_PDF_"+SEND_TO+"_APP_"+app+"_TEMPLATE_ID"+LANGUAGE;
		}else{ // SELLER, RECOMMEND
			configId = "REJECT_LETTER_PDF_"+SEND_TO+"_NORMAL_TEMPLATE_ID"+LANGUAGE;
		}
		TEMPLATE_ID  = InfBatchProperty.getInfBatchConfig(configId);
		logger.debug("configId : "+configId);
		logger.debug("TEMPLATE_ID : "+TEMPLATE_ID);
		templateBuilderRequest.setTemplateId(TEMPLATE_ID);
		templateBuilderRequest.setTemplateType(TemplateBuilderConstant.TemplateType.REJECT_LETTER_PDF);
		templateBuilderRequest.setRequestObject(rejectLetterPdf);
		templateBuilderRequest.setUniqueId(rejectLetterPdf.getLetterNo());
		templateBuilderRequest.setLanguage(rejectLetterPdf.getLanguage().toUpperCase());
		templateBuilderResponse = templateController.build(templateBuilderRequest);
		return templateBuilderResponse;
	}
	private boolean validateRejectLetterPDFInterface(TaskExecuteDataM taskExecuteDataM,TemplateBuilderResponse templateBuilderResponse)throws Exception{
		TemplateVariableDataM templateVariable = templateBuilderResponse.getTemplateVariable();
		Map<String, Object> hMapping = templateVariable.getTemplateVariable();
		String email = (String)hMapping.get(TemplateBuilderConstant.TemplateVariableName.EMAIL_PRIMARY);
		logger.debug("email : "+email);
		if(InfBatchUtil.empty(email)){
			taskExecuteDataM.setResultCode(InfBatchConstant.ResultCode.FAIL);
			taskExecuteDataM.setResultDesc("Email was not found");
			return false;
		}
		return true;
	}
}
