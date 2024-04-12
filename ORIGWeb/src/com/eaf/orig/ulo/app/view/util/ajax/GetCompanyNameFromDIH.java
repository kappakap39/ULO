package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.CacheControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ErrorController;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.util.dih.DIHProxy;
import com.eaf.orig.ulo.app.view.util.dih.model.DIHQueryResult;
import com.eaf.orig.ulo.control.util.ApplicationUtil;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class GetCompanyNameFromDIH implements AjaxInf  {
	private static transient Logger logger = Logger.getLogger(GetCompanyNameFromDIH.class);
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DIH_FIND_COMPANY_NAME);
		DIHQueryResult<String>  dihQueryResult =  new DIHQueryResult<String>();
		try{
			String COMPANY_TITLE = request.getParameter("COMPANY_TITLE");
			String COMPANY_NAME = request.getParameter("COMPANY_NAME");
			String COMPANY_TITLE_DESC = CacheControl.getName(SystemConstant.getConstant("FIELD_ID_COMPANY_TITLE"), COMPANY_TITLE,"MAPPING4"); 
			logger.debug("COMPANY_TITLE >> "+COMPANY_TITLE);	
			logger.debug("COMPANY_NAME >> "+COMPANY_NAME);
			logger.debug("COMPANY_TITLE_DESC >> "+COMPANY_TITLE_DESC);
			PersonalInfoDataM personalInfo =PersonalInfoUtil.getPersonalInfoObjectForm(request);
			DIHProxy dihProxy = new DIHProxy();
			HashMap<String, CompareDataM> comparisonFields = ApplicationUtil.getCISComparisonField(request);
			if(Util.empty(comparisonFields)){
				comparisonFields = new HashMap<String, CompareDataM>();
			}
			 dihQueryResult = dihProxy.getCisCompanyInfo(COMPANY_TITLE_DESC,COMPANY_NAME,personalInfo,comparisonFields);	
			if(!ResponseData.SUCCESS.equals(dihQueryResult.getStatusCode())){
				return responseData.error(dihQueryResult);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e,ErrorController.ERROR_MESSAGE_NAME.ERROR_REFER_TO_WORK_SHEET_DIH);
		}
		return responseData.success(dihQueryResult.getResult());
	}
}
