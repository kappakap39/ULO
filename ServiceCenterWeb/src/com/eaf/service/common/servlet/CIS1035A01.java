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
import com.eaf.service.module.manual.CIS1035A01ServiceProxy;
import com.eaf.service.module.model.CIS1035A01RequestDataM;
import com.eaf.service.module.model.CIS1035A01ResponseDataM;
import com.eaf.service.module.model.CISContactDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CIS1035A01")
public class CIS1035A01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static transient Logger logger = Logger.getLogger(CIS1035A01.class);
    public CIS1035A01() {
        super();
    }    
    public static class filePath{
    	public static final String properties = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS1035A01WS.properties";
    	public static final String text = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"customerContact.txt";
    }    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String process = request.getParameter("process");
		logger.debug("process : "+process);
		if(process.equals("defualt")){
			File file = new File((filePath.properties));
			BufferedReader reader = new BufferedReader(new FileReader(file));
			Properties prop = new Properties();
			prop.load(reader);
			reader.close();
			
			file = new File((filePath.text));
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			LineNumberReader lineNumberReader = new LineNumberReader(reader);
			lineNumberReader.skip(Long.MAX_VALUE);
			lineNumberReader.close();
			
			int row = lineNumberReader.getLineNumber();
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			String[][] value = new String[row][8];
			int i = 0;

			while((line = reader.readLine())!=null){
				value[i] = line.split("\\|");
				i++;
			}
			reader.close();
			
			String[] contactId = new String[row];
			String[] contactTp = new String[row];
			String[] contactLoc = new String[row];
			String[] contactNo = new String[row];
			String[] contactEx = new String[row];
			String[] contactSeq = new String[row];
			String[] successFlag = new String[row];
			String[] errDesc = new String[row];
			
			for(int j=0;j<row;j++){
				contactId[j] = value[j][0];
				contactTp[j] = value[j][1];
				contactLoc[j] = value[j][2];
				contactNo[j] = value[j][3];
				contactEx[j] = value[j][4];
				contactSeq[j] = value[j][5];
				successFlag[j] = value[j][6];
				errDesc[j] = value[j][7];
			}
			
			HashMap<String, Object> data = new HashMap<>();
			data.put(CIS1035A01ServiceProxy.url, prop.getProperty(CIS1035A01ServiceProxy.url));
			data.put(CIS1035A01ServiceProxy.responseConstants.cusId, prop.getProperty(CIS1035A01ServiceProxy.responseConstants.cusId));
			
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.contactId, contactId);
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.contactTp, contactTp);
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.contactLoc, contactLoc);
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.contactNo, contactNo);
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.contactEx, contactEx);
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.contactSeq, contactSeq);
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.successFlag, successFlag);
			data.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.errDesc, errDesc);
			
			ResponseUtils.sendJsonResponse(response, data);
		}else if(process.equals("request")){
			String url = request.getParameter("urlWebService_input");
			String userId = request.getParameter("userId_input");
			String hubNo = request.getParameter("hubNo_input");
			String branchNo = request.getParameter("branchNo_input");
			String confirmFlag = request.getParameter("confirmFlag_input");
			String validateFlag = request.getParameter("validateFlag_input");	
			String totalRec = request.getParameter("totalRecord_input");
			String cusType = request.getParameter("customerType_input");
			String cusId = request.getParameter("customerId_input");
			
			//CISContact Array
			String[] typeCode = request.getParameterValues("contact_typeCode");
			String[] locationCode = request.getParameterValues("contact_locationCode");
			String[] contactNo = request.getParameterValues("contact_contactNo");
			String[] contactExtNo = request.getParameterValues("contact_contactExtNo");
			String[] avaTime = request.getParameterValues("contact_avaTime");
			String[] name = request.getParameterValues("contact_name");
			
			CIS1035A01RequestDataM CIS1035A01Request = new CIS1035A01RequestDataM(); 
				CIS1035A01Request.setUserId(userId);
				CIS1035A01Request.setHubNo(hubNo);
				CIS1035A01Request.setBranchNo(branchNo);
				CIS1035A01Request.setConfirmFlag(confirmFlag);
				CIS1035A01Request.setValidateFlag(validateFlag);
				CIS1035A01Request.setTotalRecord(totalRec.equals("") ? 0 : Integer.valueOf(totalRec));
				CIS1035A01Request.setCustomerType(cusType);
				CIS1035A01Request.setCustomerId(cusId);
				
				ArrayList<CISContactDataM> cisContacts = new ArrayList<>();
				for(int i=0;i<typeCode.length;i++){
					CISContactDataM cisContact = new CISContactDataM();
					cisContact.setTypeCode(typeCode[i]);
					cisContact.setLocationCode(locationCode[i]);
					cisContact.setContactNo(contactNo[i]);
					cisContact.setContactExtNo(contactExtNo[i]);
					cisContact.setContactAvailabilityTime(avaTime[i]);
					cisContact.setContactName(name[i]);
					cisContacts.add(cisContact);
				}
				CIS1035A01Request.setCisContact(cisContacts);
			
			ServiceRequestDataM serviceRequest = new ServiceRequestDataM();
				serviceRequest.setEndpointUrl(url);
				serviceRequest.setServiceId(CIS1035A01ServiceProxy.serviceId);
				serviceRequest.setIgnoreServiceLog(true);
				serviceRequest.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				serviceRequest.setObjectData(CIS1035A01Request);
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
			
			CIS1035A01ResponseDataM CIS1035A01Response = (CIS1035A01ResponseDataM)resp.getObjectData();
			if(null == CIS1035A01Response){
				CIS1035A01Response = new CIS1035A01ResponseDataM();
			}
			object.put(CIS1035A01ServiceProxy.responseConstants.cusId, CIS1035A01Response.getCustomerId());
			ArrayList<CISContactDataM> responseCisContacts =  CIS1035A01Response.getCisContact();
			if(null == responseCisContacts){
				responseCisContacts = new ArrayList<CISContactDataM>();
			}
			object.put(CIS1035A01ServiceProxy.responseConstants.CISContactList.class.getSimpleName(),responseCisContacts);
			
			ResponseUtils.sendJsonResponse(response, object);
		}else if(process.equals("save")){
			String url = request.getParameter("urlWebService_edit");
			String customerId = request.getParameter("customerId_edit");
			
			File file = new File((filePath.properties));
			InputStream inStream = new FileInputStream(file);
			PrintWriter writer = new PrintWriter(file);
			Properties prop = new Properties();
			prop.load(inStream);
			prop.setProperty(CIS1035A01ServiceProxy.url, url);
			prop.setProperty(CIS1035A01ServiceProxy.responseConstants.cusId, customerId);
			prop.store(writer, "data record in customerContact.txt");
			
			inStream.close();
			writer.close();
			
		}else if(process.equals("add")){
			String contactId = request.getParameter("contactId_add");
			String contactTp = request.getParameter("contactTp_add");
			String contactLocation = request.getParameter("contactLocation_add");
			String contactNo = request.getParameter("contactNumber_add");
			String contactEx = request.getParameter("contactExtension_add");
			String contactSeq = request.getParameter("contactSequence_add");
			String successFlag = request.getParameter("successFlag_add");
			String errDesc = request.getParameter("errorDescription_add");
			
			String add = contactId+"|"+contactTp+"|"+contactLocation+"|"+contactNo+"|"+contactEx+"|"+contactSeq+"|"+successFlag+"|"+errDesc+"\n";
			
			File file = new File((filePath.text));
			FileWriter writer = new FileWriter(file, true);
			writer.write(add);
			writer.close();
		}else if(process.equals("edit")){
			String row = request.getParameter("row");
			String contactId = request.getParameter("contactId_add");
			String contactTp = request.getParameter("contactTp_add");
			String contactLocation = request.getParameter("contactLocation_add");
			String contactNo = request.getParameter("contactNumber_add");
			String contactEx = request.getParameter("contactExtension_add");
			String contactSeq = request.getParameter("contactSequence_add");
			String successFlag = request.getParameter("successFlag_add");
			String errDesc = request.getParameter("errorDescription_add");
			
			String edit = contactId+"|"+contactTp+"|"+contactLocation+"|"+contactNo+"|"+contactEx+"|"+contactSeq+"|"+successFlag+"|"+errDesc;
			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			PrintWriter writer = new PrintWriter(temp);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			int count = 1;
			while((line = reader.readLine())!=null){
				if(count == Integer.parseInt(row)){
					writer.println(edit);
				}else{
					writer.println(line);
				}
				count++;
			}
			writer.close();
			reader.close();
			
			if(!file.delete()){logger.debug("cannot delete this file"); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file destination"); return;}
			
		}else if(process.equals("delete")){
			String row = request.getParameter("row");
			
			File file = new File((filePath.text));
			File temp = new File(file.getAbsoluteFile()+".tmp");
			PrintWriter writer = new PrintWriter(temp);
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			int count = 1;
			while((line = reader.readLine())!=null){
				if(count == Integer.parseInt(row));
				else{
					writer.println(line);
					writer.flush();
				}
				count++;
			}
			writer.close();
			reader.close();
			
			if(!file.delete()){logger.debug("cannot delete this file"); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file destination"); return;}
			
		}
	}

}
