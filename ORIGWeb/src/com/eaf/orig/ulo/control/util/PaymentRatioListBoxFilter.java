package com.eaf.orig.ulo.control.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ListBoxFilterInf;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PaymentRatioListBoxFilter implements ListBoxFilterInf {	
	@Override
	public ArrayList<HashMap<String, Object>> filter(String configId,String cacheId, String typeId, HttpServletRequest request) {	
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		ORIGFormHandler ORIGForm = (ORIGFormHandler)request.getSession().getAttribute(ORIGFormHandler.ORIGForm);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		ArrayList<ApplicationDataM> applications = applicationGroup.getApplications();
		boolean foundLowIncomeFlag = false;
		for (ApplicationDataM applicationDataM : applications) {
			if ( null != applicationDataM && null != applicationDataM.getLowIncomeFlag() && MConstant.FLAG_Y.equals( applicationDataM.getLowIncomeFlag() ) ) {
				foundLowIncomeFlag = true;
			}
		}
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
			return ListBoxControl.getListBox(cacheId,typeId);
		}
		if( foundLowIncomeFlag ){
			return ListBoxControl.getListBox(cacheId,typeId);
		}
		return ListBoxControl.search(cacheId,typeId,"SYSTEM_ID3",MConstant.FLAG_Y);
	}
	@Override
	public void setFilterProperties(String configId, String cacheId, String typeId,HttpServletRequest request) {
		
	}
	@Override
	public void init(Object objectForm) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setElementDisplayMode(String displayMode) {
		
	}
}
