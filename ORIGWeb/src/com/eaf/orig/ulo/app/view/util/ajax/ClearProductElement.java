package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.CompareFieldElement;
import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.ulo.app.view.util.manual.SensitiveFieldAction;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CardDataM;
import com.eaf.orig.ulo.model.compare.CompareDataM;

public class ClearProductElement implements AjaxInf{
	private static transient Logger logger = Logger.getLogger(ClearProductElement.class);
	private static final String SUCCESS = "SUCCESS";
	private String PRODUCT_ELEMENT = SystemConstant.getConstant("PRODUCT_ELEMENT");
	private static final String POPUP_FORM = "REKEY_SENSITIVE_FORM";
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		ResponseDataController responseData = new ResponseDataController(request,ResponseData.FunctionId.DELETE_APPLICATION);
		HashMap<String,ArrayList<CompareFieldElement>> hCompareFieldElementList = (HashMap<String,ArrayList<CompareFieldElement>>)request.getSession().getAttribute(SensitiveFieldAction.REKEY_FIELDS);
		ArrayList<String> applicationTypes = new ArrayList<String>();
		ORIGFormHandler ORIGForm = ORIGFormHandler.getForm(request);
		ApplicationGroupDataM applicationGroup = ORIGForm.getObjectForm();
		Set<String> keyElement = hCompareFieldElementList.keySet();
		if(!Util.empty(keyElement)){
			for(String key : keyElement){
				ArrayList<CompareFieldElement> compareFieldElements = hCompareFieldElementList.get(key);
				if(!Util.empty(compareFieldElements)){
					for(Iterator<CompareFieldElement> compareFieldIterator = compareFieldElements.iterator();compareFieldIterator.hasNext();){
						CompareFieldElement	compareFieldElement = compareFieldIterator.next();
						for(Iterator<CompareDataM> compareDataIterator = compareFieldElement.getCompareDatas().iterator();compareDataIterator.hasNext();){
							CompareDataM compareData = compareDataIterator.next();
							if(PRODUCT_ELEMENT.equals(compareData.getFieldNameType())){
								compareDataIterator.remove();
								if(Util.empty(compareFieldElement.getCompareDatas())){
									compareFieldIterator.remove();
									if(Util.empty(compareFieldElements))
										hCompareFieldElementList.remove(key);
								}
							}
						}
					}
				}
			}
		}
		logger.debug("Delete Application : hCompareFieldElementList size >> "+hCompareFieldElementList);
		if(!Util.empty(hCompareFieldElementList)) {
			return responseData.success(POPUP_FORM);
		}
		return responseData.success(SUCCESS);
	}

}
