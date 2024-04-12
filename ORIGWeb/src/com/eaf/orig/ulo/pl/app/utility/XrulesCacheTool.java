package com.eaf.orig.ulo.pl.app.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.xrules.cache.data.TableLookupCache;
import com.eaf.xrules.cache.data.XRulesCacheUtil;
import com.eaf.xrules.cache.model.MTDisplayDetailDataM;
import com.eaf.xrules.cache.model.MTDisplayGroupDataM;
import com.eaf.xrules.cache.model.MTModuleServiceCacheDataM;
import com.eaf.xrules.cache.model.MTRqModuleServiceCacheDataM;
import com.eaf.xrules.cache.model.MTServiceDataM;
import com.eaf.xrules.cache.model.SensitiveFieldCacheDataM;
import com.eaf.xrules.cache.model.SensitiveGroupCacheDataM;
import com.eaf.xrules.ulo.pl.dao.util.XRulesTool;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

public class XrulesCacheTool extends XRulesCacheUtil{
	Logger logger = Logger.getLogger(this.getClass());
	public XrulesCacheTool(){
		super();
	}
	public Vector<MTDisplayGroupDataM> GetMatrixDisplay(XrulesRequestDataM requestM){		
		Vector<MTDisplayGroupDataM> mtGroupVect = new Vector<MTDisplayGroupDataM>();		
		String matrixID = this.GetMatrixServiceID(requestM);
		logger.debug("GetMatrixDisplay matrixID >> "+matrixID);
		if(null != matrixID){
			this.SetMtDisplayGroup(matrixID, mtGroupVect);
		}		
//		logger.debug("GetMatrixDisplay mtGroupVect >> "+mtGroupVect);
		return mtGroupVect;
	}
	private void SetMtDisplayGroup(String matrixID ,Vector<MTDisplayGroupDataM>  mtGroupVect){
		MTDisplayGroupDataM mtDisplayM = null;
		HashMap cache = TableLookupCache.getCacheStructure();
		Vector<MTDisplayGroupDataM> vect = (Vector)cache.get("MTDisplayGroupCache");
		if(!XRulesTool.isEmptyVector(vect)){
			for(MTDisplayGroupDataM groupM : vect) {
				if(null != groupM && groupM.getMatrixServiceID().equals(matrixID)){
					mtDisplayM = new MTDisplayGroupDataM();				
					mtDisplayM.setMatrixServiceID(groupM.getMatrixServiceID());
					mtDisplayM.setGroupDisplayCode(groupM.getGroupDisplayCode());
					mtDisplayM.setGroupLabel(groupM.getGroupLabel());
					mtDisplayM.setSequence(groupM.getSequence());					 
					this.SetMtDisplayDetail(mtDisplayM);
					mtGroupVect.add(mtDisplayM);
				}
			}
		}
	}
	private void SetMtDisplayDetail(MTDisplayGroupDataM mtDisplayM){
		HashMap cache = TableLookupCache.getCacheStructure();
		Vector<MTDisplayDetailDataM> vect = (Vector)cache.get("MTDisplayDetailCache");
		if(!XRulesTool.isEmptyVector(vect)){
			for(MTDisplayDetailDataM detailM : vect) {
				if(null != detailM && detailM.getMatrixServiceID().equals(mtDisplayM.getMatrixServiceID()) 
					&& detailM.getGroupDisplayCode().equals(mtDisplayM.getGroupDisplayCode())){				
					mtDisplayM.add(detailM);
				}
			}
		}
	}
	public String GetMatrixServiceID(XrulesRequestDataM requestM){
		String groupID = MTJobStateGroupCache.getCacheDataMs(requestM.getJobState());
		logger.debug(" groupID >> "+groupID);
		if(null != groupID){
			HashMap cache = TableLookupCache.getCacheStructure();
			Vector<MTServiceDataM> vect = (Vector)cache.get("MTServiceCache");
			if(!XRulesTool.isEmptyVector(vect)){
				for(MTServiceDataM mtServiceM : vect) {
					if(mtServiceM.getBusClass().equals(requestM.getBusClass())
						&& mtServiceM.getCardType().equals(requestM.getCardType())
							&& mtServiceM.getCustomerType().equals(requestM.getCustomerType())
								&& mtServiceM.getGroupID().equals(groupID)
									&& mtServiceM.getModuleType().equals(requestM.getModuleType())){
						return mtServiceM.getMatrixServiceID();
					}
				}
			}
		}
		return null;
	}
	public Vector<MTRqModuleServiceCacheDataM> GetExecuteModuleService(XrulesRequestDataM requestM){
		Vector<MTRqModuleServiceCacheDataM>  data = new Vector<MTRqModuleServiceCacheDataM>();
		String matrixID = this.GetMatrixServiceID(requestM);
		logger.debug("GetMatrixDisplay matrixID >> "+matrixID);
			if(null != matrixID){
				HashMap cache = TableLookupCache.getCacheStructure();
				Vector<MTRqModuleServiceCacheDataM> vect = (Vector)cache.get("MTRqModuleServiceCache");
				if(!XRulesTool.isEmptyVector(vect)){
					for(MTRqModuleServiceCacheDataM rqServiceM : vect) {
						if(rqServiceM.getxRulesServiceID().equals(matrixID)
							&& rqServiceM.getActionExecute().equals(requestM.getActionExecute())){
								data.add(rqServiceM);
						}
					}
				}
			}	
		return data;
	}
	
