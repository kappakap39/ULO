package com.eaf.service.rest.controller.application;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.exception.ApplicationException;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil.CisCustomerResult;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;
import com.eaf.orig.ulo.service.lookup.LookupServiceCenter;
import com.eaf.service.rest.model.ServiceResponse;

@RestController
@RequestMapping("/service/application")
public class ServiceApplicationController {
	private static transient Logger logger = Logger.getLogger(ServiceApplicationController.class);
	@RequestMapping(value="/updateCisData/{instantId}",method={RequestMethod.PUT})
    public @ResponseBody ResponseEntity<ServiceResponse> updateCisData(@PathVariable("instantId") String instantId){
		logger.info("instantId : "+instantId);
		ServiceResponse serviceResponse = new ServiceResponse();
		try{			
			String userId = SystemConstant.getConstant("SYSTEM_USER");
			String FLAG_YES = SystemConstant.getConstant("FLAG_YES");
			String PERSONAL_ERROR_CIS_DUPLICATE = SystemConstant.getConstant("PERSONAL_ERROR_CIS_DUPLICATE");
			String PERSONAL_ERROR_DIH_FAILED = SystemConstant.getConstant("PERSONAL_ERROR_DIH_FAILED");
			String applicationGroupId = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByInstantId(instantId);
			int lifeCycle = ORIGDAOFactory.getApplicationGroupDAO().getLifeCycle(applicationGroupId);
			logger.debug("applicationGroupId : "+applicationGroupId);
			logger.debug("lifeCycle : "+lifeCycle);
			ArrayList<PersonalInfoDataM> personalInfos = ORIGDAOFactory.getPersonalInfoDAO().loadOrigPersonalInfoForCis(applicationGroupId);
			if(!Util.empty(personalInfos)){
				for(PersonalInfoDataM personalInfo : personalInfos){
					String cisNo = personalInfo.getCisNo();
					String cidType = personalInfo.getCidType();
					String idNo = personalInfo.getIdno();
					String thFirstName = personalInfo.getThFirstName();
					String thLastName = personalInfo.getThLastName();
					String enBirthDate = (null!=personalInfo.getBirthDate())?personalInfo.getBirthDate().toString():"";
					logger.debug("cisNo : "+cisNo);
					logger.debug("cidType : "+cidType);
					logger.debug("idNo : "+idNo);
					logger.debug("thFirstName : "+thFirstName);
					logger.debug("thLastName : "+thLastName);
					logger.debug("enBirthDate : "+enBirthDate);
					if(Util.empty(cisNo)){
						personalInfo.setIdno(idNo);
						HashMap<String, CompareDataM> comparisonFields = new HashMap<>();					
						CisCustomerResult cisCustomerResult = PersonalInfoUtil.updateCisDataProcess(cidType,idNo,thFirstName,thLastName,enBirthDate,lifeCycle,userId,"",personalInfo,comparisonFields,false);
						DIHQueryResult<String> dihResult = cisCustomerResult.getDihQueryResult();
						if(ResponseData.SUCCESS.equals(dihResult.getStatusCode())){
							cisNo = cisCustomerResult.getCisNo();
							if(!Util.empty(cisNo) && !Util.empty(comparisonFields)){
								personalInfo.setCisNo(cisNo);
								personalInfo.setUpdateDate(ApplicationDate.getTimestamp());
								personalInfo.setPersonalError("");
								LookupServiceCenter.getServiceCenterManager().savePersonalCisData(personalInfo,comparisonFields,applicationGroupId,lifeCycle,userId);
							}
						}else{
							String foundDataCisMore1RowFlag = cisCustomerResult.getFoundDataCisMore1RowFlag();
							if(FLAG_YES.equals(foundDataCisMore1RowFlag)){
								 personalInfo.setPersonalError(PERSONAL_ERROR_CIS_DUPLICATE);
							}else{
								personalInfo.setPersonalError(PERSONAL_ERROR_DIH_FAILED);
							}
							personalInfo.setCisNo("");
							personalInfo.setUpdateDate(ApplicationDate.getTimestamp());
							LookupServiceCenter.getServiceCenterManager().savePersonalCisFailed(personalInfo,applicationGroupId,lifeCycle,userId);
						}
					}
				}
			}
			serviceResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		}catch(Exception e){
			logger.fatal("ERROR",e);
			serviceResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			serviceResponse.setStatusDesc(e.getLocalizedMessage());
		}
		return ResponseEntity.ok(serviceResponse);
	}
	@Deprecated
	private String getCisNoByDIH(String cidType,String idNo,String thFirstName,String thLastName,String enBirthDate) throws ApplicationException{
		String CIDTYPE_IDCARD = SystemConstant.getConstant("CIDTYPE_IDCARD");
		String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
		String CIDTYPE_MIGRANT = SystemConstant.getConstant("CIDTYPE_MIGRANT");
		String cisNo = null;
		DIHProxy dihProxy = new DIHProxy();
		if(!Util.empty(cidType) && CIDTYPE_IDCARD.equals(cidType)){
			DIHQueryResult<String> queryResult = dihProxy.getCIS_NUMBER(cidType,idNo);
			cisNo = queryResult.getResult();
		}else if(!Util.empty(cidType) &&(CIDTYPE_PASSPORT.equals(cidType)||CIDTYPE_MIGRANT.equals(cidType))){
			DIHQueryResult<String> queryResult = dihProxy.getCIS_NUMBER(cidType,idNo,thFirstName,thLastName,FormatUtil.toDate(enBirthDate,FormatUtil.EN).toString());
			cisNo = queryResult.getResult();
		}
		return cisNo;
	}
}
