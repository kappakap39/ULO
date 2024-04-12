package com.eaf.orig.ulo.app.view.form.validateform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.exception.EncryptionException;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;

public class ValidateIncreaseCardRequest extends ValidateFormHelper implements ValidateFormInf {
	private static transient Logger logger = Logger.getLogger(ValidateIncreaseCardRequest.class);
	String CARD_REQUEST_LIMIT = SystemConfig.getGeneralParam("CARD_REQUEST_LIMIT");
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		return ValidateFormInf.VALIDATE_YES;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {
		FormErrorUtil formError = new FormErrorUtil();	
		DIHQueryResult<CardLinkDataM> queryResult = new DIHQueryResult<CardLinkDataM>();

		String CARD_NO = FormatUtil.removeDash(request.getParameter("CARD_NO"));	
		String CIS_NUMBER = request.getParameter("CIS_NUMBER");		
		logger.debug("CARD_NO >> "+CARD_NO);
		logger.debug("CIS_NUMBER >> "+CIS_NUMBER);
		boolean IS_ERROR_INFOMATION = false;
		if(Util.empty(CARD_NO)){
			formError.mandatoryElement("CARD_NO","CARD_NO",CARD_NO,request);
			IS_ERROR_INFOMATION = true;
		}
		if(Util.empty(CIS_NUMBER)){
			formError.mandatoryElement("CIS_NUMBER","CIS_NUMBER",CIS_NUMBER,request);
			IS_ERROR_INFOMATION = true;
		}
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		
		int CNT = applicationGroup.countApplications();
		logger.debug("CNT >> "+CNT);
		if(CNT >= Integer.parseInt(CARD_REQUEST_LIMIT) ){
			formError.error(MessageUtil.getText(request, "MSG_MAX_CARD"));
			IS_ERROR_INFOMATION = true;
		}
		if(isDuplicateCard(applicationGroup, CARD_NO)){
			IS_ERROR_INFOMATION = true;
			formError.error(MessageUtil.getText(request, "MSG_CARD_DUPLICATE"));
		}		
		if(!IS_ERROR_INFOMATION){			
			String encryptionResult = "";
			try{
				Encryptor enc = EncryptorFactory.getDIHEncryptor();
				encryptionResult = enc.encrypt(CARD_NO);
			}catch(EncryptionException e){
				logger.fatal("ERROR",e);
				e.printStackTrace();
			}
			DIHProxy dihProxy = new DIHProxy();
			queryResult = dihProxy.getCardLinkInfoENCPT(encryptionResult);
			CardLinkDataM increaseCardRequest  = queryResult.getResult();
			String statusCode = queryResult.getStatusCode();
			if(ResponseData.SUCCESS.equals(statusCode)){
			if(Util.empty(increaseCardRequest.getCardNo())){
				formError.error(MessageUtil.getText(request, "MSG_NO_CARD_REQUEST"));
			}else if(Util.empty(increaseCardRequest.getMainCis()) || !CIS_NUMBER.equals(increaseCardRequest.getMainCis())){
				formError.error(MessageUtil.getText(request, "MSG_CARD_DONT_MAP_CIS"));
			}
			}else if(ResponseData.SYSTEM_EXCEPTION.equals(statusCode) && !Util.empty(queryResult.getErrorData())){
				HashMap<String,Object> errorDih = new HashMap<String, Object>();
				ArrayList<String> errorMessage = new ArrayList<String>();
				errorMessage.add(queryResult.getErrorData().getErrorInformation());
				errorDih.put("STATUS_CODE", ResponseData.SYSTEM_EXCEPTION);
				errorDih.put(FormErrorUtil.ERROR, errorMessage);
				return errorDih;
			}
		}
		return formError.getFormError();
	}
	
	private boolean isDuplicateCard(ApplicationGroupDataM applicationGroup, String cardNo){
		boolean isDuplicateCard = false;
		try{
			Encryptor enc = EncryptorFactory.getDIHEncryptor();
			ArrayList<CardDataM> cards = applicationGroup.getCards();
			if(!Util.empty(cards)){
				for(CardDataM card : cards){
					String encryptCardNo = card.getCardNo();
					if(!Util.empty(encryptCardNo) && cardNo.equals(enc.decrypt(encryptCardNo))){
						isDuplicateCard = true;
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		logger.debug("isDuplicateCard >> "+isDuplicateCard);
		return isDuplicateCard;
	}

}
