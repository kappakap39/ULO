package com.eaf.orig.ulo.app.view.util.dih;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.security.encryptor.Encryptor;
import com.eaf.core.ulo.security.encryptor.EncryptorFactory;
import com.eaf.core.ulo.security.hash.Hasher;
import com.eaf.core.ulo.security.hash.HashingFactory;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;

public class PrivilegeProjectCodeCardHolder implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(PrivilegeProjectCodeCardHolder.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.PRIVILEGE_PROJECT_CODE_CARD_HOLDER);
		JSONUtil json = new JSONUtil();
		try{
			String HASH_MAIN_CARD_NUMBER = "";	
			String ENCRP_MAIN_CARD_NUMBER = "";	
			String KEY_IN_MAINCARD_NO = FormatUtil.removeDash(request.getParameter("MAIN_CARD_NUMBER"));
			if(!Util.empty(KEY_IN_MAINCARD_NO)){
				Hasher hash = HashingFactory.getSHA256Hasher();
				HASH_MAIN_CARD_NUMBER = hash.getHashCode(KEY_IN_MAINCARD_NO);
				Encryptor enc = EncryptorFactory.getDIHEncryptor();
				ENCRP_MAIN_CARD_NUMBER = enc.encrypt(KEY_IN_MAINCARD_NO);
			}
			DIHProxy dihProxy = new DIHProxy();
			CardLinkDataM cardLink = null;		
			
			if(!Util.empty(KEY_IN_MAINCARD_NO)){
				DIHQueryResult<CardLinkDataM> dihPrivilege= dihProxy.getCardLinkInfoENCPT(ENCRP_MAIN_CARD_NUMBER);
				if(!ResponseData.SUCCESS.equals(dihPrivilege.getStatusCode())){
					return responseData.error(dihPrivilege);
				}
				cardLink = dihPrivilege.getResult();
			}
			logger.debug("cardLink >> "+cardLink);	
			logger.debug("KEY_IN_MAINCARD_NO >> "+KEY_IN_MAINCARD_NO);
			logger.debug("HASH_MAIN_CARD_NUMBER >> "+HASH_MAIN_CARD_NUMBER);
			logger.debug("ENCRP_MAIN_CARD_NUMBER >> "+ENCRP_MAIN_CARD_NUMBER);
			logger.debug("cardLink.getMainCardHolderName() >> "+cardLink.getMainCardHolderName());
			json.put("REFER_CARD_DESC", cardLink.getMainCardHolderName());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		return responseData.success(json.getJSON());
	}
}
