package com.eaf.service.rest.controller.followup;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.SaleInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpCSVContentDataM;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpResultApplicationDataM;

public class FollowUpResultUtil {
	private static transient Logger logger = Logger.getLogger(FollowUpResultUtil.class);
	public static void mapFollowUpData(ApplicationGroupDataM applicationGroup,FollowUpResultApplicationDataM followUpResult){
		String followUpStatus = followUpResult.getFollowUpStatus();
		String applicationGroupId = applicationGroup.getApplicationGroupId();
		String userId = followUpResult.getUserId();
		VerificationResultDataM verificationResult = applicationGroup.getVerificationResult();
		if(null != verificationResult && !Util.empty(verificationResult.getVerResultId())){
			verificationResult.setRefId(applicationGroupId);
			verificationResult.setVerLevel(MConstant.REF_LEVEL.APPLICATION_GROUP);	
			verificationResult.setDocFollowUpStatus(followUpStatus);	
			verificationResult.setUpdateBy(userId);
		}else{
			verificationResult = new VerificationResultDataM();
			verificationResult.setRefId(applicationGroupId);
			verificationResult.setVerLevel(MConstant.REF_LEVEL.APPLICATION_GROUP);
			verificationResult.setDocFollowUpStatus(followUpStatus);
			String verResultId = GenerateUnique.generate(OrigConstant.SeqNames.XRULES_VERIFICATION_RESULT_PK); 
			verificationResult.setVerResultId(verResultId);
			verificationResult.setCreateBy(userId);
			verificationResult.setUpdateBy(userId);
			applicationGroup.setVerificationResult(verificationResult);
		}
		String FIELD_ID_CONTACT_TIME =SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
		FollowUpCSVContentDataM followUpContent = followUpResult.getFollowUpContent();
		if(null != followUpContent){
			String cusAvailble = followUpContent.getCustAvailableTime();
			logger.debug("cusAvailble : "+cusAvailble);
			String contactTime = CacheControl.getName(FIELD_ID_CONTACT_TIME,"MAPPING8",cusAvailble,"CHOICE_NO");
			logger.debug("contactTime : "+contactTime);
			String telType = followUpContent.getTelType();
			String telNo = followUpContent.getTelNo();
			String telExt = followUpContent.getTelExt();
			logger.debug("telType : "+telType);
			logger.debug("telNo : "+telNo);
			logger.debug("telExt : "+telExt);
			ArrayList<String> personalTypes = new ArrayList<String>();
			personalTypes.add(SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT"));
			personalTypes.add(SystemConstant.getConstant("PERSONAL_TYPE_INFO"));
			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos(personalTypes);
			if(null != personalInfos){
				for (PersonalInfoDataM personalInfo : personalInfos) {
					personalInfo.setContactTime(contactTime);
					personalInfo.setPegaPhoneType(telType);
					personalInfo.setPegaPhoneNo(telNo);
					personalInfo.setPegaPhoneExt(telExt);
				}
			}
		}
		String SALE_TYPE_REFERENCE_SALES = SystemConstant.getConstant("SALE_TYPE_REFERENCE_SALES");
		SaleInfoDataM saleInfo = applicationGroup.getSaleInfoByType(SALE_TYPE_REFERENCE_SALES);
		if(null == saleInfo){
			saleInfo = new SaleInfoDataM();
			saleInfo.setSalesType(SALE_TYPE_REFERENCE_SALES);
			applicationGroup.addSaleInfos(saleInfo);
		}
		saleInfo.setApplicationGroupId(applicationGroupId);
		logger.debug("RecommenderEmpID : "+followUpContent.getRecommenderEmpID());
		saleInfo.setSalesId(followUpContent.getRecommenderEmpID());
		SaleInfoUtil.mapSaleInfoDetails(saleInfo);
	}
}
