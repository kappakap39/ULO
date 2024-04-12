package com.eaf.orig.ulo.app.view.form.manual;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.app.dao.AbbreviationDAOImpl;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class CopyAddressPopupForm  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(CopyAddressPopupForm.class);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");	
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
	String ADDRESS_TYPE_WORK_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_WORK_CARDLINK");
	String ADDRESS_TYPE_DOCUMENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT_CARDLINK");
	String FIELD_ID_COMPANY_TITLE = SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE");	
	String ROLE_DE2 = SystemConstant.getConstant("ROLE_DE2");
	String Blank_Space = " ";
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.COPY_CARDLINK_ADDRESS);		
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
			AbbreviationDAOImpl abbreviationdaoimpl = new AbbreviationDAOImpl();
			String elementPrefix = "";
			String processAction = request.getParameter("FUNCTION");
			String copyAddressType = request.getParameter("COPY_ADDRESS_TYPE");
			String toAddressType = request.getParameter("ADDRESS_TYPE_PAGE");	
			String personalSeq = FormatUtil.toString(personalInfo.getSeq());
			logger.debug("processAction : "+processAction);
			logger.debug("copyAddressType : "+copyAddressType);
			logger.debug("toAddressType : "+toAddressType);
			AddressDataM addressM = new AddressDataM();
			String addressElementId = "";
			if("COPY_CARDLINK".equals(processAction)){
				elementPrefix = "CL_";
				String ADDRESS_NAME = request.getParameter("ADDRESS_NAME");
				String COMPANY_NAME = request.getParameter("COMPANY_NAME");
				String COMPANY_TITLE = request.getParameter("COMPANY_TITLE");
				String DEPARTMENT = request.getParameter("DEPARTMENT");
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
				String ADRSTS = request.getParameter("ADRSTS");
				String RESIDEY = request.getParameter("RESIDEY");
				String RESIDEM = request.getParameter("RESIDEM");			
				logger.debug("RESIDEY >> "+RESIDEY);
				logger.debug("RESIDEM >> "+RESIDEM);
				if(!Util.empty(COMPANY_NAME)){
					COMPANY_NAME = Util.removeNotAllowSpeacialCharactors(COMPANY_NAME.trim());
				}
				addressM.setCompanyName(COMPANY_NAME);
				addressM.setDepartment(DEPARTMENT);
				
				
				if(Util.empty(ADDRESS_ID)){
					ADDRESS_ID = "";
				}	
				addressM.setAddress(ADDRESS_ID);
				
//				if(!Util.empty(MOO) && MOO.length() == 1){
//					MOO = LabelUtil.getText(request,"TEXT_MOO")+MOO;
//				}else if(!Util.empty(MOO)&& MOO.length() == 2 && ADDRESS_ID.length() <= 6){
//					ADDRESS_ID = ADDRESS_ID+" "+LabelUtil.getText(request,"TEXT_MOO");
//					addressM.setAddress(ADDRESS_ID);
//				}
//				addressM.setMoo(MOO);
				if(!ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(toAddressType)){
					if(!Util.empty(MOO) && MOO.length() == 1){
						MOO = LabelUtil.getText(request,"TEXT_MOO")+MOO;
					}else if(!Util.empty(MOO)&& MOO.length() == 2 && ADDRESS_ID.length() <= 6){
						ADDRESS_ID = ADDRESS_ID+" "+LabelUtil.getText(request,"TEXT_MOO");
						addressM.setAddress(ADDRESS_ID);
					}
				}
				addressM.setMoo(MOO);
				if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(toAddressType)){
					if(!Util.empty(SOI)){
						SOI = LabelUtil.getText(request, "TEXT_SOI")+SOI;
					}else SOI="";
					if(!Util.empty(ROAD) || !Util.empty(SOI)){
						ROAD =SOI+(!Util.empty(SOI)?Blank_Space:"")+(!Util.empty(ROAD)?LabelUtil.getText(request, "TEXT_ROAD")+ROAD:"");
					}
					if(!Util.empty(ADDRESS_NAME)){
						ADDRESS_NAME = LabelUtil.getText(request, "TEXT_ADDRESS_NAME")+ADDRESS_NAME;
					}
					if(!Util.empty(BUILDING)){
						BUILDING = LabelUtil.getText(request, "TEXT_BUILDING")+BUILDING;
					}
					if(!Util.empty(ROOM)){
						ROOM = LabelUtil.getText(request, "TEXT_ROOM")+ROOM;
					}
					if(!Util.empty(FLOOR)){
						FLOOR = LabelUtil.getText(request, "TEXT_FLOOR")+FLOOR;
					}
				}else {
					if(!Util.empty(SOI)){
						SOI = LabelUtil.getText(request, "TEXT_SOI")+SOI;
					}
					if(!Util.empty(ROAD)){
						ROAD = LabelUtil.getText(request, "TEXT_ROAD")+ROAD;
					}
					if(!Util.empty(BUILDING)){
						BUILDING = LabelUtil.getText(request, "TEXT_BUILDING")+BUILDING;
					}
					if(!Util.empty(FLOOR)){
						FLOOR = LabelUtil.getText(request, "TEXT_FLOOR")+FLOOR;
					}
				}
				addressM.setVilapt(ADDRESS_NAME);
				addressM.setBuilding(BUILDING);
				addressM.setRoom(ROOM);
				addressM.setFloor(FLOOR);
				addressM.setSoi(SOI);
				addressM.setRoad(ROAD);
				addressM.setCountry(COUNTRY);			
