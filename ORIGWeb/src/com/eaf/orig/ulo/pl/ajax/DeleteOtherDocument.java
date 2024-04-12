package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DocumentTool;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentDataM;


public class DeleteOtherDocument implements AjaxDisplayGenerateInf{	
	static Logger logger = Logger.getLogger(DeleteOtherDocument.class);		
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String [] checkboxOtherDoc = request.getParameterValues("otherdoc-checkbox");		
		PLDocumentDataM docM = (PLDocumentDataM)request.getSession().getAttribute("documentDataM");		
		if(docM == null){
			docM = new PLDocumentDataM();
		}		
		JsonObjectUtil jObj  = new JsonObjectUtil();		
		Vector<PLDocumentCheckListDataM> docCheckVect = docM.getDocumentOtherVect();		
				
		if(null != checkboxOtherDoc && checkboxOtherDoc.length >0){				
			for(int i = 0 ; i < checkboxOtherDoc.length ; i++){
				if(!OrigUtil.isEmptyVector(docCheckVect)){						
					for( int j = docCheckVect.size()-1; j >= 0; --j){									
						PLDocumentCheckListDataM docCheckLisM = (PLDocumentCheckListDataM) docCheckVect.get(j);
						if(null != docCheckLisM.getDocCode()&& docCheckLisM.getDocCode().equals(checkboxOtherDoc[i])){
							docCheckVect.removeElementAt(j);					
						}								
					}						
				}				
			}			
		}		
		
		DocumentTool docUtil = new DocumentTool();			
		Vector docCacheVect = docUtil.getDocumentList(docM);	
		
		jObj.CreateJsonObject("div-otherdoc-nev", this.DisplayOtherDocument(docCheckVect));	
			
		jObj.CreateJsonObject("other-doc-element",HTMLRenderUtil.displaySelectTagScriptActionAndCode_ORIG(docCacheVect,"","doc-others-list",DisplayFormatUtil.DISPLAY_MODE_EDIT,""));
		
		return jObj.returnJson();
	}
	
	public String DisplayOtherDocument(Vector<PLDocumentCheckListDataM> docCheckVect){
		StringBuilder str = new StringBuilder();
		str.append("<div class='div-otherdoc-popup'>");
			str.append("<table class='TableFrame' id='table-other-doc'>");
				AddOtherDocument otherDocTool = new AddOtherDocument();
				if(!OrigUtil.isEmptyVector(docCheckVect)){	
					for(int i=0; i<docCheckVect.size(); i++){																	
						PLDocumentCheckListDataM docListM = (PLDocumentCheckListDataM) docCheckVect.get(i);	
						str.append(otherDocTool.displayTDOtherDoc(docListM, i));
					}
				}else{
					str.append(displayNoRecordFound());
				}
			str.append("</table>");
		str.append("</div>");
		return str.toString();
	}
	
	public String displayNoRecordFound(){		
		StringBuilder str = new StringBuilder();
			str.append("<tr class=\"ResultNotFound\" id=\"doclist-notfound\"><td colspan=\"8\">Result Not Found</td></tr>");
		return str.toString();
	}
}
