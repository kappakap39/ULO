package com.eaf.orig.ulo.pl.ajax;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;

import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.view.form.util.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.ClassEngine;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.ObjectEngine;
import com.eaf.orig.ulo.pl.app.utility.SensitiveFieldTool;
import com.eaf.orig.ulo.pl.app.utility.SensitiveFiledManualEngine;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.xrules.cache.data.MatrixCache;
import com.eaf.xrules.cache.model.SensitiveFieldCacheDataM;
import com.eaf.xrules.cache.model.SensitiveGroupCacheDataM;
//import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
//import com.eaf.xrules.ulo.pl.tool.cache.XrulesCacheTool;

public class SensitiveFieldEngine implements AjaxDisplayGenerateInf{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{
		
		String attrName = request.getParameter("attrName");
		
		logger.debug("[attrName] "+attrName);
		
//		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");			
				
		PLOrigFormHandler formHandler = (PLOrigFormHandler) request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		PLApplicationDataM applicationM = formHandler.getAppForm();
				
		PLApplicationDataM sensitiveM = PLOrigFormHandler.getSensitivePLAppM(request);
		
		String searchType = (String) request.getSession().getAttribute("searchType");
		
		JSONArray jsonArray = new JSONArray();	
		
		ORIGFormUtil formUtil = new ORIGFormUtil();
		String displayMode = formUtil.getDisplayMode("VERIFICATION_SUBFORM", userM.getRoles(), applicationM.getJobState(), formHandler.getFormID(), searchType);
		
		logger.debug("displayMode >> "+displayMode);
		
		if(!HTMLRenderUtil.DISPLAY_MODE_EDIT.equals(displayMode)){	
			logger.debug(">> DISPLAY_MODE_VIEW Not Sensitive!!");
			return jsonArray.toString();
		}
				
		List<String> sButton = new ArrayList<String>();
		
		SensitiveFiledManualEngine senstiveManual = new SensitiveFiledManualEngine();
			senstiveManual.ManualSensitive(attrName ,request, applicationM, sensitiveM, userM, jsonArray, sButton);
		
//		XrulesCacheTool cacheTool = new XrulesCacheTool();		
//		Vector<SensitiveGroupCacheDataM> groupVect = cacheTool.GetSensitiveGroupCache(attrName);
		
		Vector<SensitiveGroupCacheDataM> groupVect = MatrixCache.getSensitiveGroup().get(attrName);
				
		if(OrigUtil.isEmptyVector(groupVect) && jsonArray.length() == 0 && sButton.size() == 0){
			logger.debug("[Field "+attrName+" Not SensitiveField]");
			return jsonArray.toString();
		}
		
		if(null != groupVect){		
			for(SensitiveGroupCacheDataM dataM : groupVect){
				SensitiveEngineModule(dataM,request, applicationM, sensitiveM, userM, jsonArray ,sButton);
			}
		}
		this.ButtonStyleEngine(jsonArray, sButton ,request);
		
		return jsonArray.toString();
	}
	
	public void SensitiveEngineModule(SensitiveGroupCacheDataM groupM ,HttpServletRequest request ,PLApplicationDataM applicationM 
											,PLApplicationDataM appSM ,UserDetailM userM,JSONArray jsonArray 
												,List<String> sButton){
		
		logger.debug("[SensitiveEngineModule] FieldName "+groupM.getFieldName());
		
//		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();		
//		XrulesRequestDataM requestM = xrulesTool.MapSensitiveXrulesRequestDataM(appM, groupM, userM);				
//		XrulesCacheTool cacheTool = new XrulesCacheTool();		
//		Vector<SensitiveFieldCacheDataM> sensitiveVect = cacheTool.GetSensitiveFieldService(requestM);
		
		HashMap<String,Vector<SensitiveFieldCacheDataM>> h = MatrixCache.getSensitive().get(applicationM.getMatrixServiceID());
		Vector<SensitiveFieldCacheDataM> sensitiveVect = h.get(groupM.getFieldName());
		
		if(OrigUtil.isEmptyVector(sensitiveVect)){
			logger.debug("[Field "+groupM.getFieldName()+" Not SensitiveField]");
			return;
		}
				
		SensitiveFieldTool sentiveTool = new SensitiveFieldTool();
		
		SensitiveFieldCacheDataM sentiveM = (SensitiveFieldCacheDataM)sensitiveVect.firstElement();
		
		String value = request.getParameter(sentiveM.getFieldName());
		
		logger.debug("[SensitiveEngineModule] value "+value);
		
		ObjectEngine objEngine = new ObjectEngine();
			objEngine.setSystem01(groupM.getSystem01());
			objEngine.setSystem02(groupM.getSystem02());
			objEngine.setSystem03(groupM.getSystem03());
			
		Object sentiveObj = objEngine.FindObject(groupM.getObjModelName(),PLOrigFormHandler.getSensitivePLAppM(request));
		
		Object appObj = objEngine.FindObject(groupM.getObjModelName(),PLOrigFormHandler.getPLApplicationDataM(request));
		
		ClassEngine classEngine = new ClassEngine();
		
		Field fAppObj = classEngine.getField(appObj,groupM.getObjFieldName());
		Object aObjValue = classEngine.getValue(fAppObj, appObj);
			
		Field fSentiveObj = classEngine.getField(sentiveObj,groupM.getObjFieldName());	
		Object sObjValue = classEngine.getValue(fSentiveObj, sentiveObj);	
		
		String typeObj = classEngine.getType(fAppObj);
		Object ObjInput = classEngine.ConvertValue(typeObj, value);
		
		if(null !=sentiveM.getFieldName() &&  "birth_date".equals(sentiveM.getFieldName())){
			ObjInput = classEngine.ConvertBirthDate(value);
		}
		
		if(objEngine.CompareValueObj(aObjValue, ObjInput, typeObj)){
			logger.debug("[Value Equal Application Session Rollback To Current]");
			classEngine.setValue(fSentiveObj, sentiveObj, aObjValue);
			sentiveTool.MapObjectDisplaySensitive(applicationM, appSM, sensitiveVect, ORIGXRulesTool.Constant.ROLLBACK_RULE, userM, jsonArray, sButton);
			return;
		}
				
		if(objEngine.CompareValueObj(sObjValue, ObjInput, typeObj)){			
			logger.debug("[Value Equal Sentive Session Do Nothing]");
			return;
		}
		
		logger.debug("[Value Not Equal Sentive Session Logic]");		
		classEngine.setValue(fSentiveObj, sentiveObj, ObjInput);
		sentiveTool.MapObjectDisplaySensitive(applicationM, appSM, sensitiveVect, ORIGXRulesTool.Constant.CLEAR_RULE, userM, jsonArray, sButton);			
		return;
	}	

	public void ButtonStyleEngine(JSONArray jArray ,List<String> sButton , HttpServletRequest request){
		if(null == sButton|| sButton.size() == 0) 
				return;
		if(null == jArray) jArray = new JSONArray();
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
		
		ORIGXRulesTool xrulesTool = new ORIGXRulesTool();
		
		for(String buttonID : sButton){
			xrulesTool.ButtonStyleEngine(jArray, buttonID, applicationM, request);
		}				
	}
		
}
