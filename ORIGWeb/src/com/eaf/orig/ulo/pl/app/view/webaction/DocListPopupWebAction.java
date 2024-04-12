package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.ArrayList;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.SerializeUtil;
import com.eaf.orig.ulo.pl.app.utility.DocumentCacheTool;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentCheckListReasonDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentDataM;
import com.eaf.orig.ulo.pl.model.app.PLDocumentSetDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class DocListPopupWebAction  extends WebActionHelper implements WebAction{
	Logger logger = Logger.getLogger(this.getClass());		
	@Override
	public Event toEvent(){
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
	public boolean preModelRequest() {
				
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		
		UserDetailM userM = (UserDetailM) getRequest().getSession().getAttribute("ORIGUser");
		
		PLDocumentDataM documentM = new PLDocumentDataM();
		
		String role = userM.getCurrentRole();
		
		String incomeType = getRequest().getParameter("applicant_radio");
		String customerType = getRequest().getParameter("customertype");
		
		PLPersonalInfoDataM  personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);		
		
		personalM.setApplicationIncomeType(incomeType);
		
		if(OrigUtil.isEmptyString(customerType))
			customerType = OrigConstant.CUSTOMER_TYPE_INDIVIDUAL;
		
		personalM.setCustomerType(customerType);
		
		logger.debug("[preModelRequest]..BusClass ID "+appM.getBusinessClassId());
		logger.debug("[preModelRequest]..Customer Type "+personalM.getCustomerType());
		logger.debug("[preModelRequest]..role "+role);
		logger.debug("[preModelRequest]..incomeType "+incomeType);
	
		DocumentCacheTool cacheUtil =  new DocumentCacheTool();				
		Vector<PLDocumentSetDataM> docVect = cacheUtil.LoadMasterDocument(appM.getBusinessClassId()
															, personalM.getCustomerType(), role, incomeType);				
	
		documentM.setDocumentSetVect(docVect);
		
		this.MapDocumentDataM(appM.getDocCheckListVect(), documentM);
		
		getRequest().getSession(true).setAttribute("documentDataM",documentM);
		
		return true;
	}
	
	public void MapDocumentDataM(Vector<PLDocumentCheckListDataM> docAppListVect,PLDocumentDataM documentM){
		if(null == docAppListVect) docAppListVect = new Vector<PLDocumentCheckListDataM>();
		if(null == documentM) documentM = new PLDocumentDataM();
		ArrayList<String> docActive = new ArrayList<String>();
		if(!OrigUtil.isEmptyVector(documentM.getDocumentSetVect())){			
			for(PLDocumentSetDataM docSetM :documentM.getDocumentSetVect()){
				this.MappDocumentCheckListDataM(docSetM,docAppListVect,docActive);
			}
		}	
		ArrayList<String> docList = new ArrayList<String>();		
		if(!OrigUtil.isEmptyVector(docAppListVect)){
			for(PLDocumentCheckListDataM docChkListM : docAppListVect) {
				if(!this.isExistMasterDoc(docChkListM, docActive)){
//					#SeptemWi comment not remove empty receive coc for other coc
//					if(OrigUtil.isEmptyString(docChkListM.getReceive())
//							|| HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc.equals(docChkListM.getReceive())) {
//						docList.add(docChkListM.getDocCode());
//						continue;
//					}
					this.MappOtherDocumentM(docChkListM, documentM);
				}
			}
		}	
		this.RemoveDocument(docAppListVect, docList);
	}
	
	public void RemoveDocument(Vector<PLDocumentCheckListDataM> docAppListVect,ArrayList<String> docList){
		if(null != docList){
			for(String docCode : docList) {
				if(!OrigUtil.isEmptyString(docCode)){
					logger.debug("RemoveDocument docCode >> "+docCode);
					this.RemoveDocumentCheckListDataM(docAppListVect, docCode);
				}
			}
		}		
	}
	public void RemoveDocumentCheckListDataM(Vector<PLDocumentCheckListDataM> docAppListVect, String docCode){
		if(!OrigUtil.isEmptyVector(docAppListVect)){
			for(int i = docAppListVect.size()-1; i >= 0; --i){
				PLDocumentCheckListDataM docM = (PLDocumentCheckListDataM)docAppListVect.get(i);
				if(null != docM && null != docM.getDocCode()
						&& docM.getDocCode().equals(docCode)){
					docAppListVect.remove(i);
				}				
			}
		}
	}
	
	public void MappOtherDocumentM(PLDocumentCheckListDataM docChkListM,PLDocumentDataM documentM){	
		PLDocumentCheckListDataM otherDocM = this.MapOtherDocCheckListM(docChkListM);
		this.MapOtherDocReasonM(docChkListM.getDocCkReasonVect(),otherDocM);
		documentM.add(otherDocM);
	}
	public PLDocumentCheckListDataM MapOtherDocCheckListM(PLDocumentCheckListDataM docChkListM){
		PLDocumentCheckListDataM dataM = (PLDocumentCheckListDataM)SerializeUtil.clone(docChkListM);
		if(null == dataM) dataM = new PLDocumentCheckListDataM();
		DocumentCacheTool docCacheUtil = new DocumentCacheTool();
		CacheDataM docCacheM =  docCacheUtil.getDocumentByCode(dataM.getDocCode());
			dataM.setDocDesc(docCacheM.getThDesc());
			dataM.setDocCkReasonVect(new Vector<PLDocumentCheckListReasonDataM>());
		return dataM;
	}
	public boolean isExistMasterDoc(PLDocumentCheckListDataM docChkListM,ArrayList<String> docActive){
		if(null == docActive) return false;
		for (String docCode : docActive) {
			if(docCode.equals(docChkListM.getDocCode()))
				return true;
		}
		return false;
	}
	public void MappDocumentCheckListDataM(PLDocumentSetDataM docSetM,Vector<PLDocumentCheckListDataM> docAppListVect,ArrayList<String>  docActive){
		if(!OrigUtil.isEmptyVector(docSetM.getDocumentVect())){
			for(PLDocumentCheckListDataM docChkListM : docSetM.getDocumentVect()){
				docActive.add(docChkListM.getDocCode());				
				PLDocumentCheckListDataM appDocListM = this.MapDocumentCheckListDataM(docChkListM, docAppListVect);
				this.MapDocCheckListReasonM(docChkListM,appDocListM);
			}
		}
	}
	public void MapDocCheckListReasonM(PLDocumentCheckListDataM docChkListM,PLDocumentCheckListDataM appDocListM){
		if(!OrigUtil.isEmptyVector(docChkListM.getDocCkReasonVect())){
			for(PLDocumentCheckListReasonDataM docReasonM : docChkListM.getDocCkReasonVect()){
				PLDocumentCheckListReasonDataM appDocReasonM = this.FindDocCheckListReasonM(appDocListM.getDocCkReasonVect()
																			, docReasonM.getDocCode(), docReasonM.getDocReasonID());
				docReasonM.setIsDocReason(appDocReasonM.getIsDocReason());
			}
		}
	}
	public PLDocumentCheckListDataM MapDocumentCheckListDataM(PLDocumentCheckListDataM docChkListM ,Vector<PLDocumentCheckListDataM> docAppListVect){
		PLDocumentCheckListDataM appDocListM = this.FindDocCheckListM(docAppListVect, docChkListM.getDocCode());	
		docChkListM.setReceive(appDocListM.getReceive());
		if(OrigUtil.isEmptyString(docChkListM.getReceive())) 
				docChkListM.setReceive(HTMLRenderUtil.RadioBoxCompare.NotReceiveDoc);
		docChkListM.setRemark(appDocListM.getRemark());
		return appDocListM;
	}
	
	public void MapOtherDocReasonM(Vector<PLDocumentCheckListReasonDataM> docReasonVect ,PLDocumentCheckListDataM otherDocM){
		DocumentCacheTool docUtil = new DocumentCacheTool();
		Vector<PLDocumentCheckListReasonDataM> reasonCacheVect = docUtil.MappingDocReason(otherDocM.getDocCode());
		if(!OrigUtil.isEmptyVector(reasonCacheVect)){
			for(PLDocumentCheckListReasonDataM docCacheM : reasonCacheVect) {
				PLDocumentCheckListReasonDataM docReasonM = this.FindDocCheckListReasonM(docReasonVect, docCacheM.getDocCode(), docCacheM.getDocReasonID());
					docCacheM.setIsDocReason(docReasonM.getIsDocReason());
				otherDocM.add(docCacheM);
			}
		}	
	}
	public PLDocumentCheckListDataM FindDocCheckListM(Vector<PLDocumentCheckListDataM> docChkListVect , String docCode){
			if(!OrigUtil.isEmptyVector(docChkListVect)){				
				for (PLDocumentCheckListDataM docChkListM : docChkListVect) {
					if(null != docChkListM &&  null != docChkListM.getDocCode() 
						&& docChkListM.getDocCode().equals(docCode)){	
						return docChkListM;
					}
				}
			}
		return new PLDocumentCheckListDataM();
	}
	
	public PLDocumentCheckListReasonDataM FindDocCheckListReasonM(Vector<PLDocumentCheckListReasonDataM> docReasonVect,String docCode,String docReasonID){
		if(!OrigUtil.isEmptyVector(docReasonVect)){
			for (PLDocumentCheckListReasonDataM docReasonM : docReasonVect){
				if(null != docReasonM && null != docReasonM.getDocCode() && null != docReasonM.getDocReasonID()
					&& docReasonM.getDocCode().equals(docCode)
						&& docReasonM.getDocReasonID().equals(docReasonID)){						
							docReasonM.setIsDocReason(HTMLRenderUtil.COMPARE_CHECKBOX_VALUE);
					return docReasonM;						
				}	
			}			
		}		
		return new PLDocumentCheckListReasonDataM();
	}
	
	@Override
	public int getNextActivityType() {		
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}	
}
