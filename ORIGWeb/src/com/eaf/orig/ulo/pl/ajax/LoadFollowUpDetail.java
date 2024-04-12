package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.dao.utility.UtilityDAO;
import com.eaf.orig.shared.dao.utility.UtilityDAOImpl;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.model.app.PLSaleInfoDataM;

public class LoadFollowUpDetail implements AjaxDisplayGenerateInf{
	       
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		JsonObjectUtil json = new JsonObjectUtil();
		String personal = request.getParameter("fdPersonalType");
		
		logger.debug("[getDisplayObject]..Personal Type "+personal);
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
		
		if(OrigConstant.PERSONAL_TYPE_APPLICANT.equals(personal)){
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(personal);
							
			AddressUtil addressUtil = new AddressUtil();
			Vector addressTypeVect = addressUtil.getAddressTypeCacheByPlAddressM(personalM.getAddressVect());
			
			json.CreateJsonObject("element_name", HTMLRenderUtil.displayHTML(personalM.getThaiFirstName())+" "+HTMLRenderUtil.displayHTML(personalM.getThaiLastName()));
			json.CreateJsonObject("element_addressType", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(addressTypeVect,"","fdAddressType",displayMode," onchange=\"javascript:loadTelephone();\" "));
		}else if (!OrigUtil.isEmptyString(personal)){
			PLSaleInfoDataM saleInfoM = applicationM.getSaleInfo();
			String saleName = "";
			if(saleInfoM != null){
				UtilityDAO utilDAO = new UtilityDAOImpl();
				try{
					saleName = utilDAO.getSellerName(saleInfoM.getSalesName());
				}catch(Exception e){
					logger.fatal("ERROR ",e);
				}
			}
			json.CreateJsonObject("element_name", HTMLRenderUtil.displayHTML(saleName));
			json.CreateJsonObject("element_addressType", HTMLRenderUtil.displaySelectTagScriptAction_ORIG(null,"","addressType",displayMode," onchange=\"javascript:loadTelephone();\" "));
		}
		return json.returnJson();
	}
}
