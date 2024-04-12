package com.eaf.orig.ulo.app.view.util.kvi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
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
import com.eaf.service.rest.model.ServiceResponse;

public class VerifyIncomeKVI implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(VerifyIncomeKVI.class);
	String CREATE_KVI_ENDPOINT_URL = SystemConfig.getProperty("CREATE_KVI_ENDPOINT_URL");
	String EDIT_KVI_ENDPOINT_URL = SystemConfig.getProperty("EDIT_KVI_ENDPOINT_URL");
	String KVI_DEPARTMENT_CODE = SystemConstant.getConstant("KVI_DEPARTMENT_CODE");
	String FIELD_ID_BUSINESS_TYPE = SystemConstant.getConstant("FIELD_ID_BUSINESS_TYPE");	
	String BUSINESS_TYPE_OTHER = SystemConstant.getConstant("BUSINESS_TYPE_OTHER");	
	String BUTTON_ACTION_SUBMIT = SystemConstant.getConstant("BUTTON_ACTION_SUBMIT");	
	ResponseDataController responseData =null;
	@Override
	public ResponseData processAction(HttpServletRequest request){
		responseData = new ResponseDataController(request,ResponseData.FunctionId.VERIFY_INCOME_KVI);
		String data = null;
		try {
			String ACTION = request.getParameter("action");
			logger.debug("ACTION >> "+ACTION);
			if(BUTTON_ACTION_SUBMIT.equals(ACTION)){
				return  setKVIProcessForm(request);
			}else{	
				 VerifyIncome verIncomeResult =getIncomeInfoDataM(request);
				if(ResponseData.SUCCESS.equals(verIncomeResult.responseData.getResponseCode())){
					IncomeInfoDataM incomeM = verIncomeResult.getIncomeInfoDataM();
					ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
					KVIIncomeDataM kviM = null;
					if(incomeList.size() > 0){
						kviM = (KVIIncomeDataM)incomeList.get(0);
					}
					String url = SystemConfig.getProperty("KVI_URL")+"&tokenId="+(Util.empty(kviM.getTokenId())?"":URLEncoder.encode(kviM.getTokenId(),"UTF-8"))
							+"&fId="+(Util.empty(kviM.getKviFid())?"":kviM.getKviFid());
					logger.debug("url >> "+url);
					ModuleFormHandler ModuleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
					if(Util.empty(ModuleForm)){
						ModuleForm = new ModuleFormHandler();
					}
					ModuleForm.setObjectForm(incomeM);
					request.getSession().setAttribute("ModuleForm", ModuleForm);
					data = url;
				}else{
					return verIncomeResult.responseData;
				}
				
			}			
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		return  responseData.success(data);
	}
	private  VerifyIncome  getIncomeInfoDataM(HttpServletRequest request) throws Exception{
		VerifyIncome verifyIncome = new VerifyIncome();
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ApplicationGroupDataM applicationGroup=(ApplicationGroupDataM) EntityForm.getObjectForm();
		PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
		ArrayList<IncomeInfoDataM> incomeList = personal.getIncomeInfos();
		if(Util.empty(incomeList)) {
			incomeList = new ArrayList<IncomeInfoDataM>();
			personal.setIncomeInfos(incomeList);
		}
		String incomeID =  request.getParameter("incomeID");
		String incomeType = request.getParameter("incomeType");
		logger.info("Income ID :"+incomeID);
		logger.info("Income Type :"+incomeType);
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
				CreateKVIAppRequestDataM createKVIAppRequest = new CreateKVIAppRequestDataM();
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
					BigDecimal shareHolderPct = null;
					DIHProxy dihService = new DIHProxy();
					DIHQueryResult<BigDecimal>  dihShareHolder = dihService.getShareholderPercent(personal);
					if(!ResponseData.SUCCESS.equals(dihShareHolder.getStatusCode())){
						verifyIncome.setResponseData(responseData.error(dihShareHolder));
						return verifyIncome;
					}
					shareHolderPct = dihShareHolder.getResult();
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
					serviceRequest.setEndpointUrl(CREATE_KVI_ENDPOINT_URL);
					serviceRequest.setServiceId(CreateKVIAppServiceProxy.serviceId);
					serviceRequest.setObjectData(createKVIAppRequest);
					serviceRequest.setUserId(userM.getUserNo());
					serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
					serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
				String transactionId = (String)request.getSession().getAttribute("transactionId");
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				ServiceResponseDataM resp = proxy.requestService(serviceRequest,transactionId);
				if(!ServiceResponse.Status.SUCCESS.equals(resp.getStatusCode())){
				     verifyIncome.setResponseData(responseData.error(resp.getErrorInfo()));
					return verifyIncome;
				}				
				CreateKVIAppResponseDataM createKVIAppResponse = (CreateKVIAppResponseDataM)resp.getObjectData();
				if(!Util.empty(createKVIAppResponse)) {
					logger.info("Retrieve Fid :"+createKVIAppResponse.getfId());
					kviIncomeM.setKviFid(createKVIAppResponse.getfId());
					logger.info("Retrieve Token ID ");
					kviIncomeM.setTokenId(createKVIAppResponse.getTokenId());
				}
			} else {
				logger.info("EditKVI ID ");				
				EditKVIAppRequestDataM EditKVIAppRequest = new EditKVIAppRequestDataM();
					EditKVIAppRequest.setfId(kviIncomeM.getKviFid());
				ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
					serviceRequest.setEndpointUrl(EDIT_KVI_ENDPOINT_URL);
					serviceRequest.setServiceId(EditKVIAppServiceProxy.serviceId);
					serviceRequest.setUserId(userM.getUserNo());
					serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
					serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
					serviceRequest.setObjectData(EditKVIAppRequest);					
				ServiceCenterProxy proxy = new ServiceCenterProxy();
				String transactionId = (String)request.getSession().getAttribute("transactionId");
				ServiceResponseDataM resp = proxy.requestService(serviceRequest,transactionId);
				
				if(!ServiceResponse.Status.SUCCESS.equals(resp.getStatusCode())){
					verifyIncome.setResponseData(responseData.error(resp.getErrorInfo()));					
					return  verifyIncome;
				}
				EditKVIAppResponseDataM editKVIAppResponse = (EditKVIAppResponseDataM)resp.getObjectData();
				if(!Util.empty(editKVIAppResponse)) {
					logger.info("Retrieve Token ID ");
					kviIncomeM.setTokenId(editKVIAppResponse.getTokenId());
				}
			}
		}
		verifyIncome.setIncomeInfoDataM(incomeM);
		verifyIncome.setResponseData(responseData.success());
		return verifyIncome;
	}
	
	private ResponseData setKVIProcessForm(HttpServletRequest request) {
		try {
			EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
			ApplicationGroupDataM applicationGroup =(ApplicationGroupDataM) EntityForm.getObjectForm();
			PersonalInfoDataM personal = PersonalInfoUtil.getApplicationTypePersonalInfo(applicationGroup);
			
			ModuleFormHandler ModuleForm = (ModuleFormHandler)request.getSession().getAttribute("ModuleForm");
			IncomeInfoDataM incomeM = (IncomeInfoDataM)ModuleForm.getObjectForm();
			this.setKVIProperties(request,incomeM);
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
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error();
		}
		return responseData.success();
	}
	
	private void setKVIProperties(HttpServletRequest request, Object appForm) {
		IncomeInfoDataM incomeM = (IncomeInfoDataM)appForm;
		ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
		if(incomeList == null) {
			incomeList = new ArrayList<IncomeCategoryDataM>();
			incomeM.setAllIncomes(incomeList);
		}
		KVIIncomeDataM kviM = null;
		if(incomeList.size() > 0){
			kviM = (KVIIncomeDataM)incomeList.get(0);
		} else {
			kviM = new KVIIncomeDataM();
			kviM.setSeq(incomeList.size() + 1);
			incomeList.add(kviM);
		}
		
		String VERIFIED_INCOME = request.getParameter("VERIFIED_INCOME");
		String PERCENT_CHEQUE = request.getParameter("PERCENT_CHEQUE");
		
		logger.debug("PERCENT_CHEQUE >>"+PERCENT_CHEQUE);	
		logger.debug("VERIFIED_INCOME >>"+VERIFIED_INCOME);	
		
		kviM.setPercentChequeReturn(FormatUtil.toBigDecimal(PERCENT_CHEQUE));	
		kviM.setVerifiedIncome(FormatUtil.toBigDecimal(VERIFIED_INCOME));
		
		//to check if final value from screen match with previous or not
//		if(MConstant.FLAG.TEMP.equals(incomeM.getCompareFlag())) {
//			FormErrorUtil formError = new FormErrorUtil();
//			formError.addAll(IncomeTypeUtility.executeIncomeCompareScreen(request, incomeM));
//			if(Util.empty(formError.getFormError())) {
//				incomeM.setCompareFlag(MConstant.FLAG.YES);
//			} else {
//				incomeM.setCompareFlag(MConstant.FLAG.WRONG);
//			}
//		}
		
		//Set flag yes only. No need to compare for KVI
		incomeM.setCompareFlag(MConstant.FLAG.YES);
		
	}
	
	
	@SuppressWarnings("serial")
	public class VerifyIncome implements Serializable,Cloneable{
		private ResponseData  responseData;
		private IncomeInfoDataM incomeInfoDataM;
		public ResponseData getResponseData() {
			return responseData;
		}
		public void setResponseData(ResponseData responseData) {
			this.responseData = responseData;
		}
		public IncomeInfoDataM getIncomeInfoDataM() {
			return incomeInfoDataM;
		}
		public void setIncomeInfoDataM(IncomeInfoDataM incomeInfoDataM) {
			this.incomeInfoDataM = incomeInfoDataM;
		}
	}
}
