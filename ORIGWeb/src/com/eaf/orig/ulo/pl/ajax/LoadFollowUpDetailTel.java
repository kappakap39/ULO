package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
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
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;


public class LoadFollowUpDetailTel implements AjaxDisplayGenerateInf {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	private CacheDataM cacheM = null;
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {		
		String personal = request.getParameter("fdPersonalType");			
		String addressType  = request.getParameter("fdAddressType");	
		
//		String pagetype = request.getParameter("page-type");
//		logger.debug("[getDisplayObject]..Address Type "+addressType);		
//		logger.debug("[getDisplayObject]..Personal Type "+personal);		

		PLOrigFormHandler  formHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
		if(null == formHandler) formHandler = new PLOrigFormHandler();
		PLApplicationDataM applicationM = formHandler.getAppForm();
		if(null == applicationM) applicationM = new PLApplicationDataM();
		
		String formID = formHandler.getFormID();
		String searchType = (String) request.getSession().getAttribute("searchType");		
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		
		ORIGFormUtil formUtil = new ORIGFormUtil();		
		String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), applicationM.getJobState(), formID, searchType);				
		logger.debug("DisplayMode >> "+displayMode);
		
		Vector<CacheDataM> telVect = new Vector<CacheDataM>();
		if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personal)){ //case applicant
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(personal);	
			PLAddressDataM addressM = personalM.getAddressDataM(addressType);
			if(!ORIGUtility.isEmptyString(addressM.getMobileNo())){
				cacheM = new CacheDataM();
				cacheM.setCode(CreatePhoneNumber(addressM.getMobileNo(),""));
				cacheM.setThDesc(CreatePhoneNumber(addressM.getMobileNo(),""));
				telVect.add(cacheM);
			}		
			if(!ORIGUtility.isEmptyString(addressM.getPhoneNo1())){
				cacheM = new CacheDataM();
				cacheM.setCode(CreatePhoneNumber(addressM.getPhoneNo1(),addressM.getPhoneExt1()));			
				cacheM.setThDesc(CreatePhoneNumber(addressM.getPhoneNo1(),addressM.getPhoneExt1()));
				telVect.add(cacheM);
			}		
			if(!ORIGUtility.isEmptyString(addressM.getPhoneNo2())){
				cacheM = new CacheDataM();
				cacheM.setCode(CreatePhoneNumber(addressM.getPhoneNo2(),addressM.getPhoneExt2()));			
				cacheM.setThDesc(CreatePhoneNumber(addressM.getPhoneNo2(),addressM.getPhoneExt2()));
				telVect.add(cacheM);
			}
			cacheM = new CacheDataM();
			cacheM.setCode("99");
			cacheM.setThDesc(PLMessageResourceUtil.getTextDescription(request, "FOLLOW_OTHER_PHONENO"));
			telVect.add(cacheM);

		}else if (!OrigUtil.isEmptyString(personal)){ // case sale
			PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();
			if(saleInfoM != null && !ORIGUtility.isEmptyString(saleInfoM.getSalesPhoneNo())){
				cacheM = new CacheDataM();
				cacheM.setCode(CreatePhoneNumber(saleInfoM.getSalesPhoneNo(),""));			
				cacheM.setThDesc(CreatePhoneNumber(saleInfoM.getSalesPhoneNo(),""));
				telVect.add(cacheM);
			}
			cacheM = new CacheDataM();
			cacheM.setCode("99");
			cacheM.setThDesc(PLMessageResourceUtil.getTextDescription(request, "FOLLOW_OTHER_PHONENO"));
			telVect.add(cacheM);
		}
		JsonObjectUtil json = new JsonObjectUtil();		
			json.CreateJsonObject("element_phoneNo", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(telVect,"","fdPhoneNo",displayMode," onchange=\"javascript:otherTelNumber();\""));
		return json.returnJson();
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
