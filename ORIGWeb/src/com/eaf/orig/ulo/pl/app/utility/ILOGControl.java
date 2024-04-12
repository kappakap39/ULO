package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.eaf.orig.profile.model.UserDetailM;
//import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
//import com.eaf.xrules.ulo.pl.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
////import com.eaf.xrules.ulo.pl.utility.IlogModelMapping;
//import com.ilog.service.model.response.ILOGServiceResponse;
//import com.ilog.xrules.ilog.XRulesILogProxy;

public class ILOGControl {
	
	static Logger logger = Logger.getLogger(ILOGControl.class);
	
	public HashMap<String, String> execute(int action ,PLApplicationDataM applicationM, UserDetailM userM) throws Exception{
		
		logger.debug("execute().. "+action);
		
		HashMap<String, String> result = new HashMap<String, String>();
		
		ORIGXRulesTool tool = new ORIGXRulesTool();
		XrulesRequestDataM request = tool.MapXrulesRequestDataM(applicationM,action, userM);		
//		IlogModelMapping map = new IlogModelMapping();
//			map.MappingModelIlog(request);	
//		
//		XRulesILogProxy proxy = new XRulesILogProxy();
//			proxy.setEndpoint(ORIGConfig.ILOG_SERVICE);
//		
//		ILOGServiceResponse response = 	proxy.doILOGService(String.valueOf(action), request.getPlAppIlogM());
//		if(null == response || !PLXrulesConstant.ExecuteCode.EXE_SUCCESS.equals(response.getExecuteCode())){
//			throw new Exception(PLXrulesConstant.ExecuteDesc.EXE_ILOGFAIL_DESC);
//		}
		
//		ILOGMapData mapData = new ILOGMapData();
//			mapData.map(action, applicationM, userM, result, response);
			
		return result;
	}
}
