package com.eaf.orig.ulo.app.view.form.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.ElementFormUtil;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CardLinkAddressPopupForm  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(CardLinkAddressPopupForm.class);
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	private String TYPE_SAMPLE_CARDLINK = SystemConstant.getConstant("TYPE_SAMPLE_CARDLINK");
	private String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");		
	private String ADDRESS_TYPE_WORK_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_WORK_CARDLINK");	
	private String ADDRESS_TYPE_DOCUMENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT_CARDLINK");
	private int LINE1_MAX = Integer.parseInt(SystemConstant.getConstant("LINE1_MAX"));	
	private int LINE2_MAX = Integer.parseInt(SystemConstant.getConstant("LINE2_MAX"));	
	private String COLOR_GREEN = SystemConstant.getConstant("COLOR_GREEN");	
	private String COLOR_RED = SystemConstant.getConstant("COLOR_RED");
	private String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");		
	private String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");	
	private String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DISPLAY_CARDLINK_ADDRESS);
		try{			
			Object masterObjectForm = FormControl.getMasterObjectForm(request);		
			PersonalInfoDataM personalInfo = null;
			if(masterObjectForm instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
				PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
				personalInfo = personalApplicationInfo.getPersonalInfo();
			}		
			String elementPrefix = "";
			String processAction = request.getParameter("FUNCTION");
			String copyAddressType = request.getParameter("COPY_ADDRESS_TYPE");
			String toAddressType = request.getParameter("ADDRESS_TYPE_PAGE");
			String personalType = personalInfo.getPersonalType();		
			String personalSeq = FormatUtil.toString(personalInfo.getSeq());
			logger.debug("processAction : "+processAction);
			logger.debug("copyAddressType : "+copyAddressType);
			logger.debug("toAddressType : "+toAddressType);
			logger.debug("personalType : "+personalType);
			AddressDataM addressM = new AddressDataM();
			String addressElementId = "";
			if(TYPE_SAMPLE_CARDLINK.equals(processAction)){
				elementPrefix = "CL_";
				if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(toAddressType)){
					addressElementId = "_"+FormatUtil.getAddressElementId(personalType,personalSeq,ADDRESS_TYPE_CURRENT_CARDLINK);
				}else if(ADDRESS_TYPE_WORK_CARDLINK.equals(toAddressType)){
					addressElementId = "_"+FormatUtil.getAddressElementId(personalType,personalSeq,ADDRESS_TYPE_WORK_CARDLINK);
				}else if(ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(toAddressType)){
					addressElementId = "_"+FormatUtil.getAddressElementId(personalType,personalSeq,ADDRESS_TYPE_DOCUMENT_CARDLINK);
				}
				String COMPANY_NAME = request.getParameter(elementPrefix+"COMPANY_NAME");
				String DEPARTMENT = request.getParameter(elementPrefix+"DEPARTMENT");
				String BUILDING = request.getParameter(elementPrefix+"BUILDING");
				String ROOM = request.getParameter(elementPrefix+"ROOM");
				String FLOOR = request.getParameter(elementPrefix+"FLOOR");
				String ADDRESS_ID = request.getParameter(elementPrefix+"ADDRESS_ID");
				String MOO = request.getParameter(elementPrefix+"MOO");
				String SOI = request.getParameter(elementPrefix+"SOI");
				String MOO_BUILDING_SOI = request.getParameter(elementPrefix+"MOO_BUILDING_SOI");
				String ROAD = request.getParameter(elementPrefix+"ROAD");
				String COUNTRY = request.getParameter(elementPrefix+"COUNTRY");
				String TAMBOL = request.getParameter(elementPrefix+"TAMBOL");
				String AMPHUR = request.getParameter(elementPrefix+"AMPHUR");
				String PROVINCE = request.getParameter(elementPrefix+"PROVINCE");
				String ZIPCODE = request.getParameter(elementPrefix+"ZIPCODE");	
				if(!Util.empty(COMPANY_NAME)){
					COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(COMPANY_NAME.trim());
				}
				addressM.setCompanyName(COMPANY_NAME);
				addressM.setDepartment(DEPARTMENT);
				addressM.setBuilding(BUILDING);
				addressM.setRoom(ROOM);
				addressM.setFloor(FLOOR);
				addressM.setAddress(ADDRESS_ID);
				addressM.setMoo(MOO);
				if(Util.empty(SOI)){
					addressM.setSoi(MOO_BUILDING_SOI);
				}else{
					addressM.setSoi(SOI);
				}
				addressM.setRoad(ROAD);
				addressM.setCountry(COUNTRY);
				addressM.setTambol(TAMBOL);
				addressM.setAmphur(AMPHUR);
				addressM.setProvinceDesc(PROVINCE);
				addressM.setZipcode(ZIPCODE);
			}else{
				if(ADDRESS_TYPE_CURRENT.equals(copyAddressType)){
					addressM = personalInfo.getAddress(ADDRESS_TYPE_CURRENT);
				}else if(ADDRESS_TYPE_WORK.equals(copyAddressType)){
					addressM = personalInfo.getAddress(ADDRESS_TYPE_WORK);
				}else if(ADDRESS_TYPE_DOCUMENT.equals(copyAddressType)){
					addressM = personalInfo.getAddress(ADDRESS_TYPE_DOCUMENT);
				}
			}
			
			if(null == addressM){
				addressM = new AddressDataM();
			}
			ElementFormUtil elementForm = new ElementFormUtil();
			if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(toAddressType)){	
				String cardlinkLine1 = PersonalAddressUtil.getCurrentCardlinkLine1(request,addressM);
				String cardlinkLine2 = PersonalAddressUtil.getCardlinkLine2(request,addressM);
				elementForm.put("ADDRESS_CARD_LINK_LINE1"+addressElementId,cardlinkLine1, "");
				elementForm.put("COUNT_ADDRESS_CARD_LINK_LINE1"+addressElementId,
						String.valueOf(cardlinkLine1.length()),getAddressTextColor("LINE1",cardlinkLine1.length()));
				elementForm.put("ADDRESS_CARD_LINK_LINE2"+addressElementId,cardlinkLine2, "");
				elementForm.put("COUNT_ADDRESS_CARD_LINK_LINE2"+addressElementId,
						String.valueOf(cardlinkLine2.length()), getAddressTextColor("LINE2",cardlinkLine2.length()));				
			}else if(ADDRESS_TYPE_WORK_CARDLINK.equals(toAddressType)){		
				String cardlinkLine1 = PersonalAddressUtil.getWorkCardlinkLine1(request,addressM);
				String cardlinkLine2 = PersonalAddressUtil.getCardlinkLine2(request,addressM);
				elementForm.put("ADDRESS_CARD_LINK_LINE1"+addressElementId,cardlinkLine1, "");
				elementForm.put("COUNT_ADDRESS_CARD_LINK_LINE1"+addressElementId,
						String.valueOf(cardlinkLine1.length()),getAddressTextColor("LINE1",cardlinkLine1.length()));	
				elementForm.put("ADDRESS_CARD_LINK_LINE2"+addressElementId,cardlinkLine2, "");
				elementForm.put("COUNT_ADDRESS_CARD_LINK_LINE2"+addressElementId
						,String.valueOf(cardlinkLine2.length()),getAddressTextColor("LINE2",cardlinkLine2.length()) );					
			}else if(ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(toAddressType)){
				String cardlinkLine1 = PersonalAddressUtil.getDocCardlinkLine1(request,addressM);
				String cardlinkLine2 = PersonalAddressUtil.getCardlinkLine2(request,addressM);
				elementForm.put("ADDRESS_CARD_LINK_LINE1"+addressElementId,cardlinkLine1, "");
				elementForm.put("COUNT_ADDRESS_CARD_LINK_LINE1"+addressElementId,
						String.valueOf(cardlinkLine1.length()),getAddressTextColor("LINE1",cardlinkLine1.length()));	
				elementForm.put("ADDRESS_CARD_LINK_LINE2"+addressElementId,cardlinkLine2, "");
				elementForm.put("COUNT_ADDRESS_CARD_LINK_LINE2"+addressElementId
						,String.valueOf(cardlinkLine2.length()),getAddressTextColor("LINE2",cardlinkLine2.length()));				
			}
			return responseData.success(elementForm.toJson());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}	
	public String getAddressTextColor(String line ,int length){
		String color = "";
		if("LINE1".equals(line)){
			if(length <= LINE1_MAX){//GREEN
				color = COLOR_GREEN;
			}else{//RED
				color = COLOR_RED;	
			}
		}else if("LINE2".equals(line)){
			if(length <= LINE2_MAX){//GREEN
				color = COLOR_GREEN;
			}else{//RED
				color = COLOR_RED;	
			}
		}		
		return color;
	}
}
