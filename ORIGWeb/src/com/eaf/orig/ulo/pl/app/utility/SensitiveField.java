package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;
import org.apache.log4j.Logger;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.xrules.cache.model.SensitiveFieldCacheDataM;
import com.eaf.xrules.cache.model.SensitiveGroupCacheDataM;

public class SensitiveField {
	
	Logger logger = Logger.getLogger(SensitiveField.class);
	@Deprecated
	public Vector<SensitiveFieldCacheDataM> getSensitiveFieldValidate(PLApplicationDataM plApplicationDataM ,String buttonID){								
//		PLOrigXrulesUtil plXruleUtil = new PLOrigXrulesUtil();			
//		PLXRulesDataM plXrulesM = plXruleUtil.mapPLXRulesModel(plApplicationDataM);			
//		PLXRulesServiceProxy plXrulesService = new PLXRulesServiceProxy();			
//		Vector<SensitiveFieldCacheDataM> sensitiveVect = plXrulesService.getSensitiveFieldValidate(plXrulesM, buttonID);
//		return removeDupicateFieldValidate(sensitiveVect);		
		return null;
	}	
	@Deprecated
	public Vector<SensitiveFieldCacheDataM> getSensitiveFieldName(PLApplicationDataM plApplicationM ,String fieldName){								
//		PLOrigXrulesUtil plXruleUtil = new PLOrigXrulesUtil();			
//		PLXRulesDataM plXrulesM = plXruleUtil.mapPLXRulesModel(plApplicationM);			
//		PLXRulesServiceProxy plXrulesService = new PLXRulesServiceProxy();		
//		Vector<SensitiveFieldCacheDataM> sensitiveVect = plXrulesService.getSensitiveFieldName(plXrulesM, fieldName);
//		return sensitiveVect;		
		return null;
	}
	@Deprecated
	public Vector<SensitiveGroupCacheDataM> getSensitiveGroupFieldName(String attrName){	
//		PLXRulesServiceProxy plXrulesService = new PLXRulesServiceProxy();		
//		Vector<SensitiveGroupCacheDataM> sensitiveVect = plXrulesService.getSensitiveGroupFieldName(attrName);
//		return sensitiveVect;			
		return null;
	}	
	@Deprecated
	public Vector<SensitiveFieldCacheDataM> getSensitiveFieldValidateByServiceID(PLApplicationDataM plApplicationDataM ,String serviceID){		
//		PLOrigXrulesUtil plXruleUtil = new PLOrigXrulesUtil();		
//		PLXRulesDataM plXrulesM = plXruleUtil.mapPLXRulesModel(plApplicationDataM);		
//		PLXRulesServiceProxy plXrulesService = new PLXRulesServiceProxy();		
//		Vector<SensitiveFieldCacheDataM> sensitiveVect = plXrulesService.getSensitiveFieldValidateByServiceID(plXrulesM,Long.parseLong(serviceID));		
//		return sensitiveVect;	
		return null;
	}	

		
}
