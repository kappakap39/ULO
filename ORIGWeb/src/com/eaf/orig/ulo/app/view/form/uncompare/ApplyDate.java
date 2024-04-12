package com.eaf.orig.ulo.app.view.form.uncompare;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ApplyDate extends ElementHelper implements ElementInf {
	private static transient Logger logger = Logger.getLogger(ApplyDate.class);
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();		
		CompareDataM compareData  = new CompareDataM();
		compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
		compareData.setFieldNameType(getImplementId());
		compareData.setValue(FormatUtil.display(applicationGroup.getApplyDate(),HtmlUtil.EN));
		compareData.setRole(roleId);
		compareData.setRefId(applicationGroup.getApplicationGroupId());
		compareData.setRefLevel(CompareDataM.RefLevel.APPLICATION_GROUP);
		compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
		compareData.setUniqueId(applicationGroup.getApplicationGroupId());
		compareData.setUniqueLevel(CompareDataM.UniqueLevel.APPLICATION_GROUP);
		compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
		compareData.setConfigData(CompareSensitiveFieldUtil.unCompareFieldConfigDataMToJSonString(applicationGroup,""));
		compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));

		compareList.add(compareData);
		return compareList;
	}
}
