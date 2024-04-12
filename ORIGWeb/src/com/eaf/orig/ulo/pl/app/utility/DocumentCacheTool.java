package com.eaf.orig.ulo.pl.app.utility;

import java.util.HashMap;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.TableLookupCache;
import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.cache.properties.DocumentCheckListSetProperties;
import com.eaf.orig.cache.properties.DocumentListProperties;
import com.eaf.orig.cache.properties.DocumentReasonProperties;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.constant.CacheConstant;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentSetDataM;

public class DocumentCacheTool {		
	
	static Logger logger = Logger.getLogger(DocumentCacheTool.class);
		
	public Vector<PLDocumentSetDataM> LoadMasterDocument(String busClass ,String customerType, String role ,String incomeType){	
		  logger.debug("[LoadMasterDocument]..");
		  Vector<DocumentCheckListSetProperties> docSetVect =  this.getDocumentSet(busClass, customerType, role, incomeType);		  
		  Vector<PLDocumentSetDataM> docVect = this.MappingDocList(docSetVect);
		  return docVect;
	}
	
	public Vector<PLDocumentSetDataM> MappingDocList( Vector<DocumentCheckListSetProperties> docSetVect){		
		PLDocumentSetDataM docM = null;		        	
		Vector<PLDocumentSetDataM> docVect = new Vector<PLDocumentSetDataM>();		
		  if(!OrigUtil.isEmptyVector(docSetVect)){
		  	for(DocumentCheckListSetProperties docSetM : docSetVect){	
		  		docM = this.MapDocumentSetDataM(docSetM);	
		  		this.MapDocumentCheckListDataM(docSetM,docM);
		  		docVect.add(docM);					  			
		  	}			  	
		  }
		  
		return docVect;
	}
	public void MapDocumentCheckListDataM(DocumentCheckListSetProperties docSetM,PLDocumentSetDataM docM){
	    HashMap h = TableLookupCache.getCacheStructure();
        Vector<DocumentListProperties> docListCacheVect = (Vector) (h.get(CacheConstant.CacheName.DocList));	
		PLDocumentCheckListDataM docCkM = null;
		if(!ORIGUtility.isEmptyVector(docListCacheVect)){
			for(DocumentListProperties docListM : docListCacheVect){
				if(this.MathDocListType(docListM, docSetM)){
					docCkM = this.MapDocumentCheckListDataM(docListM);
					docCkM.setDocCkReasonVect(this.MappingDocReason(docListM.getDocCode()));
					docM.add(docCkM);
				}
			}
		}
	}
	
	public PLDocumentCheckListDataM MapDocumentCheckListDataM(DocumentListProperties docListM){
		PLDocumentCheckListDataM docCkM = new PLDocumentCheckListDataM();
			docCkM.setDocSetID(docListM.getDocSetID());
			docCkM.setDocCode(docListM.getDocCode());
			docCkM.setDocDesc(docListM.getThDesc());	
		return docCkM;
	}
	public boolean MathDocListType(DocumentListProperties docListM,DocumentCheckListSetProperties docSetM){
		if(docSetM.getDocSetID() == null || docListM.getDocSetID() == null)
			return false;
		if(docSetM.getDocSetID().equals(docListM.getDocSetID())
				&& CacheConstant.status.ACITVE.equals(docListM.getActiveStatus())){
			return true;
		}
		return false;
	}
	public PLDocumentSetDataM MapDocumentSetDataM(DocumentCheckListSetProperties docSetM){
		PLDocumentSetDataM docM = new PLDocumentSetDataM();
			docM.setDocSetID(docSetM.getDocSetID());  		
			docM.setDocSetDesc(docSetM.getDocSetName());  		
	  		docM.setRequireAtLast(docSetM.getRequireAtLeast());
  		return docM;
	}
	
