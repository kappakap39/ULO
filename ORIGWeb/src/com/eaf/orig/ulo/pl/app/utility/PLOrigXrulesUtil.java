package com.eaf.orig.ulo.pl.app.utility;

import java.util.Vector;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.service.ORIGEJBService;
import com.eaf.orig.shared.util.OrigUtil;
import com.eaf.orig.ulo.pl.constant.WorkflowConstant;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLPersonalInfoDataM;
import com.eaf.xrules.cache.model.MTRqModuleServiceCacheDataM;
import com.eaf.xrules.ulo.pl.model.GroupModuleVerificationDataM;
import com.eaf.xrules.ulo.pl.model.PLXRulesDataM;
import com.eaf.xrules.ulo.pl.model.PLXrulesApplicationDataM;
//import com.eaf.xrules.ulo.pl.moduleservice.core.PLXRulesServiceProxy;
//import com.eaf.xrules.ulo.pl.moduleservice.core.ejb.ExecuteServiceManager;

/**
 * @author Rawi Songchaisin
 * Type: PLOrigXrulesUtil
 */
@Deprecated
public class PLOrigXrulesUtil {
	
	Logger logger = Logger.getLogger(PLOrigXrulesUtil.class);
	
	static PLOrigXrulesUtil me;
	
	public PLOrigXrulesUtil() {
		super();
	}
	
	public static PLOrigXrulesUtil getInstance() {
        if (me == null) {
            me = new PLOrigXrulesUtil();
        }
        return me;
    }
	@Deprecated
	public GroupModuleVerificationDataM getRequiredModuleServices(PLApplicationDataM plApplicationM){		
		 logger.debug("[getRequiredModuleServices].. ");
//		 Vector<RequiredModuleServiceM> moduleService = null;		 
//		 GroupModuleVerificationDataM groupModule = null;
		 Vector<MTRqModuleServiceCacheDataM> moduleServices;
		 try{			 
			 PLXRulesDataM plXrulesM = this.mapPLXRulesModel(plApplicationM);							  	          
//	         PLXRulesServiceProxy plXrulesService = new PLXRulesServiceProxy();	         
//	         moduleServices = plXrulesService.getModuleServiceGroup(plXrulesM);	         
//	         return this.MappingGroupModuleService(moduleServices);	          
		 }catch(Exception e){
			 logger.fatal("Error : ",e);
		 }		
		return new GroupModuleVerificationDataM();
    }
	@Deprecated
	 public PLXRulesDataM mapPLXRulesModel(PLApplicationDataM plApplicationDataM){
		 
		 PLXRulesDataM plXruleM = new PLXRulesDataM();
		 
		 if(plApplicationDataM ==null) plApplicationDataM = new PLApplicationDataM();
		 
		 try{
			 
			 plXruleM.setBusinessClass(plApplicationDataM.getBusinessClassId());
			 
			 plXruleM.setCardType(OrigConstant.CardType.CARD_TYPE_B);
			 
			 if(OrigUtil.isEmptyString((plApplicationDataM.getJobState())))
				 plApplicationDataM.setJobState(WorkflowConstant.JobState.DE_IQ);
			 
			 plXruleM.setJobState(plApplicationDataM.getJobState());
				 
			 PLPersonalInfoDataM plPersonalInfoM = plApplicationDataM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
			 			
			 if(plPersonalInfoM == null) plPersonalInfoM = new PLPersonalInfoDataM();
			 
			 if(OrigUtil.isEmptyString((plPersonalInfoM.getCustomerType())))
					 plPersonalInfoM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
			 
			 plXruleM.setCustomerType(plPersonalInfoM.getCustomerType()); 
						 
//			 logger.debug("[mapPLXRulesModel].. Business Class "+plXruleM.getBusinessClass());
//			 logger.debug("[mapPLXRulesModel].. CardType  "+plXruleM.getCardType());
//			 logger.debug("[mapPLXRulesModel].. Job State "+plXruleM.getJobState());
//			 logger.debug("[mapPLXRulesModel].. Customer Type "+plXruleM.getCustomerType());
			 
		 }catch(Exception e){
			 logger.fatal("Error : ",e);
		 }
		 return (plXruleM == null )?new PLXRulesDataM():plXruleM;
	 }	 
	@Deprecated
	public GroupModuleVerificationDataM MappingGroupModuleService(Vector<MTRqModuleServiceCacheDataM> moduleServices){
//		
//		logger.debug("[MappingGroupModuleService].. ");
//		
//		GroupModuleVerificationDataM groupModule = new GroupModuleVerificationDataM();
//		 
//		if(OrigUtil.isEmptyVector(moduleServices)) return new GroupModuleVerificationDataM();
//		
//		if(!OrigUtil.isEmptyVector(moduleServices)){
//			for(MTRqModuleServiceCacheDataM serviceM : moduleServices){				
//					switch (serviceM.getGroupService()){
//						case PLXrulesConstant.GroupService.BASIC_VERIFY:
//							 groupModule.getGroupBasicVerification().setActive(true);
//							 groupModule.getGroupBasicVerification().add(serviceM);	 
//							 break;
//						case PLXrulesConstant.GroupService.BANK_VERIFY:
//							 groupModule.getGroupBankVerification().setActive(true);
//							 groupModule.getGroupBankVerification().add(serviceM);	 
//							 break;
//						case PLXrulesConstant.GroupService.NCBFIFO_VERIFY:
//							 groupModule.getGroupNcbFifoVerification().setActive(true);
//							 groupModule.getGroupNcbFifoVerification().add(serviceM);	 
//							 break;	
//						case PLXrulesConstant.GroupService.DOCLIST_VERIFY:
//							 groupModule.getGroupDocListVerification().setActive(true);
//							 groupModule.getGroupDocListVerification().add(serviceM);	 
//							 break;	
//						case PLXrulesConstant.GroupService.EARN_INSTALLMENT_VERIFY:
//							 groupModule.getGroupEarningInstallmentVerification().setActive(true);
//							 groupModule.getGroupEarningInstallmentVerification().add(serviceM);	 
//							 break;	
//						case PLXrulesConstant.GroupService.CUSTOMER_VERIFY:
//							 groupModule.getGroupCustomerVerification().setActive(true);
//							 groupModule.getGroupCustomerVerification().add(serviceM);	
//							 break;
//						case PLXrulesConstant.GroupService.OTHER_VERIFY:
//							 groupModule.getGroupOtherVerification().setActive(true);
//							 groupModule.getGroupOtherVerification().add(serviceM);	
//							 break;	
//						default:
//							break;
//					}				
//			}		
//		}	
//		return (groupModule == null)? new GroupModuleVerificationDataM(): groupModule;
		return null;
	}
	
