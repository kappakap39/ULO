package com.ava.workflow;

import javax.xml.ws.Action;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.service.dao.WfApplicationGroupDAO;
import com.eaf.orig.ulo.service.dao.WfDAOFactory;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.model.ServiceLogDataM;
import com.eaf.service.common.proxy.ServiceCenterController;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.common.utils.RsErrorUtil;
import com.eaf.service.module.manual.WFInquiryAppServiceProxy;
import com.eaf.service.module.model.SearchWorkFlowInquiryDataM;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@javax.jws.WebService (endpointInterface="com.ava.workflow.WorkFlowDelegate", targetNamespace="http://workflow.ava.com/", serviceName="WorkFlowService", portName="WorkFlowPort")
public class WorkFlowPortBindingImpl{
	private static transient Logger logger = Logger.getLogger(WorkFlowPortBindingImpl.class);
	String UNMATCHED = WFInquiryAppServiceProxy.errorConstants.unmatched;//00
	String NOTFOUND = WFInquiryAppServiceProxy.errorConstants.notfound;//01
	String ERROR = WFInquiryAppServiceProxy.errorConstants.error;//02
	List<RsError> rsErrorList = new ArrayList<>();
	String errorMsg = "";
	
    public WfInquiryRespM inquiry(WfInquiryReqM arg0) {	
    	WfInquiryRespM resp = new WfInquiryRespM();
    	
    	String serviceReqRespId = ServiceUtil.generateServiceReqResId();
		logger.debug("serviceReqRespId >> "+serviceReqRespId);
		
		KBankRqHeader kBankRqHeader = arg0.getKBankRqHeader();
		String userId = kBankRqHeader.getUserId();
		logger.debug("userId >> "+userId);
		
		ServiceCenterController serviceController = new ServiceCenterController();		
		ServiceLogDataM serviceLogRequest = new ServiceLogDataM();
			serviceLogRequest.setServiceReqRespId(serviceReqRespId);
			serviceLogRequest.setActivityType(ServiceConstant.IN);
			serviceLogRequest.setServiceDataObject(arg0);
			serviceLogRequest.setServiceId(WFInquiryAppServiceProxy.serviceId);
			serviceLogRequest.setUserId(userId);
		serviceController.createLog(serviceLogRequest);
		
    	//Header
		String RsAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
		KBankRsHeader kBankRsHeader = new KBankRsHeader(); 
	    	kBankRsHeader.setFuncNm(kBankRqHeader.getFuncNm());
	    	kBankRsHeader.setRqUID(kBankRqHeader.getRqUID());
	    	kBankRsHeader.setRsAppId(RsAppId);
	    	kBankRsHeader.setRsUID(serviceReqRespId);
	    	kBankRsHeader.setRsDt(ServiceUtil.getXMLGregorianCalendar());
	    	kBankRsHeader.setCorrID(serviceReqRespId);
        try{
        	SearchWorkFlowInquiryDataM searchWorkFlowInquiry = searchWorkFlowInquiry(arg0);
        	List<String> applicationGroupIds = searchWorkFlowInquiry.getApplicationGroupIds();
        	List<ApplicationGroupM> applicationGroupList = new ArrayList<>();
        	logger.debug("isSearchApplicationGroupId >> "+searchWorkFlowInquiry.isSearchApplicationGroupId());
        	if(!Util.empty(applicationGroupIds)){
        		WfApplicationGroupDAO wfApplicationGroupDAO = WfDAOFactory.getApplicationGroupDAO();
	        	for(String applicationGroupId : applicationGroupIds){
	        		ApplicationGroupM applicationGroup = wfApplicationGroupDAO.loadApplicationGroup(applicationGroupId);
	        		if(Util.empty(applicationGroup.getAppNo()))
	        		{
	        			//Attempt to load appGroup data from catalog
	        			applicationGroup = wfApplicationGroupDAO.loadApplicationGroupCat(applicationGroupId);
	        		}
	        		applicationGroupList.add(applicationGroup);
	        	}
        	}else if(searchWorkFlowInquiry.isSearchApplicationGroupId()){
				RsError rsError = new RsError();
        		rsError.setErrorCode(NOTFOUND);
        		rsError.setErrorDesc(MessageErrorUtil.getText(NOTFOUND));
        		RsErrorUtil.setRsErrorDefualt(rsError, kBankRsHeader);
        		rsErrorList.add(rsError);
			}
        	resp.setKBankHeader(kBankRsHeader);
        	resp.setNoRecord(applicationGroupList.size());
        	resp.getApplicationGroupM().addAll(applicationGroupList);
        	resp.setPersonalId(Util.empty(searchWorkFlowInquiry.getPersonalId()) ? "" : searchWorkFlowInquiry.getPersonalId());
        	
        	if(!Util.empty(rsErrorList)){
        		kBankRsHeader.getRsError().addAll(rsErrorList);
        	}
        	
        	if(searchWorkFlowInquiry.isSearchUnmatched()){
        		kBankRsHeader.setStatusCode(ServiceResponse.Status.BUSINESS_EXCEPTION);
        	}else{
        		kBankRsHeader.setStatusCode(ServiceResponse.Status.SUCCESS);
        	}
        }catch(Exception e){
        	logger.fatal("Error ",e);
        	errorMsg = e.getLocalizedMessage();
        	RsError rsError = new RsError();
    		rsError.setErrorCode(ERROR);
    		rsError.setErrorDesc(MessageErrorUtil.getText(ERROR));
    		RsErrorUtil.setRsErrorDefualt(rsError, kBankRsHeader);
    		rsErrorList.add(rsError);
    		kBankRsHeader.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
        	if(!Util.empty(rsErrorList)){
        		kBankRsHeader.getRsError().addAll(rsErrorList);
        	}
        }
//        finally{
//        	try{
//        		wfInquiryReqMToXml(arg0);
//                wfInquiryRespMToXml(resp);
//        	}catch(Exception e){
//        		logger.fatal("error generate xml");
//        	}
//        }
        
        ServiceLogDataM serviceLogResponse = new ServiceLogDataM();
			serviceLogResponse.setServiceReqRespId(serviceReqRespId);
			serviceLogResponse.setActivityType(ServiceConstant.OUT);
			serviceLogResponse.setServiceDataObject(resp);
			serviceLogResponse.setServiceId(WFInquiryAppServiceProxy.serviceId);
			serviceLogResponse.setUserId(userId);
			serviceLogResponse.setRespCode(kBankRsHeader.getStatusCode());
		try{
			Gson gson = new Gson();
			if(!ServiceUtil.empty(kBankRsHeader.getRsError())){
				serviceLogResponse.setRespDesc(gson.toJson(kBankRsHeader.getRsError()));
			}
		}catch(Exception e){
			errorMsg = e.getLocalizedMessage();
			logger.fatal("ERROR",e);
		}
		logger.debug("errorMsg >> "+errorMsg);
		serviceLogResponse.setErrorMessage(errorMsg);
		serviceController.createLog(serviceLogResponse);
        return resp;
    }
    
