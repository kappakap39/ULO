package com.eaf.orig.ulo.app.view.util.dih;

//import java.util.ArrayList;
//import java.util.HashMap;

import org.apache.log4j.Logger;

//import com.eaf.core.ulo.common.display.FormatUtil;
//import com.eaf.core.ulo.common.message.MessageErrorUtil;
//import com.eaf.core.ulo.common.model.ErrorData;
//import com.eaf.core.ulo.common.model.ResponseData;
//import com.eaf.core.ulo.common.util.Util;
//import com.eaf.im.rest.common.model.ServiceResponse;
//import com.eaf.j2ee.pattern.control.ErrorController;
//import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
//import com.eaf.orig.ulo.constant.MConstant;
//import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
//import com.eaf.orig.ulo.control.util.PersonalInfoUtil.CisCustomerResult;
//import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
//import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
//import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
//import com.eaf.orig.ulo.model.compare.CompareDataM;
//import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;

public class CustomerInfoUtil{
	private static transient Logger logger = Logger.getLogger(CustomerInfoUtil.class);
	/*
	public static void setComparisonField(ApplicationGroupDataM applicationGroup,HashMap<String, CompareDataM> comparisonFields){
		logger.debug("setComparisonField");
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
	public static ProcessResponse updateComparisonsProcess(PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields,String srcOfData,String userId,String roleId){
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			String foundDataCisMore1RowFlag = MConstant.FLAG.NO;
			String ID_NO = personalInfo.getIdno();
			String CID_TYPE = personalInfo.getCidType();
			String TH_FIRST_NAME = personalInfo.getThFirstName();
			String TH_LAST_NAME = personalInfo.getThLastName();
			String EN_BIRTH_DATE = FormatUtil.display(personalInfo.getBirthDate(),FormatUtil.EN,FormatUtil.Format.ddMMyyyy);
			String CIS_NO = personalInfo.getCisNo();
			logger.debug("ID_NO : "+ID_NO);
			logger.debug("CID_TYPE : "+CID_TYPE);
			logger.debug("TH_FIRST_NAME : "+TH_FIRST_NAME);
			logger.debug("TH_LAST_NAME : "+TH_LAST_NAME);
			logger.debug("EN_BIRTH_DATE : "+EN_BIRTH_DATE);
			logger.debug("CIS_NO : "+CIS_NO);
			if(Util.empty(CIS_NO)){
//				CustomerInformation customerInfo = new CustomerInformation();
//				CisCustomerResult cisCustomerResult = customerInfo.getCisNo(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE);
				CisCustomerResult cisCustomerResult = PersonalInfoUtil.getCisNoResult(CID_TYPE,ID_NO,TH_FIRST_NAME,TH_LAST_NAME,EN_BIRTH_DATE);
				CIS_NO = 	cisCustomerResult.getCisNo();
				foundDataCisMore1RowFlag = cisCustomerResult.getFoundDataCisMore1RowFlag();
			}			
			logger.debug("foundDataCisMore1RowFlag : "+foundDataCisMore1RowFlag);
			logger.debug("CIS_NO : "+CIS_NO);
			if(MConstant.FLAG.NO.equals(foundDataCisMore1RowFlag)){
				if(!Util.empty(CIS_NO)){
					if(null == comparisonFields){
						comparisonFields = new HashMap<String, CompareDataM>();
					}
					logger.debug("comparisonFields : "+comparisonFields);
					DIHProxy dihProxy = new DIHProxy();
					DIHQueryResult<String>  personalInfoMapperResult = dihProxy.personalDataMapper(CIS_NO,personalInfo,comparisonFields,false);
					if(!ResponseData.SUCCESS.equals(personalInfoMapperResult.getStatusCode())){					 
						processResponse.setStatusCode(personalInfoMapperResult.getStatusCode());
						processResponse.setErrorData(personalInfoMapperResult.getErrorData());
						return processResponse;
					}			
					personalInfo.setCisNo(CIS_NO);
					int index = 1;
					for (String elementId : comparisonFields.keySet()) {
						CompareDataM comparisonField = comparisonFields.get(elementId);
						comparisonField.setRole(roleId);
						comparisonField.setCreateBy(userId);
						comparisonField.setUpdateBy(userId);
						comparisonField.setSeq(index++);
					}
				}
			}else{
				processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processResponse.setErrorData(ErrorController.error(ResponseData.SystemType.CIS,ErrorData.ErrorType.DATA_INCORRECT,MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW_DE2")));
				processResponse.setData(MessageErrorUtil.getText("ERROR_CIS_MORE_THAN_1_ROW_DE2"));
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(ErrorController.error(e));
			processResponse.setData(e.getLocalizedMessage());
		}
		return processResponse;
	}
	public static void updateComparisons(PersonalInfoDataM personalInfo,HashMap<String, CompareDataM> comparisonFields,String srcOfData,String userId,String roleId)throws Exception{
		try{
			ProcessResponse processResponse = updateComparisonsProcess(personalInfo,comparisonFields,srcOfData,userId,roleId);
			if(!processResponse.getStatusCode().equals(ServiceResponse.Status.SUCCESS)){
				throw new Exception(processResponse.getData());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			throw new Exception(e.getLocalizedMessage());
		}
	}
	*/
}