	public Vector<PLDocumentCheckListReasonDataM> MappingDocReason(String docID){		
		 HashMap h = TableLookupCache.getCacheStructure();		 
		 Vector<DocumentReasonProperties> docReasonCacheVect = (Vector) (h.get(CacheConstant.CacheName.DocReason)); 
		 Vector<PLDocumentCheckListReasonDataM> data = new Vector<PLDocumentCheckListReasonDataM>();
	     if(!ORIGUtility.isEmptyVector(docReasonCacheVect)){	    	 
    	 	for(DocumentReasonProperties docReasonM:docReasonCacheVect){	    	 		
	 			if(null != docReasonM.getDocCode() && docReasonM.getDocCode().equals(docID)){	
	 				data.add(this.MappDocumentCheckListReasonM(docReasonM));
	 			}	  
    	 	}	    	 
	     }		
	     return data;
	}
	public PLDocumentCheckListReasonDataM MappDocumentCheckListReasonM(DocumentReasonProperties docReasonM){
		PLDocumentCheckListReasonDataM reasonM = new PLDocumentCheckListReasonDataM();	    	 				
			reasonM.setDocCode(docReasonM.getDocCode());
			reasonM.setDocReasonID(docReasonM.getReasonID());
			reasonM.setDocReasonDesc(docReasonM.getReasonDesc());
		return reasonM;
	}
	public Vector<DocumentCheckListSetProperties> getDocumentSet(String busClass ,String customerType, String role ,String incomeType){				
        HashMap h = TableLookupCache.getCacheStructure(); 
        Vector<DocumentCheckListSetProperties> docSetCacheVect = null;        
        if(OrigUtil.isEmptyString(incomeType)){
        	docSetCacheVect = (Vector)(h.get(CacheConstant.CacheName.DocCheckListSet));    
        }else{
        	docSetCacheVect = (Vector)(h.get(CacheConstant.CacheName.DocCheckListSetInc));    
        }         
        Vector<DocumentCheckListSetProperties> docSetVect = new Vector<DocumentCheckListSetProperties>(); 
        if(ORIGUtility.isEmptyString(busClass)) busClass = "ALL_ALL_ALL";
    	if(!ORIGUtility.isEmptyVector(docSetCacheVect)){    		
			for(DocumentCheckListSetProperties docSetM:docSetCacheVect){    
				if(this.ValidateActiveDocCkList(docSetM , incomeType) 
						&& this.ValidateMatchType(docSetM, customerType, role, incomeType)){  
					if(ORIGCacheUtil.isMatchBusClass(docSetM.getBusClassID(),busClass)){    	   									
						docSetVect.add(docSetM);
					}				
				}
			}
    	}    	
    	return docSetVect;   
	}
	
	public boolean ValidateMatchType(DocumentCheckListSetProperties docSetM,String customerType, String role ,String incomeType){
		if(!OrigUtil.isEmptyString(incomeType)){
			if(docSetM.getCustomerType() == null 
				|| docSetM.getIncomeType() == null 
					|| docSetM.getRole() == null)
				return false;
			if(docSetM.getCustomerType().equals(customerType)
					&& docSetM.getIncomeType().equals(incomeType)
						&& docSetM.getRole().equals(role)){   
				return true;
			}
		}else{
			if(docSetM.getCustomerType() == null
						|| docSetM.getRole() == null)
					return false;
			if(docSetM.getCustomerType().equals(customerType)
					&& docSetM.getRole().equals(role)){   
				return true;
			}
		}
		return false;	
	}	
	public boolean ValidateActiveDocCkList(DocumentCheckListSetProperties docSetM , String incomeType){	
		if(OrigUtil.isEmptyString(incomeType)){
			if(CacheConstant.status.ACITVE.equals(docSetM.getDocSetStatus())
						&& CacheConstant.status.ACITVE.equals(docSetM.getDocCkListStatus())
								&& CacheConstant.status.ACITVE.equals(docSetM.getDocCkListRoleStatus())
										&& CacheConstant.status.ACITVE.equals(docSetM.getDocCkListDetailStatus())){
			return true;
		}
		}else{
			if(CacheConstant.status.ACITVE.equals(docSetM.getDocCkListIncStatus())
						&& CacheConstant.status.ACITVE.equals(docSetM.getDocSetStatus())
							&& CacheConstant.status.ACITVE.equals(docSetM.getDocCkListStatus())
									&& CacheConstant.status.ACITVE.equals(docSetM.getDocCkListRoleStatus())
											&& CacheConstant.status.ACITVE.equals(docSetM.getDocCkListDetailStatus())){
				return true;
			}
		}
		return false;
	}
	
	public Vector<CacheDataM> getDocumentCacheM(){		
		  HashMap h = TableLookupCache.getCacheStructure();	        
	      Vector<CacheDataM> docCacheVect = (Vector) (h.get(CacheConstant.CacheName.Document));	      
	      return (Vector<CacheDataM>)SerializeUtil.clone(docCacheVect);
	}
	
	public CacheDataM getDocumentByCode(String docCode){
		if(ORIGUtility.isEmptyString(docCode))
			return new CacheDataM();		
		 Vector<CacheDataM> docCacheVect =  this.getDocumentCacheM();		 
		 if(!ORIGUtility.isEmptyVector(docCacheVect)){
			 for (CacheDataM cacheM : docCacheVect) {									
				if(CacheConstant.status.ACITVE.equals(cacheM.getActiveStatus())
						&& cacheM.getCode().equalsIgnoreCase(docCode)){
					return cacheM;
				}						
			 }
		 }		 
		 return new CacheDataM();
	}
	public static String getDocumentReasonByCode(String docCode ,String docReasonCode){
		 HashMap h = TableLookupCache.getCacheStructure();
		 Vector<DocumentReasonProperties> docReasonCacheVect = (Vector) (h.get(CacheConstant.CacheName.DocReason));
		 if(!ORIGUtility.isEmptyVector(docReasonCacheVect)){
			 for(DocumentReasonProperties docReason:docReasonCacheVect){
				 if(docReason.getDocCode().equalsIgnoreCase(docCode)&&
						 docReason.getReasonID().equalsIgnoreCase(docReasonCode)){
					 return docReason.getReasonDesc();
				 }
			 }
		 }		 
		 return "";
	}
}
