package com.eaf.orig.ulo.app.view.form.manual.defualtvalue;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.properties.SystemConstant;
import com.eaf.core.ulo.common.util.Util;
import com.eaf.orig.ulo.app.view.form.manual.displayvalue.Rekey2MakerDisplayValue;

public class PLACE_RECEIVE_CARD_DE1_2DefualtValue extends Rekey2MakerDisplayValue {
	private static final transient Logger logger = Logger.getLogger(PLACE_RECEIVE_CARD_DE1_2DefualtValue.class);
	String DEFAULT_MAILING_ADDRESS = SystemConstant.getConstant("DEFAULT_MAILING_ADDRESS");
	@Override
	public Object getValue(String elementFieldId, Object elementValue,HttpServletRequest request) {
		elementValue=super.getValue(elementFieldId, elementValue, request);
		if(Util.empty(elementValue)){
			elementValue=DEFAULT_MAILING_ADDRESS;
		}
		logger.debug("PLACE_RECEIVE_CARD_DE1_2DefualtValue>>"+elementValue);
		return elementValue;
	}
}
