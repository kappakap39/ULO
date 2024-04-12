package com.eaf.orig.ulo.app.view.form.popup;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;

public class CardLinkHomeAddressPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CardLinkCurrentAddressPopup.class);
//	private String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	private String ADDRESS_TYPE_DOCUMENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT_CARDLINK");
	private String ADDRESS_FORMAT_MANUAL = SystemConstant.getConstant("ADDRESS_FORMAT_MANUAL");
	int  MIN_WORK_CARD_LINK_LINE_1 = Integer.parseInt(SystemConstant.getConstant("MIN_WORK_CARD_LINK_LINE_1"));
	int  MIN_WORK_CARD_LINK_LINE_2 = Integer.parseInt(SystemConstant.getConstant("MIN_WORK_CARD_LINK_LINE_2"));
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_DOCUMENT_CARDLINK);
		String CARDLINK_PREFIX = "CL_";
//		PersonalAddressUtil addressUtil = new PersonalAddressUtil();
		
		String ADDRESS_ID = request.getParameter(CARDLINK_PREFIX+"ADDRESS_ID");
		String MOO = request.getParameter(CARDLINK_PREFIX+"MOO");
		String SOI = request.getParameter(CARDLINK_PREFIX+"SOI");
		String ROAD = request.getParameter(CARDLINK_PREFIX+"ROAD");
		String ADDRESS_FORMAT = request.getParameter(CARDLINK_PREFIX+"ADDRESS_FORMAT");
		String TAMBOL = request.getParameter(CARDLINK_PREFIX+"TAMBOL");
		String AMPHUR = request.getParameter(CARDLINK_PREFIX+"AMPHUR");
		String PROVINCE = request.getParameter(CARDLINK_PREFIX+"PROVINCE");
		String ZIPCODE = request.getParameter(CARDLINK_PREFIX+"ZIPCODE");

		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);	
		logger.debug("MOO >>"+MOO);	
		logger.debug("SOI >>"+SOI);	
		logger.debug("ROAD >>"+ROAD);	
		logger.debug("ADDRESS_FORMAT >>"+ADDRESS_FORMAT);
		logger.debug("TAMBOL >>"+TAMBOL);	
		logger.debug("AMPHUR >>"+AMPHUR);	
		logger.debug("PROVINCE >>"+PROVINCE);	
		logger.debug("ZIPCODE >>"+ZIPCODE);	

		address.setAddress(ADDRESS_ID);
		address.setMoo(MOO);
		address.setSoi(SOI);
		address.setRoad(ROAD);
		if(Util.empty(ADDRESS_FORMAT)){
			address.setAddressFormat(ADDRESS_FORMAT_MANUAL);
		}else{
			address.setAddressFormat(ADDRESS_FORMAT);
		}
		address.setTambol(TAMBOL);
		address.setAmphur(AMPHUR);
		address.setProvinceDesc(PROVINCE);
		address.setZipcode(ZIPCODE);
		address.setAddress1(PersonalAddressUtil.getDocCardlinkLine1(request,address));
		address.setAddress2(PersonalAddressUtil.getCardlinkLine2(request,address));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "CARDLINK_HOME_ADDRESS_POPUP";
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM addressDocCardlink = hashAddress.get(ADDRESS_TYPE_DOCUMENT_CARDLINK);
		if(null == addressDocCardlink){
			addressDocCardlink = new AddressDataM();
		}

		String cardlinkLine1 = PersonalAddressUtil.getDocCardlinkLine1(request,addressDocCardlink);
		String cardlinkLine2 = PersonalAddressUtil.getCardlinkLine2(request,addressDocCardlink);
	  	if(!Util.empty(cardlinkLine1) && cardlinkLine1.length() > MIN_WORK_CARD_LINK_LINE_1 ){
	  		formError.error(MessageErrorUtil.getText("ERROR_ADDRESS_LENGTH"));
		}
		if(!Util.empty(cardlinkLine2) && cardlinkLine2.length() > MIN_WORK_CARD_LINK_LINE_2){
			formError.error(MessageErrorUtil.getText("ERROR_ADDRESS_LENGTH"));
		} 
		
		formError.mandatory(subformId, "ADDRESS_ID_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"ADDRESS_ID"), addressDocCardlink.getAddress(), request);
		formError.mandatory(subformId, "MOO_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"MOO"), addressDocCardlink.getMoo(), request);
		formError.mandatory(subformId, "MOO_BUILDING_SOI_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"MOO_BUILDING_SOI"), addressDocCardlink.getSoi(), request);
		formError.mandatory(subformId, "ROAD_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"ROAD"), addressDocCardlink.getRoad(), request);
		formError.mandatory(subformId, "TAMBOL_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"TAMBOL"), addressDocCardlink.getTambol(), request);
		formError.mandatory(subformId, "AMPHUR_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"AMPHUR"), addressDocCardlink.getAmphur(), request);
		formError.mandatory(subformId,"PROVINCE_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"PROVINCE"),addressDocCardlink.getProvinceDesc(),request);
		formError.mandatory(subformId,"ZIPCODE_"+ADDRESS_TYPE_DOCUMENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_DOCUMENT_POPUP_2")+" : "+LabelUtil.getText(request,"ZIPCODE"),addressDocCardlink.getZipcode(),request);

		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
		AddressDataM addressHome = hashAddress.get(ADDRESS_TYPE_DOCUMENT_CARDLINK);
		if(null == addressHome){
			addressHome = new AddressDataM();
		}
		HashMap<String,AddressDataM> hashlastAddress = (HashMap<String,AddressDataM>)lastObjectForm;
		AddressDataM lastAddress = hashAddress.get(ADDRESS_TYPE_DOCUMENT_CARDLINK);
		if(null == lastAddress){
			lastAddress = new AddressDataM();
		}

		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.auditForm(subformId,"ADDRESS_ID",addressHome,lastAddress,request);

		return auditFormUtil.getAuditForm();
	}	

}
