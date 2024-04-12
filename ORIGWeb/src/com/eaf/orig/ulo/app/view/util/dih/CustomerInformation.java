package com.eaf.orig.ulo.app.view.util.dih;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.common.utility.CompareSensitiveUtility;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.view.form.FormHandleManager;
import com.eaf.j2ee.pattern.view.form.FormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalAddressUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil.CisCustomerResult;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.google.gson.Gson;

public class CustomerInformation implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(CustomerInformation.class);	
	String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT");		
	String FIELD_ID_OCCUPATION = SystemConstant.getConstant("FIELD_ID_OCCUPATION");
	String FORM_NAME_ORIG = SystemConstant.getConstant("FORM_NAME_ORIG");
	String FORM_NAME_ENTITY = SystemConstant.getConstant("FORM_NAME_ENTITY");
	String FIELD_ID_PROFESSION = SystemConstant.getConstant("FIELD_ID_PROFESSION");	
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String OCCUPATION_OTHER = SystemConstant.getConstant("OCCUPATION_OTHER");
	String PROFESSION_OTHER = SystemConstant.getConstant("PROFESSION_OTHER");	
	String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");	
	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");	
	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");	
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");	
//	ArrayList<ErrorResponseDataM> errors = new ArrayList<>();
	
	private Personal getLastPersonal(HttpServletRequest request){	
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}
		Personal personal = new Personal();
		personal.setCisNo(personalInfo.getCisNo());
		personal.setCidType(personalInfo.getCidType());
		personal.setIdNo(personalInfo.getIdno());
		return personal;
	}
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		String PERSONAL_ERROR_CIS_DUPLICATE = SystemConstant.getConstant("PERSONAL_ERROR_CIS_DUPLICATE");
		String PERSONAL_ERROR_DIH_FAILED = SystemConstant.getConstant("PERSONAL_ERROR_DIH_FAILED");
		CisCustomerResult cisCustomerResult = new PersonalInfoUtil().new CisCustomerResult();
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DIH_CUSTOMER_INFO);
		try{
			Personal lastPersonal = getLastPersonal(request);
			logger.debug("lastPersonal >> "+lastPersonal);		
			FormHandleManager formHandleManager = (FormHandleManager) request.getSession(true).getAttribute("formHandlerManager");
			if(null == formHandleManager){
				formHandleManager = new FormHandleManager();
			}		
			String formName = formHandleManager.getCurrentFormHandler();
			logger.debug("formName >> "+formName);
			FormHandler currentForm = (FormHandler) (request.getSession(true).getAttribute(formName));
			if(null != currentForm){
				currentForm.setProperties(request);
			}		
					
			String CID_TYPE = request.getParameter("CID_TYPE");
			String ID_NO = request.getParameter("ID_NO");		
			String TH_FIRST_NAME = request.getParameter("TH_FIRST_NAME");
			if(!Util.empty(TH_FIRST_NAME)){
				TH_FIRST_NAME = TH_FIRST_NAME.toUpperCase();
			}		
			String TH_LAST_NAME = request.getParameter("TH_LAST_NAME");	
			if(!Util.empty(TH_LAST_NAME)){
				TH_LAST_NAME = TH_LAST_NAME.toUpperCase();
			}
			String EN_BIRTH_DATE = request.getParameter("EN_BIRTH_DATE");
			String elementFieldId = request.getParameter("elementFieldId");
			if(!Util.empty(EN_BIRTH_DATE)){
//				EN_BIRTH_DATE = FormatUtil.toString(FormatUtil.toDate(EN_BIRTH_DATE, FormatUtil.EN));
				EN_BIRTH_DATE = FormatUtil.toDate(EN_BIRTH_DATE,FormatUtil.EN).toString();
			}
			logger.debug("elementFieldId : "+elementFieldId);	
			logger.debug("CID_TYPE : "+CID_TYPE);	
			logger.debug("ID_NO : "+ID_NO);			
			logger.debug("TH_FIRST_NAME : "+TH_FIRST_NAME);	
			logger.debug("TH_LAST_NAME : "+TH_LAST_NAME);	
			logger.debug("EN_BIRTH_DATE : "+EN_BIRTH_DATE);
			
			Object masterObjectForm = FormControl.getMasterObjectForm(request);		
			PersonalInfoDataM personalInfo = null;
			int lifeCycle = 0;
			if(masterObjectForm instanceof ApplicationGroupDataM){
				ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
				lifeCycle = applicationGroup.getLifeCycle();
			}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
				PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
				personalInfo = personalApplicationInfo.getPersonalInfo();
				lifeCycle = personalApplicationInfo.getMaxLifeCycle();
			}		
			int searchCisPersonalInfoFlag = searchCisPersonalInfoFlag(lastPersonal,elementFieldId,CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE);
			logger.debug("searchCisPersonalInfoFlag >> "+searchCisPersonalInfoFlag);
			if(SearchCisActionFlag.SEARCH_CIS == searchCisPersonalInfoFlag){
				UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
				String userId = userM.getUserName();
				HashMap<String, CompareDataM> comparisonFields = getComparisonField(request);
				if(null == comparisonFields){
					comparisonFields = new HashMap<String, CompareDataM>();
				}
				String roleId = FormControl.getFormRoleId(request);
				cisCustomerResult = PersonalInfoUtil.updateCisDataProcess(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE,lifeCycle,userId,roleId,personalInfo,comparisonFields,true);
				logger.debug("cisCustomerResult : "+cisCustomerResult);
				String cisNo = 	cisCustomerResult.getCisNo();
				String foundDataCisMore1RowFlag = cisCustomerResult.getFoundDataCisMore1RowFlag();
				logger.debug("cisNo : "+cisNo);
				logger.debug("foundDataCisMore1RowFlag : "+foundDataCisMore1RowFlag);
				DIHQueryResult<String>  dihCisNoResult =cisCustomerResult.getDihQueryResult();
				if(ResponseData.SUCCESS.equals(dihCisNoResult.getStatusCode())){
					personalInfo.setPersonalError("");
					cisCustomerResult = new Gson().fromJson(cisCustomerResult.getDihQueryResult().getResult(), com.eaf.orig.ulo.control.util.PersonalInfoUtil.CisCustomerResult.class);
					if(MConstant.FLAG.NO.equals(foundDataCisMore1RowFlag) && !Util.empty(cisNo)){
						setComparisonField(CompareSensitiveUtility.fillterDuplicateCompareFieldNameType(comparisonFields),request);
						cisCustomerResult.setRefreshFormFlag(MConstant.FLAG.YES);
					}else if(!Util.empty(personalInfo.getCisNo())){
						clearCustomerValue(personalInfo,elementFieldId);
						cisCustomerResult.setRefreshFormFlag(MConstant.FLAG.YES);
					}
				}else{
					if(MConstant.FLAG.YES.equals(foundDataCisMore1RowFlag)){
						 personalInfo.setPersonalError(PERSONAL_ERROR_CIS_DUPLICATE);
					}else{
						personalInfo.setPersonalError(PERSONAL_ERROR_DIH_FAILED);
					}
					clearCustomerValue(personalInfo,elementFieldId);
					return responseData.error(dihCisNoResult);
				}
			}else if(SearchCisActionFlag.CLEAR_VALUE == searchCisPersonalInfoFlag && !Util.empty(personalInfo.getCisNo())){
				clearCustomerValue(personalInfo,elementFieldId);
				cisCustomerResult.setRefreshFormFlag(MConstant.FLAG.YES);
			}else if(SearchCisActionFlag.CLEAR_VALUE == searchCisPersonalInfoFlag && "CID_TYPE".equals(elementFieldId)){
				clearCustomerValue(personalInfo,elementFieldId);
				cisCustomerResult.setRefreshFormFlag(MConstant.FLAG.YES);
			}
			if("CID_TYPE".equals(elementFieldId)){
				cisCustomerResult.setRefreshFormFlag(MConstant.FLAG.YES);
			}
			
			PersonalAddressUtil.updatePersonalAddressSeq(personalInfo);
			PersonalInfoUtil.clearNotMatchSoruceOfDataCis(request);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e,ErrorController.ERROR_MESSAGE_NAME.ERROR_REFER_TO_WORK_SHEET_DIH);
		}		
		Gson gson = new Gson(); 
		return responseData.success(gson.toJson(cisCustomerResult));
	}
	
	private void clearCustomerValue(PersonalInfoDataM personalInfo,String elementFieldId){
		logger.debug("clearCustomerValue..");
		if("CID_TYPE".equals(elementFieldId)){
			personalInfo.setIdno(null);
		}
		personalInfo.setCisNo(null);
		personalInfo.setCisPrimSegment(null);
		personalInfo.setCisPrimSubSegment(null);
		personalInfo.setCisSecSegment(null);
		personalInfo.setCisSecSubSegment(null);
		personalInfo.setCisCustType(null);
		personalInfo.setThTitleCode(null);
		personalInfo.setThTitleDesc(null);
		personalInfo.setThFirstName(null);
		personalInfo.setThMidName(null);
		personalInfo.setThLastName(null);
		personalInfo.setEnTitleCode(null);
		personalInfo.setEnTitleDesc(null);
		personalInfo.setEnFirstName(null);
		personalInfo.setEnMidName(null);
		personalInfo.setEnLastName(null);
		personalInfo.setBirthDate(null);
		personalInfo.setVisaType(null);
		personalInfo.setVisaExpDate(null);
		personalInfo.setCidExpDate(null);
		personalInfo.setMarried(null);
		personalInfo.setDegree(null);
		personalInfo.setOccupation(null);	
		personalInfo.setProfession(null);
		personalInfo.setGender(null);
		if(!CIDTYPE_IDCARD.equals(personalInfo.getCidType())){
			personalInfo.setNationality(null);
		}
		personalInfo.setNationality(null);	
		personalInfo.setMobileNo(null);
		personalInfo.setEmailPrimary(null);
		ArrayList<AddressDataM>  addressList = personalInfo.getAddresses();
		if(!Util.empty(addressList)){
			for(AddressDataM address : addressList){
				String ADDRESS_TYPE = address.getAddressType();
				if(ADDRESS_TYPE_CURRENT.equals(ADDRESS_TYPE)){
					address.setVilapt(null);
					address.setBuilding(null);
					address.setRoom(null);
					address.setFloor(null);
					address.setAddress(null);
					address.setMoo(null);
					address.setSoi(null);
					address.setRoad(null);
					address.setCountry(null);
					address.setTambol(null);
					address.setAmphur(null);
					address.setProvinceDesc(null);
					address.setZipcode(null);
				}else if(ADDRESS_TYPE_WORK.equals(ADDRESS_TYPE)){
					address.setCompanyName(null);
					address.setVilapt(null);
					address.setBuilding(null);
					address.setRoom(null);
					address.setFloor(null);
					address.setAddress(null);
					address.setMoo(null);
					address.setSoi(null);
					address.setRoad(null);
					address.setCountry(null);
					address.setTambol(null);
					address.setAmphur(null);
					address.setProvinceDesc(null);
					address.setZipcode(null);
				}else if(ADDRESS_TYPE_DOCUMENT.equals(ADDRESS_TYPE)){
					address.setVilapt(null);
					address.setBuilding(null);
					address.setRoom(null);
					address.setFloor(null);
					address.setAddress(null);
					address.setMoo(null);
					address.setSoi(null);
					address.setRoad(null);
					address.setCountry(null);
					address.setTambol(null);
					address.setAmphur(null);
					address.setProvinceDesc(null);
					address.setZipcode(null);				
				}
			}
		}
	}
	public class SearchCisActionFlag{
		public static final int NO_ACTION = -1;
		public static final int SEARCH_CIS = 0;
		public static final int CLEAR_VALUE = 1;
	}	
	
	@SuppressWarnings("serial")
	public class Personal implements Serializable,Cloneable{
		private String cisNo;
		private String cidType;
		private String idNo;
		public String getCidType() {
			return cidType;
		}
		public void setCidType(String cidType) {
			this.cidType = cidType;
		}
		public String getIdNo() {
			return idNo;
		}
		public void setIdNo(String idNo) {
			this.idNo = idNo;
		}		
		public String getCisNo() {
			return cisNo;
		}
		public void setCisNo(String cisNo) {
			this.cisNo = cisNo;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Personal [cisNo=");
			builder.append(cisNo);
			builder.append(", cidType=");
			builder.append(cidType);
			builder.append(", idNo=");
			builder.append(idNo);
			builder.append("]");
			return builder.toString();
		}	
		
	}
	
	private int searchCisPersonalInfoFlag(Personal lastPersonal,String elementFieldId,String CID_TYPE,String ID_NO
			,String TH_FIRST_NAME,String TH_LAST_NAME,String EN_BIRTH_DATE){
		int searchCisPersonalInfoFlag = SearchCisActionFlag.NO_ACTION;
		logger.debug("elementFieldId >> "+elementFieldId);
		logger.debug("lastPersonal >> "+lastPersonal);	
		logger.debug("ID_NO >> "+ID_NO);
		logger.debug("CID_TYPE >> "+CID_TYPE);
		if("CID_TYPE".equals(elementFieldId) && !CID_TYPE.equals(lastPersonal.getCidType())){
			logger.debug("change CID_TYPE..");
			searchCisPersonalInfoFlag = SearchCisActionFlag.CLEAR_VALUE;
		}else{
			if(CIDTYPE_IDCARD.equals(CID_TYPE)){
				if("ID_NO".equals(elementFieldId)||"CID_TYPE".equals(elementFieldId)){
					if(!Util.empty(ID_NO) && !Util.empty(CID_TYPE)){
						if(!ID_NO.equals(lastPersonal.getIdNo()) || !CID_TYPE.equals(lastPersonal.getCidType())|| Util.empty(lastPersonal.getCisNo())){
							searchCisPersonalInfoFlag = SearchCisActionFlag.SEARCH_CIS;
						}
					}else if(!Util.empty(lastPersonal.getCisNo())){
						searchCisPersonalInfoFlag = SearchCisActionFlag.CLEAR_VALUE;
					}
				}
			}else if(CIDTYPE_PASSPORT.equals(CID_TYPE)||CIDTYPE_MIGRANT.equals(CID_TYPE)){
				logger.debug("CIDTYPE_PASSPORT,CIDTYPE_MIGRANT");
				if(!Util.empty(ID_NO) && !Util.empty(CID_TYPE) && !Util.empty(TH_FIRST_NAME) 
						&& !Util.empty(TH_LAST_NAME) && !Util.empty(EN_BIRTH_DATE)){
					logger.debug("ID_NO,CID_TYPE,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE");
					if(!ID_NO.equals(lastPersonal.getIdNo()) || !CID_TYPE.equals(lastPersonal.getCidType()) || Util.empty(lastPersonal.getCisNo())){
						searchCisPersonalInfoFlag = SearchCisActionFlag.SEARCH_CIS;
					}
				}else if(!Util.empty(lastPersonal.getCisNo())){
					searchCisPersonalInfoFlag = SearchCisActionFlag.CLEAR_VALUE;
				}
			}
		}
		return searchCisPersonalInfoFlag;
	}
	
	private HashMap<String, CompareDataM> getComparisonField(HttpServletRequest request){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		return applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
	}
	private void  setComparisonField(HashMap<String, CompareDataM> comparisonFields,HttpServletRequest request){
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<ComparisonGroupDataM> comparisonGroups = applicationGroup.getComparisonGroups();
		if(null == comparisonGroups){
			comparisonGroups = new ArrayList<ComparisonGroupDataM>();
			applicationGroup.setComparisonGroups(comparisonGroups);
		}
		ComparisonGroupDataM comprisionGroup = applicationGroup.getComparisonGroups(CompareDataM.SoruceOfData.CIS);
		if(null == comprisionGroup){
			comprisionGroup = new ComparisonGroupDataM();
			comprisionGroup.setSrcOfData(CompareDataM.SoruceOfData.CIS);
			comparisonGroups.add(comprisionGroup);
		}
		comprisionGroup.setComparisonFields(comparisonFields);
	}
}
