package com.eaf.service.common.activemq.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.flp.security.encryptor.FLPEncryptorFactory;
import com.eaf.flp.eapp.util.EAppUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.app.SaleInfoDataM;
import com.eaf.service.common.activemq.AMQMessageSender;
import com.eaf.service.common.activemq.CreateXML;
import com.eaf.service.common.activemq.DateConverter;
import com.eaf.service.common.activemq.SmartServeMqUtils;
import com.eaf.service.common.activemq.model.SSCaseInfo;
import com.eaf.service.common.activemq.model.SSCreateCaseBody;
import com.eaf.service.common.activemq.model.SSCreateCaseRoot;
import com.eaf.service.common.activemq.model.SSCustInfo;
import com.eaf.service.common.activemq.model.SSFulfillmentInfoDefault;
import com.eaf.service.common.activemq.model.SSHeaderRequest;
import com.eaf.service.common.util.ServiceUtil;



public class BaseSmartServeCreateCaseService  {

	private static final long serialVersionUID = -3288902640185225145L;

	private static transient final Logger LOG = Logger.getLogger(BaseSmartServeCreateCaseService.class);

	public static final String FULFILLMENT_INFO_CLASS = "FULFILLMENT_INFO_CLASS";
	public static final String FUNCTION_CODE = "FUNCTION_CODE";
	public static final String EXTERNAL_ACTION_NAME = "ACTION_NAME";
	public static final String CHECKPOINT = "CHECKPOINT";
	public static final String APPLICATION_NO = "APPLICATION_NO";
	public static final String USER_ID = "USER_ID";
	public static final String ATTACH_DOC_GROUP_LIST = "ATTACH_DOC_GROUP_LIST";
	public static final String DOC_GROUP_INFO_LIST = "DOC_GROUP_INFO_LIST";

	public final static String SUCCESS = "00";
	public final static String CONNECTION_EXCEPTION = "01";
	public final static String JMS_EXCEPTION = "02";
	public final static String EXCEPTION = "03";

	protected SSCaseInfo prepareCommonCaseInfo(ApplicationGroupDataM applicationGroupDataM) throws Exception {
		
		SaleInfoDataM saleInfoData= applicationGroupDataM.getSaleInfoByType(SystemConstant.getConstant("SALE_TYPE_REFERENCE_SALES"));
		if(saleInfoData==null){
			saleInfoData=new SaleInfoDataM();
		}
		
		SSCaseInfo caseInfo = new SSCaseInfo();
		caseInfo.setCreatorID(ServiceUtil.displayText(applicationGroupDataM.getSourceUserId()));

		caseInfo.setMQCustomFlag(SystemConstant.getConstant("ACTIVEMQ_MQ_CUSTOM_FLAG"));
		caseInfo.setMQCustomChannel(SystemConstant.getConstant("ACTIVEMQ_MQ_CUSTOM_CHANNEL"));
		return caseInfo;
	}

