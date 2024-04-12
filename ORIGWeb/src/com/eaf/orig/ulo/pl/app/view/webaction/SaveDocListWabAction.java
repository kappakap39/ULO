package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentSetDataM;
import com.eaf.xrules.shared.constant.PLXrulesConstant;

public class SaveDocListWabAction extends WebActionHelper implements WebAction{
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Event toEvent() {		
		return null;
	}

	@Override
	public boolean requiredModelRequest() {	
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {
		return false;
	}

	@Override
	public boolean preModelRequest(){		
		try{						
			logger.debug("Save Doclist >> ");
			
			ArrayList<String> docList = new ArrayList<String>();
			
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());			
			PLDocumentDataM documentM = (PLDocumentDataM) getRequest().getSession().getAttribute("documentDataM");
			
			UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
			
			if(null == documentM){
				documentM = new PLDocumentDataM();			
			}
			
			Vector<PLDocumentSetDataM> docMainSetVect = documentM.getDocumentSetVect();	
			if(null == docMainSetVect){
				docMainSetVect = new Vector<PLDocumentSetDataM>();
				documentM.setDocumentSetVect(docMainSetVect);
			}
			
			Vector<PLDocumentCheckListDataM> docOtherVect = documentM.getDocumentOtherVect();
			if(null == docOtherVect){
				docOtherVect = new Vector<PLDocumentCheckListDataM>();
				documentM.setDocumentOtherVect(docOtherVect);
			}
						
			Vector<PLDocumentCheckListDataM> appDocChkVect = applicationM.getDocCheckListVect();			
			if(null == appDocChkVect){
				appDocChkVect = new Vector<PLDocumentCheckListDataM>();
				applicationM.setDocCheckListVect(appDocChkVect);
			}
			
			Vector<PLDocumentCheckListDataM> docMasterVect = null;
			String docRecive = null;
			if(!OrigUtil.isEmptyVector(docMainSetVect)){				
				for(PLDocumentSetDataM docSetM :docMainSetVect){						
					docMasterVect = new Vector<PLDocumentCheckListDataM>();							
					docMasterVect = docSetM.getDocumentVect();	
					if(!OrigUtil.isEmptyVector(docMasterVect)){								
						for(PLDocumentCheckListDataM docCheckListM :docMasterVect){											
							if(!OrigUtil.isEmptyString(docCheckListM.getDocCode())){
								docRecive = getRequest().getParameter("radio_"+docCheckListM.getDocCode());
								if(!OrigUtil.isEmptyString(docRecive) && !HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc.equals(docRecive)){
									docList.add(docCheckListM.getDocCode());
									this.SetDocumentCheckListDataM(docCheckListM,userM,applicationM,getRequest());	
								}else{
									continue;
								}										
							}
						}							
					}				
				}			
			}		
			
			if(!OrigUtil.isEmptyVector(docOtherVect)){				
				for(PLDocumentCheckListDataM docCheckListM :docOtherVect){					
					if(!OrigUtil.isEmptyString(docCheckListM.getDocCode())){
						docRecive = getRequest().getParameter("radio_"+docCheckListM.getDocCode());
//						#SepteMWi Comment
//						if(!OrigUtil.isEmptyString(docRecive) && !HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc.equals(docRecive)){
							docList.add(docCheckListM.getDocCode());
							this.SetDocumentCheckListDataM(docCheckListM,userM,applicationM,getRequest());		
//						}else{
//							continue;
//						}									
					}
				}					
			}
			
			this.RemoveDocument(applicationM, docList);
			
			String docFinalStaus = getRequest().getParameter("doc-final-status");
			String docMemo = getRequest().getParameter("doc-demo");
			ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
						
			JsonObjectUtil jObjectUtil = new JsonObjectUtil();
			
			String docResultField = "result_"+PLXrulesConstant.ModuleService.DOC_LIST;
			String docCodeField = "code_"+PLXrulesConstant.ModuleService.DOC_LIST;
			
			String docStatusResult = cacheUtil.getNaosCacheDisplayNameDataM(65, docFinalStaus);
			
			if(PLXrulesConstant.DocStatus.DOC_COMPLATE_AFTER_TRACK.equals(docFinalStaus)
					|| PLXrulesConstant.DocStatus.DOC_COMPLATE_BEFORE_TRACK.equals(docFinalStaus)
						|| PLXrulesConstant.DocStatus.DOC_NOTCOMPLATE.equals(docFinalStaus)){
				docStatusResult = docStatusResult+"("+userM.getCurrentRole()+")";
			}
			
			applicationM.setDocListResultCode(docFinalStaus);
			applicationM.setDocListResult(docStatusResult);
			applicationM.setDocListNotepad(docMemo);
			
			jObjectUtil.CreateJsonObject(docCodeField,docFinalStaus);
			jObjectUtil.CreateJsonObject(docResultField,docStatusResult);
			
			jObjectUtil.ResponseJsonArray(getResponse());
			
		} catch (Exception e) {
			logger.fatal("Error : ",e);
		}	
		return true;
	}	
	public void RemoveDocument(PLApplicationDataM applicationM ,ArrayList<String> docList){
//		#SeptemWi Comment
//		if(null != docList){
//			for(String docCode : docList) {
//				if(!OrigUtil.isEmptyString(docCode)){
//					this.RemoveDocumentCheckListDataM(applicationM, docCode);
//				}
//			}
//		}	
		Vector<PLDocumentCheckListDataM> docVect = applicationM.getDocCheckListVect();
		if(!OrigUtil.isEmptyVector(docVect)){
			for(int i = docVect.size()-1; i >= 0; --i){
				PLDocumentCheckListDataM docM = (PLDocumentCheckListDataM)docVect.get(i);
				if(!FoundDoc(docM,docList)){
					docVect.removeElementAt(i);
				}
			}
		}
	}
	public boolean FoundDoc(PLDocumentCheckListDataM docM,ArrayList<String> docList){
		if(null == docM) return false;
		for(String doc : docList){
			if(null != docM.getDocCode() && docM.getDocCode().equals(doc)){
				return true;
			}
		}
		return false;
	}
	public void RemoveDocumentCheckListDataM(PLApplicationDataM applicationM , String docCode){
		Vector<PLDocumentCheckListDataM> docVect = applicationM.getDocCheckListVect();
		if(!OrigUtil.isEmptyVector(docVect)){
			for(int i = docVect.size()-1; i >= 0; --i){
				PLDocumentCheckListDataM docM = (PLDocumentCheckListDataM)docVect.get(i);
				if(null != docM && null != docM.getDocCode()
							&& docM.getDocCode().equals(docCode)){
					docVect.remove(i);
				}				
			}
		}
	}
	public void SetDocumentCheckListDataM(PLDocumentCheckListDataM docCheckListM 
											,UserDetailM userM, PLApplicationDataM applicationM
												,HttpServletRequest request) throws Exception{
		
		if(null == applicationM){
			applicationM = new PLApplicationDataM();
		}
		
		String docCode = docCheckListM.getDocCode();		
		String receive = getRequest().getParameter("radio_"+docCode);		
		String reMarkDoc = getRequest().getParameter("remark_"+docCode);	
		
		PLDocumentCheckListDataM docListM = applicationM.GetPLDocumentCheckListDataM(docCode);
		
		docListM.setReceive(receive);
		docListM.setRemark(reMarkDoc);
		
		if(null == docListM.getCreateBy())	{
			docListM.setCreateBy(userM.getUserName());
		}
		docListM.setUpdateBy(userM.getUserName());
		
		PLDocumentCheckListReasonDataM docReasonM = null;
		
		Vector<PLDocumentCheckListReasonDataM> docReasonVect = docCheckListM.getDocCkReasonVect();		
		
		docListM.setDocCkReasonVect(new Vector<PLDocumentCheckListReasonDataM>());
		
		String docReason = null;
		if(!OrigUtil.isEmptyVector(docReasonVect)){			
			for(PLDocumentCheckListReasonDataM docReasonMs :docReasonVect){					
				docReasonM = new PLDocumentCheckListReasonDataM();								
				docReason = getRequest().getParameter("checkBox_"+docReasonMs.getDocCode()+"_"+docReasonMs.getDocReasonID());				
				if(OrigUtil.isEmptyString(docReason))
					continue;
				docReasonM.setDocCheckListId(docListM.getDocCheckListId());
				docReasonM.setDocCode(docReasonMs.getDocCode());					
				docReasonM.setDocReasonID(docReasonMs.getDocReasonID());
				docListM.add(docReasonM);					
			}			
		}		
	}	
	@Override
	public int getNextActivityType() {		
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken(){
		return false;
	}	
}