//				if(!PersonalAddressUtil.bkkProvince(PROVINCE)){
//					TAMBOL = LabelUtil.getText(request,"TEXT_TAMBOL")+TAMBOL;
//				}else 
//					if(PersonalAddressUtil.bkkProvince(PROVINCE)){
					if(!Util.empty(TAMBOL)&&!Util.empty(abbreviationdaoimpl.getAbbreviation(TAMBOL))&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
						TAMBOL = LabelUtil.getText(request,"TEXT_TAMBOL")+abbreviationdaoimpl.getAbbreviation(TAMBOL);
					}else if(!Util.empty(TAMBOL)&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
						TAMBOL = LabelUtil.getText(request,"TEXT_TAMBOL")+TAMBOL;
					}else if(!Util.empty(abbreviationdaoimpl.getAbbreviation(TAMBOL))){
						TAMBOL = abbreviationdaoimpl.getAbbreviation(TAMBOL);
					}
//				}
				addressM.setTambol(TAMBOL);
//				if(!PersonalAddressUtil.bkkProvince(PROVINCE)){
//					AMPHUR = LabelUtil.getText(request,"TEXT_AMPHUR")+AMPHUR;
//				}else 
//					if(PersonalAddressUtil.bkkProvince(PROVINCE)){	
					if(!Util.empty(AMPHUR)&&!Util.empty(abbreviationdaoimpl.getAbbreviation(AMPHUR))&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
						AMPHUR = LabelUtil.getText(request,"TEXT_AMPHUR")+abbreviationdaoimpl.getAbbreviation(AMPHUR);
					}else if(!Util.empty(AMPHUR)&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
						AMPHUR = LabelUtil.getText(request,"TEXT_AMPHUR")+AMPHUR;
					}else 
						if(!Util.empty(abbreviationdaoimpl.getAbbreviation(AMPHUR))){
						AMPHUR = abbreviationdaoimpl.getAbbreviation(AMPHUR);
					}
//				}
				addressM.setAmphur(AMPHUR);
//				if(!PersonalAddressUtil.bkkProvince(PROVINCE)){
//					PROVINCE = LabelUtil.getText(request,"TEXT_PROVINCE")+PROVINCE;
//				}else 
//					if(PersonalAddressUtil.bkkProvince(PROVINCE)){
//					if(!Util.empty(PROVINCE)&&!Util.empty(abbreviationdaoimpl.getAbbreviation(PROVINCE))&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
//					PROVINCE = LabelUtil.getText(request,"TEXT_PROVINCE")+abbreviationdaoimpl.getAbbreviation(PROVINCE);
//					PROVINCE = abbreviationdaoimpl.getAbbreviation(PROVINCE);
//					}else if(!Util.empty(PROVINCE)&&!PersonalAddressUtil.bkkProvince(PROVINCE)){
//						PROVINCE = LabelUtil.getText(request,"TEXT_PROVINCE")+PROVINCE;
//						PROVINCE = abbreviationdaoimpl.getAbbreviation(PROVINCE);
//					}else 
					if(!Util.empty(abbreviationdaoimpl.getAbbreviation(PROVINCE))){
						PROVINCE = abbreviationdaoimpl.getAbbreviation(PROVINCE);
					}
