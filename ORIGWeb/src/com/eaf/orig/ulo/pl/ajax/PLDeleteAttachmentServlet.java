/**
 * Create Date Mar 12, 2012 
 * Create By Sankom
 * Copyright (c) 2012 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15 th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
*/
package com.eaf.orig.ulo.pl.ajax;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.eaf.j2ee.pattern.util.ErrorUtil;
import com.eaf.orig.cache.properties.ORIGCacheDataM;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.model.InterfaceImportResponseDataM;
import com.eaf.orig.shared.utility.ORIGCacheUtil;
import com.eaf.orig.shared.utility.ORIGFormUtil;
import com.eaf.orig.ulo.pl.app.utility.AttachmentUtility;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.formcontrol.view.form.PLOrigFormHandler;
import com.eaf.orig.ulo.pl.model.app.PLApplicationDataM;
import com.eaf.orig.ulo.pl.model.app.PLAttachmentHistoryDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * Servlet implementation class PLDeleteAttachmentServlet
 */
public class PLDeleteAttachmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 Logger logger = Logger.getLogger(PLDeleteAttachmentServlet.class);     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PLDeleteAttachmentServlet() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 logger.debug("Do get");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Do post");
		UserDetailM userM =	(UserDetailM) request.getSession().getAttribute("ORIGUser");
		if (request.getParameter("attachmentIds") != null) {
			PLOrigFormHandler plOrigFormHandler = (PLOrigFormHandler) request.getSession().getAttribute("PLORIGForm");
			AttachmentUtility attachmentUtil = AttachmentUtility.getInstance();
			PLApplicationDataM plAppM = plOrigFormHandler.getAppForm();   
			
			String appRecordID = plAppM.getAppRecordID();
			Vector<PLAttachmentHistoryDataM> attachmentVect = plAppM.getAttachmentHistoryVect();
			String attachmentIds = request.getParameter("attachmentIds");
			logger.debug("attachmentIds = "+attachmentIds);
			
			if (attachmentIds != null) {
				String[] aAttachmentId = attachmentIds.trim().split(",");	  
			    //remove attachment		   		 
				for (int i = 0; i < aAttachmentId.length; i++) {
			    	String attachId=aAttachmentId[i];
			        logger.debug("attachId="+attachId);	
					for (int j = 0; j < attachmentVect.size(); j++) {
						PLAttachmentHistoryDataM attachmentHistory = attachmentVect.get(j);
						if (attachmentHistory.getAttachId().equalsIgnoreCase(attachId)) {
			        		 attachmentVect.remove(j);
			        		 j--;
			        	 }
			         }
			    }
			    try {
//					 ORIGDAOFactory.getPLOrigAttachmentHistoryMDAO().deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(attachmentVect, appRecordID);
			    	PLORIGEJBService.getORIGDAOUtilLocal().deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(attachmentVect, appRecordID);
				} catch ( Exception e) {
					logger.fatal(e.getMessage());
				}
			}
			//Render Result
			//Write Output 		 
			response.setContentType("text/html;charset=TIS620");
			response.setCharacterEncoding("UTF-8");
			
			ORIGFormUtil formUtil = new ORIGFormUtil();
			String searchType = (String) request.getSession().getAttribute("searchType");
			String displayMode = formUtil.getDisplayMode("ATTACHMENT_SUBFORM", userM.getRoles(), plAppM.getJobState(), plOrigFormHandler.getFormID(), searchType);
			  
			OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream(),"UTF-8");
			StringBuffer buff = new StringBuffer();	   
			  
//			   buff.append("<html>");
//			   buff.append("<head>");
//			   buff.append("<title></title>");
//			   buff.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
//			   buff.append("</head><body>");
//			   buff.append("<script> opener.drawAttachTable('"+HTMLRenderUtil.replaceQuote(attachmentUtil.drawAttachmentTable(attachmentVect, userM.getCurrentRole(), displayMode, userM.getUserName()))+"');");
//			   buff.append(" window.close();</script>");
//			   buff.append("</body><html>");
//			   Comment Why Error 
			   			   buff.append(attachmentUtil.drawAttachmentTable(attachmentVect, userM.getCurrentRole(), displayMode, userM.getUserName()));
			   logger.debug("buff.toString()"+buff.toString());
			   out.write(buff.toString());
			   out.flush();
			   out.close();	
			   
		} else {
			
			InterfaceImportResponseDataM responseDetailM = new InterfaceImportResponseDataM();
			String interfaceType = request.getParameter("interface_type");
			logger.debug("@@@@@ interfaceType:" + interfaceType);
			ORIGCacheDataM cacheM = ORIGCacheUtil.getInstance().GetListboxCacheDataM(OrigConstant.fieldId.INTERFACE_TYPE, OrigConstant.BusClass.FCP_ALL_ALL, interfaceType);
			
			String filePath = cacheM.getSystemID11();
			
			try {
				if(OrigConstant.InterfaceType.INCREASE_CREDIT_LINE.equals(interfaceType)){
					String attachId = "CL"+DataFormatUtility.dateToStringYYYYMMDD(new Date());
					PLAttachmentHistoryDataM attachM = PLORIGEJBService.getORIGDAOUtilLocal().loadModelOrigAttachmentHistoryMFromAttachId(attachId);
					filePath = attachM.getFilePath();
					AttachmentUtility.getInstance().delete(new File(filePath));
			  		PLORIGEJBService.getORIGDAOUtilLocal().deleteTableORIG_ATTACHMENT_HISTORY_ByAttachId(attachId);
				} else {
					PLORIGEJBService.getPLORIGSchedulerManager().clearDataDeleteImportInterface(cacheM, userM.getUserName());
					AttachmentUtility.getInstance().removeOnlyFileInDir(filePath);
					String backupPath = filePath + File.separator + OrigConstant.BACKUP + File.separator + DataFormatUtility.dateToStringYYYYMMDD(new Date());
					AttachmentUtility.getInstance().delete(new File(backupPath));
				}
				String message = "<span class='BigtodotextGreenLeft'>"+"File Deleted"+"</span>";
				//responseDetailM.setInterfaceType(interfaceType);
				responseDetailM.setImportFileName(null);
				responseDetailM.setAttachFileName(null);
				responseDetailM.setResponseDetail(message);
				request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
				
			} catch (Exception e) {
				
				logger.fatal(e.getMessage());
				String message = "<span class='BigtodotextRedLeft'>"+ErrorUtil.getShortErrorMessage(request, "SYSTEM_ERROR")+"</span>";
				responseDetailM.setInterfaceType(interfaceType);
				responseDetailM.setResponseDetail(message);
				request.getSession().setAttribute("IMPORT_RESPONSE",responseDetailM);
			}
			
		  	String redirectUrl = "FrontController?page=MANUAL_IMPORT_SCREEN";
	        logger.debug("@@@@@ redirectUrl:"+redirectUrl);
	        response.sendRedirect(redirectUrl);
		}
	}
}
