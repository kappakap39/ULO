package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class GetMaillingAddress implements AjaxDisplayGenerateInf {
	Logger log = Logger.getLogger(GetMaillingAddress.class);
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		StringBuilder result = new StringBuilder();
		
		PLOrigFormHandler origForm = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		PLApplicationDataM applicationM = origForm.getAppForm();
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		Vector<PLAddressDataM> vAddress = personalM.getAddressVect();
		
		Vector<CacheDataM> vMailingAddress = new Vector<CacheDataM>();
		
		if(!OrigUtil.isEmptyVector(vAddress)){
			for (int i = 0; i < vAddress.size(); i++) {
				PLAddressDataM addresM = (PLAddressDataM) vAddress.get(i);
				if (!OrigConstant.ADDRESS_TYPE_IC.equals(addresM.getAddressType())) {
					CacheDataM cacheDataM = new CacheDataM();
					cacheDataM.setCode(addresM.getAddressType());// address type
					cacheDataM.setThDesc(HTMLRenderUtil.displayHTMLFieldIDDesc(addresM.getAddressType(), 12));// address desciption
					vMailingAddress.add(cacheDataM);
				}
			}
		}
		
		result.append(HTMLRenderUtil.displaySelectTagScriptAction_ORIG(
							vMailingAddress, personalM.getMailingAddress(),
									"mailling_address", HTMLRenderUtil.DISPLAY_MODE_EDIT,"onChange='GetCardlink()'"));
			
//#SeptemWi
//			com.eaf.j2ee.pattern.control.ScreenFlowManager screenFlowManager =
//				(com.eaf.j2ee.pattern.control.ScreenFlowManager) request.getSession(true).getAttribute("screenFlowManager");
//			screenFlowManager.setCurrentScreen("PL_MAIN_APPFORM");
//	        
			
		return result.toString();
	}

}
