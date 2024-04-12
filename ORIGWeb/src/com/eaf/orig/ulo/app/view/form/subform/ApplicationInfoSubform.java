package com.eaf.orig.ulo.app.view.form.subform;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.orig.shared.view.form.ORIGSubForm;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ApplicationInfoSubform extends ORIGSubForm{
	private static transient Logger logger = Logger.getLogger(ApplicationInfoSubform.class);
	@Override
	public void setProperties(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;		
		String APPLY_DATE = request.getParameter("APPLY_DATE");			
		logger.debug("APPLY_DATE >> "+APPLY_DATE);				
		applicationGroup.setApplyDate(FormatUtil.toDate(APPLY_DATE,FormatUtil.TH));
	}
	@Override
	public HashMap validateForm(HttpServletRequest request, Object appForm) {
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)appForm;
		FormErrorUtil formError = new FormErrorUtil();
		String subformId = "APPLICATION_INFO_SUBFORM";
		logger.debug("subformId >> "+subformId);
		formError.mandatory(subformId,"APPLY_DATE",applicationGroup,request);	
		return formError.getFormError();
	}
	@Override
	public boolean validateSubForm(HttpServletRequest request){
		return false;
	}	
	
	@Override
	public ArrayList<FieldElement> elementForm(HttpServletRequest request,Object objectForm) {
		ArrayList<FieldElement> fieldElements = new ArrayList<FieldElement>();
		ArrayList<String> filedNames = new ArrayList<String>();
		filedNames.add("APPLY_DATE");
		try {
			ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectForm);			
			 for(String elementId:filedNames){
				 FieldElement fieldElement = new FieldElement();
				 fieldElement.setElementId(elementId);
				 fieldElement.setElementLevel(CompareDataM.UniqueLevel.APPLICATION_GROUP);
				 fieldElement.setElementGroupId(applicationGroup.getApplicationGroupId());
				 fieldElement.setElementGroupLevel(CompareDataM.GroupDataLevel.APPLICATION_GROUP);
				 fieldElements.add(fieldElement);
			 }
			
		} catch (Exception e) {
			logger.fatal("ERROR",e);
		}
		return fieldElements;
	}
}