    public SearchWorkFlowInquiryDataM searchWorkFlowInquiry(WfInquiryReqM request){
    	SearchWorkFlowInquiryDataM searchWorkFlowInquiry = new SearchWorkFlowInquiryDataM(); 
    	try{
    		String cisNo = request.getCisNo();
    		String docNo = request.getDocNo();
    		String docType = request.getDocType();
    		String thFirstName = request.getThFirstName();
    		String thLastName = request.getThLastName();
    		Date dateOfBirth = null;
    		if(!Util.empty(request.getDob())){
    			dateOfBirth = new Date(request.getDob().toGregorianCalendar().getTime().getTime());
    		}
    		WfApplicationGroupDAO wfApplicationGroupDAO = WfDAOFactory.getApplicationGroupDAO();
	    	if(!ServiceUtil.empty(cisNo)){
	    		logger.debug("Search by CIS Customer ID");
	    		searchWorkFlowInquiry = wfApplicationGroupDAO.getApplicationGroupId(cisNo);
	    		if(Util.empty(searchWorkFlowInquiry)){
	    			if(!ServiceUtil.empty(docNo) && !ServiceUtil.empty(docType)){
	    				logger.debug("Not found , then Search by Document ID");
	    				searchWorkFlowInquiry = wfApplicationGroupDAO.getApplicationGroupId(docNo, docType);
	    				if(Util.empty(searchWorkFlowInquiry)){
	    					logger.debug("Not found , then Search by Customer Name");
		    				if(!ServiceUtil.empty(thFirstName) && !ServiceUtil.empty(thLastName)){
		    					searchWorkFlowInquiry = wfApplicationGroupDAO.getApplicationGroupId(thFirstName, thLastName, dateOfBirth);
		    				}
	    				}
	    			}else if(!ServiceUtil.empty(thFirstName) && !ServiceUtil.empty(thLastName)){
	    				logger.debug("Not found , then Search by Customer Name");
	    				searchWorkFlowInquiry = wfApplicationGroupDAO.getApplicationGroupId(thFirstName, thLastName, dateOfBirth);
	    			}
	    		}
	    	}else if(!ServiceUtil.empty(docNo) && !ServiceUtil.empty(docType)){
	    		logger.debug("Search by Document ID");
	    		searchWorkFlowInquiry = wfApplicationGroupDAO.getApplicationGroupId(docNo, docType);
	    		if(Util.empty(searchWorkFlowInquiry)){
	    			logger.debug("Not found , then Search by Customer Name");
		    		if(!ServiceUtil.empty(thFirstName) && !ServiceUtil.empty(thLastName)){
		    			searchWorkFlowInquiry = wfApplicationGroupDAO.getApplicationGroupId(thFirstName, thLastName, dateOfBirth);
					}
	    		}
	    	}else if(!ServiceUtil.empty(thFirstName) && !ServiceUtil.empty(thLastName)){
	    		logger.debug("Search by Customer Name");
	    		searchWorkFlowInquiry = wfApplicationGroupDAO.getApplicationGroupId(thFirstName, thLastName, dateOfBirth);
	    	}else{
	    		searchWorkFlowInquiry.setSearchUnmatched(true);
	    		errorMsg = MessageErrorUtil.getText(UNMATCHED);
        		RsError rsError = new RsError();
	        		rsError.setErrorCode(UNMATCHED);
	        		rsError.setErrorDesc(errorMsg);
        		rsErrorList.add(rsError);
        	}
	    	
	    	//Attempt to look for data in catalog table
	    	if(!searchWorkFlowInquiry.isSearchUnmatched())
	    	{
	    		SearchWorkFlowInquiryDataM searchWorkFlowInquiryCat = wfApplicationGroupDAO.getApplicationGroupIdCat(cisNo, docNo, docType, thFirstName, thLastName, dateOfBirth);
				List<String> appGroupIdCatList = searchWorkFlowInquiryCat.getApplicationGroupIds();
				List<String> appGroupIdList = searchWorkFlowInquiry.getApplicationGroupIds();
				for(String appGroupIdCat : appGroupIdCatList)
				{
					if(!appGroupIdList.contains(appGroupIdCat))
					{
						appGroupIdList.add(appGroupIdCat);
					}
				}
				
				if(Util.empty(searchWorkFlowInquiry.getPersonalId()))
				{
					searchWorkFlowInquiry.setPersonalId(searchWorkFlowInquiryCat.getPersonalId());
				}
				
	    		searchWorkFlowInquiry.setApplicationGroupIds(appGroupIdList);
	    	}
	    	
    	}catch(Exception e){
    		errorMsg = MessageErrorUtil.getText(ERROR);
    		logger.fatal("Error ",e);
    		RsError rsError = new RsError();
	    		rsError.setErrorCode(ERROR);
	    		rsError.setErrorDesc(errorMsg);
    		rsErrorList.add(rsError);
    	}
		return searchWorkFlowInquiry;
    }
    
