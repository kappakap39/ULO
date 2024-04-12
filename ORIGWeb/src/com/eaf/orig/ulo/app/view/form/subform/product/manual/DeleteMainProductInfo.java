package com.eaf.orig.ulo.app.view.form.subform.product.manual;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;

public class DeleteMainProductInfo implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(DeleteMainProductInfo.class);
	String PRODUCT_CRADIT_CARD = SystemConstant.getConstant("PRODUCT_CRADIT_CARD");
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_MAIN_PRODUCT);
		try{
			String uniqueId = request.getParameter("uniqueId");
			logger.debug("uniqueId >> " + uniqueId);
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
						if (null != applicationRecordId && applicationRecordId.equals(uniqueId)) {
							appIter.remove();
						} else if (null != referApplicationRecordId && referApplicationRecordId.equals(uniqueId)) {
							appIter.remove();
						}
					} else {
						if (null != applicationRecordId && applicationRecordId.equals(uniqueId)) {
							appIter.remove();
						}
					}
				}
			}
			ApplicationUtil.clearNotMatchApplicationRelation(applicationGroup);
			return responseData.success();
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
