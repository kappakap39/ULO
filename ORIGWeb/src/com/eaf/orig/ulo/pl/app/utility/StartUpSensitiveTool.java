package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.view.form.util.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.xrules.cache.model.SensitiveGroupCacheDataM;

public class StartUpSensitiveTool implements AjaxDisplayGenerateInf{	
	static Logger logger = Logger.getLogger(StartUpSensitiveTool.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {	
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");		
		
		XrulesCacheTool cacheTool = new XrulesCacheTool();			
		Vector<SensitiveGroupCacheDataM> sensitiveVect = cacheTool.getSenstiive();		
		JsonObjectUtil json = new JsonObjectUtil();	
		
		String searchType = (String) request.getSession().getAttribute("searchType");
		
		ORIGFormUtil formUtil = new ORIGFormUtil();
		String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), applicationM.getJobState(), formHandler.getFormID(), searchType);
		
		logger.debug("displayMode >> "+displayMode);
		
		if(HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){		
			if(null != sensitiveVect){
				for(SensitiveGroupCacheDataM dataM :sensitiveVect){
	//				logger.debug("FieldName >> "+dataM.getFieldName());
					json.CreateJsonObject(dataM.getFieldName(), "#"+dataM.getFieldName());
				}
			}
		}
		return json.returnJson();
	}
	
}
