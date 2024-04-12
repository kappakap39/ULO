package com.eaf.orig.ulo.app.view.form.subform.product.kec;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.orig.ulo.app.view.form.sensitive.util.CompareSensitiveFieldUtil;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class KECProductElement extends ElementHelper {
	@SuppressWarnings("unused")
	private static transient Logger logger = Logger.getLogger(KECProductElement.class);

	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		return "/orig/ulo/product/kec/KECIASubform.jsp";
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
		fields.add(productElementField);
		try {
			PersonalInfoDataM personalInfo = (PersonalInfoDataM)objectForm;
			for(String fieldName : fields){
				FieldElement fieldElement =  new FieldElement();
				 fieldElement.setElementGroupId(personalInfo.getPersonalId());
				 fieldElement.setElementGroupLevel(CompareSensitiveFieldUtil.getGroupDataLevelByPersonal(personalInfo));
				fieldElement.setElementId(fieldName);
				fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION);
				fieldElementList.add(fieldElement);
			}
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElementList;
	}
}