	@Deprecated
	public JSONArray ExecuteModuleService(PLApplicationDataM plApplicationM ,PLXrulesApplicationDataM plXrulesAppM,UserDetailM userM, String buttonId){
		logger.debug("[ExecuteModuleService].. buttonId "+buttonId);
		JSONArray jsonArray = null;
		try{			
			 if(plApplicationM == null) plApplicationM = new PLApplicationDataM();
			 
			 if(plXrulesAppM == null) plXrulesAppM = new PLXrulesApplicationDataM();
			 
			 this.mapPLXrulesAppModel(plApplicationM, plXrulesAppM, userM, buttonId);
			 
//			 ExecuteServiceManager xRulesService = ORIGEJBService.getExecuteServiceManager();
			 
//			 PLXrulesResultDataM plXrulesResultM = xRulesService.ExecuteServiceRules(plXrulesAppM);
			 
//			 MapPLXrulesResultJson mapJson = new MapPLXrulesResultJson();
//			 
//			 jsonArray = mapJson.MappingObject(plApplicationM, plXrulesResultM);
//			 
//			 if(plXrulesResultM == null) plXrulesResultM = new PLXrulesResultDataM();
//			 
//			 plXrulesAppM.setPlXrulesEaiM(plXrulesResultM.getPlXrulesEaiM());
			 
		} catch (Exception e) {
			logger.debug("Error ",e);
		}	
		logger.debug("[ExecuteModuleService]..Finish");
		return jsonArray;
	}
	
	
	@Deprecated
	public void ButtonExecuteStyle(PLApplicationDataM plApplicationM , String buttonId ,JSONArray jsonArray){
//		PLXRulesServiceProxy plXrulesService = new PLXRulesServiceProxy();
//		PLXRulesDataM plXrulesM = this.mapPLXRulesModel(plApplicationM,buttonId);
//		Vector<RequiredModuleServiceM> moduleService = plXrulesService.getModuleButtonExecute(plXrulesM, plXrulesM.getButtonId());
//		SensitiveFieldTool jsonFieldEngine = new SensitiveFieldTool();
//		JSONObject jObject = null;
//		for(RequiredModuleServiceM serviceM :moduleService){
//			if(jsonFieldEngine.isFoundNotExecute((int)serviceM.getServiceID(), plApplicationM)){
//				jObject = new JSONObject();
//				jObject.put(MapJsonFieldEngine.SensitiveFieldTool.SENSITIVE_TYPE, "button");
//				jObject.put("id", buttonId);
//				jObject.put("value", "button-red");
//				jsonArray.put(jObject);
//				return;
//			}
//		}
//		jObject = new JSONObject();
//		jObject.put(MapJsonFieldEngine.SensitiveFieldTool.SENSITIVE_TYPE, "button");
//		jObject.put("id", buttonId);
//		jObject.put("value", "button");
//		jsonArray.put(jObject);
//		return;
	}
	
