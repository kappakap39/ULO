package com.eaf.orig.ulo.app.view.form.subform;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.date.ApplicationDate;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.profile.model.UserDetailM;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.dm.DocumentManagementDataM;

public class DmWareHouseInfo extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(DmWareHouseInfo.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		UserDetailM userM = (UserDetailM) request.getSession().getAttribute("ORIGUser");
		DocumentManagementDataM dmManageDataM = (DocumentManagementDataM)appForm;
		dmManageDataM.setContainerNo(request.getParameter("DM_FOLDER_NO"));
		dmManageDataM.setBoxNo(request.getParameter("DM_BOX_NO"));		
		dmManageDataM.setUpdateBy(userM.getUserName());
		dmManageDataM.setUpdateDate(ApplicationDate.getTimestamp());
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		DocumentManagementDataM dmManageDataM = (DocumentManagementDataM)appForm;
		String DM_IS_DOC_COMPLETE = request.getParameter("DM_IS_DOC_COMPLETE");
		logger.debug("DM_IS_DOC_COMPLETE>>>"+DM_IS_DOC_COMPLETE);
		if(SystemConstant.getConstant("FLAG_YES").equals(DM_IS_DOC_COMPLETE)){
			formError.mandatoryElement("DM_FOLDER_NO","DM_FOLDER_NO",dmManageDataM.getContainerNo(),request);
			formError.mandatoryElement("DM_BOX_NO","DM_BOX_NO",dmManageDataM.getBoxNo(),request);	
		}	
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request) {		
		return false;
	}
}
