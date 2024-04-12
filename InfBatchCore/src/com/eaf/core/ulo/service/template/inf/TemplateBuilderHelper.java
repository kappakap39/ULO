package com.eaf.core.ulo.service.template.inf;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;
import com.eaf.core.ulo.service.template.model.TemplateResultDataM;
import com.eaf.core.ulo.service.template.model.TemplateVariableDataM;
import com.eaf.core.ulo.service.template.util.TemplateBuilderUtil;

public abstract class TemplateBuilderHelper implements TemplateBuilderInf{
	private static transient Logger logger = Logger.getLogger(TemplateBuilderHelper.class);
	private TemplateBuilderRequest templateBuilderRequest;
	private TemplateMasterDataM templateMaster;
	@Override
	public void init(TemplateBuilderRequest templateBuilderRequest,TemplateMasterDataM templateMaster) {
		this.templateBuilderRequest = templateBuilderRequest;
		this.templateMaster = templateMaster;
	}
	@Override
	public TemplateBuilderRequest getTemplateBuilderRequest(){
		return templateBuilderRequest;
	}
	@Override
	public TemplateMasterDataM getTemplateMaster() {
		return templateMaster;
	}
	@Override
	public TemplateResultDataM bulidTemplateResult(TemplateVariableDataM templateVariable) {
		TemplateResultDataM templateResult = new TemplateResultDataM();
		String bodyMessage ="";
		String header = "";
		String detail1 ="";
		String detail2 ="";
		String detail3="";
		String postScript1="";
		String postScript2="";
		String remark1="";
		String bodyMessageTh ="";
		String bodyMessageEn ="";
		String alertMessageTh ="";
		String alertMessageEn ="";
		
//		logger.debug("templateVariable >> "+templateVariable.getTemplateVariable());
		if(TemplateBuilderConstant.TemplateType.REJECT_LETTER.equals(templateBuilderRequest.getTemplateType())){
			String templateHeader =templateMaster.getTemplateHeader();
			String templateDetail1 = templateMaster.getDetail1();
			String templateDetail2 = templateMaster.getDetail2();
			String templateDetail3 = templateMaster.getDetail3();
			String templatePostScript1 =templateMaster.getPostScript1();
			String templatePostScript2=templateMaster.getPostScript2();
			String templateRemark1=templateMaster.getRemark1();

//			logger.debug("templateHeader \n "+templateHeader);		
//			logger.debug("templateDetail1 \n "+templateDetail1);	
//			logger.debug("templateDetail2 \n "+templateDetail2);	
//			logger.debug("templateDetail3 \n "+templateDetail3);
			detail1=TemplateBuilderUtil.getReplaceTemplateMessage(templateDetail1,templateVariable.getTemplateVariable());
			detail2=TemplateBuilderUtil.getReplaceTemplateMessage(templateDetail2,templateVariable.getTemplateVariable());
			detail3=TemplateBuilderUtil.getReplaceTemplateMessage(templateDetail3,templateVariable.getTemplateVariable());
			postScript1 =TemplateBuilderUtil.getReplaceTemplateMessage(templatePostScript1,templateVariable.getTemplateVariable());
			postScript2 =TemplateBuilderUtil.getReplaceTemplateMessage(templatePostScript2,templateVariable.getTemplateVariable());
			remark1 = TemplateBuilderUtil.getReplaceTemplateMessage(templateRemark1,templateVariable.getTemplateVariable());
		}else{
			String templateType =templateBuilderRequest.getTemplateType();
			logger.debug("templateBuilderRequest.getTemplateType()  "+templateType);		
			if(TemplateBuilderConstant.TemplateType.K_MOBILE.equals(templateBuilderRequest.getTemplateType())){
				String templateBodyTh = templateMaster.getTemplateBodyKmobileTh();
				String templateBodyEn = templateMaster.getTemplateBodyKmobileEn();
//				logger.debug("templateBody \n "+templateBody);				
				bodyMessageTh = TemplateBuilderUtil.getReplaceTemplateMessage(templateBodyTh,templateVariable.getTemplateVariable());
				bodyMessageEn = TemplateBuilderUtil.getReplaceTemplateMessage(templateBodyEn,templateVariable.getTemplateVariable());
				logger.debug("Alert message Thai : " + templateMaster.getAlertMessageTh());
				logger.debug("Alert message Eng : " + templateMaster.getAlertMessageEn());
				alertMessageTh = TemplateBuilderUtil.getReplaceTemplateMessage(templateMaster.getAlertMessageTh(),templateVariable.getTemplateVariable());
				alertMessageEn = TemplateBuilderUtil.getReplaceTemplateMessage(templateMaster.getAlertMessageEn(),templateVariable.getTemplateVariable());
				logger.debug("Alert message Thai after ReplaceTemplateMessage : " + alertMessageTh);
				logger.debug("Alert message Eng after ReplaceTemplateMessage : " + alertMessageEn);
				templateResult.setBodyMsgTh(bodyMessageTh);
				templateResult.setBodyMsgEn(bodyMessageEn);
				templateResult.setAlertMessageTh(alertMessageTh);
				templateResult.setAlertMessageEn(alertMessageEn);
			}else{
				String templateBody = templateMaster.getTemplateBody();
//				logger.debug("templateBody \n "+templateBody);		
				bodyMessage = TemplateBuilderUtil.getReplaceTemplateMessage(templateBody,templateVariable.getTemplateVariable());
				
				templateResult.setBodyMsg(bodyMessage);
			}
			if(TemplateBuilderConstant.TemplateType.EMAIL.equals(templateType) || TemplateBuilderConstant.TemplateType.REJECT_LETTER_PDF.equals(templateType)){
				header = TemplateBuilderUtil.getReplaceTemplateMessage(templateMaster.getTemplateHeader(), templateVariable.getTemplateVariable());		
			}	
		}		
		
		templateResult.setHeaderMsg(header);
		templateResult.setDetail1(detail1);
		templateResult.setDetail2(detail2);
		templateResult.setDetail3(detail3);
		templateResult.setPostScript1(postScript1);
		templateResult.setPostScript2(postScript2);
		templateResult.setRemark1(remark1);
		
		return templateResult;
	}
}
