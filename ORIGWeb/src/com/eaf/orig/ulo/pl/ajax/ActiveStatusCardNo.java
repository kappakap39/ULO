package com.eaf.orig.ulo.pl.ajax;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.eaf.orig.shared.constant.OrigConstant;
import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.model.app.PLAccountCardDataM;
import com.eaf.orig.ulo.pl.service.PLORIGEJBService;
import com.eaf.orig.ulo.pl.util.ejb.ORIGDAOUtilLocal;

public class ActiveStatusCardNo implements AjaxDisplayGenerateInf {
	static Logger logger = Logger.getLogger(ActiveStatusCardNo.class);
	@Override
	public String getDisplayObject(HttpServletRequest request)throws PLOrigApplicationException {
		String MESSAGE = "NOTACTIVE";
		String cardNo = request.getParameter("card_info_card_no");			
		logger.debug("cardNo >> "+cardNo);		
		ORIGDAOUtilLocal origBean = PLORIGEJBService.getORIGDAOUtilLocal();
		PLAccountCardDataM accCardM = origBean.loadAccountCardByCardNo(cardNo);
		if(null != accCardM && OrigConstant.ACTIVE_FLAG.equals(accCardM.getCardStatus())){
			MESSAGE = "ACTIVE";
		}
		logger.debug("MESSAGE >> "+MESSAGE);	
		return MESSAGE;
	}

}
