package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.displaymode.ConsentModelDisplayMode;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;

public class IAPersonalApplicantForm extends FormHelper implements FormAction {
	private static transient Logger logger = Logger.getLogger(IAPersonalApplicantForm.class);
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_RELATION_APPLICATION_LEVEL = SystemConstant.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
	String IM_PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("IM_PERSONAL_TYPE_APPLICANT");
	String IM_PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("IM_PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public Object getObjectForm() {
		String PERSONAL_TYPE = getRequestData("REQ_PERSONAL_TYPE");
		String PERSONAL_ID = getRequestData("REQ_PERSONAL_ID");
		logger.debug("PERSONAL_TYPE >> " + PERSONAL_TYPE);
		logger.debug("PERSONAL_ID >> " + PERSONAL_ID);		
		PersonalApplicationInfoDataM personalApplicantInfo = new PersonalApplicationInfoDataM();				
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(PERSONAL_ID);
		logger.debug("personalInfo >> " + personalInfo);		
		if (null == personalInfo) {
			int PERSONAL_SEQ = OrigApplicationForm.getPersonalSeq(PERSONAL_TYPE,applicationGroup);
			personalInfo = new PersonalInfoDataM();
			personalInfo.setPersonalType(PERSONAL_TYPE);
			personalInfo.setSeq(PERSONAL_SEQ);		
		}
		String personalId = personalInfo.getPersonalId();
		if (Util.empty(personalId)) {
			personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
		}
		personalInfo.setPersonalId(personalId);
		logger.debug("personalInfo.getPersonalType >> " + personalInfo.getPersonalType());
		logger.debug("personalInfo.getSeq >> " + personalInfo.getSeq());		
		personalApplicantInfo.setPersonalInfo(personalInfo);
		if(Util.empty(personalInfo.getCidType())){
			ConsentModelDisplayMode docCheck = new ConsentModelDisplayMode();
			String doucumentCodeNonThai = SystemConstant.getConstant("DOCUMENT_TYPE_NON_THAI_NATIONALITY");
			String doucumentCodeThai = SystemConstant.getConstant("DOCUMENT_TYPE_THAI_NATIONALITY");
			String doucumentCodePassport = SystemConstant.getConstant("DOCUMENT_TYPE_PASSPORT");
			String doucumentCodeAlien = SystemConstant.getConstant("DOCUMENT_TYPE_ALIEN_ID");
			boolean checkNonThai= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodeNonThai);
			boolean checkThai= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodeThai);
			boolean checkPassport= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodePassport);
			boolean checkAlien= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCodeAlien);
			if(checkNonThai){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY"));
			}else if(checkAlien){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_NON_THAI_NATINALITY"));
			}else if(checkThai){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_IDCARD"));
			}else if(checkPassport){
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_PASSPORT"));
			}else{
				personalInfo.setCidType(SystemConstant.getConstant("CIDTYPE_IDCARD"));
			}
		}
		return personalApplicantInfo;
	}

	@Override
	public String processForm() {
		ORIGFormHandler ORIGForm = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null == personalInfos){
			personalInfos = new ArrayList<PersonalInfoDataM>();
			applicationGroup.setPersonalInfos(personalInfos);
		}
		PersonalApplicationInfoDataM personalApplicantInfo = (PersonalApplicationInfoDataM) objectForm;
		PersonalInfoDataM personalInfo = personalApplicantInfo.getPersonalInfo();
		String personalId = personalInfo.getPersonalId();
		String personalType = personalInfo.getPersonalType();
		int personalSeq = personalInfo.getSeq();
		logger.debug("personalId >> "+personalId);
		logger.debug("personalType >> "+personalType);
		logger.debug("personalSeq >> "+personalSeq);
		int personalIndex = -1;
		try {
			if (Util.empty(personalId)) {
				personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
				personalInfo.setPersonalId(personalId);
			
			} else {
				personalIndex = applicationGroup.getPersonalIndex(personalId);
			}
		} catch (Exception e) {
			logger.fatal("ERROR ", e);
		}
		logger.debug("PERSONAL_ID >> "+personalId);
		logger.debug("PERSONAL_INDEX >> "+personalIndex);
		
		applicationGroup.setExecuteFlag(null);
		if(personalIndex == -1){
			personalInfos.add(personalInfo);
		}else{
			personalInfos.set(personalIndex,personalInfo);
		}
		PersonalInfoUtil.defaultPersonalIdOfImage(applicationGroup,personalId,personalType,personalSeq);
		return null;
	}
	
	@Override
	public ArrayList<ORIGSubForm> filterSubform(HttpServletRequest request,ArrayList<ORIGSubForm> subforms, Object objectForm) {
		logger.debug("filterSubform...");
		ArrayList<ORIGSubForm> filterSubforms = new ArrayList<ORIGSubForm>();
		if(!Util.empty(subforms)){
			for (ORIGSubForm origSubForm : subforms) {
				String subformId = origSubForm.getSubFormID();
				String renderSubformFlag = origSubForm.renderSubformFlag(request,objectForm);
				logger.debug("[subformId] "+subformId+" renderSubformFlag >> "+renderSubformFlag);
				if(MConstant.FLAG.YES.equals(renderSubformFlag)){
					filterSubforms.add(origSubForm);
				}
			}
		}
		return filterSubforms;
	}
	
}
