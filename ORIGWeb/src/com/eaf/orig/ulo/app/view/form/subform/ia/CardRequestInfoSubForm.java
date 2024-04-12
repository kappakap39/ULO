package com.eaf.orig.ulo.app.view.form.subform.ia;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.properties.RenderSubform;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.app.view.util.kpl.KPLUtil;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.LoanDataM;

public class CardRequestInfoSubForm extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(CardRequestInfoSubForm.class);
	
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)appForm);
		ArrayList<ApplicationDataM> applications = applicationGroup.filterApplicationLifeCycle();
		if(!Util.empty(applications)){
			for(ApplicationDataM applicationM :applications){
				String applicationRecordId = applicationM.getApplicationRecordId();
				LoanDataM loan = applicationM.getLoan();
				String REQUEST_LOAN_AMT = request.getParameter("REQUEST_LOAN_AMT_"+applicationRecordId);
				loan.setRequestLoanAmt(FormatUtil.toBigDecimal(REQUEST_LOAN_AMT));
			}
		}
	}
	
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		FormErrorUtil formError = new FormErrorUtil();
//		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
//		List<ApplicationDataM> appList = applicationGroup.filterApplicationLifeCycle();
//		if(Util.empty(appList)){
//			logger.debug("No products Alert!!!  ");
//			formError.error(MessageErrorUtil.getText(request,"ERROR_NO_APPLICATIONS")); 
//		}
		formError.mandatory(getSubFormID(),"VALIDATE_INCREASE_CARD_REQUEST",appForm,request);
		return formError.getFormError();
	}

	@Override
	public boolean validateSubForm(HttpServletRequest request) {
		return false;
	}
	
	@Override
	public String renderSubformFlag(HttpServletRequest request,Object objectForm) 
	{
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		if(KPLUtil.isKPL(applicationGroup))
		{
			return MConstant.FLAG_N;
		}		
		return MConstant.FLAG_Y;
	}

}
