package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.ulo.cache.store.CacheBranchInfomationManager;
import com.eaf.ulo.cache.store.CacheExpireStoreManager;

public class BRANCH_RECEIVE_CARDProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(BRANCH_RECEIVE_CARDProperty.class);
	private String ADDRESS_PLACE_RECEIVE_CARD_BRANCH = SystemConstant.getConstant("ADDRESS_PLACE_RECEIVE_CARD_BRANCH");	
	private String CACHE_EXP_BRANCH_INFO = SystemConstant.getConstant("CACHE_EXP_BRANCH_INFO");	

	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("BRANCH_RECEIVE_CARDProperty.validateFlag...");		
		Object masterObjectForm = getMasterObjectForm();
		if(null == masterObjectForm){
			masterObjectForm = FormControl.getMasterObjectForm(request);
		}
		PersonalInfoDataM personalInfo = null;
		if(masterObjectForm instanceof ApplicationGroupDataM){
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
			String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
			personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		}else if (masterObjectForm instanceof PersonalApplicationInfoDataM){
			PersonalApplicationInfoDataM personalApplicationInfo = (PersonalApplicationInfoDataM)masterObjectForm;
			personalInfo = personalApplicationInfo.getPersonalInfo();
		}		
		
		logger.debug("MANDATORY_BRANCH_RECEIVE_CARD BY PLACE_RECEIVE_CARD >> "+personalInfo.getPlaceReceiveCard());
		ArrayList<String> MANDATORY_BRANCH_RECEIVE_CARD = SystemConstant.getArrayListConstant("MANDATORY_BRANCH_RECEIVE_CARD");
		if(MANDATORY_BRANCH_RECEIVE_CARD.contains(personalInfo.getPlaceReceiveCard())){
			return ValidateFormInf.VALIDATE_SUBMIT;
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();		
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
		PersonalInfoDataM lastpersonalInfo = (PersonalInfoDataM)lastObjectForm;
		
		if(Util.empty(personalInfo)){
			personalInfo = new PersonalInfoDataM();
		}
		if(Util.empty(lastpersonalInfo)){
			lastpersonalInfo = new PersonalInfoDataM();
		}
		
		if(ADDRESS_PLACE_RECEIVE_CARD_BRANCH.equals(personalInfo.getPlaceReceiveCard())){
			boolean compareFlag = CompareObject.compare(personalInfo.getBranchReceiveCard(), lastpersonalInfo.getBranchReceiveCard(),null);
			if(!compareFlag){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalInfo.getPersonalType(), LabelUtil.getText(request, "BRANCH_RECEIVE_CARD"), request));
				audit.setOldValue(FormatUtil.displayText(getBranch(lastpersonalInfo.getBranchReceiveCard())));
				audit.setNewValue(FormatUtil.displayText(getBranch(personalInfo.getBranchReceiveCard())));
				audits.add(audit);
			}
		}
		return audits;
	}
	
	private String getBranch(String branchId){
		KbankBranchInfoDataM branchInfo = CacheExpireStoreManager.getCacheProperties(CACHE_EXP_BRANCH_INFO, branchId);
		if(null == branchInfo){
			DIHQueryResult<KbankBranchInfoDataM> branchInfoResult = DIHProxy.getBranchInfoByRC_CD(branchId);
			branchInfo = branchInfoResult.getResult();
			CacheExpireStoreManager.putCache( CACHE_EXP_BRANCH_INFO, "",branchId, branchInfo);
		}
		return branchInfo.getBranchName();
	}
}
