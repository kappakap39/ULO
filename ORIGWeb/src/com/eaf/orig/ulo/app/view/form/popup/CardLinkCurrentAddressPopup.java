package com.eaf.orig.ulo.app.view.form.popup;

import java.math.BigDecimal;
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
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;

@SuppressWarnings("serial")
public class CardLinkCurrentAddressPopup extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CardLinkCurrentAddressPopup.class);
	private String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
	private String ADDRESS_FORMAT_MANUAL = SystemConstant.getConstant("ADDRESS_FORMAT_MANUAL");	
	private String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
	int  MIN_WORK_CARD_LINK_LINE_1 = Integer.parseInt(SystemConstant.getConstant("MIN_WORK_CARD_LINK_LINE_1"));
	int  MIN_WORK_CARD_LINK_LINE_2 = Integer.parseInt(SystemConstant.getConstant("MIN_WORK_CARD_LINK_LINE_2"));
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		logger.debug("CardLinkCurrentAddressPopup...");	
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM address = hashAddress.get(ADDRESS_TYPE_CURRENT_CARDLINK);	
		String CARDLINK_PREFIX = "CL_";		
		String ADDRESS_ID = request.getParameter(CARDLINK_PREFIX+"ADDRESS_ID");
		String MOO = request.getParameter(CARDLINK_PREFIX+"MOO");
		String MOO_BUILDING_SOI = request.getParameter(CARDLINK_PREFIX+"MOO_BUILDING_SOI");
		String ROAD = request.getParameter(CARDLINK_PREFIX+"ROAD");
		String ADDRESS_FORMAT = request.getParameter(CARDLINK_PREFIX+"ADDRESS_FORMAT");
		String TAMBOL = request.getParameter(CARDLINK_PREFIX+"TAMBOL");
		String AMPHUR = request.getParameter(CARDLINK_PREFIX+"AMPHUR");
		String PROVINCE = request.getParameter(CARDLINK_PREFIX+"PROVINCE");
		String ZIPCODE = request.getParameter(CARDLINK_PREFIX+"ZIPCODE");
		String ADRSTS = request.getParameter("ADRSTS");
		String RESIDEY = request.getParameter("RESIDEY");
		String RESIDEM = request.getParameter("RESIDEM");		
		logger.debug("ADDRESS_ID >>"+ADDRESS_ID);	
		logger.debug("MOO >>"+MOO);	
		logger.debug("MOO_BUILDING_SOI >>"+MOO_BUILDING_SOI);	
		logger.debug("ROAD >>"+ROAD);	
		logger.debug("ADDRESS_FORMAT >>"+ADDRESS_FORMAT);
		logger.debug("TAMBOL >>"+TAMBOL);	
		logger.debug("AMPHUR >>"+AMPHUR);	
		logger.debug("PROVINCE >>"+PROVINCE);	
		logger.debug("ZIPCODE >>"+ZIPCODE);	
		logger.debug("ADRSTS >>"+ADRSTS);	
		logger.debug("RESIDEY >>"+RESIDEY);	
		logger.debug("RESIDEM >>"+RESIDEM);	
		address.setAddress(ADDRESS_ID);
		address.setMoo(MOO);
		address.setSoi(MOO_BUILDING_SOI);
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
		address.setAddress1(PersonalAddressUtil.getCurrentCardlinkLine1(request,address));
		address.setAddress2(PersonalAddressUtil.getCardlinkLine2(request,address));
		if(!Util.empty(ADRSTS))address.setAdrsts(ADRSTS);
		if(!Util.empty(RESIDEY))address.setResidey(new BigDecimal(RESIDEY));
		if(!Util.empty(RESIDEM))address.setResidem(new BigDecimal(RESIDEM));
	}

	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		String subformId = "CARDLINK_CURRENT_ADDRESS_POPUP";
		
		logger.debug("subformId >> "+subformId);
		FormErrorUtil formError = new FormErrorUtil();
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)appForm;
		AddressDataM AddressCurrentCardlink = hashAddress.get(ADDRESS_TYPE_CURRENT_CARDLINK);	
		if(null == AddressCurrentCardlink){
			AddressCurrentCardlink = new AddressDataM();
		}
		
		String cardlinkLine1 = PersonalAddressUtil.getCurrentCardlinkLine1(request,AddressCurrentCardlink);
		String cardlinkLine2 = PersonalAddressUtil.getCardlinkLine2(request,AddressCurrentCardlink);
	  	if(!Util.empty(cardlinkLine1) && cardlinkLine1.length() > MIN_WORK_CARD_LINK_LINE_1 ){
	  		formError.error(MessageErrorUtil.getText("ERROR_ADDRESS_LENGTH"));
		}
		if(!Util.empty(cardlinkLine2) && cardlinkLine2.length() > MIN_WORK_CARD_LINK_LINE_2){
			formError.error(MessageErrorUtil.getText("ERROR_ADDRESS_LENGTH"));
		}  
		
		formError.mandatory(subformId, "ADDRESS_ID_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"ADDRESS_ID"), AddressCurrentCardlink.getAddress(), request);
		formError.mandatory(subformId, "MOO_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"MOO"), AddressCurrentCardlink.getMoo(), request);
		formError.mandatory(subformId, "MOO_BUILDING_SOI_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"MOO_BUILDING_SOI"), AddressCurrentCardlink.getSoi(), request);
		formError.mandatory(subformId, "ROAD_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"ROAD"), AddressCurrentCardlink.getRoad(), request);
		formError.mandatory(subformId, "TAMBOL_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"TAMBOL"), AddressCurrentCardlink.getTambol(), request);
		formError.mandatory(subformId, "AMPHUR_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"AMPHUR"), AddressCurrentCardlink.getAmphur(), request);
		formError.mandatory(subformId,"PROVINCE_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"PROVINCE"),AddressCurrentCardlink.getProvinceDesc(),request);
		formError.mandatory(subformId,"ZIPCODE_"+ADDRESS_TYPE_CURRENT_CARDLINK,"",LabelUtil.getText(request,"ADDRESS_CURRENT_POPUP_2")+" : "+LabelUtil.getText(request,"ZIPCODE"),AddressCurrentCardlink.getZipcode(),request);
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm) {	
		HashMap<String,AddressDataM> hashAddress = (HashMap<String,AddressDataM>)objectForm;
		AddressDataM addressHome = hashAddress.get(ADDRESS_TYPE_CURRENT_CARDLINK);
		if(null == addressHome){
			addressHome = new AddressDataM();
		}
		HashMap<String,AddressDataM> hashlastAddress = (HashMap<String,AddressDataM>)lastObjectForm;
		AddressDataM lastAddress = hashAddress.get(ADDRESS_TYPE_CURRENT_CARDLINK);
		if(null == lastAddress){
			lastAddress = new AddressDataM();
		}

		AuditFormUtil auditFormUtil = new AuditFormUtil();
		String subformId = getSubFormID();
		auditFormUtil.auditForm(subformId,"ADDRESS_ID",addressHome,lastAddress,request);

		return auditFormUtil.getAuditForm();
	}	

}
