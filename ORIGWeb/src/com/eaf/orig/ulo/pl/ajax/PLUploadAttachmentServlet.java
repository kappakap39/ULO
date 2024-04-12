package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.utility.ORIGDisplayFormatUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.utility.AttachmentUtility;
import com.eaf.orig.ulo.pl.app.utility.HTMLRenderUtil;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

public class PLUploadAttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(PLUploadAttachmentServlet.class); 
    public PLUploadAttachmentServlet() {
        super();        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 logger.debug("Not Support doget");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Upload  Attachment");		 
		request.setCharacterEncoding("UTF-8");
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		PLOrigFormHandler  plOrigFormHandler =	(PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
		   
		ORIGFormUtil formUtil = new ORIGFormUtil();
		PLApplicationDataM plapplicationDataM=plOrigFormHandler.getAppForm();
		String searchType = (String) request.getSession().getAttribute("searchType");
		String displayMode=formUtil.getDisplayMode("ATTACHMENT_SUBFORM", userM.getRoles(), plapplicationDataM.getJobState(), plOrigFormHandler.getFormID(), searchType);
		   
		int maxMemorySize=10*1024*1024;
		AttachmentUtility  attachmentUtil=AttachmentUtility.getInstance();
		double maxFileSize=attachmentUtil.getMaxFileSize()*1024;
		String tempDir=attachmentUtil.getAttachmentTempPath();
		logger.debug("Encoding="+request.getCharacterEncoding());
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		String appRecId=""; 
		String fileCatagoryCode="";
		String returnURL="";
       if(isMultipart){
    	// Create a factory for disk-based file items
    	   DiskFileItemFactory factory = new DiskFileItemFactory();

    	   // Set factory constraints
    	   factory.setSizeThreshold(maxMemorySize);
    	   
//    	   #rawi fix bug big file cannot upload
//    	   factory.setRepository(new File(tempDir));
    	   File tmp = new File(tempDir);
    	   if(!tmp.exists()){
    		   tmp.mkdirs();
    	   }
    	   factory.setRepository(tmp);
    	   
		 // Create a new file upload handler
		 ServletFileUpload upload = new ServletFileUpload(factory);
        //String appString  
		 PLAttachmentHistoryDataM attachmentDataM=null;
		boolean overSize=false;
		 try {
			// Parse the request
			 List /* FileItem */ items = upload.parseRequest(request);
			 Iterator iter = items.iterator();
			 while (iter.hasNext()) {
				    FileItem item = (FileItem)iter.next();
				    String name = item.getFieldName();
				    //InputStream stream = item.openStream();
				    if (item.isFormField()) {
				    	String value=item.getString();
				        logger.debug("Form field " + name + " with value "+value+ " detected.");					         
				       if("appRecId".equalsIgnoreCase(name)){
				    	   appRecId=value;
				       } else if("file_category_code".equalsIgnoreCase(name)){
				    	   fileCatagoryCode=value;
				       }else if("returnURL".equalsIgnoreCase(name)){
				    	   returnURL=value;
				       }
				        
				    } else {
				        logger.debug("File field " + name + " with file name "+ item.getName() + " detected.");
				        logger.debug("Content Type="+ item.getContentType());
				        // Process the input stream
				        if(item.getSize()<maxFileSize){
					        attachmentDataM = AttachmentUtility.getInstance().createFile(item);				        
					        File fileCreate = new File(attachmentDataM.getFilePath()+File.separator+attachmentDataM.getFileName());
					        item.write(fileCreate);			
				        }else{
				        	logger.debug("File Over Size");		
				        	String FILE_SIZE = ( ORIGDisplayFormatUtil.displayCommaNumber(item.getSize()/(1024d *1024d)));
				        	String MAX_SIZE  = ORIGDisplayFormatUtil.displayCommaNumber(attachmentUtil.getMaxFileSize()/1024d);
				        	String ERROR_MSG = "File Size "+FILE_SIZE+" MB. * File Size must not over "+MAX_SIZE+" MB.";
				        	request.getSession().setAttribute("ATTACH_ERROR_MSG", ERROR_MSG);
				        	logger.debug("redirect to request.getRequestURI() ="+request.getRequestURI());
				        	overSize=true;
				        }
				    }
				    
				}
			logger.debug("appRecId="+appRecId);
			logger.debug("fileCatagoryCode="+fileCatagoryCode);
			logger.debug("overSize="+overSize);
			if(overSize){
				response.sendRedirect(returnURL);		
				return;
			}
			attachmentDataM.setApplicationRecordId(appRecId);
	        attachmentDataM.setCreateBy(userM.getUserName());
	        attachmentDataM.setUpdateBy(userM.getUserName());
	        attachmentDataM.setFileType(fileCatagoryCode);
			if(attachmentDataM!=null){
//				ORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().saveUpdateModelOrigAttachmentHistoryM(attachmentDataM);
				PLORIGEJBService.getORIGDAOUtilLocal().saveUpdateModelOrigAttachmentHistoryM(attachmentDataM);
			}
		   
		   Vector<PLAttachmentHistoryDataM> attachmentVector=null;
		   if(plOrigFormHandler.getAppForm()!=null){
			   attachmentVector=plOrigFormHandler.getAppForm().getAttachmentHistoryVect();
		   }
		   if(attachmentVector==null){
			   attachmentVector=new Vector<PLAttachmentHistoryDataM>();
		   }
		   
		   //20121031 #Vikrom for PO
		   attachmentDataM.setIsNew(OrigConstant.FLAG_Y);
		   attachmentVector.add(attachmentDataM);
		   response.setContentType("text/html;charset=UTF-8");
		   response.setCharacterEncoding("UTF-8");
		   OutputStreamWriter out=new OutputStreamWriter(response.getOutputStream(),"UTF-8");	   
		   StringBuffer buff=new StringBuffer();		 
		   buff.append("<html><body><script>");		   
		   buff.append(" opener.drawAttachTable('"+ HTMLRenderUtil.replaceQuote(attachmentUtil.drawAttachmentTable(attachmentVector, userM.getCurrentRole(), displayMode, userM.getUserName()))+"');");
		   //buff.append(HTMLRenderUtil.displayHTML(attachmentUtil.drawAttachmentTable(attachmentVector)));
		   buff.append(" window.close()");
		   buff.append("</script></body><html>");
		   out.write(buff.toString());
		   out.flush();
		   out.close();
		} catch (Exception e) {			 
			logger.fatal("ERROR ",e);
			request.getSession().setAttribute("ATTACH_ERROR_MSG",ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR"));
			response.sendRedirect(returnURL);	
		} 
       }else{
    	   request.getSession().setAttribute("ATTACH_ERROR_MSG",ErrorUtil.getShortErrorMessage(request,"INTERNAL_SERVER_ERROR"));
    	   logger.debug("Form Not Multi path");
    	   response.sendRedirect(returnURL);	
       } 
		 
	}

}
