package com.eaf.orig.ulo.app.view.form.cis.fields;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class PhoneNoIncrease extends ElementHelper implements ElementInf {	
	private String PERSONAL_TYPE_APPLICANT = SystemConstant.getConstant("PERSONAL_TYPE_APPLICANT");
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm){
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
//		ArrayList<PersonalInfoDataM> personalInfoList = applicationGroup.getPersonalInfos();
		PersonalInfoDataM personalinfo = applicationGroup.getPersonalInfo(PERSONAL_TYPE_APPLICANT);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		if(!Util.empty(personalinfo)) {
				CompareDataM compareData  = new CompareDataM();
				String personalRefId = CompareSensitiveFieldUtil.getPersonalRefId(personalinfo);
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setValue(personalinfo.getMobileNo());
				compareData.setRefId(personalRefId);
				compareData.setRefLevel(CompareDataM.RefLevel.PERSONAL);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.CARD_LINK);
				compareData.setUniqueId(personalinfo.getPersonalId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.PERSONAL);
				compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
				compareData.setConfigData(CompareSensitiveFieldUtil.fieldConfigDataMToJSonString(personalinfo,""));
				compareList.add(compareData);
			
		}
		return compareList;
	}
}