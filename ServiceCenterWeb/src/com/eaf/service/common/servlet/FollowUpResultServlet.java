package com.eaf.service.common.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsefa.common.lowlevel.filter.HeaderAndFooterFilter;
import org.jsefa.csv.CsvIOFactory;
import org.jsefa.csv.CsvSerializer;
import org.jsefa.csv.config.CsvConfiguration;
import org.jsefa.csv.lowlevel.config.QuoteMode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.followup.FollowUpResultRequest;
import com.eaf.orig.ulo.service.followup.result.model.FollowUpCSVContentDataM;
import com.eaf.service.common.api.ServiceCache;
import com.eaf.service.common.constant.ServiceConstant;
import com.eaf.service.common.util.ServiceUtil;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;


@WebServlet("/FollowUpResultServlet")
public class FollowUpResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(FollowUpResultServlet.class);	
    public FollowUpResultServlet() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"FollowUpResult.properties";
    }    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	logger.debug("FollowUpResultServlet");
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
	    		String CaseID = req.getParameter("CaseID");	    		
	    		String FollowUpStatus = req.getParameter("FollowUpStatus");
	    		String BranchCode = req.getParameter("BranchCode");
	    		String RecommenderEmpID = req.getParameter("RecommenderEmpID");
	    		String CustAvailableTime = req.getParameter("CustAvailableTime");
	    		String TelType = req.getParameter("TelType");
	    		String TelNo = req.getParameter("TelNo");
	    		String TelExt = req.getParameter("TelExt");	    		
	    		FollowUpResultRequest followUpResultRequest = new FollowUpResultRequest();
	    			String RqAppId = ServiceCache.getGeneralParam("KBANK_APP_ID");
	    			followUpResultRequest.setFuncNm(ServiceConstant.ServiceId.FollowUpResult);
	    			followUpResultRequest.setRqUID(ServiceUtil.generateRqUID(RqAppId,CaseID));
	    			followUpResultRequest.setRqDt(ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
	    			followUpResultRequest.setUserId(ServiceCache.getGeneralParam("KBANK_USER_ID"));
	    			followUpResultRequest.setRqAppId(RqAppId);
	    			
	    			followUpResultRequest.setCaseID(CaseID);
		    	FollowUpCSVContentDataM followUpCSVContentDataM = new FollowUpCSVContentDataM();
			    	followUpCSVContentDataM.setCaseID(CaseID);
			    	followUpCSVContentDataM.setFollowUpStatus(FollowUpStatus);
			    	followUpCSVContentDataM.setBranchCode(BranchCode);
			    	followUpCSVContentDataM.setRecommenderEmpID(RecommenderEmpID);
			    	followUpCSVContentDataM.setCustAvailableTime(CustAvailableTime);
			    	followUpCSVContentDataM.setTelType(TelType);
			    	followUpCSVContentDataM.setTelNo(TelNo);
			    	followUpCSVContentDataM.setTelExt(TelExt);
		    	String CSV_HEADER ="CaseID,FollowUpStatus,BranchCode,RecommenderEmpID,CustAvailableTime,TelType,TelNo,TelExt";
		    	String csvContent = getCSVContent(followUpCSVContentDataM,CSV_HEADER);
		    	logger.debug("csvContent : "+csvContent);
		    	followUpResultRequest.setCSVContent(csvContent);
		    	/*CSVContentDataM csvContent = new CSVContentDataM();
			    	csvContent.setCaseID(CaseID);
			    	csvContent.setFollowUpStatus(FollowUpStatus);
			    	csvContent.setBranchCode(BranchCode);
			    	csvContent.setRecommenderEmpID(RecommenderEmpID);
			    	csvContent.setCustAvailableTime(CustAvailableTime);
			    	csvContent.setTelType(TelType);
			    	csvContent.setTelNo(TelNo);
			    	csvContent.setTelExt(TelExt);	*/
			    String errorMsg = "";
			    ResponseEntity<String> responseEntity = null;
			    try{
			    	RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory(){
						@Override
						protected void prepareConnection(HttpURLConnection connection, String httpMethod)throws IOException {
					        if(connection instanceof HttpsURLConnection ){
					            ((HttpsURLConnection) connection).setHostnameVerifier(new HostnameVerifier(){									
									@Override
									public boolean verify(String arg0, SSLSession arg1) {
										return true;
									}
								});
					        }
							super.prepareConnection(connection, httpMethod);
						}
					});	
					String endPointUrl = url;
			        logger.info("endPointUrl : "+endPointUrl);	        
			        HttpHeaders httpHeaderReq = new HttpHeaders();
						httpHeaderReq.set("KBank-FuncNm", followUpResultRequest.getFuncNm());
						httpHeaderReq.set("KBank-RqUID", followUpResultRequest.getRqUID());
						httpHeaderReq.set("KBank-RqDt",ServiceUtil.display(ServiceUtil.getCurrentTimestamp(),ServiceUtil.EN,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
						httpHeaderReq.set("KBank-RqAppId", followUpResultRequest.getRqAppId());
						httpHeaderReq.set("KBank-UserId", followUpResultRequest.getUserId());
						httpHeaderReq.set("KBank-TerminalId", followUpResultRequest.getTerminalId());
						httpHeaderReq.set("KBank-UserLangPref", followUpResultRequest.getUserLangPref());
						httpHeaderReq.set("KBank-CorrID", followUpResultRequest.getCorrID());
			    	HttpEntity<FollowUpResultRequest> requestEntity = new HttpEntity<FollowUpResultRequest>(followUpResultRequest,httpHeaderReq);
					responseEntity = restTemplate.exchange(URI.create(endPointUrl),HttpMethod.POST,requestEntity,String.class);
			    }catch(Exception e){
			    	errorMsg = e.getLocalizedMessage();
			    	logger.fatal("ERROR",e);
			    }
			    HttpHeaders httpHeaderResp = responseEntity.getHeaders();
				String statusCode = httpHeaderResp.getFirst("KBank-StatusCode");
				String responseBody = responseEntity.getBody();
				logger.debug("httpHeaderResp >> "+httpHeaderResp);
				logger.debug("StatusCode >> "+statusCode);
				logger.debug("responseBody >> "+responseBody);
				
	    		HashMap<String,Object> data = new HashMap<String,Object>();
	    		Gson gson = new Gson();
	    		data.put("jsonRq", gson.toJson(followUpResultRequest));
	    		HashMap<String,Object> responseObject = new HashMap<>();
	    			responseObject.put("httpHeaderResp", httpHeaderResp);
	    			responseObject.put("responseBody",responseBody);
	    			responseObject.put("StatusCode",statusCode);
	    		data.put("jsonRs",gson.toJson(responseObject));
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
    
    private String getCSVContent(FollowUpCSVContentDataM  followUpCSVContent,String header){
		CsvConfiguration csvConfiguration = new CsvConfiguration();
		csvConfiguration.setFieldDelimiter(',');
		csvConfiguration.setDefaultNoValueString("");
		csvConfiguration.setQuoteCharacter('"');
		csvConfiguration.setDefaultQuoteMode(QuoteMode.ALWAYS);
//		csvConfiguration.getSimpleTypeConverterProvider().registerConverterType(String.class,CsvUniCode.class);
		csvConfiguration.setLineBreak("\n");
	    csvConfiguration.setLineFilter(new HeaderAndFooterFilter(1, false, false));
    	CsvSerializer serializer = (CsvSerializer) CsvIOFactory.createFactory(csvConfiguration,FollowUpCSVContentDataM.class).createSerializer();
        StringWriter writer = new StringWriter();
        serializer.open(writer);
        serializer.getLowLevelSerializer().writeLine(header); // the header
        if(!Util.empty(followUpCSVContent)){
        	logger.debug("CaseID()>>>"+followUpCSVContent.getCaseID());
        	serializer.write(followUpCSVContent);
        }
        return  writer.toString();
    }
}
