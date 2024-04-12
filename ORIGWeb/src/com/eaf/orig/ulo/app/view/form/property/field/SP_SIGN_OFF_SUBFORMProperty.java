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
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageDataM;
import com.eaf.orig.ulo.model.app.ApplicationImageSplitDataM;

public class SP_SIGN_OFF_SUBFORMProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(SP_SIGN_OFF_SUBFORMProperty.class);
	String DOC_TYPE_SP_SIGN_OFF = SystemConfig.getGeneralParam("DOC_TYPE_SP_SIGN_OFF");	
	private String ELEMENT_ID;
	@Override
	public boolean invokeDisplayMode() {
		return true;
	}
	@Override
	public String displayMode(Object objectElement, HttpServletRequest request){	
		logger.debug("SP SIGN OFF DISPLAY MODE");
		Object masterObjectForm = FormControl.getMasterObjectForm(request);		
		ApplicationGroupDataM applicationGroup = (ApplicationGroupDataM)masterObjectForm;
		logger.debug("applicationGroup >> "+applicationGroup.getApplicationGroupId());
		ArrayList<ApplicationImageDataM> applicationImages = applicationGroup.getApplicationImages();
		if(!Util.empty(applicationImages)){
			for (ApplicationImageDataM applicationImage : applicationGroup.getApplicationImages()) {
				logger.debug("applicationImage >> "+applicationImage.getAppImgId());
				ArrayList<ApplicationImageSplitDataM> applicationImageSplits = applicationImage.getApplicationImageSplits();
				logger.debug("applicationImageSplits >> "+applicationImageSplits.size());
				if(!Util.empty(applicationImageSplits)){
					for (ApplicationImageSplitDataM applicationImageSplit : applicationImage.getApplicationImageSplits()) {
						logger.debug("applicationImageSplit >> "+applicationImageSplit.getDocType());
						if(!Util.empty(applicationImageSplit)){
							if(DOC_TYPE_SP_SIGN_OFF.equals(applicationImageSplit.getDocType())){
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
							if(DOC_TYPE_SP_SIGN_OFF.equals(applicationImageSplit.getDocType())){
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

		ApplicationDataM application = (ApplicationDataM)objectForm;
		ApplicationDataM lastApplication = (ApplicationDataM)lastObjectForm;

		if(Util.empty(application)){
			application = new ApplicationDataM();
		}
		if(Util.empty(lastApplication)){
			lastApplication = new ApplicationDataM();
		}
		
		Date spSignOffDate = application.getSpSignoffDate();	
		Date lastSpSignOffDate = lastApplication.getSpSignoffDate();
		
		String spSignOffBy = application.getSpSignoffAuthBy();
		String lastSpSignOffBy = lastApplication.getSpSignoffAuthBy();
		
		if("SP_SIGN_OFF_DATE".equals(ELEMENT_ID)){
			boolean compareSpSignOffDate = CompareObject.compare(spSignOffDate, lastSpSignOffDate, null);
			logger.debug("compareSpSignOffDate >> " + compareSpSignOffDate);
			if (!compareSpSignOffDate) {
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, "SP_SIGN_OFF_DATE"));
				audit.setOldValue(FormatUtil.display(lastSpSignOffDate ,FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
				audit.setNewValue(FormatUtil.display(spSignOffDate ,FormatUtil.TH,FormatUtil.Format.ddMMyyyy));
				audits.add(audit);
			}
		}
		if("SP_SIGN_OFF_AUTHORIZED_BY".equals(ELEMENT_ID)){
			boolean compareSpSignOffBy = CompareObject.compare(spSignOffBy, lastSpSignOffBy, null);
			logger.debug("compareSpSignOffBy >> " + compareSpSignOffBy);
			if (!compareSpSignOffBy) {
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, "SP_SIGN_OFF_AUTHORIZED_BY"));
				audit.setOldValue(FormatUtil.display(lastSpSignOffBy));
				audit.setNewValue(FormatUtil.display(spSignOffBy));
				audits.add(audit);
			}
		}
		return audits;
	}
	@Override
	public void auditInfo(String subformId, String elementId) {
		this.ELEMENT_ID = elementId;
	}
}
