package com.eaf.orig.ulo.pl.ajax;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.InterfaceImportResponseDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.ulo.pl.app.utility.AttachmentUtility;
import com.eaf.orig.ulo.pl.app.utility.OrigImportFileInf;

/**
 * Servlet implementation class PLImportCreditLineServlet
 */
public class OrigManualImportInfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String packageName = "com.eaf.orig.ulo.pl.app.importfile"; 
	Logger logger = Logger.getLogger(OrigManualImportInfServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrigManualImportInfServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("Upload  Attachment");	
		int maxMemorySize=10*1024*1024;
		AttachmentUtility  attachmentUtil=AttachmentUtility.getInstance();
		String tempDir=attachmentUtil.getAttachmentTempPath();
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
        if(isMultipart){
	        // Create a factory for disk-based file items
	        DiskFileItemFactory factory = new DiskFileItemFactory();
	
	   	    // Set factory constraints
	   	    factory.setSizeThreshold(maxMemorySize);
	   	    factory.setRepository(new File(tempDir));
	
			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(1024*1024*1034*1024);
			
			String interfaceType = "";
			
			try {
				// Parse the request
				List /* FileItem */ items = upload.parseRequest(request);
				Iterator iter = items.iterator();
				FileItem importFile = null;
				FileItem attachFile = null;
				while (iter.hasNext()) {
					FileItem item = (FileItem)iter.next();
					String name = item.getFieldName();
					if (item.isFormField()) {
						if("interface_type".equals(name)){
							interfaceType = item.getString(); //set value of interface_type to interfaceType
						    logger.debug("Form field " + name + " with value " +interfaceType+ " detected.");
						}
					 } else {
					 	logger.debug("File field " + name + " with file name " + item.getName() + " detected.");
					 	logger.debug("Content Type="+ item.getContentType());
					 	if("fileNameCreditLine".equalsIgnoreCase(name) && item.getName() != null && !"".equals(item.getName())){
					    	importFile = item;
					    }else if("fileNameApprove".equalsIgnoreCase(name) && item.getName() != null && !"".equals(item.getName())){
					    	attachFile = item;
					    }
					 }
				}
				if(importFile != null){
					ORIGCacheDataM cacheM = ORIGCacheUtil.getInstance().GetListboxCacheDataM(OrigConstant.fieldId.INTERFACE_TYPE, OrigConstant.BusClass.FCP_ALL_ALL, interfaceType);
					String className = packageName + "." + cacheM.getSystemID7();
					logger.debug("@@@@@ className:" + className);
					try{
						OrigImportFileInf importInf = (OrigImportFileInf)Class.forName(className).newInstance();
						importInf.processImportFile(request, cacheM, importFile, attachFile);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			} catch (Exception e) {	
				e.printStackTrace();
				logger.debug("Error:"+e);
				//create error message
				InterfaceImportResponseDataM responseDetailM = new InterfaceImportResponseDataM();
				String errorMessage = "<span class='BigtodotextRedLeft'>"+ErrorUtil.getShortErrorMessage(request, "SYSTEM_ERROR")+"</span>";
				responseDetailM.setInterfaceType(interfaceType);
				responseDetailM.setResponseDetail(errorMessage);
				request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
			} 
        }else{
        	logger.debug("Form Not Multi path");
        } 
        //redirect to import credit line screen
        String redirectUrl = "FrontController?page=MANUAL_IMPORT_SCREEN";
        logger.debug("@@@@@ redirectUrl:"+redirectUrl);
        response.sendRedirect(redirectUrl);
	}

}
