package com.eaf.orig.ulo.app.view.util.dih;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;

public class CardHolderNameInformation implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(CardHolderNameInformation.class);
	String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");
	String APPLICATION_CARD_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("APPLICATION_CARD_TYPE_SUPPLEMENTARY");
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.CARD_HOLDER_NAME);
		String data = null;
		try{
			JSONUtil json = new JSONUtil();
			String HASH_MAIN_CARD_NUMBER = "";	
			String ENCRP_MAIN_CARD_NUMBER = "";	
			String KEY_IN_MAINCARD_NO = FormatUtil.removeDash(request.getParameter("MAIN_CARD_NUMBER"));
			ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
			ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
			String personalId = null;
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_INFO);
			int PERSONAL_SEQ = 1;
			String PERSONAL_TYPE = PERSONAL_TYPE_INFO;
			if(Util.empty(personalInfo)){			
				try{
					personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
				}catch(Exception e){
					logger.fatal("ERROR ",e);
					return responseData.error(e, ErrorController.ERROR_MESSAGE_NAME.ERROR_REFER_TO_WORK_SHEET_DIH);
				}
				personalInfo = new PersonalInfoDataM();
				personalInfo.setPersonalType(PERSONAL_TYPE);
				personalInfo.setSeq(PERSONAL_SEQ);
				personalInfo.setPersonalId(personalId);
				applicationGroup.addPersonalInfo(personalInfo);
			}
			if(!Util.empty(KEY_IN_MAINCARD_NO)){
				Hasher hash = HashingFactory.getSHA256Hasher();
				HASH_MAIN_CARD_NUMBER = hash.getHashCode(KEY_IN_MAINCARD_NO);
				Encryptor enc = EncryptorFactory.getDIHEncryptor();
				ENCRP_MAIN_CARD_NUMBER = enc.encrypt(KEY_IN_MAINCARD_NO);
			} else {
				applicationGroup.setMainCardNo(null);
				applicationGroup.setMainCardHolderName(null);
				personalInfo.setMainCardNo(null);
				personalInfo.setMainCardHolderName(null);
			}
			
			String VERIFY_PRIVILEGE_DOC_PROJECT_CODE =request.getParameter("VERIFY_PRIVILEGE_DOC_PROJECT_CODE");
			CardLinkDataM cardLink = null;
			logger.debug("KEY_IN_MAINCARD_NO >> "+KEY_IN_MAINCARD_NO);
			logger.debug("HASH_MAIN_CARD_NUMBER >> "+HASH_MAIN_CARD_NUMBER);
			logger.debug("ENCRP_MAIN_CARD_NUMBER >> "+ENCRP_MAIN_CARD_NUMBER);
			DIHProxy dihProxy = new DIHProxy();
			if(!Util.empty(KEY_IN_MAINCARD_NO)){
				DIHQueryResult<CardLinkDataM>  dihCardLinkResult = dihProxy.getCardLinkInfoENCPT(ENCRP_MAIN_CARD_NUMBER);
				if(!ResponseData.SUCCESS.equals(dihCardLinkResult.getStatusCode())){
					return responseData.error(dihCardLinkResult);
				}
				cardLink =dihCardLinkResult.getResult();
			}
			logger.debug("cardLink >> "+cardLink);	
			if(!Util.empty(cardLink) && APPLICATION_CARD_TYPE_SUPPLEMENTARY.equals(cardLink.getApplicationType())){
				cardLink = null;
			}
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
			if(Util.empty(personalInfos)){
				personalInfos = new ArrayList<PersonalInfoDataM>();
			}
			HashMap<String, CompareDataM> comparisonFields = getComparisonField(request);
			if(null == comparisonFields){
				comparisonFields = new HashMap<String, CompareDataM>();
			}	
			if(!Util.empty(cardLink) && Util.empty(VERIFY_PRIVILEGE_DOC_PROJECT_CODE)){
				String mainCardNo = ENCRP_MAIN_CARD_NUMBER;
				String mainCardHolderName = cardLink.getMainCardHolderName();
				String cisNo = cardLink.getCisNo();
				logger.debug("mainCardNo >> "+mainCardNo);
				logger.debug("mainCardHolderName >> "+mainCardHolderName);
				logger.debug("cisNo >> "+cisNo);
				if(!Util.empty(mainCardHolderName)){
					applicationGroup.setMainCardHolderName(mainCardHolderName);
					applicationGroup.setMainCardNo(mainCardNo);
					personalInfo.setMainCardHolderName(mainCardHolderName);
					personalInfo.setMainCardNo(mainCardNo);
					DIHQueryResult<String>  dihMapperResult =  dihProxy.personalInfoMapper(cisNo,personalInfo,comparisonFields);
					if(!ResponseData.SUCCESS.equals(dihMapperResult.getStatusCode())){
						return responseData.error(dihMapperResult);
					}
				}else{
					applicationGroup.setMainCardNo(null);
					applicationGroup.setMainCardHolderName(null);
					personalInfo.setMainCardNo(null);
					personalInfo.setMainCardHolderName(null);
				}
			}
			
			List<ApplicationDataM> appList = applicationGroup.getActiveApplications();
			if(!Util.empty(appList)) {
				for(ApplicationDataM appM : appList) {
					CardDataM cardM = appM.getCard();
					cardM.setMainCardNo(personalInfo.getMainCardNo());
					cardM.setMainCardHolderName(personalInfo.getMainCardHolderName());
				}
			}
			if(!Util.empty(cardLink) && !Util.empty(cardLink.getMainCardHolderName())){
				json.put("REFER_CARD_DESC", cardLink.getMainCardHolderName());
			} else {
				json.put("REFER_CARD_DESC", personalInfo.getMainCardHolderName());
			}
			json.put("REFER_MAIN_CARD",personalInfo.getMainCardNo());
			data = json.getJSON();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e, ErrorController.ERROR_MESSAGE_NAME.ERROR_REFER_TO_WORK_SHEET_DIH);
		}
		return responseData.success(data);
	}
	
	private HashMap<String, CompareDataM> getComparisonField(HttpServletRequest request){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		return applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
	}

}