package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.PaymentMethodDataM;

public class PAY_METHODProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(PAY_METHODProperty.class);
	private String FIELD_ID_PAYMENT_METHOD = SystemConstant.getConstant("FIELD_ID_PAYMENT_METHOD");
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue) {
		logger.debug("PAY_METHODProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		String personalType = (String)objectValue;
		PaymentMethodDataM paymentMethod = (PaymentMethodDataM)objectForm;
		PaymentMethodDataM lastpaymentMethod = (PaymentMethodDataM)lastObjectForm;
		
		if(Util.empty(paymentMethod)){
			paymentMethod = new PaymentMethodDataM();
		}
		if(Util.empty(lastpaymentMethod)){
			lastpaymentMethod = new PaymentMethodDataM();
		}
		
		boolean comparePayment = CompareObject.compare(paymentMethod.getPaymentMethod(), lastpaymentMethod.getPaymentMethod(),null);
		if(!comparePayment){
			AuditDataM audit = new AuditDataM();
			audit.setFieldName(AuditFormUtil.getAuditFieldName(
					personalType, LabelUtil.getText(request, "PAYMENT_METHOD"), request));
			audit.setOldValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_PAYMENT_METHOD, lastpaymentMethod.getPaymentMethod())));
			audit.setNewValue(FormatUtil.displayText(ListBoxControl.getName(FIELD_ID_PAYMENT_METHOD, paymentMethod.getPaymentMethod())));
			audits.add(audit);
		}
		return audits;
	}
}
