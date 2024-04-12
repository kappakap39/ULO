package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.manual.SensitiveFieldAction;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil.CisCustomerResult;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.CardLinkDataM;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class RekeySensitiveForm extends FormHelper implements FormAction{
	private static transient Logger logger = Logger.getLogger(RekeySensitiveForm.class);
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT");	
	String SENSITIVE_FIELD_ID_NO =SystemConstant.getConstant("SENSITIVE_FIELD_ID_NO");
	String APPLICATION_TYPE_INCREASE  = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	@Override
	public Object getObjectForm() {
		ORIGFormHandler ORIGForm = ORIGFormHandler.getForm(request);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		logger.debug("applicationGroup >>>> "+applicationGroup);
		if(Util.empty(applicationGroup)){
			applicationGroup = new ApplicationGroupDataM();
		}
		return applicationGroup;
	}
	
	@Override
	public String processForm(){
		String ROLE_DE1_1 = SystemConstant.getConstant("ROLE_DE1_1");
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		String roleId = FormControl.getFormRoleId(request);
		logger.info("transactionId : "+transactionId);
		logger.debug("roleId : "+roleId);
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DIH_CUSTOMER_INFO);
		Gson gson = new Gson();
			ORIGFormHandler ORIGForm = ORIGFormHandler.getForm(request);
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
			ORIGForm.setObjectForm(applicationGroup);
			ORIGForm.compareSensitiveFormAction(request,MConstant.FLAG.SUBMIT); // set submit flag bcoz  fields is already compare n will not show on compare popup after validate
			ORIGForm.checkCompareDataChangeForPrevRole(request);
//			String ValidateForm = "ValidateForm";	
//			String VALIDATE_INCREASE_CARD_REQUEST  = SystemConstant.getConstant("VALIDATE_INCREASE_CARD_REQUEST");
			
			HashMap<String,CompareDataM> comparisonFields = new HashMap<String, CompareDataM>();
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getUsingPersonalInfo();
			try{
				if(ROLE_DE1_1.equals(roleId)){
					if(!Util.empty(personalInfos)){
						UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
						String userId = userM.getUserName();
						List<CisCustomerResult> cisCustomerResults = new ArrayList<CisCustomerResult>();
						for(PersonalInfoDataM personalInfo:personalInfos){
							if(!Util.empty(personalInfo)){
								String CID_TYPE = personalInfo.getCidType();
								String ID_NO = personalInfo.getIdno();
								String TH_FIRST_NAME = personalInfo.getThFirstName();
								String TH_LAST_NAME = personalInfo.getThLastName();
								String EN_BIRTH_DATE = (null!=personalInfo.getBirthDate())?personalInfo.getBirthDate().toString():"";
								String PERSONAL_TYPE = personalInfo.getPersonalType();
								logger.debug("CID_TYPE : "+CID_TYPE);
								logger.debug("ID_NO : "+ID_NO);
								logger.debug("TH_FIRST_NAME : "+TH_FIRST_NAME);
								logger.debug("TH_LAST_NAME : "+TH_LAST_NAME);
								logger.debug("EN_BIRTH_DATE : "+EN_BIRTH_DATE);
								logger.debug("PERSONAL_TYPE : "+PERSONAL_TYPE);
								HashMap<String,CompareDataM> comparisonFieldInfos = new HashMap<String, CompareDataM>();
								CisCustomerResult cisCustomerResult = PersonalInfoUtil.updateCisDataProcess(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE,applicationGroup.getLifeCycle()
										,userId,roleId,personalInfo,comparisonFieldInfos,false);
								DIHQueryResult<String> dihDataResult  = cisCustomerResult.getDihQueryResult();
								if(ResponseData.SUCCESS.equals(dihDataResult.getStatusCode())){
									if(!Util.empty(comparisonFieldInfos)){
										comparisonFields.putAll(comparisonFieldInfos);
									}
									personalInfo.setPersonalError("");
								}else{
									String errorMsg = processCisError(applicationGroup,personalInfo,cisCustomerResult,request);
									cisCustomerResult.setErrorMsg(errorMsg);
								}
								setComparisonField(comparisonFields,request);
								cisCustomerResults.add(cisCustomerResult);
							}
						}
						if(!Util.empty(cisCustomerResults)){
							for(CisCustomerResult cisCustomerResult : cisCustomerResults){
								DIHQueryResult<String> dihDataResult  = cisCustomerResult.getDihQueryResult();
								if(!Util.empty(cisCustomerResult.getErrorMsg())){
									return gson.toJson(responseData.error(dihDataResult,cisCustomerResult.getErrorMsg()));
								}
							}
						}
					}
					if(APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType())){
						for(PersonalInfoDataM personal : personalInfos){
							ArrayList<String> appId = new ArrayList<String>();
							ArrayList<ApplicationDataM> applications = applicationGroup.getApplications(personal.getPersonalId(), personal.getPersonalType(), PERSONAL_RELATION_APPLICATION_LEVEL);
							ArrayList<ApplicationDataM> allApplications =applicationGroup.getApplications();
							if(!Util.empty(applications)){				
								for(ApplicationDataM application:applications){
									CardDataM card = application.getCard();
									if(!Util.empty(card) && !Util.empty(personal.getCisNo())){
										DIHQueryResult<CardLinkDataM> queryResult = new DIHQueryResult<CardLinkDataM>();
										DIHProxy dihProxy = new DIHProxy();
										queryResult = dihProxy.getCardLinkInfoENCPT(card.getCardNo());
										CardLinkDataM increaseCardRequest  = queryResult.getResult();
										String statusCode = queryResult.getStatusCode();
										if(ResponseData.SUCCESS.equals(statusCode)){
											if(Util.empty(increaseCardRequest.getCardNo())){
												logger.debug(" remove Application rekey : null CardNo");
												appId.add(application.getApplicationRecordId());
											}else if(Util.empty(increaseCardRequest.getMainCis()) || !personal.getCisNo().equals(increaseCardRequest.getMainCis())){
												logger.debug(" remove Application rekey : cis ");
												appId.add(application.getApplicationRecordId());
											}
										}else if(ResponseData.SYSTEM_EXCEPTION.equals(statusCode)){
											ErrorData errorData = queryResult.getErrorData();
											if(Util.empty(errorData)){
												errorData = new ErrorData();
											}
											return gson.toJson(responseData.error(errorData.getErrorInformation()));
										}
									}
								}
								for (Iterator<ApplicationDataM> iterator = allApplications.iterator(); iterator.hasNext();) {
									 ApplicationDataM application =  iterator.next();
									 if(appId.contains(application.getApplicationRecordId())){
										 //iterator.remove();
										 return gson.toJson(responseData.error(MessageUtil.getText(request, "MSG_CARD_DONT_MAP_CIS")));
									 }
								}
							}
						}
					}
				}
				request.getSession().removeAttribute(SensitiveFieldAction.REKEY_FIELDS);
			}catch(Exception e){
				logger.fatal("ERROR",e);
				return gson.toJson(responseData.error(e.getLocalizedMessage()));
			}
		return gson.toJson(responseData.success());
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
	private void removeComparisonField(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo,HttpServletRequest request)throws Exception{
		HashMap<String, CompareDataM> comparisonFields = applicationGroup.getComparisonField(CompareDataM.SoruceOfData.CIS);
		logger.debug("comparisonFields : "+comparisonFields);
		if(!Util.empty(comparisonFields)){
			boolean isRemoved = false;
			for(Iterator<Entry<String, CompareDataM>>iterator = comparisonFields.entrySet().iterator(); iterator.hasNext(); ) {
				Map.Entry<String,CompareDataM> comparisonEntry = iterator.next();
				CompareDataM compare = comparisonEntry.getValue();
				 logger.debug("compare : "+compare);
				 if(!Util.empty(compare)){
					 String srcOfData = FormatUtil.displayText(compare.getSrcOfData());
					 String uniqueLevel = FormatUtil.displayText(compare.getUniqueLevel());
					 String uniqueId = FormatUtil.displayText(compare.getUniqueId());
					 int lifeCycle = compare.getLifeCycle();
					 if(CompareDataM.SoruceOfData.CIS.equals(srcOfData) 
							 && CompareDataM.UniqueLevel.PERSONAL.equals(uniqueLevel) 
							 && uniqueId.equals(personalInfo.getPersonalId()) 
							 && lifeCycle==applicationGroup.getLifeCycle()){
						 iterator.remove();
						 isRemoved = true;
					 }
				 }
		    }
			if(isRemoved){
				setComparisonField(comparisonFields,request);
			}
		}
	}
	private String processCisError(ApplicationGroupDataM applicationGroup,PersonalInfoDataM personalInfo,CisCustomerResult cisCustomerResult,HttpServletRequest request)throws Exception{
		String PERSONAL_ERROR_CIS_DUPLICATE = SystemConstant.getConstant("PERSONAL_ERROR_CIS_DUPLICATE");
		String PERSONAL_ERROR_DIH_FAILED = SystemConstant.getConstant("PERSONAL_ERROR_DIH_FAILED");
		String REKEY_FUNCTION_POINT_APPLICANT_CIS_DUPLICATE = SystemConstant.getConstant("REKEY_FUNCTION_POINT_APPLICANT_CIS_DUPLICATE");
		String REKEY_FUNCTION_POINT_SUPPLEMENTARY_CIS_DUPLICATE = SystemConstant.getConstant("REKEY_FUNCTION_POINT_SUPPLEMENTARY_CIS_DUPLICATE");
		String REKEY_FUNCTION_POINT_DIH_FAILED = SystemConstant.getConstant("REKEY_FUNCTION_POINT_DIH_FAILED");
		personalInfo.setCisNo("");
		String foundDataCisMore1RowFlag = cisCustomerResult.getFoundDataCisMore1RowFlag();
		String functionPoint = "";
		if(FLAG_YES.equals(foundDataCisMore1RowFlag)){
			 personalInfo.setPersonalError(PERSONAL_ERROR_CIS_DUPLICATE);
			 removeComparisonField(applicationGroup,personalInfo,request);
			 if(PERSONAL_TYPE_APPLICANT.equals(personalInfo.getPersonalType())){
				 functionPoint = REKEY_FUNCTION_POINT_APPLICANT_CIS_DUPLICATE;
			 }else if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalInfo.getPersonalType())){
				 functionPoint = REKEY_FUNCTION_POINT_SUPPLEMENTARY_CIS_DUPLICATE;
			 }
		}else{
			personalInfo.setPersonalError(PERSONAL_ERROR_DIH_FAILED);
			functionPoint = REKEY_FUNCTION_POINT_DIH_FAILED;
		}
		return functionPoint;
	}
	private boolean isCisMoreThan1Row(DIHQueryResult<String> dihDataResult){
		logger.debug("dih StatusCode : "+dihDataResult.getStatusCode());
		logger.debug("dih Result : "+dihDataResult.getResult());
		if(!ResponseData.SUCCESS.equals(dihDataResult.getStatusCode()) && MConstant.FLAG_Y.equals(dihDataResult.getResult())){
			return true;
		}
		return false;
	}
}