package com.eaf.service.common.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import com.eaf.service.common.util.DataFormat;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CIS1034A01ServiceProxy;
import com.eaf.service.module.model.CIS1034A01RequestDataM;
import com.eaf.service.module.model.CIS1034A01ResponseDataM;
import com.eaf.service.module.model.CISDocInfoDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS1034A01")
public class CIS1034A01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CIS1034A01.class);
    public CIS1034A01() {
        super();
    }    
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS1034A01WS.properties";
    	public static final String text = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"documentInformation.txt";
    }    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.file));
			InputStream inStream = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(inStream);
			inStream.close();
			
			file = new File((filePath.text));
			FileReader reader = new FileReader(file);
			LineNumberReader lineNumberReader = new LineNumberReader(reader);
			lineNumberReader.skip(Long.MAX_VALUE);
			
			int data = lineNumberReader.getLineNumber();
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));			
			HashMap<String, Object> object = new HashMap<>();
			try{
				String line = null;
				String[][] value = new String[data][4];
				int i=0;
				while((line=bufferedReader.readLine())!=null){
					logger.debug("line : "+line);
					try{
					value[i] = line.split("\\|");
					}catch(Exception e){
						logger.fatal("ERROR",e);
					}
					i++;
				}
				String[] docType = new String[data];
				String[] docId = new String[data];
				String[] succFlag = new String[data];
				String[] errDesc = new String[data];
				for(int j=0;j<data;j++){
					docType[j] = value[j][0];
					docId[j] = value[j][1];
					succFlag[j] = value[j][2];
					errDesc[j] = value[j][3];
				}
				object.put(CIS1034A01ServiceProxy.url, prop.getProperty(CIS1034A01ServiceProxy.url));
				object.put(CIS1034A01ServiceProxy.responseConstants.cusId, prop.getProperty(CIS1034A01ServiceProxy.responseConstants.cusId));
				object.put(CIS1034A01ServiceProxy.responseConstants.docInfoList.docType, docType);
				object.put(CIS1034A01ServiceProxy.responseConstants.docInfoList.docNo, docId);
				object.put(CIS1034A01ServiceProxy.responseConstants.docInfoList.succFlag, succFlag);
				object.put(CIS1034A01ServiceProxy.responseConstants.docInfoList.errDesc, errDesc);
			}catch(Exception e){
				logger.fatal("ERROR",e);
			}finally{
				bufferedReader.close();
			}			
			ResponseUtils.sendJsonResponse(response, object);			
		}else if(process.equals("request")){
			String url = request.getParameter("url_input");
			String userId = request.getParameter("userId_input");
			String hubNo = request.getParameter("hubNo_input");
			String brNo = request.getParameter("branchNo_input");
			String confirmFlag = request.getParameter("confirmFlag_input");
			String validateFlag = request.getParameter("validateFlag_input");
			String totalRecord = request.getParameter("totalRecord_input");
			String cusType = request.getParameter("cusType_input");
			String cusId = request.getParameter("cusId_input");
			
			String[] docNo = request.getParameterValues("documentNo_input");
			String[] docType = request.getParameterValues("documentType_input");
			String[] placeIssue = request.getParameterValues("placeIssue_input");
			String[] issueDate = request.getParameterValues("issueDate_input");
			String[] expiryDate = request.getParameterValues("expiryDate_input");
			
			CIS1034A01RequestDataM CIS1034A01Request = new CIS1034A01RequestDataM();
				CIS1034A01Request.setUserId(userId);
				CIS1034A01Request.setHubNo(hubNo);
				CIS1034A01Request.setBranchNo(brNo);
				CIS1034A01Request.setConfirmFlag(confirmFlag);
				CIS1034A01Request.setValidateFlag(validateFlag);
				CIS1034A01Request.setTotalRecord(totalRecord.equals("") ? 0 : Integer.valueOf(totalRecord));
				CIS1034A01Request.setCustomerType(cusType);
				CIS1034A01Request.setCustomerId(cusId);
				ArrayList<CISDocInfoDataM> cisDocInfos = new ArrayList<>();
				try{
					for(int i=0;i<docNo.length;i++){
						try{
							CISDocInfoDataM cisDocInfo = new CISDocInfoDataM();
							cisDocInfo.setDocumentNo(docNo[i]);
							cisDocInfo.setDocumentType(docType[i]);
							cisDocInfo.setPlaceIssue(placeIssue[i]);
							cisDocInfo.setIssueDate(DataFormat.stringToDate(issueDate[i]));
							cisDocInfo.setExpiryDate(DataFormat.stringToDate(expiryDate[i]));
							cisDocInfos.add(cisDocInfo);
						}catch(Exception e){
							logger.fatal("ERROR",e);
						}
					}
				}catch(Exception e){
					logger.fatal("ERROR",e);
				}
				CIS1034A01Request.setCisDocInfo(cisDocInfos);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS1034A01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1034A01Request);
			ServiceResponseDataM resp = null;	
			try{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
					resp = proxy.requestService(serviceRequest);
			}catch(Exception e){
				e.printStackTrace();
			}
			HashMap<String,Object> object = new HashMap<String,Object>();
			Gson gson = new Gson();
			object.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			object.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			
			CIS1034A01ResponseDataM CIS1034A01Response = (CIS1034A01ResponseDataM)resp.getObjectData();
			if(null == CIS1034A01Response){
				CIS1034A01Response = new CIS1034A01ResponseDataM();
			}
			ArrayList<CISDocInfoDataM> responseCisDocInfos = CIS1034A01Response.getCisDocInfo();
			if(null == responseCisDocInfos){
				responseCisDocInfos = new ArrayList<CISDocInfoDataM>();
			}
			object.put(CIS1034A01ServiceProxy.responseConstants.cusId,CIS1034A01Response.getCustomerId());
			object.put(CIS1034A01ServiceProxy.responseConstants.docInfoList.class.getSimpleName(),responseCisDocInfos);
			ResponseUtils.sendJsonResponse(response, object);
			
		}else if(process.equals("save")){
			String url = request.getParameter("url_setting");
			String cusId = request.getParameter("cusId_setting");
			
			File file = new File((filePath.file));
			InputStream inStream = new FileInputStream(file);
			PrintWriter writer = new PrintWriter(file);
			Properties prop = new Properties();
			prop.load(inStream);
			inStream.close();
			
			prop.setProperty(CIS1034A01ServiceProxy.url, url);
			prop.setProperty(CIS1034A01ServiceProxy.responseConstants.cusId, cusId);
			prop.store(writer, "data record in documentInformation.txt");
			writer.close();
			
		}else if(process.equals("add")){
			String docType = request.getParameter("docType_add");
			String docNo = request.getParameter("docNo_add");
			String succFlag = request.getParameter("succFlag_add");
			String errDesc = request.getParameter("errDesc_add");
			
			String addLine = docType+","+docNo+","+succFlag+","+errDesc+"\n";
			File file = new File((filePath.text));
			FileWriter writer = new FileWriter(file, true);
			writer.write(addLine);
			writer.close();
			
		}else if(process.equals("edit")){
			String docType = request.getParameter("docType_add");
			String docNo = request.getParameter("docNo_add");
			String succFlag = request.getParameter("succFlag_add");
			String errDesc = request.getParameter("errDesc_add");
			String row = request.getParameter("row_hidden");
			
			String editLine = docType+"|"+docNo+"|"+succFlag+"|"+errDesc;
			logger.debug("edit line : "+editLine);
			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(temp);
			
			String line = null;
			int rowMatching = 1;
			while((line = reader.readLine())!=null){
				if(rowMatching == Integer.parseInt(row)){
					logger.debug("row edit : "+editLine);
					writer.println(editLine);
				}else{
					logger.debug("row : "+line);
					writer.println(line);
				}
				rowMatching++;
			}
			writer.close();
			reader.close();
			System.gc();
			if(!file.delete()){logger.debug("cannot delete this file."); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file."); return;}
		}else if(process.equals("delete")){
			String row = request.getParameter("row");
			logger.debug("row : "+row);
			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			
			BufferedReader reader = new BufferedReader(new FileReader(file));
			PrintWriter writer = new PrintWriter(temp);
			
			String line = null;
			int rowMatching = 1;
			while((line = reader.readLine())!=null){
				if(rowMatching == Integer.parseInt(row)){
				}else{
					logger.debug("row : "+line);
					writer.println(line);
				}
				rowMatching++;
			}
			writer.close();
			reader.close();
			System.gc();
			if(!file.delete()){logger.debug("cannot delete this file."); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file."); return;}
		}
	}

}
