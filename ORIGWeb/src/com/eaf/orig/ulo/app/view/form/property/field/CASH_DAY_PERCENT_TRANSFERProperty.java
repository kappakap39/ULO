package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.ValidateFormInf;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.properties.FormControl;
import com.eaf.core.ulo.common.properties.ListBoxControl;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.ApplicationDataM;
import com.eaf.orig.ulo.model.app.ApplicationGroupDataM;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class CASH_DAY_PERCENT_TRANSFERProperty  extends FieldPropertyHelper{
	private static transient Logger logger = Logger.getLogger(CASH_DAY_PERCENT_TRANSFERProperty.class);
	String FIELD_ID_PERCENT_TRANSFER = SystemConstant.getConstant("FIELD_ID_PERCENT_TRANSFER");
	@Override
	public boolean invokeValidateFlag() {
		return true;
	}
	@Override
	public String validateFlag(HttpServletRequest request, Object objectForm) {
		logger.debug("CASH_DAY_PERCENT_TRANSFERProperty.validateFlag");
		String PRODUCT_K_EXPRESS_CASH = SystemConstant.getConstant("PRODUCT_K_EXPRESS_CASH");
		String CASH_DAY_1 = SystemConstant.getConstant("CASH_DAY_1");	
		String CASH_DAY_5 = SystemConstant.getConstant("CASH_DAY_5");
		String CASH_1_HOUR = SystemConstant.getConstant("CASH_1_HOUR");
		String []cashTransFerType = {CASH_DAY_1,CASH_DAY_5,CASH_1_HOUR};
		ApplicationGroupDataM applicationGroup= (ApplicationGroupDataM)FormControl.getObjectForm(request);
		ApplicationDataM applicationItem = applicationGroup.getApplicationProduct(PRODUCT_K_EXPRESS_CASH);
		
		CashTransferDataM cashTransfer = applicationItem.getCashTransfer(cashTransFerType);	
		logger.debug("cashTransfer "+cashTransfer);
		if(!Util.empty(cashTransfer) && !Util.empty(cashTransfer.getCashTransferType()) ){
			logger.debug("cashTransfer.getCashTransferType() "+cashTransfer.getCashTransferType());
			return ValidateFormInf.VALIDATE_YES;
		}
		return ValidateFormInf.VALIDATE_NO;
	}
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request, Object objectForm, Object lastObjectForm, Object objectValue) {
		logger.debug("CASH_DAY_PERCENT_TRANSFERProperty.auditForm");
		ArrayList<AuditDataM> audits = new ArrayList<>();
		try{
			CashTransferDataM cashTransfer = (CashTransferDataM)objectForm;
			CashTransferDataM lastCashTransfer = (CashTransferDataM)lastObjectForm;
			boolean compareFlag = CompareObject.compare(cashTransfer.getPercentTransfer(), lastCashTransfer.getPercentTransfer(),null);
			if(!compareFlag){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(LabelUtil.getText(request, "PERCENT_TRANSFER") + " ("+LabelUtil.getText(request, "CASH_TRANSFER_TYPE") + ")");
				audit.setOldValue(ListBoxControl.getName(FIELD_ID_PERCENT_TRANSFER, String.valueOf(lastCashTransfer.getPercentTransfer())));
				audit.setNewValue(ListBoxControl.getName(FIELD_ID_PERCENT_TRANSFER, String.valueOf(cashTransfer.getPercentTransfer())));
				audits.add(audit);
			}
		}catch(Exception e){
			logger.fatal("ERROR ",e);
		}
		return audits;
	}
}
