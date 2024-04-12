package com.eaf.service.common.mlp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.ava.flp.eapp.submitapplication.model.ApplicationGroup;
import com.eaf.core.ulo.common.postapproval.CardLinkAction;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.shared.dao.OrigObjectDAO;
import com.eaf.orig.shared.service.OrigServiceLocator;
import com.eaf.orig.ulo.app.factory.ModuleFactory;
import com.eaf.orig.ulo.app.key.dao.UniqueIDGeneratorDAO;
import com.eaf.orig.ulo.app.postapproval.process.NewCardProcess;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.app.CardlinkCustomerDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.ia.KBankHeader;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.dao.ServiceFactory;
import com.eaf.service.common.eapp.mapper.EAppModelMapper;
import com.eaf.service.common.master.ServiceLocator;
import com.eaf.service.common.mlp.bean.MLPServiceManagerBean;
import com.eaf.service.common.model.ServiceReqRespDataM;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.rest.application.model.SubmitApplicationResponse;
import com.eaf.service.rest.model.KbankError;
import com.eaf.service.rest.model.ResponseKBankHeader;
import com.eaf.service.rest.model.ServiceResponse;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.eaf.ulo.cache.dao.CacheServiceLocator;
import com.eaf.ulo.cache.lookup.model.LookupCacheDataM;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet("/MLPSubmitApp")
public class MLPSubmitAppServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(MLPSubmitAppServlet.class);
	public static String webContentPath = "";
	
	@Override
    public void init() throws ServletException {
    	webContentPath = getServletContext().getRealPath("");
    	String logConfigPath = getServletContext().getRealPath("/WEB-INF/log4j.properties");
    	logger = Logger.getLogger(this.getClass());
		PropertyConfigurator.configure(logConfigPath);
    	logger.debug("init()..");
    	boolean loadCacheOnStartup = CacheController.loadCacheOnStartup(CacheServiceLocator.ORIG_DB);
		logger.debug("loadCacheOnStartup >> "+loadCacheOnStartup);
    	LookupCacheDataM lookupCache = new LookupCacheDataM();
			lookupCache.setLookupName(CacheConstant.LookupName.SERVICE_CENTER);
			lookupCache.setRuntime(CacheConstant.Runtime.SERVER);
			lookupCache.create(CacheConstant.CacheType.METAB_CACHE,CacheServiceLocator.ORIG_DB,"ORIG_METABCACHE",loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONSTANT,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SYSTEM_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.GENERAL_PARAM,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.INFBATCH_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.ENCRYPTION_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.APPLICATION_DATE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			//lookupCache.create(CacheConstant.CacheType.REPORT_PARAM,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			lookupCache.create(CacheConstant.CacheType.SERVICE_TYPE,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
			//lookupCache.create(CacheConstant.CacheType.SIMULATE_CONFIG,CacheServiceLocator.ORIG_DB,loadCacheOnStartup);
		CacheController.startup(lookupCache);
		
		OrigServiceLocator.getInstance().setJAVA_ENV("java:comp/env/");
		ServiceLocator.getInstance().setJAVA_ENV("java:comp/env/");
    }
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException 
	{
		String SUCCESS_CODE = SystemConstant.getConstant("SUCCESS_CODE");
		String CACHE_ORGANIZATION = SystemConstant.getConstant("CACHE_ORGANIZATION");
		String CACHE_BUSINESS_CLASS = SystemConstant.getConstant("CACHE_BUSINESS_CLASS");
		String DECISION_FINAL_DECISION_APPROVE = SystemConstant.getConstant("DECISION_FINAL_DECISION_APPROVE");	
		String JOBSTATE_APPROVED = SystemConstant.getConstant("JOBSTATE_APPROVED");
		String APPLICATION_STATIC_APPROVED = SystemConstant.getConstant("APPLICATION_STATIC_APPROVED");
		String CHANNEL_BRANCH = SystemConstant.getConstant("CHANNEL_BRANCH");
		String PTT_CARD_TYPE = SystemConstant.getConstant("PTT_CARD_TYPE");
		String PTT_PARAM_TYPE = SystemConstant.getConstant("PTT_PARAM_TYPE");
		String MLP_APP = "MLPSubmitApplication";
		
		SubmitApplicationResponse submitApplicationResponse = new SubmitApplicationResponse();
    	ResponseKBankHeader responseKbankHeader = new ResponseKBankHeader();
    	Gson gson = new Gson();
    	
    	String transactionId = ServiceUtil.generateTransectionId();
    	String appGroupNo = "";
    	String appGroupId = "";
    	String userName = "";
    	
		try
		{
			resp.setCharacterEncoding("UTF-8");
			resp.setContentType("text/plain");
			
			String reqString = IOUtils.toString(req.getReader());	
    		//logger.debug("reqString = " + reqString);
    		
	    	JsonParser parser = new JsonParser();
	    	JsonObject o = parser.parse(reqString).getAsJsonObject();
	    	JsonObject kh = o.getAsJsonObject("kbankHeader");
	    	JsonObject ag = o.getAsJsonObject("applicationGroup");
	    	logger.debug("ag " + ag.toString());
	    	
	    	ObjectMapper objectMapper = new ObjectMapper();
	    	objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    	KBankHeader kbankHeader = objectMapper.readValue(kh.toString(), KBankHeader.class);
	    	userName = kbankHeader.getUserId();
			responseKbankHeader.setRqUID(kbankHeader.getRqUID());
	    	responseKbankHeader.setRsAppId( kbankHeader.getRsAppId());
	    	ApplicationGroup applicationGroup = objectMapper.readValue(ag.toString(), ApplicationGroup.class);
	    	
	    	logger.debug("appGroupp " + applicationGroup.getApplicationGroupNo());
	    	ApplicationGroupDataM uloApplicationGroup = new EAppModelMapper().mapUloModel(null, applicationGroup);
	    	logger.debug("applicationGroupId : " + uloApplicationGroup.getApplicationGroupId());
	    	
	    	appGroupId = uloApplicationGroup.getApplicationGroupId();
	    	appGroupNo = uloApplicationGroup.getApplicationGroupNo();
	    	
	    	//Create request log
	    	String serviceReqRespId = ServiceUtil.generateServiceReqResId();
			ServiceReqRespDataM serviceReq = new ServiceReqRespDataM();
			serviceReq.setReqRespId(serviceReqRespId);
			serviceReq.setTransId(transactionId);
			serviceReq.setRespCode(null);
			serviceReq.setRespDesc(null);
			serviceReq.setErrorMessage(null);
			serviceReq.setServiceId(MLP_APP);
			serviceReq.setActivityType(ServiceConstant.IN);
			serviceReq.setContentMsg(reqString);
			serviceReq.setRefCode(appGroupNo);
			serviceReq.setCreateDate(ServiceUtil.getCurrentTimestamp());
			serviceReq.setAppId(appGroupId);
			serviceReq.setCreateBy(userName);
			ServiceFactory.getServiceDAO(ServiceLocator.ORIG_DB).createLog(serviceReq);
	    	
	    	//New Customer Process
			UniqueIDGeneratorDAO uniqueIDDAO = ModuleFactory.getUniqueCardGeneratorDAO(OrigObjectDAO.EXECUTION_MANUAL);
	    	for(ApplicationDataM application : uloApplicationGroup.getApplications())
	    	{
	    		//Set Final Decision to Approve
	    		application.setFinalAppDecision(DECISION_FINAL_DECISION_APPROVE);
	    		
	    		//Create cardLinkCusts
	    		String applicationRecordId = application.getApplicationRecordId();
	    		CardDataM cardM = application.getCard();
	    		
	    		//Generate Card No
	    		if(cardM != null)
	    		{
	    			NewCardProcess ncp = new NewCardProcess();
	    			ProcessResponse statusCode = ncp.execute(uloApplicationGroup, application);
	    			if(!SUCCESS_CODE.equals(statusCode.getStatusCode()))
	    			{
	    				if(null != statusCode.getErrorData())
	    				{
							throw new Exception(statusCode.getErrorData().getErrorInformation());
						}
	    			}
	    		}
	    		
	    		//Add membershipNo for PTT card
	    		if(cardM != null && PTT_CARD_TYPE.equals(cardM.getCardType()))
	    		{
	    			String applicationType = uloApplicationGroup.getApplicationType();
	    			if(ApplicationUtil.isGenerateMemberShipNo(applicationType, uloApplicationGroup.getApplicationTemplate(), cardM.getApplicationType())){
	    				String membershipNo = uniqueIDDAO.getGenerateRunningNoStack(cardM.getCardType(),PTT_PARAM_TYPE);
	    				logger.debug("membershipNo : "+membershipNo);
	    				cardM.setMembershipNo(membershipNo);
	    			}
	    		}
	    		
				PersonalInfoDataM personalInfo = uloApplicationGroup.getPersonalInfoByRelation(applicationRecordId);
				
				if(null != personalInfo && null != cardM){
					CardlinkCustomerDataM cardLink = ApplicationUtil.getCardlinkCustomer(uloApplicationGroup, applicationRecordId);
					if(null == cardLink){
						String businessClassId = application.getBusinessClassId();
						logger.info("businessClassId : "+businessClassId);
						String orgId =  CacheControl.getName(CACHE_BUSINESS_CLASS,"BUS_CLASS_ID",businessClassId,"ORG_ID");
						String orgNo = CacheControl.getName(CACHE_ORGANIZATION,"ORG_ID",orgId,"ORG_NO");
						logger.debug("orgId : "+orgId);
						logger.debug("orgNo : "+orgNo);
						cardLink = new CardlinkCustomerDataM();
						cardLink.setPersonalId(personalInfo.getPersonalId());
						cardLink.setOrgId(orgNo);
						String cardlinkCustNo = uniqueIDDAO.getCardLinkNo(cardM);
						logger.debug("cardlinkCustNo : "+cardlinkCustNo);
						cardLink.setCardlinkCustNo(cardlinkCustNo);
						cardLink.setNewCardlinkCustFlag(MConstant.FLAG.YES);				
						personalInfo.addCardLinkCustomer(cardLink);
					}
				}
	    	}
	    	ApplicationUtil.defaultCardLinkCustId(uloApplicationGroup);
	    	CardLinkAction processCardLink = new CardLinkAction();
			processCardLink.processCardlinkAction(uloApplicationGroup);
			
			uloApplicationGroup.setJobState(JOBSTATE_APPROVED);
			uloApplicationGroup.setApplicationStatus(APPLICATION_STATIC_APPROVED);
			uloApplicationGroup.setApplyChannel(CHANNEL_BRANCH);
	    	
	    	//Save applicationGroup data to MLP database
			Context ctx = new InitialContext();
			MLPServiceManagerBean mlpSMB = (MLPServiceManagerBean) ctx.lookup("ejblocal:com.eaf.service.common.mlp.bean.MLPServiceManagerBean");
			mlpSMB.saveApplication(uloApplicationGroup, kbankHeader.getUserId());
	    	
	    	logger.debug("Create applicationGroup completed.");
	    	
	    	PrintWriter pw = resp.getWriter();
	    	responseKbankHeader.setStatusCode(SUCCESS_CODE);
	    	submitApplicationResponse.setKBankHeader(responseKbankHeader);
	    	
			pw.print(gson.toJson(submitApplicationResponse));
			pw.flush();
			pw.close();
    	}
		catch(Exception e)
    	{
    		logger.fatal("ERROR ",e);
    		responseKbankHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
    		responseKbankHeader.error(ServiceResponse.Status.SYSTEM_EXCEPTION, e.getMessage());
			submitApplicationResponse.setKBankHeader(responseKbankHeader);
    		
    		PrintWriter pw = resp.getWriter();
			pw.print(gson.toJson(submitApplicationResponse));
			pw.flush();
			pw.close();
    	}
		
		//Create response log
		try
		{
			String serviceReqRespId = ServiceUtil.generateServiceReqResId();
			ServiceReqRespDataM serviceResp = new ServiceReqRespDataM();
			serviceResp.setReqRespId(serviceReqRespId);
			serviceResp.setTransId(transactionId);
			serviceResp.setRespCode(responseKbankHeader.getStatusCode());
			serviceResp.setRespDesc(null);
			for(KbankError err : responseKbankHeader.getErrorVect())
			{
				serviceResp.setErrorMessage(err.getErrorDesc());
			}
			serviceResp.setServiceId(MLP_APP);
			serviceResp.setActivityType(ServiceConstant.OUT);
			serviceResp.setContentMsg(gson.toJson(submitApplicationResponse));
			serviceResp.setRefCode(appGroupNo);
			serviceResp.setCreateDate(ServiceUtil.getCurrentTimestamp());
			serviceResp.setAppId(appGroupId);
			serviceResp.setCreateBy(userName);
			ServiceFactory.getServiceDAO(ServiceLocator.ORIG_DB).createLog(serviceResp);
		}
		catch(Exception e)
		{
			logger.fatal("ERROR ",e);
		}
	}
}
