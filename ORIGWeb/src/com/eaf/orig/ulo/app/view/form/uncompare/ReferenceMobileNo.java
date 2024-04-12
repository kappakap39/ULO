package com.eaf.orig.ulo.app.view.form.uncompare;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ReferencePersonDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ReferenceMobileNo extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ReferenceMobileNo.class);
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();
		ArrayList<ReferencePersonDataM> referencePersonList = applicationGroup.getReferencePersons();
		if(!Util.empty(referencePersonList)) {
			for(ReferencePersonDataM referencePerson : referencePersonList) {
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setValue(referencePerson.getMobile());
				compareData.setRole(roleId);
				compareData.setRefId(applicationGroup.getApplicationGroupId()+CompareDataM.MARKER+referencePerson.getSeq());
				compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION_GROUP);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				compareData.setUniqueId(FormatUtil.toString(referencePerson.getSeq()));
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION_GROUP);
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
				String configData = CompareSensitiveFieldUtil.unCompareFieldConfigDataMToJSonString(applicationGroup,"");
//				logger.debug("configData : "+configData);
				compareData.setConfigData(configData);
				compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));
				compareList.add(compareData);
			}
		}
		return compareList;
	}

}
