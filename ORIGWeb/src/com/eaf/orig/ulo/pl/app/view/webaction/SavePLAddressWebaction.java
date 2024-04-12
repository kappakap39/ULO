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
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class SavePLAddressWebaction extends WebActionHelper implements WebAction {
	
	Logger log = Logger.getLogger(SaveAddressWebAction.class);

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
		try {
		
			PLOrigFormHandler origFrom = (PLOrigFormHandler) getRequest().getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
			
			UserDetailM ORIGUser = (UserDetailM)getRequest().getSession().getAttribute("ORIGUser");
			String searchType = (String)getRequest().getSession().getAttribute("searchType");
			String currentPerson = (String) getRequest().getSession().getAttribute("currentPerson");
			
			if(OrigUtil.isEmptyString(currentPerson)){
				currentPerson = PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT;
			}
			
			log.debug("currentPerson >> "+currentPerson);
			
			PLApplicationDataM applicationM = origFrom.getAppForm();
			
			ORIGFormUtil formUtil = new ORIGFormUtil();			
			String displayMode = formUtil.getDisplayMode("ADDRESS_SUBFORM", ORIGUser.getRoles(), applicationM.getJobState(), origFrom.getFormID(), searchType);
			
			PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(currentPerson);
					
			boolean addFlag = false;
			
			String address_type = getRequest().getParameter("address_type");
			String address_style = getRequest().getParameter("address_style");
			
			String address_status = getRequest().getParameter("address_status");
			String rent_payment = getRequest().getParameter("returndchecks");
			String time_current_address_year = getRequest().getParameter("time_current_address_year");
			String time_current_address_month = getRequest().getParameter("time_current_address_month");
			String place_name = getRequest().getParameter("place_name");
			String number = getRequest().getParameter("number");
			String building = getRequest().getParameter("building");
			String floor = getRequest().getParameter("floor");
			String room = getRequest().getParameter("room");
			String moo = getRequest().getParameter("moo");
			String village = getRequest().getParameter("village");
			String soi = getRequest().getParameter("soi");
			String road = getRequest().getParameter("road");
			String tambol = getRequest().getParameter("tambol");
			String amphur = getRequest().getParameter("amphur");
			String province = getRequest().getParameter("province");
			String zipcode = getRequest().getParameter("zipcode");
			String country = getRequest().getParameter("country_no");
			String mobile_no = getRequest().getParameter("mobile_no");
			String telephone1 = getRequest().getParameter("telephone1");
			String ext_tel_1 = getRequest().getParameter("ext_tel_1");
			String telephone2 = getRequest().getParameter("telephone2");
			String ext_tel_2 = getRequest().getParameter("ext_tel_2");
			String fax_no = getRequest().getParameter("fax_no");
			String note = getRequest().getParameter("note");
			
			String oldAddressType = getRequest().getParameter("old-address-type");
			
			String companyTitle = getRequest().getParameter("address_company_title");
			String companyName= getRequest().getParameter("address_company_name");
			String department = getRequest().getParameter("occ_department");
						
			PLAddressDataM addressM = new PLAddressDataM();
			
			Vector<PLAddressDataM> addressVect = personalM.getAddressVect();
			
			if(OrigUtil.isEmptyString(addressM.getAddressType())){							
				addressM.setAddressType(address_type);	
				addFlag = true;					
			}
			
			addressM.setBuildingType(address_style);
			
			addressM.setAdrsts(address_status);
			
			addressM.setRents(DataFormatUtility.replaceCommaForBigDecimal(rent_payment));
			
			if(!OrigUtil.isEmptyString(time_current_address_year)){
				addressM.setResidey(DataFormatUtility.StringToInt(time_current_address_year));
			}
			
			if(!OrigUtil.isEmptyString(time_current_address_month)){
				addressM.setResidem(DataFormatUtility.StringToInt(time_current_address_month));
			}
			
			addressM.setPhoneNo1(telephone1);
			addressM.setPhoneExt1(ext_tel_1);
			addressM.setPhoneNo2(telephone2);
			addressM.setPhoneExt2(ext_tel_2);
			addressM.setPlaceName(place_name);
			addressM.setAddressNo(number);
			
			addressM.setFloor(floor);
			addressM.setRoom(room);
			addressM.setMoo(moo);
			addressM.setHousingEstate(village);
			addressM.setSoi(soi);
			addressM.setRoad(road);
			addressM.setTambol(tambol);
			addressM.setAmphur(amphur);
			addressM.setProvince(province);
			addressM.setZipcode(zipcode);
			addressM.setCountry(country);
			addressM.setMobileNo(mobile_no);
			addressM.setFaxNo(fax_no);
			addressM.setRemark(note);
			
			//septem for address type 03 set CompanyTitle,CompanyName,Department,Building
			if("03".equals(address_type)){
				addressM.setCompanyTitle(companyTitle);
				addressM.setCompanyName(companyName);
				personalM.setDepartment(department);
				addressM.setBuilding(building);
			}
			
			
			int seq = 0;
			if(addFlag){
				if(!OrigUtil.isEmptyVector(addressVect)){
					seq = addressVect.size();
					for(int i = addressVect.size() - 1; i >= 0; --i){
						PLAddressDataM addM = addressVect.get(i);
						if(null != addM.getAddressType() && addM.getAddressType().equals(oldAddressType)){
							seq = addM.getAddressSeq();
							addressVect.remove(i);
							break;
						}
					}
				}
				addressVect.add(seq, addressM);
			}
			
			personalM.setAddressVect(addressVect);
			
			for(int i=0;i<addressVect.size();i++){
				PLAddressDataM addressM1 = (PLAddressDataM) addressVect.get(i);
				addressM1.setAddressSeq(i);
			}
			
			getRequest().getSession().setAttribute("AddressDataM", addressM);
					    
		    
		    AddressUtil addressUtil = new AddressUtil();
		    JsonObjectUtil json = new JsonObjectUtil();

			Vector<PLAddressDataM> addrVect = personalM.getAddressVect();
			
			StringBuilder STR = new StringBuilder("");			
			
			if (!OrigUtil.isEmptyVector(addrVect)) {
				for(int i = 0; i < addrVect.size(); i++){
					PLAddressDataM addrM = addrVect.get(i);
					STR.append(addressUtil.CreatePLAddressM(addrM,personalM.getPersonalType(), displayMode,getRequest(), personalM.getDepartment()));					
				}
				json.CreateJsonObject("addressResult", STR.toString());
			}
			
			Vector<CacheDataM> vMailingAddress = new Vector<CacheDataM>();			
			if(!OrigUtil.isEmptyVector(addrVect)){
				for (int i = 0; i < addrVect.size(); i++) {
					PLAddressDataM addresM = (PLAddressDataM) addrVect.get(i);
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
						
			json.CreateJsonObject("mailling-address",HTMLRenderUtil.displaySelectTagScriptAction_ORIG(
						vMailingAddress, mailling_address,
								"mailling_address", HTMLRenderUtil.DISPLAY_MODE_EDIT," onChange='GetCardlink()' "));
	
			GetCardLinkLine.GetJsonCardLinkAddress(getRequest(), json, mailling_address, addrVect, personalM.getDepartment());
			
			json.ResponseJsonArray(getResponse());		  
				
		}catch(Exception e){
			log.fatal("Error ",e);
		}
		getRequest().getSession().removeAttribute("currentPerson");
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
