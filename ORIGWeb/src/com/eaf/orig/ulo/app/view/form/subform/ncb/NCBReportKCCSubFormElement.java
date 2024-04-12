package com.eaf.orig.ulo.app.view.form.subform.ncb;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.core.ulo.common.display.ElementHelper;
import com.eaf.core.ulo.common.display.ElementInf;
import com.eaf.orig.ulo.model.ncb.NcbInfoDataM;

public class NCBReportKCCSubFormElement extends ElementHelper implements ElementInf{
	private static transient Logger logger = Logger.getLogger(NCBReportKCCSubFormElement.class);	
	@Override
	public String getElement(HttpServletRequest request, Object objectElement) {	
		NcbInfoDataM ncbInfo =(NcbInfoDataM)objectElement;
		String TRACKING_CODE = ncbInfo.getTrackingCode();
		String PERSONAL_ID = ncbInfo.getPersonalId();
		logger.debug("TRACKING_CODE>>"+TRACKING_CODE);
		logger.debug("PERSONAL_ID>>"+PERSONAL_ID);
		return "/orig/ulo/subform/ncb/NCBReportKCCSubForm.jsp?TRACKING_CODE="+TRACKING_CODE+"&PERSONAL_ID="+PERSONAL_ID;
	}
	@Override
	public String getDisplayType() {
		return ElementInf.DISPLAY_JSP;
	}
}