	@Deprecated
	public String ButtonExecuteStyle(PLApplicationDataM plApplicationM , String buttonId){
//		PLXRulesServiceProxy plXrulesService = new PLXRulesServiceProxy();
//		PLXRulesDataM plXrulesM = this.mapPLXRulesModel(plApplicationM,buttonId);
//		Vector<RequiredModuleServiceM> moduleService = plXrulesService.getModuleButtonExecute(plXrulesM, plXrulesM.getButtonId());
//		SensitiveFieldTool jsonFieldEngine = new SensitiveFieldTool();
//		for(RequiredModuleServiceM serviceM :moduleService){
//			if(jsonFieldEngine.isFoundNotExecute((int)serviceM.getServiceID(), plApplicationM)){
//				return "button-red";
//			}
//		}		
//		return "button";
		return null;
	}
	
	@Deprecated
    public PLXRulesDataM mapPLXRulesModel(PLApplicationDataM plApplicationM ,String buttonId){
		
		 logger.debug("[mapPLXRulesModel]..");
		 
		 PLXRulesDataM plXrulesM = new PLXRulesDataM();
		
		 if(plApplicationM == null) plApplicationM = new PLApplicationDataM();
		 
		 plXrulesM.setBusinessClass(plApplicationM.getBusinessClassId());
		 
		 plXrulesM.setCardType(OrigConstant.CardType.CARD_TYPE_B);
		 
		 if(OrigUtil.isEmptyString((plApplicationM.getJobState())))
			 plXrulesM.setJobState(WorkflowConstant.JobState.DE_IQ);
		 
		 plXrulesM.setJobState(plApplicationM.getJobState());
			 
		 PLPersonalInfoDataM personlM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		 
		 if(OrigUtil.isEmptyString((personlM.getCustomerType())))
			 plXrulesM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
		 
		 plXrulesM.setCustomerType(personlM.getCustomerType());
		 
		 plXrulesM.setButtonId(buttonId);
		 
		 return plXrulesM;		 
	} 
	
	@Deprecated
	public void mapPLXrulesAppModel(PLApplicationDataM plApplicationM ,PLXrulesApplicationDataM plXrulesAppM ,UserDetailM userM , String buttonId ){
		
		 logger.debug("[mapPLXrulesAppModel]..");
		
		 if(plXrulesAppM == null) plXrulesAppM = new PLXrulesApplicationDataM();
		 
		 plXrulesAppM.setBusinessClass(plApplicationM.getBusinessClassId());
		 
		 plXrulesAppM.setCardType(OrigConstant.CardType.CARD_TYPE_B);
		 
		 if(OrigUtil.isEmptyString((plApplicationM.getJobState())))
			 plApplicationM.setJobState(WorkflowConstant.JobState.DE_IQ);
		 
		 plXrulesAppM.setJobState(plApplicationM.getJobState());
			 
		 PLPersonalInfoDataM plPersonalInfoM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		 
		 logger.debug("[mapPLXrulesAppModel]..plPersonalInfoM.getIdNo "+plPersonalInfoM.getIdNo());
		 
		 if(OrigUtil.isEmptyString((plPersonalInfoM.getCustomerType())))
				 plPersonalInfoM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
		 
		 plXrulesAppM.setCustomerType(plPersonalInfoM.getCustomerType()); 
		 
		 plXrulesAppM.setPlApplicationM(plApplicationM);
		 
		 plXrulesAppM.setUserM(userM);
		 
		 plXrulesAppM.setButtonId(buttonId);
	}
	
	@Deprecated
	public PLXrulesApplicationDataM mapPLXrulesAppModel(PLApplicationDataM plApplicationM ,PLXrulesApplicationDataM plXrulesAppM ,UserDetailM userM){
		
		 logger.debug("[mapPLXrulesAppModel]..");
		
		 if(plXrulesAppM == null) plXrulesAppM = new PLXrulesApplicationDataM();
		 
		 plXrulesAppM.setBusinessClass(plApplicationM.getBusinessClassId());
		 
		 plXrulesAppM.setCardType(OrigConstant.CardType.CARD_TYPE_B);
		 
		 if(OrigUtil.isEmptyString((plApplicationM.getJobState())))
			 plApplicationM.setJobState(WorkflowConstant.JobState.DE_IQ);
		 
		 plXrulesAppM.setJobState(plApplicationM.getJobState());
			 
		 PLPersonalInfoDataM plPersonalInfoM = plApplicationM.getPLPersonalInfoDataM(PLPersonalInfoDataM.PersonalType.PERSONAL_TYPE_APPLICANT);
		 
		 logger.debug("[mapPLXrulesAppModel]..plPersonalInfoM.getIdNo "+plPersonalInfoM.getIdNo());
		 
		 if(OrigUtil.isEmptyString((plPersonalInfoM.getCustomerType())))
				 plPersonalInfoM.setCustomerType(OrigConstant.CUSTOMER_TYPE_INDIVIDUAL);
		 
		 plXrulesAppM.setCustomerType(plPersonalInfoM.getCustomerType()); 
		 
		 plXrulesAppM.setPlApplicationM(plApplicationM);
		 
		 plXrulesAppM.setUserM(userM);
		 
		 plXrulesAppM.setPlXrulesEaiM(plXrulesAppM.getPlXrulesEaiM());
		 
		 return plXrulesAppM;
	}
	
}
