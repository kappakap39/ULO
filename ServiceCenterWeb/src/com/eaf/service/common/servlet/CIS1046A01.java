package com.eaf.service.common.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CIS1046A01ServiceProxy;
import com.eaf.service.module.model.CIS1046A01RequestDataM;
import com.eaf.service.module.model.CIS1046A01ResponseDataM;
import com.eaf.service.module.model.CISAddrRelationDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS1046A01")
public class CIS1046A01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CIS1046A01.class);
    public CIS1046A01() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS1046A01WS.properties";
    	public static final String text = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"addressType.txt";
    }    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String processCode = request.getParameter("process");
		logger.debug("processCode >> "+processCode);		
		if("defualt".equals(processCode)){
			File file = new File((filePath.file));
			InputStream input = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(input);
			input.close();
			
			file = new File((filePath.text));
			input = new FileInputStream(file);
			LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(input));
			lineNumberReader.skip(Long.MAX_VALUE);
			
			int lnMax = lineNumberReader.getLineNumber();
			logger.debug("lnMax : "+lnMax);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			String[][] value = new String[lnMax][3];
			int i = 0;
			while((line = reader.readLine())!=null){
				logger.debug("line : "+line);
				value[i] = line.split("\\|");
				i++;
			}
			String[] addrType = new String[lnMax];
			String[] succFlag = new String[lnMax];
			String[] errorDesc = new String[lnMax];
			for(int j=0;j<lnMax;j++){
				addrType[j] = value[j][0];
				succFlag[j] = value[j][1];
				errorDesc[j] = value[j][2];
			}
			input.close();
			reader.close();
			
			HashMap<String,Object> data = new HashMap<>();
			data.put(CIS1046A01ServiceProxy.url,prop.getProperty(CIS1046A01ServiceProxy.url));
			data.put(CIS1046A01ServiceProxy.responseConstants.cusId,prop.getProperty(CIS1046A01ServiceProxy.responseConstants.cusId));
			data.put(CIS1046A01ServiceProxy.responseConstants.addrId,prop.getProperty(CIS1046A01ServiceProxy.responseConstants.addrId));
			data.put(CIS1046A01ServiceProxy.responseConstants.accRefId,prop.getProperty(CIS1046A01ServiceProxy.responseConstants.accRefId));
			data.put(CIS1046A01ServiceProxy.responseConstants.CISAddrRelation.addrType,addrType);
			data.put(CIS1046A01ServiceProxy.responseConstants.CISAddrRelation.succFlag,succFlag);
			data.put(CIS1046A01ServiceProxy.responseConstants.CISAddrRelation.errorDesc,errorDesc);
			
			ResponseUtils.sendJsonResponse(response, data);
		}else if("request".equals(processCode)){
			String url = request.getParameter("urlWebService");
			String userId = request.getParameter("userId");
			String hubNo = request.getParameter("hubNo");
			String branchNo = request.getParameter("branchNo");
			String confirmFlag = request.getParameter("confirmFlag");
			String customerType = request.getParameter("customerType");
			String customerId = request.getParameter("customerId");
			String addressId = request.getParameter("addressId");
			String[] addressType = request.getParameterValues("addressType");
			String accountId = request.getParameter("accountId");
			String accountRefId = request.getParameter("accountRefId");
			String accountLv = request.getParameter("accountLv");
			
			CIS1046A01RequestDataM CIS1046A01Request = new CIS1046A01RequestDataM(); 
				CIS1046A01Request.setUserId(userId);
				CIS1046A01Request.setHubNumber(hubNo);
				CIS1046A01Request.setBranchNo(branchNo);
				CIS1046A01Request.setConfirmFlag(confirmFlag);
				CIS1046A01Request.setCustomerType(customerType);
				CIS1046A01Request.setCustomerId(customerId);
				CIS1046A01Request.setAddressId(addressId);
					ArrayList<CISAddrRelationDataM> CISAddrRelations = new ArrayList<>();
					for(String addrType : addressType){
						CISAddrRelationDataM CISAddrRelation = new CISAddrRelationDataM();
						CISAddrRelation.setAddressType(addrType);
						CISAddrRelations.add(CISAddrRelation);
					}
					CIS1046A01Request.setCisAddrRelations(CISAddrRelations);
				CIS1046A01Request.setAccountId(accountId);
				CIS1046A01Request.setAccountReferenceId(accountRefId);
				CIS1046A01Request.setAccountLevel(accountLv);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS1046A01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1046A01Request);
			ServiceResponseDataM resp = null;	
			try{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
					resp = proxy.requestService(serviceRequest);
			}catch(Exception e){
				e.printStackTrace();
			}
			HashMap<String,Object> data = new HashMap<String,Object>();
			Gson gson = new Gson();
			data.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			data.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			
			CIS1046A01ResponseDataM CIS1046A01Response = (CIS1046A01ResponseDataM)resp.getObjectData(); 
			if(null == CIS1046A01Response){
				CIS1046A01Response = new CIS1046A01ResponseDataM();
			}
			data.put(CIS1046A01ServiceProxy.responseConstants.cusId, CIS1046A01Response.getCustomerId());
			data.put(CIS1046A01ServiceProxy.responseConstants.addrId, CIS1046A01Response.getAddressId());
			ArrayList<CISAddrRelationDataM> responseCISAddrRelations = CIS1046A01Response.getCISAddrRelations();
			if(null == responseCISAddrRelations){
				responseCISAddrRelations = new ArrayList<CISAddrRelationDataM>();
			}
			data.put(CIS1046A01ServiceProxy.responseConstants.CISAddrRelation.class.getSimpleName(),responseCISAddrRelations);
			data.put(CIS1046A01ServiceProxy.responseConstants.accRefId, CIS1046A01Response.getAccountReferenceId());
			
			ResponseUtils.sendJsonResponse(response, data);
		}else if("save".equals(processCode)){
			String url = request.getParameter("urlWebServiceEdit");
			String customerId = request.getParameter("customerIdEdit");
			String addrId = request.getParameter("addressIdEdit");
			String accRefId = request.getParameter("accountRefIdEdit");
			
			File file = new File((filePath.file));
			FileWriter writer = new FileWriter(file);
			Properties prop = new Properties();
			prop.setProperty(CIS1046A01ServiceProxy.url, url);
			prop.setProperty(CIS1046A01ServiceProxy.responseConstants.cusId, customerId);
			prop.setProperty(CIS1046A01ServiceProxy.responseConstants.addrId, addrId);
			prop.setProperty(CIS1046A01ServiceProxy.responseConstants.accRefId, accRefId);
			prop.store(writer, "data record in addressType.txt");
			writer.flush();
			writer.close();
		}else if("add".equals(processCode)){
			String addrType = request.getParameter("addrType_add");
			String succFlag = request.getParameter("succFlag_add");
			String errDesc = request.getParameter("errDesc_add");
			
			String addLine = addrType+"|"+succFlag+"|"+errDesc+"\n";
			File file = new File((filePath.text));
			FileWriter writer = new FileWriter(file, true);
			writer.write(addLine);
			writer.close();
			
		}else if("edit".equals(processCode)){
			String addrType = request.getParameter("addrType_add");
			String succFlag = request.getParameter("succFlag_add");
			String errDesc = request.getParameter("errDesc_add");
			String row = request.getParameter("row");
			
			String update = addrType+"|"+succFlag+"|"+errDesc;
			
			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			PrintWriter writer = new PrintWriter(new FileWriter(temp));
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			int count = 1;
			while((line = reader.readLine())!=null){
				if(count == Integer.parseInt(row)){
					writer.println(update);
					writer.flush();
					logger.debug("update : "+update);
				}else{
					writer.println(line);
					writer.flush();
					logger.debug("line : "+line);
				}
				count++;
			}
			writer.close();
			reader.close();
			
			if(!file.delete()) {logger.debug("file cannot delete"); return;}
			if(!temp.renameTo(file)) {logger.debug("cannot rename to file"); return;}
		}else if("delete".equals(processCode)){
			String row = request.getParameter("row");
			
			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			PrintWriter writer = new PrintWriter(temp);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			int count = 1;
			while((line = reader.readLine())!=null){
				if(count == Integer.parseInt(row)){
					
				}else{
					writer.println(line);
					writer.flush();
				}
				count++;
			}
			reader.close();
			writer.close();
			
			if(!file.delete()){logger.debug("cannot delete file"); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file"); return;}
		}
		
	}

}
