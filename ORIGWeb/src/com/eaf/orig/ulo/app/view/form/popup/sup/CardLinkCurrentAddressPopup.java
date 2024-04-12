package com.eaf.orig.ulo.app.view.form.popup.sup;

//import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;

//import com.eaf.core.ulo.common.display.FormatUtil;
//import com.eaf.core.ulo.common.properties.SystemConstant;
//import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
//import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
//import com.eaf.orig.ulo.model.app.AddressDataM;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

@SuppressWarnings("serial")
public class CardLinkCurrentAddressPopup extends ORIGSubForm{
//	private static transient Logger logger = Logger.getLogger(CardLinkCurrentAddressPopup.class);
//	String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");

	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
//		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
//		
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(1);	
//		PersonalAddressUtil addressUtil = new PersonalAddressUtil();	
//		
//		AddressDataM currentAddress = personalInfo.getAddress(ADDRESS_TYPE_CURRENT_CARDLINK);
//		if(null == currentAddress){
//			currentAddress = new AddressDataM();
//			currentAddress.setAddressType(ADDRESS_TYPE_CURRENT_CARDLINK);
//			personalInfo.addAddress(currentAddress);
//		}		
//
//		String ADDRESS_ID = request.getParameter("ADDRESS_ID");
//		String MOO = request.getParameter("MOO");
//		String SOI = request.getParameter("SOI");
//		String ROAD = request.getParameter("ROAD");
//		String COUNTRY = request.getParameter("COUNTRY");
//		String TAMBOL = request.getParameter("TAMBOL");
//		String AMPHUR = request.getParameter("AMPHUR");
//		String PROVINCE = request.getParameter("PROVINCE");
//		String ZIPCODE = request.getParameter("ZIPCODE");
//		String ADRSTS = request.getParameter("ADRSTS");
//		String RESIDEY = request.getParameter("RESIDEY");
//		String RESIDEM = request.getParameter("RESIDEM");
//
//		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);	
//		logger.debug("MOO >>"+MOO);	
//		logger.debug("SOI >>"+SOI);	
//		logger.debug("ROAD >>"+ROAD);	
//		logger.debug("COUNTRY >>"+COUNTRY);	
//		logger.debug("TAMBOL >>"+TAMBOL);	
//		logger.debug("AMPHUR >>"+AMPHUR);	
//		logger.debug("PROVINCE >>"+PROVINCE);	
//		logger.debug("ZIPCODE >>"+ZIPCODE);	
//		logger.debug("ADRSTS >>"+ADRSTS);	
//		logger.debug("RESIDEY >>"+RESIDEY);	
//		logger.debug("RESIDEM >>"+RESIDEM);	
//
//		currentAddress.setAddress(ADDRESS_ID);
//		currentAddress.setMoo(MOO);
//		currentAddress.setSoi(SOI);
//		currentAddress.setRoad(ROAD);
//		currentAddress.setCountry(COUNTRY);
//		currentAddress.setTambol(TAMBOL);
//		currentAddress.setAmphur(AMPHUR);
//		currentAddress.setProvince(FormatUtil.toBigDecimal(PROVINCE));
//		currentAddress.setZipcode(FormatUtil.toBigDecimal(ZIPCODE));
//		currentAddress.setAddress1(addressUtil.getCurrentCardlinkLine1(request,currentAddress));
//		currentAddress.setAddress2(addressUtil.getCardlinkLine2(request,currentAddress));
//		if(!Util.empty(ADRSTS))	currentAddress.setAdrsts(ADRSTS);
//		if(!Util.empty(RESIDEY))	currentAddress.setResidey(new BigDecimal(RESIDEY));
//		if(!Util.empty(RESIDEM))	currentAddress.setResidem(new BigDecimal(RESIDEM));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}

}