	protected SSCustInfo prepareCommonCustInfo(PersonalInfoDataM customer) throws Exception {
	
		AddressDataM home= customer.getAddress(SystemConstant.getConstant("ADDRESS_TYPE_CURRENT"));
		AddressDataM office= customer.getAddress(SystemConstant.getConstant("ADDRESS_TYPE_WORK"));
		if(home==null){
			home=new AddressDataM();
		}
		if(office==null){
			office=new AddressDataM();
		}
		
		SSCustInfo custInfo = new SSCustInfo();
		custInfo.setCISID(ServiceUtil.displayText(customer.getCisNo()));
		custInfo.setCustType(ServiceUtil.displayText(customer.getCustomerType()));
		custInfo.setDocType(ServiceUtil.displayText(SmartServeMqUtils.mappingDocType(customer.getCidType())));
		custInfo.setDocID(ServiceUtil.displayText(customer.getIdno()));
		custInfo.setTHNameTitle(ServiceUtil.displayText(customer.getThTitleDesc()));
		custInfo.setThaiName(ServiceUtil.displayText(customer.getThFirstName()));
		custInfo.setThaiLastName(ServiceUtil.displayText(customer.getThLastName()));
		custInfo.setENNameTitle(ServiceUtil.displayText(customer.getEnTitleDesc()));
		custInfo.setEngName(ServiceUtil.displayText(customer.getEnFirstName()));
		custInfo.setEngLastName(ServiceUtil.displayText(customer.getEnLastName()));
		custInfo.setFullNameThai(ServiceUtil.displayText(customer.getThName()));
		custInfo.setFullNameEn(ServiceUtil.displayText(customer.getEnName()));
		
		custInfo.setBirthdate(ServiceUtil.display(customer.getBirthDate(),ServiceUtil.EN,"yyyyMMdd"));
		custInfo.setTelHome(ServiceUtil.replaceAll(home.getPhone1(),"-"));
		custInfo.setTelHomeExt(ServiceUtil.displayText(home.getPhone2()));
		custInfo.setTelOffice(ServiceUtil.replaceAll(office.getPhone1(),"-"));
		custInfo.setTelMobile(ServiceUtil.replaceAll(customer.getMobileNo(),"-"));
		custInfo.setFax(ServiceUtil.replaceAll(home.getFax(),"-"));
		custInfo.setEmail(ServiceUtil.displayText(ServiceUtil.displayText(customer.getEmailPrimary())));
		
		custInfo.setOffCLAddr1(ServiceUtil.displayText(office.getAddress1()));
		custInfo.setOffCLAddr2(ServiceUtil.displayText(office.getAddress2()));
		custInfo.setPrimarySegCode(ServiceUtil.displayText(customer.getCisPrimSegment()));
		custInfo.setPrimarySubSegCode(ServiceUtil.displayText(customer.getCisPrimSubSegment()));
		custInfo.setSecondarySegCode(ServiceUtil.displayText(customer.getCisSecSegment()));
		custInfo.setSecondarySubSegCode(ServiceUtil.displayText(customer.getCisSecSubSegment()));
		custInfo.setPrimarySegDetail(ServiceUtil.displayText(customer.getCisPrimDesc()));
		custInfo.setPrimarySubSegDetail(ServiceUtil.displayText(customer.getCisPrimSubDesc()));
		custInfo.setSecondarySegDetail(ServiceUtil.displayText(customer.getCisSecDesc()));
		custInfo.setSecondarySubSegDetail(ServiceUtil.displayText(customer.getCisSecSubDesc()));
		return custInfo;
	}

	protected <T extends SSFulfillmentInfoDefault> void prepareCommonFulfillmentInfo(T fulfillmentInfo, ApplicationGroupDataM applicationGroupDataM) throws Exception {
		String CACHE_TEMPLATE = SystemConstant.getConstant("CACHE_TEMPLATE");
		ArrayList<ApplicationDataM> applications=applicationGroupDataM.getApplications();
		ApplicationDataM application=new ApplicationDataM();;
		if(applications!=null&&applications.size()>0){
			application=applications.get(0);
		}
		
		SaleInfoDataM saleInfoData= applicationGroupDataM.getSaleInfoByType(SystemConstant.getConstant("SALE_TYPE_REFERENCE_SALES"));
		if(saleInfoData==null){
			saleInfoData=new SaleInfoDataM();
		}
//		String qr1=EAppUtil.getQr1(application.getProduct());
		String qr1 = CacheControl.getName(CACHE_TEMPLATE, "TEMPLATE_ID", applicationGroupDataM.getApplicationTemplate(), "TEMPLATE_CODE");
		fulfillmentInfo.setSetID(applicationGroupDataM.getApplicationGroupNo());
		fulfillmentInfo.setFormQRCode(qr1);
		fulfillmentInfo.setFormatTypeNo(EAppUtil.getFormatType(qr1));
		fulfillmentInfo.setITPurpose(EAppUtil.getItPurpose(qr1));
		fulfillmentInfo.setFormLocation(EAppUtil.getFormLocation(qr1));
		fulfillmentInfo.setProductTypeNo(EAppUtil.getProductTypeNo(qr1));
		fulfillmentInfo.setFormTypeNo(EAppUtil.getFormTypeNo(qr1));
		fulfillmentInfo.setFormVersionNo(EAppUtil.getFormVersion(qr1));
		fulfillmentInfo.setPriority(SystemConstant.getConstant("ACTIVEMQ_PRIORITY"));
		fulfillmentInfo.setWebScanUser(ServiceUtil.displayText(applicationGroupDataM.getSourceUserId()));
		fulfillmentInfo.setIPAddress(ServiceUtil.displayText(ServiceUtil.getServerIp()));
		fulfillmentInfo.setEventFlag(SystemConstant.getConstant("ACTIVEMQ_EVENTFLAG"));
		fulfillmentInfo.setAttachmentFlag((ServiceUtil.empty(applicationGroupDataM.getLhRefId())?MConstant.FLAG_N:MConstant.FLAG_Y));
		fulfillmentInfo.setDocumentChronicleID(ServiceUtil.displayText(applicationGroupDataM.getLhRefId()));
	}



