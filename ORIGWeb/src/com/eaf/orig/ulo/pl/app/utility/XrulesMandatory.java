package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
//import com.eaf.orig.ulo.pl.app.rule.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.xrules.cache.data.MatrixCache;
import com.eaf.xrules.cache.model.SensitiveFieldCacheDataM;
//import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
//import com.eaf.xrules.ulo.pl.tool.cache.XrulesCacheTool;

public class XrulesMandatory implements AjaxDisplayGenerateInf{	
      
	Logger logger = Logger.getLogger(XrulesMandatory.class);

	private String fieldValue;

	@Override
	public String getDisplayObject(HttpServletRequest request)	throws PLOrigApplicationException {
				
		String buttonID =  request.getParameter("buttonID");
		
		logger.debug("[XrulesMandatory]...ButtonID "+buttonID);
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		if(null == applicationM) applicationM = new PLApplicationDataM();
		
		JsonObjectUtil json = new JsonObjectUtil();
		
		try{			
//			XrulesCacheTool cache = new XrulesCacheTool();			
//			ORIGXRulesTool xrulesTool = new ORIGXRulesTool();			
//			XrulesRequestDataM requestM = xrulesTool.MapXrulesRequestDataM(applicationM, buttonID, null);			
//			Vector<SensitiveFieldCacheDataM> sensitiveVect = cache.GetMandatorySensitiveField(requestM);
			
			HashMap<String, Vector<SensitiveFieldCacheDataM>> hCache = MatrixCache.getMandatory().get(applicationM.getMatrixServiceID());
			Vector<SensitiveFieldCacheDataM> sensitiveVect = hCache.get(buttonID);
			
			if(!OrigUtil.isEmptyObject(sensitiveVect)){				
				for (SensitiveFieldCacheDataM sensitiveM :sensitiveVect){					
					this.fieldValue = request.getParameter(sensitiveM.getFieldName());
					if(OrigUtil.isEmptyString(this.fieldValue)){
//						String message = cache.GetMessageMandatory(sensitiveM.getFieldName());
						String message = MatrixCache.getMessage().get(sensitiveM.getFieldName());
						if(null != message){
							json.CreateJsonObject(sensitiveM.getFieldName(), message);		
						}
					}						
				}				
			}		
			
			XrulesManualMandatoryTool manualTool = new XrulesManualMandatoryTool();
				manualTool.Mandatory(buttonID, json, applicationM ,request);
			
		}catch (Exception e) {
			logger.fatal("getDisplayObject Error ",e);
		}				
		return json.returnJson();
	}
	
	public String getFieldValue() {
		return fieldValue;
	}	
}
