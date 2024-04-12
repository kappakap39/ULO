package com.eaf.orig.ulo.app.view.util.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.j2ee.pattern.util.JSONUtil;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.app.view.form.displaymode.ConsentModelDisplayMode;
import com.eaf.orig.ulo.model.app.PersonalApplicationInfoDataM;
public class GetDocumentPersonal  implements AjaxInf  {
	private static transient Logger logger = Logger.getLogger(GetDocumentPersonal.class);

	
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.GET_DOCUMENT_PERSONAL);
		EntityFormHandler EntityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");
		ConsentModelDisplayMode docCheck = new ConsentModelDisplayMode();
		try{
			PersonalApplicationInfoDataM personalApplicantInfo = new PersonalApplicationInfoDataM();
			if(EntityForm != null){
			 personalApplicantInfo = (PersonalApplicationInfoDataM)EntityForm.getObjectForm();
			}
			JSONUtil json = new JSONUtil();
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			PersonalInfoDataM personalInfo;
			String personalType =  request.getParameter("PERSON_TYPE");
			if(personalApplicantInfo == null){
				 personalInfo = applicationGroup.getPersonalInfo(personalType);
				
			}else{
				 personalInfo = personalApplicantInfo.getPersonalInfo();
			}
			logger.debug("application Group "+applicationGroup.getApplicationGroupId());
			
			if(null == personalInfo){
				personalInfo = new PersonalInfoDataM();
				personalInfo.setPersonalType(personalType);
			}
			logger.debug("displayMode personalInfoType >> " + personalInfo.getIdno());
			String doucumentCode = SystemConstant.getConstant("DOCUMENT_TYPE_NON_THAI_NATIONALITY");
			// get Check Document of Personal
			boolean check= docCheck.checkReceiveDoc(applicationGroup,personalInfo,doucumentCode);
			if(check){ // has Document
				json.put("check","y");
				personalInfo.setCidType("04");
			}else{ // don't have 	
				json.put("check","n");
			} 
			return responseData.success(json.getJSON());
		}catch(Exception e){
			logger.debug("ERROR >>>"+e);
			//logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}

}
