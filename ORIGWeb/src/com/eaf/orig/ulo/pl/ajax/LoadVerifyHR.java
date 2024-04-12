package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.PLMessageResourceUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;


public class LoadVerifyHR implements AjaxDisplayGenerateInf {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private CacheDataM cacheM = null;
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
			
		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		if(null == formHandler) formHandler = new PLOrigFormHandler();
		PLApplicationDataM appM = formHandler.getAppForm();
		if(null == appM) appM = new PLApplicationDataM();
		
		String formID = formHandler.getFormID();
		String searchType = (String) request.getSession().getAttribute("searchType");		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		ORIGFormUtil formUtil = new ORIGFormUtil();		
		String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), appM.getJobState(), formID, searchType);				
		logger.debug("DisplayMode >> "+displayMode);
		
		PLPersonalInfoDataM plPersonalInfoM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
		PLAddressDataM plAddressM = plPersonalInfoM.getAddressDataM(PLAddressDataM.AddressType.OFFICE_ADDRESS);
		
		if(null == plAddressM) plAddressM = new PLAddressDataM();
		
		Vector telVect = new Vector();
		
		if(!ORIGUtility.isEmptyString(plAddressM.getMobileNo())){
			cacheM = new CacheDataM();
			cacheM.setCode(CreatePhoneNumber(plAddressM.getMobileNo(),""));
			cacheM.setThDesc(CreatePhoneNumber(plAddressM.getMobileNo(),""));
			telVect.add(cacheM);
		}
		
		if(!ORIGUtility.isEmptyString(plAddressM.getPhoneNo1())){
			cacheM = new CacheDataM();
			cacheM.setCode(CreatePhoneNumber(plAddressM.getPhoneNo1(),plAddressM.getPhoneExt1()));			
			cacheM.setThDesc(CreatePhoneNumber(plAddressM.getPhoneNo1(),plAddressM.getPhoneExt1()));
			telVect.add(cacheM);
		}
		
		if(!ORIGUtility.isEmptyString(plAddressM.getPhoneNo2())){
			cacheM = new CacheDataM();
			cacheM.setCode(CreatePhoneNumber(plAddressM.getPhoneNo2(),plAddressM.getPhoneExt2()));			
			cacheM.setThDesc(CreatePhoneNumber(plAddressM.getPhoneNo2(),plAddressM.getPhoneExt2()));
			telVect.add(cacheM);
		}
		
		cacheM = new CacheDataM();
		cacheM.setCode(AddFollowDetail.OTHER_PHONENO_CODE);
		cacheM.setThDesc(PLMessageResourceUtil.getTextDescription(request, "FOLLOW_OTHER_PHONE"));
		telVect.add(cacheM);
		
		JsonObjectUtil jObjectUtil = new JsonObjectUtil();
		
		jObjectUtil.CreateJsonObject("div-verhr-phoneno", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(telVect,"","verhr-phoneno",displayMode," onchange=\"javascript:otherTelNumber();\" "));
		
		return jObjectUtil.returnJson();
	}	

	public String CreatePhoneNumber(String phoneNo ,String ext){		
		StringBuilder str = new StringBuilder();		
		str.append(phoneNo);		
		if(!ORIGUtility.isEmptyString(ext)){
			str.append(" Ext. "+ext);						
		}		
		logger.debug("phoneNo "+str);
		return str.toString();
	}
}
