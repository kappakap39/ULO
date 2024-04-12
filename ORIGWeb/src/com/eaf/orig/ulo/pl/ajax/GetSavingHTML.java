package com.eaf.orig.ulo.pl.ajax;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import com.eaf.orig.shared.servlet.AjaxDisplayGenerateInf;
import com.eaf.orig.ulo.pl.app.dao.exception.PLOrigApplicationException;
import com.eaf.orig.ulo.pl.app.utility.DataFormatUtility;
import com.eaf.orig.ulo.pl.app.utility.SavingUtil;

public class GetSavingHTML implements AjaxDisplayGenerateInf {

	@Override
	public String getDisplayObject(HttpServletRequest request)
			throws PLOrigApplicationException {
		String type = request.getParameter("type");
		String bankname = request.getParameter("bankname");
		String fund = request.getParameter("fund");
		String balance = request.getParameter("balance");
		
		BigDecimal summary = new BigDecimal(0);
		summary.add(DataFormatUtility.StringToBigDecimal(balance));
		SavingUtil savingutil = new SavingUtil();
		String result = savingutil.getSavingHTML(type, bankname, fund, balance);
		return result+"|"+summary.toString();
	}

}
