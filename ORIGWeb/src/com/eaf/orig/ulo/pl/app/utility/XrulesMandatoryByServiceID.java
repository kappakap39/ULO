package com.eaf.orig.ulo.pl.app.utility;

//import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
//import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
//import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
//import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.orig.ulo.pl.sensitive.SensitiveField;
//import com.eaf.xrules.cache.model.SensitiveFieldCacheDataM;


public class XrulesMandatoryByServiceID implements AjaxDisplayGenerateInf {	
	
	Logger logger = Logger.getLogger(XrulesMandatoryByServiceID.class);
	
	private String fieldValue;	

	public String getFieldValue() {
		return fieldValue;
	}

	@Override
	public String getDisplayObject(HttpServletRequest request) 	throws PLOrigApplicationException {
//		logger.debug("[getDisplayObject]...");
//		String serviceID = request.getParameter("serviceID");
//		logger.debug("[getDisplayObject]...serviceID "+serviceID);
//		PLOrigFormHandler plOrigFromHandler = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
//		PLApplicationDataM plApplicationDataM = plOrigFromHandler.getAppForm();
//		JsonObjectUtil jObjectUtil = new JsonObjectUtil();
//		try{
//			SensitiveField sensitiveField = new SensitiveField();
//			Vector<SensitiveFieldCacheDataM> sensitiveVect = sensitiveField.getSensitiveFieldValidateByServiceID(plApplicationDataM, serviceID);
//			if(sensitiveVect !=null && sensitiveVect.size() >0){//				
//				for (SensitiveFieldCacheDataM sensitiveM :sensitiveVect){//						
//						logger.debug("[sensitiveM.getFieldName()]..."+sensitiveM.getFieldName());//					
//						this.fieldValue = request.getParameter(sensitiveM.getFieldName());//																	
//						if(ORIGUtility.isEmptyString(this.fieldValue)){
//							jObjectUtil.CreateJsonObject(sensitiveM.getFieldName(), "* Error Field : "+sensitiveM.getFieldName());
//						}						
//				}				
//			}			
//		}catch (Exception e) {
//			logger.fatal("Error ",e);
//		}		
//		return jObjectUtil.returnJson();
		return null;
	}

}
