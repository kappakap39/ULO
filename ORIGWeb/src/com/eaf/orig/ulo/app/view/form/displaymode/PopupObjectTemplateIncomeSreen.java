package com.eaf.orig.ulo.app.view.form.displaymode;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormDisplayObjectHelper;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormEffects;
import com.eaf.orig.ulo.model.app.IncomeCategoryDataM;
import com.eaf.orig.ulo.model.app.IncomeInfoDataM;
import com.eaf.orig.ulo.model.app.PayslipDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;

public class PopupObjectTemplateIncomeSreen extends FormDisplayObjectHelper{
	private static final Logger logger = Logger.getLogger(PopupObjectTemplateIncomeSreen.class);
	private static String ROLE_VT = SystemConstant.getConstant("ROLE_VT");
	@Override
	public String getObjectFormMode(String objectId, String objectType,	Object objectElement) {
		Object object = FormControl.getObjectForm(request);
		String formMode = FormEffects.Effects.SHOW;
		if(object instanceof IncomeInfoDataM){
			IncomeInfoDataM incomeM = (IncomeInfoDataM)object;
			ArrayList<IncomeCategoryDataM> incomeList = incomeM.getAllIncomes();
			if(incomeList.size() > 0){
				PayslipDataM payslipM = (PayslipDataM)incomeList.get(0);
				if(payslipM.getNoMonth() != 0) {
					formMode = FormEffects.Effects.DISABLED;
				}
			}
		}
		
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		String roleId = flowControl.getRole();
		logger.debug("flowControl >> "+actionType);
		logger.debug("roleId >> "+roleId);
		if(SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType)){
			formMode = FormEffects.Effects.HIDE;
		}else if(SystemConstant.lookup("ROLE_ALL_CA_INBOX",roleId) || ROLE_VT.equals(roleId)){
			formMode = FormEffects.Effects.DISABLED;
		}
		return formMode;
	}

}
