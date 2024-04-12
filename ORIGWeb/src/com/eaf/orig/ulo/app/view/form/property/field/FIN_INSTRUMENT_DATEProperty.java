package com.eaf.orig.ulo.app.view.form.property.field;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.FieldPropertyHelper;
import com.eaf.core.ulo.common.message.LabelUtil;
import com.eaf.core.ulo.common.message.MessageErrorUtil;
import com.eaf.core.ulo.common.util.DateValidateUtil;
import com.eaf.core.ulo.common.util.FormErrorUtil;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.model.app.FinancialInstrumentDataM;

public class FIN_INSTRUMENT_DATEProperty extends FieldPropertyHelper {
	private static transient Logger logger = Logger.getLogger(FIN_INSTRUMENT_DATEProperty.class);
	@Override
	public boolean invokeValidateForm() {
		return true;
	}
	@Override
	public HashMap<String,Object> validateForm(HttpServletRequest request, Object objectForm) {	
		logger.debug("FIN_INSTRUMENT_DATEProperty.validateForm");
		FinancialInstrumentDataM finInstrumentM = (FinancialInstrumentDataM)objectForm;
		FormErrorUtil formError = new FormErrorUtil();
		if(!Util.empty(finInstrumentM.getOpenDate()) && !Util.empty(finInstrumentM.getExpireDate())){
			if(DateValidateUtil.validateDate(finInstrumentM.getExpireDate(), DateValidateUtil.LIMIT_YEAR_DATE_VALIDATE,DateValidateUtil.EN )){
				formError.error("END_DATE_"+finInstrumentM.getSeq(),LabelUtil.getText(request,"INSTR_END_DATE")+MessageErrorUtil.getText(request, "ERROR_DATE_FORMAT"));
			}else if(DateValidateUtil.validateDate(finInstrumentM.getOpenDate(), DateValidateUtil.LIMIT_YEAR_DATE_VALIDATE,DateValidateUtil.EN)){
				formError.error("START_DATE_"+finInstrumentM.getSeq(),LabelUtil.getText(request,"START_DATE")+MessageErrorUtil.getText(request, "ERROR_DATE_FORMAT"));
			}else{
				if(finInstrumentM.getExpireDate().before(finInstrumentM.getOpenDate())) {
					formError.error("END_DATE_"+finInstrumentM.getSeq(), MessageErrorUtil.getText(request,"ERROR_ID_FIN_INSTRUMENT_DATE"));
					formError.element("START_DATE_"+finInstrumentM.getSeq());
				}
			}
		}
		return formError.getFormError();
	}
}
