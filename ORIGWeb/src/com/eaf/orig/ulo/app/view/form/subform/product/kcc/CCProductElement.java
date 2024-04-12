package com.eaf.orig.ulo.app.view.form.subform.product.kcc;

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
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class CCProductElement extends ElementHelper {
	private static transient Logger logger = Logger.getLogger(CCProductElement.class);

	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/product/kcc/CCIASubform.jsp";
	}

	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement>  fieldElementList = new ArrayList<FieldElement>();
		String productElementField = "PRODUCT_ELEMENT";
		ArrayList<String> fields = new ArrayList<String>();
		try {
			ApplicationGroupDataM applicationGroup = FormControl.getOrigObjectForm(request);
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
			if(Util.empty(personalInfo)){
				personalInfo = new PersonalInfoDataM();
			}
			String groupLevel ="";
			if(SystemConstant.getConstant("APPLICATION_TYPE_ADD_SUP").equals(applicationGroup.getApplicationType())){
				groupLevel =CompareDataM.GroupDataLevel.PERSONAL_SUPPLEMENTATY;
				fields.add("SUP_CARD_INFO");
			}else{
				groupLevel=CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo);	
				fields.add(productElementField);
			}
			
			
			
			for(String fieldName : fields){
				FieldElement fieldElement =  new FieldElement();
				fieldElement.setElementId(fieldName);
				fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				fieldElement.setElementGroupId(personalInfo.getPersonalId());
				fieldElement.setElementGroupLevel(groupLevel);
				fieldElementList.add(fieldElement);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElementList;
	}
}
