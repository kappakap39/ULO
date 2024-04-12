package com.eaf.orig.ulo.app.view.form.uncompare;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.AddressDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class DocumentAddressMoo extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(DocumentAddressMoo.class);	
	String ADDRESS_TYPE_DOCUMENT = SystemConstant.getConstant("ADDRESS_TYPE_DOCUMENT");
	
	@Override
	public <T>Object getObjectElement(HttpServletRequest request,Object objectForm,FieldElement fieldElement){
		String roleId = FormControl.getFormRoleId(request);
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);
		PersonalInfoDataM personalInfoM = applicationGroup.getPersonalById(fieldElement.getElementGroupId());
		ArrayList<CompareDataM> compareList = new ArrayList<CompareDataM>();		
		if(!Util.empty(personalInfoM)) {
			AddressDataM address =   personalInfoM.getAddress(ADDRESS_TYPE_DOCUMENT);
			if(!Util.empty(address)) {
				String addressRefId = CompareSensitiveFieldUtil.getAddressRefId(personalInfoM,ADDRESS_TYPE_DOCUMENT);
				CompareDataM compareData  = new CompareDataM();
				compareData.setApplicationGroupId(applicationGroup.getApplicationGroupId());
				compareData.setFieldNameType(getImplementId());
				compareData.setValue(address.getMoo());
				compareData.setRole(roleId);
				compareData.setRefId(CompareSensitiveFieldUtil.getPersonalRefId(personalInfoM));
				compareData.setCurrentRefId(addressRefId);
				compareData.setRefLevel(CompareDataM.RefLevel.ADDRESS);
				compareData.setSrcOfData(CompareDataM.SoruceOfData.TWO_MAKER);
				compareData.setUniqueId(address.getAddressId());
				compareData.setUniqueLevel(CompareDataM.UniqueLevel.ADDRESS);
				compareData.setSeq(CompareSensitiveFieldUtil.getCompareFieldSeq(getImplementId()));
				compareData.setConfigData(CompareSensitiveFieldUtil.unCompareFieldConfigDataMToJSonString(personalInfoM,""));
				compareData.setFieldName(CompareSensitiveFieldUtil.getFieldName(compareData));

				compareList.add(compareData);
			}
		}
		return compareList;
	}

}
