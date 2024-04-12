package com.eaf.orig.ulo.app.view.form.cis.fields;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class MiddleNameTH extends ElementHelper implements ElementInf {
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm){
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<PersonalInfoDataM> personalInfoList = applicationGroup.getPersonalInfos();
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(personalInfoList)) {
			for(PersonalInfoDataM personalInfoM: personalInfoList) {
				CompareDataM compareData  = new CompareDataM();
				String personalRefId = CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM);
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setValue(personalInfoM.getThMidName());
				compareData.setRefId(personalRefId);
				compareData.setRefLevel(CompareDataM.RefLevel.PERSONAL);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.CIS);
				compareData.setUniqueId(personalInfoM.getPersonalId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.PERSONAL);
				compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalInfoM,""));
//				compareData.setFieldName(getImplementId()+CompareDataM.MARKER+personalRefId);
				compareList.add(compareData);
			}
		}
		return compareList;
	}
}
