package com.eaf.orig.ulo.app.view.util.ajax;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class WrapFormIdForBundleSME implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(WrapFormIdForBundleSME.class);	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.WRAP_FORMID_FOR_BUNDLE_SME);
		String data = null;
		try{
			String INC_TYPE_BUNDLE_SME = SystemConstant.getConstant("INC_TYPE_BUNDLE_SME");
			String APPLICATION_TEMPLATE_CC_BUNDLE_SME = SystemConstant.getConstant("APPLICATION_TEMPLATE_CC_BUNDLE_SME");
			String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
			String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
			String appRecordID = request.getParameter("appRecordID");
			String type = request.getParameter("type");
			String formName = request.getParameter("formName");
			logger.info("appRecordID :"+appRecordID);
			logger.info("Type :"+type);
			logger.info("formName :"+formName);
			if(INC_TYPE_BUNDLE_SME.equals(type)) {
				EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
				ApplicationGroupDataM appGroupM = (ApplicationGroupDataM) EntityForm.getObjectForm();
				ApplicationDataM appM = appGroupM.getApplicationById(appRecordID);
				//TODO remove once in uat
				if(Util.empty(appM)) {
					appM = new ApplicationDataM();
					appM.setApplicationRecordId("0000");
					appM.setBusinessClassId(PRODUCT_K_EXPRESS_CASH);
					appGroupM.addApplications(appM);
				}
				String applicationTemplate = appGroupM.getApplicationTemplate();
				if(APPLICATION_TEMPLATE_CC_BUNDLE_SME.equals(applicationTemplate)) {
					data = formName+"_"+PRODUCT_CRADIT_CARD;
				} else {
					data = formName+"_"+PRODUCT_K_EXPRESS_CASH;
				}
			} else {
				data = formName;
			}
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
