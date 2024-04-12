package com.eaf.orig.rest.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.engine.SQLQueryEngine;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.ulo.app.view.form.rest.ia.resource.ImageDocumentService;
import com.eaf.orig.ulo.model.ia.DocumentSegment;
import com.eaf.orig.ulo.model.ia.ImageDocumentRequest;
import com.eaf.orig.ulo.model.ia.KBankHeader;
import com.eaf.service.rest.model.ServiceResponse;
//import com.eaf.core.ulo.common.rest.RESTClient;
//import com.eaf.core.ulo.common.rest.RESTResponse;
//import com.eaf.orig.ulo.model.ia.ImageDocumentResponse;

public class CreateIAApplication implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(CreateIAApplication.class);
	String CREATE_IA_APPLICATION_URL = SystemConfig.getProperty("CREATE_IA_APPLICATION_URL");
	String IMAGE_TEMPLATE_PATH = SystemConfig.getProperty("IMAGE_TEMPLATE_PATH");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ResponseData processAction(HttpServletRequest request){		
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.CREATE_IA_APPLICATION); 
		try{
			UserDetailM userM = (UserDetailM)request.getSession().getAttribute("ORIGUser");	
			String QR_APPLY_TYPE = request.getParameter("QR_APPLY_TYPE");
			String QR1_PRE_PRINTED = request.getParameter("QR1_PRE_PRINTED");
			String QR2_RUNNING_NO = request.getParameter("QR2_RUNNING_NO");
			String RC_CODE = request.getParameter("RC_CODE");
			String CHANNEL = request.getParameter("CHANNEL");
			String BRANCH_NO = request.getParameter("BRANCH_NO");
			String SCAN_DATE = request.getParameter("SCAN_DATE");
			logger.debug("QR_APPLY_TYPE >> "+QR_APPLY_TYPE);
			logger.debug("QR1_PRE_PRINTED >> "+QR1_PRE_PRINTED);
			logger.debug("QR2_RUNNING_NO >> "+QR2_RUNNING_NO);
			logger.debug("RC_CODE >> "+RC_CODE);
			logger.debug("CHANNEL >> "+CHANNEL);
			logger.debug("BRANCH_NO >> "+BRANCH_NO);
			logger.debug("SCAN_DATE >> "+SCAN_DATE);
			
			ImageDocumentRequest imageDocumentRequest = new ImageDocumentRequest();
				imageDocumentRequest.setScanDate(FormatUtil.toDate(SCAN_DATE,FormatUtil.TH));
				imageDocumentRequest.setQrApplyType(QR_APPLY_TYPE);
				imageDocumentRequest.setQr1(QR1_PRE_PRINTED);
				imageDocumentRequest.setQr2(QR2_RUNNING_NO);			
				imageDocumentRequest.setRcCode(RC_CODE);
				imageDocumentRequest.setBranchCode(BRANCH_NO);
				imageDocumentRequest.setChannel(CHANNEL);
				if(!Util.empty(QR1_PRE_PRINTED)){
					imageDocumentRequest.setDocumentSegments(getDocumentSegment(QR1_PRE_PRINTED,QR2_RUNNING_NO));
				}
			KBankHeader kbankHeader = new KBankHeader();
			kbankHeader.setUserId(userM.getUserName());
			imageDocumentRequest.setKbankHeader(kbankHeader);
//			RESTClient restClient = new RESTClient();
//			RESTResponse restResponse = restClient.executeRESTCall(CREATE_IA_APPLICATION_URL,imageDocumentRequest);
//			jsonResponse = restResponse.getJsonResponse();
//			if(null == jsonResponse){
//				jsonResponse = new JSONObject();
//			}
			ImageDocumentService imageService = new ImageDocumentService();
			imageService.create(imageDocumentRequest);			
			SQLQueryEngine queryEngine = new SQLQueryEngine();
			HashMap Hash = queryEngine.LoadModule("SELECT * FROM ORIG_APPLICATION_GROUP WHERE APPLICATION_GROUP_NO = ?",QR2_RUNNING_NO);
			JSONObject jsonResponse = new JSONObject();
				jsonResponse.put("resultCode",ServiceResponse.Status.SUCCESS);
				jsonResponse.put("resultDesc","Success.");
				jsonResponse.put("applicationGroupNo",SQLQueryEngine.display(Hash, "APPLICATION_GROUP_NO"));
				jsonResponse.put("applicationGroupId",SQLQueryEngine.display(Hash, "APPLICATION_GROUP_ID"));
				jsonResponse.put("instantId",SQLQueryEngine.display(Hash, "INSTANT_ID"));
				jsonResponse.put("jobState",SQLQueryEngine.display(Hash, "JOB_STATE"));
				jsonResponse.put("applicationStatus",SQLQueryEngine.display(Hash, "APPLICATION_STATUS"));
				jsonResponse.put("applicationTemplate",SQLQueryEngine.display(Hash, "APPLICATION_TEMPLATE"));
			logger.debug("jsonResponse >> "+jsonResponse);
			return responseData.success(jsonResponse.toString());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
	private String getImageTempletePath(String qr1,String qr2){
		String imageTemplatePath = IMAGE_TEMPLATE_PATH+qr1+File.separator+"UniqueSmart"+File.separator+qr2;
		File folder = new File(imageTemplatePath);
		if(!folder.exists()){
			imageTemplatePath = IMAGE_TEMPLATE_PATH+qr1;
		}
		return imageTemplatePath;
	}
	private ArrayList<DocumentSegment> getDocumentSegment(String qr1,String qr2){
		ArrayList<DocumentSegment> documentSegments = new ArrayList<DocumentSegment>();
		try{
			String templatePath = getImageTempletePath(qr1, qr2);
			logger.debug("templatePath >> "+templatePath);
			File folder = new File(templatePath);
			File[] listOfFiles = folder.listFiles();
			logger.debug("listOfFiles >> "+listOfFiles);
			if(null != listOfFiles){
				for(File file : listOfFiles){
					String Name = file.getName();
					String fileName = Name.replaceFirst("[.][^.]+$","");					
					if(!Util.empty(fileName)&&!fileName.contains("thumb")&&!file.isDirectory()){
						logger.debug("fileName >> "+fileName);
						DocumentSegment  documentSegment = new DocumentSegment();
						String[] objectFileName = fileName.split("\\-");
						try{
							String imageId = objectFileName[0];
							documentSegment.setImageId(imageId);
						}catch(Exception e){}
						try{
							String docTypeGroup = objectFileName[1];
							documentSegment.setDocTypeGroup(docTypeGroup);
						}catch(Exception e){}
						try{
							String docTypeCode = objectFileName[2];
							documentSegment.setDocTypeCode(docTypeCode);
						}catch(Exception e){}		
						try{
							String docTypeSeq = objectFileName[3];
							documentSegment.setDocTypeSeq(Integer.parseInt(docTypeSeq));
						}catch(Exception e){}
						try{
							String seq = objectFileName[4];
							documentSegment.setSeq(Integer.parseInt(seq));
						}catch(Exception e){}
						documentSegments.add(documentSegment);
					}
				}
			}
		}catch(Exception e){
			logger.fatal("ERROR",e);
		}
		logger.debug("documentSegments.size >> "+documentSegments.size());
		return documentSegments;
	}
}
