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
import org.json.JSONObject;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.service.common.model.ServiceRequestDataM;
import com.eaf.service.common.model.ServiceResponseDataM;
import com.eaf.service.common.proxy.ServiceCenterProxy;
import com.eaf.service.common.utils.ResponseUtils;
import com.eaf.service.module.manual.CBS1215I01ServiceProxy;
import com.eaf.service.module.model.CBS1215I01RequestDataM;
import com.eaf.service.module.model.CBS1215I01ResponseDataM;
import com.eaf.service.module.model.CBSRetentionDataM;
import com.eaf.ulo.cache.constant.CacheConstant;
import com.eaf.ulo.cache.controller.CacheController;
import com.google.gson.Gson;

@WebServlet("/CBS1215I01")
public class CBS1215I01 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger =Logger.getLogger(CBS1215I01.class);
	
    public CBS1215I01() {
        super();
    }
    public static class filePath{
    	public static String file = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"CBS1215I01WS.properties";
    	public static String text = CacheController.getCacheData(CacheConstant.CacheName.SIMULATE_CONFIG,"SIMULATE_CONFIG_PATH")
    			+"properties"+File.separator+"account.txt";
    }
    
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String processCode = request.getParameter("process");
		logger.debug("processCode >> "+processCode);
		if("defualt".equals(processCode)){
			File file = new File(filePath.file);
			FileInputStream fileInput = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fileInput);
			fileInput.close();
			
			logger.debug("moreDataInd : "+prop.getProperty(CBS1215I01ServiceProxy.responseConstant.moreDataInd));
			HashMap<String,Object> data = new HashMap<String,Object>();
			data.put(CBS1215I01ServiceProxy.url, prop.getProperty(CBS1215I01ServiceProxy.url));
			data.put(CBS1215I01ServiceProxy.responseConstant.account, prop.getProperty(CBS1215I01ServiceProxy.responseConstant.account));
			data.put(CBS1215I01ServiceProxy.responseConstant.totalRetentionAmount, prop.getProperty(CBS1215I01ServiceProxy.responseConstant.totalRetentionAmount));
			data.put(CBS1215I01ServiceProxy.responseConstant.moreDataInd, prop.getProperty(CBS1215I01ServiceProxy.responseConstant.moreDataInd));
			data.put(CBS1215I01ServiceProxy.responseConstant.numberRecord, prop.getProperty(CBS1215I01ServiceProxy.responseConstant.numberRecord));
			data.put(CBS1215I01ServiceProxy.responseConstant.totalRecord, prop.getProperty(CBS1215I01ServiceProxy.responseConstant.totalRecord));
			
			file = new File(filePath.text);
			LineNumberReader countLine = new LineNumberReader(new FileReader(file));
			countLine.skip(Long.MAX_VALUE);
			countLine.close();
			
			int count = countLine.getLineNumber();
			logger.debug("count : "+count);
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			String line = null;
			String[][] value = new String[count][27];
			int i = 0;
			while((line = buffer.readLine())!=null){
				value[i] = line.split("\\|");
				i++;
				logger.debug("line : "+line);
			}
			buffer.close();
			
			String[] rententionType = new String[count];
			String[] rententionTypeDesc = new String[count];
			for(int j=0;j<count;j++){
				rententionType[j] = value[j][20];
				rententionTypeDesc[j] = value[j][21];
			}
			Gson gson = new Gson();
			logger.debug("rentention type(gson): "+gson.toJson(rententionType));
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionType,rententionType);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionTypeDesc,rententionTypeDesc);
			ResponseUtils.sendJsonResponse(response, data);
			
		}else if("request".equals(processCode)){
			String url = request.getParameter("urlWebService");
			String account = request.getParameter("account");
			String retentionSituationCode = request.getParameter("retentionSituationCode");
			String retentionType = request.getParameter("retentionType");
			String subAccountNum = request.getParameter("subAccountNumber");
			String maxRow = request.getParameter("maxRow");
			String startIndex = request.getParameter("startIndex");
			
			CBS1215I01RequestDataM CBS1215I01Request = new CBS1215I01RequestDataM();
				CBS1215I01Request.setAccountId(account);
				CBS1215I01Request.setRententionSituationCode(retentionSituationCode);
				CBS1215I01Request.setRententionType(retentionType);
				CBS1215I01Request.setSubAccountNumber(subAccountNum);
				CBS1215I01Request.setMaxRow(maxRow.equals("") ? 0 : Integer.valueOf(maxRow));
				CBS1215I01Request.setStartIndex(startIndex.equals("") ? 0 : Integer.valueOf(startIndex));
				
			ServiceRequestDataM service = new ServiceRequestDataM();
				service.setEndpointUrl(url);
				service.setIgnoreServiceLog(true);
				service.setServiceId(CBS1215I01ServiceProxy.serviceId);
				service.setUserId(SystemConstant.getConstant("SYSTEM_USER"));
				service.setObjectData(CBS1215I01Request);

			ServiceResponseDataM resp = null;	
			try{
				ServiceCenterProxy proxy = new ServiceCenterProxy();
					resp = proxy.requestService(service);
			}catch(Exception e){
				e.printStackTrace();
			}
			CBS1215I01ResponseDataM responseObject = (CBS1215I01ResponseDataM)resp.getObjectData();
			if(null == responseObject){
				responseObject = new CBS1215I01ResponseDataM();
			}
			
			HashMap<String,Object> data = new HashMap<String,Object>();			
			Gson gson = new Gson();
			data.put("jsonRq", gson.toJson(resp.getRequestObjectData()));
			data.put("jsonRs", gson.toJson(resp.getResponseObjectData()));
			data.put(CBS1215I01ServiceProxy.responseConstant.account, responseObject.getAccountId());
			ArrayList<CBSRetentionDataM> cbsRetentions = responseObject.getCbsRetentions();
			if(null == cbsRetentions){
				cbsRetentions = new ArrayList<CBSRetentionDataM>();
			}
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.class.getSimpleName(),cbsRetentions);
			data.put(CBS1215I01ServiceProxy.responseConstant.totalRetentionAmount, responseObject.getTotalRetentionAmount());
			data.put(CBS1215I01ServiceProxy.responseConstant.moreDataInd, responseObject.getMoreDataInd());
			data.put(CBS1215I01ServiceProxy.responseConstant.numberRecord, responseObject.getNumberRecord());
			data.put(CBS1215I01ServiceProxy.responseConstant.totalRecord, responseObject.getTotalRecord());
			
			ResponseUtils.sendJsonResponse(response, data);
		}else if("viewDetail".equals(processCode)){
			String retentionType = request.getParameter("retentionType");
			String retentionTypeDescription = request.getParameter("retentionTypeDescription");
			String row = request.getParameter("row");
			logger.debug("check row :"+row);
			logger.debug("retentionType : "+retentionType);
			logger.debug("retentionTypeDescription : "+retentionTypeDescription);
			
			File file = new File(filePath.text);
			BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			int count = 1;
			String line = null;
			String[] value = new String[27];
			if(row==null){
				while((line = buffer.readLine())!=null){
					logger.debug("read line : "+line);
					if(line.matches("(.*)"+retentionType+"(.*)") && line.replaceAll("\\(", "").replaceAll("\\)", "").matches("(.*)"+retentionTypeDescription.replaceAll("\\(", "").replaceAll("\\)", "")+"(.*)")){
						logger.debug("match line : "+line);
						value = line.split("\\|");
					}
				}
			}else{
				while((line = buffer.readLine())!=null){
					if(count == Integer.parseInt(row)){
						logger.debug("read line : "+line);
						value = line.split("\\|");
					}
					count++;
				}
			}
			buffer.close();
			HashMap<String,Object> data = new HashMap<String,Object>();
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.cancelAuthUserId, value[0]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.cancelBranchDesc, value[1]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.cancelBranchId, value[2]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.cancelRententionReason, value[3]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.cancelUserId, value[4]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.concept, value[5]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.createAuthUserId, value[6]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.currencyCode, value[7]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.entryOrigin, value[8]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.indexSequence, value[9]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionAmount, value[10]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionCancelDate, value[11]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionCancelTime, value[12]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionCreateTime, value[13]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionCreateDate, value[14]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionIndicator, value[15]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionIndicatorDesc, value[16]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionMaturityDate, value[17]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionNumber, value[18]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionSituationCode, value[19]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionType, value[20]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.retentionTypeDesc, value[21]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.subAccountNum, value[22]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.transactionDate, value[23]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.transactionBranchId, value[24]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.transactionBranchName, value[25]);
			data.put(CBS1215I01ServiceProxy.responseConstant.retentionInfomantionList.userId, value[26]);

			ResponseUtils.sendJsonResponse(response, data);

		}else if("addDetail".equals(processCode)){
			String cancelAuthId = request.getParameter("cancelAuthId");
			String cancelBranchDesc = request.getParameter("cancelBranchDesc");
			String cancelBranchId = request.getParameter("cancelBranchId");
			String cancelReteReason = request.getParameter("cancelReteReason");
			String cancelUserId = request.getParameter("cancelUserId");
			String concept = request.getParameter("concept");
			String createAuthId = request.getParameter("createAuthId");
			String curCode = request.getParameter("curCode");
			String entryOrigin = request.getParameter("entryOrigin");
			String indexSeq = request.getParameter("indexSeq");
			String reteAmt = request.getParameter("reteAmt");
			String reteCancelDate = request.getParameter("reteCancelDate");
			String reteCancelTime = request.getParameter("reteCancelTime");
			String reteCreateTime = request.getParameter("reteCreateTime");
			String reteCreateDate = request.getParameter("reteCreateDate");
			String reteInd = request.getParameter("reteInd");
			String reteIndDesc = request.getParameter("reteIndDesc");
			String reteMaturityDate = request.getParameter("reteMaturityDate");
			String reteNum = request.getParameter("reteNum");
			String reteSitnCode = request.getParameter("reteSitnCode");
			String reteType = request.getParameter("reteType");
			String reteTypeDesc = request.getParameter("reteTypeDesc");
			String subAccNum = request.getParameter("subAccNum");
			String tranDate = request.getParameter("tranDate");
			String tranBranchId = request.getParameter("tranBranchId");
			String tranBranchName = request.getParameter("tranBranchName");
			String userId = request.getParameter("userId");
			
			String addLine = cancelAuthId+"|"+cancelBranchDesc+"|"+cancelBranchId+"|"+cancelReteReason+"|"+cancelUserId+"|";
			addLine += concept+"|"+createAuthId+"|"+curCode+"|"+entryOrigin+"|"+indexSeq+"|";
			addLine += reteAmt+"|"+reteCancelDate+"|"+reteCancelTime+"|"+reteCreateTime+"|"+reteCreateDate+"|";
			addLine += reteInd+"|"+reteIndDesc+"|"+reteMaturityDate+"|"+reteNum+"|"+reteSitnCode+"|";
			addLine += reteType+"|"+reteTypeDesc+"|"+subAccNum+"|"+tranDate+"|"+tranBranchId+"|";
			addLine += tranBranchName+"|"+userId;
			
			logger.debug("addLine : "+addLine);
			
			File file = new File(filePath.text);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
			bw.write(addLine);
			bw.close();
		}else if("update".equals(processCode)){
			String cancelAuthId = request.getParameter("cancelAuthId");
			String cancelBranchDesc = request.getParameter("cancelBranchDesc");
			String cancelBranchId = request.getParameter("cancelBranchId");
			String cancelReteReason = request.getParameter("cancelReteReason");
			String cancelUserId = request.getParameter("cancelUserId");
			String concept = request.getParameter("concept");
			String createAuthId = request.getParameter("createAuthId");
			String curCode = request.getParameter("curCode");
			String entryOrigin = request.getParameter("entryOrigin");
			String indexSeq = request.getParameter("indexSeq");
			String reteAmt = request.getParameter("reteAmt");
			String reteCancelDate = request.getParameter("reteCancelDate");
			String reteCancelTime = request.getParameter("reteCancelTime");
			String reteCreateTime = request.getParameter("reteCreateTime");
			String reteCreateDate = request.getParameter("reteCreateDate");
			String reteInd = request.getParameter("reteInd");
			String reteIndDesc = request.getParameter("reteIndDesc");
			String reteMaturityDate = request.getParameter("reteMaturityDate");
			String reteNum = request.getParameter("reteNum");
			String reteSitnCode = request.getParameter("reteSitnCode");
			String reteType = request.getParameter("reteType");
			String reteTypeDesc = request.getParameter("reteTypeDesc");
			String subAccNum = request.getParameter("subAccNum");
			String tranDate = request.getParameter("tranDate");
			String tranBranchId = request.getParameter("tranBranchId");
			String tranBranchName = request.getParameter("tranBranchName");
			String userId = request.getParameter("userId");
			String row = request.getParameter("row");
			
			String update = cancelAuthId+"|"+cancelBranchDesc+"|"+cancelBranchId+"|"+cancelReteReason+"|"+cancelUserId+"|";
			update += concept+"|"+createAuthId+"|"+curCode+"|"+entryOrigin+"|"+indexSeq+"|";
			update += reteAmt+"|"+reteCancelDate+"|"+reteCancelTime+"|"+reteCreateTime+"|"+reteCreateDate+"|";
			update += reteInd+"|"+reteIndDesc+"|"+reteMaturityDate+"|"+reteNum+"|"+reteSitnCode+"|";
			update += reteType+"|"+reteTypeDesc+"|"+subAccNum+"|"+tranDate+"|"+tranBranchId+"|";
			update += tranBranchName+"|"+userId;
			
			try{
				File file = new File(filePath.text);
				if(!file.isFile()){
					logger.debug("Parameter is not an existing file");
					return;
				}
					
				File tempFile = new File(file.getAbsolutePath()+".tmp");
				BufferedReader br = new BufferedReader(new FileReader(file));
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile, true), "UTF-8"));
				
				String line = null;
				int count = 1;
				while((line = br.readLine())!=null){
					if(count==Integer.parseInt(row)){
						pw.println(update);
						pw.flush();
						logger.debug("update "+update);
					}else{
						pw.println(line);
						pw.flush();
						logger.debug("line "+line);
					}
					count++;
				}
				pw.close();
				br.close();
				System.gc();
				if(!file.delete()){
					logger.debug("Could not delete file");
					return;
				}
				if(!tempFile.renameTo(file)){
					logger.debug("Could not rename file");
				}
			}catch(Exception e){
				logger.debug("Error "+e);
			}
			JSONObject jsonObject = new JSONObject();
			
		}else if("save".equals(processCode)){
			String url = request.getParameter("urlWebServiceEdit");
			String account = request.getParameter("accountEdit");
			String moreDataInd = request.getParameter("moreDataIndicatorEdit");
			
			File file = new File(filePath.file);
			FileOutputStream outStream = new FileOutputStream(file);
			Properties prop = new Properties();
			
			prop.setProperty(CBS1215I01ServiceProxy.url, url);
			prop.setProperty(CBS1215I01ServiceProxy.responseConstant.account, account);
			prop.setProperty(CBS1215I01ServiceProxy.responseConstant.moreDataInd, moreDataInd);
			prop.store(outStream, "data record in account.txt");
			outStream.flush();
			outStream.close();

		}else if("delete".equals(processCode)){
			String row = request.getParameter("row");
			
			File file = new File(filePath.text);
			File temp = new File(file.getAbsoluteFile()+".tmp");
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), "UTF-8"));
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
			
			if(!file.delete()){logger.debug("cannot delete this file"); return;}
			if(!temp.renameTo(file)){logger.debug("cannot rename to file"); return;}
		}
	}

}
