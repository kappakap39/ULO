package com.eaf.orig.ulo.app.view.form.manual;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.app.dao.OrigComparisionDataDAO;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.app.view.form.displaymode.ConsentModelDisplayMode;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.DocumentCheckListUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.ComparisonGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

@SuppressWarnings("serial")
public class OrigApplicationForm extends FormHelper implements FormAction{
	public static transient Logger logger = Logger.getLogger(OrigApplicationForm.class);
	public String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	public String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");	
	public String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
	public String SUP_CARD_SEPARATELY_FORM = SystemConstant.getConstant("SUP_CARD_SEPARATELY_FORM");
	public String FLIP_TYPE_INC = SystemConstant.getConstant("FLIP_TYPE_INC");
	public String DEFAULT_IA_APPLICATION_FORM = SystemConstant.getConstant("DEFAULT_IA_APPLICATION_FORM");
	public String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
	private String APPLICATION_TYPE_INCREASE = SystemConstant.getConstant("APPLICATION_TYPE_INCREASE");
	private String[] DISPLAY_ADDRESS_TYPE = SystemConstant.getArrayConstant("DISPLAY_ADDRESS_TYPE");
	@Override
	public Object getObjectForm() throws Exception{
		logger.debug("getObjectForm...");
		String applicationGroupId = getRequestData("APPLICATION_GROUP_ID");
		String taskId = getRequestData("TASK_ID");
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		logger.debug("transactionId >> "+transactionId);
		logger.debug("applicationGroupId >> "+applicationGroupId);
		logger.debug("taskId >> "+taskId);
		return getApplicationGroup(applicationGroupId,taskId,transactionId);
	}
	public ApplicationGroupDataM getApplicationGroup(String applicationGroupId,String taskId,String transactionId) throws Exception{
		ApplicationGroupDataM applicationGroup = null;
		try{
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			trace.create("LoadApplication");
			OrigApplicationGroupDAO applicationGroupDAO = ModuleFactory.getApplicationGroupDAO("",transactionId);
			applicationGroup = applicationGroupDAO.loadOrigApplicationGroupM(applicationGroupId);
			if(null == applicationGroup){
				applicationGroup = new ApplicationGroupDataM();
				//#comment for Prod 734556
//				try{
					applicationGroupId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_APPLICATION_GROUP_PK);
					applicationGroup.setApplicationGroupId(applicationGroupId);
//				}catch(Exception e){
//					logger.fatal("ERROR ",e);
//				}			
			}else{
				applicationGroupId = applicationGroup.getApplicationGroupId();
			}
			applicationGroup.setTaskId(taskId);	
			trace.end("LoadApplication");
			
			int lifeCycle = applicationGroup.getMaxLifeCycle();
			logger.debug("lifeCycle : "+lifeCycle);
			
			trace.create("LoadComparison");
			
			/**#rawi change logic load comparision data.
			ArrayList<ComparisonGroupDataM> comparisonGroups = new ArrayList<ComparisonGroupDataM>();
			OrigComparisionDataDAO origComparisionDao = ModuleFactory.getOrigComparisionDataDAO();
			ComparisonGroupDataM comparisonCis = origComparisionDao.loadComparisonGroup(applicationGroupId,CompareDataM.SoruceOfData.CIS);
			ComparisonGroupDataM comparisonCardLink = origComparisionDao.loadComparisonGroup(applicationGroupId,CompareDataM.SoruceOfData.CARD_LINK);
			logger.debug("comparisonCis >> "+comparisonCis);
			logger.debug("comparisonCardLink >> "+comparisonCardLink);
			comparisonGroups.add(comparisonCis);
			comparisonGroups.add(comparisonCardLink);
			ComparisonGroupDataM twoMaker = origComparisionDao.loadComparisonGroup(applicationGroupId,CompareDataM.SoruceOfData.TWO_MAKER,roleId,lifeCycle);
			if(null == twoMaker){
				twoMaker = new ComparisonGroupDataM();
			}
			twoMaker.setRoleId(roleId);
			comparisonGroups.add(twoMaker);
			// Last role mapping comparison
			String prevRoleId = getLastRole(roleId);
			ComparisonGroupDataM prevRoleData = origComparisionDao.loadComparisonGroup(applicationGroupId,CompareDataM.SoruceOfData.TWO_MAKER,prevRoleId,lifeCycle);
			if(null == prevRoleData){
				prevRoleData = new ComparisonGroupDataM();
			}
			prevRoleData.setRoleId(prevRoleId);
			prevRoleData.setSrcOfData(CompareDataM.SoruceOfData.PREV_ROLE);
			comparisonGroups.add(prevRoleData);

			applicationGroup.setComparisonGroups(comparisonGroups);
			boolean supplementaryApplicant =   origComparisionDao
					.isContainIASuplementtary(applicationGroupId, CompareDataM.RefLevel.PERSONAL, CompareDataM.SoruceOfData.TWO_MAKER,lifeCycle);
			applicationGroup.setSupplementaryApplicant(supplementaryApplicant);
			*/

			String prevRoleId = getLastRole(roleId);
			OrigComparisionDataDAO origComparisionDao = ModuleFactory.getOrigComparisionDataDAO();
			Map<String,ComparisonGroupDataM> comparisonGroupData  = origComparisionDao.loadComparisonGroupData(applicationGroupId,roleId,prevRoleId,lifeCycle);
			logger.debug("comparisonGroupData : "+comparisonGroupData);
			ArrayList<ComparisonGroupDataM> comparisonGroups = new ArrayList<ComparisonGroupDataM>();
			ComparisonGroupDataM comparisonCis = comparisonGroupData.get(CompareDataM.SoruceOfData.CIS);
			if(null==comparisonCis){
				comparisonCis = new ComparisonGroupDataM();
			}
			comparisonCis.setSrcOfData(CompareDataM.SoruceOfData.CIS);
			ComparisonGroupDataM comparisonCardLink = comparisonGroupData.get(CompareDataM.SoruceOfData.CARD_LINK);
			if(null==comparisonCardLink){
				comparisonCardLink = new ComparisonGroupDataM();
			}
			comparisonCardLink.setSrcOfData(CompareDataM.SoruceOfData.CARD_LINK);
			comparisonGroups.add(comparisonCis);
			comparisonGroups.add(comparisonCardLink);

			ComparisonGroupDataM twoMaker = new ComparisonGroupDataM(CompareDataM.SoruceOfData.TWO_MAKER);
			ComparisonGroupDataM prevRoleData = comparisonGroupData.get(CompareDataM.SoruceOfData.PREV_ROLE);
			if(Util.empty(prevRoleData)){
				prevRoleData = new ComparisonGroupDataM();
				HashMap<String, CompareDataM> prevRoleComparisonField = new HashMap<String, CompareDataM>();
				prevRoleData.setComparisonFields(prevRoleComparisonField);
			}
			ComparisonGroupDataM comparisonGroup = comparisonGroupData.get(CompareDataM.SoruceOfData.TWO_MAKER);
			if(null!=comparisonGroup){
				 HashMap<String,CompareDataM> comparisonFields = comparisonGroup.getComparisonFields();
				 if(null!=comparisonFields){
					 for(CompareDataM compareData : comparisonFields.values()){
						if(null!=prevRoleId&&prevRoleId.equals(compareData.getRole())&&compareData.getLifeCycle()==lifeCycle){
							HashMap<String, CompareDataM> prevRoleComparisonFields = prevRoleData.getComparisonFields();
							if(null==prevRoleComparisonFields){
								prevRoleComparisonFields = new HashMap<String, CompareDataM>();
								prevRoleData.setComparisonFields(prevRoleComparisonFields);
							}
							prevRoleComparisonFields.put(compareData.getFieldName(), compareData);
						}else if(null!=roleId&&roleId.equals(compareData.getRole())&&compareData.getLifeCycle()==lifeCycle){
							HashMap<String, CompareDataM> twoMakeComparisonFields = twoMaker.getComparisonFields();
							if(null==twoMakeComparisonFields){
								twoMakeComparisonFields = new HashMap<String, CompareDataM>();
								twoMaker.setComparisonFields(twoMakeComparisonFields);
							}
							twoMakeComparisonFields.put(compareData.getFieldName(), compareData);
						}
					}
				 }
			}
			ComparisonGroupDataM comparisonGroupIa = comparisonGroupData.get(CompareDataM.SoruceOfData.IA_TWO_MAKER);
			if(null!=comparisonGroupIa){
				 HashMap<String,CompareDataM> comparisonFields = comparisonGroupIa.getComparisonFields();
				 if(null!=comparisonFields){
					 for(CompareDataM compareData : comparisonFields.values()){
						if(!applicationGroup.isSupplementaryApplicant()&&SystemConstant.getConstant("ROLE_IA").equals(compareData.getRole())
								&&compareData.getLifeCycle()==lifeCycle&&CompareDataM.RefLevel.PERSONAL.equals(compareData.getRefLevel())){
							if(null!=compareData.getRefId()&&compareData.getRefId().length()>1){
								String refId = compareData.getRefId().substring(0,1);
								if(SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY").equals(refId)){
									applicationGroup.setSupplementaryApplicant(true);
								}
							}
						}
					}
				 }
			}
			twoMaker.setRoleId(roleId);
			comparisonGroups.add(twoMaker);
			
				prevRoleData.setRoleId(prevRoleId);
				prevRoleData.setSrcOfData(CompareDataM.SoruceOfData.PREV_ROLE);
				comparisonGroups.add(prevRoleData);
			
			applicationGroup.setComparisonGroups(comparisonGroups);
			trace.end("LoadComparison");
			
			String auditFlag = applicationGroup.getAuditLogFlag();
			if(Util.empty(auditFlag)){
				applicationGroup.setAuditLogFlag(MConstant.AuditFlag.WAIT_AUDIT_DATA);
			}
			DocumentCheckListUtil.defaultRequiredDocFlag(applicationGroup);
			
			logger.debug("formId >> "+formId);
			if(SUP_CARD_SEPARATELY_FORM.equals(formId)){
				ApplicationDataM application = applicationGroup.filterApplicationItemLifeCycle();
				if(null != application){
					CardDataM card = application.getCard();
					if(null != card){
						applicationGroup.setMainCardHolderName(card.getMainCardHolderName());
						applicationGroup.setMainCardNo(card.getMainCardNo());
					}
				}
			}
			
			String FORM_NAME = CacheControl.getName(CACHE_TEMPLATE, applicationGroup.getApplicationTemplate(), "FORM_NAME");
			logger.debug("TEMPLATE FORM_NAME : "+FORM_NAME);
			if(APPLICATION_TYPE_INCREASE.equals(applicationGroup.getApplicationType()) && DEFAULT_IA_APPLICATION_FORM.equals(FORM_NAME)){
				applicationGroup.setFlipType(FLIP_TYPE_INC);
			}
			logger.debug("applicationGroup.getPaymentMethods >> "+applicationGroup.getPaymentMethods());
			logger.debug("applicationGroup.getSpecialAdditionalServices >> "+applicationGroup.getSpecialAdditionalServices());
			logger.debug("applicationGroup.getCashTransfers >> "+applicationGroup.getCashTransfers());
			ArrayList<String> addressTypes = new ArrayList<String>(Arrays.asList(DISPLAY_ADDRESS_TYPE));
			if(!Util.empty(addressTypes)){
				ArrayList<PersonalInfoDataM> usIngPersonalInfos = applicationGroup.getUsingPersonalInfo();
				if(!Util.empty(usIngPersonalInfos)){
					for(PersonalInfoDataM personalInfo : usIngPersonalInfos){
						if(!Util.empty(personalInfo)){
							for(String addressType : addressTypes){
								AddressDataM address = personalInfo.getAddress(addressType);
								if(null==address){
									address = new AddressDataM(); 
									String addressId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_ADDRESS_PK); 
									address.setAddressId(addressId); 
									address.setAddressType(addressType); 
									address.setPersonalId(personalInfo.getPersonalId()); 
									personalInfo.addAddress(address); 
								}
							}
						}
					}
				}
			}
			trace.trace();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			throw new ApplicationException(e.getMessage());
		}
		return applicationGroup;
	}
	public static int getPersonalSeq(String personalType,ApplicationGroupDataM applicationGroup){
//		#rawi comment change logic get personalSeq by PersonalType
//		int PERSONAL_SEQ = 1;
//		String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
//		if(PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
//			ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
//			if(null == personalInfos){
//				personalInfos = new ArrayList<PersonalInfoDataM>();
//			}
//			if(null == applicationGroup.getPersonalInfo(PERSONAL_TYPE_SUPPLEMENTARY)){
//				PERSONAL_SEQ = 2;
//			}else{
//				PERSONAL_SEQ = personalInfos.size()+1;
//			}			
//		}
//		return PERSONAL_SEQ;
		return applicationGroup.personalSize(personalType)+1;
	}
	public void initPersonalInfo(String personalType,int personalSeq,ApplicationGroupDataM applicationGroup){
		logger.debug("personalType >> "+personalType);
		logger.debug("personalSeq >> "+personalSeq);
		ArrayList<PersonalInfoDataM> personalInfos = applicationGroup.getPersonalInfos();
		if(null == personalInfos){
			personalInfos = new ArrayList<PersonalInfoDataM>();
			applicationGroup.setPersonalInfos(personalInfos);
		}
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(personalType);
		logger.debug("personalInfo >> "+personalInfo);
		if(null == personalInfo){
			personalInfo =  new PersonalInfoDataM();
			try{
				String personalId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_PERSONAL_INFO_PK);
				logger.debug("GenerateUnique personalId >> "+personalId);
				personalInfo.setPersonalId(personalId);
			}catch(Exception e){
				logger.fatal("ERROR ",e);
			}
//			personalInfo = new PersonalInfoDataM();
			personalInfo.setPersonalType(personalType);
			personalInfo.setSeq(personalSeq);
			applicationGroup.addPersonalInfo(personalInfo);
		}
		logger.debug("personalId >> "+personalInfo.getPersonalId());
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
	@Override
	public void displayValueForm(HttpServletRequest request,ArrayList<ORIGSubForm> subforms, Object objectForm) {
		logger.debug("displayValueForm...");
		logger.debug("formId : "+formId);
		logger.debug("formLevel : "+formLevel);
		logger.debug("roleId : "+roleId);
		if(!Util.empty(subforms)){
			for(ORIGSubForm origSubForm : subforms){
				try{
					origSubForm.setFormId(formId);
					origSubForm.setFormLevel(formLevel);
					origSubForm.setRoleId(roleId);
					if(objectForm instanceof ApplicationGroupDataM){
						ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
						if(!Util.empty(applicationGroup)){
							logger.debug("reprocessFlag : "+applicationGroup.getReprocessFlag());
							if(Util.empty(applicationGroup.getReprocessFlag())){
								origSubForm.displayValueForm(request,objectForm);
							}
						}else{
							origSubForm.displayValueForm(request,objectForm);
						}
					}else{
						origSubForm.displayValueForm(request,objectForm);
					}
				}catch(Exception e){
					logger.fatal("ERROR ",e);
				}
			}
		}
	}
	
	private String getLastRole(String roleId){		
		String[] SENSITIVE_ROLE_MAPPING = SystemConstant.getArrayConstant("SENSITIVE_ROLE_MAPPING");
		if(null != SENSITIVE_ROLE_MAPPING){
			for (String ROLE_MAPPING : SENSITIVE_ROLE_MAPPING) {
				String CURRENT_ROLE = ROLE_MAPPING.split("\\|")[0];
				String LAST_ROLE = ROLE_MAPPING.split("\\|")[1];
				if(CURRENT_ROLE.equals(roleId)){
					return LAST_ROLE;
				}
			}
		}
		return "";
	}
}