	protected String generateSmartServeCaseID(String appNo, String actionName) {
		return appNo;
	}
	
	public String createCase(ApplicationGroupDataM applicationGroupDataM , String myFunctionCode, String myExternalActionName, String checkpoint, String userId,String serviceReqResId) throws Exception {
		String funcNm = "";
		
				
		String appId=applicationGroupDataM.getApplicationGroupId();
		
		
		List<PersonalInfoDataM> customers = applicationGroupDataM.getPersonalInfos();
	//	String ssCaseId = generateSmartServeCaseID(applicationGroupDataM.getApplicationGroupId(), myExternalActionName);

		SSHeaderRequest header = SmartServeMqUtils.createHeaderRequest(userId, funcNm,appId,serviceReqResId);
		SSCreateCaseRoot rootRq = new SSCreateCaseRoot(header);
		SSCreateCaseBody body = new SSCreateCaseBody();
		

		body.setCaseInfo(prepareCommonCaseInfo(applicationGroupDataM));
		body.setCustInfo(prepareCommonCustInfo(customers.get(0)));

		SSFulfillmentInfoDefault fulfillmentInfo = new SSFulfillmentInfoDefault();;
		prepareFulfillmentInfo(fulfillmentInfo,applicationGroupDataM);

		body.getCaseInfo().setFullfillmentInfo(fulfillmentInfo);
		rootRq.setBodyRequest(body);
		rootRq.setHeaderRequest(header);
		
		String message = CreateXML.getXML(rootRq, new DateConverter());
		
		return message;
	}
	

	
	protected <T extends SSFulfillmentInfoDefault> void prepareFulfillmentInfo(T fulfillmentInfo,ApplicationGroupDataM application) throws Exception {
		prepareCommonFulfillmentInfo(fulfillmentInfo, application);
	}

	public String sendMessage(String appNo, String externalActionName, String ssCaseId, String userId, String message,String requestStyle) throws Exception {

		
		AMQMessageSender messageSender = new AMQMessageSender();
		final String JMS_DEFALT_Q_NAME = SystemConfig.getProperty("JMS_PEGA_DEFALT_Q_NAME");
		final String USER = SystemConfig.getProperty("JMS_PEGA_USER");
		final String PASSWORD = FLPEncryptorFactory.getFLPEncryptor().decrypt(SystemConfig.getProperty("JMS_PEGA_PASSWORD"));
		final String URL = SystemConfig.getProperty("JMS_PEGA_URL");
		return messageSender.sendMessage(JMS_DEFALT_Q_NAME, message, URL, USER, PASSWORD);
	}
}