package com.eaf.orig.ulo.app.postapproval.process;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.PostApprovalProcessHelper;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAO;
import com.eaf.orig.ulo.app.view.util.dih.dao.DihDAOImpl;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.app.view.util.dih.model.KbankBranchInfoDataM;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.model.dih.CISCustomerDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.module.manual.CISCustomerServiceProxy;
import com.eaf.service.rest.model.ServiceResponse;

public class NewCISCustomerProcess extends PostApprovalProcessHelper {
	private static transient Logger logger = Logger.getLogger(NewCISCustomerProcess.class);
	String CACHE_NAME_USER = SystemConstant.getConstant("CACHE_NAME_USER");	
	@Override
	public ProcessResponse execute(ApplicationGroupDataM applicationGroup, Object objElement) {
		ProcessResponse processResponse = new ProcessResponse();
		try{
			processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
			PersonalInfoDataM personalInfo = (PersonalInfoDataM) objElement;
			String userId = applicationGroup.getUserId();//CacheControl.getName(CACHE_NAME_USER, "USER_NAME", applicationGroup.getUserId(), "USER_NO");
			String department = "";
			if(ApplicationUtil.eApp(applicationGroup.getSource())){
				//if is eapp user = source user
				userId = applicationGroup.getSourceUserId();
				if(!Util.empty(applicationGroup.getBranchNo()) && !"0".equals(applicationGroup.getBranchNo())){
					department = applicationGroup.getBranchNo();
				}
				else{
					DihDAO dih = new DihDAOImpl();
					String rcCode = applicationGroup.getRcCode();
					KbankBranchInfoDataM kbankBranchInfo = dih.getBranchInfoByRC_CD(rcCode);
					if(null != kbankBranchInfo){
						department = kbankBranchInfo.getBranchNo();
					}
				}
			}else{
				department = CacheControl.getName(CACHE_NAME_USER, "USER_NAME", userId, "DEPARTMENT");
			}
//			String department = CacheControl.getName(CACHE_NAME_USER, "USER_NAME", userId, "DEPARTMENT");
			logger.info("userId : "+userId);
			logger.info("personalId : "+personalInfo.getPersonalId());
			logger.info("cisNo : "+personalInfo.getCisNo());
			logger.info("department : "+department);
			DIHProxy dihProxy = new DIHProxy();
			DIHQueryResult<CISCustomerDataM>  dihResult = null; 
			if(!Util.empty(personalInfo.getCisNo())){
				dihResult = dihProxy.selectCISCustomer(personalInfo.getCisNo());
			}else{
				dihResult = dihProxy.selectCISCustomer(personalInfo.getCidType(), personalInfo.getIdno());
			}
			
			if(!ResponseData.SUCCESS.equals(dihResult.getStatusCode())){
				processResponse.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
				processResponse.setErrorData(dihResult.getErrorData());
				return processResponse;
			}
			
			CISCustomerDataM cisCustomer = dihResult.getResult();
			
			CISCustomerServiceProxy service = new CISCustomerServiceProxy();
			ProcessResponse cisCustomerResponse = service.createCISCustomer(applicationGroup, personalInfo,cisCustomer,userId,department);
			
			logger.debug(cisCustomerResponse);
			String cisCistomerResultCode = cisCustomerResponse.getStatusCode();
			logger.debug("cisCistomerResultCode : "+cisCistomerResultCode);
			if(!ServiceResponse.Status.SUCCESS.equals(cisCistomerResultCode)){
				processResponse.setStatusCode(cisCistomerResultCode);
				processResponse.setErrorData(cisCustomerResponse.getErrorData());
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(ErrorController.error(e));
		}
		return processResponse;
	}
	@Override
	public boolean validate(ApplicationGroupDataM applicationGroup,Object objElement){
		PersonalInfoDataM personalInfo = (PersonalInfoDataM)objElement;
		String personalId = personalInfo.getPersonalId();
		logger.debug("personalId : "+personalId);
		boolean existSrcOfDataCis = applicationGroup.existSrcOfData(CompareDataM.SoruceOfData.CIS,personalId);
//		if(ApplicationUtil.eApp(applicationGroup.getSource())) existSrcOfDataCis = true;
		logger.debug("existSrcOfDataCis : "+existSrcOfDataCis);
		return (!existSrcOfDataCis);
	}
}