//				}
				addressM.setProvinceDesc(PROVINCE);
				addressM.setZipcode(ZIPCODE);
				addressM.setCompanyTitle(COMPANY_TITLE);
				if(!Util.empty(ADRSTS))	addressM.setAdrsts(ADRSTS);
				if(!Util.empty(RESIDEY)&&Util.isBigDecimal(RESIDEY))addressM.setResidey(new BigDecimal(RESIDEY));
				if(!Util.empty(RESIDEM)&&Util.isBigDecimal(RESIDEM))addressM.setResidem(new BigDecimal(RESIDEM));
				if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(toAddressType)){
					addressElementId = "_"+FormatUtil.getAddressElementId(PERSONAL_TYPE,personalSeq,ADDRESS_TYPE_CURRENT_CARDLINK);
				}else if(ADDRESS_TYPE_WORK_CARDLINK.equals(toAddressType)){
					addressElementId = "_"+FormatUtil.getAddressElementId(PERSONAL_TYPE,personalSeq,ADDRESS_TYPE_WORK_CARDLINK);
				}else if(ADDRESS_TYPE_DOCUMENT_CARDLINK.equals(toAddressType)){
					addressElementId = "_"+FormatUtil.getAddressElementId(PERSONAL_TYPE,personalSeq,ADDRESS_TYPE_DOCUMENT_CARDLINK);
				}
			}else{
				addressM = personalInfo.getAddress(copyAddressType);
			}	
			if(null == addressM){
				addressM = new AddressDataM();
			}
			JSONUtil json = new JSONUtil();		
			if(ADDRESS_TYPE_WORK.equals(copyAddressType)){
				json.put(elementPrefix+"COMPANY_NAME"+addressElementId,addressM.getCompanyName());
				json.put(elementPrefix+"COMPANY_NAME", CacheControl.getName(FIELD_ID_COMPANY_TITLE,addressM.getCompanyTitle())+(!Util.empty(addressM.getCompanyTitle())?" "+addressM.getCompanyName():addressM.getCompanyName()));
			}
			if(ADDRESS_TYPE_CURRENT_CARDLINK.equals(toAddressType)){
				json.put(elementPrefix+"MOO_BUILDING_SOI",PersonalAddressUtil.getVilaptBuildingSoiCurrent(addressM,"COPY_CARDLINK".equals(processAction)));
			}else{
				json.put(elementPrefix+"MOO_BUILDING_SOI",PersonalAddressUtil.getVilaptBuildingSoi(addressM,"COPY_CARDLINK".equals(processAction)));
			}
			String roleId = FormControl.getFormRoleId(request);
			if(ROLE_DE2.equals(roleId) && SystemConstant.getArrayListConstant("ADDRESS_TYPE_COPY_TO").contains(toAddressType)){
				AddressDataM addressTo = personalInfo.getAddress(toAddressType);
				if(Util.empty(addressTo))addressTo = new AddressDataM();
				ApplicationGroupDataM applicationGroupPreRole = FormControl.getOrigObjectForm(request);
				if(!isHasDataPreviousRole(applicationGroupPreRole,"DEPARTMENT",addressTo))json.put(elementPrefix+"DEPARTMENT", addressM.getDepartment());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"BUILDING",addressTo))json.put(elementPrefix+"BUILDING", addressM.getBuilding());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"ROOM",addressTo))json.put(elementPrefix+"ROOM", addressM.getRoom());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"FLOOR",addressTo))json.put(elementPrefix+"FLOOR", addressM.getFloor());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"ADDRESS_ID",addressTo))json.put(elementPrefix+"ADDRESS_ID", addressM.getAddress());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"MOO",addressTo))json.put(elementPrefix+"MOO", addressM.getMoo());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"SOI",addressTo))json.put(elementPrefix+"SOI", addressM.getSoi());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"ROAD",addressTo))json.put(elementPrefix+"ROAD", addressM.getRoad());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"COUNTRY",addressTo))json.put(elementPrefix+"COUNTRY", addressM.getCountry());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"TAMBOL",addressTo))json.put(elementPrefix+"TAMBOL", addressM.getTambol());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"AMPHUR",addressTo))json.put(elementPrefix+"AMPHUR", addressM.getAmphur());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"PROVINCE",addressTo))json.put(elementPrefix+"PROVINCE", addressM.getProvinceDesc());
				if(!isHasDataPreviousRole(applicationGroupPreRole,"ZIPCODE",addressTo))json.put(elementPrefix+"ZIPCODE", addressM.getZipcode());	
				if(!isHasDataPreviousRole(applicationGroupPreRole,"ADDRESS_NAME",addressTo))json.put(elementPrefix+"ADDRESS_NAME", addressM.getVilapt());
			}else{
				json.put(elementPrefix+"DEPARTMENT", addressM.getDepartment());
				json.put(elementPrefix+"BUILDING", addressM.getBuilding());
				json.put(elementPrefix+"ROOM", addressM.getRoom());
				json.put(elementPrefix+"FLOOR", addressM.getFloor());
				json.put(elementPrefix+"ADDRESS_ID", addressM.getAddress());
				json.put(elementPrefix+"MOO", addressM.getMoo());
				json.put(elementPrefix+"SOI", addressM.getSoi());
				json.put(elementPrefix+"ROAD", addressM.getRoad());
				json.put(elementPrefix+"COUNTRY", addressM.getCountry());
				json.put(elementPrefix+"TAMBOL", addressM.getTambol());
				json.put(elementPrefix+"AMPHUR", addressM.getAmphur());
				json.put(elementPrefix+"PROVINCE", addressM.getProvinceDesc());
				json.put(elementPrefix+"ZIPCODE", addressM.getZipcode());	
				json.put(elementPrefix+"ADDRESS_NAME", addressM.getVilapt());
			}
			return responseData.success(json.getJSON());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	public boolean isHasDataPreviousRole(ApplicationGroupDataM applicationGroup,String fieldElement,Object object){
		return CompareSensitiveFieldUtil.isPreviousCompareData(applicationGroup,fieldElement,object);
	}
}
