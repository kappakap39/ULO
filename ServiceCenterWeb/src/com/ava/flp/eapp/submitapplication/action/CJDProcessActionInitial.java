package com.ava.flp.eapp.submitapplication.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

import org.apache.log4j.Logger;

import com.ava.flp.eapp.submitapplication.model.ESubmitApplicationObject;
import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.performance.TraceController;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.controller.eapp.action.EAppAction;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

public class CJDProcessActionInitial extends ProcessActionHelper {

	private Logger logger = Logger.getLogger(CJDProcessActionInitial.class);
	int SUBMIT_IA_BLOCK_TIME = SystemConstant.getIntConstant("SUBMIT_IA_BLOCK_TIME");
	
	@Override
	public Object processAction() 
	{
		String CJD_SAVE_APPLICATION_URL = SystemConfig.getProperty("CJD_SAVE_APPLICATION_URL");
		
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setStatusCode(ServiceResponse.Status.SUCCESS);
		try{
			ESubmitApplicationObject submitEAppObject = (ESubmitApplicationObject)objectForm;
			ApplicationGroupDataM uloApplicationGroup = (ApplicationGroupDataM) submitEAppObject.getApplicationGroup();
			String userId = submitEAppObject.getUserId();
			
			String transactionId = uloApplicationGroup.getTransactionId();
			TraceController trace = new TraceController(this.getClass().getName(),transactionId);
			logger.debug("CJDProcessActionInitial for " + uloApplicationGroup.getApplicationGroupNo());
			
			trace.create("CJDProcessActionInitial");
			
			//Call MLPWeb CJDSaveApplicationServlet (Save CJD Application to MLP_APP)
			URL url = new URL(CJD_SAVE_APPLICATION_URL);
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			
			Gson gson = new Gson();
			String appGroupStr = gson.toJson(uloApplicationGroup);
			byte[] out = appGroupStr.getBytes(StandardCharsets.UTF_8);
			int length = out.length;
			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream())
			{
			    os.write(out, 0, out.length);
			}
			
			BufferedReader br = null;
			int statusCode = http.getResponseCode();
			if (200 <= http.getResponseCode() && http.getResponseCode() <= 299) 
			{
			    br = new BufferedReader(new InputStreamReader(http.getInputStream(),"UTF-8"));
			    String responseBodyString = IOUtils.toString(br);
				processResponse = gson.fromJson(responseBodyString, ProcessResponse.class);
				logger.debug("processResponse statusCode : " + processResponse.getStatusCode());
			}
			else 
			{
			    //br = new BufferedReader(new InputStreamReader(http.getErrorStream()));
			    throw new Exception("CJDSaveApplicationServlet fail with HttpStatus : " + statusCode);
			}
			
			http.disconnect();
			
			logger.debug("CJD Initial Process Done.");
			
			trace.end("CJDProcessActionInitial");
			trace.trace();
		}
		catch(Exception e){
			logger.fatal("ERROR",e);
			processResponse.setStatusCode(ServiceResponse.Status.SYSTEM_EXCEPTION);
			processResponse.setErrorData(EAppAction.error(e));
		}
		return processResponse;
	}
}
