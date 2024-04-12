package com.eaf.service.rest.controller.memo;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.file.FileControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.core.ulo.service.notify.cont.NotifyConstant;
import com.eaf.core.ulo.service.notify.controller.NotifyController;
import com.eaf.core.ulo.service.notify.model.NotifyRequest;
import com.eaf.core.ulo.service.notify.model.NotifyResponse;
import com.eaf.inf.batch.ulo.memo.core.InstructionMemoTemplate;
import com.eaf.inf.batch.ulo.memo.model.InstructionMemoOutputDataM;
import com.eaf.inf.batch.ulo.notification.memo.model.InstructionMemoRequest;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigApplicationGroupDAO;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.model.ServiceResponse;

@RestController
@RequestMapping("/service/instructionMemo")
public class InstructionMemoController {
	private static transient Logger logger = Logger.getLogger(InstructionMemoController.class);
	@RequestMapping(value="/send/{instanceId}", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<ServiceResponse> send(@PathVariable("instanceId") String instanceId){
		ServiceResponse serviceResponse = new ServiceResponse();
		String PRODUCT_K_PERSONAL_LOAN = ServiceCache.getConstant("PRODUCT_K_PERSONAL_LOAN");
		String BUSCLASS_KPL_TOPUP = ServiceCache.getConstant("BUSCLASS_KPL_TOPUP");		
		OrigApplicationGroupDAO origApplicationDao = ORIGDAOFactory.getApplicationGroupDAO();
		ApplicationGroupDataM applicationGroup;
		boolean generateResult = false;
		ArrayList<InstructionMemoOutputDataM> instructionMemos = new ArrayList<InstructionMemoOutputDataM>();
		try{
			applicationGroup = origApplicationDao.loadApplicationGroupByInstanceId(instanceId);
			if(!Util.empty(applicationGroup)) {
				String applicationGroupId = applicationGroup.getApplicationGroupId();
				logger.info("Generate Instruction Memo :"+applicationGroupId);	
				int lifeCycle = applicationGroup.getMaxLifeCycle();
				ApplicationDataM application = applicationGroup.filterApplicationProductLifeCycle(PRODUCT_K_PERSONAL_LOAN);
				String applicationRecordId = application.getApplicationRecordId();
				String businessClassId = application.getBusinessClassId();
				if(!Util.empty(application)) {
					if(BUSCLASS_KPL_TOPUP.equals(application.getBusinessClassId())){
						InstructionMemoOutputDataM templateClose = InstructionMemoTemplate.createInstructionMemoClose(applicationGroupId);
						InstructionMemoOutputDataM templateOpen = InstructionMemoTemplate.createInstructionMemoOpen(applicationGroupId);
						if(templateClose.isGenerateResult() && templateOpen.isGenerateResult()){
							generateResult = true;
							instructionMemos.add(templateClose);
							instructionMemos.add(templateOpen);
						}
					}else{
						InstructionMemoOutputDataM templateOpen = InstructionMemoTemplate.createInstructionMemoOpen(applicationGroupId);
						if(templateOpen.isGenerateResult()){
							generateResult = true;
							instructionMemos.add(templateOpen);
						}
					}					
				}
				String APPLICATION_LEVEL = ServiceCache.getConstant("PERSONAL_RELATION_APPLICATION_LEVEL");
				String PERSONAL_TYPE_APPLICANT = ServiceCache.getConstant("PERSONAL_TYPE_APPLICANT");
				String CACHE_BUSINESS_CLASS =ServiceCache.getConstant("CACHE_BUSINESS_CLASS");				
				PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfoRelation(applicationRecordId,PERSONAL_TYPE_APPLICANT,APPLICATION_LEVEL);
				if(null == personalInfo){
					personalInfo = new PersonalInfoDataM();
				}
				ArrayList<String> instructionMemoFiles = getInstructionMemoFiles(instructionMemos);
				logger.debug("generateResult >> "+generateResult);
				logger.debug("instructionMemoFiles >> "+instructionMemoFiles);
				if(generateResult && !ServiceUtil.empty(instructionMemoFiles)){
					NotifyRequest notifiRequest  = new NotifyRequest();
					InstructionMemoRequest requestObject = new InstructionMemoRequest();
						requestObject.setApplicationGroupId(applicationGroupId);
						requestObject.setLifeCycle(lifeCycle);
						requestObject.setCustomerName(personalInfo.getThName());
						requestObject.setIdNo(personalInfo.getIdno());
						String productName = ServiceCache.getName(CACHE_BUSINESS_CLASS, "BUS_CLASS_ID",businessClassId,"BUS_CLASS_DESC");
						requestObject.setProductName(productName);
						requestObject.setInstructionMemoFiles(instructionMemoFiles);
					notifiRequest.setNotifyId(NotifyConstant.Notify.NOTIFY_INSTRUCTION_MEMO);
					notifiRequest.setRequestObject(requestObject);
					notifiRequest.setUniqueId(applicationGroupId);
					NotifyResponse notifyResponse = NotifyController.notify(notifiRequest, null);
					logger.debug(notifyResponse);
					serviceResponse.setStatusCode(notifyResponse.getStatusCode());
					serviceResponse.setStatusDesc(notifyResponse.getStatusDesc());
				}else{
					serviceResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceResponse.setStatusDesc(e.getLocalizedMessage());
		}
		try{
			if(!ServiceUtil.empty(instructionMemos)){
				for (InstructionMemoOutputDataM instructionMemo : instructionMemos) {
					FileControl.delete(instructionMemo.getOutputPath());
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		return ResponseEntity.ok(serviceResponse);
	}
	private ArrayList<String> getInstructionMemoFiles(ArrayList<InstructionMemoOutputDataM> instructionMemos){
		ArrayList<String> instructionMemoFiles  = new ArrayList<String>();
		if(!ServiceUtil.empty(instructionMemos)){
			for (InstructionMemoOutputDataM instructionMemo : instructionMemos) {
				instructionMemoFiles.add(instructionMemo.getOutputFile());
			}
		}
		return instructionMemoFiles;
	}
}
