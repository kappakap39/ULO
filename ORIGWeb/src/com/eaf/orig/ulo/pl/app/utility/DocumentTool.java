package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.constant.CacheConstant;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentSetDataM;

public class DocumentTool {	
//	private Logger logger = Logger.getLogger(this.getClass());	
	public Vector<CacheDataM> getDocumentList(PLDocumentDataM documentDataM){			
			DocumentCacheTool docCacheUtil =  new DocumentCacheTool();
			Vector<CacheDataM> docCacheVect = (Vector<CacheDataM>) docCacheUtil.getDocumentCacheM();
			
			if(!ORIGUtility.isEmptyVector(documentDataM.getDocumentSetVect())){
				for(PLDocumentSetDataM docSetM :documentDataM.getDocumentSetVect()){
					if(!ORIGUtility.isEmptyVector(docSetM.getDocumentVect())){
						for(PLDocumentCheckListDataM mainDocCheckListM:docSetM.getDocumentVect()){
							docCacheVect = removeElement(docCacheVect, mainDocCheckListM.getDocCode());												
						}
					}
				}
			}						
			if(!ORIGUtility.isEmptyVector(documentDataM.getDocumentOtherVect())){	
				for(PLDocumentCheckListDataM otherDocCheckListM:documentDataM.getDocumentOtherVect()){
					docCacheVect = removeElement(docCacheVect, otherDocCheckListM.getDocCode());								
				}
			}						
			if(!ORIGUtility.isEmptyVector(docCacheVect)){				
				for(int i = docCacheVect.size()-1; i >= 0; --i){					
					CacheDataM cacheM = (CacheDataM) docCacheVect.get(i);							
						if(!CacheConstant.status.ACITVE.equalsIgnoreCase(cacheM.getActiveStatus())){
							docCacheVect.remove(i);
						}						
				}
			}						
//			logger.debug("[getDocumentList].. docCacheVect "+docCacheVect.size());
			return docCacheVect;
	}
	
	public Vector<CacheDataM> removeElement(Vector<CacheDataM> docCacheVect ,String docCode){
		if(!ORIGUtility.isEmptyVector(docCacheVect)){
			for(int i = docCacheVect.size()-1; i >= 0; --i){				
				CacheDataM cacheM = (CacheDataM) docCacheVect.get(i);				
				if(null != cacheM.getCode() 
						&& cacheM.getCode().equalsIgnoreCase(docCode)){
					docCacheVect.remove(i);
					break;
				}						
			}
		}
		return docCacheVect;
	}
	public static String GetWordRequireDocList(PLDocumentSetDataM docSetM ,HttpServletRequest request){
		StringBuilder str = new StringBuilder();
		int docList = (docSetM.getDocumentVect() == null)? 0:docSetM.getDocumentVect().size();
		if(docList == 0) return "";
		int requireDoc = (OrigUtil.isEmptyString(docSetM.getRequireAtLast()))? 0 :Integer.valueOf(docSetM.getRequireAtLast());
		if(requireDoc == 0) return "";
		if(requireDoc == docList){
			str.append("&nbsp;<div class=\"mandatory-box\">*</div>&nbsp;");
			str.append("(");
			str.append(PLMessageResourceUtil.getTextDescription(request, "DOC_REQUIREALL"));
			str.append(")");
		}else{
			str.append("&nbsp;<div class=\"mandatory-box\">*</div>&nbsp;");
			str.append("(");
			str.append(PLMessageResourceUtil.getTextDescription(request, "DOC_REQUIREDOC"));
			str.append("&nbsp;").append(requireDoc).append("&nbsp;");
			str.append(PLMessageResourceUtil.getTextDescription(request, "DOC_AMOUNT"));
			str.append(")");
		}			
		return str.toString();
	}
}
