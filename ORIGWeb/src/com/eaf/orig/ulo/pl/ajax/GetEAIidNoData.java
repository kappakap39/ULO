package com.eaf.orig.ulo.pl.ajax;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

//import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.control.message.Message;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.utility.ORIGXRulesTool;
import com.eaf.orig.ulo.pl.app.utility.ServiceReqRespTool;
import com.eaf.orig.ulo.pl.config.ORIGConfig;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
//import com.eaf.ulo.pl.eai.service.EAIModuleService;
//import com.eaf.ulo.pl.eai.util.EAIConstant;
//import com.eaf.xrules.ulo.pl.constant.PLXrulesConstant;
import com.eaf.xrules.ulo.pl.model.XrulesRequestDataM;
//import com.eaf.xrules.ulo.pl.moduleservice.model.EAIDataM;

public class GetEAIidNoData implements AjaxDisplayGenerateInf {
	private  Logger log = Logger.getLogger(GetEAIidNoData.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException{
		
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");
		
		PLOrigFormHandler formHandler = (PLOrigFormHandler)request.getSession().getAttribute(OrigConstant.PL_FORMHANDLER_NAME);
		
		PLApplicationDataM applicationM = formHandler.getAppForm();
		
		String endpoint1011 = ORIGConfig.EAI_CIS1011I01;
				
		String transactionID = request.getParameter("transactionId");
				
		JsonObjectUtil json = new JsonObjectUtil(); 
		
		try{			
			ORIGXRulesTool tool = new ORIGXRulesTool();
			XrulesRequestDataM requestM = tool.MapXrulesRequestDataM(applicationM, null, null);

			if(OrigUtil.isEmptyString(transactionID)){
				ServiceReqRespTool requestResponse = new ServiceReqRespTool();
				transactionID = requestResponse.GenerateTransectionId();
			}
			
			requestM.setTransId(transactionID);
			requestM.setUserM(userM);
			
//            EAIModuleService service = new EAIModuleService();			
//            	service.RequestServiceCIS1011I01(requestM,endpoint1011);
//				            	
//			EAIDataM eaiM = requestM.getEaiM();			
//			if(null == eaiM){
//				eaiM = new EAIDataM();
//			}
//            	
//			if(EAIConstant.CIS1011I01Status.SUCCESS.equals(eaiM.getMsgCIS1011I01())){
//				MapResponse(applicationM, json);
//			}else{
//				if(PLXrulesConstant.WebServiceCode.CONNECTION_ERROR_CIS1011I01.equals(eaiM.getMsgCIS1011I01())){
//					json.CreateJsonObject("ERROR","CIS1011I01 : "+ErrorUtil.getShortErrorMessage(request, "CAN_NOT_CONNECT_EAI_CIS1011I01"));
//				}else{
//					String message = eaiM.getMsgCIS1011I01();
//					if(null == message){
////						message = ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR");
//						message = Message.error();
//					}
//					json.CreateJsonObject("ERROR","CIS1011I01 : "+message);
//				}				
//			}
			
		}catch(Exception e){
//			log.fatal("ERROR >> ",e);
			json = new JsonObjectUtil();
			String message = Message.error(e);
			json.CreateJsonObject("ERROR","CIS1011I01 : "+message);
		}
		return json.returnJson();
	}
	
	private void MapResponse(PLApplicationDataM  applicationM ,JsonObjectUtil json){
				
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		
		PLPersonalInfoDataM personalM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);

		String thaiTitleCode = cacheUtil.getORIGListBoxCodeByDesc(OrigConstant.fieldId.thTitle, personalM.getThaiTitleName());
		if(!OrigUtil.isEmptyString(thaiTitleCode)){
			json.CreateJsonObject("title_thai", thaiTitleCode);
			json.CreateJsonObject("titleThai", personalM.getThaiTitleName());
			personalM.setThaiTitleName(thaiTitleCode);
		}else{
			personalM.setThaiTitleName(null);
		}
		json.CreateJsonObject("name_th", personalM.getThaiFirstName());
		json.CreateJsonObject("surname_th", personalM.getThaiLastName());
		json.CreateJsonObject("middlename_th", personalM.getThaiMidName());

		String engTitleCode = cacheUtil.getORIGListBoxCodeByDesc(OrigConstant.fieldId.ENG_TITLE, personalM.getEngTitleName());
		if(!OrigUtil.isEmptyString(engTitleCode)){
			json.CreateJsonObject("title_eng", engTitleCode);
			json.CreateJsonObject("titleEng", personalM.getEngTitleName());
			personalM.setEngTitleName(engTitleCode);
		}else{
			personalM.setEngTitleName(null);
		}
		
		json.CreateJsonObject("name_eng", personalM.getEngFirstName());
		json.CreateJsonObject("surname_eng", personalM.getEngLastName());
		json.CreateJsonObject("middlename_eng", personalM.getEngMidName());
		json.CreateJsonObject("birth_date", DataFormatUtility.dateEnToStringDateEn(personalM.getBirthDate(),DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
		
		if(personalM.getBirthDate() != null){
			int age = calculateAge(applicationM, personalM.getBirthDate());
			if(age > 0){
				personalM.setAge(age);
				String age_desc = String.valueOf(age)+"";
				log.debug("age_desc >> "+age_desc);
				json.CreateJsonObject("element_age",age_desc);
				json.CreateJsonObject("age_desc",age_desc);
			}
		}
		
		//mapping gender with mapping4
		String gender = cacheUtil.getORIGListBoxCodeByMapping4(OrigConstant.fieldId.GENDER, personalM.getGender());
		if(!OrigUtil.isEmptyString(gender)){
			json.CreateJsonObject("gender", gender);
			personalM.setGender(gender);
		}else{
			personalM.setGender(null);
		}
		
		//mapping marital status with mapping4
		String maritalStatus = cacheUtil.getORIGListBoxCodeByMapping4(OrigConstant.fieldId.MARITAL_STATUS, personalM.getMaritalStatus());
		if(!OrigUtil.isEmptyString(maritalStatus)){
			json.CreateJsonObject("marriage_status", maritalStatus);
			personalM.setMaritalStatus(maritalStatus);
		}else{
			personalM.setMaritalStatus(null);
		}
		//mapping occupation status with mapping4
		String occupation = cacheUtil.getORIGListBoxCodeByMapping4(OrigConstant.fieldId.OCCUPATION, personalM.getOccupation());
		if(!OrigUtil.isEmptyString(occupation)){
			json.CreateJsonObject("occ_occupation", occupation);
			personalM.setOccupation(occupation);
		}else{
			personalM.setOccupation(null);
		}
		
		String positionLevel = cacheUtil.getORIGListBoxCodeByMapping4(OrigConstant.fieldId.POSITION_LEVEL, personalM.getPositionLevel());
		if(!OrigUtil.isEmptyString(positionLevel)){
			json.CreateJsonObject("occ_position_level", positionLevel);
			personalM.setPositionLevel(positionLevel);
		}else{
			personalM.setPositionLevel(null);
		}
		json.CreateJsonObject("professionCode", personalM.getProfession());
		json.CreateJsonObject("occ_old_service_years", String.valueOf(personalM.getTotWorkYear()));
		json.CreateJsonObject("occ_old_service_month", String.valueOf(personalM.getTotWorkMonth()));
		
		//mapping education with mapping4
		String education = cacheUtil.getORIGListBoxCodeByMapping4(OrigConstant.fieldId.EDUCATION, personalM.getDegree());
		if(!OrigUtil.isEmptyString(education)){
			json.CreateJsonObject("education", education);
			personalM.setDegree(education);
		}else{
			personalM.setDegree(null);
		}
		
		//mapping nationality with mapping4
		String nationality = cacheUtil.getORIGListBoxCodeByMapping4(OrigConstant.fieldId.NATIONALITY, personalM.getNationality());
		if(!OrigUtil.isEmptyString(nationality)){
			json.CreateJsonObject("nationality", nationality);
			personalM.setNationality(nationality);
		}else{
			personalM.setNationality(null);
		}

		//mapping profession code with mapping4
		String profession = cacheUtil.getORIGListBoxCodeByMapping4(OrigConstant.fieldId.PROFESSION, personalM.getProfession());
		if(!OrigUtil.isEmptyString(profession)){
			json.CreateJsonObject("occ_occupation_type", profession);
			json.CreateJsonObject("professionCode", profession);
			personalM.setProfession(profession);
			if(OrigConstant.Profession.OTHERS.equals(profession)){
				json.CreateJsonObject("occ_occupation_type_text", personalM.getProfessionOther());
			}else{
				personalM.setProfessionOther(null);
			}
		}
		
	}
	
	private int calculateAge(PLApplicationDataM applicationM,Date date){
		Calendar cAppDate = Calendar.getInstance();
		Date appDate = applicationM.getAppDate();			
		if(null == appDate)	appDate = applicationM.getCreateDate();			
			cAppDate.setTime(appDate);
		int currentYear = cAppDate.get(Calendar.YEAR);
		
		Calendar cBirthDate = Calendar.getInstance();				
			cBirthDate.setTime(date);			
		int birthYear = cBirthDate.get(Calendar.YEAR);
		int age = currentYear-birthYear;
		return age;
	}
}
