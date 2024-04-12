/*
 * Created on Feb 13, 2008
 * Created by Avalant
 * 
 * Copyright (c) 2007 Avalant Co.,Ltd.
 * 20 North Sathorn Road, 15-16th Floor Bubhajit Bldg., Silom, Bangrak, Bangkok 10500, Thailand
 * All rights reserved.
 *
 * This software is the confidential and prorietary infomation of
 * Avalant Co.,Ltd. ("Confidential Infomation"). You shall not
 * disclose such Confidential Infomation and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Avalant Co.,Ltd.
 * 
 */
package com.eaf.orig.shared.servlet;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.eaf.orig.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.constant.OrigConstant;
//import com.eaf.orig.shared.dao.ORIGDAOFactory;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.AttachmentHistoryDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;

/**
 * @author Avalant
 * 
 * Type: DeleteAttachmentServlet
 */
public class DeleteAttachmentServlet extends HttpServlet implements Servlet {
    Logger logger = Logger.getLogger(DeleteAttachmentServlet.class);

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.debug("<<<<<<< Start DeleteAttachementServlet >>>>>>>");
        //http://<%=request.getServerName()%>:<%=request.getServerPort()%><%=request.getContextPath()%>/orig/appform/app_deleteimage.jsp
        //response.sendRedirect(request.getContextPath()+"/orig/appform/app_deleteimage.jsp"++queryString);
        ORIGFormHandler formHandler = (ORIGFormHandler) request.getSession().getAttribute(OrigConstant.FORMHANDLER_NAME);
        logger.debug("FormHandle -->" + formHandler);
        UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
        ApplicationDataM appForm = formHandler.getAppForm();
        ORIGUtility utility = ORIGUtility.getInstance();
        logger.debug("Query String=" + request.getQueryString());
        String queryString = "?" + request.getQueryString();
        Vector vAttach = new Vector();
        int maxDoc = 0;
        String count = request.getParameter("count");
        if (count != null && count.length() > 0)
            maxDoc = utility.stringToInt(count);
        for (int index = 1; index <= maxDoc; index++) {
            String docId = request.getParameter("docId" + index + "");
            String fileName = java.net.URLDecoder.decode(request.getParameter("fileName" + index + ""), "UTF-8");
            System.out.println(".....index=" + index);
            System.out.println(".....fileName=" + fileName);
            String size = request.getParameter("size" + index + "");
            int sizeKB = (int) Math.round(Double.parseDouble(size) / 1024);
            String docType = request.getParameter("docType" + index + "");
            AttachmentHistoryDataM attachHistoryDataM = new AttachmentHistoryDataM();
            attachHistoryDataM.setApplicationRecordId(appForm.getAppRecordID());
            attachHistoryDataM.setCreateBy(userM.getUserName());
            attachHistoryDataM.setUpdateBy(userM.getUserName());
            attachHistoryDataM.setAttachId(utility.stringToInt(docId));
            attachHistoryDataM.setFileName(fileName);
            attachHistoryDataM.setFileType(docType);
           // attachHistoryDataM.setSeq(index);                          
            vAttach.add(attachHistoryDataM);
        }
        try {            
//            ORIGDAOFactory.getOrigAttachmentHistoryMDAO().deleteNotInKeyTableORIG_ATTACHMENT_HISTORY( vAttach,appForm.getAppRecordID());
        	
        	PLORIGEJBService.getORIGDAOUtilLocal().deleteNotInKeyTableORIG_ATTACHMENT_HISTORY(vAttach,appForm.getAppRecordID());
        	
            appForm.setAttachmentVect(vAttach);
        } catch (Exception e) {
            logger.debug(" Delete not key in OrigAttachementHistoryError", e);
        }                    
        String redirectUrl = request.getContextPath() + "/orig/appform/app_deleteimage.jsp" + queryString;
        logger.debug("redirect Url ==>" + redirectUrl);
        response.sendRedirect(redirectUrl);
    }
}
