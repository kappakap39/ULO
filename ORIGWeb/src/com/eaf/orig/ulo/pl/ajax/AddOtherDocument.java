package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.j2ee.pattern.util.DisplayFormatUtil;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DocumentCacheTool;
import com.eaf.orig.ulo.pl.app.utility.DocumentTool;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentDataM;

public class AddOtherDocument implements AjaxDisplayGenerateInf{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public String getDisplayObject(HttpServletRequest request) throws PLOrigApplicationException {
		
		String docID = request.getParameter("docID");		
		logger.debug("[getDisplayObject]..docID "+docID);	
		
		PLDocumentDataM documentM = (PLDocumentDataM)request.getSession().getAttribute("documentDataM");		
		if(null == documentM)documentM = new PLDocumentDataM();
				
		DocumentCacheTool docCacheUtil = new DocumentCacheTool();
			
		PLDocumentCheckListDataM docListM =  new PLDocumentCheckListDataM();
		
		CacheDataM docCacheM =  docCacheUtil.getDocumentByCode(docID);
		
		docListM.setDocCode(docCacheM.getCode());
		docListM.setDocDesc(docCacheM.getThDesc());
		docListM.setReceive(HTMLRenderUtil.RadioBoxCompare.TrackDoc);
		docListM.setDocCkReasonVect(docCacheUtil.MappingDocReason(docID));
		
		documentM.add(docListM);
		
		int x = (null == documentM.getDocumentOtherVect())? 1 :documentM.getDocumentOtherVect().size();
		
		JsonObjectUtil jObjectUtil  = new JsonObjectUtil();
		
		DocumentTool docUtil = new DocumentTool();
		
		Vector docCacheVect = docUtil.getDocumentList(documentM);		
		
		jObjectUtil.CreateJsonObject("table-other-doc", displayTDOtherDoc(docListM, x));
		jObjectUtil.CreateJsonObject("other-doc-element",HTMLRenderUtil.displaySelectTagScriptActionAndCode_ORIG(docCacheVect,"","doc-others-list",DisplayFormatUtil.DISPLAY_MODE_EDIT,""));
		
		return jObjectUtil.returnJson();
	}
	
	public String displayTDOtherDoc(PLDocumentCheckListDataM docCkListM ,int x){
		
		StringBuilder str = new StringBuilder();	
				
		String radioName = "radio_"+docCkListM.getDocCode();
		
		String styleTr = (x%2==0)?"ResultEven":"ResultOdd";	
		
		if(HTMLRenderUtil.RadioBoxCompare.TrackDoc.equals(docCkListM.getReceive())){
			styleTr += " ResultBlue";
		}	
		
		str.append("<tr id=\""+docCkListM.getDocCode()+"\" class=\"Result-Obj "+styleTr+"\" valign=\"top\">");
		
		str.append("<td width=\"5px\"><div class=\"text-left\">");
		str.append(HTMLRenderUtil.displayCheckBoxValues(docCkListM.getDocCode(),"otherdoc-checkbox",HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,"checkBoxAll otherdoc-checkbox" ,""));
		str.append("</div></td>");
		
		str.append("<td width=\"25%\"><div class=\"text-left\">");
		str.append(HTMLRenderUtil.displayHTML((String) docCkListM.getDocDesc()));
		str.append("</div></td>");
			
		str.append("<td width=\"10%\">");
		str.append(HTMLRenderUtil.displayRadioDoc(docCkListM.getReceive(),HTMLRenderUtil.DISPLAY_MODE_EDIT,radioName,HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc,""));
		str.append("</td>");
		
		str.append("<td width=\"10%\">");
		str.append(HTMLRenderUtil.displayRadioDoc(docCkListM.getReceive(),HTMLRenderUtil.DISPLAY_MODE_EDIT,radioName,HTMLRenderUtil.RadioBoxCompare.ReceiveDoc,""));
		str.append("</td>");
		
		str.append("<td width=\"10%\">");
		str.append(HTMLRenderUtil.displayRadioDoc(docCkListM.getReceive(),HTMLRenderUtil.DISPLAY_MODE_EDIT,radioName,HTMLRenderUtil.RadioBoxCompare.OverrideDoc,"") );
		str.append("</td>");
		
		str.append("<td width=\"10%\">");
		str.append(HTMLRenderUtil.displayRadioDoc(docCkListM.getReceive(),HTMLRenderUtil.DISPLAY_MODE_EDIT,radioName,HTMLRenderUtil.RadioBoxCompare.TrackDoc,""));
		str.append("</td>");
		
		str.append("<td width=\"20%\">");

		if(!ORIGUtility.isEmptyVector(docCkListM.getDocCkReasonVect())){				
			String checkboxName = null;			
			str.append("<table  cellspacing=0 cellpadding=0 width=\"100%\" border=\"0\">");			
				for(PLDocumentCheckListReasonDataM docReasonM :docCkListM.getDocCkReasonVect()){						
						checkboxName = "checkBox_"+docReasonM.getDocCode()+"_"+docReasonM.getDocReasonID();
						str.append("<tr>");							
							str.append("<td width=\"5px\">");
							str.append(HTMLRenderUtil.displayCheckBox(docReasonM.getIsDocReason(),checkboxName,HTMLRenderUtil.COMPARE_CHECKBOX_VALUE,""));
							str.append("</td>");							
							str.append("<td  align=\"left\"><div class=\"text-left\">");
							str.append(HTMLRenderUtil.displayHTML((String)docReasonM.getDocReasonDesc()));
							str.append("</div></td>");
							
						str.append("</tr>");
				}				
			str.append("</table>");					
		}
		
		str.append("</td>");
		str.append("<td width=\"15%\">");
		str.append(HTMLRenderUtil.displayInputTextAreaTag(HTMLRenderUtil.replaceNull(docCkListM.getRemark()),"remark_"+docCkListM.getDocCode(),"3","20",HTMLRenderUtil.DISPLAY_MODE_EDIT,250));
		str.append("</td>");
		
		str.append("</tr>");
		
		return str.toString();
	}
	
}
