package com.eaf.orig.ulo.control.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.file.FileUtil;
import com.eaf.core.ulo.common.file.ImportControl;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.service.process.response.model.ProcessResponse;
import com.eaf.service.rest.model.ServiceResponse;
import com.google.gson.Gson;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet  implements javax.servlet.Servlet  {
	private static final long serialVersionUID = 1L;
	private static transient Logger logger = Logger.getLogger(UploadServlet.class);	
    public UploadServlet() {
        super();
    }
    private static HashMap<String,String> importControl = null;
    public synchronized void create(){
    	importControl = new HashMap<String, String>();
    	importControl.put(MConstant.IMPORT.IMPORT_ATTACH_FILE,"com.eaf.orig.ulo.control.imports.ImportAttachmentSubForm");
    	importControl.put(MConstant.IMPORT.IMPORT_OT_DATA,"com.eaf.orig.ulo.control.imports.ImportOTData");
    	importControl.put(MConstant.IMPORT.UPLOAD_FORECAST,"com.eaf.orig.ulo.control.imports.UploadForecast");
    	importControl.put(MConstant.IMPORT.UPDATE_PRODUCT_IMAGE,"com.eaf.orig.ulo.control.imports.UploadProductImage");
    	importControl.put(MConstant.IMPORT.UPLOAD_COMPANY_NAME,"com.eaf.orig.ulo.control.imports.UploadCompanyName");
    	
    	//UPLOAD USER MANUAL FILE
    	importControl.put(MConstant.IMPORT.UPLOAD_USER, "com.eaf.orig.ulo.control.imports.UploadUser");
    	
    	//UPLOAD PTT BLUE CARDsss FILE
    	importControl.put(MConstant.IMPORT.UPLOAD_PTT_BLUE_CARD, "com.eaf.orig.ulo.control.imports.UploadPTTBlueCard");
    	
    	//UPLOAD FILE MANUAL
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_COMPANY_GROUP,"com.eaf.mf.control.uploads.UploadCompanyGroupFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_FICS_MAPPING,"com.eaf.mf.control.uploads.UploadFICSMappingFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_COA_MAPPING,"com.eaf.mf.control.uploads.UploadCOAMappingFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_CARD_TRUSTED_COMPANY,"com.eaf.mf.control.uploads.UploadTrustedCompanyFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_CARD_HIERACHY,"com.eaf.mf.control.uploads.UploadCardHierarchyFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_THAIBEV_PARTNER,"com.eaf.mf.control.uploads.UploadThaibevPartnerFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_KBANK_SALARY, "com.eaf.mf.control.uploads.UploadKbankSalaryFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_BILLING_CYCLE, "com.eaf.mf.control.uploads.UploadBillingCycleFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_SOLO_FILE,"com.eaf.mf.control.uploads.UploadSOLOFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_CAP_PORT,"com.eaf.mf.control.uploads.UploadCAPPORTFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_CO_BRAND_CUSTOMER, "com.eaf.mf.control.uploads.UploadCoBrandFile");
    	
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_LOAN_XREF, "com.eaf.mf.control.uploads.UploadLoanXRefFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_LOAN_INT_TYPE_XREF, "com.eaf.mf.control.uploads.UploadLoanIntTypeXRefFile");
    	importControl.put(MConstant.UPLOAD_FILE_MANUAL.UPLOAD_PRICING, "com.eaf.mf.control.uploads.UploadPricingFile");
    }
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String STATUS= ResponseData.SUCCESS;
		ProcessResponse responseData  = new ProcessResponse();
		request.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf-8");
		if(null == importControl){
			importControl = new HashMap<String, String>();
			create();
		}
		
		ResponseDataController responseDatacontroller = new ResponseDataController(request,ResponseData.FunctionId.UPLOAD);
		String IMPORT_ID = request.getParameter("IMPORT_ID");
		String FILE_TYPE = request.getParameter("FILE_TYPE");
		logger.debug("IMPORT_ID >> "+IMPORT_ID);
		logger.debug("FILE_TYPE >> "+FILE_TYPE);
		Collection<Part> parts = request.getParts();
		if(null != parts){
	        for(Part part : parts){
	        	logger.debug("PATH NAME : "+part.getName());
	        	if("IMPORT_FILE".equals(part.getName())){	        		
	        		String FILE_NAME =   FileUtil.getFilename(part);	        		
	        		if(!Util.empty(FILE_NAME)){
		        		String className = importControl.get(IMPORT_ID);
		        		if(!Util.empty(className)){
				        	try{
				        		logger.debug("className >> "+className);
				        		if(!Util.empty(className)){
				        			ImportControl control = null; 		        			 
				        		    try{			        		    	
				        		    	control = (ImportControl)Class.forName(className).newInstance();			        		    	
				        			} catch (Exception e) {
				        				logger.fatal("ERROR ",e);
				        				responseDatacontroller.response(response,new Gson().toJson(responseDatacontroller.error(e)));
				        				STATUS= ResponseData.SYSTEM_EXCEPTION;
				        			}	
				        		    if(null != control){
				        		    	if(control.requiredEvent()){
				        		    	  responseData =control.processEvent(part, request, response);
				        		    	}else{
				        		    		
						        			String LOCATION = control.getFileLocation(request, IMPORT_ID, FILE_NAME);
						        			if(Util.empty(LOCATION)){
						        				LOCATION = FileUtil.getLocation(FILE_NAME);
						        			}
								        	logger.debug("FILE_NAME "+FILE_NAME);
								        	logger.debug("LOCATION "+LOCATION);
								        	FileUtil.WriteFile(part.getInputStream(),LOCATION,(int)part.getSize());
								        	responseData = control.processImport(request,FILE_TYPE,FILE_NAME,LOCATION);
								        	
				        		    	}
				        		    	if(!ServiceResponse.Status.SUCCESS.equals(responseData.getStatusCode())){
				        		    		 responseDatacontroller.response(response,new Gson().toJson(responseDatacontroller.error()));
				        		    		 STATUS= ResponseData.BUSINESS_EXCEPTION;
				        		    	}
				        		    }
				        		}
				        	}catch(Exception e){
				        		logger.fatal("ERROR ",e);
				        		responseDatacontroller.response(response,new Gson().toJson(responseDatacontroller.error(e)));
				        		STATUS= ResponseData.SYSTEM_EXCEPTION;
				        	}
		        		}else{
		        			String LOCATION = FileUtil.getLocation(FILE_NAME);
				        	logger.debug("FILE_NAME "+FILE_NAME);
				        	logger.debug("LOCATION "+LOCATION);
				        	FileUtil.WriteFile(part.getInputStream(),LOCATION,(int)part.getSize());
		        		}
	        		}
	        	}
	        }
		}
		if(ResponseData.SUCCESS.equals(STATUS)){
			responseDatacontroller.response(response,new Gson().toJson(responseDatacontroller.success(responseData.getData())));
		}		
	}
}
