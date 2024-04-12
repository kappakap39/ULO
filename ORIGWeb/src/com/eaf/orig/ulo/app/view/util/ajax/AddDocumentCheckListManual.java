package com.eaf.orig.ulo.app.view.util.ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.model.ResponseData;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.j2ee.pattern.control.AjaxInf;
import com.eaf.j2ee.pattern.control.ResponseDataController;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.formcontrol.view.form.ORIGFormHandler;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;

public class AddDocumentCheckListManual implements AjaxInf {
	
	private static transient Logger logger = Logger.getLogger(AddDocumentCheckListManual.class);
	
	@Override
	public ResponseData processAction(HttpServletRequest request) {
		String DOC_CHECKLIST_MANUAL_FLAG_INCOME = SystemConstant.getConstant("DOC_CHECKLIST_MANUAL_FLAG_INCOME");
		String DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL = SystemConstant.getConstant("DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL");
		String title = "Add Document Check List Manual";
		logger.debug(title);
		ResponseDataController responseData = new ResponseDataController(request, title);
		String subformId;
		try {
			ORIGFormHandler origHandler = (ORIGFormHandler) request.getSession().getAttribute("ORIGForm");
			ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM) origHandler.getObjectForm();
			String manualFlag = request.getParameter("manualFlag");
			
			ArrayList<DocumentCheckListDataM> docCheckListManuals;
			if(DOC_CHECKLIST_MANUAL_FLAG_INCOME.equals(manualFlag)) {
				subformId = "DOCUMENT_CHECK_LIST_MANUAL_ADD";
				docCheckListManuals = applicationGroup.getDocumentCheckListManuals();
				if(null == docCheckListManuals) {
					docCheckListManuals = new ArrayList<>();
					applicationGroup.setDocumentCheckListManuals(docCheckListManuals);
				}
			} else if(DOC_CHECKLIST_MANUAL_FLAG_ADDITIONAL.equals(manualFlag)) {
				subformId = "DOCUMENT_CHECK_LIST_MANUAL_ADD_ADDITIONAL";
				docCheckListManuals = applicationGroup.getDocumentCheckListManualAdditionals();
				if(null == docCheckListManuals) {
					docCheckListManuals = new ArrayList<>();
					applicationGroup.setDocumentCheckListManualAdditionals(docCheckListManuals);
				}
			} else {
				throw new IllegalArgumentException("Invalid Manual Flag Value: Only accept 'INCOME' or 'ADDITIONAL'.");
			}
			
			DocumentCheckListDataM docCheckList = new DocumentCheckListDataM();
			docCheckList.setDocCheckListId(GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHECK_LIST_PK));
			docCheckListManuals.add(docCheckList);
		} catch(Exception e) {
			logger.fatal("ERROR ",e);
			return responseData.error(e);
		}
		return responseData.success(subformId);
	}

}
