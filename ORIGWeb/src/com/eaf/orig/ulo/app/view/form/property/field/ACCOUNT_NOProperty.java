package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.display.FormatUtil;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.model.AuditDataM;
import com.eaf.core.ulo.common.util.AuditFormUtil;
import com.eaf.core.ulo.common.util.CompareObject;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.CashTransferDataM;

public class ACCOUNT_NOProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(ACCOUNT_NOProperty.class);
	
	@Override
	public ArrayList<AuditDataM> auditForm(HttpServletRequest request,Object objectForm, Object lastObjectForm, Object objectValue){
		ArrayList<AuditDataM> audits = new ArrayList<AuditDataM>();
		try{
			String personalType = (String)objectValue;
			CashTransferDataM cashTransfer = (CashTransferDataM)objectForm;
			CashTransferDataM lastcashTransfer = (CashTransferDataM)lastObjectForm;
			
			if(Util.empty(cashTransfer)){
				cashTransfer = new CashTransferDataM();
			}
			if(Util.empty(lastcashTransfer)){
				lastcashTransfer = new CashTransferDataM();
			}
			
			boolean compareAccountNo = CompareObject.compare(cashTransfer.getTransferAccount(), lastcashTransfer.getTransferAccount(),null);
			if(!compareAccountNo){
				AuditDataM audit = new AuditDataM();
				audit.setFieldName(AuditFormUtil.getAuditFieldName(
						personalType, LabelUtil.getText(request, "TRANSFER_ACCOUNT_AUDIT"), request));
				audit.setOldValue(FormatUtil.displayText(lastcashTransfer.getTransferAccount()));
				audit.setNewValue(FormatUtil.displayText(cashTransfer.getTransferAccount()));
				audits.add(audit);
			}		
		}catch(Exception e){
			logger.fatal("ERROR >> ",e);
		}
		return audits;
	}
}
