package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;

public class GetTitleGender implements AjaxInf {
	private static transient Logger logger = Logger.getLogger(GetTitleGender.class);
	private String CIDTYPE_PASSPORT = SystemConstant.getConstant("CIDTYPE_PASSPORT");
	private String TITLE_OTHER = SystemConstant.getConstant("TITLE_OTHER");
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_TITLE_GENDER);		
		try{
			String field = request.getParameter("field");
			String id = request.getParameter("id");
			String CID_TYPE = request.getParameter("CID_TYPE");
			String gender;
			String field_id = "";
			String idAlt;
			String desc_ID = "";
			String field_Dese = "";
			logger.debug("field : " + field);
			logger.debug("title_id : " + id);
			logger.debug("CID_TYPE : " + CID_TYPE);
			if(CIDTYPE_PASSPORT.equals(CID_TYPE)){
				field = "TH_TITLE_CODE";
			}
			
			if("TH_TITLE_CODE".equals(field)) {
				// TH
				field_id = "2";
				desc_ID = "3";
				gender = ListBoxControl.getName(field_id, id, "SYSTEM_ID2");
				idAlt = ListBoxControl.getName(field_id, id, "SYSTEM_ID1");
				field = "EN_TITLE_CODE"; // CHANGE TO ALTENATIVE Field
				field_Dese = "EN_TITLE_DESC";
			} else if("EN_TITLE_CODE".equals(field)) {
				// EN
				field_id = "3";
				desc_ID = "2";
				gender = ListBoxControl.getName(field_id, id, "SYSTEM_ID2");
				idAlt = ListBoxControl.getName(field_id, id, "SYSTEM_ID1");
				field = "TH_TITLE_CODE"; // CHANGE TO ALTENATIVE Field
				field_Dese = "TH_TITLE_DESC";
			} else {
				logger.error("Error GENDER FIELD INVALID!");
				return responseData.error();
			}
			logger.debug("Gender (SYSID2) : "+ gender);
			logger.debug("IDALT (SYSID1) : "+ idAlt);
			
			JSONUtil json = new JSONUtil();
			String TITLE_DESC = ListBoxControl.getName(desc_ID,"CODE",idAlt,"VALUE");
			logger.debug("TITLE_DESC (DisplayName) : "+ TITLE_DESC);
			if(!Util.empty(idAlt) && !TITLE_OTHER.equals(idAlt)){
				json.put(field,idAlt);
			}
			
			if(!Util.empty(gender)){
				json.put("GENDER", gender);		
			}
			if(!Util.empty(TITLE_DESC)){
				json.put(field_Dese, TITLE_DESC);
			}
			return responseData.success(json.getJSON());
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}
