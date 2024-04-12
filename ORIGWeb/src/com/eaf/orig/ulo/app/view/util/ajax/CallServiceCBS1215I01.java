package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.ErrorData;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ModuleFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.FixedGuaranteeDataM;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.service.common.api.ServiceApplicationDate;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceErrorInfo;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.module.manual.CBS1215I01ServiceProxy;
import com.eaf.service.module.manual.CIS1048O01ServiceProxy;
import com.eaf.service.module.model.CBS1215I01RequestDataM;
import com.eaf.service.module.model.CBS1215I01ResponseDataM;
import com.eaf.service.module.model.CBSRetentionDataM;
import com.eaf.service.rest.model.ServiceResponse;

public class CallServiceCBS1215I01 implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(CallServiceCBS1215I01.class);
	public static final String CBS1215_DATA = "CBS1215_DATA";
	private static final String POPUP_FORM = "CBS1215_DATA_FORM";
	String CBS1215I01_ENDPOINT_URL = SystemConfig.getProperty("CBS1215I01_ENDPOINT_URL");
	
	int START_INDEX_ROW = FormatUtil.getInt(SystemConstant.getConstant("START_INDEX_ROLE"));
	int MAX_COUNT_ROW = FormatUtil.getInt(SystemConstant.getConstant("MAX_COUNT_ROW"));
	int CBS_MAX_CALL_TIME = FormatUtil.getInt(SystemConstant.getConstant(("CBS_MAX_CALL_TIME")));
	private String userId="";
	String transactionId;
	@Override
	public ResponseData processAction(HttpServletRequest request){		
		CBS1215I01DataM cbs1215I01DataM = new CBS1215I01DataM();
		transactionId = (String)request.getSession().getAttribute("transactionId");
		ArrayList<FixedGuaranteeDataM> fixedList = new ArrayList<FixedGuaranteeDataM>();
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.CALL_SERVICE_CBS1215I01);
		ModuleFormHandler moduleHandler = (ModuleFormHandler) request.getSession().getAttribute("ModuleForm");
		ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
		UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");

		try{
			String account = request.getParameter("acctNo");
			String subAccountNum = request.getParameter("sub");
			logger.debug("account : "+account);
			logger.debug("subAccountNum : "+subAccountNum);
			userId = userM.getUserName();
			
			int startIndexRow=START_INDEX_ROW;
			int maxCountRow=MAX_COUNT_ROW;
			
			ServiceCenterProxy proxy = new ServiceCenterProxy();
			ServiceResponseDataM resp =callCBS1215I01Service(startIndexRow, maxCountRow, proxy, applicationGroup, account, subAccountNum);
			if(ServiceResponse.Status.SUCCESS.equals(resp.getStatusCode())){
				CBS1215I01ResponseDataM CBS1215I01Response = (CBS1215I01ResponseDataM)resp.getObjectData();
				int totalRecord = CBS1215I01Response.getTotalRecord();
				String moredataFlag = CBS1215I01Response.getMoreDataInd();
				logger.debug("totalRecord>>"+totalRecord);
				logger.debug("moredataFlag>>"+moredataFlag);								
				ResponseData response = mappingFixedList(moduleHandler,CBS1215I01Response, fixedList, responseData, request, cbs1215I01DataM);
				logger.debug("response.getResponseCode()>>"+response.getResponseCode());	
				if(!ResponseData.SUCCESS.equals(response.getResponseCode())){
					return response;
				}				
				
				// call CBS1215I01  by total
				if(totalRecord>maxCountRow && MConstant.FLAG.YES.equals(moredataFlag)){
					int callTimeOfTotal = totalRecord/maxCountRow;
					int modValue = totalRecord%maxCountRow;
					logger.debug("modValue>>"+modValue);
					if(modValue>0){
						callTimeOfTotal = callTimeOfTotal+1;
					}
					logger.debug("callTimeOfTotal>>"+callTimeOfTotal);					
					for(int i=0;i<callTimeOfTotal && i<CBS_MAX_CALL_TIME;i++){
						  logger.debug("moredataFlag : time >"+moredataFlag+" : "+i);
						  if(!MConstant.FLAG.YES.equals(moredataFlag)) break;
						  
						  startIndexRow =maxCountRow+1;
						  maxCountRow =maxCountRow+MAX_COUNT_ROW;
						  logger.debug("startIndexRow : "+startIndexRow);
						  logger.debug("maxCountRow : "+maxCountRow);
						  
						   resp =callCBS1215I01Service(startIndexRow, maxCountRow, proxy, applicationGroup, account, subAccountNum);						
							if(ServiceResponse.Status.SUCCESS.equals(resp.getStatusCode())){								
								response = mappingFixedList(moduleHandler,CBS1215I01Response, fixedList, responseData, request,cbs1215I01DataM);
								if(!ResponseData.SUCCESS.equals(response.getResponseCode())){
									return response;
								}
								moredataFlag = CBS1215I01Response.getMoreDataInd();
							}else{
								return  responseData.error(resp.getErrorInfo());
							}
					}					
				}else{
					return responseData.success(cbs1215I01DataM.getData());
				}				
				
			}else{
				return  responseData.error(resp.getErrorInfo());
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		
		return responseData.success(cbs1215I01DataM.getData());
	}
 
	private  ServiceResponseDataM  callCBS1215I01Service(int startIndexRow,int maxCountRow,ServiceCenterProxy proxy,ApplicationGroupDataM applicationGroup,String account ,String subAccountNum){
		ServiceResponseDataM serviceResponseDataM  = new ServiceResponseDataM();
		try {
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
			serviceRequest.setEndpointUrl(CBS1215I01_ENDPOINT_URL);
			serviceRequest.setServiceId(CBS1215I01ServiceProxy.serviceId);
			serviceRequest.setUniqueId(applicationGroup.getApplicationGroupId());
			serviceRequest.setRefId(applicationGroup.getApplicationGroupNo());
			serviceRequest.setUserId(userId);
			CBS1215I01RequestDataM CBS1215I01Request = new CBS1215I01RequestDataM();
			CBS1215I01Request.setAccountId(account);
			CBS1215I01Request.setSubAccountNumber(subAccountNum);
			CBS1215I01Request.setMaxRow(maxCountRow);
			CBS1215I01Request.setStartIndex(startIndexRow);		
			serviceRequest.setObjectData(CBS1215I01Request);
			serviceResponseDataM = proxy.requestService(serviceRequest,transactionId);
		 } catch (Exception e) {
			 logger.debug("ERROE",e);
			 serviceResponseDataM.setStatusCode(ServiceConstant.Status.SYSTEM_EXCEPTION);
			 serviceResponseDataM.setErrorInfo(error(CIS1048O01ServiceProxy.serviceId,ErrorData.ErrorType.SYSTEM_ERROR,e));
		 }
		return serviceResponseDataM;
	}
	
	private ResponseData  mappingFixedList(ModuleFormHandler moduleHandler,CBS1215I01ResponseDataM CBS1215I01Response, ArrayList<FixedGuaranteeDataM> fixedList,ResponseDataController responseData 
			,HttpServletRequest request,CBS1215I01DataM cbs1215I01DataM){
		try {
			if(!Util.empty(CBS1215I01Response)) {
				ArrayList<CBSRetentionDataM> cbsRetentionList = CBS1215I01Response.getCbsRetentions();			
				if(!Util.empty(cbsRetentionList)) {
					DIHProxy dihService = new DIHProxy();	
					int count =cbs1215I01DataM.getCountFixedM();
					for(CBSRetentionDataM cbsRetentionHash : cbsRetentionList) {
						DIHQueryResult<String> dihAccountResult = dihService.getAccountNameFixGuarantee(CBS1215I01Response.getAccountId(), cbsRetentionHash.getSubAccountNum());
						if(ResponseData.SUCCESS.equals(dihAccountResult.getStatusCode())){
							FixedGuaranteeDataM fixedM = new FixedGuaranteeDataM();
								fixedM.setAccountNo(CBS1215I01Response.getAccountId());
								fixedM.setSub(cbsRetentionHash.getSubAccountNum());
								fixedM.setAccountName(dihAccountResult.getResult());
								fixedM.setBranchNo(cbsRetentionHash.getTransactionBranchId());
								fixedM.setRetentionAmt(cbsRetentionHash.getRetentionAmount());
								fixedM.setRetentionDate(cbsRetentionHash.getRetentionCreateDate());
								//rawi fix defect map data RetentionType,ReTentionTypeDesc
								fixedM.setRetentionType(cbsRetentionHash.getRetentionType());
								fixedM.setRetentionTypeDesc(cbsRetentionHash.getRetentionTypeDesc());
								fixedM.setSeq(count++);
							fixedList.add(fixedM);	
						}else{
							 return responseData.error(dihAccountResult);
						}
					}
					cbs1215I01DataM.setCountFixedM(count);
					
				}
			}
			
			request.getSession().setAttribute(CBS1215_DATA, fixedList);
			logger.debug("fixedList>>"+fixedList);
			if(fixedList.size() == 1){
				
				IncomeInfoDataM incomeM = (IncomeInfoDataM) moduleHandler.getObjectForm();
				ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
				FixedGuaranteeDataM fixedM = fixedList.get(0);
				fixedM.setIncomeId(incomeM.getIncomeId());
				fixedM.setIncomeType(incomeM.getIncomeType());
				fixedM.setSeq(incomeList.size()+1);
				incomeList.add(fixedM);
			}else{
				if(fixedList.size()<=0){
					cbs1215I01DataM.setData(ResponseData.SYSTEM_EXCEPTION);
				}else{
					cbs1215I01DataM.setData(POPUP_FORM);
				}
			}
		} catch (Exception e) {
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		
		return responseData.success();
		
	}
	
	private ServiceErrorInfo error(String serviceId,String errorType,Exception e){	
		ServiceErrorInfo errorData = new ServiceErrorInfo();
		errorData.setServiceId(serviceId);
		errorData.setErrorType(errorType);
		errorData.setErrorInformation(e.getLocalizedMessage());
		errorData.setErrorTime(ServiceApplicationDate.getTimestamp());
		return errorData;
	}
	
	public  class CBS1215I01DataM{
		 private String data;
		 private int countFixedM=0;

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}

		public int getCountFixedM() {
			return countFixedM;
		}

		public void setCountFixedM(int countFixedM) {
			this.countFixedM = countFixedM;
		}	
		
	}
	
	
}
