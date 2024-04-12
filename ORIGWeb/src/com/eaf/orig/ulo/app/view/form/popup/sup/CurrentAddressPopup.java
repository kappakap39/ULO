package com.eaf.orig.ulo.app.view.form.popup.sup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;

@SuppressWarnings("serial")
public class CurrentAddressPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CurrentAddressPopup.class);
	private String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT_CARDLINK);
//		PersonalAddressUtil addressUtil = new PersonalAddressUtil();	
			
		String ADDRESS_STYLE = request.getParameter("ADDRESS_STYLE");
		String ADRSTS = request.getParameter("ADRSTS");
		String RENTS = request.getParameter("RENTS");
		String RESIDEY = request.getParameter("RESIDEY");
		String RESIDEM = request.getParameter("RESIDEM");
		String COPY_ADDRESS_TYPE = request.getParameter("COPY_ADDRESS_TYPE");
		String ADDRESS_NAME = request.getParameter("ADDRESS_NAME");
		String BUILDING = request.getParameter("BUILDING");
		String ROOM = request.getParameter("ROOM");
		String FLOOR = request.getParameter("FLOOR");
		String ADDRESS_ID = request.getParameter("ADDRESS_ID");
		String MOO = request.getParameter("MOO");
		String SOI = request.getParameter("SOI");
		String ROAD = request.getParameter("ROAD");
		String COUNTRY = request.getParameter("COUNTRY");
		String TAMBOL = request.getParameter("TAMBOL");
		String AMPHUR = request.getParameter("AMPHUR");
		String PROVINCE = request.getParameter("PROVINCE");
		String ZIPCODE = request.getParameter("ZIPCODE");

		logger.debug("ADDRESS_STYLE >>"+ADDRESS_STYLE);
		logger.debug("ADRSTS >>"+ADRSTS);
		logger.debug("RENTS >>"+RENTS);
		logger.debug("RESIDEY >>"+RESIDEY);
		logger.debug("RESIDEM >>"+RESIDEM);
		logger.debug("COPY_ADDRESS_TYPE >>"+COPY_ADDRESS_TYPE);
		logger.debug("ADDRESS_NAME >>"+ADDRESS_NAME);
		logger.debug("BUILDING >>"+BUILDING);
		logger.debug("ROOM >>"+ROOM);
		logger.debug("FLOOR >>"+FLOOR);
		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);
		logger.debug("MOO >>"+MOO);
		logger.debug("SOI >>"+SOI);
		logger.debug("ROAD >>"+ROAD);
		logger.debug("COUNTRY >>"+COUNTRY);
		logger.debug("TAMBOL >>"+TAMBOL);
		logger.debug("AMPHUR >>"+AMPHUR);
		logger.debug("PROVINCE >>"+PROVINCE);
		logger.debug("ZIPCODE >>"+ZIPCODE);
		
		address.setAdrsts(ADRSTS);
		address.setRents(FormatUtil.toBigDecimal(RENTS));
		address.setResidey(FormatUtil.toBigDecimal(RESIDEY));
		address.setResidem(FormatUtil.toBigDecimal(RESIDEM));
		address.setBuilding(BUILDING);
		address.setRoom(ROOM);
		address.setFloor(FLOOR);
		address.setAddress(ADDRESS_ID);
		address.setMoo(MOO);
		address.setSoi(SOI);
		address.setRoad(ROAD);
		address.setCountry(COUNTRY);
		address.setTambol(TAMBOL);
		address.setAmphur(AMPHUR);
		address.setAddress1(PersonalAddressUtil.getCurrentCardlinkLine1(request,address));
		address.setAddress2(PersonalAddressUtil.getCardlinkLine2(request,address));
//		address.setProvince(FormatUtil.toBigDecimal(PROVINCE));
		address.setZipcode(ZIPCODE);
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
