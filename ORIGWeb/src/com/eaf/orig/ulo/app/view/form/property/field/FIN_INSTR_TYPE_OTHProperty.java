package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;

@Deprecated
public class FIN_INSTR_TYPE_OTHProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(FIN_INSTR_TYPE_OTHProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("FIN_INSTR_TYPE_OTHProperty.validateForm");
		String PREFIX_ERROR = MessageErrorUtil.getText(request,"PREFIX_ERROR");
		FinancialInstrumentDataM finInstrumentM = (FinancialInstrumentDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(!Util.empty(finInstrumentM.getInstrumentType()) && 
				SystemConstant.getConstant("INSTRUMENT_TYPE_OTHER_VAL").equals(finInstrumentM.getInstrumentType())
				&& Util.empty(finInstrumentM.getInstrumentTypeDesc())) {
			formError.error("INSTRUMENT_TYPE_OTH_"+finInstrumentM.getSeq(),PREFIX_ERROR+LabelUtil.getText(request,"INSTRUMENT_TYPE"));
		}
		return formError.getFormError();
	}
}