    private static void wfInquiryReqMToXml(WfInquiryReqM req) throws IOException{
    	logger.debug("generateWFInquiryRequestToXml..");
    	Gson gson = new Gson();
//    	logger.debug("request Application = "+gson.toJson(req));
    	String xml = "";
		String wfInquiryReqPath =  "C:\\Users\\avalant\\Desktop\\WFInquiryRequest.xml";

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfInquiryReqPath),"UTF-8"));
    	try{
    		JAXBContext context = JAXBContext.newInstance(WfInquiryReqM.class);
    		Marshaller marshaller = context.createMarshaller();    		
    		StringWriter sw = new StringWriter();
    		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    		marshaller.marshal(req, sw);
    		xml = sw.toString();
    		out.write(xml);
    		logger.debug("xml : "+xml);
    	}catch(JAXBException e){
    		logger.fatal("ERROR",e);
    	}finally{
    		out.close();
    	}
    }
    
    private static void wfInquiryRespMToXml(WfInquiryRespM resp) throws IOException{
    	logger.debug("generateWFInquiryResponseToXml..");
    	Gson gson = new Gson();
//    	logger.debug("response Application = "+gson.toJson(resp));
    	String xml = "";
		String wfInquiryRespPath =  "C:\\Users\\avalant\\Desktop\\WFInquiryResponse.xml";

		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wfInquiryRespPath),"UTF-8"));
    	try{
    		JAXBContext context = JAXBContext.newInstance(WfInquiryRespM.class);
    		Marshaller marshaller = context.createMarshaller();    		
    		StringWriter sw = new StringWriter();
    		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
    		marshaller.marshal(resp, sw);
    		xml = sw.toString();
    		out.write(xml);
    		logger.debug("xml : "+xml);
    	}catch(JAXBException e){
    		logger.fatal("ERROR",e);
    	}finally{
    		out.close();
    	}
    }   
}