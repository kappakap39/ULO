package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDataM;
import com.eaf.orig.ulo.model.app.RequiredDocDetailDataM;
import com.eaf.orig.ulo.model.app.VerificationResultDataM;
import com.eaf.orig.ulo.model.document.DocumentRequestDataM;

@SuppressWarnings("serial")
public class DocumentCheckList  extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(DocumentCheckList.class);
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String VERIFY_CUSTOMER_FORM = SystemConstant.getConstant("VERIFY_CUSTOMER_FORM"); 
	String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
	String FLAG_NO = SystemConstant.getConstant("FLAG_NO");
	ArrayList<String> ROLE_IGNORE_SAVE_DOCCHECKLIST_SUBFORM  = SystemConstant.getArrayListConstant("ROLE_IGNORE_SAVE_DOCCHECKLIST_SUBFORM");
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		String roleId = FormControl.getFormRoleId(request);
		String formId = FormControl.getFormId(request);
		logger.debug("roleId : "+roleId);
		logger.debug("formId : "+formId);
		boolean requiredDocFlag = applicationGroup.getRequiredDocFlag();
		logger.debug("requiredDocFlag : "+requiredDocFlag);
		
		if(ROLE_IGNORE_SAVE_DOCCHECKLIST_SUBFORM.contains(roleId) || VERIFY_CUSTOMER_FORM.equals(formId) || !requiredDocFlag){
			return;
		}
 		applicationGroup.setDocumentCheckLists(new ArrayList<DocumentCheckListDataM>());
		ArrayList<ElementInf> elements = ImplementControl.getElements(MConstant.IMPLEMENT_TYPE.FOLLOWED_DOC_REASON);
		PersonalInfoDataM personalApplicant = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		if(!Util.empty(personalApplicant)){
			VerificationResultDataM verificationResult = personalApplicant.getVerificationResult();
			if(null != verificationResult){
				ArrayList<RequiredDocDataM> requiredDocs = verificationResult.getRequiredDocs();
				//Defect I094
				if(!Util.empty(verificationResult.getVerCusResult()) && requiredDocFlag && VERIFY_CUSTOMER_FORM.equals(formId)){
//					verificationResult.setDocFollowUpStatus(FLAG_YES);
					applicationGroup.setAlreadyFollowFlag(FLAG_YES);
				}else {
//					verificationResult.setDocFollowUpStatus(FLAG_NO);
					applicationGroup.setAlreadyFollowFlag(FLAG_NO);
				}
				if(null != requiredDocs){
					for(RequiredDocDataM requiredDoc : requiredDocs){
						if(!Util.empty(requiredDoc.getRequiredDocDetails())){
							for(RequiredDocDetailDataM requiredDocDetail : requiredDoc.getRequiredDocDetails()){
								for(ElementInf elementInf:elements){
									logger.debug("requiredDocDetail.getDocumentCode : "+requiredDocDetail.getDocumentCode());
									logger.debug("Receive Flage : "+requiredDocDetail.getReceivedFlag());
									if(MConstant.FLAG_Y.equals(elementInf.renderElementFlag(request,applicationGroup))){
										DocumentRequestDataM  documentRequest = new DocumentRequestDataM();
											documentRequest.setSeq(personalApplicant.getSeq());
											documentRequest.setDocumentCode(requiredDocDetail.getDocumentCode());
											documentRequest.setApplicantType(PERSONAL_TYPE_APPLICANT);
											documentRequest.setReceivedFlag(requiredDocDetail.getReceivedFlag());
											documentRequest.setPersonalID(personalApplicant.getPersonalId());
											documentRequest.setDocTypeId(requiredDoc.getScenarioType());
										elementInf.setObjectRequest(documentRequest);
										elementInf.processElement(request, applicationGroup);
									}
								}
							}
						}					
					}
				}
			}
		}
		
		ArrayList<PersonalInfoDataM> personalSupplymentarys = applicationGroup.getPersonalInfos(PERSONAL_TYPE_SUPPLEMENTARY);
		if(!Util.empty(personalSupplymentarys)){
			for(PersonalInfoDataM personalSupplymentary:personalSupplymentarys){
				VerificationResultDataM verificationResult = personalSupplymentary.getVerificationResult();
				if(null != verificationResult){
					ArrayList<RequiredDocDataM> requiredDocs = verificationResult.getRequiredDocs();
					//Defect I094
					if(!Util.empty(verificationResult.getVerCusResult()) && requiredDocFlag && VERIFY_CUSTOMER_FORM.equals(formId)){
						verificationResult.setDocFollowUpStatus(FLAG_YES);
					}else {
						verificationResult.setDocFollowUpStatus(FLAG_NO);
					}
					if(null != requiredDocs){
						for(RequiredDocDataM requiredDoc : requiredDocs){
							if(!Util.empty(requiredDoc.getRequiredDocDetails())){
								for(RequiredDocDetailDataM requiredDocDetail : requiredDoc.getRequiredDocDetails()){
									for(ElementInf elementInf:elements){
										if(MConstant.FLAG_Y.equals(elementInf.renderElementFlag(request,applicationGroup))){
											DocumentRequestDataM  documentRequest = new DocumentRequestDataM();
												documentRequest.setSeq(personalSupplymentary.getSeq());
												documentRequest.setDocumentCode(requiredDocDetail.getDocumentCode());
												documentRequest.setApplicantType(PERSONAL_TYPE_SUPPLEMENTARY);
												documentRequest.setReceivedFlag(requiredDocDetail.getReceivedFlag());	
												documentRequest.setPersonalID(personalSupplymentary.getPersonalId());
												documentRequest.setDocTypeId(requiredDoc.getScenarioType());
											elementInf.setObjectRequest(documentRequest);
											elementInf.processElement(request, applicationGroup);
										}
									}
								}
							}
						}
					}
				}
			}			
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		return null;
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) 
	{
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		logger.debug("<<DocumentCheckList renderSubformFlag>>");			
		if(FLAG_YES.equals(applicationGroup.foundLowIncome())){
			return MConstant.FLAG_N;
		}		
		return MConstant.FLAG_Y;
	}
}
