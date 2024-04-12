package com.eaf.service.common.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
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
import com.eaf.service.module.manual.CIS0222I01ServiceProxy;
import com.eaf.service.module.model.CIS0222I01RequestDataM;
import com.eaf.service.module.model.CIS0222I01ResponseDataM;
import com.eaf.service.module.model.CISZipCodeDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;


@WebServlet("/CIS0222I01")
public class CIS0222I01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(CIS0222I01.class);
    public CIS0222I01() {
        super();
    }
    public static class filePath{
    	public static final String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CIS0222I01WS.properties";
    	public static final String text = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"addressDescription.txt";
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String processCode = request.getParameter("process");		
		logger.debug("processCode : "+processCode);		
		if("defualt".equals(processCode)){			
			File file = new File((filePath.file));
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fileInput);
			fileInput.close();

			file = new File((filePath.text));
			fileInput = new FileInputStream(file);
			
			//Count Line in addressDescription.txt
			LineNumberReader lineCount = new LineNumberReader(new FileReader(file));
			lineCount.skip(Long.MAX_VALUE);
			lineCount.close();
			
			int row = lineCount.getLineNumber();
			BufferedReader buffer = new BufferedReader(new InputStreamReader(fileInput,"UTF-8"));
			String line=null;
			String[][] value = new String[row][4];
			int i=0;

			//read line and split into value 2d
			while((line=buffer.readLine()) != null){
				value[i++] = line.split("\\|");
			}
			buffer.close();
			
			//pack value into array
			String[] tumbol = new String[row];
			String[] amphur = new String[row];
			String[] province = new String[row];
			String[] zipCode = new String[row];
			for(int j=0;j<row;j++){
				try{
					tumbol[j] = value[j][3];
					amphur[j] = value[j][2];
					province[j] = value[j][1];
					zipCode[j] = value[j][0];
				}catch(Exception e){
					tumbol[j] = "";
					amphur[j] = "";
					province[j] = "";
					zipCode[j] = "";
				}
			}
			
			HashMap<String,Object> data = new HashMap<>();
			data.put(CIS0222I01ServiceProxy.url,prop.getProperty(CIS0222I01ServiceProxy.url));
			data.put(CIS0222I01ServiceProxy.responseConstants.totalResult,
					prop.getProperty(CIS0222I01ServiceProxy.responseConstants.totalResult));
			data.put(CIS0222I01ServiceProxy.responseConstants.addressInfomationList.tumbolDescription,tumbol);
			data.put(CIS0222I01ServiceProxy.responseConstants.addressInfomationList.amphurDescription,amphur);
			data.put(CIS0222I01ServiceProxy.responseConstants.addressInfomationList.provinceDescription,province);
			data.put(CIS0222I01ServiceProxy.responseConstants.addressInfomationList.zipCode,zipCode);
			
			ResponseUtils.sendJsonResponse(response, data);
		}else if("request".equals(processCode)){
			String urlWebService = request.getParameter("urlWebService");
			String tumbolName = request.getParameter("tumbolName");
			String amphurName = request.getParameter("amphurName");
			String provinceName = request.getParameter("provinceName");
			
			CIS0222I01RequestDataM CIS0222I01Request = new CIS0222I01RequestDataM(); 
				CIS0222I01Request.setTumbol(tumbolName);
				CIS0222I01Request.setAmphur(amphurName);
				CIS0222I01Request.setProvince(provinceName);
			
			ServiceRequestDataM CIS0222I01Service = new ServiceRequestDataM();
				CIS0222I01Service.setServiceId(CIS0222I01ServiceProxy.serviceId);
				CIS0222I01Service.setIgnoreServiceLog(true);
				CIS0222I01Service.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				CIS0222I01Service.setEndpointUrl(urlWebService);
				CIS0222I01Service.setObjectData(CIS0222I01Request);
				
			ServiceResponseDataM resp = null;	
			try{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
					resp = proxy.requestService(CIS0222I01Service);
			}catch(Exception e){
				e.printStackTrace();
			}
			CIS0222I01ResponseDataM responseObject = (CIS0222I01ResponseDataM)resp.getObjectData();
			if(null == responseObject){
				responseObject = new CIS0222I01ResponseDataM();
			}
			
			HashMap<String,Object> data = new HashMap<String,Object>();
			Gson gson = new Gson();
			data.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			data.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			data.put(CIS0222I01ServiceProxy.responseConstants.totalResult, responseObject.getTotalResult());
			ArrayList<CISZipCodeDataM> cisZipCodes = responseObject.getCisZipCode();
			if(null == cisZipCodes){
				cisZipCodes = new ArrayList<CISZipCodeDataM>();						
			}
			data.put(CIS0222I01ServiceProxy.responseConstants.addressInfomationList.class.getSimpleName(),cisZipCodes);
			
			ResponseUtils.sendJsonResponse(response, data);
		}else if("save".equals(processCode)){
			String urlWebService = request.getParameter("urlWebServiceEdit");
			
			if(urlWebService==null) urlWebService ="";
			
			File file = new File((filePath.file));
			FileOutputStream fileUpdate = new FileOutputStream(file);
			Properties prop = new Properties();
			prop.setProperty(CIS0222I01ServiceProxy.url, urlWebService);

			prop.store(fileUpdate, "data record in addressDescription.txt");
			if(null != fileUpdate){
				fileUpdate.flush();
				fileUpdate.close();
			}
			System.gc();
			logger.debug("props : "+prop);			
			
		}else if("add".equals(processCode)){
			String tumbolDescription = request.getParameter("tumbolDescriptionEdit");
			String amphurDescription = request.getParameter("amphurDescriptionEdit");
			String provinceDescription = request.getParameter("provinceDescriptionEdit");
			String zipCode = request.getParameter("zipCodeEdit");
			
			String addLine = zipCode+"|"+provinceDescription+"|"+amphurDescription+"|"+tumbolDescription+"\n";
			 
			logger.debug("addLine : "+addLine);
			File file = new File((filePath.text));
 
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile(),true), "UTF-8"));
			bw.write(addLine);
			bw.close();
			
			
		}else if("delete".equals(processCode)){
			String tumbol = request.getParameter("dTumbol");
			String amphur = request.getParameter("dAmphur");
			String province = request.getParameter("dProvince");
			String zipCode = request.getParameter("dZipCode");
			
			String removeLine = zipCode+"|"+province+"|"+amphur+"|"+tumbol;
			logger.debug("Remove Line : "+removeLine);
			File inFile = new File((filePath.text));
		      
		      if (!inFile.isFile()) {
		        logger.debug("Parameter is not an existing file");
		        return;
		      }
		       
		      //Construct the new file that will later be renamed to the original filename. 
		      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
		      
		      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile),"UTF-8"));
		      PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile), "UTF-8"));
		      
		      String line = null;
		 
		      //Read from the original file and write to the new 
		      //unless content matches data to be removed.
		      while ((line = br.readLine()) != null) {
		    	  if (!line.trim().equals(removeLine)) {
		    		  pw.println(line);
		    		  pw.flush();
		    	  }
		      }
		      pw.close();
		      br.close();
		      
		      //Delete the original file
		      if (!inFile.delete()) {
		    	  logger.debug("Could not delete file");
		        return;
		      } 
		      
		      //Rename the new file to the filename the original file had.
		      if (!tempFile.renameTo(inFile)){
		    	  logger.debug("Could not rename file");
		      }
		}else if("update".equals(processCode)){
			String tumbolDescription = request.getParameter("tumbolDescriptionEdit");
			String amphurDescription = request.getParameter("amphurDescriptionEdit");
			String provinceDescription = request.getParameter("provinceDescriptionEdit");
			String zipCode = request.getParameter("zipCodeEdit");
			String row = request.getParameter("rowEdit");
			logger.debug("tumbolDescription : "+tumbolDescription);
			String updateLine = zipCode+"|"+provinceDescription+"|"+amphurDescription+"|"+tumbolDescription;
			logger.debug("updateLine : "+updateLine);
			File inFile = new File((filePath.text));
		      
		      if (!inFile.isFile()) {
		        logger.debug("Parameter is not an existing file");
		        return;
		      }
		       
		      //Construct the new file that will later be renamed to the original filename. 
		      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
		      
		      BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inFile),"UTF-8"));
		      PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile),"UTF-8"));
		      
		      String line = null;
		      int count = 1;
		      //Read from the original file and write to the new 
		      //unless content matches data to be removed.
		      while ((line = br.readLine()) != null) {
		    	  if (count != Integer.parseInt(row)) {
		    		  pw.println(line);
		    		  pw.flush();
		    	  }else if(count == Integer.parseInt(row)){
		    		  pw.println(updateLine);
		    		  pw.flush();
		    	  }
		    	  count++;
		      }
		      pw.close();
		      br.close();
		      
		      //Delete the original file
		      if (!inFile.delete()) {
		    	  logger.debug("Could not delete file");
		        return;
		      } 
		      
		      //Rename the new file to the filename the original file had.
		      if (!tempFile.renameTo(inFile)){
		    	  logger.debug("Could not rename file");
		      }
		}
	}
}
