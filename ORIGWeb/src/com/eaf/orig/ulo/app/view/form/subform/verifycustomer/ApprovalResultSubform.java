package com.eaf.orig.ulo.app.view.form.subform.verifycustomer;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.SessionControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class ApprovalResultSubform extends ORIGSubForm {
	private static transient Logger logger = Logger.getLogger(ApprovalResultSubform.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) appForm;
		ArrayList<String> products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
		if(!Util.empty(products)){
			for (String product : products) {
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.APPROVAL_RESULT,product);
				if(null != element){
					element.processElement(request,applicationGroup);
				}
			}
		}		
	}
	
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) appForm;
		ArrayList<String> products = applicationGroup.getProducts(SystemConstant.getArrayConstant("DISPLAY_PRODUCT"));
		if(!Util.empty(products)){
			for (String product : products) {
				ElementInf element = ImplementControl.getElement(MConstant.IMPLEMENT_TYPE.APPROVAL_RESULT,product);
				if(null != element){
					formError.addAll(element.validateElement(request,applicationGroup));
					
				}
			}
		}	
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm)
	{
		String ACTION_TYPE_ENQUIRY = SystemConstant.getConstant("ACTION_TYPE_ENQUIRY");
		String renderFlag = MConstant.FLAG_N;
		FlowControlDataM flowControl = (FlowControlDataM) request.getSession().getAttribute(SessionControl.FlowControl);
		//This subform will only render if open from General Inquiry page
		if(flowControl != null && ACTION_TYPE_ENQUIRY.equals(flowControl.getActionType()))
		{
			renderFlag = MConstant.FLAG_Y;
		}
		
		logger.debug("renderSubformFlag - ApprovalResultSubform : " + renderFlag);
		return renderFlag;
	}
}
