package com.eaf.orig.ulo.app.view.form.manual;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.FormAction;
import com.eaf.j2ee.pattern.control.FormHelper;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.BundleSMEDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.KVIIncomeDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.CreateKVIAppServiceProxy;
import com.eaf.service.module.manual.EditKVIAppServiceProxy;
import com.eaf.service.module.model.CreateKVIAppRequestDataM;
import com.eaf.service.module.model.CreateKVIAppResponseDataM;
import com.eaf.service.module.model.EditKVIAppRequestDataM;
import com.eaf.service.module.model.EditKVIAppResponseDataM;

public class KVIIncomePopupForm extends FormHelper implements FormAction{
	String KVI_DEPARTMENT_CODE = SystemConstant.getConstant("KVI_DEPARTMENT_CODE");
	String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");	
	String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");	
	private static transient Logger logger = Logger.getLogger(KVIIncomePopupForm.class);
	@Override
	public Object getObjectForm() {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		String transactionId = (String)request.getSession().getAttribute("transactionId");
		ApplicationGroupDataM applicationGroup=(ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<IncomeInfoDataM> incomeList = personal.getIncomeInfos();
		if(Util.empty(incomeList)) {
			incomeList = new ArrayList<IncomeInfoDataM>();
			personal.setIncomeInfos(incomeList);
		}
		String incomeID = getRequestData("incomeID");
		String incomeType = getRequestData("incomeType");
		logger.info("Income ID :"+incomeID);
		logger.info("Income Type :"+incomeType);
		logger.info("rowId :"+rowId);
		IncomeInfoDataM incomeM = null;
		if("0".equals(incomeID)) {
			incomeM = new IncomeInfoDataM();
			incomeM.setIncomeType(incomeType);
			incomeM.setSeq(incomeList.size()+1);
		} else {
			int seq = FormatUtil.getInt(incomeID);
			incomeM = personal.getIncomeBySeq(seq);
		}
		if(incomeM != null) {
			logger.info("Retrieve Income ID :"+incomeM);
			ArrayList<IncomeCategoryDataM> incomeTypeList = incomeM.getAllIncomes();
			if(Util.empty(incomeTypeList)) {
				incomeTypeList = new ArrayList<IncomeCategoryDataM>();
				incomeM.setAllIncomes(incomeTypeList);
				KVIIncomeDataM kviM = new KVIIncomeDataM();
				kviM.setIncomeId(incomeM.getIncomeId());
				kviM.setSeq(incomeTypeList.size()+1);
				kviM.setIncomeType(incomeType);
				incomeTypeList.add(kviM);
			}
			KVIIncomeDataM kviIncomeM = (KVIIncomeDataM)incomeM.getAllIncomes().get(0);
			if(Util.empty(kviIncomeM.getKviFid())) {
				logger.info("CreateKVI ID ");
				String url = SystemConfig.getProperty("CREATE_KVI_ENDPOINT_URL");
				CreateKVIAppRequestDataM createKVIAppRequest = new CreateKVIAppRequestDataM();
				UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
				createKVIAppRequest.setFgAppNo(applicationGroup.getApplicationGroupNo());
				createKVIAppRequest.setFcDept(KVI_DEPARTMENT_CODE);
				createKVIAppRequest.setFcInputId(userM.getUserNo());
				createKVIAppRequest.setFgRequestor(personal.getThFirstName());
				createKVIAppRequest.setFgRequestorL(personal.getThLastName());
				createKVIAppRequest.setFgType(personal.getCidType());
				createKVIAppRequest.setFgId(personal.getIdno());
				createKVIAppRequest.setFgCisNo(personal.getCisNo());
				ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
				String bussinessCode = null;
				String BUSINESS_TYPE_DESC = null;
				if(!Util.empty(applications)){
					for(ApplicationDataM application : applications){
						BundleSMEDataM bundleSME = application.getBundleSME();
						if(null!=bundleSME)
							bussinessCode = bundleSME.getBusinessCode();
					}
				}
				if(!Util.empty(bussinessCode)){
					DIHProxy dihProxy = new DIHProxy();
					DIHQueryResult<String> busDesc = dihProxy.getBussinessDesc(bussinessCode);
					BUSINESS_TYPE_DESC = busDesc.getResult();
					if(BUSINESS_TYPE_OTHER.equals(bussinessCode)){
						BUSINESS_TYPE_DESC = bussinessCode;
					}
					createKVIAppRequest.setFcBusiness(bussinessCode);
				}else{
					BUSINESS_TYPE_DESC = ListBoxControl.getName(FIELD_ID_BUSINESS_TYPE,personal.getBusinessType());
					if(BUSINESS_TYPE_OTHER.equals(personal.getBusinessType())){
						BUSINESS_TYPE_DESC = personal.getBusinessTypeOther();
					}
					createKVIAppRequest.setFcBusiness(personal.getBusinessType());
				}
				createKVIAppRequest.setFcBusinessDesc(BUSINESS_TYPE_DESC);
				DIHProxy dihProxy = new DIHProxy();
				DIHQueryResult<BigDecimal> queryResult = dihProxy.getShareholderPercent(personal);
				BigDecimal shareHolderPct = queryResult.getResult();
				logger.info("shareHolderPct = "+shareHolderPct);
				int compareCompanyOrgIdBol = 0;
				if(!Util.empty(shareHolderPct)){
					compareCompanyOrgIdBol=shareHolderPct.intValue();
				}
				BigDecimal percentShareHolder = BigDecimal.valueOf(-1);
				if(compareCompanyOrgIdBol>0 && compareCompanyOrgIdBol<100){
					percentShareHolder = BigDecimal.valueOf(compareCompanyOrgIdBol);
				}
				createKVIAppRequest.setPercentShareHolder(percentShareHolder);
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CreateKVIAppServiceProxy.serviceId);
				serviceRequest.setObjectData(createKVIAppRequest);
				serviceRequest.setUserId(userM.getUserNo());
				serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
				serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				try{
					ServiceCenterProxy proxy = new ServiceCenterProxy();
					ServiceResponseDataM resp = proxy.requestService(serviceRequest,transactionId);
					CreateKVIAppResponseDataM createKVIAppResponse = (CreateKVIAppResponseDataM)resp.getObjectData();
					if(!Util.empty(createKVIAppResponse)) {
						logger.info("Retrieve Fid :"+createKVIAppResponse.getfId());
						kviIncomeM.setKviFid(createKVIAppResponse.getfId());
						logger.info("Retrieve Token ID ");
						kviIncomeM.setTokenId(createKVIAppResponse.getTokenId());
					}
				}catch(Exception e){
					logger.fatal("ERROR",e);
				}
			} else {
				logger.info("EditKVI ID ");
				String url = SystemConfig.getProperty("EDIT_KVI_ENDPOINT_URL");
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(EditKVIAppServiceProxy.serviceId);
				
				EditKVIAppRequestDataM EditKVIAppRequest = new EditKVIAppRequestDataM();
				EditKVIAppRequest.setfId(kviIncomeM.getKviFid());
				
				serviceRequest.setObjectData(EditKVIAppRequest);
				try{
					ServiceCenterProxy proxy = new ServiceCenterProxy();
					ServiceResponseDataM resp = proxy.requestService(serviceRequest,transactionId);
					EditKVIAppResponseDataM editKVIAppResponse = (EditKVIAppResponseDataM)resp.getObjectData();
					if(!Util.empty(editKVIAppResponse)) {
						logger.info("Retrieve Token ID ");
						kviIncomeM.setTokenId(editKVIAppResponse.getTokenId());
					}
				}catch(Exception e){
					logger.fatal("ERROR",e);
				}
			}
		}
		return incomeM;
	}
	
	@Override
	public String processForm() {
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup =(ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		IncomeInfoDataM incomeM = (IncomeInfoDataM)objectForm;
		String roleId = FormControl.getFormRoleId(request);
		if(MConstant.ROLE.DE1_2.equals(roleId)){
			incomeM.setCompareFlag(MConstant.FLAG.NO);
		}
		IncomeInfoDataM incomeOrig = personal.getIncomeBySeq(incomeM.getSeq());
		ArrayList<IncomeInfoDataM> incomeInfos = personal.getIncomeInfos();
		if(Util.empty(incomeOrig)) {
			incomeInfos.add(incomeM);
		} else {
			incomeInfos.set(incomeM.getSeq() - 1, incomeM);
		}
		return null;
	}
}