	public MTModuleServiceCacheDataM GetExecuteManualService(XrulesRequestDataM requestM){
		HashMap cache = TableLookupCache.getCacheStructure();
		Vector<MTModuleServiceCacheDataM> vect = (Vector)cache.get("MTModuleServiceCache");
		if(!XRulesTool.isEmptyVector(vect)){
			for(MTModuleServiceCacheDataM objM : vect) {
				if(objM.getServiceID() == requestM.getServiceID()){
					return objM;
				}
			}
		}
		return null;
	}
	
	public Vector<SensitiveFieldCacheDataM>  GetMandatorySensitiveField(XrulesRequestDataM requestM){
		Vector<SensitiveFieldCacheDataM> data = new Vector<SensitiveFieldCacheDataM>();
		String matrixID = this.GetMatrixServiceID(requestM);
		logger.debug("GetMatrixDisplay matrixID >> "+matrixID);	
			if(null != matrixID){
				HashMap cache = TableLookupCache.getCacheStructure();
				Vector<SensitiveFieldCacheDataM> vect = (Vector)cache.get("MandatoryXrules");
				if(!XRulesTool.isEmptyVector(vect)){
					for(SensitiveFieldCacheDataM cacheM : vect){
						if(cacheM.getMatrixServiceID().equals(matrixID)
								&& cacheM.getActionExecute().equals(requestM.getActionExecute())){
							data.add(cacheM);
						}
					}
				}
			}
		Vector<SensitiveFieldCacheDataM>  senVect = new Vector<SensitiveFieldCacheDataM>();
		try{
			ExecuteServiceManager execute = ORIGEJBService.getExecuteServiceManager();
			ArrayList<Integer> serviceList = execute.GetServiceExecute(requestM);
			senVect = this.RemoveNotExecuteService(serviceList, data);
		}catch (Exception e){
			logger.fatal("Exception ",e);
		}		
		return this.RemoveFoundField(senVect);
	}
	
	public Vector<SensitiveFieldCacheDataM> RemoveNotExecuteService(ArrayList<Integer> serviceList , Vector<SensitiveFieldCacheDataM> data){
		Vector<SensitiveFieldCacheDataM> senVect = new Vector<SensitiveFieldCacheDataM>();
		if(!XRulesTool.isEmptyVector(data)){
			for(SensitiveFieldCacheDataM dataM : data) {
				if(this.ValidateServiceExecute(dataM.getServiceID(), serviceList)){
					senVect.add(dataM);
				}
			}
		}
//		logger.debug("ExecuteService >> senVect "+senVect.size());
		return senVect;
	}
	
	public boolean ValidateServiceExecute(int serviceID , ArrayList<Integer> serviceList){
		if(null != serviceList){
			for(Integer obj :serviceList){
				if(obj == serviceID){
					return true;
				}
			}
		}
		return false;
	}
	
