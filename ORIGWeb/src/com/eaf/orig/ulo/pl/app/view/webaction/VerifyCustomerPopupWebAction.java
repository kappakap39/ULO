package com.eaf.orig.ulo.pl.app.view.webaction;

import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.control.FrontController;
import com.eaf.j2ee.pattern.control.event.Event;
import com.eaf.j2ee.pattern.control.event.EventResponse;
import com.eaf.j2ee.pattern.view.webaction.WebAction;
import com.eaf.j2ee.pattern.view.webaction.WebActionHelper;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.constant.CacheConstant;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesDataPhoneVerificationDataM;
import com.eaf.xrules.ulo.pl.model.app.rule.PLXRulesVerificationResultDataM;

public class VerifyCustomerPopupWebAction extends WebActionHelper implements WebAction{

	Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public Event toEvent() {		
		return null;
	}

	@Override
	public boolean requiredModelRequest() {		
		return false;
	}

	@Override
	public boolean processEventResponse(EventResponse response) {		
		return false;
	}

	@Override
	public boolean preModelRequest() {
		
		logger.debug("[preModelRequest]");
		
		PLApplicationDataM appM = PLOrigFormHandler.getPLApplicationDataM(getRequest());
	
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
	
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();
		
		if(null == xrulesVerM) {
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		
		ORIGCacheUtil cacheUtil = new ORIGCacheUtil();
		
		Vector<ORIGCacheDataM> verCusVect = cacheUtil.loadORIGCacheDataMByNameAndBusClass(CacheConstant.CacheName.VerifyCustomer,appM.getBusinessClassId());
		
		for(ORIGCacheDataM cacheM : verCusVect){
			if(!this.ValidatePLXRulesDataPhoneVerificationM(appM,cacheM, getRequest())){
				xrulesVerM.add(this.MappPLXRulesDataPhoneVerificationM(appM, cacheM, getRequest()));
			}
		}
		/**Call Ilog Chack Case Auto Pass Verify Customer #SeptemWi*/
			
		return true;
	}
	public PLXRulesDataPhoneVerificationDataM MappPLXRulesDataPhoneVerificationM( PLApplicationDataM plApplicationM,ORIGCacheDataM cacheM,HttpServletRequest request){
		PLXRulesDataPhoneVerificationDataM dataPhoneVerM = new PLXRulesDataPhoneVerificationDataM();
			dataPhoneVerM.setSeq(Integer.parseInt(cacheM.getCode()));
			dataPhoneVerM.setFieldId(cacheM.getCode());
			dataPhoneVerM.setFieldDesc(cacheM.getShortDesc());	
			dataPhoneVerM.setResult(HTMLRenderUtil.RadioBoxCompare.NotCheck);
			dataPhoneVerM.setFieldValue(MappingFieldResult(cacheM.getCode(),plApplicationM,request));
		if(OrigConstant.FLAG_Y.equalsIgnoreCase(cacheM.getSystemID1()))
			dataPhoneVerM.setIsMandatory(OrigConstant.FLAG_Y);
		return dataPhoneVerM;
	}
	public boolean ValidatePLXRulesDataPhoneVerificationM( PLApplicationDataM appM,ORIGCacheDataM cacheM ,HttpServletRequest request){
		if(null == appM) appM = new PLApplicationDataM();			
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);			
		PLXRulesVerificationResultDataM xrulesVerM = personalM.getXrulesVerification();			
		if(null == xrulesVerM) {
			xrulesVerM = new PLXRulesVerificationResultDataM();
			personalM.setXrulesVerification(xrulesVerM);
		}
		Vector<PLXRulesDataPhoneVerificationDataM> dataPhoneVect = xrulesVerM.getxRulesDataPhoneVerificationDataMs();
		if(!ORIGUtility.isEmptyVector(dataPhoneVect)){					
			for(PLXRulesDataPhoneVerificationDataM dataVerM :dataPhoneVect){					
				if(cacheM.getCode().equalsIgnoreCase(dataVerM.getFieldId())){
					dataVerM.setFieldDesc(cacheM.getShortDesc());	
					dataVerM.setFieldValue(this.MappingFieldResult(cacheM.getCode(),appM,request));
					if(ORIGUtility.isEmptyString(dataVerM.getResult())){
						dataVerM.setResult(HTMLRenderUtil.RadioBoxCompare.NotCheck);
					}				
					if(OrigConstant.FLAG_Y.equalsIgnoreCase(cacheM.getSystemID1()))
						dataVerM.setIsMandatory(OrigConstant.FLAG_Y);
					return true;
				}					
			}
		}
		return false;
	}
	

	public String MappingFieldResult(String code ,PLApplicationDataM appM ,HttpServletRequest request){
		
		if(null == appM) appM = new PLApplicationDataM();
		PLPersonalInfoDataM personalM = appM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		try{		
			switch (Integer.parseInt(code)) {
				case CacheConstant.FieldID.Field70.NAME_ENG:
					 String engFirstName = request.getParameter("name_eng");
					 String engLastName = request.getParameter("surname_eng");
					 personalM.setEngFirstName(engFirstName);
					 personalM.setEngLastName(engLastName);
					 return HTMLRenderUtil.displayHTML(personalM.getEngFirstName())+" "+HTMLRenderUtil.displayHTML(personalM.getEngLastName());
				case CacheConstant.FieldID.Field70.BIRTH_DAY:
					 personalM.setBirthDate(DataFormatUtility.StringEnToDateEn(request.getParameter("birth_date"), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
					 return DataFormatUtility.dateEnToStringDateEn(personalM.getBirthDate(), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S);
				case CacheConstant.FieldID.Field70.DAYS_OF_BIRTH:
					 personalM.setBirthDate(DataFormatUtility.StringEnToDateEn(request.getParameter("birth_date"), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
					 return DataFormatUtility.dateEnToStringDateEn(personalM.getBirthDate(), DataFormatUtility.DateFormatType.FORMAT_DAY_OF_WEEK);
				case CacheConstant.FieldID.Field70.Constellation:
					 personalM.setBirthDate(DataFormatUtility.StringEnToDateEn(request.getParameter("birth_date"), DataFormatUtility.DateFormatType.FORMAT_DDMMYYY_S));
					 return DataFormatUtility.dateEnToStringDateEn(personalM.getBirthDate(), DataFormatUtility.DateFormatType.FORMAT_CONSTELLATION);
				case CacheConstant.FieldID.Field70.ID_NO:
					 String id_no = request.getParameter("identification_no");
					 personalM.setIdNo(id_no);
					 return HTMLRenderUtil.displayHTML(personalM.getIdNo());
				case CacheConstant.FieldID.Field70.ACCOUNT_NO:
					 return "";
				case CacheConstant.FieldID.Field70.OLD_KEC_NO:
					 return "";
				default:
					break;
			}		
		}catch(Exception e){
			logger.debug("Error "+e.getMessage());
		}
		return "";
	}
	
	@Override
	public int getNextActivityType() {		
		return FrontController.POPUP_DIALOG;
	}

	@Override
	public boolean getCSRFToken() {
		// TODO Auto-generated method stub
		return false;
	}

}
