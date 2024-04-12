/*
 * Created on Sep 21, 2007
 * Created by weeraya
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
package com.eaf.orig.shared.view.form.subform;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.model.ApplicationDataM;
import com.eaf.orig.shared.model.AttachmentHistoryDataM;
import com.eaf.orig.shared.utility.ORIGUtility;
import com.eaf.orig.shared.view.form.ORIGSubForm;

/**
 * @author weeraya
 *
 * Type: AttachmentSubForm
 */
public class AttachmentSubForm extends ORIGSubForm {
	Logger logger = Logger.getLogger(AttachmentSubForm.class);
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#setProperties(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public void setProperties(HttpServletRequest request, Object appForm) {
    	logger.debug("=======AttachmentSubForm====");
		if("Y".equals((String)request.getSession().getAttribute("AttachFlag"))){
			int loop = 1;
			String count = request.getParameter("loop");		
			UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
			//NaosApplicationUtil appUtil = NaosApplicationUtil.getInstance_SG();
			ApplicationDataM app = (ApplicationDataM) appForm;
			if (app == null) {
				app = new ApplicationDataM();
			}
			Vector appAttach = app.getAttachmentVect();
			//if (appAttach == null) {
				appAttach = new Vector();
				app.setAttachmentVect(appAttach);
			//}
			
			if (count != null && !count.equals("")) {
				loop = Integer.parseInt(count);
				logger.debug("count = " + count);
				
				for (int i = 1; i <= loop; i++) {
					AttachmentHistoryDataM attachmentM = new AttachmentHistoryDataM();	
					String docId = request.getParameter("docId" + i);
					String fileName = URLDecoder.decode(request.getParameter("fileName" + i));
					String size = request.getParameter("size" + i);
					String docType = request.getParameter("docType" + i);
					logger.debug("...fileName111 = " + request.getParameter("fileName" + i));
					logger.debug("...docId = " + docId);
					logger.debug("...fileName = " + fileName);
					logger.debug("...size = " + size);
					if(docId!=null){
						attachmentM.setAttachId(Integer.parseInt(docId));
					}
					if(size!=null){
						attachmentM.setFileSize(Integer.parseInt(size));
					}
					attachmentM.setFileName(fileName);
					attachmentM.setFileType(docType);
					
			        if(ORIGUtility.isEmptyString(attachmentM.getCreateBy())){
			        	attachmentM.setCreateBy(userM.getUserName());
			        }else{
			        	attachmentM.setUpdateBy(userM.getUserName());
			        }
			        
					appAttach.add(attachmentM);
				}
				logger.debug("appAttach.size() = " + appAttach.size());
				request.getSession().removeAttribute("AttachFlag");
			}else{
				logger.debug("Delete AttachFile");
				app.setAttachmentVect(new Vector());
			}
		}

    }

    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateForm(javax.servlet.http.HttpServletRequest, java.lang.Object)
     */
    public HashMap validateForm(HttpServletRequest request, Object appForm) {
         
        return null;
    }
    
    /* (non-Javadoc)
     * @see com.eaf.orig.shared.view.form.ORIGSubForm#validateSubForm(javax.servlet.http.HttpServletRequest)
     */
    public boolean validateSubForm(HttpServletRequest request) {
        // TODO Auto-generated method stub
        return false;
    }

}
