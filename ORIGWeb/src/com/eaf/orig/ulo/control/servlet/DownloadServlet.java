package com.eaf.orig.ulo.control.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.DownloadControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.download.DownLoadM;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@WebServlet("/DownloadServlet")
@MultipartConfig
public class DownloadServlet extends HttpServlet  implements javax.servlet.Servlet  {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(DownloadServlet.class);	
    public DownloadServlet() {
        super();
    }
    private static HashMap<String,String> importControl = null;
    public synchronized void create(){
    	importControl = new HashMap<String, String>();
    	importControl.put(MConstant.DOWNLOAD.DOWNLOAD_ATTACH_FILE,"com.eaf.orig.ulo.control.download.DownloadAttachmentSubForm");
    	importControl.put(MConstant.DOWNLOAD.DOWNLOAD_OLD_ATTACH_FILE,"com.eaf.orig.ulo.control.download.DownloadOldAttachmentSubForm");
    	importControl.put(MConstant.DOWNLOAD.DOWNLOAD_EXCEL,"com.eaf.orig.ulo.control.download.DownloadExcel");
    	importControl.put(MConstant.DOWNLOAD.DOWNLOAD_LINK_REPORT,"com.eaf.orig.ulo.control.download.DownloadLinkReport");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_SOLO_FILE,"com.eaf.mf.control.downloads.DownloadSOLOFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_CAP_PORT,"com.eaf.mf.control.downloads.DownloadCapportFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_CO_BRAND_CUSTOMER,"com.eaf.mf.control.downloads.DownloadCoBrandFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_BILLING_CYCLE,"com.eaf.mf.control.downloads.DownloadBillingCycleFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_KBANK_SALARY,"com.eaf.mf.control.downloads.DownloadKbankSalaryFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_THAIBEV_PARTNER,"com.eaf.mf.control.downloads.DownloadThaibevPartnerFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_CARD_HIERACHY,"com.eaf.mf.control.downloads.DownloadCardHierarchyFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_COA_MAPPING,"com.eaf.mf.control.downloads.DownloadCOAMappingFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_CARD_TRUSTED_COMPANY,"com.eaf.mf.control.downloads.DownloadTrustedCompanyFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_FICS_MAPPING,"com.eaf.mf.control.downloads.DownloadUploadFICSMappingFile");	
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_COMPANY_GROUP,"com.eaf.mf.control.downloads.DownloadCompanyGroupFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_USER,"com.eaf.orig.ulo.control.download.DownloadUser");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_PTT_BLUE_CARD,"com.eaf.orig.ulo.control.download.DownloadPTTBlueCard");
    	
       	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_LOAN_XREF,"com.eaf.mf.control.downloads.DownloadLoanXRefFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_LOAN_INT_TYPE_XREF,"com.eaf.orig.ulo.control.download.DownloadLoanIntTypeXRefFile");
    	importControl.put(MConstant.DOWNLOAD_INVALID_FILE_MANUAL.DOWNLOAD_INVALID_PRICING,"com.eaf.orig.ulo.control.download.DownloadPricingFile");
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String DOWNLOAD_ID = request.getParameter("DOWNLOAD_ID");
		logger.debug(">> DOWNLOAD_ID>>"+ DOWNLOAD_ID);
    	try{
    		if(null == importControl){
    			importControl = new HashMap<String, String>();
    			create();
    		}
    		String className = importControl.get(DOWNLOAD_ID);
    		logger.debug("className >> "+className);
    		if(!Util.empty(className)){
    			DownloadControl control = null; 		        			 
    		    try{			        		    	
    		    	control = (DownloadControl)Class.forName(className).newInstance();			        		    	
    			} catch (Exception e) {
    				logger.fatal("ERROR ",e);
    			}	
    		    if(null != control){
    		    	if(control.requiredEvent()){
    		    		control.processEvent(request,response);
    		    	}else{
		    		   
		    		    ProcessResponse responseData  = control.processDownload(request);   
		    		    if(ServiceResponse.Status.SUCCESS.equals(responseData.getStatusCode())){
		    		    	DownLoadM download = new Gson().fromJson(responseData.getData(),DownLoadM.class);
			    		    response.setDateHeader("Last-Modified", System.currentTimeMillis());
							response.setDateHeader("Expires", 0);
							response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode(download.getFileName(),"UTF-8"));
							response.setContentType(download.getMimeType());				 
							String downloadFilePath = download.getFilePath()+download.getFileName();
							logger.debug("downloadFilePath >> " + downloadFilePath);						
							File downloadFile = new File(downloadFilePath);
							OutputStream output = response.getOutputStream();
							FileInputStream input = new FileInputStream(downloadFile);
			                try{
			                	 int bytes;
			                     while ((bytes = input.read()) != -1) {
			                         output.write(bytes);
			                     }
			                }catch(Exception e){
			                  logger.fatal("ERROR",e);
			                }finally{
			                    if (output != null) output.close();
			                    if (input != null) input.close();
			                }
		    		    }  
    		    	}
    		    }
    		}
    	}catch(Exception e){
    		logger.fatal("ERROR ",e);
    	}
	}
}