	public Vector<SensitiveFieldCacheDataM> RemoveFoundField( Vector<SensitiveFieldCacheDataM> sensitiveVect){		
		Vector<SensitiveFieldCacheDataM> data = new Vector<SensitiveFieldCacheDataM>();				
		if(!XRulesTool.isEmptyVector(sensitiveVect)){
			for (SensitiveFieldCacheDataM sensitiveM :sensitiveVect){			
				 if(!isFoundSensitiveField(sensitiveM.getFieldName(), data)){
					 data.add(sensitiveM);
				 }						
			}			
		}				
		return data;
	}	
	public boolean isFoundSensitiveField(String fieldName,Vector<SensitiveFieldCacheDataM> sensitiveData){		
		if(!XRulesTool.isEmptyVector(sensitiveData)){			
			for(SensitiveFieldCacheDataM sensitiveM :sensitiveData){
				 if(null != sensitiveM.getFieldName() 
						 		&& sensitiveM.getFieldName().equals(fieldName)){
					 return true;						
				 }
			}				
		}
		return false;		
	}
	public String GetMessageMandatory(String fieldName){
		HashMap cache = TableLookupCache.getCacheStructure();
		Vector<SensitiveFieldCacheDataM> vect = (Vector)cache.get("MandatoryMessage");
		if(!XRulesTool.isEmptyVector(vect)){
			for(SensitiveFieldCacheDataM cacheM : vect){
				if(cacheM.getFieldName().equals(fieldName)){
					return cacheM.getMessage();
				}
			}
		}
		return "";
	}
	public Vector<SensitiveGroupCacheDataM> GetSensitiveGroupCache(String fieldName){
		HashMap cache = TableLookupCache.getCacheStructure();		
		Vector<SensitiveGroupCacheDataM> vect = (Vector)cache.get("SensitiveGroupCache");
		Vector<SensitiveGroupCacheDataM> data = new Vector<SensitiveGroupCacheDataM>();
		if(!XRulesTool.isEmptyVector(vect)){
			for(SensitiveGroupCacheDataM dataM : vect){
				if(dataM.getFieldName().equals(fieldName)){
					data.add(dataM);
				}
			}
		}
		return data;
	}
	public Vector<SensitiveFieldCacheDataM> GetSensitiveFieldService(XrulesRequestDataM requestM){
		Vector<SensitiveFieldCacheDataM> data = new Vector<SensitiveFieldCacheDataM>();
		String matrixID = this.GetMatrixServiceID(requestM);
		logger.debug("GetMatrixDisplay matrixID >> "+matrixID);
		if(null != matrixID){
			HashMap cache = TableLookupCache.getCacheStructure();
			Vector<SensitiveFieldCacheDataM> vect = (Vector)cache.get("SensitiveFieldCache");
			if(!XRulesTool.isEmptyVector(vect)){
				for(SensitiveFieldCacheDataM dataM : vect){
					if(dataM.getMatrixServiceID().equals(matrixID)
						&&dataM.getFieldName().equals(requestM.getFieldName())){
						data.add(dataM);
					}
				}
			}
		}
		return data;
	}
	
	public Vector<SensitiveFieldCacheDataM> GetSensitiveField(XrulesRequestDataM requestM){
		Vector<SensitiveFieldCacheDataM> data = new Vector<SensitiveFieldCacheDataM>();
		String matrixID = this.GetMatrixServiceID(requestM);
		logger.debug("GetMatrixDisplay matrixID >> "+matrixID);
		if(null != matrixID){
			HashMap cache = TableLookupCache.getCacheStructure();
			Vector<SensitiveFieldCacheDataM> vect = (Vector)cache.get("SensitiveFieldCache");
			if(!XRulesTool.isEmptyVector(vect)){
				for(SensitiveFieldCacheDataM dataM : vect){
					if(dataM.getMatrixServiceID().equals(matrixID)){
						data.add(dataM);
					}
				}
			}
		}
		return data;
	}
	
	public Vector<SensitiveGroupCacheDataM> getSenstiive(){
//		HashMap cache = TableLookupCache.getCacheStructure();		
//		Vector<SensitiveGroupCacheDataM> vect = (Vector)cache.get("SensitiveGroupCache");
//		Vector<SensitiveGroupCacheDataM> data = new Vector<SensitiveGroupCacheDataM>();
//		if(!XRulesTool.isEmptyVector(vect)){
//			for(SensitiveGroupCacheDataM dataM : vect){
//				data.add(dataM);
//			}
//		}
//		return data;
		HashMap cache = TableLookupCache.getCacheStructure();		
		return (Vector)cache.get("SensitiveGroupCache");
	}
	
}
