package com.eaf.orig.ulo.app.view.form.property.field;

import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.display.HtmlUtil;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.SystemConfig;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;

public class POLICY_EXCEPTION_SIGN_OFF_SUBFORMProperty  extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(POLICY_EXCEPTION_SIGN_OFF_SUBFORMProperty.class);
	String DOC_TYPE_POLICY_EXCEPTION = SystemConfig.getGeneralParam("DOC_TYPE_POLICY_EXCEPTION");
	private String ELEMENT_ID = "";
	@Override
	public boolean invokeDisplayMode() {
		return true;
	}

	@Override
	public String displayMode(Object objectElement, HttpServletRequest request) {
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
		ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
		if(!Util.empty(applicationImages)){
			for (ApplicationImageDataM applicationImage : applicationGroup.getApplicationImages()) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
					if(!Util.empty(applicationImageSplits)){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
						if(!Util.empty(applicationImageSplit)){
							if(DOC_TYPE_POLICY_EXCEPTION.equals(applicationImageSplit.getDocType())){
								return HtmlUtil.EDIT;
							}
						}
					}
				}
			}
		}
		return HtmlUtil.VIEW;
	}
	
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
		ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
		if(null != applicationImages && !Util.empty(mandatoryConfig)){
			for (ApplicationImageDataM applicationImage : applicationGroup.getApplicationImages()) {
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
					if(null != applicationImageSplits){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
						if(null != applicationImageSplit){
							if(DOC_TYPE_POLICY_EXCEPTION.equals(applicationImageSplit.getDocType())){
								return ValidateFormInf.VALIDATE_SUBMIT;
							}
						}
					}
				}
			}
		}
		return ValidateFormInf.VALIDATE_NO;
	}

	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)objectForm;
		ApplicationGroupDataM lastApplicationGroup = (ApplicationGroupDataM)lastObjectForm;
		
		Date policyExSignOffDate = applicationGroup.getPolicyExSignOffDate();
		Date lastPolicyExSignOffDate = lastApplicationGroup.getPolicyExSignOffDate();
		String policySignOffBy = applicationGroup.getPolicyExSignOffBy();
		String lastPsolicySignOffBy = lastApplicationGroup.getPolicyExSignOffBy();
	
		if("POLICY_EXCEPTION_SIGN_OFF_DATE".equals(ELEMENT_ID)){
			boolean compareSpSignOffDate = CompareObject.compare(policyExSignOffDate, lastPolicyExSignOffDate, null);
			logger.debug("compareSpSignOffDate >> " + compareSpSignOffDate);
			if (!compareSpSignOffDate) {
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, "POLICY_EXCEPTION_SIGN_OFF_DATE"));
				audit.setOldValue(FormatUtil.display(lastPolicyExSignOffDate ,FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
				audit.setNewValue(FormatUtil.display(policyExSignOffDate ,FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
				audits.add(audit);
			}
		}
		
		if("POLICY_EXCEPTION_AUTHORIZED_BY".equals(ELEMENT_ID)){
			boolean compareSpSignOffBy = CompareObject.compare(policySignOffBy, lastPsolicySignOffBy, null);
			logger.debug("compareSpSignOffBy >> " + compareSpSignOffBy);
			if (!compareSpSignOffBy) {
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, "POLICY_EXCEPTION_AUTHORIZED_BY"));
				audit.setOldValue(FormatUtil.display(lastPsolicySignOffBy));
				audit.setNewValue(FormatUtil.display(policySignOffBy));
				audits.add(audit);
			}
		}
		return audits;
	}@Override
	public void auditInfo(String subformId, String elementId) {
		this.ELEMENT_ID = elementId;
	}
}
