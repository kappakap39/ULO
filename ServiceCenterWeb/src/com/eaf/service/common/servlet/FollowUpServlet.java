package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.CsvSerializer;
import org.jsefa.csv.config.CsvConfiguration;
import org.jsefa.csv.lowlevel.config.QuoteMode;

import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.pega.FollowUpRequest;
import com.eaf.orig.ulo.model.pega.FollowUpResponse;
import com.eaf.orig.ulo.service.submitapplication.model.CSVContentDataM;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.PegaFollowUpServiceProxy;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;


@WebServlet("/FollowUpServlet")
public class FollowUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(FollowUpServlet.class);	
    public FollowUpServlet() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"FollowUp.properties";
    }    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	logger.debug("FollowUpServlet");
    	String process = req.getParameter("process");
    	try{
	    	if("default".equals(process)){
	    		File file = new File((filePath.file));
				FileInputStream fileInput = new FileInputStream(file);
				Properties properties = new Properties();
				properties.load(fileInput);
				fileInput.close();
				
				HashMap<String,Object> data = new HashMap<>();
				data.put("url", properties.get("urlWebService"));
				ResponseUtils.sendJsonResponse(resp, data);
	    	}else if("request".equals(process)){
	    		String url = req.getParameter("UrlWebService");
	    		String caseId = req.getParameter("CaseID");
	    		String pegaOperatorId = req.getParameter("PegaOperatorID");
	    		String csvContent = req.getParameter("CSVContent");
	    		if(Util.empty(pegaOperatorId)){
	    			pegaOperatorId = SystemConfig.getProperty("PEGA_OPERATOER_ID");
	    		}
	    		logger.debug("pegaOperatorId : "+pegaOperatorId);
	    		
	    		FollowUpRequest followUpRequest = new FollowUpRequest();
//		    		String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
//		    		followUpRequest.setFuncNm(PegaFollowUpServiceProxy.serviceId);
//		    		followUpRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,caseId));
//		    		followUpRequest.setRqDt(ServiceApplicationDate.getTimestamp());
//		    		followUpRequest.setUserId(ServiceCache.getGeneralParam("KBANK_USER_ID"));
//		    		followUpRequest.setRqAppId(RqAppId);
		    		
		    		followUpRequest.setCaseID(caseId);
		    		followUpRequest.setPegaOperatorID(pegaOperatorId);
		    		if(!Util.empty(csvContent)){
		    			followUpRequest.setCSVContent(csvContent);		
		    		}else{
		    			logger.debug("caseId : "+caseId);
		    			try{
		    				String applicationGroupId = ORIGDAOFactory.getApplicationGroupDAO().getApplicationGroupIdByQr2(caseId);
		    				logger.debug("applicationGroupId : "+applicationGroupId);
		    				ApplicationGroupDataM applicationGroup = ORIGDAOFactory.getApplicationGroupDAO().loadOrigApplicationGroupM(applicationGroupId);
		    				if(null == applicationGroup){
		    					applicationGroup = new ApplicationGroupDataM();
		    				}
		    				ArrayList<CSVContentDataM> csvContents = mapDummyCSVContent(applicationGroup);
		    				logger.debug("csvContents : "+csvContents);
		    				followUpRequest.setCSVContent(getCSVContent(csvContents));		
		    			}catch(Exception e){
		    				logger.fatal("ERROR",e);
		    			}
		    		}
	    		ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
	    			serviceRequest.setEndpointUrl(url);
	    			serviceRequest.setServiceId(PegaFollowUpServiceProxy.serviceId);
	    			serviceRequest.setIgnoreServiceLog(true);
					serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
	    			serviceRequest.setUniqueId(followUpRequest.getCaseID());
	    			serviceRequest.setObjectData(followUpRequest);	    			
	    		ServiceCenterProxy proxy = new ServiceCenterProxy();
	    		ServiceResponseDataM serivceResponse = proxy.requestService(serviceRequest);
	    		FollowUpResponse followUpResponse = (FollowUpResponse)serivceResponse.getObjectData();
	    		if(null == followUpResponse){
	    			followUpResponse = new FollowUpResponse();
	    		}
	    		HashMap<String,Object> data = new HashMap<String,Object>();
	    		Gson gson = new Gson();
				data.put("jsonRq", gson.toJson(serivceResponse.getRequestObjectData()));
				data.put("jsonRs", gson.toJson(serivceResponse.getResponseObjectData()));   		
	    		ResponseUtils.sendJsonResponse(resp, data);
	    	}else if("save".equals(process)){
	    		String url = req.getParameter("UrlWebService");
	    		
	    		File file = new File((filePath.file));
				InputStream inStream = new FileInputStream(file);
				PrintWriter writer = new PrintWriter(file);
				Properties prop = new Properties();
				prop.load(inStream);
				inStream.close();
				
				prop.setProperty("urlWebService", url);
				prop.store(writer, null);
				writer.close();
	    	}
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
    }
	private PersonalInfoDataM getApplicationTypePersonalInfo(ApplicationGroupDataM applicationGroup){
		PersonalInfoDataM  personalInfo = new PersonalInfoDataM();
		String APPLICATION_TYPE_ADD_SUP = SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP");
		String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
		String PERSONAL_TYPE_INFO = SystemConstant.getConstant("PERSONAL_TYPE_INFO");	
		if(!Util.empty(applicationGroup)){
			String APPLICATION_TYPE = applicationGroup.getApplicationType();
			if(APPLICATION_TYPE_ADD_SUP.equals(APPLICATION_TYPE)){
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_INFO);
			}else{
				personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
			}	
		}
		return personalInfo;
	}
    private ArrayList<CSVContentDataM> mapDummyCSVContent(ApplicationGroupDataM applicationGroup){
    	String SCENARIO =SystemConstant.getConstant("DEFUALT_DSCENARIO");
    	String ALREADY_FOLLOW=SystemConstant.getConstant("DEFUALT_ALREADY_FOLLOW");
    	String FIELD_ID_CONTACT_TIME = SystemConstant.getConstant("FIELD_ID_CONTACT_TIME");
    	String DUMMY=SystemConstant.getConstant("DUMMY");
    	String ADDRESS_TYPE_CURRENT = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT");
    	String ADDRESS_TYPE_WORK = SystemConstant.getConstant("ADDRESS_TYPE_WORK");
    	String ADDRESS_TYPE_CURRENT_CARDLINK = SystemConstant.getConstant("ADDRESS_TYPE_CURRENT_CARDLINK");
		ArrayList<CSVContentDataM> csvContents = new ArrayList<CSVContentDataM>();
		PersonalInfoDataM  personalInfoApplicant = getApplicationTypePersonalInfo(applicationGroup);	
		if(Util.empty(personalInfoApplicant)) {
			personalInfoApplicant = new PersonalInfoDataM();
		}
		AddressDataM homeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT);
		if(Util.empty(homeAddress)){
			homeAddress = new AddressDataM();
		}
		AddressDataM officeAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_WORK);
		if(Util.empty(officeAddress)){
			officeAddress = new AddressDataM();
		}
		AddressDataM cardLinkAddress = personalInfoApplicant.getAddress(ADDRESS_TYPE_CURRENT_CARDLINK);
		if(Util.empty(cardLinkAddress)){
			cardLinkAddress=  new AddressDataM();
		}
		String customerAvailable = CacheControl.getName(FIELD_ID_CONTACT_TIME, "CHOICE_NO", personalInfoApplicant.getContactTime(), "MAPPING8");
		CSVContentDataM csvContent =  new CSVContentDataM();	
			csvContent.setCaseID(applicationGroup.getApplicationGroupNo());		
			csvContent.setGroup(DUMMY);
			csvContent.setScenario(SCENARIO);
			csvContent.setDocName("");
			csvContent.setMandatory(MConstant.FLAG_O);
			csvContent.setDocReason("");
			csvContent.setReasonDetail("");
			csvContent.setCustAvailableTime(Util.empty(customerAvailable)?"N/A":customerAvailable);
			csvContent.setAlreadyFollow(ALREADY_FOLLOW);
			csvContent.setMobile(personalInfoApplicant.getMobileNo());
			csvContent.setTelHome(homeAddress.getPhone1());
			csvContent.setTelHomeExt(homeAddress.getExt1());
			csvContent.setTelOffice(officeAddress.getPhone1());
			csvContent.setTelOfficeExt(officeAddress.getExt1());
			csvContent.setTelCardLink(cardLinkAddress.getPhone1());
			csvContent.setTelCardLinkExt(cardLinkAddress.getExt1());
			csvContent.setFirstNameTH(personalInfoApplicant.getThFirstName());
			csvContent.setMiddleNameTH(personalInfoApplicant.getThMidName());
			csvContent.setLastNameTH(personalInfoApplicant.getThLastName());
			csvContent.setFirstNameEN(personalInfoApplicant.getEnFirstName());
			csvContent.setMiddleNameEN(personalInfoApplicant.getEnMidName());
			csvContent.setLastNameEN(personalInfoApplicant.getEnLastName());
			csvContent.setPrimarySegment(personalInfoApplicant.getCisPrimSegment());
			csvContent.setPrimarySubSegment(personalInfoApplicant.getCisSecSubSegment());
			csvContent.setSecondarySegment(personalInfoApplicant.getCisSecSegment());
			csvContent.setSecondarySubSegment(personalInfoApplicant.getCisSecSubSegment());
			csvContent.setRMOperatorID("");
			csvContents.add(csvContent);
		return csvContents;
	}
	private String getCSVContent(ArrayList<CSVContentDataM> csvContentDataMList){
		String SUBMIT_APPLICATION_CSV_CONTENT_HEADER = SystemConstant.getConstant("SUBMIT_APPLICATION_CSV_CONTENT_HEADER");
		CsvConfiguration csvConfiguration = new CsvConfiguration();
			csvConfiguration.setFieldDelimiter(',');
			csvConfiguration.setDefaultNoValueString("");
			csvConfiguration.setQuoteCharacter('"');
			csvConfiguration.setDefaultQuoteMode(QuoteMode.ALWAYS);
//			csvConfiguration.getSimpleTypeConverterProvider().registerConverterType(String.class,CsvUniCode.class);
			csvConfiguration.setLineBreak("\n");
		CsvSerializer serializer = (CsvSerializer) CsvIOFactory.createFactory(csvConfiguration, CSVContentDataM.class).createSerializer();
        StringWriter writer = new StringWriter();
        serializer.open(writer);
        serializer.getLowLevelSerializer().writeLine(SUBMIT_APPLICATION_CSV_CONTENT_HEADER); // the header
        if(!Util.empty(csvContentDataMList)){
        	for(CSVContentDataM csvContent : csvContentDataMList){
                serializer.write(csvContent);
        	}
        }
        serializer.close(true);
    	logger.debug("csv.toString().."+writer.toString());
        return writer.toString();
	}
}
