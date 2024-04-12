package com.eaf.orig.ulo.pl.app.view.webaction;

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
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.shared.view.webaction.SaveAddressWebAction;
import com.eaf.orig.ulo.pl.ajax.GetCardLinkLine;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class DeletePLAddressWebAction extends WebActionHelper implements WebAction {
	Logger log = Logger.getLogger(SaveAddressWebAction.class);
	@Override
	public Event toEvent(){
		return null;
	}

	@Override
	public boolean requiredModelRequest(){
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response){
		return false;
	}

	@Override
	public boolean preModelRequest(){
		
		JsonObjectUtil jObj = new JsonObjectUtil();
		
		try{
				String []addressType = getRequest().getParameterValues("checkbox-addresstype");
				String personalType = getRequest().getParameter("personalType");
				
				if(OrigUtil.isEmptyString(personalType)){
					personalType = PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT;
				}
				
				log.debug("personalType >> "+personalType);
				
				String searchType = (String)getRequest().getSession().getAttribute("searchType");
				
				UserDetailM ORIGUser = (UserDetailM)getRequest().getSession().getAttribute("ORIGUser");				
				
				PLOrigFormHandler origForm = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);	
				PLApplicationDataM applicationM = origForm.getAppForm();
				
				ORIGFormUtil formUtil = new ORIGFormUtil();				
				String displayMode = formUtil.getDisplayMode("ADDRESS_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), origForm.getFormID(), searchType);
							
				if(addressType == null || addressType.length == 0){
					return true;
				}
												
				PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(personalType);
												
				Vector<PLAddressDataM> addressVect = personalM.getAddressVect();
				
				
				if(null != addressType){
					for(int i = 0 ; i < addressType.length ; i++){					
						String type  = addressType[i];		
						if(addressVect != null && addressVect.size() >0){								
							for(int j = addressVect.size()-1 ; j >= 0 ;j--){									
								PLAddressDataM addressM = (PLAddressDataM) addressVect.get(j);									
								if(!OrigUtil.isEmptyString(type) && type.equalsIgnoreCase(addressM.getAddressType())){
									addressVect.remove(j);										
								}								
							}							
						}
					}
				}
				//reset seq
				for(int i=0;i<addressVect.size();i++){
					PLAddressDataM plAddressM = (PLAddressDataM) addressVect.get(i);
					plAddressM.setAddressSeq(i);
				}
				
				
				AddressUtil addressUtil =  new AddressUtil();				
								
				StringBuilder addressStr = new StringBuilder("");			
				
				if (!OrigUtil.isEmptyVector(addressVect)) {
					for (int i = 0; i < addressVect.size(); i++) {
						PLAddressDataM addrM = addressVect.get(i);
						addressStr.append(addressUtil.CreatePLAddressM(addrM,personalM.getPersonalType(), displayMode,getRequest(), personalM.getDepartment()));					
					}
					jObj.CreateJsonObject("addressResult", addressStr.toString());
				}else{
					jObj.CreateJsonObject("addressResult",addressUtil.CreateNorecPLAddressM());
				}
				
				Vector<CacheDataM> vMailingAddress = new Vector<CacheDataM>();			
				if(!OrigUtil.isEmptyVector(addressVect)){
					for (int i = 0; i < addressVect.size(); i++) {
						PLAddressDataM addresM = (PLAddressDataM) addressVect.get(i);
						if (!OrigConstant.ADDRESS_TYPE_IC.equals(addresM.getAddressType())) {
							CacheDataM cacheDataM = new CacheDataM();
							cacheDataM.setCode(addresM.getAddressType());
							cacheDataM.setThDesc(HTMLRenderUtil.displayHTMLFieldIDDesc(addresM.getAddressType(), 12));
							vMailingAddress.add(cacheDataM);
						}
					}
				}		
				
				String mailling_address = getRequest().getParameter("mailling_address");				
				log.debug("Mailling Address >> "+mailling_address);
							
				jObj.CreateJsonObject("mailling-address",HTMLRenderUtil.displaySelectTagScriptAction_ORIG(
							vMailingAddress, mailling_address,
									"mailling_address", HTMLRenderUtil.DISPLAY_MODE_EDIT," onChange='GetCardlink()' "));
		
				GetCardLinkLine.GetJsonCardLinkAddress(getRequest(), jObj, mailling_address, addressVect, personalM.getDepartment());
											
		}catch(Exception e){
			log.fatal("Error ",e);
		}		
		jObj.ResponseJsonArray(getResponse());
		
		return true;
	}	

	@Override
	public int getNextActivityType() {
		return FrontController.NOTFORWARD;
	}

	@Override
	public boolean getCSRFToken() {
		return false;
	}

}
