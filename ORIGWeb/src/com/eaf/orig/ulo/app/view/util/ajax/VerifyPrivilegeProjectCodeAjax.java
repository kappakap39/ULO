package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionInf;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ImplementControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.formcontrol.view.form.EntityFormHandler;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeRccmdPrjCdeDataM;

public class VerifyPrivilegeProjectCodeAjax  implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(VerifyPrivilegeProjectCodeAjax.class);
	private String PREFIX_IMPLEMENTID ="PROJECT_CODE_OF_DOC_TYPE_";	
	@Override
	public ResponseData processAction(HttpServletRequest request){
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.VERIFY_PRVLG_PROJECTCODE);
		String data = "VERIFY_PRIVILEGE_PROJECT_CODE_SUBFORM";
		try{
			String documentType = request.getParameter("DOCUMENT_TYPE");
			EntityFormHandler entityForm = (EntityFormHandler)request.getSession().getAttribute("EntityForm");		
			String roleId = FormControl.getFormRoleId(request);
			PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)entityForm.getObjectForm();	
			
			ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>  projectRccList = new ArrayList<PrivilegeProjectCodeRccmdPrjCdeDataM>();
			logger.debug("DOCUMENT_TYPE ="+documentType);
			ProcessActionInf processActionInf = ImplementControl.getProcessAction("PRIVILEGE_PROJECT_CODE", PREFIX_IMPLEMENTID+documentType);
			processActionInf.init(request, privilegeProjectCode, "", "",roleId);
			String[] resultFromProcess =(String[])processActionInf.processAction();			
			if(!Util.empty(resultFromProcess)){
				for(String proejectCode :resultFromProcess){
					logger.debug("Proejct Code="+proejectCode);
					
					PrivilegeProjectCodeRccmdPrjCdeDataM PrvlgRccmdPrjCde = new PrivilegeProjectCodeRccmdPrjCdeDataM();
					PrvlgRccmdPrjCde.setProjectCode(proejectCode);
					projectRccList.add(PrvlgRccmdPrjCde);
				}
				privilegeProjectCode.setPrivilegeProjectCodePrjCdes(projectRccList);
			}
			/*if(MConstant.FLAG.YES.equals(resultFromProcess)){
				entityForm.setFormData(SystemConstant.getConstant("PRVLG_FORM_DATA_NAME"),MConstant.FLAG.YES);
			}*/			
			return responseData.success(data);
		}catch(Exception e){
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
	}
}

