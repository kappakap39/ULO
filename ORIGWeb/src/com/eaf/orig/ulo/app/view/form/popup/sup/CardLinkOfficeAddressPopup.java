package com.eaf.orig.ulo.app.view.form.popup.sup;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

//import org.apache.log4j.Logger;

//import com.eaf.core.ulo.common.display.FormatUtil;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
//import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
//import com.eaf.orig.ulo.model.app.AddressDataM;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CardLinkOfficeAddressPopup extends ORIGSubForm{
//	private static transient Logger logger = Logger.getLogger(CardLinkOfficeAddressPopup.class);
//	private String ADDRESS_TYPE_WORK_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_WORK_CARDLINK");
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
//		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
//		
//		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(1);	
//		PersonalAddressUtil addressUtil = new PersonalAddressUtil();		
//				
//		AddressDataM workAddress = personalInfo.getAddress(ADDRESS_TYPE_WORK_CARDLINK);
//		if(null == workAddress){
//			workAddress = new AddressDataM();
//			workAddress.setAddressType(ADDRESS_TYPE_WORK_CARDLINK);
//			personalInfo.addAddress(workAddress);
//		}	
//
//		String COMPANY_NAME = request.getParameter("COMPANY_NAME");
//		String DEPARTMENT = request.getParameter("DEPARTMENT");
//		String BUILDING = request.getParameter("BUILDING");
//		String FLOOR = request.getParameter("FLOOR");
//		String ADDRESS_ID = request.getParameter("ADDRESS_ID");
//		String MOO = request.getParameter("MOO");
//		String SOI = request.getParameter("SOI");
//		String ROAD = request.getParameter("ROAD");
//		String TAMBOL = request.getParameter("TAMBOL");
//		String AMPHUR = request.getParameter("AMPHUR");
//		String PROVINCE = request.getParameter("PROVINCE");
//		String ZIPCODE = request.getParameter("ZIPCODE");
//		
//		logger.debug("COMPANY_NAME >>"+COMPANY_NAME);	
//		logger.debug("DEPARTMENT >>"+DEPARTMENT);	
//		logger.debug("BUILDING >>"+BUILDING);	
//		logger.debug("FLOOR >>"+FLOOR);	
//		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);	
//		logger.debug("MOO >>"+MOO);	
//		logger.debug("SOI >>"+SOI);	
//		logger.debug("ROAD >>"+ROAD);	
//		logger.debug("TAMBOL >>"+TAMBOL);	
//		logger.debug("AMPHUR >>"+AMPHUR);	
//		logger.debug("PROVINCE >>"+PROVINCE);	
//		logger.debug("ZIPCODE >>"+ZIPCODE);	
//		
//		workAddress.setCompanyName(COMPANY_NAME);
//		workAddress.setDepartment(DEPARTMENT);
//		workAddress.setBuilding(BUILDING);
//		workAddress.setFloor(FLOOR);
//		workAddress.setAddress(ADDRESS_ID);
//		workAddress.setMoo(MOO);
//		workAddress.setSoi(SOI);
//		workAddress.setRoad(ROAD);
//		workAddress.setTambol(TAMBOL);
//		workAddress.setAmphur(AMPHUR);
//		workAddress.setProvince(FormatUtil.toBigDecimal(PROVINCE));
//		workAddress.setZipcode(FormatUtil.toBigDecimal(ZIPCODE));
//		workAddress.setAddress1(addressUtil.getWorkCardlinkLine1(request,workAddress));
//		workAddress.setAddress2(addressUtil.getCardlinkLine2(request,workAddress));		
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
