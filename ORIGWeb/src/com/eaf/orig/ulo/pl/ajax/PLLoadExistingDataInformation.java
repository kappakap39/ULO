package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

//import com.eaf.orig.shared.dao.PLORIGDAOFactory;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.app.utility.JsonObjectUtil;
import com.eaf.orig.ulo.pl.app.view.webaction.AddressUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLAddressDataM;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;


public class PLLoadExistingDataInformation implements AjaxDisplayGenerateInf{

	Logger logger = Logger.getLogger(PLLoadExistingDataInformation.class);
	
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		
		logger.debug("[getDisplayObject]..");
		
		String idNo = request.getParameter("identification_no");
		
		logger.debug("[getDisplayObject]...identification_no "+idNo);
		
		PLAddressDataM addressDataM = null;
		
		JsonObjectUtil json = new JsonObjectUtil();
		
		try{
			
			ORIGCacheUtil cacheUtil = ORIGCacheUtil.getInstance();
			
			PLApplicationDataM applicationM = PLOrigFormHandler.getPLApplicationDataM(request);
			
			if(applicationM == null) applicationM = new PLApplicationDataM();
						
			PLPersonalInfoDataM personalM = PLORIGEJBService.getORIGDAOUtilLocal().LoadPersonalInfoModel(idNo, null);
			
			if(ORIGUtility.isEmptyString(personalM.getIdNo())) return "";
			
			/**Remove Old PersonalID #SeptemWi*/
			personalM.setPersonalID(null);
			personalM.setPersonalType(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			
			if(ORIGUtility.isEmptyString(personalM.getNationality())){
				personalM.setNationality("01");
			}
			if(ORIGUtility.isEmptyString(personalM.getRace())){
				personalM.setRace("01");
			}			
			personalM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			
			/**Set plPersonalInfoM To Session #SeptemWi*/
			applicationM.add(personalM, PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);			
			
			PLPersonalInfoDataM personalInfoM = applicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			
			logger.debug("[getDisplayObject]...personalInfoM.idNo "+personalInfoM.getIdNo());
			
			/**Create Json Response #SeptemWi*/		
			
			json.CreateJsonObject("customertype", HTMLRenderUtil.displayText(personalInfoM.getCustomerType()));
			
			json.CreateJsonObject("element_cisNo", HTMLRenderUtil.displayText(personalInfoM.getCisNo()));
			
			json.CreateJsonObject("cardtype", HTMLRenderUtil.displayHTML(personalInfoM.getCidType()));
			
			json.CreateJsonObject("card_expire_date", DataFormatUtility.dateToString(personalInfoM.getCidExpDate(), "dd/MM/yyyy"));
						
			json.CreateJsonObject("titleThai", cacheUtil.getNaosCacheDisplayNameDataM(3, personalInfoM.getThaiTitleName()));
			
			json.CreateJsonObject("name_th", HTMLRenderUtil.displayHTML(personalInfoM.getThaiFirstName()));
			
			json.CreateJsonObject("title_thai", HTMLRenderUtil.displayHTML(personalInfoM.getThaiTitleName()));
			
			json.CreateJsonObject("surname_th", HTMLRenderUtil.displayHTML(personalInfoM.getThaiLastName()));
			
			json.CreateJsonObject("middlename_th", HTMLRenderUtil.displayHTML(personalInfoM.getThaiMidName()));
						
			json.CreateJsonObject("old_surname_th", HTMLRenderUtil.displayHTML(personalInfoM.getThaiOldLastName()));
			
			json.CreateJsonObject("titleEng", HTMLRenderUtil.displayHTML( personalInfoM.getEngTitleName()));
			
			json.CreateJsonObject("name_eng", HTMLRenderUtil.displayHTML(personalInfoM.getEngFirstName()));
			
			json.CreateJsonObject("title_eng", HTMLRenderUtil.displayHTML(personalInfoM.getEngTitleName()));
			
			json.CreateJsonObject("surname_eng", HTMLRenderUtil.displayHTML(personalInfoM.getEngLastName()));
			
			json.CreateJsonObject("middlename_eng", HTMLRenderUtil.displayHTML(personalInfoM.getEngMidName()));
			
			json.CreateJsonObject("birth_date",DataFormatUtility.dateToString(personalInfoM.getBirthDate(), "dd/MM/yyyy"));
			
			json.CreateJsonObject("element_age",DataFormatUtility.displayZeroEmpty(personalInfoM.getAge()));
			
			json.CreateJsonObject("gender",HTMLRenderUtil.displayHTML(personalInfoM.getGender()));
			
			json.CreateJsonObject("education", HTMLRenderUtil.displayHTML(""));
			
			json.CreateJsonObject("nationality", HTMLRenderUtil.displayHTML(personalInfoM.getNationality()));			
			
			json.CreateJsonObject("race",HTMLRenderUtil.displayHTML(personalInfoM.getRace()));
			
			json.CreateJsonObject("marriage_status",HTMLRenderUtil.displayHTML(personalInfoM.getMaritalStatus()));			
			
			json.CreateJsonObject("no_of_children", DataFormatUtility.displayZeroEmpty(personalInfoM.getNoOfchild()));
			
			json.CreateJsonObject("email1",HTMLRenderUtil.displayHTML(personalInfoM.getEmail1()));
			
			json.CreateJsonObject("sub_email",HTMLRenderUtil.displayHTML(personalInfoM.getEmail2()));
			
			AddressUtil addressUtil  = new AddressUtil();
 
			if(!ORIGUtility.isEmptyVector(personalInfoM.getAddressVect())){				
				for( int i=0; i < personalInfoM.getAddressVect().size(); i++ ){
					addressDataM = (PLAddressDataM) personalInfoM.getAddressVect().get(i);
					addressDataM.setPersonalID(null);
					json.CreateJsonObject("addressResult",addressUtil.CreatePLAddressM(addressDataM, personalInfoM.getPersonalType()
											, i+1, HTMLRenderUtil.DISPLAY_MODE_EDIT , request,personalInfoM.getDepartment()));
				}
			}else{
				json.CreateJsonObject("addressResult",addressUtil.CreateNorecPLAddressM());
			}			
			
		} catch (Exception e) {
			logger.fatal("Error : ",e);
		}
		
		return json.returnJson();
	}	
	
}
