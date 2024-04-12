package com.eaf.orig.ulo.app.view.form.popup.sup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;

//import com.eaf.core.ulo.common.display.FormatUtil;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
//import com.eaf.orig.ulo.app.view.form.popup.CardLinkCurrentAddressPopup;
//import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
//import com.eaf.orig.ulo.model.app.AddressDataM;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

@SuppressWarnings("serial")
public class CardLinkHomeAddressPopup extends ORIGSubForm{
//	private static transient Logger logger = Logger.getLogger(CardLinkCurrentAddressPopup.class);
//	String ADDRESS_TYPE_DOCUMENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT_CARDLINK");

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
//		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
//		
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(1);	
//		PersonalAddressUtil addressUtil = new PersonalAddressUtil();			
//		
//		AddressDataM address = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT_CARDLINK);
//		if(null == address){
//			address = new AddressDataM();
//			address.setAddressType(ADDRESS_TYPE_DOCUMENT_CARDLINK);
//			personalInfo.addAddress(address);
//		}		
//
//		String ADDRESS_ID = request.getParameter("ADDRESS_ID");
//		String MOO = request.getParameter("MOO");
//		String SOI = request.getParameter("SOI");
//		String ROAD = request.getParameter("ROAD");
//		String TAMBOL = request.getParameter("TAMBOL");
//		String AMPHUR = request.getParameter("AMPHUR");
//		String PROVINCE = request.getParameter("PROVINCE");
//		String ZIPCODE = request.getParameter("ZIPCODE");
//
//		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);	
//		logger.debug("MOO >>"+MOO);	
//		logger.debug("SOI >>"+SOI);	
//		logger.debug("ROAD >>"+ROAD);	
//		logger.debug("TAMBOL >>"+TAMBOL);	
//		logger.debug("AMPHUR >>"+AMPHUR);	
//		logger.debug("PROVINCE >>"+PROVINCE);	
//		logger.debug("ZIPCODE >>"+ZIPCODE);	
//
//		address.setAddress(ADDRESS_ID);
//		address.setMoo(MOO);
//		address.setSoi(SOI);
//		address.setRoad(ROAD);
//		address.setTambol(TAMBOL);
//		address.setAmphur(AMPHUR);
//		address.setProvince(FormatUtil.toBigDecimal(PROVINCE));
//		address.setZipcode(FormatUtil.toBigDecimal(ZIPCODE));
//		address.setAddress1(addressUtil.getDocCardlinkLine1(request,address));
//		address.setAddress2(addressUtil.getCardlinkLine2(request,address));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
