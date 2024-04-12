package com.eaf.orig.ulo.app.view.form.subform.product.manual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.message.MessageUtil;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.google.gson.Gson;

public class ConfirmMessageDeleteCard implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteMainProductInfo.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String PERSONAL_TYPE_SUPPLEMENTARY = SystemConstant.getConstant("PERSONAL_TYPE_SUPPLEMENTARY");
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_MAIN_PRODUCT);
		HashMap<String, String> data = new HashMap<String, String>();
		boolean errMsg = true;
		try{
			String uniqueId =  request.getParameter("uniqueId");
			String applicationType =  request.getParameter("applicationType");
			logger.debug("uniqueId >> "+uniqueId);
			logger.debug("applicationType >> "+applicationType);
			data.put("uniqueId", uniqueId);
			data.put("applicationType", applicationType);
			// This statement make sure you use correct model
			Object masterObjectForm = FormControl.getMasterObjectForm(request);
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) masterObjectForm;
			ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
			if (null != applications) {
				Iterator<ApplicationDataM> appIter = applications.iterator();
				while(appIter.hasNext()) {
					ApplicationDataM application = appIter.next();
					String product = application.getProduct();
					String applicationRecordId = application.getApplicationRecordId();
					if (PRODUCT_CRADIT_CARD.equals(product)) {
						String referApplicationRecordId = application.getMaincardRecordId();
						String personalType = applicationGroup.getPersonalInfoByRelation(applicationRecordId).getPersonalType();
						logger.debug("personalType >>"+personalType);
						if(null != referApplicationRecordId && referApplicationRecordId.equals(uniqueId) && PERSONAL_TYPE_SUPPLEMENTARY.equals(personalType)){
							errMsg = false;
							data.put("conFirmMessage", MessageUtil.getText(request,"MSG_CONFIRM_DELETE_MAIN_SUP_ROW"));
						}
					}
				}
					if(errMsg){
						data.put("conFirmMessage", MessageUtil.getText(request,"MSG_CONFIRM_DELETE_ROW"));
					}
			}
			return responseData.success(new Gson().toJson(data));
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
