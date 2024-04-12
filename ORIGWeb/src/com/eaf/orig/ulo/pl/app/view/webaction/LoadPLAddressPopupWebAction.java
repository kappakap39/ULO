package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;

import org.apache.log4j.Logger;

import com.eaf.cache.data.CacheDataM;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class LoadPLAddressPopupWebAction extends WebActionHelper implements WebAction {
	
	private final Logger logger = Logger.getLogger(LoadPLAddressPopupWebAction.class);
	
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
	public boolean preModelRequest() {		
		
		PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
		
		String seq = getRequest().getParameter("seq");
		String addressType = getRequest().getParameter("type");
		String personalType = getRequest().getParameter("personalType");
		String mode = getRequest().getParameter("mode");
						
		logger.debug("addressSeq >> " + seq);
		logger.debug("addressType >> " + addressType);
		logger.debug("personalType >> " + personalType);
		logger.debug("mode >> " + mode);
		
		if(addressType==null){
			addressType = "";			
		}
		
		Vector<ORIGCacheDataM> addressVect = getAddressType(applicationM.getBusinessClassId());
		
		if(OrigUtil.isEmptyString(personalType)){
			personalType = PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT;
		}
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(personalType);
		
		PLAddressDataM addressM = personalM.getAddressDataM(addressType);
		
		Vector vAddressVect = personalM.getAddressVect();
		
		if(!ORIGUtility.isEmptyVector(vAddressVect)){			
			for(int i=0;i<vAddressVect.size();i++){
				PLAddressDataM addressM1 = (PLAddressDataM)vAddressVect.get(i);
				for (int j = addressVect.size() - 1; j >= 0; --j) {
					CacheDataM cacheM =  (CacheDataM) addressVect.get(j);
					if(cacheM.getCode().equals(addressM1.getAddressType()) 
									&& !cacheM.getCode().equals(addressType)){
						addressVect.remove(j);	
					}						
				}				
			}			
			if(!OrigUtil.isEmptyString(mode)){
				addressM.setAddressCheck(addressVect);
			}			
		}
		
		if(!OrigUtil.isEmptyString(seq)){
			addressM.setSeq(Integer.valueOf(seq));
		}
		
		getRequest().getSession().setAttribute("addressType", addressVect);
		getRequest().getSession().setAttribute("AddressDataM", addressM);
		getRequest().getSession().setAttribute("currentPerson", personalType);		
			
		return true;
	}
	private Vector<ORIGCacheDataM> getAddressType(String busClassID){
		Vector<ORIGCacheDataM> data = new Vector<ORIGCacheDataM>();
		ORIGCacheUtil origc = new ORIGCacheUtil();		
		Vector<ORIGCacheDataM> addressVect = (Vector)origc.getNaosCacheDataMs(busClassID, 12);
		for (ORIGCacheDataM origCacheDataM : addressVect) {
			data.add(origCacheDataM);
		}
		return data;
	}
	@Override
	public int getNextActivityType() {		
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken() {
		return false;
	}
	

}
