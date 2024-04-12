package com.eaf.orig.ulo.pl.ajax;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DocumentTool;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentSetDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;

public class MandatoryDocCheckList implements AjaxDisplayGenerateInf{
	Logger logger = Logger.getLogger(MandatoryDocCheckList.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		String docFinalStaus = request.getParameter("doc-final-status");
		
		PLDocumentDataM documentM = (PLDocumentDataM) request.getSession().getAttribute("documentDataM");		
		if(null == documentM) documentM = new PLDocumentDataM();
			
		Vector<PLDocumentSetDataM> docMainSetVect = 	documentM.getDocumentSetVect();	
		if(null == docMainSetVect){
			docMainSetVect = new Vector<PLDocumentSetDataM>();
			documentM.setDocumentSetVect(docMainSetVect);
		}
		
		Vector<PLDocumentCheckListDataM> docOtherVect = documentM.getDocumentOtherVect();
		if(null == docOtherVect){
			docOtherVect = new Vector<PLDocumentCheckListDataM>();
			documentM.setDocumentOtherVect(docOtherVect);
		}
		
		Vector<PLDocumentCheckListDataM> docMasterVect = null;
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		if(!OrigUtil.isEmptyVector(docMainSetVect)){				
			for(PLDocumentSetDataM docSetM :docMainSetVect){
				docMasterVect = new Vector<PLDocumentCheckListDataM>();							
				docMasterVect = docSetM.getDocumentVect();	
				if(!OrigUtil.isEmptyVector(docMasterVect)){								
					for(PLDocumentCheckListDataM docCheckListM :docMasterVect){											
						if(!OrigUtil.isEmptyString(docCheckListM.getDocCode())){
							this.MandatoryDocument(request, docCheckListM, jObj);									
						}
					}							
				}
			}			
		}	
		
		if(!OrigUtil.isEmptyVector(docOtherVect)){				
			for(PLDocumentCheckListDataM docCheckListM :docOtherVect){
				if(!OrigUtil.isEmptyString(docCheckListM.getDocCode())){
					this.MandatoryDocument(request, docCheckListM, jObj);
				}
			}					
		}
		
		logger.debug("DocFinalStaus >> "+docFinalStaus);
		
		if(PLXrulesConstant.DocStatus.DOC_COMPLATE_BEFORE_TRACK.equals(docFinalStaus)
				|| PLXrulesConstant.DocStatus.DOC_COMPLATE_AFTER_TRACK.equals(docFinalStaus)){
			String msg = "";
			if(!OrigUtil.isEmptyVector(docMainSetVect)){				
				for(PLDocumentSetDataM docSetM :docMainSetVect){
					docMasterVect = new Vector<PLDocumentCheckListDataM>();							
					docMasterVect = docSetM.getDocumentVect();	
					if(!OrigUtil.isEmptyVector(docMasterVect)){	
						int require =  (null == docSetM.getRequireAtLast())?0:Integer.valueOf(docSetM.getRequireAtLast());
						int receive = this.GetReceiveDocument(request , docMasterVect);
						if(receive < require){
							msg = HTMLRenderUtil.displayHTML(docSetM.getDocSetDesc())+DocumentTool.GetWordRequireDocList(docSetM,request);
							jObj.CreateJsonMessage("error",msg);
						}
					}
				}			
			}
			
			boolean error = false;
			if(!OrigUtil.isEmptyVector(docMainSetVect)){				
				for(PLDocumentSetDataM docSetM :docMainSetVect){
					docMasterVect = new Vector<PLDocumentCheckListDataM>();							
					docMasterVect = docSetM.getDocumentVect();	
					if(!OrigUtil.isEmptyVector(docMasterVect)){								
						if(this.MandatorySaveDocumentStatus(request, docMasterVect)){
							error = true;
							break;
						}		
					}
				}			
			}
			if(!error){
				if(!OrigUtil.isEmptyVector(docOtherVect)){
					if(this.MandatorySaveOtherDocumentStatus(request, docOtherVect)){
						error = true;
					}
				}
			}
			if(error){
				jObj.CreateJsonMessage("error",ErrorUtil.getShortErrorMessage(request, "DOC_INFER_DOC_STATUS_FAIL"));
			}			
		}
		
		return jObj.returnJson();
	}
	
