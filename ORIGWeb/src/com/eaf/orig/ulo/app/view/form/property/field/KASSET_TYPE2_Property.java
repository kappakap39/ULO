package com.eaf.orig.ulo.app.view.form.property.field;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeDocDataM;
import com.eaf.orig.ulo.model.app.PrivilegeProjectCodeKassetDocDataM;

public class KASSET_TYPE2_Property extends FieldPropertyHelper {
@Override
public boolean invokeValidateFlag() {
	return true;
	}

@Override
public String validateFlag(HttpServletRequest request, Object objectForm) {
	int PROJECT_CODE_INDEX = 0;
	String KASSET_TYPE_MORE_6_MONTH =  SystemConstant.getConstant("KASSET_TYPE_MORE_6_MONTH");
	PrivilegeProjectCodeDataM privilegeProjectCode = (PrivilegeProjectCodeDataM)FormControl.getMasterObjectForm(request);
	if(!Util.empty(privilegeProjectCode.getPrivilegeProjectCodeDoc(PROJECT_CODE_INDEX))){
	    PrivilegeProjectCodeDocDataM  prvlgProjectDoc = privilegeProjectCode.getPrivilegeProjectCodeDoc(PROJECT_CODE_INDEX);
	   if(!Util.empty(privilegeProjectCode.getPrivilegeProjectCodeDoc(PROJECT_CODE_INDEX))){
		PrivilegeProjectCodeKassetDocDataM kassetDocDataM = prvlgProjectDoc.getPrivilegeProjectCodeKassetDoc(PROJECT_CODE_INDEX);
		if(!Util.empty(kassetDocDataM)){
				String KASSET_TYPE = kassetDocDataM.getKassetType();
				if(KASSET_TYPE_MORE_6_MONTH.equals(KASSET_TYPE)){
				return ValidateFormInf.VALIDATE_YES;
				}
			 }
		  }
		}
	return ValidateFormInf.VALIDATE_NO;
	}
}
