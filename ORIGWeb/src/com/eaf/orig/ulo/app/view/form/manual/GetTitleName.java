package com.eaf.orig.ulo.app.view.form.manual;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;

public class GetTitleName  implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(GetTitleName.class);
	String FIELD_ID_TH_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_TH_TITLE_CODE");
	String FIELD_ID_EN_TITLE_CODE = SystemConstant.getConstant("FIELD_ID_EN_TITLE_CODE");
	String TITLE_OTHER = SystemConstant.getConstant("TITLE_OTHER");
	String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		logger.debug("GetTitleName");	
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_TITLE_NAME);
		try{
			String FIELD = "";
			String TITLE_NAME = request.getParameter("TITLE_NAME");	
			String TITLE_DESC = request.getParameter(TITLE_NAME);	
			String TITLE_CODE_NAME = request.getParameter("TITLE_CODE_NAME");			
			logger.debug("TITLE_NAME >>"+TITLE_NAME);	
			logger.debug("TITLE_DESC >>"+TITLE_DESC);	
			logger.debug("TITLE_CODE_NAME >>"+TITLE_CODE_NAME);				
			if("TH_TITLE_CODE".equals(TITLE_CODE_NAME)) {
				FIELD = FIELD_ID_TH_TITLE_CODE; // CHANGE TO ALTENATIVE Field
			}else if("EN_TITLE_CODE".equals(TITLE_CODE_NAME)) {
				FIELD = FIELD_ID_EN_TITLE_CODE; // CHANGE TO ALTENATIVE Field
			}else if("REL_TITLE_NAME".equals(TITLE_CODE_NAME)) {
				FIELD = FIELD_ID_TH_TITLE_CODE; // CHANGE TO ALTENATIVE Field
			}else if(TITLE_CODE_NAME.indexOf("TH_TITLE_CODE_PERSON") > -1) {
				FIELD = FIELD_ID_TH_TITLE_CODE; // CHANGE TO ALTENATIVE Field
			} else {
				FIELD = FIELD_ID_TH_TITLE_CODE;
			}	
			//UAT Defect : 2991,2892 #Fix title Code
//			String CID_TYPE = request.getParameter("CID_TYPE");
//			if(CIDTYPE_PASSPORT.equals(CID_TYPE)) {
//				FIELD = FIELD_ID_EN_TITLE_CODE;
//			}			
			logger.debug("FIELD : "+FIELD);
			String TITLE_CODE = ListBoxControl.getName(FIELD,"VALUE",TITLE_DESC,"CODE");	
			logger.debug(TITLE_NAME+" >> "+TITLE_CODE);	
			if(Util.empty(TITLE_CODE)){
				TITLE_CODE = TITLE_OTHER;
			}
			JSONUtil json = new JSONUtil();
			json.put(TITLE_CODE_NAME, TITLE_CODE);			
			return responseData.success(json.getJSON());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
