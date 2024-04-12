package com.eaf.orig.ulo.app.view.form.privilegeprojectcode.process;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ProcessActionHelper;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;

public class ConditionOfDocumentForKAsset extends ProcessActionHelper{
	private static transient Logger logger = Logger.getLogger(ConditionOfDocumentForKAsset.class);
	private String[] PROJECT_CODE = SystemConstant.getArrayConstant("PROJECT_CODE_OF_K_ASSET");
	@Override
	public Object processAction() {
		int PRVLG_PROJECT_DOC_INDEX=0;
		PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)objectForm;		
		try {
			PrivilegeProjectCodeDocDataM   privilegeProjectCodeDoc =  privilegeProjectCode.getPrivilegeProjectCodeDoc(PRVLG_PROJECT_DOC_INDEX);
			if(SystemConstant.getConstant("PRVLG_DOC_TYPE_K_ASSET").equals(privilegeProjectCodeDoc.getDocType())){
				return PROJECT_CODE;
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return null;
	}
}