	public int GetReceiveDocument(HttpServletRequest request , Vector<PLDocumentCheckListDataM> docMasterVect){
		int count = 0;
		for(PLDocumentCheckListDataM docCheckListM : docMasterVect) {
			String receive = request.getParameter("radio_"+docCheckListM.getDocCode());
			if(HTMLRenderUtil.RadioBoxCompare.OverrideDoc.equals(receive)
				|| HTMLRenderUtil.RadioBoxCompare.ReceiveDoc.equals(receive)){
				count++;
			}
		}
		return count;
	}
	
	public boolean MandatorySaveDocumentStatus(HttpServletRequest request,Vector<PLDocumentCheckListDataM> docMasterVect){		
		for(PLDocumentCheckListDataM docCheckListM : docMasterVect) {
			String receive = request.getParameter("radio_"+docCheckListM.getDocCode());		
			if(HTMLRenderUtil.RadioBoxCompare.TrackDoc.equals(receive)){
				return true;
			}
		}
		return false;
	}
	public boolean MandatorySaveOtherDocumentStatus(HttpServletRequest request,Vector<PLDocumentCheckListDataM> docOtherVect){
		for (PLDocumentCheckListDataM docCheckListM : docOtherVect) {
			String receive = request.getParameter("radio_"+docCheckListM.getDocCode());
			if(OrigUtil.isEmptyString(receive) ||
				HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc.equals(receive)|| 
					HTMLRenderUtil.RadioBoxCompare.TrackDoc.equals(receive)){
				return true;
			}
		}
		return false;
	}
	public void MandatoryDocument(HttpServletRequest request,PLDocumentCheckListDataM docCheckListM,JsonObjectUtil jObj){
		String DOC_ERROR_PREFIX = ErrorUtil.getShortErrorMessage(request,"DOC_ERROR_PREFIX");
		String COMMENT		= ErrorUtil.getShortErrorMessage(request,"DOC_COMMENT_PREFIX");
		String REASON		= ErrorUtil.getShortErrorMessage(request,"DOC_REASON_PREFIX");
		String receive = request.getParameter("radio_"+docCheckListM.getDocCode());		
		String reMarkDoc = request.getParameter("remark_"+docCheckListM.getDocCode());	
		String msg = "";
		if(!OrigUtil.isEmptyString(receive) && !HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc.equals(receive)){
			if(HTMLRenderUtil.RadioBoxCompare.OverrideDoc.equals(receive) && OrigUtil.isEmptyString(reMarkDoc)){
				msg = DOC_ERROR_PREFIX+docCheckListM.getDocDesc()+" "+COMMENT;
				jObj.CreateJsonMessage("error",msg);
			}
			if(HTMLRenderUtil.RadioBoxCompare.TrackDoc.equals(receive)){
				Vector<PLDocumentCheckListReasonDataM> docReasonVect = docCheckListM.getDocCkReasonVect();
				if(!OrigUtil.isEmptyVector(docReasonVect)){
					if(!this.MandatoryDocReason(request,docReasonVect)){
						msg = DOC_ERROR_PREFIX+docCheckListM.getDocDesc()+" "+REASON;
						jObj.CreateJsonMessage("error", msg);
					}
				}
			}
		}
	}
	
	public boolean MandatoryDocReason(HttpServletRequest request , Vector<PLDocumentCheckListReasonDataM> docReasonVect){
		String docReason = null;
		for(PLDocumentCheckListReasonDataM reasonM : docReasonVect) {
			docReason = request.getParameter("checkBox_"+reasonM.getDocCode()+"_"+reasonM.getDocReasonID());
			if(!OrigUtil.isEmptyString(docReason)){
				return true;
			}
		}
		return false;
	}
	
}
