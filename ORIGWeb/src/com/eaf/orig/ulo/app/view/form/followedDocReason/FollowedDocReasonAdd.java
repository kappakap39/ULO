package com.eaf.orig.ulo.app.view.form.followedDocReason;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.app.util.GenerateUnique;
import com.eaf.orig.ulo.constant.MConstant;
import com.eaf.orig.ulo.control.util.PersonalInfoUtil;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListDataM;
import com.eaf.orig.ulo.model.app.DocumentCheckListReasonDataM;
import com.eaf.orig.ulo.model.app.PersonalInfoDataM;
import com.eaf.orig.ulo.model.control.FlowControlDataM;
import com.eaf.orig.ulo.model.document.DocumentRequestDataM;

public class FollowedDocReasonAdd extends ElementHelper {
	private static transient Logger logger = Logger.getLogger(FollowedDocReasonAdd.class);
	String ROLE_FU = SystemConstant.getConstant("ROLE_FU");
	String ROLE_CA = SystemConstant.getConstant("ROLE_CA");
	String ROLE_VT = SystemConstant.getConstant("ROLE_VT");
	String VERIFY_CUSTOMER_FORM = SystemConstant.getConstant("VERIFY_CUSTOMER_FORM"); 
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {
		String  urlValue = (String)objectElement;
		return "/orig/ulo/subform/element/FollowedDocReasonAddElement.jsp?"+urlValue;
	}	
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
	@Override
	public String renderElementFlag(HttpServletRequest request,	Object objectElement) {
		FlowControlDataM flowControl = (FlowControlDataM)request.getSession().getAttribute("FlowControl");
		String actionType = flowControl.getActionType();
		String renderElementFlag = MConstant.FLAG.NO;
		String roleId = FormControl.getFormRoleId(request);	
		String formId = FormControl.getFormId(request);
		logger.debug("roleId : "+roleId);
		logger.debug("formId : "+formId);
		if(!ROLE_FU.equals(roleId) && !VERIFY_CUSTOMER_FORM.equals(formId) && (!SystemConstant.lookup("ACTION_TYPE_ENQUIRY",actionType) && !ROLE_CA.equals(roleId) && !ROLE_VT.equals(roleId))){
			renderElementFlag = MConstant.FLAG.YES;
		}
		logger.debug("renderElementFlag : "+renderElementFlag);
		return renderElementFlag;
	}	
	@Override
	public String processElement(HttpServletRequest request,Object objectElement) {
		ApplicationGroupDataM applicationGroup = ((ApplicationGroupDataM)objectElement);
		DocumentRequestDataM  documentRequest = (DocumentRequestDataM)getObjectRequest();
		String applicantType = documentRequest.getApplicantType();
		String documantCode = documentRequest.getDocumentCode();
		String personalId = documentRequest.getPersonalID();
		String docTypeId = documentRequest.getDocTypeId();		
		logger.debug("applicantType : "+applicantType);
		logger.debug("documantCode : "+documantCode);
		logger.debug("personalId : "+personalId);
		logger.debug("docTypeId : "+docTypeId);		
		if(!Util.empty(personalId)){
			String elementObjectId = documantCode+"_"+applicantType+"_"+personalId;
			logger.debug("elementObjectId : "+elementObjectId);
			PersonalInfoDataM personalInfo = applicationGroup.getPersonalById(personalId);
			ArrayList<DocumentCheckListReasonDataM> documentReasons = new ArrayList<DocumentCheckListReasonDataM>();
			String remark = request.getParameter("FOLLOWED_REMARK_"+elementObjectId);
			String[] reasons = request.getParameterValues("FOLLOWED_DOC_REASON_SELECT_"+elementObjectId);
			logger.debug("remark : "+remark);
			logger.debug("reasons : "+reasons);
			if((reasons != null && reasons.length > 0) || !Util.empty(remark)) {
				String IMApplicatType = PersonalInfoUtil.getIMPersonalType(personalInfo);
				DocumentCheckListDataM documentCheckList = applicationGroup.getDocumentCheckList(IMApplicatType, documantCode);
				if(null == documentCheckList){
					documentCheckList = new DocumentCheckListDataM();
					documentCheckList.setApplicantTypeIM(IMApplicatType);
					documentCheckList.setDocumentCode(documantCode);
					documentCheckList.setDocTypeId(docTypeId);
					String docCheckListId = GenerateUnique.generate(OrigConstant.SeqNames.ORIG_DOC_CHECK_LIST_PK);
					documentCheckList.setDocCheckListId(docCheckListId);
					applicationGroup.addDocumentCheckList(documentCheckList);
				}
				if(!Util.empty(reasons)){
					for(String reasonCode : reasons) {
						DocumentCheckListReasonDataM reasonM = new DocumentCheckListReasonDataM();
						reasonM.setDocReason(reasonCode);
						documentReasons.add(reasonM);
					}
				}
				documentCheckList.setDocumentCheckListReasons(documentReasons);
				documentCheckList.setRemark(remark);
			}
			PersonalInfoUtil.defaultPersonalDocumentCheckListRelation(applicationGroup,personalId,applicantType,personalInfo.getSeq());
		}		
		return null;
	}
}
