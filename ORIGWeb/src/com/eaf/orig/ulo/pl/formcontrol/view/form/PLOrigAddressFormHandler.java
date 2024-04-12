package com.eaf.orig.ulo.pl.formcontrol.view.form;


import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;

import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;

public class PLOrigAddressFormHandler extends ORIGFormHandler {
	Logger log = Logger.getLogger(PLOrigAddressFormHandler.class);
	
	public static final String PLORIGAddressForm = "PLORIGAddressForm";
	
	public PLOrigAddressFormHandler() {
		super();
		
	}

	public void setProperties(HttpServletRequest request) {
		log.debug("[setProperties] [Address] .. Begin");
		
		PLOrigFormHandler origform = (PLOrigFormHandler) request.getSession().getAttribute(PLOrigFormHandler.PLORIGForm);
		
		PLApplicationDataM applicationDataM =origform.getAppForm();
		
		PLPersonalInfoDataM personalInfoDataM = applicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		if(personalInfoDataM==null){
			personalInfoDataM = new PLPersonalInfoDataM();
		}
	
		log.debug("[setProperties] [Address] ..  PersonalType "+personalInfoDataM.getPersonalType());
		
		String address_type = request.getParameter("address_type");
//		String address_type_copy = request.getParameter("Copy");
//		String address_style = request.getParameter("address_style");
		String address_status = request.getParameter("address_status");
		String rent_payment = request.getParameter("rent_payment");
//		String time_current_address_year = request.getParameter("time_current_address_year");
//		String time_current_address_month = request.getParameter("time_current_address_month");
		String place_name = request.getParameter("place_name");
		String number = request.getParameter("number");
		String building = request.getParameter("building");
		String floor = request.getParameter("floor");
		String room = request.getParameter("room");
		String moo = request.getParameter("moo");
		String village = request.getParameter("village");
		String soi = request.getParameter("soi");
		String road = request.getParameter("road");
		String tambol = request.getParameter("tambol");
		String amphur = request.getParameter("amphur");
		String province = request.getParameter("province");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
		String mobile_no = request.getParameter("mobile_no");
		String telephone1 = request.getParameter("telephone1");
		String ext_tel_1 = request.getParameter("ext_tel_1");
		String telephone2 = request.getParameter("telephone2");
		String ext_tel_2 = request.getParameter("ext_tel_2");
		String fax_no = request.getParameter("fax_no");
		String note = request.getParameter("note");
		
		
		/** Get or New AddressDataM **/
		PLAddressDataM pladdressDataM = personalInfoDataM.getAddressDataM(address_type);
		
		
		Vector<PLAddressDataM> plAddressVect = personalInfoDataM.getAddressVect();
		
		if(ORIGUtility.isEmptyString(pladdressDataM.getAddressType())){			
			log.debug("[setProperties] [Address] .. is Empty AddressType "+address_type);
			pladdressDataM.setAddressSeq(plAddressVect.size()+1);			
			pladdressDataM.setAddressType(address_type);	
			personalInfoDataM.add(pladdressDataM);
		}
		
		pladdressDataM.setBuildingType(address_type);
		pladdressDataM.setAdrsts(address_status);
		
		pladdressDataM.setRents(DataFormatUtility.replaceCommaForBigDecimal(rent_payment));
		
//		if(time_current_address_year!=null){
//			pladdressDataM.setReYears(DataFormatUtility.StringToInt(time_current_address_year));
//		}
		
//		if(time_current_address_month!=null){
//			pladdressDataM.setReMonth(DataFormatUtility.StringToInt(time_current_address_month));
//		}
		
		pladdressDataM.setPhoneNo1(telephone1);
		pladdressDataM.setPhoneExt1(ext_tel_1);
		pladdressDataM.setPhoneNo2(telephone2);
		pladdressDataM.setPhoneExt2(ext_tel_2);
		pladdressDataM.setPlaceName(place_name);
		pladdressDataM.setHouseID(number);
		pladdressDataM.setBuilding(building);
		pladdressDataM.setFloor(floor);
		pladdressDataM.setRoom(room);
		pladdressDataM.setMoo(moo);
		pladdressDataM.setHousingEstate(village);
		pladdressDataM.setSoi(soi);
		pladdressDataM.setRoad(road);
		pladdressDataM.setTambol(tambol);
		pladdressDataM.setAmphur(amphur);
		pladdressDataM.setProvince(province);
		pladdressDataM.setZipcode(zipcode);
		pladdressDataM.setCountry(country);
		pladdressDataM.setMobileNo(mobile_no);
		pladdressDataM.setFaxNo(fax_no);
		pladdressDataM.setFaxExt(note);
				
		log.debug("[setProperties] [Address] .. No "+ pladdressDataM.getAddressSeq());
		log.debug("[setProperties] [Address] .. Address Type "+ pladdressDataM.getAddressType());
		log.debug("[setProperties] [Address] .. Status  "+ pladdressDataM.getStatus());
			
		log.debug("[setProperties] [Address] .. End");
	}
	
	
	
}
