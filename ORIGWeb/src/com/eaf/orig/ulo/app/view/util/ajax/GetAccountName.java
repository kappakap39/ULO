package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.app.dao.ORIGDAOFactory;
import com.eaf.orig.ulo.app.dao.OrigPersonalAccountDAO;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;


public class GetAccountName implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(GetAccountName.class);
	String PERSONAL_TYPE = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");	
	String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_CALCULATE_AGE);		
		try{
		JSONUtil json = new JSONUtil();
		String accountNo =  request.getParameter("ACCOUNT_NO");
		ORIGFormHandler ORIGForm = ORIGFormHandler.getForm(request);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		PersonalInfoDataM personalInfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE);
		String personalId = personalInfo.getPersonalId();
		logger.debug("GetAccountName personalId >> "+personalId);	
		logger.debug("GetAccountName accountNo >> "+accountNo);	
		OrigPersonalAccountDAO accountDAO = ORIGDAOFactory.getAccountDAO();
		String  accountName = accountDAO.loadAccountName(personalId,accountNo);
		
		json.put("accountName",accountName);
		return responseData.success(json.getJSON());
		}catch(Exception e){
			logger.debug("ERROR >>>"+e);
			//logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}

}

