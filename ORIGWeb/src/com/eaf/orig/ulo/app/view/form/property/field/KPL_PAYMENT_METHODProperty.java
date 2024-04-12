package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;

public class KPL_PAYMENT_METHODProperty  extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(KPL_PAYMENT_METHODProperty.class);
	String PRODUCT_K_PERSONAL_LOAN = SystemConstant.getConstant("PRODUCT_K_PERSONAL_LOAN");
	String ELEMENT_ID;
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		logger.debug("KPL_PAYMENT_METHODProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		try{			
			
			PaymentMethodDataM paymentMethod = (PaymentMethodDataM)objectForm;	
			PaymentMethodDataM lastpaymentMethod = (PaymentMethodDataM)lastObjectForm;	
			
			if(Util.empty(paymentMethod)){
				paymentMethod = new PaymentMethodDataM();
			}
			if(Util.empty(lastpaymentMethod)){
				lastpaymentMethod = new PaymentMethodDataM();
			}

			boolean compareAccountNo = CompareObject.compare(paymentMethod.getAccountNo(), lastpaymentMethod.getAccountNo(),null);
			if(!compareAccountNo){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(AuditFormUtil.getAuditFieldName(OrigConstant.PERSONAL_TYPE_APPLICANT, LabelUtil.getText(request, "ACCOUNT_NO"), request));
				audit.setOldValue(FormatUtil.displayText(lastpaymentMethod.getAccountNo()));
				audit.setNewValue(FormatUtil.displayText(paymentMethod.getAccountNo()));
				audits.add(audit);
			}
			
		}catch(Exception e){
			logger.fatal("ERROR >> ",e);
		}
		return audits;
	}
	
	@Override
	public void auditInfo(String subformId, String elementId) {
		this.ELEMENT_ID = elementId;
	}
}
