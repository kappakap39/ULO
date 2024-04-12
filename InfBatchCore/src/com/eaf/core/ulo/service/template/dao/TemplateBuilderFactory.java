package com.eaf.core.ulo.service.template.dao;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.cache.InfBatchProperty;
import com.eaf.core.ulo.service.template.cont.TemplateBuilderConstant;
import com.eaf.core.ulo.service.template.model.TemplateBuilderRequest;
import com.eaf.core.ulo.service.template.model.TemplateMasterDataM;

public class TemplateBuilderFactory {
	private static transient Logger logger = Logger.getLogger(TemplateBuilderFactory.class);
	public static TemplateMasterDataM getTemplate(TemplateBuilderRequest templateBuilderRequest){
		String templateType = templateBuilderRequest.getTemplateType();
		logger.debug("templateType >> "+templateType);
		TemplateBuilderDAO templateDAO = new TemplateBuilderDAOImpl();
		if(TemplateBuilderConstant.TemplateType.EMAIL.equals(templateType)){
			return templateDAO.getEmailTemplate(templateBuilderRequest);
		}else if(TemplateBuilderConstant.TemplateType.SMS.equals(templateType)){
			return templateDAO.getSmsTemplate(templateBuilderRequest);
		}else if(TemplateBuilderConstant.TemplateType.REJECT_LETTER.equals(templateType)){
			if(templateBuilderRequest.isBundle()){
				String REJECT_LETTER_PRODUCT_NAME_BUNDLE = InfBatchProperty.getInfBatchConfig("REJECT_LETTER_PRODUCT_NAME_BUNDLE");
				return templateDAO.getRejectLetterTemplateBundle(templateBuilderRequest, REJECT_LETTER_PRODUCT_NAME_BUNDLE);
			}
			else{
				return templateDAO.getRejectLetterTemplate(templateBuilderRequest);
			}
		}else if(TemplateBuilderConstant.TemplateType.REJECT_LETTER_PDF.equals(templateType)){
			return templateDAO.getEmailTemplate(templateBuilderRequest);
		}else if(TemplateBuilderConstant.TemplateType.K_MOBILE.equals(templateType)){
			return templateDAO.getKMobileTemplate(templateBuilderRequest);
		}
	
		return null;
	}
}
