package com.eaf.orig.ulo.app.view.form.ProductForm;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.model.FieldElement;
import com.eaf.core.ulo.common.properties.ImplementControl;
//import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class AddProductForm extends ElementHelper {
	private static transient Logger logger = Logger.getLogger(ImplementControl.class);
//	String COVERPAGE_TYPE_VETO = SystemConstant.getConstant("COVERPAGE_TYPE_VETO");	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String  subformId= (String)objectElement;
		return "/orig/ulo/subform/element/AddProductFormElement.jsp?subformId="+subformId;
	}
	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}

	@Override
	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
		String FLAG = MConstant.FLAG.YES;
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)getObjectRequest();
		if(null == applicationGroup){
			applicationGroup = new ApplicationGroupDataM();
		}
		logger.debug("IS_VETO : "+applicationGroup.isVeto());
//		logger.debug("COVERPAGE_TYPE : "+applicationGroup.getCoverpageType());
//		if(applicationGroup.isVeto() || COVERPAGE_TYPE_VETO.equals(applicationGroup.getCoverpageType())){
		if(applicationGroup.isVeto()){
			FLAG = MConstant.FLAG.NO;
		}		
		return FLAG;
	}
	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		return null;
	}
}
